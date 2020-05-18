var http = require("http");
var url = require("url");
var fs = require("fs");
var path = require("path");
var socketio = require("socket.io");

var MongoClient = require('mongodb').MongoClient;
var MongoServer = require('mongodb').Server;
var mimeTypes = { "html": "text/html", "jpeg": "image/jpeg", "jpg": "image/jpeg", "png": "image/png", "js": "text/javascript", "css": "text/css", "swf": "application/x-shockwave-flash"};

var httpServer = http.createServer(
	function(request, response) {
		var uri = url.parse(request.url).pathname;
		if (uri=="/") uri = "/usuario.html";
		var fname = path.join(process.cwd(), uri);
		fs.exists(fname, function(exists) {
			if (exists) {
				fs.readFile(fname, function(err, data){
					if (!err) {
						var extension = path.extname(fname).split(".")[1];
						var mimeType = mimeTypes[extension];
						response.writeHead(200, mimeType);
						response.write(data);
						response.end();
					}
					else {
						response.writeHead(200, {"Content-Type": "text/plain"});
						response.write('Error de lectura en el fichero: '+uri);
						response.end();
					}
				});
			}
			else{
				console.log("Peticion invalida: "+uri);
				response.writeHead(200, {"Content-Type": "text/plain"});
				response.write('404 Not Found\n');
				response.end();
			}
		});
	}
);

//valor por defecto
var estadoPersiana = 'abierta';
var estadoAC = 'encendido';

MongoClient.connect("mongodb://localhost:27017/", function(err, db) {
    httpServer.listen(8080);
    var io = socketio.listen(httpServer);

	var dbo = db.db("sensoresBD");
	dbo.createCollection("sensores", function(err, collection){
    	io.sockets.on('connection',
		function(client) {

			client.on('infoSensores', function (data) {

                collection.insert(data, {safe:true}, function(err, result) {});

                var informacion = "temperatura: " + data.temperatura + ", luminosidad: " + data.luminosidad + ", fecha: " + data.fecha;

				//se notifica a todos los clientes
				io.sockets.emit('actualizar',informacion);
                //llamar al agente para que controle que no se pase de un valor
			});
			
			client.on('getEstadoPersiana', function (data){
				client.emit('estadoPersiana', estadoPersiana);
			});

			client.on('getEstadoAC', function (data){
				client.emit('estadoAC', estadoAC);
			});

			client.on('cambiarEstadoPersiana', function (data){
				if (estadoPersiana == 'abierta')
					estadoPersiana = 'cerrada';
				else
					estadoPersiana = 'abierta';

				io.sockets.emit('estadoPersiana', estadoPersiana);
				//llamas a todos los usuarios

				//aqui añadir un mensaje a sensores para cambiar información
				//tanto de luz como de temperatura
			});	

			client.on('cambiarEstadoAC', function (data){
				if (estadoAC == 'ecendido')
					estadoAC = 'apagado';
				else
					estadoAC = 'ecendido';

				io.sockets.emit('estadoAC', estadoAC);
				//llamas a todos los usuarios

				//aqui añadir un mensaje a sensores para cambiar información
				//solo de temperatura
			});	
            
		});
    });
});

console.log("Servicio MongoDB iniciado");


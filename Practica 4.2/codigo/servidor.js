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

/* var temperatura = 20;
var luminosidad = 20; */

MongoClient.connect("mongodb://localhost:27017/", function(err, db) {
    httpServer.listen(8080);
	var io = socketio.listen(httpServer);
	
	//valores de umbral máximo
	var temperaturaMaxima = 45;
	var luminosidadMaxima = 35;

	//valores de umbral mínimo
	var temperaturaMinima = 15;
	var luminosidadMinima = 10;

	//intervalo de temperatura maximo de seguridad
	var intervaloTempeSupMaximo = 44;
	var intervaloTempeInfMaximo = 38;

	//intervalo de temperatura maximo de seguridad
	var intervaloTempeSupMinimo = 0;
	var intervaloTempeInfMinimo = 14;

	//intervalo de luminosidad maximo de seguridad
	var intervaloLumSupMaximo = 34;
	var intervaloLumInfMaximo = 30;

	//intervalo de luminosidad maximo de seguridad
	var intervaloLumSupMinimo = 0;
	var intervaloLumInfMinimo = 8;


	var dbo = db.db("sensoresBD");
	dbo.createCollection("sensores", function(err, collection){
    	io.sockets.on('connection',
		function(client) {

			client.on('infoSensores', function (data) {

                collection.insert(data, {safe:true}, function(err, result) {});

                var informacion = "temperatura: " + data.temperatura + ", luminosidad: " + data.luminosidad + ", fecha: " + data.fecha;

				//se notifica a todos los clientes
				io.sockets.emit('actualizar',informacion);
				io.sockets.emit('refrescar',{temperatura:data.temperatura, luminosidad:data.luminosidad});
				
				agente(data.temperatura,data.luminosidad);
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

				io.sockets.emit('actualizarEstadoPersiana', {persiana:estadoPersiana});

				//aqui añadir un mensaje a sensores para cambiar información
				//tanto de luz como de temperatura
			});	

			client.on('cambiarEstadoAC', function (data){
				if (estadoAC == 'encendido')
					estadoAC = 'apagado';
				else
					estadoAC = 'encendido';

				io.sockets.emit('estadoAC', estadoAC);
				//llamas a todos los usuarios

				io.sockets.emit('actualizarEstadoAC', {AC:estadoAC});

			});	

			function agente(temperatura,luminosidad){
				console.log("agente trabajando");

				if(temperatura <= temperaturaMinima)
					io.sockets.emit('alertas', "Peligro!!, temperatura por debajo de la mínima");

				if(luminosidad <= luminosidadMinima)
					io.sockets.emit('alertas', "Peligro!!, luminosidad por debajo de la mínima");

				if(temperatura >= temperaturaMaxima)
					io.sockets.emit('alertas', "Peligro!!, temperatura por encima de la maxima");

				if(luminosidad >= luminosidadMaxima)
					io.sockets.emit('alertas', "Peligro!!, luminosidad por encima de la maxima");


				if(temperatura >= temperaturaMaxima || luminosidad >= luminosidadMaxima){
					console.log("cerrando persiana");
					if(estadoPersiana != 'cerrada')
					{
						io.sockets.emit('estadoPersiana', 'cerrada');
						io.sockets.emit('actualizarEstadoPersiana', {persiana:'cerrada'});
					}
				}


				if(temperatura < intervaloTempeSupMaximo && temperatura > intervaloTempeInfMaximo)
				{
					if(estadoAC != 'encendido')
					{
						io.sockets.emit('estadoAC', 'encendido');
						io.sockets.emit('actualizarEstadoAC', {AC:'encendido'});
						io.sockets.emit('alertas', "Se ha encendido AC por agente");
					}

				}

				if(temperatura < intervaloTempeSupMinimo && temperatura > intervaloTempeInfMinimo)
				{
					if(estadoAC != 'apagado')
					{
						io.sockets.emit('estadoAC', 'apagado');
						io.sockets.emit('actualizarEstadoAC', {AC:'apagado'});
						io.sockets.emit('alertas', "Se ha apagado AC por agente");
					}
					else if(estadoPersiana != 'cerrada')
					{
						io.sockets.emit('estadoPersiana', 'cerrada');
						io.sockets.emit('actualizarEstadoPersiana', {persiana:'cerrada'});
						io.sockets.emit('alertas', "Se ha cerrado la persiana por agente");
					}
				}

				if(luminosidad < intervaloLumSupMaximo && luminosidad > intervaloLumInfMaximo)
				{
					if(estadoPersiana != 'cerrada')
					{
						io.sockets.emit('estadoPersiana', 'cerrada');
						io.sockets.emit('actualizarEstadoPersiana', {persiana:'cerrada'});
						io.sockets.emit('alertas', "Se ha cerrado la persiana por agente");
					}

				}

				if(luminosidad < intervaloLumSupMinimo && luminosidad > intervaloLumInfMinimo)
				{
					if(estadoPersiana != 'abierta')
					{
						io.sockets.emit('estadoPersiana', 'abierta');
						io.sockets.emit('actualizarEstadoPersiana', {persiana:'abierta'});
						io.sockets.emit('alertas', "Se ha abierta la persiana por agente");
					}

				}
				
			}
            
		});
    });
});

console.log("Servicio MongoDB iniciado");


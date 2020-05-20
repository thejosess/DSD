var http = require("http");
var url = require("url");
var fs = require("fs");
var path = require("path");
var socketio = require("socket.io");

var request = require('request');

var MongoClient = require('mongodb').MongoClient;
var MongoServer = require('mongodb').Server;
var mimeTypes = { "html": "text/html", "jpeg": "image/jpeg", "jpg": "image/jpeg", "png": "image/png", "js": "text/javascript", "css": "text/css", "swf": "application/x-shockwave-flash"};

var nombre;

function robo(){
	console.log("te han robado y no te has dado cuenta");
}

var httpServer = http.createServer(
	function(request, response) {

		var adr = "http://127.0.0.1:8080" + request.url;
		const current_url = new URL(adr);
		const search_params = current_url.searchParams;	
		nombre = search_params.get('atracador');
		console.log(nombre);

		if(nombre != null)
			robo();
		

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

MongoClient.connect("mongodb://localhost:27017/", function(err, db) {
    httpServer.listen(8080);
	var io = socketio.listen(httpServer);

	var dbo = db.db("sensoresBD");
	
	dbo.createCollection("sensores", function(err, collection){
    	io.sockets.on('connection',
		function(client) {

			client.on('tiempoCiudad', function (data){
				var tiempoAPI;

				var apiKey = '89b2e927d79029343f1fd3e0ce0f8071';
				var ciudad = data;
				var url = `http://api.openweathermap.org/data/2.5/weather?q=${ciudad}&appid=${apiKey}`;
			
				request(url, function (err, response, data) {
				  if(err){
					console.log('error:', error);
				  } else {
					console.log(data);
					var tiempo = JSON.parse(data);
					tiempoAPI = `Hacen ${tiempo.main.temp/10} grados en ${tiempo.name} y hace una humedad de ${tiempo.main.humidity} !`;
					console.log('data:', tiempoAPI);
					client.emit('consultaCiudad',tiempoAPI);
				  }
				});
			});

			client.on('infoSensores', function (data) {

                collection.insert(data, {safe:true}, function(err, result) {});

                var informacion = "temperatura: " + data.temperatura + ", luminosidad: " + data.luminosidad + ", fecha: " + data.fecha;

/* 				var informacion = collection.find(informacion);
 */				//se notifica a todos los clientes
				io.sockets.emit('actualizar',informacion);
				io.sockets.emit('refrescar',{temperatura:data.temperatura, luminosidad:data.luminosidad});
				
				agente(data.temperatura,data.luminosidad,false);
			});

			client.on('registro', function () {

				collection.find().sort({_id:-1}).limit(3).forEach(function(result){
					var informacion = "temperatura: " + result.temperatura + ", luminosidad: " + result.luminosidad + ", fecha: " + result.fecha;
					client.emit('registroBD',informacion);
				});

			});

			client.on('temperaturaValor',function(data){
				//lo limito a 10
				collection.find({ temperatura: { $gt: data } }).limit(10).sort({_id:-1}).forEach(function(result){
					var informacion = "temperatura: " + result.temperatura + ", luminosidad: " + result.luminosidad + ", fecha: " + result.fecha;
					client.emit('consultaTemperatura',informacion);
				});
			});			

/* 			client.on('registroFuego', function(data){
				console.log("REGISTRO FUEGO");

				var prueba = collection.find( { fuego: { $eq: "hubo un fuego" } } )
				console.log(prueba.fecha);
			}); */
			
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

				agente(0,0,true);

			});	

			client.on('cambiarEstadoAC', function (data){
				if (estadoAC == 'encendido')
					estadoAC = 'apagado';
				else
					estadoAC = 'encendido';

				io.sockets.emit('estadoAC', estadoAC);
				//llamas a todos los usuarios

				io.sockets.emit('actualizarEstadoAC', {AC:estadoAC});

				agente(0,0,true);

			});
			
			client.on('fuego', function (data){
				io.sockets.emit('antiFuego');
				io.sockets.emit('alertaFuego', "Se detectado fuego y se ha usado el sistema de agua");

			});

			client.on('usandoAntiFuego',function(data){
				if(estadoPersiana != 'abierta')
				{
					io.sockets.emit('estadoPersiana', 'abierta');
					io.sockets.emit('actualizarEstadoPersiana', {persiana:'abierta'});
					io.sockets.emit('alertas', "Se ha abierta la persiana por el fuego");
				}

				io.sockets.emit('tempeFuego',data);
			});

			function agente(temperatura,luminosidad, cambioEstado){
				console.log("agente trabajando");

				if(!cambioEstado)
				{
					if(temperatura <= temperaturaMinima)
						io.sockets.emit('alertas', "Peligro!!, temperatura por debajo de la mínima");

					if(luminosidad <= luminosidadMinima)
						io.sockets.emit('alertas', "Peligro!!, luminosidad por debajo de la mínima");

					if(temperatura >= temperaturaMaxima)
						io.sockets.emit('alertas', "Peligro!!, temperatura por encima de la maxima, agente cierra persiana");

					if(luminosidad >= luminosidadMaxima)
						io.sockets.emit('alertas', "Peligro!!, luminosidad por encima de la maxima, agente cierra persiana");


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
				else{
					if(estadoAC == 'encendido' && estadoPersiana == 'abierta')
						io.sockets.emit('alertas', "Tienes la ventana abierta y el AC encendido");
				}
				
			}
            
		});
	});
	

	dbo.createCollection("registrosFuego", function(err, collection){
    	io.sockets.on('connection',
		function(client) {

		client.on('registroFuego', function(data){
			collection.insert(data, {safe:true}, function(err, result) {});
			console.log("guardando registro fuego");

		});

		client.on('registroBDFuego', function () {
			collection.find().sort({_id:-1}).limit(4).forEach(function(result){
				var informacion = "Hubo un fuego en la fecha:  " + result.fecha;
				console.log("recogiendo datos fuego");
				client.emit('actualizarInfoFuego',informacion);
			});
		});
	});
});


});

console.log("Servicio MongoDB iniciado");


# Practica 4.2: Sistema domótico ~ José Santos
En esta práctica vamos a realizar el desarrollo de un sistema domótico básico compuesto de dos sensores (luminosidad y temperatura), dos actuadores (motor persiana y sistema de aire acondicionado), un servidor que sirve páginas para mostrar el estado y actuar sobre los elementos de la vivienda. Dicho servidor tiene también un agente capaz de notificar alarmas y tomar decisiones. Importante leer la referencía antes de continuar con el trabajo [5].


![](img/figura1.png)


## Usuario y servidor básicos
En primer lugar decidí crear el servidor (archivo node js) y el usuario (archivo html) de una forma básica e inspirandome en los ejemplos de la parte obligatoria. Con el servidor tuve dudas respecto a los sensores. No tenía muy claro si los sensores eran datos aleatorios mediante una hebra o era necesario crear un archivo html para estos. Tras comprender mejor el pdf, me dí cuenta de que era necesario crear un archivo html y este sería mediante un formulario el que enviaría los datos al servidor. Pensé que la mejor idea era que el servidor despachara esas url de la siguiente forma (utilizando una filosofía de url limpia y dejando el servidor como manejador de url, tal y como hago en SIBW con php pero aqui con node)

    let http = require('http');
    let fs = require('fs');
    
    let handleRequest = (request, response) => {
        console.log(request.url);
        if(request.url == '/sensores.html')
        {    
            response.writeHead(200, {
                'Content-Type': 'text/html'
            });
            fs.readFile('sensores.html', null, function (error, data) {
                if (error) {
                    response.writeHead(404);
                    response.write('Whoops! js not found!');
                } else {
                    response.write(data);
                }
                response.end();
            });
        }
        else
        {    
            response.writeHead(200, {
                'Content-Type': 'text/html'
            });
            fs.readFile('./usuario.html', null, function (error, data) {
                if (error) {
                    response.writeHead(404);
                    response.write('Whoops! File not found!');
                } else {
                    response.write(data);
                }
                response.end();
            });
        }
    };
    
    http.createServer(handleRequest).listen(8181);


Pero me di cuenta de que esto violaría en cierta medida la separación de responsabilidades y la ocultación de funcionamiento, ya que el usuario accede al servicio mediante la url pero los sensores no pueden ser accedidos por el usuario. Por lo tanto decidí cargar unicamente usuario.html en el servidor y para acceder a los sensores, abrir el html de forma "manual". 

    var httpServer = http.createServer(
	function(request, response) {
        var uri = request.url;
		var fname = path.join(process.cwd(), uri);
		fs.exists(fname, function(exists) {
			if (exists && (uri == 'usuario.html' || uri == 'sensores.html')) {
				fs.readFile(fname, function(err, data){
					if (!err) {
						var extension = path.extname(fname).split(".")[1];
						var mimeType = mimeTypes[extension];
						response.writeHead(200, mimeType);
						response.write(data);
						response.end();
					}

                            ...

## Sensores
Para crear sensores.html entendí que era necesario una simulación de estos datos, sin embargo y tras hablar por el foro y leer el guión me di cuenta de que era mediante un formulario den sensores.html donde se enviaría esos datos al servidor (con formato JSON) y este se lo enviaría a todos los usuario.[2]


        <script src="/socket.io/socket.io.js"></script>
        <script type="text/javascript">
            var serviceURL = 'localhost:8080';
            var socket = io.connect(serviceURL);

        function enviarInformacion(){
            console.log("enviado información sensores");

		    var serviceURL = 'localhost:8080';
			var socket = io.connect(serviceURL);
            
            var tempe = document.getElementById("tempe").value;
            var luz = document.getElementById("luz").value;

            console.log(tempe);

            var date =  new Date();
            var fecha = (date.getMonth()+1) + "/" + date.getDate()+ "/" + date.getFullYear() + " a las "+ date.getHours() + ":" + (date.getMinutes()<10?'0':'') + date.getMinutes(); 

            socket.emit('infoSensores', {temperatura:tempe, luminosidad:luz, fecha:fecha});
	    }
	    </script>

socket.emit('infoSensores) envía la información al servidor y este se la envía al usuario y la guarda en la base de datos.

        io.sockets.on('connection',
		function(client) {

			client.on('infoSensores', function (data) {

                collection.insert(data, {safe:true}, function(err, result) {});

                var informacion = "temperatura: " + data.temperatura + ", luminosidad: " + data.luminosidad + ", fecha: " + data.fecha;

                io.sockets.emit('actualizar',informacion);
                
            });

                            ...

Para realizar esta parte me encontré con dificultades, fruto de no tener mucha práctica con estas tecnologías. Para acceder a sensores.html, lo hacía desde el sistema de fichero y esto me reportaba el siguiente error:

        <script> con origen "file:///socket.io/socket.io.js". sensores.html
        ReferenceError: io is not defined.
        
esto se producía ya que al no acceder desde el servidor, no encontraba el archivo socket.io.js y por tanto no definía la variable io. Para ello hace falta acceder de la siguiente forma

    http://127.0.0.1:8080/sensores.html

Cabe destacar que para depurar use la orden 

    document.write('<div>Print this after the script tag</div>')

y a la hora de hacer el formulario tuve que buscar como llamar a una función en javascript ya que yo solo sabía hacerlo con PHP (en sensores.html) [1]

    		<form action="javascript:void(0);" onsubmit="javascript:enviarInformacion();">
            <label for="fname">Luminosidad (lm):</label>
                        ...
          </form> 


sensores funcionando

![](img/sensores.png)


## Cambiar y obtener estados persiana y AC
Después de realizar el sensor, me puse con los estados de persiana y AC,para ello es necesario pedir el estado de la persiana y AC para poder mostrarlo (ojo con el orden para declarar las funciones, esto es Javascript ;) ) .

        socket.on('estadoPersiana', function(data){
            var estadoPersiana = document.getElementById('estadoPersiana');
            estadoPersiana.innerHTML = data;
        });

        socket.on('estadoAC', function(data){
            var estadoAC = document.getElementById('estadoAC');
            estadoAC.innerHTML = data;
        });

        socket.emit('getEstadoPersiana');
        socket.emit('getEstadoAC');

El servidor recibe dicha petición y le muestra el valor del estado de la solicitud (dos variables que tiene almacenadas, estadoPersiana y estadoAC, encendido y abierto por defecto)

    			client.on('getEstadoPersiana', function (data){
				client.emit('estadoPersiana', estadoPersiana);
			});

			client.on('getEstadoAC', function (data){
				client.emit('estadoAC', estadoAC);
			});

Tras realizar esto me dispuse a desarrollar los cambios de estado de persiana y AC. Para es necesario que tras clickar en el botón el usuario en usuario.html,

    			<button id="cambiarEstadoPersiana" style="background-color: LightGray;" onclick="cambiarEstadoPersiana()">Cambiar estado persiana </button>

 se le envíe una petición al servidor cambiando el estado de la persiana
 
                        function cambiarEstadoPersiana(){
            socket.emit('cambiarEstadoPersiana');
            }

reciba la respuesta por parte del servidor para cambiar el estado (añadir en el html). 

            socket.on('estadoPersiana', function(data){
            var estadoPersiana = document.getElementById('estadoPersiana');
            estadoPersiana.innerHTML = data;
            });



Aunque en el guión no aparece añadí de forma adicional que tras cambiar el estado de la persiana o del AC, el servidor enviará una petición al sensor (simulando que tras cambiar el estado, vuelve a tomar las medidas), este da respuesta al servidor y el servidor a su vez llama al resto de cliente para que actualicen su información.

        	client.on('cambiarEstadoPersiana', function (data){
				if (estadoPersiana == 'abierta')
					estadoPersiana = 'cerrada';
				else
					estadoPersiana = 'abierta';

				io.sockets.emit('estadoPersiana', estadoPersiana);
				//llamas a todos los usuarios

				io.sockets.emit('actualizarEstadoPersiana', {persiana:estadoPersiana, temperatura:temperatura, luminosidad:luminosidad});
			});	

Los sensores tienen dos variables donde se almacenan la temperatura y la luminosidad, además de un valor factor que intenta simular la estación del año. Esto quiere decir que si fuese invierno factor tendría un valor negativo, ya que si tu abres la ventana en invierno, la temperatura baja y en el casod de la luminosidad, se hace un valor absoluto, independientemente si es verano o invierno, si abres la ventana, aumenta la luminosidad. En mi caso, el factor está en verano y si apagas el AC, entonces aumenta la temperatura y si abres la persiana también aumenta la temperatura ya que entra el calor de fuera.

servidor

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

sensores.html

            socket.on('actualizarEstadoPersiana', function(data){


            if(data.persiana == 'abierta')
            {
                temperatura = temperatura + factor;
                luminosidad = luminosidad + Maths.abs(factor);
            }
            else{
                temperatura = temperatura - factor;
                luminosidad = luminosidad - Maths.abs(factor);
            }
            var date =  new Date();
            var fecha = (date.getMonth()+1) + "/" + date.getDate()+ "/" + date.getFullYear() + " a las "+ date.getHours() + ":" + (date.getMinutes()<10?'0':'') + date.getMinutes();

            socket.emit('infoSensores', {temperatura:temperatura, luminosidad:luminosidad, fecha:fecha});
        });

Hasta este punto, tuve que solucionar un fallo en el que llamaba desde servidor.js de la siguiente forma a sensores.html 
    				client.emit('cambioEstadoAC', estadoAC);

como es lógico, fallaba y al darme cuenta lo cambie por

        				io.sockets.emit('actualizarEstadoPersiana', {persiana:estadoPersiana});

Para aclarar lo que se realiza aquí, he diseñado un diagrama báscio para ilustrarlo. [3]
![](img/cambioEstado.png)

Primero el usuario pide un cambioEstado tras clickar en el botón, el servidor lo recibe y le envía el cambio de estado para poder mostrar en el html del usuario y además el servidor le dice a sensores que se ha cambiado el estado, entonces este actualiza su información respecto a la temperatura y la luminosidad. Tras actualizar su información, le dice al servidor que ha actualizado su información (infoSensores) y el servidor le envía la información actualizada al usuario. (video probando esto) [4]   
Añadir también que estos cambios de estados se realizan para todos los usuarios.

## Agente básico
Con el afán de mantener una correcta separación de responsabilidades, servidor tiene los estados de AC y de la persiana y el sensor.html tiene la temperatura y la luminosidad. 


			function agente(temperatura,luminosidad){
				console.log("agente trabajando");

				if(temperatura <= temperaturaMinima)
					io.sockets.emit('alertas', "Peligro!!, temperatura por debajo de la mínima");

                                            ...

				if(temperatura >= temperaturaMaxima || luminosidad >= luminosidadMaxima){
					console.log("cerrando persiana");
					if(estadoPersiana != 'cerrada')
					{
						io.sockets.emit('estadoPersiana', 'cerrada');
						io.sockets.emit('actualizarEstadoPersiana', {persiana:'cerrada'});
					}
				}

Por lo tanto a la hora de actuar el agente, he implementado una función agente que se le pasa una temperatura y una luminosidad y esta función la que se encarga de decidir si actuar sobre los estados de AC y de la persiana, así como de alertar al usuario. Dicha función agente se llama, cada vez que el sensor le envía información actualizada al servidor.

    			client.on('infoSensores', function (data) {

                collection.insert(data, {safe:true}, function(err, result) {});

                var informacion = "temperatura: " + data.temperatura + ", luminosidad: " + data.luminosidad + ", fecha: " + data.fecha;

                                ...
				agente(data.temperatura,data.luminosidad);
			});

Es de interés señalar que mi agente si enciendes el AC y este provoca que aumente la temperatura (o la luminosidad) por encima de la temperatura máxima, entonces este recibe la temperatura cambiada por el sensor gracias al servidor y cierra la persiana (tal y como dice el guión). También hago algunas consideraciones adicionales en el agente y controlo si tanto la temperatura como la luminosidad se encuentran entre unos intervalos (tanto máximos como mínimos) peligrosos y realiza acciones como cerrar persianas o apagar el AC. Esto hace posible que no haga falta llamar al agente desde el cambio de estado, ya que en el cambio de estado se llama a sensores y este llama a servidor (mirar sección del cambio de estado, hablado en el punto anterior) con la nueva información (y aqui llama a agente).

    			if(temperatura < intervaloTempeSupMaximo && temperatura > intervaloTempeInfMaximo)
				{
					if(estadoAC != 'encendido')
					{
						io.sockets.emit('estadoAC', 'encendido');
						io.sockets.emit('actualizarEstadoAC', {AC:'encendido'});
						io.sockets.emit('alertas', "Se ha encendido AC por agente");
					}

				}

Y aviso a los usuarios de que ha sido el agente quien lo ha realizado, todo ello para no asustarlos (podrían pensar que todo está controlado por Hal 9000 y no queremos eso).   
Tal y como se ve en el diagrama, cuando se realiza un cambio de estado y sensores.html devuelve la información actualiza (1.2infoSensores), servidor.js llama a la función agente y esta tras recibir una anomalía, realiza un cambio de estado y vuelve a llamar a actualizarEstado (con el cambio correspondiente) de sensores.html y este ya si devuelve la información actualizada que se envía al usuario.html.
![](img/agente.png).
He realiza un video mostrando su funcionamiento como en el punto anterior, ya que con imagenes no creo que se ilustre de forma correcta (videos/agente.webm)

## Detector de humo

            socket.emit('fuego');
            al servidor y este al sistema antifuego


es cierto pagina es fea pero la finalidad de esta practica está en la comunicación y hacer uso de otras cosas
detector de humo lo he hecho con js para cambiar respecto al resto que son html e incrustas el js

explicar que he añadido la información de la temepratura actual 
y que lo llamo al inicio de servidor o sensores.html

problema es que tengo que llamar desde antiFuego a servidor y este a sensores
    socket.on('antiFuego', function(data) {
        console.log("utilizando aspersores");
        socket.emit('usandoAntiFuego');
    });

//es algo lioso tanta comunicaicón pero así separo y aislo funciuounalidades
es algo inutil pudiendo hacerlo desde servidor pero es una simulación

para obtener todos los datos de la bd es 
db.collectionName.find()

esxplicar que los usuarios solo ven la sesión actual, para ver datos de la BD, tienen que pedirlo
me parecía una burrada tener que estar dandole al usuario todo lo que hay en la BD continuamente

añadir lo de la BD que lo he puesto para que se vean tambien como "consultas" aunque no sean así
y que he podido trabajar con BD

al principio lo hacia asi.
			client.on('registro', function () {
				collection.find().sort({_id:-1}).limit(3).forEach(function(result){
					var informacion = "temperatura: " + result.temperatura + ", luminosidad: " + result.luminosidad + ", fecha: " + result.fecha;
					client.emit('registroBD',informacion);
				});
			});
pero estaba enviando muchos mensajes, mejor hacer un array
referencia: https://docs.mongodb.com/manual/reference/method/db.collection.find/
es importante lo de id
me costaba mucho hacerlo y al final tuve que hacerlo de una forma poco ortodoxa
hice una array global 	var arrayG = [];
es que se quedaba en el ambito de la funcion y no quería enviar tantos mensajes a los clientes y no sabía como hacerlo de otra forma, estuve mirando la documentación de mongoDB pero no encontré nada que me satisfaciera.

al final lo dejé enviando tantos maensajes porque no encontré otra fomra

array y recorrerlos tampoco sabia en javascritp
            datos.array.forEach(element => {
            var listElement = document.getElementById('listBD');
			var listItem = document.createElement('li');
            listItem.innerHTML = element;
            //ya son enviado en formato JSON
            listElement.appendChild(listItem);
            });

y

muchas veces me equivocaba porque accedia al JSON directamente y me mostraba object en vez de los valores

explicar lo de la alarma

probe a hacer         socket.emit('registroFuego',data);


			client.on('registroFuego', function(data){
				console.log("REGISTRO FUEGO");

				var prueba = collection.find( { fuego: { $eq: "hubo un fuego" } } )
				console.log(prueba.fecha);
			});

yu liuego esto en server
			client.on('registroFuego', function(data){
				collection.insert(data, {safe:true}, function(err, result) {});
				console.log("guardando registro fuego");
			});
pero no terminabas de ir

creando una collections distintas ya que no me permitía hacerlo mas a mocho en una sola collections

[1]:https://stackoverflow.com/questions/1818249/form-with-no-action-and-where-enter-does-not-reload-page
[2]:https://stackoverflow.com/questions/8935414/getminutes-0-9-how-to-display-two-digit-numbers
[3]: el diagrama está en la carpeta de imagenes
[4]: en esta ruta video/cambioEstados.webm hay un video con su funcionamiento, ha sido grabado con una herramienta de Ubuntu y he de añadir que ha ido muy bien.
[5]: Es importante tener en cuenta que yo he considerado que estamos en verano y de día, abrir la persiana aumenta la temperatura y la luminosidad. Y también he considerado que el AC es en el modo para bajar la temperatura, por lo tanto encederlo baja la temperatura y apagarlo, aumenta la temperatura.
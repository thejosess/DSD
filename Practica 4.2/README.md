# Practica 4.2: Sistema domótico ~ José Santos
En esta práctica vamos a realizar el desarrollo de un sistema domótico básico compuesto de dos sensores (luminosidad y temperatura), dos actuadores (motor persiana y sistema de aire acondicionado), un servidor que sirve páginas para mostrar el estado y actuar sobre los elementos de la vivienda. Dicho servidor tiene también un agente capaz de notificar alarmas y tomar decisiones.

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

Los sensores tienen dos variables donde se almacenan la temperatura y la luminosidad, además de un valor factor que intenta simular la estación del año. Esto quiere decir que si fuese invierno factor tendría un valor negativo, ya que si tu abres la ventana en invierno, la temperatura baja y en el casod de la luminosidad, se hace un valor absoluto, independientemente si es verano o invierno, si abres la ventana, aumenta la luminosidad.

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


[1]:https://stackoverflow.com/questions/1818249/form-with-no-action-and-where-enter-does-not-reload-page
[2]:https://stackoverflow.com/questions/8935414/getminutes-0-9-how-to-display-two-digit-numbers
[3]: el diagrama está en la carpeta de imagenes
[4]: en esta ruta video/cambioEstados.webm hay un video con su funcionamiento, ha sido grabado con una herramienta de Ubuntu y he de añadir que ha ido muy bien.
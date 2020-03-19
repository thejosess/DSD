/*
 * This is sample code generated by rpcgen.
 * These are only templates and you can use them
 * as a guideline for developing your own functions.
 */

#include "calculadora.h"
#include <unistd.h>
#include <stdio.h>


void operaciones_basicas(char *host, int entero1, int entero2, char operador){

	CLIENT *clnt;
	double  *result_1;

#ifndef	DEBUG
clnt = clnt_create (host, CALPROG, DIRVER, "udp");
if (clnt == NULL) {
	clnt_pcreateerror (host);
	exit (1);
}
#endif	/* DEBUG */

	switch(operador)
		{
		case '+':
			result_1 = suma_1(entero1, entero2, clnt);
			if (result_1 == (double *) NULL) {
				clnt_perror (clnt, "call failed");
			}
			else
			{
				printf("\nEl resultado es:\n");
				printf("%lf \n", *result_1);
			}
			break;
		case '-':
			result_1 = resta_1(entero1, entero2, clnt);
			if (result_1 == (double *) NULL) {
				clnt_perror (clnt, "call failed");
			}
			else
			{
				printf("\nEl resultado es:\n");
				printf("%lf \n", *result_1);
			}
			break;

		case 'x':
			result_1 = multiplicacion_1(entero1, entero2, clnt);
			if (result_1 == (double *) NULL) {
				clnt_perror (clnt, "call failed");
			}
			else
			{
				printf("\nEl resultado es:\n");
				printf("%lf \n", *result_1);
			}
			break;
		case '/':
			result_1 = division_1(entero1, entero2, clnt);
			if (result_1 == (double *) NULL) {
				clnt_perror (clnt, "call failed");
			}
			else
			{
				printf("\nEl resultado es:\n");
				printf("%lf \n", *result_1);
			}
			break;
		}


#ifndef	DEBUG
	clnt_destroy (clnt);
#endif	 /* DEBUG */

}

void mostrar_vector(t_array *v1){
	printf("\nEl resultado es:\n");
	printf("[ ");
	for(int i = 0; i < v1->t_array_len; i++){
	printf("%lf",v1->t_array_val[i]);
	printf(", ");
	}
	printf("]");
	printf("\n");
}

void operaciones_vectores(char *host, t_array v1, t_array v2, int n, char operador){
	
	CLIENT *clnt;
	t_array  *result_2;  

	#ifndef	DEBUG
	clnt = clnt_create (host, CALPROG, DIRVER, "udp");
	if (clnt == NULL) {
		clnt_pcreateerror (host);
		exit (1);
	}
	#endif	/* DEBUG */

		result_2 = malloc(v2.t_array_len);
		result_2 = sumavectores_1(v1, v2, v2.t_array_len, clnt);

		switch (operador)
		{
		case '+':
			
			if (result_2 == (t_array *) NULL) 
				clnt_perror (clnt, "call failed");
			else
			{
				mostrar_vector(result_2);
			}
			break;

		case '-':
			if (result_2 == (t_array *) NULL) 
				clnt_perror (clnt, "call failed");
			else
			{
				mostrar_vector(result_2);
			}
			break;
		
		case 'x':
			if (result_2 == (t_array *) NULL) 
				clnt_perror (clnt, "call failed");
			else
			{
				mostrar_vector(result_2);
			}
			break;

		case '/':
			if (result_2 == (t_array *) NULL) 
				clnt_perror (clnt, "call failed");
			else
			{
				mostrar_vector(result_2);
			}
			break;
		}

		free(v1.t_array_val);
		free(v2.t_array_val);
		free(result_2->t_array_val);	

#ifndef	DEBUG
	clnt_destroy (clnt);
#endif	 /* DEBUG */
}

void operaciones_funciones_basicas(char *host, funcion f1, funcion f2, char operador)
{
	CLIENT *clnt;
	funcion  *result_3;  

	#ifndef	DEBUG
	clnt = clnt_create (host, CALPROG, DIRVER, "udp");
	if (clnt == NULL) {
		clnt_pcreateerror (host);
		exit (1);
	}
	#endif	/* DEBUG */

		switch (operador)
		{
		case '+':
			
			result_3 = sumafunciones_1(f1, f2, clnt);
			if (result_3 == (funcion *) NULL) {
				clnt_perror (clnt, "call failed");
			}
			else
			{
				mostrar_funcionP(result_3);
			}
			break;

		case '-':
			result_3 = restafunciones_1(f1, f2, clnt);
			if (result_3 == (funcion *) NULL) {
				clnt_perror (clnt, "call failed");
			}
			else
			{
				mostrar_funcionP(result_3);
			}
			break;

		
		case 'x':
			result_3 = multiplicacionfunciones_1(f1, f2, clnt);
			if (result_3 == (funcion *) NULL) {
				clnt_perror (clnt, "call failed");
			}
			else
			{
				mostrar_funcionP(result_3);
			}
			break;


		case '/':
			result_3 = divisionfunciones_1(f1, f2, clnt);
			if (result_3 == (funcion *) NULL) {
				clnt_perror (clnt, "call failed");
			}
			else
			{
				mostrar_funcionP(result_3);
			}
			break;

		}
		
#ifndef	DEBUG
	clnt_destroy (clnt);
#endif	 /* DEBUG */
}

funcion leer_funcion(){
	funcion f1;

	printf("\nIntroduce el valor de la x^2:\n");
	scanf("%lf",&f1.x);
	f1.exponente_x = 2;

	printf("\nIntroduce el valor de la x^1:\n");
	scanf("%lf",&f1.y);
	f1.exponente_y = 1;

	printf("\nIntroduce exponente de la x^0:\n");
	scanf("%lf",&f1.z);
	f1.exponente_z = 0;

	return f1;
}

void mostrar_funcionP(funcion* f1){
	printf("Funcion resultante: ");
	
	printf("%lf",f1->x);
	printf("X^");
	printf("%d",f1->exponente_x);
	printf(" +");

	printf("%lf",f1->y);
	printf("X^");
	printf("%d",f1->exponente_y);
	printf(" +");


	printf("%lf",f1->z);
	printf("X^");
	printf("%d",f1->exponente_z);

	printf("\n");
}

void mostrar_funcion(funcion f1){
	printf("%lf",f1.x);
	printf("X^");
	printf("%d",f1.exponente_x);
	printf(" +");

	printf("%lf",f1.y);
	printf("X^");
	printf("%d",f1.exponente_y);
	printf(" +");


	printf("%lf",f1.z);
	printf("X^");
	printf("%d",f1.exponente_z);

	printf("\n");
}

void derivar_funcion(char *host, funcion f1){
	CLIENT *clnt;
	funcion  *result_3;  

	#ifndef	DEBUG
	clnt = clnt_create (host, CALPROG, DIRVER, "udp");
	if (clnt == NULL) {
		clnt_pcreateerror (host);
		exit (1);
	}
	#endif	/* DEBUG */

	result_3 = derivadafunciones_1(f1,clnt);
	if (result_3 == (funcion *) NULL) {
		clnt_perror (clnt, "call failed");
	}
	else
	{
		mostrar_funcionP(result_3);
	}

#ifndef	DEBUG
	clnt_destroy (clnt);
#endif	 /* DEBUG */
}

void integral_funcion(char *host, funcion f1){
	CLIENT *clnt;
	funcion  *result_3;  

	#ifndef	DEBUG
	clnt = clnt_create (host, CALPROG, DIRVER, "udp");
	if (clnt == NULL) {
		clnt_pcreateerror (host);
		exit (1);
	}
	#endif	/* DEBUG */

	result_3 = integralfunciones_1(f1,clnt);
	if (result_3 == (funcion *) NULL) {
		clnt_perror (clnt, "call failed");
	}
	else
	{
		mostrar_funcionP(result_3);
	}

#ifndef	DEBUG
	clnt_destroy (clnt);
#endif	 /* DEBUG */
}

int main (int argc, char *argv[])
{
	char *host;

	int entero1, entero2,opcion;
	char operador;
	char caracter[1];

	int tam;
	int elemento;

	if (argc != 2) {
		printf ("usage: %s <server_host>\n", argv[0]);
		exit (1);
	}
	host = argv[1];

	printf("CALCULADORA\n");
	printf("************\n");
	printf("Seleccione una opcion:\n1.Operaciones con enteros\n2.Operaciones con vectores\n3.Operacion con funciones\n\n");
	scanf("%d",&opcion);

	switch (opcion)
	{
		case 1:
			printf("\nIntroduce dos enteros separados por un espacio: \n");
			scanf("%d %d", &entero1, &entero2);
			printf("\nIntroduce el operador:\n");
			scanf(" %c",&operador);

			if(operador == '/' && entero2 == 0)
			{
				printf("No se puede dividir entre cero");
				exit(1);
			}
			else
				operaciones_basicas(host,entero1,entero2,operador);
		break;
		case 2:
			printf("\nIntroduce el tamaño de los vectores(han de ser iguales) :\n");
			scanf("%d",&tam);
			printf("\nIntroduce el operador:\n");
			scanf(" %c",&operador);

			t_array v1,v2,v3;

			v1.t_array_len = tam;
			v1.t_array_val = malloc(v1.t_array_len);

			v2.t_array_len = tam;
			v2.t_array_val = malloc(v2.t_array_len);

			for(int i = 0; i < v1.t_array_len; i++){
				printf("\nIntroduce elemento i al vector 1:\n");
				scanf("%lf",&v1.t_array_val[i]);
				
			}

			//se podria hacer con un bucle pero creo que asi es mas claro y la idea de la practica no es perder el tiempo en programacion y centrarse en el uso de RPC
			for(int i = 0; i < v2.t_array_len; i++){
				printf("\nIntroduce elemento i al vector 2:\n");
				scanf("%lf",&v2.t_array_val[i]);
			}

			operaciones_vectores(host,v1,v2,v2.t_array_len, operador);
		break;

		case 3:
		printf("\nFuncion ->");
		funcion f1, f2;
		f1 = leer_funcion();
		mostrar_funcion(f1);
		//las funciones por defecto las he hecho de grado 2 porque a la hora de hacer las cuentas tampoco supone
		//una gran diferencia cambiarlo todo para que sean funcion de un tamaño elegido por el usuario

		f2 = leer_funcion();
		mostrar_funcion(f2);

		printf("\n\n1.Operaciones basicas con funciones\n2.Integrar o derivar\n");
		scanf("%d",&opcion);



			switch(opcion)
			{
				case 1:
					printf("\nIntroduce el operador:\n");
					scanf(" %c",&operador);
					operaciones_funciones_basicas(host,f1,f2,operador);
				break;

				case 2:
					printf("\n1.Integral\n2.Derivar\n");
					scanf("%d",&opcion);
					if(opcion == 1)
					{
						printf("\n1.Integral sobre la primera funcion\n2.Integral sobre la segunda funcion\n");
						scanf("%d",&opcion);
						if(opcion == 1){
							integral_funcion(host,f1);
						}
						if(opcion == 2){
							integral_funcion(host,f2);			
						}	
					}
					if(opcion == 2)
					{
						printf("\n1.Derivar sobre la primera funcion\n2.Derivar sobre la segunda funcion\n");
						scanf("%d",&opcion);
						if(opcion == 1){
							derivar_funcion(host,f1);
						}
						if(opcion == 2){
							derivar_funcion(host,f2);			
						}	
					}
					//poner algo asi como un seguir integrando o algo asi
				break;

			}
		break;
	}



exit (0);
}
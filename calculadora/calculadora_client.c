/*
 * This is sample code generated by rpcgen.
 * These are only templates and you can use them
 * as a guideline for developing your own functions.
 */

#include "calculadora.h"
#include <unistd.h>
#include <stdio.h>

void
calprog_1(char *host, int entero1, char operador, int entero2)
{
	CLIENT *clnt;
	double  *result_1;
	int num1 = entero1;
	int num2 = entero2;

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
		result_1 = suma_1(num1, num2, clnt);
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
		result_1 = resta_1(num1, num2, clnt);
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
		result_1 = multiplicacion_1(num1, num2, clnt);
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
		result_1 = division_1(num1, num2, clnt);
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


int main (int argc, char *argv[])
{
	const int num_operaciones = 4;
	char *host;
	int entero1, entero2,opcion;
	char operador, ch;

	char caracter[1];

	if (argc != 5) {
		printf ("usage: %s <server_host>\n", argv[0]);
		exit (1);
	}
	host = argv[1];

	printf("CALCULADORA\n");
	printf("************\n");
	printf("Seleccione una opcion:\n1.Operaciones con enteros\n2.Operaciones con vectores\n\n");
	scanf("%d",&opcion);

	switch (opcion)
	{
	case 1:
		printf("\nIntroduce dos enteros separados por un espacio: \n");
		scanf("%d %d", &entero1, &entero2);
		printf("\nIntroduce el operador:\n");
		scanf(" %c",&operador);
		break;
	
	default:
		break;
	}

	if(operador == '/' && entero2 == 0)
		printf("No se puede dividir entre cero");

	calprog_1 (host,entero1,operador,entero2);
exit (0);
}

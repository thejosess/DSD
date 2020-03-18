/*
 * This is sample code generated by rpcgen.
 * These are only templates and you can use them
 * as a guideline for developing your own functions.
 */

#include "calculadora.h"


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
	char *host;
	int entero1, entero2;
	char operador;

	if (argc != 5) {
		printf ("usage: %s <server_host> <entero> <operador> <entero>\n", argv[0]);
		exit (1);
	}
	host = argv[1];
	entero1 = atoi(argv[2]);
	operador = (char) argv[3][0];
	entero2 = atoi(argv[4]);

	calprog_1 (host,entero1,operador,entero2);
exit (0);
}

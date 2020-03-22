/*
 * This is sample code generated by rpcgen.
 * These are only templates and you can use them
 * as a guideline for developing your own functions.
 */

#include "calculadora.h"

double*
suma_1_svc(int arg1, int arg2,  struct svc_req *rqstp)
{
	static double  result;
	result = arg1 + arg2;
	return &result;
}

double *
resta_1_svc(int arg1, int arg2,  struct svc_req *rqstp)
{
	static double  result;
	result = arg1 - arg2;
	return &result;
}

double *
multiplicacion_1_svc(int arg1, int arg2,  struct svc_req *rqstp)
{
	static double  result;
	result = arg1 * arg2;
	return &result;
}

double *
division_1_svc(int arg1, int arg2,  struct svc_req *rqstp)
{
	static double  result;
	result = (arg1 + 0.0) / arg2;	//obtendriamos los decimales ya que es un double con el 0.0
	return &result;
}

t_array *
sumavectores_1_svc(t_array a, t_array b, int n,  struct svc_req *rqstp)
{
	static t_array  result;	
	result.t_array_len = n;
	result.t_array_val = malloc(n*sizeof(double));

	for(int i = 0; i < result.t_array_len; i++)
	{
		result.t_array_val[i] = a.t_array_val[i] + b.t_array_val[i];
	}

	return &result;
}

t_array *
restvectores_1_svc(t_array a, t_array b, int n,  struct svc_req *rqstp)
{
	static t_array  result;	
	result.t_array_len = n;
	result.t_array_val = malloc(n*sizeof(double));

	for(int i = 0; i < result.t_array_len; i++)
	{
		result.t_array_val[i] = a.t_array_val[i] - b.t_array_val[i];
	}

	return &result;
}

t_array *
multiplicacionvectores_1_svc(t_array a, t_array b, int n,  struct svc_req *rqstp)
{
	static t_array  result;	
	result.t_array_len = n;
	result.t_array_val = malloc(n*sizeof(double));

	for(int i = 0; i < result.t_array_len; i++)
	{
		result.t_array_val[i] = a.t_array_val[i] * b.t_array_val[i];
	}

	return &result;
}

t_array *
divisionvectores_1_svc(t_array a, t_array b, int n,  struct svc_req *rqstp)
{
	static t_array  result;	
	result.t_array_len = n;
	result.t_array_val = malloc(n*sizeof(double));

	for(int i = 0; i < result.t_array_len; i++)
	{
		result.t_array_val[i] = a.t_array_val[i] / b.t_array_val[i];
	}

	return &result;
}

funcion *
sumafunciones_1_svc(funcion f1, funcion f2,  struct svc_req *rqstp)
{
	static funcion result;

	result.x = f1.x + f2.x;
	result.y = f1.y + f2.y;
	result.z = f1.z + f2.z;

	return &result;
}

funcion *
restafunciones_1_svc(funcion f1, funcion f2,  struct svc_req *rqstp)
{
	static funcion result;

	result.x = f1.x - f2.x;
	result.y = f1.y - f2.y;
	result.z = f1.z - f2.z;

	return &result;
}

funcion *
multiplicacionfunciones_1_svc(funcion f1, funcion f2,  struct svc_req *rqstp)
{
	static funcion result;

	result.x = f1.x * f2.x;
	result.y = f1.y * f2.y;
	result.z = f1.z * f2.z;

	return &result;
}

funcion *
divisionfunciones_1_svc(funcion f1, funcion f2,  struct svc_req *rqstp)
{
	static funcion result;

	result.x = f1.x / f2.x;
	result.y = f1.y / f2.y;
	result.z = f1.z / f2.z;

	return &result;
}

funcion *
derivadafunciones_1_svc(funcion f1,  struct svc_req *rqstp)
{
	static funcion result;

	result.z = f1.y ;
	result.y = f1.x * 2;
	result.x = 0;

	result.exponente_x = 2;
	result.exponente_y = 1;
	result.exponente_z = 0;

	return &result;
}

funcion *
integralfunciones_1_svc(funcion f1,  struct svc_req *rqstp)
{
	static funcion  result;

	result.exponente_x = f1.exponente_x + 1;
	result.x = f1.exponente_x / (double) result.exponente_x;

	result.exponente_y = f1.exponente_y + 1;
	result.y = f1.exponente_y / (double) result.exponente_y;

	result.exponente_z = f1.exponente_z + 1;
	result.z = f1.exponente_z / (double) result.exponente_z;


	return &result;
}

//iba a hacer con numeros de mas de una cifra pero ya no tenia mucho tiempo
double *
resultadoformula_1_svc(char *formula, int n,  struct svc_req *rqstp)
{
		static double  result;

		const int TAMANO = 20;
		int numero;
		char *operadores = malloc(TAMANO);
		int *numeros = malloc(TAMANO);
		int tam_numeros = 0;
		int tam_operadores = 0;
		char *c[1];


		for(int i=0;i<n;i++)
		{
			if(formula[i] == '+')
			{
				operadores[tam_operadores] = formula[i];
				tam_operadores++;
			}
			else if(formula[i] == '-')
			{
				operadores[tam_operadores] = formula[i];
				tam_operadores++;
			}
			else if(formula[i] == 'x')
			{
				operadores[tam_operadores] = formula[i];
				tam_operadores++;
			}
			else if(formula[i] == '/')
			{
				operadores[tam_operadores] = formula[i];
				tam_operadores++;
			}
			else
			{
				c[0] = formula[i];
				int valor = atoi(c);
				numeros[tam_numeros] = valor;
				tam_numeros++;
			}//intente hacer una que evaluase la expresion cuando encontrase el = con bool_t pero no me funcionaba a la hora de decirle = TRUE
		}

		int j = 0;
		int h = 0;
		int primera_vez = 0;

		double resultado;

		//entra en el for?
		for(int i=0; i<tam_operadores;i++)
		{
			if(operadores[i] == '+')
			{
				if(primera_vez == 0)
				{
					resultado = numeros[j] + numeros[j+1];
					primera_vez = 1;
				}
				else{
					resultado = resultado + numeros[j+1];
				}
			}
			if(operadores[i] == '-')
			{
				if(primera_vez == 0)
				{
					resultado = numeros[j] - numeros[j+1];
					primera_vez = 1;
				}
				else{
					resultado = resultado - numeros[j+1];
				}
			}
			if(operadores[i] == '/')
			{
				if(primera_vez == 0)
				{
					resultado = numeros[j] / numeros[j+1];
					primera_vez = 1;
				}
				else{
					resultado = resultado / numeros[j+1];
				}
			}
			if(operadores[i] == 'x')
			{
				if(primera_vez == 0)
				{
					resultado = numeros[j] * numeros[j+1];
					primera_vez = 1;
				}
				else{
					resultado = resultado * numeros[j+1];
				}
			}
			j++;
		}

		result = resultado;

		free(operadores);
		free(numeros);
	
	return &result;
}

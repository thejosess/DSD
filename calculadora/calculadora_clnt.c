/*
 * Please do not edit this file.
 * It was generated using rpcgen.
 */

#include <memory.h> /* for memset */
#include "calculadora.h"

/* Default timeout can be changed using clnt_control() */
static struct timeval TIMEOUT = { 25, 0 };

double *
suma_1(int arg1, int arg2,  CLIENT *clnt)
{
	suma_1_argument arg;
	static double clnt_res;

	memset((char *)&clnt_res, 0, sizeof(clnt_res));
	arg.arg1 = arg1;
	arg.arg2 = arg2;
	if (clnt_call (clnt, suma, (xdrproc_t) xdr_suma_1_argument, (caddr_t) &arg,
		(xdrproc_t) xdr_double, (caddr_t) &clnt_res,
		TIMEOUT) != RPC_SUCCESS) {
		return (NULL);
	}
	return (&clnt_res);
}

double *
resta_1(int arg1, int arg2,  CLIENT *clnt)
{
	resta_1_argument arg;
	static double clnt_res;

	memset((char *)&clnt_res, 0, sizeof(clnt_res));
	arg.arg1 = arg1;
	arg.arg2 = arg2;
	if (clnt_call (clnt, resta, (xdrproc_t) xdr_resta_1_argument, (caddr_t) &arg,
		(xdrproc_t) xdr_double, (caddr_t) &clnt_res,
		TIMEOUT) != RPC_SUCCESS) {
		return (NULL);
	}
	return (&clnt_res);
}

double *
multiplicacion_1(int arg1, int arg2,  CLIENT *clnt)
{
	multiplicacion_1_argument arg;
	static double clnt_res;

	memset((char *)&clnt_res, 0, sizeof(clnt_res));
	arg.arg1 = arg1;
	arg.arg2 = arg2;
	if (clnt_call (clnt, multiplicacion, (xdrproc_t) xdr_multiplicacion_1_argument, (caddr_t) &arg,
		(xdrproc_t) xdr_double, (caddr_t) &clnt_res,
		TIMEOUT) != RPC_SUCCESS) {
		return (NULL);
	}
	return (&clnt_res);
}

double *
division_1(int arg1, int arg2,  CLIENT *clnt)
{
	division_1_argument arg;
	static double clnt_res;

	memset((char *)&clnt_res, 0, sizeof(clnt_res));
	arg.arg1 = arg1;
	arg.arg2 = arg2;
	if (clnt_call (clnt, division, (xdrproc_t) xdr_division_1_argument, (caddr_t) &arg,
		(xdrproc_t) xdr_double, (caddr_t) &clnt_res,
		TIMEOUT) != RPC_SUCCESS) {
		return (NULL);
	}
	return (&clnt_res);
}

t_array *
sumavectores_1(t_array a, t_array b, int n,  CLIENT *clnt)
{
	sumavectores_1_argument arg;
	static t_array clnt_res;

	memset((char *)&clnt_res, 0, sizeof(clnt_res));
	arg.a = a;
	arg.b = b;
	arg.n = n;
	if (clnt_call (clnt, sumaVectores, (xdrproc_t) xdr_sumavectores_1_argument, (caddr_t) &arg,
		(xdrproc_t) xdr_t_array, (caddr_t) &clnt_res,
		TIMEOUT) != RPC_SUCCESS) {
		return (NULL);
	}
	return (&clnt_res);
}

t_array *
restvectores_1(t_array a, t_array b, int n,  CLIENT *clnt)
{
	restvectores_1_argument arg;
	static t_array clnt_res;

	memset((char *)&clnt_res, 0, sizeof(clnt_res));
	arg.a = a;
	arg.b = b;
	arg.n = n;
	if (clnt_call (clnt, restVectores, (xdrproc_t) xdr_restvectores_1_argument, (caddr_t) &arg,
		(xdrproc_t) xdr_t_array, (caddr_t) &clnt_res,
		TIMEOUT) != RPC_SUCCESS) {
		return (NULL);
	}
	return (&clnt_res);
}

t_array *
multiplicacionvectores_1(t_array a, t_array b, int n,  CLIENT *clnt)
{
	multiplicacionvectores_1_argument arg;
	static t_array clnt_res;

	memset((char *)&clnt_res, 0, sizeof(clnt_res));
	arg.a = a;
	arg.b = b;
	arg.n = n;
	if (clnt_call (clnt, multiplicacionVectores, (xdrproc_t) xdr_multiplicacionvectores_1_argument, (caddr_t) &arg,
		(xdrproc_t) xdr_t_array, (caddr_t) &clnt_res,
		TIMEOUT) != RPC_SUCCESS) {
		return (NULL);
	}
	return (&clnt_res);
}

t_array *
divisionvectores_1(t_array a, t_array b, int n,  CLIENT *clnt)
{
	divisionvectores_1_argument arg;
	static t_array clnt_res;

	memset((char *)&clnt_res, 0, sizeof(clnt_res));
	arg.a = a;
	arg.b = b;
	arg.n = n;
	if (clnt_call (clnt, divisionVectores, (xdrproc_t) xdr_divisionvectores_1_argument, (caddr_t) &arg,
		(xdrproc_t) xdr_t_array, (caddr_t) &clnt_res,
		TIMEOUT) != RPC_SUCCESS) {
		return (NULL);
	}
	return (&clnt_res);
}

funcion *
sumafunciones_1(funcion f1, funcion f2,  CLIENT *clnt)
{
	sumafunciones_1_argument arg;
	static funcion clnt_res;

	memset((char *)&clnt_res, 0, sizeof(clnt_res));
	arg.f1 = f1;
	arg.f2 = f2;
	if (clnt_call (clnt, sumaFunciones, (xdrproc_t) xdr_sumafunciones_1_argument, (caddr_t) &arg,
		(xdrproc_t) xdr_funcion, (caddr_t) &clnt_res,
		TIMEOUT) != RPC_SUCCESS) {
		return (NULL);
	}
	return (&clnt_res);
}

funcion *
restafunciones_1(funcion f1, funcion f2,  CLIENT *clnt)
{
	restafunciones_1_argument arg;
	static funcion clnt_res;

	memset((char *)&clnt_res, 0, sizeof(clnt_res));
	arg.f1 = f1;
	arg.f2 = f2;
	if (clnt_call (clnt, restaFunciones, (xdrproc_t) xdr_restafunciones_1_argument, (caddr_t) &arg,
		(xdrproc_t) xdr_funcion, (caddr_t) &clnt_res,
		TIMEOUT) != RPC_SUCCESS) {
		return (NULL);
	}
	return (&clnt_res);
}

funcion *
multiplicacionfunciones_1(funcion f1, funcion f2,  CLIENT *clnt)
{
	multiplicacionfunciones_1_argument arg;
	static funcion clnt_res;

	memset((char *)&clnt_res, 0, sizeof(clnt_res));
	arg.f1 = f1;
	arg.f2 = f2;
	if (clnt_call (clnt, multiplicacionFunciones, (xdrproc_t) xdr_multiplicacionfunciones_1_argument, (caddr_t) &arg,
		(xdrproc_t) xdr_funcion, (caddr_t) &clnt_res,
		TIMEOUT) != RPC_SUCCESS) {
		return (NULL);
	}
	return (&clnt_res);
}

funcion *
divisionfunciones_1(funcion f1, funcion f2,  CLIENT *clnt)
{
	divisionfunciones_1_argument arg;
	static funcion clnt_res;

	memset((char *)&clnt_res, 0, sizeof(clnt_res));
	arg.f1 = f1;
	arg.f2 = f2;
	if (clnt_call (clnt, divisionFunciones, (xdrproc_t) xdr_divisionfunciones_1_argument, (caddr_t) &arg,
		(xdrproc_t) xdr_funcion, (caddr_t) &clnt_res,
		TIMEOUT) != RPC_SUCCESS) {
		return (NULL);
	}
	return (&clnt_res);
}

funcion *
derivadafunciones_1(funcion f1, funcion f2,  CLIENT *clnt)
{
	derivadafunciones_1_argument arg;
	static funcion clnt_res;

	memset((char *)&clnt_res, 0, sizeof(clnt_res));
	arg.f1 = f1;
	arg.f2 = f2;
	if (clnt_call (clnt, derivadaFunciones, (xdrproc_t) xdr_derivadafunciones_1_argument, (caddr_t) &arg,
		(xdrproc_t) xdr_funcion, (caddr_t) &clnt_res,
		TIMEOUT) != RPC_SUCCESS) {
		return (NULL);
	}
	return (&clnt_res);
}

funcion *
integralfunciones_1(funcion f1, funcion f2,  CLIENT *clnt)
{
	integralfunciones_1_argument arg;
	static funcion clnt_res;

	memset((char *)&clnt_res, 0, sizeof(clnt_res));
	arg.f1 = f1;
	arg.f2 = f2;
	if (clnt_call (clnt, integralFunciones, (xdrproc_t) xdr_integralfunciones_1_argument, (caddr_t) &arg,
		(xdrproc_t) xdr_funcion, (caddr_t) &clnt_res,
		TIMEOUT) != RPC_SUCCESS) {
		return (NULL);
	}
	return (&clnt_res);
}

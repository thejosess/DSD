/*
 * Please do not edit this file.
 * It was generated using rpcgen.
 */

#include "calculadora.h"
#include <stdio.h>
#include <stdlib.h>
#include <rpc/pmap_clnt.h>
#include <string.h>
#include <memory.h>
#include <sys/socket.h>
#include <netinet/in.h>

#ifndef SIG_PF
#define SIG_PF void(*)(int)
#endif

static double *
_suma_1 (suma_1_argument *argp, struct svc_req *rqstp)
{
	return (suma_1_svc(argp->arg1, argp->arg2, rqstp));
}

static double *
_resta_1 (resta_1_argument *argp, struct svc_req *rqstp)
{
	return (resta_1_svc(argp->arg1, argp->arg2, rqstp));
}

static double *
_multiplicacion_1 (multiplicacion_1_argument *argp, struct svc_req *rqstp)
{
	return (multiplicacion_1_svc(argp->arg1, argp->arg2, rqstp));
}

static double *
_division_1 (division_1_argument *argp, struct svc_req *rqstp)
{
	return (division_1_svc(argp->arg1, argp->arg2, rqstp));
}

static t_array *
_sumavectores_1 (sumavectores_1_argument *argp, struct svc_req *rqstp)
{
	return (sumavectores_1_svc(argp->a, argp->b, argp->n, rqstp));
}

static t_array *
_restvectores_1 (restvectores_1_argument *argp, struct svc_req *rqstp)
{
	return (restvectores_1_svc(argp->a, argp->b, argp->n, rqstp));
}

static t_array *
_multiplicacionvectores_1 (multiplicacionvectores_1_argument *argp, struct svc_req *rqstp)
{
	return (multiplicacionvectores_1_svc(argp->a, argp->b, argp->n, rqstp));
}

static t_array *
_divisionvectores_1 (divisionvectores_1_argument *argp, struct svc_req *rqstp)
{
	return (divisionvectores_1_svc(argp->a, argp->b, argp->n, rqstp));
}

static funcion *
_sumafunciones_1 (sumafunciones_1_argument *argp, struct svc_req *rqstp)
{
	return (sumafunciones_1_svc(argp->f1, argp->f2, rqstp));
}

static funcion *
_restafunciones_1 (restafunciones_1_argument *argp, struct svc_req *rqstp)
{
	return (restafunciones_1_svc(argp->f1, argp->f2, rqstp));
}

static funcion *
_multiplicacionfunciones_1 (multiplicacionfunciones_1_argument *argp, struct svc_req *rqstp)
{
	return (multiplicacionfunciones_1_svc(argp->f1, argp->f2, rqstp));
}

static funcion *
_divisionfunciones_1 (divisionfunciones_1_argument *argp, struct svc_req *rqstp)
{
	return (divisionfunciones_1_svc(argp->f1, argp->f2, rqstp));
}

static funcion *
_derivadafunciones_1 (funcion  *argp, struct svc_req *rqstp)
{
	return (derivadafunciones_1_svc(*argp, rqstp));
}

static funcion *
_integralfunciones_1 (funcion  *argp, struct svc_req *rqstp)
{
	return (integralfunciones_1_svc(*argp, rqstp));
}

static double *
_resultadoformula_1 (resultadoformula_1_argument *argp, struct svc_req *rqstp)
{
	return (resultadoformula_1_svc(argp->formula, argp->n, rqstp));
}

static void
calprog_1(struct svc_req *rqstp, register SVCXPRT *transp)
{
	union {
		suma_1_argument suma_1_arg;
		resta_1_argument resta_1_arg;
		multiplicacion_1_argument multiplicacion_1_arg;
		division_1_argument division_1_arg;
		sumavectores_1_argument sumavectores_1_arg;
		restvectores_1_argument restvectores_1_arg;
		multiplicacionvectores_1_argument multiplicacionvectores_1_arg;
		divisionvectores_1_argument divisionvectores_1_arg;
		sumafunciones_1_argument sumafunciones_1_arg;
		restafunciones_1_argument restafunciones_1_arg;
		multiplicacionfunciones_1_argument multiplicacionfunciones_1_arg;
		divisionfunciones_1_argument divisionfunciones_1_arg;
		funcion derivadafunciones_1_arg;
		funcion integralfunciones_1_arg;
		resultadoformula_1_argument resultadoformula_1_arg;
	} argument;
	char *result;
	xdrproc_t _xdr_argument, _xdr_result;
	char *(*local)(char *, struct svc_req *);

	switch (rqstp->rq_proc) {
	case NULLPROC:
		(void) svc_sendreply (transp, (xdrproc_t) xdr_void, (char *)NULL);
		return;

	case suma:
		_xdr_argument = (xdrproc_t) xdr_suma_1_argument;
		_xdr_result = (xdrproc_t) xdr_double;
		local = (char *(*)(char *, struct svc_req *)) _suma_1;
		break;

	case resta:
		_xdr_argument = (xdrproc_t) xdr_resta_1_argument;
		_xdr_result = (xdrproc_t) xdr_double;
		local = (char *(*)(char *, struct svc_req *)) _resta_1;
		break;

	case multiplicacion:
		_xdr_argument = (xdrproc_t) xdr_multiplicacion_1_argument;
		_xdr_result = (xdrproc_t) xdr_double;
		local = (char *(*)(char *, struct svc_req *)) _multiplicacion_1;
		break;

	case division:
		_xdr_argument = (xdrproc_t) xdr_division_1_argument;
		_xdr_result = (xdrproc_t) xdr_double;
		local = (char *(*)(char *, struct svc_req *)) _division_1;
		break;

	case sumaVectores:
		_xdr_argument = (xdrproc_t) xdr_sumavectores_1_argument;
		_xdr_result = (xdrproc_t) xdr_t_array;
		local = (char *(*)(char *, struct svc_req *)) _sumavectores_1;
		break;

	case restVectores:
		_xdr_argument = (xdrproc_t) xdr_restvectores_1_argument;
		_xdr_result = (xdrproc_t) xdr_t_array;
		local = (char *(*)(char *, struct svc_req *)) _restvectores_1;
		break;

	case multiplicacionVectores:
		_xdr_argument = (xdrproc_t) xdr_multiplicacionvectores_1_argument;
		_xdr_result = (xdrproc_t) xdr_t_array;
		local = (char *(*)(char *, struct svc_req *)) _multiplicacionvectores_1;
		break;

	case divisionVectores:
		_xdr_argument = (xdrproc_t) xdr_divisionvectores_1_argument;
		_xdr_result = (xdrproc_t) xdr_t_array;
		local = (char *(*)(char *, struct svc_req *)) _divisionvectores_1;
		break;

	case sumaFunciones:
		_xdr_argument = (xdrproc_t) xdr_sumafunciones_1_argument;
		_xdr_result = (xdrproc_t) xdr_funcion;
		local = (char *(*)(char *, struct svc_req *)) _sumafunciones_1;
		break;

	case restaFunciones:
		_xdr_argument = (xdrproc_t) xdr_restafunciones_1_argument;
		_xdr_result = (xdrproc_t) xdr_funcion;
		local = (char *(*)(char *, struct svc_req *)) _restafunciones_1;
		break;

	case multiplicacionFunciones:
		_xdr_argument = (xdrproc_t) xdr_multiplicacionfunciones_1_argument;
		_xdr_result = (xdrproc_t) xdr_funcion;
		local = (char *(*)(char *, struct svc_req *)) _multiplicacionfunciones_1;
		break;

	case divisionFunciones:
		_xdr_argument = (xdrproc_t) xdr_divisionfunciones_1_argument;
		_xdr_result = (xdrproc_t) xdr_funcion;
		local = (char *(*)(char *, struct svc_req *)) _divisionfunciones_1;
		break;

	case derivadaFunciones:
		_xdr_argument = (xdrproc_t) xdr_funcion;
		_xdr_result = (xdrproc_t) xdr_funcion;
		local = (char *(*)(char *, struct svc_req *)) _derivadafunciones_1;
		break;

	case integralFunciones:
		_xdr_argument = (xdrproc_t) xdr_funcion;
		_xdr_result = (xdrproc_t) xdr_funcion;
		local = (char *(*)(char *, struct svc_req *)) _integralfunciones_1;
		break;

	case resultadoFormula:
		_xdr_argument = (xdrproc_t) xdr_resultadoformula_1_argument;
		_xdr_result = (xdrproc_t) xdr_double;
		local = (char *(*)(char *, struct svc_req *)) _resultadoformula_1;
		break;

	default:
		svcerr_noproc (transp);
		return;
	}
	memset ((char *)&argument, 0, sizeof (argument));
	if (!svc_getargs (transp, (xdrproc_t) _xdr_argument, (caddr_t) &argument)) {
		svcerr_decode (transp);
		return;
	}
	result = (*local)((char *)&argument, rqstp);
	if (result != NULL && !svc_sendreply(transp, (xdrproc_t) _xdr_result, result)) {
		svcerr_systemerr (transp);
	}
	if (!svc_freeargs (transp, (xdrproc_t) _xdr_argument, (caddr_t) &argument)) {
		fprintf (stderr, "%s", "unable to free arguments");
		exit (1);
	}
	return;
}

int
main (int argc, char **argv)
{
	register SVCXPRT *transp;

	pmap_unset (CALPROG, DIRVER);

	transp = svcudp_create(RPC_ANYSOCK);
	if (transp == NULL) {
		fprintf (stderr, "%s", "cannot create udp service.");
		exit(1);
	}
	if (!svc_register(transp, CALPROG, DIRVER, calprog_1, IPPROTO_UDP)) {
		fprintf (stderr, "%s", "unable to register (CALPROG, DIRVER, udp).");
		exit(1);
	}

	transp = svctcp_create(RPC_ANYSOCK, 0, 0);
	if (transp == NULL) {
		fprintf (stderr, "%s", "cannot create tcp service.");
		exit(1);
	}
	if (!svc_register(transp, CALPROG, DIRVER, calprog_1, IPPROTO_TCP)) {
		fprintf (stderr, "%s", "unable to register (CALPROG, DIRVER, tcp).");
		exit(1);
	}

	svc_run ();
	fprintf (stderr, "%s", "svc_run returned");
	exit (1);
	/* NOTREACHED */
}

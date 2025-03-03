/*
 * Please do not edit this file.
 * It was generated using rpcgen.
 */

#ifndef _CALCULADORA_H_RPCGEN
#define _CALCULADORA_H_RPCGEN

#include <rpc/rpc.h>


#ifdef __cplusplus
extern "C" {
#endif


typedef struct {
	u_int t_array_len;
	double *t_array_val;
} t_array;

struct funcion {
	double x;
	int exponente_x;
	double y;
	int exponente_y;
	double z;
	int exponente_z;
};
typedef struct funcion funcion;

struct suma_1_argument {
	int arg1;
	int arg2;
};
typedef struct suma_1_argument suma_1_argument;

struct resta_1_argument {
	int arg1;
	int arg2;
};
typedef struct resta_1_argument resta_1_argument;

struct multiplicacion_1_argument {
	int arg1;
	int arg2;
};
typedef struct multiplicacion_1_argument multiplicacion_1_argument;

struct division_1_argument {
	int arg1;
	int arg2;
};
typedef struct division_1_argument division_1_argument;

struct sumavectores_1_argument {
	t_array a;
	t_array b;
	int n;
};
typedef struct sumavectores_1_argument sumavectores_1_argument;

struct restvectores_1_argument {
	t_array a;
	t_array b;
	int n;
};
typedef struct restvectores_1_argument restvectores_1_argument;

struct multiplicacionvectores_1_argument {
	t_array a;
	t_array b;
	int n;
};
typedef struct multiplicacionvectores_1_argument multiplicacionvectores_1_argument;

struct divisionvectores_1_argument {
	t_array a;
	t_array b;
	int n;
};
typedef struct divisionvectores_1_argument divisionvectores_1_argument;

struct sumafunciones_1_argument {
	funcion f1;
	funcion f2;
};
typedef struct sumafunciones_1_argument sumafunciones_1_argument;

struct restafunciones_1_argument {
	funcion f1;
	funcion f2;
};
typedef struct restafunciones_1_argument restafunciones_1_argument;

struct multiplicacionfunciones_1_argument {
	funcion f1;
	funcion f2;
};
typedef struct multiplicacionfunciones_1_argument multiplicacionfunciones_1_argument;

struct divisionfunciones_1_argument {
	funcion f1;
	funcion f2;
};
typedef struct divisionfunciones_1_argument divisionfunciones_1_argument;

struct resultadoformula_1_argument {
	char *formula;
	int n;
};
typedef struct resultadoformula_1_argument resultadoformula_1_argument;

#define CALPROG 0x20000002
#define DIRVER 1

#if defined(__STDC__) || defined(__cplusplus)
#define suma 1
extern  double * suma_1(int , int , CLIENT *);
extern  double * suma_1_svc(int , int , struct svc_req *);
#define resta 2
extern  double * resta_1(int , int , CLIENT *);
extern  double * resta_1_svc(int , int , struct svc_req *);
#define multiplicacion 3
extern  double * multiplicacion_1(int , int , CLIENT *);
extern  double * multiplicacion_1_svc(int , int , struct svc_req *);
#define division 4
extern  double * division_1(int , int , CLIENT *);
extern  double * division_1_svc(int , int , struct svc_req *);
#define sumaVectores 5
extern  t_array * sumavectores_1(t_array , t_array , int , CLIENT *);
extern  t_array * sumavectores_1_svc(t_array , t_array , int , struct svc_req *);
#define restVectores 6
extern  t_array * restvectores_1(t_array , t_array , int , CLIENT *);
extern  t_array * restvectores_1_svc(t_array , t_array , int , struct svc_req *);
#define multiplicacionVectores 7
extern  t_array * multiplicacionvectores_1(t_array , t_array , int , CLIENT *);
extern  t_array * multiplicacionvectores_1_svc(t_array , t_array , int , struct svc_req *);
#define divisionVectores 8
extern  t_array * divisionvectores_1(t_array , t_array , int , CLIENT *);
extern  t_array * divisionvectores_1_svc(t_array , t_array , int , struct svc_req *);
#define sumaFunciones 9
extern  funcion * sumafunciones_1(funcion , funcion , CLIENT *);
extern  funcion * sumafunciones_1_svc(funcion , funcion , struct svc_req *);
#define restaFunciones 10
extern  funcion * restafunciones_1(funcion , funcion , CLIENT *);
extern  funcion * restafunciones_1_svc(funcion , funcion , struct svc_req *);
#define multiplicacionFunciones 11
extern  funcion * multiplicacionfunciones_1(funcion , funcion , CLIENT *);
extern  funcion * multiplicacionfunciones_1_svc(funcion , funcion , struct svc_req *);
#define divisionFunciones 12
extern  funcion * divisionfunciones_1(funcion , funcion , CLIENT *);
extern  funcion * divisionfunciones_1_svc(funcion , funcion , struct svc_req *);
#define derivadaFunciones 13
extern  funcion * derivadafunciones_1(funcion , CLIENT *);
extern  funcion * derivadafunciones_1_svc(funcion , struct svc_req *);
#define integralFunciones 14
extern  funcion * integralfunciones_1(funcion , CLIENT *);
extern  funcion * integralfunciones_1_svc(funcion , struct svc_req *);
#define resultadoFormula 15
extern  double * resultadoformula_1(char *, int , CLIENT *);
extern  double * resultadoformula_1_svc(char *, int , struct svc_req *);
extern int calprog_1_freeresult (SVCXPRT *, xdrproc_t, caddr_t);

#else /* K&R C */
#define suma 1
extern  double * suma_1();
extern  double * suma_1_svc();
#define resta 2
extern  double * resta_1();
extern  double * resta_1_svc();
#define multiplicacion 3
extern  double * multiplicacion_1();
extern  double * multiplicacion_1_svc();
#define division 4
extern  double * division_1();
extern  double * division_1_svc();
#define sumaVectores 5
extern  t_array * sumavectores_1();
extern  t_array * sumavectores_1_svc();
#define restVectores 6
extern  t_array * restvectores_1();
extern  t_array * restvectores_1_svc();
#define multiplicacionVectores 7
extern  t_array * multiplicacionvectores_1();
extern  t_array * multiplicacionvectores_1_svc();
#define divisionVectores 8
extern  t_array * divisionvectores_1();
extern  t_array * divisionvectores_1_svc();
#define sumaFunciones 9
extern  funcion * sumafunciones_1();
extern  funcion * sumafunciones_1_svc();
#define restaFunciones 10
extern  funcion * restafunciones_1();
extern  funcion * restafunciones_1_svc();
#define multiplicacionFunciones 11
extern  funcion * multiplicacionfunciones_1();
extern  funcion * multiplicacionfunciones_1_svc();
#define divisionFunciones 12
extern  funcion * divisionfunciones_1();
extern  funcion * divisionfunciones_1_svc();
#define derivadaFunciones 13
extern  funcion * derivadafunciones_1();
extern  funcion * derivadafunciones_1_svc();
#define integralFunciones 14
extern  funcion * integralfunciones_1();
extern  funcion * integralfunciones_1_svc();
#define resultadoFormula 15
extern  double * resultadoformula_1();
extern  double * resultadoformula_1_svc();
extern int calprog_1_freeresult ();
#endif /* K&R C */

/* the xdr functions */

#if defined(__STDC__) || defined(__cplusplus)
extern  bool_t xdr_t_array (XDR *, t_array*);
extern  bool_t xdr_funcion (XDR *, funcion*);
extern  bool_t xdr_suma_1_argument (XDR *, suma_1_argument*);
extern  bool_t xdr_resta_1_argument (XDR *, resta_1_argument*);
extern  bool_t xdr_multiplicacion_1_argument (XDR *, multiplicacion_1_argument*);
extern  bool_t xdr_division_1_argument (XDR *, division_1_argument*);
extern  bool_t xdr_sumavectores_1_argument (XDR *, sumavectores_1_argument*);
extern  bool_t xdr_restvectores_1_argument (XDR *, restvectores_1_argument*);
extern  bool_t xdr_multiplicacionvectores_1_argument (XDR *, multiplicacionvectores_1_argument*);
extern  bool_t xdr_divisionvectores_1_argument (XDR *, divisionvectores_1_argument*);
extern  bool_t xdr_sumafunciones_1_argument (XDR *, sumafunciones_1_argument*);
extern  bool_t xdr_restafunciones_1_argument (XDR *, restafunciones_1_argument*);
extern  bool_t xdr_multiplicacionfunciones_1_argument (XDR *, multiplicacionfunciones_1_argument*);
extern  bool_t xdr_divisionfunciones_1_argument (XDR *, divisionfunciones_1_argument*);
extern  bool_t xdr_resultadoformula_1_argument (XDR *, resultadoformula_1_argument*);

#else /* K&R C */
extern bool_t xdr_t_array ();
extern bool_t xdr_funcion ();
extern bool_t xdr_suma_1_argument ();
extern bool_t xdr_resta_1_argument ();
extern bool_t xdr_multiplicacion_1_argument ();
extern bool_t xdr_division_1_argument ();
extern bool_t xdr_sumavectores_1_argument ();
extern bool_t xdr_restvectores_1_argument ();
extern bool_t xdr_multiplicacionvectores_1_argument ();
extern bool_t xdr_divisionvectores_1_argument ();
extern bool_t xdr_sumafunciones_1_argument ();
extern bool_t xdr_restafunciones_1_argument ();
extern bool_t xdr_multiplicacionfunciones_1_argument ();
extern bool_t xdr_divisionfunciones_1_argument ();
extern bool_t xdr_resultadoformula_1_argument ();

#endif /* K&R C */

#ifdef __cplusplus
}
#endif

#endif /* !_CALCULADORA_H_RPCGEN */

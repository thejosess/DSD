/*
 * Please do not edit this file.
 * It was generated using rpcgen.
 */

#include "calculadora.h"

bool_t
xdr_t_array (XDR *xdrs, t_array *objp)
{
	register int32_t *buf;

	 if (!xdr_array (xdrs, (char **)&objp->t_array_val, (u_int *) &objp->t_array_len, ~0,
		sizeof (double), (xdrproc_t) xdr_double))
		 return FALSE;
	return TRUE;
}

bool_t
xdr_funcion (XDR *xdrs, funcion *objp)
{
	register int32_t *buf;

	 if (!xdr_double (xdrs, &objp->x))
		 return FALSE;
	 if (!xdr_int (xdrs, &objp->exponente_x))
		 return FALSE;
	 if (!xdr_double (xdrs, &objp->y))
		 return FALSE;
	 if (!xdr_int (xdrs, &objp->exponente_y))
		 return FALSE;
	 if (!xdr_double (xdrs, &objp->z))
		 return FALSE;
	 if (!xdr_int (xdrs, &objp->exponente_z))
		 return FALSE;
	return TRUE;
}

bool_t
xdr_suma_1_argument (XDR *xdrs, suma_1_argument *objp)
{
	 if (!xdr_int (xdrs, &objp->arg1))
		 return FALSE;
	 if (!xdr_int (xdrs, &objp->arg2))
		 return FALSE;
	return TRUE;
}

bool_t
xdr_resta_1_argument (XDR *xdrs, resta_1_argument *objp)
{
	 if (!xdr_int (xdrs, &objp->arg1))
		 return FALSE;
	 if (!xdr_int (xdrs, &objp->arg2))
		 return FALSE;
	return TRUE;
}

bool_t
xdr_multiplicacion_1_argument (XDR *xdrs, multiplicacion_1_argument *objp)
{
	 if (!xdr_int (xdrs, &objp->arg1))
		 return FALSE;
	 if (!xdr_int (xdrs, &objp->arg2))
		 return FALSE;
	return TRUE;
}

bool_t
xdr_division_1_argument (XDR *xdrs, division_1_argument *objp)
{
	 if (!xdr_int (xdrs, &objp->arg1))
		 return FALSE;
	 if (!xdr_int (xdrs, &objp->arg2))
		 return FALSE;
	return TRUE;
}

bool_t
xdr_sumavectores_1_argument (XDR *xdrs, sumavectores_1_argument *objp)
{
	 if (!xdr_t_array (xdrs, &objp->a))
		 return FALSE;
	 if (!xdr_t_array (xdrs, &objp->b))
		 return FALSE;
	 if (!xdr_int (xdrs, &objp->n))
		 return FALSE;
	return TRUE;
}

bool_t
xdr_restvectores_1_argument (XDR *xdrs, restvectores_1_argument *objp)
{
	 if (!xdr_t_array (xdrs, &objp->a))
		 return FALSE;
	 if (!xdr_t_array (xdrs, &objp->b))
		 return FALSE;
	 if (!xdr_int (xdrs, &objp->n))
		 return FALSE;
	return TRUE;
}

bool_t
xdr_multiplicacionvectores_1_argument (XDR *xdrs, multiplicacionvectores_1_argument *objp)
{
	 if (!xdr_t_array (xdrs, &objp->a))
		 return FALSE;
	 if (!xdr_t_array (xdrs, &objp->b))
		 return FALSE;
	 if (!xdr_int (xdrs, &objp->n))
		 return FALSE;
	return TRUE;
}

bool_t
xdr_divisionvectores_1_argument (XDR *xdrs, divisionvectores_1_argument *objp)
{
	 if (!xdr_t_array (xdrs, &objp->a))
		 return FALSE;
	 if (!xdr_t_array (xdrs, &objp->b))
		 return FALSE;
	 if (!xdr_int (xdrs, &objp->n))
		 return FALSE;
	return TRUE;
}

bool_t
xdr_sumafunciones_1_argument (XDR *xdrs, sumafunciones_1_argument *objp)
{
	 if (!xdr_funcion (xdrs, &objp->f1))
		 return FALSE;
	 if (!xdr_funcion (xdrs, &objp->f2))
		 return FALSE;
	return TRUE;
}

bool_t
xdr_restafunciones_1_argument (XDR *xdrs, restafunciones_1_argument *objp)
{
	 if (!xdr_funcion (xdrs, &objp->f1))
		 return FALSE;
	 if (!xdr_funcion (xdrs, &objp->f2))
		 return FALSE;
	return TRUE;
}

bool_t
xdr_multiplicacionfunciones_1_argument (XDR *xdrs, multiplicacionfunciones_1_argument *objp)
{
	 if (!xdr_funcion (xdrs, &objp->f1))
		 return FALSE;
	 if (!xdr_funcion (xdrs, &objp->f2))
		 return FALSE;
	return TRUE;
}

bool_t
xdr_divisionfunciones_1_argument (XDR *xdrs, divisionfunciones_1_argument *objp)
{
	 if (!xdr_funcion (xdrs, &objp->f1))
		 return FALSE;
	 if (!xdr_funcion (xdrs, &objp->f2))
		 return FALSE;
	return TRUE;
}

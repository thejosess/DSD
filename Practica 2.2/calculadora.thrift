
struct funcion{
	1: required double x;
	2: required i32 exponente_x;
	3: required double y;
	4: required i32 exponente_y;
	5: required double z;
	6: required i32 exponente_z;
}

service Calculadora{
	void ping(),
	i32 suma(1:i32 num1, 2:i32 num2),
	i32 resta(1:i32 num1, 2:i32 num2),
	i32 multiplicacion(1:i32 num1, 2:i32 num2),
	i32 division(1:i32 num1, 2:i32 num2),
	i32 modulo(1:i32 num1, 2:i32 num2),
	list<double> sumarVectores(1:list<double> v1, 2:list<double> v2),
	list<double> restarVectores(1:list<double> v1, 2:list<double> v2),
	list<double> multiplicarVectores(1:list<double> v1, 2:list<double> v2),
	list<double> dividirVectores(1:list<double> v1, 2:list<double> v2),
	funcion sumaFunciones(1:funcion f1, 2:funcion f2),
	funcion restaFunciones(1:funcion f1, 2:funcion f2), 
	funcion multiplicacionFunciones(1:funcion f1, 2:funcion f2),
	funcion divisionFunciones(1:funcion f1, 2:funcion f2),
	funcion derivadaFunciones(1:funcion f1),
}

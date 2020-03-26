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

}
	

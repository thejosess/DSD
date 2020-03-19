typedef double t_array<>;

struct funcion{
	double x2;
	double x;
	double valor;
};


program CALPROG {
	version DIRVER {
		double suma(int,int) = 1;
		double resta(int,int) = 2;
		double multiplicacion(int,int) = 3;
		double division(int,int) = 4;
		t_array sumaVectores(t_array a, t_array b, int n) = 5;
		t_array restVectores(t_array a, t_array b, int n) = 6;
		t_array multiplicacionVectores(t_array a, t_array b, int n) = 7;
		t_array divisionVectores(t_array a, t_array b, int n) = 8;
		funcion sumaFunciones(funcion f1, funcion f2) = 9;
		funcion restaFunciones(funcion f1, funcion f2) = 10;
		funcion multiplicacionFunciones(funcion f1, funcion f2) = 11;
		funcion divisionFunciones(funcion f1, funcion f2) = 12;
		funcion derivadaFunciones(funcion f1, funcion f2) = 13;
		funcion integralFunciones(funcion f1, funcion f2) = 14;
	} =1;
} = 0x20000002;

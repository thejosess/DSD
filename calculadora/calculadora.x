typedef double t_array<>;

struct funcion{
	double x;
	int exponente_x;
	double y;
	int exponente_y;
	double z;
	int exponente_z;
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
		funcion derivadaFunciones(funcion f1) = 13;
		funcion integralFunciones(funcion f1) = 14;
		double resultadoFormula(string formula, int n) = 15;
	} =1;
} = 0x20000002;

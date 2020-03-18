typedef double t_array<>;


program CALPROG {
	version DIRVER {
		double suma(int,int) = 1;
		/* TODO revisar como introduces los doubles para que no de fallo*/
		double resta(int,int) = 2;
		double multiplicacion(int,int) = 3;
		double division(int,int) = 4;
		t_array sumaVectores(t_array a, t_array b, int n) = 5;
	} =1;
} = 0x20000002;

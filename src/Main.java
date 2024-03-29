import java.util.Scanner;

public class Main {
	static int [][] matriz;
	static String alfabeto;
	static int longitud;
	static int n;
	static int k;
	static int numEntero = 0;
	
	public static void main(String[] args) {
		//Declaramos variables globales:
				n = 4;
				k = 7;
				matriz = new int[n][k];
				alfabeto = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ abcdefghiíjklmnñopqrstuvwxyz";
				
				for(int i = 0 ; i < matriz.length; i ++) {
					for(int j = 0; j < matriz[i].length; j++) {
					
						if(i == j) {
							matriz[i][j] = 1;
						}else {
							matriz[i][j] = 0;
						}
						
					}
				}
				
				matriz[0][5]=1;
				matriz[0][6]=1;
				matriz[1][4]=1;
				matriz[1][6]=1;
				matriz[2][4]=1;
				matriz[2][5]=1;
				matriz[3][4]=1;
				matriz[3][5]=1;
				matriz[3][6]=1;
				
				System.out.println("MATRIZ");
				for(int i = 0; i < matriz.length; i++) {
					for(int j = 0; j <matriz[i].length; j++) {
						System.out.print(matriz[i][j]);
					}
					System.out.println();
				}
				
				//COD1 -- hacer el logaritmo en base 2 de la longitud del alfabeto
				longitud = alfabeto.length();
				double logaritmo = Math.log10(longitud)/Math.log10(2);
				numEntero = (int) Math.round(logaritmo);
				System.out.println("LOGARITMO Y SU ENTERO");
				System.out.println(logaritmo);
				System.out.println(numEntero);
				
				Scanner input = new Scanner(System.in);
				System.out.println("1: Codificar		2: Decodificar		3: Decodificar Con Ruido");
				
				int eleccion = 0;
				try {
					
					eleccion = input.nextInt();

				} catch (Exception e) {

					System.err.println("Error al elegir opcion");
					return;
				}
				
				switch (eleccion) {
				case 1:
					sinRuido cod = new sinRuido(matriz, n, k, numEntero, longitud, alfabeto);
					cod.codificar();
					break;

				case 2:
					sinRuido decod = new sinRuido(matriz, n, k, numEntero, longitud, alfabeto);
					decod.decodificar();
					break;
					
				case 3:
					ConRuido decodRuido = new ConRuido(matriz, n, k, numEntero, alfabeto);
					decodRuido.decodificar();
					break;
					
				default:
					System.out.println("Chao");
					return;
				}
	}
}

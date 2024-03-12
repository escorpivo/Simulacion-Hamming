import java.util.Scanner;
import java.math.*;
public class sinRuido {
	
	static int [][] matriz;
	static String alfabeto;
	static int longitud;

	public static void main(String[] args) {
		
		//Declaramos variables globales:
		matriz = new int[4][7];
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
		
		for(int i = 0; i < matriz.length; i++) {
			for(int j = 0; j <matriz[i].length; j++) {
				System.out.print(matriz[i][j]);
			}
			System.out.println();
		}
		
		
		Scanner input = new Scanner(System.in);
		System.out.println("1: Codificar		2: Decodificar");
		
		int eleccion = 0;
		try {
			
			eleccion = input.nextInt();

		} catch (Exception e) {

			System.err.println("Error al elegir opcion");
			return;
		}
		
		switch (eleccion) {
		case 1:
			codificar();
			break;

		case 2:
			decodificar();
			break;
			
		default:
			System.out.println("Chao");
			return;
		}
		
	}
	
	public static void codificar() {
		longitud = alfabeto.length();
		System.out.println(longitud);
		System.out.println(alfabeto);
		
		//COD1 -- hacer el logaritmo en base 2 de la longitud del alfabeto
		double logaritmo = Math.log10(longitud)/Math.log10(2);
		int numEntero = (int) Math.round(logaritmo);
		System.out.println(logaritmo);
		System.out.println(numEntero);
		
		//Pedir la cadena que se va a codificar
		System.out.println("Escriba la cadena a codificar");
		Scanner input = new Scanner(System.in);
		String cadena = input.nextLine();
		
		//Cojo la posicion de cada letra de la cadena y le resto una posición
		int[] pos = new int[cadena.length()];
		for(int i = 0; i < cadena.length(); i++) {
			for(int j = 0; j < longitud; j++) {
				if(cadena.charAt(i) == alfabeto.charAt(j)) {
					//J - es la posición del alfabeto   I - es la posicion de la letra en nuestra cadena
					pos[i] = j; //No le restamos uno aunque el metodo lo exiga porque ya empezamos a contar desde cero
				}
			}
		}
		
		for(int i = 0; i < pos.length; i++) {
			System.out.println(pos[i]);
		}
		
		String[] codificacion = new String[cadena.length()];
		//Hacer la codificación de las posiciones de pos
		for(int i = 0; i < pos.length; i++) {
			codificacion[i] = Integer.toBinaryString(pos[i]);
		}
		
		for(int i = 0; i < codificacion.length; i++) {
			System.out.println(codificacion[i]);
		}
		
		//Como nos van a quedar con distintas longitudes, hay que igualar las longitudes añadiendo 0's por la izquierda
		//Primero hay que saber que numero es ek más largo
		int n = 0; 
		int m = 0;
		int maxLongCaracter = 0;
		for(int i = 0; i < codificacion.length-1; i++) {
			n = codificacion[i].length();
			m = codificacion[i+1].length();
				
			if(n > m) {
				maxLongCaracter = n;
			} else {
				maxLongCaracter = m;
			}		
		}
		
		System.out.println(maxLongCaracter);
		//Recorro el array de las codificaciones de la cadena
		StringBuffer rellenarCeros = new StringBuffer();
		for(int i = 0; i < codificacion.length; i++) {
			int espaciosARellenar = maxLongCaracter - codificacion[i].length();
			
			//Recorro hasta los espacios que hay que rellenar para poner 0's por la izquierda
			for(int j = 0; j < espaciosARellenar; j++) {
				rellenarCeros.append("0");
			}
			
			//Añado los 0's que me faltan al array
			codificacion[i] = rellenarCeros.toString() + codificacion[i];
			
			//Baciar el StringBuffer para que no me de problemas
			rellenarCeros = new StringBuffer();
		}
		
		for(int i = 0; i < codificacion.length; i++) {
			System.out.println(codificacion[i]);
		}
		
		//Crear la variable COD1 sumando las codificaciones del array
		StringBuffer cod1 = new StringBuffer();
		for(int i = 0; i < codificacion.length; i++) {
			cod1.append(codificacion[i]);
		}
	}
	
	public static void decodificar() {
		
	}

}

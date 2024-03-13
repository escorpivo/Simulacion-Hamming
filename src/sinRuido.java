import java.util.ArrayList;
import java.util.Scanner;
import java.math.*;

public class sinRuido {
	
	static int [][] matriz;
	static String alfabeto;
	static int longitud;
	static int n;
	static int k;

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
	
	
	//parte de codificar sin ruido
	public static void codificar() {
		longitud = alfabeto.length();
		System.out.println(longitud);
		System.out.println(alfabeto);
		
		//COD1 -- hacer el logaritmo en base 2 de la longitud del alfabeto
		double logaritmo = Math.log10(longitud)/Math.log10(2);
		int numEntero = (int) Math.round(logaritmo);
		System.out.println("LOGARITMO Y SU ENTERO");
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
		
		System.out.println("POSICIONES LETRAS CADENA");
		for(int i = 0; i < codificacion.length; i++) {
			System.out.println(codificacion[i]);
		}
		
		//Como nos van a quedar con distintas longitudes, hay que igualar las longitudes añadiendo 0's por la izquierda
		//Primero hay que saber que numero es ek más largo
		int a = 0; 
		int m = 0;
		int maxLongCaracter = 0;
		for(int i = 0; i < codificacion.length-1; i++) {
			a = codificacion[i].length();
			m = codificacion[i+1].length();
				
			if(a > m) {
				maxLongCaracter = a;
			} else {
				maxLongCaracter = m;
			}		
		}
		
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
			
			//Apuntar a otro sitio de memoria para que no genere errores
			rellenarCeros = new StringBuffer();
		}
		
		System.out.println("RELLENO CON 0'S");
		for(int i = 0; i < codificacion.length; i++) {
			System.out.println(codificacion[i]);
		}
		
		//Crear la variable COD1 sumando las codificaciones del array
		StringBuffer cod1 = new StringBuffer();
		for(int i = 0; i < codificacion.length; i++) {
			cod1.append(codificacion[i]);
		}
		
		System.out.println("COD1");
		System.out.println(cod1);
		
		//Para poder multiplicar los numeros de COD1 necesitamos hacer bloques 
		//con el tamaño de n de la matriz
		//En este caso n es 4
		String[] bloques = new String[5] ; //CAMBIAR A ARRAY PARA QUE NO HAYA PROBLEMAS CON LA LONGITUD
		int numBloque = 0;
		boolean faltanNumEnBloque = false;
		for(int i = 0; i < cod1.length(); i+=n) {
			if(i+n > cod1.length()) {
				String bloqueFinal = cod1.toString().substring(i, cod1.length());
				bloques[numBloque] = bloqueFinal;
				faltanNumEnBloque = true;
			} else {
				String bloque = cod1.toString().substring(i, i+n);
				bloques[numBloque] = bloque;
				numBloque++;
			}
		}
		
		System.out.println("BLOQUES");
		for(int i = 0; i < bloques.length; i++) {
			System.out.println(bloques[i]);
		}
		
		//Comprobamos que todas tienen el mismo tamaño
		//Sabemos de antemano que el bloque que puede que no se llene es el ultimo
		//Para eso utilizamos el boolean faltanNumEnBloque de arriba.
		
		//Añadimos, según la práctica, numeros aleatorios a ese bloque
		if(faltanNumEnBloque) {
			int numFaltan = n - bloques[numBloque].length();

			for(int i = 0; i < numFaltan; i++) {
				int num = (int)Math.floor(Math.random()*2);
				bloques[numBloque] += String.valueOf(num);
			}
		}
		
		System.out.println("BLOQUES X2");
		for(int i = 0; i < bloques.length; i++) {
			System.out.print(bloques[i]);
		}
		System.out.println();
		//Bloques es un vector que contiene
		
		//Multiplicar matrices. Multiplicar los distintos bloques por la matriz generadora
		int[][] codigos = new int[bloques.length][k];
		/*
		for(int z = 0; z < matriz[0].length; z++) {
			for(int i = 0; i < bloques.length; i++) {
				int suma = 0; 
				for(int j = 0; j < matriz[0].length; j++) {
					suma += matriz[j][z] * Character.getNumericValue(bloques[i].charAt(j));
					if((suma%2) == 0) {
						suma = 0;
					} else {
						suma = 1;
					}
				}
				codigos[i][z] = suma;
			}
		}
		
		for(int i = 0; i < codigos.length; i++) {
			for(int j = 0; j < codigos[0].length; j++) {
				System.out.print(codigos[i][j]);
			}
			System.out.println();
		}
		*/
	}
	
	
	
	//parte de decodificar sin ruido
	public static void decodificar() {
		
		System.out.println("Prometo que más adelante decodificaré bien :)");
		
	}

}

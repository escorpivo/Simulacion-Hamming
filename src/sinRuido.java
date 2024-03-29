import java.util.ArrayList;

import java.util.Scanner;
import java.math.*;

public class sinRuido {
	
	static int [][] matriz;
	static String alfabeto;
	static int longitud;
	static int n;
	static int k;
	static int numEntero = 0;
	
	public sinRuido(int [][] matriz, int n, int k, int numEntero, int longitud, String alfabeto) {
		this.matriz = matriz;
		this.n= n;
		this.k = k;
		this.numEntero = numEntero;
		this.longitud = longitud;
		this.alfabeto = alfabeto;
	}
	
	//parte de codificar sin ruido
	public static void codificar() {
		System.out.println(longitud);
		System.out.println(alfabeto);
		
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
		
		//Recorro el array de las codificaciones de la cadena
		StringBuffer rellenarCeros = new StringBuffer();
		for(int i = 0; i < codificacion.length; i++) {
			int espaciosARellenar = numEntero - codificacion[i].length();
			
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
		/*
		ArrayList<ArrayList<String>> matrizBloques = new ArrayList<ArrayList<String>>();
		int numBloque = 0;
		boolean faltanNumEnBloque = false;
		for(int i = 0; i < cod1.length(); i+=n) {
			if(i+n > cod1.length()) {
				String bloqueFinal = cod1.toString().substring(i, cod1.length());
				matrizBloques.get(numBloque).add(bloqueFinal);
				faltanNumEnBloque = true;
			} else {
				String bloque = cod1.toString().substring(i, i+n);
				matrizBloques.get(numBloque).add(bloque);
				numBloque++;
			}
		}
		
		for(int i = 0; i < matrizBloques.size(); i++) {
			for(int j = 0; j < matrizBloques.get(0).size(); j++) {
				System.out.print(matrizBloques.get(i).get(j));
			}
			System.out.println();
		}
		*/
		ArrayList<String> bloques = new ArrayList<String>();
		//String[] bloques = new String[(cod1.length()/4)]; /*CAMBIADO A ARRAY e intento implementar que sea dinamico para
		//q no de problema con los null -Víctor*/

		int numBloque = 0;
		boolean faltanNumEnBloque = false;

		for(int i = 0; i < cod1.length(); i+=n) {
			
			if(i+n > cod1.length()) {

				String bloqueFinal = cod1.toString().substring(i, cod1.length());
				//bloques[numBloque] = bloqueFinal;
				bloques.add(bloqueFinal);
				faltanNumEnBloque = true;

			} else {

				String bloque = cod1.toString().substring(i, i+n);
				//bloques[numBloque] = bloque;
				bloques.add(bloque);
				numBloque++;
			}
		}
		
		
		System.out.println("BLOQUES");

		for(int i = 0; i < bloques.size(); i++) {

				System.out.println(bloques.get(i));
	
		}
		
		//Comprobamos que todas tienen el mismo tamaño
		//Sabemos de antemano que el bloque que puede que no se llene es el ultimo
		//Para eso utilizamos el boolean faltanNumEnBloque de arriba.
		
		//Añadimos, según la práctica, numeros aleatorios a ese bloque
		if(faltanNumEnBloque) {
			int numFaltan = n - bloques.get(numBloque).length();

			for(int i = 0; i < numFaltan; i++) {
				int num = (int)Math.floor(Math.random()*2);
				String a = bloques.get(numBloque) + String.valueOf(num);
				//bloques[numBloque] += String.valueOf(num);
				bloques.remove(numBloque);
				bloques.add(a);
			}
		}
		
		System.out.println("BLOQUES X2");
		for(int i = 0; i < bloques.size(); i++) {
				System.out.println(bloques.get(i));
			
		}
		
		System.out.println();
		//Bloques es un vector que contiene todos los numeros de COD más algunos numeros de mas por el random
		//Multiplicar matrices. Multiplicar los distintos bloques por la matriz generadora
		int[][] codigos = new int[bloques.size()][k];
		
		for(int z = 0; z < matriz[0].length; z++) {
			for(int i = 0; i < bloques.size(); i++) {
				int suma = 0; 
				for(int j = 0; j < matriz.length; j++) {
					suma += matriz[j][z] * Character.getNumericValue(bloques.get(i).charAt(j));
					if((suma%2) == 0) {
						suma = 0;
					} else {
						suma = 1;
					}
				}
				codigos[i][z] = suma;
			}
		}
		
		System.out.println("MATRIZ RESULTANTE");
		for(int i = 0; i < codigos.length; i++) {
			for(int j = 0; j < codigos[0].length; j++) {
				System.out.print(codigos[i][j]);
			}
			System.out.println();
		}
		
		//Pasar de la matriz de los bloques a un array cod2
		StringBuffer cod2 = new StringBuffer();
		for(int i = 0; i < codigos.length; i++) {
			for(int j = 0; j < codigos[0].length; j++) {
				cod2.append(String.valueOf(codigos[i][j]));
			}
		}
		
		System.out.println("COD2");
		System.out.println(cod2);
	}
	
	
	
	//parte de decodificar sin ruido
	public static void decodificar() {
		System.out.println("Prometo que más adelante decodificaré bien :)");
		
		System.out.println("Escriba la secuencia binaria a decodificar");
		Scanner in = new Scanner(System.in);
		
		//Al contrario que en el caso anterior, aqui empezamos por la variable cod2
		String cod2 = in.nextLine();
		
		//Almacenar por bloques por la dimensión de la matriz k
		ArrayList<String> bloques = new ArrayList<String>();
		
		for(int i = 0; i < cod2.length(); i+=k) {
			bloques.add(cod2.substring(i, i+k));
		}
		System.out.println(k);
		System.out.println(numEntero);
		System.out.println("BLOQUES");
		for(int i = 0; i < bloques.size(); i++) {
			System.out.println(bloques.get(i));
		}
		
		//Este paso es en el que en el paso anterior multiplicabamos por la matriz
		//Ahora, con quedarnos con los primeros n (otra dimension de la matriz) numeros nos basta
		//Lo almacenamos en cod1
		StringBuffer cod1 = new StringBuffer();
		for(int i = 0; i < bloques.size(); i++) {
			cod1.append(bloques.get(i).substring(0, 4));
		}
		
		System.out.println("\nCOD1");
		System.out.println(cod1.toString());
		
		//De COD1 tenemos que pasar a la codificación de las palabras
		//Voy a probar a cojer k-1 numeros
		ArrayList<String> codificaciones = new ArrayList<String>();
		for(int i = 0; i < cod1.length(); i+=numEntero) {
			//Si la cadena sigue pero nos pasamos, eso es que esos numeros sobran
			if((i+k-1) > cod1.length()) {
				break;
			}
			codificaciones.add(cod1.substring(i, i+numEntero));
		}
		
		System.out.println("\nCODIFICACION LETRAS");
		for(int i = 0; i < codificaciones.size(); i++) {
			System.out.println(codificaciones.get(i));
		}
		
		//Decodificar las posiciones de codificaciones y almacenarlas en un array
		//Como en el anterior caso a la posicion le restábamos 1, ahora habria que sumarselo
		//Pero como más adelante vamos a empezar a contar desde la posicon 0, no hace falta sumarle 1
		ArrayList<String> posiciones = new ArrayList<String>();
		int pos = 0;
		for(int i = 0; i < codificaciones.size(); i++) {
			pos = Integer.parseInt(codificaciones.get(i),2);
			posiciones.add(String.valueOf(pos));
		}
		
		System.out.println("\nPOSICIONES");
		for(int i = 0; i < posiciones.size(); i++) {
			System.out.println(posiciones.get(i));
		}
		
		//Como ya tenemos las posiciones, ahora hay que encontrar la letra con respecto al alfabeto
		StringBuffer result = new StringBuffer();
		for(int i = 0; i < posiciones.size(); i++) {
			for(int j = 0; j < alfabeto.length(); j++) {
				if(Integer.parseInt(posiciones.get(i)) == j) { //misma posicion, cogemos letra
					result.append(alfabeto.charAt(j));
				}
			}
		}
		
		System.out.println("RESULTADO");
		System.out.println(result.toString());
		
		
		
		
	}

}

import java.util.ArrayList;
import java.util.Scanner;
import java.math.*;

public class ConRuido {
	private static int[][] matrizH;
	private static String alfabeto;
	private static int[][] matrizGeneradora;
	private static int n;
	private static int k;
	private static int numEntero;
	
	public ConRuido(int[][] matrizGeneradora, int n, int k, int numEntero, String alfabeto) {
		this.matrizGeneradora = matrizGeneradora;
		this.n= n;
		this.k = k;
		this.numEntero = numEntero;
		this.alfabeto = alfabeto;
	}
	
	public static void decodificar() {
		int nH = 3;
		int kH = 7;
		int redundancia = 3;
		
		matrizH = new int[nH][kH];
		
		//Creación de la matriz H
		//CAMBIAR POR QUE LEA LA MATRIZ POR UN FICHERO
		matrizH[0][0] = 0;
		matrizH[0][1] = 0;
		matrizH[0][2] = 0;
		matrizH[0][3] = 1;
		matrizH[0][4] = 1;
		matrizH[0][5] = 1;
		matrizH[0][6] = 1;
		
		matrizH[1][0] = 0;
		matrizH[1][1] = 1;
		matrizH[1][2] = 1;
		matrizH[1][3] = 0;
		matrizH[1][4] = 0;
		matrizH[1][5] = 1;
		matrizH[1][6] = 1;
		
		matrizH[2][0] = 1;
		matrizH[2][1] = 0; 
		matrizH[2][2] = 1;
		matrizH[2][3] = 0;
		matrizH[2][4] = 1;
		matrizH[2][5] = 0;
		matrizH[2][6] = 1;
		
		//Creamos el array y que tendrá una dimensión de K numeros
		//Es la traspuesta de aux
		ArrayList<String>  y =  new ArrayList<String>();
		
		System.out.println("Ingrese la secuencia binaria");
		Scanner in = new Scanner(System.in);
		String cadenaBinaria = in.nextLine();
		
		StringBuffer cadena = new StringBuffer();
		
		for(int i = 0; i < cadenaBinaria.length(); i++) {
			cadena.append(cadenaBinaria.charAt(i));
		}
		
		System.out.println("CADENA BINARIA");
		System.out.println(cadena);
		
		
		for(int i = 0; i < cadena.length(); i+=kH) {
			y.add(cadena.substring(i, i+kH));
		}
		
		System.out.println("VECTOR Y");
		for(int i = 0; i < y.size(); i++) {
			System.out.println(y.get(i));
		}
		
		//Transponer el vector para multiplicarlo por la matriz
		
		
		//Creamos otro array que será la solución de la multiplicación deñ vector y por la matriz H
		ArrayList<Integer> aux = new ArrayList<Integer>();
		
		//Multiplicamos el array por la matriz H
		for(int z = 0; z < matrizH.length ; z++) {
			for(int i = 0; i < y.size(); i++) {
				int suma = 0; 
				for(int j = 0; j < matrizH[0].length; j++) {
					suma += matrizH[z][j] * Character.getNumericValue(y.get(i).charAt(j));
					if((suma%2) == 0) {
						suma = 0;
					} else {
						suma = 1;
					}
				}
				aux.add(suma);
			}
		}
		
		System.out.println("POSICION DE ERRORES");
		for(int i = 0; i < aux.size(); i++) {
			System.out.print(aux.get(i));
		}
		
		//Nos dice cuantas columnas va a tener nuestra matriz s
		//Las filas nos lo dice la redundancia
		int columnas = y.size();
		
		System.out.println();
		System.out.println("COLUMNAS MATRIZ ERRORES");
		System.out.println(columnas);
		
		
		int[][] s = new int[redundancia][columnas];
		int l = 0;
		for(int i = 0; i < redundancia; i++) {
			for(int j = 0; j < columnas; j++) {
				s[i][j] = aux.get(l);
				l++;
			}
		}
		
		System.out.println("MATRIZ DE ERRORES");
		for(int i = 0; i < s.length; i++) {
			for(int j = 0; j < s[0].length; j++) {
				System.out.print(s[i][j]);
			}
			System.out.println();
		}
		
		
		//Cada columna de s es un error, asi q tenemos que sacar la posicion de cada error
		int[] posicionesConError = new int[columnas];
		int posError = 0;
		for(int i = 0; i < s[0].length; i++) {
			StringBuilder posBinario = new StringBuilder();
			for(int j = 0; j < s.length; j++) {
				posBinario.append(String.valueOf(s[j][i]));
			}
			posError = Integer.parseInt(posBinario.toString(),2);
			posicionesConError[i] = posError;
		}
		
		System.out.println("POSICIONES");
		for(int i = 0; i < posicionesConError.length; i++) {
			System.out.println(posicionesConError[i]);
		}
		
		//Cambiar el error de la posiciones del aaray y
		
		StringBuffer corregido = new StringBuffer();
		StringBuffer correccion = new StringBuffer();
		
		for(int i = 0; i < kH; i++) {
			correccion.append("0");
		}
		
		System.out.println("CORRECCION");
		System.out.println(correccion.toString());
		
		//En las posiciones que hay error tenemos que añadir un 1 en correccion 
		for(int i = 0; i < posicionesConError.length; i++) {
			correccion.replace(posicionesConError[i]-1, posicionesConError[i], "1");
			System.out.println(correccion);
			
			//Restar las secuencias de y menos la correccion
			for(int j = 0; j < correccion.length(); j++) {
				if(y.get(i).charAt(j) == correccion.charAt(j)) {
					corregido.append("0");
				} else {
					corregido.append("1");
				}
			}
			
			//Volvemos a poner correccion a todo 0's para seguir con la siguiente iteración
			correccion.replace(posicionesConError[i]-1, posicionesConError[i], "0");
		}
		
		System.out.println("CORREGIDO");
		System.out.println(corregido.toString());

		String cod2 = corregido.toString();
		//Almacenar por bloques por la dimensión de la matriz k
		ArrayList<String> bloques = new ArrayList<String>();
				
		for(int i = 0; i < cod2.length(); i+=k) {
			bloques.add(cod2.substring(i, i+k));
		}
		System.out.println();
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

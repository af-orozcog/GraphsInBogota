package model.data_structures;

import java.util.Random;

public class QuickSort {

	//Tomado del libro guia 
	public static void sort(Comparable[] a)
	{
		// NO FUNCIONA LA BARAJADA :(
		//StdRandom.shuffle(a); // elimina la dependencia del input "barajandolo"
		
		sort(a, 0, a.length - 1);
	}
	
	private static void sort(Comparable[] a, int low, int high)
	{
		if (high <= low) return;
		int j = partition(a, low, high); 
		sort(a, low, j-1); 
		sort(a, j+1, high); 
	}
	private static int partition(Comparable[] a, int lo, int hi)
	{ 
		int i = lo, j = hi+1; // Indices derecho e izquierdo
		Comparable v = a[lo]; // Elemento de particion
		while (true)
		{ // Correr a la derecha, correr a la izquierda, chequear recorrido completado, cambiar
			while (less(a[++i], v)) if (i == hi) break;
			while (less(v, a[--j])) if (j == lo) break;
			if (i >= j) break;
			exch(a, i, j);
		}
		exch(a, lo, j); // Insertar ve
		return j; // Retornar a[lo..j-1] <= a[j] <= a[j+1..hi].
	}
	
	private static void exch(Comparable[] a, int i, int j)
	{	Comparable temp = a[i]; a[i] = a[j]; a[j] = temp;	}
	private static boolean less(Comparable x, Comparable y) 
	{	return x.compareTo(y)<0;	}
}

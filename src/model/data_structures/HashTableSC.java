package model.data_structures;

import java.util.Arrays;
import java.util.Iterator;

public class HashTableSC<K extends Comparable<K>, V> implements ITablaHash<K,V>{

	//-----------------------------------------------------------------//
	//--------------------------Atributos------------------------------//
	//-----------------------------------------------------------------//

	/**
	 * Tama�o del hashTable
	 */
	private int m;

	/**
	 * N�mero de elementos del hashTable
	 */
	private int size;

	/**
	 * N�mero de rehash.
	 */
	private int rehash;

	/**
	 * Arreglo de nodos del hashTable
	 */
	private Node<K,V>[] st;

	//----------------------------------------------------------------------//
	//--------------------------Clase auxiliar------------------------------//
	//----------------------------------------------------------------------//
	/**
	 * Clase de nodo donde se guardan las llaves y los valores
	 * @param <K> Tipo de objeto de la llave
	 * @param <V> Tipo de objeto del valor
	 */
	private static class Node<K extends Comparable<K>, V>{

		//-----------------------------------------------------------------//
		//--------------------------Atributos------------------------------//
		//-----------------------------------------------------------------//
		/**
		 * Llave del nodo
		 */
		private K key;

		/**
		 * Valor del nodo
		 */
		private V val;

		/**
		 * Siguiente nodo
		 */
		private Node<K,V> next;

		//--------------------------------------------------------------------------//
		//--------------------------M�todo constructor------------------------------//
		//--------------------------------------------------------------------------//

		/**
		 * M�todo constructor del nodo
		 * @param pKey Llave del nodo
		 * @param pVal Valor del nodo
		 * @param pNext Siguiente nodo
		 */
		public Node(K pKey, V pVal, Node<K, V> pNext) {
			key = pKey;
			val = pVal;
			next = pNext;
		}
	}

	//--------------------------------------------------------------------------//
	//--------------------------M�todo constructor------------------------------//
	//--------------------------------------------------------------------------//

	/**
	 * M�todo constructor que inicializa el tama�o de los arreglos donde se guardan los nodos con su llave y valor
	 * @param pSize el tama�o inicial de los arreglos. pSize > 0
	 * post: se han inicializado los arreglos donde se guardaran las llaves y los valores.
	 */
	@SuppressWarnings("unchecked")
	public HashTableSC(int pM) {
		if(pM == 0)
			m = 1;
		m = pM;
		st = new Node[m];
		rehash = 0;
		size = 0;
	}

	//---------------------------------------------------------------//
	//--------------------------M�todos------------------------------//
	//---------------------------------------------------------------//

	@Override
	public String toString() {
		return "HashTableSC [m=" + m + ", size=" + size + ", rehash=" + rehash + ", st=" + Arrays.toString(st) + "]";
	}

	/**
	 * M�todo que permite dispersar las llaves seg�n el hashCode generado por Java
	 * Devuelve un numero entre 0 y el tama�o de los arreglos -1
	 * @param key la llave a la cual se le quiere sacar el indice
	 * @return un numero entre 0 y el tama�o de los arreglos -1
	 */
	private int hash(K key) {
		return (key.hashCode() & 0x7fffffff) % m;
	}

	/**
	 * Rehace la tabla de hash con un nuevo tama�o.
	 * @param newSize nuevo tama�o de la tabla de hash
	 */
	@SuppressWarnings("unused")
	private void rehash(int newSize) {
		m = newSize;
		if(m == 0) m = 1;

		HashTableSC<K,V> tabla = new HashTableSC<K,V>(m);
		for (Node<K, V> node : st) {
			for(Node<K, V> x = node; x!=null; x = x.next) {
				tabla.put(x.key, x.val);
			}
		}
		st = tabla.st;
		size = tabla.size;
		m = tabla.m;
		rehash++;
	}

	/**
	 * Adiciona un elemento a la tabla
	 */
	public void put(K key, V val) {
		if((double)size >= (5)*((double)m)) rehash(m*2 + 1);

		int i = hash(key);
		
		for(Node<K, V> x = st[i]; x!=null; x = x.next) {
			if(key.compareTo(x.key) == 0) {
				x.val = val;
				return;
			}
		}
		st[i] = new Node<K, V>(key, val, st[i]);
		size++;
	}

	/**
	 * M�todo que notifica si una cierta llave esta en el HashTable
	 * @param Key la llave que se quiere comparar, Key != null
	 * @return verdadero o falso seg�n se encuentre la llave.
	 */
	public boolean contains(K key) {
		int i = hash(key);
		Node<K,V> primero = st[i];
		for(Node<K, V> x = st[i]; x!=null; x = x.next) {
			if(key.compareTo(x.key) == 0) { 
				st[i] = primero;
				return true;
			}
		}
		return false;
	}

	/**
	 * Retorna un elemento de la tabla seg�n la llave
	 */
	public V get(K key) {
		int i = hash(key);
		Node<K,V> primero = st[i];
		for(Node<K, V> x = st[i]; x!=null; x = x.next) {
			if(key.compareTo(x.key) == 0) {
				V val = x.val;
				st[i] = primero;
				return val;
			}
		}
		return null;
	}

	/**
	 * Elimina un elemento seg�n la llave
	 */
	public V delete(K key) {
		int i = hash(key);
		Node<K,V> x = st[i]; Node<K,V> y = null; 
		while(x!=null) {
			if(x.key.equals(key)) break;
			y = x;
			x = x.next;
		}	
		if(x == null) return null;
		size--;
		if(y!=null) y.next = x.next;
		else st[i] = x.next;
		return x.val;
	}

	/**
	 * M�todo que retorna si la tabla esta vac�a
	 * @return Si la tabla esta vac�a
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Retorna el n�mero de elementos de la tabla
	 * @return El n�mero de elementos de la tabla
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Retorna el tama�o del arreglo
	 * @return El tama�o del arreglo
	 */
	public int getM() {
		return m;
	}

	/**
	 * Retorna el n�mero de rehash
	 * @return El n�mero de rehash
	 */
	public int getRehash() {
		return rehash;
	}

	/**
	 * Retorna un iterador de las llaves.s
	 */
	public Iterator<K> keys() {
		ORArray<K> t = new ORArray<K>(size);
		for (Node<K, V> node : st) {
			for(Node<K, V> x = node; x!=null; x = x.next) {
				t.add(x.key);
			}
		}
		return t.iterator();
	}
}

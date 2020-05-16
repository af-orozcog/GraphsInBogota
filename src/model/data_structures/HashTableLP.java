package model.data_structures;


import java.util.Iterator;

public class HashTableLP <K extends Comparable<K>,V> implements ITablaHash<K, V> {
	//---------------------------Atributos------------------------------//
	
	/**
	 * El numero de llaves que se en el hashTable
	 */
	private int numberKeys;
	
	/**
	 * el tama�o de los arreglos donde se guardan las llaves y los valores
	 */
	private int size;
	
	/**
	 * arreglo donde se guardan las llaves
	 */
	private K keys[];
	
	/**
	 * arreglo donde se guardan los valores
	 */
	private V values[];
	
	/**
	 * N�mero de rehash realizados
	 */
	private int rehash;
	
	//------------------------------------Constructores--------------------------//
	
	/**
	 * M�todo constructor que inicializa el tama�o de los arreglos donde se guardan las llaves y los valores
	 * @param pSize el tama�o inicial de los arreglos. pSize > 0
	 * post: se han inicializado los arreglos donde se guardaran las llaves y los valores.
	 */
	public HashTableLP(int pSize) {
		if(pSize == 0)
			pSize = 1;
		size = pSize;
		keys = (K[])new Comparable[pSize];
		values = (V[]) new Object[pSize];
		numberKeys = 0;
		rehash = 0;
	}
	
	/**
	 * M�todo que permite dispersar las llaves seg�n el hashCode generado por Java
	 * Devuelve un numero entre 0 y el tama�o de los arreglos -1
	 * @param key la llave a la cual se le quiere sacar el indice
	 * @return un numero entre 0 y el tama�o de los arreglos -1
	 */
	private int hash(K key) {
		return (key.hashCode() & 0x7fffffff) % size;
	}
	
	/**
	 * M�todo que cambia en tama�o actual de los arreglos de llaves y valores
	 * al valor pasado pasado por parametro
	 * @param pNewSize el nuevo tama�o de los arreglos. pNewSize > 0
	 */
	private void resizing(int pNewSize) {
		HashTableLP<K,V> t = new HashTableLP<K,V>(pNewSize);
		for(int i = 0; i < size; i++) {
			if(keys[i] != null)
				t.put(keys[i], values[i]);
		}
		keys = t.keys;
		values = t.values;
		size = t.size;
		rehash++;
		//System.out.println("aqui entra");
	}
	
	/**
	 * M�todo que adiciona un nuevo par llave - valor a sus correspondientes arreglos
	 * post: se ha adicionado un nuevo elemento al HashTable 
	 */
	public void put(K key, V val) {
		if((double)numberKeys >= (2.0/4.0)*((double)size)) resizing(size*2+1);
		int i;
		for(i = hash(key); keys[i] != null; i = (i+1)%size) {
			if(keys[i].compareTo(key) == 0) {values[i] = val; return;}
		}
		keys[i] = key;
		values[i] = val;
		numberKeys++;

	}

	/**
	 * M�todo que devuelve el elemento asociado con la llave pasada por parametro.
	 * @param Key la llave asociada a un valor, Key != null
	 * @return el elemento asociado con la llave pasada por parametro.
	 */
	public V get(K key) {
		//System.out.println("el hash de la llave" + hash(key));
		for(int i = hash(key); keys[i] != null; i = (i+1)%size) {
			if(keys[i].compareTo(key) == 0) {
				//System.out.println("se encontro en: "+ i);
				return values[i];
			}
			if(i == 1210) {
				//System.out.println("que gonorrea porque no lo cogio como igual: " + keys[i].compareTo(key));
				
			}
		}
		//System.out.println(values[1210]);
		return null;
	}
	
	/**
	 * M�todo que notifica si una cierta llave esta en el HashTable
	 * @param Key la llave que se quiere comparar, Key != null
	 * @return verdadero o falso seg�n se encuentre la llave.
	 */
	public boolean contains(K key) {
		//System.out.println("entro aqui?");
		for(int i = 0; i < keys.length; i++) {
			if(keys[i] != null && keys[i].compareTo(key) == 0) {
			//	System.out.println("regreso true");
				return true;
			}
		}
		return false;
	}
	/**
	 * M�todo que borra el elemento asociado con la llave pasada por parametro y la llave
	 * post: se ha borrado la llave y el elemento asociado con la llave.
	 * @param Key la llave asociada a un valor. Key != null
	 * @return el valor que se borro asociado a la llave pasada por parametro.
	 */
	public V delete(K key) {
		if(!contains(key)) return null;
		int i;
		for(i = 0; i < keys.length; i++) {
			if(keys[i] != null && keys[i].compareTo(key) == 0)
				break;
		}
		V answer = values[i];
		keys[i] = null;
		values[i] = null;
		i = (i+1)%size;
		while(keys[i] != null) {
			K toAddAgainK = keys[i];
			V toAddAgainV = values[i];
			keys[i] = null;
			values[i] = null;
			numberKeys--;
			this.put(toAddAgainK,toAddAgainV);
			i = (i+1)%size;
		}
		numberKeys--;
		if(numberKeys > 0 && numberKeys <= size/8) resizing(size/2);
		return answer;
	}

	/**
	 * Notifica si el hashTable no tiene ning�n dato
	 * @return booleano diciendo si no hay datos
	 */
	public boolean isEmpty() {
		return numberKeys == 0;
	}
	
	/**
	 * regresa el n�mero de elementos, ya sea llaves o valores del hashTable
	 * @return el n�mero de elementos, ya sea llaves o valores del hashTable
	 */
	public int getSize() {
		return numberKeys;
	}
	
	/**
	 * Retorna el tama�o del arreglo
	 * @return El tama�o del arreglo
	 */
	public int getM() {
		return size;
	}
	
	/**
	 * Retorna el n�mero de rehash
	 * @return El n�mero de rehash
	 */
	public int getRehash() {
		return rehash;
	}	
	
	/**
	 * M�todo que devuelve un iterador a las llaves que se encuentran en HashTable
	 * @return un iterador a las llaves que se encuentran en HashTable
	 */
	public Iterator<K> keys() {
		ORArray<K> t = new ORArray<K>(numberKeys);
		for(int i = 0; i < keys.length; i++) {
			if(keys[i] != null)
				t.add(keys[i]);
		}
		return t.iterator();
	}
}


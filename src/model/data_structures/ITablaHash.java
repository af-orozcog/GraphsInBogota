package model.data_structures;

import java.util.Iterator;

public interface ITablaHash<K extends Comparable<K>, V>  {
	
	/**
	 * M�todo que retorna el n�mero de elementos de la tabla hash
	 * @return el n�mero de elementos de la tabla hash.
	 */
	public int getSize();
	
	/**
	 * M�todo que adiciona un nuevo elemento por llave y valor
	 * post: se ha adicionado un nuevo elemento al HashTable 
	 */
	public void put(K key, V val);
	
	/**
	 * M�todo que devuelve el elemento asociado con la llave pasada por parametro.
	 * @param Key la llave asociada a un valor, Key != null
	 * @return el elemento asociado con la llave pasada por parametro.
	 */
	public V get(K key);
	
	/**
	 * M�todo que borra el elemento asociado con la llave pasada por parametro y la llave
	 * post: se ha borrado la llave y el elemento asociado con la llave.
	 * @param Key la llave asociada a un valor. Key != null
	 * @return el valor que se borro asociado a la llave pasada por parametro.
	 */
	public V delete(K key);
	
	/**
	 * M�todo que devuelve un iterador a las llaves que se encuentran en HashTable
	 * @return un iterador a las llaves que se encuentran en HashTable
	 */
	public Iterator<K> keys();
	
	/**
	 * M�todo que retorna si la tabla contiene un elemento con esa llave
	 * @param key La llave a buscar
	 * @return Si la tabla contiene un valor asociado a esa llave
	 */
	public boolean contains(K key);

	/**
	 * Retorna el tama�o del arreglo
	 * @return El tama�o del arreglo
	 */
	public int getM();
	
	/**
	 * Retorna el n�mero de rehash
	 * @return El n�mero de rehash
	 */
	public int getRehash();
	
	/**
	 * 
	 * @return
	 */
	public boolean isEmpty();
}

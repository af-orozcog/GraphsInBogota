package model.data_structures;

import java.util.Iterator;

/**
 * Inteface del arbol binario RedBlack
 */
public interface IRedBlackBST<V,K extends Comparable<K>> {

	/**
	 * Retornar el n�mero de parejas [Llave,Valor] del �rbol
	 * @return El n�mero de parejas [Llave,Valor] del �rbol
	 */
	public int size();
	
	/**
	 * Informa si el �rbol es vac�o
	 * @return Si el �rbol es vac�o
	 */
	public boolean isEmpty();
	
	/**
	 * Retorna el valor V asociado a la llave key dada. 
	 * Si la llave no se encuentra se retorna el valor null
	 * @param key Llave del valor a buscar
	 * @return El valor V asociado a la llave key dada.
	 */
	public V get(K key);
	
	/**
	 * Retorna la altura del camino desde la raiz para llegar a la llave key (si la llave existe). 
	 * Retorna valor �1 si la llave No existe.
	 * @param key Llave donde se busca saber la altura.
	 * @return La altura del camino desde la raiz para llegar a la llave key (si la llave existe).
	 */
	int getHeight(K key);
	
	/**
	 * Indica si la llave key se encuentra en el �rbol
	 * @param key Llave a buscar
	 * @return Si la llave key se encuentra en el �rbol
	 */
	boolean contains(K key);
	
	/**
	 * Inserta la pareja [key, val] en el �rbol respetando el balanceo RedBlack. 
	 * Si la llave ya existe se reemplaza el valor. 
	 * @param key Llave de la pareja a insertar.
	 * @param val Valor de la pareja a insertar.
	 * @throws Exception Si la llave key o el valor val es null
	 */
	void put(K key, V val) throws Exception;
	
	/**
	 * Retorna la altura del �rbol definida como la altura de la rama m�s alta 
	 * (aquella que tenga mayor n�mero de enlaces desde la ra�z a una hoja).
	 * @return La altura del �rbol definida como la altura de la rama m�s alta
	 */
	int height();
	
	/**
	 * Retorna la llave m�s peque�a del �rbol. Valor null si �rbol vac�o
	 * @return La llave m�s peque�a del �rbol.
	 */
	K min();

	/**
	 * Retorna la llave m�s grande del �rbol. Valor null si �rbol vac�o
	 * @return La llave m�s grande del �rbol.
	 */
	K max();
	
	/**
	 * Valida si el �rbol es Binario Ordenado y est� balanceado RojoNegro a la izquierda. 
	 * Hay que validar que:
	 * (a) La llave de cada nodo sea mayor que cualquiera de su sub�rbol izquierdo.
	 * (b) La llave de cada nodo sea menor que cualquiera de su sub�rbol derecho.
	 * (c) Un nodo NO puede tener enlace rojo a su hijo derecho.
	 * (d) No puede haber dos enlaces rojos consecutivos a la izquierda. 
	 * Es decir, un nodo NO puede tener un enlace rojo a su hijo izquierdo 
	 * y su hijo izquierdo NO puede tener enlace rojo a su hijo izquierdo.
	 * (e) Todas las ramas tienen el mismo n�mero de enlaces negros.
	 * @return Si el �rbol es Binario Ordenado y est� balanceado RojoNegro a la izquierda.
	 */
	boolean check();
	
	/**
	 * Retorna todas llaves del �rbol como un iterador
	 * @return Un iterador de todas las llaves.
	 */
	Iterator<K> keys();
	
	/**
	 * Retorna todos los valores V en el �rbol que est�n asociados al rango de llaves dado.
	 * Por eficiencia, debe intentarse No recorrer todo el �rbol.
	 * @param init Valor m�nimo para la llave.
	 * @param end Valor m�ximo para la llave.
	 * @return Un iterador de todos los valores V que tengan llaves en el rango dado por par�metro
	 */
	Iterator<V> valuesInRange(K init, K end);
	
	/**
	 * Retorna todas las llaves K en el �rbol que se encuentran en el rango de llaves dado.
	 * Por eficiencia, debe intentarse No recorrer todo el �rbol.
	 * @param init Valor m�nimo para la llave.
	 * @param end Valor m�ximo para la llave.
	 * @return Un iterador de todos los valores V que tengan llaves en el rango dado por par�metro
	 */
	Iterator<K> keysInRange(K init, K end);
}

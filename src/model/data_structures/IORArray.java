package model.data_structures;

import java.util.Comparator;

public interface IORArray<T> extends Iterable<T> {

	/**
	 * Agrega un objeto al final del arreglo
	 * @param pNew Nuevo elemento. pNew != null.
	 */
	public void add(T pNew);

	/**
	 * Retorna el elemento dado por parametro
	 * @param pPos Posci�n del elemento buscado. pPos >= 0.
	 * @return El elemento buscado.
	 */
	public T getElement( int pPos);

	/**
	 * Retorna el tama�o actual del arreglo
	 * @return El tama�o actual del arreglo
	 */
	public Integer getSize();

	/**
	 * Elimina el �ltimo elemento de la lista.
	 * @return El elemento eliminado.
	 */
	public T delete();

	/**
	 * Elimina un elemento de una poscisi�n determinada y retorna lo eliminado.
	 * @param posK Posici�n del elemento a eliminar. posK >= 0.
	 * @return El elemento eliminado.
	 */
	public T deleteAtK(int posK);

	/**
	 * Ordena la lista seg�n el comparador y el rango de posici�n pasado por parametro.
	 * @param order. Comparador utilizado para ordenar el arreglo. order != null
	 * @param lo. entero que representa la posici�n de inicio desde la cual se ordena. lo >= 0 && lo < size 
	 * @param hi. entero que representa la posici�n final hasta donde se ordena. hi >= 0 && hi < size
	 */
	public void sort(Comparator<T> order, int lo, int hi);

	/**
	 * M�todo que ordena todo el arreglo seg�n un comparador pasado por parametro
	 * @param order. comparador con el cual se ordena el arreglo. order != null
	 * post: se ordena el arreglo seg�n el comparador
	 */
	public void sort(Comparator<T> order);

	/**
	 * M�todo que regresa verdadero o falso seg�n si el arreglo esta vacio o no.
	 * @return verdadero o falso si el arreglo esta vacio
	 */
	public boolean isEmpty();
}
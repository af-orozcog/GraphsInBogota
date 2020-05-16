package model.data_structures;

import java.util.Iterator;

public class Queue<T> implements IQueue<T> {

	/**
	 * Primer elemento de la cola.
	 */
	private Node first;
	
	/**
	 * �ltimo elemento de la cola
	 */
	private Node last;
	
	/**
	 * Tama�o de la cola
	 */
	private int size;

	/**
	 * Clase nodo, se usa para guardar los datos de la Cola
	 * @author Nicol�s Abondano. 201812467
	 *
	 */
	class Node{

		/**
		 * El siguiente nodo.
		 */
		Node next;

		/**
		 * El dato guardado en el nodo.
		 */
		T dato;

		/**
		 * Constructor del nodo,
		 */
		public Node(T pDato){ 
			next = null; 
			dato = pDato;
		}
	}

	/**
	 * Constructor de la clase Queue
	 */
	public Queue() {
		size = 0;
		first = null;
		last = null;
	}

	/**
	 * Retorna true si la Cola esta vacia
	 * @return true si la Cola esta vacia, false de lo contrario
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Retorna el numero de elementos contenidos
	 * @return el numero de elemntos contenidos
	 */
	public int size() {
		return size;
	}

	/**
	 * Inserta un nuevo elemento en la Cola
	 * @param t el nuevo elemento que se va ha agregar
	 */
	public void enqueue(T t) {
		Node nuevo = new Node(t);
		if(first != null) {
			last.next = nuevo;
			last = nuevo;
		}
		else {
			first = nuevo;
			last = nuevo;
		}
		size++;
	}

	/**
	 * Quita y retorna el elemento agregado menos recientemente
	 * @return el elemento agregado menos recientemente
	 */
	public T dequeue() {
		T eliminado = null;
		eliminado = first.dato;
		if(last.equals(first)) {
			last = null; first = null;
		}
		else
			first = first.next;
		size--;
		return eliminado;
	}

	/**
	 * Crea un objeto de tipo iterador de la Cola.
	 */
	public Iterator<T> iterator() {
		return new IteratorQueue(first);
	}

	public T first() {
		return first.dato;
	}
	
	/**
	 * Clase que implementa iterador para utilizar con la Cola.
	 * @author Nicol�s Abondano 201812467
	 *
	 */
	private class IteratorQueue implements Iterator<T> {

		/**
		 * Pr�ximo nodo a recorrer por el iterador.
		 */
		private Node proximo;

		/**
		 * Constructor de la clase lista iterador.
		 */
		public IteratorQueue(Node primero) {
			proximo = primero;
		}

		/**
		 * Retorna si hay un proximo nodo a recorrer
		 * @return Si hay siguiente nodo a recorrer
		 */
		public boolean hasNext() {
			if(proximo == null)
				return false;
			return true;
		}

		/**
		 * Retorna el proximo nodo a recorrer
		 * @return el proximo nodo a recorrer
		 */
		public T next() {
			T actual = proximo.dato;
			proximo = proximo.next;
			return actual;
		}

		/**
		 * M�todo no implementado
		 */
		public void remove() {

		}
	}

}

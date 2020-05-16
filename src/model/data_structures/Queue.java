package model.data_structures;

public class Queue<E extends Node> implements IQueue<E>
{
	private E first;
	private int size;
	private E last;
	//Constructor
	public Queue(){
		first = null;
		last = null;
		int size = 0;
	}
	//Pone el objeto al final de la cola
	public void ponerEnCola(E elem) {
		if ( size == 0)
		{	first = elem;
			last = elem;	}
		else 
		{	last.setNext(elem);
			last = elem;	}			
		size++;
	}
	//Saca el primero que esté en la cola
	public E sacarDeCola() throws Exception {
		if(first == null)
			throw new Exception("La lista está vacia");
		E primero = first;
		first = (E)first.getNext();
		size--;
		return primero;
	}	
	//Retorna el tamaño
	public int darTamanio() {
		return size;
	}
	//Retorna si la lista está o no vacía
	public boolean vacia() {
		return first == null;
	}
	//Retorna el objeto que está en primer lugar
	public E darPrimero() {
		return first;
	}
}
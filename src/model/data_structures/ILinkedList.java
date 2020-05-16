package model.data_structures;


public interface ILinkedList<T> {
	
	//retorna el tamaño de la lista
	public int getSize();
	
	public boolean isEmpty();
	
	//retorna el primer nodo de la lista
	public Node<T> getFirst();
	
	//retorna el último nodo de la lista
	public Node<T> getLast();
	
	//agrega un nodo al inicio de la lista
	public void prepend(Node<T> node);
	
	//agrega un nodo al final de la lista
	public void append(Node<T> node);
	
	//remueve el primer nodo
	public void removeFirst();
	
	//remueve el ultimo nodo
	public void removeLast();
	
	//intercambia 2 nodos de lugar
	public void swap(Node<T> swap1 ,Node<T> swap2);
	
	public Node<T> get(int pIndex);
}

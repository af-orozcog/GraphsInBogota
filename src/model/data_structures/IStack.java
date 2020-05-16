package model.data_structures;

public interface IStack<T> {
	
	public void push(Node<T> elem);
	
	public T pop ();
	
	public int size();
	
	public boolean isEmpty();
	
	//permite ver el primer elemento de la pila sin eliminarlo.
	public T getFirst();
	
}

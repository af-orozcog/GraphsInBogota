package model.data_structures;

public class Node<T> {
	
	private Node<T> next;
	private int index;
	private T object;

	public Node (T pObject) 
	{
		next = null;
		object = pObject;
	}

	public Node<T> getNext() 
	{
		return next;}

	public void setNext ( Node<T> pNext) 
	{
		next = pNext;
	}

	public T getObject()
	{
		return object;
	}

	public void setObject (T pObject) 
	{
		object = pObject;
	}
	
	public int getIndex()
	{
		return index;
	}

	public void setIndex(int pIndex)
	{
		index = pIndex;
	}
}

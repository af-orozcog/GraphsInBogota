package model.data_structures;


public class Stack<T> implements IStack<T> {

	//Se usará la implementación con lista encadenada y nodos.
	private Node<T> head;
	private int size;

	public Stack()
	{
		head = null;
		size = 0;
	}

	public void push(Node<T> add) 
	{
		if(head == null)
		{
			head = add;
		}
		else
		{
			add.setNext(head);
			head = add;
		}
		size++;
	}


	public T pop() {
		T retornar = getFirst();
		eliminarPrimero();
		return retornar;
	}

	private void eliminarPrimero()
	{
		if(head!=null)
		{
			Node<T> newHead = head.getNext();
			head.setNext(null);
			head = newHead;
			
		}
	}

	public int size() 
	{
		return size;
	}


	public boolean isEmpty() 
	{	
		return ( size == 0 ) ? true : false;
	}


	public T getFirst() 
	{
		if(head != null)
		{
			return head.getObject();
		}
		else
		{
			return null;
		}
	}

}

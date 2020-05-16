package model.data_structures;

public class LinkedList<T> implements ILinkedList<T> {

	private Node<T> first;
	private Node<T> last;
	private int size;

	public LinkedList()
	{
		first = null;
		last = null;
		size = 0;
	}

	public int getSize() {
		return size;
	}

	public boolean isEmpty() {
		if(size == 0)
			return true;

		return false;
	}

	public Node<T> getFirst() {
		return first;
	}

	public Node<T> getLast() {
		return last;
	}

	//agregar al inicio
	public void prepend(Node<T> node) 
	{
		node.setIndex(0);
		node.setNext(getFirst());
		first = node;
		if(size == 0)
		{
			last = node;
		}
		
		Node<T> current = first.getNext();
		int newIndex = first.getIndex()+1;
		while(current != null)
		{
			current.setIndex(newIndex);
			current = current.getNext();
			newIndex++;
		}
		size++;
	}

	public void append(Node<T> node) {
		
		if(last != null)
		{
			last.setNext(node);
			node.setIndex(last.getIndex()+1);
			last = node;
			size++;
		}
		else
		{
			node.setIndex(0);
			first = node;
			last = node;
			size++;
		}
	}

	public void removeFirst() {
		if(first == null)
			System.out.println("La lista esta vacía");
		else
		{ //Si en la lista hay un siguiente del primer nodo
			Node<T> segundo = first.getNext();
			if(segundo != null)
			{
				first = segundo;
				first.setIndex(0);
				Node<T> current = first;
				while(current.getNext() != null)
				{
					current.setIndex(current.getIndex()-1);
					current = current.getNext();
				}
				size--;
				last=get(size);
			} 
			else //Si no hay segundo nodo
			{
				last = null;
				first = null; 
				size = 0;
			}
		}
	}

	public void removeLast() {
		if(size == 0) {
			System.out.println("Lista vacia"); return;}
		else 
		{
			if(size == 1) 
			{
				first = null;
				last = null;
				size = 0;
				return;
			}
			else 
			{ 
				size = size -1;
				Node<T> temp = get(size-1);
				temp.setNext(null);
				last = temp;
				return;
			}
		}
	}

	private void swapIndex(int indexA, int indexB)
	{
		if(indexA != indexB)
		{
			Node<T> a = get(indexA);
			Node<T> b = get(indexB);
			if(indexA != 0 && indexB != 0) 
			{
				Node<T> aBefore = get(indexA-1);
				Node<T> bBefore = get(indexB-1);
				Node<T> aAfter = a.getNext();
				Node<T> bAfter = b.getNext();
				aBefore.setNext(b);
				b.setNext(aAfter);
				bBefore.setNext(a);
				a.setNext(bAfter);
				a.setIndex(indexB);
				b.setIndex(indexA);
				return;
			} else 
			{
				// Si el B es el primero
				if(indexB == 0)
				{
					//Si A es el ultimo
					if(indexA == size)
					{
						Node<T> penultimo = a.getNext();
						a.setNext(b.getNext());
						first = a;
						penultimo.setNext(b);
						last = b;
						return;
					}
					// Si A no es el ultimo
					Node<T> anteriorA = get(indexA-1);
					Node<T> siguienteA = a.getNext();
					Node<T> segundo = b.getNext();
					a.setNext(segundo);
					first = a;
					anteriorA.setNext(b);
					b.setNext(siguienteA);
					return;
				}
				//Si A es el primero
				if(indexA == 0)
				{
					//Si B es el ultimo
					if(indexB == size)
					{
						Node<T> penultimo = b.getNext();
						b.setNext(a.getNext());
						first = b;
						penultimo.setNext(a);
						last = a;
						return;
					}
					// Si A no es el ultimo
					Node<T> anteriorB = get(indexB-1);
					Node<T> siguienteB = b.getNext();
					Node<T> segundo = a.getNext();
					b.setNext(segundo);
					first = b;
					anteriorB.setNext(a);
					b.setNext(siguienteB);
					return;
				} 
				//Si A es el ultimo
				if ( indexA == size)
				{
					Node<T> penultimo = get(indexA-1);
					//Si B es el primero
					if(indexB == 0)
					{
						Node<T> segundo = b.getNext();
						a.setNext(segundo);
						a = first;
						penultimo.setNext(b);
						last = b;
						return;
					}
					//Si B no es el primero
					Node <T> siguienteB = b.getNext();
					Node<T> anteriorB = get(indexB-1);
					penultimo.setNext(b);
					last = b;
					anteriorB.setNext(a);
					a.setNext(siguienteB);
					return;
				}
				//Si B es el ultimo
				if ( indexB == size)
				{
					Node<T> penultimo = get(indexB-1);
					//Si A es el primero
					if(indexA == 0)
					{
						Node<T> segundo = a.getNext();
						b.setNext(segundo);
						b = first;
						penultimo.setNext(a);
						last = a;
						return;
					}
					//Si A no es el primero
					Node <T> siguienteA = a.getNext();
					Node<T> anteriorA = get(indexA-1);
					penultimo.setNext(a);
					last = a;
					anteriorA.setNext(b);
					b.setNext(siguienteA);
					return;
				}
			}
		}
	}

	public void swap(Node<T> swap1, Node<T> swap2)
	{
		swapIndex(swap1.getIndex(),swap2.getIndex());
	}

	public Node<T> get(int pIndex)
	{
		if(pIndex == 0)
			return first;
		else if(pIndex == size)
			return last;

		Node<T> current = first;
		int count = 0;
		if (current != null) 
		{
			while(count < pIndex)
			{
				current = current.getNext();
				count++;
			}
			return current; 
		}
		else
			return null;
	}

}
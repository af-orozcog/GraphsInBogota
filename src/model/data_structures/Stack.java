package model.data_structures;

import java.util.Iterator;

/**
 * 
 * @author Andres
 *
 * @param <T>
 */
public class Stack<T> implements IStack<T> {
	
	//------------------Atributos------------------------------------//
	
	/**
	 * primer nodo de la pila
	 */
	private Node first;
	
	/**
	 * tama�o de la pila
	 */
	private int size;
	
	//--------------------Constructores-------------------------------//
	
	/**
	 * constructor de la pila
	 * post: se inicializa el primer nodo como nulo y el tama�o como 0
	 */
	public Stack(){
		first = null;
		size = 0;
	}
	
	//------------------Clase auxiliar--------------------------------//
	
	/**
	 * Clase que representa los nodos de la pila
	 * @author 
	 *
	 */
	class Node{
		//-----------------------------Atributos--------------------------//
		
		/**
		 * nodo que va despues del actual
		 */
		Node next;
		
		/**
		 * valor que se va a guardar en cada nodo
		 */
		T obje;
		
		//----------------------------Costructores--------------------------//
		
		/**
		 * m�todo constructor del nodo
		 * @param pObje obje valor que se va a guardar en el nodo.
		 * @param pNext siguiente nodo al actual
		 */
		public Node(T pObje, Node pNext){obje = pObje; next = pNext;}
	}
	
	//--------------------------------Metodos-----------------------------//
	
	/**
	 * m�todo que regresa un iterador para recorrer la pila
	 * @return iterador de la pila
	 */
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return new IteradorStack(first);
	}

	/**
	 * retorna si la pila esta vacia
	 * @return veradero o falso seg�n el caso
	 */
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return size == 0;
	}
	
	/**
	 * retorna el tama�o actual de la pila
	 * @return tama�o de la pila
	 */
	public int size() {
		// TODO Auto-generated method stub
		return size;
	}
	
	/**
	 * ingresa un nuevo objeto en el comienzo de la pila
	 * post: se ha a�adido un nuevo objeto al inicio de la pila
	 * @param t objeto que se va a a�adir.
	 */
	public void push(T t) {
		// TODO Auto-generated method stub
		if(first == null){
			first = new Node(t,null);
		}
		else{
			Node newFirst = new Node(t,first);
			first = newFirst;
		}
		size++;
	}
	
	/**
	 * retorna el �ltimo elemento adicionado a la pila
	 * @return ultimo elemento adicionado a la pila
	 */
	public T pop() {
		// TODO Auto-generated method stub
		T ans = first.obje;
		first = first.next;
		size--;
		return ans;
	}
	/**
	 * clase que crea un iterador a la pila
	 * @author Andres
	 *
	 */
	class IteradorStack implements Iterator<T>{

		//----------------------------Atributos------------------------//
		
		/**
		 * nodo desde donde inicia.
		 */
		Node nodo;
		
		//---------------------------Constructores--------------------//
		
		/**
		 * m�todo constructor que inicializa el nodo al nodo pasado por parametro
		 * @param primero nodo desde el cual se inicia el recorrido, primero != null
		 */
		public IteradorStack(Node primero){
			nodo = primero;
		}
		
		
		//--------------------------M�todos--------------------------//
		
		/**
		 * retorna si hay un nodo despu�s del actual
		 * @return verdadero o falso seg�n sea el caso
		 */
		public boolean hasNext() {
			return nodo != null;
		}
		
		/**
		 * retorna el objeto que se encuentra en el siguiente nodo al actual.
		 * @return el objeto que se encuetra en el siguiente nodo.
		 */
		public T next() {
			T ans = nodo.obje;
			nodo = nodo.next;
			return ans;
		}
		
	}
	
}

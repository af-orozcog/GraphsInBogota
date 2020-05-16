package model.data_structures;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;

public class ORArray<T> implements IORArray<T> {


	//--------------------------Atributos-------------------------//

	/**
	 * cantidad de datos en el arreglo 
	 */
	private int size;

	/**
	 * arreglo donde se almacenan los datos
	 */
	private T datos[];

	//----------------------------Constructores---------------------//

	/**
	 * se inicializan el arreglo de datos y se asigna a size 0
	 * post: se inicializo el arreglo
	 */
	public ORArray() {
		datos = (T[]) new Object[10];
		size = 0;
	}

	/**
	 * Se inicializa el arreglo de datos con el tama�o inicial indicado y se asigna
	 * a size 0
	 * @param tama�oInicial tama�o del arreglo tama�oInicial > 0
	 * post: se inicializo el arreglo
	 */
	public ORArray(int tamanoInicial) {
		datos = (T[]) new Object[tamanoInicial];
		size = 0;
	}

	//------------------------------M�todos-------------------------//


	/**
	 * M�todo que cambia el tama�o del arreglo copiando su contenido a un arreglo m�s grande.
	 * @param newSize tama�o del arreglo newSize >= 0
	 * @throws Exception si no fue posible aumentar el tama�o del arreglo
	 */
	private void resizing(int newSize)throws Exception {
		if(newSize == 0)  newSize = 1;
		T copy[] = (T[]) new Object[newSize];
		int recorrer;
		if(size < newSize)
			recorrer = size;
		else
			recorrer = newSize;
		for(int i = 0; i < recorrer; i++) {
			copy[i] = datos[i];
		}
		datos = copy;
		
	}

	/**
	 * M�todo que mueve los elementos a la izquierda de la posici�n actual
	 * @param position desde la cual se empiezan a mover los datos a la izquierda
	 * post: se han movido los elementos a la izquierda y se ha "eliminado" el dato en posici�n
	 */
	private void moveElementsLeft(int position) {
		int movIndex = position;
		while(movIndex < size-1) {
			datos[movIndex] = datos[movIndex+1];
			movIndex++;
		}
	}

	/**
	 * M�todo que devuelve un iterador al arreglo
	 * @return devuelve un iterador al arreglo.
	 */
	public Iterator<T> iterator() {
		return new IteratorOrderedArray();
	}

	/**
	 * M�todo que a�ade un elemento al final del arreglo
	 * @param pNew. elemento que se va a�adir.
	 * post: se ha a�adido un elemento al final del arreglo.
	 */
	public void add(T pNew) {
		if(size == datos.length) {
			try {
				resizing(size*2);
			}catch(Exception e) {
				e.printStackTrace();
				return;
			}
		}
		datos[size] = pNew;
		size++;
	}

	/**
	 * M�todo que a�ade un elemento en una posici�n del arreglo
	 * @param pNew. elemento que se va a�adir.
	 * @param pIndice. poscici�n donde se quiere a�adir el arreglo.
	 * post: se ha a�adido un elemento al final del arreglo.
	 */
	public void addPos(T pNew, int pIndice) {
		if(datos[pIndice] == null)
			size++;
		datos[pIndice] = pNew;
	}

	/**
	 * M�todo que devuelve el elemento en la posici�n dada por parametro.
	 * @param pPos. posici�n del elemento que se desea tener. pPos < size, pPos>= 0
	 * @return elemento en la posici�n especificada.
	 */
	public T getElement(int pPos) {
		if(pPos >=size || pPos < 0) return null;
		return datos[pPos];
	}

	/**
	 * M�todo que devuelve la cantidad de elementos en el arreglo.
	 * @return la cantidad de elementos en el arreglo.
	 */
	public Integer getSize() {
		return size;
	}

	/**
	 * M�todo que borra y devuelve el elemento que se ubique en el ultima posici�n del arreglo especificada por size
	 * post: se borra el ultimo elemento especificado por el valor de size.
	 * @return el elemento borrado o null sino se elimino ninguno
	 */
	public T delete() {
		if(size == 0) return null;
		T answer = null;
		try {
			answer = datos[size -1];
		}catch(Exception e) {
			e.printStackTrace();
		}
		datos[size - 1] = null;
		size--;
		if(size <= datos.length/4) {
			try {
				resizing(datos.length/2);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return answer;
	}

	/**
	 * M�todo que devuelve el elemento que se borro en la posici�n especificada por parametro.
	 * @param posK. posici�n en la cual se quiere borra el elemento. posK < size, posK>= 0
	 */
	public T deleteAtK(int posK) {
		if(size == 0) return null;
		if(posK >= size || posK < 0) return null;
		T answer = datos[posK];
		moveElementsLeft(posK);
		size--;
		if(size <= datos.length/4) {
			try {
				resizing(datos.length/2);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return answer;
	}

	public T deleteElm(T del) {
		if(size == 0) return null;
		T answer = null; int in = -1;
		try {
			for (int i=0; i<datos.length;i++) {
				T t = datos[i];
				if(t.equals(del)) {
					answer = deleteAtK(in);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return answer;
	}
	
	/**
	 * M�todo que intercambia los elementos de dos posiciones del arreglo
	 * @param x. la posici�n de uno de los elementos. x >= 0 && x < size.
	 * @param y. la posici�n de uno de los elementos. y >= 0 && y < size.
	 * post: se han intercambiado los elementos en las posiciones x y y.
	 */
	private void exchPosition(int x, int y) {
		T temp = datos[x];
		datos[x] = datos[y];
		datos[y] = temp;
	}

	/**
	 * M�todo que ordena el arreglo seg�n en comparador pasado por parametro y las posici�n final e inicial.
	 * @param order. Comparador con el cual se ordena el arreglo. order != null.
	 * @param lo. entero que representa la posici�n de inicio desde la cual se ordena. lo >= 0 && lo < size 
	 * @param hi. entero que representa la posici�n final hasta donde se ordena. hi >= 0 && hi < size
	 * post: se ha ordenado el arreglo seg�n en comparador en el rango indicado
	 */
	public void sort(Comparator<T> order, int lo, int hi) {
		if(lo < 0 | lo >= size | hi < 0 | hi >= size) return;
		if(lo >= hi) return ;
		int lt = lo, movIndex = lo+1, gt = hi;
		T initial = datos[lo];
		while(movIndex <= gt) {
			int compVal = order.compare(initial, datos[movIndex]);
			if(compVal > 0) exchPosition(lt++,movIndex++);
			else if(compVal<0) exchPosition(movIndex,gt--);
			else movIndex++;
		}
		sort(order,lo, lt-1);
		sort(order,gt+1,hi);
	}

	/**
	 * M�todo que ordena todo el arreglo seg�n un comparador pasado por parametro
	 * @param order. comparador con el cual se ordena el arreglo. order != null
	 * post: se ordena el arreglo seg�n el comparador
	 */
	public void sort(Comparator<T> order) {
		if(datos[0] instanceof Integer) {
			order = (Comparator<T>) new IntComparator();
		}
		shuffle();
		sort(order,0,size - 1);
	}

	/**
	 * M�todo que cambia la posici�n de los elementos en el arreglo pasado por parametro
	 * @param arreglo un arreglo comparable
	 * post: se ha cambiado el orden del arreglo
	 */
	private void shuffle() {
		Random random = null;
		if (random == null) random = new Random();
		for (int i = size; i > 1; i--) {
			exchPosition( i - 1, random.nextInt(i));
		}
	}

	/**
	 * M�todo que regresa verdadero o falso seg�n si el arreglo esta vacio o no.
	 * @return verdadero o falso si el arreglo esta vacio
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	//------------------------------------Clase auxiliar----------------------//


	/**
	 * Clase auxiliar utilizada para implementar el iterador de OrderedResizingArrayList
	 */
	class IteratorOrderedArray implements Iterator<T>{

		//--------------------------Atributos------------------------------//

		/**
		 * primera posici�n cada vez que se crea el iterador
		 */
		private int firstPo = 0;

		/**
		 * M�todo que informa si hay un elemento despu�s de la posici�n actual.
		 * @return verdadero o falso si hay un elemento despu�s de la posici�n actual.
		 */
		public boolean hasNext() {
			return firstPo < size;
		}

		/**
		 * Regresa el elemento en la siguiente posici�n.
		 * @return elemento en la siguiente posici�n
		 */
		public T next() {
			return datos[firstPo++];
		}
	}
	class IntComparator implements Comparator<Integer> {

	    @Override
	    public int compare(Integer v1, Integer v2) {
	        return v1 < v2 ? -1 : v1 > v2 ? +1 : 0;
	    }
	}
}

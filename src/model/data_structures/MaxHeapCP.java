package model.data_structures;

import java.util.Iterator;

public class MaxHeapCP<T extends Comparable<T>> implements IQueue<T>{

	//-----------------------Atributos-------------------------------//
	
	/**
	 * atributo donde se guardan los datos
	 */
	private ORArray<T> arreglo;
	
	//----------------------Constructor-------------------------------//
	
	/**
	 * incializa el arreglo donde se van a guardar los datos 
	 * y a�ade el primer elemento igual a null
	 */
	public MaxHeapCP(){
		arreglo = new ORArray<T>();
		arreglo.add(null);
	}
	public MaxHeapCP(int tamanoInicial){
		arreglo = new ORArray<T>(tamanoInicial);
		arreglo.add(null);
	}
	
	//----------------------Metodos------------------------------------//
	
	/**
	 * M�todo que devuelve la cantidad de elementos en el priority queue
	 * @return la cantidad de elementos en el priority queue
	 */
	public int darNumeroElementos() {
		return arreglo.getSize()-1;
	}
	
	/**
	 * M�todo que agrega un nuevo elemento al priority queue
	 * post: se ha agregado un elemento al priority queue y la estructura queda ordenada
	 */
	public void enqueue(T elemento) {
		arreglo.add(elemento);
		insert(arreglo.getSize()-1);
	}
	
	/**
	 * M�todo que se encarga de bajar o mantener en su posici�n un elemento del heap
	 * @param pos la posici�n del elemento
	 * post: el heap queda ordenado seg�n prioridad
	 */
	private void sinkDown(int pos) {
		T elemento = arreglo.getElement(pos);
		int pos1 = pos*2; int pos2 = pos*2 +1;
		T hijo1 = arreglo.getElement(pos1);
		T hijo2 = arreglo.getElement(pos2);
		if(hijo1 != null && elemento.compareTo(hijo1)<0 &&
				hijo2 != null && elemento.compareTo(hijo2)<0) {
			int posG = hijo1.compareTo(hijo2)>0?pos1:pos2;
			exch(pos, posG);
			sinkDown(posG);
		}
		else if(hijo1 != null && elemento.compareTo(hijo1)<0) {
			exch(pos, pos1);
			sinkDown(pos1);
		}
		else if(hijo2 != null && elemento.compareTo(hijo2)<0) {
			exch(pos, pos2);
			sinkDown(pos2);
		}
		else
			return;
	}
	
	/**
	 * M�todo que se encarga de subir o mantener la posici�n de un elemento del heap
	 * @param pos la posici�n del elemento
	 * post: el heap queda ordenado seg�n la prioridad 
	 */
	private void insert(int pos) {
		if(pos <= 1) return;
		T elemento = arreglo.getElement(pos);
		int posP = pos/2;
		T padre = arreglo.getElement(posP);
		if(elemento.compareTo(padre)>0) {
			exch(pos, posP);
			insert(posP);
		}
	}
	
	/**
	 * M�todo que se encarga de modificar cambiar los elementos en la posici�n especificada por parametro
	 * @param i1 posici�n del primer elemento i1 > 0 i1 < tama�o del heap
	 * @param i2 posici�n del segundo elemento i2> 0 i2 < tama�o del heap
	 */
	private void exch(int i1, int i2) {
		T e1 = arreglo.getElement(i1);
		T e2 = arreglo.getElement(i2);
		arreglo.addPos(e1, i2);
		arreglo.addPos(e2, i1);
	}
	
	/**
	 * M�todo que se encarga de borrar el maximo, y devolverlo
	 * post: el heap queda ordenado seg�n las prioridades de los elementos
	 * @return el maximo elemento en el heap
	 */
	public T dequeue() {
		int size = arreglo.getSize();
		T max;
		switch (size) {
		case 1:
			max = null;
			break;
		case 2:
			max = arreglo.delete();
			break;
		default:
			exch(1, size-1);
			max = arreglo.deleteAtK(size-1);
			sinkDown(1);
			break;
		}
		return max;
	}
	
	/**
	 * M�todo que devuelve el maximo elemento en el heap
	 * @return devuelve el maximo elemento en el heap
	 */
	public T max() {
		if(darNumeroElementos() == 0) return null;
		return arreglo.getElement(1);
	}
	
	/**
	 * M�todo que devuelve un booleano informando si el heap esta vacio
	 * @return booleano informando si el heap esta vacio
	 */
	public boolean esVacia() {
		return arreglo.getSize() == 1;
	}

	@Override
	public Iterator<T> iterator() {
		MaxHeapCP<T> nuevo = new MaxHeapCP<T>();
		ORArray<T> toPut = new ORArray<T>(arreglo.getSize());
		for(int i = 0; i < arreglo.getSize(); i++) {
			toPut.add(arreglo.getElement(i));
		}
		nuevo.arreglo = toPut;
		ORArray<T> answer = new ORArray<T>(arreglo.getSize());
		while(!nuevo.esVacia()) {
			answer.add(nuevo.dequeue());
		}
		return (Iterator<T>) answer.iterator();
	}
	
	/**
	 * 
	 * @param N
	 * @return
	 */
	public Queue<T> getNmaximums(int N) {
		MaxHeapCP<T> nuevo = new MaxHeapCP<T>();
		ORArray<T> toPut = new ORArray<T>(arreglo.getSize());
		for(int i = 0; i < arreglo.getSize(); i++) {
			toPut.add(arreglo.getElement(i));
		}
		nuevo.arreglo = toPut;
		Queue<T> answer = new Queue<T>();
		for(int i = 0; i < N && !nuevo.esVacia(); i++) {
			answer.enqueue(nuevo.dequeue());
		}
		return answer;
	}


	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return arreglo.getSize() == 0;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return arreglo.getSize();
	}

}
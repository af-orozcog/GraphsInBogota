package model.data_structures;

import java.util.Comparator;
import java.util.Iterator;

public class RedBlackBST<K extends Comparable<K>, V> implements IRedBlackBST<V, K>{

	//-------------------------------Constantes------------------------------------//

	/**
	 * Constante que representa el color negro
	 */
	private final static boolean BLACK = false;

	/**
	 * Constante que representa el color rojo
	 */
	private final static boolean RED = true;


	//--------------------------------Atributos------------------------------------//

	/**
	 * Nodo que se ubica en la raiz del arbol
	 */
	private Node root;

	/**
	 * Arreglo de llaves que se devuelven cuando el usuario lo requiere
	 */
	private ORArray<K> keys;

	/**
	 * arreglo donde se guardan las llaves para entregar al usuario 
	 */
	private ORArray<V> values;

	/**
	 * Arreglo donde se almacenan todas las llaves que se adicionan al arbol.
	 */
	private ORArray<K> otherKeys;

	/**
	 * Constructor que inicializa las listas de llaves y valores.
	 */
	public RedBlackBST() {
		keys = new ORArray<K>();
		values = new ORArray<V>();
		otherKeys = new ORArray<K>();
	}

	//--------------------------------Clase Auxuliar------------------------------//

	/**
	 * Clase auxiliar que representa los nodos que se van a utilizar para construir el arbol
	 *
	 */
	private class Node{
		//----------------------------Atributos-----------------------------------//

		/**
		 * La llave que identifica esa posici�n del arbol
		 */
		K key;

		/**
		 * valor que va a guardar en el nodo
		 */
		V val;

		/**
		 * los nodos (hijos) izquierdos y derechos
		 */
		Node right, left;

		/**
		 * numero de nodos "abajo" m�s el mismo
		 */
		int N;

		/**
		 * el color del nodo
		 */
		boolean color;

		//-------------------------------Constructor----------------------------//


		/**
		 * M�todo que inicializa el nodo con la llave que va a tener, la altura que tiene en el arbol el color y el valor que va a guardar
		 * @param pKey la llave que va a tener. PKey != null
		 * @param pVal el valor que va guardar
		 * @param pN la altura que va a tener. pN >= 0
		 * @param pColor el color que va a tener.
		 */
		public Node(K pKey, V pVal, int pN, boolean pColor){
			this.key = pKey; this.val = pVal;this.color = pColor; this.N = pN; this.right = null; this.left = null;
		}
	}

	/**
	 * Devuelve el n�mero de nodos en el arbol
	 */
	public int size(){
		return size(root);
	}

	/**
	 * M�todo que devuelve el n�mero de nodos bajo el nodo pasado por parametro m�s uno
	 * @param x el nodo del cual se va a obtener el dato. 
	 * @return el n�mero de nodos bajo el nodo pasado por parametro m�s uno
	 */
	private int size(Node x){
		if(x == null) return 0;
		return x.N;
	}

	/**
	 * M�todo que retonar verdadero o falso seg�n si el arbol esta vacio.
	 */
	public boolean isEmpty(){
		if(root == null) return true;
		return false;
	}

	/**
	 * Devuelve el valor del nodo con llave pasada por parametro.
	 * @param pKey la llave del nodo del cual se quiere saber el valor guardado. pKey != null.
	 */
	public V get(K pKey){
		return get(root, pKey);
	}

	/**
	 * Busca el nodo con la llave pasada por parametro a partir del subArbol del nodo pasado por parametros
	 * @param x nodo que comienza el subarbol donde se va a buscar el nodo con llave especificada. 
	 * @param pKey la llave del nodo que se esta buscando. pKey != null
	 * @return el valor del nodo con la llave pasada por parametro o null si no se encuentra.
	 */
	private V get(Node x, K pKey){
		if(x == null) return null;
		int cmp = pKey.compareTo(x.key);
		if(cmp < 0) return get(x.left, pKey);
		if(cmp > 0) return get(x.right, pKey);
		return x.val;
	}

	/**
	 * Retorna la altura del camino desde la raiz para llegar a la llave key (si la llave existe). 
	 * Retorna valor �1 si la llave No existe.
	 * @param key Llave donde se busca saber la altura.
	 */
	public int getHeight(K key) {
		int h = 0;
		int height = getHeight(key,h);

		Node n = getNode(key);
		if(n==null)return -1;
		return height;
	}

	/**
	 * Retorna el nodo asociado a esa llave
	 * @param x Nodo desde donde se inicia la busqueda
	 * @param pKey Llave a asociada al nodo a buscar
	 * @return El nodo asociado a la llave.
	 */
	private int getHeight(Node x, K pKey, int h){
		if(x == null) return -1;

		int cmp = pKey.compareTo(x.key);
		if(cmp == 0) return h;
		int h1 = h; int h2 = h;

		if(cmp < 0) {
			if(x.left.color) return getHeight(x.left, pKey,h1);
			else h1++; return getHeight(x.left, pKey,h1);
		}

		if(x.right.color) return getHeight(x.right, pKey,h2);
		else h2++; return getHeight(x.right, pKey,h2);
	}

	/**
	 * Retorna el nodo asociado a esa llave
	 * @param pKey Llave a asociada al nodo a buscar
	 * @return El nodo asociado a la llave.
	 */
	private int getHeight(K pKey, int h){
		return getHeight(root, pKey, h);
	}

	private Node getNode(K pKey) {
		return getNode(root, pKey);
	}

	/**
	 * Retorna el nodo asociado a esa llave
	 * @param x Nodo desde donde se inicia la busqueda
	 * @param pKey Llave a asociada al nodo a buscar
	 * @return El nodo asociado a la llave.
	 */
	private Node getNode(Node x, K pKey){
		if(x == null) return null;
		int cmp = pKey.compareTo(x.key);
		if(cmp < 0) return getNode(x.left, pKey);
		if(cmp > 0) return getNode(x.right, pKey);
		return x;
	}

	/**
	 * Retrona la altura desde un nodo.
	 * @param n Nodo desde donde se mide la altura
	 * @return La altura de un nodo.
	 */
	private int getTreeHeight(Node n) {
		if (n == null) return 0;
		if (n.color) return (Math.max(getTreeHeight(n.left), getTreeHeight(n.right)));
		return (Math.max(getTreeHeight(n.left), getTreeHeight(n.right))) + 1;
	}

	/**
	 * Indica si la llave key se encuentra en el �rbol.
	 */
	public boolean contains(K key) {
		return get(key) != null;
	}

	/**
	 * M�todo que inserta un nodo en el arbol
	 * @param pKey la llave del nodo
	 * @param pVal el valor que se va a guardar en el nodo
	 * post: se ha insertado un nodo en el arbol y el arbol ha quedado balanceado
	 */
	public void put(K pKey, V pVal){
		root = put(root, pKey, pVal);
		root.color = BLACK;
	}

	/**
	 * M�todo que mueve havia la izquierda el nodo pasado por parametro 
	 * en relaci�n de su hijo derecho
	 * @param x nodo que se va a rotar a la izquierda. x != null
	 * @return nuevo nodo que queda en la posici�n de su padre.
	 * post: el arbol ha quedado balanceado localmente
	 */
	private Node rotateLeft(Node x){
		Node t = x.right;
		x.right = t.left;
		t.left = x;
		t.color = x.color;
		x.color = RED;
		t.N = x.N;
		x.N = size(x.left) + size(x.right) + 1;
		return t;
	}

	/**
	 * M�todo que rota hacia la derecha el nodo actual con respecto al 
	 * hijo izquierdo
	 * @param x nodo que se va a rotar. x != null
	 * @return nuevo nodo que queda en la posici�n de su padre.
	 * post: el arbol ha quedado balanceado localmente
	 */
	private Node rotateRight(Node x){
		Node t = x.left;
		x.left = t.right;
		t.right = x;
		t.color = x.color;
		x.color = RED;
		t.N = x.N;
		x.N = size(x.left) + size(x.right) + 1;
		return t;
	}


	/**
	 * M�todo que inserta un nuevo nodo en el subArbol que comienza en el nodo pasado por parametro,
	 * Pasando la referencia del nodo insertado a su padre
	 * @param x raiz del subArbol donde se piensa insertar el nuevo nodo
	 * @param pKey la llave del nuevo nodo
	 * @param pVal valor que va a tener el nuevo nodo
	 * @return devuelve la referencia del nodo insertado a su padre correspondiente
	 */
	private Node put(Node x, K pKey, V pVal){
		if(x == null) { otherKeys.add(pKey); return new Node(pKey, pVal, 1, RED);}
		int cmp = pKey.compareTo(x.key);
		if(cmp < 0) x.left = put(x.left, pKey, pVal);
		else if(cmp > 0) x.right = put(x.right, pKey, pVal);
		else x.val = pVal;

		if(!isRed(x.left) && isRed(x.right)) x = rotateLeft(x);
		if(isRed(x.left) && isRed(x.left.left)) x = rotateRight(x);
		if(isRed(x.left) && isRed(x.right)) changeColors(x);
		x.N = size(x.left) + size(x.right) + 1;
		return x;
	}

	/**
	 * Retorna la altura del �rbol definida como la altura de la rama m�s alta 
	 * (aquella que tenga mayor n�mero de enlaces desde la ra�z a una hoja).
	 */
	public int height() {
		return getTreeHeight(root);
	}

	/**
	 * Retorna la llave m�s peque�a del �rbol. Valor null si �rbol vac�o
	 */
	public K min() {
		return min(root);
	}

	/**
	 * Retorna el valor m�nimo desde un nodo por par�metro
	 * @param n Nodo desde donde se desea buscar el minimo.
	 * @return El valor m�nimo desde el nodo
	 */
	private K min(Node n) {
		if(n.left == null) return n.key;
		return min(n.left);
	}

	/**
	 * Retorna la llave m�s grande del �rbol. Valor null si �rbol vac�o
	 */
	public K max() {
		return max(root);
	}

	/**
	 * Retorna el valor m�ximo desde un nodo por par�metro.
	 * @param n Nodo desde donde se desea buscar el m�ximo.
	 * @return El valor m�ximo desde el nodo.
	 */
	private K max(Node n) {
		if(n.right == null) return n.key;
		return min(n.right);
	}

	/**
	 * M�todo que rectifca que el arbol este balanceado y devuelve la respuesta. 
	 * @return verdadero o falso seg�n si el arbol esta balanceado.
	 */
	private boolean isBalanced() {
		if(root == null)
			return true;
		return isBalanced(root)[1] == 1;
	}
	
	/**
	 * M�todo que cuenta la longitud del cada una de las ramas del subArbol que comienza
	 * en el nodo pasado por parametro y notifica su altura y si esta balanceado.
	 * @param start nodo desde el cual comienza el subArbol. start != null
	 * @return un arreglo donde se evidencia la altura del arbol y si esta balanceado
	 */
	private int[] isBalanced(Node start) {
		if(start.left == null && start.right == null) {
			if(start.color) {
				int answer[] = {0,1};
				return answer;
			}
			int answer[] = {1,1};
			return answer;
		}
		else if(start.left != null && start.right != null) {
			int left[] = isBalanced(start.left);
			int right[] = isBalanced(start.right);
			if(left[1] == 1 && right[1] == 1) {
				if(left[0] == right[0]) {
					int answer[] = {0,0};
					if(start.color) {
						answer[0] = (left[0]);
						answer[1] = 1;
					}
					else {
						answer[0] = (left[0])+1;
						answer[1] = 1;
					}
					return answer;
				}
			}
			int answer[] = {0,0};
			return answer;
		}
		else if(start.left != null && start.right == null) {
			if(isBalanced(start.left)[0] == 0) {
				int answer[] = {1,1};
				return answer;
			}
			int answer[] = {1,0};
			return answer;
		}
		int answer[] = {0,0};
		return answer;
	}

	/**
	 * M�todo que rectifca que el arbol este balanceado y devuelve la respuesta. 
	 */
	public boolean check() {
		if(root == null)
			return true;
		return isBalanced(root)[1] == 1;
	}

	/**
	 * Iterador de las llaves
	 */
	public Iterator<K> keys() {
		return otherKeys.iterator();
	}

	/**
	 * M�todo que agrega las llaves/valores de manera ascendente en el arreglo keys/valores
	 * @param start nodo desde el que comienza para determinar el camino en el arbol. start != null
	 * @param lesstn minimo del rango. lesstn != null
	 * @param moretn maximo del rango. moretn != null
	 * @param key notifica al m�todo si se estan agregando llaves o valores.
	 */
	private void keysValuesInRange(Node start,K lesstn, K moretn, boolean key) {
		if(start == null)
			return;
		if(start.key.compareTo(lesstn) >= 0 && start.key.compareTo(moretn) <= 0) {
			if(key) {
				keysValuesInRange(start.left,lesstn,moretn,key);
				keys.add(start.key);
				keysValuesInRange(start.right, lesstn, moretn,key);
			}
			else {
				keysValuesInRange(start.left,lesstn,moretn,key);
				values.add(start.val);
				keysValuesInRange(start.right, lesstn, moretn,key);
			}
		}
		if(start.key.compareTo(moretn) > 0) 
			keysValuesInRange(start.left,lesstn,moretn,key);
		if(start.key.compareTo(lesstn) < 0)
			keysValuesInRange(start.right, lesstn, moretn,key);
	}

	/**
	 * M�todo que devuelve las llaves que se encuentran en un rango especifico
	 * @param lesstn minimo del rango. lesstn != null
	 * @param moretn maximo del rango. moretn != null
	 * @return retorna las lalves en un rango especifico.
	 */
	private ORArray<V> valuesInRangeArray(K lesstn, K moretn){
		if (root == null)
			return null;
		keys = new ORArray<K>();
		values = new ORArray<V>();
		keysValuesInRange(root,lesstn,moretn, false);
		return values;
	}

	/**
	 * Retorna todos los valores V en el �rbol que est�n asociados al rango de llaves dado.
	 * Por eficiencia, debe intentarse No recorrer todo el �rbol.
	 */
	public Iterator<V> valuesInRange(K init, K end) {
		return valuesInRangeArray(init, end).iterator();
	}


	/**
	 * M�todo que devuelve las llaves que se encuentran en un rango especifico
	 * @param lesstn minimo del rango. lesstn != null
	 * @param moretn maximo del rango. moretn != null
	 * @param ascending notifica al m�todo como se deben agregar las llaves.
	 * @return retorna las lalves en un rango especifico de manera ascendente o descendente.
	 */
	private ORArray<K> keysInRangeArray(K lesstn, K moretn){
		if (root == null)
			return null;
		keys = new ORArray<K>();
		values = new ORArray<V>();
		keysValuesInRange(root,lesstn,moretn,true);
		return keys;
	}	

	/**
	 * Retorna todas las llaves K en el �rbol que se encuentran en el rango de llaves dado.
	 * Por eficiencia, debe intentarse No recorrer todo el �rbol.
	 */
	public Iterator<K> keysInRange(K init, K end) {
		return keysInRangeArray(init, end).iterator();
	}

	/**
	 * M�todo que regresa falso o verdadero si el nodo es un nodo de tipo 2
	 * @param x el nodo que se quiere rectificar. 
	 * @return falso o verdadero si el nodo es de tipo 2
	 */
	private boolean is2Node(Node x){
		if(x == null){
			int a = 2 / 0;
		}
		if((x.right != null && x.right.color) || (x.left != null && x.left.color)) return false;
		if(x.color) return false;
		if(!x.color) return true;
		return (x.right == null && x.left == null);
	}


	/**
	 * M�todo que informa si el nodo pasado por parametro es rojo
	 * @param x nodo al cual se va a rectificar si es rojo
	 * @return true o false seg�n si el nodo es rojo
	 */
	private boolean isRed(Node x){
		if(x == null) return false;
		return x.color;
	}

	/**
	 * M�todo que cambia los colores del nodo pasado por parametro a rojo
	 * y los hijos los convierte a color negro
	 * @param x nodo al cual se le van a cambiar los colores y el de los hijos. x != null
	 * post: se han cambiado los colores del nodo pasado por paramtero y sus hijos
	 */
	private void changeColors(Node x){
		x.color = RED;
		x.left.color = BLACK;
		x.right.color = BLACK;
	}


	/**
	 * M�todo que cambia la raiz del arbol de tal manera que sea un 3 nodo si no lo es. 
	 * @param x el nodo que representa la raiz.
	 * @return la nueva raiz convertida a un 3 nodo.
	 */
	private Node changeRoot(Node x){
		if(x == null) return null;
		if(!is2Node(x.left)) return x;
		if(is2Node(x.right)){
			x.left.color = RED;
			x.right.color = RED;
			return x;
		}
		Node t1 = x.right.left;
		Node t2 = t1.left;
		x.left.color = RED;
		x.right.left = t1.right;
		t1.right = x.right;
		x.right = t2;
		t1.left = x;
		t1.right.color = BLACK;
		t1.left.color = BLACK;
		return t1;

	}

	/**
	 * M�todo que borra el elemento minimo del subArbol que comienza en el nodo pasado por parametro.
	 * @param x nodo desde donde comienza el subArbol. x != null 
	 * @return la nueva referencia al nodo que se ubica a la izquierda del nodo pasado por parametro.
	 */
	private Node deleteMin(Node x){
		if(x == null) return null;
		//System.out.println(x.key);
		if(x.left == null) return null;
		if(!is2Node(x.left)) {
			x.left = deleteMin(x.left);
			if(!isRed(x.left) && isRed(x.right)) x = rotateLeft(x);
			if(isRed(x.left) && isRed(x.left.left)) x = rotateRight(x);
			if(isRed(x.left) && isRed(x.right)) changeColors(x);
			x.N = size(x.left) + size(x.right) + 1;
			return x;
		}

		if(is2Node(x.left) && !is2Node(x.right)){
			Node t1 = x.right.left;
			Node t2 = t1.left;
			x.left.color = RED;
			x.right.left = t1.right;
			t1.right = x.right;
			x.right = t2;
			t1.left = x;
			t1.color = x.color;
			t1.right.color = BLACK;
			t1.left.color = BLACK;
			t1.left = deleteMin(t1.left);
			if(!isRed(t1.left) && isRed(t1.right)) t1 = rotateLeft(t1);
			if(isRed(t1.left) && isRed(t1.left.left)) t1 = rotateRight(t1);
			if(isRed(t1.left) && isRed(t1.right)) changeColors(t1);
			t1.N = size(t1.left) + size(t1.right) + 1;
			return t1;
		}

		x.color = BLACK;
		x.left.color = RED;
		x.right.color = RED;
		x.left = deleteMin(x.left);
		if(!isRed(x.left) && isRed(x.right)) x = rotateLeft(x);
		if(isRed(x.left) && isRed(x.left.left)) x = rotateRight(x);
		if(isRed(x.left) && isRed(x.right)) changeColors(x);
		x.N = size(x.left) + size(x.right) + 1;
		return x;

	}

	/**
	 * M�todo que el valor minimo del arbol.
	 * post: se ha borrado el valor minimo del arbol y el arbol ha quedado balanceado.
	 */
	public void deleteMin(){
		System.out.println(root.val);
		root = changeRoot(root);
		System.out.println(root.val);
		root = deleteMin(root);
		System.out.println("to the left of the root is: " + root.left.key);
		root.color = BLACK;
	}

	public ORArray<V> ValuesInRangeInt(K lesstn, K moretn) {
		Comparator<K> order = (Comparator<K>) new IntComparator();
		ORArray<V> answer = new ORArray<V>();
		otherKeys.sort(order);
		int hi = otherKeys.getSize()-1;
		int lo = 0;
		int mid = lo + (hi - lo)/2;
		while(lo <= hi) {
			if(otherKeys.getElement(mid).compareTo(lesstn)>= 0 && (mid == 0 || otherKeys.getElement(mid-1).compareTo(lesstn) < 0))
				break;
			else if(otherKeys.getElement(mid).compareTo(lesstn) < 0)
				lo = mid + 1;
			else
				hi = mid - 1;
			mid = lo + (hi - lo)/2;
		}
		for(int i = mid; i < otherKeys.getSize(); i++) {
			if(otherKeys.getElement(i).compareTo(moretn) > 0)
				break;
			answer.add(get(otherKeys.getElement(i)));
		}
		return answer;
	}
	
	
	class IntComparator implements Comparator<Integer> {

	    @Override
	    public int compare(Integer v1, Integer v2) {
	        return v1 < v2 ? -1 : v1 > v2 ? +1 : 0;
	    }
	}
}
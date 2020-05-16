package model.data_structures;

/**
 * Clase auxiliar Pair
 * @param <K> tipo de valor �nico del pair
 */
public class Pair<K extends Comparable<K>, V extends Comparable<V>> implements Comparable<Pair<K,V>>{

	/**
	 * Primer valor del pair
	 */
	public K first;

	/**
	 * Segundo valor del pair
	 */
	public V second;

	/**
	 * M�todo constructor del pair
	 * @param pKey Valor del primer valor del pair
	 * @param pValue Valor del segundo valor del pair
	 */
	public Pair(K pFirst, V pSecond) {
		first = pFirst;
		second = pSecond;
	}

	public int compareTo(Pair<K,V> o) {
		int uno = first.compareTo(o.first);
		int dos = second.compareTo(o.second);
		if(uno > 0)
			return 1;
		if(uno < 0)
			return -1;
		if(dos > 0)
			return 1;
		if(dos < 0)
			return -1;
		return 0;
	}
	
}

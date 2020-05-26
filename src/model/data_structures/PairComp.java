package model.data_structures;

public class PairComp<K extends Comparable<K>,V> implements Comparable {

	
	private K first;
	private V second;
	
	public PairComp(K first, V second) {
		this.first = first;
		this.second = second;
	}
	
	
	public K getFirst() {
		return first;
	}	
	
	public V getSecond() {
		return second;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		PairComp<K,V> co = (PairComp<K,V>)o;
		return first.compareTo(co.getFirst());
	}

}

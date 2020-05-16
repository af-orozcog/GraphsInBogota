package model.data_structures;

public class Edge<A extends Comparable<A>> implements Comparable<Edge<A>> {

	private int v; 
	
	private int w; 
	
	private  A info;
	
	public Edge(int pV, int pW, A pInfo) {
		v = pV;
		w = pW;
		info = pInfo;
	}
	
	public A getInfo() {
		return info;
	}
	
	public int either() {
		return v;
	}
	
	
	public int other(int vertex) {
		if(vertex == v) return w;
		else if(vertex == w) return v;
		return -1;
	}
	
	public void setInfo(A pInfo) {
		info = pInfo;
	}
	
	public int compareTo(Edge<A> that)
	{
		return this.info.compareTo(that.info);
	}

	public int comparador(Edge<A> that) {
		if(v == that.v && w == that.w)
			return 0;
		if(v == that.w && w == that.v)
			return 0;
		return -1;
	}
	
	public void swapEdges() {
		int temp = v;
		v = w;
		w  = temp;
	}
}
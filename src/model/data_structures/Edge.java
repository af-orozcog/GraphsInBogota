package model.data_structures;

public class Edge<A extends Comparable<A>> implements Comparable<Edge<A>> {

	/**
	 * the id of one of the vertex
	 */
	private int v; 
	
	/**
	 * the id of one of the vertex
	 */
	private int w; 
	
	/**
	 * the information that will be stored in the edge
	 */
	private  A info;
	
	/**
	 * Constructor method with take the ids of the end points and the info of the edge will store
	 * @param pV one of the end points of the edge
	 * @param pW one of the end points of the edge
	 * @param pInfo the info that will be stored in the edge
	 */
	public Edge(int pV, int pW, A pInfo) {
		v = pV;
		w = pW;
		info = pInfo;
	}
	
	/**
	 * returns the info of the edge
	 * @return the info of the edge
	 */
	public A getInfo() {
		return info;
	}
	
	/**
	 * return one of the end points of the edge
	 * @return one of the end points of the edge
	 */
	public int either() {
		return v;
	}
	
	/**
	 * given one of the endpoints return the other end point of the edge
	 * @param vertex the id of one of the endpoints
	 * @return the other end point of the edge
	 */
	public int other(int vertex) {
		if(vertex == v) return w;
		else if(vertex == w) return v;
		return -1;
	}
	
	/**
	 * methos to set the info of the edge
	 * @param pInfo
	 */
	public void setInfo(A pInfo) {
		info = pInfo;
	}
	
	/**
	 * Method to compare two edges
	 */
	public int compareTo(Edge<A> that)
	{
		return this.info.compareTo(that.info);
	}

	/**
	 * custom comparator for the edge compares based on end points
	 * @param that the other edge
	 * @return 0 , -1 if they are equal or no
	 */
	public int comparador(Edge<A> that) {
		if(v == that.v && w == that.w)
			return 0;
		if(v == that.w && w == that.v)
			return 0;
		return -1;
	}
	
	/**
	 * method to swap the end points
	 */
	public void swapEdges() {
		int temp = v;
		v = w;
		w  = temp;
	}
}
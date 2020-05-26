package model.data_structures;

import java.util.Iterator;

public class Dijkstra<K extends Comparable<K>,V extends IInfoVertex> {
	/**
	 * 
	 */
	private Integer[] dad;
	
	/**
	 * 
	 */
	private Double[] distTo;
	
	/**
	 * 
	 */
	private MinPQ<PairComp<Double,Integer>> pq;

	
	Graph<K,V,Double> graph;
	
	/**
	 * 
	 * @param G
	 * @param s
	 * @param option
	 */
	public Dijkstra(Graph<K,V,Double> G,K s, boolean option)
	{
		graph = G;
		dad = new Integer[G.V()];
		distTo = new Double[G.V()];
		pq = new MinPQ<PairComp<Double,Integer>>(G.V());
		for (int v = 0; v < G.V(); v++)
			distTo[v] = Double.POSITIVE_INFINITY;
		distTo[G.translate(s)] = 0.0;
		dad[G.translate(s)] = -1;
		pq.insert(new PairComp<Double,Integer>(0.0,G.translate(s)));
		while(pq.size() != 0) {
			PairComp<Double,Integer> front = pq.delMin();
			if(distTo[front.getSecond()] < front.getFirst()) continue;
			Iterator<Edge<Double>> it = G.edgesTo(G.translateInverse(front.getSecond()));
			while(it.hasNext()){
				Edge<Double> va = it.next();
				int ot = va.other(front.getSecond());
				Double val = 0.0;
				if(option) 
					val = G.getInfoVertex(G.translateInverse(ot)).getInfo2();
				else
					val = va.getInfo();
				if(distTo[ot] > val) {
					distTo[ot] = val;
					dad[ot] = front.getSecond();
				}
			}
		}
	}
	
	public Double distance(K to) {
		return distTo[graph.translate(to)];
	}
	
	public ORArray<Edge<Double>> journey(K to){
		if(distTo[graph.translate(to)] == Double.POSITIVE_INFINITY) return null;
		ORArray<Edge<Double>> ans = new ORArray<Edge<Double>>();
		int rev = graph.translate(to);
		while(dad[rev] != -1) {
			ans.add(graph.getEdge(graph.translateInverse(rev), graph.translateInverse(dad[rev])));
			rev = dad[rev];
		}
		return ans;
	}
}

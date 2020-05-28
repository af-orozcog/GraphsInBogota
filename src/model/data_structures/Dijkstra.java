package model.data_structures;

import java.util.Iterator;

import model.vo.VertexInfo;

public class Dijkstra {
	
	/**
	 * Array to remember the node that added the specific node minimum distance
	 */
	private Integer[] dad;
	
	/**
	 * Array to remeber the minimum distance up to that node
	 */
	private Double[] distTo;
	
	/**
	 * a copy of the graph passed by parameter
	 */
	Graph<Integer,VertexInfo,Double> graph;
	
	/**
	 * Constructor method
	 * it initializes dist to and dad to the given values needed to call other functions
	 * notice this method can be used for multi-source Dijkstra
	 * @param G
	 * @param s
	 * @param option
	 */
	public Dijkstra(Graph<Integer,VertexInfo,Double> G,ORArray<Integer> s, boolean option)
	{
		MinPQ<PairComp<Double,Integer>> pq;
		graph = G;
		dad = new Integer[G.V()];
		distTo = new Double[G.V()];
		pq = new MinPQ<PairComp<Double,Integer>>(G.V());
		for (int v = 0; v < G.V(); v++)
			distTo[v] = Double.POSITIVE_INFINITY;
		for(Integer va: s) {
			distTo[G.translate(va)] = 0.0;
			dad[G.translate(va)] = -1;
			pq.insert(new PairComp<Double,Integer>(0.0,G.translate(va)));
			System.out.println(G.translate(va));
		}
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
				//System.out.println("cual es la sapa distancia :v "+ val + " wuttt " + graph.translateInverse(ot) + " wtf is ot" + ot);
				if(distTo[ot] > val + distTo[front.getSecond()]) {
					distTo[ot] = val + distTo[front.getSecond()];
					dad[ot] = front.getSecond();
					pq.insert(new PairComp<Double,Integer>(val,ot));
				}
			}
		}
	}
	
	/**
	 * Method that returns the minimum distance from a source to the node passed by parameter
	 * @param to the id of the node we want to reach
	 * @return the minimum distance from a source to the node passed by parameter
	 */
	public Double distance(Integer to) {
		return distTo[graph.translate(to)];
	}
	
	/**
	 * method that return the route from the source node to a give node
	 * @param to the id of the node we want to reach
	 * @return the route from the source node to a give node
	 */
	public ORArray<Edge<Double>> journey(Integer to){
		if(distTo[graph.translate(to)] == Double.POSITIVE_INFINITY) return null;
		ORArray<Edge<Double>> ans = new ORArray<Edge<Double>>();
		int rev = graph.translate(to);
		while(dad[rev] != -1) {
			ans.add(graph.getEdge(graph.translateInverse(rev), graph.translateInverse(dad[rev])));
			rev = dad[rev];
		}
		return ans;
	}
	
	/**
	 * Method that generates a graph of the shortest routes froma  set of nodes
	 * @return a graph of the shortest routes from a set of nodes
	 */
	public Graph<Integer,VertexInfo,Double> generateGraph(){
		Graph<Integer,VertexInfo,Double> ans = new Graph<Integer,VertexInfo,Double>();
		for(int i = 0; i < dad.length;++i) {
			if(distTo[i] == Double.POSITIVE_INFINITY || dad[i] == -1) continue;
			ans.addVertex(graph.translateInverse(i), graph.getInfoVertex(graph.translateInverse(i)));
			ans.addVertex(graph.translateInverse(dad[i]), graph.getInfoVertex(graph.translateInverse(dad[i])));
			ans.addEdge(graph.translateInverse(i), graph.translateInverse(dad[i]), graph.getInfoArc(graph.translateInverse(i), graph.translateInverse(dad[i])));
		}
		return ans;
	}
	
}

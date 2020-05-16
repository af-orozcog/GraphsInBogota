package model.data_structures;

import java.util.Iterator;

public class Dijkstra<K extends Comparable<K>,V extends Comparable<V>> {
	private Edge<Double>[] edgeTo;
	
	private double[] distTo;
	
	private IndexMinPQ<Double> pq;
	
	private HashTableSC<Pair<Integer,Integer>,Integer> visited;
	
	private Graph<K,V,Double> grafo;
	
	public Dijkstra(Graph<K,V,Double> G, K s)
	{
		visited = new HashTableSC<Pair<Integer,Integer>,Integer>(500);
		edgeTo = new Edge[G.V()];
		distTo = new double[G.V()];
		grafo = G;
		pq = new IndexMinPQ<Double>(G.V());
		for (int v = 0; v < G.V(); v++)
			distTo[v] = Double.POSITIVE_INFINITY;
		distTo[G.translate(s)] = 0.0;
		pq.insert(G.translate(s), 0.0);
		while (!pq.isEmpty())
			relax(G, pq.delMin());
	}
	private void relax(Graph G, int v)
	{
		Iterator<Edge<Double>> ite = G.edgesTo(G.translateInverse(v));
		while(ite.hasNext())
		{
			Edge<Double> e = ite.next();
			int w = e.other(v);
			int first = v;
			int second = w;
			if(first < second) {
				int temp = w;
				second = first;
				first = temp;
			}
			if(visited.contains(new Pair<Integer,Integer>(first,second)))
				continue;
			if (distTo[w] > distTo[v] + e.getInfo())
			{
				distTo[w] = distTo[v] + e.getInfo();
				if(e.either() != v)
					e.swapEdges();
				edgeTo[w] = e;
				if (pq.contains(w)) pq.changeKey(w, distTo[w]);
				else pq.insert(w, distTo[w]);
				visited.put(new Pair<Integer,Integer>(first,second), 1);
			}
		}
	}
	public double distTo(K v) {
        return distTo[grafo.translate(v)];
    }
	
	public boolean hasPathTo(K v) {
        return distTo[grafo.translate(v)] < Double.POSITIVE_INFINITY;
    }
	
	public Iterable<Edge<Double>> pathTo(K v) {
        if (!hasPathTo(v)) return null;
        Stack<Edge<Double>> path = new Stack<Edge<Double>>();
        for (Edge<Double> e = edgeTo[grafo.translate(v)]; e != null; e = edgeTo[e.either()]) {
            path.push(e);
        }
        return path;
    }
	
}

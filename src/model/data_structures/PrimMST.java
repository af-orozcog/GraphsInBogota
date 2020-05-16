package model.data_structures;

import java.util.Iterator;
import model.data_structures.Edge;
import model.data_structures.IndexMinPQ;

public class PrimMST<K extends Comparable<K>,V extends Comparable<V>> {
	private Edge<Double>[] edgeTo; // shortest edge from tree vertex
	private double[] distTo; // distTo[w] = edgeTo[w].weight()
	private boolean[] marked; // true if v on tree
	private IndexMinPQ<Double> pq; // eligible crossing edges
	public PrimMST(Graph<K,V,Double> G)
	{
		edgeTo = new Edge[G.V()];
		distTo = new double[G.V()];
		marked = new boolean[G.V()];
		for (int v = 0; v < G.V(); v++)
			distTo[v] = Double.POSITIVE_INFINITY;
		pq = new IndexMinPQ<Double>(G.V());
		distTo[0] = 0.0;
		pq.insert(0, 0.0); // Initialize pq with 0, weight 0.
		while (!pq.isEmpty())
			visit(G, pq.delMin()); // Add closest vertex to tree.
	}
	private void visit(Graph<K,V,Double> G, int v)
	{ // Add v to tree; update data structures.
		marked[v] = true;
		Iterator<Edge<Double>> ite = G.edgesTo(G.translateInverse(v));
		while(ite.hasNext())
		{
			Edge<Double> e = ite.next();
			int w = e.other(v);
			if (marked[w]) continue; // v-w is ineligible.
			if (e.getInfo() < distTo[w])
			{ // Edge e is new best connection from tree to w.
				System.out.println("camino elegido de: " + v + " a " + w + " con peso: " + e.getInfo());
				edgeTo[w] = e;
				distTo[w] = e.getInfo();
				if (pq.contains(w)) pq.changeKey(w, distTo[w]);
				else pq.insert(w, distTo[w]);
			}
		}
	}
	public Iterable<Edge<Double>> edges() {
        Queue<Edge<Double>> mst = new Queue<Edge<Double>>();
        for (int v = 0; v < edgeTo.length; v++) {
            Edge<Double> e = edgeTo[v];
            if (e != null) {
                mst.enqueue(e);
            }
        }
        return mst;
    }
	public double weight() {
        double weight = 0.0;
        for (Edge<Double> e : edges())
            weight += e.getInfo();
        return weight;
    }
}

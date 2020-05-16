package model.data_structures;

public class KruskalMST<K extends Comparable<K>,V extends Comparable<V>> {
	private double weight;
	private Queue<Edge<Double>> mst;
	public KruskalMST(Graph<K,V,Double> G)
	{
		weight = 0;
		MinPQ<Edge<Double>> pq = new MinPQ<Edge<Double>>();
        for (Edge<Double> e : G.edges()) {
            pq.insert(e);
        }

        // run greedy algorithm
        mst = new Queue<Edge<Double>>();
        UF uf = new UF(G.V());
        while (!pq.isEmpty() && mst.size() < G.V() - 1) {
            Edge<Double> e = pq.delMin();
            int v = e.either();
            int w = e.other(v);
            if (!uf.connected(v, w)) { // v-w does not create a cycle
                uf.union(v, w);  // merge v and w components
                mst.enqueue(e);  // add edge e to mst
                weight += e.getInfo();
            }
        }
	}
	public Iterable<Edge<Double>> edges()
	{ return mst; }
	public double weight() {return weight;}
}

package model.data_structures;
import java.util.Iterator;

import model.data_structures.Graph;

public class BFSPaths<K extends Comparable<K>,V extends Comparable<V>,A extends Comparable<A>> {
	private boolean[] marked; // Is a shortest path to this vertex known?
	private int[] edgeTo; // last vertex on known path to this vertex
	private final K s; // source
	private Graph<K,V,A> grafo;
	public BFSPaths(Graph<K,V,A> G, K s)
	{
		marked = new boolean[G.V()];
		for(int i = 0; i < marked.length; i++) {
			marked[i] = false;
		}
		edgeTo = new int[G.V()];
		this.s = s;
		grafo = G;
		bfs(G, s);
	}
	private void bfs(Graph<K,V,A> G, K s)
	{
		Queue<Integer> queue = new Queue<Integer>();
		marked[G.translate(s)] = true; // Mark the source
		queue.enqueue(G.translate(s)); // and put it on the queue.
		while (!queue.isEmpty())
		{
			int v = queue.dequeue(); // Remove next vertex from the queue.
			Iterator<K> nextTo = G.adj(G.translateInverse(v));
			while(nextTo.hasNext()) {
				K w = nextTo.next();
				if (!marked[G.translate(w)]) // For every unmarked adjacent vertex,
				{
					//System.out.println("se acaba de marcar: " + G.translate(w));
					edgeTo[G.translate(w)] = v; // save last edge on a shortest path,
					marked[G.translate(w)] = true; // mark it because path is known,
					queue.enqueue(G.translate(w)); // and add it to the queue.
				}
			}
		}
	}
	public boolean hasPathTo(K v)
	{ return marked[grafo.translate(v)]; }
	
	public Iterable<K> pathTo(K v)
	{
		if (!hasPathTo(v)) return null;
		Stack<K> path = new Stack<K>();
		for (int x = grafo.translate(v); x != grafo.translate(s); x = edgeTo[x])
			path.push(grafo.translateInverse(x));
		path.push(s);
		return path;
	}
}

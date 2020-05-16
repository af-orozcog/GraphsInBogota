package model.data_structures;

import java.util.Iterator;

public class ConnectedComponents<K extends Comparable<K>,V extends Comparable<V>,A extends Comparable<A>> {
	private boolean[] marked;
	private int[] id;
	private int count;
	private Graph<K,V,A> grafo;
	public ConnectedComponents(Graph<K,V,A> G)
	{
		marked = new boolean[G.V()];
		id = new int[G.V()];
		grafo = G;
		count = 0;
		for (int s = 0; s < G.V(); s++) {
			if (!marked[s])
			{
				dfs(G, G.translateInverse(s));
				count++;
			}
		}
	}
	private void dfs(Graph<K,V,A> G, K v)
	{
		//System.out.println("aqui al menos?");
		marked[G.translate(v)] = true;
		id[G.translate(v)] = count;
		Iterator<K> nextTo = G.adj(v);
		while(nextTo.hasNext()) {
			//System.out.println("nunca entra aqui");
			K w = nextTo.next();
			if (!marked[G.translate(w)])
				dfs(G, w);
		}
		
	}
	public boolean connected(int v, int w)
	{ return id[v] == id[w]; }
	public int id(int v)
	{ return id[v]; }
	public int count()
	{ return count; }
	
	public Queue<Edge<A>> biggestConnectedComponent(){
		Queue<Edge<A>> answer = new Queue<Edge<A>>();
		int biggest = 0;
		int which = -1;
		for(int i = 0; i < count; i++) {
			int counter = 0;
			for(int j = 0; j < id.length;j++) {
				if(id[j] == i) {
					counter++;
				}
			}
			if(counter > biggest) {
				biggest = counter;
				which = i;
			}
		}
		for(int i = 0; i < id.length; i++) {
			for(int j = i +1; j < id.length; j++) {
				if(id[i] == which && connected(i,j) && grafo.getInfoArc(grafo.translateInverse(i), grafo.translateInverse(j)) != null)
					answer.enqueue(grafo.getEdge(grafo.translateInverse(i), grafo.translateInverse(j)));
			}
		}
		return answer;
	}
}

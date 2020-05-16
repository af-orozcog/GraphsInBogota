package model.data_structures;
import java.util.Iterator;

import model.data_structures.ORArray;
import model.data_structures.Queue;
import model.data_structures.Edge;
public class Graph <K extends Comparable<K>,V extends Comparable<V>,A extends Comparable<A>> {

	private int V; // number of vertices
	
	private int E; // number of edges
	
	private ORArray<Queue<Edge<A>>> adj; // adjacency lists
	
	private HashTableSC<K, Integer> st;
	
	private HashTableSC<Integer, K> stReverse; 
	
	private ORArray<V> info;
	
	private IndexMaxPQ<V> ordered;
	
	public Graph()
	{
		this.V = 0;
		this.E = 0;
		adj = new ORArray<Queue<Edge<A>>>();
		st = new HashTableSC<K,Integer>(200);
		stReverse = new HashTableSC<Integer, K>(200);
		info = new ORArray<V>();
	}
	
	public int V() { return V; }
	public int E() { return E; }
	
	public void addEdge(K idVertexIni, K idVertexFin, A infoArc)
	{
		if(st.get(idVertexIni) == null) return;
		if(st.get(idVertexFin) == null) return;
		Queue<Edge<A>> valores = adj.getElement(st.get(idVertexIni));
		Edge<A> toAdd = new Edge<A>(st.get(idVertexIni),st.get(idVertexFin),infoArc);
		for(Edge<A> edg: valores) {
			if(edg.comparador(toAdd) == 0)
				return;
		}
		int v = toAdd.either(), w = toAdd.other(v);
		adj.getElement(v).enqueue(toAdd);
		adj.getElement(w).enqueue(toAdd);
		E++;
	}
	
	public void addVertex(K idVertex, V infoVertex) {
		if(st.get(idVertex) != null)
			return;
		st.put(idVertex, V);
		stReverse.put(V, idVertex);
		V++;
		info.add(infoVertex);
		adj.add(new Queue<Edge<A>>());
		
	}
	
	public V getInfoVertex(K idVertex) {
		if(st.get(idVertex) == null)
			return null;
		return info.getElement(st.get(idVertex));
	}
	
	public void setInfoVertex(K idVertex, V infoVertex) {
		if(st.get(idVertex) == null) return;
		info.addPos(infoVertex, st.get(idVertex));
	}
	
	public A getInfoArc(K idVertexIni, K idVertexFin){
		if(st.get(idVertexIni) == null) 
			return null;
		if(st.get(idVertexFin) == null)
			return null;
		Queue<Edge<A>> valores = adj.getElement(st.get(idVertexIni));
		A answer = null;
		for(Edge<A> edg : valores) {
			int one = edg.either();
			if(one == st.get(idVertexFin)) {
				answer = edg.getInfo();
				break;
			}
			else if(edg.other(one) == st.get(idVertexFin)) {
				answer = edg.getInfo();
				break;
			}
		}
		return answer;
	}
	
	public void setInfoArc(K idVertexIni, K idVertexFin,A infoArc) {
		if(st.get(idVertexIni) == null)
			return;
		if(st.get(idVertexFin) == null)
			return;
		Queue<Edge<A>> valores = adj.getElement(st.get(idVertexIni));
		for(Edge<A> edg : valores) {
			int one = edg.either();
			if(one == st.get(idVertexFin)) {
				edg.setInfo(infoArc);
				break;
			}
			else if(edg.other(one) == st.get(idVertexFin)) {
				edg.setInfo(infoArc);
				break;
			}
		}
	}
	
	public Iterator<K> adj(K v)
	{ 
		ORArray<K> answer = new ORArray<K>();
		if(st.get(v) == null)
			return null;
		Queue<Edge<A>> valores = adj.getElement(st.get(v));
		for(Edge<A> edg : valores) {
			int one = edg.either();
			if(stReverse.get(one).compareTo(v) != 0)
				answer.add(stReverse.get(one));
			else
				answer.add(stReverse.get(edg.other(one)));
		}
		return answer.iterator();
	}
	
	public Integer translate(K value) {
		return st.get(value);
	} 
	public K translateInverse(Integer value) {
		return stReverse.get(value);
	}
	
	public Iterator<K> vertices(){
		return st.keys();
	}
	
	public Iterator<Edge<A>> edgesTo(K value){
		int val = st.get(value);
		return adj.getElement(val).iterator();
	}
	
	public Iterable<Edge<A>> edges() {
        Queue<Edge<A>> list = new Queue<Edge<A>>();
        for (int v = 0; v < V; v++) {
            int selfLoops = 0;
            Queue<Edge<A>> valores = adj.getElement(v);
    		for(Edge<A> e : valores) { 
                if (e.other(v) > v) {
                    list.enqueue(e);
                }
                // add only one copy of each self loop (self loops will be consecutive)
                else if (e.other(v) == v) {
                    if (selfLoops % 2 == 0) list.enqueue(e);
                    selfLoops++;
                }
            }
        }
        return list;
    }
	
	public Edge<A> getEdge(K idVertexIni, K idVertexFin){
		if(st.get(idVertexIni) == null)
			return null;
		if(st.get(idVertexFin) == null)
			return null;
		Edge<A> answer = null;
		Queue<Edge<A>> valores = adj.getElement(st.get(idVertexIni));
		for(Edge<A> edg : valores) {
			int one = edg.either();
			if(one == st.get(idVertexFin)) {
				answer = edg;
				break;
			}
			else if(edg.other(one) == st.get(idVertexFin)) {
				answer = edg;
				break;
			}
		}
		return answer;
	}
	
	public void createPQ(){
		ordered = new IndexMaxPQ(info.getSize());
		for(int i = 0; i < info.getSize(); i++){
			ordered.insert(i, info.getElement(i));
		}
	}
	
	public IndexMaxPQ<V> getPQ(){
		return ordered;
	}
	
	
}

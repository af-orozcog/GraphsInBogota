package model.data_structures;
import java.util.Iterator;

import model.data_structures.ORArray;
import model.data_structures.Queue;
import model.vo.VertexInfo;
import model.data_structures.Edge;
public class Graph <K extends Comparable<K>,V,A extends Comparable<A>> {

	@Override
	public String toString() {
		return "Graph [V=" + V + ", E=" + E + ", adj=" + adj + ", st=" + st + ", stReverse=" + stReverse + ", info="
				+ info + "]";
	}

	/**
	 * Number of vertices in the graph
	 */
	private int V; 
	
	/**
	 * number of edges in the graph
	 */
	private int E; // number of edges
	
	/**
	 * adjacency list for the graph edges
	 */
	private ORArray<Queue<Edge<A>>> adj; // adjacency lists
	
	/**
	 * hash map to transalte between possible keys used for the nodes
	 */
	private HashTableSC<K, Integer> st;
	
	/**
	 * hash map to transalte between possible keys used for the nodes
	 */
	private HashTableSC<Integer, K> stReverse; 
	
	/**
	 * Info of the each node 
	 */
	private ORArray<V> info;
	
	
	/**
	 * Constructor of the graph
	 */
	public Graph()
	{
		this.V = 0;
		this.E = 0;
		adj = new ORArray<Queue<Edge<A>>>();
		st = new HashTableSC<K,Integer>(200);
		stReverse = new HashTableSC<Integer, K>(200);
		info = new ORArray<V>();
	}
	
	/**
	 * returns the number of nodes in the graph
	 * @return the number of nodes in the graph
	 */
	public int V() { return V; }
	
	/**
	 * returns the number of edges in the graph
	 * @return the number of edges in the graph
	 */
	public int E() { return E; }
	
	/**
	 * Method that adds an edge between two nodes already in the graph
	 * @param idVertexIni one of the nodes that will be connected
	 * @param idVertexFin one of the nodes that will be connected
	 * @param infoArc the information that will be stored in the edge
	 */
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

	/**
	 * Method to add a node to the graph with its info
	 * @param idVertex the id of the node to add
	 * @param infoVertex the information associated with that node
	 */
	public void addVertex(K idVertex, V infoVertex) {
		if(st.get(idVertex) != null)
			return;
		st.put(idVertex, V);
		stReverse.put(V, idVertex);
		V++;
		info.add(infoVertex);
		adj.add(new Queue<Edge<A>>());
		
	}
	
	/**
	 * returns the information of the node passed by parameter
	 * @param idVertex the id of the vertex we want the info from
	 * @return the information of the node passed by parameter
	 */
	public V getInfoVertex(K idVertex) {
		if(st.get(idVertex) == null)
			return null;
		return info.getElement(st.get(idVertex));
	}
	
	
	/**
	 * Method that sets info to a given vertex already in the graph
	 * @param idVertex the id of the vertex we want the info from
	 * @param infoVertex the information of the node
	 */
	public void setInfoVertex(K idVertex, V infoVertex) {
		if(st.get(idVertex) == null) return;
		info.addPos(infoVertex, st.get(idVertex));
	}
	
	/**
	 * Method that returns the information of the edge between two nodes passed by parameter
	 * @param idVertexIni the id of one of the end points of the edge
	 * @param idVertexFin the id of one of the end points of the edge
	 * @return the information of the edge between two nodes passed by parameter
	 */
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
	
	/**
	 * Method that set the information of one edge
	 * @param idVertexIni one end point of the edge
	 * @param idVertexFin one end point of the edge
	 * @param infoArc the info of the arc
	 */
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
	
	/**
	 * Method that returns an iterator of the adjacent nodes of a node 
	 * @param v the key of the node
	 * @return returns an iterator of the adjacent nodes of a node 
	 */
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
	
	/**
	 * Method that translate from the original id to a numerical ID
	 * @param value the original key of a value
	 * @return numerical ID
	 */
	public Integer translate(K value) {
		return st.get(value);
	} 
	
	/**
	 * Method that translate inverse from a numerical id to a key ID
	 * @param value of the translated id
	 * @return key ID
	 */
	public K translateInverse(Integer value) {
		return stReverse.get(value);
	}
	
	/**
	 * returns an iterator of all vertices in the graph
	 * @return iterator of all vertices in the graph
	 */
	public Iterator<K> vertices(){
		return st.keys();
	}
	
	/**
	 * returns all the edges of a node
	 * @param value the key ID of the node
	 * @return all the edges of a node
	 */
	public Iterator<Edge<A>> edgesTo(K value){
		int val = st.get(value);
		return adj.getElement(val).iterator();
	}
	
	/**
	 * returns all the edges of the node
	 * @return all the edges of the node
	 */
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
	
	/**
	 * returns the edge given the end points key ID
	 * @param idVertexIni the ID of one of the end points
	 * @param idVertexFin the ID of one of the end points
	 * @return the edge given the end points key ID
	 */
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
	
	/**
	 * Method that prunes a MST to only use edges that connect nodes of interest
	 * @param graph the graph generated as an MST
	 * @param needed the nodes that are needed
	 * @return an array with the edges of the new graph
	 */
	public static ORArray<Edge<Double>> pruneMST(Graph<Integer,VertexInfo,Double> graph, HashTableSC<Integer,Integer> needed){
		ORArray<Edge<Double>> ans = new ORArray<Edge<Double>>();
		Integer fi = needed.keys().next();
		boolean marked[] = new boolean[graph.V()];
		modifiedDFS(graph.translate(fi),graph,needed,ans,marked);
		return ans;
	}
	
	/**
	 * Modified DFS that only taked the edges that connects nodes of interest
	 * @param t
	 * @param graph
	 * @param needed
	 * @param ans
	 * @param marked
	 * @return
	 */
	private static boolean modifiedDFS(int t,Graph<Integer,VertexInfo,Double> graph, HashTableSC<Integer,Integer> needed, ORArray<Edge<Double>> ans,
			boolean marked[]) {
		marked[t] = true;
		boolean ret = false;
		if(needed.contains(t)) {ret = true; needed.delete(t);}
		Iterator<Integer> it = graph.adj(graph.translateInverse(t));
		while(it.hasNext()) {
			Integer ad = it.next();
			if(marked[graph.translate(ad)]) continue;
			boolean should = modifiedDFS(graph.translate(ad), graph, needed, ans, marked);
			if(should) {ans.add(graph.getEdge(graph.translateInverse(t), ad));ret = true;}
		}
		return ret;
	}
	
	
	/**
	 * Method that calculates the connected components given a graph
	 * @param graph the graph to be used
	 * @return a hashtable with the edges of a connected component
	 */
	public static HashTableSC<Integer, ORArray<Edge<Double>>> ConnectedComponent(Graph<Integer,VertexInfo,Double> graph){
		HashTableSC<Integer,ORArray<Edge<Double>>> ans = new HashTableSC<Integer,ORArray<Edge<Double>>>(299);
		Iterator<Integer> it = graph.vertices();
		int color = 1;
		boolean marked[] = new boolean[graph.V()];
		while(it.hasNext()) {
			Integer from = it.next();
			if(marked[graph.translate(from)])continue;
			DFSColors(graph.translate(from),graph,ans,marked,color);
			++color;
		}
		return ans;
	}
	
	/**
	 * altered DFS that paints the graph
	 * @param t
	 * @param graph
	 * @param ans
	 * @param marked
	 * @param color
	 */
	private static void DFSColors(int t,Graph<Integer,VertexInfo,Double> graph, HashTableSC<Integer,ORArray<Edge<Double>>> ans, boolean marked[], int color) {
		marked[t] = true;
		Iterator<Integer> it = graph.adj(graph.translateInverse(t));
		while(it.hasNext()) {
			Integer ad = it.next();
			if(marked[graph.translate(ad)]) continue;
			if(!ans.contains(color)) 
				ans.put(color, new ORArray<Edge<Double>>());
			ans.get(color).add(graph.getEdge(graph.translateInverse(t), ad));
			DFSColors(graph.translate(ad), graph, ans, marked,color);
		}
	}
	
}

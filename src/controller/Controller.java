package controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;

import model.CargaGrafo;
import model.Comparendo;
import model.data_structures.Dijkstra;
import model.data_structures.Edge;
import model.data_structures.Graph;
import model.data_structures.HashTableSC;
import model.data_structures.KruskalMST;
import model.data_structures.ORArray;
import model.data_structures.PairComp;
import model.vo.Coordinates;
import model.vo.VertexInfo;


public class Controller {

	/* Instancia del Modelo*/
	//private Modelo modelo;
	
	/* Instancia de la Vista*/
	//private View view;
	
	Graph<Integer,VertexInfo,Double> grafo = new Graph<Integer,VertexInfo,Double>();
	
	ORArray<PairComp<Integer, Integer>> infraccionesNodo;
	
	ORArray<Integer> nodosConEstaciones; 
	
	private Comparable<Comparendo>[] consulta;
	
	CargaGrafo cargaDatos;
	
	/**
	 * Crear la vista y el modelo del proyecto
	 * @param capacidad tamaNo inicial del arreglo
	 */
	public Controller ()
	{
		cargaDatos = new CargaGrafo();
		grafo=cargaDatos.g;
		nodosConEstaciones=cargaDatos.nodosConEstaciones;
	}	
	public void run() 
	{
		Scanner lector = new Scanner(System.in);
		boolean fin = false;
		String dato = "";
		String respuesta = "";
		while( !fin ){
			System.out.println("Que opcion quiere hacer??? SApo doble sapo");

			int option = lector.nextInt();
			switch(option){
				case 1:
					System.out.println("-----------------------------------------------------------------------");
					System.out.println("-----------------------------------------------------------------------");
					System.out.println("-----------------------------------------------------------------------");
					System.out.println("Por favor digite el nodo desde donde quiere partir: ");
					int from = lector.nextInt();
					System.out.println("Por favor digite el nodo al que quiere llegar: ");
					int to = lector.nextInt();
				    long start = System.currentTimeMillis();
				    CaminoDistanciaMinima1A(from,to);
				    long end = System.currentTimeMillis();
				    System.out.println("el tiempo que toma al algoritmo encontrar la respuesta y dibujar el camino"
				    		+ "es: " + (end-start));
				    System.out.println("-----------------------------------------------------------------------");
					System.out.println("-----------------------------------------------------------------------");
					System.out.println("-----------------------------------------------------------------------");
					break;	
				case 2:
					PoliceStationComponents();
					break;
				case 3:
					System.out.println("-----------------------------------------------------------------------");
					System.out.println("-----------------------------------------------------------------------");
					System.out.println("-----------------------------------------------------------------------");
					System.out.println("Por favor digite el numero de comparendos de mayor gravedad que se quiere"
							+ "utilizar: ");
					int m = lector.nextInt();
					
					break;
			}
		}
		
	}
	
	/**
	 * 
	 * @param idVertice1
	 * @param idVertice2
	 */
	public void CaminoDistanciaMinima1A(int idVertice1, int idVertice2) {
		ORArray<Integer> send = new ORArray<Integer>();
		send.add(idVertice1);
		Dijkstra caminos = new Dijkstra(this.grafo,send,false);
		System.out.println("La distancia m�s corta entre ambos puntos es: "+ caminos.distance(grafo.translateInverse(idVertice2)));
		ORArray<Edge<Double>> paint = caminos.journey(grafo.translateInverse(idVertice2));
		//TODO falta pintar mi doggo
	}
	
	/**
	 * 
	 * @param idVertice1
	 * @param idVertice2
	 */
	public void CaminoDistanciaMinima1B(int idVertice1, int idVertice2) {
		ORArray<Integer> send = new ORArray<Integer>();
		send.add(idVertice1);
		Dijkstra caminos = new Dijkstra(this.grafo,send,true);
		System.out.println("La distancia m�s corta entre ambos puntos es, seg�n numero de infracciones: "+ caminos.distance(grafo.translateInverse(idVertice2)));
		ORArray<Edge<Double>> paint = caminos.journey(grafo.translateInverse(idVertice2));
		//TODO falta pintar mi doggo
	}
	
	/**
	 * 
	 * @return
	 */
	public Graph<Integer,VertexInfo,Double> MST() {
		KruskalMST arbol = new KruskalMST(grafo);
		ORArray<Edge<Double>> arcos = new ORArray<Edge<Double>>();
		Iterable<Edge<Double>> recorrer = arbol.edges();
		for(Edge<Double> va : recorrer) 
			arcos.add(va);
		Graph<Integer,VertexInfo,Double> g = new Graph<Integer,VertexInfo,Double>();
		for(Edge<Double> ed: arcos) {
			int from = ed.either();
			int to = ed.other(from);
			g.addVertex(grafo.translateInverse(from), grafo.getInfoVertex(grafo.translateInverse(from)));
			g.addVertex(grafo.translateInverse(to), grafo.getInfoVertex(grafo.translateInverse(to)));
			g.addEdge(grafo.translateInverse(from), grafo.translateInverse(to), ed.getInfo());
		}
		return g;
	}
	
	/**
	 * 
	 */
	public void ArbolMayorComparendos(int m) {
		Graph<Integer,VertexInfo,Double> g = MST();
		ORArray<PairComp<Integer, VertexInfo>> vertex = new ORArray<PairComp<Integer, VertexInfo>>();
		Iterator<Integer> it = g.vertices();
		while(it.hasNext()) {
			int val = it.next();
			vertex.add(new PairComp<Integer, VertexInfo>(val, grafo.getInfoVertex(g.translateInverse(val))));
		}
		ORArray<PairComp<Double, Integer>> need = new ORArray<PairComp<Double,Integer>>();
		for(int i = 0; i < vertex.getSize();++i) 
			need.add(new PairComp<Double,Integer>(vertex.getElement(i).getSecond().getInfo2(),vertex.getElement(i).getFirst()));
		Comparator<PairComp<Double,Integer>> comp = new Comparator<PairComp<Double,Integer>>() {
			@Override
			public int compare(PairComp<Double, Integer> o1, PairComp<Double, Integer> o2) {
				return o1.getFirst().compareTo(o2.getFirst());
			}
		};
		need.sort(comp);
		HashTableSC<Integer, Integer> needed = new HashTableSC<Integer, Integer>(200);
		for(int i = need.getSize()-1, j = 0; i > -1 && j < m;--i,++j)
			needed.put(g.translate(need.getElement(i).getSecond()), 1);
		
		ORArray<Edge<Double>> aPintar = Graph.pruneMST(g, needed);
		double costo = 0.0;
		for(Edge<Double> edg: aPintar) {
			costo += edg.getInfo();
		}
		System.out.println("el costo del arbol es: ");
	}
	
	
	/**
	 * 
	 */
	public void ArbolMayorGravedad(int m) {
		Graph<Integer,VertexInfo,Double> g = MST();
		Comparator<PairComp<Integer,Integer>> comp = new Comparator<PairComp<Integer,Integer>>() {
			@Override
			public int compare(PairComp<Integer, Integer> o1, PairComp<Integer, Integer> o2) {
				return o1.getFirst().compareTo(o2.getFirst());
			}
		};
		infraccionesNodo.sort(comp);
		HashTableSC<Integer, Integer> needed = new HashTableSC<Integer, Integer>(200);
		for(int i = infraccionesNodo.getSize()-1, j = 0; i > -1 && j < m;--i,++j)
			needed.put(g.translate(infraccionesNodo.getElement(i).getSecond()), 1);
		ORArray<Edge<Double>> aPintar = Graph.pruneMST(g, needed);
	}
	
	/**
	 * 
	 * @param m
	 */
	public void shortestPathsPolice(int m) {
		Comparator<PairComp<Integer,Integer>> comp = new Comparator<PairComp<Integer,Integer>>() {
			@Override
			public int compare(PairComp<Integer, Integer> o1, PairComp<Integer, Integer> o2) {
				return o1.getFirst().compareTo(o2.getFirst());
			}
		};
		
		infraccionesNodo.sort(comp);
		HashTableSC<Integer, Integer> needed = new HashTableSC<Integer, Integer>(200);
		for(int i = infraccionesNodo.getSize()-1, j = 0; i > -1 && j < m;--i,++j)
			needed.put(infraccionesNodo.getElement(i).getSecond(), 1);
		Dijkstra caminos = new Dijkstra(this.grafo,nodosConEstaciones,false);
		ORArray<Edge<Double>> aPintar = new ORArray<Edge<Double>>();
		Iterator<Integer> it = needed.keys();
		while(it.hasNext()) {
			Integer see = it.next();
			ORArray<Edge<Double>> road = caminos.journey(see);
			for(Edge<Double> edg: road){
				aPintar.add(edg);
			}
		}
		//TODO pintelo mi doggo
	}
	
	/**
	 * 
	 */
	public void PoliceStationComponents() {
		//Dijkstra caminos = new Dijkstra(this.grafo,nodosConEstaciones,false);
		//Graph<Integer,VertexInfo,Double> G = caminos.generateGraph();
		HashTableSC<Integer,ORArray<Edge<Double>>> pintar = Graph.ConnectedComponent(grafo);
		System.out.println("cuantos componentes conectador hay en la re puta :v "+ pintar.getSize());
		Iterator<Integer> it = pintar.keys();
		for(int color = 1; it.hasNext();++color) {
			ORArray<Edge<Double>> thro =  pintar.get(it.next());
			for(Edge<Double> edg:  thro) {
				//todos dentro del for tienen que tener el mismo color :v
				int one = edg.either();
				int ot = edg.other(one);
				Coordinates onee = grafo.getInfoVertex(grafo.translateInverse(one)).getCoordinates();
				Coordinates twoo = grafo.getInfoVertex(grafo.translateInverse(ot)).getCoordinates();
				//del mismo color esos perros :v
			}
		}
		//la llave es el color y los arcos
	}
	
}

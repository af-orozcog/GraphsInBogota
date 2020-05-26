package controller;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;

import model.Comparendo;
import model.data_structures.Dijkstra;
import model.data_structures.Edge;
import model.data_structures.Graph;
import model.data_structures.HashTableSC;
import model.data_structures.KruskalMST;
import model.data_structures.ORArray;
import model.data_structures.PairComp;
import model.vo.VertexInfo;


public class Controller {

	/* Instancia del Modelo*/
	//private Modelo modelo;
	
	/* Instancia de la Vista*/
	//private View view;
	
	Graph<Integer,VertexInfo,Double> grafo = new Graph<Integer,VertexInfo,Double>();
	
	
	private Comparable<Comparendo>[] consulta;
	/**
	 * Crear la vista y el modelo del proyecto
	 * @param capacidad tamaNo inicial del arreglo
	 */
	/*public Controller ()
	{
		view = new View();
		modelo = new Modelo();
	
		
	}*/
	/*	
	public void run() 
	{
		Scanner lector = new Scanner(System.in);
		boolean fin = false;
		String dato = "";
		String respuesta = "";
		while( !fin ){
			view.printMenu();

			int option = lector.nextInt();
			switch(option){
				case 1:
				    modelo = new Modelo();
					
				    long start = System.currentTimeMillis();
				    consulta= modelo.dearreglo();
				    long end = System.currentTimeMillis();
				    		
				    		
				    view.printMessage("Tiempo de carga (seg): " + (end-start)/1000.0);
					view.printMessage("Datos cargados: " + consulta.length + "\n");
			   view.printMessage("Primer dato: " + consulta[0] + "\n");
			 view.printMessage("Ultimo dato: " + consulta[1] + "\n");
					
				
			

				   break;
					
				case 2:
					System.out.println("3. Ordenar consulta ascendentemente con ShellSort");
					if(consulta != null)
					{
						long tiempo = modelo.sortingBenchmarkOptionA(consulta,
								model.logic.Modelo.SHELLSORT );
						System.out.println("Primeros 10 comparendos");
						for(int i = 0; i <= 10 ;i++)
						{
							System.out.println("------------------------------------------------------------------------------------------------------------");
							System.out.println(consulta[i].toString());
							System.out.println("------------------------------------------------------------------------------------------------------------");

						}
						System.out.println("Últimos 10 comparendos");
						for(int i = consulta.length-1; i >= (consulta.length+10) ;i--)
						{
							System.out.println("------------------------------------------------------------------------------------------------------------");
							System.out.println(consulta[i].toString());
							System.out.println("------------------------------------------------------------------------------------------------------------");

						}
						System.out.println("El ordenamiento tomó "+tiempo+" milisegundos");
					
			}
			else
			{
				System.out.println("No se ha realizado la consulta");
			}
			break;
				case 3:
					System.out.println("4. Ordenar consulta ascendentemente usando MergeSort");
					if(consulta != null)
					{
						long tiempo = modelo.sortingBenchmarkOptionA(consulta,
								model.logic.Modelo.MERGESORT );
						System.out.println("Primeros 10 comparendos");
						for(int i = 0; i <= 10 ;i++)
						{
							System.out.println("------------------------------------------------------------------------------------------------------------");
							System.out.println(consulta[i].toString());
							System.out.println("------------------------------------------------------------------------------------------------------------");

						}
						System.out.println("Últimos 10 comparendos");
						for(int i = consulta.length-1; i >= (consulta.length+10) ;i--)
						{
							System.out.println("------------------------------------------------------------------------------------------------------------");
							System.out.println(consulta[i].toString());
							System.out.println("------------------------------------------------------------------------------------------------------------");

						}
						System.out.println("El ordenamiento tomó "+tiempo+" milisegundos");
					}
					else
					{
						System.out.println("No se ha realizado la consulta");
					}
					break;

				case 4: 
					System.out.println("4. Ordenar consulta ascendentemente usando QuickSort");
					if(consulta != null)
					{
						long tiempo = modelo.sortingBenchmarkOptionA(consulta,
								model.logic.Modelo.QUICKSORT );
						System.out.println("Primeros 10 comparendos");
						for(int i = 0; i <= 10 ;i++)
						{
							System.out.println("------------------------------------------------------------------------------------------------------------");
							System.out.println(consulta[i].toString());
							System.out.println("------------------------------------------------------------------------------------------------------------");
						}
						System.out.println("Últimos 10 comparendos");
						for(int i = consulta.length-1; i >= (consulta.length+10) ;i--)
						{
							System.out.println("------------------------------------------------------------------------------------------------------------");
							System.out.println(consulta[i].toString());
							System.out.println("------------------------------------------------------------------------------------------------------------");

						}
						System.out.println("El ordenamiento tomó "+tiempo+" milisegundos");
					}
					else
					{
						System.out.println("No se ha realizado la consulta");
					}
					break;	
			}
		}
		
	}*/
	
	/**
	 * 
	 * @param idVertice1
	 * @param idVertice2
	 */
	public void CaminoDistanciaMinima1A(int idVertice1, int idVertice2) {
		ORArray<Integer> send = new ORArray<Integer>();
		send.add(idVertice1);
		Dijkstra<Integer> caminos = new Dijkstra<Integer>(this.grafo,send,false);
		System.out.println("La distancia más corta entre ambos puntos es: "+ caminos.distance(grafo.translateInverse(idVertice2)));
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
		Dijkstra<Integer> caminos = new Dijkstra<Integer>(this.grafo,send,true);
		System.out.println("La distancia más corta entre ambos puntos es, según numero de infracciones: "+ caminos.distance(grafo.translateInverse(idVertice2)));
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
			g.addVertex(from, grafo.getInfoVertex(grafo.translateInverse(from)));
			g.addVertex(to, grafo.getInfoVertex(grafo.translateInverse(to)));
			g.addEdge(from, to, ed.getInfo());
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
			needed.put(need.getElement(i).getSecond(), 1);
		ORArray<Edge<Double>> aPintar = Graph.pruneMST(g, needed);
	}
	
	
	/**
	 * 
	 */
	public void ArbolMayorGravedad(int m) {
		Graph<Integer,VertexInfo,Double> g = MST();
		ORArray<PairComp<Integer, VertexInfo>> vertex = new ORArray<PairComp<Integer, VertexInfo>>();
		Iterator<Integer> it = g.vertices();
		while(it.hasNext()) {
			int val = it.next();
			vertex.add(new PairComp<Integer, VertexInfo>(val, grafo.getInfoVertex(g.translateInverse(val))));
		}
		ORArray<PairComp<Double, Integer>> need = new ORArray<PairComp<Double,Integer>>();
		for(int i = 0; i < vertex.getSize();++i) 
			need.add(new PairComp<Double,Integer>(vertex.getElement(i).getSecond().getInfo1(),vertex.getElement(i).getFirst()));
		Comparator<PairComp<Double,Integer>> comp = new Comparator<PairComp<Double,Integer>>() {
			@Override
			public int compare(PairComp<Double, Integer> o1, PairComp<Double, Integer> o2) {
				return o1.getFirst().compareTo(o2.getFirst());
			}
		};
		need.sort(comp);
		HashTableSC<Integer, Integer> needed = new HashTableSC<Integer, Integer>(200);
		for(int i = need.getSize()-1, j = 0; i > -1 && j < m;--i,++j)
			needed.put(need.getElement(i).getSecond(), 1);
		ORArray<Edge<Double>> aPintar = Graph.pruneMST(g, needed);
	}
	
}

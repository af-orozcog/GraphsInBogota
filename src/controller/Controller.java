package controller;



import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;

import com.teamdev.jxmaps.LatLng;

import model.CargaGrafo;
import model.Comparendo;
import model.data_structures.Dijkstra;
import model.data_structures.Edge;
import model.data_structures.Graph;
import model.data_structures.HashTableLP;
import model.data_structures.HashTableSC;
import model.data_structures.KruskalMST;
import model.data_structures.ORArray;
import model.data_structures.PairComp;
import model.vo.Coordinates;
import model.vo.Mapa;
import model.vo.Mapa2;
import model.vo.PoliceStation;
import model.vo.VertexInfo;


public class Controller {

	/* Instancia del Modelo*/
	//private Modelo modelo;

	/* Instancia de la Vista*/
	//private View view;

	Graph<Integer,VertexInfo,Double> grafo = new Graph<Integer,VertexInfo,Double>();

	ORArray<PairComp<Integer, Integer>> infraccionesNodo;

	ORArray<Integer> nodosConEstaciones; 

	HashTableLP<Integer,Comparendo> comparendos;
	HashTableLP<Integer,PoliceStation> estaciones;
	CargaGrafo cargaDatos;
	LatLng pequeno;
	LatLng grande;

	/**
	 * Crear la vista y el modelo del proyecto
	 * @param capacidad tamaNo inicial del arreglo
	 */
	public Controller ()
	{
		cargaDatos = new CargaGrafo();
		grafo=cargaDatos.g;
		nodosConEstaciones=cargaDatos.nodosConEstaciones;
		comparendos=cargaDatos.comparendos;
		estaciones=cargaDatos.estaciones;
		pequeno=new LatLng(cargaDatos.latmin,cargaDatos.lonmin);
		grande=new LatLng(cargaDatos.latmax,cargaDatos.lonmax);


		System.out.println("******************* Información de la carga *******************");
		System.out.println("Comparendos en el archivo: "+ comparendos.getSize());
		System.out.println("Comparendo con mayor OBJECTID: "+ cargaDatos.mayor);
		System.out.println("Estaciones en el archivo: "+ estaciones.getSize());
		System.out.println("Estación con mayor OBJECTID: "+ cargaDatos.bigEst);
		System.out.println("Vértices en el grafo: "+ grafo.V());
		VertexInfo verticeGrande=grafo.getInfoVertex(cargaDatos.vertMax);
		System.out.println("Vértice con mayor ID: "+verticeGrande.getId()+" Latitud: "+verticeGrande.getCoordinates().lat+" Longitud: "+verticeGrande.getCoordinates().lon );
		System.out.println("Arcos en el grafo: "+ grafo.E());
		System.out.println("Arco con mayor ID: IDOrigen: "+cargaDatos.arcMax[0]  +" IDDestino: "+cargaDatos.arcMax[1]+ " Distancia: "+grafo.getInfoArc(cargaDatos.arcMax[0], cargaDatos.arcMax[1]));
		System.out.println("Arco con mayor longitud: IDOrigen: "+cargaDatos.distMax[0]  +" IDDestino: "+cargaDatos.distMax[1]+ " Distancia: "+grafo.getInfoArc(cargaDatos.distMax[0], cargaDatos.distMax[1]));
		System.out.println("***************************************************************");
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
			case 4:
				generarMapa("Grafo completo",null,1,grafo);

			}
		}

	}

	public void generarMapa(String titulo,ORArray<Edge<Double>> paint, int colores, Graph<Integer,VertexInfo,Double> g )
	{
		Mapa2 example = new Mapa2(titulo);

		if(paint!=null)
		{
			for (int i=0;i<colores;i++)
			{
				for(Edge<Double> edg: paint) {
					int one = edg.either();
					int two = edg.other(one);
					Coordinates onee = grafo.getInfoVertex(grafo.translateInverse(one)).getCoor();
					Coordinates twoo = grafo.getInfoVertex(grafo.translateInverse(two)).getCoor();						
					double lat1 = onee.lat;
					double lon1 = onee.lon;
					double lat2 = twoo.lat;
					double lon2 = twoo.lon;
					example.generateSimplePath(new LatLng(lat1,lon1), new LatLng(lat2,lon2), false);			
				}
			}
		}
		else {
			
			Graph<Integer,VertexInfo,Double> ausar=grafo;
			Iterator<Edge<Double>> arcos= ausar.edges().iterator();
			while(arcos.hasNext())
			{
				Edge<Double> arc = arcos.next();
				VertexInfo info1=(VertexInfo)ausar.getInfoVertex(ausar.translateInverse(arc.either()));
				double lat1 = info1.getCoordinates().lat;
				double lon1 = info1.getCoordinates().lon;
				VertexInfo info2=(VertexInfo)ausar.getInfoVertex(ausar.translateInverse(arc.other(arc.either())));

				double lat2 = info2.getCoordinates().lat;
				double lon2 = info2.getCoordinates().lon;
				example.generateSimplePath(new LatLng(lat1,lon1), new LatLng(lat2,lon2), false);			
			}
		}
	}





	/**
	 * Mï¿½todo que calcula la distancia minima entre dos puntos ingresados por el usuario
	 * @param idVertice1 identificador del indice de partida
	 * @param idVertice2 identificador del indice de llegada
	 */
	public void CaminoDistanciaMinima1A(int idVertice1, int idVertice2) {
		ORArray<Integer> send = new ORArray<Integer>();
		send.add(idVertice1);
		Dijkstra caminos = new Dijkstra(this.grafo,send,false);
		System.out.println("La distancia mas corta entre ambos puntos es: "+ caminos.distance(idVertice2));
		double val = caminos.distance(idVertice2); 
		Double comp = Double.POSITIVE_INFINITY; 
		if(val == comp)return;
		ORArray<Edge<Double>> paint = caminos.journey(idVertice2);
		generarMapa("Req 1A",paint,1,null);
	}

	/**
	 * Mï¿½todo que calcula la distancia minima entre dos puntos
	 * En este caso la distancia minima es el numero de comparendos en 
	 * nodos que pasa
	 * @param idVertice1 identificador del indice de partida
	 * @param idVertice2 identificador del indice de llegada
	 */
	public void CaminoDistanciaMinima1B(int idVertice1, int idVertice2) {
		ORArray<Integer> send = new ORArray<Integer>();
		send.add(idVertice1);
		Dijkstra caminos = new Dijkstra(this.grafo,send,true);
		System.out.println("La distancia mï¿½s corta entre ambos puntos es, segï¿½n numero de infracciones: "+ caminos.distance(idVertice2));
		if(caminos.distance(idVertice2) == Double.POSITIVE_INFINITY)return;
		ORArray<Edge<Double>> paint = caminos.journey(idVertice2);
		generarMapa("Req 1B",paint,1,null);
	}

	/**
	 * Grafo generado de sacar el MST en el grafo global
	 * @return MST del grafo global
	 */
	public Graph<Integer,VertexInfo,Double> MST() {
		KruskalMST<Integer,VertexInfo> arbol = new KruskalMST<Integer,VertexInfo>(grafo);
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
		generarMapa("MST",null,1,g);

		return g;
	}

	/**
	 * Method that takes the tree and prunes it to only show the edges and nodes related to the M 
	 * places with the most infractions
	 * @param m the amount of needed infractions
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

		ORArray<Edge<Double>> aPintar = new ORArray<Edge<Double>>();
		while(needed.getSize() != 0) {
			ORArray<Edge<Double>> temp = Graph.pruneMST(g, needed);
			for(Edge<Double> edg: temp)
				aPintar.add(edg);
		}
		double costo = 0.0;
		for(Edge<Double> edg: aPintar) 
			costo += edg.getInfo();
		System.out.println("el costo del arbol es: "  + costo);
		generarMapa("Arbol mayor comparendos",aPintar,1,null);

	}


	/**
	 * 
	 * @param m
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
		ORArray<Edge<Double>> aPintar = new ORArray<Edge<Double>>();
		while(needed.getSize() != 0) {
			ORArray<Edge<Double>> temp = Graph.pruneMST(g, needed);
			for(Edge<Double> edg: temp)
				aPintar.add(edg);
		}
		double costo = 0.0;
		for(Edge<Double> edg: aPintar) 
			costo += edg.getInfo();
		System.out.println("el costo del arbol es: "  + costo);
		generarMapa("Arbol mayor Gravedad",aPintar,1,null);

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
		Double costo = 0.0;
		while(it.hasNext()) {
			Integer see = it.next();
			ORArray<Edge<Double>> road = caminos.journey(see);
			for(Edge<Double> edg: road){
				aPintar.add(edg);
				costo += edg.getInfo();
			}
		}
		System.out.println("El costo de este camino que conecta el grafo es: "+ costo);
		generarMapa("Caminos cortos policía",aPintar,1,null);

	}

	/**
	 * Method that prints the connected components of infractions and police stations
	 * This is done by allocating the infractions to the closest
	 */
	public void PoliceStationComponents() {
		Dijkstra caminos = new Dijkstra(this.grafo,nodosConEstaciones,false);
		Graph<Integer,VertexInfo,Double> G = caminos.generateGraph();
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
	}

}

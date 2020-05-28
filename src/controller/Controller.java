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
import model.vo.PoliceStation;
import model.vo.VertexInfo;


public class Controller {

	/**
	 * 
	 * Clase auxiliar para hacer comparaciones
	 *
	 */
	class Gravedad implements Comparable<Gravedad>{
		
		/**
		 * string que representa el tipo de servicio
		 */
		private String TipoServicio;

		/**
		 * string que representa la infraccion
		 */
		private String infraccion;
		
		/**
		 * Constructor de gravedad
		 * @param tipoServicio
		 * @param infraccion
		 */
		public Gravedad(String tipoServicio, String infraccion) {
			this.TipoServicio = tipoServicio;
			this.infraccion = infraccion;
		}
		
		@Override
		/**
		 * Comaprador para infraccion
		 */
		public int compareTo(Gravedad o) {
			if(o.TipoServicio.compareTo(this.TipoServicio) == 0 )
				return infraccion.compareTo(o.infraccion);
			if(TipoServicio.compareTo("Publico") == 0) return 1;
			if(o.TipoServicio.compareTo("Publico") == 0) return 1;
			if(TipoServicio.compareTo("Oficial") == 0) return 1;
			return 0;
		}
		
	}

	/**
	 * grafo de bogota
	 */
	private Graph<Integer,VertexInfo,Double> grafo = new Graph<Integer,VertexInfo,Double>();

	/**
	 * infracciones con la gravedad
	 */
	private ORArray<PairComp<Gravedad, Integer>> infraccionesNodoGravedad;
	

	/**
	 * id de todas los nodos que tienen estaciones
	 */
	private ORArray<Integer> nodosConEstaciones; 
	
	/**
	 * hashtable con la informacion de los comparendos
	 */
	private HashTableLP<Integer,Comparendo> comparendos;
	
	/**
	 * hashtable con la informacion de las estaciones
	 */
	private HashTableLP<Integer,PoliceStation> estaciones;
	
	/**
	 * clase de carga de datos
	 */
	private CargaGrafo cargaDatos;
	
	/**
	 * la latitud mas pequeña del grafo
	 */
	private LatLng pequeno;
	
	/**
	 * la  latitud mas pqueña del grafo
	 */
	private LatLng grande;

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
		Iterator<Integer> vertices = grafo.vertices();
		infraccionesNodoGravedad = new ORArray<PairComp<Gravedad, Integer>>();
		nodosConEstaciones = new ORArray<Integer>();
		while(vertices.hasNext()) {
			Integer val = vertices.next();
			VertexInfo info = grafo.getInfoVertex(val);
			for(Integer idd : info.getInfractions()) {
				Gravedad needAdd = new Gravedad(comparendos.get(idd).getTIPO_SERVICIO(),comparendos.get(idd).getINFRACCION());
				infraccionesNodoGravedad.add(new PairComp<Gravedad,Integer>(needAdd,val));
			}
			if(info.hasPoliceStation()) nodosConEstaciones.add(val);
		}
	}	
	
	/**
	 * 
	 */
	public void run() 
	{
		Scanner lector = new Scanner(System.in);
		boolean fin = false;
		String dato = "";
		String respuesta = "";
		while( !fin ){
			System.out.println("Cual de las siguientes opciones quiere hacer?\n"
					+ "1.Obtener el camino de costo mínimo entre dos ubicaciones geográficas por distancia\n"
					+ "2.Determinar la red de comunicaciones que soporte la instalación de cámaras de video en los M puntos donde se presenta el mayor número de comparendos en la ciudad.\n"
					+ "3.Obtener el camino de costo mínimo entre dos ubicaciones geográficas por número de comparendos\n"
					+ "4.Determinar la red de comunicaciones que soporte la instalación de cámaras de video en los M puntos donde se presentan los comparendos de mayor gravedad.\n"+
					"5.Obtener los caminos más cortos para que los policías puedan atender los M comparendos más graves.\n"
					+ "6.Identificar las zonas de impacto de las estaciones de policia\n");

			int option = lector.nextInt();
			switch(option){
			case 1:
				System.out.println("-----------------------------------------------------------------------");
				System.out.println("-----------------------------------------------------------------------");
				System.out.println("-----------------------------------------------------------------------");
				System.out.println("Por favor digite el nodo desde donde quiere partir: ");
				int from1 = lector.nextInt();
				System.out.println("Por favor digite el nodo al que quiere llegar: ");
				int to1 = lector.nextInt();
				long start1 = System.currentTimeMillis();
				CaminoDistanciaMinima1A(from1,to1);
				long end1 = System.currentTimeMillis();
				System.out.println("el tiempo que toma al algoritmo encontrar la respuesta y dibujar el camino"
						+ "es: " + (end1-start1));
				System.out.println("-----------------------------------------------------------------------");
				System.out.println("-----------------------------------------------------------------------");
				System.out.println("-----------------------------------------------------------------------");
				break;	
			case 2:
				System.out.println("-----------------------------------------------------------------------");
				System.out.println("-----------------------------------------------------------------------");
				System.out.println("-----------------------------------------------------------------------");
				System.out.println("Por favor digite el numero de nodos con mayor cantidad de comparendos que se quieren"
						+ " utilizar: ");
				int m2 = lector.nextInt();
				long start2 = System.currentTimeMillis();
				ArbolMayorComparendos(m2);
				long end2 = System.currentTimeMillis();
				System.out.println("el tiempo que toma al algoritmo encontrar la respuesta y dibujar el camino"
						+ "es: " + (end2-start2));
				System.out.println("-----------------------------------------------------------------------");
				System.out.println("-----------------------------------------------------------------------");
				System.out.println("-----------------------------------------------------------------------");
				break;
			case 3:
				System.out.println("-----------------------------------------------------------------------");
				System.out.println("-----------------------------------------------------------------------");
				System.out.println("-----------------------------------------------------------------------");
				System.out.println("Por favor digite el nodo desde donde quiere partir: ");
				int from3 = lector.nextInt();
				System.out.println("Por favor digite el nodo al que quiere llegar: ");
				int to3 = lector.nextInt();
				long start3 = System.currentTimeMillis();
				CaminoDistanciaMinima1A(from3,to3);
				long end3 = System.currentTimeMillis();
				System.out.println("el tiempo que toma al algoritmo encontrar la respuesta y dibujar el camino"
						+ "es: " + (end3-start3));
				System.out.println("-----------------------------------------------------------------------");
				System.out.println("-----------------------------------------------------------------------");
				System.out.println("-----------------------------------------------------------------------");
				break;
			case 4:
				System.out.println("-----------------------------------------------------------------------");
				System.out.println("-----------------------------------------------------------------------");
				System.out.println("-----------------------------------------------------------------------");
				System.out.println("Por favor digite el numero de comparendos de mayor gravedad que se quiere"
						+ " utilizar: ");
				int m4 = lector.nextInt();
				long start4 = System.currentTimeMillis();
				ArbolMayorGravedad(m4);
				long end4 = System.currentTimeMillis();
				System.out.println("el tiempo que toma al algoritmo encontrar la respuesta y dibujar el camino"
						+ "es: " + (end4-start4));
				System.out.println("-----------------------------------------------------------------------");
				System.out.println("-----------------------------------------------------------------------");
				System.out.println("-----------------------------------------------------------------------");
				break;
			
			case 5:
				System.out.println("-----------------------------------------------------------------------");
				System.out.println("-----------------------------------------------------------------------");
				System.out.println("-----------------------------------------------------------------------");
				System.out.println("Por favor digite el numero de comparendos de mayor gravedad que se quiere"
						+ " utilizar: ");
				int m5 = lector.nextInt();
				long start5 = System.currentTimeMillis();
				shortestPathsPolice(m5);
				long end5 = System.currentTimeMillis();
				System.out.println("el tiempo que toma al algoritmo encontrar la respuesta y dibujar el camino"
						+ "es: " + (end5-start5));
				System.out.println("-----------------------------------------------------------------------");
				System.out.println("-----------------------------------------------------------------------");
				System.out.println("-----------------------------------------------------------------------");
				break;
			case 6:
				System.out.println("-----------------------------------------------------------------------");
				System.out.println("-----------------------------------------------------------------------");
				System.out.println("-----------------------------------------------------------------------");
				System.out.println("Por favor digite el numero de comparendos de mayor gravedad que se quiere"
						+ "utilizar: ");
				long start6 = System.currentTimeMillis();
				PoliceStationComponents();
				long end6 = System.currentTimeMillis();
				System.out.println("el tiempo que toma al algoritmo encontrar la respuesta y dibujar el camino"
						+ "es: " + (end6-start6));
				System.out.println("-----------------------------------------------------------------------");
				System.out.println("-----------------------------------------------------------------------");
				System.out.println("-----------------------------------------------------------------------");
				break;
			}
		}

	}

	public void generarMapaGrafo(Graph<Integer, VertexInfo, Double> grafo2,LatLng min,LatLng max,boolean pSinoVertices, Graph<Long, VertexInfo, Double> pGrafoAdicional)
	{
		Mapa x=new Mapa(grafo2,min,max,pSinoVertices, pGrafoAdicional);
		Mapa.graficarMapa(x);
	}

	public void generarMapaAux(Graph<Integer, VertexInfo, Double> grafo2,ORArray<Edge<Double>> paint, LatLng min,LatLng max)
	{
		Mapa x=new Mapa(grafo2,paint,min,max);
		Mapa.graficarMapa(x);
	}




	/**
	 * Mï¿½todo que calcula la distancia minima entre dos puntos ingresados por el usuario
	 * @param idVertice1 identificador del indice de partida
	 * @param idVertice2 identificador del indice de llegada
	 */
	public void CaminoDistanciaMinima1A(int idVertice1, int idVertice2) {
		ORArray<Integer> send = new ORArray<Integer>();
		send.add(idVertice1);
		System.out.println("Calculando las distancias minimas");
		Dijkstra caminos = new Dijkstra(this.grafo,send,false);
		System.out.println("Terminando de calcular las distancias minimas");
		System.out.println("La distancia mas corta entre ambos puntos es: "+ caminos.distance(idVertice2));
		double val = caminos.distance(idVertice2); 
		Double comp = Double.POSITIVE_INFINITY; 
		if(val == comp)return;
		System.out.println("calculando el camino optimo");
		ORArray<Edge<Double>> paint = caminos.journey(idVertice2);
		System.out.println("terminando de calcular el camino optimo");
		System.out.println("tamanio de arcos "+ paint.getSize());
		generarMapaAux(grafo,paint,pequeno,grande);
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
		System.out.println("Calculando las distancias minimas");
		Dijkstra caminos = new Dijkstra(this.grafo,send,true);
		System.out.println("Terminando de calcular las distancias minimas");
		System.out.println("La distancia mï¿½s corta entre ambos puntos es, segï¿½n numero de infracciones: "+ caminos.distance(idVertice2));
		if(caminos.distance(idVertice2) == Double.POSITIVE_INFINITY)return;
		System.out.println("calculando el camino optimo");
		ORArray<Edge<Double>> paint = caminos.journey(idVertice2);
		System.out.println("terminando de calcular el camino optimo");
		System.out.println("tamanio de arcos "+ paint.getSize());
		generarMapaAux(grafo,paint,pequeno,grande);
	}

	/**
	 * Grafo generado de sacar el MST en el grafo global
	 * @return MST del grafo global
	 */
	public Graph<Integer,VertexInfo,Double> MST() {
		System.out.println("Construyendo el MST");
		KruskalMST<Integer,VertexInfo> arbol = new KruskalMST<Integer,VertexInfo>(grafo);
		System.out.println("Terminado el MST");
		ORArray<Edge<Double>> arcos = new ORArray<Edge<Double>>();
		Iterable<Edge<Double>> recorrer = arbol.edges();
		for(Edge<Double> va : recorrer) 
			arcos.add(va);
		System.out.println("Generando el grafo de los Arcos del MST");
		Graph<Integer,VertexInfo,Double> g = new Graph<Integer,VertexInfo,Double>();
		for(Edge<Double> ed: arcos) {
			int from = ed.either();
			int to = ed.other(from);
			g.addVertex(grafo.translateInverse(from), grafo.getInfoVertex(grafo.translateInverse(from)));
			g.addVertex(grafo.translateInverse(to), grafo.getInfoVertex(grafo.translateInverse(to)));
			g.addEdge(grafo.translateInverse(from), grafo.translateInverse(to), ed.getInfo());
		}
		System.out.println("Terminando de generar el grafo de los Arcos del MST");
		//generarMapaGrafo(g, pequeno, grande, false, null);
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
		System.out.println("Organizando los vertices segun la cantidad de comparendos");
		need.sort(comp);
		System.out.println("Terminando de organizar los vertices segun la cantidad de comparendos");
		HashTableSC<Integer, Integer> needed = new HashTableSC<Integer, Integer>(200);
		for(int i = need.getSize()-1, j = 0; i > -1 && j < m;--i,++j)
			needed.put(g.translate(need.getElement(i).getSecond()), 1);

		ORArray<Edge<Double>> aPintar = new ORArray<Edge<Double>>();
		System.out.println("Empezando a limpiar el arbol");
		while(needed.getSize() != 0) {
			ORArray<Edge<Double>> temp = Graph.pruneMST(g, needed);
			for(Edge<Double> edg: temp)
				aPintar.add(edg);
		}
		System.out.println("Terminando de limpiar el arbol");
		double costo = 0.0;
		for(Edge<Double> edg: aPintar) 
			costo += edg.getInfo();
		System.out.println("el costo del arbol es: "  + costo);
		System.out.println("el tamanio del grafo en nodos " + aPintar.getSize());
		generarMapaAux(grafo,aPintar,pequeno,grande);
	}


	/**
	 * Genera el arbol con las m infracciones de mayor gravedad
	 * @param m la cantidad de infracciones de mayor gravedad
	 */
	public void ArbolMayorGravedad(int m) {
		Graph<Integer,VertexInfo,Double> g = MST();
		Comparator<PairComp<Gravedad,Integer>> comp = new Comparator<PairComp<Gravedad,Integer>>() {
			@Override
			public int compare(PairComp<Gravedad, Integer> o1, PairComp<Gravedad, Integer> o2) {
				return o1.getFirst().compareTo(o2.getFirst());
			}
		};
		System.out.println("Organizando los vertices segun la gravedad de los comparendos");
		infraccionesNodoGravedad.sort(comp);
		System.out.println("Terminando de organizar los vertices segun la gravedad de los comparendos");
		HashTableSC<Integer, Integer> needed = new HashTableSC<Integer, Integer>(200);
		for(int i = infraccionesNodoGravedad.getSize()-1, j = 0; i > -1 && j < m;--i,++j)
			needed.put(g.translate(infraccionesNodoGravedad.getElement(i).getSecond()), 1);
		ORArray<Edge<Double>> aPintar = new ORArray<Edge<Double>>();
		System.out.println("Empezando a limpiar el arbol");
		while(needed.getSize() != 0) {
			ORArray<Edge<Double>> temp = Graph.pruneMST(g, needed);
			for(Edge<Double> edg: temp)
				aPintar.add(edg);
		}
		System.out.println("Terminando de limpiar el arbol");
		double costo = 0.0;
		for(Edge<Double> edg: aPintar) 
			costo += edg.getInfo();
		System.out.println("el costo del arbol es: "  + costo);
		System.out.println("el tamanio del grafo en nodos " + aPintar.getSize());
		generarMapaAux(grafo,aPintar,pequeno,grande);

	}

	/**
	 * Method to generate the shortest paths from police station to the M most important infractions
	 * @param m the quantity of the most important infractions
	 */
	public void shortestPathsPolice(int m) {
		Comparator<PairComp<Gravedad,Integer>> comp = new Comparator<PairComp<Gravedad,Integer>>() {
			@Override
			public int compare(PairComp<Gravedad, Integer> o1, PairComp<Gravedad, Integer> o2) {
				return o1.getFirst().compareTo(o2.getFirst());
			}
		};
		System.out.println("Organizando los vertices segun la gravedad de los comparendos");
		infraccionesNodoGravedad.sort(comp);
		System.out.println("Terminando de organizar los vertices segun la gravedad de los comparendos");
		HashTableSC<Integer, Integer> needed = new HashTableSC<Integer, Integer>(200);
		for(int i = infraccionesNodoGravedad.getSize()-1, j = 0; i > -1 && j < m;--i,++j)
			needed.put(infraccionesNodoGravedad.getElement(i).getSecond(), 1);
		System.out.println("Generando los caminos mas cortos");
		Dijkstra caminos = new Dijkstra(this.grafo,nodosConEstaciones,false);
		System.out.println("Terminando de generar los caminos mas cortos");
		ORArray<Edge<Double>> aPintar = new ORArray<Edge<Double>>();
		Iterator<Integer> it = needed.keys();
		Double costo = 0.0;
		System.out.println("Creando el arreglo con los edges");
		while(it.hasNext()) {
			Integer see = it.next();
			ORArray<Edge<Double>> road = caminos.journey(see);
			for(Edge<Double> edg: road){
				aPintar.add(edg);
				costo += edg.getInfo();
			}
		}
		System.out.println("terminando de crear el arreglo de distancia minimas");
		System.out.println("El costo de este camino que conecta el grafo es: "+ costo);
		generarMapaAux(grafo,aPintar,pequeno,grande);

	}

	/**
	 * Method that prints the connected components of infractions and police stations
	 * This is done by allocating the infractions to the closest
	 */
	public void PoliceStationComponents() {
		System.out.println("asignando a cada estacion de policia los comparendos más cercanos");
		Dijkstra caminos = new Dijkstra(this.grafo,nodosConEstaciones,false);
		System.out.println("finalizando de asignar a cada estacion de policia los comparendos más cercanos");
		System.out.println("empezando a generar grafo de distancia minimas");
		Graph<Integer,VertexInfo,Double> G = caminos.generateGraph();
		System.out.println("terminando de generar grafo de distancia minimas");
		HashTableSC<Integer,ORArray<Edge<Double>>> pintar = Graph.ConnectedComponent(G);
		System.out.println("empezando a pintar");
		Iterator<Integer> it = pintar.keys();
		for(int color = 1; it.hasNext();++color) {
			ORArray<Edge<Double>> thro =  pintar.get(it.next());
			for(Edge<Double> edg:  thro) {
				//todos dentro del for tienen que tener el mismo color :v
				int one = edg.either();
				int ot = edg.other(one);
				Coordinates onee = G.getInfoVertex(G.translateInverse(one)).getCoordinates();
				Coordinates twoo = G.getInfoVertex(G.translateInverse(ot)).getCoordinates();
				//del mismo color esos perros :v
			}
		}
	}

}

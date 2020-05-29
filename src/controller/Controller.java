package controller;



import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;

import com.teamdev.jxmaps.CircleOptions;
import com.teamdev.jxmaps.LatLng;
import com.teamdev.jxmaps.PolylineOptions;

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
			Integer pol = info.getPoliceStation();
			if(pol != -1) nodosConEstaciones.add(val);
		}
		System.out.println("******************* Información de la carga *******************");
		System.out.println("Comparendos en el archivo: "+ comparendos.getSize());
		System.out.println("Comparendo con mayor OBJECTID: "+ cargaDatos.mayor);
		System.out.println("Estaciones en el archivo: "+ estaciones.getSize());
		System.out.println("Estación con mayor OBJECTID: "+ cargaDatos.bigEst);
		System.out.println("Vértices en el grafo: "+ grafo.V());
		VertexInfo verticeGrande=grafo.getInfoVertex(cargaDatos.vertMax);
		System.out.println("Vértice con mayor ID: "+verticeGrande.getId()+" Latitud: "+verticeGrande.getCoordinates().lat+" Longitud: "+verticeGrande.getCoordinates().lon );
		System.out.println("Arcos en el grafo: "+ grafo.E());
		System.out.println("Arco con mayor ID: IDOrigen: "+cargaDatos.arcMax[0]  +" IDDestino: "+cargaDatos.arcMax[1]+ " Distancia: "+grafo.getInfoArc(cargaDatos.arcMax[0], cargaDatos.arcMax[1])+" Kilómetros");
		System.out.println("Arco con mayor longitud: IDOrigen: "+cargaDatos.distMax[0]  +" IDDestino: "+cargaDatos.distMax[1]+ " Distancia: "+grafo.getInfoArc(cargaDatos.distMax[0], cargaDatos.distMax[1])+" Kilómetros");
		System.out.println("***************************************************************");		
	}	

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
				System.out.println("Por favor digite la latitud del nodo desde donde quiere partir: ");
				double from1lat = lector.nextDouble();
				System.out.println("Por favor digite la longitud del nodo desde donde quiere partir: ");
				double from1long = lector.nextDouble();
				System.out.println("Por favor digite la latitud del nodo al que quiere llegar: ");
				double to1lat = lector.nextDouble();
				System.out.println("Por favor digite la longitud del nodo al que quiere llegar: ");
				double to1long = lector.nextDouble();
				Coordinates from1 = new Coordinates(from1lat, from1long);
				Coordinates to1 = new Coordinates(to1lat, to1long);
				int fromm1 = closest(from1);
				int too1 = closest(to1);
				long start1 = System.currentTimeMillis();
				CaminoDistanciaMinima1A(fromm1,too1);
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
				System.out.println("Por favor digite la latitud del nodo desde donde quiere partir: ");
				double from3lat = lector.nextDouble();
				System.out.println("Por favor digite la longitud del nodo desde donde quiere partir: ");
				double from3long = lector.nextDouble();
				System.out.println("Por favor digite la latitud del nodo al que quiere llegar: ");
				double to3lat = lector.nextDouble();
				System.out.println("Por favor digite la longitud del nodo al que quiere llegar: ");
				double to3long = lector.nextDouble();
				Coordinates from3 = new Coordinates(from3lat, from3long);
				Coordinates to3 = new Coordinates(to3lat, to3long);
				int fromm3 = closest(from3);
				int too3 = closest(to3);
				long start3 = System.currentTimeMillis();
				CaminoDistanciaMinima1A(fromm3,too3);
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
				long start6 = System.currentTimeMillis();
				System.out.println("Por favor digite el valor de M");
				int m6 = lector.nextInt();
				PoliceStationComponents(m6);
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


	public int closest(Coordinates val) {
		Iterator<Integer> it = grafo.vertices();
		int who = -1;
		double dist = 1000000000000.0;
		while(it.hasNext()) {
			Integer node = it.next();
			VertexInfo info = grafo.getInfoVertex(node);
			double comp = CargaGrafo.haversine(info.getCoor(), val);
			if(comp < dist) {who = node;dist = comp;}
		}
		return who;
	}



	public void generarMapa(String titulo,ORArray<Edge<Double>> paint,Graph<Integer,VertexInfo,Double> g,HashTableSC<Integer,ORArray<Edge<Double>>> pintar, int m )
	{
		//System.out.println("cual es el sapo hp problema");
		Mapa2 example = new Mapa2(titulo);


		if(paint!=null)
		{
			//Inicial
			Edge<Double> primer=paint.getElement(0);
			//Final
			Edge<Double> ultimo=paint.getElement(paint.getSize()-1);

			//Primer nodo
			int one = primer.either();
			//Ultimo nodo
			int two = ultimo.either();

			Coordinates onee = grafo.getInfoVertex(grafo.translateInverse(one)).getCoor();
			Coordinates twoo = grafo.getInfoVertex(grafo.translateInverse(two)).getCoor();						
			double lat1 = onee.lat;
			double lon1 = onee.lon;
			double lat2 = twoo.lat;
			double lon2 = twoo.lon;

			example.generateMarker(new LatLng(lat1,lon1));
			example.generateMarker(new LatLng(lat2,lon2));

			for(Edge<Double> edg: paint) {
				one = edg.either();
				two = edg.other(one);
				onee = grafo.getInfoVertex(grafo.translateInverse(one)).getCoor();
				twoo = grafo.getInfoVertex(grafo.translateInverse(two)).getCoor();						
				lat1 = onee.lat;
				lon1 = onee.lon;
				lat2 = twoo.lat;
				lon2 = twoo.lon;
				example.generateSimplePath(new LatLng(lat1,lon1), new LatLng(lat2,lon2), false);			
			}
		}
		else if(g!=null && pintar==null) {

			Graph<Integer,VertexInfo,Double> ausar=g;
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
		else if(pintar!=null)
		{
			Graph<Integer,VertexInfo,Double> ausar=g;
			Iterator<Integer> it = pintar.keys();
			String[]colores= {"#e6194b", "#3cb44b", "#ffe119", "#4363d8", "#f58231", 
					"#911eb4", "#46f0f0", "#f032e6", "#bcf60c", "#fabebe", "#008080",
					"#e6beff", "#9a6324", "#fffac8", "#800000", "#aaffc3", "#808000", 
					"#ffd8b1", "#000075", "#808080", "#ffffff", "#000000"};
			for(int color = 1; it.hasNext();++color) {

				PoliceStation estacion=null;
				int ccomparendos=0;
				ORArray<Edge<Double>> thro =  pintar.get(it.next());



				PolylineOptions settingsLine=new PolylineOptions();
				settingsLine.setGeodesic(true);
				settingsLine.setStrokeColor(colores[color-1]);
				settingsLine.setStrokeOpacity(1.0);
				settingsLine.setStrokeWeight(2.0);


				example.setSettingsLine(settingsLine);

				for(Edge<Double> edg:  thro) {
					int one = edg.either();
					int ot = edg.other(one);

					VertexInfo v1=ausar.getInfoVertex(ausar.translateInverse(one));
					VertexInfo v2=ausar.getInfoVertex(ausar.translateInverse(ot));
					Coordinates onee = v1.getCoordinates();
					Coordinates twoo = v2.getCoordinates();
					double lat1 = onee.lat;
					double lon1 = onee.lon;
					double lat2 = twoo.lat;
					double lon2 = twoo.lon;
					ccomparendos+=v1.getInfractions().getSize()+v2.getInfractions().getSize();
					if(v1.getPoliceStation()!=-1)
					{
						estacion=estaciones.get(v1.getPoliceStation());
					}
					else if(v2.getPoliceStation()!=-1)
					{
						estacion=estaciones.get(v2.getPoliceStation());
					}
					if(ccomparendos<=m)
					{
						example.generateSimplePath(new LatLng(lat1,lon1), new LatLng(lat2,lon2), false);	
					}
					ccomparendos+=v1.getInfractions().getSize()+v2.getInfractions().getSize();
				}
				final int constante=250;
				double radio=(ccomparendos*100*constante)/comparendos.getSize();

				CircleOptions settingsCircle=new CircleOptions();
				settingsCircle.setFillColor(colores[color-1]);
				settingsCircle.setRadius(radio);
				settingsCircle.setFillOpacity(0.35);
				settingsCircle.setStrokeColor(colores[color-1]);
				example.setSettingsCircle(settingsCircle);
				example.generateMarker(new LatLng(estacion.getEPOLATITUD(),estacion.getEPOLONGITU()));
				example.generateArea(new LatLng(estacion.getEPOLATITUD(),estacion.getEPOLONGITU()), radio);
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
		generarMapa("Req 1A",paint,null,null,0);


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
		generarMapa("Req 1B",paint,null,null,0);

		System.out.println("terminando de calcular el camino optimo");
		System.out.println("tamanio de arcos "+ paint.getSize());

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

		//generarMapa("MST",null,g,null);
		System.out.println("Terminando de generar el grafo de los Arcos del MST");
		return g;
	}


	/**
	 * Method that takes the tree and prunes it to only show the edges and nodes related to the M 
	 * places with the most infractions
	 * @param m the amount of needed infractions
	 */
	public void ArbolMayorComparendos(int m) {
		Graph<Integer,VertexInfo,Double> g = MST();
		System.out.println("tamanio del arbol antes de ser limpiado, vertices " + g.V() + " edges "+ g.E());
		ORArray<PairComp<Integer, VertexInfo>> vertex = new ORArray<PairComp<Integer, VertexInfo>>();
		Iterator<Integer> it = g.vertices();
		while(it.hasNext()) {
			int val = it.next();
			vertex.add(new PairComp<Integer, VertexInfo>(val, g.getInfoVertex(val)));
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
		System.out.println("el nuevo numero de edges es: "+ aPintar.getSize());
		double costo = 0.0;
		for(Edge<Double> edg: aPintar) 
			costo += edg.getInfo();
		System.out.println("el costo del arbol es: "  + costo);
		System.out.println("Creando el nuevo arbol para apintar");
		Graph<Integer,VertexInfo,Double> send = new Graph<Integer,VertexInfo,Double>();
		for(Edge<Double> ed: aPintar) {
			int from = ed.either();
			int to = ed.other(from);
			send.addVertex(g.translateInverse(from), g.getInfoVertex(g.translateInverse(from)));
			send.addVertex(g.translateInverse(to), g.getInfoVertex(g.translateInverse(to)));
			send.addEdge(g.translateInverse(from), g.translateInverse(to), ed.getInfo());
		}
		System.out.println("Terminando de crear el nuevo arbol para apintar");
		generarMapa("Arbol mayor comparendos",null,send,null,0);
	}


	/**
	 * Genera el arbol con las m infracciones de mayor gravedad
	 * @param m la cantidad de infracciones de mayor gravedad
	 */
	public void ArbolMayorGravedad(int m) {
		Graph<Integer,VertexInfo,Double> g = MST();
		System.out.println("tamanio del arbol antes de ser limpiado, vertices " + g.V() + " edges "+ g.E());
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
		for(int i = infraccionesNodoGravedad.getSize()-1, j = 0; i > -1 && j < m;--i,++j) {
			//System.out.println("id de los nodos "+ infraccionesNodoGravedad.getElement(i).getSecond());
			needed.put(g.translate(infraccionesNodoGravedad.getElement(i).getSecond()), 1);
		}
		ORArray<Edge<Double>> aPintar = new ORArray<Edge<Double>>();
		System.out.println("Empezando a limpiar el arbol");
		while(needed.getSize() != 0) {
			ORArray<Edge<Double>> temp = Graph.pruneMST(g, needed);
			for(Edge<Double> edg: temp)
				aPintar.add(edg);
		}
		System.out.println("Terminando de limpiar el arbol");
		System.out.println("el nuevo numero de edges es: "+ aPintar.getSize());
		double costo = 0.0;
		for(Edge<Double> edg: aPintar) 
			costo += edg.getInfo();
		System.out.println("el costo del arbol es: "  + costo);
		Graph<Integer,VertexInfo,Double> send = new Graph<Integer,VertexInfo,Double>();
		for(Edge<Double> ed: aPintar) {
			int from = ed.either();
			int to = ed.other(from);
			send.addVertex(g.translateInverse(from), g.getInfoVertex(g.translateInverse(from)));
			send.addVertex(g.translateInverse(to), g.getInfoVertex(g.translateInverse(to)));
			send.addEdge(g.translateInverse(from), g.translateInverse(to), ed.getInfo());
		}
		System.out.println("Terminando de crear el nuevo arbol para apintar");

		generarMapa("Arbol mayor Gravedad",null,send,null,0);
		//System.out.println("el tamanio del grafo en nodos " + aPintar.getSize());

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
		for(int i = infraccionesNodoGravedad.getSize()-1, j = 0; i > -1 && j < m;--i,++j) {
			//	System.out.println("id de los nodos "+ infraccionesNodoGravedad.getElement(i).getSecond());
			needed.put(infraccionesNodoGravedad.getElement(i).getSecond(), 1);
		}
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
		generarMapa("Caminos cortos policía",aPintar,null,null,0);

	}

	/**
	 * Method that prints the connected components of infractions and police stations
	 * This is done by allocating the infractions to the closest
	 */
	public void PoliceStationComponents(int m) {
		System.out.println("asignando a cada estacion de policia a los "+m+ " comparendos más cercanos");
		Dijkstra caminos = new Dijkstra(this.grafo,nodosConEstaciones,false);
		System.out.println("finalizando de asignar a cada estacion de policia a los "+m+" comparendos más cercanos");
		System.out.println("empezando a generar grafo de distancia minimas");
		Graph<Integer,VertexInfo,Double> G = caminos.generateGraph();
		System.out.println("tamanio del grafo vertices "+ G.V() + " edges "+ G.E());
		System.out.println("terminando de generar grafo de distancia minimas");
		ORArray<PairComp<Integer, VertexInfo>> vertex = new ORArray<PairComp<Integer, VertexInfo>>();
		Iterator<Integer> it = G.vertices();
		while(it.hasNext()) {
			int val = it.next();
			vertex.add(new PairComp<Integer, VertexInfo>(val, grafo.getInfoVertex(val)));
		}
		HashTableSC<Integer, Integer> needed = new HashTableSC<Integer, Integer>(200);
		for(int i = 0; i < vertex.getSize();++i) 
			if(vertex.getElement(i).getSecond().getInfo2() != 0.0)
				needed.put(G.translate(vertex.getElement(i).getFirst()), 1);
		for(Integer val: nodosConEstaciones) 
			needed.put(G.translate(val), 1);
		ORArray<Edge<Double>> aPintar = new ORArray<Edge<Double>>();
		System.out.println("Empezando a limpiar el arbol");
		while(needed.getSize() != 0) {
			ORArray<Edge<Double>> temp = Graph.pruneMST(G, needed);
			for(Edge<Double> edg: temp)
				aPintar.add(edg);
			//System.out.println("tamanio needed: " + needed.getSize());
		}
		System.out.println("Terminando de limpiar el arbol");
		System.out.println("el nuevo numero de edges es: "+ aPintar.getSize());
		System.out.println("Generando de nuevo el grafo a pintar");
		Graph<Integer,VertexInfo,Double> grafoPintar = new Graph<Integer, VertexInfo, Double>();
		for(Edge<Double> edg: aPintar) {
			grafoPintar.addVertex(G.translateInverse(edg.either()),G.getInfoVertex(G.translateInverse(edg.either())));
			grafoPintar.addVertex(G.translateInverse(edg.other(edg.either())),G.getInfoVertex(G.translateInverse(edg.other(edg.either()))));
			grafoPintar.addEdge(G.translateInverse(edg.either()), G.translateInverse(edg.other(edg.either())), G.getInfoArc(G.translateInverse(edg.either()), G.translateInverse(edg.other(edg.either()))));
		}
		G = null;
		caminos = null;
		System.out.println("Terminando de generar el grafo a pintar");
		System.out.println("Empezando a generar los componentes conectados");
		HashTableSC<Integer,ORArray<Edge<Double>>> pintar = Graph.ConnectedComponent(grafoPintar);
		System.out.println("Terminando de generar los componentes conectados");
		generarMapa("Componentes estación de policía",null,grafoPintar,pintar,m);

	}

}

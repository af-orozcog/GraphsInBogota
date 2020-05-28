package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;

import model.data_structures.Graph;
import model.data_structures.HashTableLP;
import model.data_structures.ORArray;
import model.vo.Coordinates;
import model.vo.PoliceStation;
import model.vo.VertexInfo;

public class CargaGrafo {

	public static Graph<Integer,VertexInfo,Double> g = new Graph<Integer,VertexInfo,Double>();
	public static ORArray<Integer> nodosConEstaciones=new ORArray<Integer>();
	public static HashTableLP<Integer,Comparendo> comparendos=new HashTableLP<Integer,Comparendo>(250000);
	public static HashTableLP<Integer,PoliceStation> estaciones=new HashTableLP<Integer,PoliceStation>(21);
	public static double latmin;
	public static double latmax;
	public static double lonmin;
	public static double lonmax;
	public CargaGrafo()
	{
		//		cargarGrafo();
		try {
			loadJSON("./data/GRAFOBENDITO.json");
			cargarInfracciones();
			cargarEstaciones();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws IOException {
		//		File file=new File("./data/Vertices.txt");    //creates a new file instance  
		//		FileReader fr=new FileReader(file);   //reads the file  
		//		BufferedReader br=new BufferedReader(fr);  //creates a buffering character input stream  
		//		String line;
		//		while((line=br.readLine())!=null)  
		//		{  
		//			String temp[] = line.split(",");
		//			Coordinates coordenadas=new Coordinates( Double.parseDouble(temp[1]), Double.parseDouble(temp[2]));
		//			VertexInfo vertexInfo=new VertexInfo(coordenadas,Integer.parseInt(temp[0]));
		//			g.addVertex(Integer.parseInt(temp[0]), vertexInfo);
		//		}  
		//		fr.close();    //closes the stream and release the resources  
		//		File file2=new File("./data/Arcos.txt");    //creates a new file instance  
		//		FileReader fr2=new FileReader(file2);   //reads the file  
		//		BufferedReader br2=new BufferedReader(fr2);  //creates a buffering character input stream 
		//		while((line=br2.readLine())!=null)  
		//		{  
		//			if(line.contains("#"))
		//				continue;
		//			String temp[] = line.split(" ");
		//			int f = Integer.parseInt(temp[0]);
		//			VertexInfo informacion=(VertexInfo) g.getInfoVertex(f), informacion2;
		//			Coordinates v =  (Coordinates) informacion.getCoordinates();
		//			for(int i = 1; i<temp.length; ++i) {
		//				int tempp = Integer.parseInt(temp[i]);
		//				informacion2=(VertexInfo) g.getInfoVertex(tempp);
		//				Coordinates v2 =  (Coordinates) informacion2.getCoordinates() ;
		//
		//				Double x1 = v.lat - v2.lat; 
		//				x1 = x1*x1;
		//				Double y1 = v.lon - v2.lon; 
		//				y1 = y1*y1;
		//				final int R = 6371; // Radio de la tierra
		//
		//				Double latDistance = toRad(v2.lat-v.lat);
		//				Double lonDistance = toRad(v2.lon-v.lon);
		//
		//				Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + 
		//						Math.cos(toRad(v.lat)) * Math.cos(toRad(v.lat)) * 
		//						Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		//
		//				Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		//				Double distance = R * c;
		//				g.addEdge(f, tempp, distance);
		//			}
		//		}
		//		br2.close();
		//		cargarInfracciones();




		//Para guardar el grafo
		//		cargarGrafo();
		//		cargarInfracciones();
		//		saveJSON("./data/GRAFOBENDITO.json");
		//		cargarEstaciones();
		//		try {
		//			loadJSON("./data/GRAFOBENDITO.json");
		//		} catch (Exception e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}


		//				System.out.println("Escribiendo");
		//				Gson gson = new Gson();
		//				String url = "./data/grafo.json";
		//				try{
		//					FileWriter fileWriter = new FileWriter(new File(url), true);
		//		
		//					Iterator<Integer> iter=g.vertices();
		//					while (iter.hasNext()) {
		//						Integer v = (Integer) iter.next();
		//						VertexInfo v2 =  (VertexInfo) g.getInfoVertex(v);
		//						fileWriter.write(gson.toJson(v2));
		//					}
		//					fileWriter.close();
		//				}
		//				catch(Exception e){System.err.println("error en la escritura del archivo JSON");
		//				System.out.println(e.getMessage());}
		//				System.out.println("Escrito");

	}

	public static Double haversine(Coordinates v, Coordinates v2)
	{
		Double x1 = v.lat - v2.lat; 
		x1 = x1*x1;
		Double y1 = v.lon - v2.lon; 
		y1 = y1*y1;
		final int R = 6371; // Radio de la tierra

		Double latDistance = toRad(v2.lat-v.lat);
		Double lonDistance = toRad(v2.lon-v.lon);

		Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + 
				Math.cos(toRad(v.lat)) * Math.cos(toRad(v.lat)) * 
				Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

		Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		Double distance = R * c;
		return distance;
	}




	private static void saveJSON(String rutaArchivo) {
		JSONArray grafo = new JSONArray();
		Iterator<Integer> it1 = g.vertices();
		while(it1.hasNext()) {
			Integer id1 = it1.next();	
			VertexInfo info= (VertexInfo) g.getInfoVertex(id1);
			Coordinates coor=info.getCoordinates();
			JSONObject vertice = new JSONObject();
			vertice.put("id", id1);
			vertice.put("lon", coor.lat);
			vertice.put("lat", coor.lon);

			Iterator<Integer> it2 = g.adj(id1);
			JSONArray adj = new JSONArray();
			while(it2.hasNext()) {
				adj.add(it2.next().toString());
			}
			vertice.put("adj", adj);

			JSONArray infractions = new JSONArray();
			Iterator<Integer> it3 = info.getInfractions().iterator();
			while(it3.hasNext()) {
				infractions.add(it3.next().toString());
			}
			vertice.put("infractions", infractions);
			vertice.put("station", info.getPoliceStation());
			grafo.add(vertice);
		}
		try (FileWriter file = new FileWriter(rutaArchivo)) {

			file.write(grafo.toJSONString());
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}




	public static void loadJSON(String rutaArchivo) throws Exception
	{
		try {
			FileReader nm = new FileReader(rutaArchivo);
			JSONParser parser = new JSONParser();
			JSONArray array = (JSONArray) parser.parse(nm);
			boolean primero=true;
			for(Object o1 : array) {
				JSONObject vertice = (JSONObject) o1;
				Coordinates coor = new Coordinates(
						Double.parseDouble(vertice.get("lat").toString()), 
						Double.parseDouble(vertice.get("lon").toString()));

				if (primero)
				{
					latmin=coor.lat;
					latmax=coor.lat;
					lonmin=coor.lon;
					lonmax=coor.lon;
					primero=false;
				}
				else {
					if(coor.lat<latmin)
						latmin=coor.lat;
					if (coor.lat>latmax)
						latmax=coor.lat;
					if(coor.lon<lonmin)
						lonmin=coor.lon;
					if(coor.lon>lonmax)
						lonmax=coor.lon;
				}

				VertexInfo cont = new VertexInfo(coor,Integer.parseInt(vertice.get("id").toString()));

				JSONArray inf = (JSONArray) vertice.get("infractions");


				for(Object o2 : inf) {
					Integer infra = Integer.parseInt(o2.toString());
					cont.addInfraction(infra);;
				}

				cont.addPoliceStation(Integer.parseInt(vertice.get("station").toString()));
				if(!vertice.get("station").toString().equals("-1"))
				{
					nodosConEstaciones.add(Integer.parseInt(vertice.get("id").toString()));
				}

				Integer id = Integer.parseInt(vertice.get("id").toString());
				g.addVertex(id, cont);

				JSONArray adj = (JSONArray) vertice.get("adj");
				for(Object o2 : adj) {
					Integer ady = Integer.parseInt(o2.toString());
					if(g.getInfoVertex(ady) != null) {
						VertexInfo segundo=(VertexInfo) g.getInfoVertex(ady);
						g.addEdge(id, ady, haversine(coor, segundo.getCoordinates()));
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();	
		}
	}




	private static void cargarInfracciones()
	{	
		Gson gson=new Gson();

		try {

			BufferedReader br = new BufferedReader(new FileReader("./data/comparendos.geojson"));

			//convert the json string back to object
			Listado obj = gson.fromJson(br, Listado.class);
			for (Features feature : obj.getInfo().getFeatures()) {
				Comparendo comparendo=feature.getComparendo();
				Double latitud=feature.getGeometry().getCoordinates()[0];
				Double longitud=feature.getGeometry().getCoordinates()[1];
				Coordinates coordenada=new Coordinates(latitud,longitud); 
				if(comparendo.getCLASE_VEHICULO().equals("AUTOMÓVIL"))
					comparendo.setCLASE_VEHICULO("AUTOMOVIL");
				comparendos.put(comparendo.OBJECTID, comparendo);
				//				Integer idVertice= 0;
				//
				//				VertexInfo info =  (VertexInfo) g.getInfoVertex(idVertice);
				//				Coordinates coorver=info.getCoordinates();
				//
				//				Double minima=haversine(coordenada,coorver);
				//
				//
				//				Iterator<Integer> iter=g.vertices();
				//
				//
				//				while (iter.hasNext()) {
				//					Integer v = (Integer) iter.next();
				//					info =  (VertexInfo) g.getInfoVertex(v);
				//					coorver=info.getCoordinates();
				//					if(haversine(coordenada,coorver)<minima)
				//					{
				//						idVertice=v;
				//						minima=haversine(coordenada,coorver);
				//					}
				//				}
				//				VertexInfo infoC=(VertexInfo) g.getInfoVertex(idVertice);
				//				infoC.addInfraction(comparendo.OBJECTID);
			}


		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	private static void cargarEstaciones()
	{	
		Gson gson=new Gson();

		try {

			BufferedReader br = new BufferedReader(new FileReader("./data/estaciones.geojson"));

			//convert the json string back to object
			Lista obj = gson.fromJson(br, Lista.class);
			for (Features2 feature : obj.getInfo().getFeatures()) {
				PoliceStation estacion=feature.getEstacion();
				estaciones.put(estacion.getOBJECTID(), estacion);
			}


		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static Double toRad(Double value) {
		return value * Math.PI / 180;
	}

	private static void cargarGrafo()
	{
		Gson gson=new Gson();

		try {

			BufferedReader br = new BufferedReader(new FileReader("./data/grafoCompleto2.json"));
			Vertice[] obj = gson.fromJson(br, Vertice[].class);


			for (Vertice vertice : obj) {
				Coordinates coordenadas=new Coordinates(vertice.lat,vertice.lon);
				VertexInfo vertexInfo=new VertexInfo(coordenadas,vertice.indice);
				g.addVertex(vertice.indice, vertexInfo);
				for (Arco arco : vertice.edges) {
					g.addEdge(arco.lFrom, arco.lTo, arco.millas);
				}

				VertexInfo actualizador=(VertexInfo) g.getInfoVertex(vertice.indice);

				for(conEsta2 comp:vertice.comparendos)
				{
					actualizador.addInfraction(comp.properties.OBJECTID);
				}

				if(vertice.estaciones.length>0)
				{
					actualizador.addPoliceStation(vertice.getEstaciones()[0].properties.getOBJECTID());
				}
				else
				{
					actualizador.addPoliceStation(-1);

				}
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}

//Para cargar grafo
class Vertice{
	int indice;
	Double lat;
	Double lon;
	@Override
	public String toString() {
		return "Vertice [indice=" + indice + ", lat=" + lat + ", lon=" + lon + ", edges=" + Arrays.toString(edges)
		+ ", comparendos=" + Arrays.toString(comparendos) + ", estaciones=" + Arrays.toString(estaciones) + "]";
	}
	public int getIndice() {
		return indice;
	}
	public void setIndice(int indice) {
		this.indice = indice;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public Double getLon() {
		return lon;
	}
	public void setLon(Double lon) {
		this.lon = lon;
	}
	public Arco[] getEdges() {
		return edges;
	}
	public void setEdges(Arco[] edges) {
		this.edges = edges;
	}
	public conEsta2[] getComparendos() {
		return comparendos;
	}
	public void setComparendos(conEsta2[] comparendos) {
		this.comparendos = comparendos;
	}
	public conEsta[] getEstaciones() {
		return estaciones;
	}
	public void setEstaciones(conEsta[] estaciones) {
		this.estaciones = estaciones;
	}
	Arco[] edges;
	conEsta2[] comparendos;
	conEsta[] estaciones;
}
class Arco{
	Double millas;
	int lFrom;
	@Override
	public String toString() {
		return "Arco [millas=" + millas + ", IFrom=" + lFrom + ", ITo=" + lTo + "]";
	}
	public Double getMillas() {
		return millas;
	}
	public void setMillas(Double millas) {
		this.millas = millas;
	}
	public int getlFrom() {
		return lFrom;
	}
	public void setlFrom(int lFrom) {
		this.lFrom = lFrom;
	}
	public int getlTo() {
		return lTo;
	}
	public void setlTo(int lTo) {
		this.lTo = lTo;
	}
	int lTo;
}

class conEsta2{
	String type;
	@Override
	public String toString() {
		return "conEsta2 [type=" + type + ", geometry=" + geometry + ", properties=" + properties + "]";
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Geometrias2 getGeometry() {
		return geometry;
	}
	public void setGeometry(Geometrias2 geometry) {
		this.geometry = geometry;
	}
	public Comparendo getProperties() {
		return properties;
	}
	public void setProperties(Comparendo properties) {
		this.properties = properties;
	}
	Geometrias2 geometry;
	Comparendo properties;
}

class Geometrias2
{
	String type;
	Double[] coordinates;
	@Override
	public String toString() {
		return "Geometrias2 [type=" + type + ", coordinates=" + Arrays.toString(coordinates) + "]";
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Double[] getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(Double[] coordinates) {
		this.coordinates = coordinates;
	}
}



class conEsta{
	String type;
	int id;
	@Override
	public String toString() {
		return "conEsta [type=" + type + ", id=" + id + ", geometry=" + geometry + ", properties=" + properties + "]";
	}
	Geometrias geometry;
	PoliceStation properties;

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Geometrias getGeometry() {
		return geometry;
	}
	public void setGeometry(Geometrias geometry) {
		this.geometry = geometry;
	}
	public PoliceStation getProperties() {
		return properties;
	}
	public void setProperties(PoliceStation properties) {
		this.properties = properties;
	}
}

class Geometrias
{
	String type;
	Double[] coordinates;
	@Override
	public String toString() {
		return "Geometrias [type=" + type + ", coordinates=" + Arrays.toString(coordinates) + "]";
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Double[] getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(Double[] coordinates) {
		this.coordinates = coordinates;
	}


}

//Para cargar Comparendos
class Listado {
	Informacion listado;
	public Informacion getInfo()
	{
		return this.listado;
	}
	@Override
	public String toString() {
		return "Listado [listado=" + listado + "]";
	}
}

class Informacion{
	String type;
	String name;
	Crs crs;
	Features[] features;
	public Features[] getFeatures() {
		return features;
	}
}

class Crs{
	String type;
	Properties properties;
}
class Properties{
	String name;
}

class Features{
	String type;
	Comparendo properties;
	Geometry geometry;
	public Comparendo getComparendo()
	{
		return properties;
	}
	public Geometry getGeometry()
	{
		return geometry;
	}
}

class Geometry{
	String type;
	Double[]coordinates;
	public Double[] getCoordinates() {
		return coordinates;
	}
}


//
//Para cargar Estaciones
class Lista {
	Informacion2 lista;
	public Informacion2 getInfo()
	{
		return this.lista;
	}
	@Override
	public String toString() {
		return "Lista [lista=" + lista + "]";
	}
}

class Informacion2{
	String type;
	Crs crs;
	Features2[] features;
	public Features2[] getFeatures() {
		return features;
	}
}

class Features2{
	String type;
	PoliceStation properties;
	Geometry geometry;
	public PoliceStation getEstacion()
	{
		return properties;
	}
	public Geometry getGeometry()
	{
		return geometry;
	}
}




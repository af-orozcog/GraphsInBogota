package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.data_structures.Edge;
import model.data_structures.Graph;
import model.data_structures.VertexContent;
import model.vo.Coordinates;
import model.vo.VertexInfo;

public class CargaGrafo {

	static Graph g = new Graph();

	public static void main(String[] args) throws IOException {
		File file=new File("./data/Vertices.txt");    //creates a new file instance  
		FileReader fr=new FileReader(file);   //reads the file  
		BufferedReader br=new BufferedReader(fr);  //creates a buffering character input stream  
		String line;
		while((line=br.readLine())!=null)  
		{  
			String temp[] = line.split(",");
			Coordinates coordenadas=new Coordinates( Double.parseDouble(temp[1]), Double.parseDouble(temp[2]));
			VertexInfo vertexInfo=new VertexInfo(coordenadas,Integer.parseInt(temp[0]));
			g.addVertex(Integer.parseInt(temp[0]), vertexInfo);
		}  
		fr.close();    //closes the stream and release the resources  
		File file2=new File("./data/Arcos.txt");    //creates a new file instance  
		FileReader fr2=new FileReader(file2);   //reads the file  
		BufferedReader br2=new BufferedReader(fr2);  //creates a buffering character input stream 
		while((line=br2.readLine())!=null)  
		{  
			if(line.contains("#"))
				continue;
			String temp[] = line.split(" ");
			int f = Integer.parseInt(temp[0]);
			VertexInfo informacion=(VertexInfo) g.getInfoVertex(f), informacion2;
			Coordinates v =  (Coordinates) informacion.getCoordinates();
			for(int i = 1; i<temp.length; ++i) {
				int tempp = Integer.parseInt(temp[i]);
				informacion2=(VertexInfo) g.getInfoVertex(tempp);
				Coordinates v2 =  (Coordinates) informacion2.getCoordinates() ;

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
				g.addEdge(f, tempp, distance);
			}
		}
		br2.close();
		cargarInfracciones();
		
		System.out.println("Escribiendo");
		Gson gson = new Gson();
		String url = "./data/grafo.json";
		try{
			FileWriter fileWriter = new FileWriter(new File(url), true);
			String jsonString;
			Iterator<Integer> iter=g.vertices();
			while (iter.hasNext()) {
				Integer v = (Integer) iter.next();
				VertexInfo v2 =  (VertexInfo) g.getInfoVertex(v);
				fileWriter.write(gson.toJson(v2));
			}
			fileWriter.close();
		}
		catch(Exception e){System.err.println("error en la escritura del archivo JSON");
		System.out.println(e.getMessage());}
		System.out.println("Escrito");

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
				
				Integer idVertice= 0;
				
				VertexInfo info =  (VertexInfo) g.getInfoVertex(idVertice);
				Coordinates coorver=info.getCoordinates();
				
				Double minima=haversine(coordenada,coorver);
				
				
				Iterator<Integer> iter=g.vertices();
				
				
				while (iter.hasNext()) {
					Integer v = (Integer) iter.next();
					info =  (VertexInfo) g.getInfoVertex(v);
					coorver=info.getCoordinates();
					if(haversine(coordenada,coorver)<minima)
					{
						idVertice=v;
						minima=haversine(coordenada,coorver);
					}
				}
				VertexInfo infoC=(VertexInfo) g.getInfoVertex(idVertice);
				infoC.addInfraction(comparendo.OBJECTID);
			}

			VertexInfo infoC=(VertexInfo) g.getInfoVertex(0);
			System.out.println(infoC.getInfractions());
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static Double toRad(Double value) {
		return value * Math.PI / 180;
	}

}
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


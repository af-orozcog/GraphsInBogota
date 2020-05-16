package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.data_structures.Edge;
import model.data_structures.Graph;
import model.data_structures.VertexContent;
import model.vo.Coordinates;

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
			g.addVertex(Integer.parseInt(temp[0]), coordenadas);
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
			Coordinates v =  (Coordinates) g.getInfoVertex(f), v2;
			for(int i = 1; i<temp.length; ++i) {
				int tempp = Integer.parseInt(temp[i]);
				v2 =  (Coordinates) g.getInfoVertex(tempp);

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
		System.out.println("Escribiendo");
		Gson gson = new Gson();
		String url = "./data/grafo.json";
		try{
			FileWriter fileWriter = new FileWriter(new File(url), true);
			String jsonString;
			Iterator<Integer> iter=g.vertices();
			while (iter.hasNext()) {
				Integer v = (Integer) iter.next();
				fileWriter.write(gson.toJson(v));
			}
			fileWriter.close();
		}
		catch(Exception e){System.err.println("error en la escritura del archivo JSON");}
		System.out.println("Escrito");

	}

	private static Double toRad(Double value) {
		return value * Math.PI / 180;
	}

}


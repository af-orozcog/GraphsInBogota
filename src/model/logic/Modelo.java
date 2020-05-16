package model.logic;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import model.Comparendo;
import model.data_structures.ILinkedList;
import model.data_structures.Node;

import model.data_structures.ShellSort;
import model.data_structures.QuickSort;
import model.data_structures.MergeSort;
import model.data_structures.LinkedList;


/**
 * Definicion del modelo del mundo
 *
 */
public class Modelo {

	public static final String SHELLSORT = "ShellSort";
	public static final String QUICKSORT = "QuickSort";
	public static final String MERGESORT = "MergeSort";
	public static String PATH = "./data/comparendos_dei_2018.geojson";
	//	public static String PATH = "./data/comparendos_dei_2018.geojson";
	public ILinkedList<Comparendo> linkedList;
	public Comparendo[] lista=null;
	public Comparable<Comparendo>[] cargarlinkedList() {



		

		JsonReader reader;
		try {
			reader = new JsonReader(new FileReader(PATH));
			JsonElement elem = JsonParser.parseReader(reader);
			JsonArray e2 = elem.getAsJsonObject().get("features").getAsJsonArray();
			lista=new Comparendo[e2.size()];

			SimpleDateFormat parser=new SimpleDateFormat("yyyy/MM/dd");
			int i=0;
			for(JsonElement e: e2) {
				int OBJECTID = e.getAsJsonObject().get("properties").getAsJsonObject().get("OBJECTID").getAsInt();

				String s = e.getAsJsonObject().get("properties").getAsJsonObject().get("FECHA_HORA").getAsString();	
				Date FECHA_HORA = parser.parse(s); 

				String MEDIO_DETE = e.getAsJsonObject().get("properties").getAsJsonObject().get("MEDIO_DETE").getAsString();
				String CLASE_VEHI = e.getAsJsonObject().get("properties").getAsJsonObject().get("CLASE_VEHI").getAsString();
				String TIPO_SERVI = e.getAsJsonObject().get("properties").getAsJsonObject().get("TIPO_SERVI").getAsString();
				String INFRACCION = e.getAsJsonObject().get("properties").getAsJsonObject().get("INFRACCION").getAsString();
				String DES_INFRAC = e.getAsJsonObject().get("properties").getAsJsonObject().get("DES_INFRAC").getAsString();	
				String LOCALIDAD = e.getAsJsonObject().get("properties").getAsJsonObject().get("LOCALIDAD").getAsString();

				double longitud = e.getAsJsonObject().get("geometry").getAsJsonObject().get("coordinates").getAsJsonArray()
						.get(0).getAsDouble();

				double latitud = e.getAsJsonObject().get("geometry").getAsJsonObject().get("coordinates").getAsJsonArray()
						.get(1).getAsDouble();

				Comparendo c = new Comparendo(OBJECTID, FECHA_HORA, DES_INFRAC, MEDIO_DETE, CLASE_VEHI, TIPO_SERVI, INFRACCION, LOCALIDAD, longitud, latitud);
				
				lista[i]=c;
				i++;

			}

		} catch (FileNotFoundException | ParseException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return lista;	

	}
	public  Comparendo[] dearreglo()
	{
		
		Comparendo[] array1 = (Comparendo[]) cargarlinkedList();
		Comparendo[] array = new Comparendo[lista.length];
		for(int i =0 ;i< array1.length; i++ )
		{
			array[i] =  array1[i];
			
		}
		return array;
	}

	public long sortingBenchmarkOptionA(Comparable[] array, String sort)
	{
		switch(sort)
		{
		case SHELLSORT:
			long shellStart = System.currentTimeMillis();
			ShellSort.sort(array);
			long shellEnd = System.currentTimeMillis();
			return shellEnd - shellStart;
		case QUICKSORT:
			long quickStart = System.currentTimeMillis();
			QuickSort.sort(array);
			long quickEnd = System.currentTimeMillis();
			return quickEnd - quickStart;
		case MERGESORT:
			long mergeStart = System.currentTimeMillis();
			MergeSort.sort(array);
			long mergeEnd = System.currentTimeMillis();
			return mergeEnd - mergeStart;
		}
		return 0;
	}

}

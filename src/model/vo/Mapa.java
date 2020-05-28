package model.vo;
import com.teamdev.jxmaps.LatLng;
import com.teamdev.jxmaps.Map;
import com.teamdev.jxmaps.MapReadyHandler;
import com.teamdev.jxmaps.MapStatus;
import com.teamdev.jxmaps.Marker;
import com.teamdev.jxmaps.Polyline;
import com.teamdev.jxmaps.PolylineOptions;
import com.teamdev.jxmaps.swing.MapView;

import model.data_structures.*;

import com.teamdev.jxmaps.Circle;
import com.teamdev.jxmaps.LatLngBounds;
import com.teamdev.jxmaps.CircleOptions;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
/**
 * Clase que representa un mapa generado a partir de un grafo
 * @author nicot
 */
public class Mapa extends MapView
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Grafo a graficar en el mapa
	 */
	private Graph<Integer, VertexInfo, Double> mapa;
	/**
	 * Contructor de la clase
	 * @param grafo2
	 */
	public Mapa(Graph<Integer, VertexInfo, Double> grafo2,ORArray<Edge<Double>> paint,LatLng min,LatLng max) {
		mapa=grafo2;
		setOnMapReadyHandler(new MapReadyHandler() 
		{
			@Override
			public void onMapReady(MapStatus status) 
			{
				// Check if the map is loaded correctly
				if (status == MapStatus.MAP_STATUS_OK) 
				{
					// Getting the associated map objec
					final Map map = getMap();


					//Contador de vertices
					int n = 0;
					ArrayList<Polyline> arcos = new ArrayList<Polyline>();					


					//Opciones para imprimir el mapa.
					PolylineOptions options = new PolylineOptions();
					// Setting geodesic property value
					options.setGeodesic(true);
					// Setting stroke color value
					options.setStrokeColor("#36D61C");
					// Setting stroke opacity value
					options.setStrokeOpacity(1.0);
					// Setting stroke weight value
					options.setStrokeWeight(2.0);
					// Applying options to the polyline
					CircleOptions op = new CircleOptions();
					op.setFillColor("#EC2C03");
					op.setStrokeColor("#EC2C03");
					//op.setStrokeOpacity(1.0);
					op.setRadius(2);
					ArrayList<Circle> circulos = new ArrayList<Circle>();
					for(Edge<Double> edg: paint) {
						int one = edg.either();
						int two = edg.other(one);
						Coordinates onee = mapa.getInfoVertex(mapa.translateInverse(one)).getCoor();
						Coordinates twoo = mapa.getInfoVertex(mapa.translateInverse(two)).getCoor();						
						double lat1 = onee.lat;
						double lon1 = onee.lon;

						double lat2 = twoo.lat;
						double lon2 = twoo.lon;

						if(n <paint.getSize() && lat1 >= min.getLat() && lat1 <= max.getLat() && lon1 >= min.getLng() && lon1 <= max.getLng()
								&& lat2 >= min.getLat() && lat2 <= max.getLat() && lon2 >= min.getLng() && lon2 <= max.getLng()) 
						{


							if(n==0) {
								Marker marker=new Marker(map);
								marker.setPosition(new LatLng(lat1,lon1));
							}
							if(n==paint.getSize()-1)
							{
								Marker marker2=new Marker(map);
								marker2.setPosition(new LatLng(lat2,lon2));
							}
							LatLng[] path = {new LatLng(lat1,lon1),new LatLng(lat2,lon2)};
							arcos.add(new Polyline(map));
							arcos.get(arcos.size()-1).setPath(path);
							arcos.get(arcos.size()-1).setOptions(options);
							circulos.add(new Circle(map));
							circulos.get(circulos.size()-1).setCenter(new LatLng(lat1,lon1));
							circulos.get(circulos.size()-1).setOptions(op);
							circulos.add(new Circle(map));
							circulos.get(circulos.size()-1).setCenter(new LatLng(lat2,lon2));
							circulos.get(circulos.size()-1).setOptions(op);
							n++;
						}
					}
					map.fitBounds(new LatLngBounds(min,max));
					// Setting initial zoom value
					map.setZoom(14);
				}
			}
		});
	}

	public Mapa(Graph<Integer, VertexInfo, Double> grafo2,LatLng min,LatLng max,Boolean hacerSoloVertices, Graph<Long, VertexInfo, Double> pGrafoAdicional) 
	{
		mapa = grafo2;
		setOnMapReadyHandler(new MapReadyHandler() 
		{
			@Override
			public void onMapReady(MapStatus status) 
			{
				if(!hacerSoloVertices)
				{
					// Check if the map is loaded correctly
					if (status == MapStatus.MAP_STATUS_OK) 
					{
						// Getting the associated map objec
						final Map map = getMap();
						Iterator<Integer> vertices = mapa.vertices();
						//Contador de vertices
						int n = 0;
						ArrayList<Polyline> arcos = new ArrayList<Polyline>();
						Iterator<Edge<Double>> colaArcos = mapa.edges().iterator();
						//Opciones para imprimir el mapa.
						PolylineOptions options = new PolylineOptions();
						// Setting geodesic property value
						options.setGeodesic(true);
						// Setting stroke color value
						options.setStrokeColor("#36D61C");
						// Setting stroke opacity value
						options.setStrokeOpacity(1.0);
						// Setting stroke weight value
						options.setStrokeWeight(2.0);
						// Applying options to the polyline
						CircleOptions op = new CircleOptions();
						op.setFillColor("#EC2C03");
						op.setStrokeColor("#EC2C03");
						//op.setStrokeOpacity(1.0);
						op.setRadius(2);
						ArrayList<Circle> circulos = new ArrayList<Circle>();

						while(colaArcos.hasNext()) 
						{

							Edge<Double> arc = colaArcos.next();
							VertexInfo info1=(VertexInfo)mapa.getInfoVertex(mapa.translateInverse(arc.either()));
							double lat1 = info1.getCoordinates().lat;
							double lon1 = info1.getCoordinates().lon;
							VertexInfo info2=(VertexInfo)mapa.getInfoVertex(mapa.translateInverse(arc.other(arc.either())));

							double lat2 = info2.getCoordinates().lat;
							double lon2 = info2.getCoordinates().lon;


							if(n <mapa.V() && lat1 >= min.getLat() && lat1 <= max.getLat() && lon1 >= min.getLng() && lon1 <= max.getLng()
									&& lat2 >= min.getLat() && lat2 <= max.getLat() && lon2 >= min.getLng() && lon2 <= max.getLng()) 
							{

								LatLng[] path = {new LatLng(lat1,lon1),new LatLng(lat2,lon2)};

								arcos.add(new Polyline(map));
								arcos.get(arcos.size()-1).setPath(path);
								arcos.get(arcos.size()-1).setOptions(options);

//								circulos.add(new Circle(map));
//								circulos.get(circulos.size()-1).setCenter(new LatLng(lat1,lon1));
//								circulos.get(circulos.size()-1).setOptions(op);
//								circulos.add(new Circle(map));
//								circulos.get(circulos.size()-1).setCenter(new LatLng(lat2,lon2));
//								circulos.get(circulos.size()-1).setOptions(op);


								n++;
							}
						}

						if(pGrafoAdicional != null) {

							vertices = mapa.vertices();
							//Contador de vertices
							n = 0;
							colaArcos = mapa.edges().iterator();
							//Opciones para imprimir el mapa.
							// Setting geodesic property value
							options.setGeodesic(true);
							// Setting stroke color value
							options.setStrokeColor("#C929E3");
							// Setting stroke opacity value
							options.setStrokeOpacity(1.0);
							// Setting stroke weight value
							options.setStrokeWeight(4.0);
							// Applying options to the polyline
							op.setFillColor("#13D3FC");
							op.setStrokeColor("#13D3FC");
							//op.setStrokeOpacity(1.0);
							op.setRadius(4);

							while(colaArcos.hasNext()) 
							{
								Edge<Double> arc = colaArcos.next();
								VertexInfo info1=(VertexInfo)mapa.getInfoVertex(mapa.translateInverse(arc.either()));
								double lat1 = info1.getCoordinates().lat;
								double lon1 = info1.getCoordinates().lon;
								VertexInfo info2=(VertexInfo)mapa.getInfoVertex(mapa.translateInverse(arc.other(arc.either())));

								double lat2 = info2.getCoordinates().lat;
								double lon2 = info2.getCoordinates().lon;


								if(n <mapa.V()&& lat1 >= min.getLat() && lat1 <= max.getLat() && lon1 >= min.getLng() && lon1 <= max.getLng()
										&& lat2 >= min.getLat() && lat2 <= max.getLat() && lon2 >= min.getLng() && lon2 <= max.getLng()) 
								{
									LatLng[] path = {new LatLng(lat1,lon1),new LatLng(lat2,lon2)};
									arcos.add(new Polyline(map));
									arcos.get(arcos.size()-1).setPath(path);
									arcos.get(arcos.size()-1).setOptions(options);
									circulos.add(new Circle(map));
									circulos.get(circulos.size()-1).setCenter(new LatLng(lat1,lon1));
									circulos.get(circulos.size()-1).setOptions(op);
									circulos.add(new Circle(map));
									circulos.get(circulos.size()-1).setCenter(new LatLng(lat2,lon2));
									circulos.get(circulos.size()-1).setOptions(op);
									n++;
								}
							}
						}
						//						System.out.println("Numero de arcos graficados:: "+n);
						map.fitBounds(new LatLngBounds(min,max));
						// Setting initial zoom value
						map.setZoom(14);
					}
				}
				else
				{
					if (status == MapStatus.MAP_STATUS_OK) 
					{
						// Getting the associated map objec
						final Map map = getMap();
						Iterator<Integer> vertices = mapa.vertices();
						//Contador de vertices
						int n = 0;
						// Applying options to the polyline
						CircleOptions op = new CircleOptions();
						op.setFillColor("#EC2C03");
						op.setStrokeColor("#EC2C03");
						//op.setStrokeOpacity(1.0);
						op.setRadius(2);
						ArrayList<Circle> circulos = new ArrayList<Circle>();

						while(vertices.hasNext())
						{
							Integer id = vertices.next();
							VertexInfo info1=(VertexInfo)mapa.getInfoVertex(id);
							double lat1 = info1.getCoordinates().lat;
							double lon1 = info1.getCoordinates().lon;

							if(n <mapa.E()&& lat1 >= min.getLat() && lat1 <= max.getLat() && lon1 >= min.getLng() && lon1 <= max.getLng()) 
							{
								circulos.add(new Circle(map));
								circulos.get(circulos.size()-1).setCenter(new LatLng(lat1,lon1));
								circulos.get(circulos.size()-1).setOptions(op);
								n++;
							}
						}

						if(pGrafoAdicional != null) {

							ArrayList<Polyline> arcos = new ArrayList<Polyline>();
							Iterator<Edge<Double>> colaArcos = mapa.edges().iterator();
							PolylineOptions options = new PolylineOptions();

							//Contador de vertices
							n = 0;
							colaArcos = mapa.edges().iterator();
							//Opciones para imprimir el mapa.
							// Setting geodesic property value
							options.setGeodesic(true);
							// Setting stroke color value
							options.setStrokeColor("#C929E3");
							// Setting stroke opacity value
							options.setStrokeOpacity(1.0);
							// Setting stroke weight value
							options.setStrokeWeight(4.0);
							// Applying options to the polyline
							op.setFillColor("#13D3FC");
							op.setStrokeColor("#13D3FC");
							//op.setStrokeOpacity(1.0);
							op.setRadius(4);

							while(colaArcos.hasNext()) 
							{
								Edge<Double> arc = colaArcos.next();
								VertexInfo info1=(VertexInfo)mapa.getInfoVertex(mapa.translateInverse(arc.either()));
								double lat1 = info1.getCoordinates().lat;
								double lon1 = info1.getCoordinates().lon;
								VertexInfo info2=(VertexInfo)mapa.getInfoVertex(mapa.translateInverse(arc.other(arc.either())));

								double lat2 = info2.getCoordinates().lat;
								double lon2 = info2.getCoordinates().lon;

								if(n <2000&& lat1 >= min.getLat() && lat1 <= max.getLat() && lon1 >= min.getLng() && lon1 <= max.getLng()
										&& lat2 >= min.getLat() && lat2 <= max.getLat() && lon2 >= min.getLng() && lon2 <= max.getLng()) 
								{
									LatLng[] path = {new LatLng(lat1,lon1),new LatLng(lat2,lon2)};
									arcos.add(new Polyline(map));
									arcos.get(arcos.size()-1).setPath(path);
									arcos.get(arcos.size()-1).setOptions(options);
									circulos.add(new Circle(map));
									circulos.get(circulos.size()-1).setCenter(new LatLng(lat1,lon1));
									circulos.get(circulos.size()-1).setOptions(op);
									circulos.add(new Circle(map));
									circulos.get(circulos.size()-1).setCenter(new LatLng(lat2,lon2));
									circulos.get(circulos.size()-1).setOptions(op);
									n++;
								}
							}
						}

						//					System.out.println("Numero de arcos graficados:: "+n);
						map.fitBounds(new LatLngBounds(min,max));
						// Setting initial zoom value
						map.setZoom(15);
					}
				}
			}
		});
	}

	public static void graficarMapa(Mapa sample)
	{
		JFrame frame = new JFrame("Bogotá D.C");

		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.add(sample, BorderLayout.CENTER);
		frame.setSize(700, 500);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}


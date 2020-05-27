package model.vo;

import model.data_structures.IInfoVertex;
import model.data_structures.ORArray;

public class VertexInfo implements IInfoVertex{
	/**
	 * coordinadas del vertice
	 */
	private Coordinates coor;
	
	/**
	 * la lista de infracciones que tiene 
	 */
	private ORArray<Integer> infractions;
	
	/**
	 * el promedio de letalidad de las infracciones
	 */
	private Double average;
	
	/**
	 * la estacion de policia que puede tener asociado
	 */
	private PoliceStation policeStation;
	
	
	private Integer id;
	
	/**
	 * 
	 * @param pCoor
	 */
	public VertexInfo( Coordinates pCoor,Integer pId) {
		setCoordinates(pCoor);
		infractions = new ORArray<Integer>();
		average = null;
		id=pId;
	}
	
	/**
	 * 
	 * @param id
	 * @param ave
	 */
	public void addInfraction(int id) {
		infractions.add(id);
	}
	
	/**
	 * 
	 * @param policeStation
	 */
	public void addPoliceStation(PoliceStation policeStation) {
		this.policeStation = policeStation;
	}
	
	public ORArray<Integer> getInfractions()
	{
		return infractions;
	}
	/**
	 * promedio de gravedad de los comparendos
	 */

	@Override
	public Double getInfo1() {
		return this.average;
	}

	/**
	 * Mayor numero de comparendos
	 */
	@Override
	public Double getInfo2() {
		return this.infractions.getSize().doubleValue();
	}

	public Coordinates getCoordinates() {
		return coor;
	}

	public void setCoordinates(Coordinates coor) {
		this.coor = coor;
	}

}

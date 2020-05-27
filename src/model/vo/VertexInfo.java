package model.vo;

import model.data_structures.IInfoVertex;
import model.data_structures.ORArray;

public class VertexInfo implements IInfoVertex{
	/**
	 * 
	 */
	private Coordinates coor;
	
	/**
	 * 
	 */
	private ORArray<Integer> infractions;
	
	/**
	 * 
	 */
	private Double average;
	
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
	@Override
	public Double getInfo1() {
		return this.average;
	}

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

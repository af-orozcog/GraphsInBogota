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
	private ORArray<Long> infractions;
	
	/**
	 * 
	 */
	private Double average;
	
	private PoliceStation policeStation;
	
	
	/**
	 * 
	 * @param pCoor
	 */
	public VertexInfo( Coordinates pCoor) {
		coor = pCoor;
		infractions = new ORArray<Long>();
		average = null;
	}
	
	/**
	 * 
	 * @param id
	 * @param ave
	 */
	public void addInfraction(Long id, Double ave) {
		infractions.add(id);
		if(average == null)
			average = ave;
		else
			average = (average +ave)/2.0;
	}
	
	/**
	 * 
	 * @param policeStation
	 */
	public void addPoliceStation(PoliceStation policeStation) {
		this.policeStation = policeStation;
	}
	
	@Override
	public Double getInfo1() {
		return this.average;
	}

	@Override
	public Double getInfo2() {
		return this.infractions.getSize().doubleValue();
	}

}

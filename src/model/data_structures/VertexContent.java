package model.data_structures;

import model.vo.Coordinates;

public class VertexContent implements Comparable<VertexContent>{
	public Coordinates coor;
	public ORArray<Long> infractions;
	
	public VertexContent( Coordinates pCoor ) {
		coor = pCoor;
		infractions = new ORArray<Long>();
	}

	public int compareTo(VertexContent o) {
		if(infractions.getSize() < o.infractions.getSize())
			return 1;
		if(infractions.getSize() < o.infractions.getSize())
			return -1;
		return 0;
	}
	
}

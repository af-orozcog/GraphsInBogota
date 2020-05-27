package model;


import java.util.Date;



public class Comparendo {
	int OBJECTID;
	Double FECHA_HORA;
	String DES_INFRACCION;
	String MEDIO_DETECCION;
	String CLASE_VEHICULO;
	String TIPO_SERVICIO;
	String INFRACCION;
	String LOCALIDAD;
	public int getOBJECTID() {
		return OBJECTID;
	}
	public void setOBJECTID(int oBJECTID) {
		OBJECTID = oBJECTID;
	}
	public Double getFECHA_HORA() {
		return FECHA_HORA;
	}
	public void setFECHA_HORA(Double fECHA_HORA) {
		FECHA_HORA = fECHA_HORA;
	}
	public String getDES_INFRACCION() {
		return DES_INFRACCION;
	}
	public void setDES_INFRACCION(String dES_INFRACCION) {
		DES_INFRACCION = dES_INFRACCION;
	}
	public String getMEDIO_DETECCION() {
		return MEDIO_DETECCION;
	}
	public void setMEDIO_DETECCION(String mEDIO_DETECCION) {
		MEDIO_DETECCION = mEDIO_DETECCION;
	}
	public String getCLASE_VEHICULO() {
		return CLASE_VEHICULO;
	}
	public void setCLASE_VEHICULO(String cLASE_VEHICULO) {
		CLASE_VEHICULO = cLASE_VEHICULO;
	}
	public String getTIPO_SERVICIO() {
		return TIPO_SERVICIO;
	}
	public void setTIPO_SERVICIO(String tIPO_SERVICIO) {
		TIPO_SERVICIO = tIPO_SERVICIO;
	}
	public String getINFRACCION() {
		return INFRACCION;
	}
	public void setINFRACCION(String iNFRACCION) {
		INFRACCION = iNFRACCION;
	}
	public String getLOCALIDAD() {
		return LOCALIDAD;
	}
	public void setLOCALIDAD(String lOCALIDAD) {
		LOCALIDAD = lOCALIDAD;
	}
	@Override
	public String toString() {
		return "Comparendo [OBJECTID=" + OBJECTID + ", FECHA_HORA=" + FECHA_HORA + ", DES_INFRACCION=" + DES_INFRACCION
				+ ", MEDIO_DETECCION=" + MEDIO_DETECCION + ", CLASE_VEHICULO=" + CLASE_VEHICULO + ", TIPO_SERVICIO="
				+ TIPO_SERVICIO + ", INFRACCION=" + INFRACCION + ", LOCALIDAD=" + LOCALIDAD + "]";
	}

}

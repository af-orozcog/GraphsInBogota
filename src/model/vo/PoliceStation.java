package model.vo;

public class PoliceStation {
	
	@Override
	public String toString() {
		return "PoliceStation [coor=" + coor + ", OBJECTID=" + OBJECTID + ", id=" + id + ", EPOCOD_PLAN=" + EPOCOD_PLAN
				+ ", EPOCOD_ENT=" + EPOCOD_ENT + ", EPOCOD_PROY=" + EPOCOD_PROY + ", EPOANIO_GEO=" + EPOANIO_GEO
				+ ", EPOFECHA_INI=" + EPOFECHA_INI + ", EPOFECHA_FIN=" + EPOFECHA_FIN + ", EPODESCRIP=" + EPODESCRIP
				+ ", EPOEST_PROY=" + EPOEST_PROY + ", EPOINTERV_ESP=" + EPOINTERV_ESP + ", EPODIR_SITIO=" + EPODIR_SITIO
				+ ", EPOCOD_SITIO=" + EPOCOD_SITIO + ", EPOLATITUD=" + EPOLATITUD + ", EPOLONGITU=" + EPOLONGITU
				+ ", EPOSERVICIO=" + EPOSERVICIO + ", EPOHORARIO=" + EPOHORARIO + ", EPOTELEFON=" + EPOTELEFON
				+ ", EPOCELECTR=" + EPOCELECTR + ", EPOPWEB=" + EPOPWEB + ", EPOIUUPLAN=" + EPOIUUPLAN + ", EPOIUSCATA="
				+ EPOIUSCATA + ", EPOIULOCAL=" + EPOIULOCAL + ", EPOEASOCIA=" + EPOEASOCIA + ", EPOFUNCION="
				+ EPOFUNCION + ", EPOTEQUIPA=" + EPOTEQUIPA + ", EPONOMBRE=" + EPONOMBRE + ", EPOIDENTIF=" + EPOIDENTIF
				+ ", EPOFECHA_C=" + EPOFECHA_C + "]";
	}

	private  Coordinates coor;

	private int OBJECTID;

	public int getOBJECTID() {
		return OBJECTID;
	}

	public void setOBJECTID(int oBJECTID) {
		OBJECTID = oBJECTID;
	}

	private Long id;
	
	private int EPOCOD_PLAN;
	
	private String EPOCOD_ENT;
	
	private String EPOCOD_PROY;
	
	private long EPOANIO_GEO;
	
	private long EPOFECHA_INI;
	
	private long EPOFECHA_FIN;
	
	private String EPODESCRIP;
	
	private String EPOEST_PROY;
	
	private String EPOINTERV_ESP;
	
	private String EPODIR_SITIO;
	
	private String EPOCOD_SITIO;
	
	private Double EPOLATITUD;
	
	private Double EPOLONGITU;
	
	private String EPOSERVICIO;
	
	private String EPOHORARIO;
	
	private String EPOTELEFON;
	
	private String EPOCELECTR;

	private String EPOPWEB;
	
	private String EPOIUUPLAN;
	
	private String EPOIUSCATA;
	
	private String EPOIULOCAL;
	
	private String EPOEASOCIA;
	
	private String EPOFUNCION;
	
	private String EPOTEQUIPA;
	
	private String EPONOMBRE;
	
	private String EPOIDENTIF;
	
	private String EPOFECHA_C;
	
	/**
	 * @param coor
	 * @param id
	 * @param ePOCOD_PLAN
	 * @param ePOCOD_ENT
	 * @param ePOCOD_PROY
	 * @param ePOANIO_GEO
	 * @param ePOFECHA_INI
	 * @param ePOFECHA_FIN
	 * @param ePODESCRIP
	 * @param ePOEST_PROY
	 * @param ePOINTERV_ESP
	 * @param ePODIR_SITIO
	 * @param ePOCOD_SITIO
	 * @param ePOLATITUD
	 * @param ePOLONGITU
	 * @param ePOSERVICIO
	 * @param ePOHORARIO
	 * @param ePOTELEFON
	 * @param ePOCELECTR
	 * @param ePOPWEB
	 * @param ePOIUUPLAN
	 * @param ePOIUSCATA
	 * @param ePOIULOCAL
	 * @param ePOEASOCIA
	 * @param ePOFUNCION
	 * @param ePOTEQUIPA
	 * @param ePONOMBRE
	 * @param ePOIDENTIF
	 * @param ePOFECHA_C
	 */
	public PoliceStation(Coordinates coor, Long id, int ePOCOD_PLAN, String ePOCOD_ENT, String ePOCOD_PROY,
			long ePOANIO_GEO, long ePOFECHA_INI, long ePOFECHA_FIN, String ePODESCRIP, String ePOEST_PROY,
			String ePOINTERV_ESP, String ePODIR_SITIO, String ePOCOD_SITIO, Double ePOLATITUD, Double ePOLONGITU,
			String ePOSERVICIO, String ePOHORARIO, String ePOTELEFON, String ePOCELECTR, String ePOPWEB,
			String ePOIUUPLAN, String ePOIUSCATA, String ePOIULOCAL, String ePOEASOCIA, String ePOFUNCION,
			String ePOTEQUIPA, String ePONOMBRE, String ePOIDENTIF, String ePOFECHA_C) {
		super();
		this.coor = coor;
		this.id = id;
		EPOCOD_PLAN = ePOCOD_PLAN;
		EPOCOD_ENT = ePOCOD_ENT;
		EPOCOD_PROY = ePOCOD_PROY;
		EPOANIO_GEO = ePOANIO_GEO;
		EPOFECHA_INI = ePOFECHA_INI;
		EPOFECHA_FIN = ePOFECHA_FIN;
		EPODESCRIP = ePODESCRIP;
		EPOEST_PROY = ePOEST_PROY;
		EPOINTERV_ESP = ePOINTERV_ESP;
		EPODIR_SITIO = ePODIR_SITIO;
		EPOCOD_SITIO = ePOCOD_SITIO;
		EPOLATITUD = ePOLATITUD;
		EPOLONGITU = ePOLONGITU;
		EPOSERVICIO = ePOSERVICIO;
		EPOHORARIO = ePOHORARIO;
		EPOTELEFON = ePOTELEFON;
		EPOCELECTR = ePOCELECTR;
		EPOPWEB = ePOPWEB;
		EPOIUUPLAN = ePOIUUPLAN;
		EPOIUSCATA = ePOIUSCATA;
		EPOIULOCAL = ePOIULOCAL;
		EPOEASOCIA = ePOEASOCIA;
		EPOFUNCION = ePOFUNCION;
		EPOTEQUIPA = ePOTEQUIPA;
		EPONOMBRE = ePONOMBRE;
		EPOIDENTIF = ePOIDENTIF;
		EPOFECHA_C = ePOFECHA_C;
	}

	/**
	 * @return the coor
	 */
	public Coordinates getCoor() {
		return coor;
	}

	/**
	 * @param coor the coor to set
	 */
	public void setCoor(Coordinates coor) {
		this.coor = coor;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the ePOCOD_PLAN
	 */
	public int getEPOCOD_PLAN() {
		return EPOCOD_PLAN;
	}

	/**
	 * @param ePOCOD_PLAN the ePOCOD_PLAN to set
	 */
	public void setEPOCOD_PLAN(int ePOCOD_PLAN) {
		EPOCOD_PLAN = ePOCOD_PLAN;
	}

	/**
	 * @return the ePOCOD_ENT
	 */
	public String getEPOCOD_ENT() {
		return EPOCOD_ENT;
	}

	/**
	 * @param ePOCOD_ENT the ePOCOD_ENT to set
	 */
	public void setEPOCOD_ENT(String ePOCOD_ENT) {
		EPOCOD_ENT = ePOCOD_ENT;
	}

	/**
	 * @return the ePOCOD_PROY
	 */
	public String getEPOCOD_PROY() {
		return EPOCOD_PROY;
	}

	/**
	 * @param ePOCOD_PROY the ePOCOD_PROY to set
	 */
	public void setEPOCOD_PROY(String ePOCOD_PROY) {
		EPOCOD_PROY = ePOCOD_PROY;
	}

	/**
	 * @return the ePOANIO_GEO
	 */
	public long getEPOANIO_GEO() {
		return EPOANIO_GEO;
	}

	/**
	 * @param ePOANIO_GEO the ePOANIO_GEO to set
	 */
	public void setEPOANIO_GEO(long ePOANIO_GEO) {
		EPOANIO_GEO = ePOANIO_GEO;
	}

	/**
	 * @return the ePOFECHA_INI
	 */
	public long getEPOFECHA_INI() {
		return EPOFECHA_INI;
	}

	/**
	 * @param ePOFECHA_INI the ePOFECHA_INI to set
	 */
	public void setEPOFECHA_INI(long ePOFECHA_INI) {
		EPOFECHA_INI = ePOFECHA_INI;
	}

	/**
	 * @return the ePOFECHA_FIN
	 */
	public long getEPOFECHA_FIN() {
		return EPOFECHA_FIN;
	}

	/**
	 * @param ePOFECHA_FIN the ePOFECHA_FIN to set
	 */
	public void setEPOFECHA_FIN(long ePOFECHA_FIN) {
		EPOFECHA_FIN = ePOFECHA_FIN;
	}

	/**
	 * @return the ePODESCRIP
	 */
	public String getEPODESCRIP() {
		return EPODESCRIP;
	}

	/**
	 * @param ePODESCRIP the ePODESCRIP to set
	 */
	public void setEPODESCRIP(String ePODESCRIP) {
		EPODESCRIP = ePODESCRIP;
	}

	/**
	 * @return the ePOEST_PROY
	 */
	public String getEPOEST_PROY() {
		return EPOEST_PROY;
	}

	/**
	 * @param ePOEST_PROY the ePOEST_PROY to set
	 */
	public void setEPOEST_PROY(String ePOEST_PROY) {
		EPOEST_PROY = ePOEST_PROY;
	}

	/**
	 * @return the ePOINTERV_ESP
	 */
	public String getEPOINTERV_ESP() {
		return EPOINTERV_ESP;
	}

	/**
	 * @param ePOINTERV_ESP the ePOINTERV_ESP to set
	 */
	public void setEPOINTERV_ESP(String ePOINTERV_ESP) {
		EPOINTERV_ESP = ePOINTERV_ESP;
	}

	/**
	 * @return the ePODIR_SITIO
	 */
	public String getEPODIR_SITIO() {
		return EPODIR_SITIO;
	}

	/**
	 * @param ePODIR_SITIO the ePODIR_SITIO to set
	 */
	public void setEPODIR_SITIO(String ePODIR_SITIO) {
		EPODIR_SITIO = ePODIR_SITIO;
	}

	/**
	 * @return the ePOCOD_SITIO
	 */
	public String getEPOCOD_SITIO() {
		return EPOCOD_SITIO;
	}

	/**
	 * @param ePOCOD_SITIO the ePOCOD_SITIO to set
	 */
	public void setEPOCOD_SITIO(String ePOCOD_SITIO) {
		EPOCOD_SITIO = ePOCOD_SITIO;
	}

	/**
	 * @return the ePOLATITUD
	 */
	public Double getEPOLATITUD() {
		return EPOLATITUD;
	}

	/**
	 * @param ePOLATITUD the ePOLATITUD to set
	 */
	public void setEPOLATITUD(Double ePOLATITUD) {
		EPOLATITUD = ePOLATITUD;
	}

	/**
	 * @return the ePOLONGITU
	 */
	public Double getEPOLONGITU() {
		return EPOLONGITU;
	}

	/**
	 * @param ePOLONGITU the ePOLONGITU to set
	 */
	public void setEPOLONGITU(Double ePOLONGITU) {
		EPOLONGITU = ePOLONGITU;
	}

	/**
	 * @return the ePOSERVICIO
	 */
	public String getEPOSERVICIO() {
		return EPOSERVICIO;
	}

	/**
	 * @param ePOSERVICIO the ePOSERVICIO to set
	 */
	public void setEPOSERVICIO(String ePOSERVICIO) {
		EPOSERVICIO = ePOSERVICIO;
	}

	/**
	 * @return the ePOHORARIO
	 */
	public String getEPOHORARIO() {
		return EPOHORARIO;
	}

	/**
	 * @param ePOHORARIO the ePOHORARIO to set
	 */
	public void setEPOHORARIO(String ePOHORARIO) {
		EPOHORARIO = ePOHORARIO;
	}

	/**
	 * @return the ePOTELEFON
	 */
	public String getEPOTELEFON() {
		return EPOTELEFON;
	}

	/**
	 * @param ePOTELEFON the ePOTELEFON to set
	 */
	public void setEPOTELEFON(String ePOTELEFON) {
		EPOTELEFON = ePOTELEFON;
	}

	/**
	 * @return the ePOCELECTR
	 */
	public String getEPOCELECTR() {
		return EPOCELECTR;
	}

	/**
	 * @param ePOCELECTR the ePOCELECTR to set
	 */
	public void setEPOCELECTR(String ePOCELECTR) {
		EPOCELECTR = ePOCELECTR;
	}

	/**
	 * @return the ePOPWEB
	 */
	public String getEPOPWEB() {
		return EPOPWEB;
	}

	/**
	 * @param ePOPWEB the ePOPWEB to set
	 */
	public void setEPOPWEB(String ePOPWEB) {
		EPOPWEB = ePOPWEB;
	}

	/**
	 * @return the ePOIUUPLAN
	 */
	public String getEPOIUUPLAN() {
		return EPOIUUPLAN;
	}

	/**
	 * @param ePOIUUPLAN the ePOIUUPLAN to set
	 */
	public void setEPOIUUPLAN(String ePOIUUPLAN) {
		EPOIUUPLAN = ePOIUUPLAN;
	}

	/**
	 * @return the ePOIUSCATA
	 */
	public String getEPOIUSCATA() {
		return EPOIUSCATA;
	}

	/**
	 * @param ePOIUSCATA the ePOIUSCATA to set
	 */
	public void setEPOIUSCATA(String ePOIUSCATA) {
		EPOIUSCATA = ePOIUSCATA;
	}

	/**
	 * @return the ePOIULOCAL
	 */
	public String getEPOIULOCAL() {
		return EPOIULOCAL;
	}

	/**
	 * @param ePOIULOCAL the ePOIULOCAL to set
	 */
	public void setEPOIULOCAL(String ePOIULOCAL) {
		EPOIULOCAL = ePOIULOCAL;
	}

	/**
	 * @return the ePOEASOCIA
	 */
	public String getEPOEASOCIA() {
		return EPOEASOCIA;
	}

	/**
	 * @param ePOEASOCIA the ePOEASOCIA to set
	 */
	public void setEPOEASOCIA(String ePOEASOCIA) {
		EPOEASOCIA = ePOEASOCIA;
	}

	/**
	 * @return the ePOFUNCION
	 */
	public String getEPOFUNCION() {
		return EPOFUNCION;
	}

	/**
	 * @param ePOFUNCION the ePOFUNCION to set
	 */
	public void setEPOFUNCION(String ePOFUNCION) {
		EPOFUNCION = ePOFUNCION;
	}

	/**
	 * @return the ePOTEQUIPA
	 */
	public String getEPOTEQUIPA() {
		return EPOTEQUIPA;
	}

	/**
	 * @param ePOTEQUIPA the ePOTEQUIPA to set
	 */
	public void setEPOTEQUIPA(String ePOTEQUIPA) {
		EPOTEQUIPA = ePOTEQUIPA;
	}

	/**
	 * @return the ePONOMBRE
	 */
	public String getEPONOMBRE() {
		return EPONOMBRE;
	}

	/**
	 * @param ePONOMBRE the ePONOMBRE to set
	 */
	public void setEPONOMBRE(String ePONOMBRE) {
		EPONOMBRE = ePONOMBRE;
	}

	/**
	 * @return the ePOIDENTIF
	 */
	public String getEPOIDENTIF() {
		return EPOIDENTIF;
	}

	/**
	 * @param ePOIDENTIF the ePOIDENTIF to set
	 */
	public void setEPOIDENTIF(String ePOIDENTIF) {
		EPOIDENTIF = ePOIDENTIF;
	}

	/**
	 * @return the ePOFECHA_C
	 */
	public String getEPOFECHA_C() {
		return EPOFECHA_C;
	}

	/**
	 * @param ePOFECHA_C the ePOFECHA_C to set
	 */
	public void setEPOFECHA_C(String ePOFECHA_C) {
		EPOFECHA_C = ePOFECHA_C;
	}

	
}

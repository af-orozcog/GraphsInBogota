package model.vo;

/**
 * Clase que representa los datos de reporte de la lectura de las infracciones 
 * 
 */
public class StatisticsVOMovingViolations {
	
	// total de infracciones cargadas
	private int totalInfracciones;
	
	// numero de meses cargados
	private int numeroMeses;
	
	// numero de infraccioens cargadas x cada mes
	private int [] numeroInfraccionesxMes;
	
	/**
	 * Constructor con el resumen de la carga de la lectura de las infracciones
	 // TODO definir los parametros necesarios para inicializar los atributos de la clase
	 * @param parametro1 con valor para un dato de la carga de archivos
	 * @param parametro2 con valor para un dato de la carga de archivos
	 * 
	 */
	public StatisticsVOMovingViolations(int[] parametro1)
	{
		numeroInfraccionesxMes = parametro1;
		numeroMeses = 6; totalInfracciones = 0;
		for (int i = 0; i < numeroMeses; i++) {
			totalInfracciones+=numeroInfraccionesxMes[i];
		}
	}
	
	/**
	 * Retorna el numero de meses que fueron cargados en la lectura de archivos
	 * @return numero de meses que fueron cargados en la lectura de archivos
	 */
	public int darNumeroDeMesesCargados()
	{
		return numeroMeses;
	}
	
	/**
	 * Retorna el numero total de infracciones cargadas 
	 */
	public int darTotalInfracciones()
	{
		return totalInfracciones;
	}
	
	/** 
	 * Retorna el numero de infracciones cargadas por cada mes de lectura de archivos
	 * @return arreglo con el numero de infracciones cargadas por cada mes de lectura de archivos
	 */
	public int [] darNumeroDeInfraccionesXMes()
	{
		return numeroInfraccionesxMes;
	}

}

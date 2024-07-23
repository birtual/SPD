package lopicost.spd.iospd.statistics;

import java.util.List;

/**
 * Estadisticas de un proceso de iospd
 */
public interface ProcessStatistics {

	/**
	 * numero de filas procesadas en total 
	 */
	int getProcessedRowsCount();
	
	/**
	 * numero de filas con errores 
	 */
	int getErrorsRowsCount();
	
	/**
	 * numero de filas sin errores 
	 */
	int getOkRowsCount();
	
	/**
	 * devuelve los errores (lista de strings) 
	 */
	List getErrors();
}

package lopicost.spd.iospd;

import java.util.List;


/**
 * Sirve para poder juntar los procesos de import con los de export 
 */
public interface Process {

	/**
	 * Si es null, desactiva el logging
	 * Si no es null, lo activa
	 */
	public void setLogging( ProcessLogging logging );
	
	public void stop();
	public int getRowsToProcess();
	public int getProcessedRows();
	public int getNumErrors();
	public String getLastError();
	public List getErrors();
	
}

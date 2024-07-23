
package lopicost.spd.iospd.connectors;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;


public abstract class ConnectorImpl implements Connector {

	
	private String pathFile = null;
	private Vector pData = null;
	private int processedRows = 0;
	private boolean importConnector = true;
	private String idProcess = null;
	
	public ConnectorImpl(){	}

	
	public boolean init(String idProc, boolean isImportconnector){
		
		boolean sortida = true;
		
		this.idProcess = idProc;
		this.importConnector = isImportconnector;

		sortida = initEspecific();
		return sortida;
	}

	
	public ConnectorImpl(boolean isImportConnector){
		importConnector = isImportConnector;
	}
	
	
	public String getIdProcess() {
		return idProcess;	}
	public void setIdProcess(String idProces) {
		this.idProcess = idProces;	}
	public String getPathFile() {
		return pathFile;}
	/**
	 * Substiuye las separaciones de directorio '/' por File.separator 
	 * @param _pathFile
	 */
	public void setPathFile(String _pathFile) {
		_pathFile = _pathFile.replace('/',File.separatorChar);
		this.pathFile = _pathFile;	
	}
	public boolean isImportConnector() {
		return importConnector;}
	public void setImportConnector(boolean importConnector) {
		this.importConnector = importConnector;}
	public void setProcessedRows(int processedRows) {
		this.processedRows = processedRows;}
	public int getProcessedRows(){
		return this.processedRows;
	}
	
    public void setData(Vector pData) {
    	this.pData=pData;}
    public Vector getData(){
    	return this.pData;
    }
    
    
    
    
    
     /**
      * Converteix 1 linea (en vector) al format del conector.
      * @param linTexto Vector d'Strings
      * @return String amb el format corresponent.
      * 	null si linTexto no és un Vector de Srings o no és correcte
      */
    public String write(Vector linTexto){
    	
    	// validem que és un vector de Strings
    	try {
    		String texto1 = (String)linTexto.get(0);
    	} catch (ClassCastException ce){
    		return null;
    	}
    	
    	// preparem dades
    	Vector pDataTemp = getData();
    	Vector vectorAux = new Vector(); 
    	vectorAux.add(linTexto);

    	setData(vectorAux);
    	String sortida = (String)((List)this.write()).get(0);
    	
    	// reestablim dades
    	setData(pDataTemp);
    	
    	return sortida;
    }
    
    
	/**
	 * Escriu al fitxer pathFileOut el contingut de pData
	 */
	public boolean writeFile() 
	{
		try {
			FileWriter fileOut = new FileWriter(this.pathFile); 
			Iterator itPDataFormatat = write().iterator(); 
			
			while (itPDataFormatat.hasNext()) {
				fileOut.write((String)itPDataFormatat.next());
			}
			
			fileOut.close();
			
		} catch (Exception e) {
			return false;
		}
		return true;
	}


	/**
	 * Escriu al fitxer  el contingut de pData
	 */
	public boolean appendLines() 
	{
		try {
			FileWriter _fileOut = new FileWriter(this.pathFile, true); /**/
			Iterator itPDataFormatat = write().iterator(); 
			
			while (itPDataFormatat.hasNext()) {
				_fileOut.write((String)itPDataFormatat.next());
			}
			
			_fileOut.close(); /**/
			
		} catch (Exception e) {
			e.printStackTrace(); //TODO borrar
			return false;
		}

		return true;
	}
	


	
	public abstract boolean startReading() ;
	public abstract boolean startWriting() ;
	public abstract boolean endReading();
	public abstract boolean endWriting() ;
	public abstract Vector getNextRow() ;
	public abstract List write() ;
	public abstract String getContentType() ;
	public abstract boolean initEspecific();
}

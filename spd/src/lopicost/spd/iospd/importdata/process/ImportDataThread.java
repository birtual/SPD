
package lopicost.spd.iospd.importdata.process;


import  lopicost.spd.iospd.ProcessLogging;
import  lopicost.spd.iospd.connectors.Connector;
import lopicost.spd.iospd.importdata.struts.form.ImportDataForm;


import java.util.List;


public class ImportDataThread extends Thread 
{
	String fileInput=null;
	public boolean started = true;
	private int status = SHUTDOWN;
	public static final int SHUTDOWN= 0;
	public static final int IN_PROCESS = 1;
	public static final int FINALIZED_OK = 2;
	public static final int FINALIZED_KO = 3;
	private ImportProcess proc= null;
//	public String idProceso = "";
//	public String idDivisionResidencia = "";

	
	/*
	 * Inicialitzem el thread
	 * */
	public boolean initialize(String spdUsuario, ImportDataForm form, String idProceso,  String idRobot, String idDivisionResidencia, 
			String _fileInput,String _fileInputError, String processClassName, 
			String readerClassName, ProcessLogging log, boolean cargaAnexa) 
	{
		boolean initialized= true;
		try
		{
	//		ctx= pCtx;
			proc= (ImportProcess) Class.forName(processClassName).newInstance();
            Connector reader= (Connector) Class.forName(readerClassName).newInstance();
			setStatus(SHUTDOWN);
			reader.setIdDivisionResidencia(idDivisionResidencia);
			reader.setIdRobot(idRobot);
			reader.setIdProceso(idProceso);
			reader.setSpdUsuario(spdUsuario);
			reader.setCargaAnexa(cargaAnexa);
		
				
//			proc.init(fileInput,reader);
			reader.setPathFileIn(_fileInput);
			reader.setPathFileOut(_fileInputError);
			proc.init( _fileInput, reader);
			proc.setLogging( log );
		}
		catch (Exception e)
		{
			e.printStackTrace();
			setStatus(FINALIZED_KO);
			initialized= false;
		}
		return initialized;
	}	


	
	public void run() {
		try	{
			setStatus(IN_PROCESS);
			proc.start();
			setStatus(FINALIZED_OK);
		} catch (Exception e) {
			setStatus(FINALIZED_KO);
		}
		finally
		{
		}
		started = false;
	}
	
	
	

	
	/**
	 * Método que devuelve el estado actual del thread
	 * @return int estado en que se encuentra el thread
	 */
	public int checkStatus()
	{
		return status;
	}

	/** Set de estado
	 * @param status
	 */
	public void setStatus(int status) 
	{
		this.status = status;
	}

    public int getNumErrors() 
    {
        return proc.getNumErrors();
    }
    
    public List getErrors() {
        return proc.getErrors();
    }
    
    public int getFilesProcessades() {
        return proc.getProcessedRows();
    }


}

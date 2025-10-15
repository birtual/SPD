package lopicost.spd.iospd.importdata.process;


import lopicost.config.logger.Logger;
import lopicost.spd.controller.SpdLogAPI;
import lopicost.spd.excepciones.MaxLineasNulasException;
import lopicost.spd.iospd.IOSpdApi;
import lopicost.spd.iospd.ProcessLogging;
import lopicost.spd.iospd.connectors.Connector;
import lopicost.spd.iospd.statistics.DefaultStatistics;
import lopicost.spd.iospd.statistics.ProcessStatistics;
import lopicost.spd.model.DivisionResidencia;
import lopicost.spd.persistence.DivisionResidenciaDAO;
import lopicost.spd.utils.MessageManager;
import lopicost.spd.utils.StringUtil;
import lopicost.spd.utils.TextManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;


public abstract class ImportProcessImpl implements ImportProcess
{
    public static final String ERRORCODE_NUMROWS_INVALID			= "E00";
	
    public static final int MAXERRORS = 10240;	
	private String fileIn ="";	
	protected int processedRows= 0;
	private int RowsToProcess= 0;
	private String lastError= "";
    public List errors= new ArrayList();
	private int numErrors = 0;
	private Connector conector = null;
	private List errorsToExp = new ArrayList();
	private ProcessLogging log;
	protected IOSpdApi ioSpdApi= null;
	private String idProceso ="";	
	private String idDivisionResidencia ="";	
	private String idRobot ="";	
	private int oidFicheroResiCabecera =0;	
	private String _ficheroCarga="";
	private boolean cargaAnexa=false;

	private String spdUsuario;	
	
	public ImportProcessImpl()
	{
		super();
	}
	
	protected ProcessLogging getLog() {
		return log;
	}
	
	private void inicialitzaFitxerLectura() throws IOException 
	{
       conector.setPathFileIn(this.fileIn);
       
       if (!conector.startReading())
           throw new IOException(TextManager.getMensaje("ImportData.error.initFile"));
    }

	public int getProcessedRows()
	{
		return processedRows;
	}
	
	private void setProcessedRows(int i)
	{
		this.processedRows = i;
	}

    private void setLastError(String lastError) 
    {
        this.lastError = lastError;
        if (numErrors < MAXERRORS)
            errors.add(lastError);
        numErrors++;
    }

    
    protected abstract boolean beforeProcesarEntrada(Vector row) throws Exception;
    //protected abstract boolean procesarEntrada(String idRobot, String idDivisionResidencia, String idProceso, Vector row, int count, boolean cargaAnexa) throws Exception;
    protected abstract boolean procesarEntrada(String idRobot, DivisionResidencia div, String idProceso, Vector row, int count, boolean cargaAnexa) throws Exception;

    protected abstract void afterprocesarEntrada(Vector row) throws Exception;
    protected abstract boolean beforeStart(String filein) throws Exception;
    protected abstract void afterStart() throws Exception;

    public String getLastError() 
    {
        return this.lastError;
    }
    
    public int getNumErrors() 
    {
        return numErrors;
    }
    
    public int getRowsToProcess() 
    {
        return this.RowsToProcess;
    }
    
    public void init(String pFileIn,Connector reader) throws IOException 
    {
    	this.fileIn = pFileIn;
    	this.conector= reader;
    	this.idProceso=reader.getIdProceso();
    	this.idDivisionResidencia =reader.getIdDivisionResidencia();
    	this.idRobot =reader.getIdRobot();
    	this.spdUsuario=reader.getSpdUsuario();
    	this.cargaAnexa=reader.isCargaAnexa();
        this.inicialitzaFitxerLectura();
    }
    
    public void start() throws Exception 
    {
    	
    	Logger.get().debug("01.- ImportProcessImpl.start()");
    	
		Vector rowInProcess= null;
		String errores = null;
		ioSpdApi = new IOSpdApi();
		ProcessStatistics statistics = new DefaultStatistics( this );
		int count=0;
		
		if ( log != null )
		{
			if ( !log.init() ) {
				errores = MessageManager.instance().message("ImportData.error.log", log.getLogFilename());
				setLastError( errores );
				errores = null;
			}
		}
		
		//logTop( statistics );
		try 
		{
		    if (!beforeStart(this.fileIn))
		        return;
		    
			//saltamos cabecera (CORREGIDO, NO nos la saltamos)
		    //rowInProcess = conector.getNextRow();
		    
			// 1.- Tratamos los datos
			//xxxxxwhile ((rowInProcess!=null ||  (rowInProcess=conector.getNextRow())!=null)   && !rowInProcess.isEmpty()) 
		    int totalFilas = conector.getFilasTotales();
		    boolean fin = false;
//		    boolean cargaAnexa = conector.isCargaAnexa();  //en caso que se a�ada un fichero a un proceso ya existente 
		    //while ( totalFilas>=count && (rowInProcess=conector.getNextRow())!=null )
		    DivisionResidencia div = DivisionResidenciaDAO.getDivisionResidenciaById(getSpdUsuario(), this.idDivisionResidencia);
		    while ( totalFilas>=count && !fin)
			{
		    	rowInProcess=conector.getNextRow();
		    			
		    	//esCabecera=compruebaSiEsCabecera(rowInProcess);
				count++;
			    if (this.beforeProcesarEntrada(rowInProcess) && !fin)
			    {
				    // 2.- procesar registro y recoger errores
			        try {
			        	//fin=procesarEntrada(this.idRobot, this.idDivisionResidencia, this.idProceso, rowInProcess, count, this.cargaAnexa);
			        	fin=procesarEntrada(this.idRobot, div, this.idProceso, rowInProcess, count, this.cargaAnexa);
			       
			        /*catch (MaxLineasNulasException e) {
                 		e.getMessage();
                        fin = true;
                        break;
                   } catch (LineaDescartadaException e) {
                    	e.getMessage();
                    } catch (LineaDuplicadaException e) {
                    	e.getMessage();
                    } catch (ColumnasInsuficientesException e) {
                        e.getMessage();
                    */
			        	} catch (Exception e) {
                        if (e instanceof MaxLineasNulasException) {		//cortamos el bucle si recibimos esta excepci�n
                            fin = true;
                         } 
                        
                    	if(this.processedRows>1); //no guardamos error de cabecera
			            {
			            	int filasExcel = this.getProcessedRows()+1 ;
			            	//errores= TextManager.getMensaje("ImportData.error.linea")+" " + rowInProcess.toString();
			            	//errores= TextManager.getMensaje("ImportData.error.linea")+" " + rowInProcess;
	                        errores= "Fila " +filasExcel + " descartada: "  + rowInProcess;
	                        if(e.getMessage()!=null && !e.getMessage().equalsIgnoreCase("") && !e.getMessage().equalsIgnoreCase("null")) 
	                        	errores+= " - " +  e.getMessage();
			            }
////////////////////                        errores+= " --> "+rowInProcess.toString();
						//writeError(rowInProcess, e.getMessage());                                   
			        }
					//Incrementem el n�mero de files processades
					this.setProcessedRows(this.getProcessedRows()+1);
					// 3.- En caso de errores escribirlo en la salida
					if(errores!=null)
							this.setLastError(StringUtil.limpiarTextoComentarios(errores));
					errores = null;
					
					this.afterprocesarEntrada(rowInProcess);
			    }
			}
			
		}
		catch (Exception e) 
		{
			//e.printStackTrace();
			throw new Exception("Error en la carga. " + e.getMessage());
		}
		finally
		{
			conector.endReading();
			conector.endWriting();
			this.afterStart();
			logBody( statistics );
			logBottom( statistics );
		}
    }
    


	public void stop() 
    {
    }
    
    public List getErrors() {
        return this.errors;
    }
    
    /**
     * Escriu 1 linea al fitxer d'error
	 * @param row
	 * @param error
	 * @throws IOException
     * @throws SQLException 
     * @throws ClassNotFoundException 
	 */
	private void writeError(Vector row, String error)  throws IOException, ClassNotFoundException, SQLException
	{
		//A�ado un log en BDD para poder relanzar los erroneos y tenerlo mejor detectados.
		SpdLogAPI.addLog(getSpdUsuario(), getIdDivisionResidencia(), getIdProceso(), SpdLogAPI.A_IOSPD, getLogSubAccion(), (getProcessedRows()+1)+"", error,row.toString());
		
		row.add(error);
		this.errorsToExp.add(row);   // agregamos al listado de errores (necesario???)
		
		Vector linError = new Vector();
		linError.add(row);
		conector.setData(linError);
		if (!conector.appendLines())
			throw new IOException(TextManager.getMensaje("ImportData.error.writeErrorLin") + this.getProcessedRows()+1);
	}
	
    protected String notEmpty( String s )
    {
    	if ( s == null || s.length() == 0) return null;
    	else return s;
    }
    
	public void setLogging( ProcessLogging logging )
	{
		this.log = logging;
	}
	
	


	protected void logTop( ProcessStatistics statistics ) 
	{
		if ( log != null)
		{
			try {
				log.top( statistics );
				//jpapell 26/09/2007
				//A�ado un log en BDD para poder controlar los processos lanzados .
				SpdLogAPI.addLog(getSpdUsuario(), getIdDivisionResidencia(), getIdProceso(), SpdLogAPI.A_IOSPD, getLogAccion(), SpdLogAPI.C_START,
						"0", "["+new Date().toString()+"] Lanzado proceso IOSGA ");
				
			} catch ( Exception e) {
				Logger.get().error("No se puede hacer log del top", e);
			}
		}
	}
	
	protected void logBody( ProcessStatistics statistics ) 
	{
		if ( log != null)
		{
			try {
				log.body( statistics );
			} catch ( Exception e) {
				Logger.get().error("No se puede hacer log del body", e);
			}
		}
	}
	
	protected void logBottom( ProcessStatistics statistics ) 
	{
		if ( log != null)
		{
			try {
				log.bottom( statistics );
				//jpapell 26/09/2007
				//A�ado un log en BDD para poder controlar los processos lanzados .
				SpdLogAPI.addLog(getSpdUsuario(), getIdDivisionResidencia(), getIdProceso(), SpdLogAPI.A_IOSPD, getLogAccion(), SpdLogAPI.C_END,
						"0", "["+new Date().toString()+"] Lanzado proceso IOSGA-> procesados:["+statistics.getProcessedRowsCount()+"] ok:["+statistics.getOkRowsCount()+"] ko:["+statistics.getErrorsRowsCount()+"]");
				
			} catch ( Exception e) {
				Logger.get().error("No se puede hacer log del bottom", e);
			}
			finally {
				try { log.close(); } catch (Exception e) {}
			}
		}
	}
	
	protected String getLogAccion(){
		return SpdLogAPI.TODOS_REGISTROS;
		
	}
	
	protected String getLogSubAccion(){
		return SpdLogAPI.TODOS_REGISTROS;
		
	}
	
	public String getIdProceso() {
		return idProceso;
	}

	public void setIdProceso(String idProceso) {
		this.idProceso = idProceso;
	}

	public String getIdDivisionResidencia() {
		return this.idDivisionResidencia;
	}

	public void setIdDivisionResidencia(String idDivisionResidencia) {
		this.idDivisionResidencia = idDivisionResidencia;
	}

	public int getOidFicheroResiCabecera() {
		return oidFicheroResiCabecera;
	}

	public void setOidFicheroResiCabecera(int oidFicheroResiCabecera) {
		this.oidFicheroResiCabecera = oidFicheroResiCabecera;
	}

	public String getSpdUsuario() {
		return spdUsuario;
	}

	public void setSpdUsuario(String spdUsuario) {
		this.spdUsuario = spdUsuario;
	}

	public boolean isCargaAnexa() {
		return cargaAnexa;
	}

	public void setCargaAnexa(boolean cargaAnexa) {
		this.cargaAnexa = cargaAnexa;
	}


	
}    



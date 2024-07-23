/*
1	csv	Archivo csv	lopicost.spd.iospd.connectors.ConnectorCsv	Formato csv	CSV
 */
package lopicost.spd.iospd.connectors;


//imports de java 
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;


public class ConnectorCsvPipe extends ConnectorImpl implements Connector
{
    private static final String DELIMITER = "|";
    
	private int processedRows= 0;
	private BufferedReader mycsv= null;
    private BufferedWriter fileOut = null;
	private String actualRow= null;
	private String pathFileIn= null;
	private String pathFileOut= null;
    private Vector pData= null;
    private String idProceso = "";
    private String idDivisionResidencia = "";
    private String idRobot = "";
    private String spdUsuario = "";
    private  boolean cargaAnexa=false;

	public ConnectorCsvPipe()
	{
	}
    
	public String getIdProceso() {
		return idProceso;
	}

	public void setIdProceso(String idProceso) {
		this.idProceso = idProceso;
	}

	public String getIdDivisionResidencia() {
		return idDivisionResidencia;
	}

	public void setIdDivisionResidencia(String idDivisionResidencia) {
		this.idDivisionResidencia = idDivisionResidencia;
	}


	public void setPathFileIn(String pFilename)
    {
		pathFileIn=pFilename;
    }
    
	public void setPathFileOut(String pFilename) {
		this.pathFileOut = pFilename;
	
	}
	
    public boolean startReading()
    {
        boolean result= true; 
        if (pathFileIn!=null && pathFileIn.toLowerCase().endsWith(".csv"))
        {
           try
           {
               mycsv= new BufferedReader(new FileReader(pathFileIn));
           }
           catch(Exception e)   
           {
               result= false;
           }
        }
        else
        {
            result= false;
        }
        return result;
    }
    
     public boolean startWriting()
    {
        boolean result= true; 
        if (pathFileOut!=null && pathFileOut.toLowerCase().endsWith(".csv"))
        {
           try
           {
           	   fileOut = new BufferedWriter(new FileWriter(this.pathFileOut));
           }
           catch(Exception e)   
           {
               result= false;
           }
        }
        else
        {
            result= false;
        }
        return result;
    }
    
	public boolean endReading()
	{
        boolean result= true; 
		try
		{
			mycsv.close();
		}
		catch (Exception e) 
        {
		    result= false;
        }
        return result;
	}
		
	public boolean endWriting()
	{
        boolean result= true; 
		try
		{
			fileOut.close();
		}
		catch (Exception e) 
        {
		    result= false;
        }
        return result;
	}
		
	public Vector getNextRow()
	{
		Vector row = null;
		try 
		{
				actualRow = mycsv.readLine();
		} 
		catch (Exception e)
		{
		    return null;
		}
		
		if (actualRow!=null)
		{
			row = (Vector) tractaFila(actualRow);
			processedRows++;
		}
		
		return row;
	}

    public int getProcessedRows()
    {
        return processedRows;
    }
    
    public void setProcessedRows(int processedRows) 
    {
        this.processedRows = processedRows;
    }
    
    private Vector tractaFila(String row)
	{	
    	row+=DELIMITER+"dummy";
    	String[] res = row.split(DELIMITER);
    	Vector salida = new Vector();    	
    	for (int i = 0 ; i < res.length-1 ; i++) {
    		salida.add( res[i] != null ? res[i].trim() : "" );
    	}

		return salida; 
	}

    public void setData(Vector pData) {
       this.pData=pData;
    }


	/**
	 * Torna en un list de string amb el format del conector el contingut de pData
	 */
    public List write() {
        Vector pData2=new Vector();
        List result=new ArrayList();
        StringBuffer buff=null;
        String reg= null;
        for (int i=0;i<getData().size();i++)
        {
            buff=new StringBuffer();
            pData2=(Vector)getData().elementAt(i);
            for (int j=0;j<pData2.size();j++){
                if (pData2.elementAt(j)!=null)
                {
                	reg= pData2.elementAt(j).toString()+";";
                	reg= reg.replace('\n',' ');
                	reg= reg.replace('\r',' ');
                    buff.append(reg);
                }
                else
                    buff.append(";");
            }
            buff.append("\r\n");
            result.add(buff.toString());
            setProcessedRows(getProcessedRows()+1);
        }
        return result;
    }
	
    public String getContentType(){
        return "text/csv";
    }

	/**
	 * Escriu al fitxer pathFileOut el contingut de pData
	 */
	public boolean writeFile() 
	{
		try {
			FileWriter fileOut = new FileWriter(this.pathFileOut); 
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
	 * Escriu al fitxer pathFileOut el contingut de pData
	 * No obre ni tanca el fitxer
	 */
	public boolean appendLines() 
	{
		if (this.pathFileOut!=null){			
	
			try {
				FileWriter _fileOut = new FileWriter(this.pathFileOut, true); /**/
				Iterator itPDataFormatat = write().iterator(); 
				
				while (itPDataFormatat.hasNext()) {
					_fileOut.write((String)itPDataFormatat.next());
				}
				
				_fileOut.close(); /**/
				
			} catch (Exception e) {
				return false;
			}
		}		
		return true;
	}

	@Override
	public boolean initEspecific() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getIdRobot() {
		return idRobot;
		
	}

	public void setIdRobot(String idRobot) {
		this.idRobot = idRobot;
		
	}

	@Override
	public int getFilasProcesadas() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setFilasProcesadas(int filasProcesadas) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getFilasTotales() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setFilasTotales(int filasTotales) {
		// TODO Auto-generated method stub
		
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

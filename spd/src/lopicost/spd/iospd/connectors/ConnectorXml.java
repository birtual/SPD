
package lopicost.spd.iospd.connectors;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class ConnectorXml /* implements Connector*/
{
	private int processedRows= 0;
	private Iterator itRows= null;
	private String pathFileIn= null;
	private String pathFileOut= null;
    private Vector pData= null;
    private String spdUsuario = "";
    private  boolean cargaAnexa=false;

	public ConnectorXml()
	{
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
        if (pathFileIn!=null && pathFileIn.toLowerCase().endsWith(".xml"))
        {
    		try
    		{
    			SAXBuilder builder = new SAXBuilder(false); 
    			Document doc = builder.build(pathFileIn);
    			Element rootElement= doc.getRootElement();
    			if (rootElement!=null)
    			{
    				List elements= rootElement.getChildren();
    				if (elements!=null)
    					itRows= elements.iterator();
    			}
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
        return result;
	}
		
	public Vector getNextRow()
	{
		Vector row = null;
		Element actualRow= null;
		try 
		{
			if (itRows!=null && itRows.hasNext())
				actualRow = (Element) itRows.next();
		} 
		catch (Exception e)
		{
		    return null;
		}
		
		if (actualRow!=null)
		{
			row = tractaFila(actualRow);
			processedRows++;
		}
		
		return row;
	}

	private Vector tractaFila(Element row)
	{
        Vector salida = new Vector();
        Iterator itCols= null;
        if (row!=null)
        {
        	List cols= row.getChildren();
        	if (cols!=null)
        		itCols= cols.iterator();
        }
        	

        while(itCols!=null && itCols.hasNext())
        {
        	Element col= (Element) itCols.next();
        	String value= null;
        	if (col!=null)
        		value=col.getValue();
            salida.add(value);
        }
		return salida; 
	}
		
	public int getProcessedRows()
	{
		return processedRows;
	}

    public void setProcessedRows(int processedRows)
    {
        this.processedRows = processedRows;
    }

    public void setData(Vector pData) {
        this.pData=pData;
    }

	/**
	 * Torna en un list de string amb el format del conector el contingut de pData
	 */
    public List write() {
        Vector pData2=new Vector();
        Vector pData=this.pData;
        List result=new ArrayList();;
        StringBuffer buff= null;
        result.add("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\r\n");
        result.add("<iosga>\r\n");
        for (int i=0;i<pData.size();i++)
        {
            buff=new StringBuffer();
            buff.append("\t<entity>\r\n");
            pData2=(Vector)pData.elementAt(i);
            for (int j=0;j<pData2.size();j++){
                buff.append("\t\t<property><![CDATA[");
                if (pData2.elementAt(j)!=null)
                    buff.append(pData2.elementAt(j));
                buff.append("]]></property>\r\n");
            }
            buff.append("\t</entity>\r\n");
            result.add(buff.toString());
            setProcessedRows(getProcessedRows()+1);
        }
        result.add("</iosga>\r\n");
        return result;
    }
    
    public String getContentType(){
        return "text/xml";
    }


	/**
	 * Escriu al fitxer pathFileOut el contingut de pData
	 */
	public boolean writeFile() 
	{
		if (this.pathFileOut!=null){		
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
		}
		return true;
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

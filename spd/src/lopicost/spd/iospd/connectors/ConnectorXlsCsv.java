package lopicost.spd.iospd.connectors;

import java.io.File;
import java.io.FileInputStream;
import org.apache.poi.hssf.usermodel.*;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;



import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ConnectorXlsCsv implements Connector
{
	private int filasProcesadas= 0;
	private int filasTotales= 0;
	
	private Iterator itRows= null;
	private String pathFileIn= null;
	private String pathFileOut= null;
    private Vector pData= null;
    private FormulaEvaluator formulaEvaluator= null;
    private String idProceso = "";
    private String idDivisionResidencia = "";
    private String idRobot = "";
    private String spdUsuario = "";
    private Sheet sheet= null;
    private  boolean cargaAnexa=false;
    

	public ConnectorXlsCsv()
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
        if (pathFileIn!=null && ( pathFileIn.toLowerCase().endsWith(".xls") || pathFileIn.toLowerCase().endsWith(".xlsx")))
        {
    		try
    		{
    			//obtaining input bytes from a file  
    			FileInputStream file = new FileInputStream(new File(pathFileIn));
    			//creating workbook instance that refers to .xls file  
    			 Workbook workbook;

   	           // Detecta el tipo de archivo y utiliza la clase correspondiente
   	            if (pathFileIn.endsWith(".xls")) {
   	                workbook = new HSSFWorkbook(file); // Para archivos xls (formato Excel 97-2003)
   	            } else if (pathFileIn.endsWith(".xlsx")) {
   	                workbook = new XSSFWorkbook(file); // Para archivos xlsx (formato Excel 2007 y posterior)
   	            } else {
   	                throw new IllegalArgumentException("Tipo de archivo no compatible: " + pathFileIn);
   	            }

    	       sheet = workbook.getSheetAt(0);
    			
    			try{
    				filasTotales=sheet.getLastRowNum();
    			}catch(Exception e){
    				result= false;
    			}
    			//evaluating cell type  
    			formulaEvaluator=workbook.getCreationHelper().createFormulaEvaluator();  
    			//itRows = sheet.rowIterator();
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
		Vector row = new Vector(); 
		//XSSFRow actualRow= null;
		HSSFRow actualRow= null;
		
		try 
		{
			//if (itRows!=null && itRows.hasNext())
			//	actualRow = (XSSFRow) itRows.next();
			if (filasProcesadas<=sheet.getLastRowNum())
			{
				//actualRow = (XSSFRow)sheet.getRow(filasProcesadas);
				actualRow = (HSSFRow)sheet.getRow(filasProcesadas);
				 
				 for(int j=0;j< actualRow.getLastCellNum();j++){
					 //XSSFCell hssFCell = (XSSFCell)actualRow.getCell(j);
					 HSSFCell hssFCell = (HSSFCell)actualRow.getCell(j);
					 if(hssFCell ==null){
	                        hssFCell = actualRow.createCell(j, CellType.STRING);
	                        //hssFCell.setCellValue("");
	                    }else{

	                        // Verificar si la celda es de tipo numérico y si su valor es un número entero
	                        if (hssFCell.getCellType() == CellType.NUMERIC && hssFCell.getNumericCellValue() == (int) hssFCell.getNumericCellValue()) {
	                            int intValue = (int) hssFCell.getNumericCellValue();
	                            hssFCell.setCellValue(String.valueOf(intValue)); // Almacenar el valor como cadena sin ".0"
	                        } else {
	                        	hssFCell.setCellValue(hssFCell.toString()); // Si no es entero, almacenar el valor tal como está
	                        }

	                            
	                        hssFCell.setCellType(CellType.STRING);
	                    }
					 	row.add(hssFCell);
				 }
			}
		} 
		catch (Exception e)
		{
			filasProcesadas++; //para saltar la fila en caso de error o nula
		    return null;
		}
		
		if (actualRow!=null)
		{
			row = tractaFila(actualRow);
			filasProcesadas++;
		}
		
		return row;
	}

	//private Vector tractaFila(XSSFRow row)
	private Vector tractaFila(HSSFRow row)
	{
        Vector salida = new Vector();
        Iterator itCell= null;

        HSSFCell cell=null;
		itCell = row.cellIterator();
		 
		 while (itCell.hasNext()) 
		{
			cell = (HSSFCell) itCell.next();
			//System.out.println(formulaEvaluator.evaluateInCell(cell).getCellType());

			switch(formulaEvaluator.evaluateInCell(cell).getCellType())  
			{  
				case NUMERIC:   //field that represents numeric cell type  
				//getting the value of the cell as a number  
				if(DateUtil.isCellDateFormatted(cell)){
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			        String  s =  sdf.format(cell.getDateCellValue());
					 //System.out.println(cell.getDateCellValue());
					 salida.add(s);System.out.println(s);
     
			    } else {
			       // System.out.println(cell.getNumericCellValue());
			        salida.add(cell.getNumericCellValue()+"" );
   
			    }
				break;  
				case STRING:    //field that represents string cell type  
				//getting the value of the cell as a string  
					try
					{
						salida.add(cell.getStringCellValue() );
					}
					catch(NullPointerException e)
					{
						salida.add("");
					}
				break;  
				case BLANK:    
					salida.add("");
					break;  
		        case BOOLEAN:
		        	salida.add(cell.getBooleanCellValue());

		            break;
				default:  salida.add("");
			}  
			
		}  
		 
		 //System.out.println(salida);
		return salida; 
	}
		
	public int getProcessedRows()
	{
		return filasProcesadas;
	}

    public void setProcessedRows(int filasProcesadas)
    {
        this.filasProcesadas = filasProcesadas;
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

	public boolean startWriting() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean endWriting() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean appendLines() {
		// TODO Auto-generated method stub
		return false;
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

	public String getIdRobot() {
		return idRobot;
		
	}

	public void setIdRobot(String idRobot) {
		this.idRobot = idRobot;
		
	}

	public int getFilasProcesadas() {
		return filasProcesadas;
	}

	public void setFilasProcesadas(int filasProcesadas) {
		this.filasProcesadas = filasProcesadas;
		
	}
	public int getFilasTotales() {
		return filasTotales;
	}

	public void setFilasTotales(int filasTotales) {
		this.filasTotales = filasTotales;
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

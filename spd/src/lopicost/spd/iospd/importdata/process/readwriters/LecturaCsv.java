
package lopicost.spd.iospd.importdata.process.readwriters;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Vector;

public class LecturaCsv implements Lectura
{
    private static final String DELIMITER = ";";

	private int processedRows= 0;
	private BufferedReader mycsv= null;
	private String actualRow= null;
	private int numColumns= 0;
    private String fileName= null;

	public LecturaCsv()
	{
	}
    
    public void setFile(String pFilename)
    {
        fileName=pFilename;
    }
	
    public boolean startReading()
    {
        boolean result= true; 
        if (fileName!=null && fileName.toLowerCase().endsWith(".csv"))
        {
           try
           {
               mycsv= new BufferedReader(new FileReader(fileName));
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
			row = tractaFila(actualRow);
			processedRows++;
		}
		
		return row;
	}

	private Vector tractaFila(String row)
	{
        String tmp=new String(row);
        int posDelim=0;
        Vector salida = new Vector();
        String token;

        while(tmp!=null && tmp.length()>0)
        {
            posDelim= tmp.indexOf(DELIMITER);
            if (posDelim==-1)
                posDelim=tmp.length();
            token= tmp.substring(0,posDelim);
            salida.add(token);
            if (tmp.indexOf(DELIMITER)>=0) 
                tmp= tmp.substring(posDelim+DELIMITER.length());
            else 
                tmp=tmp.substring(posDelim);
        }
		return salida; 
	}
		
	public int getProcessedRows()
	{
		return processedRows;
	}

	
}

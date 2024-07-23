package lopicost.spd.iospd;


import lopicost.spd.iospd.statistics.ProcessStatistics;
import lopicost.spd.utils.MessageManager;
import lopicost.spd.utils.SPDConstants;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Guarda un log de un proceso 
 * El log esta compuesto de:
 * 
 * - top: cabecera
 * - body: el detalle
 * - bottom : un resumen
 */
public class ProcessLogging 
{
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");

	private String filename;
	private BufferedWriter out;
	private String originalFilename;


	
	public ProcessLogging( String procId, String originalFilename )  
	{
		super();
    	this.filename = SPDConstants.IOSPD_OUTPATH + "/" + procId + "_" + sdf.format( new Date() ) + ".log";
    	this.originalFilename = originalFilename;
    	
	}
	public boolean init()
	{
    	try {
    		this.out = new BufferedWriter(new FileWriter(this.filename));
    		return true;
    	} catch (Exception e) {
    		return false;
    	}
	}
	
	
	/**
	 * Hace log de la cabecera
	 */
	public void top( ProcessStatistics statistics ) throws Exception
	{
        MessageManager messages = MessageManager.instance();
        
		try
		{
			if ( out != null )
			{
				out.write("\n");
				out.write("\n-------------------------------------------------------------");
				//out.write("\n"+messages.message("iospd.log.statistics.user", (context!=null?context.getContextUser().getIdName():"")));
				out.write("\n"+messages.message("iospd.log.statistics.user", ("")));
				out.write("\n"+messages.message("iospd.log.statistics.input", originalFilename));
				out.write("\n-------------------------------------------------------------");
				out.write("\n");
				out.flush();
			}
		}
		catch (Exception e) 
        {
		    throw new Exception("Error en ProcessLogging.top() : " + e.getMessage());
        }
	}
	
	
	
	
	/**
	 * Hace log del detalle
	 */
	public void body( ProcessStatistics statistics ) throws Exception
	{
		List errors = statistics.getErrors();
        MessageManager messages = MessageManager.instance();
		
		try
		{
			if ( out != null)
			{
				if ( errors != null && errors.size() > 0)
				{
					for (Iterator i = errors.iterator(); i.hasNext(); )
					{
						Object error = i.next();
						out.write( "\n" + error.toString() );
					}
				}
				else
				{
					out.write( "\n"+messages.message("iospd.log.statistics.noerrors") );
				}
				out.flush();
			}
		}
		catch (Exception e)
		{
		    throw new Exception("Error en ProcessLogging.body() : " + e.getMessage());
		}
	}

	
	
	/**
	 * Hace log del resumen
	 */
	public void bottom( ProcessStatistics statistics ) throws Exception
	{
        MessageManager messages = MessageManager.instance();
        
		try
		{
			if ( out != null )
			{
				if ( statistics != null )
				{
					out.write("\n");
					out.write("\n-------------------------------------------------------------");
					out.write("\n"+messages.message("iospd.log.statistics.totalLines", new Integer(statistics.getProcessedRowsCount())));
					out.write("\n"+messages.message("iospd.log.statistics.okLines", new Integer(statistics.getOkRowsCount())));
					out.write("\n"+messages.message("iospd.log.statistics.errorrLines", new Integer(statistics.getErrorsRowsCount())));
					out.write("\n-------------------------------------------------------------");
					out.write("\n");
					out.flush();
				}
			}
		}
		catch (Exception e) 
        {
		    throw new Exception("Error en ProcessLogging.bottom() : " + e.getMessage());
        }
	}
	
	
	/**
	 * Cierra el fichero de log
	 */
	public void close()
	{
		try {
			if ( out != null ) out.close();
		} catch (Exception e) {}
	}
	
	
	public String getLogFilename()
	{
		return filename;
	}
	
	public void write(String what)
	{
		try {
			out.write(what);
		} catch (Exception e) {e.printStackTrace();}
	}
	
	public void flush() {
		try {
			out.flush();
		} catch (Exception e) {e.printStackTrace();} 
	}
	
	
	
	

}

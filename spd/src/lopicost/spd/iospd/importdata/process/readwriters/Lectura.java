
package lopicost.spd.iospd.importdata.process.readwriters;

import java.util.Vector;


public interface Lectura
{
	public void setFile(String pFilename);
	
    public boolean startReading();
    
	public boolean endReading();
		
	public Vector getNextRow();

	public int getProcessedRows();
	
}

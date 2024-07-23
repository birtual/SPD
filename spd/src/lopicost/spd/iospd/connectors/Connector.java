package lopicost.spd.iospd.connectors;

import java.util.List;
import java.util.Vector;

public interface Connector
{
	
	public void setPathFileIn(String pFilename);
	public void setPathFileOut(String pFilename);	
    public boolean startReading();
    public boolean startWriting();    
	public boolean endReading();	
	public boolean endWriting();		
	public Vector getNextRow();
	public int getProcessedRows();
    public void setData(Vector pData);
    public List write();
    public String getContentType();
    public boolean writeFile();
    public boolean appendLines();
    public String getIdProceso();
    public void setIdProceso(String pIdProceso);

    public String getIdDivisionResidencia();
    public void setIdDivisionResidencia(String pIdDivisionResidencia);
	public String getIdRobot();
	public void setIdRobot(String idRobot);
	public int getFilasProcesadas();
	public void setFilasProcesadas(int filasProcesadas);
	public int getFilasTotales();
	public void setFilasTotales(int filasTotales);
	public String getSpdUsuario();
	public void setSpdUsuario(String spdUsuario);
	public boolean isCargaAnexa();
	public void setCargaAnexa(boolean cargaAnexa);
}

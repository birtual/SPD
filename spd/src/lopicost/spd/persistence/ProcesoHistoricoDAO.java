package lopicost.spd.persistence;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import lopicost.config.pool.dbaccess.Conexion;
import lopicost.spd.model.Aviso;
import lopicost.spd.model.Proceso;
import lopicost.spd.model.ProcesoHistorico;
import lopicost.spd.security.helper.VisibilidadHelper;


public class ProcesoHistoricoDAO extends GenericDAO{
	
	
	static String className="ProcesoHistoricoDAO";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    // Insertar un nuevo procesoHistorico
    public static boolean creaProcesoHistoricoPorLanzadera(String lanzadera) throws SQLException {
    	int result=0;
        String sql = " INSERT INTO SPD_procesosHistorico ( "
           		+ "oidProceso, version, lanzadera, nombreProceso, descripcion, usuarioCreacion,  "
           		+ "activo, parametros, tipoEjecucion, frecuenciaPeriodo, tipoPeriodo, diasSemana, diasMes, horaEjecucion, "
           		+ "maxReintentos, maxDuracionSegundos, fechaDesde, fechaHasta "
            		+ ")"
           		+ " SELECT oidProceso, version, lanzadera, nombreProceso, descripcion, usuarioCreacion, "
           		+ " activo, parametros, tipoEjecucion, frecuenciaPeriodo, tipoPeriodo, diasSemana, diasMes, horaEjecucion, "
           		+ " maxReintentos, maxDuracionSegundos, fechaDesde, fechaHasta "
           		+ " FROM SPD_procesos WHERE lanzadera = '"+lanzadera+"'";
        Connection con = Conexion.conectar(); {
   		System.out.println(className + "  - creaProcesoHistoricoPorLanzadera-->  " +sql );		
   		try {
         PreparedStatement pstat = con.prepareStatement(sql);
         result=pstat.executeUpdate();
       
     } catch (SQLException e) {
         e.printStackTrace();
     } finally {con.close();}
	
   		return result>0;
        }
    }

/*    
    public List<ProcesoHistorico> obtenerPorProceso(int oidProceso) throws SQLException {
        List<ProcesoHistorico> historiales = new ArrayList<>();
        String sql = "SELECT * FROM HistorialEjecuciones WHERE oidProceso = ?";
        try (Connection conn = Conexion.conectar();
        		PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, oidProceso);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                	ProcesoHistorico h = new ProcesoHistorico();
                    h.setOidHistorial(rs.getInt("oidHistorial"));
                    h.setOidProceso(rs.getInt("oidProceso"));
                    h.setFechaEjecucion(rs.getTimestamp("fechaEjecucion"));
                    h.setEstadoEjecucion(rs.getString("estadoEjecucion"));
                    h.setResultado(rs.getString("resultado"));
                    h.setTipoEjecucion(rs.getString("tipoEjecucion"));
                    h.setNombreArchivoResultado(rs.getString("nombreArchivoResultado"));
                    h.setDuracionSegundos(rs.getInt("duracionSegundos"));
                    h.setUsuarioEjecucion(rs.getString("usuarioEjecucion"));
                    historiales.add(h);
                }
            }
        }
        return historiales;
    }
*/

    // Obtener todos los registros del historial
    public List<ProcesoHistorico> obtenerTodos() throws SQLException {
        List<ProcesoHistorico> historiales = new ArrayList<>();
        String sql = "SELECT * FROM HistorialEjecuciones";
        try (Connection conn = Conexion.conectar();
        		PreparedStatement stmt = conn.prepareStatement(sql)) {
        	ResultSet rs = stmt.executeQuery(sql);
        }

        return historiales;
    }
}









 
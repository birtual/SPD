package lopicost.spd.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import lopicost.spd.model.Enlace;
import lopicost.config.pool.dbaccess.Conexion;
import lopicost.spd.model.Menu;
import java.util.ArrayList;
import java.util.List;

public class MenuDAO
{
    static String className;
    
    static {
        MenuDAO.className = "MenuDAO";
    }
    
    public static List findByIdProfile(final String idperfil) throws ClassNotFoundException, SQLException {
        final List<Menu> result = new ArrayList<Menu>();
        Menu menu = new Menu();
        String qry = " select pe.IDPERFIL, e.IDAPARTADO, e.IDENLACE, pe.ACTIVO, pe.ORDEN,  e.ALIASENLACE, e.NOMBREENLACE,  ";
        qry = String.valueOf(qry) + " e.PREENLACE, e.LINKENLACE, e.DESCRIPCION,  e.PARAMSENLACE, e.ACTIVO, e.NUEVAVENTANA ";
        qry = String.valueOf(qry) + " from SPD_enlace e  ";
        qry = String.valueOf(qry) + " left join SPD_perfil_enlace pe  on pe.IDENLACE=e.IDENLACE  and pe.idperfil='" + idperfil + "' ";
        qry = String.valueOf(qry) + "order by e.IDAPARTADO asc, e.IDENLACE asc";
        final Connection con = Conexion.conectar();
        System.out.println(String.valueOf(MenuDAO.className) + "--> findByIdProfile  -->" + qry);
        ResultSet resultSet = null;
        try {
            final PreparedStatement pstat = con.prepareStatement(qry);
            resultSet = pstat.executeQuery();
            while (resultSet.next()) {
            	menu = creaMenu(resultSet);
              
                result.add(menu);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return result;
        }
        finally {
            con.close();
        }
        con.close();
        return result;
    }
    
    private static Menu creaMenu(ResultSet resultSet) throws SQLException {
      	 Menu menu = new Menu();
        if (resultSet!=null) {
        	Enlace enlace = null;
          enlace = new Enlace();
          enlace.setAliasEnlace(resultSet.getString("aliasenlace"));
          enlace.setIdEnlace(resultSet.getString("idEnlace"));
          enlace.setIdApartado(resultSet.getString("idApartado"));
          enlace.setNombreEnlace(resultSet.getString("nombreEnlace"));
          enlace.setPreEnlace(resultSet.getString("preEnlace"));
          enlace.setLinkEnlace(resultSet.getString("linkEnlace"));
          enlace.setParamsEnlace(resultSet.getString("paramsEnlace"));
          enlace.setDescripcion(resultSet.getString("descripcion"));
          enlace.setActivo(resultSet.getInt("activo") == 1);
          enlace.setOrden(resultSet.getInt("orden"));
          menu.setEnlace(enlace);
          menu.setOrden(resultSet.getInt("orden"));
          menu.setIdPerfil(resultSet.getString("IDPERFIL"));
        	
        }
		return menu;
	}

	public static boolean borrar(final String idPerfil, final String idEnlace) throws ClassNotFoundException, SQLException {
        int result = 0;
        final Connection con = Conexion.conectar();
        String qry = " DELETE FROM dbo.SPD_perfil_enlace ";
        qry = String.valueOf(qry) + " where  idPerfil ='" + idPerfil + "' ";
        qry = String.valueOf(qry) + " and   idEnlace ='" + idEnlace + "' ";
        System.out.println("borrar -->" + qry);
        try {
            final PreparedStatement pstat = con.prepareStatement(qry);
            result = pstat.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }finally {con.close();}
        return result > 0;
    }
    
    public static boolean nuevo(final String idPerfil, final Enlace enlace) throws ClassNotFoundException, SQLException {
        int result = 0;
        final Connection con = Conexion.conectar();
        String qry = " INSERT INTO SPD_perfil_enlace (IDPERFIL, IDENLACE, ACTIVO, ORDEN) VALUES (";
        qry = String.valueOf(qry) + "'" + idPerfil + "', '" + enlace.getIdEnlace() + "', 1, '" + enlace.getOrden() + "')";
        System.out.println("nuevo -->" + qry);
        try {
            final PreparedStatement pstat = con.prepareStatement(qry);
            result = pstat.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }finally {con.close();}
        return result > 0;
    }

	
	    public static Menu findById( String idperfil, String idEnlace) throws ClassNotFoundException, SQLException {
	       
	        Menu menu = new Menu();
	        String qry = " select pe.IDPERFIL, e.IDAPARTADO, e.IDENLACE, pe.ACTIVO, pe.ORDEN,  e.ALIASENLACE, e.NOMBREENLACE,  ";
	        qry = String.valueOf(qry) + " e.PREENLACE, e.LINKENLACE, e.DESCRIPCION,  e.PARAMSENLACE, e.ACTIVO, e.NUEVAVENTANA ";
	        qry = String.valueOf(qry) + " from SPD_enlace e  ";
	        qry = String.valueOf(qry) + " left join SPD_perfil_enlace pe  on pe.IDENLACE=e.IDENLACE  and pe.idperfil='" + idperfil + "' and e.idEnlace='" + idEnlace + "' ";
	        final Connection con = Conexion.conectar();
	        System.out.println(String.valueOf(MenuDAO.className) + "--> findById  -->" + qry);
	        ResultSet resultSet = null;
	        try {
	            final PreparedStatement pstat = con.prepareStatement(qry);
	            resultSet = pstat.executeQuery();
	            while (resultSet.next()) {
	            	menu = creaMenu(resultSet);
	            }
	        }
	        catch (SQLException e) {
	            e.printStackTrace();
	            return menu;
	        }
	        finally {
	            con.close();
	        }
	        con.close();
	        return menu;
	    }

		public static void intercambiarPosicion(String idPerfil, String idEnlace, int posicion, int desplazamiento) throws SQLException {
	        int result = 0;
	        final Connection con = Conexion.conectar();
	        String qry = " update SPD_perfil_enlace set orden = orden + " + desplazamiento + " where idPerfil = '" + idPerfil+ "' and idEnlace= '"+idEnlace+"'";
	        String qry2 = " update SPD_perfil_enlace set orden = " + posicion + " where idPerfil = '" + idPerfil+ "' and idEnlace= '"+idEnlace+"'";
	        System.out.println("intercambiarPosicion 1  -->" + qry);
	        System.out.println("intercambiarPosicion 2  -->" + qry2);
	        try {
	            PreparedStatement pstat = con.prepareStatement(qry);
	            result = pstat.executeUpdate();
	            pstat = con.prepareStatement(qry2);
	            result = pstat.executeUpdate();
	        }
	        catch (SQLException e) {
	            e.printStackTrace();
	        }finally {con.close();}
	        
	        
	    }

    
    
}
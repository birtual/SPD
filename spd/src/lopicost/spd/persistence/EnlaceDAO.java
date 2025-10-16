package lopicost.spd.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import lopicost.spd.model.Enlace;
import lopicost.spd.model.GestSustitucionesLite;
import lopicost.config.pool.dbaccess.Conexion;
import java.util.ArrayList;
import java.util.List;
import lopicost.spd.model.Usuario;
import lopicost.spd.struts.form.EnlacesForm;
import lopicost.spd.struts.helper.EnlacesHelper;
import lopicost.spd.utils.SPDConstants;

public class EnlaceDAO
{
    static String className = "EnlaceDAO";

    /*
    public static List findByIdUserAndType(final Usuario usuario, final String idApartado) throws ClassNotFoundException, SQLException {
        final List result = new ArrayList();
        Enlace enlace = null;
        String qry = " SELECT distinct pe.orden, pe.nivel, pe.IDPERFIL, e.*, p.* ";
        qry = String.valueOf(qry) + "FROM SPD_PERFIL p ";
        qry = String.valueOf(qry) + "INNER JOIN SPD_PERFIL_ENLACE pe ON p.IDPERFIL=pe.IDPERFIL ";
        qry = String.valueOf(qry) + "INNER JOIN SPD_ENLACE e ON e.IDENLACE=pe.IDENLACE ";
        qry = String.valueOf(qry) + "INNER JOIN SPD_usuarios u ON u.perfil=p.OIDPERFIL ";
        qry = String.valueOf(qry) + " and u.idUsuario='" + ((usuario != null) ? usuario.getIdUsuario() : null) + "' ";
          if(idApartado!=null && !idApartado.equals(""))
        	qry = String.valueOf(qry) + " and e.idApartado='" + idApartado + "' ";
        qry = String.valueOf(qry) + " and pe.ACTIVO='1' and e.ACTIVO='1'  and p.ACTIVO='1'  ";
        qry = String.valueOf(qry) + " order by e.idApartado, pe.ORDEN  ";
        final Connection con = Conexion.conectar();
        System.out.println(String.valueOf(EnlaceDAO.className) + "--> findByIdUserAndType  -->" + qry);
        ResultSet resultSet = null;
        try {
        	final Usuario user = UsuarioDAO.findByIdUser((usuario != null) ? usuario.getIdUsuario() : null);
        	
            final PreparedStatement pstat = con.prepareStatement(qry);
            resultSet = pstat.executeQuery();
            while (resultSet.next()) {
            	enlace=crearEnlace(resultSet, user);
            	result.add(enlace);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return result;
        }
        finally {
        	con.close();
        }
       return result;
    }
    */
    
    public static List findByIdUserAndTypeAndLevel(final Usuario usuario, final String idApartado, int nivel) throws ClassNotFoundException, SQLException {
        final List result = new ArrayList();
        Enlace enlace = null;
        String qry = " SELECT distinct pe.orden, pe.nivel, pe.IDPERFIL, ea.ORDEN, ea.ALIASAPARTADO,  e.*, p.* ";
        qry = String.valueOf(qry) + " FROM SPD_PERFIL p ";
        qry = String.valueOf(qry) + " INNER JOIN SPD_PERFIL_ENLACE pe ON p.IDPERFIL=pe.IDPERFIL ";
        qry = String.valueOf(qry) + " INNER JOIN SPD_ENLACE e ON e.IDENLACE=pe.IDENLACE ";
        qry = String.valueOf(qry) + " LEFT JOIN SPD_ENLACEAPARTADOS ea ON e.IDAPARTADO = ea.IDAPARTADO ";
        qry = String.valueOf(qry) + " INNER JOIN SPD_usuarios u ON u.perfil=p.OIDPERFIL ";
        qry = String.valueOf(qry) + " and u.idUsuario='" + ((usuario != null) ? usuario.getIdUsuario() : null) + "' ";
        qry = String.valueOf(qry) + " and pe.nivel='" + nivel+ "'   ";
        if(idApartado!=null && !idApartado.equalsIgnoreCase("INICIO"))
        	qry = String.valueOf(qry) + " and e.idApartado='" + idApartado + "' ";
        qry = String.valueOf(qry) + " and pe.ACTIVO='1' and e.ACTIVO='1'  and ea.ACTIVO='1'  and p.ACTIVO='1'  ";
        qry = String.valueOf(qry) + " order by ea.ORDEN, e.idApartado, pe.ORDEN, e.IDENLACE  ";
        final Connection con = Conexion.conectar();
        System.out.println(String.valueOf(EnlaceDAO.className) + "--> findByIdUserAndType  -->" + qry);
        ResultSet resultSet = null;
        try {
        	final Usuario user = UsuarioDAO.findByIdUser((usuario != null) ? usuario.getIdUsuario() : null);
        	
            final PreparedStatement pstat = con.prepareStatement(qry);
            resultSet = pstat.executeQuery();
            while (resultSet.next()) {
            	enlace=crearEnlace(resultSet, user);
            	result.add(enlace);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return result;
        }
        finally {
        	con.close();
        }
       return result;
    }
    
    
    private static Enlace crearEnlace(ResultSet resultSet, Usuario user) throws SQLException {
    	Enlace enlace = new Enlace();
    	if(resultSet!=null)
    	{
    		//enlace.setAliasEnlace(resultSet.getString("aliasEnlace"));
            enlace.setIdEnlace(resultSet.getString("idEnlace"));
            String idApartado = resultSet.getString("idApartado");
            String nombreApartado = resultSet.getString("aliasapartado");
        //   idApartado=EnlacesHelper.remplazaTexto(idApartado);
            
            enlace.setIdApartado(idApartado);
            enlace.setNombreApartado(nombreApartado);
            enlace.setNombreEnlace(resultSet.getString("nombreEnlace"));
            String preEnlace = resultSet.getString("preEnlace");
            preEnlace=preEnlace.replace("$BIRT$", SPDConstants.BIRT_URL);
            enlace.setPreEnlace(preEnlace);
            
            enlace.setLinkEnlace(resultSet.getString("linkEnlace"));
            enlace.setParamsEnlace(resultSet.getString("paramsEnlace"));
            enlace.setDescripcion(resultSet.getString("descripcion"));
            enlace.setActivo(resultSet.getInt("activo") == 1);
          //  enlace.setOrden(resultSet.getInt("orden"));
            //  enlace.setNivel(resultSet.getInt("nivel"));
            enlace.setNuevaVentana(resultSet.getInt("nuevaVentana") == 1);
            if(user!=null)
            {
            	enlace.setUsuario(user);
                //Parámetro para control en apps
                if (enlace.getParamsEnlace()!=null && enlace.getParamsEnlace().contains("free1")) {
                    enlace.setParamsEnlace(enlace.getParamsEnlace().concat(user.getFree1()));
                }
            }
    	}
    	return enlace;
	}

	public static Enlace findById(final String idEnlace) throws ClassNotFoundException, SQLException {
        Enlace enlace = null;
        String qry = " SELECT e.*, ea.ALIASAPARTADO, ea.orden  ";
        qry+= " from SPD_enlace e ";
        qry+= " LEFT JOIN SPD_enlaceApartados ea ON e.IDAPARTADO = ea.IDAPARTADO ";
         qry = String.valueOf(qry) + "where e.idEnlace='" + idEnlace + "' ";
        final Connection con = Conexion.conectar();
        System.out.println(String.valueOf(EnlaceDAO.className) + "--> findByIdUserAndType  -->" + qry);
        ResultSet resultSet = null;
        try {
        	final PreparedStatement pstat = con.prepareStatement(qry);
            resultSet = pstat.executeQuery();
            if (resultSet.next()) {
            	enlace=crearEnlace(resultSet, null);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return enlace;
        }
        finally {
            con.close();
        }
      
        return enlace;
    }
    
    
    public static List findAll(final Usuario usuario, final String campoGoogle) throws ClassNotFoundException, SQLException {
        final List result = new ArrayList();
        Enlace enlace = null;
        String qry = " SELECT distinct e.*, ea.ALIASAPARTADO, ea.orden ";
        qry+=  " FROM  SPD_ENLACE e  ";
        qry+=  " LEFT JOIN SPD_ENLACEAPARTADOS ea ON e.IDAPARTADO = ea.IDAPARTADO ";
        qry+=  " where 1=1 ";
       if(campoGoogle!=null && !campoGoogle.equals(""))
        {
            qry+=  " AND e.IDAPARTADO like '%" + campoGoogle+"%' ";
            qry+=  " OR ea.ALIASAPARTADO like '%" + campoGoogle+"%' ";
            qry+=  " OR e.IDENLACE like '%" + campoGoogle+"%' ";
          //  qry+=  " OR e.ALIASENLACE like '%" + campoGoogle+"%' ";
            qry+=  " OR e.NOMBREENLACE like '%" + campoGoogle+"%' ";
            qry+=  " OR e.PREENLACE like '%" + campoGoogle+"%' ";
            qry+=  " OR e.PARAMSENLACE like '%" + campoGoogle+"%' ";
            qry+=  " OR e.DESCRIPCION like '%" + campoGoogle+"%' ";
        	
        }
    	final Usuario user = UsuarioDAO.findByIdUser((usuario != null) ? usuario.getIdUsuario() : null);
       	
        qry = String.valueOf(qry) + " order by ea.orden, e.idenlace asc ";
        final Connection con = Conexion.conectar();
        System.out.println(String.valueOf(EnlaceDAO.className) + "--> findByIdUserAndType  -->" + qry);
        ResultSet resultSet = null;
        try {
            final PreparedStatement pstat = con.prepareStatement(qry);
            resultSet = pstat.executeQuery();
            while (resultSet.next()) {
            	enlace=crearEnlace(resultSet, user);
            	result.add(enlace);
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
    
    
	public static boolean edita(Enlace enlace) throws ClassNotFoundException, SQLException {

        int result=0;
		  Connection con = Conexion.conectar();
	  	   String qry = "UPDATE dbo.SPD_ENLACE set ";
	  	   		//qry+= " set  aliasEnlace= '"+enlace.getAliasEnlace()+"', ";
	  	   		//qry+= " idApartado= '"+enlace.getIdApartado()+"', ";
	  	   		qry+= " nombreEnlace= '"+enlace.getNombreEnlace()+"', ";
	  	   		qry+= " preEnlace= '"+enlace.getPreEnlace()+"', ";
	  	   		qry+= " linkEnlace = '"+enlace.getLinkEnlace()+"', ";
	  	   		qry+= " paramsEnlace= '"+enlace.getParamsEnlace()+"', ";
	  	   		qry+= " descripcion= '"+enlace.getDescripcion()+"', ";
	  	   		qry+= " activo= '"+(enlace.isActivo()?1:0)+"', ";
	  	   		qry+= " nuevaventana= '"+(enlace.isNuevaVentana()?1:0)+"' ";
	  	   		
	       		qry+= " where idEnlace= '"+enlace.getIdEnlace()+"' ";
	      		System.out.println(className + "  - edita-->  " +qry );		
	    try {
	         PreparedStatement pstat = con.prepareStatement(qry);
	         result=pstat.executeUpdate();
	       
	     } catch (SQLException e) {
	         e.printStackTrace();
	     } finally {con.close();}

		
		return result>0;
	}

	public static boolean nuevo(EnlacesForm f) throws ClassNotFoundException, SQLException {

        int result=0;
		  Connection con = Conexion.conectar();
		  String qry = "INSERT INTO SPD_enlace  ";
		  		qry+= " ( ";
		  		qry+= " 	IDENLACE, IDAPARTADO, NOMBREENLACE, ";
		  		qry+= " 	PREENLACE, LINKENLACE, PARAMSENLACE, DESCRIPCION,"; 
		  		qry+= " 	ACTIVO, NUEVAVENTANA ";
		  		qry+= " ) VALUES ( ";
	  	   		qry+= " '"+f.getIdEnlace()+"', '"+f.getIdApartado()+"', '"+f.getNombreEnlace()+"', ";
	  	   		qry+= " '"+f.getPreEnlace()+"', '"+f.getLinkEnlace()+"', '"+f.getParamsEnlace()+"', '"+f.getDescripcion()+"', ";
	  	   		qry+= " '"+(f.isActivo()?1:0)+"', '"+(f.isNuevaVentana()?1:0)+"' ";
		  		qry+= " )  ";

                
	      		System.out.println(className + "  - nuevo-->  " +qry );		
	    try {
	         PreparedStatement pstat = con.prepareStatement(qry);
	         result=pstat.executeUpdate();
	       
	     } catch (SQLException e) {
	         e.printStackTrace();
	     } finally {con.close();}
		
		return result>0;
	}

	public static boolean borrar(String idEnlace) throws ClassNotFoundException, SQLException {

        int result=0;
		  Connection con = Conexion.conectar();
	  	   String qry = "DELETE dbo.SPD_ENLACE ";
	       		qry+= " WHERE IDENLACE= '"+idEnlace+"' ";
	       		System.out.println(className + "  - borrar-->  " +qry );		
	 	
	    try {
	         PreparedStatement pstat = con.prepareStatement(qry);
	         result=pstat.executeUpdate();
	       
	     } catch (SQLException e) {
	         e.printStackTrace();
	     } finally {con.close();}

		
		return result>0;
	}

	public static Enlace findByPosicion(String idPerfil, int i) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
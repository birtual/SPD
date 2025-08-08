package lopicost.spd.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lopicost.config.pool.dbaccess.Conexion;
import lopicost.spd.model.Farmacia;

 
 
public class FarmaciaDAO {
	
	
	static String className="FarmaciaDAO";
	
	  public static Farmacia getFarmaciaPorUserAndId(String spdUsuario, String idFarmacia) throws Exception {

		  Farmacia  c = null;
			String qry= "  	select distinct  f.* from dbo.bd_farmacia f ";
			qry+= "  	inner join dbo.bd_divisionResidencia d on d.idFarmacia=f.idFarmacia ";
			qry+= "  	inner join dbo.bd_residencia r on (d.idResidencia=r.idResidencia)   ";
			qry+= "  	inner  join SPD_usuarios_permisos up on (  ";
			qry+= "         (d.idFarmacia = up.idFarmacia and up.idFarmacia is not null)  ";
			qry+= "         or (d.idDivisionResidencia=up.idDivisionResidencia and up.idDivisionResidencia is not null)  ";
			qry+= "  	       or (d.idRobot=up.idRobot and up.idRobot is not null)  ";
			qry+= "  	       or (d.idResidencia=up.idResidencia and up.idResidencia is not null))  ";
			qry+= "  	inner join SPD_usuarios u on u.idUsuario=up.idUsuario  ";
			qry+= "  	where up.idUsuario='"+spdUsuario+"' ";
			qry+= "  	and UPPER(u.estado)='ACTIVO' ";
			qry+= "  	and UPPER(r.status)='ACTIVA' ";
			qry+= "  	and f.idFarmacia='"+idFarmacia+"' ";
			  
	    	 Connection con = Conexion.conectar();
	    	 System.out.println(className + "--> getFarmaciaPorUserAndId -->" +qry );		
	    	 ResultSet resultSet = null;
	    	
	    	 try {
	    		 PreparedStatement pstat = con.prepareStatement(qry);
	    		 resultSet = pstat.executeQuery();

	    		 while (resultSet.next()) {
	    			  c =creaFarmacia(resultSet);
	    		 }
		     } catch (SQLException e) {
		         e.printStackTrace();
		     }finally {con.close();}
	    	 return c;
	  }



	  public static List<Farmacia> getFarmaciasPorUser(String spdUsuario) throws Exception {

			String qry= "  	select distinct  f.* from dbo.bd_farmacia f ";
			qry+= "  	inner join dbo.bd_divisionResidencia d on d.idFarmacia=f.idFarmacia ";
			qry+= "  	inner join dbo.bd_residencia r on (d.idResidencia=r.idResidencia)   ";
			qry+= "  	inner  join SPD_usuarios_permisos up on (  ";
			qry+= "         (d.idFarmacia = up.idFarmacia and up.idFarmacia is not null)  ";
			qry+= "         or (d.idDivisionResidencia=up.idDivisionResidencia and up.idDivisionResidencia is not null)  ";
			qry+= "  	       or (d.idRobot=up.idRobot and up.idRobot is not null)  ";
			qry+= "  	       or (d.idResidencia=up.idResidencia and up.idResidencia is not null))  ";
			qry+= "  	inner join SPD_usuarios u on u.idUsuario=up.idUsuario  ";
			qry+= "  	where up.idUsuario='"+spdUsuario+"' ";
			qry+= "  	and UPPER(u.estado)='ACTIVO' ";
			qry+= "  	and UPPER(r.status)='ACTIVA' ";
			
			  
	    	 Connection con = Conexion.conectar();
	
	    	 System.out.println(className + "--> getFarmaciasPorUser -->" +qry );		
  
 	    	 ResultSet resultSet = null;

	    	 List<Farmacia> lista= new ArrayList<Farmacia>();	
	    	 try {
	    		 PreparedStatement pstat = con.prepareStatement(qry);
	    		 resultSet = pstat.executeQuery();

	    		 while (resultSet.next()) {
	    			 Farmacia  c =creaFarmacia(resultSet);
	    			 lista.add(c);
	    		 }
		     } catch (SQLException e) {
		         e.printStackTrace();
		     }finally {con.close();}
			    
	    	 return lista;
	  }

	  
	  private static Farmacia creaFarmacia(ResultSet resultSet) throws SQLException {
		  		Farmacia farm = new Farmacia();
		        if (resultSet!=null) {
		          farm.setCodigoUP(resultSet.getString("codigoUP"));
		          farm.setCodPostal(resultSet.getString("codPostal"));
		          farm.setContacto(resultSet.getString("contacto"));
		          farm.setDireccion(resultSet.getString("direccion"));
		          farm.setEmail(resultSet.getString("email"));
		          farm.setFax(resultSet.getString("fax"));
		          farm.setFechaAlta(resultSet.getDate("fechaAlta"));
		          farm.setFechaBaja(resultSet.getDate("fechaBaja"));
		          farm.setIdFarmacia(resultSet.getString("idFarmacia"));
		          farm.setNombreFarmacia(resultSet.getString("nombreFarmacia"));
		          farm.setPoblacion(resultSet.getString("poblacion"));
		          farm.setProvincia(resultSet.getString("provincia"));
		          farm.setTelefono(resultSet.getString("telefono"));
			        	
		        }
				return farm;
			}



	public static Farmacia getFarmaciaPorIdDivisionResidencia(String spdUsuario, String idDivisionResidencia) throws Exception {

			  Farmacia  c = null;
				String qry= "  	select distinct  f.* from dbo.bd_farmacia f ";
				qry+= "  	inner join dbo.bd_divisionResidencia d on d.idFarmacia=f.idFarmacia ";
				qry+= "  	inner join dbo.bd_residencia r on (d.idResidencia=r.idResidencia)   ";
				qry+= "  	inner  join SPD_usuarios_permisos up on (  ";
				qry+= "         (d.idFarmacia = up.idFarmacia and up.idFarmacia is not null)  ";
				qry+= "         or (d.idDivisionResidencia=up.idDivisionResidencia and up.idDivisionResidencia is not null)  ";
				qry+= "  	       or (d.idRobot=up.idRobot and up.idRobot is not null)  ";
				qry+= "  	       or (d.idResidencia=up.idResidencia and up.idResidencia is not null))  ";
				qry+= "  	inner join SPD_usuarios u on u.idUsuario=up.idUsuario  ";
				qry+= "  	where up.idUsuario='admin' "; //TO - DO cambiarlo
				qry+= "  	and UPPER(u.estado)='ACTIVO' ";
				qry+= "  	and UPPER(r.status)='ACTIVA' ";
				qry+= "  	and d.idDivisionResidencia='"+idDivisionResidencia+"' ";
				  
		    	 Connection con = Conexion.conectar();
		    	 System.out.println(className + "--> getFarmaciaPorIdDivisionResidencia -->" +qry );		
		    	 ResultSet resultSet = null;
		    	
		    	 try {
		    		 PreparedStatement pstat = con.prepareStatement(qry);
		    		 resultSet = pstat.executeQuery();

		    		 while (resultSet.next()) {
		    			  c =creaFarmacia(resultSet);
		    		 }
			     } catch (SQLException e) {
			         e.printStackTrace();
			     }finally {con.close();}
		    	 return c;
		  }

	
}
 
package lopicost.spd.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lopicost.config.pool.dbaccess.Conexion;
import lopicost.spd.model.DivisionResidencia;
import lopicost.spd.security.helper.VisibilidadHelper;
import lopicost.spd.struts.form.GenericForm;

 
 
public class DivisionResidenciaDAO {
	
	static String className = "DivisionResidenciaDAO";
	  
    public static DivisionResidencia getDivisionResidenciaByOid(String spdUsuario, int oid) throws Exception {
     	
    	Connection con = Conexion.conectar();
        DivisionResidencia  c =new DivisionResidencia();
        if(oid>0)
		{
			String qry = "SELECT d.*, r.* FROM dbo.bd_divisionResidencia d  ";
			qry+= " INNER JOIN dbo.bd_residencia r  ON d.idResidencia=r.idResidencia ";
			qry+= " WHERE d.oidDivisionResidencia IN ( " + VisibilidadHelper.oidDivisionResidenciasVisibles(spdUsuario)  + ")";
			qry+= " AND d.oidDivisionResidencia = '"+oid+"' ";

			
			System.out.println(className + "  - getDivisionResidenciaByOid -->  " +qry );		
	         ResultSet resultSet = null;
	       try {
	            PreparedStatement pstat = con.prepareStatement(qry);
	            resultSet = pstat.executeQuery();

	            while (resultSet.next()) {
	            	c=creaObjetoDivisionResidencia(resultSet);
	            }
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }finally {con.close();}
	 
		} else 
			System.out.println("getDivisionResidenciaByOid --> Residencia no escogida");		
	        return c;
    }
    

  
    public static DivisionResidencia getDivisionResidenciaById(String spdUsuario, String id) throws Exception {
     	
    	Connection con = Conexion.conectar();
        DivisionResidencia  c =new DivisionResidencia();
		if(id!=null && !id.equals(""))
		{
			String qry = "SELECT d.*, r.* FROM dbo.bd_divisionResidencia d  ";
			qry+= " INNER JOIN dbo.bd_residencia r  ON d.idResidencia=r.idResidencia ";
			qry+= " WHERE 1=1 ";
			qry+= " AND d.oidDivisionResidencia IN ( " + VisibilidadHelper.oidDivisionResidenciasVisibles(spdUsuario)  + ")";
			qry+= " AND d.idDivisionResidencia = '"+id+"' ";


		 	   System.out.println(className + "  - getDivisionResidenciaById -->  " +qry );		
	         ResultSet resultSet = null;
	       try {
	            PreparedStatement pstat = con.prepareStatement(qry);
	            resultSet = pstat.executeQuery();

	            while (resultSet.next()) {
	            	c=creaObjetoDivisionResidencia(resultSet);
	            }
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }finally {con.close();}
	 
		} else 
			System.out.println("getDivisionResidenciaById --> Residencia no escogida");		
	        return c;
    }


	public static List<DivisionResidencia> getListaDivisionResidencias(String spdUsuario) throws Exception {
	  	
		   Connection con = Conexion.conectar();
	        String qry = "SELECT d.*, r.* from dbo.bd_divisionResidencia d ";
				qry+= " INNER JOIN dbo.bd_residencia r  ON d.idResidencia=r.idResidencia ";
				qry+= " WHERE d.oidDivisionResidencia IN ( " + VisibilidadHelper.oidDivisionResidenciasVisibles(spdUsuario)  + ")";
				qry+= " ORDER BY d.nombreDivisionResidencia";
		 	   System.out.println(className + "  - getListaDivisionResidencias -->  " +qry );		
	        	
	         ResultSet resultSet = null;
	 		List<DivisionResidencia> listaDivisionResidencias= new ArrayList<DivisionResidencia>();
	       try {
	            PreparedStatement pstat = con.prepareStatement(qry);
	            resultSet = pstat.executeQuery();

	            while (resultSet.next()) {
	            	DivisionResidencia  c = creaObjetoDivisionResidencia(resultSet);
	            	if(c!=null)
	            		listaDivisionResidencias.add(c);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }finally {con.close();}
	 
	        return listaDivisionResidencias;
}

    
	public static List<DivisionResidencia> getListaDivisionResidenciasSinSustXResi(String spdUsuario, int oidGestSustituciones) throws Exception {
	  	
		   Connection con = Conexion.conectar();
	        String qry = "SELECT d.*, r.* FROM dbo.bd_divisionResidencia d.";
				qry+= " INNER JOIN dbo.bd_residencia r  ON d.idResidencia=r.idResidencia ";
	        	qry+= " WHERE d.oidDivisionResidencia IN ( " + VisibilidadHelper.oidDivisionResidenciasVisibles(spdUsuario)  + ")";
	        	qry+= " AND d.oidDivisionResidencia NOT IN ";
	        	qry+= "		 (SELECT gr.oidDivisionResidencia FROM dbo.gest_sustitucionesXResi gr WHERE gr.oidGestSustituciones='" +oidGestSustituciones+ "' )";
	        	qry+= " ORDER BY d.nombreDivisionResidencia";
        	
	
	 	  	 System.out.println(className + "  - getListaDivisionResidenciasSinSustXResi -->  " +qry );		

	         ResultSet resultSet = null;
	 		List<DivisionResidencia> listaDivisionResidencias= new ArrayList<DivisionResidencia>();
	       try {
	            PreparedStatement pstat = con.prepareStatement(qry);
	            resultSet = pstat.executeQuery();

	            while (resultSet.next()) {
	            	DivisionResidencia  c =creaObjetoDivisionResidencia(resultSet);
	            	if(c!=null)
	            		listaDivisionResidencias.add(c);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }finally {con.close();}
	 
	        return listaDivisionResidencias;
}
	
	
	private static DivisionResidencia creaObjetoDivisionResidencia(ResultSet resultSet) throws SQLException {
        
		DivisionResidencia  c =null;
		if(resultSet!=null){
			c =new DivisionResidencia();
			c.setOidResidencia(resultSet.getInt("oidResidencia"));
			c.setIdResidencia(resultSet.getString("idResidencia"));
	    	c.setNombreDivisionResidencia(resultSet.getString("nombreDivisionResidencia"));
	    	c.setIdDivisionResidencia(resultSet.getString("idDivisionResidencia"));
	    	c.setIdFarmacia(resultSet.getString("idFarmacia"));
	    	c.setIdRobot(resultSet.getString("idRobot"));
	        c.setFechaAlta(resultSet.getDate("fechaAlta"));
	        c.setOidDivisionResidencia(resultSet.getInt("oidDivisionResidencia"));
	    	c.setNombreBolsa(resultSet.getString("nombreBolsa"));
	    	c.setIdProcessIospd(resultSet.getString("idProcessIospd"));
	    	c.setExtRE(resultSet.getInt("extRE"));
	    	c.setExtRE_diaSemana(resultSet.getInt("extRE_diaSemana"));
	    	c.setExtRE_diaSemanaLiteral(getDiaLiteral(resultSet.getInt("extRE_diaSemana")));
	    	c.setIdLayout(resultSet.getString("layoutBolsa"));
	    	c.setLocationId(resultSet.getString("locationID"));
	    	c.setCargarSoloCipsExistentes(resultSet.getInt("cargarSoloCipsExistentes"));
			
		}
    	return c;
	}

	
	private static String getDiaLiteral(int numDia) {
		String res="";
		switch (numDia) {
		case 1: res= "lunes"; break;
		case 2: res= "martes";break;
		case 3: res= "miércoles";break;
		case 4: res= "jueves";break;
		case 5: res= "viernes";break;
		case 6: res= "sábado";break;
		case 7: res= "domingo";break;
		} 
		return res;
	}


	public static List<DivisionResidencia> getSecurityListaDivisionResidencias(String spdUsuario) throws Exception {
	  	
		   Connection con = Conexion.conectar();
	        String qry = "SELECT d.*, r.* FROM dbo.bd_divisionResidencia d";
			qry+= " INNER JOIN dbo.bd_residencia r  ON d.idResidencia=r.idResidencia ";
			qry+= " WHERE d.oidDivisionResidencia IN ( " + VisibilidadHelper.oidDivisionResidenciasVisibles(spdUsuario)  + ")";
   	        qry+= " ORDER BY d.nombreDivisionResidencia";

 	  	     System.out.println(className + "  - getSecurityListaDivisionResidencias -->  " +qry );		
	        	
	       ResultSet resultSet = null;
	 		List<DivisionResidencia> listaDivisionResidencias= new ArrayList<DivisionResidencia>();
	       try {
	            PreparedStatement pstat = con.prepareStatement(qry);
	            resultSet = pstat.executeQuery();

	            while (resultSet.next()) {
	            	DivisionResidencia  c =creaObjetoDivisionResidencia(resultSet);
	            	if(c!=null)
	            		listaDivisionResidencias.add(c);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	 
	        return listaDivisionResidencias;
}

	/**
	 * @deprecated
	 * @param spdUsuario
	 * @return
	 * @throws Exception
	 */
	public static boolean updateACeroResisUser(String spdUsuario) throws Exception {

        int result=0;
		  Connection con = Conexion.conectar();
	  	   String qry = " UPDATE dbo.bd_divisionResidencia set  extRE= 0 ";
	  	   		qry+= " WHERE oidDivisionResidencia IN ( " + VisibilidadHelper.oidDivisionResidenciasVisibles(spdUsuario)  + ")";

	  	   		System.out.println(className + "  - updateACeroResisUser-->  " +qry );		
	    try {
	         PreparedStatement pstat = con.prepareStatement(qry);
	         result=pstat.executeUpdate();
	       
	     } catch (SQLException e) {
	         e.printStackTrace();
	     }finally {con.close();}
		
		return result>0;
	}

/**
 * @deprecated
 * @param spdUsuario
 * @param enlacesForm
 * @return
 * @throws Exception
 */
	public static boolean updateAUnoSeleccionadas(String spdUsuario, GenericForm enlacesForm) throws Exception {
	      int result=0;
			  Connection con = Conexion.conectar();
			  String[] oidVariasDivisionResidencia = enlacesForm.getOidVariasDivisionResidencia();
	
		  	   		String textoIn =" ";
		  	   		int i = 0;
		            for (i = 0; i < oidVariasDivisionResidencia.length-1; i++) {
		            	textoIn+= oidVariasDivisionResidencia[i] + ",";
		            }
		            textoIn+= oidVariasDivisionResidencia[i]; // la última sin coma final 
		 	  	  
		            String qry = " UPDATE dbo.bd_divisionResidencia set  extRE= 1 ";
		  	   		qry+= " WHERE oidDivisionResidencia IN ( " + VisibilidadHelper.oidDivisionResidenciasVisibles(spdUsuario)  + ")";
					qry+= " AND  oidDivisionResidencia in (" + textoIn +")";
			  	   		
		  	  	     System.out.println(className + "  - updateAUnoSeleccionadas-->  " +qry );		
		    try {
		         PreparedStatement pstat = con.prepareStatement(qry);
		         result=pstat.executeUpdate();
		       
		     } catch (SQLException e) {
		         e.printStackTrace();
		     }
		    finally {con.close();}
			return result>0;
		}
		
	/**
	 * Método para dar de alta una divisionResidencia,
	 * @param spdUsuario
	 * @param div
	 * @return
	 * @throws Exception
	 * Previamente ha de existir Farmacia, Residencia, Robot
	 */
	public static boolean nuevo(String spdUsuario, DivisionResidencia div) throws Exception {
	      int result=0;
		  Connection con = Conexion.conectar();
 	  	  
		  String qry = " INSERT INTO bd_divisionResidencia  ";
		  	qry+=" ( ";
		  	qry+=" 	idDivisionResidencia, nombreDivisionResidencia, ";		  	
		  	qry+=" 	oidResidencia, idResidencia, ";
		  	qry+=" 	idFarmacia, idRobot, fechaAlta, ";
		  	qry+=" 	nombreBolsa, idProcessIospd, tipoCLIfarmatic, ";
		  	qry+=" 	extRE, extRE_diaSemana  ";
		  	qry+=" ) VALUES ( ";
		  	qry+=" '"+ div.getIdDivisionResidencia()+"', '"+ div.getNombreDivisionResidencia()+"', ";
		  	qry+=" '"+ div.getOidResidencia()+"' , '"+ div.getIdResidencia()+"', ";
		  	qry+=" '"+ div.getIdFarmacia()+"' , '"+ div.getIdRobot()+"', CONVERT(datetime, getdate(), 120),   ";
		  	qry+=" '"+ div.getNombreBolsa()+"', '"+ div.getIdProcessIospd()+"', '"+ div.getTipoCLIfarmatic()+"', ";
		  	qry+=" '"+ div.getExtRE()+"', '"+ div.getExtRE_diaSemana()+"' ";
		  	
			  	   		
		  	System.out.println(className + "  - nuevo-->  " +qry );		
		    try {
		         PreparedStatement pstat = con.prepareStatement(qry);
		         result=pstat.executeUpdate();
		       
		     } catch (SQLException e) {
		         e.printStackTrace();
		     }
		    finally {con.close();}
			return result>0;
		}
		
	
	
	  

	
}


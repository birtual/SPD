package lopicost.spd.persistence;


import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lopicost.config.pool.dbaccess.Conexion;
import lopicost.spd.security.helper.VisibilidadHelper;
import lopicost.spd.struts.bean.CabecerasXLSBean;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.struts.form.FicheroResiForm;
import lopicost.spd.utils.SPDConstants;
import lopicost.spd.utils.StringUtil;
 
 
public class CabecerasXLSDAO2 {
	
	static String className="CabecerasXLSDAO";
	private final String cLOGGERHEADER = className + ":";
	private final String cLOGGERHEADER_ERROR = cLOGGERHEADER + "ERROR: : ";

	public static List<CabecerasXLSBean>  list(String spdUsuario,  int OidDivisionResidencia) throws Exception {
		  
		List<CabecerasXLSBean>  result = new ArrayList();
		
		Connection con = Conexion.conectar();
		   String qry = "SELECT * FROM dbo.SPD_cabecerasXLS g INNER JOIN bd_divisionResidencia d on g.idDivisionResidencia=d.idDivisionResidencia ";
	 			qry+= " WHERE g.idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
	 			qry+= " AND d.OidDivisionResidencia ='" + OidDivisionResidencia+"'";
	 			qry+= " ORDER BY g.posicionEnVistas ASC";

	   			System.out.println(className + "--> list -->  " +qry );		
		     	ResultSet resultSet = null;
		     	CabecerasXLSBean c = null;
		 	    try {
		 	         PreparedStatement pstat = con.prepareStatement(qry);
		 	         resultSet = pstat.executeQuery();

		 	         while (resultSet.next()) {
  
		 	         c = creaCabecerasXLSBean(resultSet);
		 	        if(c!=null)
		 	        	result.add(c);
		 	         }
				 	} catch (SQLException e) {
		 	         e.printStackTrace();
		 	     }finally {con.close();}

		 	     return result;
		 	 }

	
	
	private static CabecerasXLSBean creaCabecerasXLSBean(ResultSet resultSet) throws SQLException {
		CabecerasXLSBean c = null;
		if(resultSet!=null)
		{
			c = new CabecerasXLSBean();
			//c.setFechaInsert(resultSet.getDate("fechaInsert"));
			c.setHoraToma(resultSet.getString("horaToma"));
			c.setIdDivisionResidencia(resultSet.getString("idDivisionResidencia"));
			c.setIdToma(resultSet.getString("idToma"));
			c.setLangToma(resultSet.getInt("langToma"));
			c.setNombreToma(resultSet.getString("nombreToma"));
			c.setOidCabeceraXLS(resultSet.getInt("oidCabeceraXLS"));
			c.setPosicionEnBBDD(resultSet.getInt("posicionEnBBDD"));
			c.setPosicionEnVistas(resultSet.getInt("posicionEnVistas"));
			c.setTipo(resultSet.getString("tipo"));
			c.setDesdeTomaPrimerDia(resultSet.getInt("desdeTomaPrimerDia")==1);
		    c.setHastaTomaUltimoDia(resultSet.getInt("hastaTomaUltimoDia")==1);
		}
		return c;
	}



	public static FicheroResiBean getCabecerasXLS(String spdUsuario,  String idDivisionResidencia) throws Exception {
		   Connection con = Conexion.conectar();
		   String qry = "SELECT * FROM dbo.SPD_cabecerasXLS g  ";
	 			qry+= " WHERE g.idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
	 			qry+= " AND g.idDivisionResidencia ='" + idDivisionResidencia+"'";
	 			qry+= " ORDER BY g.posicionEnBBDD ASC";

	   			System.out.println(className + "--> getCabecerasXLS -->  " +qry );		
		     	ResultSet resultSet = null;
		     	FicheroResiBean f = null;
		 	    try {
		 	         PreparedStatement pstat = con.prepareStatement(qry);
		 	         resultSet = pstat.executeQuery();
		 	         
		 	         f = creaFicheroResiBean(resultSet);

		 	        
				 	} catch (SQLException e) {
		 	         e.printStackTrace();
		 	     }finally {con.close();}

		 	     return f;
		 	 }

	
    public static FicheroResiBean getCabecerasXLSByOidCabecera(String spdUsuario,  int  oidFicheroResiCabecera) throws Exception {
     	
    	Connection con = Conexion.conectar();
        FicheroResiBean  c =new FicheroResiBean();
        if(oidFicheroResiCabecera>0)
		{
    		String qry = "SELECT * FROM dbo.SPD_cabecerasXLS g  ";
 			qry+= " WHERE g.idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
			qry+= " AND g.idDivisionResidencia IN  ";
			qry+= " 	( SELECT idDivisionResidencia FROM SPD_ficheroResiDetalle WHERE oidFicheroResiCabecera= '"+oidFicheroResiCabecera+"' ) ";
			qry+= " ORDER BY g.posicionEnBBDD ASC";

			System.out.println(className + "  - getCabecerasXLSByOidCabecera -->  " +qry );		
	         ResultSet resultSet = null;
	       try {
	            PreparedStatement pstat = con.prepareStatement(qry);
	            resultSet = pstat.executeQuery();
	            	c=creaFicheroResiBean(resultSet);
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }finally {con.close();}
	 
		} else 
			System.out.println("getCabecerasXLSByOidCabecera --> Error ");		
	        return c;
    }
    
    

	private static FicheroResiBean creaFicheroResiBean(ResultSet resultSet) throws SQLException {
         FicheroResiBean f = new FicheroResiBean();
         int i = 0;
         int numeroDeTomasBase=0; 
         while (resultSet.next()) {
        	 f.setIdDivisionResidencia(resultSet.getString("idDivisionResidencia")); //será el mismo valor en el bucle 
        	 i++; // Incrementa i para la próxima llamada
        	 
        	 //para detectar las tomas BASE que son las que se reciben en Excel, normalmente 5 o 6, el resto son EXTRAS creadas posteriormente
        	 String tipo = resultSet.getString("tipo"); 
        	 if(tipo!=null && tipo.equalsIgnoreCase("BASE"))
        		 numeroDeTomasBase++;
        	 
            // String nombreToma = resultSet.getString("nombreToma") + "##" + resultSet.getString("posicionEnVistas");
        	 String nombreToma = resultSet.getString("nombreToma");
             String methodName = "setResiToma" + i;
           
             try {
                   Method method = FicheroResiBean.class.getMethod(methodName, String.class);
                   method.invoke(f, nombreToma);
                   
             
               } catch (Exception e) {
                   e.printStackTrace();
                  // Manejo de excepciones según sea necesario
               }
            }
         f.setNumeroDeTomasBase(numeroDeTomasBase);
         f.setNumeroDeTomas(i);
	     f.setTipoRegistro(SPDConstants.REGISTRO_CABECERA);
	     f.setIdEstado("ORIGINAL");
	     if(i==0) return null; //no lo encuentra
	     
  		return f;
	}


/*	public static boolean edita(String spdUsuario, FicheroResiForm formulari, FicheroResiBean cabPlantilla, FicheroResiBean cab) throws Exception {
        int result=0;
		Connection con = Conexion.conectar();
	//		  String log=checkTratamientoValido(b);
			//  boolean datosOk=(log!=null&&!log.equals("")?false:true);
		
		int numeroDeTomas=cabPlantilla.getNumeroDeTomas()+1;
		if(numeroDeTomas>0)
		{
			String resiTomaX = "ResiToma" + (numeroDeTomas);
			// Utilizar reflexión para acceder al método getResiTomaX en el formulario
		    Method getterMethod = formulari.getClass().getMethod("get" + resiTomaX);
		    String resiTomaXValue = (String) getterMethod.invoke(formulari);
		    String resiTomaLiteral = formulari.getResiTomaLiteral();
		    if(resiTomaXValue==null || resiTomaXValue.equals("")) resiTomaXValue=numeroDeTomas+":00";
		    if(resiTomaLiteral==null || resiTomaLiteral.equals("")) resiTomaLiteral=resiTomaXValue;
		           
			String qry = "INSERT INTO SPD_cabecerasXLS ( idDivisionResidencia, idToma, nombreToma, posicionEnBBDD, posicionEnVistas, horaToma, langToma, tipo) VALUES  ";
			qry+= "  ( '"+cab.getIdDivisionResidencia() +"' , '"+resiTomaXValue.replace(":", "") +"', '"+resiTomaLiteral +"', '"+numeroDeTomas +"', '"+numeroDeTomas +"', '"+resiTomaXValue +"', '0', 'EXTRA' ) ";
			  	    	    		
			System.out.println(className + "--> edita -->" +qry );		

			try {
				PreparedStatement pstat = con.prepareStatement(qry);
				result=pstat.executeUpdate();
			       
				//actualización de las líneas creadas en la importación 
				FicheroResiDetalleDAO.addTomaLineas(spdUsuario, cab);		
				//actualización de la cabecera creada en la importación 
				FicheroResiDetalleDAO.addTomaCabecera(spdUsuario, cab, resiTomaX, resiTomaXValue);

			} catch (SQLException e) {
				 e.printStackTrace();
			}finally {con.close();}
		}
			return result>0;
		}
*/

	public static boolean esCampoBorrable(String spdUsuario, int oidFicheroResiCabecera, int numeroDeToma) throws Exception {

		boolean borrable=false;
		String resiTomaX = "ResiToma" + (numeroDeToma);
		Connection con = Conexion.conectar();
        FicheroResiBean  c =new FicheroResiBean();
        
   		String qry = "SELECT CASE WHEN COUNT(*) > 0 THEN 'NOBORRAR' ELSE 'BORRABLE' END as resultado   ";
		qry+= " FROM SPD_ficheroResiDetalle ";
		qry+= " WHERE idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ") ";
		qry+= " AND tipoRegistro = 'LINEA' ";
		qry+= " AND " +  resiTomaX + " IS NOT NULL AND  " +  resiTomaX + "  != ''  AND  UPPER('"+  resiTomaX +"')  != 'NULL' ";
		qry+= " AND oidFicheroResiCabecera = '"+oidFicheroResiCabecera+"' ";
			
		System.out.println(className + "  - esCampoBorrable -->  " +qry );		
		ResultSet resultSet = null;
		try {
			PreparedStatement pstat = con.prepareStatement(qry);
	        resultSet = pstat.executeQuery();
	        resultSet.next();
	        borrable = (resultSet.getString("resultado").equalsIgnoreCase("BORRABLE")?true:false);
	            
	       } catch (SQLException e) {
	            e.printStackTrace();
	        }finally {con.close();}
	       return borrable;
    }

	public static boolean borradoDeToma(String spdUsuario, FicheroResiForm formulari, FicheroResiBean cabPlantilla, FicheroResiBean cab, int numeroDeToma) throws Exception {
        int result=0;
       
        
			  Connection con = Conexion.conectar();
			  String resiTomaX = "resiToma" + (numeroDeToma);
		  	
			  String qry = " DELETE FROM dbo.SPD_cabecerasXLS ";
		  	  qry+= "  WHERE idDivisionResidencia ='"+cab.getIdDivisionResidencia() +"' ";   	 
		  	  qry+= "  AND posicionEnBBDD ='"+numeroDeToma+"'  ";   	    
		  	  qry+= "  AND tipo ='EXTRA'  ";   	   	
		  	  System.out.println(className + "--> borradoDeToma -->" +qry );		

		      		
		  	  //actualización de la cabecera creada en la importación 
		  	  FicheroResiDetalleDAO.borraTomaDelProceso(spdUsuario, cab, resiTomaX);

		  	  
		    try {
		         PreparedStatement pstat = con.prepareStatement(qry);
		         result=pstat.executeUpdate();
		       
		     } catch (SQLException e) {
		         e.printStackTrace();
		     }finally {con.close();}

			
			return result>0;
		}


	public static boolean crearPlantilla(String spdUsuario, FicheroResiForm form) throws Exception {
        int result=0;
       	Connection con = Conexion.conectar();

        try {     	
	 		FicheroResiBean ultimaCabecera = FicheroResiDetalleDAO.getGestFicheroResiBolsaByForm(spdUsuario, -1, form, true, false, false);
			int numeroDeTomas=ultimaCabecera.getNumeroDeTomas();
			String resiTomaX = "";
			for(int i=1;i<=numeroDeTomas;i++)
			{
				resiTomaX = "resiToma" + (i);
	 				
				String qry = "INSERT INTO SPD_cabecerasXLS (idDivisionResidencia, idToma, nombreToma, posicionEnBBDD, posicionEnVistas, horaToma, langToma, tipo, desdeTomaPrimerDia, hastaTomaUltimoDia)  ";
				qry+= " SELECT '"+ultimaCabecera.getIdDivisionResidencia() +"',  ";
				qry+= " '"+getIdToma("'" +resiTomaX+"'", i)+"' , "+resiTomaX+", " + i + ", " + i + ", '"+getHoraToma("+'"+resiTomaX+"'", i)+"' , 0, '"+getBase(i)+"', 0, 0 ";
				qry+= " FROM SPD_ficheroResiDetalle WHERE oidFicheroResiCabecera= '"+form.getOidFicheroResiCabecera() +"' AND tipoRegistro='CABECERA'";
				System.out.println(className + "  - esCampoBorrable -->  " +qry );		
				
				PreparedStatement pstat = con.prepareStatement(qry);
		        result=pstat.executeUpdate();
			}
	        } catch (SQLException e) {
	         e.printStackTrace();
	     }finally {con.close();}
      return result>0;
}


	
	
	
	/**
	 * Devuelve el ID de la toma, que acostumbra ser 4 dígitos (la hora sin los :)
	 * @param cadena
	 * @param posicion
	 * @return
	 */
	private static String getIdToma(String cadena, int posicion) {
		String result = getHoraToma(cadena, posicion);
		if(result!=null && !result.equals("")) 
			result=result.replace(":", "");
		
		return result;
	}


	/**
	 * Por defecto devuelve que es una toma BASE (de las que hay en Excel de la resi. En caso de superar 6 se devuelve como si fuera Extra
	 * @param i
	 * @return
	 */
	private static String getBase(int i) {
		String result = "BASE";
		if(i>6) result="EXTRA";
		return result;
	}


	/**
	 * A partir de lo que se devuelva en la select, se construye el resultado. En caso de ser una hora, se devuelve tal cual. En caso de ser un texto que no sigue 
	 * formato XX:XX se construye una hora a partir de la posición que ocupa, devolviendo formato XX:XX  
	 * @param cadena
	 * @param posicion
	 * @return
	 */
	private static String getHoraToma(String cadena, int posicion) {
		String result="";
		String formatoExpresionRegular = "\\d{2}:\\d{2}";
		System.out.println(cadena.matches(formatoExpresionRegular));
		 if (cadena.matches(formatoExpresionRegular)) {
			 result =cadena;
	        } else {
	        	 if (posicion < 1 || posicion > 99) {
	        		 throw new IllegalArgumentException("El número debe estar en el rango de 1 a 99.");
	             }
	        	 result = String.format("%02d:00", posicion, 0);
	        }
		return result;
		
	}



	public static boolean addNuevaToma(String idUsuario, FicheroResiBean cab, CabecerasXLSBean nuevaToma) throws Exception {
        int result=0;
       	Connection con = Conexion.conectar();

        try {     	
				String qry = "INSERT INTO SPD_cabecerasXLS (idDivisionResidencia, idToma, nombreToma, posicionEnBBDD, posicionEnVistas, horaToma, langToma, tipo, desdeTomaPrimerDia, hastaTomaUltimoDia )  ";
				qry+= " VALUES ( '"+nuevaToma.getIdDivisionResidencia() +"',  '"+getIdToma(nuevaToma.getHoraToma(), nuevaToma.getPosicionEnBBDD())+"' , ";
				qry+= "'"+ nuevaToma.getNombreToma()+"', " + nuevaToma.getPosicionEnBBDD() + ", " + nuevaToma.getPosicionEnBBDD() + ", ";
				qry+= "'"+getHoraToma(nuevaToma.getHoraToma(),  nuevaToma.getPosicionEnBBDD())+"' , 0, '"+getBase(nuevaToma.getPosicionEnBBDD())+"', 0, 0)";
				System.out.println(className + "  - addNuevaToma -->  " +qry );		
				
				PreparedStatement pstat = con.prepareStatement(qry);
		        result=pstat.executeUpdate();

				
    	} catch (SQLException e) {
			 e.printStackTrace();
		}finally {con.close();}
				
				
      return result>0;
}

	public static boolean actualizaPosicion(CabecerasXLSBean cab) throws Exception {
	        int result=0;
			Connection con = Conexion.conectar();

			  	
				  String qry = " UPDATE dbo.SPD_cabecerasXLS ";
				  qry+= "  SET posicionEnVistas='"+cab.getPosicionEnVistas()+"'"; 
				  qry+= "  WHERE idDivisionResidencia ='"+cab.getIdDivisionResidencia() +"' ";   	 
			  	  qry+= "  AND oidCabeceraXLS ='"+cab.getOidCabeceraXLS()+"'  ";   	    
	   	
			  	  System.out.println(className + "--> actualizaPosicion -->" +qry );		

			
			    try {
			         PreparedStatement pstat = con.prepareStatement(qry);
			         result=pstat.executeUpdate();
			       
			     } catch (SQLException e) {
			         e.printStackTrace();
			     }finally {con.close();}

				
				return result>0;
			}






	public static CabecerasXLSBean findByFilters(int oidDivisionResidencia, int oidCabeceraXLS, int posicionEnVistas, String idToma, String horaToma, String horaTomaLiteral, boolean desdeTomaPrimerDia, boolean hastaTomaUltimoDia  ) throws Exception {
     	
    	Connection con = Conexion.conectar();
    	CabecerasXLSBean  c =null;
    	if(horaTomaLiteral!=null && !horaTomaLiteral.equals("")) 
    		horaTomaLiteral=StringUtil.limpiarTextoyEspacios(horaTomaLiteral);
      
    	String qry = "SELECT * FROM dbo.SPD_cabecerasXLS g  ";
 		qry+= " WHERE 1=1 ";
 		qry+= " AND idDivisionResidencia in (select idDivisionResidencia from bd_divisionResidencia where oidDivisionResidencia= '"+oidDivisionResidencia+"' ) " ;
 		if(oidCabeceraXLS>0)
 			qry+= " AND oidCabeceraXLS= '"+oidCabeceraXLS+"'  ";
 		if(posicionEnVistas>0)
 			qry+= " AND posicionEnVistas= '"+posicionEnVistas+"'";
 		if(idToma!=null && !idToma.equals(""))
 			qry+= " AND idToma= '"+idToma+"'";
 		if((horaToma!=null && !horaToma.equals("")) || ((horaTomaLiteral!=null && !horaTomaLiteral.equals(""))))
 		{
 			qry+= " AND (horaToma= '"+horaToma+"' OR nombreToma= '"+horaTomaLiteral+"')";
 		}
 		if(desdeTomaPrimerDia)
 			qry+=" AND desdeTomaPrimerDia = 1 ";
 		if(hastaTomaUltimoDia)
 			qry+=" AND hastaTomaUltimoDia = 1 ";
 		
			System.out.println(className + "  - findTomaByOid -->  " +qry );		
	         ResultSet resultSet = null;
	       try {
	            PreparedStatement pstat = con.prepareStatement(qry);
	            resultSet = pstat.executeQuery();
	            if (resultSet.next()) {
	            	c=creaCabecerasXLSBean(resultSet);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }finally {con.close();}
	 
		
	        return c;
    }




	public static CabecerasXLSBean getDesdeTomaPrimerDia(String idDivisionResidencia) throws Exception {
     	
    	Connection con = Conexion.conectar();
    	CabecerasXLSBean  c =null;
      
    	String qry = "SELECT * FROM dbo.SPD_cabecerasXLS g  ";
 		qry+= " WHERE 1=1 ";
 		qry+= " AND idDivisionResidencia = '"+idDivisionResidencia+"'  " ;
		qry+=" AND desdeTomaPrimerDia = 1 ";

		System.out.println(className + "  - getDesdeTomaPrimerDia -->  " +qry );
	    ResultSet resultSet = null;
	    try {
	            PreparedStatement pstat = con.prepareStatement(qry);
	            resultSet = pstat.executeQuery();
	            if (resultSet.next()) {
	            	c=creaCabecerasXLSBean(resultSet);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }finally {con.close();}
	 
		
	        return c;
    }

	public static CabecerasXLSBean getHastaTomaUltimoDia(String idDivisionResidencia) throws Exception {
     	
    	Connection con = Conexion.conectar();
    	CabecerasXLSBean  c =null;
      
    	String qry = "SELECT * FROM dbo.SPD_cabecerasXLS g  ";
 		qry+= " WHERE 1=1 ";
 		qry+= " AND idDivisionResidencia = '"+idDivisionResidencia+"'  " ;
 		qry+=" AND hastaTomaUltimoDia = 1 ";
 		
		System.out.println(className + "  - getHastaTomaUltimoDia -->  " +qry );		
	         ResultSet resultSet = null;
	       try {
	            PreparedStatement pstat = con.prepareStatement(qry);
	            resultSet = pstat.executeQuery();
	            if (resultSet.next()) {
	            	c=creaCabecerasXLSBean(resultSet);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }finally {con.close();}
	 
		
	        return c;
    }





	
	
	

}


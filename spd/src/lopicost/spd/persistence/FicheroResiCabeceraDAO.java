package lopicost.spd.persistence;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


import lopicost.config.pool.dbaccess.Conexion;
import lopicost.spd.security.helper.VisibilidadHelper;
import lopicost.spd.struts.bean.CamposPantallaBean;
import lopicost.spd.struts.bean.FicheroResiBean;

import lopicost.spd.struts.form.FicheroResiForm;
import lopicost.spd.struts.form.SustXComposicionForm;
import lopicost.spd.utils.DateUtilities;
import lopicost.spd.utils.HelperSPD;
import lopicost.spd.utils.SPDConstants;

 
public class FicheroResiCabeceraDAO {
	
	static String className="FicheroResiCabeceraDAO";
	
	static String TABLA_ACTIVA		=	"dbo.SPD_ficheroResiCabecera";
	static String TABLA_HISTORICO 	=	"dbo.SPDHst_ficheroResiCabecera";   //tabla de hist�rico

	
	public static boolean nuevo(String  spdUsuario, String idDivisionResidencia, String idProceso, String fileIn) throws ClassNotFoundException, SQLException {
		String table = TABLA_ACTIVA;
		
        int result=0;
		  Connection con = Conexion.conectar();
	  	   String qry = " INSERT INTO "+table+" (fechaCreacion,  idDivisionResidencia,   ";
	  	   	qry+= " idProceso, nombreFicheroResi, idEstado, free1, free2, free3, usuarioCreacion)	";
	  	   	qry+= "   VALUES 	";
	       	qry+= " (";
	       	qry+= " 	CONVERT(datetime, getdate(), 120),  '"+idDivisionResidencia+"'";
	       	qry+= ", '"+	idProceso+"', '"+fileIn+"','"+SPDConstants.SPD_PROCESO_1_EN_CREACION+"'";
			qry+= ", 'original', '' , '','"+spdUsuario+"'    ) ";

			System.out.println(className + "--> FicheroResiCabeceraDAO.nuevo -->" +qry );		
			
	    try {
	         PreparedStatement pstat = con.prepareStatement(qry);
	         result=pstat.executeUpdate();
	         con.commit();
	         con.close();
	         pstat.close();
	       
	     } catch (SQLException e) {
	         e.printStackTrace();
	     }
		
		return result>0;
	}

	public int getCountGestFicheroResi(String spdUsuario, FicheroResiForm form) throws Exception {
		return getCountGestFicheroResi(spdUsuario, form, false);
	}

	public int getCountGestFicheroResi(String spdUsuario, FicheroResiForm form, boolean historico) throws Exception {
		
	   	 String qry = getQueryGestFicheroResi(spdUsuario, form, true, 0, 0, null, historico);
	   	System.out.println(className + "--> getCountGestFicheroResi -->" +qry );		
		 Connection con = Conexion.conectar();
	     ResultSet resultSet = null;
	     int result =0;
	     try {
	       PreparedStatement pstat = con.prepareStatement(qry);
	       resultSet = pstat.executeQuery();
	       resultSet.next();
	       result = resultSet.getInt("quants");

	   } catch (SQLException e) {
	       e.printStackTrace();
	   }finally {con.close();}

	   return result;
	}

	public static String getQueryGestFicheroResi(String spdUsuario, FicheroResiForm form, boolean count, int inicio, int fin, String seleccionCampo) throws Exception {
		return getQueryGestFicheroResi(spdUsuario,  form.getOidFicheroResiCabecera(), form.getOidDivisionResidencia(), form.getFiltroProceso(), form.getFiltroEstado(), count, inicio, fin, seleccionCampo, false);
	}

	
	public static String getQueryGestFicheroResi(String spdUsuario, FicheroResiForm form, boolean count, int inicio, int fin, String seleccionCampo, boolean historico) throws Exception {
		//return getQueryGestFicheroResi(spdUsuario,  form.getOidFicheroResiCabecera(), form.getOidDivisionResidencia(), form.getFiltroProceso(), form.getFiltroEstado(), count, inicio, fin, seleccionCampo, historico);
		return getQueryGestFicheroResi(spdUsuario,  form.getOidFicheroResiCabecera(), form.getOidDivisionResidenciaFiltro(), form.getFiltroProceso(), form.getFiltroEstado(), count, inicio, fin, seleccionCampo, historico);
	}

	public static String getQueryGestFicheroResi( String spdUsuario, int oidFicheroResiCabecera, int oidDivisionResidencia, String idProceso,  String idEstado, boolean count, int inicio, int fin, String seleccionCampo, boolean historico) throws Exception 
	{
		
		String tabla = historico ? TABLA_HISTORICO : TABLA_ACTIVA;
		  
		String qrySelect	= " ";
		String qryFrom 		= " ";
		String qryOrder		= " ";
		String qryWhere		= " ";
		String qryAux		= " ";
		//si no viene campo informado, ponemos todo por defecto
		//String camposSelect =" d.oidDivisionResidencia, d.nombredivisionresidencia, g.*, c1.cipsNoExistentesBbdd, c2.cipsTotalesProceso, c3.cipsSpdResiNoExistentesEnProceso, c4.mensajesAlerta, c5.mensajesInfo "; 
		//String camposSelect =" d.oidDivisionResidencia, d.nombredivisionresidencia, g.*, c1.cipsNoExistentesBbdd, c2.cipsTotalesProceso, c4.mensajesAlerta, c5.mensajesInfo ";
		String camposSelect =" d.oidDivisionResidencia, d.nombredivisionresidencia, g.*, ";
		//camposSelect+=" COALESCE( c2.cipsTotalesProceso, 0) as cipsTotalesProceso , COALESCE( c4.mensajesAlerta, 0) as mensajesAlerta, ";
		camposSelect+=" g.cipsFicheroXML as cipsTotalesProceso, ";
		//camposSelect+=" COALESCE( c4.mensajesAlerta, 0) as mensajesAlerta, COALESCE( c5.mensajesInfo, 0) as mensajesInfo, COALESCE( c6.numeroValidacionesPendientes, 0) as numeroValidacionesPendientes ";
		camposSelect+=" COALESCE( c6.numeroValidacionesPendientes, 0) as numeroValidacionesPendientes ";
		
		
		if(seleccionCampo!=null && !seleccionCampo.equals("*") && !seleccionCampo.equals(""))  //nos llega un campo concreto
		{
			qryOrder=  " ORDER BY "+seleccionCampo +"";
		}	
		else qryOrder+= " ORDER BY g.fechaCreacion DESC";
		
		
		if(seleccionCampo!=null && !seleccionCampo.equals("") && !seleccionCampo.equals("*"))  //nos llega un campo concreto
			camposSelect = " g." + seleccionCampo ;
		
		if(count)  //si es contador inicializo la query
			qrySelect = "SELECT COUNT(*) AS quants";
		else 
			qrySelect = " SELECT * FROM ( SELECT distinct ROW_NUMBER() OVER(" +qryOrder+ ") AS ROWNUM, "+camposSelect+" ";
		
		
		
		qryFrom=  " FROM "+tabla+" g INNER JOIN dbo.bd_divisionResidencia d  ON (g.idDivisionResidencia=d.idDivisionResidencia)";
		qryWhere+= " WHERE EXISTS  ( " + VisibilidadHelper.oidDivisionResidenciasVisiblesExists(spdUsuario, "d.idDivisionResidencia")  + ")";
		qryAux="";
		
		
		//if(form.getOidGestFicheroResi()>0)
		if(oidFicheroResiCabecera>0)					qryWhere+=  " AND g.oidFicheroResiCabecera = '"+oidFicheroResiCabecera+"'";
		if(oidDivisionResidencia>0)						qryWhere+=  " AND d.oidDivisionResidencia = '"+oidDivisionResidencia +"' ";
		if(idProceso!=null && !idProceso.equals(""))	qryWhere+=  " AND g.idProceso = '"+idProceso +"' ";
		if(idEstado!=null && !idEstado.equals(""))		qryWhere+=  " AND g.idEstado = '"+idEstado +"' ";

				 
		if(!count)  
		{
			//contador de indicadores 
			/*			qryFrom+=  " left join ";
			qryFrom+=  " ( 	 ";
			qryFrom+=  " 	select d.idDivisionResidencia, d.idProceso, count(distinct d.resiCIP) as cipsNoExistentesBbdd ";
			qryFrom+=  " 	from dbo.SPD_ficheroResiDetalle d  ";
			qryFrom+=  " 	where d.resiCIP not in (select CIP  from bd_pacientes p ) ";
			qryFrom+=  " 	and isdate(d.resiInicioTratamiento) =1 ";
			qryFrom+=  " 	group by d.idDivisionResidencia, d.idProceso ";
			qryFrom+=  " ) c1 on (g.idDivisionResidencia=c1.idDivisionResidencia  and g.idProceso=c1.idProceso) ";
			*/	/*	
			qryFrom+=  " left join ";
			qryFrom+=  " ( 	 ";
			qryFrom+=  "	select d.idDivisionResidencia, d.idProceso, count(distinct d.resiCIP) as cipsTotalesProceso ";
			qryFrom+=  " 	from dbo.SPD_ficheroResiDetalle d  ";
//			qryFrom+=  " 	where isdate(d.resiInicioTratamiento) =1 ";
			qryFrom+=  " 	where d.tipoRegistro='LINEA'";
			qryFrom+=  " 	group by d.idDivisionResidencia, d.idProceso ";
			qryFrom+=  " ) c2 on (g.idDivisionResidencia=c2.idDivisionResidencia  and g.idProceso=c2.idProceso) ";
			*//*
			qryFrom+=  " left join ";
			qryFrom+=  " ( 	 ";
			qryFrom+=  "	select distinct d.idDivisionResidencia,  d.idProceso,  count(distinct p.CIP) - count(distinct d.resiCIP)  as cipsSpdResiNoExistentesEnProceso ";
			qryFrom+=  " 	from dbo.bd_pacientes p inner join dbo.SPD_ficheroResidetalle d   ";
			qryFrom+=  " 	on (d.idDivisionResidencia=p.idDivisionResidencia ) ";
			qryFrom+=  " 	where p.SPD='S' ";
			qryFrom+=  " 	group by d.idDivisionResidencia, d.idProceso ";
			qryFrom+=  " ) c3 on (g.idDivisionResidencia=c3.idDivisionResidencia  and g.idProceso=c3.idProceso) ";

			qryFrom+=  " left join ";
			qryFrom+=  " ( 	 ";
			qryFrom+=  "	select d.idDivisionResidencia, d.idProceso,  count('1') as mensajesAlerta  ";
			qryFrom+=  " 	from dbo.SPD_ficheroResiDetalle d  ";
//			qryFrom+=  " 	where isdate(d.resiInicioTratamiento) =1 ";
			qryFrom+=  " 	where d.tipoRegistro='LINEA'";
			//qryFrom+=  " 	and d.mensajesAlerta <>'' and d.mensajesAlerta <>'null' ";
			qryFrom+=  " 	and d.mensajesAlerta not in ('', 'null') ";
			qryFrom+=  " 	group by d.idDivisionResidencia, d.idProceso ";
			qryFrom+=  " ) c4 on (g.idDivisionResidencia=c4.idDivisionResidencia  and g.idProceso=c4.idProceso) ";

			qryFrom+=  " left join ";
			qryFrom+=  " ( 	 ";
			qryFrom+=  "	select d.idDivisionResidencia, d.idProceso,  count('1') as mensajesInfo  ";
			qryFrom+=  " 	from dbo.SPD_ficheroResiDetalle d  ";
//			qryFrom+=  " 	where isdate(d.resiInicioTratamiento) =1 ";
			qryFrom+=  " 	where d.tipoRegistro='LINEA'";
			//qryFrom+=  " 	and d.mensajesInfo <>'' and d.mensajesInfo <>'null' ";
			qryFrom+=  " 	and d.mensajesInfo not in ('', 'null') ";
			qryFrom+=  " 	group by d.idDivisionResidencia, d.idProceso ";
			qryFrom+=  " ) c5 on (g.idDivisionResidencia=c5.idDivisionResidencia  and g.idProceso=c5.idProceso) ";
*/
			qryFrom+=  " left join ";
			qryFrom+=  " ( 	 ";
			qryFrom+=  "	select d.idDivisionResidencia, d.idProceso,  count( '1') as numeroValidacionesPendientes  ";
			qryFrom+=  " 	from dbo.SPD_ficheroResiDetalle d  ";
//			qryFrom+=  " 	where isdate(d.resiInicioTratamiento) =1 ";
			qryFrom+=  " 	where d.tipoRegistro='LINEA'";
			//qryFrom+=  " 	and (UPPER(d.validar) = '"+SPDConstants.REGISTRO_VALIDAR+"' OR ( UPPER(d.validar)<> '"+SPDConstants.REGISTRO_VALIDAR+"'  AND UPPER(d.confirmar) = '"+SPDConstants.REGISTRO_CONFIRMAR+"' )) ";
			qryFrom+=  " 	and (UPPER(d.validar) in ('"+SPDConstants.REGISTRO_VALIDAR+"') OR UPPER(d.confirmar) in ('"+SPDConstants.REGISTRO_CONFIRMAR+"' )) ";
			qryFrom+=  " 	group by d.idDivisionResidencia, d.idProceso ";
			qryFrom+=  " ) c6 on (g.idDivisionResidencia=c6.idDivisionResidencia  and g.idProceso=c6.idProceso) ";


			
			
			qryAux = getOtrosSql2008(inicio, inicio+SPDConstants.PAGE_ROWS, count);
			
			//qryOrder+= " order by g.fechaCreacion desc";
			//qryOrder+= " offset "+ (inicio) + " rows ";
			//qryOrder+= " fetch next "+fin+ " rows only";
		}



		
	return qrySelect + qryFrom + qryWhere + qryAux;

	}


	/**
	 * Fetch es una cl�usula que funciona a partir del SqlServer 2008 (no inclusive)
	 * @param form
	 * @param inicio
	 * @param fin
	 * @param count
	 * @return
	 */
	private static String getOtrosSqlPost2008(SustXComposicionForm form, int inicio, int fin, boolean count) 
	{
		String otros="";
		if(!count) 
		{
			otros+= " offset "+ (inicio) + " rows ";
			otros+= " fetch next "+(fin)+ " rows only";
		}
		return otros;
	}

	/**
	 * Como FETCH es una cl�usula de versi�n SQLSERVER>2008 se crea una funci�n un poco m�s engorrosa pero
	 * que sirve para todas las versiones (ROW_NUMBER() OVER)
	 * @param form
	 * @param inicio
	 * @param fin
	 * @param count
	 * @return
	 */
	private static String getOtrosSql2008(int inicio, int fin, boolean count) 
	{
		String otros="";
		if(!count) 
		{
			otros+= " ) cte ";
			otros+= " where ROWNUM >=  "+ (inicio) + "  AND ROWNUM <= "+(fin);

		}
		return otros;
	}
	
	
	

	  public static FicheroResiBean getFicheroResiCabecera(String spdUsuario, FicheroResiForm form) throws Exception {

		  FicheroResiBean result = null;
		  List lista= getGestFicheroResi(spdUsuario, form, 0, 1, null, false);
		  if(lista!=null && lista.size()>0)
			  result=(FicheroResiBean)lista.get(0);
		  
		  return result;
	  }

	 /**
	  * 
	  * @param form
	  * @param inicio
	  * @param fin
	  * @param distinctCampo
	  * @param total
	  * @return
	 * @throws Exception 
	  */
	  public static List<FicheroResiBean> getGestFicheroResi(String spdUsuario, FicheroResiForm form, int inicio, int fin, String distinctCampo, boolean total) throws Exception {
		  return getGestFicheroResi( spdUsuario,  form,  inicio,  fin,  distinctCampo,  total, false);
	  }

		  
	  public static List<FicheroResiBean> getGestFicheroResi(String spdUsuario, FicheroResiForm form, int inicio, int fin, String distinctCampo, boolean total, boolean historico) throws Exception {

		  String qry = getQueryGestFicheroResi(spdUsuario,  form, false, inicio, fin, distinctCampo, historico);
	    	 Connection con = Conexion.conectar();
	
	    	 System.out.println(className + "--> getGestFicheroResi -->" +qry );		
  
     	//	qry+= " offset 20 rows ";
     	//	qry+= " fetch next 10 rows only";
     		
	    	 ResultSet resultSet = null;
	    	
	    	 CamposPantallaBean campos = new CamposPantallaBean();
	    	
	    	 List<FicheroResiBean> lista= new ArrayList<FicheroResiBean>();	
	    	 try {
	    		 PreparedStatement pstat = con.prepareStatement(qry);
	    		 resultSet = pstat.executeQuery();
	    		//CamposPantallaBean camposPantallaBean = new CamposPantallaBean();
	    		 while (resultSet.next()) {
	    			 FicheroResiBean  c =creaCabecera(resultSet);
	    			 lista.add(c);
	    		}
     } catch (SQLException e) {
         e.printStackTrace();
     }finally {con.close();}
	    	// result.setResultList(lista);
	    	// result.setCampos(campos);
     return lista;
 }

	  private static FicheroResiBean creaCabecera(ResultSet resultSet) throws SQLException {
				 FicheroResiBean  c =new FicheroResiBean();
			 if (resultSet!=null) {
				 c.setProcesoValido(true);
				 try{
					 c.setCipsTotalesProceso(resultSet.getInt("cipsTotalesProceso"));
				 }catch(Exception e){
					 
				 }
				 c.setCipsFicheroXML(resultSet.getInt("cipsFicheroXML"));
				 c.setCipsActivosSPD(resultSet.getInt("cipsActivosSPD"));
				//  c.setCipsNoExistentesBbdd(resultSet.getInt("cipsNoExistentesBbdd"));
				// c.setCipsSpdResiNoExistentesEnProceso(resultSet.getInt("cipsSpdResiNoExistentesEnProceso"));
				 try{
					 c.setNumeroMensajesInfo(resultSet.getInt("mensajesInfo"));
				 }catch(Exception e){}
				 
				 try{
					 c.setNumeroMensajesAlerta(resultSet.getInt("mensajesAlerta"));
				 	}catch(Exception e){}
				 try{
					 c.setNumeroValidacionesPendientes(resultSet.getInt("numeroValidacionesPendientes"));
				}catch(Exception e){}
				 if(c.getNumeroValidacionesPendientes()>0 ||  c.getNumeroMensajesAlerta()>0)
					 c.setProcesoValido(false);
				 c.setFechaCreacionFicheroXML(resultSet.getDate("fechaCreacionFicheroXML"));
				 c.setFechaHoraProceso(resultSet.getDate("fechaCreacion"));
				 c.setFechaValidacionDatos(resultSet.getDate("fechaValidacionDatos"));
				 c.setFilasTotales(resultSet.getInt("filasTotales"));
				 c.setIdDivisionResidencia(resultSet.getString("idDivisionResidencia"));
				 c.setIdEstado(resultSet.getString("idEstado"));
				 c.setIdProceso(resultSet.getString("idProceso"));
				 c.setNombreDivisionResidencia(resultSet.getString("nombreDivisionResidencia"));
				 c.setNombreFicheroResi(resultSet.getString("nombreFicheroResi"));
				 c.setNombreFicheroXML(resultSet.getString("nombreFicheroXML"));
				 c.setResumenCIPS(resultSet.getString("resumenCIPS"));
				 c.setOidDivisionResidencia(resultSet.getInt("oidDivisionResidencia"));
				 c.setOidFicheroResiCabecera(resultSet.getInt("oidFicheroResiCabecera"));
				 c.setResultLog(resultSet.getString("resultLog"));
				 c.setUsuarioValidacion(resultSet.getString("usuarioValidacion"));
				 c.setNumErrores(resultSet.getInt("numErrores"));
				 c.setErrores(resultSet.getString("errores"));
				 c.setFree1(resultSet.getString("free1"));
				 c.setFree2(resultSet.getString("free2"));
				 c.setFree3(resultSet.getString("free3"));
				 c.setPorcentajeCIPS(resultSet.getInt("porcentajeCIPS"));
				 c.setResumenCIPS(resultSet.getString("resumenCIPS"));
				 //Date fechaCalculoPrevision = resultSet.getTimestamp("fechaCalculoPrevision");
			     java.sql.Timestamp fechaCalculoPrevision = resultSet.getTimestamp("fechaCalculoPrevision");
	 		 if(fechaCalculoPrevision!=null) 
				 {
	 		    // Crea un objeto SimpleDateFormat con el formato deseado
	 		    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	 		    // Formatea el Timestamp a una cadena
	 		    String fechaFormateada = (fechaCalculoPrevision != null) ? sdf.format(new Date(fechaCalculoPrevision.getTime())) : "";
					//c.setFechaCalculoPrevision(DateUtilities.getDatetoString("dd/MM/yyyy HH:mm", resultSet.getDate("fechaCalculoPrevision")));
	 		    c.setFechaCalculoPrevision(fechaFormateada);
				 }
	 			    
				 c.setUsuarioCreacion(resultSet.getString("usuarioCreacion"));	 				 
			 }

		return c;
	}


	/**OK - 
	   * M�todo que devuelve las diferentes residencias con fichero
	   * @return List<FicheroResiCabeceraBean>
	 * @throws Exception 
	   */
	  public static List<FicheroResiBean> getListaDivisionResidenciasCargadas(String spdUsuario) throws Exception {
		  return getListaDivisionResidenciasCargadas(spdUsuario, false);
	  }

	  public static List<FicheroResiBean> getListaDivisionResidenciasCargadas(String spdUsuario, boolean historico) throws Exception {
			   Connection con = Conexion.conectar();
			   String tabla = historico ? TABLA_HISTORICO : TABLA_ACTIVA;
			   
			   List<FicheroResiBean>  result = new ArrayList();
			   String qry = "SELECT distinct  d.oidDivisionResidencia,  d.idDivisionResidencia,  d.nombreDivisionResidencia";
			    		qry+= " FROM "+tabla+" g LEFT JOIN dbo.bd_divisionResidencia d  ON (g.idDivisionResidencia=d.idDivisionResidencia) ";
			    		qry+= " WHERE d.oidDivisionResidencia IN ( " + VisibilidadHelper.oidDivisionResidenciasVisibles(spdUsuario)  + ")";
			 			qry+= " ORDER BY d.nombreDivisionResidencia";
						
			 			System.out.println(className + "--> getListaDivisionResidenciasCargadas -->" +qry );		
				     	ResultSet resultSet = null;
			 	 	
			 	    try {
			 	         PreparedStatement pstat = con.prepareStatement(qry);
			 	         resultSet = pstat.executeQuery();
			 	         while (resultSet.next()) {
			 	        	FicheroResiBean f = new FicheroResiBean();
			 	        	 f.setOidDivisionResidencia(resultSet.getInt("oidDivisionResidencia"));
			 	        	 f.setIdDivisionResidencia(resultSet.getString("idDivisionResidencia"));
			 	        	 f.setNombreDivisionResidencia(resultSet.getString("nombreDivisionResidencia"));
			 	        	result.add(f);
			 	            }
			 	     } catch (SQLException e) {
			 	         e.printStackTrace();
			 	     }finally {con.close();}

			 	     return result;
			 	 }
		
		/**OK - 
		   * M�todo que devuelve la cabecera anterior que haya tenido una carga de residentes > de un % de carga seg�n los usuarios activos de SPD 
		   * @return List<FicheroResiCabeceraBean>
		 * @throws Exception 
		   */

			public static FicheroResiBean getProcesoGlobalAnterior(String spdUsuario, String idDivisionResidencia, String procesoDiferenteA) throws Exception {
				   Connection con = Conexion.conectar();
				   String table = TABLA_ACTIVA;
				   FicheroResiBean  result = null;
				   String qry = "SELECT *";
				    		qry+= " FROM "+table+" ";
				    		qry+= " WHERE 1=1 ";
				    		qry+= " AND d.oidDivisionResidencia IN ( " + VisibilidadHelper.oidDivisionResidenciasVisibles(spdUsuario)  + ") ";
				    		qry+= " AND idProceso<>'"+procesoDiferenteA+"' ";
				    		qry+= " AND idDivisionResidencia='"+idDivisionResidencia+"' ";
				    		qry+= " AND cipsActivosSPD>0 ";
				    		qry+= " AND cipsFicheroXML>0 ";
				    		qry+= " AND cipsFicheroXML*100/cipsActivosSPD>75 ";
				 			qry+= " ORDER BY idProceso DESC ";
					 			
				 			
				 			System.out.println(className + "--> getProcesoGlobalAnterior -->" +qry );		
					     	ResultSet resultSet = null;
				 	 	
				 	    try {
				 	         PreparedStatement pstat = con.prepareStatement(qry);
				 	         resultSet = pstat.executeQuery();
				 	         if (resultSet.next()) {
				 	        	result =creaCabecera(resultSet);
				 	            }
				 	     } catch (SQLException e) {
				 	         e.printStackTrace();
				 	     }finally {con.close();}

				 	     return result;
				 	 }
			

			/*
			select 
			COALESCE(act.idProceso,ant.idProceso)
			, COALESCE(act.resiCIP,ant.resiCIP)
			, COALESCE(act.resiMedicamento,ant.resiMedicamento)
			--, COALESCE(replace(ant.idTratamientoCIP, '_',''),replace(act.idTratamientoCIP, '_',''))  
			, COALESCE(ant.idTratamientoCIP,act.idTratamientoCIP)
			from 
			( select * from SPD_ficheroResidetalle where idProceso in ('onada_riudoms_20240115_20240121_170481959' ) )act   
			FULL OUTER JOIN 
			(select * from SPD_ficheroResidetalle where idProceso in
			(select max(idProceso) as anterior
			from SPD_ficheroResiCabecera where 1=1
			and idProceso<>'onada_riudoms_20240115_20240121_170481959'
			--and idProceso='onada_casserres_20231106_20231112_169860898'
			and idDivisionResidencia='general_onada_riudoms'
			and cipsActivosSPD>0
			and cipsFicheroXML>0
			and cipsFicheroXML*100/cipsActivosSPD>75)) ant
			-- on replace(act.idTratamientoCIP, '_','')= replace(ant.idTratamientoCIP, '_','')
			 on act.idTratamientoCIP= ant.idTratamientoCIP
			where 1=1
			and (ant.idTratamientoCIP is null OR act.idTratamientoCIP is null)
			order by COALESCE(act.resiCIP,ant.resiCIP), COALESCE(act.resiMedicamento,ant.resiMedicamento)
			
			*/
			
			
		/**OK - 
		 * M�todo que devuelve los diferentes procesos cargados de una residencia
		 * @param idDivisionResidencia
		 * @return
		 * @throws Exception 
		 */
		public static List<FicheroResiBean> getProcesosCargados(String spdUsuario, int oidDivisionResidencia) throws Exception
		{
			return getProcesosCargados(spdUsuario, oidDivisionResidencia, null);
		}
		
	 /**OK - 
		 * M�todo que devuelve los diferentes procesos cargados de una residencia
		 * @param idDivisionResidencia, idEstado
		 * @return List<FicheroResiCabeceraBean>
	 * @throws Exception 
		 */
		
		
		
		public static List<FicheroResiBean> getProcesosCargados(String spdUsuario, int oidDivisionResidencia, String idEstado) throws Exception {
		Connection con = Conexion.conectar();
			   List<FicheroResiBean>  result = new ArrayList();
			 //  String qry = "SELECT distinct d.oidDivisionResidencia, d.idDivisionResidencia, g.idProceso, g.fechaCreacion, g.filasTotales, g.cipsActivosSPD, g.cipsFicheroXML, g.free1, g.free2, g.free2 ";//, COALESCE(t.revisar, 'NO') as revisar ";
			   String qry = "SELECT * "; 
			    		qry+= " FROM "+TABLA_ACTIVA+" g   ";
			    		//qry+= " LEFT JOIN dbo.SPD_ficheroResiDetalle t on g.oidFicheroResiCabecera=t.oidFicheroResiCabecera and  t.revisar='SI'  ";   
			    		qry+= " INNER JOIN bd_divisionResidencia d ON (g.idDivisionResidencia=d.idDivisionResidencia )";
			    		qry+= " INNER JOIN dbo.bd_residencia r ON (d.idResidencia=r.idResidencia) ";
			    		qry+= " WHERE  d.oidDivisionResidencia IN ( " + VisibilidadHelper.oidDivisionResidenciasVisibles(spdUsuario)  + ")";
			    	
			    		if(oidDivisionResidencia>0)
			    			qry+=  " AND d.oidDivisionResidencia ='" + oidDivisionResidencia+"'";

			    		if(idEstado!=null && !idEstado.equals(""))
			    			qry+=  " AND g.idEstado ='" + idEstado+"'";

			    		qry+=  " ORDER BY g.fechaCreacion DESC";
						
			    		System.out.println(className + "--> getProcesosCargados -->" +qry );		
				     	ResultSet resultSet = null;
			 	 	
			 	    try {
			 	         PreparedStatement pstat = con.prepareStatement(qry);
			 	         resultSet = pstat.executeQuery();
			 	         /*
			 	         while (resultSet.next()) {
			 	        	FicheroResiBean f = new FicheroResiBean();
			 	        	f.setOidDivisionResidencia(resultSet.getInt("oidDivisionResidencia"));
			 	        	f.setIdDivisionResidencia(resultSet.getString("idDivisionResidencia"));
			 	        	f.setIdProceso(resultSet.getString("idProceso"));
			 	        //	f.setRevisar(resultSet.getString("revisar"));
			 	        	
				 	       	result.add(f);
			 	            }
			 	         */
			    		 while (resultSet.next()) {
			    			 FicheroResiBean  f =creaCabecera(resultSet);
			    			 result.add(f);
			    		}
			    		 
			    		 
			    		 
			 	     } catch (SQLException e) {
			 	         e.printStackTrace();
			 	     }finally {con.close();}

			 	     return result;
			 	 }


		



		/**
		 * Actualizaci�n cabecera despu�s de cargar el fichero.
		 * @param idDivisionResidencia
		 * @param idProceso
		 * @param processedRows
		 * @param cipsTotales
		 * @param cipsNoExistentesBbdd 
		 * @param numeroMensajesAlerta 
		 * @param numeroMensajesInfo 
		 * @param cipsSpdResiNoExistentesEnFichero 
		 * @param errors
		 * @return
		 * @throws ClassNotFoundException 
		 * @throws SQLException 
		 */

	//	public static boolean editaFinCargaFicheroResi(String idDivisionResidencia, String idProceso, int processedRows, int cipsTotales, int cipsNoExistentesBbdd, int cipsSpdResiNoExistentesEnFichero, int numeroMensajesInfo, int numeroMensajesAlerta, List errors) throws ClassNotFoundException {
		public static boolean editaFinCargaFicheroResi(String idDivisionResidencia, String idProceso, int processedRows, int cipsTotales, int cipsActivosSPD,  int porcent, List errors, String resumenCIPSFichero) throws ClassNotFoundException, SQLException {
				int result=0;
			  Connection con = Conexion.conectar();
			  String resultLog = SPDConstants.SPD_FICHERO_SUBIDO_OK;
			  if(errors!=null && errors.size()>0) 
				  resultLog=SPDConstants.SPD_FICHERO_SUBIDO_CON_ERRORES;
			
			  StringBuilder stringBuilder = new StringBuilder();
		      Iterator<String> iterator = errors.iterator();
		      while (iterator.hasNext()) {
		    	  String error = iterator.next();
		          stringBuilder.append(error).append("<br>"); // Agregar cada elemento seguido de un salto de l�nea en HTML
		        }

		        // Obtener la cadena final
				String resultado = stringBuilder.toString();
			  
			  int numErrores= errors!=null?errors.size():0;
		  	   String qry = " update "+TABLA_ACTIVA+" ";
		  	    qry+= "  set idEstado ='"+SPDConstants.SPD_PROCESO_2_PENDIENTE_VALIDAR +"' ";
		  	    qry+= " ,  resultLog = '"+resultLog+"' ";
		  	    qry+= " ,  filasTotales="+processedRows +" ";
		  	    qry+= " ,  numErrores="+numErrores +" ";
		  	    qry+= " ,  errores='"+resultado +"' ";
		  	    qry+= " ,  cipsFicheroXML="+cipsTotales +" ";
		  	    qry+= " ,  cipsActivosSPD="+cipsActivosSPD +" ";
		  	    qry+= " ,  porcentajeCIPS="+porcent +" ";
		  	    qry+= " ,  resumenCIPS='"+resumenCIPSFichero +"'";
		  	  
		  	  //    qry+= "  cipsNoExistentesBbdd="+cipsNoExistentesBbdd +", ";
		  	  //    qry+= "  numeroMensajesAlerta="+numeroMensajesAlerta+", ";
		  	  //  qry+= "  numeroMensajesInfo="+numeroMensajesInfo +", ";
		  	  //   qry+= "  cipsSpdResiNoExistentesEnFichero="+cipsSpdResiNoExistentesEnFichero ;
		  	    qry+= "  where idProceso='"+ idProceso +"' "; 
		  	    qry+= "  and idDivisionResidencia ='"+idDivisionResidencia +"' ";   	    		
		  	  System.out.println(className + "--> editaFinCargaFicheroResi -->" +qry );		
	    		 
		    try {
		         PreparedStatement pstat = con.prepareStatement(qry);
		         result=pstat.executeUpdate();
		       
		     } catch (SQLException e) {
		         e.printStackTrace();
		     }finally {con.close();}

			return result>0;
		}

		public static boolean editarFrees(String string, FicheroResiBean cab, FicheroResiForm formulari) throws ClassNotFoundException, SQLException {
			int result=0;
		  Connection con = Conexion.conectar();

	  	   String qry = " update "+TABLA_ACTIVA+" ";
	  	    qry+= "  set free1 = '"+ formulari.getFree1() + "', ";
	  	    qry+= "  free2 = '"+ formulari.getFree2() + "', ";
	  	    qry+= "  free3 = '"+ formulari.getFree3() + "' ";
	  	    qry+= "  where idProceso='"+ cab.getIdProceso()+"' "; 
	  	    qry+= "  and idDivisionResidencia ='"+cab.getIdDivisionResidencia() +"' ";   	    		
	  	  System.out.println(className + "--> editarFrees -->" +qry );		
   		 


	    try {
	         PreparedStatement pstat = con.prepareStatement(qry);
	         result=pstat.executeUpdate();
	       
	     } catch (SQLException e) {
	         e.printStackTrace();
	     }finally {con.close();}

		return result>0;
	}

		public static boolean borrar(String spdUsuario, FicheroResiForm form) throws Exception 
		{
			int result=0;
			Connection con = Conexion.conectar();
			String query = " DELETE FROM "+TABLA_ACTIVA+"  ";
			query+= " WHERE idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";

			if(form.getOidFicheroResiCabecera()>0)
			query+=  " AND oidFicheroResiCabecera  = "+form.getOidFicheroResiCabecera();
			else if(form.getIdProceso()!=null && !form.getIdProceso().equals("") && form.getIdDivisionResidencia()!=null && !form.getIdDivisionResidencia().equals(""))
			{
				query+=  " AND idproceso = '"+form.getIdProceso()+"'";
				query+=  " AND idDivisionresidencia  = '"+form.getIdDivisionResidencia()+"'";
			}
			
			System.out.println(className + "--> borrar Cabecera -->" +query );		
			 	
		    try {
		         PreparedStatement pstat = con.prepareStatement(query);
		         result=pstat.executeUpdate();
		       
		     } catch (SQLException e) {
		         e.printStackTrace();
		     }finally {con.close();}
			return result>0;

		}
		
		
		/**OK 
		 * 
		 * @return
		 * @throws Exception 
		 */
		public static List<FicheroResiBean> getEstados(String spdUsuario) throws Exception
		{
			return  getEstados(spdUsuario, -1, null);
		}
		

		public static FicheroResiBean getCabeceraByFilters(String spdUsuario, FicheroResiForm form, int inicio, int fin, String distinctCampo, boolean total) throws Exception {
			   Connection con = Conexion.conectar();
			   FicheroResiBean   result = null;
/*			   String qry = "SELECT g.*";
			    		qry+=  " FROM dbo.SPD_ficheroResiCabecera g  ";
			    		qry+=" WHERE g.oidFicheroResiCabecera  = "+oidFicheroResiCabecera;
			    		qry+=" AND g.idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
*/			  
			    		
	    		String qry = getQueryGestFicheroResi(spdUsuario,  form, false, inicio, fin, distinctCampo);

			  		  
			  		  
			    		System.out.println(className + "--> getCabeceraByFilters" +qry );		
				     	ResultSet resultSet = null;
			 	 	
			 	    try {
			 	         PreparedStatement pstat = con.prepareStatement(qry);
			 	         resultSet = pstat.executeQuery();
			 	         while (resultSet.next()) {
			 	        	result = creaCabecera(resultSet);
			 	            }
			 	     } catch (SQLException e) {
			 	         e.printStackTrace();
			 	     }
			 	   finally {con.close();}
			 	     return result;
			 	 }

		
		/**OK
		 * 
		 * @param idDivisionResidencia
		 * @param idProceso
		 * @return
		 * @throws Exception 
		 */
		public static List<FicheroResiBean> getEstados(String spdUsuario, int oidDivisionResidencia, String idProceso) throws Exception {
			   Connection con = Conexion.conectar();
			   List<FicheroResiBean>  result = new ArrayList();
			   String qry = "SELECT distinct g.idEstado AS idEstado";
			    		qry+=  " FROM "+TABLA_ACTIVA+" g  ";
			    		qry+= " WHERE g.idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
			
			    		if(oidDivisionResidencia>0)
			    			qry+=  " AND g.idDivisionResidencia  IN (select idDivisionResidencia from bd_divisionResidencia where oidDivisionResidencia='" + oidDivisionResidencia+"')";

			    		if(idProceso!=null && !idProceso.equals(""))
			    			qry+=  " AND g.idProceso ='" + idProceso+"'";

			    		qry+=  " ORDER BY g.idEstado";
						
			    		System.out.println(className + "--> getEstados" +qry );		
				     	ResultSet resultSet = null;
			 	 	
			 	    try {
			 	         PreparedStatement pstat = con.prepareStatement(qry);
			 	         resultSet = pstat.executeQuery();
			 	         while (resultSet.next()) {
			 	        	FicheroResiBean f = new FicheroResiBean();
			 	        	f.setIdEstado(resultSet.getString("idEstado"));
				 	       	result.add(f);
			 	            }
			 	     } catch (SQLException e) {
			 	         e.printStackTrace();
			 	     }
			 	   finally {con.close();}
			 	     return result;
			 	 }




		
		
	
		public static int getOidFicheroResiCabecera(String spdUsuario, String idDivisionResidencia, String idProceso) throws Exception {
			   Connection con = Conexion.conectar();
			   String qry = "	SELECT distinct oidFicheroResiCabecera AS oid";
			    		qry+= " FROM "+TABLA_ACTIVA+" g  ";
			    		qry+= " WHERE g.idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
			    		if(idDivisionResidencia!=null && !idDivisionResidencia.equals(""))
			    			qry+=  " AND g.idDivisionResidencia ='" + idDivisionResidencia+"'";
			    		if(idProceso!=null && !idProceso.equals(""))
			    			qry+=  " AND g.idProceso ='" + idProceso+"'";

			    		System.out.println(className + "--> getOidFicheroResiCabecera" +qry );		
				     	ResultSet resultSet = null;
			 	 	
				     	int result =0;
						try {
							PreparedStatement pstat = con.prepareStatement(qry);
						    resultSet = pstat.executeQuery();
						    resultSet.next();
						    result = resultSet.getInt("oid");

						   } catch (SQLException e) {
						       e.printStackTrace();
						   }finally {con.close();}

						   return result;
					   }
		
		public static boolean actualizaFechaCalculoPrevision(FicheroResiBean cab) throws ClassNotFoundException, SQLException {
			int result=0;
		  Connection con = Conexion.conectar();
		
	  	   String qry = " update "+TABLA_ACTIVA+" ";
	  	    qry+= "  set fechaCalculoPrevision=CONVERT(datetime, getDate(), 120), ";
	  	    qry+= "  idEstado ='"+cab.getIdEstado()+"' ";	  	    
	  	    qry+= "  where oidFicheroResiCabecera='"+ cab.getOidFicheroResiCabecera() +"' "; 
	  	  System.out.println(className + "--> nctualizaFechaCalculoPrevision -->" +qry );		
	  
	    try {
	         PreparedStatement pstat = con.prepareStatement(qry);
	         result=pstat.executeUpdate();
	       
	     } catch (SQLException e) {
	         e.printStackTrace();
	     }finally {con.close();}

		return result>0;
	}


		public static int countProcesosMismaFecha(String idProcesoTmp) throws SQLException {
		       String qry = "select count(*) as quants from "+TABLA_ACTIVA+" where idProceso like '"+idProcesoTmp+"%'";
		       
		   	 	Connection con = Conexion.conectar();
		        ResultSet resultSet = null;
		        int result =0;
		        System.out.println(className + "-->  countProcesosMismaFecha -->" +qry );		
		        try {
		          PreparedStatement pstat = con.prepareStatement(qry);
		          resultSet = pstat.executeQuery();
		          resultSet.next();
		          result = resultSet.getInt("quants");

		      } catch (SQLException e) {
		          e.printStackTrace();
		      }finally {con.close();}

		      return result;
		   }

		public static boolean actualizaEstadosSinFinalizar()  throws ClassNotFoundException, SQLException {
			 
		       int result =0;
				  Connection con = Conexion.conectar();

				  String qry = " UPDATE "+TABLA_ACTIVA+" set idEstado='"+SPDConstants.SPD_PROCESO_4_CARGA_ERROR+"' ";
		  	 		qry+= " where  idEstado='"+SPDConstants.SPD_PROCESO_1_EN_CREACION+"' ";
		  	 		//qry+= " and fechaCreacion < getDate() ";  //se actualiza el estado de  procesos colgados de hace un d�a
		  	 		qry+= " AND DATEDIFF(HOUR, fechaCreacion, getDate()) >= 2 "; //2 horas
			 	  	 		
		  	 		System.out.println("actualizaEstadosSinFinalizar -->" +qry );		
			    try {
			         PreparedStatement pstat = con.prepareStatement(qry);
			         result=pstat.executeUpdate();
			       
			     } catch (SQLException e) {
			         e.printStackTrace();
			     }finally {con.close();}
				return result>0;
			}

		/**
		 * Pasamos a hist�rico las producciones de m�s de X d�as
		 * @param conn 
		 * @param aHistorico 
		 * @throws ClassNotFoundException
		 * @throws SQLException
		 */
		public static boolean cabecerasProcesosAnterioresAHistorico(Connection conn, List aHistorico)  throws ClassNotFoundException, SQLException {
				       int result =0;


					  String qry = " INSERT INTO "+TABLA_HISTORICO+" ( ";
					  	qry+= " fechaHistorico, oidFicheroResiCabecera,  fechaCreacion, idDivisionResidencia, idProceso, resultLog, filasTotales, ";
					  	qry+= " nombreFicheroResi, nombreFicheroXML, fechaCreacionFicheroXML, fechaValidacionDatos, usuarioValidacion, idEstado, cipsFicheroXML, numErrores, errores, free1, free2, free3, ";
					  	qry+= " usuarioCreacion, fechaCalculoPrevision, cipsActivosSPD, porcentajeCIPS)  ";
					  	qry+= " SELECT getDate(), oidFicheroResiCabecera, fechaCreacion, idDivisionResidencia, idProceso, resultLog, filasTotales, ";
					  	qry+= " nombreFicheroResi, nombreFicheroXML, fechaCreacionFicheroXML, fechaValidacionDatos, usuarioValidacion, idEstado, cipsFicheroXML, numErrores, errores, free1, free2, free3, ";
					  	qry+= " usuarioCreacion, fechaCalculoPrevision, cipsActivosSPD, porcentajeCIPS ";
					  	qry+= " FROM "+TABLA_ACTIVA+" ";
					  	qry+= " WHERE oidFicheroResiCabecera IN (" + HelperSPD.convertirListSecuencia(aHistorico).toString() + ")";
					  	
				       	System.out.println("cabecerasProcesosAnterioresAHistorico -->" +qry );		
				    try {
				         PreparedStatement pstat = conn.prepareStatement(qry);
				         result=pstat.executeUpdate();

				       
				     } catch (SQLException e) {
				       //  e.printStackTrace();
				    	 return false;
				     }finally {}
					return result>0;
				}


		public static List getCabecerasProcesosAnterioresAHistorico() throws SQLException {
			  Connection con = Conexion.conectar();
			  List result=new ArrayList();
			  String qry = " SELECT  oidFicheroResiCabecera  ";
			  	qry+= " FROM "+TABLA_ACTIVA+" ";
			  	qry+= " WHERE  fechaCreacion < getdate()- "+ SPDConstants.DIAS_PRODUCCION_PASA_A_HST ;  
			  	
		       	System.out.println("limpiarDatosHistoricoProcesosAnteriores -->" +qry );		
		    try {
		     	ResultSet resultSet = null;
		    	PreparedStatement pstat = con.prepareStatement(qry);
	 	        resultSet = pstat.executeQuery();
	 	        while (resultSet.next()) {
		 	       	result.add(resultSet.getInt("oidFicheroResiCabecera"));
	 	            }
		       
		     } catch (SQLException e) {
		       //  e.printStackTrace();
		    	
		     }finally {con.close();}

			return result;
		}


		
			/**OK
			 * M�todo para borrar el detalle de los ficheros, de forma masiva, una vez ya pasados a hist�rico
			 * @param conn 
			 * @param oidFicheroResiDetalle
			 * @param idDivisionresidencia
			 * @param idProceso
			 * @return
			 * @throws Exception 
			 */
			public static boolean borrarCabecerasYaEnHistorico(Connection conn, List aHistorico) throws Exception 
			{
			
				 
				int result=0;
				
				String query = " DELETE FROM "+TABLA_ACTIVA+"  ";
				query+= " WHERE oidFicheroResiCabecera IN (" + HelperSPD.convertirListSecuencia(aHistorico).toString() + ") ";
				query+= " AND oidFicheroResiCabecera in (SELECT oidFicheroResiCabecera FROM "+TABLA_HISTORICO+ ") "; //nos aseguramos que ya se ha copiado en hist�rico 

				System.out.println(className + "--> borrarCabecerasYaEnHistorico -->" +query );		
				 	
			    try {
			         PreparedStatement pstat = conn.prepareStatement(query);
			         result=pstat.executeUpdate();
			       
			     } catch (SQLException e) {
			    	 result=-1;
			         e.printStackTrace();
			     }finally {}
				return result>=0;
			}

		
			
			public static FicheroResiBean getFicheroResiCabeceraByOid(String spdUsuario, int oidFicheroResiCabecera) throws Exception {
				
				FicheroResiBean c = new FicheroResiBean();
				Connection con = Conexion.conectar();
				String 	qry =  " SELECT * FROM dbo.SPD_ficheroResiCabecera  g ";
	    		qry+= " INNER JOIN bd_divisionResidencia d ON (g.idDivisionResidencia=d.idDivisionResidencia )";
	    		qry+= " INNER JOIN dbo.bd_residencia r ON (d.idResidencia=r.idResidencia) ";
					qry+= " WHERE g.idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ") ";
					qry+= " AND g.oidFicheroResiCabecera= '"+oidFicheroResiCabecera +"' ";
			
		 		System.out.println(className + "--> getFicheroResiCabeceraByOid -->  " +qry );		
			    ResultSet resultSet = null;
		 	 	
		 	    try {
		 	         PreparedStatement pstat = con.prepareStatement(qry);
		 	         resultSet = pstat.executeQuery();
		 	         while (resultSet.next()) {
		 	        	 c = creaCabecera(resultSet);
		 	            }
		 	     } catch (SQLException e) {
		 	         e.printStackTrace();
		 	     }finally {con.close();}

		 	     return c ;


			}
			
			public static boolean actualizaEstadoIdProceso(String spdUsuario, String filtroDivisionResidenciasCargadas, String idProceso,  String idEstado) throws Exception {
		        int result=0;
				  Connection con = Conexion.conectar();
					  

			  	   String qry = " UPDATE dbo.SPD_ficheroResiCabecera ";
			  	    qry+= "  SET idEstado ='"+idEstado+"' ";
					qry+= "  WHERE idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
			  	    qry+= "  AND idProceso='"+ idProceso +"' "; 
			  	    qry+= "  AND idDivisionResidencia ='"+filtroDivisionResidenciasCargadas +"' ";   	    		
			  	    //qry+= "  AND tipoRegistro ='"+SPDConstants.REGISTRO_CABECERA +"' ";   	    		
			  	    
			  	  System.out.println(className + "--> actualizaEstadoIdProceso -->" +qry );		
		      		 
			    try {
			         PreparedStatement pstat = con.prepareStatement(qry);
			         result=pstat.executeUpdate();
			       
			     } catch (SQLException e) {
			         e.printStackTrace();
			     }finally {con.close();}

				
				return result>0;
			}
		



}
 
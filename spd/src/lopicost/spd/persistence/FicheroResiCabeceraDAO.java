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

import lopicost.config.logger.Logger;
import lopicost.config.pool.dbaccess.Conexion;
import lopicost.spd.helper.FicheroResiCabeceraHelper;
import lopicost.spd.model.DivisionResidencia;
import lopicost.spd.model.Farmacia;
import lopicost.spd.robot.helper.PlantillaUnificadaHelper;
import lopicost.spd.security.helper.VisibilidadHelper;
import lopicost.spd.struts.bean.CabecerasXLSBean;
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
	static String TABLA_HISTORICO 	=	"dbo.SPDHst_ficheroResiCabecera";   //tabla de histórico

	
	public static boolean nuevo(String  spdUsuario, String idDivisionResidencia, String idProceso, String fileIn) throws Exception {
		String table = TABLA_ACTIVA;
		String fechaDesde = DateUtilities.getDate(HelperSPD.obtenerFechaDesde(idProceso), "yyyyMMdd", "dd/MM/yyyy");  
		String fechaHasta = DateUtilities.getDate(HelperSPD.obtenerFechaHasta(idProceso), "yyyyMMdd", "dd/MM/yyyy"); 
		
		CabecerasXLSBean desdeToma = FicheroResiCabeceraHelper.getDesdeTomaPrimerDia(idDivisionResidencia);
		CabecerasXLSBean hastaToma = FicheroResiCabeceraHelper.getHastaTomaUltimoDia(idDivisionResidencia);
		
		String tomaInicial = desdeToma!=null?desdeToma.getIdToma():"";
		String tomaFinal = hastaToma!=null?hastaToma.getIdToma():"";
		
		FicheroResiBean cabAnt =  FicheroResiCabeceraDAO.getProcesoAnterior(spdUsuario, idDivisionResidencia, idProceso, false);
		String usuarioEntregaSPD ="";
		String usuarioRecogidaSPD ="";
		String usuarioDesemblistaSPD ="";
		String usuarioProduccionSPD ="";
		String medicoResponsable ="";
		if(cabAnt!=null)
		{
			usuarioEntregaSPD=cabAnt.getUsuarioEntregaSPD()!=null?cabAnt.getUsuarioEntregaSPD():"";
			usuarioRecogidaSPD=cabAnt.getUsuarioRecogidaSPD()!=null?cabAnt.getUsuarioRecogidaSPD():"";
			usuarioDesemblistaSPD=cabAnt.getUsuarioDesemblistaSPD()!=null?cabAnt.getUsuarioDesemblistaSPD():"";
			usuarioProduccionSPD=cabAnt.getUsuarioProduccionSPD()!=null?cabAnt.getUsuarioProduccionSPD():"";
			medicoResponsable=cabAnt.getMedicoResponsable()!=null?cabAnt.getMedicoResponsable():"";
		}

			
        int result=0;
		  Connection con = Conexion.conectar();
	  	   String qry = " INSERT INTO "+table+" ";
	  	   	qry+= " ( ";	
	  	   	qry+= " 	fechaCreacion,  idDivisionResidencia, idProceso, nombreFicheroResi, idEstado, ";
	  	   	qry+= " 	free1, free2, free3, usuarioCreacion, fechaDesde, fechaHasta, 	";
	  	   	qry+= " 	nuevaFechaDesde, nuevaFechaHasta, nuevaTomaDesde, nuevaTomaHasta, ";
	  	   	qry+= " 	usuarioEntregaSPD, usuarioRecogidaSPD, usuarioDesemblistaSPD, ";
	  	   	qry+= " 	usuarioProduccionSPD, medicoResponsable ";
	  	   	qry+= " ) VALUES 	(";
	       	qry+= " 	CONVERT(datetime, getdate(), 120),  '"+idDivisionResidencia+"', ";
	       	qry+= " 	'"+	idProceso+"', '"+fileIn+"','"+SPDConstants.SPD_PROCESO_1_EN_CREACION+"', ";
			qry+= " 	'original', '' , '','"+spdUsuario+"', '"+fechaDesde+"', '"+fechaHasta+"', ";
			qry+= " 	'"+fechaDesde+"', '"+fechaHasta+"', '"+ tomaInicial +"', '"+ tomaFinal+"',  ";
			qry+= " 	'"+usuarioEntregaSPD+"', '"+usuarioRecogidaSPD+"', '"+ usuarioDesemblistaSPD +"', '"+ usuarioProduccionSPD+"', '"+ medicoResponsable+"'  ";
			qry+= "  ) ";

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
		String camposSelect =" d.oidDivisionResidencia, d.nombredivisionresidencia, g.*, ";
		camposSelect+=" g.cipsFicheroXML as cipsTotalesProceso, ";
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
		
		if(oidFicheroResiCabecera>0)					qryWhere+=  " AND g.oidFicheroResiCabecera = '"+oidFicheroResiCabecera+"'";
		if(oidDivisionResidencia>0)						qryWhere+=  " AND d.oidDivisionResidencia = '"+oidDivisionResidencia +"' ";
		if(idProceso!=null && !idProceso.equals(""))	qryWhere+=  " AND g.idProceso = '"+idProceso +"' ";
		if(idEstado!=null && !idEstado.equals(""))		qryWhere+=  " AND g.idEstado = '"+idEstado +"' ";

				 
		if(!count)  
		{
			qryFrom+=  " left join ";
			qryFrom+=  " ( 	 ";
			qryFrom+=  "	select d.idDivisionResidencia, d.idProceso,  count( '1') as numeroValidacionesPendientes  ";
			qryFrom+=  " 	from dbo.SPD_ficheroResiDetalle d  ";
//			qryFrom+=  " 	where isdate(d.resiInicioTratamiento) =1 ";
			qryFrom+=  " 	where d.tipoRegistro='LINEA'";
			qryFrom+=  " 	and (UPPER(d.validar) in ('"+SPDConstants.REGISTRO_VALIDAR+"') OR UPPER(d.controlValidacionDatos) in ('"+SPDConstants.CTRL_VALIDAR_ALERTA+"' ) OR UPPER(d.confirmar) in ('"+SPDConstants.REGISTRO_CONFIRMAR+"' )) ";
			qryFrom+=  " 	group by d.idDivisionResidencia, d.idProceso ";
			qryFrom+=  " ) c6 on (g.idDivisionResidencia=c6.idDivisionResidencia  and g.idProceso=c6.idProceso) ";
			
			qryAux = getOtrosSql2008(inicio, inicio+SPDConstants.PAGE_ROWS, count);
			
			//qryOrder+= " order by g.fechaCreacion desc";
			//qryOrder+= " offset "+ (inicio) + " rows ";
			//qryOrder+= " fetch next "+fin+ " rows only";
		}
		
		Logger.log("SPDLogger", "getQueryGestFicheroResi qrySelect " + qrySelect + qryFrom + qryWhere + qryAux ,Logger.INFO);	
		return qrySelect + qryFrom + qryWhere + qryAux;
	}


	/**
	 * Fetch es una cláusula que funciona a partir del SqlServer 2008 (no inclusive)
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
	 * Como FETCH es una cláusula de versión SQLSERVER>2008 se crea una función un poco más engorrosa pero
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
	    		 while (resultSet.next()) {
	    			 FicheroResiBean  c =creaCabecera(resultSet);
	    			 lista.add(c);
	    		}
     } catch (SQLException e) {
         e.printStackTrace();
     }finally {con.close();}
     return lista;
 }

	  private static FicheroResiBean creaCabecera(ResultSet resultSet) throws Exception {
				 
		  
		  	FicheroResiBean  c =new FicheroResiBean();
			 if (resultSet!=null) {
				 c.setProcesoValido(true);
				 try{
					 c.setCipsTotalesProceso(resultSet.getInt("cipsTotalesProceso"));
				 }catch(Exception e){
					 
				 }
				 c.setCipsFicheroXML(resultSet.getInt("cipsFicheroXML"));
				 c.setCipsActivosSPD(resultSet.getInt("cipsActivosSPD"));
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
				 c.setFechaHoraProceso(resultSet.getTimestamp("fechaCreacion"));
				 c.setFechaValidacionDatos(resultSet.getDate("fechaValidacionDatos"));
				 c.setFilasTotales(resultSet.getInt("filasTotales"));
				 c.setIdDivisionResidencia(resultSet.getString("idDivisionResidencia"));
				 c.setIdEstado(resultSet.getString("idEstado"));
				 c.setIdProceso(resultSet.getString("idProceso"));
				 try{
					 c.setIdRobot(resultSet.getString("idRobot"));
				 	}catch(Exception e){}
				 
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
				 c.setFechaDesde(resultSet.getString("fechaDesde"));
				 c.setFechaHasta(resultSet.getString("fechaHasta"));
				 
				 c.setMedicoResponsable(resultSet.getString("medicoResponsable"));
				 c.setUsuarioEntregaSPD(resultSet.getString("usuarioEntregaSPD"));
				 c.setFechaEntregaSPD(resultSet.getString("fechaEntregaSPD"));
				 c.setUsuarioRecogidaSPD(resultSet.getString("usuarioRecogidaSPD"));
				 c.setFechaRecogidaSPD(resultSet.getString("fechaRecogidaSPD"));
				 c.setUsuarioDesemblistaSPD(resultSet.getString("usuarioDesemblistaSPD"));
				 c.setFechaDesemblistaSPD(resultSet.getString("fechaDesemblistaSPD"));
				 c.setUsuarioProduccionSPD(resultSet.getString("usuarioProduccionSPD"));
				 c.setFechaProduccionSPD(resultSet.getString("fechaProduccionSPD"));
				 c.setNombreProduccionRobot(resultSet.getString("nombreProduccionRobot"));
				 c.setNumeroCreacionFicheroXML(resultSet.getInt("numeroCreacionFicheroXML"));
				
				 
				 
			     java.sql.Timestamp fechaCalculoPrevision = resultSet.getTimestamp("fechaCalculoPrevision");
	 		 if(fechaCalculoPrevision!=null) 
				 {
	 		    // Crea un objeto SimpleDateFormat con el formato deseado
	 		    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	 		    // Formatea el Timestamp a una cadena
	 		    String fechaFormateada = (fechaCalculoPrevision != null) ? sdf.format(new Date(fechaCalculoPrevision.getTime())) : "";
	 		    c.setFechaCalculoPrevision(fechaFormateada);
				 }
	 			    
				 c.setUsuarioCreacion(resultSet.getString("usuarioCreacion"));	 
				 //campos que indican si la producción ha de ser en ortos días o tomas diferentes a la estándar
				 c.setNuevaFechaDesde(resultSet.getString("nuevaFechaDesde"));
				 c.setNuevaFechaHasta(resultSet.getString("nuevaFechaHasta"));
				 c.setNuevaTomaDesde(resultSet.getString("nuevaTomaDesde"));
				 c.setNuevaTomaHasta(resultSet.getString("nuevaTomaHasta"));
			 }
				Farmacia farmacia = FarmaciaDAO.getFarmaciaPorIdDivisionResidencia(c.getUsuarioCreacion(), c.getIdDivisionResidencia());
				
				try{
					c.setResponsableFarmacia(farmacia.getContacto());
					c.setNombreFarmacia(farmacia.getNombreFarmacia());
				}catch(Exception e){
					
				}
				

				

		return c;
	}


	/**OK - 
	   * Método que devuelve las diferentes residencias con fichero
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
	   * Método que devuelve la cabecera anterior que haya tenido una carga de residentes 
	   * @return List<FicheroResiCabeceraBean>
	 * @throws Exception 
	   */

		public static FicheroResiBean getProcesoAnterior(String spdUsuario, String idDivisionResidencia, String procesoDiferenteA, boolean global) throws Exception {
			   Connection con = Conexion.conectar();
			   String table = TABLA_ACTIVA;
			   FicheroResiBean  result = null;
			   String qry = "SELECT top 1 * ";
			    		qry+= " FROM "+table+" g ";
			    		qry+= " INNER JOIN bd_divisionResidencia d ON (g.idDivisionResidencia=d.idDivisionResidencia )";
			    		qry+= " INNER JOIN dbo.bd_residencia r ON (d.idResidencia=r.idResidencia) ";
			    		qry+= " WHERE 1=1 ";
			    		qry+= " AND g.idProceso<>'"+procesoDiferenteA+"' ";
			    		qry+= " AND g.idDivisionResidencia='"+idDivisionResidencia+"' ";
			    		qry+= " AND g.idEstado='"+SPDConstants.REGISTRO_VALIDADO+"' ";
	
			    if(global)	
			    {
		    		qry+= " AND g.cipsFicheroXML*100/g.cipsActivosSPD>75 ";
		    		qry+= " AND g.cipsActivosSPD>0  AND g.cipsFicheroXML>0   ";
			    }
			 			qry+= " ORDER BY g.oidFicheroResiCabecera DESC ";
				 			
			 			
			 			System.out.println(className + "--> getProcesoAnterior global -->" +global + " -  " + qry );		
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

		
		
		/**OK - 
		   * Método que devuelve la cabecera anterior que haya tenido una carga de residentes > de un % de carga según los usuarios activos de SPD 
		   * @return List<FicheroResiCabeceraBean>
		 * @throws Exception 
		   */

			public static FicheroResiBean getProcesoGlobalAnterior(String spdUsuario, String idDivisionResidencia, String procesoDiferenteA) throws Exception {
		 	     return getProcesoAnterior( spdUsuario,  idDivisionResidencia,  procesoDiferenteA, true);
			}
			
			
		/**OK - 
		 * Método que devuelve los diferentes procesos cargados de una residencia
		 * @param idDivisionResidencia
		 * @return
		 * @throws Exception 
		 */
		public static List<FicheroResiBean> getProcesosCargados(String spdUsuario, int oidDivisionResidencia) throws Exception
		{
			return getProcesosCargados(spdUsuario, oidDivisionResidencia, null);
		}
		
	 /**OK - 
		 * Método que devuelve los diferentes procesos cargados de una residencia
		 * @param idDivisionResidencia, idEstado
		 * @return List<FicheroResiCabeceraBean>
	 * @throws Exception 
		 */
		
		
		
		public static List<FicheroResiBean> getProcesosCargados(String spdUsuario, int oidDivisionResidencia, String idEstado) throws Exception {
		Connection con = Conexion.conectar();
			   List<FicheroResiBean>  result = new ArrayList();
			   String qry = "SELECT * "; 
			    		qry+= " FROM "+TABLA_ACTIVA+" g   ";
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
		 * Actualización cabecera después de cargar el fichero.
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
		          stringBuilder.append(error).append("<br>"); // Agregar cada elemento seguido de un salto de línea en HTML
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

		public static boolean editarCabecera(String string, FicheroResiBean cab, FicheroResiForm f) throws ClassNotFoundException, SQLException 
		{
			int result=0;
			Connection con = Conexion.conectar();
			String qry = " update "+TABLA_ACTIVA+" ";
				qry+= "  set free1 = '"+ f.getFree1() + "', ";

		   //if(f.getFechaDesde()!=null && !f.getFechaDesde().equals(""))
	  	    	qry+= "  fechaDesde = '"+ f.getFechaDesde() + "', ";
	  	  //if(f.getFechaHasta()!=null && !f.getFechaHasta().equals(""))
		  	    qry+= "  fechaHasta = '"+ f.getFechaHasta() + "', ";
	  	  //if(f.getNuevaFechaDesde()!=null && !f.getNuevaFechaDesde().equals(""))
	  	    	qry+= "  nuevaFechaDesde = '"+ f.getNuevaFechaDesde() + "', ";
	  	  //if(f.getNuevaFechaHasta()!=null && !f.getNuevaFechaHasta().equals(""))
		  	    qry+= "  nuevaFechaHasta = '"+ f.getNuevaFechaHasta() + "', ";
	  	  //if(f.getNuevaTomaDesde()!=null && !f.getNuevaTomaDesde().equals(""))
		  	    qry+= "  nuevaTomaDesde = '"+ f.getNuevaTomaDesde() + "', ";
	  	  //if(f.getNuevaTomaHasta()!=null && !f.getNuevaTomaHasta().equals(""))
		  	    qry+= "  nuevaTomaHasta = '"+ f.getNuevaTomaHasta() + "', ";

	  	  //if(f.getUsuarioEntregaSPD()!=null && !f.getUsuarioEntregaSPD().equals(""))
		  	    qry+= "  usuarioEntregaSPD = '"+ f.getUsuarioEntregaSPD() + "', ";
	  	  //if(f.getFechaEntregaSPD()!=null && !f.getFechaEntregaSPD().equals(""))
		  	    qry+= "  fechaEntregaSPD = '"+ f.getFechaEntregaSPD() + "', ";
	  	  //if(f.getUsuarioRecogidaSPD()!=null && !f.getUsuarioRecogidaSPD().equals(""))
		  	    qry+= "  usuarioRecogidaSPD = '"+ f.getUsuarioRecogidaSPD() + "', ";
	  	  //if(f.getFechaRecogidaSPD()!=null && !f.getFechaRecogidaSPD().equals(""))
		  	    qry+= "  fechaRecogidaSPD = '"+ f.getFechaRecogidaSPD() + "', ";
	  	  //if(f.getUsuarioDesemblistaSPD()!=null && !f.getUsuarioDesemblistaSPD().equals(""))
		  	    qry+= "  usuarioDesemblistaSPD = '"+ f.getUsuarioDesemblistaSPD() + "', ";
	  	  //if(f.getFechaDesemblistaSPD()!=null && !f.getFechaDesemblistaSPD().equals(""))
		  	    qry+= "  fechaDesemblistaSPD = '"+ f.getFechaDesemblistaSPD() + "', ";
	  	  //if(f.getUsuarioProduccionSPD()!=null && !f.getUsuarioProduccionSPD().equals(""))
		  	    qry+= "  usuarioProduccionSPD = '"+ f.getUsuarioProduccionSPD() + "', ";
	  	  //if(f.getFechaProduccionSPD()!=null && !f.getFechaProduccionSPD().equals(""))
		  	    qry+= "  fechaProduccionSPD = '"+ f.getFechaProduccionSPD() + "', ";
		 //if(f.getMedicoResponsable()!=null && !f.getMedicoResponsable().equals(""))
		  	    qry+= "  medicoResponsable = '"+ f.getMedicoResponsable() + "', ";


	  	    qry+= "  free2 = '"+ f.getFree2() + "', ";
	  	    qry+= "  free3 = '"+ f.getFree3() + "' ";
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
		
	  	   String qry = " UPDATE "+TABLA_ACTIVA+" ";
	  	    qry+= "  SET fechaCalculoPrevision=CONVERT(datetime, getDate(), 120), ";
	  	    qry+= "  idEstado ='"+cab.getIdEstado()+"' ";	  	    
	  	    qry+= "  WHERE oidFicheroResiCabecera='"+ cab.getOidFicheroResiCabecera() +"' "; 
	  	    
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
	  	 		//qry+= " and fechaCreacion < getDate() ";  //se actualiza el estado de  procesos colgados de hace un día
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
	 * Pasamos a histórico las producciones de más de X días
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
		  	qry+= " usuarioCreacion, fechaCalculoPrevision, cipsActivosSPD, porcentajeCIPS, fechaDesde, fechaHasta, nuevaFechaDesde, nuevaFechaHasta, nuevaTomaDesde, nuevaTomaHasta )  ";
		  	qry+= " SELECT getDate(), oidFicheroResiCabecera, fechaCreacion, idDivisionResidencia, idProceso, resultLog, filasTotales, ";
		  	qry+= " nombreFicheroResi, nombreFicheroXML, fechaCreacionFicheroXML, fechaValidacionDatos, usuarioValidacion, idEstado, cipsFicheroXML, numErrores, errores, free1, free2, free3, ";
		  	qry+= " usuarioCreacion, fechaCalculoPrevision, cipsActivosSPD, porcentajeCIPS, fechaDesde, fechaHasta, nuevaFechaDesde, nuevaFechaHasta, nuevaTomaDesde, nuevaTomaHasta, ";
		  	qry+= " usuarioEntregaSPD, fechaEntregaSPD, usuarioRecogidaSPD, fechaRecogidaSPD, usuarioDesemblistaSPD, fechaDesemblistaSPD, usuarioProduccionSPD, fechaProduccionSPD, medicoResponsable, ";
		  	qry+= " nombreProduccionRobot, numeroCreacionFicheroXML ";

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
	 * Método para borrar el detalle de los ficheros, de forma masiva, una vez ya pasados a histórico
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
		query+= " AND oidFicheroResiCabecera in (SELECT oidFicheroResiCabecera FROM "+TABLA_HISTORICO+ ") "; //nos aseguramos que ya se ha copiado en histórico 

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
	  	
	  	System.out.println(className + "--> actualizaEstadoIdProceso -->" +qry );		

	  	try {
	         PreparedStatement pstat = con.prepareStatement(qry);
	         result=pstat.executeUpdate();
	     } catch (SQLException e) {
	         e.printStackTrace();
	     }finally {con.close();}

		return result>0;
	}

	public static boolean editaFechas(FicheroResiBean cab) throws SQLException {
		int result=0;
		Connection con = Conexion.conectar();
		
		String qry = " UPDATE dbo.SPD_ficheroResiCabecera ";
  	    qry+= "  SET fechaDesde ='"+cab.getFechaDesde()+"', fechaHasta ='"+cab.getFechaHasta()+"'  ";
		qry+= "  WHERE idDivisionResidencia ='"+ cab.getIdDivisionResidencia() +"' "; 
	  	qry+= "  AND idProceso='"+ cab.getIdProceso() +"' "; 
	  	qry+= "  AND oidFicheroResiCabecera ='"+cab.getOidFicheroResiCabecera() +"' ";   	    		
	  	
	  	System.out.println(className + "--> editaFechas -->" +qry );		

	  	try {
	         PreparedStatement pstat = con.prepareStatement(qry);
	         result=pstat.executeUpdate();
	     } catch (SQLException e) {
	         e.printStackTrace();
	     }finally {con.close();}

		return result>0;
	}

	public void actualizaDatosFicheroXMLEnCabecera(String idUsuario, DivisionResidencia div, FicheroResiBean cab) throws SQLException {
		int result = 0;
		//lo siguiente ha de coincidir con XMLRobotDao.getTratamientosDeProceso
		//int numeroVeces = cab.getNumeroCreacionFicheroXML()+1;
		//String fechaArreglada = cab.getFechaDesde()!=null ? cab.getFechaDesde().replace("/", ""):numeroVeces+""; //quitamos "/" en caso que no exista le ponemos  numeroCreacionesXML
		//String nombreProduccionRobot= div.getLocationId()+"_"+fechaArreglada+"_"+ numeroVeces;
		String nombreProduccionRobot = cab.getNombreProduccionRobot();
		//if(nombreProduccionRobot==null || nombreProduccionRobot.isEmpty())
		{
			try{
				//FicheroResiBean cabMadre = FicheroResiCabeceraDAO.getFicheroResiCabeceraByOid(idUsuario, cab.getOidFicheroResiCabecera());
				//int numeroCreaciones = cab.getNumeroCreacionFicheroXML()+1;
				
				//String fechaArreglada = DateUtilities.convertFormatDateString(cab.getFechaDesde(), SPDConstants.FORMATO_FECHA_yyyyMMdd, SPDConstants.FORMATO_FECHA_DEFAULT);
				//fechaArreglada = cab.getFechaDesde()!=null ? cab.getFechaDesde().replace("/", ""):""; //quitamos "/" en caso que no exista le ponemos  numeroCreacionesXML
				//nombreProduccionRobot= div.getLocationId()+"_"+fechaArreglada;
			
				//En vez de fecha, ponemos el último String del idProceso, para poder diferenciar hay producciones con la misma fecha
				String codigo = PlantillaUnificadaHelper.extraerUltimaParte(cab.getIdProceso());
				nombreProduccionRobot= div.getLocationId()+"_"+codigo;
				
			}catch(Exception e)
			{
				nombreProduccionRobot=  div.getNombreBolsa();
			}
		}
		 
		   
		Connection con = Conexion.conectar();
		String nomFitxer = SPDConstants.PATH_DOCUMENTOS + "/robot/FILIA_RX_" + cab.getIdProceso() +  ".xml";
		String qry = " UPDATE dbo.SPD_ficheroResiCabecera ";
  	    qry+= "  SET fechaCreacionFicheroXML = GETDATE() ";
  	    qry+= "  , nombreFicheroXML = '" +  nomFitxer +"'";
  	    //qry+= "  , nombreProduccionRobot ='"+div.getLocationId()+"_"+cab.getFechaDesde()+"_"+numeroVeces +"'" ;
  	    qry+= "  , nombreProduccionRobot ='"+nombreProduccionRobot +"'" ;
  	    qry+= "  , numeroCreacionFicheroXML = COALESCE(numeroCreacionFicheroXML, 0) + 1  ";
		qry+= "  WHERE idDivisionResidencia ='"+ cab.getIdDivisionResidencia() +"' "; 
	  	qry+= "  AND idProceso='"+ cab.getIdProceso() +"' "; 
	  	qry+= "  AND oidFicheroResiCabecera ='"+cab.getOidFicheroResiCabecera() +"' ";   	    		
	  	
	  	System.out.println(className + "--> actualizaDatosFicheroXMLEnCabecera -->" +qry );		

	  	try {
	         PreparedStatement pstat = con.prepareStatement(qry);
	         result=pstat.executeUpdate();
	     } catch (SQLException e) {
	         e.printStackTrace();
	     }finally {con.close();}

		;
	}

		



}
 
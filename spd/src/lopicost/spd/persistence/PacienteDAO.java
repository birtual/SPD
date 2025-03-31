package lopicost.spd.persistence;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.impl.Log4JCategoryLog;

import lopicost.config.logger.Logger;
import lopicost.config.pool.dbaccess.Conexion;
import lopicost.spd.model.GestSustitucionesLite;
import lopicost.spd.security.helper.VisibilidadHelper;
import lopicost.spd.struts.bean.BolquersDetalleBean;
import lopicost.spd.struts.bean.DiscrepanciaBean;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.struts.bean.PacienteBean;
import lopicost.spd.struts.bean.TratamientoRctBean;
import lopicost.spd.struts.form.PacientesForm;
import lopicost.spd.struts.form.SustXComposicionForm;
import lopicost.spd.utils.SPDConstants;
import lopicost.spd.utils.StringUtil;
 
 
public class PacienteDAO extends GenericDAO{
	
	
	static String className="PacienteDAO";
	

    public static int  getCountBdPacientes(String spdUsuario, PacientesForm form) throws Exception {
    	
      	 String qry = getQueryPacientes(spdUsuario, form.getOidDivisionResidencia(), form.getOidPaciente(), form.getCampoGoogle(), form.getCampoOrder(), true, 0, 0
      			 , null, form.getFiltroEstadosSPD()
     			, form.getFiltroEstadosResidente(), form.getFiltroEstatusResidente(), form.getFiltroBolquers(), form.getFiltroMutua()  );
   	 System.out.println("getCountBdPacientes -->" +qry );		
      	 Connection con = Conexion.conectar();
     //	 	 System.out.println("connected main" );
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
    
    
    public static int  getCountBdPacientesProceso(String spdUsuario, PacientesForm form) throws Exception {
    	
   	 //String qry = getQueryPacientesProceso( form.getOidDivisionResidencia(), form.getOidPaciente(), form.getCampoGoogle(),  true,  0, 0, null, form.getIdProceso());
    String qry = getQueryPacientesProceso(spdUsuario,  form.getOidDivisionResidencia(), true,  0, 0
    		, form.getIdProceso(), form.getCampoGoogle(), form.getFiltroEstadosSPD()
			, form.getFiltroEstadosResidente(), form.getFiltroEstatusResidente(), form.getFiltroBolquers(), form.getFiltroMutua()   );
	 System.out.println("getCountBdPacientesProceso -->" +qry );		
   	 Connection con = Conexion.conectar();
  //	 	 System.out.println("connected main" );
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
    
	public static String getQueryPacientes(String spdUsuario, PacientesForm form, boolean count, int inicio, int fin, String seleccionCampo) throws Exception {
		return getQueryPacientes( spdUsuario, form.getOidDivisionResidencia(), form.getOidPaciente(), form.getCampoGoogle(), form.getCampoOrder(), count, inicio, fin
				, seleccionCampo, form.getFiltroEstadosSPD()
				, form.getFiltroEstadosResidente(), form.getFiltroEstatusResidente(), form.getFiltroBolquers(), form.getFiltroMutua()  );
	}

	
	public static String getQueryPacientesProceso(String spdUsuario, PacientesForm form, boolean count, int inicio, int fin, String seleccionCampo) throws Exception {
		//return getQueryPacientesProceso( form.getOidDivisionResidencia(), form.getOidPaciente(), form.getCampoGoogle(), count, inicio, fin, seleccionCampo, form.getIdProceso());
		return getQueryPacientesProceso( spdUsuario, form.getOidDivisionResidencia(), count, inicio, fin
				, form.getIdProceso(), form.getCampoGoogle(), form.getFiltroEstadosSPD()
				, form.getFiltroEstadosResidente(), form.getFiltroEstatusResidente(), form.getFiltroBolquers(), form.getFiltroMutua()  );
	}

	/**
	 * Función que construye la query de pacientes, ya sea para mostrar los de la residencia que se pase por parámetro
	 * @param oidDivisionResidencia - Obligatorio 
	 * @param oidPaciente	- Opcional
	 * @param cip	- Opcional
	 * @param nombre	- Opcional
	 * @param apellido1	- Opcional
	 * @param apellido2	- Opcional
	 * @param campoOrder	- en caso de ordenarlo por cualquier campo de Pacientes
	 * @param count		-	Si es para contar registros, para paginación
	 * @param inicio	- inicio para pagináción
	 * @param fin		- fin para paginación 
	 * @param seleccionCampo	-	en caso de querer mostrar un campo determinado (normalmente para listados).
	 * @return
	 * @throws Exception 
	 */
	private static String getQueryPacientes(String spdUsuario, int oidDivisionResidencia, String oidPaciente
			,  String campoGoogle, String campoOrder, boolean count, int inicio, int fin
			, String seleccionCampo, String estadoSPD,  String estadoResidente, String estatusResidente, String bolquers, String mutua) throws Exception {
		   
		String qryOrder= "order by p.CIP ";
		if (campoOrder!=null && !campoOrder.trim().equals("")) 
			qryOrder+=  " order by p."+campoOrder;
    	
    	
		//default
		String qrySelect = " select * from ( select  distinct ROW_NUMBER() OVER("+qryOrder+") AS ROWNUM,  p.* ";  
		String qryFrom=  " from dbo.bd_pacientes p inner join bd_divisionResidencia d on (p.idDivisionResidencia=d.idDivisionResidencia) ";
		String qryWhere=  " WHERE d.idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";

		String qryAux="";
		
 		//si no viene campo informado, ponemos todo por defecto
	
		if(seleccionCampo!=null && !seleccionCampo.equals("") && !seleccionCampo.equals("*"))  //nos llega un campo concreto
			qrySelect = "select distinct  p." + seleccionCampo ;

		//if(oidPaciente>0)
		if(oidPaciente!=null && !oidPaciente.equals(""))
			qryWhere+=  " and CAST( p.oidPaciente as CHAR) ='" +oidPaciente+"' ";			
		
		//forzamos que siempre sea una resi válida
		if(oidDivisionResidencia>0)
			qryWhere+=  " and  d.oidDivisionResidencia ='"+oidDivisionResidencia+"' ";
	
		campoGoogle = campoGoogle.trim(); 
    	if(campoGoogle!=null && !campoGoogle.equals(""))
    	{
			if(campoGoogle!=null && !campoGoogle.equals(""))
			{
				qryWhere+=" and ( p.CIP like '%"+campoGoogle+"%'  OR ";
				//qryWhere+=" 		p.nom like '%"+campoGoogle+"%'  OR ";
				qryWhere+=" 		(p.nom  + ' ' + p.apellido1 + ' ' + p.apellido2 ) like '%"+campoGoogle+"%'  OR ";
				//qryWhere+=" 		p.apellido2 like '%"+campoGoogle+"%'  OR ";
				qryWhere+=" 		p.comentarios like '%"+campoGoogle+"%' OR   ";
				qryWhere+=" 		CAST( p.oidPaciente as CHAR) = '"+campoGoogle+"'   ";				
				qryWhere+=" 	) ";
				
			}
    	}
		if(mutua!=null && !mutua.equals(""))
		{
			if(mutua.equals("N"))
				qryWhere+=" and (mutua = 'N' or mutua is null) ";
			else 
				if(mutua.equals("S"))
					qryWhere+=" and mutua = 'S'  ";
		}
		if(bolquers!=null && !bolquers.equals(""))
		{
			qryWhere+=" and bolquers like '"+bolquers+"' ";
		}
		if(estadoSPD!=null && !estadoSPD.equals(""))
		{
			qryWhere+=" and spd like  '"+estadoSPD+"' ";
		}
		if(estadoResidente!=null  && !estadoResidente.equals(""))
		{
			qryWhere+=" and activo = '"+estadoResidente+"' ";
		}
		if(estatusResidente!=null && !estatusResidente.equals(""))
		{
			qryWhere+=" and ( UPPER(estatus) = '"+estatusResidente.toUpperCase()+"' OR COALESCE(activo, 'SIN_ESTADO') + ' - ' + COALESCE(estatus, 'SIN_ESTATUS') = '"+estatusResidente+"'  )";
		}
    	
    	qryAux = 	getOtrosSql2008(inicio, inicio+SPDConstants.PAGE_ROWS, count);
    	
    	
    	
    /*
    	else
    	{
    		qryAux+= " offset "+ (inicio) + " rows ";
    		qryAux+= " fetch next "+SPDConstants.PAGE_ROWS+ " rows only";
    	}
    	*/
    	

		
	   	//si es contador inicializo la query
    	if(count)  
    	{
    		qrySelect = "select count(*) as quants";
    		qryOrder="";
    		qryAux= "";
    	}
    	
     	return qrySelect + qryFrom + qryWhere +qryAux;

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
	
	
	
	
	/**
	 * Función que construye la query de pacientes, para mostrar los de la residencia que tienen un tratamiento en un fichero de resi para SPD
	 * @param oidDivisionResidencia - Obligatorio 
	 * @param oidPaciente	- Opcional
	 * @param cip	- Opcional
	 * @param nombre	- Opcional
	 * @param apellido1	- Opcional
	 * @param apellido2	- Opcional
	 * @param campoOrder	- en caso de ordenarlo por cualquier campo de Pacientes
	 * @param count		-	Si es para contar registros, para paginación
	 * @param inicio	- inicio para pagináción
	 * @param fin		- fin para paginación 
	 * @param seleccionCampo	-	en caso de querer mostrar un campo determinado (normalmente para listados).
	 * @param idProceso	-	Enlace con un proceso determinado. OBLIGATORIO
	 * @param string3 
	 * @param string2 
	 * @param string 
	 * @return
	 * @throws Exception 
	 */
	private static String getQueryPacientesProceso(String spdUsuario, int oidDivisionResidencia, boolean count, int inicio, int fin
			, String idProceso, String campoGoogle, String estadoSPD, String estadoResidente, String estatusResidente, String bolquers, String mutua) throws Exception {

		//default
		String 	qrySelect = " select distinct coalesce(  p.CIP, rd.resiCIP) as CIP, rd.resiNombre, rd.resiApellido1, rd.resiApellido2, p.oidPaciente,  p.nom, p.apellido1, p.apellido2, p.bolquers, ";
				qrySelect+= " p.idPacienteResidencia, p.cognoms, p.cognomsNom, p.nIdentidad, p.segSocial, p.planta, p.habitacion, p.idDivisionResidencia, p.spd, p.ACTIVO, p.idFarmatic, p.codigoUP,  ";                      
				qrySelect+= " p.fechaProceso, p.exitus, p.estatus, p.comentarios, p.fechaAltaPaciente, p.mutua    ";  
		String 	qryFrom=  	" from dbo.bd_pacientes p inner join bd_divisionResidencia d on (p.idDivisionResidencia=d.idDivisionResidencia) ";
				qryFrom+=	" full outer join SPD_ficheroResiDetalle rd ";
				qryFrom+=	" on ( rd.resiCIP is not null and rd.resiCIP=p.CIP)";
		
	  	//forzamos que siempre sea una resi válida
				  	String qryWhere=  " WHERE p.idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
				qryWhere+= 	" and (p.CIP is not null or rd.resiCIP is not null) ";
    			qryWhere+=  " and (rd.idProceso='"+idProceso+"' OR d.oiddivisionResidencia = '"+oidDivisionResidencia+"') ";
    			
    			if(campoGoogle!=null && !campoGoogle.equals(""))
    			{
    				qryWhere+=" and ( p.CIP like '%"+campoGoogle+"%'  OR ";
    				qryWhere+=" 		p.nom like '%"+campoGoogle+"%'  OR ";
    				qryWhere+=" 		p.apellido1 like '%"+campoGoogle+"%'  OR ";
    				qryWhere+=" 		p.apellido2 like '%"+campoGoogle+"%'  OR ";
    				qryWhere+=" 		p.comentarios like '%"+campoGoogle+"%'   ";
    				qryWhere+=" 	) ";
    				
    			}
    			if(mutua!=null && !mutua.equals(""))
    			{
    				qryWhere+=" and mutua = '"+mutua+"' ";
    			}
    			if(bolquers!=null && !bolquers.equals(""))
    			{
    				qryWhere+=" and bolquers = '"+bolquers+"' ";
    			}
    			if(estadoSPD!=null)	//puede ser vacío
    			{
    				qryWhere+=" and spd = '"+estadoSPD+"' ";
    			}
    			if(estadoResidente!=null) //puede ser vacío
    			{
    				qryWhere+=" and estado = '"+estadoResidente+"' ";
    			}
    			if(estatusResidente!=null) //puede ser vacío
    			{
    				qryWhere+=" and ( UPPER(estatus) = '"+estatusResidente.toUpperCase()+"' OR estado + ' - ' + estatus = '"+estatusResidente+"'  )";
    			}
    			
    	String 	qryOrder=  	" order by CIP"; 
		String 	qryAux="";
		
		
    		
    	//si es contador inicializo la query
    	if(count)  
    	{
			qrySelect = "select count(distinct coalesce(  p.CIP, rd.resiCIP)) as quants";
			qryOrder= "";
    	}
    	else
    	{
    		qryAux+= " offset "+ (inicio) + " rows ";
    		qryAux+= " fetch next "+SPDConstants.PAGE_ROWS+ " rows only";
    	}
   		
    	return qrySelect + qryFrom + qryWhere + qryOrder + qryAux;
	}
	
	
	/*
	
	private static String getQueryPacientesProceso(String oidDivisionResidencia, String oidPaciente,  String campoGoogle,  boolean count, int inicio, int fin, String seleccionCampo, String idProceso) {
		   
		//default
		String qrySelect = "select distinct coalesce(  p.CIP, rd.resiCIP) as id, p.CIP, rd.resiCIP, rd.resiNombre, rd.resiApellido1, rd.resiApellido2, p.oidPaciente,  p.nom, p.apellido1, p.apellido2, p.bolquers, ";
		qrySelect+= " p.idPacienteResidencia, p.cognoms, p.cognomsNom, p.nIdentidad, p.segSocial, p.planta, p.habitacion, p.idDivisionResidencia, p.spd, p.ACTIVO,  ";                      
		qrySelect+= " p.fechaProceso, p.exitus, p.estatus, p.comentarios, p.fechaAltaPaciente    ";  
		String qryFrom=  " from dbo.bd_pacientes p inner join bd_divisionResidencia d on (p.idDivisionResidencia=d.idDivisionResidencia) ";
		String qryWhere=  " where 1=1 ";
		String qryOrder=  " order by p.CIP"; 
		String qryAux="";
		
		
		//si no viene campo informado, ponemos todo por defecto
	
		if(seleccionCampo!=null && !seleccionCampo.equals("") && !seleccionCampo.equals("*"))  //nos llega un campo concreto
			qrySelect = "select distinct  p." + seleccionCampo ;
   		
		
		//from
		qryFrom+=	" inner join SPD_ficheroResiCabecera rc on (rc.idDivisionResidencia=d.idDivisionResidencia) ";
    	qryFrom+=	" full outer join SPD_ficheroResiDetalle rd ";
    	qryFrom+=	" on ( ";
    	qryFrom+=	" 		rc.oidFicheroResiCabecera =rd.oidFicheroResiCabecera ";
//    	qryFrom+=	" 		and (rd.resiCIP is not null and rd.resiCIP=p.CIP or rd.resiCIP=p.CipFicheroResi)";
    	qryFrom+=	" 		and (rd.resiCIP is not null and rd.resiCIP=p.CIP)";
    	qryFrom+=	"	 ) " ;

		
		
		
		
		//if(oidPaciente>0)
		if(oidPaciente!=null && !oidPaciente.equals(""))
			qryWhere+=  " and p.oidPaciente ='" +oidPaciente+"' "; 		
    	if(campoGoogle!=null && !campoGoogle.equals(""))
    	{
			qryWhere+=  " and ";
			qryWhere+=  " ( ";
			qryWhere+=  " 		p.cip like '%"+campoGoogle+"%' ";
    		qryWhere+=  " 		p.nom like '%"+campoGoogle+"%' ";
    		qryWhere+=  " 		OR p.apellido1 like '%"+campoGoogle+"%' ";
    		qryWhere+=  "		OR p.apellido2 like '%"+campoGoogle+"%' ";
			qryWhere+=  " ) ";
    	}
    
    	//proceso Obligatorio
    	qryWhere+=  " and (rd.idProceso='"+idProceso+"' OR rd.idProceso is null) ";

    	//forzamos que siempre sea una resi válida
    	if(oidDivisionResidencia!=null && !oidDivisionResidencia.equals(""))
    	qryWhere+=  " and  d.oidDivisionResidencia ='"+oidDivisionResidencia+"' ";
    		
    	//si es contador inicializo la query
    	if(count)  
    	{
			qrySelect = "select count(*) as quants";
			qryOrder= "";
    	}
    	else
    	{
    		qryAux+= " offset "+ (inicio) + " rows ";
    		qryAux+= " fetch next "+SPDConstants.PAGE_ROWS+ " rows only";
    	}
   		
    	return qrySelect + qryFrom + qryWhere + qryOrder + qryAux;
	}
	
	*/
	  public  List<PacienteBean> getPacientes(String spdUsuario, PacientesForm form, int inicio, int fin, String distinctCampo) throws Exception {

		  String qry = getQueryPacientes( spdUsuario,  form, false, inicio, fin, distinctCampo);
	    	 Connection con = Conexion.conectar();
	
	    	 System.out.println(className + "--> getPacientes -->" +qry );		
  
     	//	qry+= " offset 20 rows ";
     	//	qry+= " fetch next 10 rows only";
     		
	    	 ResultSet resultSet = null;

	    	 List<PacienteBean> lista= new ArrayList<PacienteBean>();	
	    	 try {
	    		 PreparedStatement pstat = con.prepareStatement(qry);
	    		 resultSet = pstat.executeQuery();

	    		 while (resultSet.next()) {
	    			 PacienteBean  c =creaPaciente(resultSet);
	    			 lista.add(c);
	    		 }
     } catch (SQLException e) {
         e.printStackTrace();
     }finally {con.close();}
	    
     return lista;
 }

	  
	  /**
	   * Método que retorna un listado de pacientes junto con sus tratamientos recibidos en un fichero y ya cargados en el sistema bajo un idProceso/residencia determinado
	   * @param form
	   * @param inicio
	   * @param fin
	   * @param distinctCampo
	   * @return
	 * @throws Exception 
	   */
	public List<PacienteBean> getPacientesProceso(String spdUsuario, PacientesForm form, int inicio, int fin, String distinctCampo) throws Exception {
		  String qry = getQueryPacientesProceso( spdUsuario, form, false, inicio, fin, distinctCampo);
	    	 Connection con = Conexion.conectar();
	
	    	 System.out.println(className + "--> getPacientesProceso -->" +qry );		

		  	//	qry+= " offset 20 rows ";
		  	//	qry+= " fetch next 10 rows only";
		  		
	    	 ResultSet resultSet = null;

	    	 List<PacienteBean> listaPacientes= new ArrayList<PacienteBean>();	
	    	 List<FicheroResiBean> listaTratamientosFichero = new ArrayList<FicheroResiBean>();	
	    	 try {
	    		 PreparedStatement pstat = con.prepareStatement(qry);
	    		 resultSet = pstat.executeQuery();
	    		 
	    		 //la query ha de estar ordenada por CIP. Después comparamos Cips para incluir los tratamientos en cada uno de ellos
	    		 String idPacienteAnterior="-999";
	    		 String idPacienteEnCurso="-1";
    			 PacienteBean  c =new PacienteBean();
    			 
    	    	 while (resultSet.next()) {
	    			 
	    			 //oidPacienteEnCurso=resultSet.getInt("oidPaciente");
    	    		 idPacienteEnCurso=StringUtil.limpiarTextoyEspacios(resultSet.getString("CIP"));
    	    		 if(idPacienteAnterior=="-999") 
    	    		 {
		    			 c =creaPaciente(resultSet);
    	    			 idPacienteAnterior=idPacienteEnCurso; //si es el primer ciclo
    	    		 }
    	    		 
    	    		 if( idPacienteEnCurso==null) continue;
	    			 if( !idPacienteAnterior.equals(idPacienteEnCurso) )  //si son diferentes, hay cambio de CIP, guardamos en la lista e inicializamos
	             	 {
		    			 c.setListaTratamientosFichero(listaTratamientosFichero);
		    			 listaPacientes.add(c);
		    			 c =creaPaciente(resultSet);
		    			 listaTratamientosFichero = new ArrayList<FicheroResiBean>();	
		    			 
	             	 }
	    			
	    			 //creamos el tratamiento
	    			// FicheroResiBean f = creaTratamiento(resultSet); 
	    			//listaTratamientosFichero.add(f);
	    			 idPacienteAnterior=idPacienteEnCurso;
		     }
    	    	 if(idPacienteAnterior!=null) //usuario final
    	    	 {
    	    		// c.setListaTratamientosFichero(listaTratamientosFichero);
	    			 listaPacientes.add(c);
    	    	 }
	  } catch (SQLException e) {
	      e.printStackTrace();
	  }finally {con.close();}
		    
	  return listaPacientes;
	}

	
	
	private FicheroResiBean creaTratamiento(ResultSet resultSet) throws SQLException {
		FicheroResiBean f = new FicheroResiBean();
		f.setIdDivisionResidencia(resultSet.getString("idDivisionResidencia"));
		f.setNombreDivisionResidencia(resultSet.getString("nombreDivisionResidencia"));
		f.setOidFicheroResiCabecera(resultSet.getInt("oidFicheroResiCabecera"));
		f.setFechaHoraProceso(resultSet.getDate("fechaProceso"));
		f.setIdProceso(resultSet.getString("idProceso"));
		f.setResultLog(resultSet.getString("resultLog"));
		f.setFilasTotales(resultSet.getInt("filasTotales"));
		f.setNombreFicheroResi(resultSet.getString("nombreFicheroResi"));
		f.setNombreFicheroXML(resultSet.getString("nombreFicheroXML"));
		f.setFechaCreacionFicheroXML(resultSet.getDate("fechaCreacionFicheroXML"));
		f.setFechaValidacionDatos(resultSet.getDate("fechaValidacionDatos"));
		f.setUsuarioValidacion(resultSet.getString("usuarioValidacion"));
		f.setIdEstado(resultSet.getString("idEstado"));
	//	f.setCipsFicheroOrigen(resultSet.getInt("cipsFicheroOrigen"));
		f.setCipsFicheroXML(resultSet.getInt("cipsFicheroXML"));
	//	f.setCipsNoExistentesBbdd(resultSet.getInt("cipsNoExistentesBbdd"));
		f.setResiCIP(resultSet.getString("resiCIP"));
		f.setResiNombrePaciente(resultSet.getString("resiNombrePaciente"));
		f.setResiCn(resultSet.getString("resiCn"));
		f.setResiMedicamento(resultSet.getString("resiMedicamento"));
		f.setResiFormaMedicacion(resultSet.getString("resiFormaMedicacion"));
		f.setResiInicioTratamiento(resultSet.getString("resiInicioTratamiento"));
		f.setResiFinTratamiento(resultSet.getString("resiFinTratamiento"));
		f.setResiObservaciones(resultSet.getString("resiObservaciones"));
		f.setResiVariante(resultSet.getString("resiVariante"));
		f.setResiComentarios(resultSet.getString("resiComentarios"));
		f.setResiSiPrecisa(resultSet.getString("resiSiPrecisa"));
		f.setSpdCnFinal(resultSet.getString("spdCnFinal"));
		f.setSpdNombreBolsa(resultSet.getString("spdNombreBolsa"));
		f.setSpdFormaMedicacion(resultSet.getString("spdFormaMedicacion"));
		f.setSpdAccionBolsa(resultSet.getString("spdAccionBolsa"));
		f.setResiD1(resultSet.getString("resiD1"));
		f.setResiD2(resultSet.getString("resiD2"));
		f.setResiD3(resultSet.getString("resiD3"));
		f.setResiD4(resultSet.getString("resiD4"));
		f.setResiD5(resultSet.getString("resiD5"));
		f.setResiD6(resultSet.getString("resiD6"));
		f.setResiD7(resultSet.getString("resiD7"));
		f.setResiViaAdministracion(resultSet.getString("resiViaAdministracion"));
		f.setResiToma1(resultSet.getString("resiToma1"));
		f.setResiToma2(resultSet.getString("resiToma2"));
		f.setResiToma3(resultSet.getString("resiToma3"));
		f.setResiToma4(resultSet.getString("resiToma4"));
		f.setResiToma5(resultSet.getString("resiToma5"));
		f.setResiToma6(resultSet.getString("resiToma6"));
		f.setResiToma7(resultSet.getString("resiToma7"));
		f.setResiToma8(resultSet.getString("resiToma8"));
		f.setResiToma9(resultSet.getString("resiToma9"));
		f.setResiToma10(resultSet.getString("resiToma10"));
		f.setResiToma11(resultSet.getString("resiToma11"));
		f.setResiToma12(resultSet.getString("resiToma12"));
		f.setResiToma13(resultSet.getString("resiToma13"));
		f.setResiToma14(resultSet.getString("resiToma14"));
		f.setResiToma15(resultSet.getString("resiToma15"));
		f.setResiToma16(resultSet.getString("resiToma16"));
		f.setResiToma17(resultSet.getString("resiToma17"));
		f.setResiToma18(resultSet.getString("resiToma18"));
		f.setResiToma19(resultSet.getString("resiToma19"));
		f.setResiToma20(resultSet.getString("resiToma20"));
		f.setResiToma21(resultSet.getString("resiToma21"));
		f.setResiToma22(resultSet.getString("resiToma22"));
		f.setResiToma23(resultSet.getString("resiToma23"));
		f.setResiToma24(resultSet.getString("resiToma24"));
		//f.setValido(resultSet.getBoolean("valido"));
		f.setResultLog(resultSet.getString("resultLog"));
		f.setRow(resultSet.getInt("row"));
		f.setOidFicheroResiDetalle(resultSet.getInt("oidFicheroResiDetalle"));
		f.setMensajesInfo(resultSet.getString("mensajesInfo"));
		f.setMensajesAlerta(resultSet.getString("mensajesAlerta"));
		f.setSpdNomGtVmpp(resultSet.getString("spdNomGtVmpp"));
		f.setIdEstado(resultSet.getString("idEstado"));
		f.setResiPeriodo(resultSet.getString("resiPeriodo"));
		f.setSpdCIP(resultSet.getString("spdCIP"));
		f.setIdTratamientoCIP(resultSet.getString("idTratamientoCIP"));
		return f;
	}


	private static PacienteBean creaPaciente(ResultSet resultSet) throws SQLException {
		 PacienteBean  c =new PacienteBean();
		 c.setOidPaciente(resultSet.getInt("oidPaciente"));
		 c.setCIP(resultSet.getString("Cip"));
		 //	 c.setId(resultSet.getString("id"));
	//	 c.setCipFicheroResi(resultSet.getString("CipFicheroResi"));

		 c.setNombre(resultSet.getString("nom"));

		 String apellido1=resultSet.getString("apellido1");
		 String apellido2=resultSet.getString("apellido2");
		 String apellidos=resultSet.getString("cognoms");
		 
		 if(apellido1==null || apellido1.equals("")){ //en caso que esté vacío utilizamos el campo "cognoms" para el primer apellido. 
			 apellido1=apellidos;
			 apellido2="";
		 }
			 
		 
		 c.setApellido1(apellido1); //preparado para el futuro
		 c.setApellido2(apellido2);	//preparado para el futuro
		 c.setBolquers(resultSet.getString("bolquers"));
		 c.setIdPacienteResidencia(resultSet.getString("idPacienteResidencia"));
		 c.setApellidos(resultSet.getString("cognoms"));
		 c.setApellidosNombre(resultSet.getString("cognomsNom"));
		 c.setNumIdentidad(resultSet.getString("nIdentidad"));
		 c.setSegSocial(resultSet.getString("segSocial"));
		 c.setPlanta(resultSet.getString("planta"));
		 c.setHabitacion(resultSet.getString("habitacion"));
		 c.setIdDivisionResidencia(resultSet.getString("idDivisionResidencia"));
		 c.setSpd(resultSet.getString("spd"));
		 c.setFechaProceso(resultSet.getDate("fechaProceso"));
		 c.setExitus(resultSet.getInt("exitus"));
		 c.setActivo(resultSet.getString("activo"));
		 c.setEstatus(resultSet.getString("estatus"));
		 c.setComentarios(resultSet.getString("comentarios"));
		 c.setFechaAltaPaciente(resultSet.getString("fechaAltaPaciente"));
		 c.setIdPharmacy(resultSet.getString("idFarmatic"));
		 c.setMutua(resultSet.getString("mutua"));
		 c.setUPFarmacia(resultSet.getString("codigoUP"));
		 
		 
		return c;
	}
	
	public static   PacienteBean getPacientePorCIP(String CIP) throws Exception {

		  String qry = "SELECT * FROM bd_pacientes p ";
		  	qry+=  " WHERE p.CIP='"+CIP+"'";
		  
		  Connection con = Conexion.conectar();
		  System.out.println(className + "--> getPacientePorCIP -->" +qry );		
		  ResultSet resultSet = null;
		  PacienteBean paciente= null;	
	    	 try {
	    		 PreparedStatement pstat = con.prepareStatement(qry);
	    		 resultSet = pstat.executeQuery();
	    		 if(resultSet.next()) {
	    			 paciente =creaPaciente(resultSet);
	    			
	    		 }
   } catch (SQLException e) {
       e.printStackTrace();
   }finally {con.close();}
	    
   return paciente;
}

	
	
	public static   PacienteBean getPacientePorCIP(String spdUsuario, String CIP) throws Exception {

		  String qry = "SELECT * FROM bd_pacientes p ";
		  	qry+=  " WHERE p.CIP='"+CIP+"'";
	        qry+=  " AND p.idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
		  
		  Connection con = Conexion.conectar();
		  System.out.println(className + "--> getPacientePorCIP -->" +qry );		
		  ResultSet resultSet = null;
		  PacienteBean paciente= null;	
	    	 try {
	    		 PreparedStatement pstat = con.prepareStatement(qry);
	    		 resultSet = pstat.executeQuery();
	    		 if(resultSet.next()) {
	    			 paciente =creaPaciente(resultSet);
	    			
	    		 }
     } catch (SQLException e) {
         e.printStackTrace();
     }finally {con.close();}
	    
     return paciente;
 }

	public static   PacienteBean getPacientePorOID(String spdUsuario, String oidPaciente) throws Exception {
		
		  String qry = "SELECT * FROM bd_pacientes p ";
		  	qry+=  " WHERE  CAST( p.oidPaciente as CHAR)='"+oidPaciente+"'";
	        qry+=  " AND p.idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
		  
		  Connection con = Conexion.conectar();
		  System.out.println(className + "--> getPacientePorOID -->" +qry );		
		  ResultSet resultSet = null;
		  PacienteBean paciente= null;	
	    	 try {
	    		 PreparedStatement pstat = con.prepareStatement(qry);
	    		 resultSet = pstat.executeQuery();
	    		 if(resultSet.next()) {
	    			 paciente =creaPaciente(resultSet);
	    			
	    		 }
   } catch (SQLException e) {
       e.printStackTrace();
   }finally {con.close();}
	    
   return paciente;
}

	 public static List getEstadosResidente(String spdUsuario) throws Exception {
	        List<String> listaEstadosResidente = new ArrayList<>();
	        String qry = "SELECT DISTINCT activo FROM dbo.bd_pacientes p ";
	        	qry+=  " WHERE p.idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
	        final Connection con = Conexion.conectar();
	        System.out.println(String.valueOf(UsuarioDAO.className) + "--> getEstadosResidente -->" + qry);
	        ResultSet resultSet = null;
	        try {
	            final PreparedStatement pstat = con.prepareStatement(qry);
	            resultSet = pstat.executeQuery();
              
	            while (resultSet.next()) {
	            	listaEstadosResidente.add(resultSet.getString("activo"));
	            }
	        }
	        catch (SQLException e) {
	            e.printStackTrace();
	            log(e.toString(),Logger.ERROR);
	        }
	        finally {
	            if (resultSet != null) resultSet.close();
	            if (con != null) con.close();
	        }
	     
	        return listaEstadosResidente;
	    }

/*
	 public static List getEstatusResidente(String spdUsuario) throws Exception {
	        final List<String> listaEstatusResidente = new ArrayList<>();
	       // final String qry = "SELECT DISTINCT COALESCE(activo, 'SIN_ESTADO') + ' - ' +  COALESCE(estatus, 'SIN_ESTATUS') as actividad  FROM dbo.bd_pacientes  order by  actividad ";
	         String qry = "SELECT DISTINCT estatus as actividad  ";
	        			qry+= " FROM dbo.bd_pacientes  p ";
	        			qry+= " INNER JOIN bd_divisionResidencia d ON d.idDivisionResidencia = p.idDivisionResidencia ";
	        			qry+= " INNER JOIN bd_Residencia r ON d.idResidencia = r.idResidencia ";
	        			qry+= " WHERE r.status='activa' ";
	    	        	qry+= " AND p.idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
	        			
	        final Connection con = Conexion.conectar();
	        System.out.println(String.valueOf(UsuarioDAO.className) + "--> getEstatusResidente -->" + qry);
	        ResultSet resultSet = null;
	        try {
	            final PreparedStatement pstat = con.prepareStatement(qry);
	            resultSet = pstat.executeQuery();
           
	            while (resultSet.next()) {
	            	listaEstatusResidente.add(resultSet.getString("actividad"));
	            }
	        }
	        catch (SQLException e) {
	            e.printStackTrace();
	        }
	        finally {
	            if (resultSet != null) resultSet.close();
	            if (con != null) con.close();
	        }
	     
	        return listaEstatusResidente;
	    }
*/

	public static boolean nuevo(PacientesForm f) throws SQLException {
		
	       int result=0;
			  Connection con = Conexion.conectar();
			  String qry = "INSERT INTO bd_pacientes  ";
			  		qry+= " ( ";
			  		qry+= " 	CIP, idPacienteResidencia, nom, cognoms, cognomsNom, apellido1, apellido2, ";
			  		qry+= " 	nIdentidad, segSocial, planta, habitacion, ";
			  		qry+= " 	idDivisionResidencia, spd, fechaProceso, exitus, ";
			  		qry+= " 	estatus,  bolquers,  comentarios, fechaAltaPaciente, mutua ";
			  		qry+= " 	";			  		
			  		qry+= " ) VALUES ( ";
		  	   		qry+= " '"+f.getCIP()+"', '"+f.getIdPacienteResidencia()+"', '"+f.getNombre()+"', '"+f.getApellido1() + " " + f.getApellido2() +"', '"+f.getApellidosNombre()+"',  '"+f.getApellido1()+"', '"+f.getApellido2()+"', ";
		  	   		qry+= " '"+f.getNumIdentidad()+"', '"+f.getSegSocial()+"', '"+f.getPlanta()+"', '"+f.getHabitacion()+"', ";
		  	   		qry+= " (select idDivisionResidencia from bd_divisionResidencia where oidDivisionResidencia= '"+f.getOidDivisionResidencia()+"'), '"+f.getSpd()+"', CONVERT(datetime, getdate(), 120),  '"+f.getExitus()+"', ";
		  	   		qry+= " '"+f.getEstatus()+"', '"+f.getBolquers()+"', '"+f.getComentarios()+"', '"+f.getFechaAltaPaciente()+"',  '"+f.getMutua()+"' ";
		  	   		qry+= "";
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
	

	public static boolean edita(String query) throws SQLException {
		int result=0;
		Connection con = Conexion.conectar();
		System.out.println(className + "  - edita-->  " +query );		
		try {
			PreparedStatement pstat = con.prepareStatement(query);
		    result=pstat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {con.close();}
			return result>0;
	}


	public static List<BolquersDetalleBean> getBolquersPaciente(String idUsuario, String oidPaciente) throws SQLException {
		
		List<BolquersDetalleBean> bolquers = new ArrayList<>();
	
		//consulta del report BIRT de pañales detalle
        String sql = " select v.IDDIVISIONRESIDENCIA, PROCESOTRACTAMENT,  v.CIP, COGNOMS_NOM, v.PLANTA, v.HABITACIO, CN, NOM_MEDICAMENT, LAB, PVL, PVP ";
        	sql+= " , PRINCIPI_ACTIU, PA_PRES, GRUP_TERAPEUTIC, PRESENTACIO, UNITATS, PACKS_ENVAS, CODI_CONJUNT_HOMOG, NOM_CONJUNT_HOMOG ";
        	sql+= " , CODI_FORMA_FARMACEUTICA, NOM_FORMA_FARMACEUTICA, TRACTAMENT_INICI, TRACTAMENT_FI, TRACT_QUANTITAT, TRACT_PAUTA, TRACT_CRONIC ";
        	sql+= " , TRACT_DOSI_ESMORZAR, TRACT_DOSI_DINAR, TRACT_DOSI_BERENAR, TRACT_DOSI_SOPAR, TRACT_DOSI_RESSOPO, CODI_SUST, NOM_MEDICAMENT_SUST ";
        	sql+= " , NOM_CURT_SUST, LAB_SUST, PVL_SUST, PVP_SUST, UNITATS_SUST, PACKS_ENVAS_SUST, PRIORITAT, TIPO_SPD, CODI_FORMA_FARMACEUTICA_SUST";
        	sql+= " , NOM_FORMA_FARMACEUTICA_SUST, CODIGO_SIGUIENTE_RECETA, v.SPD, v.SERVIR_PAÑALES, v.EXITUS ";
        	sql+= " from SPDAC.dbo.v_previsionReceta v  left join bd_pacientes p on v.CIP = p.CIP ";
        	sql+= " WHERE 1=1 ";
        	sql+= " and ( v.GRUP_TERAPEUTIC  like 'O02%' OR v.GRUP_TERAPEUTIC  like '23%')  ";
           	sql+= " and CAST( p.oidPaciente as CHAR) like '"+oidPaciente+"'";
           	sql+= " order by v.PROCESOTRACTAMENT, v.NOM_MEDICAMENT ";
          	
	        final Connection con = Conexion.conectar();
	        System.out.println(String.valueOf(UsuarioDAO.className) + "--> getBolquersPaciente -->" + sql);
	        ResultSet resultSet = null;
	        try {
	            final PreparedStatement pstat = con.prepareStatement(sql);
	            resultSet = pstat.executeQuery();
           
	            while (resultSet.next()) {
	            	BolquersDetalleBean bolquer = new BolquersDetalleBean();
	            	bolquer.setIdDivisionResidencia(resultSet.getString("IDDIVISIONRESIDENCIA"));
	            	bolquer.setFechaProceso(resultSet.getDate("PROCESOTRACTAMENT"));
	            	bolquer.setCIP(resultSet.getString("CIP"));
	            	bolquer.setServirBolquers(resultSet.getString("SERVIR_PAÑALES"));
	            	bolquer.setApellidosNombre(resultSet.getString("COGNOMS_NOM"));
	            	bolquer.setCn(resultSet.getString("CN"));
	            	bolquer.setMedicamento(resultSet.getString("NOM_MEDICAMENT"));
	            	bolquer.setLab(resultSet.getString("LAB"));
	            	bolquer.setPvp(resultSet.getString("PVP"));
	            	bolquer.setGrupoTerapeutico(resultSet.getString("GRUP_TERAPEUTIC"));
	            	bolquer.setPresentacion(resultSet.getString("PRESENTACIO"));
	            	bolquer.setFinTratamiento(resultSet.getString("TRACTAMENT_FI"));
	            	bolquer.setCantidad(resultSet.getString("TRACT_QUANTITAT"));
	            	bolquer.setPauta(resultSet.getString("TRACT_PAUTA"));
	            	
	            	bolquers.add(bolquer);
	            }
	        }
	        catch (SQLException e) {
	            e.printStackTrace();
	        }
	        finally {
	            if (resultSet != null) resultSet.close();
	            if (con != null) con.close();
	        }
			
	        return bolquers;
		}


	public static boolean actualizaPlantaHabitacion(String idUsuario, FicheroResiBean medResi) throws SQLException {
		boolean result=false;
		String query = " UPDATE SPDAC.dbo.bd_pacientes SET planta='" + medResi.getResiPlanta() + "', habitacion='" + medResi.getResiHabitacion() + "' WHERE  CIP='"+medResi.getResiCIP()+"'";
		result=edita(query);
		
		return result;
	}


	public static List<DiscrepanciaBean> getDiscrepanciasPorCIP(String idUsuario, String CIP, int diasCalculo) throws SQLException {
		if(diasCalculo<1 || diasCalculo>31) diasCalculo=14; 
		  String qry = " SELECT DISTINCT  COALESCE(ISNULL(mediResi.IDDIVISIONRESIDENCIA, p.IDDIVISIONRESIDENCIA),  receta.IDDIVISIONRESIDENCIA) as idDivisionResidencia ";
		  qry+=  "  , COALESCE(ISNULL(mediResi.resiCIP, p.CIP), receta.CIP) AS CIP  ";
		  qry+=  "  , COALESCE((p.cognoms + ' ' + p.nom), receta.cognoms_nom) AS nombreResidente ";
		  qry+=  "  , ISNULL(mediResi.resiCn, '') AS resiCn ";
		  qry+=  "  , ISNULL(mediResi.resiMedicamento, '') AS resiMedicamento ";
		  qry+=  "  , ISNULL(mediResi.spdCnFinal, '') AS spdCnSust ";
		  qry+=  "  , ISNULL(mediResi.spdNombreBolsa, '') AS spdNombreBolsa ";
		  qry+=  "  , ISNULL(mediResi.spdFormaMedicacion, '') AS spdFormaMedicacion ";
		  qry+=  "  , ISNULL(mediResi.spdAccionBolsa, '') AS spdAccionBolsa ";
		  qry+=  "  , ISNULL(mediResi.resiSiPrecisa, '') AS resiSiPrecisa ";
		  qry+=  "  , ISNULL(mediResi.spdComprimidosDia, 0) AS spdPautaDia ";

		  qry+=  "   , CASE mediResi.spdCnFinal ";
		  qry+=  "     		WHEN '111111' THEN ISNULL(mediResi.spdComprimidosDia, 0)*" + diasCalculo/2  ;  // medias trazodonas
		  qry+=  "     		ELSE ISNULL(mediResi.spdComprimidosDia, 0)* " + diasCalculo;
		  qry+=  "    END  AS spdPrevision";
 
		  qry+=  "  , COALESCE(ISNULL(mediResi.spdNomGtVmp, receta.nomGTVMP) , '') as nomGTVMP ";
		  qry+=  "  , receta.CN AS recetaCN ";
		  qry+=  "  , receta.NOM_MEDICAMENT AS recetaMedicamento ";
		  qry+=  "  , ISNULL(receta.TRACT_QUANTITAT, '') AS recetaCantidad ";
		  qry+=  "  , ISNULL(receta.TRACT_PAUTA, '') AS recetaPauta ";
		  qry+=  "  , receta.ponderacion_pauta_por_dia  AS recetaPautaPorDia ";
		  qry+=  "  , receta.ponderacion_pauta_por_dia *" + diasCalculo+ " AS recetaPrevision ";
		  qry+=  "  , ISNULL(receta.TRACTAMENT_INICI, '') AS recetaInicioTratamiento ";
		  qry+=  "  , ISNULL(receta.TRACTAMENT_FI, '') AS recetaFinTratamiento ";
		  qry+=  "  , ISNULL(receta.CADUCIDAD_SIGUIENTE_RECETA, '') AS caducidadSiguienteReceta ";
		  qry+=  "  , receta.TOTAL_RECETAS_DISPENSADAS AS totalRecetasDispensadas ";
		  qry+=  "  , receta.TOTAL_RECETAS_TRATAMIENTO AS totalRecetasTratamiento ";
		  qry+=  "  , receta.TOTAL_RECETAS_DISPONIBLES AS totalRecetasDisponibles ";

		  qry+=  " FROM bd_pacientes p  ";
		  qry+=  " INNER JOIN SPD_resiMedicacion mediResi ON mediResi.resiCIP = p.CIP ";
		  qry+=  " FULL OUTER JOIN  v_previsionReceta  receta  ";
		  qry+=  "     ON	(";
		  qry+=  " 			receta.CIP=mediResi.resiCIP  ";
		  qry+=  "			AND mediResi.spdNomGtVm =receta.NOMGTVM "; 
		  qry+=  "         	AND (mediResi.spdCodGtVmp= receta.codGTVMP OR receta.CN=LEFT(mediResi.spdCnFinal,6)) ";
		  qry+=  "         ) ";

		  qry+=  " WHERE 1=1  ";
		  qry+=  " AND (p.ACTIVO='ACTIVO' or receta.ACTIVO='ACTIVO') ";
		  qry+=  " AND (COALESCE(mediResi.resiCIP, '')  ='" + CIP +"'   OR  COALESCE(receta.CIP, '') ='" + CIP +"'   ) ";
		
		  qry+=  " ORDER BY COALESCE(ISNULL(mediResi.spdNomGtVmp, receta.nomGTVMP) , '') ";
		  qry+=  " ";
	       // qry+=  " AND p.idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")"; ";
		  
		  List<DiscrepanciaBean> listaDiscrepancias= new ArrayList<DiscrepanciaBean>();
		  
		  Connection con = Conexion.conectar();
		  System.out.println(className + "--> getPacientePorCIP -->" +qry );		
		  ResultSet resultSet = null;
		  DiscrepanciaBean discrepancia= null;	
	    	 try {
	    		 PreparedStatement pstat = con.prepareStatement(qry);
	    		 resultSet = pstat.executeQuery();
	    		 while(resultSet.next()) {
	    			 discrepancia =creaDiscrepancia(resultSet);
	    			 listaDiscrepancias.add(discrepancia);
	    		 }
			   } catch (SQLException e) {
			       e.printStackTrace();
			   }finally {con.close();}
				    
		return listaDiscrepancias;
	}


	private static DiscrepanciaBean creaDiscrepancia(ResultSet resultSet) throws SQLException {
		DiscrepanciaBean d = new DiscrepanciaBean();
		d.setIdDivisionResidencia(resultSet.getString("idDivisionResidencia"));
		d.setCIP(resultSet.getString("CIP"));
		d.setNombrePaciente(resultSet.getString("nombreResidente"));
		d.setResiCn(resultSet.getString("resiCn"));
		d.setResiMedicamento(resultSet.getString("resiMedicamento"));
		d.setSpdNombreBolsa(resultSet.getString("spdNombreBolsa"));
		d.setSpdCnSust(resultSet.getString("spdCnSust"));
		d.setSpdNombreBolsa(resultSet.getString("spdNombreBolsa"));
		d.setSpdFormaMedicacion(resultSet.getString("spdFormaMedicacion"));
		d.setSpdAccionBolsa(resultSet.getString("spdAccionBolsa"));
		d.setSiPrecisa(resultSet.getString("resiSiPrecisa"));
		d.setSpdComprimidosDia(resultSet.getFloat("spdPautaDia"));
		d.setSpdPrevision(resultSet.getFloat("spdPrevision"));
		d.setNomGtVmp(resultSet.getString("nomGTVMP"));
		d.setRecetaCn(resultSet.getString("recetaCN"));
		d.setRecetaMedicamento(resultSet.getString("recetaMedicamento"));
		d.setRecetaCantidad(resultSet.getString("recetaCantidad"));
		d.setRecetaPauta(resultSet.getString("recetaPauta"));
		d.setRecetaComprimidosDia(resultSet.getFloat("recetaPautaPorDia"));
		d.setRecetaPrevision(resultSet.getFloat("recetaPrevision"));

		d.setRecetaInicioTratamiento(resultSet.getString("recetaInicioTratamiento"));
		d.setRecetaFinTratamiento(resultSet.getString("recetaFinTratamiento"));
		d.setRecetaCaducidad(resultSet.getString("caducidadSiguienteReceta"));
		d.setTotalRecetasDispensadas(resultSet.getInt("totalRecetasDispensadas"));
		d.setTotalRecetasTratamiento(resultSet.getInt("totalRecetasTratamiento"));
		d.setTotalRecetasDisponibles(resultSet.getInt("totalRecetasDisponibles"));
		return d;
	}


	public static List<TratamientoRctBean> getTratamientoRctPorCIP(String idUsuario, String CIP) throws SQLException {
		  String qry = " SELECT DISTINCT  COALESCE( p.IDDIVISIONRESIDENCIA,  receta.IDDIVISIONRESIDENCIA) as idDivisionResidencia ";
		  qry+=  "  , COALESCE( p.CIP, receta.CIP) AS CIP  ";
		  qry+=  "  , COALESCE((p.cognoms + ' ' + p.nom), receta.cognoms_nom) AS nombreResidente ";
		  qry+=  "  , receta.nomGTVMP as nomGTVMP ";
		  qry+=  "  , receta.CN AS recetaCN ";
		  qry+=  "  , receta.NOM_MEDICAMENT AS recetaMedicamento ";
		  qry+=  "  , ISNULL(receta.TRACT_QUANTITAT, '') AS recetaCantidad ";
		  qry+=  "  , ISNULL(receta.TRACT_PAUTA, '') AS recetaPauta ";
		  qry+=  "  , receta.ponderacion_pauta_por_dia  AS recetaPautaPorDia ";
		  qry+=  "  , ISNULL(receta.TRACTAMENT_INICI, '') AS recetaInicioTratamiento ";
		  qry+=  "  , ISNULL(receta.TRACTAMENT_FI, '') AS recetaFinTratamiento ";
		  qry+=  "  , ISNULL(receta.CADUCIDAD_SIGUIENTE_RECETA, '') AS caducidadSiguienteReceta ";
		  qry+=  "  , receta.TOTAL_RECETAS_DISPENSADAS AS totalRecetasDispensadas ";
		  qry+=  "  , receta.TOTAL_RECETAS_TRATAMIENTO AS totalRecetasTratamiento ";
		  qry+=  "  , receta.TOTAL_RECETAS_DISPONIBLES AS totalRecetasDisponibles ";

		  qry+=  " FROM bd_pacientes p  ";
		  qry+=  " INNER JOIN  v_previsionReceta  receta ON receta.CIP=p.CIP ";
		  qry+=  " WHERE 1=1  ";
		  qry+=  " AND (p.ACTIVO='ACTIVO' or receta.ACTIVO='ACTIVO') ";
		  qry+=  " AND COALESCE(receta.CIP, '') ='" + CIP +"'  ";
		
		  qry+=  " ORDER BY receta.nomGTVMP ";
		  qry+=  " ";
		  
		  List<TratamientoRctBean> listaTratamiento= new ArrayList<TratamientoRctBean>();
		  
		  Connection con = Conexion.conectar();
		  System.out.println(className + "--> getPacientePorCIP -->" +qry );		
		  ResultSet resultSet = null;
		  TratamientoRctBean tratamiento= null;	
	    	 try {
	    		 PreparedStatement pstat = con.prepareStatement(qry);
	    		 resultSet = pstat.executeQuery();
	    		 while(resultSet.next()) {
	    			 tratamiento =creaTratamientoRct(resultSet);
	    			 listaTratamiento.add(tratamiento);
	    		 }
			   } catch (SQLException e) {
			       e.printStackTrace();
			   }finally {con.close();}
				    
		return listaTratamiento;
	}


	private static TratamientoRctBean creaTratamientoRct(ResultSet resultSet) throws SQLException {
		TratamientoRctBean d = new TratamientoRctBean();
		d.setIdDivisionResidencia(resultSet.getString("idDivisionResidencia"));
		d.setCIP(resultSet.getString("CIP"));
		d.setNombrePaciente(resultSet.getString("nombreResidente"));
		d.setNomGtVmp(resultSet.getString("nomGTVMP"));
		d.setRecetaCn(resultSet.getString("recetaCN"));
		d.setRecetaMedicamento(resultSet.getString("recetaMedicamento"));
		d.setRecetaCantidad(resultSet.getString("recetaCantidad"));
		d.setRecetaPauta(resultSet.getString("recetaPauta"));

		d.setRecetaInicioTratamiento(resultSet.getString("recetaInicioTratamiento"));
		d.setRecetaFinTratamiento(resultSet.getString("recetaFinTratamiento"));
		d.setRecetaCaducidad(resultSet.getString("caducidadSiguienteReceta"));
		d.setTotalRecetasDispensadas(resultSet.getInt("totalRecetasDispensadas"));
		d.setTotalRecetasTratamiento(resultSet.getInt("totalRecetasTratamiento"));
		d.setTotalRecetasDisponibles(resultSet.getInt("totalRecetasDisponibles"));
		return d;
	}


	public static List<FicheroResiBean> getTratamientoSPDPorCIP(String idUsuario, String cip) {
		// TODO Esbozo de método generado automáticamente
		return null;
	}


	

		/*
	 public static List getEstadosSpd(String spdUsuario) throws Exception {
	        List listaEstadosSPD = new ArrayList();
	        String qry = "SELECT DISTINCT p.spd FROM dbo.bd_pacientes p ";
	        qry+=  " WHERE p.idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
	        
	        final Connection con = Conexion.conectar();
	        System.out.println(String.valueOf(UsuarioDAO.className) + "--> getEstadosSpd -->" + qry);
	        ResultSet resultSet = null;
	        try {
	            final PreparedStatement pstat = con.prepareStatement(qry);
	            resultSet = pstat.executeQuery();
                 
	            while (resultSet.next()) {
	            	listaEstadosSPD.add(resultSet.getString("spd"));
	            }
	        }
	        catch (SQLException e) {
	            e.printStackTrace();
	        }
	        finally {
	            if (resultSet != null) resultSet.close();
	            if (con != null) con.close();
	        }
	        con.close();
	        return listaEstadosSPD;
	    }
	 */ 
	 
}
 
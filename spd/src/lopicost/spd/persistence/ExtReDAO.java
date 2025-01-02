package lopicost.spd.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import lopicost.spd.model.Enlace;
import lopicost.spd.model.GestSustitucionesLite;
import lopicost.spd.model.Paciente;
import lopicost.config.pool.dbaccess.Conexion;
import java.util.ArrayList;
import java.util.List;
import lopicost.spd.model.Usuario;
import lopicost.spd.security.helper.VisibilidadHelper;
import lopicost.spd.struts.bean.ExtReBean;
import lopicost.spd.struts.bean.PacienteBean;
import lopicost.spd.struts.form.EnlacesForm;
import lopicost.spd.utils.SPDConstants;

public class ExtReDAO
{
	private final static String className = "ExtReDAO";
	private final String cLOGGERHEADER_ERROR = className + "ERROR: : ";


    
    public static List getDatosProcesoCaptacion(String spdUsuario) throws Exception {
    	/*
    	 * 
  		String qry = "SELECT max(CONVERT(varchar, c.fechaHoraProceso, 103)) as fechaUltimoProceso "; 
	    	qry+= " , COALESCE(c.idDivisionResidencia , p.idDivisionResidencia) AS idDivisionResidencia ";
	    	qry+= " , di.nombreDivisionResidencia  ";
	    	qry+= " , count(*) AS tratamientosProcesadosResi, count(distinct c.CIP) AS cipsProcesados, cipsActivos";
	    	qry+= " FROM ctl_consultaTractamentSIRE c ";
	    	qry+= " INNER JOIN bd_pacientes p ON c.CIP=p.CIP  ";
	    	qry+= " INNER JOIN bd_divisionResidencia di ON di.idDivisionResidencia=COALESCE(c.idDivisionResidencia , p.idDivisionResidencia)  ";
	    	qry+= " LEFT JOIN  ";
			qry+= " ( ";
			qry+= "    select iddivisionresidencia, count(*) as cipsActivos ";
			qry+= "       from bd_pacientes where activo='ACTIVO' and UPPER(CIP) not like 'MMTT%' "; //residentes de mutua
			qry+= "      group by iddivisionresidencia ";
			qry+= "  ) dat on dat.iddivisionresidencia=COALESCE(c.idDivisionResidencia , p.idDivisionResidencia) ";
			qry+= " WHERE di.oidDivisionResidencia IN ( " + VisibilidadHelper.oidDivisionResidenciasVisibles(spdUsuario)  + ")";
			qry+= " GROUP BY COALESCE(c.idDivisionResidencia , p.idDivisionResidencia), di.nombreDivisionResidencia, cipsActivos ";
			qry+= " ORDER BY max(CONVERT(varchar, c.fechaHoraProceso, 103)) ";
			*/
    	String qry = " SELECT di.idDivisionResidencia AS idDivisionResidencia  ";
    	qry+= " , di.nombreDivisionResidencia ";
    	qry+= " , COUNT(*) AS tratamientosProcesadosResi ";
    	qry+= " , cipsActivos  ";
    	qry+= " , max(CONVERT(varchar, c.fechaHoraProceso, 103)) AS fechaUltimoProcesoTrat  ";
    	qry+= " , COUNT(distinct c.CIP) AS cipsProcesadosTrat ";
    	qry+= " , COALESCE(recTratNo.cipsRecTratProcesados, 0) AS cipsProcesadosTratNo ";
    	qry+= " , max(CONVERT(varchar, recPend.fechaHoraProceso, 103))  AS fechaUltimoProcesoRedPend   ";
    	qry+= " , COALESCE(recPend.cipsRecPendProcesados, 0) AS cipsProcesadosRecPend   ";
    	qry+= " , COALESCE(recPendNo.cipsRecPendProcesados, 0) AS cipsProcesadosRecPendNo ";
    	qry+= " FROM ctl_consultaTractamentSIRE c   ";
    	qry+= " INNER JOIN bd_pacientes p ON c.CIP=p.CIP    ";
    	qry+= " INNER JOIN bd_divisionResidencia di ON di.idDivisionResidencia= p.idDivisionResidencia    ";
    	qry+= " INNER JOIN bd_residencia r ON di.idResidencia=r.idResidencia  ";
    	qry+= " LEFT JOIN ";   
    	qry+= " (      ";
    	qry+= "       SELECT iddivisionresidencia, count(*) AS cipsActivos         ";
    	qry+= "       FROM bd_pacientes "; // AND UPPER(CIP) NOT LIKE 'MMTT%'        ";
    	qry+= "       WHERE activo='ACTIVO' ";
    	qry+= "       AND (mutua IS NULL OR mutua <>'S')    ";
    	qry+= "       GROUP BY iddivisionresidencia    ";
    	qry+= " ) dat ON dat.iddivisionresidencia=p.idDivisionResidencia   ";
    	qry+= " LEFT JOIN  ";
    	qry+= " ( ";
    	qry+= "      SELECT p.idDivisionResidencia ";
    	qry+= " 	 , COUNT(distinct d.CIP) AS cipsRecPendProcesados ";
    	qry+= " 	 , MAX(d.fechaHoraProceso) AS fechaHoraProceso ";
    	qry+= "      FROM ctl_consultaPendentDispensarSIRE d  ";
    	qry+= "      INNER JOIN bd_pacientes p ON d.CIP=p.CIP    ";
    	qry+= "      INNER JOIN bd_divisionResidencia di ON di.idDivisionResidencia=p.idDivisionResidencia ";
    	qry+= " 	 WHERE p.activo='ACTIVO' ";
    	qry+= "      AND (p.mutua IS NULL OR p.mutua <>'S')    ";
    	qry+= "      GROUP BY p.idDivisionResidencia ";
    	qry+= " ) recPend ON recPend.idDivisionResidencia=di.idDivisionResidencia ";

    	qry+= " LEFT JOIN    ";
    	qry+= " (        ";
    	qry+= " 	SELECT p.idDivisionResidencia  	  ";
    	qry+= " 	 , COUNT(distinct p.CIP) AS cipsRecPendProcesados  	  ";
    	qry+= " 	, MAX(d.fechaHoraProceso) AS fechaHoraProceso        ";
    	qry+= " 	FROM ctl_consultaPendentDispensarSIRE d         ";
    	qry+= " 	INNER JOIN bd_pacientes p ON d.CIP=p.CIP           ";
    	qry+= " 	INNER JOIN bd_divisionResidencia di ON di.idDivisionResidencia=p.idDivisionResidencia  	  ";
    	qry+= " 	WHERE p.activo='ACTIVO'                ";
    	qry+= "     AND (p.mutua IS NULL OR p.mutua <>'S')    ";
    	//qry+= " 	AND p_COD_CPF in ('------', '000000')    ";
    	qry+= " 	AND ( p_COD_CPF  is null  OR p_COD_CPF in ('', '------', '000000'))     ";
		qry+= " 	AND NOT EXISTS ( ";
		qry+= "  		select '1' from  ctl_consultaPendentDispensarSIRE c2 where d.CIP=c2.CIP and c2.p_COD_CPF is not null and  c2.p_COD_CPF not in ('------', '000000')"; 
		qry+= "  )  ";
    	
    	qry+= " 	GROUP BY p.idDivisionResidencia   ";
    	qry+= " ) recPendNo ON recPendNo.idDivisionResidencia=di.idDivisionResidencia   ";
    	qry+= " LEFT JOIN  ";
    	qry+= " ( ";
    	qry+= " 	SELECT p.idDivisionResidencia  	  ";
    	qry+= " 	, COUNT(distinct p.CIP) AS cipsRecTratProcesados  "; 	 
    	qry+= " 	FROM ctl_consultaTractamentSIRE  d ";
    	qry+= " 	INNER JOIN bd_pacientes p ON d.CIP=p.CIP           ";    	
    	qry+= " 	INNER JOIN bd_divisionResidencia di ON di.idDivisionResidencia=p.idDivisionResidencia  	  ";
    	qry+= " 	WHERE p.activo='ACTIVO'                ";
    	qry+= "     AND (p.mutua IS NULL OR p.mutua <>'S')    ";
    	//qry+= " 	AND CODIGO in ('------', '000000')    ";
    	qry+= " 	AND ( CODIGO is null  OR CODIGO in ('', '------', '000000'))   ";
		qry+= " 	AND NOT EXISTS ( ";
		qry+= "  		select '1' from  ctl_consultaTractamentSIRE c2 where d.CIP=c2.CIP and c2.CODIGO is not null and  c2.CODIGO not in ('------', '000000')"; 
		qry+= "  	)  ";
    	qry+= " 	GROUP BY p.idDivisionResidencia  ";
    	qry+= " ) recTratNo ON recTratNo.idDivisionResidencia=di.idDivisionResidencia   ";
     	
    	qry+= " WHERE 1=1 ";
    	qry+= " AND p.activo='ACTIVO' ";
    	qry+= " AND (p.mutua IS NULL OR p.mutua <>'S')    ";
		qry+= " AND r.status='activa' "; 
		qry+= " AND di.oidDivisionResidencia IN ( " + VisibilidadHelper.oidDivisionResidenciasVisibles(spdUsuario)  + ") ";
		qry+= " GROUP BY di.idDivisionResidencia ";
    	qry+= " , di.nombreDivisionResidencia ";
    	qry+= " , cipsActivos ";
    	qry+= " , recTratNo.cipsRecTratProcesados ";
    	qry+= " , recPend.cipsRecPendProcesados ";
    	qry+= " , recPendNo.cipsRecPendProcesados ";
    	qry+= " , recPend.fechaHoraProceso  ";
    	qry+= " ORDER BY MAX(CONVERT(varchar, c.fechaHoraProceso, 103)) ";
	  	    System.out.println(className + "  - getDatosProcesoCaptacion -->  " +qry );		
	  	 	Connection con = Conexion.conectar();

		       ResultSet resultSet = null;
		 		List<ExtReBean> listaBeans= new ArrayList<ExtReBean>();
		       try {
		            PreparedStatement pstat = con.prepareStatement(qry);
		            resultSet = pstat.executeQuery();

		            while (resultSet.next()) {
		            	String idDivisionResidencia=resultSet.getString("idDivisionResidencia");
		            	String nombreDivisionResidencia=resultSet.getString("nombreDivisionResidencia");
		            	int cipsActivos=resultSet.getInt("cipsActivos");
		            	int cipsProcesadosRecPend=resultSet.getInt("cipsProcesadosRecPend");
		            	int cipsProcesadosRecPendNo=resultSet.getInt("cipsProcesadosRecPendNo");
		            	int cipsProcesadosTrat=resultSet.getInt("cipsProcesadosTrat");
		            	int cipsProcesadosTratNo=resultSet.getInt("cipsProcesadosTratNo");
		            	int tratamientosProcesadosResi=resultSet.getInt("tratamientosProcesadosResi");
		            	String fechaUltimoProcesoRedPend=resultSet.getString("fechaUltimoProcesoRedPend");
		            	String fechaUltimoProcesoTrat=resultSet.getString("fechaUltimoProcesoTrat");
		            	ExtReBean  c =new ExtReBean(idDivisionResidencia, nombreDivisionResidencia, tratamientosProcesadosResi, cipsActivos
		            			, fechaUltimoProcesoTrat, cipsProcesadosTrat, cipsProcesadosTratNo
		            			, fechaUltimoProcesoRedPend, cipsProcesadosRecPend, cipsProcesadosRecPendNo, null);

		            	//if(c!=null 	&& (c.isErrorFechaRecogidaTrat() || c.isErrorDatosProcesadosTrat() || c.isErrorFechaRecogidaRecPend() || c.isErrorDatosProcesadosRecPend()))
		            	if(c!=null)
		            		listaBeans.add(c);
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		 
		        return listaBeans;

    }
  
    public static List getCipsSinProcesarTrat(String spdUsuario, String idDivisionResidencia) throws Exception {
    	 
  		String qry= " SELECT distinct C.CODIGO, C.DESCRIPCION as MENSAJE, b.iddivisionresidencia, b.CIP, b.cognomsnom, b.segSocial, b.spd, b.exitus, b.estatus, b.bolquers,b.comentarios, b.ACTIVO "; 
	    	qry+= " FROM bd_pacientes b ";
	    	qry+= " INNER JOIN bd_divisionResidencia di ON di.idDivisionResidencia=b.idDivisionResidencia  ";
	    	qry+= " INNER JOIN bd_residencia r ON di.idResidencia=r.idResidencia  ";
	    	qry+= " LEFT JOIN  ";
	    	qry+= " ( ";
	    	qry+= " 	SELECT MAX(CONVERT(varchar, fechaHoraProceso, 103)) AS fechaUltimoProceso, idDivisionResidencia ";
	    	qry+= " 	FROM ctl_consultaTractamentSIRE  ";
	    	qry+= " 	GROUP BY idDivisionResidencia  ";
	    	qry+= " ) dat ON dat.iddivisionresidencia=b.iddivisionresidencia   ";
			qry+= " LEFT JOIN  ctl_consultaTractamentSIRE c  ON ";
			qry+= " (  ";
			qry+= " 	b.CIP=c.CIP  ";
			qry+= " 	AND b.idDivisionResidencia=c.idDivisionResidencia  ";
			qry+= " 	AND fechaUltimoProceso=dat.fechaUltimoProceso ";
			qry+= " )  ";
			qry+= " WHERE 1=1 ";
			qry+= " AND b.activo='ACTIVO'  "; 
	    	qry+= " AND (b.mutua IS NULL OR b.mutua <>'S')    ";
			qry+= " AND r.status='activa' "; 
			qry+= " AND di.oidDivisionResidencia IN ( " + VisibilidadHelper.oidDivisionResidenciasVisibles(spdUsuario)  + ") ";
			qry+= " AND b.idDivisionResidencia='"+idDivisionResidencia+"' ";
			qry+= "  AND (c.CIP is null  or c.CODIGO is null  OR c.CODIGO in ('------', '000000')  ) ";
			qry+= " AND NOT EXISTS ( ";
			qry+= "  		select '1' from  ctl_consultaTractamentSIRE c2 where c.CIP=c2.CIP and c2.CODIGO is not null and  c2.CODIGO not in ('------', '000000')"; 
			qry+= "  )  ";
			
	  	    System.out.println(className + "  - getCipsSinProcesarTrat -->  " +qry );		
	  	 	Connection con = Conexion.conectar();

		       ResultSet resultSet = null;
		 		List<PacienteBean> listaPacientes= new ArrayList<PacienteBean>();
		       try {
		            PreparedStatement pstat = con.prepareStatement(qry);
		            resultSet = pstat.executeQuery();

		            while (resultSet.next()) {
		            	PacienteBean p = new PacienteBean();
		            	p.setIdDivisionResidencia(resultSet.getString("idDivisionResidencia"));
		            	p.setCIP(resultSet.getString("CIP"));
		            	p.setApellidosNombre(resultSet.getString("cognomsnom"));
		            	p.setSegSocial(resultSet.getString("segSocial"));
		            	p.setSpd(resultSet.getString("spd"));
		            	p.setExitus(resultSet.getInt("exitus"));
		            	p.setEstatus(resultSet.getString("estatus"));
		            	p.setBolquers(resultSet.getString("bolquers"));
		            	p.setComentarios(resultSet.getString("comentarios"));
		            	p.setActivo(resultSet.getString("ACTIVO"));
		            	String codigo =  resultSet.getString("CODIGO");
		            	String mensaje = resultSet.getString("MENSAJE");
		            	String claseId ="";
		            	if( codigo!=null && codigo.equalsIgnoreCase("------"))
		            	
		            	{
		            		claseId = "no_procesado";
		            		if(mensaje==null || mensaje.equals(""))
		            		{
		            			mensaje="CIP no procesado: " + "("+  codigo +")";
		            		}
		            	}
		            	else if(codigo!=null && codigo.equalsIgnoreCase("000000"))
		            	{
		            		claseId = "con_mensaje";
		            		if(mensaje==null || mensaje.equals(""))
		            		{
		            			mensaje="Procesado pero sin datos, revisión manual";
		            		}
		            	}
		            	if(codigo==null || codigo.equals("") || codigo.equalsIgnoreCase("NULL"))
			            {
			            	claseId = "no_procesado";
			            	if(mensaje==null || mensaje.equals(""))
			            	{
			            		mensaje="CIP no procesado.";
			            	}
			            }
	  		            p.setMensajeTratamiento(mensaje);
	  		            p.setClaseId(claseId);
		            	listaPacientes.add(p);
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		        return listaPacientes;
    }
		
    
    public static List getCipsSinProcesarPendientes(String spdUsuario, String idDivisionResidencia) throws Exception {
   	 
  		String qry= " SELECT distinct c.p_COD_CPF as CODIGO, c.p_DES_CPF as MENSAJE, b.iddivisionresidencia, b.CIP, b.cognomsnom, b.segSocial, b.spd, b.exitus, b.estatus, b.bolquers,b.comentarios, b.ACTIVO "; 
	    	qry+= " FROM bd_pacientes b ";
	    	qry+= " INNER JOIN bd_divisionResidencia di ON di.idDivisionResidencia=b.idDivisionResidencia  ";
	    	qry+= " INNER JOIN bd_residencia r ON di.idResidencia=r.idResidencia  ";
	    	qry+= " LEFT JOIN  ";
	    	qry+= " ( ";
	    	qry+= " 	SELECT MAX(CONVERT(varchar, c2.fechaHoraProceso, 103)) AS fechaUltimoProceso, b2.idDivisionResidencia  	 ";
	    	qry+= " 	FROM ctl_consultaPendentDispensarSIRE c2   inner join bd_pacientes b2 on c2.CIP=b2.CIP    	";
	    	qry+= " 	GROUP BY b2.idDivisionResidencia  ";
	    	qry+= " ) dat ON dat.iddivisionresidencia=b.iddivisionresidencia   ";
			qry+= " LEFT JOIN  ctl_consultaPendentDispensarSIRE c  ON ";
			qry+= " (  ";
			qry+= " 	b.CIP=c.CIP  ";
			qry+= " 	AND fechaUltimoProceso=dat.fechaUltimoProceso ";
			qry+= " )  ";
			qry+= " WHERE 1=1 ";
			qry+= " AND b.activo='ACTIVO'  "; 
	    	qry+= " AND (b.mutua IS NULL OR b.mutua <>'S')    ";
			qry+= " AND r.status='activa' "; 
			qry+= " AND di.oidDivisionResidencia IN ( " + VisibilidadHelper.oidDivisionResidenciasVisibles(spdUsuario)  + ") ";
			qry+= " AND b.idDivisionResidencia='"+idDivisionResidencia+"' ";
			qry+= " AND (c.CIP is null  or p_COD_CPF is null  OR c.p_COD_CPF in ('------', '000000')  ) ";
			qry+= " AND NOT EXISTS ( ";
			qry+= "  		select '1' from  ctl_consultaPendentDispensarSIRE c2 where c.CIP=c2.CIP and c2.p_COD_CPF is not null and  c2.p_COD_CPF not in ('------', '000000')"; 
			qry+= "  )  ";
			
	  	    System.out.println(className + "  - getCipsSinProcesarPendientes -->  " +qry );		
	  	 	Connection con = Conexion.conectar();

		       ResultSet resultSet = null;
		 		List<PacienteBean> listaPacientes= new ArrayList<PacienteBean>();
		       try {
		            PreparedStatement pstat = con.prepareStatement(qry);
		            resultSet = pstat.executeQuery();

		            while (resultSet.next()) {
		            	PacienteBean p = new PacienteBean();
		            	p.setIdDivisionResidencia(resultSet.getString("idDivisionResidencia"));
		            	p.setCIP(resultSet.getString("CIP"));
		            	p.setApellidosNombre(resultSet.getString("cognomsnom"));
		            	p.setSegSocial(resultSet.getString("segSocial"));
		            	p.setSpd(resultSet.getString("spd"));
		            	p.setExitus(resultSet.getInt("exitus"));
		            	p.setEstatus(resultSet.getString("estatus"));
		            	p.setBolquers(resultSet.getString("bolquers"));
		            	p.setComentarios(resultSet.getString("comentarios"));
		            	p.setActivo(resultSet.getString("ACTIVO"));
		            	String codigo =  resultSet.getString("CODIGO");
		            	String mensaje = resultSet.getString("MENSAJE");
		            	String claseId ="";
		            	if( codigo!=null && codigo.equalsIgnoreCase("------"))
		            	
		            	{
		            		claseId = "no_procesado";
		            		if(mensaje==null || mensaje.equals(""))
		            		{
		            			mensaje="CIP no procesado: " + "("+  codigo +")";
		            		}
		            	}
		            	else if(codigo!=null && codigo.equalsIgnoreCase("000000"))
		            	{
		            		claseId = "con_mensaje";
		            		if(mensaje==null || mensaje.equals(""))
		            		{
		            			mensaje="Procesado pero sin datos, revisión manual";
		            		}
		            	}
		            	if(codigo==null || codigo.equals("") || codigo.equalsIgnoreCase("NULL"))
			            {
			            	claseId = "no_procesado";
			            	if(mensaje==null || mensaje.equals(""))
			            	{
			            		mensaje="CIP no procesado.";
			            	}
			            }
	  		            p.setMensajeTratamiento(mensaje);
	  		            p.setClaseId(claseId);		            	
		            	listaPacientes.add(p);
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		        return listaPacientes;
    }

	public static Object checkControlRecogidaRe(String spdUsuario) {
		// TODO Esbozo de método generado automáticamente
		return null;
	}

    
    
}
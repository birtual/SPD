package lopicost.spd.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

import lopicost.config.pool.dbaccess.Conexion;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import lopicost.spd.security.helper.VisibilidadHelper;
import lopicost.spd.struts.bean.ExtReBean;
import lopicost.spd.struts.bean.PacienteBean;
import lopicost.spd.struts.bean.TratamientoRctBean;
import lopicost.spd.utils.SPDConstants;


public class ExtReDAO
{
	private final static String className = "ExtReDAO";
	private final String cLOGGERHEADER_ERROR = className + "ERROR: : ";


    
    public static List getDatosProcesoCaptacion(String spdUsuario) throws Exception {
    	String qry = " SELECT di.idDivisionResidencia AS idDivisionResidencia  ";
    	qry+= " , di.nombreDivisionResidencia ";
    	qry+= " , COUNT(*) AS tratamientosProcesadosResi ";
    	qry+= " , cipsActivos  ";
    	//qry+= " , max(CONVERT(varchar, c.fechaHoraProceso, 103)) AS fechaUltimoProcesoTrat  ";
    	qry+= " , max(FORMAT(c.fechaHoraProceso, 'dd/M/yyyy HH:mm:ss')) AS fechaUltimoProcesoTrat  ";
    	qry+= " , COUNT(distinct c.CIP) AS cipsProcesadosTrat ";
    	qry+= " , COALESCE(recTratNo.cipsRecTratProcesados, 0) AS cipsProcesadosTratNo ";
    	//qry+= " , max(CONVERT(varchar, recPend.fechaHoraProceso, 103))  AS fechaUltimoProcesoRedPend   ";
    	qry+= " , max(FORMAT(recPend.fechaHoraProceso, 'dd/M/yyyy HH:mm:ss'))  AS fechaUltimoProcesoRedPend   ";
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
    	qry+= " 	 AND p_COD_CPF LIKE '[0-9][0-9][0-9][0-9][0-9][0-9]'     ";
//    	qry+= " 	 AND p_COD_CPF  is not null  ";
    	qry+= " 	 AND p_COD_CPF not in ('000000', '010101')   ";
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
     	//qry+= " 	AND ( p_COD_CPF  is null  OR p_COD_CPF in ('000000', '010101'))     ";
    	qry+= " 	AND p_COD_CPF in ('000000', '010101')     ";
		qry+= " 	AND NOT EXISTS ( ";
		qry+= "  		select '1' from  ctl_consultaPendentDispensarSIRE c2 where d.CIP=c2.CIP and c2.p_COD_CPF is not null and  c2.p_COD_CPF not in ('000000', '010101')"; 
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
    	qry+= " 	INNER JOIN bd_residencia r ON di.idResidencia=r.idResidencia  ";
    	qry+= " 	WHERE p.activo='ACTIVO'                ";
    	qry+= "     AND (p.mutua IS NULL OR p.mutua <>'S')    ";
    	//qry+= " 	AND ( CODIGO is null  OR CODIGO in ('', '------', '000000', '010101'))   ";
    	qry+= " 	AND CODIGO in ('000000', '010101')   ";
		qry+= " 	AND NOT EXISTS ( ";
		qry+= "  		SELECT '1' FROM  ctl_consultaTractamentSIRE c2 where d.CIP=c2.CIP and c2.CODIGO is not null and  c2.CODIGO not in ('000000', '010101') ";
		qry+= "  		AND c2.CODIGO LIKE '[0-9][0-9][0-9][0-9][0-9][0-9]' "; 
		qry+= "  	)  ";
    	qry+= " 	GROUP BY p.idDivisionResidencia  ";
    	qry+= " ) recTratNo ON recTratNo.idDivisionResidencia=di.idDivisionResidencia   ";
    	qry+= " WHERE 1=1 ";
		qry+= " AND codigo LIKE '[0-9][0-9][0-9][0-9][0-9][0-9]' "; 
    	qry+= " AND codigo not in ('000000', '010101') ";        
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
		            	
		            	/*Timestamp ts = resultSet.getTimestamp("fechaUltimoProcesoRedPend");
		                String fechaUltimoProcesoRedPend = ts.toLocalDateTime().format(SPDConstants.FORMAT_DATETIME_24h);
		            	Timestamp ts2 = resultSet.getTimestamp("fechaUltimoProcesoTrat");
		                String fechaUltimoProcesoTrat = ts2.toLocalDateTime().format(SPDConstants.FORMAT_DATETIME_24h);
		            	*/

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
  
    public static List<PacienteBean> getCipsProcesadosConDatosTrat(String spdUsuario, String idDivisionResidencia) throws Exception {
    	 
  		String qry= " SELECT distinct C.CODIGO, C.DESCRIPCION, b.iddivisionresidencia, b.CIP, b.cognomsnom, b.segSocial, b.spd, b.exitus, ";
  			qry+= " b.estatus, b.bolquers,b.comentarios, b.ACTIVO, c.fechaHoraProceso, c.FechaFinal,  c.Cantidad  "; 
	    	qry+= " FROM bd_pacientes b ";
	    	qry+= " INNER JOIN bd_divisionResidencia di ON di.idDivisionResidencia=b.idDivisionResidencia  ";
	    	qry+= " INNER JOIN bd_residencia r ON di.idResidencia=r.idResidencia  ";
			qry+= " LEFT JOIN  ctl_consultaTractamentSIRE c  ON ";
			qry+= " (  ";
			qry+= " 	b.CIP=c.CIP  ";
			qry+= " 	AND b.idDivisionResidencia=c.idDivisionResidencia  ";
			qry+= " )  ";
			qry+= " WHERE 1=1 ";
			qry+= " AND b.activo='ACTIVO'  "; 
	    	qry+= " AND (b.mutua IS NULL OR b.mutua <>'S')    ";
			qry+= " AND r.status='activa' "; 
			qry+= " AND di.oidDivisionResidencia IN ( " + VisibilidadHelper.oidDivisionResidenciasVisibles(spdUsuario)  + ") ";
			qry+= " AND b.idDivisionResidencia='"+idDivisionResidencia+"' ";
			qry+= " AND c.CODIGO NOT IN ('000000', '010101')   ";
			qry+= " AND c.CODIGO LIKE '[0-9][0-9][0-9][0-9][0-9][0-9]' "; 
			qry+= " ORDER BY  b.CIP, C.CODIGO, C.DESCRIPCION ";
			
	  	    System.out.println(className + "  - getCipsProcesadosConDatosTrat -->  " +qry );		
	  	 	Connection con = Conexion.conectar();

		       ResultSet resultSet = null;
		 		List<PacienteBean> listaPacientes= new ArrayList<PacienteBean>();
		       try {
		            PreparedStatement pstat = con.prepareStatement(qry);
		            resultSet = pstat.executeQuery();
		            TreeMap<String, PacienteBean> tm_CIP = new TreeMap<String, PacienteBean>();
	            	PacienteBean paciente = null;
	            	while (resultSet.next()) {
		            	String CIP = resultSet.getString("CIP");
		            	if(tm_CIP.containsKey(CIP))
		            	{
		            		paciente=tm_CIP.get(CIP);
		            	}
		            	else 
		            	{
		            		paciente = creaPaciente(resultSet);
		            		paciente.setMensajeTratamiento(""); //para que no muestre mensaje
		            		tm_CIP.put(CIP, paciente);
			            	listaPacientes.add(paciente);
		            	}
		            	TratamientoRctBean t = new TratamientoRctBean();
		            	t.setCIP(CIP);
		            	Timestamp ts = resultSet.getTimestamp("fechaHoraProceso");
		                String fechaFormateada = ts.toLocalDateTime().format(SPDConstants.FORMAT_DATETIME_24h);
		            	t.setFechaHoraProceso(fechaFormateada);
		            	t.setRecetaCn(resultSet.getString("Codigo"));
		            	t.setRecetaMedicamento(resultSet.getString("Descripcion"));
		            	t.setRecetaFinTratamiento(resultSet.getString("FechaFinal"));
		            	t.setRecetaPauta(resultSet.getString("Cantidad"));
		            	if(paciente.getListaTratamientos()==null)
		            		paciente.setListaTratamientos(new ArrayList());
		            	paciente.getListaTratamientos().add(t);
		            		
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		        return listaPacientes;
    }
	
	public static List getCipsProcesadosSinDatosTrat(String spdUsuario, String idDivisionResidencia) throws Exception {
   	 
  		String qry= " SELECT distinct C.CODIGO, C.DESCRIPCION, b.iddivisionresidencia, b.CIP, b.cognomsnom, b.segSocial, b.spd, b.exitus, ";
  			qry+= " b.estatus, b.bolquers,b.comentarios, b.ACTIVO, c.fechaHoraProceso "; 
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
			qry+= " AND c.CODIGO in ('000000', '010101')  ";
			qry+= " AND NOT EXISTS ( ";
			qry+= "  		select '1' from  ctl_consultaTractamentSIRE c2 where c.CIP=c2.CIP and c2.CODIGO is not null and  c2.CODIGO not in ('000000', '010101')"; 
			qry+= " 		AND C2.CODIGO LIKE '[0-9][0-9][0-9][0-9][0-9][0-9]' "; 
			qry+= "  )  ";
			
	  	    System.out.println(className + "  - getCipsProcesadosSinDatosTrat -->  " +qry );		
	  	 	Connection con = Conexion.conectar();

		       ResultSet resultSet = null;
		 		List<PacienteBean> listaPacientes= new ArrayList<PacienteBean>();
		       try {
		            PreparedStatement pstat = con.prepareStatement(qry);
		            resultSet = pstat.executeQuery();
		            TreeMap<String, PacienteBean> tm_CIP = new TreeMap<String, PacienteBean>();
	            	PacienteBean paciente = null;

		            while (resultSet.next()) {
		            	String CIP = resultSet.getString("CIP");
		            	if(tm_CIP.containsKey(CIP))
		            	{
		            		paciente=tm_CIP.get(CIP);
		            	}
		            	else 
		            	{
		            		paciente = creaPaciente(resultSet);
		            		tm_CIP.put(CIP, paciente);
			            	listaPacientes.add(paciente);
		            	}
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		  }
		return listaPacientes;
    }
		

	public static List getCipsProcesadosConVentanaActiva(String spdUsuario, String idDivisionResidencia)  throws Exception {
	   	 
	  		String qry= " SELECT distinct c.p_COD_CPF as CODIGO, c.p_DES_CPF as DESCRIPCION, ";
	  			qry+= " b.iddivisionresidencia, b.CIP, b.cognomsnom, b.segSocial, b.spd, b.exitus, ";
	  			qry+= " b.estatus, b.bolquers,b.comentarios, b.ACTIVO, c.fechaHoraProceso "; 
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
				qry+= " AND c.p_COD_CPF LIKE '[0-9][0-9][0-9][0-9][0-9][0-9]' "; 
				qry+= " AND c.p_COD_CPF not in ( '000000', '010101')  ";
				qry+= " ORDER BY  b.CIP, C.p_COD_CPF, C.p_DES_CPF ";

				
		  	    System.out.println(className + "  - getCipsProcesadosSinVentanaActiva -->  " +qry );		
		  	 	Connection con = Conexion.conectar();
		  	 	ResultSet resultSet = null;
			 	List<PacienteBean> listaPacientes= new ArrayList<PacienteBean>();
			    try {
			            PreparedStatement pstat = con.prepareStatement(qry);
			            resultSet = pstat.executeQuery();
			            TreeMap<String, PacienteBean> tm_CIP = new TreeMap<String, PacienteBean>();
		            	PacienteBean paciente = null;

			            while (resultSet.next()) {
			            	String CIP = resultSet.getString("CIP");
			            	if(tm_CIP.containsKey(CIP))
			            	{
			            		paciente=tm_CIP.get(CIP);
			            	}
			            	else 
			            	{
			            		paciente = creaPaciente(resultSet);
			            		paciente.setMensajeTratamiento(""); //para que no muestre mensaje
			            		tm_CIP.put(CIP, paciente);
				            	listaPacientes.add(paciente);
			            	}
			            	TratamientoRctBean t = new TratamientoRctBean();
			            	t.setCIP(CIP);
			            	Timestamp ts = resultSet.getTimestamp("fechaHoraProceso");
			                String fechaFormateada = ts.toLocalDateTime().format(SPDConstants.FORMAT_DATETIME_24h);
			            	t.setFechaHoraProceso(fechaFormateada);


			            	t.setRecetaCn(resultSet.getString("Codigo"));
			            	t.setRecetaMedicamento(resultSet.getString("Descripcion"));
			            	//t.setRecetaFinTratamiento(resultSet.getString("FechaFinal"));
			            	//t.setRecetaPauta(resultSet.getString("Cantidad"));
			            	if(paciente.getListaTratamientos()==null)
			            		paciente.setListaTratamientos(new ArrayList());
			            	paciente.getListaTratamientos().add(t);
			            }
			        } catch (SQLException e) {
			            e.printStackTrace();
			  }
			return listaPacientes;
	    }

    
    public static List getCipsProcesadosSinVentanaActiva(String spdUsuario, String idDivisionResidencia) throws Exception {
   	 
  		String qry= " SELECT distinct c.p_COD_CPF as CODIGO, c.p_DES_CPF as DESCRIPCION, ";
  			qry+= " b.iddivisionresidencia, b.CIP, b.cognomsnom, b.segSocial, b.spd, b.exitus, ";
  			qry+= " b.estatus, b.bolquers,b.comentarios, b.ACTIVO, c.fechaHoraProceso "; 
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
			qry+= " AND c.p_COD_CPF in ( '000000', '010101')  ";
			qry+= " AND NOT EXISTS ( ";
			qry+= "  		select '1' from  ctl_consultaPendentDispensarSIRE c2 where c.CIP=c2.CIP and c2.p_COD_CPF is not null and  c2.p_COD_CPF not in ('000000', '010101')"; 
			qry+= "  )  ";
			
	  	    System.out.println(className + "  - getCipsProcesadosSinVentanaActiva -->  " +qry );		
	  	 	Connection con = Conexion.conectar();
	  	 	ResultSet resultSet = null;
		 	List<PacienteBean> listaPacientes= new ArrayList<PacienteBean>();
		    try {
		            PreparedStatement pstat = con.prepareStatement(qry);
		            resultSet = pstat.executeQuery();
		            TreeMap<String, PacienteBean> tm_CIP = new TreeMap<String, PacienteBean>();
	            	PacienteBean paciente = null;

		            while (resultSet.next()) {
		            	String CIP = resultSet.getString("CIP");
		            	if(tm_CIP.containsKey(CIP))
		            	{
		            		paciente=tm_CIP.get(CIP);
		            	}
		            	else 
		            	{
		            		paciente = creaPaciente(resultSet);
		            		tm_CIP.put(CIP, paciente);
			            	listaPacientes.add(paciente);
		            	}
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

	public static List<PacienteBean> getCipsNoProcesados(String spdUsuario, String idDivisionResidencia, String tipo) throws Exception {
		   	 
	  		String qry= " SELECT distinct b.iddivisionresidencia, b.CIP, b.cognomsnom, b.segSocial, ";
	  			qry+= " 'NP' as codigo, 'No procesado' as descripcion,  b.spd, b.exitus, b.estatus, ";
	  			qry+= " b.bolquers, b.comentarios, b.ACTIVO, getDate() as fechaHoraProceso "; 
		    	qry+= " FROM bd_pacientes b ";
		    	qry+= " INNER JOIN bd_divisionResidencia di ON di.idDivisionResidencia=b.idDivisionResidencia  ";
		    	qry+= " INNER JOIN bd_residencia r ON di.idResidencia=r.idResidencia  ";
				qry+= " WHERE 1=1 ";
				qry+= " AND b.activo='ACTIVO'  "; 
		    	qry+= " AND (b.mutua IS NULL OR b.mutua <>'S')    ";
				qry+= " AND r.status='activa' "; 
				qry+= " AND di.oidDivisionResidencia IN ( " + VisibilidadHelper.oidDivisionResidenciasVisibles(spdUsuario)  + ") ";
				qry+= " AND b.idDivisionResidencia='"+idDivisionResidencia+"' ";
				qry+= " AND NOT EXISTS  ";
				qry+= " ( ";
				if(tipo!=null && tipo.equalsIgnoreCase("TRATAMIENTO"))
				{
					qry+= "  		SELECT '1' FROM  ctl_consultaTractamentSIRE c2 ";
					qry+= "  		WHERE b.CIP=c2.CIP ";
					qry+= " 		AND c2.CODIGO LIKE '[0-9][0-9][0-9][0-9][0-9][0-9]' "; 
				}
				if(tipo!=null && tipo.equalsIgnoreCase("RECETA"))
				{
					qry+= "  		SELECT '1' FROM  ctl_consultaPendentDispensarSIRE c2 ";
					qry+= "  		WHERE b.CIP=c2.CIP ";
					qry+= " 		AND c2.p_COD_CPF LIKE '[0-9][0-9][0-9][0-9][0-9][0-9]' "; 
				}
				qry+= "  )  ";
				
		  	    System.out.println(className + "  - getCipsNoProcesados -->  " +tipo + "_" + qry );		
		  	 	Connection con = Conexion.conectar();
		  	 	ResultSet resultSet = null;
			 	List<PacienteBean> listaPacientes= new ArrayList<PacienteBean>();
			    try {
			            PreparedStatement pstat = con.prepareStatement(qry);
			            resultSet = pstat.executeQuery();
			            TreeMap<String, PacienteBean> tm_CIP = new TreeMap<String, PacienteBean>();
		            	PacienteBean paciente = null;

			            while (resultSet.next()) {
			            	String CIP = resultSet.getString("CIP");
			            	if(tm_CIP.containsKey(CIP))
			            	{
			            		paciente=tm_CIP.get(CIP);
			            	}
			            	else 
			            	{
			            		paciente = creaPaciente(resultSet);
			            		tm_CIP.put(CIP, paciente);
				            	listaPacientes.add(paciente);
			            	}
			            }
			        } catch (SQLException e) {
			            e.printStackTrace();
			  }
			return listaPacientes;
	    }


    private static PacienteBean creaPaciente(ResultSet resultSet) throws SQLException {
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
    	//p.setFechaProceso(resultSet.getDate("fechaHoraProceso"));
    	
    	Timestamp ts = resultSet.getTimestamp("fechaHoraProceso");
        String fechaFormateada = ts.toLocalDateTime().format(SPDConstants.FORMAT_DATETIME_24h);
    	p.setFechaHoraProceso(fechaFormateada);
 	
    	
    	String codigo =  resultSet.getString("CODIGO");
    	String descripcion = resultSet.getString("DESCRIPCION");
    	String claseId ="";
    	if( codigo!=null && codigo.equalsIgnoreCase("------")) 
    	{
    		claseId = "no_procesado";
    		if(descripcion==null || descripcion.equals(""))
    		{
    			descripcion="CIP no procesado: " + "("+  codigo +")";
    		}
    	}
    	else if(codigo!=null && codigo.equalsIgnoreCase("000000"))
    	{
    		claseId = "con_mensaje";
    		if(descripcion==null || descripcion.equals(""))
    		{
    			descripcion="Procesado pero sin datos, revisión manual";
    		}
    	}
    	else if(codigo==null || codigo.equals("") || codigo.equalsIgnoreCase("NULL") || codigo.equalsIgnoreCase("NP"))
        {
        	claseId = "no_procesado";
        	if(descripcion==null ||descripcion.equals(""))
        	{
        		codigo="CIP no procesado.";
        		descripcion="CIP no procesado.";
        	}
        }
    	if(p.getCIP()!=null && !p.getCIP().equals("") && p.getCIP().toUpperCase().contains("MMTT"))
    	{
    		claseId = "mutua_o_sin_tsi";
    		descripcion="MUTUA" ;
    	}
    	if(p.getSegSocial()!=null && !p.getSegSocial().equals("") && p.getSegSocial().toUpperCase().contains("XXXX"))
    	{
    		claseId = "mutua_o_sin_tsi";
    		descripcion="TSI no informada." ;
    	}
          p.setMensajeTratamiento(descripcion);
          p.setClaseId(claseId);

		return p;
	}


    
    
}
package lopicost.spd.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lopicost.config.pool.dbaccess.Conexion;
import lopicost.spd.controller.ControlSPD;
import lopicost.spd.security.helper.VisibilidadHelper;
import lopicost.spd.struts.bean.CamposPantallaBean;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.struts.form.FicheroResiForm;
import lopicost.spd.utils.AegerusHelper;
import lopicost.spd.utils.HelperSPD;
import lopicost.spd.utils.SPDConstants;
import lopicost.spd.utils.StringUtil;
 
 
public class FicheroResiDetalleDAO {
	
	static String className="FicheroResiDetalleDAO";
	
	static String TABLA_ACTIVA		=	"dbo.SPD_ficheroResiDetalle";
	static String TABLA_HISTORICO 	=	"dbo.SPDHst_ficheroResiDetalle";   //tabla de histórico
	static String TABLA_CAB_ACTIVA		=	"dbo.SPD_ficheroResiCabecera";
	static String TABLA_CAB_HISTORICO 	=	"dbo.SPDHst_ficheroResiCabecera";   //tabla de histórico

	public static FicheroResiBean getFicheroResiDetalleByIdOid(String spdUsuario, int oidFicheroResiDetalle) throws Exception {
		return getFicheroResiDetalleByIdOid(spdUsuario, oidFicheroResiDetalle, false);
	}
	 
	public static FicheroResiBean getFicheroResiDetalleByIdOid(String spdUsuario, int oidFicheroResiDetalle, boolean historico) throws Exception {
		
		String tabla = historico ? TABLA_HISTORICO : TABLA_ACTIVA;
		String tabla2 = historico ? TABLA_HISTORICO : TABLA_ACTIVA;
		
		FicheroResiBean c = new FicheroResiBean();
		Connection con = Conexion.conectar();
		String 	qry =  " SELECT g.*, d.idProcessIospd FROM "+tabla+" g LEFT JOIN dbo.bd_divisionResidencia d  on g.idDivisionResidencia=d.idDivisionResidencia ";
			qry+= " WHERE g.idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ") ";
		
		if(oidFicheroResiDetalle>0)
			qry+= " AND g.oidFicheroResiDetalle = '"+oidFicheroResiDetalle +"' ";
	
 		System.out.println(className + "--> getResidencias -->  " +qry );		
	    ResultSet resultSet = null;
 	 	
 	    try {
 	         PreparedStatement pstat = con.prepareStatement(qry);
 	         resultSet = pstat.executeQuery();
 	         while (resultSet.next()) {
 	        	 c = creaFicheroResiBean(resultSet);
	      			if(c.getMensajesAlerta()!=null && !c.getMensajesAlerta().equals(""))
	      				c.setIncidencia("SI");
	      			else c.setIncidencia("NO");
 	        	 
 	            }
 	     } catch (SQLException e) {
 	         e.printStackTrace();
 	     }finally {con.close();}

 	     return c ;


	}
	
	
	public static FicheroResiBean getGestFicheroResiBolsaByForm(String spdUsuario, int oidFicheroResiDetalle, FicheroResiForm form,  boolean soloCabecera, boolean excluirCabecera, boolean excluirNoPintar) throws Exception {

		  FicheroResiBean result = null;
		  List lista= getGestFicheroResiBolsa(spdUsuario, oidFicheroResiDetalle, form, 0, 1, null, false, null, excluirCabecera, excluirNoPintar);
		  if(lista!=null && lista.size()>0)
			  result=(FicheroResiBean)lista.get(0);
		  return result;
	  }


	
	public static boolean nuevo(String idDivisionResidencia, String idProceso, FicheroResiBean b) throws ClassNotFoundException, SQLException {

		String editable=b.isEditable()?"SI":"NO";
		String confirmar=b.getIncidencia()!=null&&b.getIncidencia().equalsIgnoreCase("SI")?SPDConstants.REGISTRO_CONFIRMAR:b.getConfirmar();
		String validar=b.getIncidencia()!=null&&b.getIncidencia().equalsIgnoreCase("SI")?SPDConstants.REGISTRO_VALIDAR:b.getValidar();
		
		String spdCn=b.getSpdCnFinal();
		if(spdCn!=null && spdCn.length()>6)
			spdCn = spdCn.substring(0, 6);
		
        int result=0;
		  Connection con = Conexion.conectar();
	  	   String qry = " INSERT INTO dbo.SPD_ficheroResiDetalle (fechaInsert, idDivisionResidencia,  idProceso, resiCIP,   ";
	  	   	qry+= "  resiApellidosNombre, resiNombre, resiApellido1, resiApellido2, resiApellidos, resiCn , resiMedicamento,  ";
	  	   	qry+= "  resiFormaMedicacion,  resiInicioTratamiento, resiFinTratamiento, resiInicioTratamientoParaSPD, resiFinTratamientoParaSPD, ";
	  	   	qry+= "  resiObservaciones, resiComentarios, resiTipoMedicacion, resiSiPrecisa,  ";
	  	   	qry+= "  spdCnFinal, spdNombreBolsa, spdFormaMedicacion, spdAccionBolsa, spdNomGtVmpp, ";
	  	   	qry+= "  resiD1, resiD2, resiD3, resiD4, resiD5, resiD6, resiD7,   ";
	  	   	qry+= "  diasAutomaticos, resiViaAdministracion , resiVariante,  ";
	  	   	qry+= "  resiToma1, resiToma2, resiToma3, resiToma4, resiToma5, resiToma6, resiToma7, resiToma8,   ";
	  	   	qry+= "  resiToma9, resiToma10, resiToma11, resiToma12, resiToma13, resiToma14, resiToma15, resiToma16, ";
	  	   	qry+= "  resiToma17, resiToma18, resiToma19, resiToma20, resiToma21, resiToma22, resiToma23, resiToma24, ";	 
	  	   	qry+= "  validar, confirmar, confirmaciones, incidencia, resiPeriodo, fechaDesde, fechaHasta, ";
	  	   	qry+= "  resultLog, row, mensajesInfo, mensajesAlerta, mensajesResidencia, idEstado, idTratamientoCIP, idTratamientoSPD, oidFicheroResiCabecera, frecuencia, diasMesConcretos,";
	  	   	qry+= "  diasSemanaConcretos, tipoEnvioHelium, secuenciaGuide, numeroDeTomas, tipoRegistro, diasSemanaMarcados, editable, previsionResi, previsionSPd, detalleRow, detalleRowKey, detalleRowKeyLite, editado,";
	  	   	qry+= "  controlValidacionDatos, controlRegistroRobot, controlRegistroAnterior, controlPrincipioActivo, controlNumComprimidos, controlNoSustituible, controlDiferentesGtvmp, controlUnicoGtvm) VALUES ";
//	       	qry+= "('"+ b.getIdDivisionResidencia()+"', '"+ b.getIdProceso()+"', '"+ b.getResiCIP()+"', ";
	       	qry+= " (CONVERT(datetime, getdate(), 120),  '"+idDivisionResidencia+"', '"+ idProceso+"', '"+ b.getResiCIP()+"', ";
	    	qry+= " REPLACE('" + b.getResiApellidosNombre()+"', '''', ''''''),  REPLACE('" +  b.getResiNombrePaciente()+"', '''', ''''''),  REPLACE('" + b.getResiApellido1()+"', '''', ''''''), ";
	    	qry+= " REPLACE('" + b.getResiApellido2()+"', '''', ''''''),  REPLACE('" +  b.getResiApellidos()+"', '''', ''''''), ";
	    	qry+= " '" + b.getResiCn()+"', REPLACE('" + StringUtil.limpiarTextoComentarios(b.getResiMedicamento())+"', '''', ''''''), ";
	    	
	    	
	       	qry+= " '" + b.getResiFormaMedicacion() +"', '"+ b.getResiInicioTratamiento() +"', '"+ b.getResiFinTratamiento()+"',  '"+ b.getResiInicioTratamientoParaSPD() +"', '"+ b.getResiFinTratamientoParaSPD()+"',";
   			qry+= " REPLACE('" + b.getResiObservaciones() +"', '''', ''''''),  REPLACE('" +  b.getResiComentarios()+"', '''', ''''''), COALESCE('" +  b.getResiTipoMedicacion()+"', ''), '"+ b.getResiSiPrecisa()+"', ";
			//qry+= " '" + b.getSpdCnFinal() +"', REPLACE('" +   StringUtil.limpiarTextoComentarios(b.getSpdNombreBolsa()) +"', '''', ''''''), '"+  b.getSpdFormaMedicacion() +"', ";
   			qry+= " '" + spdCn +"', REPLACE('" +   StringUtil.replaceInvalidChars(b.getSpdNombreBolsa()) +"', '''', ''''''), '"+  b.getSpdFormaMedicacion() +"', ";
			qry+= " '" + b.getSpdAccionBolsa()+"', COALESCE('"+ b.getSpdNomGtVmpp()+"', ''), ";
			qry+= " '" + b.getResiD1()+"', '"+b.getResiD2()+"', '"+b.getResiD3()+"', '"+b.getResiD4()+"', '"+b.getResiD5()+"', '"+b.getResiD6()+"', '"+ b.getResiD7()+"', ";      
			qry+= " '" + b.getResiDiasAutomaticos()+"',   COALESCE('" + b.getResiViaAdministracion()+"', ''), COALESCE('" + b.getResiVariante()+"', ''), ";
			qry+= " '" + b.getResiToma1()+"', '"+b.getResiToma2()+"', '"+b.getResiToma3()+"', '"+b.getResiToma4()+"', '"+b.getResiToma5()+"', '"+b.getResiToma6()+"', ";
			qry+= " '" + b.getResiToma7()+"', '"+b.getResiToma8()+"', '"+b.getResiToma9()+"', '"+b.getResiToma10()+"', '"+b.getResiToma11()+"', '"+b.getResiToma12()+"', ";
			qry+= " '" + b.getResiToma13()+"', '"+b.getResiToma14()+"', '"+b.getResiToma15()+"', '"+b.getResiToma16()+"', '"+b.getResiToma17()+"', '"+b.getResiToma18()+"', ";
			qry+= " '" + b.getResiToma19()+"', '"+b.getResiToma20()+"', '"+b.getResiToma21()+"', '"+b.getResiToma22()+"', '"+b.getResiToma23()+"', '"+b.getResiToma24()+"', ";
			//qry+= " '" + validar+"','" + b.getConfirmar()+"','" + b.getConfirmaciones()+"','" + b.getIncidencia()+"','" + b.getResiPeriodo()+"',  COALESCE('"+b.getFechaDesde()+"', ''), COALESCE('"+b.getFechaHasta()+"', ''), ";
			qry+= " '" + validar+"','" + confirmar+"','" + b.getConfirmaciones()+"','" + b.getIncidencia()+"','" + b.getResiPeriodo()+"',  COALESCE('"+b.getFechaDesde()+"', ''), COALESCE('"+b.getFechaHasta()+"', ''), ";
			qry+= " '" + b.getResultLog()+"', '" + b.getRow()+"', REPLACE(COALESCE('"+b.getMensajesInfo() + "', ''), '''', ''''''), REPLACE(COALESCE('"+b.getMensajesAlerta()+"', ''), '''', ''''''), ";
			qry+= " REPLACE(COALESCE('"+b.getMensajesResidencia()+"', ''), '''', ''''''), '" + b.getIdEstado()+"', '"+b.getIdTratamientoCIP()+"', '"+b.getIdTratamientoSPD()+"', "+b.getOidFicheroResiCabecera()+", ";
			qry+= " "+b.getResiFrecuencia()+", '"+b.getDiasMesConcretos()+ "',  '" + b.getDiasSemanaConcretos()+"', '"+b.getTipoEnvioHelium()+"', COALESCE('" + b.getSecuenciaGuide()+"', ''), ";
			qry+= " '"+b.getNumeroDeTomas()+"',  COALESCE('" + b.getTipoRegistro()+"', ''),  '" + b.getDiasSemanaMarcados()+"','" + editable+ "', "+ b.getPrevisionResi() +", "+ b.getPrevisionSPD()+"," ;
			qry+= " '" + (b.getDetalleRow()!=null?b.getDetalleRow().replace("'", " "):"")+ "', '" + b.getDetalleRowKey()+"', '" + b.getDetalleRowKeyLite()+"', 'NO',";
			qry+= " '" + b.getControlValidacionDatos() + "', '" + b.getControlRegistroRobot() + "', '" + b.getControlRegistroAnterior() + "', '" + b.getControlPrincipioActivo() + "'," ;
			qry+= " '" + b.getControlNumComprimidos() + "', '" + b.getControlNoSustituible() + "', '" + b.getControlDiferentesGtvmp() + "', '" + b.getControlUnicoGtvm() + "')" ;
			
	  		System.out.println(className + "--> FicheroResiDetalleDAO.nuevo -->  " +qry );		
	   			
		    try {
		         PreparedStatement pstat = con.prepareStatement(qry);
		         result=pstat.executeUpdate();
		         con.commit();
		         con.close();
		         pstat.close();
		     } catch (SQLException e) {
		         e.printStackTrace();
		         return false;
		     }
		
		    if(result==0) 
		    	System.out.println("  ATENCION, NO GRABADO: " + b.getResiCIP() + " - " + b.getResiCn() );
		return result>0;
	}
	
	
	public static boolean borraDetalleSinCabecera() throws Exception {
        int result=0;
        //TODO generar histórico
		  Connection con = Conexion.conectar();
	  	   String qry = " delete from  dbo.SPD_ficheroResiDetalle where oidFicheroResiCabecera not in (select oidFicheroResiCabecera from SPD_ficheroResiCabecera)  ";

	  		System.out.println(className + "--> borraDetalleSinCabecera -->  " +qry );	
	  		
	  		
	    try {
	         PreparedStatement pstat = con.prepareStatement(qry);
	         result=pstat.executeUpdate();
	       con.commit();
	     } catch (SQLException e) {
	         e.printStackTrace();
	     }
		
		return result>0;
		
	}
	
	
	public static boolean limpiarCIPIdproceso(String spdUsuario, FicheroResiBean medResi, boolean cipNombre) throws Exception {
        int result=0;
        //TODO generar histórico
		  Connection con = Conexion.conectar();
	  	   String qry = " DELETE FROM dbo.SPD_ficheroResiDetalle ";
			qry+= " WHERE idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";

	  		qry+= "  AND resiCIP='"+medResi.getResiCIP()+"' ";
	  		if(cipNombre)
	  			qry+= " AND resiApellidosNombre='"+medResi.getResiApellidosNombre()+"' ";
	  		qry+= "  AND idDivisionResidencia='"+medResi.getIdDivisionResidencia()+"' ";
	  		qry+= "  AND idProceso='"+medResi.getIdProceso()+"' ";
	  		
	  		System.out.println(className + "--> limpiarCIPIdproceso -->  " +qry );	
	  		
	  		
	    try {
	         PreparedStatement pstat = con.prepareStatement(qry);
	         result=pstat.executeUpdate();
	       con.commit();
	     } catch (SQLException e) {
	         e.printStackTrace();
	     }
		
		return result>0;
		
	}

	public static boolean generarHistoricoProcesoAnterior(String spdUsuario, FicheroResiBean medResi, boolean cipNombre) throws Exception {
        int result=0;
        //TODO generar histórico
		  Connection con = Conexion.conectar();
	  	   String qry = " INSERT INTO [dbo].[SPD_ficheroResiDetalle_HST]  ";
	  	   		qry+= "   SELECT getdate(), * FROM dbo.SPD_ficheroResiDetalle ";
				qry+= " WHERE idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";

	  	   		qry+= "  AND resiCIP='"+medResi.getResiCIP()+"' ";
	  		if(cipNombre)
	  			qry+= " AND resiApellidosNombre='"+medResi.getResiApellidosNombre()+"' ";
	  		qry+= "  AND idDivisionResidencia='"+medResi.getIdDivisionResidencia()+"' ";
	  		qry+= "  AND idProceso='"+medResi.getIdProceso()+"' ";
	  		System.out.println(className + "--> generarHistoricoProcesoAnterior. -->  " +qry );		
	  		
	    try {
	         PreparedStatement pstat = con.prepareStatement(qry);
	         result=pstat.executeUpdate();
	         con.commit();
	     } catch (SQLException e) {
	         e.printStackTrace();
	     }finally {con.close();}
		
		return result>0;
	}

	public int getCountGestFicheroResiBolsa(String spdUsuario, FicheroResiForm form,  boolean soloCabecera, boolean excluirCabecera, boolean excluirNoPintar) throws Exception {
		 String qry = getQueryFicheroResiDetalle( spdUsuario, -1 , form, true, 0, 0, null, false, null, excluirCabecera, excluirNoPintar);
	   	System.out.println(className + "--> getCountGestFicheroResiBolsa -->  " +qry );		
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

	private static String getQueryFicheroResiDetalle(String spdUsuario, int oidFicheroResiDetalle, FicheroResiForm form, boolean count, int inicio, int fin, String distinctCampo, boolean total, String orderBy, boolean excluirCabecera, boolean excluirNoPintar) throws Exception {
		return getQueryFicheroResiDetalle(spdUsuario, oidFicheroResiDetalle, form, count, inicio, fin, distinctCampo, total, orderBy, excluirCabecera, excluirNoPintar, false);
	}
	
	private static String getQueryFicheroResiDetalle(String spdUsuario, int oidFicheroResiDetalle, FicheroResiForm form, boolean count, int inicio, int fin, String distinctCampo, boolean total, String orderBy, boolean excluirCabecera, boolean excluirNoPintar, boolean historico) throws Exception {	
			
		String tabla = historico ? TABLA_HISTORICO : TABLA_ACTIVA;
		
		String idProceso=(form.getIdProceso()!=null && !form.getIdProceso().equals("")?form.getIdProceso():form.getFiltroProceso());
		
		String qryOrder= "  order by  g.row, g.idProceso, g.resiCIP, g.resiMedicamento, COALESCE(g.resiInicioTratamientoParaSPD, g.resiInicioTratamiento) ";
		if(orderBy!=null && !orderBy.equals(""))
			qryOrder= "  order by  " + orderBy + ", g.row";
		String camposSelect = "  g.*, p.nom, p.apellido1, p.apellido2, d.idProcessIospd, p.oidPaciente, p.planta, p.habitacion ";

		if(distinctCampo!=null && !distinctCampo.equals(""))  //nos llega un campo concreto
		{
			camposSelect = "  g." + distinctCampo ;
			if(total ) 
				qryOrder+=  " order by g." +distinctCampo  ;
		}
		
		
		
		String qry = " select * from ( select  distinct ROW_NUMBER() OVER(" +qryOrder+ ") AS ROWNUM, "+camposSelect+" "; 
		

		

		if(count)  //si es contador inicializo la query
			qry = "select count(distinct g." + distinctCampo + "as quants";
		

			qry+=  " from "+ tabla +" g  ";
			qry+=  " left join dbo.bd_divisionResidencia d  on g.idDivisionResidencia=d.idDivisionResidencia ";
			qry+=  " left join bd_pacientes p on g.resiCIP=p.CIP ";
			
			qry+= " WHERE g.idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";

		
		if(oidFicheroResiDetalle>0 )
		{
				qry+=  " and g.oidFicheroResiDetalle = '"+oidFicheroResiDetalle+"'";
		}

		if(excluirCabecera)
		{
			qry+=  " and g.tipoRegistro <> 'CABECERA'";
		}
		if(excluirNoPintar)
		{
			qry+=  " and g.spdAccionBolsa in ('"+SPDConstants.SPD_ACCIONBOLSA_PASTILLERO+"', '"+SPDConstants.SPD_ACCIONBOLSA_SOLO_INFO+"')";
		}
		
		if(form.getOidFicheroResiCabecera()>0)
			qry+=  " and g.oidFicheroResiCabecera = '"+form.getOidFicheroResiCabecera()+"'";
		if(form.getFiltroNombreCorto()!=null && !form.getFiltroNombreCorto().equals(""))
			qry+=  " and ( g.resiMedicamento like '%"+form.getFiltroNombreCorto() +"%' OR  g.spdNombreBolsa  like '%"+form.getFiltroNombreCorto() +"%' OR  g.resiObservaciones  like '%"+form.getFiltroNombreCorto() +"%'  OR  g.resiComentarios  like '%"+form.getFiltroNombreCorto() +"%') ";
		if(form.getFiltroMedicamentoResi()!=null && !form.getFiltroMedicamentoResi().equals(""))
			qry+=  " and g.resiMedicamento = '"+form.getFiltroMedicamentoResi() +"' ";
		if(form.getFiltroNombreCortoOK()!=null && !form.getFiltroNombreCortoOK().equals(""))
		{
			qry+=  " and g.spdNombreBolsa = '"+form.getFiltroNombreCortoOK() +"' ";
		}
		if(idProceso!=null && !idProceso.equals(""))
			qry+=  " and g.idProceso = '"+idProceso +"' ";
		
		if(form.getSoloConMensajesInfo()!=null && form.getSoloConMensajesInfo().equals("1"))
			qry+=  " and g.mensajesInfo is not null and g.mensajesInfo != 'null' and g.mensajesInfo != '' ";
		if(form.getSoloConMensajesResidencia()!=null && form.getSoloConMensajesResidencia().equals("1"))
			qry+=  " and g.mensajesResidencia is not null and g.mensajesResidencia != 'null' and g.mensajesResidencia != '' ";
		if(form.getSoloConMensajesAlerta()!=null && form.getSoloConMensajesAlerta().equals("1"))
			qry+=  " and g.mensajesAlerta is not null and g.mensajesAlerta != 'null' and g.mensajesAlerta != '' ";
		 
		if(!total)
		{	

			if(StringUtil.limpiarTextoyEspacios(form.getSeleccionResiCn())!=null && !StringUtil.limpiarTextoyEspacios(form.getSeleccionResiCn()).trim().equals(""))
				qry+=  " and g.resiCn = '"+StringUtil.limpiarTextoyEspacios(form.getSeleccionResiCn()) +"' ";

			if(form.getSeleccionIdentificador()!=null && !form.getSeleccionIdentificador().equals(""))
				qry+=  " and p.oidPaciente = '"+form.getSeleccionIdentificador() +"' ";

			if(form.getSeleccionResiCIP()!=null && !form.getSeleccionResiCIP().equals(""))
				qry+=  " and g.resiCIP = '"+form.getSeleccionResiCIP() +"' ";
			if(form.getSeleccionResiNombrePaciente()!=null && !form.getSeleccionResiNombrePaciente().equals(""))
				qry+=  " and g.resiNombrePaciente= '"+form.getSeleccionResiNombrePaciente() +"' ";
			if(form.getSeleccionResiApellidosNombre()!=null && !form.getSeleccionResiApellidosNombre().equals(""))
				qry+=  " and g.resiApellidosNombre= '"+form.getSeleccionResiApellidosNombre() +"' ";
			if(form.getSeleccionResiMedicamento()!=null && !form.getSeleccionResiMedicamento().equals(""))
				qry+=  " and g.resiMedicamento= '"+form.getSeleccionResiMedicamento() +"' ";
			if(form.getSeleccionResiFormaMedicacion()!=null && !form.getSeleccionResiFormaMedicacion().equals(""))
				qry+=  " and g.resiFormaMedicacion= '"+form.getSeleccionResiFormaMedicacion() +"' ";
			if(form.getSeleccionResiObservaciones()!=null && !form.getSeleccionResiObservaciones().equals(""))
				qry+=  " and g.resiObservaciones= '"+form.getSeleccionResiObservaciones() +"' ";
			if(form.getSeleccionResiComentarios()!=null && !form.getSeleccionResiComentarios().equals(""))
				qry+=  " and g.resiComentarios= '"+form.getSeleccionResiComentarios() +"' ";
			if(form.getSeleccionResiSiPrecisa()!=null && !form.getSeleccionResiSiPrecisa().equals(""))
				qry+=  " and g.resiSiPrecisa= '"+form.getSeleccionResiSiPrecisa() +"' ";
			if(form.getSeleccionResiPeriodo()!=null && !form.getSeleccionResiPeriodo().equals(""))
				qry+=  " and g.resiPeriodo= '"+form.getSeleccionResiPeriodo() +"' ";
			if(form.getSeleccionResiVariante()!=null && !form.getSeleccionResiVariante().equals(""))
				qry+=  " and g.resiVariante= '"+form.getSeleccionResiVariante() +"' ";
			if(form.getSeleccionResiTipoMedicacion()!=null && !form.getSeleccionResiTipoMedicacion().equals(""))
				qry+=  " and g.resiTipoMedicacion= '"+form.getSeleccionResiTipoMedicacion() +"' ";
			if(form.getSeleccionResiViaAdministracion()!=null && !form.getSeleccionResiViaAdministracion().equals(""))
				qry+=  " and g.resiViaAdministracion= '"+form.getSeleccionResiViaAdministracion() +"' ";
			if(form.getSeleccionSpdCnFinal()!=null && !form.getSeleccionSpdCnFinal().equals(""))
				qry+=  " and g.SpdCnFinal= '"+form.getSeleccionSpdCnFinal() +"' ";
			if(form.getSeleccionSpdFormaMedicacion()!=null && !form.getSeleccionSpdFormaMedicacion().equals(""))
				qry+=  " and g.SpdFormaMedicacion= '"+form.getSeleccionSpdFormaMedicacion() +"' ";
			if(form.getSeleccionSpdNombreBolsa()!=null && !form.getSeleccionSpdNombreBolsa().equals(""))
				qry+=  " and g.SpdNombreBolsa= '"+form.getSeleccionSpdNombreBolsa() +"' ";
			if(form.getSeleccionSpdAccionBolsa()!=null && !form.getSeleccionSpdAccionBolsa().equals(""))
				qry+=  " and g.SpdAccionBolsa= '"+form.getSeleccionSpdAccionBolsa() +"' ";
			if(form.getSeleccionEstado()!=null && !form.getSeleccionEstado().equals(""))
				qry+=  " and g.idEstado= '"+form.getSeleccionEstado() +"' ";
			if(form.getSeleccionIncidencia()!=null && !form.getSeleccionIncidencia().equals(""))
				qry+=  " and g.incidencia= '"+form.getSeleccionIncidencia() +"' ";

			if(form.getSeleccionValidar()!=null && !form.getSeleccionValidar().equals(""))
				qry+=  " and ( g.validar= '"+form.getSeleccionValidar() +"' or g.confirmar= '"+form.getSeleccionValidar() +"'  )";
			//	qry+=  " and g.validar= '"+form.getSeleccionRevisar() +"' ";
			//if(form.getSelecciongetConfirmar()!=null && !form.getSelecciongetConfirmar().equals(""))
			//	qry+=  " and g.confirmar= '"+form.getSelecciongetConfirmar() +"' ";
			if(form.getSeleccionMensajesInfo()!=null && !form.getSeleccionMensajesInfo().equals(""))
				qry+=  " and g.mensajesInfo= '"+form.getSeleccionMensajesInfo() +"' ";
			if(form.getSeleccionMensajesResidencia()!=null && !form.getSeleccionMensajesResidencia().equals(""))
				qry+=  " and g.mensajesResidencia= '"+form.getSeleccionMensajesResidencia	() +"' ";
			if(form.getSeleccionMensajesAlerta()!=null && !form.getSeleccionMensajesAlerta().equals(""))
				qry+=  " and g.mensajesAlerta= '"+form.getSeleccionMensajesAlerta() +"' ";

			if(form.getSeleccionResultLog()!=null && !form.getSeleccionResultLog().equals(""))
				qry+=  " and g.resultLog= '"+form.getSeleccionResultLog() +"' ";
			if(form.getSeleccionSecuenciaGuide()!=null && !form.getSeleccionSecuenciaGuide().equals(""))
				qry+=  " and g.secuenciaGuide= '"+form.getSeleccionSecuenciaGuide() +"' ";
			int filtrosChecked=getFiltrosChecked(form);;
			if(filtrosChecked>0)
			{
				qry+="AND ( ";
				
				// añadimos los filtros de alertas
				if(form.isFiltroNumComprimidos()){
					qry+=  "  g.controlNumComprimidos= '"+ SPDConstants.CTRL_NCOMPRIMIDOS_DIFERENTE +"' ";
					if(filtrosChecked > 1)
						qry+=  " OR ";
					filtrosChecked--; //restamos el filtro para controlar los que quedan
				}
				if(form.isFiltroRegistroAnterior()){
					qry+=  "  g.controlRegistroAnterior in  ('"+ SPDConstants.CTRL_REGISTRO_ANTERIOR_RD_SI +"', '"+ SPDConstants.CTRL_REGISTRO_ANTERIOR_RI_SD +"' )  ";
					if(filtrosChecked > 1)
						qry+=  " OR ";
					filtrosChecked--; //restamos el filtro para controlar los que quedan
				}
				if(form.isFiltroRegistroRobot()){
					qry+=  "  g.controlRegistroRobot  = '"+ SPDConstants.CTRL_ROBOT_NO_SE_ENVIA +"'  ";
					if(filtrosChecked > 1)
						qry+=  " OR ";
					filtrosChecked--; //restamos el filtro para controlar los que quedan
				}			
				
				if(form.isFiltroValidacionDatos()){
					qry+=  "  g.controlValidacionDatos  = '"+ SPDConstants.CTRL_VALIDAR_ALERTA +"'  ";
					if(filtrosChecked > 1)
						qry+=  " OR ";
					filtrosChecked--; //restamos el filtro para controlar los que quedan
				}			
				if(form.isFiltroPrincipioActivo()){
					qry+=  "  g.controlPrincipioActivo = '"+ SPDConstants.CTRL_PRINCIPIO_ACTIVO_ALERTA +"'  ";
					if(filtrosChecked > 1)
						qry+=  " OR ";
					filtrosChecked--; //restamos el filtro para controlar los que quedan
				}			
				if(form.isFiltroNoSustituible()){
					qry+=  "  g.controlNoSustituible = '"+ SPDConstants.CTRL_SUSTITUIBLE_ALERTA +"'  ";
					if(filtrosChecked > 1)
						qry+=  " OR ";
					filtrosChecked--; //restamos el filtro para controlar los que quedan
				}			
				if(form.isFiltroDiferentesGtvmp()){
					qry+=  "  g.controlDiferentesGtvmp = '"+ SPDConstants.CTRL_DIFERENTE_GTVMP_ALERTA+"'  ";
					if(filtrosChecked > 1)
						qry+=  " OR ";
					filtrosChecked--; //restamos el filtro para controlar los que quedan
				}		
				if(form.isFiltroUnicoGtvm()){
					qry+=  "  g.controlUnicoGtvm = '"+ SPDConstants.CTRL_UNICO_GTVM_ALERTA+"'  ";
					if(filtrosChecked > 1)
						qry+=  " OR ";
					filtrosChecked--; //restamos el filtro para controlar los que quedan
				}		
				qry+=" ) ";
				
			}

			
			//controlRevisionDatos, controlRegistroRobot, controlRegistroAnterior, controlPrincipioActivo, controlNumComprimidos, controlNoSustituible, controlDiferentesGtvmp
		}
		
		if(!count && !total)  
		{
			qry+= getOtrosSql2008(inicio, fin, count);
			
		}
	
	return qry;

	}

	
	/*
	 * Detalle de un idProceso por CIP
	 * 
	 */
	  public static List<FicheroResiBean> getFicheroResiBeanPorCipYProceso(String spdUsuario, String idProceso, String  CIP,  boolean historico) throws Exception {
		     
		  String qry = getQueryFicheroResiDetallePorCipYProceso(spdUsuario, idProceso, CIP, historico);
		  Connection con = Conexion.conectar();
	 
		  System.out.println(className + "--> getGestFicheroResiBolsaPorCipYProceso -->  " +qry );
  
		  ResultSet resultSet = null;
	    	 
	    	 CamposPantallaBean campos = new CamposPantallaBean();
	    	 List<FicheroResiBean> lista= new ArrayList<FicheroResiBean>();	
	    	 try {
	    		 PreparedStatement pstat = con.prepareStatement(qry);
	    		 resultSet = pstat.executeQuery();
	    		 CamposPantallaBean camposPantallaBean = new CamposPantallaBean();
	    		 while (resultSet.next()) {
	    			 FicheroResiBean c  = creaFicheroResiBean(resultSet);
    			
	    			 lista.add(c);
	
        }
     } catch (SQLException e) {
         e.printStackTrace();
     }finally {con.close();}

	    	
     return lista;
 }
	  
	  
	
	public static String getQueryFicheroResiDetallePorCipYProceso(String spdUsuario, String idProceso, String CIP,  boolean historico) throws Exception {	
		
		String tabla = historico ? TABLA_HISTORICO : TABLA_ACTIVA;
		
	
		String qry = " select g.* "; 
			qry+= " FROM "+ tabla +" g  ";
			qry+= " WHERE g.idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
			qry+= " AND g.idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
			qry+= " AND g.tipoRegistro = 'LINEA'";
			qry+= " AND g.idProceso = '"+idProceso +"' ";
			qry+= " AND g.resiCIP = '"+CIP +"' ";
			qry+= " ORDER BY g.resiMedicamento, COALESCE(g.resiInicioTratamientoParaSPD, g.resiInicioTratamiento) ";
		

	return qry;

	}


	
	private static int getFiltrosChecked(FicheroResiForm form) {
		int filtrosChecked=0;
		if(form.isFiltroNumComprimidos()) filtrosChecked++;
		if(form.isFiltroRegistroAnterior()) filtrosChecked++;
		if(form.isFiltroRegistroRobot()) filtrosChecked++;
		if(form.isFiltroValidacionDatos()) filtrosChecked++;
		if(form.isFiltroPrincipioActivo()) filtrosChecked++;
		if(form.isFiltroNoSustituible()) filtrosChecked++;
		if(form.isFiltroDiferentesGtvmp()) filtrosChecked++;
		if(form.isFiltroUnicoGtvm()) filtrosChecked++;
		
		return filtrosChecked;
	}


	private static String getFicheroResiDetalleByIdProceso(String spdUsuario, String idDivisionResidencia, String idProceso) throws Exception {
		
			
		String qry = " SELECT *  from dbo.SPD_ficheroResiDetalle g  dbo.bd_divisionResidencia d  on g.idDivisionResidencia=d.idDivisionResidencia ";

			qry+= " WHERE g.idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";

		if(idDivisionResidencia!=null && !idDivisionResidencia.equals(""))
				qry+=  " AND g.idDivisionResidencia = '"+idDivisionResidencia +"' ";
			if(idProceso!=null && !idProceso.equals(""))
				qry+=  " AND g.idProceso = '"+idProceso +"' ";
		
			qry+=  " order by  g.idProceso, g.resiCIP, g.resiMedicamento ";
	return qry;

	}
	
	
  	/**
	 * Fetch es una cláusula que funciona a partir del SqlServer 2008 (no inclusive)
	 * @param form
	 * @param inicio
	 * @param fin
	 * @param count
	 * @return
	 */
	private static String getOtrosSqlPost2008(int inicio, int fin, boolean count) 
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
	private static String getOtrosSql2008( int inicio, int fin, boolean count) 
	{
		String otros="";
		if(!count) 
		{
			otros+= " ) cte ";
			otros+= " where ROWNUM >=  "+ (inicio) + "  AND ROWNUM <= "+(fin);

		}
		return otros;
	}
	
	  public static List<FicheroResiBean> getGestFicheroResiBolsa(String spdUsuario, int oidFicheroResiDetalle, FicheroResiForm form, int inicio, int fin, String distinctCampo, boolean total, String orderBy, boolean excluirCabecera, boolean excluirNoPintar) throws Exception {
	  return 	  getGestFicheroResiBolsa(spdUsuario, oidFicheroResiDetalle, form, inicio, fin, distinctCampo, total, orderBy, excluirCabecera, excluirNoPintar, false);
	  }
	
	  public static List<FicheroResiBean> getGestFicheroResiBolsa(String spdUsuario, int oidFicheroResiDetalle, FicheroResiForm form, int inicio, int fin, String distinctCampo, boolean total, String orderBy, boolean excluirCabecera, boolean excluirNoPintar, boolean historico) throws Exception {
		     
		  String qry = getQueryFicheroResiDetalle(spdUsuario, oidFicheroResiDetalle, form, false, inicio, fin, distinctCampo, total, orderBy, excluirCabecera, excluirNoPintar, historico);
		  Connection con = Conexion.conectar();
	 
		  System.out.println(className + "--> getGestFicheroResiBolsa -->  " +qry );
  
		  ResultSet resultSet = null;
	    	 
	    	 CamposPantallaBean campos = new CamposPantallaBean();
	    	 form.setProcesoValido(true);
	    	 List<FicheroResiBean> lista= new ArrayList<FicheroResiBean>();	
	    	 try {
	    		 PreparedStatement pstat = con.prepareStatement(qry);
	    		 resultSet = pstat.executeQuery();
	    		 CamposPantallaBean camposPantallaBean = new CamposPantallaBean();
	    		 while (resultSet.next()) {
	    			 FicheroResiBean c  = creaFicheroResiBean(resultSet);
	    			 
	    			// HelperSPD.gestionVisibilidadCampos(campos, c);

	    		

	   		    			//if(c.getSpdCnFinal()==null || c.getSpdCnFinal().equals("")  || c.getSpdCnFinal().equals("null") )
	    			//	c.setMensajesAlerta(c.getMensajesAlerta() + " FALTA CN FINAL ");
	    			//??no recuerdo por qué está puesto lo del duplicado, no encuentro dónde se fija el texto "DUPLICADO"....
	    			//if(c.getResultLog()!=null && c.getResultLog().contains("DUPLICADO") && c.getMensajesInfo()!=null && !c.getMensajesInfo().contains("DUPLICADO"))
	    			 //	c.setMensajesAlerta(c.getMensajesAlerta() + " POSIBLE DUPLICADO ");
	    			
	    			//c.setEditable(true);
	      			//en caso que sea un registro borrado no dejamos editarlos
	      			//if(c.getIdEstado()!=null && c.getIdEstado().equalsIgnoreCase(SPDConstants.SPD_DETALLE_BORRADO))
		      		//	c.setEditable(false);
	      				
	      			//if((c.getMensajesAlerta()!=null && !c.getMensajesAlerta().equals("")) || (c.getValidar()!=null && c.getValidar().equals(SPDConstants.REGISTRO_VALIDAR)))
	    			 if((c.getMensajesAlerta()!=null && !c.getMensajesAlerta().equals("")) || (c.getConfirmar()!=null && c.getConfirmar().equals(SPDConstants.REGISTRO_CONFIRMAR)))
	      			{
	      				c.setIncidencia("SI");
	      				//c.setValidar(SPDConstants.REGISTRO_VALIDAR);
	      				c.setConfirmar(SPDConstants.REGISTRO_CONFIRMAR);
	      				form.setProcesoValido(false);
//	      				if((c.getConfirmar()!=null && c.getConfirmar().equals(SPDConstants.REGISTRO_CONFIRMAR)))
//	      					c.setConfirmar("SI");
	      				
	      			}
	      			//else c.setIncidencia("NO");
	      				
	    			 //c=controlDias(c);
	    			 /*
	    			   if(c.getIncidencia()!=null&&c.getIncidencia().equalsIgnoreCase("SI"))
	    			 {
	    				 form.setProcesoValido(false);
	    			 }
	    			 */
	    			 
	    			 /*
	    			 //(NO ME ACUERDO POR QUE PUSE ESTO) --> solo metemos en lista los que cumplen condición X según formulario
	    			if(form.getSoloConMensajesInfo()!=null && form.getSoloConMensajesInfo().equals("1")	&& (c.getMensajesInfo()==null || c.getMensajesInfo().equals("")  || c.getMensajesInfo().equals("null")))
	    				c=null;
	    			 else
	 	    			if(form.getSoloConMensajesAlerta()!=null && form.getSoloConMensajesAlerta().equals("1")	&& (c.getMensajesAlerta()==null || c.getMensajesAlerta().equals("")  || c.getMensajesAlerta().equals("null")))
	 	    				c=null;
	 	    			else if(c!=null)
	 	    		*/		
	    			 lista.add(c);
	    	 //form.setCamposPantallaBean(camposPantallaBean);
	    	 
	
        }
     } catch (SQLException e) {
         e.printStackTrace();
     }finally {con.close();}

	    	
     return lista;
 }

	   
	public static List<FicheroResiBean> getSustitucionesPendientes(String idDivisionResidencia, String idProceso) throws Exception {
		Connection con = Conexion.conectar();
		//	   System.out.println("connected getListaPresentacion()" );
		List<FicheroResiBean>  result = new ArrayList();
			   
		String qry =	" SELECT DISTINCT "; 
				qry+=	" f.idDivisionResidencia, "; 
				qry+=	" f.resiCn, "; 
				qry+=	" f.resiMedicamento, "; 
				qry+=	" b.nomGtVmp, "; 
				qry+=	" COALESCE(resi.resiCn, c.resiCn) AS resiCnPosible, "; 
				qry+=	" COALESCE(resi.resiMedicamento, c.resiMedicamento) AS resiMedicamentoPosible, "; 
				qry+=	" COALESCE(resi.spdCn , c.spdCn) AS spdCnPosible, "; 
				qry+=	" COALESCE(resi.spdNombreBolsa, c.spdNombreBolsa) AS spdNombreBolsaPosible, "; 
				qry+=	" COALESCE(resi.spdFormaMedicacion, c.spdFormaMedicacion) AS spdFormaMedicacionPosible, "; 
				qry+=	" COALESCE(resi.spdAccionBolsa, c.spdAccionBolsa) AS spdAccionBolsaPosible "; 
				qry+=	" FROM dbo.SPD_ficheroResiDetalle f "; 
				qry+=	" LEFT JOIN bd_consejo b ON b.codigo = SUBSTRING(CAST(f.resiCn AS CHAR(7)), 1, 6) "; 
				qry+=	" LEFT JOIN  ( "; 
				qry+=	" 	SELECT "; 
				qry+=	" 	d2.idDivisionResidencia, "; 
				qry+=	" 	f2.resiCn, "; 
				qry+=	" 	f2.resiMedicamento, "; 
				qry+=	" 	f2.spdCn, "; 
				qry+=	" 	f2.spdNombreBolsa, "; 
				qry+=	" 	f2.spdFormaMedicacion, "; 
				qry+=	" 	f2.spdAccionBolsa, "; 
				qry+=	" 	f2.excepciones, "; 
				qry+=	" 	f2.aux1, "; 
				qry+=	" 	f2.aux2, "; 
				qry+=	" 	ROW_NUMBER() OVER (PARTITION BY f2.resiCn ORDER BY COUNT(*) DESC) AS rn "; 
				qry+=	" 	FROM dbo.SPD_sustitucionesLite f2 "; 
				qry+=	" 	JOIN dbo.bd_divisionResidencia d2 ON f2.idDivisionResidencia = d2.idDivisionResidencia "; 
				qry+=	" 	INNER JOIN dbo.bd_robot r2 ON d2.idRobot = r2.idRobot "; 
				qry+=	" 	GROUP BY d2.idDivisionResidencia, f2.resiCn, f2.resiMedicamento, f2.spdCn, f2.spdNombreBolsa, f2.spdFormaMedicacion, f2.spdAccionBolsa, f2.excepciones, f2.aux1, f2.aux2 "; 
				qry+=	" ) resi ON (f.resiCn = resi.resiCn OR UPPER(REPLACE(f.resiMedicamento, ' ', '')) = UPPER(REPLACE(resi.resiMedicamento, ' ', ''))) "; 				
				qry+=	" LEFT JOIN  ( "; 
				qry+=	" 	SELECT "; 
				qry+=	" 	d2.idDivisionResidencia, "; 
				qry+=	" 	f2.resiCn, "; 
				qry+=	" 	f2.resiMedicamento, "; 
				qry+=	" 	f2.spdCn, "; 
				qry+=	" 	f2.spdNombreBolsa, "; 
				qry+=	" 	f2.spdFormaMedicacion, "; 
				qry+=	" 	f2.spdAccionBolsa, "; 
				qry+=	" 	f2.excepciones, "; 
				qry+=	" 	f2.aux1, "; 
				qry+=	" 	f2.aux2, "; 
				qry+=	" 	ROW_NUMBER() OVER (PARTITION BY f2.resiCn ORDER BY COUNT(*) DESC) AS rn "; 
				qry+=	" 	FROM dbo.SPD_sustitucionesLite f2 "; 
				qry+=	" 	JOIN dbo.bd_divisionResidencia d2 ON f2.idDivisionResidencia = d2.idDivisionResidencia "; 
				qry+=	" 	INNER JOIN dbo.bd_robot r2 ON d2.idRobot = r2.idRobot "; 
				qry+=	" 	GROUP BY d2.idDivisionResidencia, f2.resiCn, f2.resiMedicamento, f2.spdCn, f2.spdNombreBolsa, f2.spdFormaMedicacion, f2.spdAccionBolsa, f2.excepciones, f2.aux1, f2.aux2 "; 
				qry+=	" ) c ON (f.resiCn = c.resiCn OR UPPER(REPLACE(f.resiMedicamento, ' ', '')) = UPPER(REPLACE(c.resiMedicamento, ' ', ''))) "; 
				qry+=	" WHERE f.idProceso =  '" + idProceso  + "'";
				qry+=	" AND f.idDivisionResidencia =  '" + idDivisionResidencia  + "'";
				qry+=	" AND (f.spdCnFinal IS NULL OR UPPER(f.spdCnFinal) = UPPER('NULL') OR UPPER(f.spdCnFinal) = UPPER(''))   "; 
				qry+=	" AND (f.resiCn IS NOT NULL) AND UPPER(f.resiCn) NOT IN ('NULL') "; 
				qry+=	" AND (c.rn = 1 or c.rn is null);  "; 
				qry+=	" -- Filtrar por la primera coincidencia con mayor número de coincidencias "; 

						
							
			 			System.out.println(className + "--> getSustitucionesPendientes -->  " +qry );		
				     	ResultSet resultSet = null;
			 	 	
			 	    try {
			 	         PreparedStatement pstat = con.prepareStatement(qry);
			 	         resultSet = pstat.executeQuery();
			 	         while (resultSet.next()) {
			 	        	FicheroResiBean f = new FicheroResiBean();
			 	        	 f.setIdDivisionResidencia(resultSet.getString("idDivisionResidencia"));
			 	        	 f.setResiCn(resultSet.getString("resiCn"));
			 	        	 f.setResiMedicamento (resultSet.getString("resiMedicamento"));
			 	        	 f.setSpdNomGtVmp(resultSet.getString("nomGtVmp"));
			 	        	 f.setSpdCnFinal(resultSet.getString("spdCnPosible"));
			 	        	 f.setSpdNombreBolsa(resultSet.getString("spdNombreBolsaPosible"));
			 	        	 f.setSpdFormaMedicacion(resultSet.getString("spdFormaMedicacionPosible"));
			 	        	 f.setSpdAccionBolsa(resultSet.getString("spdAccionBolsaPosible"));


			 	        	result.add(f);
			 	            }
			 	     } catch (SQLException e) {
			 	         e.printStackTrace();
			 	     }

			 	     return result;
			 	 }
		
		public static List<FicheroResiBean> getProduccionesPorCIP(String spdUsuario, String CIP, boolean historico) throws Exception {
			   Connection con = Conexion.conectar();
			   List<FicheroResiBean>  result = new ArrayList();
			   String tabla = historico ? TABLA_HISTORICO : TABLA_ACTIVA;
			   String tablaCab = historico ? TABLA_CAB_HISTORICO : TABLA_CAB_ACTIVA;
			   

			   String qry =   " SELECT distinct g.idProceso, g.idProceso + ' - ' + c.idEstado as idEstado";
			    		qry+= " FROM "+tabla+" g INNER JOIN "+tablaCab+" c ON g.oidFicheroResiCabecera = c.oidFicheroResiCabecera  ";
						qry+= " WHERE g.idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
			    		qry+= " AND g.resiCIP ='" + CIP+"' ";
			    		qry+= " ORDER BY g.idProceso desc";
						
			    		System.out.println(className + "--> getProduccionesPorCIP -->  " +qry );		
				     	ResultSet resultSet = null;
			 	 	
			 	    try {
			 	         PreparedStatement pstat = con.prepareStatement(qry);
			 	         resultSet = pstat.executeQuery();
			 	         while (resultSet.next()) {
			 	        	FicheroResiBean f = new FicheroResiBean();
			 	        	f.setIdProceso(resultSet.getString("idProceso"));
			 	        	f.setIdEstado(resultSet.getString("idEstado"));
				 	       	result.add(f);
			 	            }
			 	     } catch (SQLException e) {
			 	         e.printStackTrace();
			 	     }
			 	   finally {con.close();}
			 	     return result;
			 	 }

		/**OK
		 * Método para borrar el detalle de los ficheros, de uno en concreto o de forma masiva
		 * @param oidFicheroResiDetalle
		 * @param idDivisionresidencia
		 * @param idProceso
		 * @return
		 * @throws Exception 
		 */
		public static boolean borrar(String spdUsuario, int oidFicheroResiCabecera, int oidFicheroResiDetalle, String idTratamientoCIP) throws Exception 
		{
			int result=0;
			Connection con = Conexion.conectar();
			
			
			String query = " DELETE FROM dbo.SPD_ficheroResiDetalle  ";
			query+= " WHERE idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
			 //solo se borra masivamente si nos envían los dos campos
			query+=  " AND oidFicheroResiCabecera  = "+oidFicheroResiCabecera;

			if(idTratamientoCIP!=null && !idTratamientoCIP.equals(""))
				query+=  " AND idTratamientoCIP  = '"+idTratamientoCIP +"'";

			if(oidFicheroResiDetalle>0)
			query+=  " AND oidFicheroResiDetalle  = "+oidFicheroResiDetalle;
			System.out.println(className + "--> borrar -->" +query );		
			 	
		    try {
		         PreparedStatement pstat = con.prepareStatement(query);
		         result=pstat.executeUpdate();
		       
		     } catch (SQLException e) {
		    	 result=-1;
		         e.printStackTrace();
		     }finally {con.close();}
			return result>=0;
		}
		

		/**
		 * Método que actualiza el id del tratamieno CIP-CN-resultadoFinalrobot
		 * Se utilizará para control y detección de posibles cambios entre producciones
		 * @param spdUsuario
		 * @param oidFicheroResiCabecera
		 * @param oidFicheroResiDetalle
		 * @return
		 * @throws Exception
		 */
		public static boolean actualizaIdSpd(FicheroResiBean medResi) throws Exception 
		{
			int result=0;
			Connection con = Conexion.conectar();
	
			String query = "   update dbo.SPD_ficheroResiDetalle set idTratamientoSPD =  ";
			query+= "  COALESCE(resiCIP, '_') +'|'+   ";
			query+= "  COALESCE(spdCnFinal, '_') +'|'+ ";
			query+= "  COALESCE(spdNombreBolsa, '_') +'|'+ ";
			query+= "  COALESCE(spdAccionBolsa, '_') +'|'+ ";
			query+= "  COALESCE(NULLIF(resiD1,''), '_') +'|'+ ";
			query+= "  COALESCE(NULLIF(resiD2,''), '_') +'|'+ ";
			query+= "  COALESCE(NULLIF(resiD3,''), '_') +'|'+ ";
			query+= "  COALESCE(NULLIF(resiD4,''), '_') +'|'+ ";
			query+= "  COALESCE(NULLIF(resiD5,''), '_') +'|'+ ";
			query+= "  COALESCE(NULLIF(resiD6,''), '_') +'|'+ ";
			query+= "  COALESCE(NULLIF(resiD7,''), '_') +'|'+ ";
			query+= "  CASE  ";
			query+= "         WHEN COALESCE(resiInicioTratamientoParaSPD, '') = '' THEN resiInicioTratamiento ";
			query+= "         ELSE resiInicioTratamientoParaSPD ";
			query+= "  END  ";
			query+= "  +'|'+  ";
			query+= "  CASE  ";
			query+= "         WHEN COALESCE(resiFinTratamientoParaSPD, '') = '' THEN resiFinTratamiento ";
			query+= "         ELSE resiFinTratamientoParaSPD ";
			query+= "  END  ";
			query+= "  +'|'+  ";
			query+= "  COALESCE(NULLIF(resiToma1,''), '_') +'|'+ ";
			query+= "  COALESCE(NULLIF(resiToma2,''), '_') +'|'+ ";
			query+= "  COALESCE(NULLIF(resiToma3,''), '_') +'|'+ ";
			query+= "  COALESCE(NULLIF(resiToma4,''), '_') +'|'+ ";
			query+= "  COALESCE(NULLIF(resiToma5,''), '_') +'|'+ ";
			query+= "  COALESCE(NULLIF(resiToma6,''), '_') +'|'+ ";
			query+= "  COALESCE(NULLIF(resiToma7,''), '_') +'|'+ ";
			query+= "  COALESCE(NULLIF(resiToma8,''), '_') +'|'+ ";
			query+= "  COALESCE(NULLIF(resiToma9,''), '_') +'|'+ ";
			query+= "  COALESCE(NULLIF(resiToma10,''), '_') +'|'+ ";
			query+= "  COALESCE(NULLIF(resiToma11,''), '_') +'|'+ ";
			query+= "  COALESCE(NULLIF(resiToma12,''), '_') +'|'+ ";
			query+= "  COALESCE(NULLIF(resiToma13,''), '_') +'|'+ ";
			query+= "  COALESCE(NULLIF(resiToma14,''), '_') +'|'+ ";
			query+= "  COALESCE(NULLIF(resiToma15,''), '_') +'|'+ ";
			query+= "  COALESCE(NULLIF(resiToma16,''), '_') +'|'+ ";
			query+= "  COALESCE(NULLIF(resiToma17,''), '_') +'|'+ ";
			query+= "  COALESCE(NULLIF(resiToma18,''), '_') +'|'+ ";
			query+= "  COALESCE(NULLIF(resiToma19,''), '_') +'|'+ ";
			query+= "  COALESCE(NULLIF(resiToma20,''), '_') +'|'+ ";
			query+= "  COALESCE(NULLIF(resiToma21,''), '_') +'|'+ ";
			query+= "  COALESCE(NULLIF(resiToma22,''), '_') +'|'+ ";
			query+= "  COALESCE(NULLIF(resiToma23,''), '_') +'|'+ ";
			query+= "  COALESCE(NULLIF(resiToma24,''), '_') +'|'+ ";
			query+= "  COALESCE(NULLIF(diasMesConcretos,''), '_') +'|'+ ";
			query+= "  COALESCE(NULLIF(diasSemanaConcretos,''), '_') +'|'+ ";
			query+= "  COALESCE(NULLIF(secuenciaGuide,''), '_') +'|'+ ";
			query+= "  COALESCE(CAST(frecuencia AS varchar(3)), '_') +'|'";
			query+= "  from dbo.SPD_ficheroResiDetalle ";
			query+= " where idTratamientoCIP is  not null ";
			query+= " and tipoRegistro<>'CABECERA' ";
			query+= " and idProceso='" + medResi.getIdProceso() + "'";
			query+= " and resiCIP ='" + medResi.getResiCIP() + "'";
			query+= " and spdCnFinal ='" + medResi.getSpdCnFinal() + "'";

			
				
			System.out.println(className + "--> actualizaIdSpd -->" +query );		
			 	
		    try {
		         PreparedStatement pstat = con.prepareStatement(query);
		         result=pstat.executeUpdate();
		       
		     } catch (SQLException e) {
		    	 result=-1;
		         e.printStackTrace();
		     }finally {con.close();}
			return result>=0;
		}
		
		
		
		/**OK
		 * Método para borrar el detalle de los ficheros, de uno en concreto o de forma masiva
		 * @param oidFicheroResiDetalle
		 * @param idDivisionresidencia
		 * @param idProceso
		 * @return
		 * @throws Exception 
		 */
		public static boolean marcarBorrado(String spdUsuario, int oidFicheroResiCabecera, int oidFicheroResiDetalle) throws Exception 
		{
			int result=0;
			Connection con = Conexion.conectar();
			String query = " UPDATE dbo.SPD_ficheroResiDetalle ";
			query+= " SET idEstado ='"+SPDConstants.SPD_DETALLE_BORRADO+"' ";
			query+= " WHERE idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";

			query+= " AND 	oidFicheroResiCabecera = " + oidFicheroResiCabecera;
			query+= " AND oidFicheroResiDetalle = " + oidFicheroResiDetalle;
			
			System.out.println(className + "--> borrar -->" +query );		
					
			  
		    try {
		         PreparedStatement pstat = con.prepareStatement(query);
		         result=pstat.executeUpdate();
		       
		     } catch (SQLException e) {
		    	 result=-1;
		         e.printStackTrace();
		     }finally {con.close();}
			return result>=0;
		}
		
		/**OK
		 * Método para borrar el detalle de los ficheros, de uno en concreto o de forma masiva
		 * @param form
		 * @return
		 * @throws Exception 
		 */
		public static boolean borrar(String spdUsuario, FicheroResiForm form) throws Exception 
		{
			return  borrar(spdUsuario, form.getOidFicheroResiCabecera(), form.getOidFicheroResiDetalle(), null);
			}

		/**OK
		 * Método para borrar el detalle de los ficheros que proviene de una secuenciaGuide creada.
		 * 
		 * @param oidFicheroResiDetalle
		 * @param idDivisionresidencia
		 * @param idProceso
		 * @return
		 * @throws Exception 
		 */
		public static boolean borrarHijosSecuenciaGuide(String spdUsuario, FicheroResiBean resi) throws Exception 
		{
			int result=0;
			Connection con = Conexion.conectar();
			
			//String sufijo = SPDConstants.PREFIJO_REGISTRO_SECUENCIA_GUIDE + "_"+oidFicheroResiDetalle;
			String sufijo = resi.getResiCn() + "_" + SPDConstants.PREFIJO_REGISTRO_SECUENCIA_GUIDE+ "%"; //pongo row para no borrar registros de tratamiento doblado con diferentes pautas alternas (ej. RESINCALCIO Ceritania)

			//if(resi.getTipoEnvioHelium().equals(SPDConstants.TIPO_4_GUIDE_HELIUM))
				//sufijo+= resi.getRow(); //pongo row para no borrar registros de tratamiento doblado con diferentes pautas alternas (ej. RESINCALCIO Ceritania) en los casos de secuencias

			
			
			String query = " DELETE FROM dbo.SPD_ficheroResiDetalle  ";
			query+= " WHERE idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
			query+= " AND idProceso  ='"+resi.getIdProceso() + "'";  
			query+= " AND resiCn  LIKE '%"+sufijo+"%'";  //todos los registros que han derivado de la secuencia tienen el mismo prefijo
			query+= " AND resiCip  = '"+resi.getResiCIP()+"'";  //todos los registros que han derivado de la secuencia tienen el mismo prefijo
			System.out.println(className + "--> borrarHijosSecuenciaGuide -->" +query );		
			try {
			     PreparedStatement pstat = con.prepareStatement(query);
			     result=pstat.executeUpdate();
			    
			} catch (SQLException e) {
				result=-1;
			    e.printStackTrace();
			}finally {con.close();}
				
			 	
			return result>=0;
		}
		

		/**
		 * Método para borrar el detalle de los ficheros que proviene de una trazodona editada .
		 * @param spdUsuario
		 * @param medResi
		 * @return 
		 * @throws Exception 
		 */
		public static boolean borrarHijosTrazodonas(String spdUsuario, FicheroResiBean medResi) throws Exception {
			int result=0;
			Connection con = Conexion.conectar();
			
			String query = " DELETE FROM dbo.SPD_ficheroResiDetalle  ";
			query+= " WHERE idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
			query+= " AND idProceso  ='"+medResi.getIdProceso() + "'";  
			query+= " AND resiCIP  ='"+medResi.getResiCIP() + "'";
			query+= " AND idEstado = '"+SPDConstants.REGISTRO_CREADO_AUTOMATICAMENTE + "'";  //para que no borre el registro que se edita en caso que sea el 111111  
			//query+= " AND spdCnFinal = '111111'";  //todos los registros que han derivado de la secuencia tienen el CN 111111
			
			System.out.println(className + "--> borrarHijosTrazodonas -->" +query );		
			try {
			     PreparedStatement pstat = con.prepareStatement(query);
			     result=pstat.executeUpdate();
			    
			} catch (SQLException e) {
				result=-1;
			    e.printStackTrace();
			}finally {con.close();}
				
			 	
			return result>=0;
		}
		/*
		public static boolean edita(String spdUsuario, FicheroResiForm b) throws Exception {

	        int result=0;
			  Connection con = Conexion.conectar();
			  String log=checkTratamientoValido(b);
			  boolean hayErrores=(log!=null&&!log.equals("")?true:false);
			  

		  	   String qry = " update dbo.SPD_ficheroResiDetalle ";
		  	    qry+= "  set resiCIP ='"+b.getResiCIP()+"', ";
		  	    //qry+= "  resiApellidosNombre='"+b.getResiNombrePaciente()+"', ";
						qry+= "  resiCn='"+b.getResiCn()+"', ";
						qry+= "  resiInicioTratamiento='"+b.getResiInicioTratamiento()+"', ";
						qry+= "  resiFinTratamiento='"+b.getResiFinTratamiento()+"', ";
						qry+= "  resiInicioTratamientoParaSPD='"+b.getResiInicioTratamientoParaSPD()+"', ";
						qry+= "  resiFinTratamientoParaSPD='"+b.getResiFinTratamientoParaSPD()+"', ";
						qry+= "  resiSiPrecisa='"+b.getResiSiPrecisa()+"', ";
						qry+= "  resiViaAdministracion='"+b.getResiViaAdministracion()+"', ";
						qry+= "  spdCnFinal='"+b.getSpdCnFinal()+"', ";
						qry+= "  spdNombreBolsa='"+b.getSpdNombreBolsa()+"', ";
						qry+= "  spdFormaMedicacion='"+b.getSpdFormaMedicacion()+"', ";
						qry+= "  spdAccionBolsa='"+b.getSpdAccionBolsa()+"', ";
						qry+= "  resiD1='"+b.getResiD1()+"', ";
						qry+= "  resiD2='"+b.getResiD2()+"', ";
						qry+= "  resiD3='"+b.getResiD3()+"', ";
						qry+= "  resiD4='"+b.getResiD4()+"', ";
						qry+= "  resiD5='"+b.getResiD5()+"', ";
						qry+= "  resiD6='"+b.getResiD6()+"', ";
						qry+= "  resiD7='"+b.getResiD7()+"', ";
						qry+= "  resiToma1= COALESCE('"+b.getResiToma1()+"', ''), ";
						qry+= "  resiToma2= COALESCE('"+b.getResiToma2()+"', ''), ";
						qry+= "  resiToma3= COALESCE('"+b.getResiToma3()+"', ''), ";
						qry+= "  resiToma4= COALESCE('"+b.getResiToma4()+"', ''), ";
						qry+= "  resiToma5= COALESCE('"+b.getResiToma5()+"', ''), ";
						qry+= "  resiToma6= COALESCE('"+b.getResiToma6()+"', ''), ";
						qry+= "  resiToma7= COALESCE('"+b.getResiToma7()+"', ''), ";
						qry+= "  resiToma8= COALESCE('"+b.getResiToma8()+"', ''), ";
						qry+= "  resiToma9= COALESCE('"+b.getResiToma9()+"', ''), ";
						qry+= "  resiToma10= COALESCE('"+b.getResiToma10()+"', ''), ";
						qry+= "  resiToma11= COALESCE('"+b.getResiToma11()+"', ''), ";
						qry+= "  resiToma12= COALESCE('"+b.getResiToma12()+"', ''), ";
						qry+= "  resiToma13= COALESCE('"+b.getResiToma13()+"', ''), ";
						qry+= "  resiToma14= COALESCE('"+b.getResiToma14()+"', ''), ";
						qry+= "  resiToma15= COALESCE('"+b.getResiToma15()+"', ''), ";
						qry+= "  resiToma16= COALESCE('"+b.getResiToma16()+"', ''), ";
						qry+= "  resiToma17= COALESCE('"+b.getResiToma17()+"', ''), ";
						qry+= "  resiToma18= COALESCE('"+b.getResiToma18()+"', ''), ";
						qry+= "  resiToma19= COALESCE('"+b.getResiToma19()+"', ''), ";
						qry+= "  resiToma20= COALESCE('"+b.getResiToma20()+"', ''), ";
						qry+= "  resiToma21= COALESCE('"+b.getResiToma21()+"', ''), ";
						qry+= "  resiToma22= COALESCE('"+b.getResiToma22()+"', ''), ";
						qry+= "  resiToma23= COALESCE('"+b.getResiToma23()+"', ''), ";
						qry+= "  resiToma24= COALESCE('"+b.getResiToma24()+"', ''), ";
						qry+= "  mensajesInfo= COALESCE('"+b.getMensajesInfo()+"', ''), ";
						qry+= "  mensajesResidencia()= COALESCE('"+b.getMensajesResidencia()+"', ''), ";
						
						if(hayErrores)
						{
							qry+= "  incidencia='"+hayErrores+"', ";
						}
						else
						{
							qry+= "  incidencia='', ";
						}

						qry+= "  diasSemanaConcretos='"+HeliumHelper.detectarDiasMarcados(b)+"', ";
						qry+= "  diasMesConcretos='"+b.getDiasMesConcretos()+"', ";
						qry+= "  secuenciaGuide='"+b.getSecuenciaGuide()+"', ";
					//	qry+= "  tipoEnvioHelium='"+b.getTipoEnvioHelium()+"', ";
						qry+= "  tipoEnvioHelium='"+HeliumHelper.getTipoEnvioHelium(b)+"', ";
						qry+= "  frecuencia='"+b.getResiFrecuencia()+"', ";
						
						int nt = 0;
						if(b.getNumeroDeTomas()==null || b.getNumeroDeTomas().equals("") )
							nt=b.getFicheroResiDetalleBean().getNumeroDeTomas();
						
						qry+= "  numeroDeTomas="+nt+ ",";
						qry+= "  diasSemanaMarcados="+b.getDiasSemanaMarcados()+ ",";
						qry+= "  idEstado='"+b.getIdEstado()+"', ";
						qry+= "  controlRevisionDatos='"+b.getContextPath()IdEstado()+"', ";
						qry+= "  controlRegistroRobot='"+b.getIdEstado()+"', ";
						qry+= "  controlRegistroAnterior='"+b.getIdEstado()+"', ";
						qry+= "  controlPrincipioActivo='"+b.getIdEstado()+"', ";
						qry+= "  controlNumComprimidos='"+b.getIdEstado()+"', ";
						qry+= "  controlNoSustituible='"+b.getIdEstado()+"', ";
						qry+= "  controlDiferentesGtvmp='"+b.getIdEstado()+"', ";
						qry+= "  editado='SI'  ";
						qry+= "  WHERE idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
						qry+= "  AND 	oidFicheroResiDetalle = " + b.getOidFicheroResiDetalle();
						
						System.out.println(className + "--> edita con Form-->  " +qry );		
	      		 
		    try {
		         PreparedStatement pstat = con.prepareStatement(qry);
		         result=pstat.executeUpdate();
		       
		     } catch (SQLException e) {
		         e.printStackTrace();
		     }

			
			return result>0;
		}
*/




		private static String checkTratamientoValido(FicheroResiForm b) {
			String result="";
			
			if(b.getResiCIP()==null || b.getResiCIP().equals(""))
				result+= "Falta CIP.  <br> ";
			//if(b.getResiNombrePaciente()==null || b.getResiNombrePaciente().equals(""))
			//	result+= "Falta Nombre residente.  <br> ";
			if(b.getResiInicioTratamiento()==null || b.getResiInicioTratamiento().equals("") )
				result+= "Falta fecha inicio.  <br>";
			if(b.getResiCn()==null || b.getResiCn().equals("") )
				result+= "Falta CN residencia. <br>   ";
			//if(b.getResiMedicamento()==null || b.getResiMedicamento().equals("") )
			//	result+= "Falta nombre medicamento. <br>   ";
			if(b.getSpdCnFinal()==null || b.getSpdCnFinal().equals("") )
				result+= "Falta CN Robot. <br>   ";
			if(b.getSpdCnFinal()==null || b.getSpdCnFinal().equals("") )
				result+= "Falta CN Robot. <br>   ";
			if(b.getSpdNombreBolsa()==null || b.getSpdNombreBolsa().equals("") )
				result+= "Falta Nombre medicamento Robot. <br>   ";
			if(b.getSpdAccionBolsa()==null || b.getSpdAccionBolsa().equals("") )
				result+= "Falta Acción en bolsa. <br>   ";
			
			return result;
		}

	
		
		public static boolean edita(String spdUsuario, FicheroResiBean b) throws Exception {

			String metodoLlamada=ControlSPD.getMetodoLlamada();
	        int result=0;
			  Connection con = Conexion.conectar();
			 
			 String qry = " update dbo.SPD_ficheroResiDetalle ";
		  	 	qry+= " set resiCIP ='"+b.getResiCIP()+"', ";
		  	 //	qry+= " resiNombrePaciente='"+b.getResiNombrePaciente()+"', ";
		  	 	qry+= " resiCn='"+b.getResiCn()+"', ";
				qry+= " resiMedicamento='"+b.getResiMedicamento()+"', ";
				qry+= " resiComentarios='"+b.getResiComentarios()+"', ";
				qry+= " resiObservaciones='"+b.getResiObservaciones()+"', ";
				qry+= " resiVariante='"+b.getResiVariante()+"', ";
				qry+= " resiInicioTratamiento='"+b.getResiInicioTratamiento()+"', ";
				qry+= " resiFinTratamiento='"+b.getResiFinTratamiento()+"', ";
				qry+= " resiInicioTratamientoParaSPD='"+b.getResiInicioTratamientoParaSPD()+"', ";
				qry+= " resiFinTratamientoParaSPD='"+b.getResiFinTratamientoParaSPD()+"', ";
				qry+= " resiSiPrecisa='"+b.getResiSiPrecisa()+"', ";
				qry+= " resiViaAdministracion=coalesce('"+b.getResiViaAdministracion()+"', ''), ";
				qry+= " spdCnFinal='"+b.getSpdCnFinal()+"', ";
				qry+= " spdNombreBolsa='"+b.getSpdNombreBolsa()+"', ";
				qry+= " spdFormaMedicacion='"+b.getSpdFormaMedicacion()+"', ";
				qry+= " spdAccionBolsa='"+b.getSpdAccionBolsa()+"', ";
				qry+= " resiD1='"+b.getResiD1()+"', ";
				qry+= " resiD2='"+b.getResiD2()+"', ";
				qry+= " resiD3='"+b.getResiD3()+"', ";
				qry+= " resiD4='"+b.getResiD4()+"', ";
				qry+= " resiD5='"+b.getResiD5()+"', ";
				qry+= " resiD6='"+b.getResiD6()+"', ";
				qry+= " resiD7='"+b.getResiD7()+"', ";
				qry+= " resiToma1='"+b.getResiToma1()+"', ";
				qry+= " resiToma2='"+b.getResiToma2()+"', ";
				qry+= " resiToma3='"+b.getResiToma3()+"', ";
				qry+= " resiToma4='"+b.getResiToma4()+"', ";
				qry+= " resiToma5='"+b.getResiToma5()+"', ";
				qry+= " resiToma6='"+b.getResiToma6()+"', ";
				qry+= " resiToma7='"+b.getResiToma7()+"', ";
				qry+= " resiToma8='"+b.getResiToma8()+"', ";
				qry+= " resiToma9='"+b.getResiToma9()+"', ";
				qry+= " resiToma10='"+b.getResiToma10()+"', ";
				qry+= " resiToma11='"+b.getResiToma11()+"', ";
				qry+= " resiToma12='"+b.getResiToma12()+"', ";
				qry+= " resiToma13='"+b.getResiToma13()+"', ";
				qry+= " resiToma14='"+b.getResiToma14()+"', ";
				qry+= " resiToma15='"+b.getResiToma15()+"', ";
				qry+= " resiToma16='"+b.getResiToma16()+"', ";
				qry+= " resiToma17='"+b.getResiToma17()+"', ";
				qry+= " resiToma18='"+b.getResiToma18()+"', ";
				qry+= " resiToma19='"+b.getResiToma19()+"', ";
				qry+= " resiToma20='"+b.getResiToma20()+"', ";
				qry+= " resiToma21='"+b.getResiToma21()+"', ";
				qry+= " resiToma22='"+b.getResiToma22()+"', ";
				qry+= " resiToma23='"+b.getResiToma23()+"', ";
				qry+= " resiToma24='"+b.getResiToma24()+"', ";
				//qry+= " validar='"+validar+"', ";
				qry+= " validar='"+b.getValidar()+"', ";
				//qry+= " confirmar='"+b.getConfirmar()+"', ";
				String confirmar =b.getIncidencia()!=null&&b.getIncidencia().equalsIgnoreCase("SI")?SPDConstants.REGISTRO_CONFIRMAR:b.getConfirmar();
				qry+= " confirmar='"+confirmar+"', ";
				qry+= " confirmaciones='"+b.getConfirmaciones()+"', ";
				qry+= " incidencia='"+b.getIncidencia()+"', ";
				//qry+= "  mensajesInfo=coalesce(''"+b.getMensajesInfo()+"', ''), ";
				//qry+= "  mensajesAlerta=coalesce(''"+b.getMensajesAlerta()+"', ''), ";
				qry+= " mensajesInfo='"+b.getMensajesInfo()+"', ";
				qry+= " mensajesAlerta='"+b.getMensajesAlerta()+"', ";
				qry+= " mensajesResidencia='"+b.getMensajesResidencia()+"', ";
				
				qry+= " idEstado='"+b.getIdEstado()+"', ";
				
				//si venimos del refresh no actualizamos a editar
				if(metodoLlamada==null || metodoLlamada.equals("") || !metodoLlamada.equalsIgnoreCase("refrescar")) 
					qry+= " editado='SI', ";
				
				qry+= "  controlValidacionDatos='"+b.getControlValidacionDatos()+"', ";
				qry+= "  controlRegistroRobot='"+b.getControlRegistroRobot()+"', ";
				qry+= "  controlRegistroAnterior='"+b.getControlRegistroAnterior()+"', ";
				qry+= "  controlPrincipioActivo='"+b.getControlPrincipioActivo()+"', ";
				qry+= "  controlNumComprimidos='"+b.getControlNumComprimidos()+"', ";
				qry+= "  controlNoSustituible='"+b.getControlNoSustituible()+"', ";
				qry+= "  controlDiferentesGtvmp='"+b.getControlDiferentesGtvmp()+"', ";
				qry+= "  controlUnicoGtvm='"+b.getControlUnicoGtvm()+"', ";
				qry+= "  previsionSPD='"+b.getPrevisionSPD()+"', ";
				
				qry+= " resultLog='"+b.getResultLog()+"', ";
				qry+= " resiPeriodo='"+b.getResiPeriodo()+"', ";
				qry+= " frecuencia="+b.getResiFrecuencia()+", ";
				qry+= " diasMesConcretos='"+b.getDiasMesConcretos()+"', ";
				qry+= " diasSemanaConcretos='"+b.getDiasSemanaConcretos()+"',";
				qry+= " diasSemanaMarcados="+b.getDiasSemanaMarcados()+ ",";
				qry+= " numeroDeTomas='"+b.getNumeroDeTomas()+"',";
				qry+= " secuenciaGuide='"+b.getSecuenciaGuide()+"',";
				qry+= " tipoEnvioHelium='"+ b.getTipoEnvioHelium()+"', ";
				qry+= " idTratamientoCIP='"+b.getIdTratamientoCIP()+"',  ";
				qry+= " idTratamientoSPD='"+b.getIdTratamientoSPD()+"'  ";
				qry+= "  WHERE idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
				
				qry+= "  AND oidFicheroResiDetalle = " + b.getOidFicheroResiDetalle();
				System.out.println(className + "--> edita con Bean -->  " +qry );		
	      		 
		    try {
		         PreparedStatement pstat = con.prepareStatement(qry);
		         result=pstat.executeUpdate();
		       
		     } catch (SQLException e) {
		         e.printStackTrace();
		     }finally {con.close();}

			
			return result>0;
		}

		
		public static boolean editaCIP(String spdUsuario, String idProceso, String CIP, String nombreResidente) throws Exception {

	        int result=0;
			  Connection con = Conexion.conectar();
				 CIP=StringUtil.limpiarTextoyEspacios(CIP);
			
	
			 String qry = " UPDATE dbo.SPD_ficheroResiDetalle ";
		  	 	qry+= "  SET resiCIP ='"+CIP+"'  ";
				qry+= "  WHERE idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";

		  	    qry+= "  AND idProceso='"+ idProceso +"' "; 
		  	    qry+= "  AND resiNombrePaciente = '" + nombreResidente + "'" ;
				System.out.println(className + "-->  editaCIP  -->  " +qry );		
	      		 
		    try {
		         PreparedStatement pstat = con.prepareStatement(qry);
		         result=pstat.executeUpdate();
		       
		     } catch (SQLException e) {
		         e.printStackTrace();
		     }finally {con.close();}

			
			return result>0;
		}
		


		public static List<FicheroResiBean> getEstados(String spdUsuario ) throws Exception {
				   Connection con = Conexion.conectar();
				   List<FicheroResiBean>  result = new ArrayList();
				   String qry = "SELECT distinct g.idEstado AS idEstado";
				    		qry+=  " FROM dbo.SPD_ficheroResiDetalle g  ";
							qry+= "  WHERE g.idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
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

				 	     return result;
				 	 }

		/**
		 * Método que retorna el número de CIPS que vienen en listado 
		 * @param idDivisionResidencia
		 * @param idProceso
		 * @return
		 * @throws Exception 
		 */
		public static int getCipsTotalesCargaFichero(String spdUsuario, String idDivisionResidencia, String idProceso) throws Exception {
			Connection con = Conexion.conectar();
			String qry = "SELECT count(distinct g.resiCIP) AS quants";
			qry+=  " FROM dbo.SPD_ficheroResiDetalle g  ";
	   		
			qry+= "  WHERE  EXISTS ( " + VisibilidadHelper.oidDivisionResidenciasVisiblesExists(spdUsuario, "g.idDivisionResidencia")  + ")";

			qry+=  " AND g.idDivisionResidencia='"+idDivisionResidencia+"' ";
	   		qry+=  " AND g.idProceso='"+idProceso+"'";
	   		qry+=  " AND g.resiCIP <>'' ";
	   		qry+=  " AND g.resiCIP IS NOT NULL ";
	   		
	   		System.out.println(className + "--> getCipsTotalesCargaFichero --> " +qry );		
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
		
		
		/**
		 * Método que retorna el número de CIPS que vienen en listado que no existen en la bbdd de pacientes
		 * @param idDivisionResidencia
		 * @param idProceso
		 * @return
		 * @throws Exception 
		 */
		public static int getCipsNoExistentesBbdd(String spdUsuario, String idDivisionResidencia, String idProceso) throws Exception {
			Connection con = Conexion.conectar();
			String qry = "SELECT COUNT(distinct g.resiCIP) AS quants ";
			qry+= " FROM dbo.SPD_ficheroResiDetalle g  ";
			qry+= " WHERE g.idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";

		   	qry+= " AND g.idDivisionResidencia='"+idDivisionResidencia+"' ";
	   		qry+= " AND g.idProceso='"+idProceso+"' ";
	   		qry+= " AND g.resiCIP not in (select cip from bd_pacientes) ";
	   		qry+= " AND isdate(resiInicioTratamiento) = 1 "; // miramos que sean registros de tratamiento válidos, con fecha de tratamiento
	   		
	   		System.out.println(className + "--> getCipsNoExistentesBbdd  --> " +qry );		
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
		
		/**
		 * Método que retorna el número de CIPS que existen como SPD=S en la gestión de pacientes y no vienen en listado de la resi
		 * @param idDivisionResidencia
		 * @param idProceso
		 * @return
		 * @throws Exception 
		 */
		public static int getCipsSpdResiNoExistentesEnFichero(String spdUsuario, String idDivisionResidencia, String idProceso) throws Exception {
		
			Connection con = Conexion.conectar();

			String qry = " SELECT count(distinct p.cip) AS quants ";
			qry+=  " FROM dbo.bd_pacientes p  ";
			qry+=  " WHERE p.idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
			qry+=  " AND p.idDivisionResidencia='"+idDivisionResidencia+"' ";
			qry+=  " AND p.SPD='S' ";
	   		qry+=  " AND p.CIP ";
	   		qry+=  " NOT IN ( ";
	   		qry+=  " 			SELECT g.resiCIP FROM dbo.SPD_ficheroResiDetalle g   ";
	   		qry+=  " 			WHERE g.idDivisionResidencia='"+idDivisionResidencia+"' ";
	   		qry+=  " 			AND g.idProceso='"+idProceso+"' ";
	   		qry+=  " 	) ";
	   		
	   		
	   		System.out.println(className + "--> getCipsSpdResiNoExistentesEnFichero  --> " +qry );		
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

		
		/**
		 * Método que retorna el número de CIPs activos de SPD en una residencia concreta
		 * @param spdUsuario
		 * @param idDivisionResidencia
		 * @param idProceso
		 * @return
		 * @throws Exception
		 */
		
		public static int getCipsActivosSPD(String spdUsuario, String idDivisionResidencia) throws Exception {
			
			Connection con = Conexion.conectar();

			String qry = " SELECT count(distinct p.cip) AS quants ";
			qry+=  " FROM dbo.bd_pacientes p  ";
			qry+=  " WHERE p.idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
			qry+=  " AND p.idDivisionResidencia='"+idDivisionResidencia+"' ";
			qry+=  " AND p.SPD='S' ";
			qry+=  " AND p.ACTIVO='ACTIVO' ";
	   		
	   		
	   		System.out.println(className + "--> getCipsActivosSPD  --> " +qry );		
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
		
		
		
		public static int getNumeroMensajesInfo(String spdUsuario, String idDivisionResidencia, String idProceso)  throws Exception {
			Connection con = Conexion.conectar();

			String qry = "SELECT count(distinct g.mensajesInfo) AS quants ";
			qry+=  " FROM dbo.SPD_ficheroResiDetalle g  ";
			qry+=  " WHERE g.idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
	   		qry+=  " AND g.idDivisionResidencia='"+idDivisionResidencia+"' ";
	   		qry+=  " AND g.idProceso='"+idProceso+"' ";
		   	qry+=  " AND isdate(g.resiInicioTratamiento) =1 "; // miramos que sean registros de tratamiento válidos, con fecha de tratamiento
	   		
	   		System.out.println(className + "--> getNumeroMensajesInfo  --> " +qry );		
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
		
		public static int getNumeroMensajesAlerta(String spdUsuario, String idDivisionResidencia, String idProceso)  throws Exception {
			Connection con = Conexion.conectar();

			String qry = "SELECT count(distinct g.mensajesAlerta) as quants ";
			qry+=  " FROM dbo.SPD_ficheroResiDetalle g  ";
			qry+=  " WHERE g.idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
	   		qry+=  " AND g.idDivisionResidencia='"+idDivisionResidencia+"' ";
	   		qry+=  " AND g.idProceso='"+idProceso+"' ";
		   	qry+=  " AND isdate(g.resiInicioTratamiento) =1 "; // miramos que sean registros de tratamiento válidos, con fecha de tratamiento
	   		
	   		
	   		System.out.println(className + "--> getNumeroMensajesAlerta  --> " +qry );		
			     ResultSet resultSet = null;
			int result =0;
			try {
				PreparedStatement pstat = con.prepareStatement(qry);
			    resultSet = pstat.executeQuery();
			    resultSet.next();
			    result = resultSet.getInt("quants");

			   } catch (SQLException e) {
			       e.printStackTrace();
			   }
			finally {con.close();}
			   return result;
		   }

		public static boolean existeRegistro(String spdUsuario, String idDivisionResidencia, String idProceso, FicheroResiBean medResi)   throws Exception {
			
			Connection con = Conexion.conectar();

			String qry = "SELECT count(distinct g.idTratamientoCIP) AS quants ";
			qry+=  " FROM dbo.SPD_ficheroResiDetalle g  ";
			qry+=  " WHERE g.idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
	   		qry+=  " AND g.idDivisionResidencia='"+idDivisionResidencia+"' ";
	   		qry+=  " AND g.idProceso='"+idProceso+"' ";
	   		qry+=  " AND idTratamientoCIP='"+medResi.getIdTratamientoCIP()+"'";
	   		
	   		
	   		System.out.println(className + "--> existeRegistro  --> " +qry );		
			     ResultSet resultSet = null;
			int result =0;
			try {
				PreparedStatement pstat = con.prepareStatement(qry);
			    resultSet = pstat.executeQuery();
			    resultSet.next();
			    result = resultSet.getInt("quants");
			    if(result>0)
			    	System.out.println("  ATENCION, FILA DUPLICADA: " + medResi.getResiCIP() + " - " + medResi.getResiCn() );

			   } catch (SQLException e) {
			       e.printStackTrace();
			   }
			finally {con.close();}
			   return result>0;
		   }
		
		
		/**
		 * En el caso de Aegerus nos interesa saber si existe el registro PROCESO-CIP-CN para ir actualizando las pautas
		 * @param spdUsuario
		 * @param idDivisionResidencia
		 * @param CIP
		 * @param CN
		 * @return
		 * @throws Exception
		 */
		
		public static boolean existeRegistroAegerus(String spdUsuario, String idProceso, String idDivisionResidencia, String CIP, String CN)   throws Exception {
			
			Connection con = Conexion.conectar();

			String qry = "SELECT count(*) AS quants ";
			qry+=  " FROM dbo.SPD_ficheroResiDetalle g  ";
			qry+=  " WHERE g.idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
	   		qry+=  " AND g.idDivisionResidencia='"+idDivisionResidencia+"' ";
	   		qry+=  " AND g.idProceso='"+idProceso+"' ";
	   		qry+=  " AND g.resiCIP='"+CIP+"' ";
	   		qry+=  " AND g.resiCN='"+CN+"'";
	   		
	   		
	   		System.out.println(className + "--> existeRegistroAegerus  --> " +qry );		
			     ResultSet resultSet = null;
			int result =0;
			try {
				PreparedStatement pstat = con.prepareStatement(qry);
			    resultSet = pstat.executeQuery();
			    resultSet.next();
			    result = resultSet.getInt("quants");
			    

			   } catch (SQLException e) {
			       e.printStackTrace();
			   }
			finally {con.close();}
			   return result>0;
		   }


		/**
		 * Método que retorna el último tratamiento válido del residente en el que coincide EXACTAMENTE el cn, comentarios, observaciones y periodo, y es diferente al idProceso actual.
		 * Lo queremos para recuperar los datos de frecuencia, dias marcados del mes, etc que nos interese
		 * @param medResiActual
		 * @return
		 * @throws Exception 
		 */
		public static List<FicheroResiBean> getUltimoFicheroResiDetalle(String spdUsuario, FicheroResiBean medResiActual, boolean porDetallRow) throws Exception
		{
			return getUltimoFicheroResiDetalle(spdUsuario, medResiActual, porDetallRow, false);
		}
		public static List<FicheroResiBean> getUltimoFicheroResiDetalle(String spdUsuario, FicheroResiBean medResiActual, boolean porDetallRow, boolean historico) throws Exception {
		
			Connection con = Conexion.conectar();
			List<FicheroResiBean>  list = new ArrayList<FicheroResiBean>();
			String detalleRow=medResiActual.getDetalleRow();
			String detalleRowKey=medResiActual.getDetalleRowKey();
			String detalleRowKeyLite=medResiActual.getDetalleRowKeyLite();
			
			medResiActual.getDetalleRowKeyLite();

			
			//caso particular de Aegerus
			boolean esAegerus =medResiActual.getIdProcessIospd()!=null && medResiActual.getIdProcessIospd().equalsIgnoreCase(SPDConstants.IDPROCESO_AEGERUS);    
			if(esAegerus) detalleRow = AegerusHelper.getDetalleRowAegerus(detalleRow);

			//boolean esMontseny =medResiActual.getIdProcessIospd()!=null && medResiActual.getIdProcessIospd().equalsIgnoreCase(SPDConstants.IDPROCESO_MONTSENY);    
			//	if(esMontseny) porDetallRow=false;
			
		//	boolean esExcelStauros =medResiActual.getIdProcessIospd()!=null && medResiActual.getIdProcessIospd().equalsIgnoreCase(SPDConstants.IDPROCESO_STAUROS);    
		//	if(esExcelStauros) porDetallRow=false;

			
			String qry = "SELECT g.*, d.idProcessIospd ";
			qry+=  " FROM dbo.SPD_ficheroResiDetalle g LEFT JOIN  dbo.bd_divisionResidencia d  on g.idDivisionResidencia=d.idDivisionResidencia ";
			qry+=  " WHERE 1=1 ";
			//qry+=  " AND g.idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
			if(porDetallRow)
			{
				if(esAegerus && detalleRow!=null)	//no filtramos por las fechas
				{
					if(AegerusHelper.getDetalleRowAegerus(detalleRow)!=null && !AegerusHelper.getDetalleRowAegerus(detalleRow).equals(""))
						qry+=  " AND ( g.detalleRow like '"+AegerusHelper.getDetalleRowAegerus(detalleRow)+"%'"; 
					if(AegerusHelper.getDetalleRowAegerus(HelperSPD.getDetalleRowFechasOk(detalleRow))!=null && !AegerusHelper.getDetalleRowAegerus(HelperSPD.getDetalleRowFechasOk(detalleRow)).equals(""))
						qry+=  " OR g.detalleRow like '"+AegerusHelper.getDetalleRowAegerus(HelperSPD.getDetalleRowFechasOk(detalleRow))+"%' ";
					if(detalleRow!=null && !detalleRow.equals(""))
						qry+=  " OR g.detalleRow like '"+detalleRow+"%'";
					if(detalleRowKey!=null && !detalleRowKey.equals(""))
						qry+=  " OR g.detalleRowKey like '"+detalleRowKey+"%'";
					if(detalleRowKeyLite!=null && !detalleRowKeyLite.equals("")&& !detalleRowKeyLite.equalsIgnoreCase("NULL")) 
						qry+=  " OR g.detalleRowKeyLite like '"+detalleRowKeyLite+"%'";
					qry+=  " )";

				}
				else if (detalleRow!=null)
				{
					
					
				qry+=  " AND ( ";
				qry+=  "  g.detalleRow ='"+StringUtil.quitaEspacios(HelperSPD.getDetalleRowFechasOk(detalleRow).toUpperCase())+"' COLLATE Cyrillic_General_CI_AI ";
				qry+=  "  OR g.detalleRow ='"+StringUtil.quitaEspacios(HelperSPD.convertirAFechaNumerica(detalleRow).toUpperCase())+"' COLLATE Cyrillic_General_CI_AI ";

				if(detalleRowKey!=null && !detalleRowKey.equals(""))
				{
					qry+=  "  OR g.detalleRow  ='"+detalleRowKey+"'  ";
					qry+=  "  OR  g.detalleRowKey ='"+detalleRowKey+"'  ";
				}
				if(detalleRow!=null && !detalleRow.equals(""))
				{
					qry+=  "  OR g.detalleRow ='"+detalleRow+"'  ";
					qry+=  "  OR g.detalleRowKey ='"+detalleRow+"'  ";
				}
				if(detalleRowKeyLite!=null && !detalleRowKeyLite.equals("")&& !detalleRowKeyLite.equalsIgnoreCase("NULL")) 
					qry+=  "  OR g.detalleRowKeyLite = '"+detalleRowKeyLite+"%'";
						
						/*
						 * + " OR UPPER(REPLACE(g.detalleRow, ' ', '')) COLLATE Cyrillic_General_CI_AI ='"+StringUtil.quitaEspaciosYAcentos(detalleRow, true)+"' COLLATE Cyrillic_General_CI_AI "
						+ " OR UPPER(REPLACE(g.detalleRowKey, ' ', '')) COLLATE Cyrillic_General_CI_AI ='"+StringUtil.quitaEspaciosYAcentos(detalleRowKey, true)+"' COLLATE Cyrillic_General_CI_AI "
						+ " OR UPPER(REPLACE(g.detalleRow, ' ', '')) COLLATE Cyrillic_General_CI_AI ='"+StringUtil.quitaEspaciosYAcentos(detalleRowKey, true)+"' COLLATE Cyrillic_General_CI_AI "
						+ " OR UPPER(REPLACE(g.detalleRowKey, ' ', '')) COLLATE Cyrillic_General_CI_AI ='"+StringUtil.quitaEspaciosYAcentos(detalleRow, true)+"' COLLATE Cyrillic_General_CI_AI "
						*/
				qry+=  " )";
					
				}
				
				/*
				qry+=  " AND (  ";
				qry+=  " 	REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(g.detalleRow, ';', ''), ',', ''), '-', ''), '.', ''), ' ', '') ";				
				qry+=  " 	= ";
				qry+=  " 	'"+StringUtil.limpiarTextoDetalleRow(HelperSPD.getDetalleRowFechasOk(medResiActual.getDetalleRow()))+"' ";				
				qry+=  " 	OR REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(g.detalleRow, ';', ''), ',', ''), '-', ''), '.', ''), ' ', '') ";
				qry+=  " 	=  ";
				qry+=  " 	'"+StringUtil.limpiarTextoDetalleRow(medResiActual.getDetalleRow())+"' ";				
				qry+=  " )";
				*/
			}
			else 
				qry+=  " AND g.idTratamientoCIP ='"+medResiActual.getIdTratamientoCIP()+"' ";
			
	   		qry+=  " AND g.resiCIP = '"+medResiActual.getResiCIP()+"' ";
	   		qry+=  " AND g.idProceso <>'"+medResiActual.getIdProceso()+"' ";
	   		qry+=  " AND g.numeroDeTomas= '"+medResiActual.getNumeroDeTomas()+"' "; //para que coincida la cabecera
	   		qry+=  " AND (g.validar <> '"+SPDConstants.REGISTRO_VALIDAR+"' or g.validar is null) ";
	   		qry+=  " AND (g.confirmar <> '"+SPDConstants.REGISTRO_CONFIRMAR+"' or g.confirmar is null)   ";
			qry+=  " AND (g.incidencia <> 'SI' or g.confirmar is null) ";
			qry+=  " AND TIPOREGISTRO='LINEA' ";
		
	   		qry+=  " ORDER BY g.fechaInsert DESC ";

			
	   		System.out.println(className + "--> getAnterioresFicheroResiDetalle  --> " +qry );		
			  // 	 System.out.println("connected main" );
			ResultSet resultSet = null;
			int result =0;
			try {
				PreparedStatement pstat = con.prepareStatement(qry);
			    resultSet = pstat.executeQuery();
			    while (resultSet.next()) 
			    {
			    	FicheroResiBean c = creaFicheroResiBean(resultSet);
	    			c.setResiNombrePaciente(medResiActual.getResiNombrePaciente());
	    			c.setResiApellidos(medResiActual.getResiApellidos());
	    			c.setResiApellido1(medResiActual.getResiApellido1());
	    			c.setResiApellido2(medResiActual.getResiApellido2());
	    			c.setResiApellidosNombre(medResiActual.getResiApellidosNombre());
		 	        list.add(c);
		 	     }

			 } catch (SQLException e) {
			       e.printStackTrace();
			   }finally {con.close();}
			return   list; 

		}

		public static List<FicheroResiBean> getCabeceraFicheroResi(String spdUsuario, String idDivisionResidencia, String idProceso) throws Exception {
			return getCabeceraFicheroResi(spdUsuario, idDivisionResidencia, idProceso, false);
		}
		
		//public static List<FicheroResiBean> getCabeceraFicheroResi(String idDivisionResidencia, String idProceso, boolean recuperaCabeceraAnterior) throws ClassNotFoundException {
		public static List<FicheroResiBean> getCabeceraFicheroResi(String spdUsuario, String idDivisionResidencia, String idProceso, boolean historico) throws Exception {
			Connection con = Conexion.conectar();
			List<FicheroResiBean>  list = new ArrayList<FicheroResiBean>();
			String tabla = historico ? TABLA_HISTORICO : TABLA_ACTIVA;
			
			String qry = "SELECT g.*, d.idProcessIospd ";
			qry+=  " FROM " +tabla + "  g  LEFT JOIN  dbo.bd_divisionResidencia d  on g.idDivisionResidencia=d.idDivisionResidencia   ";
			qry+=  " WHERE 1=1 ";
			qry+=  " AND g.idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
	   		qry+=  " AND g.idDivisionResidencia='"+ idDivisionResidencia +"'";
	   		qry+=  " AND g.idProceso ='"+idProceso+"' ";

	   		qry+=  " AND g.tipoRegistro='CABECERA'";
	   		qry+=  " ORDER BY  g.fechaInsert desc ";
	
	   		
	   		System.out.println(className + "--> getCabeceraFicheroResi  --> " +qry );		
			  // 	 System.out.println("connected main" );
			ResultSet resultSet = null;
			int result =0;
			try {
				PreparedStatement pstat = con.prepareStatement(qry);
			    resultSet = pstat.executeQuery();
			    while (resultSet.next()) 
			    {
			    	FicheroResiBean c = creaFicheroResiBean(resultSet);
		 	        list.add(c);
		 	     }

			 } catch (SQLException e) {
			       e.printStackTrace();
			   }finally {con.close();}
			return   list; 

		}

		/**
		 * Una vez creada la nueva toma se actualiza a +1 el número de tomas del proceso
		 * @param cabeceraActual
		 * @return
		 * @throws Exception 
		 */
		public static boolean actualizaNumeroDeTomas(String spdUsuario, FicheroResiBean cabeceraActual) throws Exception {
	        int result=0;
			Connection con = Conexion.conectar();
			int numeroDeTomas=cabeceraActual.getNumeroDeTomas()+1;

			String qry = " UPDATE dbo.SPD_ficheroResiDetalle ";
			  	    qry+= "  SET numeroDeTomas ='"+numeroDeTomas+"' ";
					qry+=  " WHERE idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
			  	    qry+= "  AND idProceso='"+ cabeceraActual.getIdProceso() +"' "; 
			  	    qry+= "  AND idDivisionResidencia ='"+cabeceraActual.getIdDivisionResidencia() +"' ";   	    		
			  	  System.out.println(className + "--> actualizaNumeroDeTomas -->" +qry );		
		      		 
			    try {
			         PreparedStatement pstat = con.prepareStatement(qry);
			         result=pstat.executeUpdate();
			       
			     } catch (SQLException e) {
			         e.printStackTrace();
			     }finally {con.close();}

				
				return result>0;
			}
		
		
		
		public static boolean borraTomaDelProceso(String spdUsuario, FicheroResiBean cab, String resiTomaX) throws Exception {

	        int result=0;
	        int numeroDeTomas=cab.getNumeroDeTomas()-1;
	        
				  Connection con = Conexion.conectar();

				  
				 // String qry = "UPDATE dbo.SPD_ficheroResiDetalle SET numeroDeTomas=numeroDeTomas-1, " + resiTomaX + " = '' ";
				  String qry = "UPDATE dbo.SPD_ficheroResiDetalle SET numeroDeTomas="+numeroDeTomas;
				   // Generamos las actualizaciones de las columnas siguientes
			        for (int i = Integer.parseInt(resiTomaX.substring(8)); i < 24; i++) {
			            String currentColumn = "resiToma" + i;
			            String nextColumn = "resiToma" + (i + 1);
			            qry += ", " + currentColumn + " = " + nextColumn;
			        }
					qry+=  " WHERE idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
			  	    qry+= "  AND idProceso='"+ cab.getIdProceso() +"' "; 
			  	    qry+= "  AND idDivisionResidencia ='"+cab.getIdDivisionResidencia() +"' ";   	  
			  	    
				  
				  	  System.out.println(className + "--> borraTomaDelProceso -->" +qry );		
		      		 
			    try {
			         PreparedStatement pstat = con.prepareStatement(qry);
			         result=pstat.executeUpdate();
			       
			     } catch (SQLException e) {
			         e.printStackTrace();
			     }finally {con.close();}

				
				return result>0;
			}

		/**
		 * Una vez creada una nueva toma se inicializa la cabecera con el valor de la nueva toma 
		 * @param cab
		 * @param resiTomaX
		 * @param resiTomaXValue
		 * @return
		 * @throws Exception 
		 */
		public static boolean addTomaCabecera(String spdUsuario, FicheroResiBean cab, String resiTomaX, String resiTomaXValue) throws Exception {

	        int result=0;
				  Connection con = Conexion.conectar();
				  int nuevaPosicionToma=cab.getNumeroDeTomas() + 1;
				  String campoNuevaPosicionToma = "resiToma" + nuevaPosicionToma;
			  	   String qry = " UPDATE dbo.SPD_ficheroResiDetalle ";
			  	    qry+= "  SET " + campoNuevaPosicionToma + " = '"+resiTomaXValue+"', numeroDeTomas= " + nuevaPosicionToma ;
					qry+= "  WHERE idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
			  	    qry+= "  AND idProceso='"+ cab.getIdProceso() +"' "; 
			  	    qry+= "  AND idDivisionResidencia ='"+cab.getIdDivisionResidencia() +"' ";   	  
			  	    qry+= "  AND tipoRegistro='CABECERA' ";   	  
			  	    
			  	  System.out.println(className + "--> addToma -->" +qry );		
		      		 
			    try {
			         PreparedStatement pstat = con.prepareStatement(qry);
			         result=pstat.executeUpdate();
			       
			     } catch (SQLException e) {
			         e.printStackTrace();
			     }finally {con.close();}

				
				return result>0;
			}



		
		
		
		
		/**
		 * Una vez creada una nueva toma se inicializa a null (o 0 TO-DO) las líneas creadas
		 * @param cab
		 * @param resiTomaX
		 * @param resiTomaXValue
		 * @return
		 * @throws Exception 
		 */
		public static boolean addTomaLineas(String spdUsuario, FicheroResiBean cab) throws Exception {
	        int result=0;
	        int numeroDeTomas=cab.getNumeroDeTomas()+1;
				  Connection con = Conexion.conectar();

			  	   String qry = " UPDATE dbo.SPD_ficheroResiDetalle ";
			  	    qry+= "  SET  numeroDeTomas= " + numeroDeTomas ;
					qry+= " WHERE idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
			  	    qry+= "  AND idProceso='"+ cab.getIdProceso() +"' "; 
			  	    qry+= "  AND idDivisionResidencia ='"+cab.getIdDivisionResidencia() +"' ";   	  
			  	    qry+= "  AND tipoRegistro='LINEA' ";   	  
			  	    
			  	  System.out.println(className + "--> addToma -->" +qry );		
		      		 
			    try {
			         PreparedStatement pstat = con.prepareStatement(qry);
			         result=pstat.executeUpdate();
			       
			     } catch (SQLException e) {
			         e.printStackTrace();
			     }finally {con.close();}
				return result>0;
			}

		
		
		

		private static FicheroResiBean creaFicheroResiBean(ResultSet resultSet) throws SQLException {
			FicheroResiBean f = new FicheroResiBean();
	         if (resultSet!=null) {
		     	f.setIdDivisionResidencia(resultSet.getString("idDivisionResidencia"));
		     	try{f.setIdProcessIospd(resultSet.getString("idProcessIospd"));}catch(Exception e){}
		     	
	   			f.setOidFicheroResiDetalle(resultSet.getInt("oidFicheroResiDetalle"));
	   			f.setOidFicheroResiCabecera(resultSet.getInt("oidFicheroResiCabecera"));
				f.setFechaHoraProceso(resultSet.getDate("fechaInsert"));
				f.setIdProceso(resultSet.getString("idProceso"));
				f.setResiCIP(resultSet.getString("resiCIP"));
				String nombre=resultSet.getString("resiNombre");
				String apellido1=resultSet.getString("resiApellido1");
				String apellido2=resultSet.getString("resiApellido2");
				String apellidos=resultSet.getString("resiApellidos");
				//String apellidosNombre=apellido1 + " " + apellido2 + ", " + nombre;
				String apellidosNombre=resultSet.getString("resiApellidosNombre");
				try
				{
					//si no existe en bd_pacientes, solo ponemos apellido1 con todo lo que haya venido en el Excel, para poder mostrar los nombres o apellidos com
					nombre=(resultSet.getString("nom")!=null?resultSet.getString("nom"):nombre);
					apellido1=(resultSet.getString("apellido1")!=null?resultSet.getString("apellido1"):apellido1);
					apellido2=(resultSet.getString("apellido2")!=null?resultSet.getString("apellido2"):apellido2);
					apellidosNombre=(resultSet.getString("cognomsNom")!=null?resultSet.getString("cognomsNom"):apellidosNombre);

				}catch(Exception e){}
				
				try{
					f.setOidPaciente(String.valueOf(resultSet.getInt("oidPaciente")));
					if(f.getOidPaciente()!=null && f.getOidPaciente().equals("0"))
						f.setOidPaciente("");
					}catch(Exception e){};
				try{
					f.setResiPlanta(resultSet.getString("planta"));
					f.setResiHabitacion(resultSet.getString("habitacion"));
						}catch(Exception e){};

				f.setResiNombrePaciente(nombre!=null&&!nombre.equals("")?nombre:resultSet.getString("resiCIP"));

				f.setResiApellido1(apellido1);
				f.setResiApellido2(apellido2);
				f.setResiApellidos(apellido1 + " " + apellido2);
				f.setResiApellidosNombre(apellidosNombre);
				f.setResiCn(resultSet.getString("resiCn")!=null&&!resultSet.getString("resiCn").equalsIgnoreCase("null")?resultSet.getString("resiCn"):""); 
				f.setResiMedicamento(resultSet.getString("resiMedicamento")!=null&&!resultSet.getString("resiMedicamento").equalsIgnoreCase("null")?resultSet.getString("resiMedicamento"):""); 
				f.setResiInicioTratamiento(resultSet.getString("resiInicioTratamiento")!=null&&!resultSet.getString("resiInicioTratamiento").equalsIgnoreCase("null")?resultSet.getString("resiInicioTratamiento"):""); 
				f.setResiInicioTratamientoParaSPD(resultSet.getString("resiInicioTratamientoParaSPD")!=null&&!resultSet.getString("resiInicioTratamientoParaSPD").equalsIgnoreCase("null")?resultSet.getString("resiInicioTratamientoParaSPD"):""); 
				f.setResiFinTratamiento(resultSet.getString("resiFinTratamiento")!=null&&!resultSet.getString("resiFinTratamiento").equalsIgnoreCase("null")?resultSet.getString("resiFinTratamiento"):""); 
				f.setResiFinTratamientoParaSPD(resultSet.getString("resiFinTratamientoParaSPD")!=null&&!resultSet.getString("resiFinTratamientoParaSPD").equalsIgnoreCase("null")?resultSet.getString("resiFinTratamientoParaSPD"):""); 
		     	f.setResiToma1(resultSet.getString("resiToma1")!=null&&!resultSet.getString("resiToma1").equalsIgnoreCase("null")?resultSet.getString("resiToma1"):""); 
		     	f.setResiToma2(resultSet.getString("resiToma2")!=null&&!resultSet.getString("resiToma2").equalsIgnoreCase("null")?resultSet.getString("resiToma2"):""); 
		     	f.setResiToma3(resultSet.getString("resiToma3")!=null&&!resultSet.getString("resiToma3").equalsIgnoreCase("null")?resultSet.getString("resiToma3"):""); 
		     	f.setResiToma4(resultSet.getString("resiToma4")!=null&&!resultSet.getString("resiToma4").equalsIgnoreCase("null")?resultSet.getString("resiToma4"):""); 
		     	f.setResiToma5(resultSet.getString("resiToma5")!=null&&!resultSet.getString("resiToma5").equalsIgnoreCase("null")?resultSet.getString("resiToma5"):""); 
		     	f.setResiToma6(resultSet.getString("resiToma6")!=null&&!resultSet.getString("resiToma6").equalsIgnoreCase("null")?resultSet.getString("resiToma6"):""); 
		     	f.setResiToma7(resultSet.getString("resiToma7")!=null&&!resultSet.getString("resiToma7").equalsIgnoreCase("null")?resultSet.getString("resiToma7"):""); 
		     	f.setResiToma8(resultSet.getString("resiToma8")!=null&&!resultSet.getString("resiToma8").equalsIgnoreCase("null")?resultSet.getString("resiToma8"):""); 
		     	f.setResiToma9(resultSet.getString("resiToma9")!=null&&!resultSet.getString("resiToma9").equalsIgnoreCase("null")?resultSet.getString("resiToma9"):""); 
		     	f.setResiToma10(resultSet.getString("resiToma10")!=null&&!resultSet.getString("resiToma10").equalsIgnoreCase("null")?resultSet.getString("resiToma10"):""); 
		     	f.setResiToma11(resultSet.getString("resiToma11")!=null&&!resultSet.getString("resiToma11").equalsIgnoreCase("null")?resultSet.getString("resiToma11"):""); 
		     	f.setResiToma12(resultSet.getString("resiToma12")!=null&&!resultSet.getString("resiToma12").equalsIgnoreCase("null")?resultSet.getString("resiToma12"):""); 
		     	f.setResiToma13(resultSet.getString("resiToma13")!=null&&!resultSet.getString("resiToma13").equalsIgnoreCase("null")?resultSet.getString("resiToma13"):""); 
		     	f.setResiToma14(resultSet.getString("resiToma14")!=null&&!resultSet.getString("resiToma14").equalsIgnoreCase("null")?resultSet.getString("resiToma14"):""); 
		     	f.setResiToma15(resultSet.getString("resiToma15")!=null&&!resultSet.getString("resiToma15").equalsIgnoreCase("null")?resultSet.getString("resiToma15"):""); 
		     	f.setResiToma16(resultSet.getString("resiToma16")!=null&&!resultSet.getString("resiToma16").equalsIgnoreCase("null")?resultSet.getString("resiToma16"):""); 
		     	f.setResiToma17(resultSet.getString("resiToma17")!=null&&!resultSet.getString("resiToma17").equalsIgnoreCase("null")?resultSet.getString("resiToma17"):""); 
		     	f.setResiToma18(resultSet.getString("resiToma18")!=null&&!resultSet.getString("resiToma18").equalsIgnoreCase("null")?resultSet.getString("resiToma18"):""); 
		     	f.setResiToma19(resultSet.getString("resiToma19")!=null&&!resultSet.getString("resiToma19").equalsIgnoreCase("null")?resultSet.getString("resiToma19"):""); 
		     	f.setResiToma20(resultSet.getString("resiToma20")!=null&&!resultSet.getString("resiToma20").equalsIgnoreCase("null")?resultSet.getString("resiToma20"):""); 
		     	f.setResiToma21(resultSet.getString("resiToma21")!=null&&!resultSet.getString("resiToma21").equalsIgnoreCase("null")?resultSet.getString("resiToma21"):""); 
		     	f.setResiToma22(resultSet.getString("resiToma22")!=null&&!resultSet.getString("resiToma22").equalsIgnoreCase("null")?resultSet.getString("resiToma22"):""); 
		     	f.setResiToma23(resultSet.getString("resiToma23")!=null&&!resultSet.getString("resiToma23").equalsIgnoreCase("null")?resultSet.getString("resiToma23"):""); 
		     	f.setResiToma24(resultSet.getString("resiToma24")!=null&&!resultSet.getString("resiToma24").equalsIgnoreCase("null")?resultSet.getString("resiToma24"):""); 
		     	HelperSPD.eliminaTomasCero(f);			
		     	boolean hayAsteriscos=HelperSPD.hayNumerosAsteriscos(f);
		     	f.setRow(resultSet.getInt("row"));
		     	f.setTipoRegistro(resultSet.getString("tipoRegistro")!=null&&!resultSet.getString("tipoRegistro").equalsIgnoreCase("null")?resultSet.getString("tipoRegistro"):""); 
		     	f.setNumeroDeTomas(resultSet.getInt("numeroDeTomas"));
		     	f.setResiObservaciones(resultSet.getString("resiObservaciones")!=null&&!resultSet.getString("resiObservaciones").equalsIgnoreCase("null")?resultSet.getString("resiObservaciones"):""); 
				f.setResiComentarios(resultSet.getString("resiComentarios")!=null&&!resultSet.getString("resiComentarios").equalsIgnoreCase("null")?resultSet.getString("resiComentarios"):""); 
				f.setResiVariante(resultSet.getString("resiVariante")!=null&&!resultSet.getString("resiVariante").equalsIgnoreCase("null")?resultSet.getString("resiVariante"):""); 
				f.setResiTipoMedicacion(resultSet.getString("resiTipoMedicacion")!=null&&!resultSet.getString("resiTipoMedicacion").equalsIgnoreCase("null")?resultSet.getString("resiTipoMedicacion"):""); 
	    		 
				f.setResiSiPrecisa(resultSet.getString("resiSiPrecisa")!=null&&!resultSet.getString("resiSiPrecisa").equalsIgnoreCase("null")?resultSet.getString("resiSiPrecisa"):""); 
				f.setResiPeriodo(resultSet.getString("resiPeriodo")!=null&&!resultSet.getString("resiPeriodo").equalsIgnoreCase("null")?resultSet.getString("resiPeriodo"):""); 

				f.setIdTratamientoCIP(resultSet.getString("idTratamientoCIP")!=null&&!resultSet.getString("idTratamientoCIP").equalsIgnoreCase("null")?resultSet.getString("idTratamientoCIP"):""); 
				f.setIdTratamientoSPD(resultSet.getString("idTratamientoSPD")!=null&&!resultSet.getString("idTratamientoSPD").equalsIgnoreCase("null")?resultSet.getString("idTratamientoSPD"):""); 
				f.setSpdCnFinal(resultSet.getString("spdCnFinal")!=null&&!resultSet.getString("spdCnFinal").equals("")&&!resultSet.getString("spdCnFinal").equals("null")?resultSet.getString("spdCnFinal"):"");
				f.setSpdNombreBolsa(resultSet.getString("spdNombreBolsa")!=null&&!resultSet.getString("spdNombreBolsa").equals("")&&!resultSet.getString("spdNombreBolsa").equals("null")?resultSet.getString("spdNombreBolsa"):"");
				f.setSpdFormaMedicacion(resultSet.getString("spdFormaMedicacion")!=null&&!resultSet.getString("spdFormaMedicacion").equals("")&&!resultSet.getString("spdFormaMedicacion").equals("null")?resultSet.getString("spdFormaMedicacion"):"");
				f.setSpdAccionBolsa(resultSet.getString("spdAccionBolsa")!=null&&!resultSet.getString("spdAccionBolsa").equals("")&&!resultSet.getString("spdAccionBolsa").equals("null")?resultSet.getString("spdAccionBolsa"):"");
				f.setResiD1(resultSet.getString("resiD1")!=null&&!resultSet.getString("resiD1").equalsIgnoreCase("null")?resultSet.getString("resiD1"):""); 
				f.setResiD2(resultSet.getString("resiD2")!=null&&!resultSet.getString("resiD2").equalsIgnoreCase("null")?resultSet.getString("resiD2"):""); 
				f.setResiD3(resultSet.getString("resiD3")!=null&&!resultSet.getString("resiD3").equalsIgnoreCase("null")?resultSet.getString("resiD3"):""); 
				f.setResiD4(resultSet.getString("resiD4")!=null&&!resultSet.getString("resiD4").equalsIgnoreCase("null")?resultSet.getString("resiD4"):""); 
				f.setResiD5(resultSet.getString("resiD5")!=null&&!resultSet.getString("resiD5").equalsIgnoreCase("null")?resultSet.getString("resiD5"):""); 
				f.setResiD6(resultSet.getString("resiD6")!=null&&!resultSet.getString("resiD6").equalsIgnoreCase("null")?resultSet.getString("resiD6"):""); 
				f.setResiD7(resultSet.getString("resiD7")!=null&&!resultSet.getString("resiD7").equalsIgnoreCase("null")?resultSet.getString("resiD7"):""); 
				f.setResiDiasAutomaticos(resultSet.getString("diasAutomaticos")!=null&&!resultSet.getString("diasAutomaticos").equalsIgnoreCase("null")?resultSet.getString("diasAutomaticos"):""); 
				f.setFechaDesde(resultSet.getString("fechaDesde")!=null&&!resultSet.getString("fechaDesde").equalsIgnoreCase("null")?resultSet.getString("fechaDesde"):""); 
				f.setFechaHasta(resultSet.getString("fechaHasta")!=null&&!resultSet.getString("fechaHasta").equalsIgnoreCase("null")?resultSet.getString("fechaHasta"):""); 

				f.setResultLog(resultSet.getString("resultLog")!=null&&!resultSet.getString("resultLog").equalsIgnoreCase("null")?resultSet.getString("resultLog"):""); 
				f.setRow(resultSet.getInt("row"));
	  			f.setDetalleRow(HelperSPD.getDetalleRowFechasOk(resultSet.getString("detalleRow")!=null&&!resultSet.getString("detalleRow").equalsIgnoreCase("null")?resultSet.getString("detalleRow"):"")); 

	  			f.setDetalleRowKey(resultSet.getString("detalleRowKey")!=null&&!resultSet.getString("detalleRowKey").equalsIgnoreCase("null")?resultSet.getString("detalleRowKey"):""); 
	  			
				f.setMensajesAlerta(resultSet.getString("mensajesAlerta")==null||resultSet.getString("mensajesAlerta").equalsIgnoreCase("null")?"":resultSet.getString("mensajesAlerta").replace("//", "/"));
				
	  			String mensajesInfo=resultSet.getString("mensajesInfo")==null||resultSet.getString("mensajesInfo").equalsIgnoreCase("null")?"":resultSet.getString("mensajesInfo").replace("//", "/");
	  			f.setMensajesInfo(mensajesInfo);
				//f.setMensajesResidencia(resultSet.getString("mensajesResidencia")==null||resultSet.getString("mensajesResidencia").equalsIgnoreCase("null")?"":resultSet.getString("mensajesResidencia").replace("//", "/"));
				
				String mensajesResidencia=resultSet.getString("mensajesResidencia")==null||resultSet.getString("mensajesResidencia").equalsIgnoreCase("null")?"":resultSet.getString("mensajesResidencia").replace("//", "/");
				f.setMensajesResidencia(mensajesResidencia);

				f.setIncidencia(resultSet.getString("incidencia")!=null&&!resultSet.getString("incidencia").equalsIgnoreCase("null")?resultSet.getString("incidencia"):""); 
	  			f.setValidar(resultSet.getString("validar")!=null&&!resultSet.getString("validar").equalsIgnoreCase("null")?resultSet.getString("validar"):""); 
	  			f.setConfirmar(resultSet.getString("confirmar")!=null&&!resultSet.getString("confirmar").equalsIgnoreCase("null")?resultSet.getString("confirmar"):""); 
	  			f.setConfirmaciones(resultSet.getInt("confirmaciones"));
	  			f.setIdEstado(resultSet.getString("idEstado")!=null&&!resultSet.getString("idEstado").equalsIgnoreCase("null")?resultSet.getString("idEstado"):""); 
	  			f.setResiFrecuencia(resultSet.getInt("frecuencia"));
	  			//c.setDiasConToma(diasConToma);
	  			f.setDiasSemanaConcretos(resultSet.getString("diasSemanaConcretos")!=null&&!resultSet.getString("diasSemanaConcretos").equalsIgnoreCase("null")?resultSet.getString("diasSemanaConcretos"):""); 
	  			f.setDiasMesConcretos(resultSet.getString("diasMesConcretos")!=null&&!resultSet.getString("diasMesConcretos").equalsIgnoreCase("null")?resultSet.getString("diasMesConcretos"):""); 
	      		f.setTipoEnvioHelium(resultSet.getString("tipoEnvioHelium")!=null&&!resultSet.getString("tipoEnvioHelium").equalsIgnoreCase("null")?resultSet.getString("tipoEnvioHelium"):""); 
	  			f.setSecuenciaGuide(resultSet.getString("secuenciaGuide")!=null&&!resultSet.getString("secuenciaGuide").equalsIgnoreCase("null")?resultSet.getString("secuenciaGuide"):""); 
	  			if(resultSet.getString("editable")!=null && resultSet.getString("editable").equalsIgnoreCase("NO"))
	  				f.setEditable(false);
	  			else 	  				
	  				f.setEditable(true);
	  			try{f.setNumeroDeTomas(resultSet.getInt("numeroDeTomas"));}catch(Exception e){}
	  			try{f.setDiasSemanaMarcados(resultSet.getInt("diasSemanaMarcados"));}catch(Exception e){}
	  			try{f.setEditado(resultSet.getString("editado")!=null&&!resultSet.getString("editado").equalsIgnoreCase("null")?resultSet.getString("editado"):""); }catch(Exception e){}
	  			try{f.setControlNumComprimidos(resultSet.getString("controlNumComprimidos")!=null&&!resultSet.getString("controlNumComprimidos").equalsIgnoreCase("null")?resultSet.getString("controlNumComprimidos"):""); ;}catch(Exception e){}
	  			try{f.setControlPrincipioActivo(resultSet.getString("controlPrincipioActivo")!=null&&!resultSet.getString("controlPrincipioActivo").equalsIgnoreCase("null")?resultSet.getString("controlPrincipioActivo"):""); }catch(Exception e){}
	  			try{f.setControlRegistroAnterior(resultSet.getString("controlRegistroAnterior")!=null&&!resultSet.getString("controlRegistroAnterior").equalsIgnoreCase("null")?resultSet.getString("controlRegistroAnterior"):""); }catch(Exception e){}
	  			try{f.setControlRegistroRobot(resultSet.getString("controlRegistroRobot")!=null&&!resultSet.getString("controlRegistroRobot").equalsIgnoreCase("null")?resultSet.getString("controlRegistroRobot"):""); }catch(Exception e){}
	  			try{f.setControlDiferentesGtvmp(resultSet.getString("controlDiferentesGtvmp")!=null&&!resultSet.getString("controlDiferentesGtvmp").equalsIgnoreCase("null")?resultSet.getString("controlDiferentesGtvmp"):""); }catch(Exception e){}
	  			try{f.setControlUnicoGtvm(resultSet.getString("controlUnicoGtvm")!=null&&!resultSet.getString("controlUnicoGtvm").equalsIgnoreCase("null")?resultSet.getString("controlUnicoGtvm"):""); }catch(Exception e){}
	  			try{f.setControlValidacionDatos(resultSet.getString("controlValidacionDatos")!=null&&!resultSet.getString("controlValidacionDatos").equalsIgnoreCase("null")?resultSet.getString("controlValidacionDatos"):""); }catch(Exception e){}
	  			try{f.setControlNoSustituible(resultSet.getString("controlNoSustituible")!=null&&!resultSet.getString("controlNoSustituible").equalsIgnoreCase("null")?resultSet.getString("controlNoSustituible"):""); }catch(Exception e){}
	  			try{f.setPrevisionResi(resultSet.getFloat("previsionResi"));}catch(Exception e){}
	  			try{f.setPrevisionSPD(resultSet.getFloat("previsionSPD"));}catch(Exception e){}
	     	}

			return f;
		}

		private static FicheroResiBean creaFicheroResiBeanComparacion(ResultSet resultSet) throws SQLException {
			FicheroResiBean f = new FicheroResiBean();
	         if (resultSet!=null) {
		     	f.setIdDivisionResidencia(resultSet.getString("idDivisionResidencia"));
	   			f.setOidFicheroResiDetalle(resultSet.getInt("oidFicheroResiDetalle"));
	   			f.setOidFicheroResiCabecera(resultSet.getInt("oidFicheroResiCabecera"));
				f.setFechaHoraProceso(resultSet.getDate("fechaInsert"));
				f.setIdProceso(resultSet.getString("idProceso"));
				f.setResiCIP(resultSet.getString("resiCIP"));
				String nombre=resultSet.getString("resiNombre");
				String apellido1=resultSet.getString("resiApellido1");
				String apellido2=resultSet.getString("resiApellido2");
				String apellidos=resultSet.getString("resiApellidos");
				//String apellidosNombre=apellido1 + " " + apellido2 + ", " + nombre;
				String apellidosNombre=resultSet.getString("resiApellidosNombre");
				try
				{
					//si no existe en bd_pacientes, solo ponemos apellido1 con todo lo que haya venido en el Excel, para poder mostrar los nombres o apellidos com
					nombre=(resultSet.getString("nom")!=null?resultSet.getString("nom"):nombre);
					apellido1=(resultSet.getString("apellido1")!=null?resultSet.getString("apellido1"):apellido1);
					apellido2=(resultSet.getString("apellido2")!=null?resultSet.getString("apellido2"):apellido2);
					apellidosNombre=(resultSet.getString("cognomsNom")!=null?resultSet.getString("cognomsNom"):apellidosNombre);

				}catch(Exception e){
					
				}
				f.setResiNombrePaciente(nombre!=null&&!nombre.equals("")?nombre:resultSet.getString("resiCIP"));

				f.setResiApellido1(apellido1);
				f.setResiApellido2(apellido2);
				f.setResiApellidos(apellido1 + " " + apellido2);
				f.setResiApellidosNombre(apellidosNombre);
				f.setResiCn(resultSet.getString("resiCn"));
				f.setResiMedicamento(resultSet.getString("resiMedicamento"));
				f.setResiInicioTratamiento(resultSet.getString("resiInicioTratamiento"));
				f.setResiInicioTratamientoParaSPD(resultSet.getString("resiInicioTratamientoParaSPD"));
				f.setResiFinTratamiento(resultSet.getString("resiFinTratamiento"));
				f.setResiFinTratamientoParaSPD(resultSet.getString("resiFinTratamientoParaSPD"));
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
		     	HelperSPD.eliminaTomasCero(f);
		     	boolean hayAsteriscos=HelperSPD.hayNumerosAsteriscos(f);
		     	f.setRow(resultSet.getInt("row"));
		     	f.setTipoRegistro(resultSet.getString("tipoRegistro"));
		     	f.setNumeroDeTomas(resultSet.getInt("numeroDeTomas"));
		     	f.setResiObservaciones(resultSet.getString("resiObservaciones"));
				f.setResiComentarios(resultSet.getString("resiComentarios"));
				f.setResiVariante(resultSet.getString("resiVariante"));
				f.setResiTipoMedicacion(resultSet.getString("resiTipoMedicacion"));
	    		 
				f.setResiSiPrecisa(resultSet.getString("resiSiPrecisa"));
				f.setResiPeriodo(resultSet.getString("resiPeriodo"));

				f.setIdTratamientoCIP(resultSet.getString("idTratamientoCIP"));
				 
				f.setSpdCnFinal(resultSet.getString("spdCnFinal")!=null&&!resultSet.getString("spdCnFinal").equals("")&&!resultSet.getString("spdCnFinal").equals("null")?resultSet.getString("spdCnFinal"):"");
				f.setSpdNombreBolsa(resultSet.getString("spdNombreBolsa")!=null&&!resultSet.getString("spdNombreBolsa").equals("")&&!resultSet.getString("spdNombreBolsa").equals("null")?resultSet.getString("spdNombreBolsa"):"");
				f.setSpdFormaMedicacion(resultSet.getString("spdFormaMedicacion")!=null&&!resultSet.getString("spdFormaMedicacion").equals("")&&!resultSet.getString("spdFormaMedicacion").equals("null")?resultSet.getString("spdFormaMedicacion"):"");
				f.setSpdAccionBolsa(resultSet.getString("spdAccionBolsa")!=null&&!resultSet.getString("spdAccionBolsa").equals("")&&!resultSet.getString("spdAccionBolsa").equals("null")?resultSet.getString("spdAccionBolsa"):"");
				f.setResiD1(resultSet.getString("resiD1"));
				f.setResiD2(resultSet.getString("resiD2"));
				f.setResiD3(resultSet.getString("resiD3"));
				f.setResiD4(resultSet.getString("resiD4"));
				f.setResiD5(resultSet.getString("resiD5"));
				f.setResiD6(resultSet.getString("resiD6"));
				f.setResiD7(resultSet.getString("resiD7"));
				f.setResiDiasAutomaticos(resultSet.getString("diasAutomaticos"));
				f.setFechaDesde(resultSet.getString("fechaDesde"));
				f.setFechaHasta(resultSet.getString("fechaHasta"));
				 
				f.setResultLog(resultSet.getString("resultLog"));
				f.setRow(resultSet.getInt("row"));
	  			f.setDetalleRow(HelperSPD.getDetalleRowFechasOk(resultSet.getString("detalleRow")));
				f.setMensajesAlerta(resultSet.getString("mensajesAlerta")==null||resultSet.getString("mensajesAlerta").equalsIgnoreCase("null")?"":resultSet.getString("mensajesAlerta").replace("//", "/"));
				
	  			String mensajesInfo=resultSet.getString("mensajesInfo")==null||resultSet.getString("mensajesInfo").equalsIgnoreCase("null")?"":resultSet.getString("mensajesInfo").replace("//", "/");
	  			f.setMensajesInfo(mensajesInfo);
				//f.setMensajesResidencia(resultSet.getString("mensajesResidencia")==null||resultSet.getString("mensajesResidencia").equalsIgnoreCase("null")?"":resultSet.getString("mensajesResidencia").replace("//", "/"));
				
				String mensajesResidencia=resultSet.getString("mensajesResidencia")==null||resultSet.getString("mensajesResidencia").equalsIgnoreCase("null")?"":resultSet.getString("mensajesResidencia").replace("//", "/");
				f.setMensajesResidencia(mensajesResidencia);

				f.setIncidencia(resultSet.getString("incidencia"));
	  			f.setValidar(resultSet.getString("validar"));
	  			f.setConfirmar(resultSet.getString("confirmar"));
	  			f.setConfirmaciones(resultSet.getInt("confirmaciones"));
	  			
	  			f.setIdEstado(resultSet.getString("idEstado"));
	  			f.setResiFrecuencia(resultSet.getInt("frecuencia"));
	  			//c.setDiasConToma(diasConToma);
	  			f.setDiasSemanaConcretos(resultSet.getString("diasSemanaConcretos"));
	  			f.setDiasMesConcretos(resultSet.getString("diasMesConcretos"));
	      		f.setTipoEnvioHelium(resultSet.getString("tipoEnvioHelium"));
	  			f.setSecuenciaGuide(resultSet.getString("secuenciaGuide"));
	  			if(resultSet.getString("editable")!=null && resultSet.getString("editable").equalsIgnoreCase("NO"))
	  				f.setEditable(false);
	  			else 	  				
	  				f.setEditable(true);
	  			f.setNumeroDeTomas(resultSet.getInt("numeroDeTomas"));
	  			f.setDiasSemanaMarcados(resultSet.getInt("diasSemanaMarcados"));
	  			f.setEditado(resultSet.getString("editado"));
	  			f.setControlNumComprimidos(resultSet.getString("controlNumComprimidos"));
	  			f.setControlPrincipioActivo(resultSet.getString("controlPrincipioActivo"));
	  			f.setControlRegistroAnterior(resultSet.getString("controlRegistroAnterior"));
	  			f.setControlRegistroRobot(resultSet.getString("controlRegistroRobot"));
	  			f.setControlDiferentesGtvmp(resultSet.getString("controlDiferentesGtvmp"));
	  			f.setControlUnicoGtvm(resultSet.getString("controlUnicoGtvm"));
	  			f.setControlValidacionDatos(resultSet.getString("controlValidacionDatos"));
	  			f.setControlNoSustituible(resultSet.getString("controlNoSustituible"));
	  			f.setConfirmaciones(resultSet.getInt("confirmaciones"));
		     	

	     	}

			return f;
		}

		
		
		public boolean borrar(int i, String idDivisionResidencia, String idProceso) {
			// TODO Auto-generated method stub
			return false;
		}


		public static String getCIPPorNombreCompleto(String spdUsuario, FicheroResiBean frbean) throws Exception {

			String CIP =null;
			Connection con = Conexion.conectar();
			List<FicheroResiBean>  list = new ArrayList<FicheroResiBean>();
				 
			String qry = "SELECT distinct g.resiCIP ";
			qry+=  " FROM dbo.SPD_ficheroResiDetalle g  ";
			qry+=  " WHERE g.idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
		   	qry+=  " AND g.resiApellidosNombre='"+ frbean.getResiApellidosNombre()+"'";
		   	qry+=  " AND g.idProceso ='"+frbean.getIdProceso()+"' ";
		   	qry+=  " AND g.resiCIP IS NOT NULL AND g.resiCIP !='' ";

	   		System.out.println(className + "--> getCIPPorNombreCompleto  --> " +qry );		
			ResultSet resultSet = null;
			int result =0;
			try {
				PreparedStatement pstat = con.prepareStatement(qry);
			    resultSet = pstat.executeQuery();
			    int cuantos=0;
			    while (resultSet.next()) 
			    {
			    	CIP=resultSet.getString("resiCIP");
			    	cuantos++;
			    }
			    if(cuantos>1) CIP=null;	//si hay más de uno no insertamos nada por si hay dos residentes con el mismo nombre
			 } catch (SQLException e) {
			       e.printStackTrace();
			   }finally {
				   con.close();
				   
				   }
			return   CIP; 

		}


		public static boolean updateCIPVacios(String spdUsuario, FicheroResiBean frbean) throws Exception {
			boolean result=false;
					  Connection con = Conexion.conectar();

				  	   String qry = " UPDATE dbo.SPD_ficheroResiDetalle ";
				  	    qry+= "  SET resiCIP= '" + frbean.getResiCIP() + "', editado='SI' ";
						qry+=  " WHERE idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
				  	    qry+= "  AND idProceso='"+ frbean.getIdProceso() +"' "; 
				  	    qry+= "  AND resiApellidosNombre ='"+frbean.getResiApellidosNombre() +"' ";   	  
				  	    qry+=  " AND (resiCIP IS NULL OR resiCIP ='')";
				  	    
				  	  System.out.println(className + "--> updateCIPVacios -->" +qry );		
			      		 
				    try {
				         PreparedStatement pstat = con.prepareStatement(qry);
				         result=pstat.executeUpdate()>0;
				       
				     } catch (SQLException e) {
				         e.printStackTrace();
				     }finally {con.close();}
				 return result;
			}


		public static void actualizarKeyAntiguaAKeyNueva(String keyAntigua, String keyOk) throws Exception {
			boolean result=false;
			  Connection con = Conexion.conectar();

		  	   String qry = " UPDATE dbo.SPD_ficheroResiDetalle ";
		  	    qry+= "  SET idTratamientoCIP= '" + keyOk + "' ";
				qry+=  " WHERE idTratamientoCIP='"+ keyAntigua +"' "; 
		  	    
		  	  System.out.println(className + "--> actualizarKeyAntiguaAKeyNueva -->" +qry );		
	      		 
		    try {
		         PreparedStatement pstat = con.prepareStatement(qry);
		         result=pstat.executeUpdate()>0;
		       
		     } catch (SQLException e) {
		         e.printStackTrace();
		     }finally {con.close();}
		
	}


		public static int getLineasProceso(String idUsuario, String idDivisionResidencia, String idProceso) throws Exception {
			
	         int quants =0; 
	         Connection con = Conexion.conectar();
			String 	qry =  " SELECT count(*) as quants FROM dbo.SPD_ficheroResiDetalle g  ";
				qry+= " WHERE g.idDivisionResidencia ='"+ idDivisionResidencia+"' ";
				qry+= " AND g.tipoRegistro='LINEA'";
			if(idProceso!=null && !idProceso.equals(""))
				qry+= " AND g.idProceso = '"+idProceso +"' ";
			
		
	 		System.out.println(className + "--> getLineasProceso -->  " +qry );		
		    ResultSet resultSet = null;
	 	 	
	 	    try {
	 	         PreparedStatement pstat = con.prepareStatement(qry);
	 	         resultSet = pstat.executeQuery();
		 	         while (resultSet.next()) {
	 	        	 quants=resultSet.getInt("quants");
	 	            }
	 	     } catch (SQLException e) {
	 	         e.printStackTrace();
	 	     }finally {con.close();}
	 		System.out.println(className + "--> quants -->  " +quants );		
	 	     return quants  ;
		}



		public static boolean existeProcesosPosteriores(int oidDivisionResidencia, int oidFicheroResiCabecera) throws Exception {
			
	         int quants =0; 
	         Connection con = Conexion.conectar();
			String 	qry =  " SELECT count(*) as quants FROM dbo.SPD_ficheroResiDetalle g  ";
				qry+= " WHERE g.idDivisionResidencia IN ( select idDivisionResidencia from bd_divisionResidencia where oidDivisionResidencia='"+ oidDivisionResidencia+"' )";
				qry+= " AND g.tipoRegistro='CABECERA'";
			if(oidFicheroResiCabecera>0)
				qry+= " AND g.oidFicheroResiCabecera> '"+oidFicheroResiCabecera +"' ";
			
		
	 		System.out.println(className + "--> existeProcesosPosteriores -->  " +qry );		
		    ResultSet resultSet = null;
	 	 	
	 	    try {
	 	         PreparedStatement pstat = con.prepareStatement(qry);
	 	         resultSet = pstat.executeQuery();
		 	         while (resultSet.next()) {
	 	        	 quants=resultSet.getInt("quants");
	 	            }
	 	     } catch (SQLException e) {
	 	         e.printStackTrace();
	 	     }finally {con.close();}
	 		System.out.println(className + "--> quants -->  " +quants );		
	 	     return quants>0 ;
		}

		public static List<FicheroResiBean> getFilasConMensajesInfo(String spdUsuario, String idProceso) throws Exception {
			Connection con = Conexion.conectar();
			List<FicheroResiBean>  list = new ArrayList<FicheroResiBean>();
			 
			String qry = "SELECT g.*, d.idProcessIospd ";
			qry+=  " FROM dbo.SPD_ficheroResiDetalle g left join dbo.bd_divisionResidencia d  on g.idDivisionResidencia=d.idDivisionResidencia    ";
			qry+=  " WHERE g.idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
	   		qry+=  " AND g.idProceso = '"+idProceso+"' ";
	   		qry+=  " AND g.tipoRegistro = 'LINEA' ";
	   		qry+=  " AND ( 	";
	   		qry+=  " 	(g.mensajesAlerta <>'' AND g.mensajesAlerta is not null AND  UPPER(g.mensajesAlerta) <>'NULL' )	 		";
	   		qry+=  " 	OR	(g.mensajesResidencia <>'' AND g.mensajesResidencia is not null AND  UPPER(g.mensajesResidencia) <>'NULL' )	 		";
	   		qry+=  " 	OR	(g.mensajesInfo <>'' AND g.mensajesInfo is not null AND  UPPER(g.mensajesInfo) <>'NULL' )		";
	   		qry+=  " 	) 	";
	   		qry+=  " AND idProceso = '"+idProceso+"' ";
	   		qry+=  " ORDER BY g.mensajesResidencia desc, g.mensajesinfo desc, g.mensajesAlerta desc, g.resiCIP, g.resiMedicamento DESC ";

	   		
	   		System.out.println(className + "--> getFilasConMensajesInfo  --> " +qry );		
			  // 	 System.out.println("connected main" );
			ResultSet resultSet = null;
			int result =0;
			try {
				PreparedStatement pstat = con.prepareStatement(qry);
			    resultSet = pstat.executeQuery();
			    while (resultSet.next()) 
			    {
			    	FicheroResiBean c = creaFicheroResiBean(resultSet);

		 	        list.add(c);
		 	     }

			 } catch (SQLException e) {
			       e.printStackTrace();
			   }finally {con.close();}
			return   list; 


		}


		public static List<FicheroResiBean> getCambiosProcesos(String idUsuario, String proceso1, String proceso2) throws SQLException {
			Connection con = Conexion.conectar();
			List<FicheroResiBean>  list = new ArrayList<FicheroResiBean>();
			 
			String query= " SELECT ";
			query+=" 	 fechaInsert, 'idTratamientoCIP' as idTratamientoCIP, oidFicheroResiCabecera,  oidFicheroResiDetalle, 'idProceso' as idProceso,  idDivisionResidencia,  resiCIP, resiMedicamento, tipoRegistro  	 ";
			query+=" 	, 'resiNombre' as resiNombre, 'resiApellidosNombre' as resiApellidosNombre,  'resiApellido1' as resiApellido1, 'resiApellido2' as resiApellido2, 'resiApellidos' as resiApellidos ";
			query+=" 	, 'resiCn' as resiCn, 'resiInicioTratamiento' as  resiInicioTratamiento, 'resiFinTratamiento' as resiFinTratamiento, 'resiInicioTratamientoParaSPD' as resiInicioTratamientoParaSPD ";
			query+=" 	, 'resiFinTratamientoParaSPD' as resiFinTratamientoParaSPD, 'resiObservaciones' as resiObservaciones, 'resiComentarios' as resiComentarios, 'resiPeriodo' as resiPeriodo "; 
			query+=" 	, 'resiVariante' as resiVariante, 'resiSiPrecisa' as resiSiPrecisa,  'spdCIP' as spdCIP, 'spdCnFinal' as spdCnFinal, 'spdNombreBolsa' as spdNombreBolsa, 'spdFormaMedicacion' as spdFormaMedicacion ";	
			query+=" 	, 'spdAccionBolsa' as spdAccionBolsa, 'spdNomGtVmpp' as spdNomGtVmpp, 'D1' as resiD1, 'D2' as resiD2, 'D3' as resiD3, 'D4' as resiD4, 'D5' as resiD5, 'D6' as resiD6, 'D7' as resiD7    ";
			query+=" 	, 'resiViaAdministracion' as resiViaAdministracion, resiToma1, resiToma2, resiToma3, resiToma4, resiToma5, resiToma6, resiToma7, resiToma8, resiToma9, resiToma10, resiToma11, resiToma12 ";
			query+=" 	, resiToma13, resiToma14, resiToma15, resiToma16, resiToma17, resiToma18, resiToma19, resiToma20, resiToma21, resiToma22, resiToma23, resiToma24 ";
			query+=" 	, row, 'diasSemanaConcretos' as diasSemanaConcretos, 'tipoEnvioHelium' as tipoEnvioHelium, 'secuenciaGuide' as secuenciaGuide, numeroDeTomas, 'resiTipoMedicacion' as resiTipoMedicacion ";
			query+=" 	, diasSemanaMarcados, 'editable' as editable, 'filaResidencia' as detalleRow, 'diasAutomaticos' as diasAutomaticos , 'fechaDesde' as fechaDesde, 'fechaHasta' as fechaHasta, 'incidencia' as incidencia ";
			query+=" 	, 'resultLog' as resultLog, 'editado' as editado, 'idTratamientoSPD' as idTratamientoSPD, 'mensajesAlerta' as mensajesAlerta, 'mensajesResidencia' as mensajesResidencia,  'mensajesInfo' as mensajesInfo ";    
			query+=" 	, 'validar' as validar, 'confirmar' as confirmar, confirmaciones, 'idEstado' as idEstado, frecuencia, 'diasMesConcretos' as diasMesConcretos, 'controlNumComprimidos' as controlNumComprimidos "; 
			query+="  FROM SPD_ficheroResiDetalle f where f.idProceso ='"+proceso1+"'  and f.tipoRegistro='CABECERA' ";
			query+=" UNION  ";
			query+=" SELECT  ";
			query+=" 	ISNULL(f1.fechaInsert, f2.fechaInsert) as fechaInsert,  ";
			query+=" 	ISNULL(f1.idTratamientoCIP, f2.idTratamientoCIP) as idTratamientoCIP,  ";
			query+=" 	-1  as oidFicheroResiCabecera,  ";
			query+=" 	-1  as oidFicheroResiDetalle,  ";
			query+=" 	''  as idProceso,  ";
			query+=" 	ISNULL(f1.idDivisionResidencia, f2.idDivisionResidencia) as idDivisionResidencia ,  ";
			query+=" 	ISNULL(f1.resiCIP, f2.resiCIP) as resiCIP ,  ";
			query+=" 	ISNULL(f1.resiMedicamento, f2.resiMedicamento) as resiMedicamento ,  ";
			query+=" 	CASE   ";
			query+=" 		WHEN (f1.idTratamientoCIP IS NULL) THEN '"+proceso1+"'  ";
			query+=" 		ELSE '"+proceso2+"'  ";
			query+=" 	END  tipoRegistro,  ";
			query+=" 	ISNULL(f1.resiNombre, f2.resiNombre) as resiNombre,  ";
			query+=" 	ISNULL(f1.resiApellido1, f2.resiApellido1) as resiApellido1,  ";
			query+=" 	ISNULL(f1.resiApellido2, f2.resiApellido2) as resiApellido2,  ";
			query+=" 	ISNULL(f1.resiApellidos, f2.resiApellidos) as resiApellidos,  ";
			query+=" 	ISNULL(f1.resiApellidosNombre, f2.resiApellidosNombre) as resiApellidosNombre,  ";
			query+=" 	ISNULL(f1.resiCn, f2.resiCn) as resiCn,  ";
			query+=" 	ISNULL(f1.resiInicioTratamiento, f2.resiInicioTratamiento) as resiInicioTratamiento,  ";
			query+=" 	ISNULL(f1.resiFinTratamiento, f2.resiFinTratamiento) as resiFinTratamiento,  ";
			query+=" 	ISNULL(f1.resiInicioTratamientoParaSPD, f2.resiInicioTratamientoParaSPD) as resiInicioTratamientoParaSPD,  ";
			query+=" 	ISNULL(f1.resiFinTratamientoParaSPD, f2.resiFinTratamientoParaSPD) as resiFinTratamientoParaSPD,  ";
			query+=" 	ISNULL(f1.resiObservaciones, f2.resiObservaciones) as resiObservaciones,  ";
			query+=" 	ISNULL(f1.resiComentarios, f2.resiComentarios) as resiComentarios,  ";
			query+=" 	ISNULL(f1.resiPeriodo, f2.resiPeriodo) as resiPeriodo,  ";
			query+=" 	ISNULL(f1.resiVariante, f2.resiVariante) as resiVariante,  ";
			query+=" 	ISNULL(f1.resiSiPrecisa, f2.resiSiPrecisa) as resiSiPrecisa,  ";
			query+=" 	ISNULL(f1.spdCIP, f2.spdCIP) as spdCIP,  ";
			query+=" 	ISNULL(f1.spdCnFinal, f2.spdCnFinal) as spdCnFinal,  ";
			query+=" 	ISNULL(f1.spdNombreBolsa, f2.spdNombreBolsa) as spdNombreBolsa,  ";
			query+=" 	ISNULL(f1.spdFormaMedicacion, f2.spdFormaMedicacion) as spdFormaMedicacion,  ";
			query+=" 	ISNULL(f1.spdAccionBolsa, f2.spdAccionBolsa) as spdAccionBolsa,  ";
			query+=" 	ISNULL(f1.spdNomGtVmpp, f2.spdNomGtVmpp) as spdNomGtVmpp,  ";
			query+=" 	ISNULL(f1.resiD1, f2.resiD1) as resiD1,  ";
			query+=" 	ISNULL(f1.resiD2, f2.resiD2) as resiD2,  ";
			query+=" 	ISNULL(f1.resiD3, f2.resiD3) as resiD3,  ";
			query+=" 	ISNULL(f1.resiD4, f2.resiD4) as resiD4,  ";
			query+=" 	ISNULL(f1.resiD5, f2.resiD5) as resiD5,  ";
			query+=" 	ISNULL(f1.resiD6, f2.resiD6) as resiD6,  ";
			query+=" 	ISNULL(f1.resiD7, f2.resiD7) as resiD7,  ";
			query+=" 	ISNULL(f1.resiViaAdministracion, f2.resiViaAdministracion) as resiViaAdministracion,  ";
			query+=" 	ISNULL(f1.resiToma1, f2.resiToma1) as resiToma1,  ";
			query+=" 	ISNULL(f1.resiToma2, f2.resiToma2) as resiToma2,  ";
			query+=" 	ISNULL(f1.resiToma3, f2.resiToma3) as resiToma3,  ";
			query+=" 	ISNULL(f1.resiToma4, f2.resiToma4) as resiToma4,  ";
			query+=" 	ISNULL(f1.resiToma5, f2.resiToma5) as resiToma5,  ";
			query+=" 	ISNULL(f1.resiToma6, f2.resiToma6) as resiToma6,  ";
			query+=" 	ISNULL(f1.resiToma7, f2.resiToma7) as resiToma7,  ";
			query+=" 	ISNULL(f1.resiToma8, f2.resiToma8) as resiToma8,  ";
			query+=" 	ISNULL(f1.resiToma9, f2.resiToma9) as resiToma9,  ";
			query+=" 	ISNULL(f1.resiToma10, f2.resiToma10) as resiToma10,  ";
			query+=" 	ISNULL(f1.resiToma11, f2.resiToma11) as resiToma11,  ";
			query+=" 	ISNULL(f1.resiToma12, f2.resiToma12) as resiToma12,  ";
			query+=" 	ISNULL(f1.resiToma13, f2.resiToma13) as resiToma13,  ";
			query+=" 	ISNULL(f1.resiToma14, f2.resiToma14) as resiToma14,  ";
			query+=" 	ISNULL(f1.resiToma15, f2.resiToma15) as resiToma15,  ";
			query+=" 	ISNULL(f1.resiToma16, f2.resiToma16) as resiToma16,  ";
			query+=" 	ISNULL(f1.resiToma17, f2.resiToma17) as resiToma17,  ";
			query+=" 	ISNULL(f1.resiToma18, f2.resiToma18) as resiToma18,  ";
			query+=" 	ISNULL(f1.resiToma19, f2.resiToma19) as resiToma19,  ";
			query+=" 	ISNULL(f1.resiToma20, f2.resiToma20) as resiToma20,  ";
			query+=" 	ISNULL(f1.resiToma21, f2.resiToma21) as resiToma21,  ";
			query+=" 	ISNULL(f1.resiToma22, f2.resiToma22) as resiToma22,  ";
			query+=" 	ISNULL(f1.resiToma23, f2.resiToma23) as resiToma23,  ";
			query+=" 	ISNULL(f1.resiToma24, f2.resiToma24) as resiToma24,  ";
			query+=" 	ISNULL(f1.row, f2.row) as row,  ";
			query+=" 	ISNULL(f1.diasSemanaConcretos, f2.diasSemanaConcretos) as diasSemanaConcretos, ";
			query+=" 	ISNULL(f1.tipoEnvioHelium,  f2.tipoEnvioHelium) as tipoEnvioHelium, ";
			query+=" 	ISNULL(f1.secuenciaGuide,  f2.secuenciaGuide) as secuenciaGuide, ";
			query+=" 	ISNULL(f1.numeroDeTomas, f2.numeroDeTomas) as numeroDeTomas, ";
			query+=" 	ISNULL(f1.resiTipoMedicacion, f2.resiTipoMedicacion) as resiTipoMedicacion, ";
			query+=" 	ISNULL(f1.diasSemanaMarcados, f2.diasSemanaMarcados) as diasSemanaMarcados , ";
			query+=" 	ISNULL(f1.editable,    f2.editable) as editable , ";
			query+=" 	ISNULL(f1.detalleRow, f2.detalleRow) as detalleRow, ";
			query+=" 	ISNULL(f1.diasAutomaticos, f2.diasAutomaticos) as diasAutomaticos, ";
			query+=" 	ISNULL(f1.fechaDesde, f2.fechaDesde) as fechaDesde, ";
			query+=" 	ISNULL(f1.fechaHasta, f2.fechaHasta) as fechaHasta, ";
			query+=" 	ISNULL(f1.fechaHasta, f2.fechaHasta) as fechaHasta, ";
			query+=" 	ISNULL(f1.incidencia, f2.incidencia) as incidencia, ";
			
			query+=" 	ISNULL(f1.resultLog,  f2.resultLog) as resultLog, ";			
			query+=" 	ISNULL(f1.idTratamientoSPD,  f2.idTratamientoSPD) as idTratamientoSPD, ";
			query+=" 	ISNULL(f1.mensajesAlerta,  f2.mensajesAlerta) as mensajesAlerta, ";
			query+=" 	ISNULL(f1.mensajesResidencia,  f2.mensajesResidencia) as mensajesResidencia, ";
			query+=" 	ISNULL(f1.mensajesInfo,  f2.mensajesInfo) as mensajesInfo, ";
			query+=" 	ISNULL(f1.validar,  f2.validar) as validar, ";
			query+=" 	ISNULL(f1.confirmar,  f2.confirmar) as confirmar, ";
			query+=" 	ISNULL(f1.confirmaciones,  f2.confirmaciones) as confirmaciones, ";
			query+=" 	ISNULL(f1.idEstado,  f2.idEstado) as idEstado, ";
			query+=" 	ISNULL(f1.frecuencia,  f2.frecuencia) as frecuencia, ";
			query+=" 	ISNULL(f1.diasMesConcretos, f2.diasMesConcretos) as diasMesConcretos,  ";
			query+=" 	ISNULL(f1.controlNumComprimidos, f2.controlNumComprimidos) as controlNumComprimidos  ";
			
			query+=" FROM  ";
			query+=" ( ";
			query+=" 	SELECT * FROM SPD_ficheroResiDetalle WHERE idProceso='"+proceso1+"' ";
			query+=" )f1  ";
			query+=" FULL OUTER JOIN   ";
			query+=" ( ";
			query+=" 	SELECT * FROM SPD_ficheroResiDetalle WHERE idProceso='"+proceso2+"' ";
			query+=" )f2  ";
			query+=" ON f1.idTratamientoCIP=f2.idTratamientoCIP ";
			query+=" WHERE f1.idTratamientoCIP IS NULL or f2.idTratamientoCIP IS NULL ";
			query+=" ORDER BY resiCIP, resiMedicamento, tipoRegistro ASC ";


	   		
	   		System.out.println(className + "--> getFilasConMensajesInfo  --> " +query );		
			  // 	 System.out.println("connected main" );
			ResultSet resultSet = null;
			int result =0;
			try {
				PreparedStatement pstat = con.prepareStatement(query);
			    resultSet = pstat.executeQuery();
			    while (resultSet.next()) 
			    {
			    	FicheroResiBean c = creaFicheroResiBean(resultSet);

		 	        list.add(c);
		 	     }

			 } catch (SQLException e) {
			       e.printStackTrace();
			   }finally {con.close();}
			return   list; 

		}


		public static void actualizaIdSPD(FicheroResiBean medResi) {
			// TODO Esbozo de método generado automáticamente
			
		}


	/**
	 * Pasamos a histórico las producciones de más de X días
	 * @param conn 
	 * @param aHistorico 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static boolean detalleProcesosAnterioresAHistorico(Connection conn, List aHistorico)  throws ClassNotFoundException, SQLException {
		int result =0;
		String qry = " INSERT INTO SPDhst_ficheroResiDetalle ( ";
			qry+= " fechaHistorico, fechaInsert, oidFicheroResiCabecera, oidFicheroResiDetalle, tipoRegistro, idTratamientoCIP, idDivisionResidencia, idProceso, resiCIP, ";
			qry+= " resiNombre, resiApellido1, resiApellido2, resiApellidos, resiApellidosNombre, resiCn, resiMedicamento, resiFormaMedicacion, resiInicioTratamiento, resiFinTratamiento, ";
			qry+= " resiObservaciones, resiComentarios, resiPeriodo, resiVariante, resiSiPrecisa, spdCIP, spdCnFinal, spdNombreBolsa, spdFormaMedicacion, spdAccionBolsa, spdNomGtVmpp, ";
			qry+= " resiD1, resiD2, resiD3, resiD4, resiD5, resiD6, resiD7, diasAutomaticos, resiViaAdministracion, resiToma1, resiToma2, resiToma3, resiToma4, resiToma5, resiToma6, resiToma7, resiToma8, ";
			qry+= " resiToma9, resiToma10, resiToma11, resiToma12, resiToma13, resiToma14, resiToma15, resiToma16, resiToma17, resiToma18, resiToma19, resiToma20, resiToma21, resiToma22, resiToma23, resiToma24, ";
			qry+= " numeroDeTomas, incidencia, resultLog, [row], mensajesInfo, mensajesAlerta, idEstado, validar, fechaDesde, fechaHasta, frecuencia, diasMesConcretos, diasSemanaConcretos, secuenciaGuide, ";
			qry+= " tipoEnvioHelium, diasSemanaMarcados, resiInicioTratamientoParaSPD, resiFinTratamientoParaSPD, editable, resiTipoMedicacion, detalleRow, editado, mensajesResidencia, resiTomaAux, idTratamientoSPD, ";
			qry+= " previsionResi, previsionSPD, controlNumComprimidos, controlPrincipioActivo, controlRegistroAnterior, controlRegistroRobot, controlDiferentesGtvmp, controlUnicoGtvm, controlValidacionDatos, controlNoSustituible, ";
			qry+= " confirmar, confirmaciones, aux1)  ";
			qry+= " SELECT getDate(), fechaInsert, oidFicheroResiCabecera, oidFicheroResiDetalle, tipoRegistro, idTratamientoCIP, idDivisionResidencia, idProceso, resiCIP, ";
			qry+= " resiNombre, resiApellido1, resiApellido2, resiApellidos, resiApellidosNombre, resiCn, resiMedicamento, resiFormaMedicacion, resiInicioTratamiento, resiFinTratamiento, ";
			qry+= " resiObservaciones, resiComentarios, resiPeriodo, resiVariante, resiSiPrecisa, spdCIP, spdCnFinal, spdNombreBolsa, spdFormaMedicacion, spdAccionBolsa, spdNomGtVmpp, ";
			qry+= " resiD1, resiD2, resiD3, resiD4, resiD5, resiD6, resiD7, diasAutomaticos, resiViaAdministracion, resiToma1, resiToma2, resiToma3, resiToma4, resiToma5, resiToma6, resiToma7, resiToma8, ";
			qry+= " resiToma9, resiToma10, resiToma11, resiToma12, resiToma13, resiToma14, resiToma15, resiToma16, resiToma17, resiToma18, resiToma19, resiToma20, resiToma21, resiToma22, resiToma23, resiToma24, ";
			qry+= " numeroDeTomas, incidencia, resultLog, [row], mensajesInfo, mensajesAlerta, idEstado, validar, fechaDesde, fechaHasta, frecuencia, diasMesConcretos, diasSemanaConcretos, secuenciaGuide, ";
			qry+= " tipoEnvioHelium, diasSemanaMarcados, resiInicioTratamientoParaSPD, resiFinTratamientoParaSPD, editable, resiTipoMedicacion, detalleRow, editado, mensajesResidencia, resiTomaAux, idTratamientoSPD, ";
			qry+= " previsionResi, previsionSPD, controlNumComprimidos, controlPrincipioActivo, controlRegistroAnterior, controlRegistroRobot, controlDiferentesGtvmp, controlUnicoGtvm, controlValidacionDatos, controlNoSustituible, ";
			qry+= " confirmar, confirmaciones, aux1  ";
		  	qry+= " FROM SPD_ficheroResiDetalle ";
		  	qry+= " WHERE oidFicheroResiCabecera IN (" + HelperSPD.convertirListSecuencia(aHistorico).toString() + ")";
		  	
		  	
		System.out.println("limpiarDatosHistoricoProcesosAnteriores -->" +qry );		
		try {
			PreparedStatement pstat = conn.prepareStatement(qry);
			result=pstat.executeUpdate();
	     } catch (SQLException e) {
		         e.printStackTrace();
		     }finally {}
			return result>0;
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
	public static boolean borrarDetalleYaEnHistorico(Connection conn, List aHistorico) throws Exception 
	{
		int result=0;
		
		String query = " DELETE FROM dbo.SPD_ficheroResiDetalle  ";
		query+= " WHERE oidFicheroResiCabecera IN (" + HelperSPD.convertirListSecuencia(aHistorico).toString() + ")";
		query+= " AND oidFicheroResiCabecera in (select oidFicheroResiCabecera from dbo.SPDhst_ficheroResiDetalle) "; //nos aseguramos que ya se ha copiado en histórico 
		System.out.println(className + "--> borrarDetalleYaEnHistorico -->" +query );		
		 	
	    try {
	         PreparedStatement pstat = conn.prepareStatement(query);
	         result=pstat.executeUpdate();
	       
	     } catch (SQLException e) {
	    	 result=-1;
	         e.printStackTrace();
	     }finally {}
		return result>=0;
	}
	
	public static int countValidacionesPendientes(FicheroResiBean cab) throws Exception {
		Connection con = Conexion.conectar();
		String qry = "	SELECT  count( '1') as numeroValidacionesPendientes  ";
	   	qry+=  " 	from dbo.SPD_ficheroResiDetalle d  ";
	   	qry+=  " 	where d.tipoRegistro='LINEA'";
		qry+=  " AND d.idProceso ='" + cab.getIdProceso()+"'";
		qry+=  " 	and (UPPER(d.validar) in ('"+SPDConstants.REGISTRO_VALIDAR+"') OR UPPER(d.confirmar) in ('"+SPDConstants.REGISTRO_CONFIRMAR+"' )) ";

		 System.out.println(className + "--> getOidFicheroResiCabecera" +qry );		
		     	ResultSet resultSet = null;
		 	 	
		     	int result =0;
				try {
					PreparedStatement pstat = con.prepareStatement(qry);
				    resultSet = pstat.executeQuery();
				    resultSet.next();
				    result = resultSet.getInt("numeroValidacionesPendientes");
				   } catch (SQLException e) {
				       e.printStackTrace();
			   }finally {con.close();}
	   return result;
	}

	public static int contarDistintosPActivosPorCIPyGTVM(FicheroResiBean medResi) throws Exception {
		Connection con = Conexion.conectar();
		String qry = "	SELECT  distinct d.cuantos AS  cuantos ";
		qry+=  " 	FROM  bd_consejo bd INNER JOIN ";
		qry+=  " 	( ";
	   	qry+=  " 		SELECT  c.NomGtVm, count('1') AS cuantos  ";
	   	qry+=  " 		FROM dbo.SPD_ficheroResiDetalle d inner join bd_consejo c  on (d.spdCnFinal=c.codigo or d.resiCn=c.codigo ) ";
	   	qry+=  " 		WHERE d.tipoRegistro='LINEA'";
	   	qry+=  " 		AND d.spdNomGtVmpp<>'' and d.spdNomGtVmpp is not null ";
		qry+=  " 		AND d.idProceso ='" + medResi.getIdProceso()+"'";
		qry+=  " 		AND d.spdAccionBolsa NOT IN ('"+SPDConstants.SPD_ACCIONBOLSA_SI_PRECISA+"') ";
		qry+=  " 		AND idProceso ='" + medResi.getIdProceso()+"'";
		qry+=  " 		AND d.resiCIP ='" + medResi.getResiCIP()+"'";
		qry+=  " 		AND d.oidFicheroResiDetalle <>'" + medResi.getOidFicheroResiDetalle()+"'";
		qry+=  " 		group by c.NomGtVm ";
		qry+=  " 	)d ON  bd.NomGtVm=d.NomGtVm ";
		qry+=  " 	WHERE 1= 1 ";
		
		qry+=  " 	AND bd.codigo ='" + medResi.getSpdCnFinal()+"'";
		
		System.out.println(className + "--> contarDistintosPActivosPorCIPyGTVM" +qry );		
		ResultSet resultSet = null;
		 	 	
		int result =0;
		try {
			PreparedStatement pstat = con.prepareStatement(qry);
			resultSet = pstat.executeQuery();
			resultSet.next();
			try {
				result = resultSet.getInt("cuantos");
			} catch (SQLException e) {result=0;}
		} catch (SQLException e) {
			e.printStackTrace();
			}finally {con.close();}
	   return result;
	}
	
	/**
	 * 
	 * @param medResi
	 * @param confirmar 
	 * @throws SQLException 
	 */
	public static void actualizaRestoGtvmCip(FicheroResiBean medResi, boolean confirmar) throws SQLException {
        int result=0;
        Connection con = Conexion.conectar();
			  
		String qry = "	update dbo.SPD_ficheroResiDetalle set controlUnicoGtvm='ALERTA' ";
		if(confirmar) 
			qry+=  " 		, controlValidacionDatos='ALERTA',  validar='VALIDAR', confirmar='CONFIRMAR' "; 
		qry+=  " 		WHERE tipoRegistro='LINEA' 	 ";
		qry+=  " 		AND spdCnFinal in (select codigo from bd_consejo where nomGtVm  in (select nomGtVm from bd_consejo where codigo ='"+medResi.getSpdCnFinal()+"' )) "; // OR resiCn in (select codigo from bd_consejo where nomGtVm'"+medResi.getNomGtVmpp()+"'))";
		qry+=  " 		AND spdAccionBolsa NOT IN ('SI_PRECISA') ";  		
		qry+=  " 		AND idProceso ='" + medResi.getIdProceso()+"'";
		qry+=  " 		AND resiCIP ='" + medResi.getResiCIP()+"'";
		if(confirmar) 
			qry+=  " 		AND confirmar<>'CONFIRMADO'  ";
		
	 	
	    try {
	         PreparedStatement pstat = con.prepareStatement(qry);
	         result=pstat.executeUpdate();
	       
	     } catch (SQLException e) {
	         e.printStackTrace();
	     }finally {con.close();}
	}


	

	



}

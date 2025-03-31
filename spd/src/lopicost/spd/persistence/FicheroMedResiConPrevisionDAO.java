package lopicost.spd.persistence;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import lopicost.config.pool.dbaccess.Conexion;
import lopicost.spd.struts.bean.FicheroResiBean;

 
 
public class FicheroMedResiConPrevisionDAO {
	
	
	public static boolean limpiarDatosHistoricoProcesosAnterioresCIP(String idProceso, String CIP) throws ClassNotFoundException, SQLException {
		int result =0;
		Connection con = Conexion.conectar();

		String qry = " DELETE FROM dbo.SPD_resiMedicacion_hst ";
		qry+= " WHERE resiCIP ='"+CIP+"'  ";
		qry+= "  and ( idProceso in  (select idProceso from  dbo.SPD_resiMedicacion where resiCIP ='"+CIP+"'  )";
		qry+= "  or idProceso ='"+idProceso+"'  )";
		
		System.out.println("limpiarDatosHistoricoProcesoCIP -->" +qry );		
		try {
			PreparedStatement pstat = con.prepareStatement(qry);
			result=pstat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {con.close();}
		return result>0;
	}

	
	
	
	public static boolean creaHistoricoProcesosAnterioresCIP(String CIP) throws ClassNotFoundException, SQLException {
	       int result =0;
	        
			  Connection con = Conexion.conectar();

			  String qry = " INSERT INTO dbo.SPD_resiMedicacion_hst ";
			  	qry+= " select getDate(),  fechaInsert, ";
 			  	qry+= " 	idProceso, idDivisionResidencia, resiCIP, ";
 			  	qry+= " 	resiInicioTratamiento, resiFinTratamiento, ";
 			  	qry+= " 	resiNombrePaciente, resiCn, resiMedicamento, ";
 			  	qry+= " 	resiSiPrecisa, resiObservaciones, resiComentarios, ";
 			  	qry+= " 	spdCnFinal, spdNombreBolsa, spdComentarioLopicost, ";
 			  	qry+= " 	spdFormaMedicacion, spdAccionBolsa, spdCodGtVm, spdNomGtVm, ";
 			  	qry+= " 	spdCodGtVmp, spdNomGtVmp, spdCodGtVmpp, spdNomGtVmpp, ";
 			  	qry+= " 	spdEmblistable, spdComprimidosDia, spdComprimidosSemana,  ";
 			  	qry+= " 	spdComprimidosDosSemanas, spdComprimidosTresSemanas, spdComprimidosCuatroSemanas, fechaInicio, fechaFin	  ";
			  	qry+= " from dbo.SPD_resiMedicacion ";
			  	qry+= " where  resiCIP ='"+CIP+"'   and  (resiCIP ='"+CIP+"'  OR fechaInsert < getDate() - 30) ";
		 	  	//qry+= " and  idProceso ='"+idProceso+"' ";
		       				  
		       		System.out.println("creaHistoricoProcesoCIP -->" +qry );		
		    try {
		         PreparedStatement pstat = con.prepareStatement(qry);
		         result=pstat.executeUpdate();

		       
		     } catch (SQLException e) {
		         e.printStackTrace();
		     } finally {con.close();}
		 
			return result>0;
		}



	public static boolean limpiarDatosCIP(String resiCIP) throws ClassNotFoundException, SQLException {
       int result =0;
       Connection con = Conexion.conectar();
       String qry = " DELETE FROM dbo.SPD_resiMedicacion ";
			qry+= " WHERE resiCIP ='"+resiCIP+"'";
		System.out.println("limpiarDatosCIP -->" +qry );		
		try {
			PreparedStatement pstat = con.prepareStatement(qry);
			result=pstat.executeUpdate();
	     } catch (SQLException e) {
	         e.printStackTrace();
	     } finally {con.close();}
		return result>0;
	}

	

	public static boolean creaHistoricoPacientesInactivos() throws ClassNotFoundException, SQLException {
	       int result =0;
	        
			  Connection con = Conexion.conectar();

			  String qry = " INSERT INTO dbo.SPD_resiMedicacion_hst ";
		 	  	 		qry+= " select getDate(), fechaInsert,  ";
		 			  	qry+= " 	idProceso, idDivisionResidencia, resiCIP, ";
		 			  	qry+= " 	resiInicioTratamiento, resiFinTratamiento, ";
		 			  	qry+= " 	resiNombrePaciente, resiCn, resiMedicamento, ";
		 			  	qry+= " 	resiSiPrecisa, resiObservaciones, resiComentarios, ";
		 			  	qry+= " 	spdCnFinal, spdNombreBolsa, spdComentarioLopicost, ";
		 			  	qry+= " 	spdFormaMedicacion, spdAccionBolsa, spdCodGtVm, spdNomGtVm, ";
		 			  	qry+= " 	spdCodGtVmp, spdNomGtVmp, spdCodGtVmpp, spdNomGtVmpp, ";
		 			  	qry+= " 	spdEmblistable, spdComprimidosDia, spdComprimidosSemana,  ";
		 			  	qry+= " 	spdComprimidosDosSemanas, spdComprimidosTresSemanas, spdComprimidosCuatroSemanas, fechaInicio, fechaFin	  ";
					  	qry+= " from dbo.SPD_resiMedicacion c ";
		 	  	 		qry+= " inner join bd_pacientes p on (c.resiCIP=p.CIP and p.activo='INACTIVO') ";
		       				  
		       		System.out.println("creaHistoricoPacientesInactivos -->" +qry );		
		    try {
		         PreparedStatement pstat = con.prepareStatement(qry);
		         result=pstat.executeUpdate();

		       
		     } catch (SQLException e) {
		         e.printStackTrace();
		     } finally {con.close();}


			return result>0;
		}


	
	public static boolean borraProcesosPacientesInactivos() throws ClassNotFoundException, SQLException {
	       int result =0;
			  Connection con = Conexion.conectar();

			  String qry = " DELETE FROM dbo.SPD_resiMedicacion ";
		 	  	 		qry+= " WHERE resiCIP in  ";
		 	  	 		qry+= " (select CIP from  bd_pacientes where activo='INACTIVO') ";
		       				  
		       		System.out.println("borraProcesosPacientesInactivos -->" +qry );		
		    try {
		         PreparedStatement pstat = con.prepareStatement(qry);
		         result=pstat.executeUpdate();

		       
		     } catch (SQLException e) {
		         e.printStackTrace();
		     } finally {con.close();}

			return result>0;
		}
	



	public static boolean nuevo(FicheroResiBean fila) throws ClassNotFoundException, SQLException {
	       int result =0;
	        
			  Connection con = Conexion.conectar();

		  String qry = " INSERT INTO SPD_resiMedicacion ";
		  	qry+= " (  ";
		  	qry+= " 	idProceso, idDivisionResidencia, resiCIP, ";
		  	qry+= " 	resiInicioTratamiento, resiFinTratamiento, ";
		  	qry+= " 	resiNombrePaciente, resiCn, resiMedicamento, ";
		  	qry+= " 	resiSiPrecisa, resiObservaciones, resiComentarios, ";
		  	qry+= " 	spdCnFinal, spdNombreBolsa, spdComentarioLopicost, ";
		  	qry+= " 	spdFormaMedicacion, spdAccionBolsa, spdCodGtVm, spdNomGtVm, ";
		  	qry+= " 	spdCodGtVmp, spdNomGtVmp, spdCodGtVmpp, spdNomGtVmpp, ";
		  	qry+= " 	spdEmblistable, spdComprimidosDia, spdComprimidosSemana,  ";
		  	qry+= " 	spdComprimidosDosSemanas, spdComprimidosTresSemanas, spdComprimidosCuatroSemanas, fechaInicio, fechaFin	  ";
			qry+= " )  VALUES ( ";
		  	qry+= " '"+fila.getIdProceso()+"', '"+fila.getIdDivisionResidencia()+"', '"+fila.getResiCIP()+"',  ";
		  	qry+= "  COALESCE(REPLACE('"+fila.getResiInicioTratamientoParaSPD()+"', 'null', null), '"+fila.getResiInicioTratamiento()+"'), ";
		  	qry+= "  COALESCE(REPLACE('"+fila.getResiFinTratamientoParaSPD()+"', 'null', null), '"+fila.getResiFinTratamiento()+"'), ";
		  	qry+= " '"+fila.getResiApellidosNombre()+"', '"+fila.getResiCn()+"', '"+fila.getResiMedicamento()+"', ";
		  	qry+= " '"+fila.getResiSiPrecisa()+"', '"+fila.getResiObservaciones()+"', '"+fila.getResiComentarios()+"',  ";
		  	qry+= " '"+fila.getSpdCnFinal()+"', '"+fila.getSpdNombreBolsa()+"', '"+fila.getSpdComentarioLopicost()+"',  ";
		  	qry+= " '"+fila.getSpdFormaMedicacion()+"', '"+fila.getSpdAccionBolsa()+"' , '"+fila.getSpdCodGtVm()+"', '"+fila.getSpdNomGtVm()+"',";
		  	qry+= " '"+fila.getSpdCodGtVmp()+"', '"+fila.getSpdNomGtVmp()+"',  '"+fila.getSpdCodGtVmpp()+"', '"+fila.getSpdNomGtVmpp()+"', ";
		  	qry+= " '"+fila.getSpdEmblistable()+"', '"+fila.getComprimidosDia()+"', '"+fila.getComprimidosSemana()+"', ";
			qry+= " '"+fila.getComprimidosDosSemanas()+"', '"+fila.getComprimidosTresSemanas()+"', '"+fila.getComprimidosCuatroSemanas()+"', ";
			qry+= " '"+fila.getFechaInicioInt()+"', '"+fila.getFechaFinInt()+"' ";
			qry+= " ); ";
			
			
			 	  	 		
		    System.out.println("FicheroMedResiConSustitucionDAO.nuevo -->" +qry );		
		    try {
		         PreparedStatement pstat = con.prepareStatement(qry);
		         result=pstat.executeUpdate();

		       
		     } catch (SQLException e) {
		         e.printStackTrace();
		     } finally {con.close();}


			return result>0;
		}


	
	public static boolean creaHistoricoProcesosResi(String idDivisionResidencia)  throws ClassNotFoundException, SQLException {
	       int result =0;
	        
			  Connection con = Conexion.conectar();

			  String qry = " INSERT INTO SPD_resiMedicacion_hst ";
			  	qry+= " (  ";
			  	qry+= " 	fechaHistorico, fechaInsert, idProceso, idDivisionResidencia, resiCIP, ";
			  	qry+= " 	resiInicioTratamiento, resiFinTratamiento, ";
			  	qry+= " 	resiNombrePaciente, resiCn, resiMedicamento, ";
			  	qry+= " 	resiSiPrecisa, resiObservaciones, resiComentarios, ";
			  	qry+= " 	spdCnFinal, spdNombreBolsa, spdComentarioLopicost, ";
			  	qry+= " 	spdFormaMedicacion, spdAccionBolsa, spdCodGtVm, spdNomGtVm, ";
			  	qry+= " 	spdCodGtVmp, spdNomGtVmp, spdCodGtVmpp, spdNomGtVmpp, ";
			  	qry+= " 	spdEmblistable, spdComprimidosDia, spdComprimidosSemana,  ";
			  	qry+= " 	spdComprimidosDosSemanas, spdComprimidosTresSemanas, spdComprimidosCuatroSemanas, fechaInicio, fechaFin		  ";
				qry+= " )   ( ";
			  	qry+= "  	select  ";
			  	qry+= " 	getDate(), fechaInsert, idProceso, idDivisionResidencia, resiCIP, ";
			  	qry+= " 	resiInicioTratamiento, resiFinTratamiento, ";
			  	qry+= " 	resiNombrePaciente, resiCn, resiMedicamento, ";
			  	qry+= " 	resiSiPrecisa, resiObservaciones, resiComentarios, ";
			  	qry+= " 	spdCnFinal, spdNombreBolsa, spdComentarioLopicost, ";
			  	qry+= " 	spdFormaMedicacion, spdAccionBolsa, spdCodGtVm, spdNomGtVm, ";
			  	qry+= " 	spdCodGtVmp, spdNomGtVmp, spdCodGtVmpp, spdNomGtVmpp, ";
			  	qry+= " 	spdEmblistable, spdComprimidosDia, spdComprimidosSemana,  ";
			  	qry+= " 	spdComprimidosDosSemanas, spdComprimidosTresSemanas, spdComprimidosCuatroSemanas, fechaInicio, fechaFin		  ";
			  	qry+= "  	from SPD_resiMedicacion ";
			  	qry+= " 	where  idDivisionResidencia ='"+idDivisionResidencia+"' "; 
		 	  	qry+= " 	and  fechaInsert < getdate()-30 )";  //se borran procesos anteriores 
				
		 	
		       				  
		       	System.out.println("creaHistoricoProcesosResi -->" +qry );		
		    try {
		         PreparedStatement pstat = con.prepareStatement(qry);
		         result=pstat.executeUpdate();

		       
		     } catch (SQLException e) {
		         e.printStackTrace();
		     } finally {con.close();}
			return result>0;
		}

	
	

	public static boolean limpiarDatosResi(String idDivisionResidencia) throws ClassNotFoundException, SQLException {
		 
	       int result =0;
			  Connection con = Conexion.conectar();

			  String qry = " DELETE FROM dbo.SPD_resiMedicacion ";
	  	 		qry+= " where  idDivisionResidencia ='"+idDivisionResidencia+"' "; //de momento borramos todo lo anterior de la resi
	  	 		qry+= " and  fechaInsert < getdate()-30 ";  //se borran procesos anteriores 
		 	  	 		
		       				  
		       		System.out.println("limpiarDatosResi -->" +qry );		
		    try {
		         PreparedStatement pstat = con.prepareStatement(qry);
		         result=pstat.executeUpdate();
		       
		     } catch (SQLException e) {
		         e.printStackTrace();
		     }
			return result>0;
		}
	
	
	
	
	
	public static boolean borraErrores()  throws ClassNotFoundException, SQLException {
		 
	       int result =0;
			  Connection con = Conexion.conectar();

			  String qry = " DELETE FROM dbo.SPD_resiMedicacion ";
	  	 		qry+= " where  idproceso not like '%20__%' OR idproceso like '%idproceso%'";  //se borran procesos anteriores 
		 	  	 		
	  	 		System.out.println("borraErrores -->" +qry );		
		    try {
		         PreparedStatement pstat = con.prepareStatement(qry);
		         result=pstat.executeUpdate();
		       
		     } catch (SQLException e) {
		         e.printStackTrace();
		     }finally {con.close();}
			return result>0;
		} 

			
	public static boolean borraErrores_hst()  throws ClassNotFoundException, SQLException {
		 
	       int result =0;
			  Connection con = Conexion.conectar();

			  String qry = " DELETE FROM dbo.SPD_resiMedicacion_hst ";
	  	 		qry+= " where  idproceso not like '%20__%' OR idproceso like '%idproceso%'";  //se borran procesos anteriores 
		 	  	 		
	  	 		System.out.println("borraErrores_hst -->" +qry );		
		    try {
		         PreparedStatement pstat = con.prepareStatement(qry);
		         result=pstat.executeUpdate();
		       
		     } catch (SQLException e) {
		         e.printStackTrace();
		     }
			return result>0;
		}
		



	
}


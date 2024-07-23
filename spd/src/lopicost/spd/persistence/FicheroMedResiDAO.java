package lopicost.spd.persistence;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lopicost.config.pool.dbaccess.Conexion;
import lopicost.spd.model.BdConsejo;
import lopicost.spd.model.DivisionResidencia;
import lopicost.spd.model.FicheroResiDetalle;
import lopicost.spd.model.GestSustituciones;
import lopicost.spd.model.farmacia.StockFL;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.struts.bean.FormasFarmaceuticas;
import lopicost.spd.struts.bean.TiposAccionBean;
import lopicost.spd.struts.form.BdConsejoForm;
import lopicost.spd.struts.form.GestSustitucionesForm;
import lopicost.spd.struts.form.SustXComposicionForm;
import lopicost.spd.utils.SPDConstants;
 
 
public class FicheroMedResiDAO {
	
	
	public static boolean limpiarDatosHistoricoProcesosAnterioresCIP(String idProceso, String CIP) throws ClassNotFoundException, SQLException {
		int result =0;
		Connection con = Conexion.conectar();

		String qry = " DELETE FROM dbo.ctl_medicacioResi_hst ";
		qry+= " WHERE pacientCIP ='"+CIP+"' ";
		qry+= "  and ( idProceso in  (select idProceso from  dbo.ctl_medicacioResi where pacientCIP ='"+CIP+"'  )";
		qry+= "  or idProceso ='"+idProceso+"'  )";
		       				  
		System.out.println("limpiarDatosHistoricoProcesoCIP -->" +qry );		
		try {
			PreparedStatement pstat = con.prepareStatement(qry);
			result=pstat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {con.close();}
		return result>0;
	}

	
	public static boolean creaHistoricoProcesosAnterioresCIP(String CIP) throws ClassNotFoundException, SQLException {
	       int result =0;
	        
			  Connection con = Conexion.conectar();

			  String qry = " INSERT INTO dbo.ctl_medicacioResi_hst ";
			  	qry+= " select getDate(),  ";
			  	qry+= " idProceso,  idDivisionResidencia, fechaInsert,  pacientCIP,  ";
			  	qry+= " iniciTractamentResi, fiTractamentResi, pacientNomCognoms, cnResi, ";
			  	qry+= " nomMedicamentResi, cnFinal, nomCurtMedicamentFinal, comentariTractament, ";
			  	qry+= " viaAdministracio, pauta, accion, siPrecisa , observacionsResi, comentarisResi ";
			  	qry+= " from ctl_medicacioResi ";
			  	qry+= " where  pacientCIP ='"+CIP+"' ";
		 	  	//qry+= " and  idProceso ='"+idProceso+"' ";
		       				  
		       		System.out.println("creaHistoricoProcesoCIP -->" +qry );		
		    try {
		         PreparedStatement pstat = con.prepareStatement(qry);
		         result=pstat.executeUpdate();

		       
		     } catch (SQLException e) {
		         e.printStackTrace();
		     }finally {con.close();}
			return result>0;
		}

	

	
	public static boolean creaHistoricoPacientesInactivos() throws ClassNotFoundException, SQLException {
	       int result =0;
	        
			  Connection con = Conexion.conectar();

			  String qry = " INSERT INTO dbo.ctl_medicacioResi_hst ";
		 	  	 		qry+= " select getDate(), ";
					  	qry+= " idProceso,  c.idDivisionResidencia, fechaInsert,  pacientCIP,  ";
					  	qry+= " iniciTractamentResi, fiTractamentResi, pacientNomCognoms, cnResi, ";
					  	qry+= " nomMedicamentResi, cnFinal, nomCurtMedicamentFinal, comentariTractament, ";
					  	qry+= " viaAdministracio, pauta, accion, siPrecisa , observacionsResi, comentarisResi ";
					  	qry+= " from ctl_medicacioResi c ";
		 	  	 		qry+= " inner join bd_pacientes p on (c.pacientCIP=p.CIP and activo='INACTIVO') ";
		       				  
		       		System.out.println("creaHistoricoPacientesInactivos -->" +qry );		
		    try {
		         PreparedStatement pstat = con.prepareStatement(qry);
		         result=pstat.executeUpdate();

		       
		     } catch (SQLException e) {
		         e.printStackTrace();
		     }


			return result>0;
		}

	public static boolean borraProcesosPacientesInactivos() throws ClassNotFoundException, SQLException {
	       int result =0;
			  Connection con = Conexion.conectar();

			  String qry = " DELETE FROM dbo.ctl_medicacioResi ";
		 	  	 		qry+= " WHERE pacientCIP in  ";
		 	  	 		qry+= " (select CIP from  bd_pacientes where activo='INACTIVO') ";
		       				  
		       		System.out.println("borraProcesosPacientesInactivos -->" +qry );		
		    try {
		         PreparedStatement pstat = con.prepareStatement(qry);
		         result=pstat.executeUpdate();

		       
		     } catch (SQLException e) {
		         e.printStackTrace();
		     }finally {con.close();}

			return result>0;
		}



	public static boolean limpiarDatosCIP(String resiCIP) throws ClassNotFoundException, SQLException {
		int result =0;
		Connection con = Conexion.conectar();
		String qry = " DELETE FROM dbo.ctl_medicacioResi ";
			qry+= " WHERE pacientCIP ='"+resiCIP+"' ";
			
		System.out.println("limpiarDatosCIP -->" +qry );		
		try {
			PreparedStatement pstat = con.prepareStatement(qry);
			result=pstat.executeUpdate();
	     } catch (SQLException e) {
	         e.printStackTrace();
	     }finally {con.close();}
		return result>0;
	}


	public static boolean nuevo(FicheroResiBean fila) throws ClassNotFoundException, SQLException {
	       int result =0;
	        
			  Connection con = Conexion.conectar();

		  String qry = " INSERT INTO ctl_medicacioResi ";
		  	qry+= " (  ";
		  	qry+= " 	idProceso, idDivisionResidencia,   ";
		  	qry+= " 	pacientCIP, iniciTractamentResi, fiTractamentResi, ";
		  	qry+= " 	pacientNomCognoms, cnResi, nomMedicamentResi, ";
		  	qry+= " 	cnFinal, nomCurtMedicamentFinal, comentariTractament, ";
		  	qry+= " 	viaAdministracio, pauta, accion, siPrecisa, observacionsResi, comentarisResi ";
		  	qry+= " )  VALUES ( ";
		  	qry+= " '"+fila.getIdProceso()+"', '"+fila.getIdDivisionResidencia()+"', ";
		  	qry+= " '"+fila.getResiCIP()+"',  '"+fila.getResiInicioTratamientoParaSPD()+"', '"+fila.getResiFinTratamientoParaSPD()+"', ";
		  	qry+= " '"+fila.getResiApellidosNombre()+"', '"+fila.getResiCn()+"', '"+fila.getResiMedicamento()+"', ";
		  	qry+= " '"+fila.getSpdCnFinal()+"', '"+fila.getSpdNombreBolsa()+"', '"+fila.getSpdComentarioLopicost()+"',  ";
		  	qry+= " '"+fila.getSpdFormaMedicacion()+"', '"+fila.getSpdAccionBolsa()+"' , '"+fila.getSpdAccionBolsa()+"',  ";
		  	qry+= " '"+fila.getResiSiPrecisa()+"', '"+fila.getResiObservaciones()+"', '"+fila.getResiComentarios()+"'  ";
		  	
			qry+= " ); ";
			
			 	  	 		
		    System.out.println("FicheroMedResiConSustitucionDAO.nuevo -->" +qry );		
		    try {
		         PreparedStatement pstat = con.prepareStatement(qry);
		         result=pstat.executeUpdate();

		       
		     } catch (SQLException e) {
		         e.printStackTrace();
		     }finally {con.close();}


			return result>0;
		}


	public static boolean creaHistoricoProcesosResi(String idDivisionResidencia)  throws ClassNotFoundException, SQLException {
		       int result =0;
		        
				  Connection con = Conexion.conectar();

				  String qry = " INSERT INTO dbo.ctl_medicacioResi_hst ";
	 	  	 		qry+= " select getDate(), ";
				  	qry+= " idProceso,  idDivisionResidencia, fechaInsert,  pacientCIP,  ";
				  	qry+= " iniciTractamentResi, fiTractamentResi, pacientNomCognoms, cnResi, ";
				  	qry+= " nomMedicamentResi, cnFinal, nomCurtMedicamentFinal, comentariTractament, ";
				  	qry+= " viaAdministracio, pauta, accion, siPrecisa, observacionsResi, comentarisResi ";
				  	qry+= "  from ctl_medicacioResi ";
				  	qry+= " where  idDivisionResidencia ='"+idDivisionResidencia+"' "; 
			 	  	qry+= " and  fechaInsert < getdate()-30 ";  //se borran procesos anteriores 
			       				  
			       		System.out.println("creaHistoricoProcesoCIP -->" +qry );		
			    try {
			         PreparedStatement pstat = con.prepareStatement(qry);
			         result=pstat.executeUpdate();

			       
			     } catch (SQLException e) {
			         e.printStackTrace();
			     }finally {con.close();}
				return result>0;
			}


	public static boolean limpiarDatosResi(String idDivisionResidencia) throws ClassNotFoundException, SQLException {
		 
	       int result =0;
			  Connection con = Conexion.conectar();

			  String qry = " DELETE FROM dbo.ctl_medicacioResi ";
	  	 		qry+= " where  idDivisionResidencia ='"+idDivisionResidencia+"' "; //de momento borramos todo lo anterior de la resi
	  	 		qry+= " and  fechaInsert < getdate()-30 ";  //se borran procesos anteriores 
		 	  	 		
		       				  
		       		System.out.println("limpiarDatosResi -->" +qry );		
		    try {
		         PreparedStatement pstat = con.prepareStatement(qry);
		         result=pstat.executeUpdate();
		       
		     } catch (SQLException e) {
		         e.printStackTrace();
		     }finally {con.close();}
			return result>0;
		}


	public static boolean borraErrores()  throws ClassNotFoundException, SQLException {
		 
	       int result =0;
			  Connection con = Conexion.conectar();

			  String qry = " DELETE FROM dbo.ctl_medicacioResi ";
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

			  String qry = " DELETE FROM dbo.ctl_medicacioResi_hst ";
	  	 		qry+= " where  idproceso not like '%20__%' OR idproceso like '%idproceso%'";  //se borran procesos anteriores 
		 	  	 		
	  	 		System.out.println("borraErrores_hst -->" +qry );		
		    try {
		         PreparedStatement pstat = con.prepareStatement(qry);
		         result=pstat.executeUpdate();
		       
		     } catch (SQLException e) {
		         e.printStackTrace();
		     }finally {con.close();}
			return result>0;
		}




	
}


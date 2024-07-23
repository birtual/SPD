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
import lopicost.spd.model.GestSustituciones;
import lopicost.spd.model.farmacia.StockFL;
import lopicost.spd.struts.bean.FormasFarmaceuticas;
import lopicost.spd.struts.bean.TiposAccionBean;
import lopicost.spd.struts.form.BdConsejoForm;
import lopicost.spd.struts.form.GestSustitucionesForm;
import lopicost.spd.struts.form.SustXComposicionForm;
import lopicost.spd.utils.SPDConstants;
 
 
public class StockFL_DAO {
	
	public static boolean nuevo(StockFL stockFL) throws ClassNotFoundException, SQLException {
	       int result =0;
	        
			  Connection con = Conexion.conectar();

		 	  	   String qry = " INSERT INTO dbo.gest_fl_STOCK (FECHA_INSERT, CN_FL, CN6, NOMBRE, PRINC_ACTIVO, CodGtVm, "
		 	  	   		+ "NomGtVm, CodGtVmp, NomGtVmp, CodGtVmpp, NomGtVmpp, PROVEEDOR, EXISTENCIAS, LOTE, CADUCIDAD, FECHA_MAX_PEDIDO, "
		 	  	   		+ "PVL_VENTA, DTO_VENTA, PUV, ALMACEN) VALUES ";
		       		qry+= " (CONVERT(datetime, getdate(), 120), ";
		       		qry+= "'"+ stockFL.getCnFL()+"', ";
		       		qry+= "'"+ stockFL.getCn6()+"', ";
		       		qry+= "'"+ stockFL.getNombreMedicamento()+"', ";
		       		qry+= "'"+ stockFL.getPrincActivo() +"', ";
		       		qry+= "'"+ stockFL.getCodGtVm() +"', ";
		       		qry+= "'"+ stockFL.getNomGtVm() +"', ";
		       		qry+= "'"+ stockFL.getCodGtVmp() +"', ";
		       		qry+= "'"+ stockFL.getNomGtVmp() +"', ";
		       		qry+= "'"+ stockFL.getCodGtVmpp() +"', ";
		       		qry+= "'"+ stockFL.getNomGtVmpp() +"', ";
		       		qry+= "'"+ stockFL.getProveedor()+"', ";
		       		qry+= "'"+ stockFL.getExistencias()+"', ";
		       		qry+= "'"+ stockFL.getLote()+"', ";
		       		qry+= "'"+ stockFL.getCaducidad()+"', ";
		       		qry+= "'"+ stockFL.getFechaMaximaPedido()+"', ";
		       		qry+= "'"+ stockFL.getPvl_venta()+"', ";
		       		qry+= "'"+ stockFL.getDto_venta()+"', ";
		       		qry+= "'"+ stockFL.getPuv()+"', ";
		       		qry+= "'"+ stockFL.getAlmacen()+"') ";		  
		       		System.out.println("nuevo -->" +qry );		
		    try {
		         PreparedStatement pstat = con.prepareStatement(qry);
		         result=pstat.executeUpdate();

		       
		     } catch (SQLException e) {
		         e.printStackTrace();
		     }finally {con.close();}


			return result>0;
		}

	public static boolean eliminaTodosRegistros()  throws ClassNotFoundException, SQLException {

	        int result=0;
			  Connection con = Conexion.conectar();
		  	   String qry = "delete dbo.gest_fl_STOCK ";
	   		System.out.println("borra -->" +qry );		
		 	
		    try {
		         PreparedStatement pstat = con.prepareStatement(qry);
		         result=pstat.executeUpdate();
		       
		     } catch (SQLException e) {
		         e.printStackTrace();
		     }finally {con.close();}

			
			return result>0;
		}
		



	
}


package lopicost.spd.persistence;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import lopicost.config.pool.dbaccess.Conexion;
import lopicost.spd.model.DivisionResidencia;
import lopicost.spd.model.farmacia.StockFL;
import lopicost.spd.security.helper.VisibilidadHelper;

 
public class StockFL_DAO {
	
	static String className = "StockFL_DAO";

	public static boolean nuevo(StockFL stockFL) throws ClassNotFoundException, SQLException {
	       int result =0;
	        
			  Connection con = Conexion.conectar();

		 	 String qry = " INSERT INTO dbo.SPD_stockFL (";
		 	  	   	qry+= " 	fechaInsert, CN6, CN7, nombre, codiLab, nombreLab, ";
		 	  	   	qry+= " 	CodGtVmpp, NomGtVmpp, PVL, stockMinimo, existencias ";

		 	  	   	if(stockFL.getDto_venta()!=null && !stockFL.getDto_venta().equals(""))
		 	  	   		qry+= " 	, dto_venta ";
		 	  	   	
		 	  	   	qry+= " 	 ";
		 	  	   	qry+= " ) VALUES (";
		       		qry+= " 	CONVERT(datetime, getdate(), 120), ";
		       		qry+= "'"+ stockFL.getCn6()+"', ";
		       		qry+= "'"+ stockFL.getCnFL()+"', ";
		       		qry+= "'"+ stockFL.getNombreMedicamento()+"', ";
		       		qry+= "'"+ stockFL.getCodigoLaboratorio()+"', ";
		       		qry+= "'"+ stockFL.getNombreLaboratorio()+"', ";
		       		qry+= "'"+ stockFL.getCodGtVmpp() +"', ";
		       		qry+= "'"+ stockFL.getNomGtVmpp() +"', ";
		       		qry+=  stockFL.getPvl() + ", ";
		       		qry+=  stockFL.getStockMinimo() + ", ";
		       		qry+=  stockFL.getExistencias() ;
		 	  	   	
		       		if(stockFL.getDto_venta()!=null && !stockFL.getDto_venta().equals(""))
		 	  	   		qry+=  ", " + stockFL.getDto_venta();
		       		
		       		qry+=  ")" ;
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
		  	   String qry = "delete dbo.SPD_stockFL ";
	   		System.out.println("borra -->" +qry );		
		 	
		    try {
		         PreparedStatement pstat = con.prepareStatement(qry);
		         result=pstat.executeUpdate();
		       
		     } catch (SQLException e) {
		         e.printStackTrace();
		     }finally {con.close();}

			
			return result>0;
		}

	public static boolean eliminaStockFL(String cnFL) throws SQLException {

        int result=0;
		  Connection con = Conexion.conectar();
	  	   String qry = "delete dbo.SPD_stockFL where CN7 ='"+cnFL+"'";
   		System.out.println("borra -->" +qry );		
	 	
	    try {
	         PreparedStatement pstat = con.prepareStatement(qry);
	         result=pstat.executeUpdate();
	       
	     } catch (SQLException e) {
	         e.printStackTrace();
	     }finally {con.close();}

		
		return result>0;
	}


	public static StockFL getbyCN(String cnFL) throws SQLException {
    	Connection con = Conexion.conectar();
    		StockFL stock =	null;
  			String qry = "SELECT *  ";
			qry+= " FROM dbo.SPD_stockFL ";
			qry+= "  WHERE 1=1 ";
			qry+= " AND CN7 = '"+cnFL+"' ";

			System.out.println(className + "  - getbyCN -->  " +qry );		
			ResultSet resultSet = null;
			try {
	            PreparedStatement pstat = con.prepareStatement(qry);
	            resultSet = pstat.executeQuery();

	            while (resultSet.next()) {
	            	stock = new StockFL();
	            	stock.setFechaInsert(resultSet.getDate("fechaInsert"));
	            	stock.setCn6(resultSet.getString("CN6"));
	            	stock.setCnFL(resultSet.getString("CN7"));
	            	stock.setNombreMedicamento(resultSet.getString("nombre"));
	            	stock.setCodGtVmpp(resultSet.getString("codGtVmpp"));
	            	stock.setNomGtVmpp(resultSet.getString("nomGtVmpp"));
	            	stock.setCodigoLaboratorio(resultSet.getString("codiLab"));
	            	stock.setNombreLaboratorio(resultSet.getString("nombreLab"));
	            	stock.setPvl(resultSet.getString("PVL"));
	            	stock.setDto_venta(resultSet.getString("dto_venta"));
	            	stock.setStockMinimo(resultSet.getString("stockMinimo"));
	            	stock.setExistencias(resultSet.getString("existencias"));
	            }
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }finally {con.close();}
	        return stock;
    }
		



	
}


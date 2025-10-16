package lopicost.spd.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import lopicost.config.pool.dbaccess.Conexion;
import lopicost.spd.struts.bean.FicheroResiBean;



public class arregloDetalleRowKey{
	
	
	public static FicheroResiBean arregloDetalleRowKey(String idProcessIospd) throws Exception {
		
		
		Connection con = Conexion.conectar();
		String 	qry =  " SELECT * FROM dbo.SPD_detalleRowKeyTMP  ";
		qry+= " where detalleRowKey is null or detalleRowKey=''";
		qry+= " AND idProcessIospd= '"+idProcessIospd +"' ";
	
	    ResultSet resultSet = null;
 	 	
 	    try {
 	         PreparedStatement pstat = con.prepareStatement(qry);
 	         resultSet = pstat.executeQuery();
 	         
 	         String detalleRow="";String detalleRowKey="";
 	         while (resultSet.next()) {
 	        	detalleRow=resultSet.getString("detalleRow");
 	        	if(idProcessIospd.equalsIgnoreCase("importAegerus"))
 	        		detalleRowKey=importAegerus(detalleRow);
 	        	//else if(idProcessIospd.equalsIgnoreCase("importOnada"))
 	        		
 	        	//else if(idProcessIospd.equalsIgnoreCase("importExcelStauros"))
 	        		//	else if(idProcessIospd.equalsIgnoreCase("importResiPlus"))
 	        		//else if(idProcessIospd.equalsIgnoreCase("importSFAssis"))
 	            }
 	     } catch (SQLException e) {
 	         e.printStackTrace();
 	     }finally {con.close();}

 	     return null ;


	}
	
	public static String importAegerus(String detalleResiActual) {
		String detalleRowAegerus=detalleResiActual;
		if(detalleResiActual!=null)
		{
			String[] textoSeparado = detalleResiActual.split(",");
		      // Contar la cantidad de elementos en el array
			if(textoSeparado.length >=10)
			{
				detalleRowAegerus=textoSeparado[0]+",";
				for(int i=1; i<=8;i++){
					detalleRowAegerus+=textoSeparado[i]+",";
				}
				detalleRowAegerus+=textoSeparado[9];
			}
		}
		return detalleRowAegerus;
			
		
	}

	
	

}
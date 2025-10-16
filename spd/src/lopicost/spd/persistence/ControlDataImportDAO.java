package lopicost.spd.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import lopicost.config.pool.dbaccess.Conexion;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



import lopicost.spd.struts.bean.ControlDataImportBean;

import lopicost.spd.utils.DateUtilities;



public class ControlDataImportDAO
{
	private final static String className = "ControlDataImportDAO";
	private final String cLOGGERHEADER_ERROR = className + "ERROR: : ";


    /**
     * @param spdUsuario
     * @return
     * @throws Exception
     */
    public static List getDatosBdPacientesFarmatic(String spdUsuario, int diasAlerta) throws Exception {
   
    	String query = " SELECT 'bd_pacientesFarmatic' as nombreTabla, far.nombreFarmacia as nombreFarmacia,  d.nombreDivisionResidencia, d.idDivisionResidencia, f.UPfarmacia,   ";
    	query+=			" count(*) as numeroCIPs,  max(fechaInforme) as ultimaFechaRecogida  ";
    	query+=			" FROM bd_pacientesFarmatic f   inner join bd_pacientes p  on f.CIPs=p.CIP full outer join bd_divisionResidencia d on p.idDivisionResidencia=d.idDivisionResidencia ";
    	query+=			" inner join bd_residencia res on res.idResidencia=d.idResidencia ";
    	query+=			" left join bd_farmacia far on (far.codigoUP=f.UPfarmacia  or d.idFarmacia=far.idFarmacia)  ";
    	query+=			" WHERE  res.status='activa' and p.activo='ACTIVO' ";
    	query+=			" GROUP BY far.nombreFarmacia,  d.nombreDivisionResidencia, d.idDivisionResidencia, f.UPfarmacia   ";
    	//
     	
   	  	System.out.println(className + "  - getDatosBdPacientesFarmatic -->  " +query );		
	  	Connection con = Conexion.conectar();
	  	
	  	ResultSet resultSet = null;
		List<ControlDataImportBean> listaBeans= new ArrayList<ControlDataImportBean>();
			try {
				PreparedStatement pstat = con.prepareStatement(query);
		        resultSet = pstat.executeQuery();

		        while (resultSet.next()) {
		        	ControlDataImportBean  c =new ControlDataImportBean();
		            c.setNombreTabla(resultSet.getString("nombreTabla"));
		            c.setNombreFarmacia(resultSet.getString("nombreFarmacia"));
		            c.setNombreDivisionResidencia(resultSet.getString("nombreDivisionResidencia"));
		            c.setIdDivisionResidencia(resultSet.getString("idDivisionResidencia"));
		            if( resultSet.getInt("UPfarmacia")>0)
		            {
			            c.setUPfarmacia(resultSet.getInt("UPfarmacia"));
			            c.setNumeroCIPs(resultSet.getInt("numeroCIPs"));
		            	
		            }

		            
		            /**FECHA*/
		            java.sql.Timestamp ultimaFechaRecogida = resultSet.getTimestamp("ultimaFechaRecogida");
			 		 if(ultimaFechaRecogida!=null) 
					 {
			 		    // Crea un objeto SimpleDateFormat con el formato deseado
			 		    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			 		    // Formatea el Timestamp a una cadena
			 		    String fechaFormateada = (ultimaFechaRecogida != null) ? sdf.format(new Date(ultimaFechaRecogida.getTime())) : "";
							//c.setFechaCalculoPrevision(DateUtilities.getDatetoString("dd/MM/yyyy HH:mm", resultSet.getDate("fechaCalculoPrevision")));
			 		    c.setUltimaFechaRecogida(fechaFormateada);
					 }
			 		/**FIN FECHA*/
		            
		            int dias = getDiasUltimaFecha(c.getUltimaFechaRecogida());
		            c.setDiasDesdeUltimaFecha(getDiasUltimaFecha(c.getUltimaFechaRecogida()));
		            String alerta ="rojo";
		            if(dias<=diasAlerta && dias>-1) alerta ="verde";
		            	
		            c.setAlerta(alerta);
		            	if(c!=null)
		            		listaBeans.add(c);
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		        return listaBeans;
    }



	public static List<ControlDataImportBean> getDatosBdConsejo(String idUsuario, int diasAlerta)throws Exception {
   
    	String query = " SELECT 'bd_consejo' as nombreTabla,  max(dataHoraProces) as ultimaFechaRecogida, count(*) as quants  ";
    	query+=			" FROM bd_consejo   ";
    	
     	
   	  	System.out.println(className + "  - getDatosBdConsejo -->  " +query );		
	  	Connection con = Conexion.conectar();
	  	
	  	ResultSet resultSet = null;
		List<ControlDataImportBean> listaBeans= new ArrayList<ControlDataImportBean>();
			try {
				PreparedStatement pstat = con.prepareStatement(query);
		        resultSet = pstat.executeQuery();

		        while (resultSet.next()) {
		        	ControlDataImportBean  c =new ControlDataImportBean();
		            c.setNombreTabla(resultSet.getString("nombreTabla"));
		            c.setCuantos(resultSet.getInt("quants"));
			            
		            /**FECHA*/
		            java.sql.Timestamp ultimaFechaRecogida = resultSet.getTimestamp("ultimaFechaRecogida");
			 		 if(ultimaFechaRecogida!=null) 
					 {
			 		    // Crea un objeto SimpleDateFormat con el formato deseado
			 		    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			 		    // Formatea el Timestamp a una cadena
			 		    String fechaFormateada = (ultimaFechaRecogida != null) ? sdf.format(new Date(ultimaFechaRecogida.getTime())) : "";
							//c.setFechaCalculoPrevision(DateUtilities.getDatetoString("dd/MM/yyyy HH:mm", resultSet.getDate("fechaCalculoPrevision")));
			 		    c.setUltimaFechaRecogida(fechaFormateada);
					 }
			 		/**FIN FECHA*/
		            
		            int dias = getDiasUltimaFecha(c.getUltimaFechaRecogida());
		            c.setDiasDesdeUltimaFecha(getDiasUltimaFecha(c.getUltimaFechaRecogida()));
		            String alerta ="rojo";
		            if(dias<=diasAlerta && dias>-1) alerta ="verde";
		            	
		            c.setAlerta(alerta);
		            	if(c!=null)
		            		listaBeans.add(c);
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		        return listaBeans;
    }

	public static List<ControlDataImportBean> getProductosDispensadosResis(String idUsuario, int diasAlerta)  throws Exception {
   
    	String query = " SELECT 'hst_ProductosDispensadosResis' as nombreTabla, d.idDivisionResidencia, d.nombreDivisionResidencia, f.idFarmacia, f.nombreFarmacia, p.codigoUPfarmacia as UPfarmacia  ";
    	query+=			" , max(p.fecha) as ultimaFechaEnOrigen, max(p.FechaHoraInsercion) as ultimaFechaRecogida  , count(*) as totalDispensadas ";
    	query+=			" FROM hst_ProductosDispensadosResis p  ";
    	query+=			" INNER JOIN bd_pacientes pa ON (pa.CIP = p.CIP)  ";
    	query+=			" INNER JOIN bd_divisionresidencia d ON d.idDivisionResidencia = pa.idDivisionResidencia  ";
    	query+=			" INNER JOIN bd_residencia res on res.idResidencia=d.idResidencia ";
    	query+=			" INNER JOIN bd_farmacia f ON f.idFarmacia = d.idFarmacia AND p.codigoUPfarmacia = f.codigoUP  ";
    	query+=			" LEFT JOIN bd_consejo c ON c.CODIGO = p.CodigoDispensado  ";
    	query+=			" WHERE  res.status='activa' and  P.FECHA >= getDate()-31 AND p.IdVenta <> 0 ";
    	query+=			" GROUP BY d.idDivisionResidencia, d.nombreDivisionResidencia, f.idFarmacia, f.nombreFarmacia, p.codigoUPfarmacia  ";
        query+=			" order by f.nombreFarmacia,  d.nombreDivisionResidencia   ";
        
       
     	System.out.println(className + "  - getProductosDispensadosResis -->  " +query );		
	  	Connection con = Conexion.conectar();
	  	
	  	ResultSet resultSet = null;
		List<ControlDataImportBean> listaBeans= new ArrayList<ControlDataImportBean>();
			try {
				PreparedStatement pstat = con.prepareStatement(query);
		        resultSet = pstat.executeQuery();

		        while (resultSet.next()) {
		        	ControlDataImportBean  c =new ControlDataImportBean();
		            c.setNombreTabla(resultSet.getString("nombreTabla"));
		            c.setNombreFarmacia(resultSet.getString("nombreFarmacia"));
		            c.setNombreDivisionResidencia(resultSet.getString("nombreDivisionResidencia"));
		            c.setCuantos(resultSet.getInt("totalDispensadas"));
		            c.setUPfarmacia(resultSet.getInt("UPfarmacia"));
		           

		            
		            /**FECHA*/
		            java.sql.Timestamp ultimaFechaEnOrigen = resultSet.getTimestamp("ultimaFechaEnOrigen");
		            java.sql.Timestamp ultimaFechaRecogida = resultSet.getTimestamp("ultimaFechaRecogida");
		            if(ultimaFechaEnOrigen!=null) 
					 {
			 		    // Crea un objeto SimpleDateFormat con el formato deseado
			 		    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			 		    // Formatea el Timestamp a una cadena
			 		    String fechaFormateada = (ultimaFechaEnOrigen != null) ? sdf.format(new Date(ultimaFechaEnOrigen.getTime())) : "";
							//c.setFechaCalculoPrevision(DateUtilities.getDatetoString("dd/MM/yyyy HH:mm", resultSet.getDate("fechaCalculoPrevision")));
			 		    c.setUltimaFechaEnOrigen(fechaFormateada);
					 }
			         if(ultimaFechaRecogida!=null) 
					 {
				 		 // Crea un objeto SimpleDateFormat con el formato deseado
				 		 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				 		 // Formatea el Timestamp a una cadena
				 		 String fechaRecogidaFormateada = (ultimaFechaRecogida != null) ? sdf.format(new Date(ultimaFechaRecogida.getTime())) : "";
						 //c.setFechaCalculoPrevision(DateUtilities.getDatetoString("dd/MM/yyyy HH:mm", resultSet.getDate("fechaCalculoPrevision")));
				 		 c.setUltimaFechaRecogida(fechaRecogidaFormateada);
					 }
				 	/**FIN FECHA*/
			            
			            
			            
		            
		            int dias = getDiasUltimaFecha(c.getUltimaFechaEnOrigen());
		            c.setDiasDesdeUltimaFecha(getDiasUltimaFecha(c.getUltimaFechaEnOrigen()));
		            String alerta ="rojo";
		            if(dias<=diasAlerta && dias>-1) alerta ="verde";
		            	
		            c.setAlerta(alerta);
		            	if(c!=null)
		            		listaBeans.add(c);
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		        return listaBeans;
    }

	private static int getDiasUltimaFecha(String ultimaFechaRecogida) {
		
		long dias=-1;
		if(ultimaFechaRecogida!=null && !ultimaFechaRecogida.equals(""))
		{
			Date fechaProceso = DateUtilities.getDate(ultimaFechaRecogida, "dd/MM/yyyy");
			dias = DateUtilities.getLengthInDays(fechaProceso, new Date());
		}
			
		return new Integer((int) dias).intValue();
	}

//hst_CodigosRecetasDispensados

	public static List<ControlDataImportBean> getCodigosRecetasDispensados(String idUsuario, int diasAlerta)  throws Exception {
		// TODO Esbozo de método generado automáticamente throws Exception {
   
    	String query = " select 'hst_CodigosRecetasDispensados' as nombreTabla, f.idFarmacia, f.nombreFarmacia, d.idDivisionResidencia, d.nombreDivisionResidencia,    ";
      	query+=			" p.codigoUPfarmacia as UPfarmacia, max(p.fecha) as ultimaFechaEnOrigen, max(p.FechaHoraInsercion) as ultimaFechaRecogida, count(*) as cuantas ";
      	query+=			" from hst_CodigosRecetasDispensados p  ";
      	query+=			" INNER JOIN bd_pacientes pa ON (pa.CIP = p.CIP)";
      	query+=			" INNER JOIN bd_divisionresidencia d ON d.idDivisionResidencia = pa.idDivisionResidencia ";
       	query+=			" INNER JOIN bd_residencia res on res.idResidencia=d.idResidencia ";
        query+=			" INNER JOIN bd_farmacia f on p.codigoUPfarmacia=f.codigoUP ";
        query+=			" WHERE  res.status='activa' and   P.FECHA >= getDate()-31 AND p.IdVenta <> 0 ";
        query+=			" group by  f.idFarmacia, f.nombreFarmacia, d.idDivisionResidencia, d.nombreDivisionResidencia, p.codigoUPfarmacia  ";
        query+=			" order by f.nombreFarmacia,  d.nombreDivisionResidencia   ";
    	 
      	 
     	System.out.println(className + "  - getCodigosRecetasDispensados -->  " +query );		
	  	Connection con = Conexion.conectar();
	  	
	  	ResultSet resultSet = null;
		List<ControlDataImportBean> listaBeans= new ArrayList<ControlDataImportBean>();
			try {
				PreparedStatement pstat = con.prepareStatement(query);
		        resultSet = pstat.executeQuery();

		        while (resultSet.next()) {
		        	ControlDataImportBean  c =new ControlDataImportBean();
		            c.setNombreTabla(resultSet.getString("nombreTabla"));
		            c.setNombreFarmacia(resultSet.getString("nombreFarmacia"));
		            c.setUPfarmacia(resultSet.getInt("UPfarmacia"));
		            c.setNombreDivisionResidencia(resultSet.getString("nombreDivisionResidencia"));
		            c.setCuantos(resultSet.getInt("cuantas"));

		            
		            /**FECHA*/
		            java.sql.Timestamp ultimaFechaEnOrigen = resultSet.getTimestamp("ultimaFechaEnOrigen");
		            java.sql.Timestamp ultimaFechaRecogida = resultSet.getTimestamp("ultimaFechaRecogida");
		            if(ultimaFechaEnOrigen!=null) 
					 {
			 		    // Crea un objeto SimpleDateFormat con el formato deseado
			 		    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			 		    // Formatea el Timestamp a una cadena
			 		    String fechaFormateada = (ultimaFechaEnOrigen != null) ? sdf.format(new Date(ultimaFechaEnOrigen.getTime())) : "";
							//c.setFechaCalculoPrevision(DateUtilities.getDatetoString("dd/MM/yyyy HH:mm", resultSet.getDate("fechaCalculoPrevision")));
			 		    c.setUltimaFechaEnOrigen(fechaFormateada);
					 }
			         if(ultimaFechaRecogida!=null) 
					 {
				 		 // Crea un objeto SimpleDateFormat con el formato deseado
				 		 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				 		 // Formatea el Timestamp a una cadena
				 		 String fechaRecogidaFormateada = (ultimaFechaRecogida != null) ? sdf.format(new Date(ultimaFechaRecogida.getTime())) : "";
						 //c.setFechaCalculoPrevision(DateUtilities.getDatetoString("dd/MM/yyyy HH:mm", resultSet.getDate("fechaCalculoPrevision")));
				 		 c.setUltimaFechaRecogida(fechaRecogidaFormateada);
					 }
				 	/**FIN FECHA*/
			         
		            int dias = getDiasUltimaFecha(c.getUltimaFechaEnOrigen());
		            c.setDiasDesdeUltimaFecha(getDiasUltimaFecha(c.getUltimaFechaEnOrigen()));
		            String alerta ="rojo";
		            if(dias<=diasAlerta && dias>-1) alerta ="verde";
		            	
		            c.setAlerta(alerta);
		            	if(c!=null)
		            		listaBeans.add(c);
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		        return listaBeans;
    }


//hst_recetasCaducadas
	public static List<ControlDataImportBean> getRecetasCaducadas(String idUsuario, int diasAlerta) throws Exception {
   
    	String query = " select 'hst_recetasCaducadas' as nombreTabla, far.idFarmacia, far.nombreFarmacia,  d.idDivisionResidencia, d.nombreDivisionResidencia, ";
    	query+=			" max(CONVERT(datetime, c.fechaHoraProces, 103)) as ultimaFechaRecogida    ";
    	query+=			" from hst_recetasCaducadas c full outer join bd_divisionResidencia d on c.idDivisionResidencia=d.idDivisionResidencia  ";
    	query+=			" inner join bd_residencia res on res.idResidencia=d.idResidencia ";
    	query+=			" inner join bd_farmacia far on far.idFarmacia=d.idFarmacia "; 
    	query+=			" WHERE  res.status='activa' ";
    	query+=			" group by far.idFarmacia, far.nombreFarmacia, d.idDivisionResidencia, d.nombreDivisionResidencia  ";
    	query+=			" order by far.nombreFarmacia,  d.nombreDivisionResidencia   ";

    	
   	  	System.out.println(className + "  - getRecetasCaducadas -->  " +query );		
	  	Connection con = Conexion.conectar();
	  	
	  	ResultSet resultSet = null;
		List<ControlDataImportBean> listaBeans= new ArrayList<ControlDataImportBean>();
			try {
				PreparedStatement pstat = con.prepareStatement(query);
		        resultSet = pstat.executeQuery();

		        while (resultSet.next()) {
		        	ControlDataImportBean  c =new ControlDataImportBean();
		            c.setNombreTabla(resultSet.getString("nombreTabla"));
		            c.setNombreFarmacia(resultSet.getString("nombreFarmacia"));		            
		            c.setNombreDivisionResidencia(resultSet.getString("nombreDivisionResidencia"));
		            c.setIdDivisionResidencia(resultSet.getString("idDivisionResidencia"));
			       // c.setNumeroCIPs(resultSet.getInt("numeroCIPs"));
		            
		            /**FECHA*/
		            java.sql.Timestamp ultimaFechaRecogida = resultSet.getTimestamp("ultimaFechaRecogida");
			 		 if(ultimaFechaRecogida!=null) 
					 {
			 		    // Crea un objeto SimpleDateFormat con el formato deseado
			 		    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			 		    // Formatea el Timestamp a una cadena
			 		    String fechaFormateada = (ultimaFechaRecogida != null) ? sdf.format(new Date(ultimaFechaRecogida.getTime())) : "";
							//c.setFechaCalculoPrevision(DateUtilities.getDatetoString("dd/MM/yyyy HH:mm", resultSet.getDate("fechaCalculoPrevision")));
			 		    c.setUltimaFechaRecogida(fechaFormateada);
					 }
			 		/**FIN FECHA*/
		            
		            int dias = getDiasUltimaFecha(c.getUltimaFechaRecogida());
		            c.setDiasDesdeUltimaFecha(getDiasUltimaFecha(c.getUltimaFechaRecogida()));
		            String alerta ="rojo";
		            if(dias<=diasAlerta && dias>-1) alerta ="verde";  //aqui damos más margen
		            	
		            c.setAlerta(alerta);
		            	if(c!=null)
		            		listaBeans.add(c);
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		        return listaBeans;
    }



	public static List<ControlDataImportBean> getComprasVentasXProveedor(String idUsuario, int diasAlerta) throws SQLException, ParseException {
	   	String query = " SELECT 'tbl_comprasXventasXproveedor' as nombreTabla, f.nombreFarmacia, max(cv.fechaCompra) as ultimaFechaEnOrigen, max(cv.fechaExtraccion) as ultimaFechaRecogida    ";
    	query+=			" FROM tbl_comprasXventasXproveedor cv   ";
    	query+=			" LEFT JOIN bd_consejo b ON cv.cnCompra = b.Codigo ";
    	query+=			" INNER JOIN bd_farmacia f ON f.codigoUP = cv.UPfarmacia ";
    	query+=			" WHERE 1=1  ";
    	query+=			" AND UPPER(proveedor) LIKE '%FARMALOGIC%' AND b.codgtvmpp <> '' ";
       	query+=			" GROUP BY f.nombreFarmacia ";
        
   	  	System.out.println(className + "  - getComprasVentasXProveedor -->  " +query );		
	  	Connection con = Conexion.conectar();
	  	
	  	ResultSet resultSet = null;
		List<ControlDataImportBean> listaBeans= new ArrayList<ControlDataImportBean>();
			try {
				PreparedStatement pstat = con.prepareStatement(query);
		        resultSet = pstat.executeQuery();

		        while (resultSet.next()) {
		        	ControlDataImportBean  c =new ControlDataImportBean();
		            c.setNombreTabla(resultSet.getString("nombreTabla"));
		            c.setNombreFarmacia(resultSet.getString("nombreFarmacia"));
		            c.setUltimaFechaEnOrigen(resultSet.getString("ultimaFechaEnOrigen"));
		            

		            /**FECHA*/
		           
		            java.sql.Timestamp ultimaFechaRecogida = resultSet.getTimestamp("ultimaFechaRecogida");
			 		
		            if(c.getUltimaFechaEnOrigen()!=null && !c.getUltimaFechaEnOrigen().equals("")) 
					 {
			 		    // Crea un objeto SimpleDateFormat con el formato deseado
			 	        SimpleDateFormat sdfIn = new SimpleDateFormat("yyyy-MM-dd");
			 	        SimpleDateFormat sdfOut = new SimpleDateFormat("dd/MM/yyyy");
			 	        Date fechaDate = sdfIn.parse(c.getUltimaFechaEnOrigen());
			 	        String fechaOrigenFormateada = sdfOut.format(fechaDate);
			 		    c.setUltimaFechaEnOrigen(fechaOrigenFormateada);
					 }
		            if(ultimaFechaRecogida!=null) 
					 {
			 		    // Crea un objeto SimpleDateFormat con el formato deseado
			 		    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			 		    // Formatea el Timestamp a una cadena
			 		    String fechaRecogidaFormateada = (ultimaFechaRecogida != null) ? sdf.format(new Date(ultimaFechaRecogida.getTime())) : "";
							//c.setFechaCalculoPrevision(DateUtilities.getDatetoString("dd/MM/yyyy HH:mm", resultSet.getDate("fechaCalculoPrevision")));
			 		    c.setUltimaFechaRecogida(fechaRecogidaFormateada);
					 }
			 		/**FIN FECHA*/
			 		 
			            
		            int dias = getDiasUltimaFecha(c.getUltimaFechaEnOrigen());
		            c.setDiasDesdeUltimaFecha(getDiasUltimaFecha(c.getUltimaFechaEnOrigen()));
		            String alerta ="rojo";
		          //  if(dias<SPDConstants.DIAS_MAX_PROCESOS_IMPORT && dias>-1) alerta ="verde";
		            if(dias<=diasAlerta && dias>-1) alerta ="verde";
		            	
		            c.setAlerta(alerta);
		            	if(c!=null)
		            		listaBeans.add(c);
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		        return listaBeans;
    }


	public static List<ControlDataImportBean> getInfoDrugCodeMaster(String idUsuario, int diasAlerta) throws SQLException, ParseException {
	   	String query = " SELECT 'drugBarcodeMASTERS' as nombreTabla, count(cv.drugNumber) as  emes, count(distinct cv.cassette) as  tolvas  ";
    	query+=			" , cv.nombreRobot, max(cv.FechaHoraRecogida  ) as ultimaFechaRecogida ";
    	query+=			" FROM drugBarcodeMASTERS cv  ";
    	query+=			" INNER JOIN bd_robot r on cv.nombreRobot=r.nombreRobot  "; 
    	query+=			" and r.ACTIVO='SI'  ";
    	query+=			" group by cv.nombreRobot ";
        
        	
   	  	System.out.println(className + "  - getInfoDrugCodeMaster -->  " +query );		
	  	Connection con = Conexion.conectar();
	  	
	  	ResultSet resultSet = null;
		List<ControlDataImportBean> listaBeans= new ArrayList<ControlDataImportBean>();
			try {
				PreparedStatement pstat = con.prepareStatement(query);
		        resultSet = pstat.executeQuery();

		        while (resultSet.next()) {
		        	ControlDataImportBean  c =new ControlDataImportBean();
		            c.setNombreTabla(resultSet.getString("nombreTabla"));
		            c.setNombreFarmacia(resultSet.getString("nombreRobot"));
		            c.setCuantos(resultSet.getInt("emes"));
		            c.setCuantasTolvasRobot(resultSet.getInt("tolvas"));

		            /**FECHA*/
		            java.sql.Timestamp ultimaFechaRecogida = resultSet.getTimestamp("ultimaFechaRecogida");
			 		if(ultimaFechaRecogida!=null) 
					 {
			 		    // Crea un objeto SimpleDateFormat con el formato deseado
			 		    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			 		    // Formatea el Timestamp a una cadena
			 		    String fechaFormateada = (ultimaFechaRecogida != null) ? sdf.format(new Date(ultimaFechaRecogida.getTime())) : "";
							//c.setFechaCalculoPrevision(DateUtilities.getDatetoString("dd/MM/yyyy HH:mm", resultSet.getDate("fechaCalculoPrevision")));
			 		    c.setUltimaFechaRecogida(fechaFormateada);
					 }
			 		/**FIN FECHA*/
		            
		            int dias = getDiasUltimaFecha(c.getUltimaFechaRecogida());
		            c.setDiasDesdeUltimaFecha(getDiasUltimaFecha(c.getUltimaFechaRecogida()));
		            String alerta ="rojo";
		            if(dias<=diasAlerta && dias>-1) alerta ="verde";
		            	
		            c.setAlerta(alerta);
		            	if(c!=null)
		            		listaBeans.add(c);
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		        return listaBeans;
    }








    
    
}
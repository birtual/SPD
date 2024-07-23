package spd_test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lopicost.config.pool.dbaccess.Conexion;
public class arreglosFechaExcel {


		public static void main(String[] args) throws SQLException {


	     Connection connection = Conexion.conectar();
		try {
	    		String 	selectQuery =  " SELECT detalleRow  FROM dbo.SPD_ficheroResiDetalle  where resiCIP='GEMA1230718000' ";
	              // Ejecuta la consulta
	            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
	            ResultSet resultSet = selectStatement.executeQuery();

	            // Formato de fecha "dd/MM/yyyy"
	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	            // Itera sobre los resultados y actualiza las fechas
	            while (resultSet.next()) {
	            	
	            	//String cadena = "FAVA1580914005, Farnòs Vázquez, Encarna, LEVETIRACETAM 250 mg. Comprimidos 100 u., 691708, 0, 1, 0, 0, 1, 0, 43394, , , , , , x, x, x, x, x, x, x|";
	            	String cadena =resultSet.getString("detalleRow");
	    	        String cadenaTransformada = transformarFechas(cadena);
	    	        if(cadenaTransformada==null || cadenaTransformada.equals("")) cadenaTransformada=cadena;
	    	        System.out.println("Cadena : " + cadena);
	    	        System.out.println("Cadena transformada: " + cadenaTransformada);
	    	        
	    	        //System.out.println(" UPDATE dbo.SPD_ficheroResiDetalle SET aux1='" + cadenaTransformada + "' where  detalleRow='" + cadena +"';");
	    	        

	                // Actualiza el registro en la base de datos con la nueva fecha formateada
	                String updateQuery = "UPDATE dbo.SPD_ficheroResiDetalle SET aux1= '"+ cadenaTransformada + "' WHERE detalleRow = '"+ cadena + "'";
	                
	                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);

	                System.out.println("updateQuery : " + updateQuery);
	                
	                updateStatement.executeUpdate();
	            }

	    
	          
	 	     } catch (SQLException e) {
	 	         e.printStackTrace();
	 	     }finally {        // Cierra la conexión
	 	    	 connection.close();}
	 
	    }
	     	    
	     	    
	     	    
		
		public static String transformarFechas(String cadena) {
	        // Expresión regular para encontrar números de fecha en formato Excel
	        //String regex = "(?<=,\\s)(\\d{5,})(?=,)";
	        String regex = "(?<=,\\s)(4\\d{4})(?=,)";
	        Pattern pattern = Pattern.compile(regex);
	        Matcher matcher = pattern.matcher(cadena);

	        // Formato de fecha "dd/MM/yyyy"
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	        // Iterar sobre las coincidencias y reemplazarlas con la fecha convertida
	        StringBuffer sb = new StringBuffer();
	        while (matcher.find()) {
	            long fechaNumerica = Long.parseLong(matcher.group());
	            LocalDate fecha = convertirFechaNumerica(fechaNumerica);
	            String fechaFormateada = fecha.format(formatter);
	            matcher.appendReplacement(sb, ", " + fechaFormateada + ",");
	        }
	        matcher.appendTail(sb);

	        return sb.toString();
	    }

	    public static LocalDate convertirFechaNumerica(long fechaNumerica) {
	        // La fecha base de Excel es el 1 de enero de 1900 con un error de 1 día
	        LocalDate fechaBaseExcel = LocalDate.of(1899, 12, 30);

	        // Sumar el número de días a la fecha base de Excel
	        return fechaBaseExcel.plusDays(fechaNumerica);
	    }
	}


    
package spd_test.test;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class executaSql {
    
	    public static void main(String[] args) {
	        ejecutarSQLCMD("C:\\UTILS\\1\\JosepMiracle_1.sql");
	        ejecutarSQLCMD("C:\\UTILS\\1\\JosepMiracle_2.sql");
	        ejecutarSQLCMDXml("C:\\UTILS\\1\\JosepMiracle_3.sql", "C:\\UTILS\\1\\JosepMiracle_FILIA_DM.xml");
	        ejecutarSQLCMDXml("C:\\UTILS\\1\\JosepMiracle_4.sql", "C:\\UTILS\\1\\JosepMiracle_FILIA_RX.xml");
	    }

	    public static void ejecutarSQLCMD(String nombreArchivoSQL) {
	        try {
	            // Ruta del ejecutable SQLCMD.EXE (ajusta la ruta según tu instalación)
	           // String rutaSqlCmd = "C:\\Program Files\\Microsoft SQL Server\\Client SDK\\ODBC\\120\\Tools\\Binn\\SQLCMD.EXE";

	            // Comando para ejecutar SQLCMD.EXE con el archivo SQL
	            String comando = "  SQLCMD.EXE -S localhost -d SPDAC -U sa -P manager -i " + nombreArchivoSQL;
	           
	            // Construir el proceso
	            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", comando);

	            // Redirigir la salida del proceso a la consola (puedes cambiarlo según tus necesidades)
	            processBuilder.redirectErrorStream(true);

	            // Iniciar el proceso
	            Process proceso = processBuilder.start();

	            // Esperar a que el proceso termine
	            int exitCode = proceso.waitFor();

	            // Imprimir la salida del proceso
	            System.out.println("Salida d el comando SQLCMD.EXE:\n" + obtenerSalidaProceso(proceso));

	            // Imprimir el código de salida
	            System.out.println("Código de salida: " + exitCode);
	        } catch (IOException | InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    public static void ejecutarSQLCMDXml(String nombreArchivoSQL, String nombreArchivoSalida) {
	    	  try {
	              // Establecer la conexión a la base de datos
	              String url = "jdbc:sqlserver://localhost:1433;databaseName=SPDAC;user=sa;password=manager;";
	              Connection connection = DriverManager.getConnection(url);

	              // Leer el contenido del archivo SQL línea por línea con codificación UTF-16
	              StringBuilder sqlBuilder = new StringBuilder();
	              try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(nombreArchivoSQL), Charset.forName("UTF-16")))) {
	                  String line;
	                  while ((line = reader.readLine()) != null) {
	                      sqlBuilder.append(line).append("\n");
	                  }
	              }

	              // Preparar la declaración SQL
	              PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());

	              // Ejecutar la consulta
	              ResultSet resultSet = preparedStatement.executeQuery();

	              // Escribir el resultado en un archivo XML de manera formateada
	              try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivoSalida))) {
	                  writer.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
	                  while (resultSet.next()) {
	                      String xmlResultado = resultSet.getString(1);

	                      // Manipulación de la cadena XML para formatearla correctamente
	                      xmlResultado = xmlResultado.replace("><", ">\n<");

	                      writer.write(xmlResultado.trim());
	                      writer.newLine();
	                  }
	              }

	              // Cerrar recursos
	              resultSet.close();
	              preparedStatement.close();
	              connection.close();
	          } catch (IOException e) {
	              e.printStackTrace();
	          } catch (Exception e) {
	              e.printStackTrace();
	          }
	      }
	    
/*	    public static void ejecutarSQLCMDXml(String nombreArchivoSQL, String nombreArchivoSalida) {
	        try {
	            // Ruta del ejecutable SQLCMD.EXE (ajusta la ruta según tu instalación)
	        	// String rutaSqlCmd = "C:\\Program Files\\Microsoft SQL Server\\Client SDK\\ODBC\\170\\Tools\\Binn\\SQLCMD.EXE";

	            // Comando para ejecutar SQLCMD.EXE con el archivo SQL
	        	 String comando = "  SQLCMD.EXE -S localhost -d SPDAC -U sa -P manager -i " + nombreArchivoSQL;


	             // Construir el proceso
	             ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", comando);

	             // Redirigir la salida del proceso a un archivo temporal
	             File archivoTemporal = new File("temp.xml");
	             processBuilder.redirectOutput(ProcessBuilder.Redirect.to(archivoTemporal));

	             // Iniciar el proceso
	             Process proceso = processBuilder.start();

	             // Esperar a que el proceso termine
	             int exitCode = proceso.waitFor();

	             // Imprimir el código de salida
	             System.out.println("Código de salida: " + exitCode);

	             // Leer el contenido del archivo temporal
	             byte[] contenidoTemporal = Files.readAllBytes(archivoTemporal.toPath());


	             Files.write(Paths.get(nombreArchivoSalida), contenidoTemporal, StandardOpenOption.APPEND);

	             // Eliminar el archivo temporal
	             Files.deleteIfExists(archivoTemporal.toPath());
	         } catch (IOException | InterruptedException e) {
	             e.printStackTrace();
	         }
	     }
	*/


	    private static String obtenerSalidaProceso(Process proceso) throws IOException {
	        // Leer la salida del proceso
	        StringBuilder salida = new StringBuilder();
	        byte[] buffer = new byte[1024];
	        int longitud;

	        while ((longitud = proceso.getInputStream().read(buffer)) != -1) {
	            salida.append(new String(buffer, 0, longitud));
	        }

	        return salida.toString();
	    }
	}


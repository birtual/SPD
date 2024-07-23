package spd_test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import lopicost.spd.persistence.FicheroResiCabeceraDAO;
import lopicost.spd.robot.helper.PlantillaUnificadaHelper;
import lopicost.spd.robot.model.Bottle;
import lopicost.spd.robot.model.DrugRX;
import lopicost.spd.robot.model.FiliaDM;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.utils.DataUtil;
import lopicost.spd.utils.DateUtilities;
import lopicost.spd.utils.HelperSPD;
import lopicost.spd.utils.SPDConstants;
import lopicost.spd.utils.StringUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class pruebas2 {


		public static void main(String[] args) throws Exception {
			// TODO Auto-generated method stub
			/*System.out.println(new Date().getTime());
			System.out.println(new Date().getTime()/10000);
	
			String texto="OVATA EFG 3.5g/sob mg.   Sobres 30 u. ";
			System.out.println(texto);
	
			texto=StringUtil.quitaEspacios(texto);
			System.out.println(texto);
			
	
			texto=StringUtils.strip(texto, " \t\u00A0\u1680\u180e\u2000\u200a\u202f\u205f\u3000");
			System.out.println(texto);
			
			texto=cleanTheText(texto);
			System.out.println(texto);
			
			String campoOrder=" "; 
			if(campoOrder.trim().equals(""))
				System.out.println("trim ok");
			else if(campoOrder.trim().equals(" "))
				System.out.println("no ok");
			
			String result ="1.dd50gotas";


			
	/*		String a = "Áaáaëe";
			a = a.replaceAll("[^a-zA-Z0-9]", "");
			System.out.println(a);
			
			 a = "20230719_20230719_168980148";
			System.out.println(a.substring(0,8));
			System.out.println(a.substring(9,17));
	*/	
			/*String patron ="[0-9]"; 
			String a = "1,5ml";
				
			System.out.println(contieneNumeros(a));
		
			String b = HelperSPD.getPautaStandard(a);
			System.out.println(a);
			System.out.println(b);
				
			String cnResi = "CARLOS";
			System.out.println(cnResi.substring(0, 6));
			*/
	        // Define la cadena de texto original
	  /*      String mensaje = "info2 Corregir Código nacional - El CN 699158 pertenece a ESOMEPRAZOL 20 MG CAPSULA. Si se trata de OMEPRAZOL 20 MG CAPSULA sugerimos cambiarlo por el CN 706633 info2";
	        System.out.println("mensaje: " + mensaje);

	
	        long dias = 30;
	        Date fechaInicio = null;
	        Date fechaFin = null;
	        try {
	            fechaInicio = DateUtilities.getDate("02/11/2023", "dd/MM/yyyy");
	        }
	        catch (Exception ex) {}
	        fechaFin = DateUtilities.getDate("03/11/2023", "dd/MM/yyyy");
	
	         int milisecondsByDay = 86400000;
	        try {
	        	System.out.println(fechaInicio.getTime() + " fechaInicio.getTime()  ");
	           	System.out.println(fechaFin.getTime() + " fechaFin.getTime()  ");
	         	System.out.println((fechaFin.getTime() -fechaInicio.getTime()) + " resta  ");
	         	System.out.println((fechaFin.getTime() -fechaInicio.getTime()) / (milisecondsByDay ) + " división  ");
	
	            dias =  (fechaFin.getTime() - fechaInicio.getTime()) / milisecondsByDay + 1;
	            dias = (fechaFin.getTime() - fechaInicio.getTime()) / milisecondsByDay + 1;
	
	            
	            System.out.println(dias+ " dias ");
	            
	            // Tu cadena original
		        String cadenaOriginal = "Ejemplo: 123#texto y 456#otro texto";
		           ReemplazarSubstring(cadenaOriginal);
		   }
	        catch (Exception ex3) {}
	        
	        
	        String excelFilePath = "C:/Users/carco/Desktop/SPD/RESIS/1_ROBOT/2_JOANXXIII/20240102_20240118/1/TEST_TODO - copia.xlsx"; // Cambia esto a la ruta de tu archivo Excel
	        String csvFilePath = "C:/Users/carco/Desktop/SPD/RESIS/1_ROBOT/2_JOANXXIII/20240102_20240118/1/TEST_TODO_COPIACSV.csv";    // Cambia esto a la ruta donde deseas guardar el archivo CSV
	
			 convertExcelToCsv(excelFilePath, csvFilePath);
			
			
			 String result = "45416";
	         String fecha = convertirNumeroAFecha(result);
	         System.out.println("Número " + result + " convertido a fecha: " + fecha);
	         String nombreArchivo = "C:/UTILS/1/testColumnas.xlsx";
	         int numeroHoja = 0; // El índice de la hoja en la que deseas trabajar
	         int columnaOrigen = 11; // Índice de la columna de origen (12ª columna)
	         int columnaDestino = 2; // Índice de la columna de destino (3ª columna)

	         try (FileInputStream fileInputStream = new FileInputStream(nombreArchivo)) {
	             Workbook workbook = new XSSFWorkbook(fileInputStream);
	             Sheet sheet = workbook.getSheetAt(numeroHoja);

	             // Iterar sobre las filas de la hoja
	             for (Row row : sheet) {
	                 Cell cellOrigen = row.getCell(columnaOrigen);
	                 Cell cellDestino = row.getCell(columnaDestino);

	                 if (cellOrigen != null && cellDestino != null) {
	                     // Obtener el valor de la celda de origen
	                     CellValue cellValue = evaluateCell(cellOrigen, workbook.getCreationHelper());
	                     String valorOrigen = cellValue.formatAsString();

	                     // Establecer el valor de la celda de destino con el valor de la celda de origen
	                     cellDestino.setCellValue(valorOrigen);

	                     // Limpiar la celda de origen
	                     cellOrigen.setCellValue("");
	                 }
	             }

	             // Guardar los cambios en el archivo
	             try (FileOutputStream fileOutputStream = new FileOutputStream(nombreArchivo)) {
	                 workbook.write(fileOutputStream);
	             }

	             System.out.println("Posición de columna cambiada exitosamente.");

	         } catch (IOException e) {
	             e.printStackTrace();
	         }
	    		long currentTimeMillis = System.currentTimeMillis();
	    		String firstFiveDigits = String.valueOf(currentTimeMillis).substring(4, 10);
	    		System.out.println(firstFiveDigits);
	    	
			String a= "1234567";
			System.out.println(a.substring(0, 6));	
			
			int[] fechasNumericas = {0, 1, 0, 43394, 42795,
					45100,
					42795,
					42809,
					43394,
					44531,
					43495,
					44433,
					44055,
					43572,
					44819,
					42795,
					43781,
					44055,
					43761,
					43349,
					45263,
					45260
};

		       // Convertir fechas numéricas a fechas legibles
	        for (int fechaNumerica : fechasNumericas) {
	            LocalDate fecha = convertirFechaNumerica(fechaNumerica);
	            System.out.println("Fecha numérica: " + fechaNumerica + " - Fecha legible: " + fecha);
	        }
		   
	        String cadena = "FAVA1580914005, Farnòs Vázquez, Encarna, LEVETIRACETAM 250 mg. Comprimidos 100 u., 691708, 0, 1, 0, 0, 1, 0, 43394, , , , , , x, x, x, x, x, x, x|";
	        String cadenaTransformada = transformarFechas(cadena);
	        System.out.println("Cadena transformada: " + cadenaTransformada);
		            
	        */ 
			//System.out.println("Date: " + new Date());
			//System.out.println(DateUtilities.getDatetoString("dd_MM_yyyy_hh_mm", new Date()));
			//String spdUsuario="admin";
			//FicheroResiBean cab = FicheroResiCabeceraDAO.getFicheroResiCabeceraByOid(spdUsuario, 11134);

			//PlantillaUnificadaHelper.procesaPaso1DetalleTomas(spdUsuario, cab);
			
			//crearXML();

			     //   UUID uuid = UUID.randomUUID();
			       // System.out.println("UUID=" + uuid.toString() );
		/*	String detalleRow="0123456789";
				detalleRow=detalleRow.substring(0, 10);
			String detalleRow2="1234567890";
				detalleRow2=detalleRow2.substring(1, 7);
				
				System.out.println(detalleRow);
				System.out.println(detalleRow2);
				
				SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyyMMdd");
	            SimpleDateFormat formatoObjetivo = new SimpleDateFormat("dd/MM/yyyy");
		        
	            String fechaInicioTratamiento="0/07/2024";
		        String fechaFinTratamientoNull="09/07/2024";;
		               	
		        Date dateDesde = (Date) formatoObjetivo.parse(fechaInicioTratamiento);
		        Date dateHasta = (Date) formatoObjetivo.parse(fechaFinTratamientoNull);
		        
		        
		        System.out.println(" dateDesde " +  dateDesde);
		        System.out.println(" dateHasta " +  dateHasta);
		         	
		        Date hoy = new Date();
		        System.out.println(" hoy " +  hoy);
		        boolean comprobacion = DateUtilities.isBeetwenTime(dateDesde, dateHasta, hoy);    	
		        System.out.println(" comprobacion " +  comprobacion);
		            	
		        double valorDouble = Double.parseDouble("7.01");
		        
				System.out.println(" comprobacion " +       (int) Math.ceil(valorDouble));
				System.out.println(" comprobacion " +       (int) Math.ceil(valorDouble/7));
				
				System.out.println(" Menor " + Math.min(SPDConstants.MAX_COMPRIMIDOS_POR_BOLSA,SPDConstants.MAX_LINEAS_PASTILLERO_POR_BOLSA));
				System.out.println(" int "  +(int) Math.ceil(Double.parseDouble("7.01")/7));
				
				
				
			      // La fecha en formato yyyy/MM/dd
		        String inputDate = "2024/02/22";

		        try {
		            // Parsear la fecha de entrada
		            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy/MM/dd");
		            Date date = inputFormat.parse(inputDate);

		            // Formatear la fecha en el formato deseado
		            SimpleDateFormat outputFormat = new SimpleDateFormat("EEEE, dd MMM yyyy", new Locale("ca", "ES"));
		            String formattedDate = outputFormat.format(date);

		            // Convertir a mayúsculas
		            formattedDate = formattedDate.toUpperCase();

		            // Imprimir la fecha formateada
		            System.out.println(formattedDate);
		        } catch (Exception e) {
		            e.printStackTrace();
		        } */
		        
            	Calendar calendar = Calendar.getInstance();
            	
            	String stringBuilder = calendar.YEAR  + String.format("%02d", calendar.MONTH) + String.format("%02d", calendar.DAY_OF_MONTH);
				String idFree= stringBuilder.toString();
            	System.out.println(idFree);
            	
               	LocalDate currentDate = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                String formattedDate = currentDate.format(formatter);
                System.out.println(formattedDate);
                
				
		}
		
		
        public static void crearXML() {
            try {
  /*              // Crear objetos y configurar datos
                Bottle bottle1 = new Bottle();
                bottle1.setBarcode("8470006500071");
                bottle1.setOneBottleQuantity(100);

                DrugRX drug1 = new DrugRX();
                drug1.setCode("650007");
                drug1.setName("DEPAKINE 200MG");
                drug1.setStockLocation("");
                drug1.setBottle(bottle1);

                Bottle bottle2 = new Bottle();
                bottle2.setBarcode("8470006506196");
                bottle2.setOneBottleQuantity(28);

                DrugRX drug2 = new DrugRX();
                drug2.setCode("650619");
                drug2.setName("SIMVASTATINA 20MG");
                drug2.setStockLocation("");
                drug2.setBottle(bottle2);

                Bottle bottle3 = new Bottle();
                bottle3.setBarcode("8470006506202");
                bottle3.setOneBottleQuantity(28);

                DrugRX drug3 = new DrugRX();
                drug3.setCode("650620");
                drug3.setName("SIMVASTATINA 40MG");
                drug3.setStockLocation("");
                drug3.setBottle(bottle3);

                FiliaDM request = new FiliaDM();
                request.setRequestType(10);
                request.setDrugs(Arrays.asList(drug1, drug2, drug3));

                // Configurar JAXB y marshalling
                JAXBContext context = JAXBContext.newInstance(FiliaDM.class);
                Marshaller marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

                // Escribir a un archivo
                File file = new File("filiaServiceRequest.xml");
                marshaller.marshal(request, file);

                System.out.println("Archivo XML generado exitosamente: " + file.getAbsolutePath());
*/

                
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        
        
		 public static String transformarFechas(String cadena) {
		        // Expresión regular para encontrar números de fecha que comienzan con 4 y tienen 5 cifras
		        String regex = "\\b4\\d{4}\\b";
		        Pattern pattern = Pattern.compile(regex);
		        Matcher matcher = pattern.matcher(cadena);

		        // Formato de fecha "dd/MM/yyyy"
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		        // Iterar sobre las coincidencias y reemplazarlas con la fecha convertida
		        StringBuffer sb = new StringBuffer();
		        while (matcher.find()) {
		            int fechaNumerica = Integer.parseInt(matcher.group());
		            LocalDate fecha = convertirFechaNumerica(fechaNumerica);
		            String fechaFormateada = fecha.format(formatter);
		            matcher.appendReplacement(sb, fechaFormateada);
		        }
		        matcher.appendTail(sb);

		        return sb.toString();
		    }


		    
		    
		  public static LocalDate convertirFechaNumerica(int fechaNumerica) {
		        // La fecha base de Excel es el 1 de enero de 1900 con un error de 1 día
		        LocalDate fechaBaseExcel = LocalDate.of(1899, 12, 30);

		        // Sumar el número de días a la fecha base de Excel
		        return fechaBaseExcel.plusDays(fechaNumerica);
		    }

	    private static CellValue evaluateCell(Cell cell, CreationHelper creationHelper) {
	        FormulaEvaluator evaluator = creationHelper.createFormulaEvaluator();
	        return evaluator.evaluate(cell);
	    }
	    
	    
		public static String convertirNumeroAFecha(String numeroDeDias) {
	        int dias = Integer.parseInt(numeroDeDias);
	        
	        // Ajustar la fecha base a "30/12/1899" para que "45047" dé como resultado "01/05"
	        LocalDate fechaBase = LocalDate.of(1899, 12, 30);
	        LocalDate fechaCalculada = fechaBase.plusDays(dias);
	
	        // Utilizar el formato "dd/MM" solo si el día es mayor que 0
	        String resultado = fechaCalculada.getDayOfMonth() > 0
	                ? fechaCalculada.format(DateTimeFormatter.ofPattern("dd/MM"))
	                : "";
	
	        return resultado;
	    }
		
		   private static void convertExcelToCsv(String excelFilePath, String csvFilePath) {
		        try (FileInputStream file = new FileInputStream(new File(excelFilePath));
		             Workbook workbook = new XSSFWorkbook(file);
		             FileWriter csvWriter = new FileWriter(csvFilePath)) {
	
		            Sheet sheet = workbook.getSheetAt(0); // Supongamos que estás trabajando con la primera hoja
	
		            for (Row row : sheet) {
		                StringBuilder csvLine = new StringBuilder();
		                for (Cell cell : row) {
		                    if (csvLine.length() > 0) {
		                        csvLine.append(",");
		                    }
	
		                    // Obtén el contenido de la celda como texto
		                    String cellValue = getCellValueAsString(cell);
		                    csvLine.append(cellValue);
		                }
		                csvWriter.append(csvLine.toString());
		                csvWriter.append("\n");
		            }
	
		            System.out.println("Conversión exitosa. Archivo CSV generado en: " + csvFilePath);
	
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
	
		    private static String getCellValueAsString(Cell cell) {
		        if (cell == null) {
		            return "";
		        }
	
		        switch (cell.getCellType()) {
		            case STRING:
		                return cell.getStringCellValue();
		            case NUMERIC:
		                if (DateUtil.isCellDateFormatted(cell)) {
		                    // Si es una fecha, puedes ajustar el formato según tus necesidades
		                    return cell.getCellStyle().getDataFormatString();
		                } else {
		                    return String.valueOf(cell.getNumericCellValue());
		                }
		            case BOOLEAN:
		                return String.valueOf(cell.getBooleanCellValue());
		            case FORMULA:
		                return cell.getCellFormula();
		            default:
		                return "";
		        }
		    }
		    
		    
		    
	
	    public static boolean contieneNumeros(String texto) {
	        String patron = "[0-9]";
	        Pattern pattern = Pattern.compile(patron);
	        Matcher matcher = pattern.matcher(texto);
	        return matcher.find();
	    }
	    
			  public static String convertSerialNumberToDate(long serialNumber) {
			    Calendar calendar = Calendar.getInstance();
			    calendar.set(1900, 0, 1); // Establece la fecha base de Excel (1 de enero de 1900)
			    calendar.add(Calendar.DAY_OF_YEAR, (int) serialNumber - 2); // Suma los días del número de serie (ajustado por el error del año bisiesto)
			    Date date = calendar.getTime(); // Obtiene la fecha como un objeto Date
			    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // Establece el formato de fecha deseado
			    return sdf.format(date); // Devuelve la fecha como una cadena en el formato deseado
			  }
		
		
		
		
	
		
		private static Pattern pattern = Pattern.compile("[^ -~]");
		private static String cleanTheText(String text) {
		    Matcher matcher = pattern.matcher(text);
		    if ( matcher.find() ) {
		        text = text.replace(matcher.group(0), "");
		    }
		    return text;
		}
		
		
		   /**
	     * Método que devuelve un código nacional de 7 dígitos a partir de uno de 6
	     * @param cn6
	     * @return cn7
	     */
		private static int getCN7(int cn6) {
	    	
	     	int cn7 =-1;
	      	String number = String.valueOf(cn6);
	     	char[] digits1 = number.toCharArray();
	     //	System.out.println("digits1 " + digits1[0]);
	     //	System.out.println("digits1 " + digits1[1]);
	    	try {
	         	int dig1		=	Character.getNumericValue(digits1[0]);	//C
	    		int dig2		=	Character.getNumericValue(digits1[1]);	//D
	    		int dig3		=	Character.getNumericValue(digits1[2]);	//E
	    		int dig4		=	Character.getNumericValue(digits1[3]);	//F
	    		int dig5		=	Character.getNumericValue(digits1[4]);	//G
	    		int dig6		=	Character.getNumericValue(digits1[5]);	//H
	    		int algoritmo	=	3*dig2 + 3*dig4 + 3*dig6 + dig1 + dig3 + dig5 + 27;	 //J		=3*D2+3*F2+3*H2+C2+E2+G2+27
	       		System.out.println("dig1 " + dig1);
	       	 	System.out.println("dig2 " + dig2);
	    		System.out.println("dig3 " + dig3);
	    		System.out.println("dig4 " + dig4);
	    		System.out.println("dig5 " + dig5);
	    		System.out.println("dig6 " + dig6);
	    		
	    	
	    		String d = Integer.toString(cn6);
	    		System.out.println("d " + d);
	    		System.out.println(cn6 + " tiene " + d.length() + " dígitos");
	    		int length = (int) (Math.log10(cn6) + 1);
	    		System.out.println(cn6 + " tiene " + length + "length dígitos");
	    		
	    		char a = d.charAt(0);
	    		System.out.println(" 1er digito " +  a);
	    		System.out.println(" 1er digito " +  a+a);
	    		System.out.println(" int 1er digito " +  (int)a);
	    	
	            int entero = algoritmo/10; 
	            int residuo = algoritmo - entero*10;
	            int base =entero;
	            if(residuo>0) base+=1;
	            int decenaSiguiente=base*10;
	            int resultado	=	decenaSiguiente-algoritmo;	//I		=N2-J2
	            
	            cn7=cn6*10+resultado;
	    		System.out.println("algoritmo " + algoritmo);
	            System.out.println("entero " + entero);
	            System.out.println("residuo " + residuo);
	            System.out.println("base " + base);
	            System.out.println("decenaSiguiente " + decenaSiguiente);
	            System.out.println("resultado " + resultado);
	            System.out.println("cn7 " + cn7);
	     
	        
	        /*    
	            System.out.printf("El char %c es %d en entero. Podemos hacer sumas. %d + 1 = %d", a, entero, entero, entero + 1); 
	     		System.out.println(" ");
	            System.out.println("El char %c es %d en entero. Podemos hacer sumas. %d + 1 = %d"); 
	     		System.out.println(" ");
	    	*/	
	    //		int entero		=		//K		=ENTERO(J2/10)
	    //		int residuo				//L		=RESIDUO(J2;10)
	    //		int base				//M		=SI(L2>0;K2+1;K2)
	    //		int decenaSiguiente	 	//N	=	M2*10
	
	//    		int resultado			//I		=N2-J2
	    		
	/*		
	A	CODIGO6	707044
	B	CODIGO7	=SI(LARGO(A2)=7;A2;A2&I2)
	C	d1	=EXTRAE($A2;1;1)
	D	d2	=EXTRAE($A2;2;1)
	E	d3	=EXTRAE($A2;3;1)
	F	d4	=EXTRAE($A2;4;1)
	G	d5	=EXTRAE($A2;5;1)
	H	d6	=EXTRAE($A2;6;1)
	I	Resultado			=N2-J2
	J	Algoritmo			=3*D2+3*F2+3*H2+C2+E2+G2+27
	K	Entero				=ENTERO(J2/10)
	L	Residuo				=RESIDUO(J2;10)
	M	base				=SI(L2>0;K2+1;K2)
	N	Decena siguiente	=M2*10
	
	
	
	*/
	
	    	} catch (NumberFormatException e) {
	    		cn7 = -1;
	    	}
	    	
	    	return cn7;
	}    
		   
		   
		
		  public static String convertFormatDateString(String fechaEntrada, String formatoEntrada, String formatoSalida) {
		       
			  String fechaFormateada = "";
			  SimpleDateFormat sdfEntrada = new SimpleDateFormat(formatoEntrada);
			  SimpleDateFormat sdfSalida = new SimpleDateFormat(formatoSalida);
			  if(fechaEntrada==null ||  fechaEntrada.equals("")) return "";
				  
			  try {
				  // Convertir el String a un objeto Date
				  Date fecha = sdfEntrada.parse(fechaEntrada);
	
				  // Formatear la fecha según el formato deseado
				  fechaFormateada = sdfSalida.format(fecha);
	
		            System.out.println("Fecha formateada: " + fechaFormateada);
		        } catch (ParseException e) {
		            e.printStackTrace();
		        }
			  
			  return fechaFormateada;
		    }  
		  
		  public static void  ReemplazarSubstring (String cadenaOriginal)  {
			
				        // Definir el patrón de búsqueda
			        String patron = "\\d+#";
	
			        // Crear un objeto Pattern y un objeto Matcher
			        Pattern pattern = Pattern.compile(patron);
			        Matcher matcher = pattern.matcher(cadenaOriginal);
	
			        // Reemplazar todas las coincidencias con la cadena original
			        String cadenaModificada = matcher.replaceAll("");
	
			        // Imprimir el resultado
			        System.out.println("Cadena original: " + cadenaOriginal);
			        System.out.println("Cadena modificada: " + cadenaModificada);
			   
			}
	
	    
	}


    
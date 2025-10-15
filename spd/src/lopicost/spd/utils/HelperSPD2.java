
package lopicost.spd.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import lopicost.spd.model.BdConsejo;
import lopicost.spd.persistence.BdConsejoDAO;
import lopicost.spd.persistence.FicheroMedResiConPrevisionDAO;
import lopicost.spd.persistence.FicheroMedResiDAO;
import lopicost.spd.persistence.FicheroResiCabeceraDAO;
import lopicost.spd.persistence.FicheroResiDetalleDAO;
import lopicost.spd.persistence.PacienteDAO;
import lopicost.spd.struts.bean.CamposPantallaBean;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.struts.bean.PacienteBean;
import lopicost.spd.struts.form.FicheroResiForm;


/** String Util: recopilación de utilidades para tratamiento de Strings
 */
public class HelperSPD2{
	
	/**
	 * Devuelve el String defaultValue en caso que str  sea nulo o vacío o cero
	 *
	 */
	public final static String isNull(String str, String defaultValue) {
		String result =(str == null || str.isEmpty() || str.equals("")|| str.equals("0")) ? defaultValue : str ; 
	        return result;
	}
	    
	
	
	/**
	 * Método para transformar algunos carácteres de un texto concreto
	 * @param string 
	 * @param medResi 
	 * @param text
	 * @return
	 */
	public final static String getPautaStandard(FicheroResiBean medResi, String text) {
		String result=text;
		boolean confirmarPauta=false;
		if(result!=null&&!result.equalsIgnoreCase(""))
		{ 
			result=result.trim();
			result=quitaEspacios(result);
			result=result.replace("S/P", "999");
			result=result.replace("sp", "999");
			result=result.replace("½", "0,5");
			result=result.replace("1/2", "0,5");
			result=result.replace("1/2", "0,5");
			result = result.replaceAll("(\\d+)\\.$", "$1");  //elimina un punto al final

			result=result.replace("1+1/4", "1,25");
			result=result.replace("1/4", "0,25");
			result=result.replace("01-abr", "0,25");
			//result=result.replace("01/04/2021", "0,25");
			result=result.replace("1/3", "0,33");
			result=result.replace("2/3", "0,66");
			result=result.replace("01-feb", "0,5");
			//result=result.replace("01/02/2021", "0,5");
			result=result.replace("03-abr", "0,75");
			result=result.replace("3/4", "0,75");
			
			Pattern regexPattern = Pattern.compile("03/04\\d{4}");
			Matcher matcher = regexPattern.matcher(result);
			if (matcher.find()) {
				result = matcher.replaceAll("0,75");
				confirmarPauta=true;
			}

			regexPattern = Pattern.compile("01/02\\d{4}");
			matcher = regexPattern.matcher(result);
			if (matcher.find()) {
				result = matcher.replaceAll("0,5");
				confirmarPauta=true;
			}

			regexPattern = Pattern.compile("02/03\\d{4}");
			matcher = regexPattern.matcher(result);
			if (matcher.find()) {
				result = matcher.replaceAll("0,66");
				confirmarPauta=true;
			}

			regexPattern = Pattern.compile("01/03\\d{4}");
			matcher = regexPattern.matcher(result);
			if (matcher.find()) {
				result = matcher.replaceAll("0,33");
				confirmarPauta=true;
			}

			regexPattern = Pattern.compile("01/04\\d{4}");
			matcher = regexPattern.matcher(result);
			if (matcher.find()) {
				result = matcher.replaceAll("0,25");
				confirmarPauta=true;
			}
			
			regexPattern = Pattern.compile("01/05\\d{4}");
			matcher = regexPattern.matcher(result);
			if (matcher.find()) {
				result = matcher.replaceAll("1,5");
				confirmarPauta=true;
			}
			
			regexPattern = Pattern.compile("02/05\\d{4}");
			matcher = regexPattern.matcher(result);
			if (matcher.find()) {
				result = matcher.replaceAll("2,5");
				confirmarPauta=true;
			}
			
			regexPattern = Pattern.compile("03/05\\d{4}");
			matcher = regexPattern.matcher(result);
			if (matcher.find()) {
				result = matcher.replaceAll("3,5");
				confirmarPauta=true;
			}
			
		//	result=result.replace("03/04/2021", "0,75");
		//	result=result.replace("03/04/2021", "0,75");
			if(result.equalsIgnoreCase("01-may")) {
				result=result.replace("01-may", "1,5"); //confirmarPauta=true;
			}
			
	/*		
		//	result=result.replace("01/05/2021", "1,5");
			if(result.equalsIgnoreCase("44287")) {
				result=result.replace(result, "0,25");//confirmarPauta=true;
			}
			if(result.equalsIgnoreCase("44958")) {
				result=result.replace("44958", "0,5");//confirmarPauta=true;//1/2 01/02/2023
			}	
			if(result.equalsIgnoreCase("44986")) {
				result=result.replace("44986", "0,33");//confirmarPauta=true;//1/3 01/03/2023
			}	
			if(result.equalsIgnoreCase("45017")) {
				result=result.replace("45017", "0,25") ;//confirmarPauta=true;//1/4 01/04/2023
			}	
			if(result.equalsIgnoreCase("45323")) {
				result=result.replace("45323", "0,5") ;//confirmarPauta=true;
			}	
			if(result.equalsIgnoreCase("45019")) {
				result=result.replace("45019", "0,75") ;//confirmarPauta=true;//3/4 03/04/2023
			}	
			if(result.equalsIgnoreCase("45047")) {
				result=result.replace("45047", "1,5") ;//confirmarPauta=true;
			}	
			if(result.equalsIgnoreCase("45413")) {
				result=result.replace("45413", "1,5") ;//confirmarPauta=true;
			}	
			if(result.equalsIgnoreCase("45048")) {
				result=result.replace("45048", "2,5") ;//confirmarPauta=true;
			}	
			if(result.equalsIgnoreCase("45049")) {
				result=result.replace("45049", "3,5") ;//confirmarPauta=true;
			}	
			if(result.equalsIgnoreCase("45050")) { 
				result=result.replace(result, "4,5") ;//confirmarPauta=true;
			}	
*/			
			
			try
			{
				String fecha = convertirNumeroAFecha(result);
				if (fecha != null) {
				    switch (fecha) {
				        case "01/02":
				            result = "0,5";
				            break;
				        case "01/03":
				            result = "0,33";
				            break;
				        case "01/04":
				            result = "0,25";
				            break;
				        case "02/03":
				            result = "0,66";
				            break;
				        case "01/05":
				            result = "1,5";
				            break;
				        case "03/04":
				            result = "0,75";
				            break;
				        case "02/05":
				            result = "2,5";
				            break;
				        case "03/05":
				            result = "3,5";
				            break;
				        case "04/05":
				            result = "4,5";
				            break;
				        // Puedes agregar más casos según sea necesario
				        default:
				            // Acciones si la fecha no coincide con ningún caso
				    }
				}
			}catch(Exception e){
				
			}



			
			
			result=result.replace("x", "999");
			result=result.replace("X", "999");
			
			result=result.replace("-", "");
			result=result.replace("_", "");
			result=result.replace(".", ",");
			result=result.replace(";", ",");
			//result=result.replace(",", ".");
			result=result.replace(";", ".");
			result=result.replace("'", "");
			result=result.replace("´", "");
			result=result.replace("ÿ", "");		//símbolo que llega en conversión del Excel,es un espacio
			result=result.replaceAll("[a-zA-Z]", "_"); //lo modificamos por "_" por si se ha de localizar posteriormente
			
			if(contieneNumeros(result)) 
				result=result.replace("_", "");
				
			//result.matches("[+-]?\\d*(\\.\\d+)?");
			//if(!StringUtils.isNumeric(result.replace(",", "")))
			if(result!=null && !result.equalsIgnoreCase("") && !DataUtil.isNumero(result.replace(",", "")))
			{
				result="999"; //si no es vacío o nulo, no conseguimos saber el número, se ponen asteriscos
				confirmarPauta=true;
			}
			if(confirmarPauta) medResi.setMensajesInfo(medResi.getMensajesInfo() + SPDConstants.INFO_INTERNA_CONFIRMAR_SUSTITUCION_PAUTA);

		}
		return result;
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
	
	
	
	
	
    public static boolean checkNumber(int originalNumber, int targetNumber, int n) {
        if (originalNumber == targetNumber) {
            return true;
        }

        for (int i = 1; i <= n; i++) {
            if (originalNumber == targetNumber + i * 365 || originalNumber == targetNumber + i * 366) {
                return true;
            }
        }

        return false;
    }
    
    
    
    public static boolean contieneNumeros(String texto) {
        String patron = "[0-9]";
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(texto);
        return matcher.find();
    }
    
    
    
	/**
	 * Método para eliminar algunos carácteres de un texto concreto
	 * @param text
	 * @return
	 */
	public final static String limpiarTexto(String text) {
		String result=text;
		if(result!=null&&!result.equalsIgnoreCase(""))
		{ 
			result=result.trim();
			result=result.replace("-", "");
			result=result.replace(".", "");
			result=result.replace("-", "");
			result=result.replace(",", "");
			result=result.replace(";", "");
		}
		return result;
	}
	
	
	/**
	 * Método para eliminar algunos carácteres de un texto concreto
	 * @param text
	 * @return
	 */
	public final static String limpiarTextoTomas(String text) {
		String result=text;
		if(result!=null&&!result.equalsIgnoreCase(""))
		{
			result.trim();
			quitaEspacios(result);
			result.replace("-", "");
			//result.replace(".", ",");
			result.replace(",", ".");
			result.replace("_", "");
			result.replace(";", "");
			result.replace("'", "");
			result.replace("´", "");
		}
		return result;
	}

	/**
	 * Método para eliminar todos los espacios de un texto
	 * @param text
	 * @return
	 */
	public  final static String quitaEspacios(String text) {
		String result=text;
		if(result!=null&&!result.equalsIgnoreCase(""))
		{
			result.trim();
			result=result.replace(" ", "");
			result=StringUtils.strip(result, " \t\u00A0\u1680\u180e\u2000\u200a\u202f\u205f\u3000");  
		}
		
		return result;
	}
	
	
	/**
	 * Método que transforma en mayúsculas, eliminando acentos
	 * @param q String base
	 * @return String Retorna un String cuyas letras estarán todas en mayúsculas y sin acentos
	 */
	public final static String makeFlat(String q){
		q = HelperSPD2.replaceChar(q,'Á','A');
		q = HelperSPD2.replaceChar(q,'É','E');
		q = HelperSPD2.replaceChar(q,'Í','I');
		q = HelperSPD2.replaceChar(q,'Ó','O');
		q = HelperSPD2.replaceChar(q,'Ú','U');
		q = HelperSPD2.replaceChar(q,'À','A');
		q = HelperSPD2.replaceChar(q,'È','E');
		q = HelperSPD2.replaceChar(q,'Ì','I');
		q = HelperSPD2.replaceChar(q,'Ò','O');
		q = HelperSPD2.replaceChar(q,'Ù','U');
		q = HelperSPD2.replaceChar(q,'Ä','A');
		q = HelperSPD2.replaceChar(q,'Ë','E');
		q = HelperSPD2.replaceChar(q,'Ï','I');
		q = HelperSPD2.replaceChar(q,'Ö','O');
		q = HelperSPD2.replaceChar(q,'Ü','U');
		q = HelperSPD2.replaceChar(q,'Ñ','N');
		q = HelperSPD2.replaceChar(q,'Ç','C');
		
		q = HelperSPD2.replaceChar(q,'á','A');
		q = HelperSPD2.replaceChar(q,'é','E');
		q = HelperSPD2.replaceChar(q,'í','I');
		q = HelperSPD2.replaceChar(q,'ó','O');
		q = HelperSPD2.replaceChar(q,'ú','U');
		q = HelperSPD2.replaceChar(q,'à','A');
		q = HelperSPD2.replaceChar(q,'è','E');
		q = HelperSPD2.replaceChar(q,'ì','I');
		q = HelperSPD2.replaceChar(q,'ò','O');
		q = HelperSPD2.replaceChar(q,'ù','U');
		q = HelperSPD2.replaceChar(q,'ä','A');
		q = HelperSPD2.replaceChar(q,'ë','E');
		q = HelperSPD2.replaceChar(q,'ï','I');
		q = HelperSPD2.replaceChar(q,'ö','O');
		q = HelperSPD2.replaceChar(q,'ü','U');
		q = HelperSPD2.replaceChar(q,'ñ','N');
		q = HelperSPD2.replaceChar(q,'ç','C');
		
		q = HelperSPD2.replaceChar(q,'\"',' ');
		
		return q.toUpperCase();
	}

	
	 //Método que reemplaza un carácter dentro de un String
	final static public String replaceChar(String q,char in,char out){
		int idx;
		idx=q.indexOf(in);
		if(idx==-1)
			return q;
		return q.replace(in,out);
	}
	
	//Retorna el número de veces que un carácter aparece en un String
	public final  static int charCount( String str, char c ){
		int n = 0;
		for ( int i = 0; i < str.length(); ++i )
			if ( str.charAt( i ) == c )
				++n;
		return n;
	}
	
	/** 
	 * Método que se encarga de reemplazar un subString por otro <BR>
	 * <b>NOTA</b>: OJO CON EL ORDEN DE LOS PARAMS DE ESTE METODO! el string base donde queremos hacer los replaces es el ULTIMO PARAMETRO
	 * <b>NOTA</b>: ESTE METODO NO ES SEGURO (peta por ej con el string "") --> usar replaceString
	 * @param substr String cuyo contenido será reemplazado
	 * @param newsubstr String cuyo valor es el "reemplazante"
	 * @param fullstr String base para realizar los cambios
	 * @return String Retorna el String despues del reemplazo
	 */
	public final static String replace(String substr, String newsubstr, String fullstr) 
	{
		int iSub = fullstr.indexOf(substr);
		if(iSub==-1) 
			return fullstr;
		int eSub = iSub+substr.length()-1;
		String pre = fullstr.substring(0,Math.max(iSub,0));
		String post = fullstr.substring(Math.min(eSub+1,fullstr.length()),fullstr.length());
		String ret = pre+newsubstr+post; 
		if(ret.indexOf(substr)!=-1)
			return replace(substr, newsubstr, ret);
		return ret;
	}
		
	/**
	 * Método que reemplaza un carácter dentro de un String por un String
	 * @param q String base
	 * @param in Carácter a reemplazar
	 * @param out Cadena de sustitución
	 * @return String Nuevo String con el carácter reemplazado
	 */
	public final static  String replaceChar (String q, char in, String out){
		
		StringTokenizer st = new StringTokenizer(q,  new Character (in).toString ());
		int idx;
		String strFinal = new String ();
		
		idx=q.indexOf(in);
		
		if(idx==-1)
			return q;
		
		if (q.charAt (0) == in)
			strFinal = out;
		
		strFinal += st.nextToken ();
		
		while (st.hasMoreTokens()){ 
			strFinal += out;
			strFinal += st.nextToken();
		}  
		
		if (q.charAt (q.length () - 1) == in)
			strFinal += out; 
		
		return strFinal;
	}
	
	
	/**
	 * ibassola: Reemplaza los caracteres no válidos de una cadena alfanumércia.
	 * @param inString cadena de entrada
	 * @return String Retorna la cadena alfanumérica que contiene únicamente caracteres validos.
	 */    
	public static String replaceInvalidChars(String inString)
	{
		//String allowedChars = "abcdefghijklmnopqrstuvwxyz0123456789_-.";

		if (inString== null) return inString;
		
		inString = inString.replace('Á','A');
		inString = inString.replace('É','E');
		inString = inString.replace('Í','I');
		inString = inString.replace('Ó','O');
		inString = inString.replace('Ú','U');
		inString = inString.replace('À','A');
		inString = inString.replace('È','E');
		inString = inString.replace('Ì','I');
		inString = inString.replace('Ò','O');
		inString = inString.replace('Ù','U');
		inString = inString.replace('Ä','A');
		inString = inString.replace('Ë','E');
		inString = inString.replace('Ï','I');
		inString = inString.replace('Ö','O');
		inString = inString.replace('Ü','U');
		inString = inString.replace('Ñ','N');
		inString = inString.replace('Ç','C');
		inString = inString.replace('Â','A');
		inString = inString.replace('Ê','E');
		inString = inString.replace('Î','I');
		inString = inString.replace('Ô','O');
		inString = inString.replace('Û','U');
		inString = inString.replace('Å','A');
		inString = inString.replace('Æ','A');
		
		inString = inString.replace('á','a');
		inString = inString.replace('é','e');
		inString = inString.replace('í','i');
		inString = inString.replace('ó','o');
		inString = inString.replace('ú','u');
		inString = inString.replace('à','a');
		inString = inString.replace('è','e');
		inString = inString.replace('ì','i');
		inString = inString.replace('ò','o');
		inString = inString.replace('ù','u');
		inString = inString.replace('ä','a');
		inString = inString.replace('ë','e');
		inString = inString.replace('ï','i');
		inString = inString.replace('ö','o');
		inString = inString.replace('ü','u');
		inString = inString.replace('ñ','n');
		inString = inString.replace('ç','c');
		inString = inString.replace('â','a');
		inString = inString.replace('ê','e');
		inString = inString.replace('î','i');
		inString = inString.replace('ô','o');
		inString = inString.replace('û','u');
		inString = inString.replace('å','a');
		inString = inString.replace('æ','a');
		
		inString = inString.replaceAll("'\"","");
		inString = inString.replaceAll("\'","");		

				
		return inString;
	}
	
	 /**
     * Transforma un String en un Vector de Strings mediante la separación de un token
     * @param str String base
     * @param token String delimitador
     * @return Vector de Strings
     */
	 public static String[] splitStr( String str ,String token){
		StringTokenizer st = new StringTokenizer( str ,token);
		int n = st.countTokens();
		String[] array = new String[n];
		for (int i = 0; i < array.length; i++) {
			array[i] = st.nextToken();
		}
		return array;
	}
	
	 /**
	  * Decompose a String into a vector according to token ","
	  * @param text to decompose
	  * @return Vector composed with elements of the string separated by
	  * 
	  * copied from general.jar
	  */
	 public static Vector stringToVector(String text)
	 {
	 	return stringToVector(text,",");
	 } 
	 
	 /**
	  * Decompose a String into a vector according to a token
	  * @param text to decompose
	  * @param token	token which determine how to decompose the string
	  * @return Vector composed with elements of the string separated by
	  * the token
	  * 
	  * copied from general.jar
	  */
	 public static Vector stringToVector(String text,String token)
	 {
	 	StringTokenizer cad = new StringTokenizer (text,token, true);
	 	
	 	Vector vec=new Vector();
	 	String aux;
	 	while (cad.hasMoreTokens())
	 	{
	 		aux=cad.nextToken();
	 		if (!aux.equalsIgnoreCase(token)) vec.addElement(aux);
	 	}
	 	return vec;
	 }
	 
	 /**
	  * Convierto un decimal a binario.
	  * Le mete todos los zeros que sean necesarios hasta llegar
	  * al tamaño definido por leadingSize. 
	  */
	 public static String decimalToBinary(String decimal, int leadingSize)
	 {
		 String binary = Integer.toBinaryString( Integer.parseInt(decimal) );
		
		 for (int i=binary.length(); i<leadingSize; i++ )
			 binary = "0" + binary;
		 
		 return binary;
	 }

	 /**
	  * Convierte un decimal (ej, 5) a un array de booleanos (ej, {true, false, true})
	  */
	 public static boolean[] decimalToBinaryArray(String decimal, int leadingSize)
	 {
		 String binary = decimalToBinary( decimal, leadingSize );
		 boolean[] binaryArray = new boolean[ binary.length() ];
		 
		 for (int i=0; i<binaryArray.length; i++)
			 binaryArray[i] = binary.charAt(i) == '1' ? true : false;
		 
		 return binaryArray;
	 }
	 
	 /**
	  * Convierto un binario (ej, 1111110) a decimal (ej, 127)
	  */
	 public static String binaryToDecimal(String binary)
	 {
		 return String.valueOf( Integer.parseInt(binary, 2) );
	 }
	 

	 /**
	  * Convierte un array de bits que representa un binario (ej, 1111110)
	  * a decimal (ej, 127)
	  */
	 public static String binaryToDecimal(boolean[] bits)
	 {
		 String binary = "";
		 for (int i=0; i<bits.length; i++)
			 binary += bits[i] ? "1" : "0";
		 
		 return binaryToDecimal( binary );
	 }
	 
	 
	 /**
	  * Devuelve un long que representa milisegundos a partir de una hora en formato kk:mm
	  * Si ocurre algun error (por ej, formato incorrecto) devuelve 0
	  * 
	  * por ej, el texto "12:30" lo convierte a 41400000 (su equivalente en ms)  
	  */
	 public static long textToHour(String str)
	 {
		 try {
			SimpleDateFormat sdf = new SimpleDateFormat("kk:mm");
			sdf.setLenient(false);
			Date date = sdf.parse( str );
			return date.getTime();
		} catch (Exception e) {
			return 0;
		}
	 }
	 
	 /**
	  * devuelve un string que representa la hora en formato kk:mm de los ms de hour
	  * Si ocurre algun error (por ej, formato incorrecto) devuelve --:--
	  * 
	  * por ej, el long 41400000 lo convierte a 12:30 (su equivalente)
	  *  
	  */
	 public static String hourToText(long hour)
	 {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("kk:mm");
			sdf.setLenient(false);
			return sdf.format( new Date(hour) );
		} catch (Exception e) {
			return "--:--";
		}
	 }
	 
	 /** Sustituye saltos de linea y tabuladores por tags html. 
	 * @param inString
	 * @return devuelve la cadena con tags html.
	 */
	public static String toHtml(String inString) {
		if (inString != null) {
			inString = replaceChar(inString, '\r', "<br/>");
			inString = replaceChar(inString, '\n', "<br/>");
			inString = replaceChar(inString, '\t', "&nbsp;&nbsp;&nbsp;");
			return inString.trim();
		}
		
		return "";
	}

	/**
	 * Dejamos la fecha en formato STRING DD/MM/YYYY
	 * @param fechaString
	 * @return
	 */
	public static String getStringFechaArreglada(String fechaString) {
		try{

			if (fechaString==null || "".equalsIgnoreCase(fechaString)) return "";	  		  
		  	
			
			fechaString=quitaEspacios(fechaString);
			fechaString=fechaString.replace("_", "/");
			fechaString=fechaString.replace("-", "/");
			fechaString=fechaString.replace(".", "/");
			fechaString=fechaString.replace(";", "");
			fechaString=fechaString.replace("'", "");
			fechaString=fechaString.replace("´", "");
			fechaString=fechaString.replace("'", "");
			
			int day=Integer.parseInt(fechaString.substring(0,2));
		    int mes=Integer.parseInt(fechaString.substring(3,5));
			int anyo=Integer.parseInt(fechaString.substring(6));
			  if (anyo<2000) //Tratamos fechas cortas
				  	anyo+=2000;
			
			java.util.Calendar c=java.util.Calendar.getInstance();	
			c.set(java.util.Calendar.DAY_OF_MONTH,day);
			c.set(java.util.Calendar.MONTH,mes-1);
			c.set(java.util.Calendar.YEAR,anyo);
			  
			return DateUtilities.getDatetoString( "dd/MM/yyyy", c.getTime());
		}
		catch(Exception e)
		{
			return "";	
		}
	}

	/**
	 * Devuelve el Date correspondiente a una fecha formateada
	 * @param fecha fecha formateada del tipo DD/MM/AAAA o DD/MM/AA
	 * @return String en formato  DD/MM/AAAA
	
	public static String getFechaArreglada(String fecha)
	{		
		String result="";
	  if (fecha==null || "".equalsIgnoreCase(fecha)) return null;	  		  
	  try{
		  int day=Integer.parseInt(fecha.substring(0,2)); 
		  int mes=Integer.parseInt(fecha.substring(3,5));
		  int anyo=Integer.parseInt(fecha.substring(6));
		  if (anyo<2000) //Tratamos fechas cortas
			  	anyo+=2000;
		  
		  
		  if (0<day && day<=31 && 0<mes && day<=12 && isNumeric(fecha.substring(6))) 
			  result= fecha.substring(0,2)+"/"+fecha.substring(3,5)+"/"+anyo+"";
			  
	  }
			catch(Exception e)
			{
				result= "";	
			}
	return result;
		  
		}		
 */
/*
		public static boolean isNumeric(String cadena) {
		
		    boolean resultado;
		
		    try {
		        Integer.parseInt(cadena);
		        resultado = true;
		    } catch (NumberFormatException excepcion) {
		        resultado = false;
		    }
		
		    return resultado;
		}
*/
	
	/**
	 * Elimina los carácteres "duros o invisibles" 
	 * https://stackoverflow.com/questions/6198986/how-can-i-replace-non-printable-unicode-characters-in-java
	 * Based on the answers by Op De Cirkel and noackjr, the following is what I do for general string cleaning: 1. trimming leading or trailing whitespaces, 2. dos2unix, 3. mac2unix, 4. removing all "invisible Unicode characters" except whitespaces:
	 */
	
	private static Pattern pattern = Pattern.compile("[^ -~]");
	public static String cleanTheText(String text) {
	    Matcher matcher = pattern.matcher(text);
	    if ( matcher.find() ) {
	        text = text.replace(matcher.group(0), "");
	    }
	    return text;
	}
	
	
	public static void getDatosPaciente(FicheroResiBean medResi) throws Exception {
	
		PacienteBean paciente = PacienteDAO.getPacientePorCIP(medResi.getResiCIP());
		
    	if(paciente==null)
    	{
    		medResi.setMensajesInfo(SPDConstants.INFO_INTERNA_CIP_SIN_ALTA);
    	   		medResi.setResiNombrePaciente(medResi.getNombrePacienteEnFichero());
    	   		medResi.setResiApellido1("");
    	   		medResi.setResiApellido2("");
	   	   		medResi.setResiApellidos("");
	   	   		medResi.setResiApellidosNombre(medResi.getNombrePacienteEnFichero());
    	}
    	else
    	{

    		if(paciente.getIdDivisionResidencia()!=null && !paciente.getIdDivisionResidencia().equals(medResi.getIdDivisionResidencia()))
    			medResi.setMensajesInfo(SPDConstants.INFO_INTERNA_CIP_OTRA_RESI);

    	   	if(paciente.getSpd()!=null && paciente.getSpd().equalsIgnoreCase("N") )
        		medResi.setMensajesInfo(SPDConstants.INFO_INTERNA_CIP_SPD_NO);

    	   	if(paciente.getNombre()!=null && !paciente.getNombre().equals("")) //comprobamos si llega con nombre!null
    	   	{
    	   		medResi.setResiNombrePaciente(paciente.getNombre());
    	   		medResi.setResiApellido1(paciente.getApellido1());
    	   		medResi.setResiApellido2(paciente.getApellido2());
    	   	   	if(medResi.getResiApellido1() + medResi.getResiApellido2()!=null && !(medResi.getResiApellido1() + medResi.getResiApellido2()).trim().equals(""))
    	   	   	{
    	   	   		medResi.setResiApellidos(medResi.getResiApellido1() + " " + medResi.getResiApellido2());
    	   	   		medResi.setResiApellidosNombre(medResi.getResiApellido1() + " " + medResi.getResiApellido2() + ", " + paciente.getNombre());
    	   	   	}
    	   	   	else if(paciente.getApellidosNombre()!=null && !paciente.getApellidosNombre().trim().equals("") )
    	   	   	{
    	   	   		medResi.setResiApellidosNombre(paciente.getApellidosNombre());
	    	   	   	if(paciente.getApellidos()!=null && !paciente.getApellidos().trim().equals("") )
	    	   	   	{
	    	   	   		medResi.setResiApellidos(paciente.getApellidos());
	    	   	   	}
    	   	   	}
    	   	}
    	   	else //xsi no hay datos pondremos lo que llega en el Excel de la resi
    	   	{
    	   		medResi.setResiNombrePaciente(medResi.getNombrePacienteEnFichero());
    	   		medResi.setResiApellido1("");
    	   		medResi.setResiApellido2("");
	   	   		medResi.setResiApellidos("");
	   	   		medResi.setResiApellidosNombre(medResi.getNombrePacienteEnFichero());
       	}
    	   		
    	}
    		
    		
   		if(paciente!=null && paciente.getIdDivisionResidencia()!=null && !paciente.getIdDivisionResidencia().equals(medResi.getIdDivisionResidencia()))
    			medResi.setMensajesInfo(SPDConstants.INFO_INTERNA_CIP_OTRA_RESI);
   	   	if(paciente!=null && paciente.getSpd()!=null && paciente.getSpd().equalsIgnoreCase("N") )
    	   		medResi.setMensajesInfo(SPDConstants.INFO_INTERNA_CIP_SPD_NO);



	}
	
	
	public static void borrarMensajeAvisoAnterior(FicheroResiBean medResi, String mensaje, String tipo) {
  		switch (tipo) {
		case "INFO":
			if(medResi.getMensajesInfo().contains(mensaje))
	  			medResi.setMensajesInfo( medResi.getMensajesInfo().replace(mensaje, ""));

			break;
		case "ALERTA":
			if(medResi.getMensajesAlerta().contains(mensaje))
	  			medResi.setMensajesAlerta( medResi.getMensajesAlerta().replace(mensaje, ""));

			break;
		case "RESIDENCIA":
			if(medResi.getMensajesResidencia().contains(mensaje))
	  			medResi.setMensajesResidencia( medResi.getMensajesResidencia().replace(mensaje, ""));

			break;

		default:
			break;
		}
		
  	}



	/**
	 * Devuelve el total de días marcados en el tratamiento, Una utilidad para calcular la previsión de comprimidos
	 * @param fila
	 * @return
	 */
	public static int getDiasMarcados(FicheroResiBean fila) {
		
		int dias=0;
		if(fila!=null)
		{
			if(diaMarcado(fila.getResiD1())) dias++;
			if(diaMarcado(fila.getResiD2())) dias++;
			if(diaMarcado(fila.getResiD3())) dias++;
			if(diaMarcado(fila.getResiD4())) dias++;
			if(diaMarcado(fila.getResiD5())) dias++;
			if(diaMarcado(fila.getResiD6())) dias++;
			if(diaMarcado(fila.getResiD7())) dias++;
		}
		fila.setDiasConToma(dias);
		

		
		return dias;
	}
	/**
	 * Método auxiliar
	 * @param dia
	 * @return
	 */
	public static boolean diaMarcado(String dia) {
		return  dia!=null && dia.equalsIgnoreCase("X");
	}

	/**
	 * Devuelve el total de comprimidos o pauta que toma al día, Una utilidad para calcular la previsión de comprimidos
	 * @param fila
	 * @return
	 */
	public static float getPautaDia(FicheroResiBean fila) {
		float pauta=0;
		if(fila!=null)
		{

	    	//pauta
    	try{
			pauta+=getPautaToma(fila.getResiToma1());
			pauta+=getPautaToma(fila.getResiToma2());
			pauta+=getPautaToma(fila.getResiToma3());
			pauta+=getPautaToma(fila.getResiToma4());
			pauta+=getPautaToma(fila.getResiToma5());
			pauta+=getPautaToma(fila.getResiToma6());
			pauta+=getPautaToma(fila.getResiToma7());
			pauta+=getPautaToma(fila.getResiToma8());
			pauta+=getPautaToma(fila.getResiToma9());
			pauta+=getPautaToma(fila.getResiToma10());
			pauta+=getPautaToma(fila.getResiToma11());
			pauta+=getPautaToma(fila.getResiToma12());
			pauta+=getPautaToma(fila.getResiToma13());
			pauta+=getPautaToma(fila.getResiToma14());
			pauta+=getPautaToma(fila.getResiToma15());
			pauta+=getPautaToma(fila.getResiToma16());
			pauta+=getPautaToma(fila.getResiToma17());
			pauta+=getPautaToma(fila.getResiToma18());
			pauta+=getPautaToma(fila.getResiToma19());
			pauta+=getPautaToma(fila.getResiToma20());
			pauta+=getPautaToma(fila.getResiToma21());
			pauta+=getPautaToma(fila.getResiToma22());	    			
			pauta+=getPautaToma(fila.getResiToma23());
			pauta+=getPautaToma(fila.getResiToma24());	    
    	}
    	catch(Exception e)
    	{
       	}
    	fila.setComprimidosDia(pauta);
    	}
		return pauta;
	}

	/**
	 * Método auxiliar
	 * @param resiToma
	 * @return
	 */
	public static double getPautaToma(String resiToma) {
		double pautaDia=0;
		double b =0;
				
		try
		{
			//boolean a = StringUtils.isNumeric(resiToma);
			boolean a = DataUtil.isNumero(resiToma);
			pautaDia = Double.parseDouble(resiToma.replace(',', '.'));
			
			//int b = Integer.parseInt(resiToma);
			if(a && pautaDia<99)
			//	pautaDia=Integer.parseInt(resiToma);
				return pautaDia;
		}
		catch(Exception e){}
		return pautaDia;
	}

	 public static void getPeriodicidad(final FicheroResiBean fila) {
	        long dias = 30;
	        Date fechaInicio = null;
	        Date fechaFin = null;
	        try {
	            fechaInicio = DateUtilities.getDate(fila.getResiInicioTratamiento(), "dd/MM/yyyy");
	        }
	        catch (Exception ex) {}
	        try {
	            fechaFin = DateUtilities.getDate(fila.getResiFinTratamiento(), "dd/MM/yyyy");
	        }
	        catch (Exception ex2) {}
	        final String comentarios = fila.getResiComentarios().toUpperCase();
	        final String observaciones = fila.getResiObservaciones().toUpperCase();
	        fila.setResiPeriodo(SPDConstants.SPD_PERIODO_DIARIO);
	        fila.setResiFrecuencia(1);
	        final int milisecondsByDay = 86400000;
	        try {
	            dias = (fechaFin.getTime() - fechaInicio.getTime()) / milisecondsByDay + 1;
	        }
	        catch (Exception ex3) {}
	        if ((comentarios.equalsIgnoreCase(SPDConstants.SPD_PERIODO_QUINCENAL) || observaciones.equalsIgnoreCase(SPDConstants.SPD_PERIODO_QUINCENAL)) && dias == 1) {
	            fila.setResiPeriodo(SPDConstants.SPD_PERIODO_QUINCENAL);
	            fila.setResiFrecuencia(15);
	            fila.setComprimidosCuatroSemanas(fila.getComprimidosDia()*2);
	            fila.setComprimidosTresSemanas(fila.getComprimidosDia()*3/2);
	            fila.setComprimidosDosSemanas(fila.getComprimidosDia());
	            fila.setComprimidosSemana(fila.getComprimidosDia()/2);
	            fila.setComprimidosDia(fila.getComprimidosDia()/14);
	            
	        }
	        else if(fila.getDiasSemanaMarcados()==1){
	            fila.setResiPeriodo(SPDConstants.SPD_PERIODO_SEMANAL);
	            fila.setResiFrecuencia(7);
	            fila.setComprimidosCuatroSemanas(fila.getComprimidosDia()*4);
	            fila.setComprimidosTresSemanas(fila.getComprimidosDia()*3);
	            fila.setComprimidosDosSemanas(fila.getComprimidosDia()*2);
	            fila.setComprimidosSemana(fila.getComprimidosDia());
	            fila.setComprimidosDia(fila.getComprimidosDia()/7);
	        }
	        else if (dias == 1) { //TO-DO de momento si no es quincenal ni diario lo contaremos como mensual
	            fila.setResiPeriodo(SPDConstants.SPD_PERIODO_MENSUAL);
	            fila.setResiFrecuencia(30);
	            fila.setComprimidosCuatroSemanas(fila.getComprimidosDia());
	            fila.setComprimidosTresSemanas(fila.getComprimidosDia()*3/4);
	            fila.setComprimidosDosSemanas(fila.getComprimidosDia()/2);
	            fila.setComprimidosSemana(fila.getComprimidosDia()/4);
	            fila.setComprimidosDia(fila.getComprimidosDia()/28);
	        }
	    }

	 
	   public  static void changeSintrom(FicheroResiBean medResi) {
			if(medResi.getSpdNombreBolsa()!=null && (medResi.getSpdNombreBolsa().toUpperCase().contains("SINTROM") || medResi.getSpdNombreBolsa().toUpperCase().contains("ALDOCUMAR"))
					|| (medResi.getResiMedicamento()!=null && (medResi.getResiMedicamento().toUpperCase().contains("SINTROM") || medResi.getResiMedicamento().toUpperCase().contains("ALDOCUMAR")) ) )
			{
		           int numTomas = 24; // Número total de tomas
		            Class<?> tratClass = medResi.getClass();

		            try {
		                for (int toma = 1; toma <= numTomas; toma++) {
		                   
		                    Method method = tratClass.getMethod("getResiToma" + toma);
		                    Object value = method.invoke(medResi);
		                    if(DataUtil.isNumero((String) value) && value !=null && !value.equals("0")) 
		                    {
		                   		Method setMethod = tratClass.getMethod("setResiToma" + toma, String.class);
			                    
		                   		setMethod.invoke(medResi, "999"); // Ajusta el nuevo valor a 999
			                    medResi.setIdEstado(SPDConstants.REGISTRO_EDITADO_AUTOMATICAMENTE);
			                    }
		                    
		                
		                }
		            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
		                e.printStackTrace(); // Manejo de excepciones, ajusta según tus necesidades
		            }

			}
				
			
		}
	   
	   /**
	    * Método que mira si se trata de un tratamiento de sintrom
	    * @param medResi
	    * @return
	    * @throws ClassNotFoundException
	    */
	   public  static boolean checkSintrom(FicheroResiBean medResi) {
			if(medResi.getSpdNombreBolsa()!=null && (medResi.getSpdNombreBolsa().toUpperCase().contains("SINTROM") || medResi.getSpdNombreBolsa().toUpperCase().contains("ALDOCUMAR"))
					|| (medResi.getResiMedicamento()!=null && (medResi.getResiMedicamento().toUpperCase().contains("SINTROM") || medResi.getResiMedicamento().toUpperCase().contains("ALDOCUMAR")) ) )
				return true;
			else
				return false;
	   
		}   	
				
			

	   /**
	    * Método que mira si se trata de un tratamiento de trazodona o deprax
	    * @param medResi
	    * @return
	    * @throws ClassNotFoundException
	    */
		public static boolean checkTrazodona(FicheroResiBean medResi) throws ClassNotFoundException {
			if(medResi.getSpdNombreBolsa()!=null &&  !medResi.getSpdNombreBolsa().toUpperCase().contains("/2") && (medResi.getSpdNombreBolsa().toUpperCase().contains("DEPRAX") || medResi.getSpdNombreBolsa().toUpperCase().contains("TRAZODONA"))
					|| (medResi.getResiMedicamento()!=null && (medResi.getResiMedicamento().toUpperCase().contains("DEPRAX") || medResi.getResiMedicamento().toUpperCase().contains("TRAZODONA")) ) )
				return true;
			else
				return false;
	   
		}   	   
	   
	   
	   /**
	    * Método que soluciona transforma un tratamiento de trazodona (o deprax) de la siguiente forma:
	    * en caso que solo existan 0,5 en la pauta, lo que hace es transformarlo al CN 111111 (tolva de medias trazodona) y como si fuera 1 comprimido
	    * en caso que existan 0,5 mezclados con enteros o pautas de 1,5, se desdobla en dos tratamientos, la parte entera a trazodona y la parte decimal a comprimidos de medias pastillas (CN 111111)   
	    * @param medResi
	 * @throws Exception 
	    */
	public static void changeTrazodona(String spdUsuario, FicheroResiBean medResi) throws Exception {

			FicheroResiDetalleDAO.borrarHijosTrazodonas(spdUsuario, medResi);
		
            Class<?> tratClass = medResi.getClass();
	           //int numTomas = 24; // Número total de tomas
	           int numTomas =  medResi.getNumeroDeTomas(); // Número total de tomas
	           
	            boolean enteras=false;
	            boolean medias=false;
	            boolean enterasYmedias=false;
	            try {
	                for (int toma = 1; toma <= numTomas; toma++) 
	                {
	                   
	                    Method method = tratClass.getMethod("getResiToma" + toma);
	                    Object value = method.invoke(medResi);
	                    String valor= (String) value;
	                    if(valor!=null && valor.endsWith("50"))
	                    	valor=valor.replace("50", "5");

                    	switch ((String) valor) {
						case "0,25": 
						case "0.25": 
						case "0,5": 
						case "0.5": 
						case "0,75": 
						case "0.75": 
							medias=true;	
							break;
						case "1": 	
						case "2": 	
						case "3": 	
						case "4": 	
						case "5": 
							enteras=true;	
							break;
						case "1,25": 	
						case "1.25": 	
						case "1,5": 	
						case "1.5": 	
						case "1,75": 	
						case "1.75": 	
						case "2.5": 	
						case "2,5":
						case "2,75": 	
						case "2.75": 	
						case "3.5": 	
						case "3,5": 
							enterasYmedias=true;							
							break;
						default:
							break;
    	                }
	                }
	                    //no hay que hacer nada, son enteras
                    	//if(enteras && !medias  && !enterasYmedias)
                    	//	break;
                    	// hay  hacer poner media en todo y código 111111
	                	//if(!enteras && medias  && !enterasYmedias)
                       	if(!enteras && medias  && !enterasYmedias)
                       	{
							medResi.setSpdCnFinal("111111");
							//medResi.setSpdNombreBolsa("/2 " + medResi.getSpdNombreBolsa());
							medResi.setSpdNombreBolsa("/2 DEPRAX 100MG" );
							medResi.setSpdAccionBolsa(SPDConstants.SPD_ACCIONBOLSA_PASTILLERO);
							medResi.setSpdFormaMedicacion("COMPRIMIDOS");
							
							//medResi.setMensajesInfo("media Trazodona editada");
							medResi.setIdEstado(SPDConstants.REGISTRO_EDITADO_AUTOMATICAMENTE);
							medResi.setEditable(true); 
							//medResi.setIdTratamientoCIP(getID(medResi));
							
        	                for ( int toma = 1; toma <= numTomas; toma++) 
        	                {
        	                	Method method = tratClass.getMethod("getResiToma" + toma);
        	                    Object value = method.invoke(medResi);
        	                    Method setMethod = tratClass.getMethod("setResiToma" + toma, String.class);        	                    
        	                    String valor= (String) value;
        	                    if(valor!=null && valor.endsWith("50"))
        	                    	valor=valor.replace("50", "5");
        	                    
        	                    switch ((String) valor) {
        	                    case "0,25":
        	                    case "0.25":
        	                        setMethod.invoke(medResi, "0,5");
        	                        break;
        	                    case "0,5":
        	                    case "0.5":
        	                        setMethod.invoke(medResi, "1");
        	                        break;
        	                    case "0,75":
        	                    case "0.75":
        	                        setMethod.invoke(medResi, "1,5");
        	                        break;       	                    default:
        	                        break;
        	                    }
                          	}
                      	}
                        if(enterasYmedias || (enteras&&medias)) //hay que desdoblar la trazodona a media y enteras
                       	{
                       		FicheroResiBean mediasMediResi = medResi.clone();
     
                     		//creamos  mitades de uno de ellos
                       		mediasMediResi.setSpdCnFinal("111111");
							//mediasMediResi.setSpdNombreBolsa("/2 " + medResi.getSpdNombreBolsa());
                       		mediasMediResi.setSpdNombreBolsa("/2 DEPRAX 100MG" );
							//mediasMediResi.setMensajesInfo("media Trazodona creada");
                       		mediasMediResi.setTipoEnvioHelium("1");
							mediasMediResi.setIdEstado(SPDConstants.REGISTRO_CREADO_AUTOMATICAMENTE);
							mediasMediResi.setEditable(false); 
							
           	                for ( int toma = 1; toma <= numTomas; toma++) {
           	                Method method = tratClass.getMethod("getResiToma" + toma);
           	                Object value = method.invoke(mediasMediResi);
    	                    Method setMethod = tratClass.getMethod("setResiToma" + toma, String.class);
    	                    String valor= (String) value;
    	                    if(valor!=null && valor.endsWith("50"))
    	                    	valor=valor.replace("50", "5");
    	                    
    	                     switch ((String) valor) {
    	                     	case "0,25": 
								case "0.25": 								
    	                     	case "1,25": 
								case "1.25":   									 
       	                     	case "2,25": 
								case "2.25": 
									  setMethod.invoke(mediasMediResi, "0,5"); // Ajusta el nuevo valor 
									  break;
								case "0,5": 
								case "0.5": 
								case "1,5": 
								case "1.5": 
								case "2,5": 
								case "2.5": 
									  setMethod.invoke(mediasMediResi, "1"); // Ajusta el nuevo valor 
									  break;
								case "0,75": 
								case "0.75": 
								case "1,75": 
								case "1.75": 
								case "2,75": 
								case "2.75": 
									  setMethod.invoke(mediasMediResi, "1,5"); // Ajusta el nuevo valor 
									  break;								
								case "1": 
								case "2": 
								case "3": 
								case "4": 
								case "5": 
									  setMethod.invoke(mediasMediResi, "0"); // Ajusta el nuevo valor 
									  break;
								default:
								break;
								}
                          	}
						   mediasMediResi.setIdTratamientoCIP(getID(mediasMediResi)+"_MEDIODEPRAX");  //ponemos un sufijo para diferenciarla de las enteras
						  // mediasMediResi.setTipoEnvioHelium(tipoEnvioHelium);
						   HelperSPD2.getDatosProduccionAnterior( spdUsuario, mediasMediResi, false, true); //al ser desdoblada miramos la anterior por si hay particularidades
						   mediasMediResi.setEditable(false); //para que no se pueda editar 
		    	      		 
						   FicheroResiDetalleDAO.nuevo(mediasMediResi.getIdDivisionResidencia(), mediasMediResi.getIdProceso(), mediasMediResi);
 
           	                
           	                
                       		//limpiamos mitades de uno de ellos
           	                for ( int toma = 1; toma <= numTomas; toma++) {
           	                Method method = tratClass.getMethod("getResiToma" + toma);
       	                    Object value = method.invoke(medResi);
       	                    Method setMethod = tratClass.getMethod("setResiToma" + toma, String.class);
      	                    medResi.setIdEstado(SPDConstants.REGISTRO_EDITADO_AUTOMATICAMENTE);
      	                    medResi.setEditable(true);
    	                    String valor= (String) value;
    	                    if(valor!=null && valor.endsWith("50"))
    	                    	valor=valor.replace("50", "5");
    	                    
       	                    switch ((String) valor) 
       	                    {
       	                     	case "0,25": 
								case "0.25": 
								case "0,5": 
								case "0.5": 
								case "0,75": 
								case "0.75": 
									  setMethod.invoke(medResi, "0"); // Ajusta el nuevo valor 
									  break;
								case "1,25": 
								case "1.25": 
								case "1,5": 
								case "1.5": 
								case "1,75": 
								case "1.75": 
									  setMethod.invoke(medResi, "1"); // Ajusta el nuevo valor 
									  break;
								case "2,25": 
								case "2.25": 
								case "2,5": 
								case "2.5": 
								case "2,75": 
								case "2.75": 
									  setMethod.invoke(medResi, "2"); // Ajusta el nuevo valor 
									  break;
								default:
								break;
							}
                        		
 		                   
    	                    }
	                    }

	            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
	                e.printStackTrace(); // Manejo de excepciones, ajusta según tus necesidades
	            }


	}   
	/**
	 * 
    * id que se utiliza para detectar cambios = resiCIP + resiCn + resiMedicamento + resiInicioTratamiento + resiFinTratamiento + resiObservacionesVariante 
    *  + resiComentarios + resiSiPrecisa + resiPeriodo
	 *	+ resiD1 + resiD2 + resiD3 + resiD4 + resiD5 + resiD6 + resiD7 + resiToma1...resiToma24
    * @param medResi
    * @return
	 * @throws Exception 
    */
   public static String getID(FicheroResiBean medResi) throws Exception {
		
   	String keyOk="";
   	/*
   	 *result=StringUtil.cleanTheText(medResi.getResiCIP())
   			+"_"+StringUtil.cleanTheText(medResi.getResiCn())
   			+"_"+StringUtil.cleanTheText(StringUtil.limpiarTextoTomas(medResi.getResiMedicamento()))
   			+"_"+medResi.getResiInicioTratamiento()
   			+"_"+medResi.getResiFinTratamiento()
   			+"_"+StringUtil.cleanTheText(StringUtil.limpiarTextoTomas(medResi.getResiObservaciones()))
   			+"_"+StringUtil.cleanTheText(StringUtil.limpiarTextoTomas(medResi.getResiComentarios()))
   			+"_"+StringUtil.cleanTheText(StringUtil.limpiarTextoTomas(medResi.getResiSiPrecisa()))
   			+"_"+StringUtil.cleanTheText(StringUtil.limpiarTextoTomas(medResi.getResiPeriodo()))
   			+"_"+medResi.getResiD1()+"_"+medResi.getResiD2()+"_"+medResi.getResiD3()+"_"+medResi.getResiD4()+"_"+medResi.getResiD5()+"_"+medResi.getResiD6()+"_"+medResi.getResiD7()
   			+"_"+medResi.getResiToma1()+"_"+medResi.getResiToma2()+"_"+medResi.getResiToma3()+"_"+medResi.getResiToma4()+"_"+medResi.getResiToma5()+"_"+medResi.getResiToma6()
   			+"_"+medResi.getResiToma7()+"_"+medResi.getResiToma8()+"_"+medResi.getResiToma9()+"_"+medResi.getResiToma10()+"_"+medResi.getResiToma11()+"_"+medResi.getResiToma12()
   			+"_"+medResi.getResiToma13()+"_"+medResi.getResiToma14()+"_"+medResi.getResiToma15()+"_"+medResi.getResiToma16()+"_"+medResi.getResiToma17()+"_"+medResi.getResiToma18()
   			+"_"+medResi.getResiToma19()+"_"+medResi.getResiToma20()+"_"+medResi.getResiToma21()+"_"+medResi.getResiToma22()+"_"+medResi.getResiToma23()+"_"+medResi.getResiToma24()
   			;
   	*/
   	keyOk= medResi.getResiCIP()
   			+"_"+medResi.getResiCn()
   			+"_"+StringUtil.limpiarTextoTomas(medResi.getResiMedicamento())
   			+"_"+medResi.getResiInicioTratamiento()
   			+"_"+medResi.getResiFinTratamiento()
   			+"_"+medResi.getResiObservaciones()
   			+"_"+medResi.getResiComentarios()
   			+"_"+medResi.getResiSiPrecisa()
   			//+"_"+medResi.getResiPeriodo() Mejor no poner porque puede cambiar antes de la edición
   			+"_"+medResi.getResiD1()+"_"+medResi.getResiD2()+"_"+medResi.getResiD3()+"_"+medResi.getResiD4()+"_"+medResi.getResiD5()+"_"+medResi.getResiD6()+"_"+medResi.getResiD7()
   			+"_"+medResi.getResiToma1()+"_"+medResi.getResiToma2()+"_"+medResi.getResiToma3()+"_"+medResi.getResiToma4()+"_"+medResi.getResiToma5()+"_"+medResi.getResiToma6()
   			+"_"+medResi.getResiToma7()+"_"+medResi.getResiToma8()+"_"+medResi.getResiToma9()+"_"+medResi.getResiToma10()+"_"+medResi.getResiToma11()+"_"+medResi.getResiToma12()
   			+"_"+medResi.getResiToma13()+"_"+medResi.getResiToma14()+"_"+medResi.getResiToma15()+"_"+medResi.getResiToma16()+"_"+medResi.getResiToma17()+"_"+medResi.getResiToma18()
   			+"_"+medResi.getResiToma19()+"_"+medResi.getResiToma20()+"_"+medResi.getResiToma21()+"_"+medResi.getResiToma22()+"_"+medResi.getResiToma23()+"_"+medResi.getResiToma24();
		
   		if(medResi.getResiVariante()!=null && !medResi.getResiVariante().equals(""))
		{
   			keyOk+="_"+medResi.getResiVariante();
		}
   	   	//quitamos todos los caracteres que no son letras/números 
   	   	String keyAntigua = keyOk.replaceAll("[^a-zA-Z0-9]", "");
   	   	
   	   	
   		
   	//quitamos todos los caracteres que no son letras/números 
   	 keyOk = keyOk.replaceAll("[^a-zA-Z0-9_]", "");

	   	FicheroResiDetalleDAO.actualizarKeyAntiguaAKeyNueva(keyAntigua, keyOk);	//he cambiado el formato para conservar el guión bajo
	    
	   	System.out.println("----- getID --> " + keyOk);
   	return keyOk;
	}

   
   /**
    * Método que crea un ID de lo que se envía a robot 
    * @param medResi
    * @return
    * @throws Exception
    */
   public static void actualizarIDTratamientoSPD(FicheroResiBean medResi) throws Exception {
		
	   String inicioTratamiento=medResi.getResiInicioTratamientoParaSPD();
	   if(isNull(medResi.getResiInicioTratamientoParaSPD(), "").equals(""))
		   inicioTratamiento=medResi.getResiInicioTratamiento();
	   
	   String finTratamiento=medResi.getResiFinTratamientoParaSPD();
	   if(isNull(medResi.getResiFinTratamientoParaSPD(), "").equals(""))
		   finTratamiento=medResi.getResiFinTratamiento();
		   
		boolean esAegerus =medResi.getIdProcessIospd()!=null && medResi.getIdProcessIospd().equalsIgnoreCase(SPDConstants.IDPROCESO_AEGERUS);    
		   
	   	String idTratamientoSPD= medResi.getResiCIP() + "|";
	   			idTratamientoSPD+=StringUtil.quitaEspacios(medResi.getSpdCnFinal()) + "|";
	   			idTratamientoSPD+= medResi.getSpdNombreBolsa() + "|";
	   			idTratamientoSPD+= medResi.getSpdAccionBolsa() + "|"; 
                if(!esAegerus)
                {
                	idTratamientoSPD+= isNull(medResi.getResiD1(), "_") + "|";
                    idTratamientoSPD+= isNull(medResi.getResiD2(), "_") + "|";
                    idTratamientoSPD+= isNull(medResi.getResiD3(), "_") + "|";
                    idTratamientoSPD+= isNull(medResi.getResiD4(), "_") + "|";
                    idTratamientoSPD+= isNull(medResi.getResiD5(), "_") + "|";
                    idTratamientoSPD+= isNull(medResi.getResiD6(), "_") + "|";
                    idTratamientoSPD+= isNull(medResi.getResiD7(), "_") + "|";
                    idTratamientoSPD+= inicioTratamiento + "|";
                    idTratamientoSPD+= finTratamiento + "|";
                }
                idTratamientoSPD+= isNull(medResi.getResiToma1(), "_") + "|";
                idTratamientoSPD+= isNull(medResi.getResiToma2(), "_") + "|";
                idTratamientoSPD+= isNull(medResi.getResiToma3(), "_") + "|";
                idTratamientoSPD+= isNull(medResi.getResiToma4(), "_") + "|";
                idTratamientoSPD+= isNull(medResi.getResiToma5(), "_") + "|"; 
                idTratamientoSPD+= isNull(medResi.getResiToma6(), "_") + "|";
                idTratamientoSPD+= isNull(medResi.getResiToma7(), "_") + "|";
                idTratamientoSPD+= isNull(medResi.getResiToma8(), "_") + "|";
                idTratamientoSPD+= isNull(medResi.getResiToma9(), "_") + "|";
                idTratamientoSPD+= isNull(medResi.getResiToma10(), "_") + "|";
                idTratamientoSPD+= isNull(medResi.getResiToma11(), "_") + "|";
                idTratamientoSPD+= isNull(medResi.getResiToma12(), "_") + "|";
                idTratamientoSPD+= isNull(medResi.getResiToma13(), "_") + "|";
                idTratamientoSPD+= isNull(medResi.getResiToma14(), "_") + "|";
                idTratamientoSPD+= isNull(medResi.getResiToma15(), "_") + "|";
                idTratamientoSPD+= isNull(medResi.getResiToma16(), "_") + "|";
                idTratamientoSPD+= isNull(medResi.getResiToma17(), "_") + "|";
                idTratamientoSPD+= isNull(medResi.getResiToma18(), "_") + "|";
                idTratamientoSPD+= isNull(medResi.getResiToma19(), "_") + "|";
                idTratamientoSPD+= isNull(medResi.getResiToma20(), "_") + "|";
                idTratamientoSPD+= isNull(medResi.getResiToma21(), "_") + "|";
                idTratamientoSPD+= isNull(medResi.getResiToma22(), "_") + "|";
                idTratamientoSPD+= isNull(medResi.getResiToma23(), "_") + "|";
                idTratamientoSPD+= isNull(medResi.getResiToma24(), "_") + "|";
                if(!esAegerus)
                {
                	idTratamientoSPD+= isNull(medResi.getDiasMesConcretos(), "_") + "|";
                	idTratamientoSPD+= isNull(medResi.getDiasSemanaConcretos(), "_") + "|";
                    idTratamientoSPD+= isNull(StringUtil.limpiarTextoyEspacios(medResi.getSecuenciaGuide()), "_") + "|";
                    idTratamientoSPD+= isNull(String.valueOf(medResi.getResiFrecuencia()), "_") + "|";
                }
	   	
	   	   	//quitamos todos los caracteres que no son letras/números 
	   	  // idTratamientoSPD = idTratamientoSPD.replaceAll("[^a-zA-Z0-9]", "");
	   		idTratamientoSPD = idTratamientoSPD.replace("|0|", "|_|");  //para no tener en cuenta si se ha puesto '0' o es vacío
	   	   	
		    
		   	System.out.println("----- getIDTratamientoSPD --> " + idTratamientoSPD);
		   	medResi.setIdTratamientoSPD(idTratamientoSPD);
		}

   public static FicheroResiBean recuperaDatosAnteriores(String spdUsuario, FicheroResiBean medResiActual, boolean porDetallRow) throws Exception {
	   return recuperaDatosAnteriores(spdUsuario, medResiActual, porDetallRow, false);
   }

	public static FicheroResiBean recuperaDatosAnteriores(String spdUsuario, FicheroResiBean medResiActual, boolean porDetallRow, boolean historico ) throws Exception {
		FicheroResiBean medResiAnterior=null;
		List<FicheroResiBean> rows = FicheroResiDetalleDAO.getUltimoFicheroResiDetalle(spdUsuario, medResiActual, porDetallRow, historico);
		if(rows!=null && rows.size()>0)
			medResiAnterior= (FicheroResiBean)rows.get(0);
		return medResiAnterior;
	}
	   
	/**
	 * 
	 * @param spdUsuario
	 * @param medResi
	 * @param medResiAnterior
	 * @param chequearPeriodicas - Boolean para indicar si es preciso chequeo de tratamientos periódicos al recuperar información del tratamiento reutilizado
	 * @throws Exception
	 */
	public static void actualizaCamposDelAnterior(String spdUsuario, FicheroResiBean medResi, FicheroResiBean medResiAnterior, boolean chequearPeriodicas ) throws Exception {
		System.out.println("INIT actualizaCamposDelAnterior");
		if(medResiAnterior!=null)
		{
			//ponemos la información del último registro editado
			medResi.setResiSiPrecisa(medResiAnterior.getResiSiPrecisa());
			medResi.setResiPeriodo(medResiAnterior.getResiPeriodo());
			medResi.setSpdCnFinal(medResiAnterior.getSpdCnFinal());
			medResi.setSpdNombreBolsa(medResiAnterior.getSpdNombreBolsa());
			medResi.setSpdFormaMedicacion(medResiAnterior.getSpdFormaMedicacion());
			medResi.setSpdAccionBolsa(medResiAnterior.getSpdAccionBolsa());
			//medResi.setResiInicioTratamiento(medResiAnterior.getResiInicioTratamiento());
			medResi.setResiInicioTratamiento(medResi.getResiInicioTratamiento());
			//medResi.setResiFinTratamiento(medResiAnterior.getResiFinTratamiento());
			medResi.setResiFinTratamiento(medResi.getResiFinTratamiento());
			//medResi.setResiComentarios(medResiAnterior.getResiComentarios());
			medResi.setResiComentarios(medResi.getResiComentarios());
			//medResi.setResiObservaciones(medResiAnterior.getResiObservaciones());
			medResi.setResiObservaciones(medResi.getResiObservaciones());
			//medResi.setResiViaAdministracion(medResiAnterior.getResiViaAdministracion());
			medResi.setResiViaAdministracion(medResi.getResiViaAdministracion());
			//medResi.setResiVariante(medResiAnterior.getResiVariante());
			medResi.setResiVariante(medResi.getResiVariante());
			medResi.setResiD1(medResiAnterior.getResiD1());
			medResi.setResiD2(medResiAnterior.getResiD2());
			medResi.setResiD3(medResiAnterior.getResiD3());
			medResi.setResiD4(medResiAnterior.getResiD4());
			medResi.setResiD5(medResiAnterior.getResiD5());
			medResi.setResiD6(medResiAnterior.getResiD6());
			medResi.setResiD7(medResiAnterior.getResiD7());
			medResi.setDiasSemanaMarcados(medResiAnterior.getDiasSemanaMarcados());
			medResi.setResiDiasAutomaticos(medResiAnterior.getResiDiasAutomaticos());
			
			
			for (int i = 1; i <= 24; i++) {
			    String fieldName = "resiToma" + i;
			    try {
			        Field field = medResi.getClass().getDeclaredField(fieldName);
			        field.setAccessible(true);
			        Object value = field.get(medResiAnterior);
			        field.set(medResi, value);
			    } catch (NoSuchFieldException | IllegalAccessException e) {
			        e.printStackTrace();
			    }
			}
			
			HelperSPD2.checkNuevasTomas(medResi, medResiAnterior);
				
			medResi.setMensajesInfo(medResiAnterior.getMensajesInfo()); 
			medResi.setMensajesAlerta(medResiAnterior.getMensajesAlerta());
			medResi.setMensajesResidencia(medResiAnterior.getMensajesResidencia());
			medResi.setIncidencia(medResiAnterior.getIncidencia());
			medResi.setNumeroDeTomas(medResiAnterior.getNumeroDeTomas());
			medResi.setIdEstado(SPDConstants.REGISTRO_REUTILIZADO_DE_ANTERIOR_PRODUCCION); //estado
			medResi.setEditado(medResiAnterior.getEditado()); 
			medResi.setValidar(medResiAnterior.getValidar()); 
			medResi.setConfirmar(medResiAnterior.getConfirmar()); 
	
			medResi.setConfirmaciones(medResiAnterior.getConfirmaciones()); 
			if(medResi.getConfirmar().equalsIgnoreCase(SPDConstants.REGISTRO_CONFIRMADO)) //para control del número de validaciones
			{
				int confirmaciones = medResiAnterior.getConfirmaciones();
				int nConfirmaciones = SPDConstants.CTRL_PRINCIPIO_ACTIVO_N_VALIDACIONES;
				if(nConfirmaciones>confirmaciones)
					medResi.setConfirmaciones(medResiAnterior.getConfirmaciones()+1); 

			}
			
					
			medResi.setResiFrecuencia(medResiAnterior.getResiFrecuencia());
			medResi.setResiPeriodo(medResiAnterior.getResiPeriodo());
			medResi.setDiasSemanaConcretos(medResiAnterior.getDiasSemanaConcretos());
			medResi.setDiasSemanaMarcados(medResiAnterior.getDiasSemanaMarcados());
			medResi.setDiasMesConcretos(medResiAnterior.getDiasMesConcretos());
			medResi.setSecuenciaGuide(medResiAnterior.getSecuenciaGuide());
			medResi.setTipoEnvioHelium(medResiAnterior.getTipoEnvioHelium());
			medResi.setEditable(true);
			
			// Quitamos este código porque entrábamos en bucle infinito ya que se accede aquí desde chequeoTratamientoMensual o quincenal
			if (chequearPeriodicas && medResi.getResiPeriodo()!=null && medResi.getResiPeriodo().equalsIgnoreCase(SPDConstants.SPD_PERIODO_MENSUAL) ) 
			{
				HelperSPD2.chequeoTratamientoMensual(spdUsuario, medResi);
			}
			if (chequearPeriodicas && medResi.getResiPeriodo()!=null && medResi.getResiPeriodo().equalsIgnoreCase(SPDConstants.SPD_PERIODO_QUINCENAL) )
			{
				HelperSPD2.chequeoTratamientoQuincenal(spdUsuario, medResi);
			}
				
		}
		/*else 
		{
			if(medResi.getResiFrecuencia()==14)
				medResi.setDiasMesConcretos(SPDConstants.DIAS_DEFECTO_QUINCENA);
			if(medResi.getResiFrecuencia()==30)
				medResi.setDiasMesConcretos(SPDConstants.DIAS_DEFECTO_MES);
				
		}*/
		System.out.println("FIN actualizaCamposDelAnterior");

		
	}
	
	/**
	 * Se realiza un copia de los valores de las resiTomaX de la anterior cabecerea en caso que ya exista un registro anterior con más dosis.
	 * Damos por hecho que la cabecera ya se ha actualizado en caso que la anterior producción hay sido modificada
	 * @param medResi
	 * @param medResiAnterior
	 * @throws ClassNotFoundException
	 */
	private static void checkNuevasTomas(FicheroResiBean medResi, FicheroResiBean medResiAnterior) throws ClassNotFoundException {
		int numeroTomasActual = medResi.getNumeroDeTomas();
		if(numeroTomasActual==0)numeroTomasActual=1;
		int numeroTomasAnterior = medResiAnterior.getNumeroDeTomas();
		boolean hayCambios=false;
		if(numeroTomasAnterior>numeroTomasActual)
		{
			/*FicheroResiBean cabeceraActual =  ExtReHelper.getCabeceraFicheroResiPlantUnif(medResi.getIdDivisionResidencia(), medResi.getIdProceso(),  false);
			FicheroResiBean cabeceraAnterior =  ExtReHelper.getCabeceraFicheroResiPlantUnif(medResiAnterior.getIdDivisionResidencia(), medResiAnterior.getIdProceso(),  false);
			
			cabeceraActual.setNumeroDeTomas(cabeceraAnterior.getNumeroDeTomas());
			*/
			medResi.setNumeroDeTomas(medResiAnterior.getNumeroDeTomas());
			
			for (int i = numeroTomasActual; i <= numeroTomasAnterior; i++) {
				hayCambios=true;
			    String fieldName = "resiToma" + i;
			    try {
			        Field field = medResi.getClass().getDeclaredField(fieldName);
			        field.setAccessible(true);
			        Object value = field.get(medResiAnterior);
			        field.set(medResi, value);
			    } catch (NoSuchFieldException | IllegalAccessException e) {
			        e.printStackTrace();
			    }
			}
/*
 *			
			if(hayCambios)
			{
				cabeceraActual.setIdEstado(SPDConstants.REGISTRO_REUTILIZADO_DE_ANTERIOR_PRODUCCION);
				FicheroResiDetalleDAO.edita(cabeceraActual);
				FicheroResiDetalleDAO.actualizaNumeroDeTomas(cabeceraActual);
			}
*/				
			
			
		}
		
		
	}

	/**
	 * Método que buscará los días de toma concretos del mes para los casos que la frecuencia sea mensual o quincenal
	 * @param spdUsuario
	 * @param medResi
	 * @param porDetallRow - Indica si la búsqueda del reutilizado se realiza por detalleRow
	 * @param chequearPeriodicas - Boolean para indicar si es preciso chequeo de tratamientos periódicos al recuperar información del tratamiento reutilizado
	 * @return
	 * @throws Exception 
	 */

	public static boolean getDatosProduccionAnterior(String spdUsuario, FicheroResiBean medResi, boolean porDetallRow, boolean chequearPeriodicas) throws Exception {
		
		FicheroResiBean medResiAnterior = HelperSPD2.recuperaDatosAnteriores(spdUsuario, medResi, porDetallRow);
		if(medResiAnterior!=null) 
			HelperSPD2.actualizaCamposDelAnterior(spdUsuario, medResi, medResiAnterior, chequearPeriodicas);
		return medResiAnterior!=null;
			
	}
	



	public static String marcarSecuencia(FicheroResiBean medResi, String a) {
		String result="";
		int freq = new Integer(a).intValue();
		if(freq==7) result=SPDConstants.SPD_PERIODO_SEMANAL;
		else if(freq==14 || freq==15) result=SPDConstants.SPD_PERIODO_QUINCENAL;
		else if(freq==30 || freq==31) result=SPDConstants.SPD_PERIODO_MENSUAL;
		else{
			result="secuenciaGuidePorVariante";
			medResi.setSecuenciaGuide("1-"+(freq-1)+"-DAYS");
		}
		return result;
	}



	/**
	 * Función que intenta detectar el periodo
	 * @param medResi
	 * @throws Exception 
	 */
	   public static void detectarPeriodoEdicion(String spdUsuario, FicheroResiBean medResi) throws Exception {
		   
		   //la variante no la miramos en la edición, se mira en el alta
	        /*String resiVariante = "";
	        try 
	        {
	        	resiVariante = StringUtil.limpiarTextoEspaciosYAcentos(medResi.getResiVariante(), false).toLowerCase();
	        }
	        catch(Exception e){
	        	
	        }
	        */
	
	        if(medResi.getResiPeriodo().equalsIgnoreCase("diario") )
	        {
    			medResi.setDiasConToma(7);
    			medResi.setDiasSemanaMarcados(7);
    			medResi.setResiFrecuencia(1);
    			medResi.setResiD1("X");
    			medResi.setResiD2("X");
    			medResi.setResiD3("X");
    			medResi.setResiD4("X");
    			medResi.setResiD5("X");
    			medResi.setResiD6("X");
    			medResi.setResiD7("X");
    			medResi.setDiasSemanaConcretos("");
    			//medResi.setDiasMesConcretos("");
    			//medResi.setSecuenciaGuide("");//validar la funcionalidad
    			medResi.setResiPeriodo("diario");
    			//medResi.setTipoEnvioHelium("1");
	        }
	        if (medResi.getResiPeriodo().equalsIgnoreCase(SPDConstants.SPD_PERIODO_DIAS_SEMANA_CONCRETOS)) 
			{
    			HelperSPD2.detectarDiasMarcados(medResi);
				//medResi.setTipoEnvioHelium("2");
				medResi.setResiPeriodo(SPDConstants.SPD_PERIODO_DIAS_SEMANA_CONCRETOS);
    			//medResi.setSecuenciaGuide("");
			}
			//aqui ya miramos lo que hay en variante, pero después de mirar los dias anteriores
	       // String resultVariante=tratarVariante(medResi);

			if (medResi.getResiPeriodo().equalsIgnoreCase(SPDConstants.SPD_PERIODO_SEMANAL) ) 
			{
				HelperSPD2.detectarDiasMarcados(medResi);
		        //medResi.setDiasConToma(1);
				medResi.setResiFrecuencia(7);
		        
		       // medResi.setDiasSemanaMarcados(1);
		        //  medResi.setTipoEnvioHelium("2");
		        medResi.setResiPeriodo(SPDConstants.SPD_PERIODO_SEMANAL);
    			//medResi.setSecuenciaGuide("");

				if(medResi.getDiasConToma()>1)
				{

					if(medResi.getResiD1().equalsIgnoreCase("X"))
					{
						medResi.setResiD1("X");
						medResi.setDiasSemanaConcretos(SPDConstants.DIA1);
						medResi.setResiD2("");medResi.setResiD3("");medResi.setResiD4("");medResi.setResiD5("");medResi.setResiD6("");medResi.setResiD7("");
					} else if(medResi.getResiD2().equalsIgnoreCase("X")){
						medResi.setResiD2("X");
						medResi.setDiasSemanaConcretos(SPDConstants.DIA2);
						medResi.setResiD1("");medResi.setResiD3("");medResi.setResiD4("");medResi.setResiD5("");medResi.setResiD6("");medResi.setResiD7("");
					} else if(medResi.getResiD3().equalsIgnoreCase("X")){
						medResi.setResiD3("X");
						medResi.setResiD1("");medResi.setResiD2("");medResi.setResiD4("");medResi.setResiD5("");medResi.setResiD6("");medResi.setResiD7("");
						medResi.setDiasSemanaConcretos(SPDConstants.DIA3);
					} else if(medResi.getResiD4().equalsIgnoreCase("X")){
						medResi.setResiD4("X");
						medResi.setResiD1("");medResi.setResiD2("");medResi.setResiD3("");medResi.setResiD5("");medResi.setResiD6("");medResi.setResiD7("");
						medResi.setDiasSemanaConcretos(SPDConstants.DIA4);
					} else if(medResi.getResiD5().equalsIgnoreCase("X")){
						medResi.setResiD5("X");
						medResi.setResiD1("");medResi.setResiD2("");medResi.setResiD3("");medResi.setResiD4("");medResi.setResiD6("");medResi.setResiD7("");
						medResi.setDiasSemanaConcretos(SPDConstants.DIA5);
					} else if(medResi.getResiD6().equalsIgnoreCase("X")){
						medResi.setResiD6("X");
						medResi.setResiD1("");medResi.setResiD2("");medResi.setResiD3("");medResi.setResiD4("");medResi.setResiD5("");medResi.setResiD7("");
						medResi.setDiasSemanaConcretos(SPDConstants.DIA6);
					} else 	if(medResi.getResiD7().equalsIgnoreCase("X")){
						medResi.setResiD7("X");
						medResi.setResiD1("");medResi.setResiD2("");medResi.setResiD3("");medResi.setResiD4("");medResi.setResiD5("");medResi.setResiD6("");
						medResi.setDiasSemanaConcretos(SPDConstants.DIA7);
					} 
					}
				if(medResi.getDiasConToma()==0) {
					medResi.setResiD1("X");
					medResi.setResiD2("");
					medResi.setResiD3("");
					medResi.setResiD4("");
					medResi.setResiD5("");
					medResi.setResiD6("");
					medResi.setResiD7("");
					medResi.setDiasSemanaConcretos(SPDConstants.DIA1);
				}
				medResi.setDiasSemanaMarcados(1);
				medResi.setDiasConToma(1);

			}
			
			//if (medResi.getResiPeriodo().equalsIgnoreCase("quincenal") || isEqualIgnoreCase(resultVariante, "quincenal"))  {
			if (medResi.getResiPeriodo().equalsIgnoreCase(SPDConstants.SPD_PERIODO_QUINCENAL) )  {
				chequeoTratamientoQuincenal(spdUsuario, medResi);
				if(medResi.getDiasMesConcretos()==null || medResi.getDiasMesConcretos().equals(""))
					medResi.setDiasMesConcretos(SPDConstants.DIAS_DEFECTO_QUINCENA);
				medResi.setResiD1("X");
				medResi.setResiD2("X");
				medResi.setResiD3("X");
				medResi.setResiD4("X");
				medResi.setResiD5("X");
				medResi.setResiD6("X");
				medResi.setResiD7("X");
    			medResi.setDiasConToma(7);
    			medResi.setDiasSemanaMarcados(7);

		        //  medResi.setTipoEnvioHelium("3");
		        medResi.setResiFrecuencia(15);
				medResi.setResiPeriodo(SPDConstants.SPD_PERIODO_QUINCENAL);
				//medResi.setSecuenciaGuide("");
				//medResi.setDiasSemanaConcretos("");//validar la funcionalidad
			}
			//if (medResi.getResiPeriodo().equalsIgnoreCase("mensual")|| isEqualIgnoreCase(resultVariante, "mensual"))  {
			if (medResi.getResiPeriodo().equalsIgnoreCase(SPDConstants.SPD_PERIODO_MENSUAL))  {
				//	medResi.setTipoEnvioHelium("3");
				chequeoTratamientoMensual(spdUsuario, medResi);
				if(medResi.getDiasMesConcretos()==null || medResi.getDiasMesConcretos().equals(""))
					medResi.setDiasMesConcretos(SPDConstants.DIAS_DEFECTO_MES);
			
				/*
				medResi.setResiD1("X");
				medResi.setResiD2("X");
				medResi.setResiD3("X");
				medResi.setResiD4("X");
				medResi.setResiD5("X");
				medResi.setResiD6("X");
				medResi.setResiD7("X");
    			medResi.setDiasConToma(7);
    			medResi.setDiasSemanaMarcados(7);
    			*/
    			medResi.setResiFrecuencia(31);
				medResi.setResiPeriodo(SPDConstants.SPD_PERIODO_MENSUAL);
    			//medResi.setDiasSemanaConcretos("");
				//medResi.setSecuenciaGuide("");
				//medResi.setDiasSemanaConcretos("");//validar la funcionalidad

			}
			if (medResi.getResiPeriodo().equalsIgnoreCase("bimensual") )  {
				if(medResi.getDiasMesConcretos()==null || medResi.getDiasMesConcretos().equals(""))
					medResi.setDiasMesConcretos(SPDConstants.DIAS_DEFECTO_MES);
				medResi.setResiPeriodo("bimensual");
				/*medResi.setResiD1("X");
				medResi.setResiD2("X");
				medResi.setResiD3("X");
				medResi.setResiD4("X");
				medResi.setResiD5("X");
				medResi.setResiD6("X");
				medResi.setResiD7("X");
    			medResi.setDiasConToma(7);
    			medResi.setDiasSemanaMarcados(7);
    			*/
    			medResi.setSecuenciaGuide("1-1-MONTH");
				medResi.setResiFrecuencia(61);
			}
			if (medResi.getResiPeriodo().equalsIgnoreCase("trimestral"))  {
				if(medResi.getDiasMesConcretos()==null || medResi.getDiasMesConcretos().equals(""))
					medResi.setDiasMesConcretos(SPDConstants.DIAS_DEFECTO_MES);
				medResi.setResiPeriodo("trimestral");
				/*medResi.setResiD1("X");
				medResi.setResiD2("X");
				medResi.setResiD3("X");
				medResi.setResiD4("X");
				medResi.setResiD5("X");
				medResi.setResiD6("X");
				medResi.setResiD7("X");
    			medResi.setDiasConToma(7);
    			medResi.setDiasSemanaMarcados(7);*/
    			medResi.setSecuenciaGuide("1-2-MONTH");
				medResi.setResiFrecuencia(92);
			}
			if (medResi.getResiPeriodo().equalsIgnoreCase("semestral"))  {
				if(medResi.getDiasMesConcretos()==null || medResi.getDiasMesConcretos().equals(""))
					medResi.setDiasMesConcretos(SPDConstants.DIAS_DEFECTO_MES);
				medResi.setResiPeriodo("semestral");
			/*	medResi.setResiD1("X");
				medResi.setResiD2("X");
				medResi.setResiD3("X");
				medResi.setResiD4("X");
				medResi.setResiD5("X");
				medResi.setResiD6("X");
				medResi.setResiD7("X");
    			medResi.setDiasConToma(7);
    			medResi.setDiasSemanaMarcados(7);*/
    			medResi.setSecuenciaGuide("1-5-MONTH");
				medResi.setResiFrecuencia(183);
			}
			if (medResi.getResiPeriodo().equalsIgnoreCase(SPDConstants.SPD_PERIODO_ANUAL) )  {
				if(medResi.getDiasMesConcretos()==null || medResi.getDiasMesConcretos().equals(""))
					medResi.setDiasMesConcretos(SPDConstants.DIAS_DEFECTO_MES);
				medResi.setResiPeriodo(SPDConstants.SPD_PERIODO_ANUAL);
				/*medResi.setResiD1("X");
				medResi.setResiD2("X");
				medResi.setResiD3("X");
				medResi.setResiD4("X");
				medResi.setResiD5("X");
				medResi.setResiD6("X");
				medResi.setResiD7("X");
    			medResi.setDiasConToma(7);
    			medResi.setDiasSemanaMarcados(7);*/
    			medResi.setSecuenciaGuide("1-11-MONTH");
    			medResi.setResiFrecuencia(365);
			}
			if(medResi.getResiPeriodo().equalsIgnoreCase(SPDConstants.SPD_PERIODO_ESPECIAL))
			{
				medResi.setResiPeriodo(SPDConstants.SPD_PERIODO_ESPECIAL);
				if(medResi.getDiasConToma()==0)
					
				//medResi.setSecuenciaGuide("");
				/*medResi.setResiD1("X");
				medResi.setResiD2("X");
				medResi.setResiD3("X");
				medResi.setResiD4("X");
				medResi.setResiD5("X");
				medResi.setResiD6("X");
				medResi.setResiD7("X");
    			medResi.setDiasConToma(7);
    			medResi.setDiasSemanaMarcados(7);*/
				medResi.setResiFrecuencia(0);
			}
			/*if(isEqualIgnoreCase(resultVariante, "secuenciaGuidePorVariante"))
			{
				medResi.setResiPeriodo("especial");
				medResi.setResiFrecuencia(0);
			}
			*/
			//medResi.setResiFrecuencia(freq);
			//medResi.setResiPeriodo(periodo);
		}

			public static boolean isEqualIgnoreCase(String str1, String str2) {
			    return str1 != null && str1.equalsIgnoreCase(str2);
			}
			
			
	   public static void detectarPeriodoPorFrecuencia(FicheroResiBean medResi) {
	     	String periodo=SPDConstants.SPD_PERIODO_ESPECIAL; //defecto
	     	
			if(medResi.getDiasSemanaMarcados()==7 || medResi.getResiDiasAutomaticos().equalsIgnoreCase("SI"))						periodo=SPDConstants.SPD_PERIODO_DIARIO;
			if(medResi.getDiasSemanaMarcados()==1 || medResi.getResiFrecuencia()==7)												periodo=SPDConstants.SPD_PERIODO_SEMANAL;  
			if (medResi.getResiFrecuencia()==14 || medResi.getResiFrecuencia()==15)								 					periodo=SPDConstants.SPD_PERIODO_QUINCENAL;
			if (medResi.getResiFrecuencia()==28 || medResi.getResiFrecuencia()==30  || medResi.getResiFrecuencia()==31)				periodo=SPDConstants.SPD_PERIODO_MENSUAL;
			if (medResi.getResiFrecuencia()==60)																 					periodo="bimensual";
			if (medResi.getResiFrecuencia()==90)																 					periodo="trimestral";
			if (medResi.getResiFrecuencia()==180)																 					periodo="semestral";
			if (medResi.getResiFrecuencia()==360)																 					periodo=SPDConstants.SPD_PERIODO_ANUAL;

			medResi.setResiPeriodo(periodo);
			
		}


	    public static void chequeoTratamientoMensual(String spdUsuario, FicheroResiBean medResi) throws Exception {
	    	
	    	// Si es mensual, la frecuencia es 30
			int diaMesDefecto= 1;
			if(medResi.getResiFrecuencia()==30 || ( medResi.getResiPeriodo()!=null && medResi.getResiPeriodo().equalsIgnoreCase(SPDConstants.SPD_PERIODO_MENSUAL)) && !medResi.getResiCn().contains(SPDConstants.PREFIJO_REGISTRO_SECUENCIA_GUIDE))
			{
		    	//borrado de secuenciales previos
		    	HelperSPD2.borrarTratamientosSecuencialesPrevios(spdUsuario, medResi);

		    	String[] numerosMes = getDiasConcretosMes(medResi.getDiasMesConcretos());
				//en caso que solo haya un número de mes
				int diaMes=getDiasConcretosMesPorPosicion(medResi.getDiasMesConcretos(), 1);
				if(diaMes==0) diaMes=diaMesDefecto;
				String fechaMes = obtenerFechaEnRango(medResi.getFechaDesde(), medResi.getFechaHasta(), diaMes);
				Date fechaMesDate =  DateUtilities.getDate(fechaMes, "dd/MM/yyyy");
				Date fechaInicioTratamiento=DateUtilities.getDate(medResi.getResiInicioTratamiento(), "dd/MM/yyyy");
				Date fechaFinTratamiento=null;
				if(medResi.getResiFinTratamiento()!=null && !medResi.getResiFinTratamiento().equalsIgnoreCase(""))
					fechaFinTratamiento=DateUtilities.getDate(medResi.getResiFinTratamiento(), "dd/MM/yyyy");
				
				//si encontramos que la fecha está en el rango....
		        // Verificar si fecha1Date está dentro del rango del tratamiento
		        if (fechaMes!=null && fechaMesDate.compareTo(fechaInicioTratamiento) >= 0 && (fechaFinTratamiento == null || fechaMesDate.compareTo(fechaFinTratamiento) <= 0)) 
		        {
		            medResi.setResiInicioTratamientoParaSPD(fechaMes);	//actualizamos las fechas inicio y fin para que solo se produzca un dia
		            medResi.setResiFinTratamientoParaSPD(fechaMes);
		           // medResi.setDiasMesConcretos(diaMes+"");
		        }
		        else //ponemos una fecha inicio/fin con el día elegido aunque sea fuera de rango
		        {
		            medResi.setResiInicioTratamientoParaSPD(obtenerFechaDia(new Date(), diaMes));	//actualizamos las fechas inicio y fin para que solo se produzca un dia
		            medResi.setResiFinTratamientoParaSPD(obtenerFechaDia(new Date(), diaMes));
			     }
		        if(numerosMes!=null && numerosMes.length>1)
				{
		        	//String sufijo= medResi.getResiCn() + "_" + SPDConstants.PREFIJO_REGISTRO_SECUENCIA_GUIDE ;
					String sufijo=  SPDConstants.PREFIJO_REGISTRO_SECUENCIA_GUIDE ;
					
					//bucle de los números en los que se desea tner pauta en el mes
					for(int i=2; i<=numerosMes.length;i++)
					{
						//si hay más de uno, hemos de crear un nuevo FicheroBean
						FicheroResiBean nuevoDiaMes = medResi.clone();
						 diaMes=getDiasConcretosMesPorPosicion(nuevoDiaMes.getDiasMesConcretos(), i);
						if(diaMes==0) diaMes=diaMesDefecto;
						 fechaMes = obtenerFechaEnRango(nuevoDiaMes.getFechaDesde(), nuevoDiaMes.getFechaHasta(), diaMes);
						 fechaMesDate =  DateUtilities.getDate(fechaMes, "dd/MM/yyyy");
						 fechaInicioTratamiento=DateUtilities.getDate(nuevoDiaMes.getResiInicioTratamiento(), "dd/MM/yyyy");
						 fechaFinTratamiento=null;
						if(nuevoDiaMes.getResiFinTratamiento()!=null && !nuevoDiaMes.getResiFinTratamiento().equalsIgnoreCase(""))
							fechaFinTratamiento=DateUtilities.getDate(nuevoDiaMes.getResiFinTratamiento(), "dd/MM/yyyy");
						
						//si encontramos que la fecha está en el rango....
				        // Verificar si fecha1Date está dentro del rango del tratamiento
				        if (fechaMes!=null && fechaMesDate.compareTo(fechaInicioTratamiento) >= 0 && (fechaFinTratamiento == null || fechaMesDate.compareTo(fechaFinTratamiento) <= 0)) 
				        {
				        	nuevoDiaMes.setResiInicioTratamientoParaSPD(fechaMes);	//actualizamos las fechas inicio y fin para que solo se produzca un dia
				        	nuevoDiaMes.setResiFinTratamientoParaSPD(fechaMes);
				        	//nuevoDiaMes.setDiasMesConcretos(diaMes+"");
				        }
				        else //ponemos una fecha inicio/fin con el día elegido aunque sea fuera de rango
				        {

					     	nuevoDiaMes.setResiInicioTratamientoParaSPD(obtenerFechaDia(new Date(), diaMes));	//actualizamos las fechas inicio y fin para que solo se produzca un dia
					     	nuevoDiaMes.setResiFinTratamientoParaSPD(obtenerFechaDia(new Date(), diaMes));

					    }
				        nuevoDiaMes.setResiCn(nuevoDiaMes.getResiCn()+"_"+sufijo+"_"+i); //cambiamos el CN añadiendo CN_seq_j para que no sea el idTramiento idéntico al medResi
						nuevoDiaMes.setIdTratamientoCIP(getID(nuevoDiaMes));
						 
				        nuevoDiaMes.setIdEstado(SPDConstants.REGISTRO_CREADO_AUTOM_SECUENCIA_GUIDE);
						nuevoDiaMes.setTipoRegistro(SPDConstants.REGISTRO_LINEA_SEC_GUIDE);
						nuevoDiaMes.setEditable(false); 
						nuevoDiaMes.setConfirmar(""); 
						nuevoDiaMes.setValidar(""); 
						nuevoDiaMes.setIncidencia("NO");
						FicheroResiDetalleDAO.nuevo(nuevoDiaMes.getIdDivisionResidencia(), nuevoDiaMes.getIdProceso(), nuevoDiaMes);
				        
	
				}
				}	
		       }	
				

				
		}
	   
/*

	    public static void chequeoTratamientoMensual(FicheroResiBean medResi) throws ClassNotFoundException {
			// Si es mensual, la frecuencia es 30
			int diaMesDefecto= 1;
			if(medResi.getResiFrecuencia()==30 || ( medResi.getResiPeriodo()!=null && medResi.getResiPeriodo().equalsIgnoreCase(SPDConstants.SPD_PERIODO_MENSUAL)))
			{
				String[] numerosMes = getDiasConcretosMes(medResi.getDiasMesConcretos());
		
				int diaMes=getDiasConcretosMesPorPosicion(medResi.getDiasMesConcretos(), 1);
				if(diaMes==0) diaMes=diaMesDefecto;
				String fechaMes = obtenerFechaEnRango(medResi.getFechaDesde(), medResi.getFechaHasta(), diaMes);
				Date fechaMesDate =  DateUtilities.getDate(fechaMes, "dd/MM/yyyy");
				Date fechaInicioTratamiento=DateUtilities.getDate(medResi.getResiInicioTratamiento(), "dd/MM/yyyy");
				
				Date fechaFinTratamiento=null;
				if(medResi.getResiFinTratamiento()!=null && !medResi.getResiFinTratamiento().equalsIgnoreCase(""))
					fechaFinTratamiento=DateUtilities.getDate(medResi.getResiFinTratamiento(), "dd/MM/yyyy");
				//si encontramos que la fecha está en el rango....
		        // Verificar si fecha1Date está dentro del rango del tratamiento
		        if (fechaMes!=null && fechaMesDate.compareTo(fechaInicioTratamiento) >= 0 && (fechaFinTratamiento == null || fechaMesDate.compareTo(fechaFinTratamiento) <= 0)) 
		        {
		        	/*
		        	String mensaje="Fechas SPD se actualizan al día elegido: " + diaMes;
		            if(!medResi.getMensajesInfo().contains(mensaje))
		            	medResi.setMensajesInfo( medResi.getMensajesInfo() + mensaje);
		            
		            medResi.setResiInicioTratamientoParaSPD(fechaMes);	//actualizamos las fechas inicio y fin para que solo se produzca un dia
		            medResi.setResiFinTratamientoParaSPD(fechaMes);
		            medResi.setDiasMesConcretos(diaMes+"");

		        		
					//medResi.setMensajesInfo( "Tratamiento: " + medResi.getResiInicioTratamiento() + "-" + medResi.getResiFinTratamiento()+ ". Se actualiza al día elegido: " + diaMes);
			    	
		        }
		        else //ponemos una fecha inicio/fin con el día elegido aunque sea fuera de rango
		        {
					//medResi.setMensajesInfo( "Tratamiento: " + medResi.getResiInicioTratamiento() + "-" + medResi.getResiFinTratamiento()+ ".  Fuera de rango de producción pero se actualiza al día elegido: " + diaMes);
		        	/*String mensaje="Fechas SPD se actualizan al día elegido: " + diaMes;
		            if(!medResi.getMensajesInfo().contains(mensaje))
		            	medResi.setMensajesInfo( medResi.getMensajesInfo() + mensaje);
		            
		            medResi.setResiInicioTratamientoParaSPD(obtenerFechaDia(new Date(), diaMes));	//actualizamos las fechas inicio y fin para que solo se produzca un dia
		            medResi.setResiFinTratamientoParaSPD(obtenerFechaDia(new Date(), diaMes));
			        }
			}

			
		}
*/
	    
	private static String obtenerFechaDia(Date fecha, int diaMes) {
		return DateUtilities.getDatetoString("dd/MM/yyyy" , DateUtilities.cambiarDiaDeFecha(fecha, diaMes));
	}

	


	/**
	 * Método que actualiza los datos en tratamientos quincenales, en contrato la fecha de inicio y fin para que la producción se realice en esos días concretos.
	 * @param medResi
	 * @throws Exception 
	 */
	public static void chequeoTratamientoQuincenal(String spdUsuario, FicheroResiBean medResi) throws Exception {
		// Si es quincenal, la frecuencia es 14
    	//borrado de secuenciales previos
    	HelperSPD2.borrarTratamientosSecuencialesPrevios(spdUsuario, medResi);

    	int diaDefecto1= 1;
		int diaDefecto2= 15;
		
		int dia1=getDiasConcretosMesPorPosicion(medResi.getDiasMesConcretos(), 1);
		int dia2=getDiasConcretosMesPorPosicion(medResi.getDiasMesConcretos(), 2);
		if(dia1==0) dia1=diaDefecto1;
		if(dia2==0) dia2=diaDefecto2;
		String fecha1 = obtenerFechaEnRango(medResi.getFechaDesde(), medResi.getFechaHasta(), dia1);
		String fecha2 = obtenerFechaEnRango(medResi.getFechaDesde(), medResi.getFechaHasta(), dia2);
			
		Date fecha1Date =  DateUtilities.getDate(fecha1, "dd/MM/yyyy");
		Date fecha2Date =  DateUtilities.getDate(fecha2, "dd/MM/yyyy");
		Date fechaInicioTratamiento=DateUtilities.getDate(medResi.getResiInicioTratamiento(), "dd/MM/yyyy");
		Date fechaFinTratamiento=null;
		if(medResi.getResiFinTratamiento()!=null && !medResi.getResiFinTratamiento().equalsIgnoreCase(""))
			fechaFinTratamiento=DateUtilities.getDate(medResi.getResiFinTratamiento(), "dd/MM/yyyy");
			
			
		//si encontramos que la fecha está en el rango....
	     // Verificar si fecha1Date está dentro del rango del tratamiento
	    if (fecha1!=null && fecha1Date.compareTo(fechaInicioTratamiento) >= 0 && (fechaFinTratamiento == null || fecha1Date.compareTo(fechaFinTratamiento) <= 0)) 
	    {
	    	//medResi.setMensajesInfo( "Tratamiento: " + medResi.getResiInicioTratamiento() + "-" + medResi.getResiFinTratamiento()+ ". Se actualiza al día elegido: " + dia1);
        	/*String mensaje="Fechas SPD se actualizan al día elegido: " + dia1;
            if(!medResi.getMensajesInfo().contains(mensaje))
            	medResi.setMensajesInfo( medResi.getMensajesInfo() + mensaje);
            */
	        medResi.setResiInicioTratamientoParaSPD(fecha1);	//actualizamos las fechas inicio y fin para que solo se produzca un dia
	        medResi.setResiFinTratamientoParaSPD(fecha1);
			//si encontramos que la segunda fecha está también en el rango....
		    // Verificar si fecha2Date está dentro del rango del tratamiento
			if (fecha2!=null  &&  fecha2Date.compareTo(fechaInicioTratamiento) >= 0 && (fechaFinTratamiento == null || fecha2Date.compareTo(fechaFinTratamiento) <= 0)) 
			{
			   FicheroResiBean medResiClone = medResi.clone();	//hemos de duplicar el registro para poner la otra fecha
			   //medResiClone.setMensajesInfo( "Tratamiento: " + medResi.getResiInicioTratamiento() + "-" + medResi.getResiFinTratamiento()+ ". Se actualiza al día elegido: " + dia2);
		      /*	mensaje="Fechas SPD se actualizan al día elegido: " + dia2;
	            if(!medResiClone.getMensajesInfo().contains(mensaje))
	            	medResiClone.setMensajesInfo( medResi.getMensajesInfo() + mensaje);
	            */
			   String cnNuevo= medResi.getResiCn() + "_" + SPDConstants.PREFIJO_REGISTRO_SECUENCIA_GUIDE ;
			   medResiClone.setResiCn(cnNuevo);
			   medResiClone.setEditable(false); 
			   medResiClone.setResiInicioTratamientoParaSPD(fecha2);
			   medResiClone.setResiFinTratamientoParaSPD(fecha2);
			   medResiClone.setIdTratamientoCIP(getID(medResiClone));
			   medResiClone.setIdEstado(SPDConstants.REGISTRO_CREADO_AUTOM_SECUENCIA_GUIDE);
			   medResiClone.setTipoRegistro(SPDConstants.REGISTRO_LINEA_SEC_GUIDE);

				 
			  //medResiClone.setIdEstado(SPDConstants.REGISTRO_CREADO_AUTOMATICAMENTE);
			   HelperSPD2.getDatosProduccionAnterior(spdUsuario, medResiClone, true, false); //al ser desdoblada miramos la anterior por si hay particularidades
			   FicheroResiDetalleDAO.nuevo(medResiClone.getIdDivisionResidencia(), medResiClone.getIdProceso(), medResiClone);
			}
			
			medResi.setTipoEnvioHelium("3");
	    }else
	    {
	      	//si encontramos que solo la segunda fecha está en el rango....// Verificar si fecha2Date está dentro del rango del tratamiento
	      	if (fecha2!=null  &&  fecha2Date.compareTo(fechaInicioTratamiento) >= 0 && (fechaFinTratamiento == null || fecha2Date.compareTo(fechaFinTratamiento) <= 0)) 
	      	{
				//medResi.setMensajesInfo( "Tratamiento: " + medResi.getResiInicioTratamiento() + "-" + medResi.getResiFinTratamiento()+ ". Se actualiza al día elegido: " + dia2);
	        	/*String mensaje="Fechas SPD se actualizan al día elegido: " + dia2;
	            if(!medResi.getMensajesInfo().contains(mensaje))
	            	medResi.setMensajesInfo( medResi.getMensajesInfo() + mensaje);
	            */
				medResi.setResiInicioTratamientoParaSPD(fecha2);
				medResi.setResiFinTratamientoParaSPD(fecha2);
			}
	      	else
	      	{
		    	medResi.setResiInicioTratamientoParaSPD(obtenerFechaDia(new Date(), dia1));	//actualizamos las fechas inicio y fin para que solo se produzca un dia
		        medResi.setResiFinTratamientoParaSPD(obtenerFechaDia(new Date(), dia1));
	      	}
		}
        if (fecha1==null && fecha2==null) //ponemos una fecha inicio/fin con el día elegido aunque sea fuera de rango
	    {
	    	medResi.setResiInicioTratamientoParaSPD(obtenerFechaDia(new Date(), dia1));	//actualizamos las fechas inicio y fin para que solo se produzca un dia
	        medResi.setResiFinTratamientoParaSPD(obtenerFechaDia(new Date(), dia1));
        	/*String mensaje="Quincena fuera de rango SPD : " + dia1;
            if(!medResi.getMensajesInfo().contains(mensaje))
            	medResi.setMensajesInfo( medResi.getMensajesInfo() + mensaje);
            */
	    }	 
	
	}

	/**
	 * Método encargado de devolver el número de una cadena, segñun la posición indicada por parámetro
	 * @param i
	 * @param medResi
	 * @return
	 */
	private static String[] getDiasConcretosMes(String diasMesConcretos) {
		        String[] numeros = null;
		        String delimitador = null;
		        if (diasMesConcretos!=null && !diasMesConcretos.equalsIgnoreCase("") && diasMesConcretos.contains(",")) { 			
		        	numeros = diasMesConcretos.split(",");  delimitador = ",";
		        } else if (diasMesConcretos!=null && !diasMesConcretos.equalsIgnoreCase("") && diasMesConcretos.contains(";")) { 	    
		        	numeros = diasMesConcretos.split(";");  delimitador = ";";
		        } else if (diasMesConcretos!=null && !diasMesConcretos.equalsIgnoreCase("") && diasMesConcretos.contains(" ")) {     
		        	numeros = diasMesConcretos.split(" ");  delimitador = " ";
		        } else if (diasMesConcretos!=null && !diasMesConcretos.equalsIgnoreCase("") && diasMesConcretos.contains("/")) {     
		        	numeros = diasMesConcretos.split("/");  delimitador = "/";
		        } else if (diasMesConcretos!=null && !diasMesConcretos.equalsIgnoreCase("") && diasMesConcretos.contains("|")) {     
		        	numeros = diasMesConcretos.split("|");  delimitador = "|";
		        } else if (diasMesConcretos!=null && !diasMesConcretos.equalsIgnoreCase("") ) { 			
	                return numeros = new String[]{diasMesConcretos};
	        }
		        return numeros;
	}

	
	/**
	 * Método encargado de devolver el número de una cadena, segñun la posición indicada por parámetro
	 * @param i
	 * @param medResi
	 * @return
	 */
	private static int getDiasConcretosMesPorPosicion(String diasMesConcretos, int posicion) {
        int result =0;
    	String[] numeros = getDiasConcretosMes(diasMesConcretos);
		try
		{
			if (numeros!=null && posicion >= 1 && posicion <= numeros.length) 
				result= Integer.parseInt(numeros[posicion - 1]);
		}
		catch(Exception e)
		{}
		return result;
	}

/**
 * obtenerFechaDesde utiliza una expresión regular simple \\d{8} para encontrar un grupo de 8 dígitos que corresponde a la fecha fechaDesde.
 * @param idProceso
 * @return
 */
	public static String obtenerFechaDesde(String idProceso) {
		Pattern pattern = Pattern.compile("\\d{8}");
        Matcher matcher = pattern.matcher(idProceso);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }
	/**La función obtenerFechaHasta utiliza una expresión regular \\d{8}_(\\d{8})_ para encontrar el grupo de 8 dígitos que corresponde a la fecha fechaHasta dentro de la cadena.
	 * 
	 * @param idProceso
	 * @return
	 */
    public static String obtenerFechaHasta(String idProceso) {
        Pattern pattern = Pattern.compile("\\d{8}_(\\d{8})_");
        Matcher matcher = pattern.matcher(idProceso);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
    
    public static String obtenerFechaEnRango(String fechaDesde, String fechaHasta, int diaMes) {
        LocalDate localFechaDesde = LocalDate.parse(fechaDesde, DateTimeFormatter.ofPattern("yyyyMMdd"));
        LocalDate localFechaHasta = LocalDate.parse(fechaHasta, DateTimeFormatter.ofPattern("yyyyMMdd"));
        
        for (LocalDate fecha = localFechaDesde; !fecha.isAfter(localFechaHasta); fecha = fecha.plusDays(1)) {
        	//System.out.println("fecha " + fecha);
            if (fecha.getDayOfMonth() == diaMes) {
            	//System.out.println("fecha ok " + fecha);
                return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            }
        }
        
        return null;
    }
    
    public static boolean verificarEnRango(String fechaDesde, String fechaHasta, String fechaVerificar) {
        // Convertir las fechas a objetos LocalDate
    	LocalDate desde = null;
    	LocalDate hasta = null;
    	LocalDate verificar = null;	
        try{
        	desde = LocalDate.parse(fechaDesde, DateTimeFormatter.ofPattern("yyyyMMdd"));
            hasta = LocalDate.parse(fechaHasta, DateTimeFormatter.ofPattern("yyyyMMdd"));
            verificar = LocalDate.parse(fechaVerificar, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        catch(Exception e) {return false;}
 
        // Verificar si la fecha a verificar está dentro del rango
        return verificar.isAfter(desde.minusDays(1)) && verificar.isBefore(hasta.plusDays(1));
         //  return !verificar.isBefore(desde) && !verificar.isAfter(hasta);
    }
    
    public static int obtenerDia(String fechaString) {
        // Convertir la cadena de fecha en un objeto LocalDate
        LocalDate fecha = LocalDate.parse(fechaString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        // Obtener el día del mes
        return fecha.getDayOfMonth();
    } 
    
    public static long obtenerDiasProduccionSPD(String fechaDesde, String fechaHasta) {
        LocalDate localFechaDesde = LocalDate.parse(fechaDesde, DateTimeFormatter.ofPattern("yyyyMMdd"));
        LocalDate localFechaHasta = LocalDate.parse(fechaHasta, DateTimeFormatter.ofPattern("yyyyMMdd")).plusDays(1);  //sumamos uno para que incluya el fechaHasta

        long diasEntreFechas = ChronoUnit.DAYS.between(localFechaDesde, localFechaHasta);

        return  diasEntreFechas;
    }
    
    

	public static void detectarDiasMarcados(FicheroResiBean medResi) {
		List<String> dias=new ArrayList<String>();
		medResi.setDiasSemanaConcretos(""); //inicializamos
		if(HelperSPD2.diaMarcado(medResi.getResiD1())) dias.add(SPDConstants.DIA1_HELIUM);
		if(HelperSPD2.diaMarcado(medResi.getResiD2())) dias.add(SPDConstants.DIA2_HELIUM);
		if(HelperSPD2.diaMarcado(medResi.getResiD3())) dias.add(SPDConstants.DIA3_HELIUM);
		if(HelperSPD2.diaMarcado(medResi.getResiD4())) dias.add(SPDConstants.DIA4_HELIUM);
		if(HelperSPD2.diaMarcado(medResi.getResiD5())) dias.add(SPDConstants.DIA5_HELIUM);
		if(HelperSPD2.diaMarcado(medResi.getResiD6())) dias.add(SPDConstants.DIA6_HELIUM);
		if(HelperSPD2.diaMarcado(medResi.getResiD7())) dias.add(SPDConstants.DIA7_HELIUM);
		if(dias!=null&&dias.size()>0&&dias.size()<7) 
		medResi.setDiasSemanaConcretos(String.join(",", dias));
		medResi.setDiasConToma(dias.size());
		medResi.setDiasSemanaMarcados(dias.size());
	}

	
	/**
	 * Método que avisa en el campo "validar" en caso que existan comentarios/observaciones y sea un registro no reutilizado.  
	 * @param medResi
	 */
	public static void chequeoRevisionAlta(FicheroResiBean medResi) {
		System.out.println("INIT chequeoRevisionAlta");
		String obs = medResi.getResiObservaciones();
		String com = medResi.getResiComentarios();
		String var = medResi.getResiVariante();
		String tipoMed = medResi.getResiTipoMedicacion();
		boolean registroOriginal = (medResi!=null && medResi.getIdEstado()!=null && medResi.getIdEstado().equalsIgnoreCase(SPDConstants.REGISTRO_ORIGINAL)?true:false);
		boolean pastillero = (medResi.getSpdAccionBolsa()!=null && medResi.getSpdAccionBolsa().equalsIgnoreCase(SPDConstants.SPD_ACCIONBOLSA_PASTILLERO)?true:false);
		boolean noPintar = (medResi.getSpdAccionBolsa()!=null && medResi.getSpdAccionBolsa().equalsIgnoreCase(SPDConstants.SPD_ACCIONBOLSA_NO_PINTAR)?true:false);
		boolean siPrecisa = (medResi.getSpdAccionBolsa()!=null && medResi.getSpdAccionBolsa().equalsIgnoreCase(SPDConstants.SPD_ACCIONBOLSA_SI_PRECISA)?true:false);
		
		if(registroOriginal && !noPintar && !siPrecisa
			&&  ((obs!=null  && !obs.equals("")) || (com!=null  && !com.equals("")) || (var!=null  && !var.equals(""))))
		{
			medResi.setValidar(SPDConstants.REGISTRO_VALIDAR);
			medResi.setConfirmar(SPDConstants.REGISTRO_CONFIRMAR);
			if(isExcelDateFormat(obs)) 
				medResi.setMensajesInfo(medResi.getMensajesInfo() + " " + SPDConstants.AVISO_REVISA_VALOR + " (en observaciones) " + obs );
			if(isExcelDateFormat(com)) 
				medResi.setMensajesInfo(medResi.getMensajesInfo() + " " +  SPDConstants.AVISO_REVISA_VALOR + " (en comentarios) " + com  );
		}
		if(medResi.getSpdCnFinal()==null || medResi.getSpdCnFinal().equals("") )
		{
			medResi.setValidar(SPDConstants.REGISTRO_VALIDAR);
			medResi.setConfirmar(SPDConstants.REGISTRO_CONFIRMAR);
			medResi.setIncidencia("SI");
		}

		//if(medResi!=null && medResi.getIdEstado()!=null && medResi.getIdEstado().equalsIgnoreCase(SPDConstants.REGISTRO_ORIGINAL) 
		//if(registroOriginal && pastillero && tipoMed!=null  && StringUtil.limpiarTextoTomas(tipoMed).contains("FUERABLISTER"))
		if(tipoMed!=null  && StringUtil.limpiarTextoTomas(tipoMed).contains("FUERABLISTER"))
		{
			//medResi.setSpdAccionBolsa(SPDConstants.SPD_ACCIONBOLSA_SOLO_INFO); 2024/03/06 Informa Valls que se descartan
			//medResi.setRevisar(SPDConstants.REGISTRO_VALIDAR);
			medResi.setSpdAccionBolsa(SPDConstants.SPD_ACCIONBOLSA_NO_PINTAR);
			medResi.setValidar("");
			medResi.setConfirmar("");

		}
		System.out.println("FIN chequeoRevisionAlta");

		
	}
	public static void chequeoRevisionConfirmacion(FicheroResiBean medResi) {
		medResi.setValidar("");
		String obs = medResi.getResiObservaciones();
		String com = medResi.getResiComentarios();
		String var = medResi.getResiVariante();
		String tipoMed = medResi.getResiTipoMedicacion();
		boolean registroOriginal = medResi!=null && medResi.getIdEstado()!=null && medResi.getIdEstado().equalsIgnoreCase(SPDConstants.REGISTRO_ORIGINAL);
		boolean registroRobot = medResi.getSpdAccionBolsa()!=null && !medResi.getSpdAccionBolsa().equalsIgnoreCase(SPDConstants.SPD_ACCIONBOLSA_NO_PINTAR) && !medResi.getSpdAccionBolsa().equalsIgnoreCase(SPDConstants.SPD_ACCIONBOLSA_SI_PRECISA);
		boolean registroRobotPastillero = medResi.getSpdAccionBolsa()!=null && medResi.getSpdAccionBolsa().equalsIgnoreCase(SPDConstants.SPD_ACCIONBOLSA_PASTILLERO);
		
		if(registroOriginal && registroRobot && ((obs!=null  && !obs.equals("")) || (com!=null  && !com.equals(""))|| (var!=null  && !var.equals(""))))
		{
			medResi.setValidar(SPDConstants.REGISTRO_VALIDAR);
			medResi.setConfirmar(SPDConstants.REGISTRO_CONFIRMAR);
			
		}
		if(medResi.getSpdCnFinal()==null || medResi.getSpdCnFinal().equals(""))
		{
			medResi.setValidar(SPDConstants.REGISTRO_VALIDAR);
			medResi.setConfirmar(SPDConstants.REGISTRO_CONFIRMAR);
			
			medResi.setIncidencia("SI");
		}
		
		//if(medResi!=null && medResi.getIdEstado()!=null && medResi.getIdEstado().equalsIgnoreCase(SPDConstants.REGISTRO_ORIGINAL) 
		//if(medResi!=null && registroRobotPastillero &&  tipoMed!=null  && StringUtil.limpiarTextoTomas(tipoMed).contains("FUERABLISTER"))
		if(tipoMed!=null  && StringUtil.limpiarTextoTomas(tipoMed).contains("FUERABLISTER"))
		{
			medResi.setSpdAccionBolsa(SPDConstants.SPD_ACCIONBOLSA_NO_PINTAR);
			medResi.setValidar(SPDConstants.REGISTRO_VALIDAR);
			medResi.setConfirmar(SPDConstants.REGISTRO_CONFIRMAR);
			
		}
		
	}

    /**
     * Detectamos el tipo de envío Helium	
		0 --> Otros (no_pintar, Si_precisa, pauta 0,1)
		1 --> Diario
		2 --> Días concretos
		3 --> Frecuencia quincenal o mensual
		4 --> Envío guide
     * @param medResi
     * @throws ClassNotFoundException 
     */
	public static void detectarTipoEnvioHeliumLite(FicheroResiBean medResi) throws ClassNotFoundException {
		boolean registroNoRobot = medResi.getSpdAccionBolsa()!=null && (medResi.getSpdAccionBolsa().equals(SPDConstants.SPD_ACCIONBOLSA_NO_PINTAR) || medResi.getSpdAccionBolsa().equalsIgnoreCase(SPDConstants.SPD_ACCIONBOLSA_SI_PRECISA));

		//Tipo 0 --> Otros (no_pintar, Si_precisa, pauta 0,1)
		if(registroNoRobot)
		{
			medResi.setValidar("");
			medResi.setTipoEnvioHelium("0");
		}
		else 
		{
			// Tipo 2 --> Días concretos
			if(medResi.getResiPeriodo().equalsIgnoreCase(SPDConstants.SPD_PERIODO_DIAS_SEMANA_CONCRETOS))
			{
				medResi.setTipoEnvioHelium("2");
			}
			else if(medResi.getResiPeriodo().equalsIgnoreCase(SPDConstants.SPD_PERIODO_SEMANAL))
			{
				medResi.setTipoEnvioHelium("2");
			}
			// Tipo 3 --> Frecuencia quincenal o mensual
			else if(medResi.getResiPeriodo().equalsIgnoreCase(SPDConstants.SPD_PERIODO_QUINCENAL) || medResi.getResiPeriodo().equalsIgnoreCase(SPDConstants.SPD_PERIODO_MENSUAL) )
			{
				medResi.setTipoEnvioHelium("3");
				//el período ya viene marcado anteriormente
			}
			//Tipo 1 --> Diario 
			else if(medResi.getResiPeriodo().equalsIgnoreCase(SPDConstants.SPD_PERIODO_DIARIO))
			{
				medResi.setTipoEnvioHelium("1");
				medResi.setResiInicioTratamientoParaSPD(medResi.getResiInicioTratamiento());
				medResi.setResiFinTratamientoParaSPD(medResi.getResiFinTratamiento());
			}
			//Tipo 4 --> Envío guide (siempre se revisará si no existe Guide)
			else //if(medResi.getResiPeriodo().equalsIgnoreCase("especial") || medResi.getResiPeriodo().equalsIgnoreCase("especial"))
			{
				medResi.setTipoEnvioHelium("4");
				if(medResi.getSecuenciaGuide().equalsIgnoreCase(""))
				{
					medResi.setValidar(SPDConstants.REGISTRO_VALIDAR);
					medResi.setConfirmar(SPDConstants.REGISTRO_CONFIRMAR);
					
					medResi.setMensajesAlerta(medResi.getMensajesAlerta() + SPDConstants.ALERTA_REVISION_TIPOHELIUM);
				}

			}
		}
		
	}

		
	
	
	public static void actualizarBeanConFormulario(FicheroResiBean frb, FicheroResiForm formulari) throws ClassNotFoundException, SQLException {
		if(frb!=null){
			String cabecera ="| en "+frb.getResiMedicamento()+" |" ;
			String cambios ="" ;
			if(existenDiferencias(frb.getResiCIP(), formulari.getResiCIP())){ 
				cambios+="CIP: " + frb.getResiCIP() + "  cambiado a: " + formulari.getResiCIP() + " | "; 
				frb.setResiCIP(formulari.getResiCIP());
			}
			if(existenDiferencias(frb.getResiCn(), formulari.getResiCn())) 
			{
				cambios+="ResiCN: " + frb.getResiCn() + "  cambiado a: " + formulari.getResiCn() + " | "; 
				frb.setResiCn(formulari.getResiCn());

			}
			if(existenDiferencias(frb.getResiMedicamento(), formulari.getResiMedicamento())) 
			{
				cambios+="ResiMedicamento: " + frb.getResiMedicamento() + "  cambiado a: " + formulari.getResiMedicamento() + " | "; 
				frb.setResiMedicamento(formulari.getResiMedicamento());
			}
			if(existenDiferencias(frb.getResiObservaciones(), formulari.getResiObservaciones()))
			{
				cambios+="ResiObservaciones: " + frb.getResiObservaciones() + "  cambiado a: " + formulari.getResiObservaciones() + " | "; 
				frb.setResiObservaciones(formulari.getResiObservaciones());
			}
			if(existenDiferencias(frb.getResiComentarios(), formulari.getResiComentarios())) 
			{
				cambios+="ResiComentarios: " + frb.getResiComentarios() + "  cambiado a: " + formulari.getResiComentarios() + " | "; 
				frb.setResiComentarios(formulari.getResiComentarios());
			}
			if(existenDiferencias(frb.getResiVariante(), formulari.getResiVariante())) 
			{
				cambios+="ResiVariante: " + frb.getResiVariante() + "  cambiado a: " + formulari.getResiVariante() + " | "; 
				frb.setResiVariante(formulari.getResiVariante());
			}				
			if(existenDiferencias(frb.getResiTipoMedicacion(), formulari.getResiTipoMedicacion())) 
			{
				cambios+="ResiTipoMedicacion: " + frb.getResiTipoMedicacion() + "  cambiado a: " + formulari.getResiTipoMedicacion() + " | "; 
				frb.setResiTipoMedicacion(formulari.getResiTipoMedicacion());
			}				
			if(existenDiferencias(frb.getResiViaAdministracion(), formulari.getResiViaAdministracion())) 
			{
				cambios+="ResiViaAdministracion: " + frb.getResiViaAdministracion() + "  cambiado a: " + formulari.getResiViaAdministracion() + " | "; 
				frb.setResiViaAdministracion(formulari.getResiViaAdministracion());
			}				
			if(existenDiferencias(frb.getResiInicioTratamiento(), formulari.getResiInicioTratamiento())) 
			{
				if(DateUtilities.isDateValid(formulari.getResiInicioTratamiento(), "dd/MM/yyyy"))
				{
					cambios+="ResiInicioTratamiento: " + frb.getResiInicioTratamiento() + "  cambiado a: " + formulari.getResiInicioTratamiento() + " | "; 
					frb.setResiInicioTratamiento(formulari.getResiInicioTratamiento());
				}
				else
				{
					cambios+="ResiInicioTratamiento: " + frb.getResiInicioTratamiento() + " - nuevo --> fecha vacía| "; 
					frb.setMensajesAlerta(SPDConstants.ALERTA_REVISION_FECHAINICIO);
					frb.setIncidencia("SI");
				}
			}
//			System.out.println("-"+formulari.getResiFinTratamiento() + "-formulari");
//			System.out.println("-"+frb.getResiFinTratamiento()+"-frb");
			boolean difs=existenDiferencias(frb.getResiFinTratamiento(), formulari.getResiFinTratamiento());
			if(difs) 
			{
				if(DateUtilities.isDateValid(formulari.getResiFinTratamiento(), "dd/MM/yyyy"))
				{
					cambios+="ResiFinTratamiento: " + frb.getResiFinTratamiento() + "  cambiado a: " + formulari.getResiFinTratamiento() + " | "; 
					frb.setResiFinTratamiento(formulari.getResiFinTratamiento());
				}
				else
				{
					cambios+="ResiFinTratamiento: " + frb.getResiInicioTratamiento() + " - nuevo --> fecha vacía | "; 
					frb.setMensajesAlerta(SPDConstants.ALERTA_REVISION_FECHAFIN);
					frb.setIncidencia("SI");
				}
			}

			if(existenDiferencias(frb.getResiInicioTratamientoParaSPD(), formulari.getResiInicioTratamientoParaSPD())) 
			{
				cambios+="ResiInicioTratamientoParaSPD: " + frb.getResiInicioTratamientoParaSPD() + "  cambiado a: " + formulari.getResiInicioTratamientoParaSPD() + " | "; 
				frb.setResiInicioTratamientoParaSPD(formulari.getResiInicioTratamientoParaSPD());
			}				
			if(existenDiferencias(frb.getResiFinTratamientoParaSPD(), formulari.getResiFinTratamientoParaSPD())) 
			{
				cambios+="ResiFinTratamientoParaSPD: " + frb.getResiFinTratamientoParaSPD() + "  cambiado a: " + formulari.getResiFinTratamientoParaSPD() + " | "; 
				frb.setResiFinTratamientoParaSPD(formulari.getResiFinTratamientoParaSPD());
			}				
			
			boolean cambiadoASiPrecisa=false;
			if(existenDiferencias(frb.getResiSiPrecisa(), formulari.getResiSiPrecisa())) {
				
				frb.setResiSiPrecisa(formulari.getResiSiPrecisa());
				//control del check SI PRECISA con el desplegable de la acción en bolsa
				if(frb.getResiSiPrecisa()!=null && frb.getResiSiPrecisa().equalsIgnoreCase("X")) 
				{
					cambios+="ResiSiPrecisa: " + frb.getResiSiPrecisa() + "  cambiado a: " + formulari.getResiSiPrecisa() + " | "; 
					cambiadoASiPrecisa=true;
					frb.setSpdAccionBolsa(SPDConstants.SPD_ACCIONBOLSA_SI_PRECISA);
				}
				
				
			}
			/* Este campo no lo cambia el usuario
			if(!Objects.equals(frb.getResiFrecuencia(), formulari.getResiFrecuencia())) 
			{
				cambios+="ResiFrecuencia: " + frb.getResiFrecuencia() + "  cambiado a: " + formulari.getResiFrecuencia() + " | "; 
				frb.setResiFrecuencia(formulari.getResiFrecuencia());
			}
			*/
			if(existenDiferencias(frb.getResiPeriodo(), formulari.getResiPeriodo()))
			{
				cambios+="ResiPeriodo: " + frb.getResiPeriodo() + "  cambiado a: " + formulari.getResiPeriodo() + " | "; 
				frb.setResiPeriodo(formulari.getResiPeriodo());
			}
			
			
			if(existenDiferencias(frb.getDiasMesConcretos(), formulari.getDiasMesConcretos()))
			{
				cambios+="DiasMesConcretos: " + frb.getDiasMesConcretos() + "  cambiado a: " + formulari.getDiasMesConcretos() + " | "; 
				frb.setDiasMesConcretos(formulari.getDiasMesConcretos());
			}
			if(existenDiferencias(frb.getSecuenciaGuide(), formulari.getSecuenciaGuide())) 
			{
				cambios+="SecuenciaGuide: " + frb.getSecuenciaGuide() + "  cambiado a: " + formulari.getSecuenciaGuide() + " | "; 
				frb.setSecuenciaGuide(formulari.getSecuenciaGuide());
			}
		//	if(existenDiferencias(frb.getTipoEnvioHelium(), formulari.getTipoEnvioHelium())) frb.setTipoEnvioHelium(formulari.getTipoEnvioHelium());
			if(existenDiferencias(frb.getSpdCnFinal(), formulari.getSpdCnFinal()))
			{
				cambios+="SpdCnFinal: " + frb.getSpdCnFinal() + "  cambiado a: " + formulari.getSpdCnFinal() + " | "; 
				frb.setSpdCnFinal(formulari.getSpdCnFinal());
				BdConsejo bdc = BdConsejoDAO.getBdConsejobyCN(formulari.getSpdCnFinal());
				if(bdc!=null){
					frb.setSpdFormaMedicacion(bdc.getNombreFormaFarmaceutica());
				}
			}
			if(existenDiferencias(frb.getSpdNombreBolsa(), formulari.getSpdNombreBolsa()))
			{
				cambios+="SpdNombreBolsa: " + frb.getSpdNombreBolsa() + "  cambiado a: " + formulari.getSpdNombreBolsa() + " | "; 
				frb.setSpdNombreBolsa(formulari.getSpdNombreBolsa());
			}

			if(!cambiadoASiPrecisa && existenDiferencias(frb.getSpdAccionBolsa(), formulari.getSpdAccionBolsa())) 
			{
				cambios+="SpdAccionBolsa: " + frb.getSpdAccionBolsa() + "  cambiado a: " + formulari.getSpdAccionBolsa() + " | "; 
				frb.setSpdAccionBolsa(formulari.getSpdAccionBolsa());

				//control del check SI PRECISA con el desplegable de la acción en bolsa
				if(frb.getSpdAccionBolsa()!=null && !frb.getSpdAccionBolsa().isEmpty() && frb.getSpdAccionBolsa().equalsIgnoreCase(SPDConstants.SPD_ACCIONBOLSA_SI_PRECISA)
						&& !frb.getResiSiPrecisa().equalsIgnoreCase("X")) 
				{
					cambios+=" Como consecuencia del SpdAccionBolsa a SI PRECISA, se añade la X a SI_PRECISA | "; 
					frb.setResiSiPrecisa("X");
				}
				else frb.setResiSiPrecisa("");
			}

				if(!frb.getResiPeriodo().equals(SPDConstants.SPD_PERIODO_DIARIO))  //en caso que sea diario no se permiten cambios
				{
					for (int i = 1; i <= 7; i++) {
					    try {
					        // Obtén el método getResiToma correspondiente
					        Method getMethod = frb.getClass().getMethod("getResiD" + i);
					        Method setMethod = frb.getClass().getMethod("setResiD" + i, String.class);
					        
					        // Obtén el valor de frb y formulari para la toma actual
					        String frbValue = (String) getMethod.invoke(frb);
					        String formulariValue = (String) formulari.getClass().getMethod("getResiD" + i).invoke(formulari);
					        String text="" ;
					        // Comprueba si hay diferencias y actualiza
					        if (existenDiferencias(frbValue, formulariValue)) {
					            cambios += "Día " +i + ":  Cambiado de ''" + frbValue + "'' a: ''" + formulariValue + "'' | ";
					            setMethod.invoke(frb, formulariValue); // Establece el nuevo valor en frb
					        }
					    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
					        e.printStackTrace();
					    }
					}	
				}
				
			List listaTomas = formulari.getListaTomasCabecera();
			
			for (int i = 1; i <= 24; i++) {
			    try {
			        // Obtén el método getResiToma correspondiente
			        Method getMethod = frb.getClass().getMethod("getResiToma" + i);
			        Method setMethod = frb.getClass().getMethod("setResiToma" + i, String.class);
			        
			        // Obtén el valor de frb y formulari para la toma actual
			        String frbValue = (String) getMethod.invoke(frb);
			        String formulariValue = (String) formulari.getClass().getMethod("getResiToma" + i).invoke(formulari);
			        String text="" ;
			        // Comprueba si hay diferencias y actualiza
			        if (existenDiferencias(frbValue, formulariValue)) {
			        	try{
			        		lopicost.spd.helium.model.Dose toma = (lopicost.spd.helium.model.Dose)listaTomas.get(i-1);
			        		text=toma.getNombreDose();
			        	}catch(Exception e){text="T"+i ;}
			            cambios += text + ":  Cambiado de ''" + frbValue + "'' a: ''" + formulariValue + "'' | ";
			            setMethod.invoke(frb, formulariValue); // Establece el nuevo valor en frb
			        }
			    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			        e.printStackTrace();
			    }
			}
	
			boolean hayAsteriscos= hayNumerosAsteriscos(frb);
			if(existenDiferencias(frb.getMensajesInfo(), formulari.getMensajesInfo()))
			{
				cambios+="MensajesInfo: " + frb.getMensajesInfo() + "  cambiado a: " + formulari.getMensajesInfo() + " | "; 
				frb.setMensajesInfo(formulari.getMensajesInfo());
			}
			if(existenDiferencias(frb.getMensajesResidencia(), formulari.getMensajesResidencia()))
			{
				cambios+="MensajesResidencia: " + frb.getMensajesResidencia() + "  cambiado a: " + formulari.getMensajesResidencia() + " | "; 
				frb.setMensajesResidencia(formulari.getMensajesResidencia());
		}
			if(!cambios.equals(""))
			frb.setFree3(cabecera + " " + cambios); //guardamos los mensajes para el log
			else frb.setFree3(null);
			
		}
		frb.setDiasConToma(getDiasMarcados(frb));
		frb.setDiasSemanaMarcados(getDiasMarcados(frb));
	}


	public static boolean hayNumerosAsteriscos(FicheroResiBean medResi) {
		
        int numTomas = 24; // Número total de tomas
        Class<?> tratClass = medResi.getClass();
        medResi.setAsteriscos("NO");
        try {
            for (int toma = 1; toma <= numTomas; toma++) {
               
                Method method = tratClass.getMethod("getResiToma" + toma);
                Object value = method.invoke(medResi);
                
                if(value!=null && DataUtil.isNumero((String) value) && value !=null && value.equals("999")) 
                {
                	medResi.setAsteriscos("SI");
                	return true;
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
           // e.printStackTrace(); // Manejo de excepciones, ajusta según tus necesidades
            return false;
        }
        
        
		return false;
	}


	private static boolean existenDiferencias(String a, String b) {
		boolean result =false;
		if(a==null || a.equalsIgnoreCase("")) a="";
		if(b==null || b.equalsIgnoreCase("")) b="";
		//return (a!=null && b!=null && !a.equalsIgnoreCase("") && !b.equalsIgnoreCase("") && a!=b ); 
		//result =(a!=b );
		result = !Objects.equals(a, b);
		return result;
		
	}


	public static void gestionVisibilidadCampos(CamposPantallaBean camposPantallaBean, FicheroResiBean c) 
	{
		try
		{
			if(c!=null && c.getNumeroDeTomas()>0)
			{
				 if(c.getNumeroDeTomas()>=1) camposPantallaBean.setVisibleToma1(true);
				 if(c.getNumeroDeTomas()>=2) camposPantallaBean.setVisibleToma2(true);
				 if(c.getNumeroDeTomas()>=3) camposPantallaBean.setVisibleToma3(true);
				 if(c.getNumeroDeTomas()>=4) camposPantallaBean.setVisibleToma4(true);
				 if(c.getNumeroDeTomas()>=5) camposPantallaBean.setVisibleToma5(true);
				 if(c.getNumeroDeTomas()>=6) camposPantallaBean.setVisibleToma6(true);
				 if(c.getNumeroDeTomas()>=7) camposPantallaBean.setVisibleToma7(true);
				 if(c.getNumeroDeTomas()>=8) camposPantallaBean.setVisibleToma8(true);
				 if(c.getNumeroDeTomas()>=9) camposPantallaBean.setVisibleToma9(true);
				 if(c.getNumeroDeTomas()>=10) camposPantallaBean.setVisibleToma10(true);
				 if(c.getNumeroDeTomas()>=11) camposPantallaBean.setVisibleToma11(true);
				 if(c.getNumeroDeTomas()>=12) camposPantallaBean.setVisibleToma12(true);
				 if(c.getNumeroDeTomas()>=13) camposPantallaBean.setVisibleToma13(true);
				 if(c.getNumeroDeTomas()>=14) camposPantallaBean.setVisibleToma14(true);
				 if(c.getNumeroDeTomas()>=15) camposPantallaBean.setVisibleToma15(true);
				 if(c.getNumeroDeTomas()>=16) camposPantallaBean.setVisibleToma16(true);
				 if(c.getNumeroDeTomas()>=17) camposPantallaBean.setVisibleToma17(true);
				 if(c.getNumeroDeTomas()>=18) camposPantallaBean.setVisibleToma18(true);
				 if(c.getNumeroDeTomas()>=19) camposPantallaBean.setVisibleToma19(true);
				 if(c.getNumeroDeTomas()>=20) camposPantallaBean.setVisibleToma20(true);
				 if(c.getNumeroDeTomas()>=21) camposPantallaBean.setVisibleToma21(true);
				 if(c.getNumeroDeTomas()>=22) camposPantallaBean.setVisibleToma22(true);
				 if(c.getNumeroDeTomas()>=23) camposPantallaBean.setVisibleToma23(true);
				 if(c.getNumeroDeTomas()>=24) camposPantallaBean.setVisibleToma24(true);
			}
		}
		catch(Exception e)
		{}
		 
	}

	public static void controlAlertasRegistro(FicheroResiBean medResi) throws ClassNotFoundException {
	   	System.out.println("----- controlAlertasRegistro INIT --");
		boolean registroOriginal = (medResi!=null && medResi.getIdEstado()!=null && medResi.getIdEstado().equalsIgnoreCase(SPDConstants.REGISTRO_ORIGINAL)?true:false);
		
		medResi.setMensajesAlerta("");  //inicializamos alertas para no sobreescribir alertas duplicadas
		
		//if(medResi.getResiCIP()==null || medResi.getResiCIP().equals(""))//Lo elimino porque posteriormente se deja aviso en residencia  
    	//	medResi.setMensajesAlerta(SPDConstants.INFO_RESI_SIN_CIP_AVISO);
    		

		if(medResi.getResiCn()==null || medResi.getResiCn().isEmpty()|| medResi.getResiCn().equals("")|| medResi.getResiCn().equals("0"))
		{
			medResi.setMensajesAlerta(medResi.getMensajesAlerta() + SPDConstants.ALERTA_NO_CODIGO);
			medResi.setResiCn(StringUtil.limpiarTextoyEspacios(medResi.getResiMedicamento())); //se añade como CN la descripción para poder realizar la consulta
		}
		//medResi.setResiInicioTratamientoParaSPD(medResi.getResiInicioTratamiento());
		//medResi.setResiFinTratamientoParaSPD(medResi.getResiFinTratamiento());
		
		if(medResi.getResiInicioTratamientoParaSPD()==null)
			medResi.setResiInicioTratamientoParaSPD(medResi.getResiInicioTratamiento());
		if(medResi.getResiFinTratamientoParaSPD()==null)
			medResi.setResiFinTratamientoParaSPD(medResi.getResiFinTratamiento());
		
		
		if(medResi.getResiInicioTratamiento()==null || medResi.getResiInicioTratamiento().equals("") )	//ok
			medResi.setMensajesAlerta(medResi.getMensajesAlerta() + SPDConstants.ALERTA_REVISION_FECHAINICIO );
		

    	int diasSemanaMarcados=HelperSPD2.getDiasMarcados(medResi);
    	medResi.setDiasSemanaMarcados(diasSemanaMarcados);
    	
    	//medResi.setIdTratamientoCIP(HelperSPD.getID(medResi));
    	medResi.setFechaDesde(HelperSPD2.obtenerFechaDesde(medResi.getIdProceso()));  
    	medResi.setFechaHasta(HelperSPD2.obtenerFechaHasta(medResi.getIdProceso())); 


    	//20220720 - TO-DO 	pendiente para cuando se automaticen las sustituciones
    	//if(medResi.getResiCn()!=null && !medResi.getResiCn().equals("") )
		//	GestSustitucionesDAO.buscaSustitucionBiblia(medResi);
    	
    	if(medResi.getSpdCnFinal()==null || medResi.getSpdCnFinal().isEmpty()|| medResi.getSpdCnFinal().equals("")|| medResi.getSpdCnFinal().equals("0"))
		{
    		medResi.setMensajesAlerta(medResi.getMensajesAlerta() + SPDConstants.ALERTA_NO_SUSTITUCION );
    		
		}
    	if(medResi.getMensajesAlerta()!=null && !medResi.getMensajesAlerta().equals(""))
    		medResi.setIncidencia("SI");
    	
    	//en caso que tenga 999 y PASTILLERO se pone como SOLO_INFO y se añade mensaje 
    	if(medResi.getAsteriscos()!=null && medResi.getAsteriscos().equalsIgnoreCase("SI")&& medResi.getSpdAccionBolsa()!=null && medResi.getSpdAccionBolsa().equalsIgnoreCase(SPDConstants.SPD_ACCIONBOLSA_PASTILLERO))
    	{
    		medResi.setSpdAccionBolsa(SPDConstants.SPD_ACCIONBOLSA_SOLO_INFO);
    		String mensaje = SPDConstants.INFO_INTERNA_REVISION_SUSTITUCION;
    		
    		if((medResi.getMensajesInfo()==null || medResi.getMensajesInfo().equals("")) )
    		{
    			medResi.setMensajesInfo(mensaje);
    		}
    		else
    		{
        		if(!medResi.getMensajesInfo().contains(mensaje))
        		{
	        		if((medResi.getMensajesInfo()==null || medResi.getMensajesInfo().equals("")) )
        				medResi.setMensajesInfo(mensaje);
        		else 
    				medResi.setMensajesInfo(medResi.getMensajesInfo() + " " +  mensaje);
        			
        		}
    		}

    	}
       	if(registroOriginal) //todo registro nuevo se ha de validar y confirmar
    	{
			medResi.setValidar(SPDConstants.REGISTRO_VALIDAR);
			medResi.setConfirmar(SPDConstants.REGISTRO_CONFIRMAR);
	       	
			if(medResi.getSpdAccionBolsa()!=null && medResi.getSpdAccionBolsa().equalsIgnoreCase(SPDConstants.SPD_ACCIONBOLSA_PASTILLERO))
	    	{
	       		float totalcomprimidos=HelperSPD2.getTotalComprimidosDia(medResi, SPDConstants.NUM_TOMAS_SPD);
	    		if(totalcomprimidos>(SPDConstants.CTRL_MAX_COMPRIMIDOS_DIA) && (medResi.getEditado()==null || medResi.getEditado().equalsIgnoreCase("NO"))) //ponemos un límite de pastillas para mostrar un mensaje y validar pauta mientras sea un registro nuevo
	    		{
	    	   		String mensaje =SPDConstants.INFO_INTERNA_REVISION_COMPRIMIDOS + "("+totalcomprimidos+")";
	        		if((medResi.getMensajesInfo()==null || medResi.getMensajesInfo().equals("")) )
	        		{
	        			medResi.setMensajesInfo(mensaje);
	        		}
	        		else
	        		{
	            		if(!medResi.getMensajesInfo().contains(mensaje))
	            		{
	    	        		if((medResi.getMensajesInfo()==null || medResi.getMensajesInfo().equals("")) )
	            				medResi.setMensajesInfo(mensaje);
	            		else 
	        				medResi.setMensajesInfo(medResi.getMensajesInfo() + " / " +  mensaje);
	            		}
	        		}
	    		}
	    	}
    	}
       	
	   	System.out.println("----- controlAlertasRegistro FIN --");
	}

	
	public static float getTotalComprimidosDia(FicheroResiBean medResi, int NUM_TOMAS) {
		// Inicializar la suma
		float suma = 0;
        Class<?> tratClass = medResi.getClass();
        try {
            for (int toma = 1; toma <= NUM_TOMAS; toma++) {
                Method method = tratClass.getMethod("getResiToma" + toma);
                Object value = method.invoke(medResi);
                //if(DataUtil.isNumero((String) value) ) 
                if (value instanceof String && DataUtil.isNumero((String) value)) {
                    String numericValue = ((String) value).replace(",", ".");
                    suma += Float.parseFloat(numericValue);
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            // Manejar las excepciones adecuadamente
            System.err.println("Error al acceder a los métodos de resiToma: " + e.getMessage());
        }

        System.out.println("La suma de las resiTomas es: " + suma);
        return suma;
    }



	/**
	 * Borrado de un registro con el mismo idTratamiento que el que se está cargando.
	 * @param medResi
	 * @throws Exception 
	 */
	public static boolean borrarPosibleDuplicado(String spdUsuario, FicheroResiBean medResi) throws Exception {
	   	System.out.println("----- borrarPosibleDuplicado INIT --");
		if(FicheroResiDetalleDAO.existeRegistro(spdUsuario, medResi.getIdDivisionResidencia(), medResi.getIdProceso(), medResi))
		{
			return true;
		}
		FicheroResiDetalleDAO.borrar(spdUsuario, medResi.getOidFicheroResiCabecera(), medResi.getOidFicheroResiDetalle(), medResi.getIdTratamientoCIP());
	   	System.out.println("----- borrarPosibleDuplicado EXIT --");

		return false;
	}


	public static void desdoblarTratamientosSecuenciales(String spdUsuario, FicheroResiBean medResi, Date fechaInicioCalculos) throws Exception {
		int row=medResi.getRow()+1;
		boolean noPintar = (medResi.getSpdAccionBolsa()!=null && !medResi.getSpdAccionBolsa().equalsIgnoreCase(SPDConstants.SPD_ACCIONBOLSA_NO_PINTAR)?true:false);
		boolean siPrecisa = (medResi.getSpdAccionBolsa()!=null && !medResi.getSpdAccionBolsa().equalsIgnoreCase(SPDConstants.SPD_ACCIONBOLSA_SI_PRECISA)?true:false);
	
		//if(medResi.getTipoEnvioHelium()!=null && medResi.getTipoEnvioHelium().contentEquals(SPDConstants.TIPO_4_GUIDE_HELIUM) && !medResi.getIdEstado().equalsIgnoreCase(SPDConstants.REGISTRO_CREADO_AUTOM_SECUENCIA_GUIDE))
		//if(medResi.getTipoEnvioHelium()!=null && medResi.getTipoEnvioHelium().contentEquals(SPDConstants.TIPO_4_GUIDE_HELIUM) && !medResi.getResiCn().contains(SPDConstants.PREFIJO_REGISTRO_SECUENCIA_GUIDE))
		{
			HelperSPD2.borrarTratamientosSecuencialesPrevios(spdUsuario, medResi);
			
			
	  	    //HelperSPD.borrarTratamientosSecuencialesPrevios(spdUsuario, medResi);

	  	    
			String secuenciaGuide=medResi.getSecuenciaGuide();
			//String sufijo= SPDConstants.PREFIJO_REGISTRO_SECUENCIA_GUIDE + "_"+medResi.getOidFicheroResiDetalle();
			//String sufijo= medResi.getResiCn() + "_" + SPDConstants.PREFIJO_REGISTRO_SECUENCIA_GUIDE ;
			
			// Dividir la cadena usando el guión "-"
	       if(secuenciaGuide!=null && !secuenciaGuide.equals("")) 
	       {
		        // Verificar si se han dividido las partes correctamente
	    	   try{
	 	    	   	String[] partes = secuenciaGuide.split("-");
	 	    	   	int periodosConToma 		= new Integer(partes[0]).intValue(); // primero miramos i=pares (periodoSI  con medicación)
	 	    	   	int periodosConDescanso	= new Integer(partes[1]).intValue(); // Segunda parte (número)
	 	    	   	String periodoGuide = partes[partes.length-1];   //  parte (texto) DIAS, SEMANAS, MESES
			       
	 	    	   	int factorMultiplicador=getFactor(periodoGuide);

	 	    	   	//buscamos el total de días que engloba la secuencia, periodosSi+periodosNo
	 	    	   	int diasTotales=getDiasTotales(partes, factorMultiplicador);

	 	    	 //buscamos fecha inicio tratatmiento SPD, solo en caso que no haya fecha fin SPD. Esto es para poder hacer limpio en un cambio de secuencia editando una que tenga inicio/fin SPD que ya no convenga
	 	    	   if(medResi.getResiInicioTratamientoParaSPD()!=null && !medResi.getResiInicioTratamientoParaSPD().equals("") 
	 	    			   && (medResi.getResiFinTratamientoParaSPD()==null || medResi.getResiFinTratamientoParaSPD().equals("")))
	 	    		  fechaInicioCalculos=DateUtilities.getDate(medResi.getResiInicioTratamientoParaSPD(), "dd/MM/yyyy");
	 	    	   //si no buscamos fecha inicio tratatmiento
	 	    	   else  //if (fechaInicioCalculos==null)  {
	 	    	   		fechaInicioCalculos=DateUtilities.getDate(medResi.getResiInicioTratamiento(), "dd/MM/yyyy");
	 	    	   	//}
	 	    	   	
			       	//buscamos fecha inicio y fin de SPD
			       	Date fechaInicioSPD = DateUtilities.getDate(DateUtilities.getDate(medResi.getFechaDesde(), "yyyyMMdd", "dd/MM/yyyy"), "dd/MM/yyyy");
			       	Date fechaFinSPD = DateUtilities.getDate(DateUtilities.getDate(medResi.getFechaHasta(), "yyyyMMdd", "dd/MM/yyyy"), "dd/MM/yyyy");
		    	   
			       //buscamos la diferencia de días entre fechaInicioTratamiento y fechaInicioSPD
		    	   
			       	int diasDesdeFechaInicioCalculos = (int) DateUtilities.getLengthInDays(fechaInicioCalculos, fechaInicioSPD);
			       	// Realizar la resta hasta que el número sea menor a 10
			       	int diaPrimerDiaSecuencia = diasDesdeFechaInicioCalculos % diasTotales; //dias más cercanos a la fecha actual donde empezaría la secuenciaGuide
			       	Date fechaInicioSecuenciaGuide=DateUtilities.addDate(fechaInicioSPD,  -diaPrimerDiaSecuencia); //buscamos el día de comienzo de la secuencia, mirando atrás desde el inicioSPD
			       	int periodos=partes.length;

					Date fechaSpd=fechaInicioSecuenciaGuide;

					//inicio tratamiento de medResi y de la primera pareja periodoSi, periodoNo
					//Primero ponemos la fecha de inicio de la secuencia en medResi (aunque no esté dentro del periodo). A partir de aquí iremos clonando medResi para añadir en caso que estén dentro del período
					medResi.setResiInicioTratamientoParaSPD(DateUtilities.getDatetoString("dd/MM/yyyy" ,fechaInicioSecuenciaGuide));
					//actualizamos el día de la fechaSPD sumando los perídodos de SI (siempre que sea > 0)
					if(periodosConToma>0)
						fechaSpd=DateUtilities.addDate(fechaSpd, (periodosConToma*factorMultiplicador)-1); //nos situamos en el siguiente día con toma
					medResi.setResiFinTratamientoParaSPD(DateUtilities.getDatetoString("dd/MM/yyyy" ,fechaSpd));
					//actualizamos el día de la fechaSPD sumando los perídodos de NO
					fechaSpd=DateUtilities.addDate(fechaSpd, (periodosConDescanso*factorMultiplicador)+1); //nos situamos en el siguiente día con toma
					medResi.setTipoRegistro(SPDConstants.REGISTRO_LINEA); //ha de ser LINEA
					 
					int diasSi=1;
					while(fechaFinSPD.compareTo(fechaSpd) >= 0 && diasSi<30)
					{
						 FicheroResiBean medResiDiaSi = medResi.clone();
						 medResiDiaSi.setResiInicioTratamientoParaSPD(DateUtilities.getDatetoString("dd/MM/yyyy" ,fechaSpd));
						//actualizamos el día de la fechaSPD sumando los perídodos de SI
						if(periodosConToma>0)
							fechaSpd=DateUtilities.addDate(fechaSpd, (periodosConToma*factorMultiplicador)-1); //nos situamos en el siguiente día con toma
						 medResiDiaSi.setResiFinTratamientoParaSPD(DateUtilities.getDatetoString("dd/MM/yyyy" , fechaSpd));//restamos 1 para no contar el dia inicio
						//actualizamos el día de la fechaSPD sumando los perídodos de NO
						 fechaSpd=DateUtilities.addDate(fechaSpd, (periodosConDescanso*factorMultiplicador)+1); //nos situamos en el siguiente día con toma

						// medResiDiaSi.setMensajesInfo( "Fechas SPD se actualizan al día de la primera secuencia ");
						 medResiDiaSi.setResiCn(medResi.getResiCn()+"_"+SPDConstants.PREFIJO_REGISTRO_SECUENCIA_GUIDE+"_"+ diasSi  +"_"+medResi.getRow()); //cambiamos el CN añadiendo CN_seq_j_row para que no sea el idTramiento idéntico al medResi (row por si hay dos tratamientos iguales, como RESINCALCIO de Ceritania)
						 medResiDiaSi.setIdTratamientoCIP(getID(medResiDiaSi));
						 medResiDiaSi.setIdEstado(SPDConstants.REGISTRO_CREADO_AUTOM_SECUENCIA_GUIDE);
						 medResiDiaSi.setTipoRegistro(SPDConstants.REGISTRO_LINEA_SEC_GUIDE);
						 medResiDiaSi.setEditable(false); 
						 medResiDiaSi.setValidar(""); 
						 medResiDiaSi.setConfirmar(""); 
						 medResiDiaSi.setIncidencia("NO");
						 medResiDiaSi.setRow(row); row++;
						 FicheroResiDetalleDAO.nuevo(medResiDiaSi.getIdDivisionResidencia(), medResiDiaSi.getIdProceso(), medResiDiaSi);
						 diasSi++;
					}
					//CONTROL PASTILLAS - actualizamos la dosis que entra en esta producción para comparar con lo recibido
					float totalDiaResi = HelperSPD2.getTotalComprimidosDia(medResi, SPDConstants.NUM_TOMAS_DEFAULT_RESI);
					float totalDiaSPD = HelperSPD2.getTotalComprimidosDia(medResi, SPDConstants.NUM_TOMAS_SPD);
					medResi.setPrevisionResi( diasSi * totalDiaResi );
					medResi.setPrevisionSPD( diasSi * totalDiaSPD );
					
					//fin del bucle de la primera pareja periodoSi, periodoNo
					
					int j2=1;
			       for(int i=2;i<periodos-1;i++)
			       {
			    	   	FicheroResiBean medResiDiaSi = medResi.clone();
			    	    periodosConToma 	= new Integer(partes[i]).intValue(); // primero miramos i=pares (periodoSI  con medicación)
		 	    	    periodosConDescanso	= new Integer(partes[i]).intValue(); // Segunda parte (número)

						 medResiDiaSi.setResiInicioTratamientoParaSPD(DateUtilities.getDatetoString("dd/MM/yyyy" ,fechaSpd));
							//actualizamos el día de la fechaSPD sumando los perídodos de SI
						 fechaSpd=DateUtilities.addDate(fechaSpd, (periodosConToma*factorMultiplicador)-1); //nos situamos en el siguiente día con toma
						 medResiDiaSi.setResiFinTratamientoParaSPD(DateUtilities.getDatetoString("dd/MM/yyyy" , fechaSpd));
							//actualizamos el día de la fechaSPD sumando los perídodos de NO
						 fechaSpd=DateUtilities.addDate(fechaSpd, (periodosConDescanso*factorMultiplicador)+1); //nos situamos en el siguiente día con toma

						 //medResiDiaSi.setMensajesInfo( "Fechas SPD se actualizan al día del resto de secuencias ");
						 medResiDiaSi.setResiCn(medResiDiaSi.getResiCn()+"_"+SPDConstants.PREFIJO_REGISTRO_SECUENCIA_GUIDE+"_"+i+"_"+j2+"_"+medResi.getRow()); //cambiamos el CN añadiendo CN_seq_j para que no sea el idTramiento idéntico al medResi
						 medResiDiaSi.setIdTratamientoCIP(getID(medResiDiaSi));
						 medResiDiaSi.setIdEstado(SPDConstants.REGISTRO_CREADO_AUTOM_SECUENCIA_GUIDE);
						 medResiDiaSi.setTipoRegistro(SPDConstants.REGISTRO_LINEA_SEC_GUIDE);
						 medResiDiaSi.setValidar(""); 
						 medResiDiaSi.setConfirmar(""); 
						 medResiDiaSi.setIncidencia("NO");
						 medResiDiaSi.setEditable(false); 
						 medResiDiaSi.setRow(row); row++; //influye en el ordenamiento del listado
						 FicheroResiDetalleDAO.nuevo(medResiDiaSi.getIdDivisionResidencia(), medResiDiaSi.getIdProceso(), medResiDiaSi);
						 j2++;
			    	   }
			       }
		        catch(Exception e)
		        {
		        	medResi.setMensajesInfo(medResi.getMensajesInfo() + " " + SPDConstants.INFO_INTERNA_REVISION_SEQGUIDE);
		        }
	       }
		}
	}


	private static int getDiasTotales(String[] partes, int factorMultiplicador) {
	  int diasTotales=0;
		for (int i = 0; i < partes.length - 1; i++) {
	    	    diasTotales += Integer.parseInt(partes[i])*factorMultiplicador;
	    	}
		return diasTotales;
	}


	private static int getFactor(String periodoGuide) {
		int factorMultiplicador=1;
  	   switch (periodoGuide) {
	 	   case SPDConstants.SPD_GUIDE_SEMANA:     		  factorMultiplicador=7;					break;
	 	   case SPDConstants.SPD_GUIDE_SEMANAS:    		  factorMultiplicador=7;					break;
	 	   case SPDConstants.SPD_GUIDE_WEEKS:   		  factorMultiplicador=7;					break;
	 	   case SPDConstants.SPD_GUIDE_MES:	     		  factorMultiplicador=30;					break;
	 	   case SPDConstants.SPD_GUIDE_MESES:	   		  factorMultiplicador=30;					break;
	 	   case SPDConstants.SPD_GUIDE_MONTHS:	   		  factorMultiplicador=30;					break;				
	 	   default:
			factorMultiplicador=1;
		break;
  		}
		return factorMultiplicador;
	}


	public static void borrarTratamientosSecuencialesPrevios(String spdUsuario, FicheroResiBean frbean) throws Exception {

		//if(frbean.getTipoEnvioHelium()!=null && frbean.getTipoEnvioHelium().contentEquals(SPDConstants.TIPO_4_GUIDE_HELIUM) && !frbean.getIdEstado().equalsIgnoreCase(SPDConstants.REGISTRO_CREADO_AUTOM_SECUENCIA_GUIDE))
		//if(!frbean.getIdEstado().equalsIgnoreCase(SPDConstants.REGISTRO_CREADO_AUTOM_SECUENCIA_GUIDE))
		//if(!frbean.getResiCn().contains(SPDConstants.PREFIJO_REGISTRO_SECUENCIA_GUIDE))
		{
			FicheroResiDetalleDAO.borrarHijosSecuenciaGuide(spdUsuario, frbean);
			
		}

			
				
	}

/*
	public static void controlGtvmpCnResiCnSpd(FicheroResiBean medResi) throws ClassNotFoundException, SQLException {
		boolean iguales =true;
		String cnresi=medResi.getResiCn();
		if(cnresi!=null && !cnresi.isEmpty()&& cnresi.length() > 6) 
			cnresi = cnresi.substring(0, 6);
		
		String cnspd=medResi.getSpdCnFinal();
		if(cnspd!=null && !cnspd.isEmpty()&& cnspd.length() > 6) 
			cnspd = cnspd.substring(0, 6);
	        
		BdConsejo cnResi= BdConsejoDAO.getByCN(cnresi);
		BdConsejo cnSpd= BdConsejoDAO.getByCN(cnspd);
		
		String gtvmpResi="";
		String gtvmpSpd="";
		if(cnResi!=null && cnSpd!=null) 
		{
			gtvmpResi=cnResi.getNomGtVmp();
			gtvmpSpd=cnSpd.getNomGtVmp();
		}

		if(gtvmpResi!=null && gtvmpSpd!=null && !gtvmpResi.isEmpty() && !gtvmpSpd.isEmpty() && !gtvmpResi.equalsIgnoreCase(gtvmpSpd))
			iguales=false;
		
		//eliminamos posibles mensajes anteriores, sobre este tipo de mensaje (info2)
		String mensaje=recuperaMensajeActualizado(medResi.getMensajesResidencia(), "1#");
		 System.out.println("iguales" + iguales);
		if(!iguales)
		{
			//preparamos el nuevo info2 y lo añadimos
			String mensajeNuevoInfo1 ="1# -  Corregir Código nacional -  El CN " + medResi.getResiCn() + " pertenece a " + gtvmpResi + ". Si se trata de " + gtvmpSpd + " sugerimos cambiarlo por el CN " + medResi.getSpdCnFinal() + " - 1#";
			mensaje=mensaje + " " +  mensajeNuevoInfo1;
			medResi.setRevisar("SI");
			medResi.setControlDiferentesGtvmp(SPDConstants.CTRL_DIFERENTE_GTVMP_ALERTA);
    		/*
    		 * control del mensaje para no duplicarlo 
    		if((medResi.getMensajesInfo()==null || medResi.getMensajesInfo().equals("")) )
    		{
    			medResi.setMensajesInfo(mensaje);
    		}
    		else
    		{
        		if(!medResi.getMensajesInfo().contains(mensaje))
        		{
	        		if((medResi.getMensajesInfo()==null || medResi.getMensajesInfo().equals("")) )
        				medResi.setMensajesInfo(mensaje);
        		else 
        		{
        			mensaje=recuperaMensajeActualizado(mensaje, "info2");
    				medResi.setMensajesInfo(medResi.getMensajesInfo() + " / " +  mensaje);
        		}
    		}
		} 
		else 
		{
			//mensaje="El CN de la residencia" + gtvmpResi + "  no coincide su GTVMP con el CN de SPD QUETIAPINA 25 MG COMPRIMIDO";
			// quitamos el mensaje en caso que todo se ok y exista previamente 
			if(medResi.getMensajesInfo()!=null && medResi.getMensajesInfo().contains(mensaje))
			{
		  		medResi.setMensajesInfo(medResi.getMensajesInfo().replaceAll(mensaje, ""));
			}
		}
		
		}
		else medResi.setNomGtVmpp(gtvmpSpd);
		//medResi.setMensajesInfo(mensaje);
		medResi.setMensajesResidencia(mensaje);
	}
	*/

	/**
	 * Método que sustituye un texto que empieza y acaba con un separador, en un mensaje determinado. 
	 * @param mensaje
	 * @param separador
	 * @return
	 */
	public static String recuperaMensajeActualizado(String mensaje, String separador) {
       
		try{
			if(mensaje==null || mensaje.equalsIgnoreCase("null")) mensaje="";
			//System.out.println("mensaje: " + mensaje);
			//System.out.println("Pre patron");
		    // Combina el patrón en una expresión regular
		    String patron = Pattern.quote(separador) + "(.*?)" + Pattern.quote(separador);
		    //System.out.println("patron2: " + patron);
		    // Utiliza la expresión regular para encontrar la subcadena
		    // System.out.println("Pre pattern " );
		    Pattern pattern = Pattern.compile(patron);
		    // System.out.println("pattern2: " + pattern);
		    //System.out.println("Pre matcher");
		    Matcher matcher = pattern.matcher(mensaje);
		    // System.out.println("matcher: " + matcher);

		    if (matcher!=null && matcher.find()) {
		    	//System.out.println("matcher: " + matcher);
		        // Obtiene la subcadena encontrada
		        String subcadenaEncontrada = matcher.group();
		        //System.out.println("subcadenaEncontrada: " + subcadenaEncontrada);
		        // Realiza la sustitución
		        String nuevaSubcadena = "";
		        // Realiza el reemplazo en la cadena original
		        mensaje = mensaje.replace(subcadenaEncontrada, nuevaSubcadena);
		    } 
		    System.out.println("mensaje return: " + mensaje);
		}
		catch(Exception e)
		{
			
		}
 
        return mensaje;
	}
	
	
	/**
	 * Método que guarda el cálculo de previsión según la producción del Excel
	 * @param SpdUsuario 
	 * @param row
	 * @return
	 * @throws Exception
	 */
	public static  boolean creaRegistroPrevision(String SpdUsuario, FicheroResiBean fila) throws Exception {
		
			boolean procesosAnterioresLimpiados = false;
			
	      	boolean resultPrevision =true;
	      	boolean result =true;

	    	if(fila.getResiSiPrecisa()!=null && fila.getResiSiPrecisa().equalsIgnoreCase("X")) fila.setResiSiPrecisa("SI_PRECISA");
	    	
	    	//pauta
			//conteo de comprimidos
			int diasSemanaMarcados= 0;
			float comprimidosSemana=0;
	    	float comprimidosDia = 0;
	    	try{
	    		comprimidosDia=new Float(getTotalComprimidosDia(fila, SPDConstants.NUM_TOMAS_SPD)).floatValue();
	    		fila.setComprimidosDia(comprimidosDia);
	    	}
	    	catch(Exception e)
	    	{}

		
			//if(fila.getSpdAccionBolsa()!=null && fila.getSpdAccionBolsa().equalsIgnoreCase("PASTILLERO"))
			//{
			//miramos si tiene alguna periodicidad
			HelperSPD2.getPeriodicidadParaPrevision(fila);
		
			//fila.setDiasSemanaMarcados(HelperSPD.getDiasMarcados(fila));
			
			//Búsqueda de BDConsejo
			BdConsejo bdConsejo=BdConsejoDAO.getByCN(fila.getSpdCnFinal()); 
			if(bdConsejo!=null)
			{
				fila.setBdConsejo(bdConsejo);
				fila.setSpdCodGtVm(bdConsejo.getCodGtVm());
				fila.setSpdNomGtVm(bdConsejo.getNomGtVm());
				fila.setSpdCodGtVmp(bdConsejo.getCodGtVmp());
				fila.setSpdNomGtVmp(bdConsejo.getNomGtVmp());
				fila.setSpdCodGtVmpp(bdConsejo.getCodGtVmpp());
				fila.setSpdNomGtVmpp(bdConsejo.getNomGtVmpp());
				fila.setSpdEmblistable(bdConsejo.getEmblistable());
			}
			
			if(fila.getSpdEmblistable()!=null&&fila.getSpdEmblistable().equalsIgnoreCase("SI") || (fila.getSpdAccionBolsa()!=null && fila.getSpdAccionBolsa().equalsIgnoreCase(SPDConstants.SPD_ACCIONBOLSA_PASTILLERO)))
			{
				fila.setSpdEmblistable("SI");
			}

	    	
	    	//fila.setSpdComentarioLopicost(StringUtil.replaceInvalidChars((String) row.elementAt(15)));	de momento no lo guardamos
	    	
			//no hace falta comprobar si es válido, porque solo se podrá enviar cuando no haya errorres.
	     	//IOSpdApi.compruebaTratamiento(SpdUsuario, fila);
	     	resultPrevision=FicheroMedResiConPrevisionDAO.nuevo(fila);
	 		result=FicheroMedResiDAO.nuevo(fila);

	 		
			if(!resultPrevision)
			{
				throw new Exception ("No se ha podido crear el registro de previsión  (SPD_resiMedicacion)");
				//errors.add( "Registro sust creado correctamente ");
			}
			if(!result)
			{
				throw new Exception ("No se ha podido crear el registro ImportDatosResiSPD (ctl_medicacioResi)");
				//errors.add( "Registro sust creado correctamente ");
			}
			
			//FIN importación en ctl_medicacioResi

		return resultPrevision&&result;
		}
	
	private static void getPeriodicidadParaPrevision(FicheroResiBean fila) {
		    long dias = 30;
	        Date fechaInicio = null;
	        Date fechaFin = null;
	        try {
	            fechaInicio = DateUtilities.getDate(fila.getResiInicioTratamiento(), "dd/MM/yyyy");
	        }
	        catch (Exception ex) {}
	        try {
	            fechaFin = DateUtilities.getDate(fila.getResiFinTratamiento(), "dd/MM/yyyy");
	        }
	        catch (Exception ex2) {}
	        final int milisecondsByDay = 86400000;
	        try {
	            dias = (fechaFin.getTime() - fechaInicio.getTime()) / milisecondsByDay + 1;
	        }
	        catch (Exception ex3) {}
	        if (fila.getResiPeriodo()!=null && fila.getResiPeriodo().equals(SPDConstants.SPD_PERIODO_MENSUAL)) {
	            fila.setComprimidosSemana(fila.getComprimidosDia()/4);
	        }
	        else if (fila.getResiPeriodo()!=null && fila.getResiPeriodo().equals(SPDConstants.SPD_PERIODO_QUINCENAL)) {
	            fila.setComprimidosSemana(fila.getComprimidosDia()/2);
	        }
	        else if (fila.getResiPeriodo()!=null && fila.getResiPeriodo().equals(SPDConstants.SPD_PERIODO_SEMANAL)) {
	            fila.setComprimidosSemana(fila.getComprimidosDia());
	        }
	        if (fila.getResiPeriodo()!=null && fila.getResiPeriodo().equals(SPDConstants.SPD_PERIODO_DIARIO)) {
	            fila.setComprimidosSemana(fila.getComprimidosDia()*7);
	        }
	        if (fila.getResiPeriodo()!=null && fila.getResiPeriodo().equals(SPDConstants.SPD_PERIODO_DIAS_SEMANA_CONCRETOS)) {
	            fila.setComprimidosSemana(fila.getComprimidosDia()*fila.getDiasSemanaMarcados());
	        }
	        if (fila.getResiPeriodo()!=null && fila.getResiPeriodo().equals(SPDConstants.SPD_PERIODO_ESPECIAL)) {
	        	String secuenciaGuide = fila.getSecuenciaGuide();
	        	String[] partesSecuencia =secuenciaGuide.split("-");
	        	int si=0; 
	        	int no=0; 
	        	String periodo ="";
	        	try{
		        	si=new Integer(partesSecuencia[0]).intValue();
		        	no=new Integer(partesSecuencia[1]).intValue();
		        	
		        	float pastillasSemana =0;
		        	periodo = partesSecuencia[2];		
		        	if(periodo.equals(SPDConstants.GUIDE_DIAS) )
		        	{
		        		fila.setComprimidosSemana(fila.getComprimidosDia()*(si/(si + no)) * 7); 
		        	}
		        	else if(periodo.equals(SPDConstants.GUIDE_SEMANAS))
		        	{
		        		fila.setComprimidosSemana(fila.getComprimidosDia()*((si/(si + no)))); 
			        }
		        	else if(periodo.equals(SPDConstants.GUIDE_MESES))
		        	{
		        		fila.setComprimidosSemana(fila.getComprimidosDia()*((si/(si + no))/4)); 
		        	}
	        	}catch(Exception e){}
	        	

	        	
	        	
	        }
        	float comprimidosSemana =fila.getComprimidosSemana();
        	if(comprimidosSemana>0)
        	{
        		fila.setComprimidosDia(comprimidosSemana/7);
        		fila.setComprimidosDosSemanas(comprimidosSemana*2);
        		fila.setComprimidosTresSemanas(comprimidosSemana*3);
        		fila.setComprimidosCuatroSemanas(comprimidosSemana*4);
        	}    

	}


	/**
	 * Método que guarda el cálculo de previsión según la producción del Excel
	 * @param SpdUsuario 
	 * @param row
	 * @return
	 * @throws Exception
	 */
	public static  boolean creaRegistroPrevisionORI(String SpdUsuario, FicheroResiBean fila) throws Exception {
		
			boolean procesosAnterioresLimpiados = false;
			
	      	boolean resultPrevision =true;
	      	boolean result =true;

	    	if(fila.getResiSiPrecisa()!=null && fila.getResiSiPrecisa().equalsIgnoreCase("X")) fila.setResiSiPrecisa("SI_PRECISA");
	    	
	    	//pauta
			//conteo de comprimidos
			int diasSemanaMarcados= 0;
			float comprimidosSemana=0;
	    	float comprimidosDia = 0;
	    	try{
	    		comprimidosDia=new Float(getTotalComprimidosDia(fila, SPDConstants.NUM_TOMAS_SPD)).floatValue();
	    	}
	    	catch(Exception e)
	    	{}

		
			//if(fila.getSpdAccionBolsa()!=null && fila.getSpdAccionBolsa().equalsIgnoreCase("PASTILLERO"))
			//{
			//miramos si tiene alguna periodicidad
			HelperSPD2.getPeriodicidad(fila);
		
			fila.setDiasSemanaMarcados(HelperSPD2.getDiasMarcados(fila));
			comprimidosDia= HelperSPD2.getPautaDia(fila)/fila.getResiFrecuencia();
			comprimidosSemana=comprimidosDia*fila.getDiasSemanaMarcados();

			 //por defecto
			fila.setComprimidosDia(comprimidosDia);
			fila.setComprimidosSemana(comprimidosSemana );
			fila.setComprimidosDosSemanas(comprimidosSemana * 2);
			fila.setComprimidosTresSemanas(comprimidosSemana * 3);
			fila.setComprimidosCuatroSemanas(comprimidosSemana * 4);
				 
			//}
			
			//Búsqueda de BDConsejo
			BdConsejo bdConsejo=BdConsejoDAO.getByCN(fila.getSpdCnFinal()); 
			if(bdConsejo!=null)
			{
				fila.setBdConsejo(bdConsejo);
				fila.setSpdCodGtVm(bdConsejo.getCodGtVm());
				fila.setSpdNomGtVm(bdConsejo.getNomGtVm());
				fila.setSpdCodGtVmp(bdConsejo.getCodGtVmp());
				fila.setSpdNomGtVmp(bdConsejo.getNomGtVmp());
				fila.setSpdCodGtVmpp(bdConsejo.getCodGtVmpp());
				fila.setSpdNomGtVmpp(bdConsejo.getNomGtVmpp());
				fila.setSpdEmblistable(bdConsejo.getEmblistable());
			}
			
			if(fila.getSpdEmblistable()!=null&&fila.getSpdEmblistable().equalsIgnoreCase("SI") || (fila.getSpdAccionBolsa()!=null && fila.getSpdAccionBolsa().equalsIgnoreCase(SPDConstants.SPD_ACCIONBOLSA_PASTILLERO)))
			{
				fila.setSpdEmblistable("SI");
			}

	    	
	    	//fila.setSpdComentarioLopicost(StringUtil.replaceInvalidChars((String) row.elementAt(15)));	de momento no lo guardamos
	    	
			//no hace falta comprobar si es válido, porque solo se podrá enviar cuando no haya errorres.
	     	//IOSpdApi.compruebaTratamiento(SpdUsuario, fila);
	     	resultPrevision=FicheroMedResiConPrevisionDAO.nuevo(fila);
	 		result=FicheroMedResiDAO.nuevo(fila);

	 		
			if(!resultPrevision)
			{
				throw new Exception ("No se ha podido crear el registro de previsión  (SPD_resiMedicacion)");
				//errors.add( "Registro sust creado correctamente ");
			}
			if(!result)
			{
				throw new Exception ("No se ha podido crear el registro ImportDatosResiSPD (ctl_medicacioResi)");
				//errors.add( "Registro sust creado correctamente ");
			}
			
			//FIN importación en ctl_medicacioResi


		    
		return resultPrevision&&result;
		}
	/*public static List<Map<String, Object>> reordenarMatriz(List results, List<String> ordenColumnas) throws Exception {
	   
	   

	    // Lista para almacenar el resultado reorganizado
	    List<Map<String, Object>> resultadosReorganizados = new ArrayList<>();

	    // Reorganizar las columnas
	    for (Map<String, Object> fila : results) {
	        Map<String, Object> filaReorganizada = new LinkedHashMap<>(); // Mantener el orden de inserción

	        // Mantener las columnas originales
	        for (Map.Entry<String, Object> entry : fila.entrySet()) {
	            if (!ordenColumnas.contains(entry.getKey())) {
	                filaReorganizada.put(entry.getKey(), entry.getValue());
	            }
	        }

	        // Reorganizar las columnas especificadas
	        for (String columna : ordenColumnas) {
	            filaReorganizada.put(columna, fila.get(columna));
	        }

	        resultadosReorganizados.add(filaReorganizada);
	    }

	    return resultadosReorganizados;
	}
*/

public static List<FicheroResiBean> reordenarMatriz(List<FicheroResiBean> results, List<String> ordenColumnas) {
    List<FicheroResiBean> resultadosReorganizados = new ArrayList<>();

    // Reorganizar las columnas
    for (FicheroResiBean fila : results) {
        FicheroResiBean filaReorganizada = construirFilaDesdeFila(fila, ordenColumnas);
        resultadosReorganizados.add(filaReorganizada);
    }

    return resultadosReorganizados;
}

private static FicheroResiBean construirFilaDesdeFila(FicheroResiBean fila, List<String> ordenColumnas) {
    FicheroResiBean filaReorganizada = new FicheroResiBean();

    // Mantener las columnas originales
    for (Field campo : FicheroResiBean.class.getDeclaredFields()) {
        String nombreCampo = campo.getName();
        if (!ordenColumnas.contains(nombreCampo)) {
            try {
                campo.setAccessible(true);
                campo.set(filaReorganizada, campo.get(fila));
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Error al construir la fila desde la fila", e);
            }
        }
    }
    for (Field campo : FicheroResiBean.class.getDeclaredFields()) {
        campo.setAccessible(true);
        String nombreCampo = campo.getName();
        try {
            Object valorCampo = campo.get(fila);
           // System.out.println("Campo: " + nombreCampo + ", Valor: " + valorCampo);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    // Reorganizar las columnas especificadas
    for (String columna : ordenColumnas) {
        try {
            Field campo = FicheroResiBean.class.getDeclaredField(columna);
            campo.setAccessible(true);
            campo.set(filaReorganizada, campo.get(fila));
        } catch (NoSuchFieldException | IllegalAccessException e) {
           // throw new RuntimeException("Error al construir la fila desde la fila", e);
        }
    }

    return filaReorganizada;
}

/**
 * retorna true en caso de tener un formato fecha. En caso de que se incluye una hora, p.e. "21:00" Excel lo transforma a "31-dic-1899"
 * @param excelDate
 * @return
 */
	private static boolean isExcelDateFormat(String excelDate) {
	    // Utiliza una expresión regular para comprobar si el valor tiene el formato "dd-MMM-yyyy"
	    //Pattern dateFormatPattern = Pattern.compile("\\d{1,2}-[a-zA-Z]+-\\d{4}");
	    Pattern dateFormatPattern = Pattern.compile("\\d{1,2} [a-zA-Z]+ \\d{4}");
	    Matcher matcher = dateFormatPattern.matcher(excelDate);
	    return matcher.matches();
	}

	
public static int countProcesosMismaFecha(String idProcesoTmp) throws SQLException {
	return FicheroResiCabeceraDAO.countProcesosMismaFecha(idProcesoTmp);

}

public static void eliminaTomasCero(FicheroResiBean f) {
    for (int i = 1; i <= 24; i++) {
        String getterMethodName = "getResiToma" + i;
        String setterMethodName = "setResiToma" + i;

        try {
            // Obtén el valor actual de la toma usando reflection
            Method getterMethod = FicheroResiBean.class.getMethod(getterMethodName);
            String currentValue = (String) getterMethod.invoke(f);

            // Verifica si el valor es "0" y establece "0" si es así
            if (currentValue != null && currentValue.equals("0")) {
                Method setterMethod = FicheroResiBean.class.getMethod(setterMethodName, String.class);
                setterMethod.invoke(f, "");
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace(); // Maneja las excepciones según tus necesidades
        }
    }
}

public static List<FicheroResiBean>  recuperaProduccionesResidencia(String spdUsuario, int oidDivisionResidencia) throws Exception {
	return FicheroResiCabeceraDAO.getProcesosCargados(spdUsuario, oidDivisionResidencia);
}

public static void controlarMTEstrecho(String spdUsuario, FicheroResiBean medResi) {
	
}

	/** MIRAR SI ES BORRABLE
	 * si las previsiones de resi vs spd no cuadran, se indicaran en el bean
	 * @param medResi
	 */
public static void chequearPrevisionResiSPD(FicheroResiBean medResi) {
	
	if(medResi.getSpdAccionBolsa()!=null && medResi.getSpdAccionBolsa().equalsIgnoreCase(SPDConstants.SPD_ACCIONBOLSA_PASTILLERO))
	{
		//si no hay descuadre será por comprobación posterior
		medResi.setCuadraPrevision(false);
		float resi = 0;
		float spd = 0;
		try{
			resi = (medResi!=null?medResi.getPrevisionResi():0) ;
		}catch(Exception e){			
		}
		
		try{
			 spd =(medResi!=null?medResi.getPrevisionSPD():0) ;
				if(resi!=spd) medResi.setCuadraPrevision(false);
		}catch(Exception e){			
		}
	
	}
}



	public static String tratamientoLog(FicheroResiBean medResi) {
		String result="";
		if(medResi!=null)
		{
/*			result = "| Resi --> " + medResi.getResiCn() + " | " + medResi.getResiMedicamento() + " | Robot --> " + medResi.getSpdCnFinal() + " - " + medResi.getSpdNombreBolsa() + " - " + medResi.getSpdAccionBolsa()+ 
			" | Dias --> " + medResi.getResiD1() + "|" + medResi.getResiD2() + "|" + medResi.getResiD3()+ "|" + medResi.getResiD4()+ "|" + medResi.getResiD5()+ "|" + medResi.getResiD6()+ "|" + medResi.getResiD7() + 
			" | Tomas --> " + medResi.getResiToma1()+ "|" + medResi.getResiToma2()+ "|" +medResi.getResiToma3()+ "|" +medResi.getResiToma4()+ "|" +medResi.getResiToma5()+ "|" +medResi.getResiToma6()+ "|" +
			medResi.getResiToma7()+ "|" +medResi.getResiToma8()+ "|" +medResi.getResiToma9()+ "|" +medResi.getResiToma10()+ "|" +medResi.getResiToma11()+ "|" +medResi.getResiToma12()+ "|" +
			medResi.getResiToma13()+ "|" +medResi.getResiToma14()+ "|" +medResi.getResiToma15()+ "|" +medResi.getResiToma16()+ "|" +medResi.getResiToma17()+ "|" +medResi.getResiToma18()+ "|" +
			medResi.getResiToma19()+ "|" +medResi.getResiToma20()+ "|" +medResi.getResiToma21()+ "|" +medResi.getResiToma22()+ "|" +medResi.getResiToma23()+ "|" +medResi.getResiToma24()+ "|" +
			" | Variante --> " + medResi.getResiVariante() + 
			" | Comentarios --> " + medResi.getResiComentarios() +
			" | Observaciones --> " + medResi.getResiObservaciones() + 
			" | TipoMedicacion --> " + medResi.getResiTipoMedicacion() +
			" | DetalleRow --> " + medResi.getDetalleRow();  */
			result = "|  <br>  SPD Robot --> " + medResi.getSpdCnFinal() + " - " + medResi.getSpdNombreBolsa() + "| AcciónRobot --> " + medResi.getSpdAccionBolsa()+ " <br> " +
			" Dias --> " + medResi.getResiD1() + "|" + medResi.getResiD2() + "|" + medResi.getResiD3()+ "|" + medResi.getResiD4()+ "|" + medResi.getResiD5()+ "|" + medResi.getResiD6()+ "|" + medResi.getResiD7() + "|" + 
			" Tomas --> " + getTextoTomas(medResi) + 
			"  <br> Resi&nbsp;&nbsp; fecha Desde-Hasta --> " + medResi.getFechaDesde() + "-" + medResi.getFechaDesde() + " " +  
			"  <br> Robot fecha Desde-Hasta --> " + medResi.getFechaDesde() + "-" + medResi.getFechaDesde() + " " +
			"  <br> DetalleRow --> " + HelperSPD2.getDetalleRowFechasOk(medResi.getDetalleRow()) + " " +
			"  <br> ControlNumComprimidos --> " + medResi.getControlNumComprimidos() + " (Resi: " + medResi.getPrevisionResi() + " - Robot: " + medResi.getPrevisionSPD() + " )  " +
			"  <br> ControlRegistroAnterior --> " + arregloMensajeRegistroAnterior(medResi.getControlRegistroAnterior()) + " " +
			"  <br> ControlRegistroRobot --> " + medResi.getControlRegistroRobot() + " " +
			"  <br> ControlValidacionDatos --> " + medResi.getControlValidacionDatos() + " " +
			"  <br> ControlPrincipioActivoAction --> " + medResi.getControlPrincipioActivo() + " " +
			"  <br> ControlNoSustituible --> " + medResi.getControlNoSustituible() + " " +
			"  <br> ControlDiferentesGtvmp --> " + medResi.getControlDiferentesGtvmp() + " " +
			"  <br> ControlUnicoGtvm --> " + medResi.getControlUnicoGtvm() + " " 
			;  
		}
		return result;
	}



	private static String arregloMensajeRegistroAnterior(String controlRegistroAnterior) {
		String result = controlRegistroAnterior;
		if(controlRegistroAnterior!=null)
			switch (controlRegistroAnterior) {
			case SPDConstants.CTRL_REGISTRO_ANTERIOR_RD_SD:
				result="Registro residencia nuevo";
			case SPDConstants.CTRL_REGISTRO_ANTERIOR_RI_SI:
				result="Registro reutilizado";
			case SPDConstants.CTRL_REGISTRO_ANTERIOR_RI_SD:
				result="Alerta: Registro resi diferente, robot igual";
			case SPDConstants.CTRL_REGISTRO_ANTERIOR_RD_SI:
				result="Alerta: Registro resi igual, robot diferente";

			default:
				break;
			}
		
		return result;
	}



	private static String getTextoTomas(FicheroResiBean medResi) {
		int NUM_TOMAS=0;
		try{
			NUM_TOMAS = medResi.getNumeroDeTomas();
		}catch(Exception e){
		}
		String result = "|";
	        Class<?> tratClass = medResi.getClass();
	        try {
	            for (int toma = 1; toma <= NUM_TOMAS; toma++) {
	                Method method = tratClass.getMethod("getResiToma" + toma);
	                Object value = method.invoke(medResi);
	             
	                result += value + "|";
	            }
	        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
	            // Manejar las excepciones adecuadamente
	            System.err.println("Error al acceder a los métodos de resiToma: " + e.getMessage());
	        }

	       // System.out.println("La suma de las resiTomas es: " + suma);
	        return result;
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
	public static String getOtrosSql2008(int inicio, int fin, boolean count) 
	{
		String otros="";
		if(!count) 
		{
			otros+= " ) cte ";
			otros+= " where ROWNUM >=  "+ (inicio) + "  AND ROWNUM <= "+(fin);

		}
		return otros;
	}


	 public static String getDetalleRowFechasOk(String cadena) {
	        // Expresión regular para encontrar números de fecha que comienzan con 3 o 4 y tienen 5 cifras
		 	// Fechas del siglo XX (1900-1999): Números de serie de 1 a 36525 (31/12/1999).
		 	// Fechas del siglo XXI (2000-): Números de serie de 36526 en adelante.
	        String regex = "\\b[3-4]\\d{4}\\b";
	        Pattern pattern = Pattern.compile(regex);
	        Matcher matcher = pattern.matcher(cadena!=null?cadena:"");

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

/**
 * Método UNICAMENTE de momento para detalleRowKeyLiteFechas, para poder comparar las cargas con el anterior.
 * Devuelve las fechas en formato dd/mm/yyyy pero no está puesto para el resto para no alterar los detalleRow y demás actuales, porque 
 * provocaría muchas diferencias en cargas posteriores al cambiar el valor del campo
 * @param cadena
 * @return
 */
	 public static String getDetalleRowLiteFechasOk(String cadena) {
		    if (cadena == null) return "";

		    // Expresiones regulares para manejar diferentes formatos
		    String regex =
		        "\\b(\\d{1,2})[-/](\\d{1,2})[-/](\\d{2,4})\\b" + // dd-mm-yy, dd/mm/yyyy
		        "|\\b(\\d{4})[-/](\\d{1,2})[-/](\\d{1,2})\\b" +   // yyyy-mm-dd
		        "|\\b([3-4]\\d{4})\\b" +                              // Fechas numéricas de Excel
		        "|\\b(\\d{2})(\\d{2})(\\d{2,4})\\b";              // ddmmyy o ddmmyyyy

		    Pattern pattern = Pattern.compile(regex);
		    Matcher matcher = pattern.matcher(cadena);

		    // Formato de salida
		    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		    StringBuffer sb = new StringBuffer();
		    while (matcher.find()) {
		        String reemplazo;
		        try {
		            if (matcher.group(4) != null && matcher.group(5) != null && matcher.group(6) != null) { // yyyy-mm-dd
		                reemplazo = LocalDate.of(
		                    Integer.parseInt(matcher.group(4)),
		                    Integer.parseInt(matcher.group(5)),
		                    Integer.parseInt(matcher.group(6))
		                ).format(outputFormatter);
		            } else if (matcher.group(7) != null) { // Fecha numérica de Excel
		                int diasDesde1900 = Integer.parseInt(matcher.group(7));
		                reemplazo = LocalDate.of(1900, 1, 1)
		                    .plusDays(diasDesde1900 - 2)
		                    .format(outputFormatter);
		            } else if (matcher.group(1) != null && matcher.group(2) != null && matcher.group(3) != null) { // dd-mm-yy, dd/mm/yyyy
		                int dia = Integer.parseInt(matcher.group(1));
		                int mes = Integer.parseInt(matcher.group(2));
		                int anio = Integer.parseInt(
		                    matcher.group(3).length() == 2
		                    ? "20" + matcher.group(3)
		                    : matcher.group(3)
		                );
		                reemplazo = LocalDate.of(anio, mes, dia).format(outputFormatter);
		            } else if (matcher.group(8) != null && matcher.group(9) != null && matcher.group(10) != null) { // ddmmyy o ddmmyyyy
		                int dia = Integer.parseInt(matcher.group(8));
		                int mes = Integer.parseInt(matcher.group(9));
		                int anio = Integer.parseInt(
		                    matcher.group(10).length() == 2
		                    ? "20" + matcher.group(10)
		                    : matcher.group(10)
		                );
		                reemplazo = LocalDate.of(anio, mes, dia).format(outputFormatter);
		            } else {
		                reemplazo = matcher.group(); // Dejar el texto original si no coincide
		            }
		        } catch (Exception e) {
		            reemplazo = matcher.group(); // En caso de error, dejar el texto original
		        }
		        matcher.appendReplacement(sb, reemplazo);
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

	    public static String convertirAFechaNumerica(String cadena) {
	        // Expresión regular para encontrar fechas en formato "dd/MM/yyyy"
	        String regex = "\\b\\d{2}/\\d{2}/\\d{4}\\b";
	        Pattern pattern = Pattern.compile(regex);
	        Matcher matcher = pattern.matcher(cadena);

	        // Formato de fecha "dd/MM/yyyy"
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	        // Iterar sobre las coincidencias y reemplazarlas con la fecha convertida a formato numérico
	        StringBuffer sb = new StringBuffer();
	        while (matcher.find()) {
	            String fechaStr = matcher.group();
	            LocalDate fecha = LocalDate.parse(fechaStr, formatter);
	            int fechaNumerica = convertirFechaNumerica(fecha);
	            matcher.appendReplacement(sb, String.valueOf(fechaNumerica));
	        }
	        matcher.appendTail(sb);

	        return sb.toString();
	    }

	    public static int convertirFechaNumerica(LocalDate fecha) {
	        // La fecha base de Excel es el 30 de diciembre de 1899
	        LocalDate fechaBaseExcel = LocalDate.of(1899, 12, 30);

	        // Calcular la diferencia total de días entre las dos fechas
	        long diasTranscurridos = ChronoUnit.DAYS.between(fechaBaseExcel, fecha);

	        return (int) diasTranscurridos;
	    }
	  
	    public static StringBuilder convertirListSecuencia(List aHistorico) {
	       //creamos la secuencia de oid a pasar a histórico
		  StringBuilder valores = new StringBuilder();
		  for (int i = 0; i < aHistorico.size(); i++) {
		      valores.append(aHistorico.get(i));
		      if (i < aHistorico.size() - 1) {
		          valores.append(", ");
		      }
		  }
	    return valores;
	    }



		public static String getDetalleRow(Vector row, int COLUMNAS) {
			String detalleRow ="";
			if(row!=null){
				//detalleRow = row.toString();
				detalleRow = row.toString();
				if(row.size()>COLUMNAS) 
		   	 		detalleRow = row.subList(0, COLUMNAS).toString();
			}
	        // Procesa la cadena de Excel y conviértela en un arreglo o lista, por ejemplo
	       detalleRow = StringUtil.quitaEspaciosYAcentos(detalleRow.toString().replaceAll("[\\[\\]]", "").replaceAll("'", " "), true);
	       //detalleRow = detalleRow.toString().replaceAll("[\\[\\]]", "").replaceAll("'", " ");
			return detalleRow;
		}
	    
	    public String vectorToPipeSeparatedString(Vector<String> row) {
	        StringBuilder sb = new StringBuilder();
	        for (int i = 0; i < row.size(); i++) {
	            sb.append(row.get(i));
	            if (i < row.size() - 1) {
	                sb.append('|');
	            }
	        }
	        return sb.toString();
	    }
	    
	    // Métodos para enmascarar los campos
	    public static String enmascararCIP(String cip) {
	        if (cip != null && cip.length() > 10) {
	            return cip.substring(0, 5) + "*****" + cip.substring(cip.length() - 1);
	        }
	        return cip;
	    }

	    public static String enmascararNombre1(String nombre) {
	        if (nombre != null && nombre.length() > 1) {
	            return nombre.substring(0, 1) + "****"; 
	        }
	        return nombre;
	    }
	    public static String enmascararNombre(String nombre) {
	        if (nombre == null || nombre.trim().isEmpty()) {
	            return nombre;
	        }

	        StringBuilder resultado = new StringBuilder();
	        String[] palabras = nombre.split("\\s+"); // Divide por espacios

	        for (String palabra : palabras) {
	            if (palabra.length() > 3) {
	                resultado.append(palabra.substring(0, 3)).append("****");
	            } else {
	                resultado.append(palabra); // Si la palabra tiene 3 o menos caracteres, no se enmascara
	            }
	            resultado.append(" "); // Añade un espacio entre palabras
	        }

	        return resultado.toString().trim(); // Elimina el espacio extra al final
	    }

	    public static String enmascararNombre(String nombre, int inicio, int fin) {
	        if (nombre == null || nombre.trim().isEmpty()) {
	            return nombre;
	        }

	        StringBuilder resultado = new StringBuilder();
	        String[] palabras = nombre.split("\\s+"); // Divide por espacios

	        for (String palabra : palabras) {
	            if (palabra.length() > inicio+fin+1) {
	            	//System.out.println("1 " + palabra.length() +  "_" + inicio+fin+1);
	                //resultado.append(palabra.substring(0, inicio)).append("****");
	            	resultado.append(palabra.substring(0, inicio) + "*****" + palabra.substring(palabra.length() - fin));
	                
	            } else if (palabra.length() <= inicio+fin+1 && palabra.length() > inicio) {
	            	//System.out.println("2");
            	resultado.append(palabra.substring(0, inicio) + "*****" );
	               // resultado.append(palabra); // Si la palabra tiene 3 o menos caracteres, no se enmascara
	            } else if (palabra.length() <=  inicio && palabra.length() > 0 ) {
	            	//System.out.println("3");
            	resultado.append(palabra.substring(0, inicio-1) + "*****" );
	               // resultado.append(palabra); // Si la palabra tiene 3 o menos caracteres, no se enmascara
	            } else {
	            	//System.out.println("4");
                resultado.append(palabra); // Si la palabra tiene  "inicio" caracteres, no se enmascara
	            }	  
	            resultado.append(" "); // Añade un espacio entre palabras
	        }

	        return resultado.toString().trim(); // Elimina el espacio extra al final
	    }



		public static String dameFechaHora() {
			return LocalDateTime.now().format(SPDConstants.FORMAT_DATETIME_24h);
		}

	    
		public static LocalDate parseFecha(String fechaStr) {
		    try {
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		        return LocalDate.parse(fechaStr, formatter);
		    } catch (Exception e) {
		        return null;
		    }
		}

		public static LocalTime parseHora(String horaStr) {
		    try {
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		        return LocalTime.parse(horaStr, formatter);
		    } catch (Exception e) {
		        return null;
		    }
		}

	    
}

package lopicost.spd.utils;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.DateUtil;


/** String Util: recopilaci�n de utilidades para tratamiento de Strings

 */
public class StringUtil{
	

	
	public final static String limpiarTextoEspaciosYAcentos(String text, boolean upper) {
		String result=text;
		if(result!=null&&!result.equals(""))
		{ 
			result=result.trim();
			result=limpiarTexto(quitaEspacios(result));
		}
		return makeFlat(result, upper);
	}
	
	
	/**
	 * M�todo para eliminar algunos car�cteres de un texto concreto
	 * @param text
	 * @return
	 */
	public final static String limpiarTextoyEspacios(String text) {
		String result=text;
		if(result!=null&&!result.equals(""))
		{ 
			result=result.trim();
			result=limpiarTexto(quitaEspacios(result));
		}
		return result;
	}
	
	/**
	 * M�todo para eliminar algunos car�cteres de un texto concreto
	 * @param text
	 * @return
	 */
	public final static String limpiarTexto(String text) {
		String result=text;
		if(result!=null&&!result.equals(""))
		{ 
			result=result.replace("'", "");
			result=result.replace("�", "");			
			result=result.replace("`", "");
			result=result.replace(".", "");
			result=result.replace("-", "");
			result=result.replace(",", "");
			result=result.replace(";", "");
			result=result.replace("�", "");
			result=result.replaceAll("\\t","");
			result=result.trim();
		}
		return result;
	}
	/**
	 * M�todo para eliminar algunos car�cteres de un texto concreto
	 * @param text
	 * @return 
	 */
	public final static String limpiarTextoComentarios(String text) {
		String result=text;
		if(result!=null&&!result.equals(""))
		{ 
			result=result.replace("'", " ");
			result=result.replace("�", " ");			
			result=result.replace("`", " ");
			result=result.replace(".", " ");
			result=result.replace("-", " ");
			result=result.replace(",", " ");
			result=result.replace(";", " ");
			result=result.replace("�", " ");
			result=result.replaceAll("\\t","");
			result=result.trim();
		}
		return result;
	}
	
	public final static String limpiarTextoDetalleRow(String text) {
		String result=text;
		if(result!=null&&!result.equals(""))
		{ 
			result=result.replace(".", " ");
			result=result.replace("-", " ");
			result=result.replace(",", " ");
			result=result.replace(";", " ");
			result=result.trim();
		}
		return quitaEspacios(result);
	}	
	
	/**
	 * M�todo para eliminar algunos car�cteres de un texto concreto
	 * @param text
	 * @return
	 */
	public final static String limpiarTextoTomas(String text) {
		return limpiarTextoTomas(text, true) ;
	}
	
	public final static String limpiarTextoTomas(String text, boolean quitaEspacios) {
		String result=text;
		if(result!=null&&!result.equals(""))
		{
			
			result.trim();
			result=result.replace("-", " ");
			result=result.replace(".", ",");
			result=result.replace("_", " ");
			result=result.replace(";", ",");
			result=result.replace("'", ",");
			result=result.replace("�", " ");
			if(quitaEspacios) result=quitaEspacios(result);
			
			
		}
		return result;	
	}

	/**
	 * M�todo para eliminar todos los espacios de un texto
	 * @param text
	 * @return
	 */
	public  final static String quitaEspacios(String text) {
		String result=text;
		if(result!=null&&!result.equals(""))
		{
			result=result.trim();
			//result=result.replace(" ", "");
			//result = StringUtils.strip(result, " \t\u00A0\u1680\u180e\u2000\u200a\u202f\u205f\u3000");
			result = result.replaceAll("[\\s\\u00A0\\u1680\\u180E\\u2000-\\u200A\\u202F\\u205F\\u3000]+", "");
			//result = result.replaceAll("\\s+", "");
			
		}
		
		return result;
	}
	
	public  final static String quitaEspaciosYAcentos(String text, boolean upper) {
		String result=text;
		if(result!=null&&!result.equals(""))
		{
			result=makeFlat(quitaEspacios(text), upper);
		}
		
		return result;
	}
	
	/**
	 * M�todo que transforma en may�sculas, eliminando acentos
	 * @param q String base
	 * @return String Retorna un String cuyas letras estar�n todas en may�sculas y sin acentos
	 */
	public final static String makeFlat(String q, boolean upper){
		if(q==null) return "";
		q = StringUtil.replaceChar(q,'�','A');
		q = StringUtil.replaceChar(q,'�','E');
		q = StringUtil.replaceChar(q,'�','I');
		q = StringUtil.replaceChar(q,'�','O');
		q = StringUtil.replaceChar(q,'�','U');
		q = StringUtil.replaceChar(q,'�','A');
		q = StringUtil.replaceChar(q,'�','E');
		q = StringUtil.replaceChar(q,'�','I');
		q = StringUtil.replaceChar(q,'�','O');
		q = StringUtil.replaceChar(q,'�','U');
		q = StringUtil.replaceChar(q,'�','A');
		q = StringUtil.replaceChar(q,'�','E');
		q = StringUtil.replaceChar(q,'�','I');
		q = StringUtil.replaceChar(q,'�','O');
		q = StringUtil.replaceChar(q,'�','U');
		q = StringUtil.replaceChar(q,'�','N');
		q = StringUtil.replaceChar(q,'�','C');
		
		q = StringUtil.replaceChar(q,'�','a');
		q = StringUtil.replaceChar(q,'�','e');
		q = StringUtil.replaceChar(q,'�','i');
		q = StringUtil.replaceChar(q,'�','o');
		q = StringUtil.replaceChar(q,'�','u');
		q = StringUtil.replaceChar(q,'�','a');
		q = StringUtil.replaceChar(q,'�','e');
		q = StringUtil.replaceChar(q,'�','i');
		q = StringUtil.replaceChar(q,'�','o');
		q = StringUtil.replaceChar(q,'�','u');
		q = StringUtil.replaceChar(q,'�','a');
		q = StringUtil.replaceChar(q,'�','e');
		q = StringUtil.replaceChar(q,'�','i');
		q = StringUtil.replaceChar(q,'�','o');
		q = StringUtil.replaceChar(q,'�','u');
		q = StringUtil.replaceChar(q,'�','n');
		q = StringUtil.replaceChar(q,'�','c');
		q = StringUtil.replaceChar(q,'�','.');
		q = StringUtil.replaceChar(q,'�','.');
		
		q = StringUtil.replaceChar(q,'\"',' ');
		
		if(upper) return q.toUpperCase();
		else return q;
	}

	
	 //M�todo que reemplaza un car�cter dentro de un String
	final static public String replaceChar(String q,char in,char out){
		int idx;
		idx=q.indexOf(in);
		if(idx==-1)
			return q;
		return q.replace(in,out);
	}
	
	//Retorna el n�mero de veces que un car�cter aparece en un String
	public final  static int charCount( String str, char c ){
		int n = 0;
		for ( int i = 0; i < str.length(); ++i )
			if ( str.charAt( i ) == c )
				++n;
		return n;
	}
	
	/** 
	 * M�todo que se encarga de reemplazar un subString por otro <BR>
	 * <b>NOTA</b>: OJO CON EL ORDEN DE LOS PARAMS DE ESTE METODO! el string base donde queremos hacer los replaces es el ULTIMO PARAMETRO
	 * <b>NOTA</b>: ESTE METODO NO ES SEGURO (peta por ej con el string "") --> usar replaceString
	 * @param substr String cuyo contenido ser� reemplazado
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
	 * M�todo que reemplaza un car�cter dentro de un String por un String
	 * @param q String base
	 * @param in Car�cter a reemplazar
	 * @param out Cadena de sustituci�n
	 * @return String Nuevo String con el car�cter reemplazado
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
	
	public static String replaceInvalidCharsForRobot(String inString)
	{
		if (inString== null) return inString;
		
		//inString = inString.replace("(","");
		//inString = inString.replace(")","");
		//inString = inString.replace("/","");
		
		return replaceInvalidChars(inString);
	}

		
	
	/**
	 * Reemplaza los caracteres no v�lidos de una cadena alfanum�rcia.
	 * @param inString cadena de entrada
	 * @return String Retorna la cadena alfanum�rica que contiene �nicamente caracteres validos.
	 */    
	public static String replaceInvalidChars(String inString)
	{
		//String allowedChars = "abcdefghijklmnopqrstuvwxyz0123456789_-.";

		if (inString== null) return inString;
		
		inString = inString.replace('�','A');
		inString = inString.replace('�','E');
		inString = inString.replace('�','I');
		inString = inString.replace('�','O');
		inString = inString.replace('�','U');
		inString = inString.replace('�','A');
		inString = inString.replace('�','E');
		inString = inString.replace('�','I');
		inString = inString.replace('�','O');
		inString = inString.replace('�','U');
		inString = inString.replace('�','A');
		inString = inString.replace('�','E');
		inString = inString.replace('�','I');
		inString = inString.replace('�','O');
		inString = inString.replace('�','U');
		inString = inString.replace('�','N');
		inString = inString.replace('�','C');
		inString = inString.replace('�','A');
		inString = inString.replace('�','E');
		inString = inString.replace('�','I');
		inString = inString.replace('�','O');
		inString = inString.replace('�','U');
		inString = inString.replace('�','A');
		inString = inString.replace('�','A');
		
		inString = inString.replace('�','a');
		inString = inString.replace('�','e');
		inString = inString.replace('�','i');
		inString = inString.replace('�','o');
		inString = inString.replace('�','u');
		inString = inString.replace('�','a');
		inString = inString.replace('�','e');
		inString = inString.replace('�','i');
		inString = inString.replace('�','o');
		inString = inString.replace('�','u');
		inString = inString.replace('�','a');
		inString = inString.replace('�','e');
		inString = inString.replace('�','i');
		inString = inString.replace('�','o');
		inString = inString.replace('�','u');
		inString = inString.replace('�','n');
		inString = inString.replace('�','c');
		inString = inString.replace('�','a');
		inString = inString.replace('�','e');
		inString = inString.replace('�','i');
		inString = inString.replace('�','o');
		inString = inString.replace('�','u');
		inString = inString.replace('�','a');
		inString = inString.replace('�','a');
		inString = inString.replace('�','o');
		inString = inString.replace('\'',',');
		
		inString = inString.replaceAll("'\"","");
		inString = inString.replaceAll("\'","");		

			//System.out.println(" inString --> " + inString);	
		return inString;
	}
	
	 /**
     * Transforma un String en un Vector de Strings mediante la separaci�n de un token
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
	 		if (!aux.equals(token)) vec.addElement(aux);
	 	}
	 	return vec;
	 }
	 
	 /**
	  * Convierto un decimal a binario.
	  * Le mete todos los zeros que sean necesarios hasta llegar
	  * al tama�o definido por leadingSize. 
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

			if (fechaString==null || "".equals(fechaString)) return "";	  		  
		  	
			
			fechaString=quitaEspacios(fechaString);
			fechaString=fechaString.replace("_", "/");
			fechaString=fechaString.replace("-", "/");
			fechaString=fechaString.replace(".", "/");
			fechaString=fechaString.replace(";", "");
			fechaString=fechaString.replace("'", "");
			fechaString=fechaString.replace("�", "");
			fechaString=fechaString.replace("'", "");
			
			//Date javaDate= DateUtil.getJavaDate((double) new Double(fechaString).doubleValue());
			// System.out.println(new SimpleDateFormat("MM/dd/yyyy").format(javaDate));
	        


			
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
			//en caso que llegue en formato num�rico
			Date javaDate= DateUtil.getJavaDate((double) new Double(fechaString).doubleValue());
			return DateUtilities.getDatetoString( "dd/MM/yyyy", javaDate);
		}
	}

	/**
	 * Devuelve el Date correspondiente a una fecha formateada
	 * @param fecha fecha formateada del tipo DD/MM/AAAA o DD/MM/AA
	 * @return String en formato  DD/MM/AAAA
	
	public static String getFechaArreglada(String fecha)
	{		
		String result="";
	  if (fecha==null || "".equals(fecha)) return null;	  		  
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
	 * Elimina los car�cteres "duros o invisibles" 
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

	
	/**
	 * Dejamos la fecha en formato STRING DD/MM/YYYY. 
	 * Utilizado para producciones SPD no se aceptar� fecha con a�o err�neo o fecha no v�lida
	 * @param fechaString
	 * @return
	 */

	public static String getStringArregloFecha(String fechaString, String formato) {
		String fechaStringArreglada ="";
		try{
			if (fechaString==null || "".equals(fechaString)) return "";	  		  

			try{

		    	if(DataUtil.isNumeroGreatherThanZero(fechaString))
		    	{
		    		double numeroDouble = Double.parseDouble(fechaString);
		    		int parteEntera = (int) numeroDouble;
		    		fechaString = String.valueOf(parteEntera);
		    		return DateUtilities.convertSerialNumberToDate(new Long(fechaString).longValue(), formato);//"dd/MM/yyyy");
				}
					
			}catch(Exception e){
				
			}
			
			
			//Arreglo s�mbolos
			fechaString=quitaEspacios(fechaString);
			fechaString=fechaString.replace("_", "/");
			fechaString=fechaString.replace("-", "/");
			fechaString=fechaString.replace(".", "/");
			fechaString=fechaString.replace(";", "");
			fechaString=fechaString.replace("'", "");
			fechaString=fechaString.replace("�", "");		//s�mbolo que llega en conversi�n del Excel,es un espacio
			fechaString=fechaString.replace("�", "");
			fechaString=fechaString.replace("'", "");
			fechaString=fechaString.replace("//", "/");
			fechaString=fechaString.replace("  ", "");		//dos espacios	
			

			
			//localizaci�n fecha 
			String[] parts = fechaString.split("/");
			String part1 = parts[0]; // d
			String part2 = parts[1]; // m
			String part3 = parts[2]; // y
			int day=Integer.parseInt(fechaString.substring(0,part1.length()));
			int mes=Integer.parseInt(fechaString.substring(part1.length()+1,part1.length()+1 + part2.length()));
			int anyo=Integer.parseInt(fechaString.substring(part1.length()+1 + part2.length()+1, part1.length()+1 + part2.length()+1+part3.length()));
			if (part3.length()==2) //Tratamos fechas cortas
			  	anyo+=2000;
            LocalDate current_date = LocalDate.now();
            //getting the current year from the current_date
            int current_Year = current_date.getYear();
           
            if (anyo>=current_Year+120 || anyo<=current_Year-120) return ""; //Devolvemos nulo por a�o fuera de un rango l�gico
			

            java.util.Calendar c=java.util.Calendar.getInstance();	
			c.set(java.util.Calendar.DAY_OF_MONTH,day);
			c.set(java.util.Calendar.MONTH,mes-1);
			c.set(java.util.Calendar.YEAR,anyo);
			
			fechaString=DateUtilities.getDatetoString(formato,c.getTime());
			//System.out.println(" fechaStringArreglada " + fechaStringArreglada);
			//devolvemos fecha en caso que dia y mes est�n dentro del rango. sino devolvemos ""
			System.out.println(" isDateValid " + DateUtilities.isDateValid( fechaString, "dd/MM/yyyy"));
			if (DateUtilities.isDateValid( fechaString, "dd/MM/yyyy") ) 
				fechaStringArreglada = DateUtilities.getDatetoString( "dd/MM/yyyy", c.getTime());
			
			
			return fechaStringArreglada;
 		}
		catch(Exception e)
		{
			return fechaStringArreglada;	
		}
	}

	
	/**
	 * Dejamos la fecha en formato STRING DD/MM/YYYY
	 * @param fechaString
	 * @return
	 
	public static String getStringArregloFechaBak(String fechaString) {
		try{

			if (fechaString==null || "".equals(fechaString)) return "";	  		  
		  	
			
			fechaString=quitaEspacios(fechaString);
			fechaString=fechaString.replace("_", "/");
			fechaString=fechaString.replace("-", "/");
			fechaString=fechaString.replace(".", "/");
			fechaString=fechaString.replace(";", "");
			fechaString=fechaString.replace("'", "");
			fechaString=fechaString.replace("�", "");
			fechaString=fechaString.replace("'", "");
			
			int day=Integer.parseInt(fechaString.substring(0,2));
		    int mes=Integer.parseInt(fechaString.substring(3,5));
			int anyo=Integer.parseInt(fechaString.substring(6));
			  if (anyo<2000) //Tratamos fechas cortas
				  	anyo+=2000;

			  //devolvemos fecha en caso que dia y mes est�n dentro del rango. Es posible que se reciba 31/04/, en este caso se transformar�a en 01/05
			if (0<day && day<=31 && 0<mes && mes<=12 ) 
			{
				java.util.Calendar c=java.util.Calendar.getInstance();	
				c.set(java.util.Calendar.DAY_OF_MONTH,day);
				c.set(java.util.Calendar.MONTH,mes-1);
				c.set(java.util.Calendar.YEAR,anyo);
				return DateUtilities.getDatetoString( "dd/MM/yyyy", c.getTime());
			}else
				return "";	
			  
		}
		catch(Exception e)
		{
			return "";	
		}
	}
	*/
    public static String replaceInvalidCharsInNumeric(String elementAt) {
        elementAt = elementAt.replace(".0", "");
        return replaceInvalidChars(elementAt);
    }
	
    
    public static String formatearFecha(String fecha, String formatoVuelta) {
        String[] patrones = {
                "dd/MM/yyyy",
                "dd-MMM-yyyy",
                "dd/MM/yyyy HH:mm"
        };

        for (String patron : patrones) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(patron);
                if (patron.contains("HH:mm")) {
                    LocalDateTime dateTime = LocalDateTime.parse(fecha, formatter);
                    return dateTime.format(DateTimeFormatter.ofPattern(formatoVuelta));
                } else {
                    LocalDate date = LocalDate.parse(fecha, formatter);
                    return date.format(DateTimeFormatter.ofPattern(formatoVuelta));
                }
            } catch (DateTimeParseException ignored) {
            }
        }
        return "";
    }
    
    public static String convierteLetrasCIPaValorASCII(String cadenaOriginal) {
        if (cadenaOriginal == null) {
            return null;
        }
        cadenaOriginal = cadenaOriginal.toUpperCase();
        StringBuilder cadenaNueva = new StringBuilder(cadenaOriginal);
        for (int i = 0; i < cadenaNueva.length(); i++) {
            char c = cadenaNueva.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                String asciiValue = String.valueOf((int) c);
                cadenaNueva.replace(i, i + 1, asciiValue);
                // Adjust the loop index to account for the new length of the string
                i += asciiValue.length() - 1;
            }
        }
        return cadenaNueva.toString();
    }

    
    public static String normalizeText(String text) {
        if (text == null) {
            return null;
        }

        // Normalizar el texto y eliminar acentos
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{M}");
        String result = pattern.matcher(normalized).replaceAll("");

        // Reemplazar '�' y '�' por 'N' y 'n'
        result = result.replace('�', 'N').replace('�', 'n');
        result = result.replace('�', 'C').replace('�', 'c');
        result = result.replace('�', '.').replace('�', '.');
        result = result.replace('�', '.').replace('�', '.');

        return result;
    }
    
    public static String convertTextToAscii(String text) {
        StringBuilder asciiText = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                asciiText.append((int) c);
            } else {
                asciiText.append(c);
            }
        }
        return asciiText.toString();
    }
    public static String left(String input, int length) {
        return input.substring(0, Math.min(input.length(), length));
    }

    public static String substring(String input, int start, int length) {
        return input.substring(start, Math.min(start + length, input.length()));
    }

    public static String replace(String input, char oldChar, char newChar) {
        return input.replace(oldChar, newChar);
    }
    
}
package lopicost.spd.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DateUtilities 
{
	
	/**
	 * Este método devuelve un Date, incrementando el número de días y horas indicado.
	 * @param pDate Fecha base.
	 * @param pMonths Si es diferente de 0, indica el número de meses a incrementar.
	 * @param pDays Si es diferente de 0, indica el número de días a incrementar.
	 * @param pHours Si es diferente de 0, indica el número de horas a incrementar.
	 * @return Fecha incrementada.
	 */
	public static Date getRealDate(Date pDate, int pMonths, int pDays, int pMinutes)
	{
		java.util.Calendar _cal= java.util.Calendar.getInstance(new Locale("es","ES"));

		_cal.setTime(new Date(pDate.getTime()));
		if (pMonths!=0)
			_cal.add(java.util.Calendar.MONTH, pMonths);
		if (pDays!=0)
			_cal.add(java.util.Calendar.DAY_OF_MONTH,pDays);		
		if (pMinutes!=0)
			_cal.add(java.util.Calendar.MINUTE,pMinutes);
		
		return _cal.getTime();
	}
	/**
	 * Este método devuelve un Date, con el número de horas, minutos , segundos, ms, inicializados a 0.
	 * @param pDate Fecha base
	 * @return Date Fecha con los valores inicializados a 0 por lo que respecta al tiempo (hora, minutos,...)
	 */
	public static Date getStartDay (Date pDate) 
	{
		Calendar c = Calendar.getInstance();
		c.setTime(pDate);
		c.set(Calendar.HOUR_OF_DAY,0);
		c.set(Calendar.MINUTE,0);
		c.set(Calendar.SECOND,0);			
		c.set(Calendar.MILLISECOND,0);		
		return c.getTime();  
	}

	/**
	 * Este método devuelve un Date, con el número de horas, minutos , segundos, ms, al fin del dia.
	 * @param pDate Fecha base
	 * @return Date Fecha con los valores inicializados al fin del dia por lo que respecta al tiempo (hora, minutos,...)
	 */
	public static Date getEndDay (Date pDate) 
	{
		Calendar c = Calendar.getInstance();
		c.setTime(pDate);
		c.set(Calendar.HOUR_OF_DAY,23);
		c.set(Calendar.MINUTE,59);
		c.set(Calendar.SECOND,59);			
		c.set(Calendar.MILLISECOND,999);		
		return c.getTime();  
	}

	/**
	 * Este método devuelve un booleano dependiendo si la fecha pDate está en el intervalo de pStartDate y pEndDate.
	 *  Estas comprobaciones se realizan sin tener en cuenta las horas, minutos, ..., sinó que se realizan a nivel de
	 *  fecha (año, mes y dia)
	 * @param pStartDate
	 * @param pEndDate
	 * @param pDate
	 * @return
	 */
	public static boolean isBeetwenTime (Date pStartDate, Date pEndDate, Date pDate)
	{
		boolean bisBetweenTime = false;
		// 1. Obtenemos un calendar de la fecha de inicio con valores inicializados a 0.
		Date _dStartDate = getStartDay(pStartDate);
		Calendar _cStartDate = Calendar.getInstance();
		_cStartDate.setTime(_dStartDate);
		
		// 2. Obtenemos un calendar de la fecha de fin con los valores de fin de dia.
		Date _dEndDate = getEndDay(pEndDate);		
		Calendar _cEndDate = Calendar.getInstance();
		_cEndDate.setTime(_dEndDate);
		
		// 3. Obtenemos un calendar con los valores inicializados a 0, excepto el segundo para poder comparar.
		Date _dCompareWith = getStartDay(pDate);
		Calendar _cCompareWith = Calendar.getInstance();		
		_cCompareWith.setTime(_dCompareWith);
		_cCompareWith.add(Calendar.SECOND,1);
		
		//if (_cCompareWith.before(_cStartDate) && _cCompareWith.after(_cEndDate)) {
		if (_cStartDate.before(_cCompareWith) && _cEndDate.after(_cCompareWith)) {
			bisBetweenTime = true;			
		}
		return bisBetweenTime;

	}
	
	public static Date getDate(String pszDate, String psdfFormat)	  
	{
		String date= null;
		java.util.Date realDate = null;
		SimpleDateFormat fmt = new SimpleDateFormat(psdfFormat); 
		try { 		
			if(pszDate != null  && !pszDate.equals("") ){
				date = pszDate;
			}
			if(date!=null && !date.equals("")){
				realDate = fmt.parse(date);
			}else{
				realDate = new java.util.Date();
				date = fmt.format(realDate); 
			}
		}catch(java.text.ParseException pe){
			//realDate = new java.util.Date();
			//date = fmt.format(realDate);
			return null; 
		}catch(Exception ex){
			ex.printStackTrace();
		}	
		return realDate;
	}
	
	/**
	 * Método que devuelve la fecha en formato deseado, en este caso, entra en un formato y sale con formato  pasado por parámetro
	 * @param pszDate
	 * @param psdfFormatInput
	 * @param psdfFormatOutput
	 * @return
	 */
	public static String getDate(String pszDate, String psdfFormatInput, String psdfFormatOutput) {
	    String date = null;
	    java.util.Date realDate = null;
	    SimpleDateFormat fmtInput = new SimpleDateFormat(psdfFormatInput);
	    SimpleDateFormat fmtOutput = new SimpleDateFormat(psdfFormatOutput);
	    try {
	        if (pszDate != null && !pszDate.equals("")) {
	            date = pszDate;
	        } else
	        	return null;
	        if (date != null && !date.equals("")) {
	            realDate = fmtInput.parse(date);
	        } else {
	            realDate = new java.util.Date();
	            date = fmtInput.format(realDate);
	        }
	    } catch (java.text.ParseException pe) {
	        pe.printStackTrace();
	        return null;
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	    return fmtOutput.format(realDate);
	}
	
	/**
	 * Método que devuelve la fecha en formato deseado, en este caso, entra en un formato y sale con formato  pasado por parámetro. Si no hay fecha se devuelve una por defecto
	 * @param pszDate
	 * @param psdfFormatInput
	 * @param psdfFormatOutput
	 * @return
	 */
	public static String getDateOrDateDefault(String pszDate, String psdfFormatInput, String psdfFormatOutput) {
		
		String date = getDate( pszDate,  psdfFormatInput,  psdfFormatOutput) ;
	   
	    if(date==null)	 date=getDate( "31/12/2099",  psdfFormatInput,  psdfFormatOutput) ;
	    
	    return date;
	}
	
	
	
	
	/**
	 * Este método devuelve el número de minutos a partir de una fecha de 
	 *  entrada. Se tiene en cuenta los datos referente al tiempo (horas y minutos)
	 *  y no dias, años, etc.
	 * @param pDate Fecha de entrada
	 * @return Long número de minutos que representa el Date
	 */
	public static Long getDateTimeInMinutes (java.util.Date pDate)
	{
		Long _lRet = null;
		Calendar c = Calendar.getInstance();
		c.setTime(pDate);
		int _iHour   = c.get(Calendar.HOUR_OF_DAY);
		int _iMinute = c.get(Calendar.MINUTE);
		
		_lRet = new Long( _iHour * 60 + _iMinute) ; 
		
		return _lRet;	
		
	}
	
	/**
	 * Este método devuelve una cadena (String) con el formato deseado.
	 * @param sdf SimpleDateFormat (formato que será el que se devolverá)
	 * @param date Fecha de entrada
	 * @return String Fecha con el formato establecido en el parámetro de entrada sdf
	 */
	public static String getDatetoString(SimpleDateFormat sdf, Date date)
	{

		String _cszDateFormated;
		_cszDateFormated = sdf.format(date);
		return _cszDateFormated;					
	}

	/**
	 * Este método devuelve una cadena (String) con el formato deseado.
	 * @param format String (formato que será el que se devolverá)
	 * @param date Fecha de entrada
	 * @return String Fecha con el formato establecido en el parámetro de entrada sdf
	 */	
	public static String getDatetoString(String format, Date date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return getDatetoString(sdf, date);
	}
	
	public static Date addDate(Date d, int numDays)
	{
		Calendar cal = getCalendar(d);
		cal.add(Calendar.DAY_OF_YEAR, numDays);
		return cal.getTime();
	}

	public static String addDateString(Date d, int numDays, String format)
	{
		return getDatetoString("dd/MM/yyyy", addDate(d, numDays));
	}
	
	public static long getLengthInMinutes(Date pStartdate, Date pEnddate)
	{
		int timer1 = (int) getDateTimeInMinutes(pStartdate).intValue();
		int timer2 = (int) getDateTimeInMinutes(pEnddate).intValue();
		// Duración en milisegundos
		long length= pEnddate.getTime()-pStartdate.getTime();
		// Duracion en segundos 
		length/=1000;
		// Duracion en minutos
		length/=60;
				
		return length;
	}
	
	
	public static long getLengthInSeconds(Date pStartdate, Date pEnddate)
	{
		int timer1 = (int) getDateTimeInMinutes(pStartdate).intValue();
		int timer2 = (int) getDateTimeInMinutes(pEnddate).intValue();
		// Duración en milisegundos
		long length= pEnddate.getTime()-pStartdate.getTime();
		// Duracion en segundos 
		length/=1000;
		return length;
	}
	
	
	 
	public static long getLengthInDays(Date pStartdate, Date pEnddate)
	{
		if(pStartdate==null || pEnddate==null) return -1;
		// Duración en milisegundos
		long length= pEnddate.getTime()-pStartdate.getTime();
		// Duracion en segundos 
		length/=1000;
		// Duracion en minutos
		length/=60;
		//Duracion en horas
		length/=60;
		
		
		long j = length/24;
		long i = length%24;
		if(i==23)
			length+=1;


		//Duracion en dias
		
		length/=24;
		return length;
	}
	
	public static long getLengthInYears(Date pStartdate, Date pEnddate)
	{
		// Duración en milisegundos
		long length= pEnddate.getTime()-pStartdate.getTime();
		// Duracion en segundos 
		length/=1000;
		// Duracion en minutos
		length/=60;
		//Duracion en horas
		length/=60;
		//
	
	
		long j = length/24;
		long i = length%24;
		if(i==23)
			length+=1;


		//Duracion en dias
	
		length/=24;
		length/=365;
		return length;
	}
	
	public static Date actualDate(){ 
		String fecha = ""; 
		Date today=new Date();
		//SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); 
		//fecha = sdf.format(today); 
		return today;
	} 

	
	public static List getWeekdaysBetween( Date d1, Date d2 )
	{
		return getWeekdaysBetween(d1, d2, true, true, true, true, true, true, true);
	}

	/**
	 * Devuelve los dias entre las 2 fechas que sean lunes, martes, ... de acuerdo con los parametros monday, tuesday ...
	 * 
	 * @param d1 fecha inicial. menor o igual que d2
	 * @param d2 fecha final. mayor o igual que d1
	 * @param monday queremos que los lunes se incluyan en la lista?
	 * @param tuesday queremos que los martes se incluyan en la lista?
	 * @param wednesday queremos que los miercoles se incluyan en la lista?
	 * @param thursday queremos que los jueves se incluyan en la lista?
	 * @param friday queremos que los viernes se incluyan en la lista?
	 * @param saturday queremos que los sabados se incluyan en la lista?
	 * @param sunday queremos que los domingos se incluyan en la lista?
	 * @return
	 */
	public static List getWeekdaysBetween( Date d1, Date d2, boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday, boolean saturday, boolean sunday) 
	{
		List days = new ArrayList();

		Calendar cal1 = getCalendar( d1 );
		Calendar cal2 = getCalendar( d2 );
		
		if ( cal1.after(cal2) )
			return days;
		
		do 
		{
			if ( monday && cal1.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY)
				days.add( cal1.getTime() );
			else if ( tuesday && cal1.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY)
				days.add( cal1.getTime() );
			else if ( wednesday && cal1.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY)
				days.add( cal1.getTime() );
			else if ( thursday && cal1.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY)
				days.add( cal1.getTime() );
			else if ( friday && cal1.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)
				days.add( cal1.getTime() );
			else if ( saturday && cal1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
				days.add( cal1.getTime() );
			else if ( sunday && cal1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
				days.add( cal1.getTime() );

			// avanzo 1 dia
			cal1.add( Calendar.DATE, 1 );
		} 
		while ( cal1.before(cal2) || cal1.equals(cal2) );
		
		return days;
	}
	
	
	public static List getWeekdaysBetween( Date d1, Date d2, boolean weekdays[])
	{
		return getWeekdaysBetween( d1, d2, weekdays[0], weekdays[1], weekdays[2], weekdays[3], weekdays[4], weekdays[5], weekdays[6]);
	}
	
	/**
	 * Devuelve todos los dias HABILES entre las 2 fechas
	 *  
	 * @param d1 fecha inicial
	 * @param d2 fecha final
	 * @param weekdays dias de la semana que queremos
	 * @param calLocales calendario de referencia para quitar los dias festivos locales
	 * @param calNacionales calendario de referencia para quitar los dias festivos nacionales
	 
	private static List getWeekdaysBetweenExcludeFeast( Date d1, Date d2, boolean weekdays[],  lopicost.spd.model.Calendar calLocales,  lopicost.spd.model.Calendar calNacionales )
	{
		List all = getWeekdaysBetween( d1, d2, weekdays );

		return all;
	}
*/

	
	public static Calendar getCalendar( Date d )
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime( d );
		//cal.setFirstDayOfWeek( Calendar.MONDAY );
		
		return cal;
	}
	
	/**
	 * Esta la fecha d en los dias definidos por weekdays?
	 */
	public static boolean isInWeekdays( Date d, long weekdays )
	{
		boolean days[] = StringUtil.decimalToBinaryArray( String.valueOf(weekdays), 7);
		int weekday = getWeekday( d );
		
		switch ( weekday ) {
			case Calendar.MONDAY: 		return days[0];
			case Calendar.TUESDAY: 		return days[1];
			case Calendar.WEDNESDAY: 	return days[2];
			case Calendar.THURSDAY: 	return days[3];
			case Calendar.FRIDAY: 		return days[4];
			case Calendar.SATURDAY: 	return days[5];
			case Calendar.SUNDAY: 		return days[6];
		}
		
		return false;
	}
	
	
	/**
	 * Este fecha a que dia de la semana se corresponde?
	 * @return un entero de acuerdo con Calendar
	 */
	public static int getWeekday( Date d )
	{
		return getCalendar( d ).get( Calendar.DAY_OF_WEEK );
	}
	
	/**
	 * Retorna quin dia de la setmana es correspon la data en format binari
	 * Ex: dimarts:  0100000
	 * Ex: dissabte: 0000010
	 */
	public static String getWeekdayAsBinary( Date d )
	{
		int weekday = getWeekday(d);
		String s = "";
		s += weekday == 0 ? "1" : "0"; 
		s += weekday == 1 ? "1" : "0"; 
		s += weekday == 2 ? "1" : "0"; 
		s += weekday == 3 ? "1" : "0"; 
		s += weekday == 4 ? "1" : "0"; 
		s += weekday == 5 ? "1" : "0"; 
		s += weekday == 6 ? "1" : "0"; 
		s += weekday == 7 ? "1" : "0";
		return s;
	}
	
	
	/**
	 * Retorna el weekdays como un string separados por comas. Ej: 1,2,3,4,5,6,7
	 */
	public static String getWeekdaysString(long weekdays)
	{
		boolean days[] = StringUtil.decimalToBinaryArray(String.valueOf(weekdays), 7);
		String result="";
		for (int i = 0; i < days.length; i++) {
			Boolean bool = new Boolean(days[i]);  
			if (bool.booleanValue()){
				if (result.length()>0){
					result+=","+new Integer(i+1).toString();
				}else{
					result+=new Integer(i+1).toString();
				}
			}
		}
		return result;
	}
	
	
	/**
	 * Retorna 01/mesactual/anyactual
	 */
	public static Date getNowAsFirstOfMonth()
	{
		return getDateAsFirstOfMonth(new Date());
	}
	
	public static String getNowAsFirstOfMonthStr()
	{
		 return getDatetoString("dd/MM/yyyy", getNowAsFirstOfMonth());
	}
	
	
	public static Date getDateAsFirstOfMonth(Date d)
	{
		String mm = getDatetoString("MM", d);
		String yyyy = getDatetoString("yyyy", d);
		return getDate("01/"+mm+"/"+yyyy, "dd/MM/yyyy");
	}
	
	
	public static Date addMonth(Date d, int numMonths)
	{
		Calendar cal = DateUtilities.getCalendar(d);
		cal.add(Calendar.MONTH, numMonths);
		return cal.getTime();
	}
	
	public static Date addYear(Date d, int numYears)
	{
		Calendar cal = DateUtilities.getCalendar(d);
		cal.add(Calendar.YEAR, numYears);
		return cal.getTime();
	}
	
	
	public static List getMonthsBetween(Date d1, Date d2)
	{
		List months = new ArrayList();
		
		if (d2.compareTo(d1) > 0)
		{
			Calendar cal1 = getCalendar(d1);
			Calendar cal2 = getCalendar(d2);
			
			while (cal1.before(cal2))
			{
				cal1.add(Calendar.MONTH, 1);
				
				if (cal1.before(cal2))
					months.add(cal1.getTime());
			}
		}
		
		return months;
	}
	
	/*
	public static Date getDateFromDateAndHour(Date d, Long h)
	{
		String dateStr = getDatetoString("dd/MM/yyyy", d);
		String hourStr = ProgrammingUtils.getDateFromNumeric(h);
		Date fullDate = getDate(dateStr+" "+hourStr, "dd/MM/yyyy hh:mm");
		return fullDate;
	}
	*/
	/**
	 * Devuelve el Date correspondiente a una fecha formateada
	 * @param fecha fecha formateada del tipo DD/MM/AAAA o DD/MM/AA
	 * @return Date
	 */
/*	public static Date getFechaDesdeString(String fecha)
	{		  
	  java.util.Calendar c=java.util.Calendar.getInstance();	
	  if (fecha==null || "".equals(fecha)) return null;	  		  
	  int day=Integer.parseInt(fecha.substring(0,2));
	  int mes=Integer.parseInt(fecha.substring(3,5));
	  int anyo=Integer.parseInt(fecha.substring(6));
	  if (anyo<2000) //Tratamos fechas cortas
	  	anyo+=2000;
	  c.set(java.util.Calendar.DAY_OF_MONTH,day);
	  c.set(java.util.Calendar.MONTH,mes-1);
	  c.set(java.util.Calendar.YEAR,anyo);
	  return c.getTime();
	}				
*/
	
	/**
	 * Controla la validez de una fecha en String, con un formato determinado
	 * @param date
	 * @param dateFormat
	 * @return
	 */
	
	public static boolean isDateValid(String date, String dateFormat) 
	{
		//String DATE_FORMAT = "dd/MM/yyyy";
	        try {
	    		if(date!=null)
	    		{
	    			
		            DateFormat df = new SimpleDateFormat(dateFormat);
		            df.setLenient(false);
	    			Date parsedDate = df.parse(date);
		            df.parse(date);
		            if (!date.equals(df.format(parsedDate))) 
		            	return false;
		            
	    			return true;
 	    		}else return false;
	        } catch (ParseException e) {
	            return false;
	        }
	}
	
	


	  public static String convertSerialNumberToDate(long serialNumber, String format) {
		    Calendar calendar = Calendar.getInstance();
		    calendar.set(1900, 0, 1); // Establece la fecha base de Excel (1 de enero de 1900)
		    calendar.add(Calendar.DAY_OF_YEAR, (int) serialNumber - 2); // Suma los días del número de serie (ajustado por el error del año bisiesto)
		    Date date = calendar.getTime(); // Obtiene la fecha como un objeto Date
		    SimpleDateFormat sdf = new SimpleDateFormat(format); // Establece el formato de fecha deseado
		    return sdf.format(date); // Devuelve la fecha como una cadena en el formato deseado
		  }
	  
	  
	  public static String convertFormatDateString(String fechaEntrada, String formatoEntrada, String formatoSalida) {
	       
		  if(fechaEntrada==null ||  fechaEntrada.equals("")) return "";

		  String fechaFormateada = "";
		  SimpleDateFormat sdfEntrada = new SimpleDateFormat(formatoEntrada);
		  SimpleDateFormat sdfSalida = new SimpleDateFormat(formatoSalida);
		  
		  try {
			  // Convertir el String a un objeto Date
			  Date fecha = sdfEntrada.parse(fechaEntrada);

			  // Formatear la fecha según el formato deseado
			  fechaFormateada = sdfSalida.format(fecha);

	            System.out.println("Fecha formateada: " + fechaFormateada);
	        } catch (ParseException e) {
	            //e.printStackTrace();
	        	return fechaEntrada;
	        }
	        return fechaFormateada;
	    }  
	  
	  public static Date cambiarDiaDeFecha(Date fechaOriginal, int nuevoDia) {
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(fechaOriginal);
	        calendar.set(Calendar.DAY_OF_MONTH, nuevoDia);
	        return calendar.getTime();
	    }
	  
	  
	  public static String desplazarFecha(String fecha, int dias, String formatoEntrada, String formatoSalida) {
	      try
	      {
		        DateTimeFormatter formatterEntrada = DateTimeFormatter.ofPattern(formatoEntrada);
		        DateTimeFormatter formatterSalida = DateTimeFormatter.ofPattern(formatoSalida);

		        // Parsear la fecha, desplazar y devolver en el nuevo formato
		        LocalDate fechaOriginal = LocalDate.parse(fecha, formatterEntrada);
		        LocalDate fechaDesplazada = fechaOriginal.plusDays(dias);
		        return fechaDesplazada.format(formatterSalida);
	    	  
	      }
	      catch(Exception e)
	      {
	    	  return fecha;
	      }
	    }

	    public static int diasEntreFechas(String fechaInicio, String fechaFin, String formatoFecha) {
	    	try{
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatoFecha);
		        LocalDate inicio = LocalDate.parse(fechaInicio, formatter);
		        LocalDate fin = LocalDate.parse(fechaFin, formatter);
		        return (int) ChronoUnit.DAYS.between(inicio, fin);
	    	}
	    	catch(Exception e)
	    	{
	    		return 0;
	    	}
	    }
	    
	    
}

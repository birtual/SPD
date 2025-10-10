package lopicost.spd.utils;


import java.util.*;


/**
 * @see  lopicost.spd.utils.MessageManager
 */
	public class TextManager 
	{
		private static ResourceBundle literals=null;
		private static ResourceBundle literalsLogs=null;
		public static String getMensaje(String literal){
			try{
				if( literals==null ) literals = ResourceBundle.getBundle( SPDConstants.MESSAGE_RESOURCE_PROPERTIES );
				//para idiomas - ResourceBundle bundle = ResourceBundle.getBundle("SPDMessageResources", new Locale("es", "ES"));
				return literals.getString(literal);
			}catch(Exception e){
				return "!["+literal+"]";
			}
		}

		

		
		public static boolean isEmptyOrNull(String cadena)
		{
			return (cadena==null || cadena.equals(""));
		}

		public static boolean isNotEmpty(String cadena)
		{
			return (cadena!=null && !cadena.equals(""));
		}

		/**
		 * Devuelve el mensaje con clave key
		 * @param key String con la clave en el SPDLog.properties
		 * @param textForReplacing String texto que reemplazara a las @@ que haya en el mensaje
		 * @return String formateado
		 */
		public static String getFormattedLogMessage(String key,String textForReplacing)
		{
			String mess=TextManager.getLog(key);
			StringBuffer sb=new StringBuffer(mess);
			while (mess.indexOf("@@")>=0)
			{
				sb.replace(mess.indexOf("@@"),mess.indexOf("@@")+2,textForReplacing);
				mess=sb.toString();
			}
			return mess;
			
		}
		/**
		 * Devuelve el mensaje con clave key
		 * @param key String con la clave en el SPDMessage.properties
		 * @param textForReplacing String texto que reemplazara a las @@ que haya en el mensaje
		 * @return String formateado
		 */
		public static String getFormattedLogMessage(String key,String[] textForReplacing)
		{
			String mess=TextManager.getLog(key);
			StringBuffer sb=new StringBuffer(mess);
			int count=0;
			while (mess.indexOf("@@")>=0 && count<textForReplacing.length)
			{
				sb.replace(mess.indexOf("@@"),mess.indexOf("@@")+2,textForReplacing[count]);
				mess=sb.toString();
				count++;
			}
			return mess;
			
		}	
		
		public static String getLog(String literal){
			try{
				if( literalsLogs==null ) literalsLogs = ResourceBundle.getBundle("lopicost.config.pool.dbaccess."+SPDConstants.MESSAGE_LOGS_PROPERTIES);
				return literalsLogs.getString(literal);
			}catch(Exception e){
				return "!["+literal+"]";
			}
		}
		
}
	



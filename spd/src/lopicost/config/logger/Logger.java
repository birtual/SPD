package lopicost.config.logger;

import java.io.PrintStream;
import java.util.Date;

/**
 * Gestion de logs con log4j
 */
public class Logger 
{
	public static final int DEBUG= 0;
	public static final int INFO= 1;
	public static final int WARNING= 2;
	public static final int ERROR= 3;
	public static final int FATAL= 4;
	
	protected static org.apache.log4j.Logger rootLogger=org.apache.log4j.Logger.getLogger("SPDLogger");
	protected static org.apache.log4j.Logger fatalLogger=org.apache.log4j.Logger.getLogger("FatalLogger");

	
	/**
	 * Almacenamos el log en los loggers por defecto.
	 * @param msg Mensaje a loggear
	 * @param level Nivel de log
	 */
	public static void log(String msg,int level)
	{
		switch (level)
		{
			// good practice: optimizamos con el isDebugEnabled()
			case DEBUG: if (rootLogger.isDebugEnabled()) rootLogger.debug(msg);
			break;
			case INFO: rootLogger.info(msg);
			break;
			case WARNING: rootLogger.warn(msg);
			break;
			case ERROR: rootLogger.error(msg);
			break;
			case FATAL: fatalLogger.fatal(msg);
			break;
		}
	}

	/**
	 * Almacenamos el log en los loggers por defecto.
	 * @param logId Identificador del log
	 * @param msg Mensaje a loggear
	 * @param level Nivel de log
	 */
	public static void log(String logId, String msg,int level)
	{
		org.apache.log4j.Logger logger= org.apache.log4j.Logger.getLogger(logId);
		switch (level)
		{
			// good practice: optimizamos con el isDebugEnabled()
			case DEBUG: if (logger.isDebugEnabled()) logger.debug(new Date() +  " - " + msg);
			break;
			case INFO: logger.info(new Date() +  " - " + msg);
			break;
			case WARNING: logger.warn(new Date() +  " - " + msg);
			break;
			case ERROR: logger.error(new Date() +  " - " + msg);
			break;
			case FATAL: fatalLogger.fatal(new Date() +  " - " + msg);
			break;
		}
	}
	
	
	/**
	 * Escribe en log de error con la traza completa de la excepcion e
	 */
	public static void logerror( String msg, Throwable e)
	{
		rootLogger.error( msg, e );
	}
	
	/**
	 * Devuelve el rootLogger.
	 * 
	 * El problema de llamar a los metodos log de esta clase es que en los logs aparece
	 * siempre la misma traza (lopicost.config.logger.Logger) y no ayuda. Haciendo log sobre el
	 * Logger devuelto por este metodo conseguimos que en la traza aparezca el nombre de la
	 * clase que está generando el log. 
	 */
	public static org.apache.log4j.Logger get()
	{
		return rootLogger;
	}
	
	/**
	 * Devuelve el logger logId
	 * 
	 * El problema de llamar a los metodos log de esta clase es que en los logs aparece
	 * siempre la misma traza (lopicost.config.logger.Logger) y no ayuda. Haciendo log sobre el
	 * Logger devuelto por este metodo conseguimos que en la traza aparezca el nombre de la
	 * clase que está generando el log. 
	 */
	public static org.apache.log4j.Logger get( String logId )
	{
		return org.apache.log4j.Logger.getLogger(logId);
	}
	
    /**
     * Redirigir System.out y System.err a Log4j.
     */
    public static void redirectSystemStreams() {
        System.setOut(new PrintStream(new LogOutputStream(rootLogger, INFO), true));
        System.setErr(new PrintStream(new LogOutputStream(rootLogger, ERROR), true));
    }
	
}


package lopicost.spd.utils;

import lopicost.spd.commons.struts.SPDClientMessageResourcesFactory;
import java.util.Locale;
import org.apache.struts.util.MessageResources;

/**
 * Utilitat per accedir als literals de struts.
 * Suporta multiidioma i literals especifics.
 */
public class MessageManager {
	
	private static final MessageManager instance = new MessageManager();
	
	private MessageResources resources;

	protected MessageManager() {
		super();
		SPDClientMessageResourcesFactory factory = new SPDClientMessageResourcesFactory();
		resources = factory.createResources(SPDConstants.MESSAGE_RESOURCE_PROPERTIES);
	}
	
	/**
	 * Devuelve la instancia de esta clase asociada al locale del usuario
	 */
	public static MessageManager instance()
	{
		return instance;
	}

	/**
	 * Obtiene el literal, sin parametros
	*/ 
	public String message(String key)
	{
		return message( key, null, null, null, null);
	}
	
	/**
	 * Obtiene el literal, con un parametro
	*/ 
	public String message(String key, Object arg1)
	{
		return message( key, arg1, null, null, null);
	}
	 
	/**
	 * Obtiene el literal, con dos parametros
	*/
	public String message(String key, Object arg1, Object arg2)
	{
		return message( key, arg1, arg2, null, null);
	}
	 
	/**
	 * Obtiene el literal, con tres parametros
	 */
	public String message(String key, Object arg1, Object arg2, Object arg3)
	{
		return message( key, arg1, arg2, arg3, null);
	}
	
	/**
	 * Obtiene el literal, con cuatro parametros
	 */
	public String message(String key, Object arg1, Object arg2, Object arg3, Object arg4)
	{
		return doMessage( key, new Object[] { arg1, arg2, arg3, arg4 } );
		
	}
	
	public String message(String key, Object[] args)
	{
		return doMessage(key, args);
	}
	
	protected String doMessage(String key, Object [] args)
	{
		String msg = "???" + key + "???";
		
		if (resources != null) {
			String str = resources.getMessage(getLocale(), key, args);
			if (str != null) msg = str;
		}
		
		return msg;
	}
	
	
	protected Locale getLocale()
	{

		return null;
	}

	
	
	

}

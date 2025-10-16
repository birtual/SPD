package lopicost.commons.struts;

import java.util.Locale;

import org.apache.log4j.Logger;
import org.apache.struts.util.MessageResourcesFactory;
import org.apache.struts.util.PropertyMessageResources;

/**
 * Gestió dels literals de struts.
 * 
 * Primer va a buscar el literal al fitxer de literals específic del client
 * i en cas que no el trobi després va a buscar al fitxer comú de literals. 
 */
public class ClientMessageResources extends PropertyMessageResources 
{
	// fitxer de literals especific
	private PropertyMessageResources specificResources;

	/**
	 * @param factory La factoria que ha creat aquest ClientMessageResources
	 * @param messagesFile nom del fitxer dels literals comuns a tots els clients (sense idioma_pais ni .properties)
	 * @param specificMessagesFile nom del fitxer dels literals específic del clients (sense idioma_pais ni .properties).
	 * 								Pot ser null, en aquest cas no hi ha fitxer especific
	 */
	public ClientMessageResources(MessageResourcesFactory factory, String messagesFile, String specificMessagesFile) {
		super(factory, messagesFile);
		
		if (specificMessagesFile != null)
			specificResources = new PropertyMessageResources(factory, specificMessagesFile);
	}

	/**
	 * Primer busca al fitxer de literals especifics
	 * Si no el troba despres busca al fitxer comu
	 */
	public String getMessage(Locale locale, String key) 
	{
		String msg = null;
		
		// 1. vaig a buscar al fitxer de literals especifics
		if (specificResources != null) { 
			if (specificResources.isPresent(locale, key)) {
				msg = specificResources.getMessage(locale, key);
			}
		}
		
		// 2. si no el trobo vaig al fitxer comu
		if (msg == null) {
			msg = super.getMessage(locale, key);
			if (msg.startsWith("???") && msg.endsWith("???")) {
				// el literal no existeix, escric un warn per poder localitzar-lo i arreglar
				getLog().warn("Literal no trobat: "+key);
			}
		}
		
		return msg;
	}
	
	protected Logger getLog() {
		return Logger.getLogger(ClientMessageResources.class);
	}
	

}

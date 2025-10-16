package lopicost.commons.struts;

import lopicost.commons.struts.ClientMessageResources;

import org.apache.struts.util.MessageResources;
import org.apache.struts.util.MessageResourcesFactory;

/**
 * Crea instancies d'objectes ClientMessageResources
 */
public abstract class ClientMessageResourcesFactory extends MessageResourcesFactory {
	

	/**
	 * Crea un nou ClientMessageResources
	 */
	public MessageResources createResources(String messagesFile) 
	{
		return new ClientMessageResources(this, messagesFile, getSpecificMessagesFile(messagesFile));
	}

	/**
	 * Retorna el nom del fitxer de literals especific.
	 * Es passa per paràmetre el nom del fitxer de literals comú per si es vol
	 * concatenar alguna cosa per tal d'identificar automàticament el fitxer especific
	 * Retorna NULL en cas que no hi hagi fitxer especific
	 */
	public abstract String getSpecificMessagesFile(String messagesFile);
	
}

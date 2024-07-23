package lopicost.spd.commons.struts;

import lopicost.commons.struts.ClientMessageResourcesFactory;

import org.apache.struts.util.MessageResources;

/**
 * Crea instancies d'objectes SPDClientMessageResources
 */
public class SPDClientMessageResourcesFactory extends ClientMessageResourcesFactory {

	/**
	 * Concatena "Client" al nom del fitxer
	 */
	public String getSpecificMessagesFile(String messagesFile) {
		return messagesFile+"Client";
	}

	public MessageResources createResources(String messagesFile) {
		return new SPDClientMessageResources(this, messagesFile, getSpecificMessagesFile(messagesFile));
	}

}

package lopicost.spd.commons.struts;

import org.apache.log4j.Logger;
import org.apache.struts.util.MessageResourcesFactory;

import lopicost.commons.struts.ClientMessageResources;

public class SPDClientMessageResources extends ClientMessageResources {

	public SPDClientMessageResources(MessageResourcesFactory factory, String messagesFile, String specificMessagesFile) {
		super(factory, messagesFile, specificMessagesFile);
	}

	protected Logger getLog() {
		return lopicost.config.logger.Logger.get();
	}

}

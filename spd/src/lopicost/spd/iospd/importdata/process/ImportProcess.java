
package lopicost.spd.iospd.importdata.process;

import  lopicost.spd.iospd.Process;
import  lopicost.spd.iospd.connectors.Connector;

import java.io.IOException;


public interface ImportProcess extends Process
{
	public void init(String _fileInput, Connector reader) throws IOException;
	public void start() throws IOException, Exception;

}

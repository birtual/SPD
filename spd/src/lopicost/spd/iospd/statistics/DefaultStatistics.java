package lopicost.spd.iospd.statistics;

import lopicost.spd.iospd.Process;

import java.util.List;

/**
 * Implementacion por defecto de las estadisticas
 */
public class DefaultStatistics implements ProcessStatistics {

	private Process process;
	
	public DefaultStatistics( Process process ) {
		super();
		this.process = process;
	}

	public int getProcessedRowsCount() {
		return process.getProcessedRows();
	}

	public int getErrorsRowsCount() {
		return process.getErrors().size();
	}

	public List getErrors() {
		return process.getErrors();
	}
	
	public int getOkRowsCount()
	{
		return getProcessedRowsCount() - getErrorsRowsCount();
	}
	
	

}

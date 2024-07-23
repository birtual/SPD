
package lopicost.spd.iospd.importdata.struts.form;


import java.util.List;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;


public class ImportDataForm extends ActionForm 
{
	
	private String operation="FILTER";
	private String idThread= null;
	private String parameter = " ";

	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	private int filesProcessades=0;
	private String idUsuario="";
	private FormFile file = null; // Para el upload de la documentación
	private String filenameIn= "";
	private String filenameOut= "";
	private String exportType= "";
	private String fileType= "";
    private List errors;
    private List readers;
    private List process;
    private String end="";
    private String descProcess="";
    private String descReader="";
    private String type="";
    private List lstDivisionResidencias;
	private String idDivisionResidencia="";
	private String idProceso=""; 
    private String idProcessIospd=""; 
    private String fechaInicioSpd=""; 
    private String fechaFinSpd=""; 
    private List listaRobots;
	private String idRobot="";
	private boolean existePlantilla=false;
	private boolean cargaExtra=false;


	public String getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getFechaInicioSpd() {
		return fechaInicioSpd;
	}
	public void setFechaInicioSpd(String fechaInicioSpd) {
		this.fechaInicioSpd = fechaInicioSpd;
	}
	public String getFechaFinSpd() {
		return fechaFinSpd;
	}
	public void setFechaFinSpd(String fechaFinSpd) {
		this.fechaFinSpd = fechaFinSpd;
	}
	public String getIdProcessIospd() {
		return idProcessIospd;
	}
	public void setIdProcessIospd(String idProcessIospd) {
		this.idProcessIospd = idProcessIospd;
	}
	public List getLstDivisionResidencias() {
		return lstDivisionResidencias;
	}
	public void setLstDivisionResidencias(List lstDivisionResidencias) {
		this.lstDivisionResidencias = lstDivisionResidencias;
	}
	
    public String getIdDivisionResidencia() {
		return idDivisionResidencia;
	}
	public void setIdDivisionResidencia(String idDivisionResidencia) {
		this.idDivisionResidencia = idDivisionResidencia;
	}
	/**
     * @return Returns the exportType.
     */
    public String getExportType() {
        return exportType;
    }
    /**
     * @param exportType The exportType to set.
     */
    public void setExportType(String exportType) {
        this.exportType = exportType;
    }
	/**
	 * @return
	 */
	public int getFilesProcessades() {
		return filesProcessades;
	}

	/**
	 * @return
	 */
	public String getIdThread() {
		return idThread;
	}

	/**
	 * @return
	 */
	public String getOperation() {
		return operation;
	}

	/**
	 * @param i
	 */
	public void setFilesProcessades(int i) {
		filesProcessades = i;
	}

	/**
	 * @param i
	 */
	public void setIdThread(String i) {
		this.idThread = i;
	}

	/**
	 * @param string
	 */
	public void setOperation(String string) {
		operation = string;
	}

	/**
	 * @return
	 */
	public FormFile getFile() {
		return file;
	}

	/**
	 * @param file
	 */
	public void setFile(FormFile file) {
		this.file = file;
	}

	/**
	 * @return
	 */
	public String getFilenameIn() {
		return filenameIn;
	}

	/**
	 * @return
	 */
	public String getFilenameOut() {
		return filenameOut;
	}

	/**
	 * @param string
	 */
	public void setFilenameIn(String string) {
		filenameIn = string;
	}

	/**
	 * @param string
	 */
	public void setFilenameOut(String string) {
		filenameOut = string;
	}

    /**
     * @return Returns the fileType.
     */
    public String getFileType() {
        return fileType;
    }
    /**
     * @param fileType The fileType to set.
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     * @return Returns the errors.
     */
    public List getErrors() {
        return errors;
    }
    /**
     * @param errors The errors to set.
     */
    public void setErrors(List errors) {
        this.errors = errors;
    }
    /**
     * @return Returns the process.
     */
    public List getProcess() {
        return process;
    }
    /**
     * @param process The process to set.
     */
    public void setProcess(List process) {
        this.process = process;
    }
    /**
     * @return Returns the readers.
     */
    public List getReaders() {
        return readers;
    }
    /**
     * @param readers The readers to set.
     */
    public void setReaders(List readers) {
        this.readers = readers;
    }
    /**
     * @return Returns the reader.
     */
    /**
     * @return Returns the end.
     */
    public String getEnd() {
        return end;
    }
    /**
     * @param end The end to set.
     */
    public void setEnd(String end) {
        this.end = end;
    }

    /**
     * @return Returns the descProcess.
     */
    public String getDescProcess() {
        return descProcess;
    }
    /**
     * @param descProcess The descProcess to set.
     */
    public void setDescProcess(String descProcess) {
        this.descProcess = descProcess;
    }
    /**
     * @return Returns the descReader.
     */
    public String getDescReader() {
        return descReader;
    }
    /**
     * @param descReader The descReader to set.
     */
    public void setDescReader(String descReader) {
        this.descReader = descReader;
    }
    /**
     * @return Returns the type.
     */
    public String getType() {
        return type;
    }
    /**
     * @param type The type to set.
     */
    public void setType(String type) {
        this.type = type;
    }
	public List getListaRobots() {
		return listaRobots;
	}
	public void setListaRobots(List listaRobots) {
		this.listaRobots = listaRobots;
	}
	public String getIdRobot() {
		return idRobot;
	}
	public void setIdRobot(String idRobot) {
		this.idRobot = idRobot;
	}
	public boolean isExistePlantilla() {
		return existePlantilla;
	}
	public void setExistePlantilla(boolean existePlantilla) {
		this.existePlantilla = existePlantilla;
	}

	public String getIdProceso() {
		return idProceso;
	}
	public void setIdProceso(String idProceso) {
		this.idProceso = idProceso;
	}
	public boolean isCargaExtra() {
		return cargaExtra;
	}
	public void setCargaExtra(boolean cargaExtra) {
		this.cargaExtra = cargaExtra;
	}



 }

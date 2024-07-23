package lopicost.spd.iospd.model;

import lopicost.spd.model.GenericSPDEntity;

import java.io.Serializable;

public class IOSpdProcess extends GenericSPDEntity implements Serializable {


    /** identifier field */
    private Long oidprocess;

    /** persistent field */
    private String idprocess;

    /** nullable persistent field */
    private String nameprocess;

    /** nullable persistent field */
    private String classname;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private String type;
    
    /** nullable persistent field */
    private int activo;
    
    /** nullable persistent field */
    private int gestion;
    
    /** full constructor */
    public IOSpdProcess(String idprocess, String nameprocess, String classname,String description,String type, int activo, int gestion) {
        this.idprocess = idprocess;
        this.nameprocess = nameprocess;
        this.classname = classname;
        this.description = description;
        this.type = type;
        this.activo = activo;
        this.gestion = gestion;
        
    }

    public int getActivo() {
		return activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}

	/** default constructor */
    public IOSpdProcess() {
    }

    /** minimal constructor */
    public IOSpdProcess(String idprocess) {
        this.idprocess = idprocess;
    }

    /**
     * @return Returns the classname.
     */
    public String getClassname() {
        return classname;
    }

    /**
     * @param classname The classname to set.
     */
    public void setClassname(String classname) {
        this.classname = classname;
    }

    /**
     * @return Returns the idprocess.
     */
    public String getIdprocess() {
        return idprocess;
    }

    /**
     * @param idprocess The idprocess to set.
     */
    public void setIdprocess(String idprocess) {
        this.idprocess = idprocess;
    }

    /**
     * @return Returns the nameprocess.
     */
    public String getNameprocess() {
        return nameprocess;
    }

    /**
     * @param nameprocess The nameprocess to set.
     */
    public void setNameprocess(String nameprocess) {
        this.nameprocess = nameprocess;
    }

    /**
     * @return Returns the oidprocess.
     */
    public Long getOidprocess() {
        return oidprocess;
    }

    /**
     * @param oidprocess The oidprocess to set.
     */
    public void setOidprocess(Long oidprocess) {
        this.oidprocess = oidprocess;
    }

    /**
     * @return Returns the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
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

	public int getGestion() {
		return gestion;
	}

	public void setGestion(int gestion) {
		this.gestion = gestion;
	}



}

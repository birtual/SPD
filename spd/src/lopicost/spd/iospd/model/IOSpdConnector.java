package lopicost.spd.iospd.model;

import lopicost.spd.model.GenericSPDEntity;

import java.io.Serializable;

public class IOSpdConnector extends GenericSPDEntity implements Serializable {

    /** identifier field */
    private int oidreader;

    /** persistent field */
    private String idreader;

    /** nullable persistent field */
    private String namereader;

    /** nullable persistent field */
    private String classname;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private String type;
    
    /** nullable persistent field */
    private int activo;
    
    /** full constructor */
    public IOSpdConnector(String idreader, String namereader, String classname,String description,String type, int activo) {
        this.idreader = idreader;
        this.namereader = namereader;
        this.classname = classname;
        this.description = description;
        this.type = type;
        this.activo = activo;
    }

    public int getActivo() {
		return activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}

	/** default constructor */
    public IOSpdConnector() {
    }

    /** minimal constructor */
    public IOSpdConnector(String idreader) {
        this.idreader = idreader;
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
     * @return Returns the idreader.
     */
    public String getIdreader() {
        return idreader;
    }

    /**
     * @param idreader The idreader to set.
     */
    public void setIdreader(String idreader) {
        this.idreader = idreader;
    }

    /**
     * @return Returns the namereader.
     */
    public String getNamereader() {
        return namereader;
    }

    /**
     * @param namereader The namereader to set.
     */
    public void setNamereader(String namereader) {
        this.namereader = namereader;
    }

    /**
     * @return Returns the oidreader.
     */
    public int getOidreader() {
        return oidreader;
    }

    /**
     * @param oidreader The oidreader to set.
     */
    public void setOidreader(int oidreader) {
        this.oidreader = oidreader;
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


}

package lopicost.spd.iospd.model;

import lopicost.spd.model.GenericSPDEntity;

import java.io.Serializable;

public class IOSpdReader extends GenericSPDEntity implements Serializable {

    private Long oidreader;
    private String idreader;
    private String namereader;
    private String classname;
    private String description; 
    private int activo;

    public int getActivo() {
		return activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}

	/** full constructor */
    public IOSpdReader(String idreader, String namereader, String classname,String description, int activo) {
        this.idreader = idreader;
        this.namereader = namereader;
        this.classname = classname;
        this.description = description;
        this.activo = activo;
    }

    /** default constructor */
    public IOSpdReader() {
    }

    /** minimal constructor */
    public IOSpdReader(String idreader) {
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
    public Long getOidreader() {
        return oidreader;
    }

    /**
     * @param oidreader The oidreader to set.
     */
    public void setOidreader(Long oidreader) {
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


}

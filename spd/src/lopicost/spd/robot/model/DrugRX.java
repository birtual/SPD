package lopicost.spd.robot.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"code", "val"})
public class DrugRX {
    private String code;
     private String val;
    
    @XmlAttribute
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @XmlAttribute
	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = " " + val;
	}
    
    
}

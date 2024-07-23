package lopicost.spd.robot.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"orderType", "orderNumber", "patientId", "patientName", "startDate", "pouches"})
public class Patient {
    private String orderType;
    private String orderNumber;
    private String startDate;
    private List<Pouch> pouches = new ArrayList<Pouch>();
    private String patientId;
    private String patientName;
    
    @XmlAttribute
    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    @XmlAttribute
    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    @XmlAttribute
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @XmlElement(name = "pouch")
    public List<Pouch> getPouches() {
        return pouches;
    }

    public void setPouches(List<Pouch> pouches) {
        this.pouches = pouches;
    }

    @XmlAttribute
	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

    @XmlAttribute
	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
    
}


package lopicost.spd.robot.model;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import java.util.ArrayList;
import java.util.List;
@XmlRootElement(name = "filiaServiceRequest")
@XmlType(propOrder = {"requestType", "basic", "patients"})
public class FiliaRX {
    private int requestType;
    private Basic basic;
    private List<Patient> patients = new ArrayList<Patient>();

    @XmlElement
    public int getRequestType() {
        return requestType;
    }

    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }

    @XmlElement
    public Basic getBasic() {
        return basic;
    }

    public void setBasic(Basic basic) {
        this.basic = basic;
    }

    @XmlElement(name = "patient")
    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }
}
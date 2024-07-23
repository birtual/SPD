package lopicost.spd.robot.model;

import javax.xml.bind.annotation.XmlAttribute;



public class Basic {
    private String locationId;
    private int machineNumber;

    @XmlAttribute
    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    @XmlAttribute
    public int getMachineNumber() {
        return machineNumber;
    }

    public void setMachineNumber(int machineNumber) {
        this.machineNumber = machineNumber;
    }}
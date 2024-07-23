package lopicost.spd.robot.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"flag", "offsetDays", "doseTime", "layout", "freeInformation", "drugs", "prints"})
@XmlRootElement(name = "pouch")
public class Pouch {
    private int flag;
    private int offsetDays;
    private String doseTime;
    private String layout;
    private String freeInformation;
    private List<DrugRX> drugs = new ArrayList<DrugRX>(); //inicializadas paa añadir directamente
    private List<Print> prints= new ArrayList<Print>();;

    @XmlAttribute
    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    @XmlAttribute
    public int getOffsetDays() {
        return offsetDays;
    }

    public void setOffsetDays(int offsetDays) {
        this.offsetDays = offsetDays;
    }

    @XmlAttribute
    public String getDoseTime() {
        return doseTime;
    }

    public void setDoseTime(String doseTime) {
        this.doseTime = doseTime;
    }

    @XmlAttribute
    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    @XmlAttribute
    public String getFreeInformation() {
        return freeInformation;
    }

    public void setFreeInformation(String freeInformation) {
        this.freeInformation = freeInformation;
    }

    @XmlElement(name = "drug")
    public List<DrugRX> getDrugs() {
        return drugs;
    }

    public void setDrugs(List<DrugRX> drugs) {
        this.drugs = drugs;
    }

    @XmlElement(name = "print")
    public List<Print> getPrints() {
        return prints;
    }

    public void setPrints(List<Print> prints) {
        this.prints = prints;
    }
}

package lopicost.spd.robot.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlRootElement(name = "filiaServiceRequest")
@XmlType(propOrder = {"requestType", "drugs"}) // Orden específico
public class FiliaDM {
    private int requestType=10;
    private List<DrugDM> drugs;

    @XmlElement
    public int getRequestType() {
        return requestType;
    }

    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }

    @XmlElement(name = "drug")
    public List<DrugDM> getDrugs() {
        return drugs;
    }

    public void setDrugs(List<DrugDM> drugs) {
        this.drugs = drugs;
    }
}

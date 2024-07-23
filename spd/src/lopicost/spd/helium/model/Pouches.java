package lopicost.spd.helium.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "pouches")
public class Pouches {
	@XmlElement(name = "pouch")
    private List<Pouch> pouchList = new ArrayList<Pouch>();



    public void setPouchList(List<Pouch> pouches) {
        this.pouchList = pouches;
    }

	public Pouches() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void add(Pouch pouch) {
		pouchList.add(pouch);
		
	}

	public List<Pouch> getList() {
		return pouchList;
	}
    
}
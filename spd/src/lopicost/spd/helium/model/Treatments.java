package lopicost.spd.helium.model;

import java.util.ArrayList;

import java.util.List;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.w3c.dom.Element;

@XmlRootElement(name = "treatments")
public class Treatments {
	@XmlElement(name = "treatment")
	private List<Treatment> treatmentList = new ArrayList<Treatment>();



    public void setTreatmentList(List<Treatment> treatments) {
        this.treatmentList = treatments;
    }

	public Treatments() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void add(Treatment treatment) {
		treatmentList.add(treatment);
		
	}

	public List<Treatment> getList() {
		return treatmentList;
	}

    
}
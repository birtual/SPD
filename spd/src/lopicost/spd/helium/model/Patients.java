package lopicost.spd.helium.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "patients")
public class Patients {
	@XmlElement(name = "patient")
    private List<Patient> patientList = new ArrayList<Patient>();

   
    public List<Patient> getList() {
       return patientList;
   }	

    public void setPatientList(List<Patient> patients) {
        this.patientList = patients;
    }

	public Patients() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void add(Patient patient) {
		patientList.add(patient);
		
	}

    
}
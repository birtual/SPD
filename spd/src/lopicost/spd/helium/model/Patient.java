package lopicost.spd.helium.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.w3c.dom.Element;

import lopicost.spd.helium.helper.HeliumHelper;
import lopicost.spd.utils.DateUtilities;

@XmlRootElement(name = "patient")
@XmlAccessorType(XmlAccessType.FIELD)
public class Patient {
	@XmlAttribute(name = "idPatient")
	private String idPatient;										// Identificador único del paciente dentro del centro. Se puede coger una clave interna del sistema informático siempre vinculada al paciente.
	@XmlTransient	//para que no se muestre en el XML
	private String cip;		
	private boolean active;											// Indicador de si el paciente está o no activo, valores true y false. Si el paciente está activo se podrán generar blisters.
	private Date admissionDate;										// Fecha de ingreso en el centro, su formato es dd-mm-yyyy. Ejemplo: 02-01-2019
	private Date lastAdmissionDate;									// Ultima fecha de ingreso en el centro, su formato es dd-mm-yyyy
	private boolean hospitalized;									// Indicador de si el paciente está o no hospitalizado, valores true y false.
	private String dni;												// Documento nacional de identidad del paciente
	private String name;											// Nombre del paciente
	private String surname1;										// Primer apellido del paciente
	private String surname2;										// Segundo apellido del paciente
	private Date birthday;											// Fecha de nacimiento del paciente en format dd-mm-yyyy
	//private List<Treatment> treatments = new ArrayList<Treatment>();
	//@XmlElement(name = "treatments")
	private Treatments treatments;									  // Conjunto de tratamientos de un paciente o posología
	private String 	floor;											// planta etc.			
	private String room;											// Dirección de la habitación de un paciente en el centro;
	private String idPharmacy;										//Id Farmatic
	//public String getIdPatient() 									{		return idPatient;							}
	public void setIdPatient(String idPatient) 						{		this.idPatient = idPatient;					}
	public String getCip() 											{		return cip;									}
	public void setCip(String cip)									{		this.cip = cip;								}

	public boolean isActive() 										{		return active;								}
	public void setActive(boolean active) 							{		this.active = active;						}
	public Date getAdmissionDate() 									{		return admissionDate;						}
	public void setAdmissionDate(Date admissionDate) 				{		this.admissionDate = admissionDate;			}
	public Date getLastAdmissionDate() 								{		return lastAdmissionDate;					}
	public void setLastAdmissionDate(Date lastAdmissionDate)		{		this.lastAdmissionDate = lastAdmissionDate;	}
	public boolean isHospitalized() 								{		return hospitalized;						}
	public void setHospitalized(boolean hospitalized) 				{		this.hospitalized = hospitalized;			}
	public String getDni() 											{		return dni;									}
	public void setDni(String dni) 									{		this.dni = dni;								}
	public String getName() 										{		return name;								}
	public void setName(String name) 								{		this.name = name;							}
	public String getSurname1() 									{		return surname1;							}
	public void setSurname1(String surname1) 						{		this.surname1 = surname1;					}
	public String getSurname2() 									{		return surname2;							}
	public void setSurname2(String surname2) 						{		this.surname2 = surname2;					}
	public Date getBirthday() 										{		return birthday;							}
	public void setBirthday(Date birthday) 							{		this.birthday = birthday;					}
	public Treatments getTreatments() 								{		return treatments;							}
	public void setTreatments(Treatments treatments) 				{		this.treatments = treatments;				}
	public String getFloor() 										{		return floor;								}
	public void setFloor(String floor) 								{		this.floor = floor;							}
	public String getRoom() 										{		return room;								}
	public void setRoom(String room) 								{		this.room = room;							}
	public String getIdPharmacy() 									{		return idPharmacy;							}
	public void setIdPharmacy(String idPharmacy) 					{		this.idPharmacy = idPharmacy;				}

	public Patient() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Patient(TreeMap doses_TreeMap) {
		
		 Treatments treatments = new Treatments();
		 Treatment treatment = new Treatment();
		 Pouches pouches = new Pouches();
		 this.treatments=treatments;
		 //treatment.setIdTreatment(getCip()+"-T1");
	     treatment.setActive(true);
	     this.treatments.add(treatment);
	     List doses= HeliumHelper.getDosesTreemapToList(doses_TreeMap);
	     Iterator it = doses.iterator();
	     while(it.hasNext())
	     {
	    	 Dose dose = (Dose)it.next();
	    	 Pouch pouch =new Pouch();
	    	 Lines lines =new Lines();
	    //	 pouch.setIdPouch(getTreatments()+"-"+dose.getCodeDose());
	    	 pouch.setDose(dose);
	    	 pouch.setLines(lines);
	    	 pouches.add(pouch);
	     }

	     this.treatments.getList().get(0).setPouches(pouches);

 	
	}
	
	
	
	
}

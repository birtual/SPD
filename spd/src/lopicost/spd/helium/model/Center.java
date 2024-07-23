package lopicost.spd.helium.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@XmlRootElement(name = "careHome")
@XmlAccessorType(XmlAccessType.FIELD)
public class Center {
	@XmlAttribute(name = "idCareHome")
	private String idCareHome;									// idDivisionResidencia - Identificador único nacional relacionado con el centro. Puede estar formado por varias claves como un CIF + código interno del centro.
	@XmlElement
	private boolean active;										// bd_residencia.status - Indica si el centro está o no activo; toma los valores true o false
	@XmlElement
	private String name;										// bd_divisionResidencia.nombreDivisionResidencia  - Nombre del centro
	@XmlElement
	private String city;										// Nombre de la ciudad
	@XmlElement
	private String cp;											// Código postal
	@XmlElement
	private String province;									// Código de provincia
	@XmlElement
	private String phone;										// Teléfono del centro
	@XmlElement
	private String email;										// Email del centro
	@XmlElement
	private String country;										// Nombre del pais
    @XmlElementWrapper(name = "doses")
    @XmlElement(name = "dose")
	private List<Dose> doses = new ArrayList<Dose>();			// Contenido xml con todas las horas de las tomas existentes en el centro
	@XmlElement
	private List<Section> sections = new ArrayList<Section>();	// N estructuras xml con el contenido de varias secciones
	@XmlElement(name = "patients")
	private Patients patients;									// Contenido xml con los pacientes
	@XmlTransient	//para que no se muestre en el XML
	private String idProceso;									// guardará el idProceso que se procesa, se  usará para construir la salida
	
	//public String getIdCareHome() 								{ 		return idCareHome;				}
	public void setIdCareHome(String idCareHome) 				{		this.idCareHome = idCareHome;	}
	public boolean isActive() 									{		return active;					}
	public void setActive(boolean active) 						{		this.active = active;			}
	public String getName() 									{		return name;					}
	public void setName(String name) 							{		this.name = name;				}
	public String getCity() 									{		return city;					}
	public void setCity(String city) 							{		this.city = city;				}
	public String getCp() 										{		return cp;						}
	public void setCp(String cp) 								{		this.cp = cp;					}
	public String getProvince() 								{		return province;				}
	public void setProvince(String province)					{		this.province = province;		}
	public String getPhone() 									{		return phone;					}
	public void setPhone(String phone) 							{		this.phone = phone;				}
	public String getEmail() 									{		return email;					}
	public void setEmail(String email) 							{		this.email = email;				}
	public String getCountry() 									{		return country;					}
	public void setCountry(String country) 						{		this.country = country;			}
	public List<Dose> getDoses() 								{		return doses;					}
	public void setDoses(List<Dose> doses)						{		this.doses = doses;				}
	public List<Section> getSections() 							{		return sections;				}
	public void setSections(List<Section> sections)				{		this.sections = sections;		}
	
	
	// public Patients getPatients() 								{		return patients;			    }
	 //public List<Patient> getPatients()						{       return patients;				}
	public String getIdCenter() {
		return idCareHome;
	}    
	public String getIdProceso() {
		return idProceso;
	}
	public void setIdProceso(String idProceso) {
		this.idProceso = idProceso;
	}
	public void setPatients(Patients patients) 					{		this.patients = patients;		}
	public Center() {
		super();
		// TODO Auto-generated constructor stub
	}


	
	
	
}

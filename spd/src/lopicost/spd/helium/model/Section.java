package lopicost.spd.helium.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "section")
public class Section {
	@XmlAttribute(name = "idSection")
	private String idSection;							// Identificador único de la sección
	private String name;								// Nombre de la sección
	private String observations;						// Observaciones
	
	
	//public String getIdSection() 						{		return idSection;					}
	public void setIdSection(String idSection) 			{		this.idSection = idSection;			}
	public String getName() 							{		return name;						}
	public void setName(String name) 					{		this.name = name;					}
	public String getObservations() 					{		return observations;				}
	public void setObservations(String observations) 	{		this.observations = observations;	}
	public Section() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}

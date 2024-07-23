package lopicost.spd.helium.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "dose")
@XmlAccessorType(XmlAccessType.FIELD)
public class Dose {


	@XmlAttribute(name = "idDose")
	private String idDose;				// Identificador único del centro que referencie a una hora de toma. Su valor puede coincidir con la hora de la toma.
//	private String codeDose;	
	@XmlElement
	private String hour;				// Hora de la toma del paciente en formato “hh:mm”
	@XmlElement
	private String name;				// Descripción del periodo del día de la toma.
	
	@XmlTransient	//para que no se muestre en el XML
	private String idDivisionResidencia;	
	@XmlTransient	//para que no se muestre en el XML
	private String nombreDose;	
	@XmlTransient	//para que no se muestre en el XML
	private int lang;	
	@XmlTransient	//para que no se muestre en el XML
	private String tipo;	

	public Dose(String idDose, String hour, String name) {
		super();
		this.idDose = idDose;
	//	this.codeDose = idDose;
		this.hour = hour;
		this.name = name;
	}
	public Dose() {
		super();
	}
	
	//public String getIdDose() 					{ 		return idDose;					}
	public void setIdDose(String idDose)			{		this.idDose = idDose;			}
	public String getCodeDose() 					{ 		return idDose;					}
//	public void setCode(String codeDose)			{		this.codeDose = codeDose;		}
	public String getHour() 						{		return hour;					}
	public void setHour(String hour) 				{		this.hour = hour;				}
	public String getName() 						{		return name;					}
	public void setName(String name)				{		this.name = name;				}
	public void setIdDivisionResidencia(String id) 	{		this.idDivisionResidencia = id;	}
	public String getNombreDose() 					{		return nombreDose;				}
	public void setNombreDose(String nom) 			{		this.nombreDose = nom;			}
	public int getLang() 							{		return lang;					}
	public void setLang(int lang) 					{		this.lang = lang;				}

	public String getTipo() 						{		return tipo;					}
	public void setTipo(String tipo) 				{		this.tipo = tipo;				}
	
}

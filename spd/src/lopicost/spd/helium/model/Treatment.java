package lopicost.spd.helium.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.w3c.dom.Element;

@XmlRootElement(name = "treatment")
public class Treatment {
	@XmlAttribute(name = "idTreatment")
	private String idTreatment;								// Identificador único del tratamiento en el centro. Un paciente puede tener varios tratamientos. Puede ser una clave compuesta, siempre vinculada al tratamiento del paciente. Si solo existe un tratamiento se puede coger como valor la clave del identificador del paciente.
//	private String codeTreatment;							// Identificador único del tratamiento en el centro. Un paciente puede tener varios tratamientos. Puede ser una clave compuesta, siempre vinculada al tratamiento del paciente. Si solo existe un tratamiento se puede coger como valor la clave del identificador del paciente.
	private String idReceipt;								// Identificador único de la receta.
	private boolean active;									// Indica si el tratamiento está o no activo en este momento, puede tomar un valor true o false.
	@XmlElement
	private String startTreatment;							// Fecha de inicio del tratamiento en formato dd-mm-yyyy hh:mm.
	@XmlElement
	private String endTreatment;							// Fecha de finalización del tratamiento en formato dd-mm-yyyy hh:mm.
	private Pouches pouches = new Pouches();	// Bolsas de medicación que puede tomar un paciente en un día en diferentes periodos del día.
	
	//public String getIdTreatment() 							{		return idTreatment;						}
	public void setIdTreatment(String idTreatment) 			{		this.idTreatment = idTreatment;			}
	public String getCodeTreatment() 						{		return idTreatment;					}
//	public void setCodeTreatment(String codeTreatment)		{		this.codeTreatment = codeTreatment;		}

	public String getIdReceipt() 							{		return idReceipt;						}
	public void setIdReceipt(String idReceipt) 				{		this.idReceipt = idReceipt;				}
	public boolean isActive() 								{		return active;							}
	public void setActive(boolean active) 					{		this.active = active;					}
	public String getStartTreat()						{		return startTreatment;					}
	public void setStartTreatment(String startTreatment) 	{		this.startTreatment = startTreatment;	}
	public String getEndTreat() 							{		return endTreatment;					}
	public void setEndTreatment(String endTreatment) 		{		this.endTreatment = endTreatment;		}
	public Pouches getPouches() 							{		return pouches;							}
	public void setPouches(Pouches pouches) 				{		this.pouches = pouches;					}
	
	
	public Treatment() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	
	
}

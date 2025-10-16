package lopicost.spd.helium.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@XmlRootElement(name = "pouch")
public class Pouch {
	@XmlAttribute(name = "idPouch")
	private String idPouch;									// Identificador único de la bolsa de medicación que toma un paciente en un día. Si no se dispone de ella esta clave puede estar compuesta por la clave del paciente, la clave del tratamiento y la clave de la hora de la toma.
	//private String codePouch;								// Identificador único de la bolsa de medicación que toma un paciente en un día. Si no se dispone de ella esta clave puede estar compuesta por la clave del paciente, la clave del tratamiento y la clave de la hora de la toma.
	private Dose dose;										// Contenido xml con la hora de la toma
	private Lines lines = new Lines();						// Listado de medicamentos que tomará un paciente en diferentes periodo del día durante un periodo del tiempo.
	private boolean activa=false;
	
	//public String getIdPouch() 								{		return idPouch;				}
	public void setIdPouch(String idPouch) 					{		this.idPouch = idPouch;		}
	public String getCodePouch() 							{		return idPouch;			}
	//public void setCodePouch(String codePouch) 				{		this.codePouch = codePouch;	}
	public Dose getDose() 									{		return dose;				}
	public void setDose(Dose dose) 							{		this.dose = dose;			}
	public Lines getLines() 								{		return lines;				}
	public void setLines(Lines lines) 						{		this.lines = lines;			}
	
	
	@XmlTransient  //para que no aparezca en el XML
	public boolean isActiva() {
		return activa;
	}
	public void setActiva(boolean activa) {
		this.activa = activa;
	}
	public Pouch(String idPouch, Dose dose, Lines lines) {
		super();
		this.idPouch = idPouch;
	//	this.codePouch = idPouch;
		this.dose = dose;
		this.lines = lines;
	}
	public Pouch() {
		super();
	}

	
	

}

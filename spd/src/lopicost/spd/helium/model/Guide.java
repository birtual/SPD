package lopicost.spd.helium.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.w3c.dom.Element;

@XmlRootElement(name = "guide")
public class Guide {
	@XmlAttribute(name = "idGuide")
	private String idGuide;						// Identificador único del patrón a aplicar para esta toma de este paciente. Si no se dispone de esta clave se puede coger la clave de “idLine” y añadir un contador incremental.
	private int number;							// Es un contador entero que indica el orden de la etiqueta; el contador empieza siempre por 1.
	private int periods;						// Este valor es un numérico entero e indica el número de periodos sobre los que se aplicará esta restricción.
	private String periodtype;					// Este valor indica el tipo de periodo; este puede tomar los valores “DAYS”, “WEEKS” o “MONTHS”
	private boolean active;						// Este valor indica si sobre este periodo de tiempo el paciente ha de tomar o no el medicamento; si es sí toma el valor true, en caso contrario tiene el valor false.
	
	
	//public String getIdGuide() 					{		return idGuide;					}
	public void setIdGuide(String idGuide) 		{		this.idGuide = idGuide;			}
	public int getNumber() 						{		return number;					}
	public void setNumber(int number) 			{		this.number = number;			}
	public int getPeriods() 					{		return periods;					}
	public void setPeriods(int periods) 		{		this.periods = periods;			}
	public String getPeriodtype() 				{		return periodtype;				}
	public void setPeriodtype(String periodtype){		this.periodtype = periodtype;	}
	public boolean isActive() 					{		return active;					}
	public void setActive(boolean active) 		{		this.active = active;			}
	
	public Guide() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}

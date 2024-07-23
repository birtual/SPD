package lopicost.spd.helium.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.w3c.dom.Element;

import lopicost.spd.utils.SPDConstants;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "line")
@XmlType(propOrder = {
        "activePeriod",
        "from",
        "to",
        "irreplaceblePill",
        "pill",
        "pill_desc",
        "outOfBlister",
        "amount",
        "needed",
        "guides", "dayfromWeek", "daytoWeek", "dayfromMonth", "daytoMonth"
        , "monthdays"
        ,"weekdays" 
})
public class Line {

	@XmlAttribute(name = "idLine")
	private String idLine;									// Identificador �nico del medicamento a tomar por un paciente en una bolsa de medicaci�n correspondiente a un periodo del d�a. Si no se dispone de esta clave se puede coger la clave de la bolsa y el c�digo de medicamento mas un contador incremental (para casos de tener medicaci�n replicada, por ejemplo del tipo si precisa).
	private boolean activePeriod;							// Indicador que se ha de informar siempre; si es true indica que la medicaci�n del paciente es diaria, si es false no lo es.	
	private String from;									// Solo se informa si la mediaci�n es diaria. Esta fecha es la fecha de inicio de la toma, tiene formato dd-mm-yyyy hh:mm.
	private String to;										// Solo se informa si la mediaci�n es diaria. Esta fecha es la fecha de fin de la toma, tiene formato dd-mm-yyyy hh:mm.
	private String dayfromWeek;								// Solo se informa si la medicaci�n se toma ciertos dias de la semana. Esta fecha indica el inicio de la toma, tiene formato dd-mm-yyyy hh:mm.
	private String daytoWeek;								// Solo se informa si la medicaci�n se toma ciertos dias de la semana. Esta fecha indica el fin de la toma, tiene formato dd-mm-yyyy hh:mm.
	private String dayfromMonth;							// Solo se informa si la medicaci�n se toma ciertos dias del mes. Esta fecha indica el inicio de la toma, tiene formato dd-mm-yyyy hh:mm
	private String daytoMonth;								// Solo se informa si la medicaci�n se toma ciertos dias del mes. Esta fecha indica el fin de la toma, tiene formato dd-mm-yyyy hh:mm
	private String weekdays;								// Solo se informa en caso de medicaci�n semanal. Indica los dias de la semana en los cuales se tiene que hacer las tomas.El valor de este campo es una cadena de par�metros no repetidos separados por comas que pueden tomar los siguientes valores: {�monday�,�tuesday�,�wednesday�,�thursday�,�friday�,�saturday�,�sunday�}.
	private String monthdays;								// Solo se informa en caso de medicaci�n mensual. Indica los dias del mes en los cuales se tienen que hacer las tomas. Este valor es un entero comprendido entre 1 y 31.
	@XmlElement(name = "guides")
	private Guides guides = null;	// Solo se informa en caso de tener medicaci�n con peculiaridades. En el caso de que la medicaci�n se tome dias,semanas o meses alternos con una determinada frecuencia aqu� se registran dichas peculiaridades. Estas pecularidades tienen un car�cter restrictivo sobre las tomas diarias, semanales o mensuales ya creadas.
	private boolean needed=false;									// Si la medicaci�n es de tipo si precisa toma el valor de true, en caso contrario toma el valor de false.
	private boolean irreplaceblePill; 						// Si la medicaci�n no es reemplazable toma el valor true, false en caso contrario
	private String pill;									// Aqu� se informa del identificador del medicamento; puede ser el c�digo nacional (C.N) o bien otro c�digo interno del centro correspondiente a otro tipo de medicaci�n
	private String pill_desc;								// Esta es la descripci�n larga del medicamento. Se precisa para casos en los que la medicaci�n se crea de manera manual.
	private boolean outOfBlister;							// Indicador de si el medicamento va o no fuera de blister; es true si va fuera de blister y false si va en blister. Si no se informa se entiende que va dentro de blister
	private Float amount;									// Es la cantidad de medicaci�n que se deber� tomar; se acepta medicaci�n fraccionada. Ejemplo: 0.25,0.5,0.75, 1,1.25,2,3�.


	
	//public String getIdLine() 									{		return idLine;								}
	public void setIdLine(String idLine) 						{		this.idLine = idLine;						}
	public String getId() 										{		return idLine;								}
	public boolean isActivePeriod() 							{		return activePeriod;						}
	public void setActivePeriod(boolean activePeriod) 			{		this.activePeriod = activePeriod;			}
	public String getFrom() 									{		return from;								}
	public void setFrom(String from) 							{		this.from = from;							}
	public String getTo() 										{		return to;									}
	public void setTo(String to) 								{		this.to = to;								}
	public String getDayfromWeek() 								{		return dayfromWeek;							}
	public void setDayfromWeek(String dayfromWeek) 				{		this.dayfromWeek = dayfromWeek;				}
	public String getDaytoWeek() 								{		return daytoWeek;							}
	public void setDaytoWeek(String daytoWeek) 					{		this.daytoWeek = daytoWeek;					}
	public String getDayfromMonth() 							{		return dayfromMonth;						}
	public void setDayfromMonth(String dayfromMonth) 			{		this.dayfromMonth = dayfromMonth;			}
	public String getDaytoMonth() 								{		return daytoMonth;							}
	public void setDaytoMonth(String daytoMonth) 				{		this.daytoMonth = daytoMonth;				}
	@XmlTransient
	public String getWeekdays() 								{	
		if(this.weekdays!=null && !this.weekdays.equals(""))
		{
			this.weekdays = this.weekdays.replace(SPDConstants.DIA1, SPDConstants.DIA1_HELIUM)
				.replace(SPDConstants.DIA2, SPDConstants.DIA2_HELIUM)
				.replace(SPDConstants.DIA3, SPDConstants.DIA3_HELIUM)
				.replace(SPDConstants.DIA4, SPDConstants.DIA4_HELIUM)
				.replace(SPDConstants.DIA5, SPDConstants.DIA5_HELIUM)
				.replace(SPDConstants.DIA6, SPDConstants.DIA6_HELIUM)
				.replace(SPDConstants.DIA7, SPDConstants.DIA7_HELIUM)
				;
		}
		return this.weekdays;							
		}
	
	
	public void setWeekdays(String weekdays) 					{		
		if(weekdays!=null && !weekdays.equals(""))
		{
			weekdays=weekdays.replace(SPDConstants.DIA1, SPDConstants.DIA1_HELIUM)
				.replace(SPDConstants.DIA2, SPDConstants.DIA2_HELIUM)
				.replace(SPDConstants.DIA3, SPDConstants.DIA3_HELIUM)
				.replace(SPDConstants.DIA4, SPDConstants.DIA4_HELIUM)
				.replace(SPDConstants.DIA5, SPDConstants.DIA5_HELIUM)
				.replace(SPDConstants.DIA6, SPDConstants.DIA6_HELIUM)
				.replace(SPDConstants.DIA7, SPDConstants.DIA7_HELIUM)
				;
		}
		this.weekdays = weekdays;
		}
	@XmlTransient
	public String getMonthdays() 								{		return monthdays;							}
	public void setMonthdays(String monthdays) 					{		this.monthdays = monthdays;					}

	public Guides getGuides() {
		if(guides==null) guides =new Guides();
		return guides;
	}
	public void setGuides(Guides guides) {
		this.guides = guides;
	}
	public boolean isNeeded() 									{		return needed;								}
	public void setNeeded(boolean needed) 						{		this.needed = needed;						}
	public boolean isIrreplaceblePill() 						{		return irreplaceblePill;					}
	public void setIrreplaceblePill(boolean irreplaceblePill) 	{		this.irreplaceblePill = irreplaceblePill;	}
	public String getPill() 									{		return pill;								}
	public void setPill(String pill) 							{		this.pill = pill;							}
	public String getPill_desc() 								{		return pill_desc;							}
	public void setPill_desc(String pill_desc) 					{		this.pill_desc = pill_desc;					}
	public boolean isOutOfBlister() 							{		return outOfBlister;						}
	public void setOutOfBlister(boolean outOfBlister) 			{		this.outOfBlister = outOfBlister;			}
	 @XmlTransient
	public Float getAmount() 									{		return amount;								}
	public void setAmount(Float amount) 						{		this.amount = amount;						}
	public Line() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
}

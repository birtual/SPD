package lopicost.spd.robot.bean.rd;

import java.util.ArrayList;
import java.util.List;


public class LineaBolsaSPD {
    private MedicamentoPaciente medicamentoPaciente;
    private double cantidad;
    private String freeInformation;
    
    

	public double getCantidad() 									{		return cantidad;						}
	public void setCantidad(double cantidad) 						{		this.cantidad = cantidad;				}
	public String getFreeInformation() 								{		return freeInformation;					}
	public void setFreeInformation(String freeInformation) 			{		this.freeInformation = freeInformation;	}
	public MedicamentoPaciente getMedicamentoPaciente() 			{		return medicamentoPaciente;				}
	public void setMedicamentoPaciente(MedicamentoPaciente medic) 	{		this.medicamentoPaciente = medic;		}

}

package lopicost.spd.model.spd;

import java.util.ArrayList;
import java.util.List;

import lopicost.spd.model.rd.MedicamentoRobot;


public class LineaBolsaSPD {
    private MedicamentoRobot medicamentoPaciente;
    private double cantidad;
    private String freeInformation;
    
    

	public double getCantidad() 									{		return cantidad;						}
	public void setCantidad(double cantidad) 						{		this.cantidad = cantidad;				}
	public String getFreeInformation() 								{		return freeInformation;					}
	public void setFreeInformation(String freeInformation) 			{		this.freeInformation = freeInformation;	}
	public MedicamentoRobot getMedicamentoPaciente() 			{		return medicamentoPaciente;				}
	public void setMedicamentoPaciente(MedicamentoRobot medic) 	{		this.medicamentoPaciente = medic;		}

}

package lopicost.spd.robot.bean.rd;

import java.util.ArrayList;
import java.util.List;


public class LineaBolsaSPD {
    private MedicamentoPaciente medicamentoPaciente;
    private int cantidad;
    private String freeInformation;
    
    

	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public String getFreeInformation() {
		return freeInformation;
	}
	public void setFreeInformation(String freeInformation) {
		this.freeInformation = freeInformation;
	}
	public MedicamentoPaciente getMedicamentoPaciente() {
		return medicamentoPaciente;
	}
	public void setMedicamentoPaciente(MedicamentoPaciente medicamentoPaciente) {
		this.medicamentoPaciente = medicamentoPaciente;
	}

}

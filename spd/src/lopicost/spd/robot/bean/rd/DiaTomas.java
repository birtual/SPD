package lopicost.spd.robot.bean.rd;

import java.util.ArrayList;
import java.util.List;

public class DiaTomas {
	private int orden;
	private int cantidadDia;
	private String pautaDiaria;
	private String fechaToma;

	private List<Toma> tomas = new ArrayList<Toma>();
	

	
	public String getFechaToma() {
		return fechaToma;
	}
	public void setFechaToma(String fechaToma) {
		this.fechaToma = fechaToma;
	}
	public int getOrden() {
		return orden;
	}
	public void setOrden(int ordenDiaEnProduccion) {
		this.orden = ordenDiaEnProduccion;
	}
	public int getCantidadDia() {
		return cantidadDia;
	}
	public void setCantidadDia(int cantidadDia) {
		this.cantidadDia = cantidadDia;
	}
	public String getPautaDiaria() {
		return pautaDiaria;
	}
	public void setPautaDiaria(String pautaDiaria) {
		this.pautaDiaria = pautaDiaria;
	}
	public List<Toma> getTomas() {
		return tomas;
	}
	public void setTomas(List<Toma> tomas) {
		this.tomas = tomas;
	}

	
}

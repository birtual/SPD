package lopicost.spd.model.spd;

import java.util.ArrayList;
import java.util.List;

public class DiaSPD {
	private int ordenDiaEnProduccion;
	private double cantidadDia;
	private String pautaDiaria;
	private String fechaToma;
	private List<BolsaSPD> bolsaSPD = new ArrayList<BolsaSPD>();
	
	
	public int getOrdenDiaEnProduccion() 							{		return ordenDiaEnProduccion;							}
	public void setOrdenDiaEnProduccion(int ordenDiaEnProduccion) 	{		this.ordenDiaEnProduccion = ordenDiaEnProduccion;		}
	public String getPautaDiaria() 									{		return pautaDiaria;										}
	public void setPautaDiaria(String pautaDiaria) 					{		this.pautaDiaria = pautaDiaria;							}
	public String getFechaToma() 									{		return fechaToma;										}
	public void setFechaToma(String fechaToma) 						{		this.fechaToma = fechaToma;								}
	public List<BolsaSPD> getBolsaSPD() 							{		return bolsaSPD;										}
	public void setBolsaSPD(List<BolsaSPD> bolsaSPD) 				{		this.bolsaSPD = bolsaSPD;								}
	public double getCantidadDia() 									{		return cantidadDia;										}
	public void setCantidadDia(double cantidadDia) 					{		this.cantidadDia = cantidadDia;							}
	
}

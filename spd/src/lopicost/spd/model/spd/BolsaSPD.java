package lopicost.spd.model.spd;

import java.util.ArrayList;
import java.util.List;


public class BolsaSPD {
	private String idBolsa;
    private int numeroOrdenBolsa;		
    private String codigoBarras;
    private int flag;					//0 SOLO_INFO / 1 PASTILLERO
    private int offsetDays;				//días relativos
    private String tomaDelDia;			//toma del día	(doseTime)
    private String fechaEmbolsado;		//día de relleno	(fillDate)
    private String horaEmbolsado;	//inicio hora de relleno (fillTime)
    private String freeInformation;
    private List<LineaBolsaSPD> lineasBolsa = new ArrayList<LineaBolsaSPD>();

	public String getIdBolsa() {
		return idBolsa;
	}

	public void setIdBolsa(String idBolsaSPD) {
		this.idBolsa = idBolsaSPD;
	}

	public int getNumeroOrdenBolsa() {
		return numeroOrdenBolsa;
	}

	public void setNumeroOrdenBolsa(int numeroOrdenBolsa) {
		this.numeroOrdenBolsa = numeroOrdenBolsa;
	}

	public String getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getOffsetDays() {
		return offsetDays;
	}

	public void setOffsetDays(int offsetDays) {
		this.offsetDays = offsetDays;
	}

	public String getTomaDelDia() {
		return tomaDelDia;
	}

	public void setTomaDelDia(String tomaDelDia) {
		this.tomaDelDia = tomaDelDia;
	}

	public String getFechaEmbolsado() {
		return fechaEmbolsado;
	}

	public void setFechaEmbolsado(String fechaEmbolsado) {
		this.fechaEmbolsado = fechaEmbolsado;
	}

	public String getHoraEmbolsado() {
		return horaEmbolsado;
	}

	public void setHoraEmbolsado(String horaInicioEmbolsado) {
		this.horaEmbolsado = horaInicioEmbolsado;
	}


	public String getFreeInformation() {
		return freeInformation;
	}

	public void setFreeInformation(String freeInformation) {
		this.freeInformation = freeInformation;
	}

	public List<LineaBolsaSPD> getLineasBolsa() {
		return lineasBolsa;
	}

	public void setLineasBolsa(List<LineaBolsaSPD> drugs) {
		this.lineasBolsa = drugs;
	} 


  


}

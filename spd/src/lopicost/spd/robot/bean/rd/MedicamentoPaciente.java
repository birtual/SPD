package lopicost.spd.robot.bean.rd;

import java.util.ArrayList;
import java.util.List;


public class MedicamentoPaciente extends Medicamento{
    private String nombreMedicamentoBolsa;
    private String codigoMedicamentoRobot;
    private String fechaDesemblistado;
    private String pautaResidencia;
    private List<DiaTomas> diaTomas = new ArrayList<DiaTomas>();
    private String leyendaInfo;
    private String formaFarmaceutica;
    
    
	public DiaTomas getDiaTomaSegura(int index) {
	    if (index >= 0 && index < diaTomas.size()) {
	        return diaTomas.get(index); // puede devolver un objeto o null
	    }
	    return null;
	}
	

	
	public String getFormaFarmaceutica() {
		return formaFarmaceutica;
	}



	public void setFormaFarmaceutica(String formaFarmaceutica) {
		this.formaFarmaceutica = formaFarmaceutica;
	}



	public String getNombreMedicamentoBolsa() {
		return nombreMedicamentoBolsa;
	}
	public void setNombreMedicamentoBolsa(String nombreMedicamentoBolsa) {
		this.nombreMedicamentoBolsa = nombreMedicamentoBolsa;
	}
	public String getCodigoMedicamentoRobot() {
		return codigoMedicamentoRobot;
	}
	public void setCodigoMedicamentoRobot(String codigoMedicamentoRobot) {
		this.codigoMedicamentoRobot = codigoMedicamentoRobot;
	}
	public List<DiaTomas> getDiaTomas() {
		return diaTomas;
	}
	public void setDiaTomas(List<DiaTomas> diaTomas) {
		this.diaTomas = diaTomas;
	}
	public String getPautaResidencia() {
		return pautaResidencia;
	}
	public void setPautaResidencia(String pautaResidencia) {
		this.pautaResidencia = pautaResidencia;
	}
	public String getLeyendaInfo() {
		return leyendaInfo;
	}
	public void setLeyendaInfo(String leyendaInfo) {
		this.leyendaInfo = leyendaInfo;
	}

	public String getFechaDesemblistado() {
		return fechaDesemblistado;
	}
	public void setFechaDesemblistado(String fechaDesemblistado) {
		this.fechaDesemblistado = fechaDesemblistado;
	}
}

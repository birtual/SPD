package lopicost.spd.robot.bean.rd;

import java.util.ArrayList;
import java.util.List;


public class MedicamentoPaciente {
    private String cn;
    private String nombreMedicamentoBolsa;
    private String nombreMedicamentoConsejo;
    private String codigoMedicamentoRobot;
    private String labMedicamento;
    private String fechaDesemblistado;
    private String lote;
    private String caducidad;
    private String pautaResidencia;
    private List<DiaTomas> diaTomas = new ArrayList<DiaTomas>();
    private String leyendaInfo;
    
    
	public DiaTomas getDiaTomaSegura(int index) {
	    if (index >= 0 && index < diaTomas.size()) {
	        return diaTomas.get(index); // puede devolver un objeto o null
	    }
	    return null;
	}
	
	
	
	public String getCn() {
		return cn;
	}
	public void setCn(String cn) {
		this.cn = cn;
	}
	public String getNombreMedicamentoBolsa() {
		return nombreMedicamentoBolsa;
	}
	public void setNombreMedicamentoBolsa(String nombreMedicamentoBolsa) {
		this.nombreMedicamentoBolsa = nombreMedicamentoBolsa;
	}
	public String getNombreMedicamentoConsejo() {
		return nombreMedicamentoConsejo;
	}
	public void setNombreMedicamentoConsejo(String nombreMedicamentoConsejo) {
		this.nombreMedicamentoConsejo = nombreMedicamentoConsejo;
	}
	public String getCodigoMedicamentoRobot() {
		return codigoMedicamentoRobot;
	}
	public void setCodigoMedicamentoRobot(String codigoMedicamentoRobot) {
		this.codigoMedicamentoRobot = codigoMedicamentoRobot;
	}
	public String getLabMedicamento() {
		return labMedicamento;
	}
	public void setLabMedicamento(String labMedicamento) {
		this.labMedicamento = labMedicamento;
	}
	public String getLote() {
		return lote;
	}
	public void setLote(String lote) {
		this.lote = lote;
	}
	public String getCaducidad() {
		return caducidad;
	}
	public void setCaducidad(String caducidad) {
		this.caducidad = caducidad;
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

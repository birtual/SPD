package lopicost.spd.robot.bean.rd;



public abstract class Medicamento {
    private String cn;
    private String nombreMedicamentoConsejo;
    private String labMedicamento;
    private String lote;
    private String caducidad;
	public String getCn() {
		return cn;
	}
	public void setCn(String cn) {
		this.cn = cn;
	}
	public String getNombreMedicamentoConsejo() {
		return nombreMedicamentoConsejo;
	}
	public void setNombreMedicamentoConsejo(String nombreMedicamentoConsejo) {
		this.nombreMedicamentoConsejo = nombreMedicamentoConsejo;
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
    
    
}

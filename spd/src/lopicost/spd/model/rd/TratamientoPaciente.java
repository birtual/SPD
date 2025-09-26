package lopicost.spd.model.rd;



public class TratamientoPaciente {
    private MedicamentoBirtual medicamentoBirtual;
    private MedicamentoReceta medicamentoReceta;
    private MedicamentoRobot medicamentoRobot;
    private float cantidadTotalEmblistadaSPD;
    private String pautaResidencia;
    // private int cantidadDispensadaReceta;
    //private int cantidadCustodia;
    private String leyendaInfo;
    private String observaciones;
    private boolean emblistar = false;

    
    

	public MedicamentoBirtual getMedicamentoBirtual() {
		return medicamentoBirtual;
	}
	public void setMedicamentoBirtual(MedicamentoBirtual medicamentoBirtual) {
		this.medicamentoBirtual = medicamentoBirtual;
	}
	public MedicamentoReceta getMedicamentoReceta() {
		return medicamentoReceta;
	}
	public void setMedicamentoReceta(MedicamentoReceta medicamentoReceta) {
		this.medicamentoReceta = medicamentoReceta;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public boolean isEmblistar() {
		return emblistar;
	}
	public void setEmblistar(boolean emblistar) {
		this.emblistar = emblistar;
	}
	public MedicamentoRobot getMedicamentoRobot() {
		return medicamentoRobot;
	}
	public void setMedicamentoRobot(MedicamentoRobot medicamentoPaciente) {
		this.medicamentoRobot = medicamentoPaciente;
	}
	public String getLeyendaInfo() {
		return leyendaInfo;
	}
	public void setLeyendaInfo(String leyendaInfo) {
		this.leyendaInfo = leyendaInfo;
	}
	public float getCantidadTotalEmblistadaSPD() {
		return cantidadTotalEmblistadaSPD;
	}
	public void setCantidadTotalEmblistadaSPD(float cantidadTotalEmblistadaSPD) {
		this.cantidadTotalEmblistadaSPD = cantidadTotalEmblistadaSPD;
	}
	public String getPautaResidencia() {
		return pautaResidencia;
	}
	public void setPautaResidencia(String pautaResidencia) {
		this.pautaResidencia = pautaResidencia;
	}




}

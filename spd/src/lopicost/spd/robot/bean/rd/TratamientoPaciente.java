package lopicost.spd.robot.bean.rd;



public class TratamientoPaciente {
    private MedicamentoPaciente medicamentoPaciente;
    private MedicamentoReceta medicamentoReceta;
    private String tratamientoInicio;		//fecha inicio según residencia
    private String tratamientoFin;			//fecha fin según residencia
    //private int cantidadTotal;
    private String pautaResidencia;			
    private double cantidadUtilizadaSPD;
    // private int cantidadDispensadaReceta;
    //private int cantidadCustodia;
    private String leyendaInfo;
    private String observaciones;
    private boolean emblistar = false;

    
    

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
	public MedicamentoPaciente getMedicamentoPaciente() {
		return medicamentoPaciente;
	}
	public void setMedicamentoPaciente(MedicamentoPaciente medicamentoPaciente) {
		this.medicamentoPaciente = medicamentoPaciente;
	}
	public String getTratamientoInicio() {
		return tratamientoInicio;
	}
	public void setTratamientoInicio(String tratamientoInicio) {
		this.tratamientoInicio = tratamientoInicio;
	}
	public String getTratamientoFin() {
		return tratamientoFin;
	}
	public void setTratamientoFin(String tratamientoFin) {
		this.tratamientoFin = tratamientoFin;
	}
	public String getPautaResidencia() {
		return pautaResidencia;
	}
	public void setPautaResidencia(String pautaResidencia) {
		this.pautaResidencia = pautaResidencia;
	}
	public double getCantidadUtilizadaSPD() {
		return cantidadUtilizadaSPD;
	}
	public void setCantidadUtilizadaSPD(double cantidadUtilizadaSPD) {
		this.cantidadUtilizadaSPD = cantidadUtilizadaSPD;
	}
	
	public String getLeyendaInfo() {
		return leyendaInfo;
	}
	public void setLeyendaInfo(String leyendaInfo) {
		this.leyendaInfo = leyendaInfo;
	}




}

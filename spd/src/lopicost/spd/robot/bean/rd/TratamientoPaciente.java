package lopicost.spd.robot.bean.rd;



public class TratamientoPaciente {
    private MedicamentoPaciente medicamentoPaciente;
    private String tratamientoInicio;		//fecha inicio según residencia
    private String tratamientoFin;			//fecha fin según residencia
    //private int cantidadTotal;
    private String pautaResidencia;			
    private int cantidadUtilizadaSPD;
    // private int cantidadDispensadaReceta;
    //private int cantidadCustodia;
    private String leyendaInfo;
    private boolean emblistar = false;

    
    

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
	public int getCantidadUtilizadaSPD() {
		return cantidadUtilizadaSPD;
	}
	public void setCantidadUtilizadaSPD(int cantidadUtilizadaSPD) {
		this.cantidadUtilizadaSPD = cantidadUtilizadaSPD;
	}
	
	public String getLeyendaInfo() {
		return leyendaInfo;
	}
	public void setLeyendaInfo(String leyendaInfo) {
		this.leyendaInfo = leyendaInfo;
	}




}

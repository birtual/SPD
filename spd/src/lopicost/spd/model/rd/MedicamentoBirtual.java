package lopicost.spd.model.rd;

import java.util.ArrayList;
import java.util.List;

import lopicost.spd.model.Medicamento;


public class MedicamentoBirtual extends Medicamento{
    
    private String cnResi;
	private String nombreConsejoCnResi;
	
    private String CnFinal;
	private String nombreMedicamentoEnBolsa;
	private String nombreConsejoCnFinal;

	private double cantidadPrevistaSPD;
	private String periodo;
    private String accion;
    private String siPrecisa;
    private String variante;
    private String observaciones;
    private String comentarios;
    private String tratamientoInicio;		//fecha inicio según residencia
    private String tratamientoFin;			//fecha fin según residencia
    private List<DiaTomas> diaTomas = new ArrayList<DiaTomas>();
    private String pautaResidencia;			
    private String formaFarmaceutica;
	private String orderNumber;
	private String idDetalle;
	private String idFreeInformation;
	private String numBolsasToma;
	private String planta;
	private String habitacion;
	private String dispensar;
	
	
	public DiaTomas getDiaTomaSegura(int index) {
	    if (index >= 0 && index < diaTomas.size()) {
	        return diaTomas.get(index); // puede devolver un objeto o null
	    }
	    return null;
	}


	public String getDispensar() {
		return dispensar;
	}


	public void setDispensar(String dispensar) {
		this.dispensar = dispensar;
	}


	public String getPlanta() {
		return planta;
	}


	public void setPlanta(String planta) {
		this.planta = planta;
	}


	public String getHabitacion() {
		return habitacion;
	}


	public void setHabitacion(String habitacion) {
		this.habitacion = habitacion;
	}


	public String getNumBolsasToma() {
		return numBolsasToma;
	}


	public void setNumBolsasToma(String numBolsasToma) {
		this.numBolsasToma = numBolsasToma;
	}


	public String getIdFreeInformation() {
		return idFreeInformation;
	}


	public void setIdFreeInformation(String idFreeInformation) {
		this.idFreeInformation = idFreeInformation;
	}


	public String getIdDetalle() {
		return idDetalle;
	}


	public void setIdDetalle(String idDetalle) {
		this.idDetalle = idDetalle;
	}


	public String getOrderNumber() {
		return orderNumber;
	}


	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}


	public String getCnResi() {
		return cnResi;
	}


	public void setCnResi(String cnResi) {
		this.cnResi = cnResi;
	}


	public String getNombreMedicamentoEnBolsa() {
		return nombreMedicamentoEnBolsa;
	}


	public void setNombreMedicamentoEnBolsa(String nombreMedicamentoResi) {
		this.nombreMedicamentoEnBolsa = nombreMedicamentoResi;
	}


	public String getNombreConsejoCnResi() {
		return nombreConsejoCnResi;
	}


	public void setNombreConsejoCnResi(String nombreConsejoCnResi) {
		this.nombreConsejoCnResi = nombreConsejoCnResi;
	}


	public String getCnFinal() {
		return CnFinal;
	}


	public void setCnFinal(String cnFinal) {
		CnFinal = cnFinal;
	}



	public String getNombreConsejoCnFinal() {
		return nombreConsejoCnFinal;
	}


	public void setNombreConsejoCnFinal(String nombreConsejoCnFinal) {
		this.nombreConsejoCnFinal = nombreConsejoCnFinal;
	}


	public double getCantidadPrevistaSPD() {
		return cantidadPrevistaSPD;
	}


	public void setCantidadPrevistaSPD(double cantidadPrevistaSPD) {
		this.cantidadPrevistaSPD = cantidadPrevistaSPD;
	}


	public String getPeriodo() {
		return periodo;
	}


	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}


	public String getAccion() {
		return accion;
	}


	public void setAccion(String accion) {
		this.accion = accion;
	}


	public String getSiPrecisa() {
		return siPrecisa;
	}


	public void setSiPrecisa(String siPrecisa) {
		this.siPrecisa = siPrecisa;
	}


	public String getVariante() {
		return variante;
	}


	public void setVariante(String variante) {
		this.variante = variante;
	}


	public String getObservaciones() {
		return observaciones;
	}


	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}


	public String getComentarios() {
		return comentarios;
	}


	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
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


	public String getFormaFarmaceutica() {
		return formaFarmaceutica;
	}


	public void setFormaFarmaceutica(String formaFarmaceutica) {
		this.formaFarmaceutica = formaFarmaceutica;
	}

	
}

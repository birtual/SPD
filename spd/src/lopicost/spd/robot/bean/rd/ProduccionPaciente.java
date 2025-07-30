package lopicost.spd.robot.bean.rd;

import java.util.ArrayList;
import java.util.List;

import lopicost.spd.struts.bean.PacienteBean;


public class ProduccionPaciente {
	private String orderNumber;
	private PacienteBean paciente;
    private List<TratamientoPaciente> tratamientosPaciente = new ArrayList<TratamientoPaciente>();
    private List<TratamientoPaciente> ttosEmblistados = new ArrayList<TratamientoPaciente>();
    private List<TratamientoPaciente> ttosFueraBlister= new ArrayList<TratamientoPaciente>();
    private int diasProduccion;
    private String diaSPDInicio;		//startDate
    private String diaSPDFin;			//endDate
    private List<DiaSPD> diasSPD = new ArrayList<DiaSPD>();

    
	public List<TratamientoPaciente> getTtosEmblistados() {
		return ttosEmblistados;
	}
	public void setTtosEmblistados(List<TratamientoPaciente> ttosEmblistados) {
		this.ttosEmblistados = ttosEmblistados;
	}
	public List<TratamientoPaciente> getTtosFueraBlister() {
		return ttosFueraBlister;
	}
	public void setTtosFueraBlister(List<TratamientoPaciente> ttosFueraBlister) {
		this.ttosFueraBlister = ttosFueraBlister;
	}
	public int getDiasProduccion() {
		return diasProduccion;
	}
	public void setDiasProduccion(int diasProduccion) {
		this.diasProduccion = diasProduccion;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public PacienteBean getPaciente() {
		return paciente;
	}
	public void setPaciente(PacienteBean paciente) {
		this.paciente = paciente;
	}

	public List<TratamientoPaciente> getTratamientosPaciente() {
		return tratamientosPaciente;
	}
	public void setTratamientosPaciente(List<TratamientoPaciente> tratamientosPaciente) {
		this.tratamientosPaciente = tratamientosPaciente;
	}
	public String getDiaSPDInicio() {
		return diaSPDInicio;
	}
	public void setDiaSPDInicio(String diaSPDInicio) {
		this.diaSPDInicio = diaSPDInicio;
	}
	public String getDiaSPDFin() {
		return diaSPDFin;
	}
	public void setDiaSPDFin(String diaSPDFin) {
		this.diaSPDFin = diaSPDFin;
	}
	public List<DiaSPD> getDiasSPD() {
		return diasSPD;
	}
	public void setDiasSPD(List<DiaSPD> diasSPD) {
		this.diasSPD = diasSPD;
	}


}

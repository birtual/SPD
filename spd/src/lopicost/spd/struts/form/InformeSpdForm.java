package lopicost.spd.struts.form;

import java.util.ArrayList;
import java.util.List;

import lopicost.spd.robot.bean.rd.ProduccionPaciente;
import lopicost.spd.struts.bean.FicheroResiBean;

public class InformeSpdForm extends GenericForm
{
	List<ProduccionPaciente> producciones = new ArrayList<ProduccionPaciente>();
	FicheroResiBean cabecera =null;
	int oidFicheroResiCabecera; 
	String idResidenciaCarga;


	
	public FicheroResiBean getCabecera() {
		return cabecera;
	}

	public void setCabecera(FicheroResiBean cabecera) {
		this.cabecera = cabecera;
	}

	public int getOidFicheroResiCabecera() {
		return oidFicheroResiCabecera;
	}

	public void setOidFicheroResiCabecera(int oidFicheroResiCabecera) {
		this.oidFicheroResiCabecera = oidFicheroResiCabecera;
	}

	public String getIdResidenciaCarga() {
		return idResidenciaCarga;
	}

	public void setIdResidenciaCarga(String idResidenciaCarga) {
		this.idResidenciaCarga = idResidenciaCarga;
	}

	public List<ProduccionPaciente> getProducciones() {
		return producciones;
	}

	public void setProducciones(List<ProduccionPaciente> producciones) {
		this.producciones = producciones;
	}}
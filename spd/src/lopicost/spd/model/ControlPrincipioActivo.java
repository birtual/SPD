package lopicost.spd.model;


import java.io.Serializable;

public class ControlPrincipioActivo implements Serializable
{
    private Farmacia farmacia;
    private DivisionResidencia divisionResidencia;
    private BdConsejo bdConsejo;
    
    private String idFarmacia;
    private String idDivisionResidencia;
    private String codGtVm;
    private String nomGtVm;
    private int nivel;

	
	public String getIdFarmacia() {
		return idFarmacia;
	}


	public void setIdFarmacia(String idFarmacia) {
		this.idFarmacia = idFarmacia;
	}


	public String getIdDivisionResidencia() {
		return idDivisionResidencia;
	}


	public void setIdDivisionResidencia(String idDivisionResidencia) {
		this.idDivisionResidencia = idDivisionResidencia;
	}


	public String getCodGtVm() {
		return codGtVm;
	}


	public void setCodGtVm(String codGtVm) {
		this.codGtVm = codGtVm;
	}


	public String getNomGtVm() {
		return nomGtVm;
	}


	public void setNomGtVm(String nomGtVm) {
		this.nomGtVm = nomGtVm;
	}


	public int getNivel() 			{	return nivel;				}	public void setNivel(int nivel) 						{	this.nivel = nivel;					}


	public Farmacia getFarmacia() {
		return farmacia;
	}


	public void setFarmacia(Farmacia farmacia) {
		this.farmacia = farmacia;
	}


	public DivisionResidencia getDivisionResidencia() {
		return divisionResidencia;
	}


	public void setDivisionResidencia(DivisionResidencia divisionResidencia) {
		this.divisionResidencia = divisionResidencia;
	}


	public BdConsejo getBdConsejo() {
		return bdConsejo;
	}


	public void setBdConsejo(BdConsejo bdConsejo) {
		this.bdConsejo = bdConsejo;
	}
	
	
}
package lopicost.spd.struts.form;

import java.util.ArrayList;
import java.util.List;
import lopicost.spd.model.DivisionResidencia;
import lopicost.spd.model.Farmacia;
import lopicost.spd.model.Robot;

public class FarmaciasForm   extends GenericForm {

	  private List<Farmacia> listaFarmacias = new ArrayList();
	  private String idFarmacia;
	  private String nombreFarmacia;
	  private List<DivisionResidencia> listaDivisionResidencias= new ArrayList();
	  private List<Robot> listaRobots = new ArrayList();
	  private String contacto;
	  private String codigoUP;
	  
	  
	  
	public List<Farmacia> getListaFarmacias() {
		return listaFarmacias;
	}
	public void setListaFarmacias(List<Farmacia> listFarmacias) {
		this.listaFarmacias = listFarmacias;
	}

	public String getIdFarmacia() {
		return idFarmacia;
	}
	public void setIdFarmacia(String idFarmacia) {
		this.idFarmacia = idFarmacia;
	}
	public String getNombreFarmacia() {
		return nombreFarmacia;
	}
	public void setNombreFarmacia(String nombreFarmacia) {
		this.nombreFarmacia = nombreFarmacia;
	}
	public List<DivisionResidencia> getListaDivisionResidencias() {
		return listaDivisionResidencias;
	}
	public void setListaDivisionResidencias(List<DivisionResidencia> listDivisionResidencias) {
		this.listaDivisionResidencias = listDivisionResidencias;
	}
	public List<Robot> getListaRobots() {
		return listaRobots;
	}
	public String getContacto() {
		return contacto;
	}
	public void setContacto(String contacto) {
		this.contacto = contacto;
	}
	public String getCodigoUP() {
		return codigoUP;
	}
	public void setCodigoUP(String codigoUP) {
		this.codigoUP = codigoUP;
	}

	  
	  
	  

	


	
	
}

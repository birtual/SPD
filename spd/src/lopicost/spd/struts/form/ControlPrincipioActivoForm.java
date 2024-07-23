package lopicost.spd.struts.form;

import java.util.ArrayList;
import java.util.List;

import lopicost.spd.model.BdConsejo;
import lopicost.spd.model.ControlPrincipioActivo;
import lopicost.spd.model.DivisionResidencia;
import lopicost.spd.model.Farmacia;

public class ControlPrincipioActivoForm  extends GenericForm {

	
	private String idFarmacia = "";
	private String nomGtVm = "";
	private String codGtVm = "";
	private List<ControlPrincipioActivo> listaControlPrincipioActivo = new ArrayList<ControlPrincipioActivo>();
	private List<Farmacia> listaFarmacias = new ArrayList<Farmacia>();
	
	
	public String getIdFarmacia() 	{		return idFarmacia;	}	public void setIdFarmacia(String idFarmacia) 	{		this.idFarmacia = idFarmacia;	}
	public String getNomGtVm() 		{		return nomGtVm;		}	public void setNomGtVm(String nomGtVm) 			{		this.nomGtVm = nomGtVm;			}
	public String getCodGtVm() 		{		return codGtVm;		}	public void setCodGtVm(String codGtVm) 			{		this.codGtVm = codGtVm;			}
	public List<ControlPrincipioActivo> getListaControlPrincipioActivo() 									{		return listaControlPrincipioActivo;								}
	public void setListaControlPrincipioActivo(List<ControlPrincipioActivo> listaControlPrincipioActivo) 	{		this.listaControlPrincipioActivo = listaControlPrincipioActivo;	}
	public List<Farmacia> getListaFarmacias() 						{		return listaFarmacias;					}
	public void setListaFarmacias(List<Farmacia> listaFarmacias) 	{		this.listaFarmacias = listaFarmacias;	}
	

	
	
}

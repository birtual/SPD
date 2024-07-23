package lopicost.spd.model;

import java.io.Serializable;
import java.util.Date;

public class SustXComposicion implements Serializable {
	private int oidSustXComposicion;
	private Date fechaCreacion;
	
	private String idRobot;
	
	private String cn6;
	private String cn7;
	private String nombreMedicamento;
	private String rentabilidad;
	private String nota;
	private String ponderacion;
	private String codiLab;
	private String nombreLab;
	private String comentarios;
	private String tolva;
	private String sustituible;
	private String aplicarNivelGtvmp;
	
	private Date ultimaModificacion;
	  
	private String codigoGT ="";
	private String grupoTerapeutico ="";
		//private String codiPactivo ="";
		//private String nomPactivo =""; se cambia por GTVM 
		
		
	private String codGtAtcNivel3 =""; 
	private String nomGtAtcNivel3 ="";		// nivel de grupo terapéutico de la ATC (mismo subgrupo farmacológico/terapéutico).
	private String codGtAtcNivel4 ="";
	private String nomGtAtcNivel4 ="";		// nivel de grupo terapéutico de la ATC (mismo subgrupo farmacológico/terapéutico).
	private String codGtAtcNivel5 ="";
	private String nomGtAtcNivel5 ="";		// nivel de grupo terapéutico de la ATC (mismo principio activo)
	private String codGtVm ="";				//= Principio activo
	private String nomGtVm =""; 
	private String codGtVmp ="";			
	private String nomGtVmp ="";			
	private String codGtVmpp =""; 			//Es conjunto homog
	private String nomGtVmpp ="";			// grupo VMP (igual principio activo, dosis y forma farmacéutica).
	  
	private String tipoCarga ="";			//masiva o por formulario.

	public SustXComposicion(int oidSustXComposicion, Date fecha, String idRobot, String codGtVmp, String nomGtVmp,
			String rentabilidad, String ponderacion, String codiLab, String nombreLab, String comentarios, Date ultimaModificacion) {
		super();
		this.oidSustXComposicion = oidSustXComposicion;
		this.idRobot = idRobot;
		this.fechaCreacion = fechaCreacion;
		this.codGtVmp = codGtVmp;
		this.nomGtVmp = nomGtVmp;
		this.rentabilidad = rentabilidad;
		this.ponderacion = ponderacion;
		this.codiLab = codiLab;
		this.nombreLab = nombreLab;
		this.comentarios = comentarios;
		this.ultimaModificacion = ultimaModificacion;
	}
	
	public SustXComposicion() {
		super();
	}

	public Date getUltimaModificacion() 						{		return ultimaModificacion;						}
	public void setUltimaModificacion(Date ultimaModificacion) 	{		this.ultimaModificacion = ultimaModificacion;	}

	public String getNombreLab() 								{		return nombreLab;								}
	public void setNombreLab(String nombreLab) 					{		this.nombreLab = nombreLab;						}

	public String getIdRobot() 									{		return idRobot;									}
	public void setIdRobot(String idRobot) 						{		this.idRobot = idRobot;							}

	public int getOidSustXComposicion() 						{		return oidSustXComposicion;						}
	public void setOidSustXComposicion(int oidSustXComposicion) {		this.oidSustXComposicion = oidSustXComposicion;	}

	public Date getFechaCreacion() 								{		return fechaCreacion;							}
	public void setFechaCreacion(Date fechaCreacion) 			{		this.fechaCreacion = fechaCreacion;				} 

	public String getCn6() 										{		return cn6;										}
	public void setCn6(String cn6) 								{		this.cn6 = cn6;									}

	public String getCn7() 										{		return cn7;										}
	public void setCn7(String cn7) 								{		this.cn7 = cn7;									}
	
	public String getNombreMedicamento()						{		return nombreMedicamento;						}
	public void setNombreMedicamento(String nombreMedicamento) 	{		this.nombreMedicamento = nombreMedicamento;		}

	public String getRentabilidad() 								{		return rentabilidad;							}
	public void setRentabilidad(String rentabilidad) 			{		this.rentabilidad = rentabilidad;				}

	public String getPonderacion() 								{		return ponderacion;								}
	public void setPonderacion(String ponderacion) 				{		this.ponderacion = ponderacion;					}

	public String getCodiLab() 									{		return codiLab;									}
	public void setCodiLab(String codiLab) 						{		this.codiLab = codiLab;							}

	public String getComentarios() 								{		return comentarios;								}
	public void setComentarios(String comentarios) 				{		this.comentarios = comentarios;					}

	public String getCodigoGT() 								{		return codigoGT;								}
	public void setCodigoGT(String codigoGT) 					{		this.codigoGT = codigoGT;						}

	public String getGrupoTerapeutico() 						{		return grupoTerapeutico;						}
	public void setGrupoTerapeutico(String grupoTerapeutico) 	{		this.grupoTerapeutico = grupoTerapeutico;		}

	public String getCodGtAtcNivel3() 							{		return codGtAtcNivel3;							}
	public void setCodGtAtcNivel3(String codGtAtcNivel3) 		{		this.codGtAtcNivel3 = codGtAtcNivel3;			}

	public String getNomGtAtcNivel3() 							{		return nomGtAtcNivel3;							}
	public void setNomGtAtcNivel3(String nomGtAtcNivel3) 		{		this.nomGtAtcNivel3 = nomGtAtcNivel3;			}

	public String getCodGtAtcNivel4() 							{		return codGtAtcNivel4;							}
	public void setCodGtAtcNivel4(String codGtAtcNivel4) 		{		this.codGtAtcNivel4 = codGtAtcNivel4;			}

	public String getNomGtAtcNivel4() 							{		return nomGtAtcNivel4;							}
	public void setNomGtAtcNivel4(String nomGtAtcNivel4) 		{		this.nomGtAtcNivel4 = nomGtAtcNivel4;			}

	public String getCodGtAtcNivel5() 							{		return codGtAtcNivel5;							}
	public void setCodGtAtcNivel5(String codGtAtcNivel5) 		{		this.codGtAtcNivel5 = codGtAtcNivel5;			}

	public String getNomGtAtcNivel5() 							{		return nomGtAtcNivel5;							}
	public void setNomGtAtcNivel5(String nomGtAtcNivel5) 		{		this.nomGtAtcNivel5 = nomGtAtcNivel5;			}

	public String getCodGtVmp() 								{		return codGtVmp;								}
	public void setCodGtVmp(String codGtVmp) 					{		this.codGtVmp = codGtVmp;						}

	public String getNomGtVmp() 								{		return nomGtVmp;								}
	public void setNomGtVmp(String nomGtVmp) 					{		this.nomGtVmp = nomGtVmp;						}

	public String getCodGtVmpp() 								{		return codGtVmpp;								}
	public void setCodGtVmpp(String codGtVmpp) 					{		this.codGtVmpp = codGtVmpp;						}

	public String getNomGtVmpp() 								{		return nomGtVmpp;								}
	public void setNomGtVmpp(String nomGtVmpp) 					{		this.nomGtVmpp = nomGtVmpp;						}

	public String getNota() 										{		return nota;									}
	public void setNota(String nota) 							{		this.nota = nota;								}
	
	public String getCodGtVm() 									{		return codGtVm;									}
	public void setCodGtVm(String codGtVm) 						{		this.codGtVm = codGtVm;							}

	public String getNomGtVm() 									{		return nomGtVm;									}
	public void setNomGtVm(String nomGtVm) 						{		this.nomGtVm = nomGtVm;							}

	public String getTipoCarga() 								{		return tipoCarga;								}
	public void setTipoCarga(String tipoCarga) 					{		this.tipoCarga = tipoCarga;						}

	public String getSustituible() 								{		return sustituible;								}
	public void setSustituible(String sustituible) 				{		this.sustituible = sustituible;					}

	public String getAplicarNivelGtvmp() 						{		return aplicarNivelGtvmp;						}
	public void setAplicarNivelGtvmp(String aplicarNivelGtvmp) 	{		this.aplicarNivelGtvmp = aplicarNivelGtvmp;		}

	public String getTolva() 									{		return tolva;									}
	public void setTolva(String tolva) 							{		this.tolva = tolva;								}

	
	public String toString() {
		return "SustXComposicion [oidSustXComposicion=" + oidSustXComposicion + ", fechaCreacion=" + fechaCreacion
				+ ", rentabilidad=" + rentabilidad+ ", ponderacion=" + ponderacion + ", codiLab=" + codiLab + ", nombreLab=" + nombreLab
				+ ", comentarios=" + comentarios + ", ultimaModificacion=" + ultimaModificacion + ", codigoGT=" + codigoGT 
				+ ", grupoTerapeutico=" + grupoTerapeutico //+ ", codiPactivo=" + codiPactivo + ", nomPactivo=" + nomPactivo 
				+ ", codGtAtcNivel3=" + codGtAtcNivel3 + ", nomGtAtcNivel3=" + nomGtAtcNivel3
				+ ", codGtAtcNivel4=" + codGtAtcNivel4 + ", nomGtAtcNivel4=" + nomGtAtcNivel4 
				+ ", codGtAtcNivel5=" + codGtAtcNivel5 + ", nomGtAtcNivel5=" + nomGtAtcNivel5 
				+ ", codGtVmp=" + codGtVmp + ", nomGtVmp=" + nomGtVmp 
				+ ", codGtVmpp=" + codGtVmpp + ", nomGtVmpp=" + nomGtVmpp + ", toString()="
				+ super.toString() + "]";
	}

	public String toStringForExportCSV() {
		return  " " + oidSustXComposicion + "; " + fechaCreacion + "; " + codGtVmp + "; " +  nomGtVmp + "; " + rentabilidad
				 + "; " +  ponderacion  + "; " +  codiLab  + "; " +  nombreLab  + "; " +  comentarios  + "; " + ultimaModificacion 
				 + "; " +  codigoGT  + "; " +  grupoTerapeutico  + "; " +  codGtVm  + "; " +  nomGtVm  + "; " +  codGtAtcNivel3 
				 + "; " +  nomGtAtcNivel3  + "; " +  codGtAtcNivel4  + "; " +  nomGtAtcNivel4  + "; " + codGtAtcNivel5  + "; " +  nomGtAtcNivel5 
				 + "; " +  codGtVmpp  + "; " +  nomGtVmpp + "; " +  codGtVmp  + "; " +  nomGtVmp+ " ";
	}
	

	
}
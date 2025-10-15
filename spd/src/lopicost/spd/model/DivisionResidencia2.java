package lopicost.spd.model;

import java.io.Serializable;
import java.util.Date;

public class DivisionResidencia2 implements Serializable {
	  private int oidResidencia;
	  private int oidDivisionResidencia;
	  private String idDivisionResidencia;
	  private String nombreDivisionResidencia;
	  private String idResidencia;
	  private Residencia residencia;
	  private Farmacia farmacia;
	  private Robot robot;
	  private String idFarmacia;
	  private String idRobot;
	  private String idLayout;
	  private String nombreBolsa;
	  private String idProcessIospd;
	  private Date fechaAlta;
	  private int extRE;
	  private int extRE_diaSemana;
	  private String extRE_diaSemanaLiteral;
	  private String tipoCLIfarmatic;
	  private String locationId; //nombre que aparece en el robot, como identificador de la carga, no en la bolsa

	  
	public DivisionResidencia2() {
		super();
	}


	public String getIdDivisionResidencia() 	{	return idDivisionResidencia;	}	public void setIdDivisionResidencia(String idDivResidencia) {		this.idDivisionResidencia = idDivResidencia;}
	public String getNombreDivisionResidencia() {	return nombreDivisionResidencia;}	public void setNombreDivisionResidencia(String nDivResi)	{		this.nombreDivisionResidencia = nDivResi;	}
	public String getIdResidencia() 			{	return idResidencia;			}	public void setIdResidencia(String idResidencia)			{		this.idResidencia = idResidencia;			}
	public String getIdFarmacia() 				{	return idFarmacia;				}	public void setIdFarmacia(String idFarmacia) 				{		this.idFarmacia = idFarmacia;				}
	public String getIdRobot() 					{	return idRobot;					}	public void setIdRobot(String idRobot) 						{		this.idRobot = idRobot;						}
	public Date getFechaAlta() 					{	return fechaAlta;				}	public void setFechaAlta(Date fechaAlta) 					{		this.fechaAlta = fechaAlta;					}
	public int getOidDivisionResidencia() 		{	return oidDivisionResidencia;	}	public void setOidDivisionResidencia(int oidDivResi) 		{		this.oidDivisionResidencia = oidDivResi;	}
	public int getExtRE() 						{	return extRE;					}	public void setExtRE(int extRE) 							{		this.extRE = extRE;							}
	public int getExtRE_diaSemana() 			{	return extRE_diaSemana;			}	public void setExtRE_diaSemana(int extRE_diaSemana) 		{		this.extRE_diaSemana = extRE_diaSemana;		}
	public String getExtRE_diaSemanaLiteral() 	{	return extRE_diaSemanaLiteral;	}	public void setExtRE_diaSemanaLiteral(String extRE)			{		this.extRE_diaSemanaLiteral = extRE;		}
	public String getIdLayout() 				{	return idLayout;				}	public void setIdLayout(String idLayout) 					{		this.idLayout = idLayout;					}
	public String getLocationId() 				{	return locationId;				}	public void setLocationId(String locationId) 				{		this.locationId = locationId;				}
	public String getNombreBolsa() 				{	return nombreBolsa;				}	public void setNombreBolsa(String nombreBolsa)				{		this.nombreBolsa = nombreBolsa;				}
	public String getIdProcessIospd() 			{	return idProcessIospd;			}	public void setIdProcessIospd(String idProcessIospd) 		{		this.idProcessIospd = idProcessIospd;		}
	public int getOidResidencia() 				{	return oidResidencia;			}	public void setOidResidencia(int oidResidencia) 			{		this.oidResidencia = oidResidencia;			}
	public Farmacia getFarmacia() 				{	return farmacia;				}	public void setFarmacia(Farmacia farmacia) 					{		this.farmacia = farmacia;					}
	public Residencia getResidencia() 			{	return residencia;				}	public void setResidencia(Residencia residencia) 			{		this.residencia = residencia;				}
	public Robot getRobot() 					{	return robot;					}	public void setRobot(Robot robot) 							{		this.robot = robot;							}
	public String getTipoCLIfarmatic() 			{	return tipoCLIfarmatic;			}	public void setTipoCLIfarmatic(String tipoCLIfarmatic) 		{		this.tipoCLIfarmatic = tipoCLIfarmatic;		}

	
	
}

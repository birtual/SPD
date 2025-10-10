package lopicost.spd.robot.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class DetallesTomasBean implements Serializable  {
	
	String CIP;
	String planta="";
	String habitacion="";
	String CN=null;
	String nombreMedicamento=null;
	String orderNumber =null;
	
	String pautaResidencia="";

	Date dateInicioTratamiento=null;
	Date dateFinTratamiento=null;
	
    Date dateDesde=null;
    Date dateHasta=null;
    
  
	boolean spdD1=false; 
	boolean spdD2=false;
	boolean spdD3=false;
	boolean spdD4=false;
	boolean spdD5=false;
	boolean spdD6=false;
	boolean spdD7=false;
	
	String dispensar="N";

	List<String> resiTomas = new ArrayList<String>();


	
	public String getCIP() 					{		return CIP;						}	public void setCIP(String cIP) 						{		CIP = cIP;								}	
	public String getOrderNumber()  		{		return orderNumber;				}	public void setOrderNumber(String orderNumber) 		{		this.orderNumber = orderNumber;			}
	public String getPlanta() 				{		return planta;					}	public void setPlanta(String planta) 				{		this.planta = planta;					}
	public String getHabitacion() 			{		return habitacion;				}	public void setHabitacion(String habitacion)		{		this.habitacion = habitacion;			}
	public String getCN() 					{		return CN;						}	public void setCN(String cN) 						{		CN = cN;								}
	public String getNombreMedicamento()	{		return nombreMedicamento;		}	public void setNombreMedicamento(String nombre) 	{		this.nombreMedicamento = nombre;		}
	public Date getDateInicioTratamiento() 	{		return dateInicioTratamiento;	}	public void setDateInicioTratamiento(Date dateInici){		this.dateInicioTratamiento = dateInici;	}
	public Date getDateFinTratamiento() 	{		return dateFinTratamiento;		}	public void setDateFinTratamiento(Date dateFin) 	{		this.dateFinTratamiento = dateFin;		}
	public String getPautaResidencia() 		{		return pautaResidencia;			}	public void setPautaResidencia(String pauta) 		{		this.pautaResidencia = pauta;			}
	/*
	public Date getDateFinTratamiento() throws ParseException 	
	{		
		if(dateFinTratamiento==null || dateFinTratamiento.equals("")) 
		{
			SimpleDateFormat formatoObjetivo = new SimpleDateFormat("dd/MM/yyyy");
			dateFinTratamiento= (Date) formatoObjetivo.parse("01/12/3000");
		}
		return dateFinTratamiento;		
	}	
	*/

	public Date getDateDesde() 			{	return dateDesde;		}	public void setDateDesde(Date dateDesde) 		{		this.dateDesde = dateDesde;	}
	public Date getDateHasta() 			{	return dateHasta;		}	public void setDateHasta(Date dateHasta) 		{		this.dateHasta = dateHasta;	}
	public List<String> getResiTomas() 	{	return resiTomas;		}	public void setResiTomas(List<String> resiTomas){		this.resiTomas = resiTomas;	}
	
	public boolean isSpdD1() {		return spdD1;	}	public void setSpdD1(boolean spdD1) {		this.spdD1 = spdD1;	}
	public boolean isSpdD2() {		return spdD2;	}	public void setSpdD2(boolean spdD2) {		this.spdD2 = spdD2;	}
	public boolean isSpdD3() {		return spdD3;	}	public void setSpdD3(boolean spdD3) {		this.spdD3 = spdD3;	}
	public boolean isSpdD4() {		return spdD4;	}	public void setSpdD4(boolean spdD4) {		this.spdD4 = spdD4;	}
	public boolean isSpdD5() {		return spdD5;	}	public void setSpdD5(boolean spdD5) {		this.spdD5 = spdD5;	}
	public boolean isSpdD6() {		return spdD6;	}	public void setSpdD6(boolean spdD6) {		this.spdD6 = spdD6;	}
	public boolean isSpdD7() {		return spdD7;	}	public void setSpdD7(boolean spdD7) {		this.spdD7 = spdD7;	}
	
	
	public String getDispensar() {		return dispensar;	}	public void setDispensar(String dispensar) {		this.dispensar = dispensar;	}



}

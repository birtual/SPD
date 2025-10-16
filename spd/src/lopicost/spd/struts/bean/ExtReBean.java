package lopicost.spd.struts.bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

import lopicost.spd.utils.SPDConstants;

public class ExtReBean {
	
	  private String  idDivisionResidencia;
	  private String  nombreDivisionResidencia;
	  private int  cipsActivos;
	  private int  cipsProcesadosTrat;
	  private int  cipsProcesadosTratNo;
	  private int  cipsProcesadosTratError;
	  private int  cipsProcesadosRecPend;
	  private int  cipsProcesadosRecPendNo;
	  private int  cipsProcesadosRecPendError;
	  private int  tratamientosProcesadosResi;
	  private String fechaUltimoProcesoTrat;
	  private String fechaUltimoProcesoRecPend;
	  private String  ultimoCIPProcesado;
	  
	  private int  percentTrat;
	  private boolean errorFechaRecogidaTrat;
	  private boolean errorDatosProcesadosTrat;
	  
	  private int  percentRecPend;
	  private boolean errorFechaRecogidaRecPend;
	  private boolean errorDatosProcesadosRecPend;
	  
	  private String textoAviso="";
	  
	  

	public ExtReBean() {
		super();
	}

	public ExtReBean(String idDivisionResidencia, String  nombreDivisionResidencia, int tratamientosProcesadosResi, int cipsActivos
			, String fechaUltimoProcesoTrat, int  cipsProcesadosTrat, int  cipsProcesadosTratNo
			, String fechaUltimoProcesoRedPend, int  cipsProcesadosRecPend, int  cipsProcesadosRecPendNo
			, String  ultimoCIPProcesado) {
		this.idDivisionResidencia = idDivisionResidencia;
		this.nombreDivisionResidencia = nombreDivisionResidencia;
		this.cipsActivos = cipsActivos;
		this.cipsProcesadosTrat = cipsProcesadosTrat;
		this.cipsProcesadosTratNo = cipsProcesadosTratNo;
		this.cipsProcesadosTratError = cipsActivos - cipsProcesadosTrat - cipsProcesadosTratNo;
		this.tratamientosProcesadosResi = tratamientosProcesadosResi;
		this.fechaUltimoProcesoTrat = fechaUltimoProcesoTrat;
		this.cipsProcesadosRecPend = cipsProcesadosRecPend;		
		this.cipsProcesadosRecPendNo = cipsProcesadosRecPendNo;		
		this.cipsProcesadosRecPendError = cipsActivos - cipsProcesadosRecPend - cipsProcesadosRecPendNo;		
		this.fechaUltimoProcesoRecPend = fechaUltimoProcesoRedPend;
		this.ultimoCIPProcesado = ultimoCIPProcesado;
		this.percentTrat = getPercentTrat();
		this.errorFechaRecogidaTrat = isErrorFechaRecogidaTrat();
		this.errorDatosProcesadosTrat = isErrorDatosProcesadosTrat();
		this.percentRecPend = getPercentRecPend();
		this.errorFechaRecogidaRecPend = isErrorFechaRecogidaRecPend();
		this.errorDatosProcesadosRecPend = isErrorDatosProcesadosRecPend();
	}

	public int getCipsProcesadosTratError() 									{ 		return cipsProcesadosTratError;								}
	public void setCipsProcesadosTratError(int cipsProcesadosTratError) 		{		this.cipsProcesadosTratError = cipsProcesadosTratError;		}
	public int getCipsProcesadosRecPendError() 									{		return cipsProcesadosRecPendError;							}
	public void setCipsProcesadosRecPendError(int cipsProcesadosRecPendError) 	{		this.cipsProcesadosRecPendError = cipsProcesadosRecPendError;}
	public String getIdDivisionResidencia() 									{		return idDivisionResidencia;								}
	public void setIdDivisionResidencia(String idDivisionResidencia) 			{		this.idDivisionResidencia = idDivisionResidencia;			}
	public String getNombreDivisionResidencia()									{		return nombreDivisionResidencia;							}
	public void setNombreDivisionResidencia(String nombreDivisionResidencia)	{		this.nombreDivisionResidencia = nombreDivisionResidencia;	}
	public int getCipsActivos() 												{		return cipsActivos;											}
	public void setCipsActivos(int cipsActivos) 								{		this.cipsActivos = cipsActivos;								}
	public int getCipsProcesadosRecPend() 										{		return cipsProcesadosRecPend;								}
	public void setCipsProcesadosRecPend(int cipsProcesadosRecPend) 			{		this.cipsProcesadosRecPend = cipsProcesadosRecPend;			}
	public int getCipsProcesadosTrat() 											{		return cipsProcesadosTrat;									}
	public void setCipsProcesadosTrat(int cipsProcesadosTrat) 					{		this.cipsProcesadosTrat = cipsProcesadosTrat;				}
	public int getTratamientosProcesadosResi() 									{		return tratamientosProcesadosResi;							}
	public void setTratamientosProcesadosResi(int tratamientosProcesadosResi) 	{		this.tratamientosProcesadosResi = tratamientosProcesadosResi;}
	public String getFechaUltimoProcesoTrat() 									{		return fechaUltimoProcesoTrat;								}
	public void setFechaUltimoProcesoTrat(String fechaUltimoProcesoTrat) 		{		this.fechaUltimoProcesoTrat = fechaUltimoProcesoTrat;		}
	public String getFechaUltimoProcesoRecPend() 								{		return fechaUltimoProcesoRecPend;							}
	public void setFechaUltimoProcesoRecPend(String fechaUltimoProcesoRedPend) 	{		this.fechaUltimoProcesoRecPend = fechaUltimoProcesoRedPend;	}
	public String getUltimoCIPProcesado() 										{		return ultimoCIPProcesado;									}
	public void setUltimoCIPProcesado(String ultimoCIPProcesado) 				{		this.ultimoCIPProcesado = ultimoCIPProcesado;				}
	public int getCipsProcesadosTratNo() 										{		return cipsProcesadosTratNo;								}
	public void setCipsProcesadosTratNo(int cipsProcesadosTratNo) 				{		this.cipsProcesadosTratNo = cipsProcesadosTratNo;			}
	public int getCipsProcesadosRecPendNo() 									{		return cipsProcesadosRecPendNo;								}
	public void setCipsProcesadosRecPendNo(int cipsProcesadosRecPendNo) 		{		this.cipsProcesadosRecPendNo = cipsProcesadosRecPendNo;		}
	public String getTextoAviso() 												{		return textoAviso;											}
	public void setTextoAviso(String textoAviso) 								{		this.textoAviso = textoAviso;								}

	
	
	public int getPercentTrat() {
        if (this.cipsActivos == 0) {
            return 0; // Evitar división por cero
        }
        //return (int) Math.round((double) (this.cipsProcesadosTrat-this.cipsProcesadosTratNo) / this.cipsActivos * 100); // Redondear al entero más cercano
        return (int) Math.round((double) (this.cipsProcesadosTrat+this.cipsProcesadosTratNo) / this.cipsActivos * 100); // Redondear al entero más cercano
	}
	public int getPercentRecPend() {
        if (this.cipsActivos == 0) {
            return 0; // Evitar división por cero
        }
      //  return (int) Math.round((double) (this.cipsProcesadosRecPend-this.cipsProcesadosRecPendNo) / this.cipsActivos * 100); // Redondear al entero más cercano
        return (int) Math.round((double) (this.cipsProcesadosRecPend + this.cipsProcesadosRecPendNo) / this.cipsActivos * 100); // Redondear al entero más cercano
	}

	
	public boolean isErrorFechaRecogidaTrat() {
		return 	checkFecha(SPDConstants.DIAS_MAX_RECOGIDA_IOFWIN, this.fechaUltimoProcesoTrat);
	}

	public boolean isErrorFechaRecogidaRecPend() {
		return 	checkFecha(SPDConstants.DIAS_MAX_RECOGIDA_IOFWIN, this.fechaUltimoProcesoRecPend);
	}

		
	/**
	 * control de la recogida, para detectar la última fecha 
	 * @return
	 
	public boolean checkFecha(int diasMax, String fecha) {
		//this.fechaUltimoProcesoTrat=
		// Formato de la fecha
	    //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
	    // Convertir el string a LocalDate
		//LocalDate inputDate = LocalDate.parse(fecha, formatter);
		LocalDate inputDate = LocalDate.parse(fecha, SPDConstants.FORMAT_DATETIME_24h);
	    // Obtener la fecha actual
	    LocalDate today = LocalDate.now();
        // Calcular la diferencia en días
        long daysBetween = ChronoUnit.DAYS.between(inputDate, today);
				
        if (daysBetween > diasMax) return true;
            //System.out.println("La fecha excede el número máximo de días.");
        else 
        	//System.out.println("La fecha está dentro del límite.");
        	return false;
	}*/
	
	/**
	 * control de la recogida, para detectar la última fecha 
	 * @param diasMax
	 * @param fecha
	 * @return
	 */
	public boolean checkFecha(int diasMax, String fecha) {
		if(fecha==null || fecha.equals("")) return false;
	    LocalDate inputDate = null;

	    DateTimeFormatter formatterConHora = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm:ss");
	    DateTimeFormatter formatterSoloFecha = DateTimeFormatter.ofPattern("d/M/yyyy");

	    // Intentar parsear con hora
	    try {
	        LocalDateTime ldt = LocalDateTime.parse(fecha, formatterConHora);
	        inputDate = ldt.toLocalDate();
	    } catch (DateTimeParseException e1) {
	        try {
	            inputDate = LocalDate.parse(fecha, formatterSoloFecha);
	        } catch (DateTimeParseException e2) {
	            // Formato inválido
	            throw new IllegalArgumentException("Formato de fecha inválido: " + fecha);
	        }
	    }

	    // Obtener la fecha actual
	    LocalDate today = LocalDate.now();

	    // Calcular la diferencia en días
	    long daysBetween = ChronoUnit.DAYS.between(inputDate, today);

	    return daysBetween > diasMax;
	}


	/**
	 * control de la recogida, para detectar si los datos se han procesado ok
	 * @return
	 */
	
	public boolean isErrorDatosProcesadosTrat() {
		int percentMinTrat= SPDConstants.PERCENT_MIN_RECOGIDA_DATOS_IOFWIN;
		if(getPercentTrat()<percentMinTrat)
			return true;
		return false;
	}

	public boolean isErrorDatosProcesadosRecPend() {
		int percentMinRecPend= SPDConstants.PERCENT_MIN_RECOGIDA_DATOS_IOFWIN;
		if(getPercentRecPend()<percentMinRecPend)
			return true;
		return false;
	}

	
	
}

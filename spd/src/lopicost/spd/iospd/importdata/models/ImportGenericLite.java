
package lopicost.spd.iospd.importdata.models;

import lopicost.spd.controller.ControlSPD;
import lopicost.spd.controller.SpdLogAPI;
import lopicost.spd.excepciones.ColumnasInsuficientesException;
import lopicost.spd.excepciones.MaxLineasNulasException;
import lopicost.spd.helper.FicheroResiDetalleHelper;
import lopicost.spd.iospd.importdata.process.ImportProcessImpl;
import lopicost.spd.persistence.FicheroResiCabeceraDAO;
import lopicost.spd.persistence.FicheroResiDetalleDAO;
import lopicost.spd.persistence.PacienteDAO;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.struts.bean.PacienteBean;
import lopicost.spd.utils.DataUtil;
import lopicost.spd.utils.DateUtilities;
import lopicost.spd.utils.HelperSPD;
import lopicost.spd.utils.SPDConstants;
import lopicost.spd.utils.StringUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import java.util.Vector;


public class ImportGenericLite extends ImportProcessImpl
{
	String CIPanterior="";
	int nulasSeguidas=0;
	int numeroDoses=0;
	int oidFicheroResiCabecera= 0;
	int reg = 11;  //numeroCorteCabecera  / celda de la fecha inicio, que es la obligatoria. a partir de aquí pueden venir vacías
	TreeMap rowsTratados =new TreeMap();
	TreeMap<String, String>  cipsFicheroAnexo =new TreeMap<>(); // se guardan los CIPS que se cargan de nuevo, para borrar previamente el tratamiento y cargarlo con el nuevo fichero
	
	public ImportGenericLite(){
		super();
	}

    protected boolean beforeProcesarEntrada(Vector row) throws Exception 
    {
    	//comprobación de que no es linea vacía, solo tendría dos caracteres, "[" y "]".
    	if(row!=null)
    	{
        	String rowString=row.toString().replace(",", "").replace(" ", "");
        	
        	if(rowString.length()==2)
        		return false;
    	}
    	//else return false;
    			
    	return true;
    }
    protected void afterprocesarEntrada(Vector row) throws Exception 
    {
    	
    }
 

    public void procesarCabecera(String idDivisionResidencia, String idProceso) throws Exception 
    {	
    	boolean recuperaPlantillaCabecera = true;
    	if(isCargaAnexa())	//si es una carga auxiliar recuperamos la cabecera del proceso donde lo cargamos
        	recuperaPlantillaCabecera = false;

    	
 		//recuperamos la cabecera del listado
		FicheroResiBean cab =  FicheroResiDetalleHelper.getCabeceraFicheroResi(getSpdUsuario(), idDivisionResidencia, idProceso, recuperaPlantillaCabecera);
		
		if(cab==null)  //si no devuelve nada, vamos a la cabecera por defecto 
			cab =  FicheroResiDetalleHelper.getCabeceraFicheroResi(getSpdUsuario(), idDivisionResidencia, idProceso, true);

		//esta ser�a la cabecera top del proceso
		FicheroResiBean cabeceraTop=FicheroResiCabeceraDAO.getFicheroResiCabeceraByOid(getSpdUsuario(), cab.getOidFicheroResiCabecera());
		if(cabeceraTop!=null && cabeceraTop.getErrores()!=null && !cabeceraTop.getErrores().equals(""))
			errors.add( cabeceraTop.getErrores() );
		
		numeroDoses=cab.getNumeroDeTomas();
		this.oidFicheroResiCabecera= ioSpdApi.getOidFicheroResiCabecera(getSpdUsuario(), idDivisionResidencia, idProceso);
		cab.setOidFicheroResiCabecera(oidFicheroResiCabecera);
		cab.setFechaDesde(HelperSPD.obtenerFechaDesde(idProceso));  
    	cab.setFechaHasta(HelperSPD.obtenerFechaHasta(idProceso)); 

    	
		if(!isCargaAnexa())
		FicheroResiDetalleDAO.nuevo(idDivisionResidencia, idProceso, cab);
		
     }

 
 
    /*
     * Las Onadas llegan con 5 o 6  pautas de tomas--> primera hora(opcional) / quincdesayuno / comida / merienda / cena / resopón
     * (non-Javadoc)
     * @see lopicost.spd.iospd.importdata.process.ImportProcessImpl#procesarEntrada(java.lang.String, java.lang.String, java.util.Vector, int)
     */
    //public void procesarEntrada(String idRobot, String idDivisionResidencia, String idProceso, Vector row, int count) throws Exception 
    public boolean procesarEntrada(String idRobot, String idDivisionResidencia, String idProceso, Vector row, int count, boolean cargaAnexa) throws Exception
    {
       	boolean finalizar = false;
       //	System.out.println( "--> procesarEntrada. INICIO row  "  + new Date() ); 		
       	
	   	//int oidFicheroResiCabecera= ioSpdApi.getOidFicheroResiCabecera(getSpdUsuario(), idDivisionResidencia, idProceso);
       	
     	//if (row!=null && row.size()>=reg+1) así está en resi+
    	if (row!=null && row.size()>=reg && nulasSeguidas<SPDConstants.MAX_LINEAS_NULAS_CARGA)  //20250901 - Control de m�x l�neas nulas 
        {
    		if (this.rowsTratados.containsKey(String.valueOf(row))) {
    			throw new Exception ("Es un tratamiento que est� duplicado ");
    		}
    		this.rowsTratados.put(String.valueOf(row), String.valueOf(row));
    		
    	    FicheroResiBean medResi= new FicheroResiBean();
    		medResi.setOidFicheroResiCabecera(this.oidFicheroResiCabecera);
       		medResi.setRow(count);
    		medResi.setIdProceso(idProceso);
    		medResi.setIdDivisionResidencia(idDivisionResidencia);
    		medResi.setTipoRegistro(SPDConstants.REGISTRO_LINEA);
    		recogerDatosRow(medResi, row);		

    	   	
    	   	
        	int diasSemanaMarcados=HelperSPD.getDiasMarcados(medResi);  //importante!! para que detecte que hay días marcados y no los llene automáticamente.
        	medResi.setDiasSemanaMarcados(diasSemanaMarcados);
        	
        	medResi.setFechaDesde(HelperSPD.obtenerFechaDesde(medResi.getIdProceso()));  
        	medResi.setFechaHasta(HelperSPD.obtenerFechaHasta(medResi.getIdProceso())); 
        	
        	
        //	HelperSPD.controlAlertasRegistro(medResi);
        	//tratamos los casos de un segundo fichero de carga.
        	//localización de los CIPS a tratar, se borrará lo que se haya cargado previamente y se mete en un TreeMap para no borrarlo de nuevo e insertar los nuevos. 
        	if(cargaAnexa)
        	{
        		int oidCabecera =-1;
        		String CIP = medResi.getResiCIP();
        		if(!cipsFicheroAnexo.containsKey(CIP))
        		{
        			oidCabecera =  FicheroResiDetalleHelper.getCabeceraFicheroResi(getSpdUsuario(), idDivisionResidencia, idProceso);
        			FicheroResiDetalleHelper.borrarTratamientosCIPEnProceso(getSpdUsuario(), medResi);
        			medResi.setOidFicheroResiCabecera(oidCabecera);
        			cipsFicheroAnexo.put(CIP, CIP);
        		}
        		
        	}
        	
        	
        	desarrollarRegistro(medResi);
       	 
  	   		//miramos si existe, para no duplicar
  	       	boolean existe = false;
  		    existe= FicheroResiDetalleDAO.existeRegistro(getSpdUsuario(), medResi.getIdDivisionResidencia(), medResi.getIdProceso(), medResi);
  	    		
  	    	if(!existe)
  	    	{

  	  	    	ControlSPD.aplicarControles(getSpdUsuario(), medResi);
  	  	    	
  	    		FicheroResiDetalleHelper.nuevo(medResi.getIdDivisionResidencia(), medResi.getIdProceso(), medResi);
  	    		//FicheroResiDetalleDAO.nuevo(medResi.getIdDivisionResidencia(), medResi.getIdProceso(), medResi);
  		
  				System.out.println( "--> FicheroResiDetalleHelper.nuevo;  "  + medResi.getIdTratamientoSPD() );		
  	    	}
   	    		
  	       }
 	        else 
  	        {
 	        	nulasSeguidas++;
 	        	if(nulasSeguidas>=SPDConstants.MAX_LINEAS_NULAS_CARGA)
 	        	{
 	        		finalizar=true; //interesa que no se siga procesando
 	        		//throw new Exception ("Se ha superado el m�ximo l�neas nulas permitidas en la carga: " + SPDConstants.MAX_LINEAS_NULAS_CARGA);
 	        		throw new MaxLineasNulasException("Se ha superado el m�ximo l�neas nulas permitidas en la carga: " + SPDConstants.MAX_LINEAS_NULAS_CARGA);
 	        	}
 	        		
  	            //throw new Exception ("Columnas insuficientes para la importaci�n. ");
 	        	throw new ColumnasInsuficientesException("Columnas insuficientes para la importaci�n.");
  	        	
  	        }
    	System.out.println( "--> procesarEntrada. FIN row;  "  + new Date() );		
    	return finalizar;
    }
    

	protected void recogerDatosRow(FicheroResiBean medResi, Vector row) throws Exception {
	}


	/**
     * M�todo para crear un registro de inicio del proceso de carga
     * y para crear el registro de los datos de cabecera del proceso
     */

	protected boolean beforeStart(String filein) throws Exception 
    {
		boolean result=false;
		try {
			if(isCargaAnexa())
			{
				//creaci�n de log en BBDD
				try{
					SpdLogAPI.addLog(getSpdUsuario(), "",  this.getIdDivisionResidencia(),  this.getIdProceso(),  SpdLogAPI.A_PRODUCCION, SpdLogAPI.B_CARGAFICHERO, SpdLogAPI.C_START
							, "SpdLog.produccion.cargafichero.anexo", this.getIdProceso()  );
				}catch(Exception e){} // Inicio de la carga de fichero.
			}
			else 
			{
				result=ioSpdApi.addGestFicheroResi(getSpdUsuario(), this.getIdDivisionResidencia(), this.getIdProceso(), filein);
				if(result)
				{
					//creaci�n de log en BBDD
					try{
						SpdLogAPI.addLog(getSpdUsuario(), "",  this.getIdDivisionResidencia(),  this.getIdProceso(),  SpdLogAPI.A_PRODUCCION, SpdLogAPI.B_CARGAFICHERO, SpdLogAPI.C_START
								, "SpdLog.produccion.cargafichero.inicio", this.getIdProceso()  );
					}catch(Exception e){} // Inicio de la carga de fichero.
				}
			}
			
		} catch (ClassNotFoundException e) {
			throw new Exception ("Error en la carga del fichero");
		}
		procesarCabecera(this.getIdDivisionResidencia(), this.getIdProceso());

		return result || isCargaAnexa();
    }	
	
	
    /**
     * M�todo para actualizar el registro de carga
     * @throws Exception 
     */
    protected void afterStart() throws Exception 
    {
    	boolean result=false;
    	
		try {
			int cipsActivosSPD= ioSpdApi.getCipsActivosSPD(getSpdUsuario(), this.getIdDivisionResidencia());
			int cipsTotales= ioSpdApi.getCipsTotalesCargaFichero(getSpdUsuario(), this.getIdDivisionResidencia(), this.getIdProceso());
			int filasTotales= this.processedRows;
			
			if(isCargaAnexa())
				filasTotales= ioSpdApi.getLineasProceso(getSpdUsuario(), this.getIdDivisionResidencia(), this.getIdProceso());
			int porcent = 0;
			try { porcent =(cipsTotales*100/cipsActivosSPD);				}catch(Exception e){porcent =0;}

			
			//result=ioSpdApi.editaFinCargaFicheroResi(this.getIdDivisionResidencia(), this.getIdProceso(), filasTotales, cipsTotales, cipsActivosSPD, porcent, this.errors);
			result=FicheroResiDetalleHelper.editaFinCargaFicheroResi(getSpdUsuario(), this.getIdDivisionResidencia(), this.getIdProceso(), filasTotales, cipsTotales, cipsActivosSPD, porcent, this.errors);
			if(result)
			{
				//creaci�n de log en BBDD
				try{
					SpdLogAPI.addLog(getSpdUsuario(), "",  this.getIdDivisionResidencia(),  this.getIdProceso(), SpdLogAPI.A_PRODUCCION, SpdLogAPI.B_CARGAFICHERO, SpdLogAPI.C_START
							, "SpdLog.produccion.cargafichero.fin", this.getIdProceso()  );
				}catch(Exception e){}	//Fin de la carga de fichero.
			}
			
			
			result=ioSpdApi.borraDetalleSinCabecera();
			result=ioSpdApi.actualizaEstadosSinFinalizar();
			result=ioSpdApi.buscarParaPasarAHistorico();
			
			if(!cipsFicheroAnexo.isEmpty() && cipsFicheroAnexo.size()>0)
			{
				StringBuilder resultado = new StringBuilder();
		        for (String clave : cipsFicheroAnexo.keySet()) {
		        	 resultado.append(clave).append("\n\n");
		        }
		        //throw new Exception ("Actualizados los tratamientos de los siguientes CIPS:  " + resultado);
		        errors.add ("Actualizados los tratamientos de los siguientes CIPS:  " + resultado);
			}
			
			
		} catch (ClassNotFoundException e) {
				throw new Exception ("Error en la carga del fichero");
		}
    }


	protected void desarrollarRegistro(FicheroResiBean medResi )throws Exception {
		//boolean registroRobot = medResi.getSpdAccionBolsa()!=null && !medResi.getSpdAccionBolsa().equalsIgnoreCase(SPDConstants.SPD_ACCIONBOLSA_NO_PINTAR) && !medResi.getSpdAccionBolsa().equalsIgnoreCase(SPDConstants.SPD_ACCIONBOLSA_SI_PRECISA);
		boolean registroRobot = medResi.getSpdAccionBolsa()!=null && (medResi.getSpdAccionBolsa().equalsIgnoreCase(SPDConstants.SPD_ACCIONBOLSA_SOLO_INFO) || medResi.getSpdAccionBolsa().equalsIgnoreCase(SPDConstants.SPD_ACCIONBOLSA_PASTILLERO));
		boolean registroRobotPastillero = registroRobot && medResi.getSpdAccionBolsa().equalsIgnoreCase(SPDConstants.SPD_ACCIONBOLSA_PASTILLERO);

		boolean registroOriginal = (medResi!=null && medResi.getIdEstado()!=null && medResi.getIdEstado().equalsIgnoreCase(SPDConstants.REGISTRO_ORIGINAL)?true:false);
		boolean pastillero = registroRobotPastillero;
		boolean noPintar = (medResi.getSpdAccionBolsa()!=null && medResi.getSpdAccionBolsa().equalsIgnoreCase(SPDConstants.SPD_ACCIONBOLSA_NO_PINTAR)?true:false);
		boolean siPrecisa = (medResi.getSpdAccionBolsa()!=null && medResi.getSpdAccionBolsa().equalsIgnoreCase(SPDConstants.SPD_ACCIONBOLSA_SI_PRECISA)?true:false);

		//medResi.setDetalleRow(
		
  		//se intenta extraer la periodicidad de l tratamiento
		detectarPeriodoAlta(getSpdUsuario(), medResi);

       //cálculo de la dosis que se prevee que entra en la producción según lo recibido de la resi
       medResi.setPrevisionResi(ControlSPD.contarDosisProduccionResi(medResi));
       
       //tratamiento de la trazodona, por si hay medias pastillas
		if(HelperSPD.checkTrazodona(medResi))
      	{
	      		HelperSPD.changeTrazodona(getSpdUsuario(), medResi);
      	}

		//se recupera información de la producción anterior en caso que no se haya reutilizado previamente.
		boolean reutilizado = medResi.getIdEstado().equalsIgnoreCase(SPDConstants.REGISTRO_REUTILIZADO_DE_ANTERIOR_PRODUCCION); //estado);
		
		if(!reutilizado)
			HelperSPD.getDatosProduccionAnterior(getSpdUsuario(), medResi, true, true);
      	
		HelperSPD.chequeoRevisionAlta(medResi);
      	 		
	   	boolean validoParaSpd = true;
	   	
  	   	if(!registroRobot) 
	   		validoParaSpd = false;
		
  	   	//si es un registro nuevo pasamos varios de los filtros
	   	if(!reutilizado)
	    {
   	      	if(HelperSPD.checkSintrom(medResi))
	    	{
  	    	  HelperSPD.changeSintrom(medResi);
	      	}
     	  	
   	      	HelperSPD.detectarTipoEnvioHeliumLite(medResi);
   	      	HelperSPD.controlAlertasRegistro(medResi);
   	      	//HelperSPD.controlGtvmpCnResiCnSpd(medResi);
      	}
	   	else
	   	{
	   		PacienteBean paciente = PacienteDAO.getPacientePorCIP(medResi.getResiCIP());
	   		
	   		
	   		if(medResi.getIdDivisionResidencia()!=null && !medResi.getIdDivisionResidencia().equals(medResi.getIdDivisionResidencia()))
    			medResi.setMensajesInfo(SPDConstants.INFO_INTERNA_CIP_OTRA_RESI);

	   		if(paciente!=null)
    	   		//eliminamos el mensaje anterior de aviso en caso que exista.
        		HelperSPD.borrarMensajeAvisoAnterior(medResi, SPDConstants.INFO_INTERNA_CIP_SIN_ALTA, "INFO");
	   		
    	   	if(paciente!=null && paciente.getSpd()!=null && paciente.getSpd().equalsIgnoreCase("S") )
    	   		//eliminamos el mensaje anterior de aviso en caso que exista.
        		HelperSPD.borrarMensajeAvisoAnterior(medResi, SPDConstants.INFO_INTERNA_CIP_SPD_NO, "INFO");

       		if(paciente!=null && paciente.getIdDivisionResidencia()!=null && paciente.getIdDivisionResidencia().equals(medResi.getIdDivisionResidencia()))
       			HelperSPD.borrarMensajeAvisoAnterior(medResi, SPDConstants.INFO_INTERNA_CIP_OTRA_RESI, "INFO");

       		

	   	}

	       //cálculo de la dosis que se enviará a robot
		//medResi.setPrevisionSPD(ControlSPD.contarDosisProduccionSPD(medResi));
	   	
	   	if(validoParaSpd) //filtros que siempre hemos de pasar es el de desdoblamiento de secuencias
	    {
	  	      	//HelperSPD.desdoblarTratamientosSecuenciales(getSpdUsuario(), medResi, null);
				HelperSPD.desdoblarTratamientosSecuenciales(getSpdUsuario(), medResi, DateUtilities.getDate(medResi.getResiInicioTratamiento(), "dd/MM/yyyy"));
	  	      	
	  	  //    	HelperSPD.controlarMTEstrecho(getSpdUsuario(), medResi);
  	  //    	HelperSPD.controlarReceta(getSpdUsuario(), medResi);
	  	      
  	      	HelperSPD.chequearPrevisionResiSPD(medResi);
	  	      	
	  	}
	      	
  	      
		if((medResi.getResiCIP()==null || medResi.getResiCIP().equals("")) && medResi.getResiNombrePaciente()!=null && !medResi.getResiNombrePaciente().equals("")) //en caso que no exista CIP  ponemos el nombre
		{
			FicheroResiDetalleHelper.actualizaCIP(getSpdUsuario(), medResi);
			
		}   
	
		
	}

	protected String  crearDetalleRowKey(String detalleRow, List<Integer> posicionesEliminar  )throws Exception {
        
		
		// Si la lista de posiciones a eliminar es nula o vacía, devolver detalleRow
        //if (detalleRow==null  || detalleRow.isEmpty() || posicionesEliminar == null || posicionesEliminar.isEmpty()) {
        if (posicionesEliminar == null || posicionesEliminar.isEmpty()) 
        {
            if (detalleRow!=null && !detalleRow.isEmpty()) {
            	return StringUtil.quitaEspacios(detalleRow);
            }
           	else return "";
        }
     
        // Separar los detalles originales
        //String[] detalles = StringUtil.quitaEspacios(detalleRow).split(", ");
        String[] detalles = detalleRow.split(",");
     
        // Construir la nueva cadena sin los elementos en las posiciones especificadas
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < detalles.length; i++) {
            if (!deberiaEliminar(i + 1, posicionesEliminar)) { // Las posiciones en la lista comienzan desde 1
                sb.append(StringUtil.quitaEspaciosYAcentos(detalles[i], false)); //no lo ponemos UPPER para dejarlo igual que detalleRow, en caso que no entre en este for
                if (i < detalles.length - 1) {
                    sb.append(",");
                }
            }
        }
        return sb.toString();
  //      return StringUtil.quitaEspacios(sb.toString());
    }
	
	protected String  crearDetalleRowKeyLite(Vector row, String detalleRow, List<Integer> posicionesEliminar  )throws Exception {
        
		
		// Si la lista de posiciones a eliminar es nula o vacía, devolver detalleRow
        //if (detalleRow==null  || detalleRow.isEmpty() || posicionesEliminar == null || posicionesEliminar.isEmpty()) {
        if (posicionesEliminar == null || posicionesEliminar.isEmpty()) 
        {
            if (row!=null && row.size()>0) {
            	return StringUtil.quitaEspacios(detalleRow);
            }
           	else return "";
        }

     
        // Construir la nueva cadena sin los elementos en las posiciones especificadas
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < row.size(); i++) {
            if (!deberiaEliminar(i + 1, posicionesEliminar)) { // Las posiciones en la lista comienzan desde 1
            	String cadena = (String)row.get(i);
            	//cadena = HelperSPD.getDetalleRowFechasOk(cadena);
            	
                sb.append(StringUtil.limpiarTextoComentarios(StringUtil.quitaEspaciosYAcentos(cadena, false))); 
                if (i < row.size() - 1) {
                    sb.append("|");
                }  
            }
        }
        
        return sb.toString();
  //      return StringUtil.quitaEspacios(sb.toString());
    }
	

    // Método que determina si un índice debería ser eliminado
    public static boolean deberiaEliminar(int indice, List<Integer> posicionesEliminar) {
        if (posicionesEliminar == null || posicionesEliminar.isEmpty()) {
            return false;
        }
        return posicionesEliminar.contains(indice);
    }

	public List<Integer> getPosicionesAEliminar() {
		// TODO Esbozo de método generado automáticamente
		return new ArrayList<Integer>();
	}

	/**
	 * Función que intenta detectar el periodo
	 * @param medResi
	 * @throws Exception 
	 */
	   public static void detectarPeriodoAlta(String spdUsuario, FicheroResiBean medResi) throws Exception {
	     	
	        String observaciones = StringUtil.limpiarTextoEspaciosYAcentos(medResi.getResiObservaciones(), false).toLowerCase();
	        String comentarios = StringUtil.limpiarTextoEspaciosYAcentos(medResi.getResiComentarios(), false).toLowerCase();
	        String resiPeriodo = StringUtil.limpiarTextoEspaciosYAcentos(medResi.getResiPeriodo(), false).toLowerCase();
	        String resiVariante = StringUtil.limpiarTextoEspaciosYAcentos(medResi.getResiVariante(), false).toLowerCase();
	        
	        
			if(medResi.getDiasSemanaMarcados()==7 || medResi.getDiasSemanaMarcados()==0 || medResi.getResiDiasAutomaticos().equalsIgnoreCase("SI"))
			{
				
				//si llegan sin marcar, los ponemos automáticos
				if(medResi.getDiasSemanaMarcados()==0 && medResi.getResiDiasAutomaticos().equalsIgnoreCase("NO")) medResi.setResiDiasAutomaticos("SI");
				
				medResi.setDiasConToma(7);
    			medResi.setDiasSemanaMarcados(7);
    			medResi.setResiFrecuencia(1);
    			medResi.setResiD1("X");
    			medResi.setResiD2("X");
    			medResi.setResiD3("X");
    			medResi.setResiD4("X");
    			medResi.setResiD5("X");
    			medResi.setResiD6("X");
    			medResi.setResiD7("X");
    			medResi.setDiasSemanaConcretos("");
    			medResi.setDiasMesConcretos("");
    			medResi.setSecuenciaGuide("");//validar la funcionalidad
    			medResi.setResiPeriodo(SPDConstants.SPD_PERIODO_DIARIO);
    			medResi.setTipoEnvioHelium(SPDConstants.TIPO_1_DIARIO_HELIUM);
			}
			if(medResi.getDiasSemanaMarcados()<7 && medResi.getDiasSemanaMarcados()>0)	
			{
    			HelperSPD.detectarDiasMarcados(medResi);
				medResi.setTipoEnvioHelium(SPDConstants.TIPO_2_DIAS_CONCRETOS_HELIUM);
				medResi.setResiPeriodo(SPDConstants.SPD_PERIODO_DIAS_SEMANA_CONCRETOS);
    			medResi.setSecuenciaGuide("");
 			}
			
			//aqui ya miramos lo que hay en variante, pero después de mirar los dias anteriores
	        String resultVariante=tratarVariante(medResi);

	        
	        
			if ((medResi.getDiasSemanaMarcados()==1) 
					|| HelperSPD.isEqualIgnoreCase(observaciones, SPDConstants.SPD_PERIODO_SEMANAL) 
					|| HelperSPD.isEqualIgnoreCase(comentarios, SPDConstants.SPD_PERIODO_SEMANAL) 
					|| HelperSPD.isEqualIgnoreCase(resiPeriodo, SPDConstants.SPD_PERIODO_SEMANAL)
					|| HelperSPD.isEqualIgnoreCase(resultVariante, SPDConstants.SPD_PERIODO_SEMANAL)
				) 
			{
				HelperSPD.detectarDiasMarcados(medResi);
		        medResi.setResiPeriodo(SPDConstants.SPD_PERIODO_SEMANAL);
    			medResi.setSecuenciaGuide("");
		        medResi.setTipoEnvioHelium("2");
		        medResi.setResiFrecuencia(7);
    			medResi.setValidar(SPDConstants.REGISTRO_VALIDAR);
    			medResi.setConfirmar(SPDConstants.REGISTRO_CONFIRMAR);
				
				if(medResi.getDiasConToma()!=1)
				{
					medResi.setDiasConToma(1);
			        medResi.setDiasSemanaMarcados(1);
					
				}
				

				if(medResi.getDiasConToma()>0)
				{

					if(medResi.getResiD1().equalsIgnoreCase("X"))
					{
						medResi.setResiD1("X");
						medResi.setDiasSemanaConcretos(SPDConstants.DIA1);
						medResi.setResiD2("");medResi.setResiD3("");medResi.setResiD4("");medResi.setResiD5("");medResi.setResiD6("");medResi.setResiD7("");
					} else if(medResi.getResiD2().equalsIgnoreCase("X")){
						medResi.setResiD2("X");
						medResi.setDiasSemanaConcretos(SPDConstants.DIA2);
						medResi.setResiD1("");medResi.setResiD3("");medResi.setResiD4("");medResi.setResiD5("");medResi.setResiD6("");medResi.setResiD7("");
					} else if(medResi.getResiD3().equalsIgnoreCase("X")){
						medResi.setResiD3("X");
						medResi.setDiasSemanaConcretos(SPDConstants.DIA3);
						medResi.setResiD1("");medResi.setResiD2("");medResi.setResiD4("");medResi.setResiD5("");medResi.setResiD6("");medResi.setResiD7("");
					} else if(medResi.getResiD4().equalsIgnoreCase("X")){
						medResi.setResiD4("X");
						medResi.setDiasSemanaConcretos(SPDConstants.DIA4);
						medResi.setResiD1("");medResi.setResiD2("");medResi.setResiD3("");medResi.setResiD5("");medResi.setResiD6("");medResi.setResiD7("");
					} else if(medResi.getResiD5().equalsIgnoreCase("X")){
						medResi.setResiD5("X");
						medResi.setDiasSemanaConcretos(SPDConstants.DIA5);
						medResi.setResiD1("");medResi.setResiD2("");medResi.setResiD3("");medResi.setResiD4("");medResi.setResiD6("");medResi.setResiD7("");
					} else if(medResi.getResiD6().equalsIgnoreCase("X")){
						medResi.setResiD6("X");
						medResi.setDiasSemanaConcretos(SPDConstants.DIA6);
						medResi.setResiD1("");medResi.setResiD2("");medResi.setResiD3("");medResi.setResiD4("");medResi.setResiD5("");medResi.setResiD7("");
					} else 	if(medResi.getResiD7().equalsIgnoreCase("X")){
						medResi.setResiD7("X");
						medResi.setDiasSemanaConcretos(SPDConstants.DIA7);
						medResi.setResiD1("");medResi.setResiD2("");medResi.setResiD3("");medResi.setResiD4("");medResi.setResiD5("");medResi.setResiD6("");
					} 
				}
				else if(medResi.getDiasConToma()==0)
				{
					medResi.setResiD1("X");
					medResi.setResiD2("");
					medResi.setResiD3("");
					medResi.setResiD4("");
					medResi.setResiD5("");
					medResi.setResiD6("");
					medResi.setResiD7("");
					medResi.setDiasSemanaConcretos(SPDConstants.DIA1);
				}

			}
		       
			if (HelperSPD.isEqualIgnoreCase(observaciones, SPDConstants.SPD_PERIODO_QUINCENAL) || HelperSPD.isEqualIgnoreCase(comentarios, SPDConstants.SPD_PERIODO_QUINCENAL) || HelperSPD.isEqualIgnoreCase(resiPeriodo, SPDConstants.SPD_PERIODO_QUINCENAL) || HelperSPD.isEqualIgnoreCase(resultVariante, SPDConstants.SPD_PERIODO_QUINCENAL) ) 
			{
				HelperSPD.chequeoTratamientoQuincenal(spdUsuario, medResi);
				if(medResi.getDiasMesConcretos()==null || medResi.getDiasMesConcretos().equals(""))
					medResi.setDiasMesConcretos(SPDConstants.DIAS_DEFECTO_QUINCENA);
		        medResi.setTipoEnvioHelium("3");
				medResi.setResiFrecuencia(15);
				medResi.setResiPeriodo(SPDConstants.SPD_PERIODO_QUINCENAL);
    			medResi.setSecuenciaGuide("");
				medResi.setDiasSemanaConcretos("");//validar la funcionalidad
				//medResi.setRevisar("SI");


			}
			if (HelperSPD.isEqualIgnoreCase(observaciones, SPDConstants.SPD_PERIODO_MENSUAL) || HelperSPD.isEqualIgnoreCase(comentarios, SPDConstants.SPD_PERIODO_MENSUAL) || HelperSPD.isEqualIgnoreCase(resiPeriodo, SPDConstants.SPD_PERIODO_MENSUAL) || HelperSPD.isEqualIgnoreCase(resultVariante, SPDConstants.SPD_PERIODO_MENSUAL)) 
			 {
				medResi.setTipoEnvioHelium("3");
				if(medResi.getDiasMesConcretos()==null || medResi.getDiasMesConcretos().equals(""))
					medResi.setDiasMesConcretos(SPDConstants.DIAS_DEFECTO_MES);
				medResi.setResiFrecuencia(31);
				medResi.setResiPeriodo(SPDConstants.SPD_PERIODO_MENSUAL);
    			medResi.setDiasSemanaConcretos("");
    			medResi.setSecuenciaGuide("");
				medResi.setDiasSemanaConcretos("");//validar la funcionalidad
				HelperSPD.chequeoTratamientoMensual(spdUsuario, medResi);
				//medResi.setRevisar("SI");
			}

			if (medResi.getResiPeriodo().equalsIgnoreCase("bimensual") ||  HelperSPD.isEqualIgnoreCase(observaciones, "bimensual") || HelperSPD.isEqualIgnoreCase(comentarios, "bimensual") || HelperSPD.isEqualIgnoreCase(resiPeriodo, "bimensual"))  {
				if(medResi.getDiasMesConcretos()==null || medResi.getDiasMesConcretos().equals(""))
					medResi.setDiasMesConcretos(SPDConstants.DIAS_DEFECTO_MES);
				medResi.setResiPeriodo("bimensual");
    			medResi.setSecuenciaGuide("1-1-MONTH");
				medResi.setResiFrecuencia(61);
				//medResi.setRevisar("SI");

			}
			if (medResi.getResiPeriodo().equalsIgnoreCase("trimestral") ||  HelperSPD.isEqualIgnoreCase(observaciones, "trimestral") || HelperSPD.isEqualIgnoreCase(comentarios, "trimestral") || HelperSPD.isEqualIgnoreCase(resiPeriodo, "trimestral"))  {
				if(medResi.getDiasMesConcretos()==null || medResi.getDiasMesConcretos().equals(""))
					medResi.setDiasMesConcretos(SPDConstants.DIAS_DEFECTO_MES);
				medResi.setResiPeriodo("trimestral");
    			medResi.setSecuenciaGuide("1-2-MONTH");
				medResi.setResiFrecuencia(92);
				//medResi.setRevisar("SI");

			}
			if (medResi.getResiPeriodo().equalsIgnoreCase("semestral") ||  HelperSPD.isEqualIgnoreCase(observaciones, "semestral") || HelperSPD.isEqualIgnoreCase(comentarios, "semestral") || HelperSPD.isEqualIgnoreCase(resiPeriodo, "semestral"))  {
				if(medResi.getDiasMesConcretos()==null || medResi.getDiasMesConcretos().equals(""))
					medResi.setDiasMesConcretos(SPDConstants.DIAS_DEFECTO_MES);
				medResi.setResiPeriodo("semestral");
    			medResi.setSecuenciaGuide("1-5-MONTH");
				medResi.setResiFrecuencia(183);
				//medResi.setRevisar("SI");

			}
			if (medResi.getResiPeriodo().equalsIgnoreCase(SPDConstants.SPD_PERIODO_ANUAL) ||  HelperSPD.isEqualIgnoreCase(observaciones, SPDConstants.SPD_PERIODO_ANUAL) || HelperSPD.isEqualIgnoreCase(comentarios, SPDConstants.SPD_PERIODO_ANUAL) || HelperSPD.isEqualIgnoreCase(resiPeriodo, SPDConstants.SPD_PERIODO_ANUAL))  {
				if(medResi.getDiasMesConcretos()==null || medResi.getDiasMesConcretos().equals(""))
					medResi.setDiasMesConcretos(SPDConstants.DIAS_DEFECTO_MES);
				medResi.setResiPeriodo(SPDConstants.SPD_PERIODO_ANUAL);
    			medResi.setSecuenciaGuide("1-11-MONTH");
    			medResi.setResiFrecuencia(365);
    			//medResi.setRevisar("SI");

			}
			if(medResi.getResiPeriodo().equals(SPDConstants.SPD_PERIODO_ESPECIAL) || medResi.getResiPeriodo()==null|| medResi.getResiPeriodo().equals(""))
			{
				medResi.setResiPeriodo(SPDConstants.SPD_PERIODO_ESPECIAL);
				medResi.setSecuenciaGuide("");
				medResi.setResiFrecuencia(0);
				//medResi.setRevisar("SI");

				//	medResi.setTipoEnvioHelium("4");
			}
			if(HelperSPD.isEqualIgnoreCase(resultVariante, "secuenciaGuidePorVariante"))
			{
				medResi.setResiPeriodo(SPDConstants.SPD_PERIODO_ESPECIAL);
				medResi.setResiFrecuencia(0);
			}
		}
		public static String tratarVariante(FicheroResiBean medResi) {
		   	//check en variante tipo resi+ 
			//int freq = 0; //por defecto 0,
			String result="";
			String a = "";
			String gdrVariante = "";	//variante de la opci�n GDR
			String gdrComentarios = "";	//comentarios de la opci�n GDR
				try	{ 			
					a = StringUtil.limpiarTextoEspaciosYAcentos(medResi.getResiVariante(), true);
					if(a.equalsIgnoreCase("diasidiano")|| a.equalsIgnoreCase("cada48horas"))
					{
						result="secuenciaGuidePorVariante";
						medResi.setSecuenciaGuide("1-1-DAYS");
					}
					else 
					{
						try	{ 	
							gdrVariante = StringUtil.limpiarTextoEspaciosYAcentos(medResi.getResiVariante(), true);
							gdrVariante = gdrVariante.replace("REPETICIO", "").replace("CADA", "").replace("DIA/ES", "");
							if(DataUtil.isNumero(gdrVariante))
								result = HelperSPD.marcarSecuencia(medResi, gdrVariante);

							gdrComentarios = StringUtil.limpiarTextoEspaciosYAcentos(medResi.getResiComentarios(), true);
							gdrComentarios = gdrComentarios.replace("CADA", "").replace("DIAS", "");
							if(DataUtil.isNumero(gdrComentarios))
								result = HelperSPD.marcarSecuencia(medResi, gdrComentarios);
							

							gdrVariante = StringUtil.limpiarTextoEspaciosYAcentos(medResi.getResiVariante(), true);
							gdrVariante = gdrVariante.replace("DIA/ES", "").replace("DEL", "").replace("MES", "").replace(":", "");
							if(DataUtil.isNumero(gdrVariante))
							{
								medResi.setTipoEnvioHelium("3");
								medResi.setDiasMesConcretos(gdrVariante);
								medResi.setResiFrecuencia(31);
								medResi.setResiPeriodo(SPDConstants.SPD_PERIODO_MENSUAL);
				    			medResi.setDiasSemanaConcretos("");
				    			medResi.setSecuenciaGuide("");
							}
							
						}catch(Exception e){}
					}
				}catch(Exception e){}
				//check en comentarios

				
			return result;
		}
	   
    
}    

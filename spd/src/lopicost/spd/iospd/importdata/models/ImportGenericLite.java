
package lopicost.spd.iospd.importdata.models;

import lopicost.spd.controller.ControlSPD;
import lopicost.spd.controller.SpdLogAPI;
import lopicost.spd.helper.FicheroResiDetalleHelper;
import lopicost.spd.iospd.importdata.process.ImportProcessImpl;
import lopicost.spd.persistence.FicheroResiCabeceraDAO;
import lopicost.spd.persistence.FicheroResiDetalleDAO;
import lopicost.spd.persistence.PacienteDAO;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.struts.bean.PacienteBean;
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
    	//comprobación de que no es línea vacía, solo tendría dos carácteres, "[" y "]".
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

		//esta sería la cabecera top del proceso
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
    public void procesarEntrada(String idRobot, String idDivisionResidencia, String idProceso, Vector row, int count, boolean cargaAnexa) throws Exception
    {
       	boolean result = false;
       //	System.out.println( "--> procesarEntrada. INICIO row  "  + new Date() ); 		
       	
	   	//int oidFicheroResiCabecera= ioSpdApi.getOidFicheroResiCabecera(getSpdUsuario(), idDivisionResidencia, idProceso);
       	
     	//if (row!=null && row.size()>=reg+1) así está en resi+
    	if (row!=null && row.size()>=reg)
        {
    		if (this.rowsTratados.containsKey(String.valueOf(row))) {
    			throw new Exception ("Es un tratamiento que está duplicado ");
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
  	            throw new Exception ("Columnas insuficientes para la importación. ");
    	
    	System.out.println( "--> procesarEntrada. FIN row;  "  + new Date() );		
    }
    
 
	protected void recogerDatosRow(FicheroResiBean medResi, Vector row) throws Exception {
	}


	/**
     * Método para crear un registro de inicio del proceso de carga
     * y para crear el registro de los datos de cabecera del proceso
     */

	protected boolean beforeStart(String filein) throws Exception 
    {
		boolean result=false;
		try {
			if(isCargaAnexa())
			{
				//creación de log en BBDD
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
					//creación de log en BBDD
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
     * Método para actualizar el registro de carga
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

			
			result=ioSpdApi.editaFinCargaFicheroResi(this.getIdDivisionResidencia(), this.getIdProceso(), filasTotales, cipsTotales, cipsActivosSPD, porcent, this.errors);
			if(result)
			{
				//creación de log en BBDD
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
		HelperSPD.detectarPeriodoAlta(getSpdUsuario(), medResi);

       //cálculo de la dosis que se prevee que entra en la producción según lo recibido de la resi
       medResi.setPrevisionResi(ControlSPD.contarDosisProduccionResi(medResi));
       
       //tratamiento de la trazodona, por si hay medias pastillas
		if(HelperSPD.checkTrazodona(medResi))
      	{
	      		HelperSPD.changeTrazodona(getSpdUsuario(), medResi);
      	}

		//se recupera información de la producción anterior.
		boolean reutilizado = HelperSPD.getDatosProduccionAnterior(getSpdUsuario(), medResi, true, true);
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


    
}    

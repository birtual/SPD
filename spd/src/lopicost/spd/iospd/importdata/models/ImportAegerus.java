
package lopicost.spd.iospd.importdata.models;

import lopicost.spd.controller.SpdLogAPI;
import lopicost.spd.helper.FicheroResiDetalleHelper;

import lopicost.spd.iospd.importdata.process.ImportProcessImpl;

import lopicost.spd.persistence.FicheroResiDetalleDAO;
import lopicost.spd.persistence.GestSustitucionesLiteDAO;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.utils.AegerusHelper;

import lopicost.spd.utils.HelperSPD;
import lopicost.spd.utils.SPDConstants;
import lopicost.spd.utils.StringUtil;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;


/**
 * Método encargado de importar el fichero recibido de la residencia, pero habiendo realizado nuestras sustituciones 
 * La finalidad es persistir los datos de SOLO_INFO y PASTILLERO del SPD junto con los NO_PINTAR, para poder compararlos
 * con las recetas y ver discrepancias.
 * El fichero ya viene "limpio" porque es el paso previo al envío al robot
 * author CARLOS
 *
 */

public class ImportAegerus extends ImportProcessImpl
{
	int reg = 11;  //numeroCorteCabecera  / celda de la fecha inicio, que es la obligatoria. a partir de aquí pueden venir vacías
	String CIPanterior="";
	int numeroDoses=0;
	int oidFicheroResiCabecera= 0;
   	int celdaFinal=30; //sería un valor tope cuando encontremos la fecha final que coincide con fecha hasta



    TreeMap CIPSTratados =new TreeMap();
    TreeMap CNSTratados =new TreeMap();
	TreeMap rowsTratados =new TreeMap();
	TreeMap tFechasProduccionTeorico =new TreeMap();		//fechas que se eligen como inicio / fin para producir
	TreeMap tFechasProduccionFichero =new TreeMap();		//fechas que se detectan que se reciben en el fichero Aegerus
	TreeMap tFechasProduccionSPD =new TreeMap();			//fechas intersección de las dos anteriores, que serán las que se envíen a robot
	TreeMap<String, String>  cipsFicheroAnexo =new TreeMap<>(); // se guardan los CIPS que se cargan de nuevo, para borrar previamente el tratamiento y cargarlo con el nuevo fichero

	
    FicheroResiBean medResi;
    String idDivisionResidencia = null;
    String idProceso = null;
    String fechaDesde= null;
    String fechaHasta= null;
    String diasRangoSPD= null;
    long diasProduccionSPD =0;
 
	public ImportAegerus(){
		super();
	}


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
	
	
	
    protected boolean beforeProcesarEntrada(Vector row) throws Exception 
    {
    	//comprobación de que no es línea vacía, solo tendría dos carácteres, "[" y "]".
    	if(row!=null)
    	{
        	String rowString=row.toString().replace(",", "").replace(" ", "");
        	
        	if(rowString.length()==2)
        		return false;
    	}
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
		numeroDoses=cab.getNumeroDeTomas();
		this.oidFicheroResiCabecera= ioSpdApi.getOidFicheroResiCabecera(getSpdUsuario(), idDivisionResidencia, idProceso);
		cab.setOidFicheroResiCabecera(oidFicheroResiCabecera);
    	
		fechaDesde = HelperSPD.obtenerFechaDesde(idProceso);  
		fechaHasta = HelperSPD.obtenerFechaHasta(idProceso); 

		cab.setFechaDesde(fechaDesde);  
    	cab.setFechaHasta(fechaHasta); 

    	if(!isCargaAnexa())
    		FicheroResiDetalleDAO.nuevo(idDivisionResidencia, idProceso, cab);
    	
    	tFechasProduccionTeorico = AegerusHelper.getTreeMapDiasRango(fechaDesde, fechaHasta); //TreeMap con todas las fecha entre el rango de fechas inicio/fin 
		diasProduccionSPD= tFechasProduccionTeorico.size();	//días de producción teóricos
		
	   // diasRangoSPD= AegerusHelper.buscarDiasRangoSPD(fechaDesde, fechaHasta);
     }

    public void procesarEntrada(String idRobot, String idDivisionResidencia, String idProceso, Vector row, int count, boolean cargaAnexa) throws Exception {
 
       	System.out.println( "--> procesarEntrada. INICIO row  "  + new Date() );		
 
       	this.idProceso=idProceso;
       	this.idDivisionResidencia=idDivisionResidencia;
        	 
        if (row != null && row.size() >= 12) {
        	
        	if(AegerusHelper.esFechaAegerus((String) row.elementAt(5)))
        	{
            	
                this.creaRegistro(row, count);
                return;
        	}
        	else
     	       throw new Exception ("Línea descartada  " + row.toString());
        }        
 	    else 
	       throw new Exception ("Columnas insuficientes para la importación. " + row.toString());
    }
    
    
    public void creaRegistro(Vector row, int count) throws Exception 
    {
    	//row = AegerusHelper.comenzarEnMediodia(this.fechaDesde, row);
    	//row = AegerusHelper.acabarEnMediodia(this.fechaHasta, row);
		if (this.rowsTratados.containsKey(String.valueOf(row))) {
			throw new Exception ("Es un tratamiento que está duplicado ");
		}
		this.rowsTratados.put(String.valueOf(row), String.valueOf(row));
		
		
    	boolean result = true;
        //En el TreeMap hemos de guardar las cnResi-CIP-diasAegerus para cada FicheroResiBean. Si hay variación de alguna de ellas, crearíamos otro nuevo.
    	final String cnResi = StringUtil.replaceInvalidCharsInNumeric((String)row.elementAt(1));
        final String CIP = StringUtil.replaceInvalidChars((String)row.elementAt(2));
        
        tFechasProduccionSPD = buscarTreeMapDiasAegerusEnRango(row);
        boolean diario=false;
        boolean sinDias=false;
        boolean diasConcretos=false;
        String diasMesConcretos ="";
         
        if(tFechasProduccionSPD.size()>1 && !tFechasProduccionSPD.containsKey("NO"))
        {
    		diario=true;
    		diasProduccionSPD=tFechasProduccionSPD.size();
        }
        if(tFechasProduccionSPD.size()==1 && tFechasProduccionSPD.containsKey("NO"))
        {
    		sinDias=true;
    		diasProduccionSPD=0;
        }
        if(tFechasProduccionSPD.size()>1 && tFechasProduccionSPD.containsKey("NO"))
        {
    		diasConcretos=true;
       		diasMesConcretos=getStringDiasTreeMap();
       		diasProduccionSPD=tFechasProduccionSPD.size()-1; //quitamos uno por el "NO"
        }

        
 		String detalleRow = HelperSPD.getDetalleRow(row, celdaFinal);
          // Procesa la cadena de Excel y conviértela en un arreglo o lista, por ejemplo
 		detalleRow = StringUtil.quitaEspaciosYAcentos(row.toString().replaceAll("[\\[\\]]", "").replaceAll("'", ""), true);

        if (!this.CNSTratados.containsKey(String.valueOf(CIP) + "_" + cnResi+ "_" + diasMesConcretos)) {
        	
        	//si no existe, construimos uno nuevo
         	FicheroResiBean medResi= new FicheroResiBean();
         	medResi.setRow(count);
         	//if(diasAegerus.equalsIgnoreCase(SPDConstants.REGISTRO_AEGERUS_TODO_NO))
         	if(sinDias)
         	{
           		//mensual
        		medResi.setTipoEnvioHelium("2");
        		medResi.setResiPeriodo(SPDConstants.SPD_PERIODO_DIAS_SEMANA_CONCRETOS); //sin marcar días para que no salga
        		medResi.setDiasSemanaConcretos("");
        		medResi.setDiasSemanaMarcados(0);
        		medResi.setSecuenciaGuide("");
        		medResi.setDiasSemanaConcretos("");//validar la funcionalidad
         	}
         	else if(diario)
            {
           		//diario
        		medResi.setTipoEnvioHelium("1");
        		medResi.setResiFrecuencia(1);
        		medResi.setResiPeriodo(SPDConstants.SPD_PERIODO_DIARIO);
        		medResi.setDiasSemanaConcretos("");
        		medResi.setDiasSemanaMarcados(7);
        		medResi.setSecuenciaGuide("");
        		medResi.setDiasSemanaConcretos("");
        		medResi.setDiasMesConcretos("");
            	AegerusHelper.marcaTodosDias(medResi);
            } 
         	else if(diasConcretos)
         	{
         		//mensual
         		medResi.setTipoEnvioHelium("3");
         		medResi.setResiFrecuencia(31);
         		medResi.setResiPeriodo(SPDConstants.SPD_PERIODO_MENSUAL);
         		medResi.setDiasSemanaConcretos("");
         		medResi.setDiasSemanaMarcados(7);
         		medResi.setSecuenciaGuide("");
         		medResi.setDiasSemanaConcretos("");//validar la funcionalidad
         		medResi.setDiasMesConcretos(diasMesConcretos);
         		AegerusHelper.marcaTodosDias(medResi);
           }
         	medResi.setNumeroDeTomas(24); //importante, todas tienen este número
            
            
    		medResi.setOidFicheroResiCabecera(oidFicheroResiCabecera);
      		//String detalleRow = HelperSPD.getDetalleRow(row, celdaFinal);
      	             // Procesa la cadena de Excel y conviértela en un arreglo o lista, por ejemplo
            //detalleRow = StringUtil.quitaEspaciosYAcentos(row.toString().replaceAll("[\\[\\]]", "").replaceAll("'", ""), true);
            //medResi.setDetalleRow(HelperSPD.getDetalleRowFechasOk(detalleRow));
    		medResi.setDetalleRow(detalleRow);
    		medResi.setDetalleRowKey(AegerusHelper.getDetalleRowAegerus(medResi.getDetalleRow()));
    		medResi.setIdProceso(this.idProceso);
    		medResi.setIdDivisionResidencia(idDivisionResidencia);
    		medResi.setTipoRegistro(SPDConstants.REGISTRO_LINEA);
    		medResi.setFechaDesde(this.fechaDesde);
    		medResi.setFechaHasta(this.fechaHasta);
    		medResi.setIdProcessIospd(SPDConstants.IDPROCESO_AEGERUS);
          	
    		//recogemos del Excel
    		int i=0;
          	medResi.setResiMedicamento(StringUtil.replaceInvalidChars((String) row.elementAt(i))); i++;	
    		medResi.setResiCn(StringUtil.replaceInvalidChars((String) row.elementAt(i))); i++;
    		medResi.setResiCIP(StringUtil.replaceInvalidChars((String) row.elementAt(i))); i++;	
    		medResi.setNombrePacienteEnFichero((String) row.elementAt(i));i++;
         	HelperSPD.getDatosPaciente(medResi);
         	medResi.setResiHabitacion((String) row.elementAt(i)); i++;
        	if(medResi.getResiHabitacion()!=null && !medResi.getResiHabitacion().equals("") && medResi.getResiHabitacion().length()>0)
        		medResi.setResiPlanta(medResi.getResiHabitacion().substring(0,1));

        	medResi.setResiInicioTratamiento((String) row.elementAt(i)); i++;
        	medResi.setResiFinTratamiento((String) row.elementAt(i)); i++;
        	String toma = "0";
        	try {toma = HelperSPD.getPautaStandard(medResi,  (String) row.elementAt(i)); i++;} catch(Exception e){}
        	
        	medResi.setResiFormaMedicacion((String) row.elementAt(i));i++;
        	
        	String hora=(String) row.elementAt(i);i++;
        	AegerusHelper.detectarToma(medResi, toma, hora);
        	
            //cálculo de la dosis que se prevee que entra en la producción según lo recibido de la resi
        	try {medResi.setPrevisionResi(new Float(toma.replace(",", ".")).floatValue() * diasProduccionSPD );} catch(Exception e){}
        	try {medResi.setPrevisionSPD(new Float(toma.replace(",", ".")).floatValue() * diasProduccionSPD );} catch(Exception e){}
        	
           
       		buscaSustitucion(medResi);

            //lo metemos en el TreeMap
       		this.CNSTratados.put(String.valueOf(CIP) + "_" + cnResi+ "_" + diasMesConcretos, medResi);
                		
    	}
        else {//añadimos los datos que nos interesa, ResiToma, 
         
         	
        	
        	this.medResi = (FicheroResiBean)this.CNSTratados.get(String.valueOf(CIP) + "_" + cnResi+ "_" + diasMesConcretos);
          	//se añade la línea del excel en el detalleRow para consulta desde la web
        	this.medResi.setDetalleRow(this.medResi.getDetalleRow() + "_" +  detalleRow);
 
        	String toma = "0";
        	try {toma = HelperSPD.getPautaStandard(this.medResi,  (String) row.elementAt(7)); } catch(Exception e){}
        	String hora=(String) row.elementAt(9);
        	AegerusHelper.detectarToma(this.medResi, toma, hora);
           
	      // String detalleRow = row.toString();
	       // Procesa la cadena de Excel y conviértela en un arreglo o lista, por ejemplo
	      // detalleRow = row.toString().replaceAll("[\\[\\]]", "").replaceAll("'", "");
	      // this.medResi.setDetalleRow(this.medResi.getDetalleRow() + "  <br>  " + detalleRow);
	       
            		
            //añadimos cálculo de la dosis que se prevee que entra en la producción según lo recibido de la resi
	       float previsionResiAcumulada=medResi.getPrevisionResi();
	       float previsionSPDAcumulada=medResi.getPrevisionSPD();
	       try {medResi.setPrevisionResi(previsionResiAcumulada + new Float(toma).floatValue() * diasProduccionSPD );} catch(Exception e){}
	       try {medResi.setPrevisionSPD(previsionSPDAcumulada + new Float(toma).floatValue() * diasProduccionSPD );} catch(Exception e){}

        }
 	
    }

    
   private String getStringDiasTreeMap() {

	   String diasString="";
       for (Object obj : tFechasProduccionSPD.entrySet()) {
           Map.Entry<String, String> entry = (Map.Entry<String, String>) obj;
           String clave = entry.getKey();
           String valor = entry.getValue();
           //System.out.println("Clave: " + clave + ", Valor: " + valor);
           if(!valor.equalsIgnoreCase("NO"))
           diasString+=valor+",";
       }
		return diasString;
	}




/* * Método para crear un registro de inicio del proceso de carga
    * y para crear el registro de los datos de cabecera del proceso
   

	private String buscarDiasAegerusEnRango(Vector row) {
		
		String diasProduccionRango="";
		int i=10; //los días empiezan a partir de la fila 11 (row.elementAt(10))
       	String dia = (String) row.elementAt(i);i++; //dia1
       	boolean finalDias = AegerusHelper.checkFinalDias(dia);
       	boolean diario=true;
       	int contador=0; //si llega todo como NO para poder tener un límite en el bucle 
        while (!finalDias && contador<=celdaFinal)
    	{
        	//es dia de tratamiento casos y está dentro del rango de producción
          	if(AegerusHelper.esFechaAegerus(dia) && HelperSPD.verificarEnRango(this.fechaDesde, this.fechaHasta, dia))
           	{
           		int diaFechas = HelperSPD.obtenerDia(dia);
           		if(diasProduccionRango!=null && !diasProduccionRango.equals(""))
           			diasProduccionRango=diasProduccionRango+",";
           		diasProduccionRango= diasProduccionRango+diaFechas;
           		
           	}
           	else if(AegerusHelper.noTomarEseDia(dia))
           	{
           		diario=false;
           	}
           	else if(AegerusHelper.esFechaAegerus(dia) && !HelperSPD.verificarEnRango(this.fechaDesde, this.fechaHasta, dia))
           	{
           		break;	
           	}
           	

           	try {
           		dia = (String) row.elementAt(i);
           	}
           	catch(Exception e){
           		break;
           	}
           	if(celdaFinal!=30 && dia.equalsIgnoreCase(this.fechaHasta)) celdaFinal=i;
           	
           	i++;
           	
           	finalDias = AegerusHelper.checkFinalDias(dia);
           	
           	contador++;
    	}

        if(diario) 
        {
        	diasProduccionRango=""; //si es diario quitamos los días
        }else
        {
        	//en caso que todo haya llegado con NO hemos de evitar que sea diario.
        	if(diasProduccionRango.equals("")) diasProduccionRango=SPDConstants.REGISTRO_AEGERUS_TODO_NO;
        	
        }

		return diasProduccionRango;
	}
	


	private String buscarDiasAegerusEnRangoNumerico(Vector row) {
		
		String diasProduccionRango="";
		int i=10; //los días empiezan a partir de la fila 11 (row.elementAt(10))
       	String dia = (String) row.elementAt(i);i++; //dia1
       	boolean finalDias = AegerusHelper.checkFinalDias(dia);
       	boolean diario=true;
       	int contador=0; //si llega todo como NO para poder tener un límite en el bucle 
        while (!finalDias && contador<=celdaFinal)
    	{
        	//es dia de tratamiento casos y está dentro del rango de producción
          	if(AegerusHelper.esFechaAegerus(dia) && HelperSPD.verificarEnRango(this.fechaDesde, this.fechaHasta, dia))
           	{
           		int diaFechas = HelperSPD.obtenerDia(dia);
           		if(diasProduccionRango!=null && !diasProduccionRango.equals(""))
           			diasProduccionRango=diasProduccionRango+",";
           		diasProduccionRango= diasProduccionRango+diaFechas;
           	}

           	try {
           		dia = (String) row.elementAt(i);
           	}
           	catch(Exception e){
           		break;
           	}
           	if(celdaFinal!=30 && dia.equalsIgnoreCase(this.fechaHasta)) celdaFinal=i;
           	
           	i++;
           	
           	finalDias = AegerusHelper.checkFinalDias(dia);
           	
           	contador++;
    	}

        if(diario) 
        {
        	diasProduccionRango=""; //si es diario quitamos los días
        }else
        {
        	//en caso que todo haya llegado con NO hemos de evitar que sea diario.
        	if(diasProduccionRango.equals("")) diasProduccionRango=SPDConstants.REGISTRO_AEGERUS_TODO_NO;
        	
        }

		return diasProduccionRango;
	}
	
 */

	private TreeMap buscarTreeMapDiasAegerusEnRango(Vector row) {
		
		TreeMap tDiasProduccionRango=new TreeMap();
		
		int i=10; //los días empiezan a partir de la fila 11 (row.elementAt(10))
       	String dia = (String) row.elementAt(i);i++; //dia1
       	boolean finalDias = AegerusHelper.checkFinalDias(dia);
       	boolean diario=true;
       	int contador=0; //si llega todo como NO para poder tener un límite en el bucle 
        while (!finalDias && contador<=celdaFinal)
    	{
        	//es dia de tratamiento casos y está dentro del rango de producción
          	if(AegerusHelper.esFechaAegerus(dia) && HelperSPD.verificarEnRango(this.fechaDesde, this.fechaHasta, dia))
           	{
           		tDiasProduccionRango.put(dia, HelperSPD.obtenerDia(dia)+"");
           	}
           	else if(AegerusHelper.noTomarEseDia(dia) && !tDiasProduccionRango.containsKey("NO"))
           	{
           		tDiasProduccionRango.put("NO", "NO");
           	}

           	try {
           		dia = (String) row.elementAt(i);
           	}
           	catch(Exception e){
           		break;
           	}
           	if(celdaFinal!=30 && dia.equalsIgnoreCase(this.fechaHasta)) celdaFinal=i;
           	
           	i++;
           	
           	finalDias = AegerusHelper.checkFinalDias(dia);
           	
           	contador++;
    	}


		return tDiasProduccionRango;
	}
	
	
	
   /**
    * Método para actualizar el registro de carga
    * @throws Exception 
    */
   protected void afterStart() throws Exception 
   {
   	boolean result=false;
   	
		try {
			
			persistirObjectos();

			int cipsActivosSPD= ioSpdApi.getCipsActivosSPD(getSpdUsuario(), this.getIdDivisionResidencia());
			int cipsTotales= ioSpdApi.getCipsTotalesCargaFichero(getSpdUsuario(), this.getIdDivisionResidencia(), this.getIdProceso());
			int filasTotales= this.processedRows;

			result=ioSpdApi.borraDetalleSinCabecera();
			result=ioSpdApi.actualizaEstadosSinFinalizar();

			if(isCargaAnexa())
				filasTotales= ioSpdApi.getLineasProceso(getSpdUsuario(), this.getIdDivisionResidencia(), this.getIdProceso());

			int porcent = 0;
			try { porcent =(cipsTotales*100/cipsActivosSPD);				}catch(Exception e){porcent =0;}

			
			//result=ioSpdApi.editaFinCargaFicheroResi(this.getIdDivisionResidencia(), this.getIdProceso(), filasTotales, cipsTotales, cipsActivosSPD, porcent, this.errors);
			result=FicheroResiDetalleHelper.editaFinCargaFicheroResi(getSpdUsuario(), this.getIdDivisionResidencia(), this.getIdProceso(), filasTotales, cipsTotales, cipsActivosSPD, porcent, this.errors);

			if(result)
			{
				//creación de log en BBDD
				try{
					SpdLogAPI.addLog(getSpdUsuario(), "",  this.getIdDivisionResidencia(),  this.getIdProceso(), SpdLogAPI.A_PRODUCCION, SpdLogAPI.B_CARGAFICHERO, SpdLogAPI.C_START
							, "SpdLog.produccion.cargafichero.fin", this.getIdProceso()  );
				}catch(Exception e){}	//Fin de la carga de fichero.
			}
			
			
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
   
   protected void persistirObjectos() throws Exception {
	   boolean result;
   		Set set = CNSTratados.entrySet();
   		Iterator it =(Iterator) set.iterator();
   		int i=0;
   		while(it.hasNext()) {
   			Map.Entry me = (Map.Entry)it.next();
   			System.out.println(" me.getKey() --> "  + me.getKey());
   			FicheroResiBean medResi = (FicheroResiBean)me.getValue();
   			medResi.setIdTratamientoCIP(AegerusHelper.getIDAegerus(medResi));

   			
   			boolean validoParaSpd = medResi.getSpdAccionBolsa()!=null && (medResi.getSpdAccionBolsa().equalsIgnoreCase(SPDConstants.SPD_ACCIONBOLSA_SOLO_INFO) || medResi.getSpdAccionBolsa().equalsIgnoreCase(SPDConstants.SPD_ACCIONBOLSA_PASTILLERO));
   			//tratamiento de la trazodona, por si hay medias pastillas
     		if(HelperSPD.checkTrazodona(medResi))
           	{
     	      	HelperSPD.changeTrazodona(getSpdUsuario(), medResi);
           	}
     		
     		//se recupera información de la producción anterior.
     		boolean reutilizado = AegerusHelper.getDatosProduccionAnterior(getSpdUsuario(), medResi, true);
       	   	//si es un registro nuevo pasamos varios de los filtros
     	   	if(validoParaSpd)
     	    {
        	    if(HelperSPD.checkSintrom(medResi))
     	      	{
        	    	HelperSPD.changeSintrom(medResi);
     	      	}
     		  	HelperSPD.controlAlertasRegistro(medResi);
     	   		HelperSPD.chequeoTratamientoMensual(getSpdUsuario(), medResi);
    	  		HelperSPD.chequearPrevisionResiSPD(medResi);
           	}

     		if((medResi.getResiCIP()==null || medResi.getResiCIP().equals("")) && medResi.getResiNombrePaciente()!=null && !medResi.getResiNombrePaciente().equals("")) //en caso que no exista CIP  ponemos el nombre
     		{
     			FicheroResiDetalleHelper.actualizaCIP(getSpdUsuario(), medResi);
     			
     		}   
     		
        	//tratamos los casos de un segundo fichero de carga.
        	//localización de los CIPS a tratar, se borrará lo que se haya cargado previamente y se mete en un TreeMap para no borrarlo de nuevo e insertar los nuevos. 
        	if(isCargaAnexa())
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
        	
        	

     		
     		
   	   		//miramos si existe, para no duplicar
   	       	boolean existe = false;
   	       	//existe= FicheroResiDetalleDAO.existeRegistroAegerus(getSpdUsuario(), medResi.getIdProceso(), medResi.getIdDivisionResidencia(), medResi.getResiCIP(), medResi.getResiCn());
   	    		
   	    	if(!existe)
   	    	{

   	  	    	AegerusHelper.aplicarControles(getSpdUsuario(), medResi, true);
   	  	    	
   	  	    	AegerusHelper.aplicarExcepcionesFalguera(getSpdUsuario(), medResi);

   	  	    	
   	  	    	
   	  	    	
   	  	    	
   	  	    	
   	    		FicheroResiDetalleHelper.nuevo(medResi.getIdDivisionResidencia(), medResi.getIdProceso(), medResi);
   	    		//FicheroResiDetalleDAO.nuevo(medResi.getIdDivisionResidencia(), medResi.getIdProceso(), medResi);
   		
   				System.out.println( "--> FicheroResiDetalleHelper.nuevo;  "  + medResi.getIdTratamientoSPD() );		
   	    	}
   	    	
    	    System.out.println( "--> procesarEntrada. FIN row;  "  + new Date() );	
    			

  	    } 

   		CNSTratados.clear();
   }
   
   
    
	
    private void buscaSustitucion(FicheroResiBean fila) throws Exception {
		//la búsqueda de sustitución se realiza en la carga
		if(fila.getResiCn()!=null && !fila.getResiCn().equals("") )
			GestSustitucionesLiteDAO.buscaSustitucionLite(getSpdUsuario(), fila);

}
    

	
}
		



	 
	 








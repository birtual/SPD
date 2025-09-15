
package lopicost.spd.iospd.importdata.models;

import lopicost.spd.excepciones.LineaDescartadaException;
import lopicost.spd.persistence.GestSustitucionesLiteDAO;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.utils.StringUtil;
import lopicost.spd.utils.DataUtil;
import lopicost.spd.utils.DateUtilities;
import lopicost.spd.utils.HelperSPD;
import lopicost.spd.utils.SPDConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class ImportOnada extends ImportGenericLite
{
	int reg = 11;  //numeroCorteCabecera  / celda de la fecha inicio, que es la obligatoria. a partir de aquí pueden venir vacías
	//int COLUMNAS = 23; //número de columnas a tratar del fichero 
	boolean primeraColumnaFecha =false;
	
	public ImportOnada(){
		super();
	}


	
	protected void recogerDatosRow(FicheroResiBean medResi, Vector row) throws Exception {

    	int i = 0;   //Pivote que recorre las celdas. Si existe fecha en la primera columna del excel, moveremos +1 el pivote sobre las celdas
    	System.out.println(" row.size() " + row.size());
    	int COLUMNAS = 23; //número de columnas a tratar del fichero 

    	
    	//para saber si existe, cogeremos como válido el campo fecha inicio (que es obligatorio en el tratamiento)
    	//miramos si la columna 1 llega con campo fecha (Data Prescripció) para descartarlo
    	String campo0 = (String) row.elementAt(0);
    	String esFecha = "";
    	if(DataUtil.isNumeroGreatherThanZero(campo0))
    	{
    		double numeroDouble = Double.parseDouble(campo0);
    		int parteEntera = (int) numeroDouble;
    		if(parteEntera>50000) parteEntera=0;//número de una posible fecha 
    		campo0 = String.valueOf(parteEntera);
    		
    	}
    	
    	esFecha = StringUtil.getStringArregloFecha(campo0, "dd/MM/yyyy");
		if(DateUtilities.isDateValid(esFecha, "dd/MM/yyyy")) //si no es fecha válida (fecha inicio) se entiende que la fecha inicio es la siguiente columna (+1)
		{
			i=1;
			primeraColumnaFecha =true;
			COLUMNAS=COLUMNAS+1; //añadimos un punto
		}
		
		//una vez sabemos dónde se ubica la fechaDesde (que está en i+10)  miramos si es una fila válida o es cabecera, 
		String esOkFechaDesde = StringUtil.getStringArregloFecha((String) row.elementAt(i+10), "dd/MM/yyyy");
		if(!DateUtilities.isDateValid(esOkFechaDesde, "dd/MM/yyyy")) //si no es fecha válida (fecha inicio) se entiende que la fecha inicio es la siguiente columna (+1)
		{
			//throw new Exception ("No es un tratamiento válido.");
			throw new LineaDescartadaException("No es un tratamiento válido. ");
		}
		
		
    	String detalleRow = HelperSPD.getDetalleRow(row, COLUMNAS);
        medResi.setDetalleRow(detalleRow);
    	System.out.println(" detalleRow " + detalleRow);
        medResi.setDetalleRowKey(crearDetalleRowKey(detalleRow, getPosicionesAEliminar()));
    	System.out.println(" detalleRowKey " + medResi.getDetalleRowKey());
        medResi.setDetalleRowKeyLite(crearDetalleRowKeyLite(row, detalleRow, getPosicionesAEliminar()));
    	System.out.println(" detalleRowKeyLite " + medResi.getDetalleRowKeyLite());

    	
    	medResi.setResiCIP(StringUtil.limpiarTextoyEspacios((String) row.elementAt(i)));i++;
    	
    	medResi.setNombrePacienteEnFichero((String) row.elementAt(i));i++;
     	HelperSPD.getDatosPaciente(medResi);

    	
	   	//medResi.setResiMedicamento(StringUtil.limpiarTextoTomas((String) row.elementAt(i)));i++;
	   	medResi.setResiMedicamento((String) row.elementAt(i));i++;
		medResi.setResiCn(StringUtil.limpiarTextoyEspacios((String) row.elementAt(i)));i++;
		
		//si no hay CNResi ponemos el nombre de medicamento
		if(medResi.getResiCn()==null || medResi.getResiCn().equals(""))
		{
			medResi.setResiCn(StringUtil.limpiarTextoyEspacios(medResi.getResiMedicamento()));
			medResi.setValidar(SPDConstants.REGISTRO_VALIDAR);
			medResi.setConfirmar(SPDConstants.REGISTRO_CONFIRMAR);
			//medResi.setMensajesInfo(SPDConstants.INFO_CN_NO_INDICADO);
			medResi.setMensajesResidencia(SPDConstants.INFO_RESI_CN_NO_INDICADO);
		}
		medResi.setNumeroDeTomas(numeroDoses); // IMPORTANTE PARA TABULAR EL LISTADO!! TO-DO

		
		String toma1=HelperSPD.getPautaStandard(medResi, (String) row.elementAt(i));i++; //AD - Antes desayuno
		String toma2=HelperSPD.getPautaStandard(medResi, (String) row.elementAt(i));i++; //esmorzar 9h
		String toma3=HelperSPD.getPautaStandard(medResi, (String) row.elementAt(i));i++; //dinar 13h
		String toma4=HelperSPD.getPautaStandard(medResi, (String) row.elementAt(i));i++; //merienda 16h
		String toma5=HelperSPD.getPautaStandard(medResi, (String) row.elementAt(i));i++; //cena 20h
		String toma6=HelperSPD.getPautaStandard(medResi, (String) row.elementAt(i));i++; //resopón 24h
		String resiPauta=toma1+"-"+toma2+"-"+toma3+"-"+toma4+"-"+toma5+"-"+toma6;

		medResi.setResiToma1(toma1);
		medResi.setResiToma2(toma2);
		medResi.setResiToma3(toma3);
		medResi.setResiToma4(toma4);
		medResi.setResiToma5(toma5);
		medResi.setResiToma6(toma6);
		medResi.setResiPauta(resiPauta);
		
		HelperSPD.hayNumerosAsteriscos(medResi);
		
		medResi.setResiInicioTratamiento(StringUtil.getStringArregloFecha((String) row.elementAt(i), "dd/MM/yyyy"));i++;	//ok
		
		try //a partir de aqui encapsulamos en try catch porque es posible que no lleguen 21 celdas
		{
			medResi.setResiFinTratamiento(StringUtil.getStringArregloFecha((String) row.elementAt(i),  "dd/MM/yyyy"));i++;		//ok
			String siPrecisa = StringUtil.limpiarTextoyEspacios((String) row.elementAt(i));i++;
			if(siPrecisa!=null && (siPrecisa.equalsIgnoreCase("X") || siPrecisa.equalsIgnoreCase("SIPRECISA")) )
				siPrecisa="X"; else siPrecisa="";
			
			//medResi.setResiSiPrecisa(siPrecisa.equals("SIPRECISA")?"X":siPrecisa);
			medResi.setResiSiPrecisa(siPrecisa);
			medResi.setResiObservaciones(StringUtil.limpiarTextoComentarios((String) row.elementAt(i)));i++;
			medResi.setResiComentarios(StringUtil.limpiarTextoComentarios((String) row.elementAt(i)));i++;
			medResi.setResiPeriodo(StringUtil.limpiarTexto((String) row.elementAt(i)));i++;
			medResi.setResiD1(StringUtil.limpiarTextoyEspacios((String) row.elementAt(i)));i++;
			medResi.setResiD2(StringUtil.limpiarTextoyEspacios((String) row.elementAt(i)));i++;
			medResi.setResiD3(StringUtil.limpiarTextoyEspacios((String) row.elementAt(i)));i++;
			medResi.setResiD4(StringUtil.limpiarTextoyEspacios((String) row.elementAt(i)));i++;
			medResi.setResiD5(StringUtil.limpiarTextoyEspacios((String) row.elementAt(i)));i++;
			medResi.setResiD6(StringUtil.limpiarTextoyEspacios((String) row.elementAt(i)));i++;
			medResi.setResiD7(StringUtil.limpiarTextoyEspacios((String) row.elementAt(i)));i++;
			medResi.setResiViaAdministracion(""); // En onada no se recibe
			medResi.setResiFormaMedicacion("");   //En Onada no se recibe
			medResi.setResiVariante("");//En Onada no se recibe
	    	int diasSemanaMarcados=HelperSPD.getDiasMarcados(medResi);
	    	medResi.setDiasSemanaMarcados(diasSemanaMarcados);
	    	
		}catch(Exception e)
		{}

		//la búsqueda de sustitución se realiza en la carga
		if(medResi.getResiCn()!=null && !medResi.getResiCn().equals("") )
			GestSustitucionesLiteDAO.buscaSustitucionLite(getSpdUsuario(), medResi);
   
		if(medResi.getResiSiPrecisa()!=null && medResi.getResiSiPrecisa().equalsIgnoreCase("X")) {
			medResi.setSpdAccionBolsa(SPDConstants.SPD_ACCIONBOLSA_SI_PRECISA);
			medResi.setValidar(""); //mejor no revisarlo porque cada vez aparecerían en cada producción
		}
		
		medResi.setIdTratamientoCIP(HelperSPD.getID(medResi));
		
		//en caso de duplicado lo muestra en errores de carga
		//boolean existeDuplicado=HelperSPD.borrarPosibleDuplicado(getSpdUsuario(), medResi);
		//if(existeDuplicado)
		//	throw new Exception ("Es un tratamiento que está duplicado ");
	}

	/**
	1					2	3		4	5		6	7	8	9	10	11	12			13			14			15			16				17		18	19	20	21	22	23	24
	Data Prescripció	NSS	Nom	Fàrmac	C.N.	AD	D	C	M	C	N	Data inici	Data final	Si Precisa	Comentaris	Observacions	Període	dl	dm	dc	dj	dv	ds	di
	 */
	public List<Integer> getPosicionesAEliminar() {
		
		//en resiPlus no tendremos en cuenta  1 (Data Prescripció)
		List<Integer> result =new ArrayList<Integer>();
		if(primeraColumnaFecha)
			result.add(1);
			
		return result;
	}


    
}    




package lopicost.spd.iospd.importdata.models;


import lopicost.spd.model.BdConsejo;
import lopicost.spd.persistence.BdConsejoDAO;
import lopicost.spd.persistence.GestSustitucionesLiteDAO;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.utils.StringUtil;
import lopicost.spd.utils.DataUtil;
import lopicost.spd.utils.HelperSPD;
import lopicost.spd.utils.SPDConstants;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class ImportGDRExcel2 extends ImportGenericLite
{
	int COLUMNAS = 13; //número de columnas a tratar del fichero 

	
	public ImportGDRExcel2(){
		super();
	}

	/**
	 * Resident
		Nº Targ.San.
		Tipus
		Medicament
		Data inici
		Data fi
		Planificació
		Posologia
		Tipus unitat
		Emblistable
		Només en cas de
		Història CAP
		Comentari

	 */

	protected void recogerDatosRow(FicheroResiBean medResi, Vector row) throws Exception {

    	int i = 0;   
 		try{
			//saltamos cabecera
			if(getProcessedRows()==0)
			{
				throw new Exception ("No es un tratamiento válido.");
			}
		}catch(Exception e)
		{throw new Exception ("No es un tratamiento válido.");}
    	
    	
 	   	medResi.setNombrePacienteEnFichero((String) row.elementAt(i));i++;								//Resident
       	medResi.setResiCIP(StringUtil.limpiarTextoyEspacios((String) row.elementAt(i)));i++;			//Nº Targ.San.
 		HelperSPD.getDatosPaciente(medResi);	
  		medResi.setResiTipoMedicacion((String) row.elementAt(i));i++;									//Tipus
		medResi.setResiMedicamento((String) row.elementAt(i));i++;										//Medicament
		try{medResi.setResiInicioTratamiento(StringUtil.getStringArregloFecha((String) row.elementAt(i),  "dd/MM/yyyy"));i++;}    	catch(Exception e){} //Data inici 
    	try{medResi.setResiFinTratamiento(StringUtil.getStringArregloFecha((String) row.elementAt(i),  "dd/MM/yyyy"));i++;}    	catch(Exception e){} //Data fi
    	medResi.setResiVariante(StringUtil.limpiarTextoComentarios((String) row.elementAt(i)));i++;		//Planificació
    	//medResi.setResiPeriodo((String) row.elementAt(i));i++;
    	medResi.setResiPauta((String) row.elementAt(i));i++;											//Posologia
    	medResi.setResiFormaMedicacion((String) row.elementAt(i));i++;									//Tipus unitat
    	medResi.setResiEmblistable((String) row.elementAt(i));i++;										//Emblistable
       	medResi.setResiObservaciones((String) row.elementAt(i));i++;									//Només en cas de
       	i++; //saltamos Història CAP																	//saltamos "Història CAP"
       	medResi.setResiComentarios(StringUtil.limpiarTextoComentarios((String) row.elementAt(i)));i++;	//Comentari
    	
       	
       	String valor = medResi.getResiPauta(); // Ejemplo: "1|0|2|0 (3)"
        // Eliminamos lo que está entre paréntesis (incluido el espacio antes)
       	String sinSuma = valor.replaceAll("\\s*\\(.*\\)", ""); //  "12|3|45|6"
       	String[] partes = sinSuma.split("\\|");
       	
       	try{medResi.setResiToma1(HelperSPD.getPautaStandard(medResi, partes[0].trim())); }catch(Exception e){medResi.setResiToma1("");}//esmorzar
    	try{medResi.setResiToma2(HelperSPD.getPautaStandard(medResi, partes[1].trim())); }catch(Exception e){medResi.setResiToma2("");}//dinar
    	try{medResi.setResiToma3(HelperSPD.getPautaStandard(medResi, partes[2].trim())); }catch(Exception e){medResi.setResiToma3("");}//merienda
    	try{medResi.setResiToma4(HelperSPD.getPautaStandard(medResi, partes[3].trim())); }catch(Exception e){medResi.setResiToma4("");}//cena
       		
 		//medResi.setResiCn(StringUtil.limpiarTextoEspaciosYAcentos(medResi.getResiMedicamento(), true ));//10/07/2025 - De momento en este sistema no llega nunca el CN, ponemos la descripción
 		String variante = normalizar(medResi.getResiVariante());
 		boolean diaria=variante.isEmpty() || variante.contains("diaria");
 		String resultVariante=HelperSPD.tratarVariante(medResi);
 		
 		
 		if(	diaria || variante.contains("dilluns")  || variante.contains("lunes"))  			medResi.setResiD1("X");
 		if(	diaria || variante.contains("dimarts")  || variante.contains("martes")) 			medResi.setResiD2("X");
 		if(	diaria || variante.contains("dimecres") || variante.contains("miercoles")) 			medResi.setResiD3("X");
 		if(	diaria || variante.contains("dijous")   || variante.contains("martes")) 			medResi.setResiD4("X");
 		if(	diaria || variante.contains("divendres")|| variante.contains("martes")) 			medResi.setResiD5("X");
 		if(	diaria || variante.contains("dissabte") || variante.contains("martes")) 			medResi.setResiD6("X");
 		if(	diaria || variante.contains("diumenge") || variante.contains("domingo")) 			medResi.setResiD7("X");
		
		int diasSemanaMarcados=HelperSPD.getDiasMarcados(medResi);  //importante!! para que detecte que hay días marcados y no los llene automáticamente.
    	medResi.setDiasSemanaMarcados(diasSemanaMarcados);
    	
 		
    	String detalleRow = HelperSPD.getDetalleRow(row, COLUMNAS);
        medResi.setDetalleRow(detalleRow);
    	System.out.println(" detalleRow " + detalleRow);
        medResi.setDetalleRowKey(crearDetalleRowKey(detalleRow, getPosicionesAEliminar()));
    	System.out.println(" detalleRowKey " + medResi.getDetalleRowKey());
        medResi.setDetalleRowKeyLite(crearDetalleRowKeyLite(row, detalleRow, getPosicionesAEliminar()));
    	System.out.println(" detalleRowKeyLite " + medResi.getDetalleRowKeyLite());

    	medResi.setResiSiPrecisa(tratarSiPrecisa(medResi));
    	
		
		medResi.setNumeroDeTomas(numeroDoses); // IMPORTANTE PARA TABULAR EL LISTADO!! TO-DO
		
		///esta residencia no envía CN (a fecha 10/07/2025), pero el nombre del medicamento es el del Consejo Nombre + ' - ' + presentacion
		BdConsejo bdConsejo;
		if(!DataUtil.isNumero(medResi.getResiCn()) )
		{
			bdConsejo = BdConsejoDAO.getBdCNPorNombre(medResi.getResiMedicamento());
			if(bdConsejo!=null) medResi.setResiCn(bdConsejo.getCnConsejo());
		}

		//la búsqueda de sustitución se realiza en la carga
		if( (medResi.getResiCn()!=null && !medResi.getResiCn().equals("") ) || (medResi.getResiMedicamento()!=null && !medResi.getResiMedicamento().equals("") )  )
    	GestSustitucionesLiteDAO.buscaSustitucionLite(getSpdUsuario(), medResi);
		
		
    	//si no hay CNResi ponemos el nombre de medicamento
		
		if(medResi.getResiCn()==null || medResi.getResiCn().equals(""))
		{
			//si hemos encontrado sustitución no hacemos validar/confirmar
			if(medResi.getSpdCnFinal()!=null && !medResi.getSpdCnFinal().isEmpty())
			{
				medResi.setResiCn(StringUtil.limpiarTextoEspaciosYAcentos(medResi.getResiMedicamento(), true));
			}
			else
			{
				medResi.setValidar(SPDConstants.REGISTRO_VALIDAR);
				medResi.setConfirmar(SPDConstants.REGISTRO_CONFIRMAR);
				//medResi.setMensajesInfo(SPDConstants.INFO_CN_NO_INDICADO);
				//medResi.setMensajesResidencia(SPDConstants.INFO_RESI_CN_NO_INDICADO);
			}
			
		}

		
		
		if(medResi.getResiSiPrecisa()!=null && medResi.getResiSiPrecisa().equalsIgnoreCase("X")) {
			medResi.setSpdAccionBolsa(SPDConstants.SPD_ACCIONBOLSA_SI_PRECISA);
			//medResi.setRevisar("SI");
			medResi.setValidar(""); //mejor no revisarlo porque cada vez aparecerían en cada producción
		}
		
		medResi.setIdTratamientoCIP(HelperSPD.getID(medResi));
		boolean existeDuplicado=HelperSPD.borrarPosibleDuplicado(getSpdUsuario(), medResi);
		
		if(existeDuplicado)
			//this.errors.add(TextManager.getMensaje("ImportData.error.linea")+" " + row);
			//this.errors.add("Es un tratamiento que está duplicado " );
			throw new Exception ("Es un tratamiento que está duplicado ");
		System.out.println(" -----  borrarPosibleDuplicado Fin-->  " );
		
	}
	

	private String tratarSiPrecisa(FicheroResiBean medResi) {
		String siPrecisa = ""; 
		String observaciones = StringUtil.limpiarTextoyEspacios(medResi.getResiObservaciones()).toUpperCase();
		String comentarios = StringUtil.limpiarTextoyEspacios(medResi.getResiComentarios()).toUpperCase();
		if((observaciones!=null &&  observaciones.contains("S/P")) || (comentarios!=null &&  comentarios.contains("SIPRECISA")))
			siPrecisa="X";
		

		return siPrecisa;
	}

	private static String normalizar(String str) {
    	if(str==null) return "";
        // Elimina acentos y convierte a minúsculas
        return Normalizer.normalize(str, Normalizer.Form.NFD)
                         .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                         .toLowerCase();
    }
    
    
	
	/**
1		 2				3		4			5			6		7				8		  9		10		11			12				13				14
Resident Nº Targ.San.	Tipus	Medicament	Data inici	Data fi	Planificació	Posologia Tipus unitat	Emblistable	Només en cas de	Història CAP	Comentari
	 */
	public List<Integer> getPosicionesAEliminar() {
		//en GDR no tendremos en cuenta  13 Història CAP
		List<Integer> result =new ArrayList<Integer>();
		result.add(13);
		//result.add(6);
		//result.add(7);//TMP
		//result.add(8);//TMP
		//result.add(9);
		//result.add(10);//TMP
		//result.add(11);//TMP
		return result;
	}
	

    
}    

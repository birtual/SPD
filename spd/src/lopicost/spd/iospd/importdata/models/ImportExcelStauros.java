
package lopicost.spd.iospd.importdata.models;


import lopicost.spd.controller.SpdLogAPI;
import lopicost.spd.persistence.GestSustitucionesLiteDAO;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.struts.bean.PacienteBean;
import lopicost.spd.struts.helper.PacientesHelper;
import lopicost.spd.utils.StringUtil;
import lopicost.spd.utils.HelperSPD;
import lopicost.spd.utils.SPDConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class ImportExcelStauros extends ImportGenericLite
{
	int reg = 5;  //numeroCorteCabecera  / celda anterior a la de la primera dose
	int COLUMNAS = 21; //número de columnas a tratar del fichero 
	
	public ImportExcelStauros(){
		super();
	}

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
    	
    	
    	
    	medResi.setResiHabitacion((String) row.elementAt(i)); i++;
    	if(medResi.getResiHabitacion()!=null && !medResi.getResiHabitacion().equals("") && medResi.getResiHabitacion().length()>0)
    		medResi.setResiPlanta(medResi.getResiHabitacion().substring(0,1));

       	medResi.setResiCIP(StringUtil.limpiarTextoyEspacios((String) row.elementAt(i)));i++;
    	medResi.setNombrePacienteEnFichero((String) row.elementAt(i));i++;
		HelperSPD.getDatosPaciente(medResi);	

		medResi.setResiCn(StringUtil.limpiarTextoyEspacios((String) row.elementAt(i)));i++;
		medResi.setResiMedicamento((String) row.elementAt(i));i++;

		try{medResi.setResiToma1(HelperSPD.getPautaStandard(medResi, (String) row.elementAt(i)));i++; }catch(Exception e){}//7h
		try{medResi.setResiToma2(HelperSPD.getPautaStandard(medResi, (String) row.elementAt(i)));i++; }catch(Exception e){}//esmorzar 9h
		try{medResi.setResiToma3(HelperSPD.getPautaStandard(medResi, (String) row.elementAt(i)));i++; }catch(Exception e){}//dinar 13h
		try{medResi.setResiToma4(HelperSPD.getPautaStandard(medResi, (String) row.elementAt(i)));i++; }catch(Exception e){}//merienda 16h
		try{medResi.setResiToma5(HelperSPD.getPautaStandard(medResi, (String) row.elementAt(i)));i++; }catch(Exception e){}//cena 20h
		try{medResi.setResiToma6(HelperSPD.getPautaStandard(medResi, (String) row.elementAt(i)));i++; }catch(Exception e){}//resopón 24h

		try{medResi.setResiD1(StringUtil.limpiarTextoyEspacios((String) row.elementAt(i)));i++;}catch(Exception e){}
		try{medResi.setResiD2(StringUtil.limpiarTextoyEspacios((String) row.elementAt(i)));i++;}catch(Exception e){}
		try{medResi.setResiD3(StringUtil.limpiarTextoyEspacios((String) row.elementAt(i)));i++;}catch(Exception e){}
		try{medResi.setResiD4(StringUtil.limpiarTextoyEspacios((String) row.elementAt(i)));i++;}catch(Exception e){}
		try{medResi.setResiD5(StringUtil.limpiarTextoyEspacios((String) row.elementAt(i)));i++;}catch(Exception e){}
		try{medResi.setResiD6(StringUtil.limpiarTextoyEspacios((String) row.elementAt(i)));i++;}catch(Exception e){}
		try{medResi.setResiD7(StringUtil.limpiarTextoyEspacios((String) row.elementAt(i)));i++;}catch(Exception e){}
		
		int diasSemanaMarcados=HelperSPD.getDiasMarcados(medResi);  //importante!! para que detecte que hay días marcados y no los llene automáticamente.
    	medResi.setDiasSemanaMarcados(diasSemanaMarcados);
    	
    	try{medResi.setResiComentarios(StringUtil.limpiarTextoComentarios((String) row.elementAt(i)));i++;}catch(Exception e){}
    	try{medResi.setResiVariante(StringUtil.limpiarTextoComentarios((String) row.elementAt(i)));i++;}catch(Exception e){}
		
    	String detalleRow = HelperSPD.getDetalleRow(row, COLUMNAS);
        medResi.setDetalleRow(detalleRow);
    	System.out.println(" detalleRow " + detalleRow);
        medResi.setDetalleRowKey(crearDetalleRowKey(detalleRow, getPosicionesAEliminar()));
    	System.out.println(" detalleRowKey " + medResi.getDetalleRowKey());

		String siPrecisa = StringUtil.limpiarTextoyEspacios(medResi.getResiVariante());
		if(siPrecisa!=null &&  siPrecisa.equalsIgnoreCase("SIPRECISA"))
			siPrecisa="X"; else siPrecisa="";

		medResi.setResiSiPrecisa(siPrecisa);
		
    	try{medResi.setResiFinTratamiento(StringUtil.getStringArregloFecha((String) row.elementAt(i),  "dd/MM/yyyy"));i++;}    	catch(Exception e){}

		medResi.setResiInicioTratamiento(StringUtil.getStringArregloFecha("01/01/2024", "dd/MM/yyyy"));	//no nos envían fecha inicio

    	//si no hay CNResi ponemos el nombre de medicamento
		if(medResi.getResiCn()==null || medResi.getResiCn().equals(""))
		{
			medResi.setResiCn(medResi.getResiMedicamento());
			medResi.setValidar(SPDConstants.REGISTRO_VALIDAR);
			medResi.setConfirmar(SPDConstants.REGISTRO_CONFIRMAR);
			//medResi.setMensajesInfo(SPDConstants.INFO_CN_NO_INDICADO);
			medResi.setMensajesResidencia(SPDConstants.INFO_RESI_CN_NO_INDICADO);
			
		}

		medResi.setNumeroDeTomas(numeroDoses); // IMPORTANTE PARA TABULAR EL LISTADO!! TO-DO
		

		
		//la búsqueda de sustitución se realiza en la carga
		if(medResi.getResiCn()!=null && !medResi.getResiCn().equals("") )
    		GestSustitucionesLiteDAO.buscaSustitucionLite(getSpdUsuario(), medResi);
		
		if(medResi.getResiSiPrecisa()!=null && medResi.getResiSiPrecisa().equalsIgnoreCase("X")) {
			medResi.setSpdAccionBolsa(SPDConstants.SPD_ACCIONBOLSA_SI_PRECISA);
			//medResi.setRevisar("SI");
			medResi.setValidar(""); //mejor no revisarlo porque cada vez aparecerían en cada producción
		}
		
		medResi.setIdTratamientoCIP(HelperSPD.getID(medResi));
		boolean existeDuplicado=HelperSPD.borrarPosibleDuplicado(getSpdUsuario(), medResi);
		actualizaPlantaHabitacion(getSpdUsuario(), medResi);
		if(existeDuplicado)
			//this.errors.add(TextManager.getMensaje("ImportData.error.linea")+" " + row);
			//this.errors.add("Es un tratamiento que está duplicado " );
			throw new Exception ("Es un tratamiento que está duplicado ");
		System.out.println(" -----  borrarPosibleDuplicado Fin-->  " );
		
	}
	

	/**
1			2		3			4			5			6		7		8		9		10		11		12	13	14	15	16	17	18	19			20			21
Habitación	CIP	nombrePaciente	CNresi	MEDICAMENTORESI	7h	esmorzar	dinar	berenar	sopar	resopó	L	M	X	J	V	S	d	COMENTARIOS	SI_PRECISA	fecha STOP
	 */
	public List<Integer> getPosicionesAEliminar() {
		//en resiPlus no tendremos en cuenta  1 (Habitación) 
		List<Integer> result =new ArrayList<Integer>();
		result.add(1);
		return result;
	}

	public boolean actualizaPlantaHabitacion( String idUsuario, FicheroResiBean medResi) throws Exception {
		boolean result =false;
		try{
			PacienteBean pac = PacientesHelper.getPacientePorCIP(medResi.getResiCIP());
			if(!(pac!=null && pac.getPlanta().equalsIgnoreCase(medResi.getResiPlanta())&& pac.getHabitacion().equalsIgnoreCase(medResi.getResiHabitacion())))
			{
				result = PacientesHelper.actualizaPlantaHabitacion(idUsuario, medResi);
				if(result){
					String antes=" Planta= '" + medResi.getResiPlanta() + "', habitacion='" + medResi.getResiHabitacion() + "'";
					String despues=" Planta= '" + medResi.getResiPlanta() + "', habitacion='" + medResi.getResiHabitacion() + "'";
					//INICIO creación de log en BBDD
					try{
						SpdLogAPI.addLog(idUsuario, medResi.getResiCIP(),  medResi.getIdDivisionResidencia(), null, SpdLogAPI.A_RESIDENTE, SpdLogAPI.B_EDICION, SpdLogAPI.C_DATOSGENERALES,
								"SpdLog.tratamiento.edicion.auto.plantaHabitacion", 
							new String[]{idUsuario, medResi.getResiCIP(), antes, despues} );
					}catch(Exception e){}	// Cambios--> @@.
					//FIN creación de log en BBDD
				}
					
			}


		}catch(Exception e){
		
	}
		
		return result;
	}
	

}    


package lopicost.spd.iospd.importdata.models;


import lopicost.spd.persistence.GestSustitucionesLiteDAO;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.struts.bean.PacienteBean;
import lopicost.spd.utils.StringUtil;
import lopicost.spd.utils.DateUtilities;
import lopicost.spd.utils.HelperSPD;
import lopicost.spd.utils.SPDConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;


public class ImportResiplus extends ImportGenericLite
{
	int reg = 21;  //numeroCorteCabecera  / celda anterior a la de la primera dose
	int COLUMNAS = 26; //n�mero de columnas a tratar del fichero 

	
	public ImportResiplus(){
		super();
	}

	protected void recogerDatosRow(FicheroResiBean medResi, Vector row) throws Exception {

    	int i = 0;   
    	
		//Como sabemos que fechaDesde est� en la columna 6 (i=5)  miramos si es una fila v�lida o es cabecera, 
		try{
			String esOkFechaDesde = StringUtil.getStringArregloFecha((String) row.elementAt(5), "dd/MM/yyyy");
			if(!DateUtilities.isDateValid(esOkFechaDesde, "dd/MM/yyyy")) //si no es fecha v�lida (fecha inicio) se entiende que la fecha inicio es la siguiente columna (+1)
			{
				//this.errors.add("Importaci�n - Linea descartada por no ser v�lida --> " + row);
				//return;
				throw new Exception ("No es un tratamiento v�lido.");
			}
			
		}catch(Exception e)
		{
			throw new Exception ("No es un tratamiento v�lido.");
			//this.errors.add("Importaci�n - Linea descartada por no ser v�lida --> " + row);
			//return;
			
		}
		
		
    	String campoAux1 = (String) row.elementAt(i);i++;				//C�digo resi
    	//String  nombreResidente = (String) row.elementAt(i);i++;
    	medResi.setNombrePacienteEnFichero((String) row.elementAt(i));i++;
    	
    	String campoAux2 = (String) row.elementAt(i);i++;				//N� Ident.
    	String campoAux3 = (String) row.elementAt(i);i++;				//Seg. Social
       	medResi.setResiCIP(StringUtil.limpiarTextoyEspacios((String) row.elementAt(i)));i++;
    	
       	//String detalleRow = row.toString();
        // Procesa la cadena de Excel y convi�rtela en un arreglo o lista, por ejemplo
		//detalleRow = row.toString().replaceAll("[\\[\\]]", "").replaceAll("'", " ");
        //medResi.setDetalleRow(HelperSPD.getDetalleRowFechasOk(detalleRow));
		
//			f.setDetalleRow(HelperSPD.getDetalleRowFechasOk(resultSet.getString("detalleRow")!=null&&!resultSet.getString("detalleRow").equalsIgnoreCase("null")?resultSet.getString("detalleRow"):"")); 

    	String detalleRow = HelperSPD.getDetalleRow(row, COLUMNAS);
        medResi.setDetalleRow(detalleRow);
    	System.out.println(" detalleRow " + detalleRow);
        medResi.setDetalleRowKey(crearDetalleRowKey(detalleRow, getPosicionesAEliminar()));
    	System.out.println(" detalleRowKey " + medResi.getDetalleRowKey());
        medResi.setDetalleRowKeyLite(crearDetalleRowKeyLite(row, detalleRow, getPosicionesAEliminar()));
    	System.out.println(" detalleRowKeyLite " + medResi.getDetalleRowKeyLite());

		
		HelperSPD.getDatosPaciente(medResi);
		
		medResi.setResiInicioTratamiento(StringUtil.getStringArregloFecha((String) row.elementAt(i), "dd/MM/yyyy"));i++;	//ok
		medResi.setResiFinTratamiento(StringUtil.getStringArregloFecha((String) row.elementAt(i),  "dd/MM/yyyy"));i++;		//ok
		System.out.println(new Date()  + " -----  recogerDatosRow InicioTratamiento-->  "  + medResi.getResiInicioTratamiento());
		System.out.println(" -----  recogerDatosRow FinALTratamiento-->  "  + medResi.getResiFinTratamiento());
	   	//medResi.setResiMedicamento(StringUtil.limpiarTextoTomas((String) row.elementAt(i)));i++;
		medResi.setResiMedicamento((String) row.elementAt(i));i++;
		medResi.setResiD1(StringUtil.limpiarTextoyEspacios((String) row.elementAt(i)));i++;
		medResi.setResiD2(StringUtil.limpiarTextoyEspacios((String) row.elementAt(i)));i++;
		medResi.setResiD3(StringUtil.limpiarTextoyEspacios((String) row.elementAt(i)));i++;
		medResi.setResiD4(StringUtil.limpiarTextoyEspacios((String) row.elementAt(i)));i++;
		medResi.setResiD5(StringUtil.limpiarTextoyEspacios((String) row.elementAt(i)));i++;
		medResi.setResiD6(StringUtil.limpiarTextoyEspacios((String) row.elementAt(i)));i++;
		medResi.setResiD7(StringUtil.limpiarTextoyEspacios((String) row.elementAt(i)));i++;
		medResi.setResiVariante(StringUtil.limpiarTextoComentarios((String) row.elementAt(i)));i++;
		medResi.setResiComentarios(StringUtil.limpiarTextoComentarios((String) row.elementAt(i)));i++;
		medResi.setResiTipoMedicacion((String) row.elementAt(i));i++;					// T. Medicaci�n
		medResi.setResiToma1(HelperSPD.getPautaStandard(medResi, (String) row.elementAt(i)));i++; //AD - Antes desayuno
  		medResi.setResiToma2(HelperSPD.getPautaStandard(medResi, (String) row.elementAt(i)));i++; //esmorzar 9h
		medResi.setResiToma3(HelperSPD.getPautaStandard(medResi, (String) row.elementAt(i)));i++; //dinar 13h
		medResi.setResiToma4(HelperSPD.getPautaStandard(medResi, (String) row.elementAt(i)));i++; //merienda 16h
		medResi.setResiToma5(HelperSPD.getPautaStandard(medResi, (String) row.elementAt(i)));i++; //cena 20h
		medResi.setResiToma6(HelperSPD.getPautaStandard(medResi, (String) row.elementAt(i)));i++; //resop�n 24h
		String resiPauta=medResi.getResiToma1()+"-"+medResi.getResiToma2()+"-"+medResi.getResiToma3()+"-"+medResi.getResiToma4()+"-"+medResi.getResiToma5()+"-"+medResi.getResiToma6();
		medResi.setResiPauta(resiPauta);
		
		
		medResi.setResiViaAdministracion((String) row.elementAt(i));i++; //V�a administraci�n
		medResi.setResiCn(StringUtil.limpiarTextoyEspacios((String) row.elementAt(i)));i++;
   
		
		int diasSemanaMarcados=HelperSPD.getDiasMarcados(medResi);  //importante!! para que detecte que hay d�as marcados y no los llene autom�ticamente.
    	medResi.setDiasSemanaMarcados(diasSemanaMarcados);
    	
    	
    	
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
		String siPrecisa = StringUtil.limpiarTextoyEspacios(medResi.getResiVariante());
		
		if(siPrecisa!=null &&  siPrecisa.equalsIgnoreCase("SIPRECISA"))
			siPrecisa="X"; else siPrecisa="";

		medResi.setResiSiPrecisa(siPrecisa);

		
		//	medResi.setResiObservaciones(StringUtil.limpiarTextoComentarios((String) row.elementAt(i)));i++;
	//	medResi.setResiPeriodo(StringUtil.limpiarTexto((String) row.elementAt(i)));i++;
	//	medResi.setResiViaAdministracion(""); En onada no se recibe
	//	medResi.setResiFormaMedicacion("");   En Onada no se recibe
    	
		//la b�squeda de sustituci�n se realiza en la carga
		if(medResi.getResiCn()!=null && !medResi.getResiCn().equals("") )
    		GestSustitucionesLiteDAO.buscaSustitucionLite(getSpdUsuario(), medResi);
		
		   
		if(medResi.getResiSiPrecisa()!=null && medResi.getResiSiPrecisa().equalsIgnoreCase("X")) {
			medResi.setSpdAccionBolsa(SPDConstants.SPD_ACCIONBOLSA_SI_PRECISA);
			//medResi.setRevisar("SI");
			medResi.setValidar(""); //mejor no revisarlo porque cada vez aparecer�an en cada producci�n
		}
		
		

		System.out.println(" -----  recogerDatosRow getID INIT-->  "  + medResi.getResiFinTratamiento());
		medResi.setIdTratamientoCIP(HelperSPD.getID(medResi));
		System.out.println(" -----  recogerDatosRow getID FIN-->  "  + medResi.getIdTratamientoCIP());
		
		//System.out.println(" -----  borrarPosibleDuplicado INICIO-->  ");
		//en caso de duplicado lo muestra en errores de carga
		//boolean existeDuplicado=HelperSPD.borrarPosibleDuplicado(getSpdUsuario(), medResi);
		//if(existeDuplicado)
			//this.errors.add(TextManager.getMensaje("ImportData.error.linea")+" " + row);
			//this.errors.add("Es un tratamiento que est� duplicado " );
			//throw new Exception ("Es un tratamiento que est� duplicado ");
		//System.out.println(" -----  borrarPosibleDuplicado Fin-->  " );
		
		
		
	}
	
	/**
	 * 1		2		3		4				5		6		7		8			9	10	11	12	13	14	15		16		17			18				19	20	21	22	23	24		25					26
	C�digo	 Nombre	 N� Ident.	Seg. Social	 N� C.I.P.	Desde 	Hasta	Medicamento	L	M	X	J	V	S	D	Variante	Comentarios	T. Medicaci�n	" "	DE	CO	ME	CE	N	V�a administraci�n	C. Nacional
	 */
	public List<Integer> getPosicionesAEliminar() {
		//en resiPlus no tendremos en cuenta  1 (C�digo) / 3 (N� Ident.) / 4 (Seg. Social)
		List<Integer> result =new ArrayList<Integer>();
		result.add(1);
		result.add(3);
		result.add(4);
		return result;
	}

	

}    

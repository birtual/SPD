
package lopicost.spd.iospd.importdata.models;


import lopicost.spd.persistence.GestSustitucionesLiteDAO;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.struts.bean.PacienteBean;
import lopicost.spd.utils.StringUtil;
import lopicost.spd.utils.DataUtil;
import lopicost.spd.utils.DateUtilities;
import lopicost.spd.utils.HelperSPD;
import lopicost.spd.utils.SPDConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;


public class ImportSFAssis extends ImportGenericLite
{
	int reg = 5;  //numeroCorteCabecera  / celda anterior a la de la primera dose
	int COLUMNAS = 24; //número de columnas a tratar del fichero 

	
	public ImportSFAssis(){
		super();
	}

	protected void recogerDatosRow(FicheroResiBean medResi, Vector row) throws Exception {

		//para identificar un registro válido una vez sabemos dónde se ubica algún:
		//fechaDesde (10 o 12) 
		//CN:  que ha de llegar si o si --> "cn resi" (3), "CN propuesto" (5), "CN final" (5 o 7) 
		//miramos si es una fila válida o es cabecera, 

		/* ya no hace falta este control
		String esOkFechaDesde = StringUtil.getStringArregloFecha((String) row.elementAt(10), "dd/MM/yyyy");
		if(!DateUtilities.isDateValid(esOkFechaDesde, "dd/MM/yyyy")) //si no es fecha válida (fecha inicio) se entiende que la fecha inicio es la siguiente columna (+1)
		{	//primera fecha 
			esOkFechaDesde = StringUtil.getStringArregloFecha((String) row.elementAt(12), "dd/MM/yyyy");
			if(!DateUtilities.isDateValid(esOkFechaDesde, "dd/MM/yyyy")) 
			{	//segunda fecha 
				boolean unCn = DataUtil.isNumero((String) row.elementAt(3) );
				if(!unCn)	//primer CN buscado
					unCn = DataUtil.isNumero((String) row.elementAt(5));
				if(!unCn)	//segundo CN buscado
					unCn = DataUtil.isNumero((String) row.elementAt(7));
				if(!unCn)	//tercer CN buscado
					throw new Exception ("Línea descartada.");
			}
		}
		*/
		
    	int i = 0;   
       	medResi.setResiCIP(StringUtil.limpiarTextoyEspacios((String) row.elementAt(i)));i++;
       	if(medResi.getResiCIP()!=null && medResi.getResiCIP().equalsIgnoreCase("CIP"))	
			throw new Exception ("Línea cabecera descartada.");
       	
       	medResi.setResiIdResidente((String) row.elementAt(i));i++;
    	medResi.setNombrePacienteEnFichero((String) row.elementAt(i));i++;
		medResi.setResiCn(StringUtil.limpiarTextoyEspacios((String) row.elementAt(i)));i++;
		medResi.setResiMedicamento((String) row.elementAt(i));i++;
		//i++;	//CN propuesto	// YA NO HACE FALTA QUE LLEGUEN
		//i++;	//DESCRIPCION	// YA NO HACE FALTA QUE LLEGUEN
		//medResi.setSpdCnFinal((String) row.elementAt(i));i++;
		//medResi.setSpdNombreBolsa((String) row.elementAt(i));i++;
		//medResi.setSpdFormaMedicacion((String) row.elementAt(i));i++;
		//medResi.setSpdAccionBolsa((String) row.elementAt(i));i++;
		i++;	//dosis
		String fechaInicio = StringUtil.getStringArregloFecha((String) row.elementAt(i), "dd/MM/yyyy");
		if(fechaInicio==null || fechaInicio.equals("")) 
			 fechaInicio = StringUtil.formatearFecha((String) row.elementAt(i), "dd/MM/yyyy");
		
		if(fechaInicio==null || fechaInicio.equals(""))  fechaInicio = "01/01/1900"; //hemos de poner alguna para que se procese
		
		
		medResi.setResiInicioTratamiento(fechaInicio);i++;		
		medResi.setResiFinTratamiento(StringUtil.getStringArregloFecha((String) row.elementAt(i),  "dd/MM/yyyy"));i++;
		i++;	//saltamos variante
		//medResi.setResiVariante(StringUtil.limpiarTextoComentarios((String) row.elementAt(i)));i++;
		medResi.setResiObservaciones(StringUtil.limpiarTextoComentarios((String) row.elementAt(i)));i++;
		medResi.setResiComentarios(StringUtil.limpiarTextoComentarios((String) row.elementAt(i)));i++;
		
		medResi.setResiD1(StringUtil.limpiarTextoyEspacios((String) row.elementAt(i)));i++;
		medResi.setResiD2(StringUtil.limpiarTextoyEspacios((String) row.elementAt(i)));i++;
		medResi.setResiD3(StringUtil.limpiarTextoyEspacios((String) row.elementAt(i)));i++;
		medResi.setResiD4(StringUtil.limpiarTextoyEspacios((String) row.elementAt(i)));i++;
		medResi.setResiD5(StringUtil.limpiarTextoyEspacios((String) row.elementAt(i)));i++;
		medResi.setResiD6(StringUtil.limpiarTextoyEspacios((String) row.elementAt(i)));i++;
		medResi.setResiD7(StringUtil.limpiarTextoyEspacios((String) row.elementAt(i)));i++;

		medResi.setResiToma1(HelperSPD.getPautaStandard(medResi, (String) row.elementAt(i)));i++; //primera hora
  		medResi.setResiToma2(HelperSPD.getPautaStandard(medResi, (String) row.elementAt(i)));i++; //esmorzar 9h
		medResi.setResiToma3(HelperSPD.getPautaStandard(medResi, (String) row.elementAt(i)));i++; //dinar 13h
		medResi.setResiToma4(HelperSPD.getPautaStandard(medResi, (String) row.elementAt(i)));i++; //merienda 16h
		medResi.setResiToma5(HelperSPD.getPautaStandard(medResi, (String) row.elementAt(i)));i++; //cena 20h
		medResi.setResiToma6(HelperSPD.getPautaStandard(medResi, (String) row.elementAt(i)));i++; //resopón 24h
		String resiPauta=medResi.getResiToma1()+"-"+medResi.getResiToma2()+"-"+medResi.getResiToma3()+"-"+medResi.getResiToma4()+"-"+medResi.getResiToma5()+"-"+medResi.getResiToma6();
		medResi.setResiPauta(resiPauta);

		HelperSPD.getDatosPaciente(medResi);	


		int diasSemanaMarcados=HelperSPD.getDiasMarcados(medResi);  //importante!! para que detecte que hay días marcados y no los llene automáticamente.
    	medResi.setDiasSemanaMarcados(diasSemanaMarcados);

    	String detalleRow = HelperSPD.getDetalleRow(row, COLUMNAS);
        medResi.setDetalleRow(detalleRow);
    	System.out.println(" detalleRow " + detalleRow);
        medResi.setDetalleRowKey(crearDetalleRowKey(detalleRow, getPosicionesAEliminar()));
    	medResi.setDetalleRowKeyLite(crearDetalleRowKeyLite(row, detalleRow, getPosicionesAEliminar()));
       	System.out.println(" detalleRowKey 		" + medResi.getDetalleRowKey());
       	System.out.println(" detalleRowKeyLite 	" + medResi.getDetalleRowKeyLite());

		//String siPrecisa = StringUtil.limpiarTextoyEspacios(medResi.getResiVariante());
		String siPrecisa = StringUtil.limpiarTextoyEspacios(medResi.getResiComentarios());
		
		if(siPrecisa!=null &&  siPrecisa.equalsIgnoreCase("SOLOSIPRECISA"))
			siPrecisa="X"; else 
			{
				siPrecisa = StringUtil.limpiarTextoyEspacios(medResi.getResiObservaciones());
				if(siPrecisa!=null &&  siPrecisa.equalsIgnoreCase("SOLOSIPRECISA"))
					siPrecisa="X"; else 
						siPrecisa="";
			}

		medResi.setResiSiPrecisa(siPrecisa);

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
		
		//la búsqueda de sustitución se realiza en la carga. si llega el CN final buscaremos el nombre corto y mantendremos la acción en caso que venga en el Excel.
		if(medResi.getSpdCnFinal()!=null && !medResi.getSpdCnFinal().equals(""))
		{
    		GestSustitucionesLiteDAO.buscaSustitucionLitePorCnFinal(getSpdUsuario(), medResi);
		}
		else if(medResi.getResiCn()!=null && !medResi.getResiCn().equals("") && (medResi.getSpdCnFinal()==null || medResi.getSpdCnFinal().equals("")))
    		GestSustitucionesLiteDAO.buscaSustitucionLite(getSpdUsuario(), medResi);
		
		if(medResi.getResiSiPrecisa()!=null && medResi.getResiSiPrecisa().equalsIgnoreCase("X")) {
			medResi.setSpdAccionBolsa(SPDConstants.SPD_ACCIONBOLSA_SI_PRECISA);
			//medResi.setRevisar("SI");
			medResi.setValidar(""); //mejor no revisarlo porque cada vez aparecerían en cada producción
		}
	
		medResi.setIdProcessIospd(SPDConstants.IDPROCESO_MONTSENY);
		
		
		medResi.setIdTratamientoCIP(HelperSPD.getID(medResi));
		boolean existeDuplicado=HelperSPD.borrarPosibleDuplicado(getSpdUsuario(), medResi);
		if(existeDuplicado)
			//this.errors.add(TextManager.getMensaje("ImportData.error.linea")+" " + row);
			//this.errors.add("Es un tratamiento que está duplicado " );
			throw new Exception ("Es un tratamiento que está duplicado ");
		System.out.println(" -----  borrarPosibleDuplicado Fin-->  " );
		
	}

	/**
1	2	3			4			5		6			7			8		9				10				11				12	13	14	15	16	17	18	19		20			21		22			23		24
CIP	NHC	Nombre	C.Nacional	Medicamento	Dosis	F. inicio	F. final	Código	Frecuencia/alternancia	Observaciones	LU	MA	MI	JU	VI	SA	DO	Ayunas	Desayuno	Comida	Merienda	Cena	Recena
	 */
	public List<Integer> getPosicionesAEliminar() {
		//en resiPlus no tendremos en cuenta  2 (NHC) / 6 (Dosis) / (Código)
		List<Integer> result =new ArrayList<Integer>();
		result.add(2);
		result.add(6);
		//result.add(7);//TMP
		//result.add(8);//TMP
		result.add(9);
		//result.add(10);//TMP
		//result.add(11);//TMP
		return result;
	}
	
}    

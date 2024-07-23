package lopicost.spd.robot.helper;



import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import lopicost.spd.model.DivisionResidencia;

import lopicost.spd.robot.model.FiliaDM;
import lopicost.spd.robot.model.FiliaRX;
import lopicost.spd.struts.bean.FicheroResiBean;


public class PlantillaUnificada {
	

	
	private final String cLOGGERHEADER = "PlantillaUnificada: ";
	private final String cLOGGERHEADER_ERROR = cLOGGERHEADER + "ERROR: PlantillaUnificada: ";
	static TreeMap Doses_TreeMap =new TreeMap();

	

	public static FiliaDM creaFicheroDM(String spdUsuario, FicheroResiBean cab) throws Exception {
		//recuperar el listado de distinct medicamentos del proceso,
		
		FiliaDM medicamentos = PlantillaUnificadaHelper.getMedicamentosProceso(spdUsuario, cab);
		
		return medicamentos;
		//Crear el XML a partir de la consulta
		
		}
	
	public static FiliaRX creaFicheroRX(String spdUsuario, FicheroResiBean cab, DivisionResidencia div) throws Exception {
		//Cabecera con tomas
		//arrayTramosHorarios
	//	FicheroResiBean cab = FicheroResiCabeceraDAO.getFicheroResiCabeceraByOid(spdUsuario, oidFicheroResiCabecera);
		FiliaRX filiaRX = PlantillaUnificadaHelper.getTratamientosProceso(spdUsuario, cab, div);

		//recuperar el listado ORDENADO del detalle del proceso,ordenado por CIP, nombreBolsa
		
		//insertar en la tabla tmp_detallesTomas_U_ 
		return filiaRX;


	}


}

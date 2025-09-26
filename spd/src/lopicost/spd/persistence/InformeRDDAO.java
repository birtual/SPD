package lopicost.spd.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;

import lopicost.config.pool.dbaccess.Conexion;
import lopicost.spd.model.spd.BolsaSPD;
import lopicost.spd.model.spd.DiaSPD;
import lopicost.spd.model.spd.LineaBolsaSPD;
import lopicost.spd.model.spd.ProduccionPaciente;
import lopicost.spd.model.rd.AspectoMedicamento;
import lopicost.spd.model.rd.DiaTomas;
import lopicost.spd.model.rd.TratamientoPaciente;
import lopicost.spd.model.rd.MedicamentoReceta;
import lopicost.spd.model.rd.Toma;
import lopicost.spd.struts.bean.CabecerasXLSBean;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.struts.bean.PacienteBean;
import lopicost.spd.utils.HelperSPD;
import lopicost.spd.utils.SPDConstants;
import lopicost.spd.helper.InformeRDHelper;


import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class InformeRDDAO
{
    static String className;
	static InformeRDHelper helper = new InformeRDHelper();
	   	
    static {
        InformeRDDAO.className = "InformeRDDAO";
    }
    
    
    public static String getQueryByIdProceso(FicheroResiBean cab) throws Exception {

    	String fechaInicioSPD = cab.getFechaDesde();
    	
    	String qry = " SELECT    ISNULL( r.offsetDays,   		";	
		  qry +=  "     CASE WHEN b.fechaToma IS NOT NULL THEN    				";	
		  qry +=  "         DATEDIFF(DAY, CAST(CONVERT(date, '"+fechaInicioSPD+"', 103) AS date), b.fechaToma)    			";	
		  qry +=  "         ELSE NULL  		";	
		  qry +=  "  	END   ) AS offsetDays, ";	
		  qry +=  "      b.fechaHoraProceso AS fechaHoraProceso_Birtual, ";	
		  qry +=  " 	b.idDivisionResidencia AS idDivisionResidencia_Birtual, ";	
		  qry +=  " 	b.idProceso AS idProceso_Birtual, ";	
		  qry +=  " 	b.CIP AS CIP_Birtual, ";	
		  qry +=  " 	b.CN AS CN_Birtual, ";	
		  qry +=  " 	b.nombreMedicamento AS nombreMedicamento_Birtual, ";	
		  qry +=  " 	b.cantidadToma AS cantidadToma_Birtual, ";	
		  qry +=  " 	b.dispensar AS dispensar_Birtual, ";	
		  qry +=  " 	b.fechaToma AS fechaToma_Birtual, ";	
		  qry +=  " 	CONVERT(VARCHAR(10), CAST(b.fechaToma AS DATE), 103) AS fechaToma2_Birtual,";	
		  qry +=  " 	b.idLineaRX AS idLineaRX_Birtual, ";	
		  qry +=  " 	b.tramoToma AS tramoToma_Birtual, ";	
		  qry +=  " 	b.idToma AS idToma_Birtual, ";	
		  qry +=  " 	b.nombreToma AS nombreToma_Birtual, ";	
		  qry +=  " 	b.planta AS planta_Birtual, ";	
		  qry +=  " 	b.habitacion AS habitacion_Birtual, ";	
		  qry +=  " 	b.numBolsa AS numBolsa_Birtual, ";	
		  qry +=  " 	b.idBolsa AS idBolsa_Birtual, ";	
		  qry +=  " 	b.idFreeInformation AS idFreeInformation_Birtual, ";	
		  qry +=  " 	b.idDetalle AS idDetalle_Birtual, ";	
		  qry +=  " 	b.orderNumber AS orderNumber_Birtual, ";	
		  qry +=  " 	b.CodGtVm AS CodGtVm_Birtual, ";	
		  qry +=  " 	b.NomGtVm AS NomGtVm_Birtual, ";	
		  qry +=  " 	b.CodGtVmp AS CodGtVmp_Birtual, ";	
		  qry +=  " 	b.NomGtVmp AS NomGtVmp_Birtual, ";	
		  qry +=  " 	b.CodGtVmpp AS CodGtVmpp_Birtual, ";	
		  qry +=  " 	b.NomGtVmpp AS NomGtVmpp_Birtual, ";	
		  qry +=  " 	b.pautaResidencia AS pautaResidencia_Birtual, ";	
		  qry +=  " 	b.nombreMedicamentoConsejo AS nombreConsejo_Birtual,";	 
		  qry +=  " 	b.nombreLaboratorio AS nombreLab_Birtual,";	
		  qry +=  " 	r.idRobot AS idRobot_Robot, ";	
		  qry +=  " 	r.idDivisionResidencia AS idDivisionResidencia_Robot, ";	
		  qry +=  " 	r.idResidenciaCarga AS idResidenciaCarga_Robot, ";	
		  qry +=  " 	r.diaInicioSPD AS diaInicioSPD_Robot, ";	
		  qry +=  " 	r.CIP AS CIP_Robot, ";	
		  qry +=  " 	r.prescriptionOrderNumber AS prescriptionOrderNumber_Robot, ";	
		  qry +=  " 	r.diaDesemblistado AS diaDesemblistado_Robot, ";	
		  qry +=  " 	r.diaEmbolsado AS diaEmbolsado_Robot, ";	
		  qry +=  " 	r.horaEmbolsado AS horaEmbolsado_Robot, ";	
		  qry +=  " 	r.totalBolsas AS totalBolsas_Robot, ";	
		  qry +=  " 	r.numeroOrdenBolsa AS numeroOrdenBolsa_Robot, ";	
		  qry +=  " 	r.primerIdBolsaSPD AS primerIdBolsaSPD_Robot, ";	
		  qry +=  " 	r.ultimoIdBolsaSPD AS ultimoIdBolsaSPD_Robot, ";	
		  qry +=  " 	r.idBolsa AS idBolsa_Robot, ";	
		  qry +=  " 	r.CN AS CN_Robot, ";	
		  qry +=  " 	r.nombreMedicamentoBolsa AS nombreEnBolsa_Robot, ";	
		  qry +=  " 	r.cantidad AS cantidad_Robot, ";	
		  qry +=  " 	r.lote AS lote_Robot, ";	
		  qry +=  " 	r.caducidad AS caducidad_Robot, ";	
		  qry +=  " 	r.codigoBarras AS codigoBarras_Robot, ";	
		  qry +=  " 	r.codigoMedicamentoRobot AS codigoM_Interno_Robot, ";	
		  qry +=  " 	r.freeInformation AS freeInformation_Robot, ";	
		  qry +=  " 	r.offsetDays AS offsetDays_Robot, ";	
		  qry +=  " 	r.doseTime AS doseTime_Robot, ";	
		  qry +=  " 	r.numeroTolva AS numeroTolva_Robot, ";	
		  qry +=  " 	r.fechaInsert AS fechaInsert_Robot, ";	
		  qry +=  " 	r.CodGtVm AS CodGtVm_Robot, ";	
		  qry +=  " 	r.NomGtVm AS NomGtVm_Robot, ";	
		  qry +=  " 	r.CodGtVmpp AS CodGtVmpp_Robot, ";	
		  qry +=  " 	r.NomGtVmpp AS NomGtVmpp_Robot, ";	
		  qry +=  " 	r.CodGtVmp AS CodGtVmp_Robot, ";	
		  qry +=  " 	r.NomGtVmp AS NomGtVmp_Robot, ";	
		  qry +=  " 	r.idProceso AS idProceso_Robot, ";	
		  qry +=  " 	r.numeroSerie AS numeroSerie_Robot, ";	
		  qry +=  " 	r.fechaCorte AS fechaCorte_Robot, ";	
		  qry +=  " 	r.FORMA AS FORMA, ";	
		  qry +=  " 	r.COLOR1 AS COLOR1, ";	
		  qry +=  " 	r.COLOR2 AS COLOR2, ";	
		  qry +=  " 	r.RANURA AS RANURA, ";	
		  qry +=  " 	r.INSCRIPCIONA AS INSCRIPCIONA, ";	
		  qry +=  " 	r.INSCRIPCIONB AS INSCRIPCIONB, ";	
		  qry +=  " 	r.DIBUJO AS DIBUJO, ";	
		  qry +=  " 	r.LARGO AS LARGO, ";	
		  qry +=  " 	r.ANCHO AS ANCHO, ";	
		  qry +=  " 	r.nombreMedicamentoConsejo AS nombreConsejo_Robot, ";	
		  qry +=  " 	r.nombreLaboratorio AS nombreLab_Robot";	
		  qry +=  " FROM SPD_XML_detallesTomasRobot b ";	
		  qry +=  " LEFT JOIN SPD_robotProducciones r ";	
		  qry +=  "     ON b.idProceso = r.idProceso ";	
		  qry +=  " 	AND b.CIP = r.CIP ";	
		  qry +=  " 	AND b.idFreeInformation = r.FreeInformation ";	
		  qry +=  " 	AND b.idDivisionResidencia = r.idDivisionResidencia ";	
		  qry +=  " 	AND ( b.CodGtVm = r.CodGtVm or b.nombreMedicamento=r.nombreMedicamentoBolsa) ";	
		  /*qry +=  " LEFT JOIN spd_auxCodGT t  ";	
		  qry +=  "     ON t.codGtVmp = r.codGtVmp  ";	
		  qry +=  " 	AND t.codGtVm = r.codGtVm  ";	
		  qry +=  " 	AND t.codGtVmpp = r.codGtVmpp ";	
		  qry +=  " 	AND t.codGtVmp = b.codGtVmp  ";	
		  qry +=  " 	AND t.codGtVm = b.codGtVm  ";	*/
	//	  qry +=  " --  AND t.codGtVmpp = b.codGtVmpp ";	
		  qry +=  " WHERE b.idProceso = '"+ cab.getIdProceso() +"' ";	
		  qry +=  " AND NOT (dispensar = 'S' AND idRobot IS NULL) ";	
	//	  qry +=  " -- AND b.nomGtVm ='SERTRALINA' ";	
	//	  qry +=  " -- AND b.CIP='SEBO0660228000' ";	
	//	  qry +=  " -- AND NOT (r.codGtVm IS NOT NULL AND t.codGtVm IS NULL); ";
		  qry +=  " ORDER BY b.CIP, b.fechaToma, b.idToma, b.dispensar; ";
		    
			  return qry;
    }
 
    
    
    public static List<ProduccionPaciente> findLiteByIdProceso(String spdUsuario, FicheroResiBean cab, boolean recetas, boolean mezclar) throws Exception {
    	
	   	String qry = getQueryByIdProceso(cab);
    	
    	List<ProduccionPaciente> producciones = new ArrayList<ProduccionPaciente>();
    	
    	producciones = desarrollaListadoProduccion( spdUsuario,  qry,  cab,  recetas,  mezclar);
    	
        return producciones;
    }
 
    

 
    
	private static List<MedicamentoReceta> buscarDispensacionesReceta(String CIP, int cuantos)throws SQLException, ClassNotFoundException 
	{
		List<MedicamentoReceta>  result = new ArrayList();
		Connection con = Conexion.conectar();
		String qry = " SELECT  TOP " + cuantos + " *  ";
		qry+= " FROM hst_CodigosRecetasDispensados  r ";
		qry+= " LEFT JOIN bd_consejo b ON r.codigoDispensado = b.codigo ";
		qry+= " WHERE r.cip = '" + CIP+ "'";
		qry+= " ORDER BY Fecha desc";

		System.out.println(className + "--> buscarDispensacionesReceta" +qry );		
		try {
			ResultSet rs = null;
			PreparedStatement pstat = con.prepareStatement(qry);
			rs = pstat.executeQuery();
			while (rs.next()) {
				MedicamentoReceta medic = helper.creaMedicamentoReceta(rs);
				result.add(medic);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	return result;
	}

	public static MedicamentoReceta buscarUltimaDispensacionReceta(String CIP, String nomGtVmp)throws SQLException, ClassNotFoundException 
	{
		MedicamentoReceta  medic = null;
		AspectoMedicamento  ident = null;
		Connection con = Conexion.conectar();
		String qry = " SELECT  TOP 1 r.*, a.*  ";
		qry+= " FROM hst_CodigosRecetasDispensados  r ";
		qry+= " LEFT JOIN bd_consejo b1 ON r.codigoDispensado = b1.CODIGO ";
		qry+= " LEFT JOIN bd_consejo_aspecto a ON  r.codigoDispensado = a.CODIGO " ;
		qry+= " WHERE r.CIP = '" + CIP+ "' AND NomGtVmp='"+nomGtVmp+"' ";
		qry+= " ORDER BY r.fecha DESC ";

			
		System.out.println(className + "--> buscarUltimaDispensacionReceta" +qry );		
		try {
			ResultSet rs = null;
			PreparedStatement pstat = con.prepareStatement(qry);
			rs = pstat.executeQuery();
			while (rs.next()) {
				medic = helper.creaMedicamentoReceta(rs);
				ident = helper.creaIdentificacion(rs);
				medic.setAspectoMedicamento(ident);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	return medic;
	}


	   
	   public static List<ProduccionPaciente> desarrollaListadoProduccion(String spdUsuario, String qry, FicheroResiBean cab, boolean recetas, boolean mezclar) throws Exception {
	    	
	    	List<ProduccionPaciente> producciones = new ArrayList<ProduccionPaciente>();
	    	
	    	
	    	
	    	
			//recuperamos las tomas
			List<CabecerasXLSBean> tomasCabecera = CabecerasXLSDAO.list(spdUsuario, cab.getOidDivisionResidencia());
			
			int maxTomas = tomasCabecera.size();
			
			
		   	TreeMap<String, PacienteBean> tm_CIPS =new TreeMap<String, PacienteBean>();
		   	TreeMap<String, TratamientoPaciente> tm_Tratamientos =new TreeMap<String, TratamientoPaciente>();
		   	//TreeMap<String, MedicamentoPaciente> Medic_TreeMap =new TreeMap<String, MedicamentoPaciente>();
		   	TreeMap<String, DiaSPD> tm_DiasSPD =new TreeMap<String, DiaSPD>();
		   	TreeMap<String, DiaTomas> tm_DiasTomas =new TreeMap<String, DiaTomas>();
		   	TreeMap<String, BolsaSPD> tm_BolsaSPD =new TreeMap<String, BolsaSPD>();
		   	//TreeMap<String, Toma> tm_Tomas =new TreeMap<String, Toma>();
		   	
		   	String keyCIP="";
		   	String keyTratamiento="";
		    //String keyMedic="";
		    String keyDiaSPD="";
		    String keyDiaTomas="";
		    String keyBolsaSPD="";
		    

		   	PacienteBean paciente = null; 
		   	ProduccionPaciente produccionPaciente = new ProduccionPaciente();

		   	Connection con = Conexion.conectar();
		   	
		    System.out.println(HelperSPD.dameFechaHora() + " " + String.valueOf(InformeSpdDAO.className) + "--> desarrollaListadoProduccion  -->" + qry);
	        ResultSet rs = null;
	        try {
	            PreparedStatement pstat = con.prepareStatement(qry);
	            rs = pstat.executeQuery();

	            while (rs.next()) {
	            	if(helper.descartable(rs)) continue; //descartamos. CASO 2 
	 
	            	// Control de Paciente 
	            	keyCIP=rs.getString("CIP_Birtual");
	            	if (tm_CIPS.containsKey(keyCIP)) 
	 		      	{
	            		paciente = tm_CIPS.get(keyCIP);
	 		      	}
	            	else
	            	{
	            		
	            		produccionPaciente = helper.creaProduccion(rs, cab);
	            		producciones.add(produccionPaciente);
	            		paciente = helper.creaPaciente(keyCIP);
	                	produccionPaciente.setPaciente(paciente);
	            		//creamos la estructura de tomas
	                	tm_DiasSPD = helper.crearTreemapDiasSPD(keyCIP, cab);
	            		produccionPaciente.getDiasSPD().clear(); // opcional: si quieres vaciarla antes
	            		produccionPaciente.getDiasSPD().addAll(tm_DiasSPD.values());
	            		if(recetas)
	            			paciente.setDispensacionesReceta(buscarDispensacionesReceta(paciente.getCIP(), SPDConstants.CUANTAS_DISPENSACIONES));
	            		//System.out.println(HelperSPD.dameFechaHora() + " findLiteByResidenciaCarga --> tm_DiasSPD " + tm_DiasSPD);	
	         		
	                	tm_CIPS.put(keyCIP, paciente);
	            	}
	            	// Control del tratamiento de la medicación
	        	    TratamientoPaciente tratamiento = null;
	        	    //MedicamentoPaciente medic = new MedicamentoPaciente();
	            	keyTratamiento = keyCIP + "_" +  rs.getString("CN_Robot") + "_" +  rs.getString("lote_Robot");
	            	if (tm_Tratamientos.containsKey(keyTratamiento)) 
	 		      	{
	            		tratamiento = tm_Tratamientos.get(keyTratamiento);
	            		tratamiento.setCantidadTotalEmblistadaSPD(tratamiento.getCantidadTotalEmblistadaSPD() + rs.getFloat("cantidad_Robot") ); //actualizamos cantidad total
	 		      	}
	            	else
	            	{
	                	tratamiento = helper.creaTratamientoPaciente(rs, mezclar);
	                	//produccionPaciente.getTratamientosPaciente().add(tratamiento);
	                	//separamos emblistados de fuera de blister
	            		if(tratamiento.isEmblistar())
	            			produccionPaciente.getTtosEmblistados().add(tratamiento);
	            		else
	            			produccionPaciente.getTtosFueraBlister().add(tratamiento);
	                	tm_Tratamientos.put(keyTratamiento, tratamiento);
	            	}
	 
	        	    DiaTomas diaTomas = null;
	            	keyDiaTomas =  keyTratamiento + "_" + rs.getInt("offsetDays"); //contiene el CN y sus días 
	            	if (tm_DiasTomas.containsKey(keyDiaTomas)) 
	 		      	{
	             		diaTomas = tm_DiasTomas.get(keyDiaTomas);
	             		diaTomas.setCantidadDia(diaTomas.getCantidadDia() + rs.getInt("cantidad_Robot")); //añadimos cantidad global
	 		      	}
	            	else
	            	{
	            		diaTomas = helper.creaDiaTomas(rs);
	            		helper.insertarEnPosicion(tratamiento.getMedicamentoRobot().getDiaTomas(), rs.getInt("offsetDays"), diaTomas); 	//añadimos el día de tomas en el tratamiento/medicación 
	            		tm_DiasTomas.put(keyDiaTomas, diaTomas);
	            	}
	            	//	System.out.println(HelperSPD.dameFechaHora() + " findLiteByResidenciaCarga --> tm_DiasTomas " + tm_DiasTomas);	
	                int numTomas= tratamiento.getMedicamentoRobot().getDiaTomas().size();
	                //	System.out.println(HelperSPD.dameFechaHora() + " findLiteByResidenciaCarga --> numTomas " + numTomas + " - " + tratamiento.getMedicamentoPaciente().getNombreMedicamentoConsejo());	
	               	//Toma toma = helper.creaToma(rs, numTomas++);
	               	Toma toma = helper.creaToma(rs, numTomas);
	            	diaTomas.getTomas().add(toma);
	            	
	             	// Control del día SPD con todos los CN. se añade en producción, para el report 2 de detalle bolsas  
	        	    DiaSPD diaSPD = null;
	        	    keyDiaSPD =  keyCIP + "_" + rs.getInt("offsetDays") ; //contiene todas las bolsas de la producción
	 
	        	    diaSPD = tm_DiasSPD.get(keyDiaSPD);
	           		//System.out.println(HelperSPD.dameFechaHora() + " findLiteByResidenciaCarga --> diaSPD " + diaSPD!=null?diaSPD.getCantidadDia():"Sin diaSPD");		
	        	    //	System.out.println(HelperSPD.dameFechaHora() + " findLiteByResidenciaCarga --> keyCIP tm_DiasSPD " + keyCIP + " " + tm_DiasSPD);	
	       	    
	         		if(diaSPD!=null && diaSPD.getCantidadDia()<=0) 
	         			helper.complementaDiaSPD(rs, diaSPD);

	        	
	        	    BolsaSPD bolsaSPD = null;
	             	keyBolsaSPD = keyDiaSPD + "_" +  rs.getString("idFreeInformation_Birtual"); //contiene una bolsa de la producción. Ponemos también freeInformation 
	             	//	System.out.println(HelperSPD.dameFechaHora() + " findLiteByResidenciaCarga --> keyBolsaSPD " + keyBolsaSPD);	
	             	//	System.out.println(HelperSPD.dameFechaHora() + " findLiteByResidenciaCarga --> keyBolsaSPD " + tm_BolsaSPD);	
	               	if (tm_BolsaSPD.containsKey(keyBolsaSPD)) 
	 		      	{
	            		bolsaSPD = tm_BolsaSPD.get(keyBolsaSPD);
	            		//	System.out.println(HelperSPD.dameFechaHora() + " findLiteByResidenciaCarga if --> bolsaSPD " + bolsaSPD.getIdBolsa());	
	 		      	}
	            	else
	            	{
	            		bolsaSPD = helper.creaBolsaSPD(rs);
	            		//	System.out.println(HelperSPD.dameFechaHora() + " findLiteByResidenciaCarga else --> bolsaSPD " + bolsaSPD.getIdBolsa());	
	               		try
	               		{
	               			diaSPD.getBolsaSPD().add(bolsaSPD);
	                		tm_BolsaSPD.put(keyBolsaSPD, bolsaSPD);
	               		}catch(Exception e){
	               			
	               		}
	            	} 
	               	//	System.out.println(HelperSPD.dameFechaHora() + " findLiteByResidenciaCarga bolsaSPD OK");	

	            	LineaBolsaSPD linea = helper.creaLineaBolsaSPD(rs);
	            	//	System.out.println(HelperSPD.dameFechaHora() + " findLiteByResidenciaCarga linea");	
	            	linea.setMedicamentoPaciente(tratamiento.getMedicamentoRobot());
	            	bolsaSPD.getLineasBolsa().add(linea);
	            
	            }
	        }
	        catch (SQLException e) {
	            e.printStackTrace();
	            return producciones;
	        }
	        finally {
	            con.close();
	        }
	        con.close();
	        return producciones;
	    }
	 
	   

	public AspectoMedicamento buscarIdentificacion(String codigo) throws SQLException, ClassNotFoundException 
	{
		AspectoMedicamento  medic = null;
		Connection con = Conexion.conectar();
		String qry = " SELECT  *  ";
		qry+= " FROM bd_consejo_aspecto  ";
		qry+= " WHERE CODIGO = '" + codigo+ "'";
		
		System.out.println(className + "--> buscarIdentificacion" +qry );		
		try {
			ResultSet rs = null;
			PreparedStatement pstat = con.prepareStatement(qry);
			rs = pstat.executeQuery();
			while (rs.next()) {
				medic = helper.creaIdentificacion(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	return medic;
	}


  


    
    
}
package lopicost.spd.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;

import lopicost.config.pool.dbaccess.Conexion;
import lopicost.spd.model.BdConsejo;
import lopicost.spd.robot.bean.rd.BolsaSPD;
import lopicost.spd.robot.bean.rd.DiaSPD;
import lopicost.spd.robot.bean.rd.DiaTomas;
import lopicost.spd.robot.bean.rd.LineaBolsaSPD;
import lopicost.spd.robot.bean.rd.MedicamentoDispensado;
import lopicost.spd.robot.bean.rd.MedicamentoPaciente;
import lopicost.spd.robot.bean.rd.ProduccionPaciente;
import lopicost.spd.robot.bean.rd.Toma;
import lopicost.spd.robot.bean.rd.TratamientoPaciente;
import lopicost.spd.robot.helper.InformeProdHelper;
import lopicost.spd.struts.bean.CabecerasXLSBean;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.struts.bean.PacienteBean;
import lopicost.spd.utils.SPDConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class InformeProdSpdDAO
{
    static String className;
	static InformeProdHelper helper = new InformeProdHelper();
	   	
	   	
    static {
        InformeProdSpdDAO.className = "InformeProdSPDDAO";
    }
    
	/**

     */
    public static List<ProduccionPaciente> findLiteByResidenciaCarga(String spdUsuario, FicheroResiBean cab) throws Exception {
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
	    
	    //String keyTomas="";
	   	PacienteBean paciente = null; 
	   	ProduccionPaciente produccionPaciente = new ProduccionPaciente();
	   	//List<Menu> result = new ArrayList<Menu>();
	    //Menu menu = new Menu();
	   	String fechaInicioSPD = cab.getFechaDesde();

		String qry = " SELECT * FROM ( ";
			  qry +=  " SELECT ";
			  qry +=  "		d.fechaHoraProceso, d.idProceso,  ";
			  qry +=  " 	COALESCE(d.idDivisionResidencia, r.idDivisionResidencia) as idDivisionResidencia, ";
			  qry +=  " 	COALESCE(d.CIP, r.CIP) as CIP, ";
			  qry +=  " 	COALESCE(d.nombreMedicamento, r.nombreMedicamentoBolsa) as nombreMedicamento, ";
			  qry +=  " 	COALESCE(d.cantidadToma, r.cantidad) as cantidad, ";
			  qry +=  " 	d.dispensar, ";
			  qry +=  " 	d.fechaToma,  ";
			  qry +=  " 	CONVERT(VARCHAR(10), CAST(d.fechaToma AS DATE), 103) AS fechaToma2, ";
			  qry +=  " 	d.idLineaRX, d.tramoToma, d.idToma,  ";
			  qry +=  " 	COALESCE(d.nombreToma, r.doseTime) as nombreToma, ";     
			  qry +=  " 	d.planta, d.habitacion, d.numBolsa, ";
			  qry +=  " 	COALESCE(d.idBolsa, r.idBolsa) as idBolsa, ";
			  qry +=  " 	COALESCE(d.idFreeInformation, r.freeInformation) as freeInformation, ";
			  qry +=  " 	COALESCE(d.CN, r.CN) as CN, ";
			  qry +=  " 	d.idDetalle,  ";
			  qry +=  " 	COALESCE(d.orderNumber, r.prescriptionOrderNumber) as prescriptionOrderNumber, ";
			  qry +=  " 	COALESCE(d.CodGtVm, r.CodGtVm) as CodGtVm, ";
			  qry +=  " 	COALESCE(d.NomGtVm, r.NomGtVm) as NomGtVm, ";
			  qry +=  " 	COALESCE(d.CodGtVmp, r.CodGtVmp) as CodGtVmp, ";
			  qry +=  " 	COALESCE(d.NomGtVmp, r.NomGtVmp) as NomGtVmp, ";
			  qry +=  " 	COALESCE(d.CodGtVmpp, r.CodGtVmpp) as CodGtVmpp, ";
			  qry +=  " 	COALESCE(d.NomGtVmpp, r.NomGtVmpp) as NomGtVmpp, ";
			  qry +=  " 	d.pautaResidencia,  r.idRobot, r.idResidenciaCarga, r.diaInicioSPD,  ";
			  qry +=  " 	r.diaDesemblistado, r.diaEmbolsado, r.horaEmbolsado, r.totalBolsas, r.numeroOrdenBolsa, ";
			  qry +=  " 	r.primerIdBolsaSPD, r.ultimoIdBolsaSPD, r.lote, r.caducidad, r.codigoBarras,  ";
			  qry +=  " 	r.codigoMedicamentoRobot, r.offsetDays, r.numeroTolva, r.fechaInsert, ";
			  qry +=  " 	d.nombreLaboratorio, d.nombreMedicamentoConsejo, r.numeroSerie, "; 
			  qry +=  " 	'MATCH CodGtVmp' AS tipoMatch ";
			  qry +=  " FROM SPD_XML_detallesTomasRobot d ";
			  qry +=  " LEFT JOIN SPD_robotProducciones r ";
			  qry +=  " 	ON d.idFreeInformation = r.FreeInformation ";
			  qry +=  " 	AND d.idDivisionResidencia = r.idDivisionResidencia ";
			  qry +=  " 	AND d.CodGtVmp = r.CodGtVmp ";
			  qry +=  " WHERE d.idProceso = '"+ cab.getIdProceso() +"' ";
			  //qry +=  " AND COALESCE(d.CIP, r.CIP) = 'ARNI1320113002' ";
			  qry +=  " ";
			  qry +=  " UNION ALL ";
			  qry +=  " ";
			  qry +=  " SELECT "; 
			  qry +=  " 	d2.fechaHoraProceso, d2.idProceso, "; 
			  qry +=  " 	COALESCE(d2.idDivisionResidencia, r2.idDivisionResidencia) as idDivisionResidencia, ";
			  qry +=  " 	COALESCE(d2.CIP, r2.CIP) as CIP, ";
			  qry +=  " 	COALESCE(d2.nombreMedicamento, r2.nombreMedicamentoBolsa) as nombreMedicamento, ";
			  qry +=  " 	COALESCE(d2.cantidadToma, r2.cantidad) as cantidad, ";
			  qry +=  " 	d2.dispensar, ";
			  qry +=  " 	d2.fechaToma, ";
			  qry +=  " 	CONVERT(VARCHAR(10), CAST(d2.fechaToma AS DATE), 103) AS fechaToma2, ";
			  qry +=  " 	d2.idLineaRX, d2.tramoToma, d2.idToma,  ";
			  qry +=  " 	COALESCE(d2.nombreToma, r2.doseTime) as nombreToma, ";     
			  qry +=  " 	d2.planta, d2.habitacion, d2.numBolsa, ";
			  qry +=  " 	COALESCE(d2.idBolsa, r2.idBolsa) as idBolsa, ";
			  qry +=  " 	COALESCE(d2.idFreeInformation, r2.freeInformation) as freeInformation, ";
			  qry +=  " 	COALESCE(d2.CN, r2.CN) as CN, ";
			  qry +=  " 	d2.idDetalle, ";
			  qry +=  " 	COALESCE(d2.orderNumber, r2.prescriptionOrderNumber) as prescriptionOrderNumber, ";
			  qry +=  " 	COALESCE(d2.CodGtVm, r2.CodGtVm) as CodGtVm, ";
			  qry +=  " 	COALESCE(d2.NomGtVm, r2.NomGtVm) as NomGtVm, ";
			  qry +=  " 	COALESCE(d2.CodGtVmp, r2.CodGtVmp) as CodGtVmp, ";
			  qry +=  " 	COALESCE(d2.NomGtVmp, r2.NomGtVmp) as NomGtVmp, ";
			  qry +=  " 	COALESCE(d2.CodGtVmpp, r2.CodGtVmpp) as CodGtVmpp, ";
			  qry +=  " 	COALESCE(d2.NomGtVmpp, r2.NomGtVmpp) as NomGtVmpp, ";
			  qry +=  " 	d2.pautaResidencia, r2.idRobot, r2.idResidenciaCarga, r2.diaInicioSPD, "; 
			  qry +=  " 	r2.diaDesemblistado, r2.diaEmbolsado, r2.horaEmbolsado, r2.totalBolsas, r2.numeroOrdenBolsa, ";
			  qry +=  " 	r2.primerIdBolsaSPD, r2.ultimoIdBolsaSPD, r2.lote, r2.caducidad, r2.codigoBarras,  ";
			  qry +=  " 	r2.codigoMedicamentoRobot, r2.offsetDays,  r2.numeroTolva, r2.fechaInsert, ";
			  qry +=  " 	d2.nombreLaboratorio, d2.nombreMedicamentoConsejo, r2.numeroSerie,  "; 
			  qry +=  " 	'MATCH CodGtVm' AS tipoMatch ";
			  qry +=  " FROM SPD_robotProducciones r2 ";
			  qry +=  " LEFT JOIN SPD_XML_detallesTomasRobot d2 ";
			  qry +=  " 	ON d2.idFreeInformation = r2.FreeInformation ";
			  qry +=  " 	AND d2.idDivisionResidencia = r2.idDivisionResidencia ";
			  qry +=  " 	AND d2.CodGtVm = r2.CodGtVm ";
			  qry +=  " WHERE r2.idResidenciaCarga = '"+cab.getNombreProduccionRobot()+"' ";
			  qry +=  " 	AND NOT EXISTS ( ";
			  qry +=  " 	SELECT 1 ";
			  qry +=  " 	FROM SPD_XML_detallesTomasRobot d3 ";
			  qry +=  " 	WHERE d3.idFreeInformation = r2.FreeInformation ";
			  qry +=  "     	AND d3.idDivisionResidencia = r2.idDivisionResidencia ";
			  qry +=  "     	AND d3.CodGtVmp = r2.CodGtVmp ";
			  qry +=  " 		AND d3.idProceso = '"+ cab.getIdProceso() +"' ";
			  qry +=  " 	) ";
			  //qry +=  " AND COALESCE(d2.CIP, r2.CIP) = 'ARNI1320113002' ";
			  
			  qry +=  " ) AS combinado ";
			  qry +=  " WHERE NOT (dispensar = 'S' AND idRobot IS NULL) ";
			  qry +=  " ORDER BY CIP, fechaToma, idToma, dispensar; ";
		    
        Connection con = Conexion.conectar();
        System.out.println(String.valueOf(InformeProdSpdDAO.className) + "--> findLiteByResidenciaCarga  -->" + qry);
        ResultSet rs = null;
        try {
            PreparedStatement pstat = con.prepareStatement(qry);
            rs = pstat.executeQuery();

            while (rs.next()) {
            	if(helper.descartable(rs)) continue; //descartamos. CASO 2 
 
            	// Control de Paciente 
            	keyCIP=rs.getString("CIP");
            	//if(keyCIP.equalsIgnoreCase("JAGI1340920005")) //para pruebas
            		//continue;
            		//System.out.println(keyCIP);
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
            		paciente.setDispensacionesReceta(buscarDispensacionesReceta(paciente.getCIP(), SPDConstants.CUANTAS_DISPENSACIONES));
           		
                	tm_CIPS.put(keyCIP, paciente);
            	}
            	// Control del tratamiento de la medicación
        	    TratamientoPaciente tratamiento = null;
        	    //MedicamentoPaciente medic = new MedicamentoPaciente();
            	keyTratamiento = keyCIP + "_" +  rs.getString("CN") + "_" +  rs.getString("lote");
            	if (tm_Tratamientos.containsKey(keyTratamiento)) 
 		      	{
            		tratamiento = tm_Tratamientos.get(keyTratamiento);
            		tratamiento.setCantidadUtilizadaSPD(tratamiento.getCantidadUtilizadaSPD() + rs.getDouble("cantidad") ); //actualizamos cantidad total
 		      	}
            	else
            	{
                	tratamiento = helper.creaTratamientoPaciente(rs);
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
             		diaTomas.setCantidadDia(diaTomas.getCantidadDia() + rs.getInt("cantidad")); //añadimos cantidad global
 		      	}
            	else
            	{
            		diaTomas = helper.creaDiaTomas(rs);
            		helper.insertarEnPosicion(tratamiento.getMedicamentoPaciente().getDiaTomas(), rs.getInt("offsetDays"), diaTomas); 	//añadimos el día de tomas en el tratamiento/medicación 
            		tm_DiasTomas.put(keyDiaTomas, diaTomas);
            	}
            	int numTomas= tratamiento.getMedicamentoPaciente().getDiaTomas().size();
        	    Toma toma = helper.creaToma(rs, numTomas++);
            	diaTomas.getTomas().add(toma);
            	
             	// Control del día SPD con todos los CN. se añade en producción, para el report 2 de detalle bolsas  
        	    DiaSPD diaSPD = null;
        	    keyDiaSPD =  keyCIP + "_" + rs.getInt("offsetDays") ; //contiene todas las bolsas de la producción

        	    diaSPD = tm_DiasSPD.get(keyDiaSPD);
         		if(diaSPD.getCantidadDia()<=0) 
         			helper.complementaDiaSPD(rs, diaSPD);

        	
        	    BolsaSPD bolsaSPD = null;
             	keyBolsaSPD = keyDiaSPD + "_" +  rs.getString("freeInformation"); //contiene una bolsa de la producción. Ponemos también freeInformation 
            	if (tm_BolsaSPD.containsKey(keyBolsaSPD)) 
 		      	{
            		bolsaSPD = tm_BolsaSPD.get(keyBolsaSPD);
 		      	}
            	else
            	{
            		bolsaSPD = helper.creaBolsaSPD(rs);
               		diaSPD.getBolsaSPD().add(bolsaSPD);
            		tm_BolsaSPD.put(keyBolsaSPD, bolsaSPD);
            	}
            	LineaBolsaSPD linea = helper.creaLineaBolsaSPD(rs);
            	linea.setMedicamentoPaciente(tratamiento.getMedicamentoPaciente());
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
 
    
	private static List<MedicamentoDispensado> buscarDispensacionesReceta(String CIP, int cuantos)throws SQLException, ClassNotFoundException 
	{
		List<MedicamentoDispensado>  result = new ArrayList();
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
				MedicamentoDispensado medic = helper.creaMedicamentoDispensado(rs);
				result.add(medic);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	return result;
	}
 

	/**
	 * Total
	 * @param spdUsuario
	 * @param cab
	 * @return
	 * @throws Exception
	 */
    public static List<ProduccionPaciente> findByIdResidenciaCarga(String spdUsuario, FicheroResiBean cab) throws Exception {
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
	    
	    //String keyTomas="";
	   	PacienteBean paciente = null; 
	   	ProduccionPaciente produccionPaciente = new ProduccionPaciente();
	   	//List<Menu> result = new ArrayList<Menu>();
	    //Menu menu = new Menu();
	   	String fechaInicioSPD = cab.getFechaDesde();

		String qry = " SELECT * FROM ( ";
			  qry +=  " SELECT ";
			  qry +=  "		d.fechaHoraProceso, d.idProceso,  ";
			  qry +=  " 	COALESCE(d.idDivisionResidencia, r.idDivisionResidencia) as idDivisionResidencia, ";
			  qry +=  " 	COALESCE(d.CIP, r.CIP) as CIP, ";
			  qry +=  " 	COALESCE(d.nombreMedicamento, r.nombreMedicamentoBolsa) as nombreMedicamento, ";
			  qry +=  " 	COALESCE(d.cantidadToma, r.cantidad) as cantidad, ";
			  qry +=  " 	d.dispensar, ";
			  qry +=  " 	d.fechaToma,  ";
			  qry +=  " 	CONVERT(VARCHAR(10), CAST(d.fechaToma AS DATE), 103) AS fechaToma2, ";
			  qry +=  " 	d.idLineaRX, d.tramoToma, d.idToma,  ";
			  qry +=  " 	COALESCE(d.nombreToma, r.doseTime) as nombreToma, ";     
			  qry +=  " 	d.planta, d.habitacion, d.numBolsa, ";
			  qry +=  " 	COALESCE(d.idBolsa, r.idBolsa) as idBolsa, ";
			  qry +=  " 	COALESCE(d.idFreeInformation, r.freeInformation) as freeInformation, ";
			  qry +=  " 	COALESCE(d.CN, r.CN) as CN, ";
			  qry +=  " 	d.idDetalle,  ";
			  qry +=  " 	COALESCE(d.orderNumber, r.prescriptionOrderNumber) as prescriptionOrderNumber, ";
			  qry +=  " 	COALESCE(d.CodGtVm, r.CodGtVm) as CodGtVm, ";
			  qry +=  " 	COALESCE(d.NomGtVm, r.NomGtVm) as NomGtVm, ";
			  qry +=  " 	COALESCE(d.CodGtVmp, r.CodGtVmp) as CodGtVmp, ";
			  qry +=  " 	COALESCE(d.NomGtVmp, r.NomGtVmp) as NomGtVmp, ";
			  qry +=  " 	COALESCE(d.CodGtVmpp, r.CodGtVmpp) as CodGtVmpp, ";
			  qry +=  " 	COALESCE(d.NomGtVmpp, r.NomGtVmpp) as NomGtVmpp, ";
			  qry +=  " 	d.pautaResidencia,  r.idRobot, r.idResidenciaCarga, r.diaInicioSPD,  ";
			  qry +=  " 	r.diaDesemblistado, r.diaEmbolsado, r.horaEmbolsado, r.totalBolsas, r.numeroOrdenBolsa, ";
			  qry +=  " 	r.primerIdBolsaSPD, r.ultimoIdBolsaSPD, r.lote, r.caducidad, r.codigoBarras,  ";
			  qry +=  " 	r.codigoMedicamentoRobot, r.offsetDays, r.numeroTolva, r.fechaInsert, ";
			  qry +=  " 	d.nombreLaboratorio, d.nombreMedicamentoConsejo, "; 
			  qry +=  " 	'MATCH CodGtVmp' AS tipoMatch ";
			  qry +=  " FROM SPD_XML_detallesTomasRobot d ";
			  qry +=  " LEFT JOIN SPD_robotProducciones r ";
			  qry +=  " 	ON d.idFreeInformation = r.FreeInformation ";
			  qry +=  " 	AND d.idDivisionResidencia = r.idDivisionResidencia ";
			  qry +=  " 	AND d.CodGtVmp = r.CodGtVmp ";
			  qry +=  " WHERE d.idProceso = '"+ cab.getIdProceso() +"' ";
			  qry +=  " ";
			  qry +=  " UNION ALL ";
			  qry +=  " ";
			  qry +=  " SELECT "; 
			  qry +=  " 	d2.fechaHoraProceso, d2.idProceso, "; 
			  qry +=  " 	COALESCE(d2.idDivisionResidencia, r2.idDivisionResidencia) as idDivisionResidencia, ";
			  qry +=  " 	COALESCE(d2.CIP, r2.CIP) as CIP, ";
			  qry +=  " 	COALESCE(d2.nombreMedicamento, r2.nombreMedicamentoBolsa) as nombreMedicamento, ";
			  qry +=  " 	COALESCE(d2.cantidadToma, r2.cantidad) as cantidad, ";
			  qry +=  " 	d2.dispensar, ";
			  qry +=  " 	d2.fechaToma, ";
			  qry +=  " 	CONVERT(VARCHAR(10), CAST(d2.fechaToma AS DATE), 103) AS fechaToma2, ";
			  qry +=  " 	d2.idLineaRX, d2.tramoToma, d2.idToma,  ";
			  qry +=  " 	COALESCE(d2.nombreToma, r2.doseTime) as nombreToma, ";     
			  qry +=  " 	d2.planta, d2.habitacion, d2.numBolsa, ";
			  qry +=  " 	COALESCE(d2.idBolsa, r2.idBolsa) as idBolsa, ";
			  qry +=  " 	COALESCE(d2.idFreeInformation, r2.freeInformation) as freeInformation, ";
			  qry +=  " 	COALESCE(d2.CN, r2.CN) as CN, ";
			  qry +=  " 	d2.idDetalle, ";
			  qry +=  " 	COALESCE(d2.orderNumber, r2.prescriptionOrderNumber) as prescriptionOrderNumber, ";
			  qry +=  " 	COALESCE(d2.CodGtVm, r2.CodGtVm) as CodGtVm, ";
			  qry +=  " 	COALESCE(d2.NomGtVm, r2.NomGtVm) as NomGtVm, ";
			  qry +=  " 	COALESCE(d2.CodGtVmp, r2.CodGtVmp) as CodGtVmp, ";
			  qry +=  " 	COALESCE(d2.NomGtVmp, r2.NomGtVmp) as NomGtVmp, ";
			  qry +=  " 	COALESCE(d2.CodGtVmpp, r2.CodGtVmpp) as CodGtVmpp, ";
			  qry +=  " 	COALESCE(d2.NomGtVmpp, r2.NomGtVmpp) as NomGtVmpp, ";
			  qry +=  " 	d2.pautaResidencia, r2.idRobot, r2.idResidenciaCarga, r2.diaInicioSPD, "; 
			  qry +=  " 	r2.diaDesemblistado, r2.diaEmbolsado, r2.horaEmbolsado, r2.totalBolsas, r2.numeroOrdenBolsa, ";
			  qry +=  " 	r2.primerIdBolsaSPD, r2.ultimoIdBolsaSPD, r2.lote, r2.caducidad, r2.codigoBarras,  ";
			  qry +=  " 	r2.codigoMedicamentoRobot, r2.offsetDays,  r2.numeroTolva, r2.fechaInsert,  ";
			  qry +=  " 	d2.nombreLaboratorio, d2.nombreMedicamentoConsejo, "; 
			  qry +=  " 	'MATCH CodGtVm' AS tipoMatch ";
			  qry +=  " FROM SPD_robotProducciones r2 ";
			  qry +=  " LEFT JOIN SPD_XML_detallesTomasRobot d2 ";
			  qry +=  " 	ON d2.idFreeInformation = r2.FreeInformation ";
			  qry +=  " 	AND d2.idDivisionResidencia = r2.idDivisionResidencia ";
			  qry +=  " 	AND d2.CodGtVm = r2.CodGtVm ";
			  qry +=  " WHERE r2.idResidenciaCarga = '"+cab.getNombreProduccionRobot()+"' ";
			  qry +=  " 	AND NOT EXISTS ( ";
			  qry +=  " 	SELECT 1 ";
			  qry +=  " 	FROM SPD_XML_detallesTomasRobot d3 ";
			  qry +=  " 	WHERE d3.idFreeInformation = r2.FreeInformation ";
			  qry +=  "     	AND d3.idDivisionResidencia = r2.idDivisionResidencia ";
			  qry +=  "     	AND d3.CodGtVmp = r2.CodGtVmp ";
			  qry +=  " 		AND d3.idProceso = '"+ cab.getIdProceso() +"' ";
			  qry +=  " 	) ";
			  qry +=  " ) AS combinado ";
			  qry +=  " WHERE NOT (dispensar = 'S' AND idRobot IS NULL) ";
			  qry +=  " ORDER BY CIP, fechaToma, idToma, dispensar; ";
		    
        Connection con = Conexion.conectar();
        System.out.println(String.valueOf(InformeProdSpdDAO.className) + "--> findLiteByResidenciaCarga  -->" + qry);
        ResultSet rs = null;
        try {
            PreparedStatement pstat = con.prepareStatement(qry);
            rs = pstat.executeQuery();

            while (rs.next()) {
            	if(helper.descartable(rs)) continue; //descartamos. CASO 2 
 
            	// Control de Paciente 
            	keyCIP=rs.getString("CIP");
            	//if(keyCIP.equalsIgnoreCase("JAGI1340920005")) //para pruebas
            		//continue;
            		//System.out.println(keyCIP);
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

            		
                	tm_CIPS.put(keyCIP, paciente);
            	}
            	// Control del tratamiento de la medicación
        	    TratamientoPaciente tratamiento = null;
        	    //MedicamentoPaciente medic = new MedicamentoPaciente();
            	keyTratamiento = keyCIP + "_" +  rs.getString("CN") + "_" +  rs.getString("lote");
            	if (tm_Tratamientos.containsKey(keyTratamiento)) 
 		      	{
            		tratamiento = tm_Tratamientos.get(keyTratamiento);
            		tratamiento.setCantidadUtilizadaSPD(tratamiento.getCantidadUtilizadaSPD() + rs.getInt("cantidad") ); //actualizamos cantidad total
 		      	}
            	else
            	{
                	tratamiento = helper.creaTratamientoPaciente(rs);
                	produccionPaciente.getTratamientosPaciente().add(tratamiento);
                	tm_Tratamientos.put(keyTratamiento, tratamiento);
            	}
 
        	    DiaTomas diaTomas = null;
            	keyDiaTomas =  keyTratamiento + "_" + rs.getInt("offsetDays"); //contiene el CN y sus días 
            	if (tm_DiasTomas.containsKey(keyDiaTomas)) 
 		      	{
             		diaTomas = tm_DiasTomas.get(keyDiaTomas);
             		diaTomas.setCantidadDia(diaTomas.getCantidadDia() + rs.getInt("cantidad")); //añadimos cantidad global
 		      	}
            	else
            	{
            		diaTomas = helper.creaDiaTomas(rs);
            		helper.insertarEnPosicion(tratamiento.getMedicamentoPaciente().getDiaTomas(), rs.getInt("offsetDays"), diaTomas); 	//añadimos el día de tomas en el tratamiento/medicación 
            		tm_DiasTomas.put(keyDiaTomas, diaTomas);
            	}
            	int numTomas= tratamiento.getMedicamentoPaciente().getDiaTomas().size();
        	    Toma toma = helper.creaToma(rs, numTomas++);
            	diaTomas.getTomas().add(toma);
            	
             	// Control del día SPD con todos los CN. se añade en producción, para el report 2 de detalle bolsas  
        	    DiaSPD diaSPD = null;
        	    keyDiaSPD =  keyCIP + "_" + rs.getInt("offsetDays") ; //contiene todas las bolsas de la producción

        	    diaSPD = tm_DiasSPD.get(keyDiaSPD);
         		if(diaSPD.getCantidadDia()<=0) 
         			helper.complementaDiaSPD(rs, diaSPD);

        	    /*
        	    if (tm_DiasSPD.containsKey(keyDiaSPD)) 
 		      	{
             		diaSPD = tm_DiasSPD.get(keyDiaSPD);
             		if(diaSPD.getCantidadDia()<=0) 
             			helper.complementaDiaSPD(rs, diaSPD);
             			
 		      	}
            	else
            	{
            		diaSPD = helper.creaDiaSPD(rs);
            		//produccionPaciente.getDiasSPD().add(diaSPD);
            		tm_DiasSPD.put(keyDiaSPD, diaSPD);
            	}
             	*/
        	    BolsaSPD bolsaSPD = null;
             	keyBolsaSPD = keyDiaSPD + "_" +  rs.getString("freeInformation"); //contiene una bolsa de la producción. Ponemos también freeInformation 
            	if (tm_BolsaSPD.containsKey(keyBolsaSPD)) 
 		      	{
            		bolsaSPD = tm_BolsaSPD.get(keyBolsaSPD);
 		      	}
            	else
            	{
            		bolsaSPD = helper.creaBolsaSPD(rs);
               		diaSPD.getBolsaSPD().add(bolsaSPD);
            		tm_BolsaSPD.put(keyBolsaSPD, bolsaSPD);
            	}
            	LineaBolsaSPD linea = helper.creaLineaBolsaSPD(rs);
            	linea.setMedicamentoPaciente(tratamiento.getMedicamentoPaciente());
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

  


    
    
}
package lopicost.spd.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;

import lopicost.config.pool.dbaccess.Conexion;
import lopicost.spd.robot.bean.rd.BolsaSPD;
import lopicost.spd.robot.bean.rd.DiaSPD;
import lopicost.spd.robot.bean.rd.DiaTomas;
import lopicost.spd.robot.bean.rd.LineaBolsaSPD;
import lopicost.spd.robot.bean.rd.ProduccionPaciente;
import lopicost.spd.robot.bean.rd.Toma;
import lopicost.spd.robot.bean.rd.TratamientoPaciente;
import lopicost.spd.robot.helper.InformeHelper;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.struts.bean.PacienteBean;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class InformeSpdDAO2
{
    static String className;
	static InformeHelper helper = new InformeHelper();
	   	
	   	
    static {
        InformeSpdDAO2.className = "InformeSPDDAO";
    }
    
    public static List<ProduccionPaciente> findByIdResidenciaCarga(String spdUsuario, FicheroResiBean cab) throws Exception {
    	List<ProduccionPaciente> producciones = new ArrayList<ProduccionPaciente>();
    	
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
        String qryOld = " SELECT distinct    ";
        qryOld +=  " r.numeroOrdenBolsa, r.CIP, r.prescriptionOrderNumber, r.idBolsa, r.CN, r.nombreMedicamentoBolsa, ";
        qryOld +=  " (bc.NOMBRE + ' ' + bc.PRESENTACION) as nombreMedicamento, r.diaEmbolsado, r.horaEmbolsado,  r.diaDesemblistado,  r.diaInicioSPD,   ";
        qryOld +=  " r.lote, r.caducidad, r.cantidad, r.codigoMedicamentoRobot, r.codigoBarras, r.freeInformation, r.offsetDays,  ";
        qryOld +=  " r.doseTime, r.numeroTolva ";
        qryOld +=  " FROM SPD_robotProducciones r  ";
        qryOld +=  " LEFT JOIN bd_consejo bc  ON bc.CODIGO = r.CN ";
        qryOld +=  " WHERE 1 = 1 ";
        qryOld +=  " AND (r.idResidenciaCarga = '"+cab.getNombreProduccionRobot()+"')  ";
        qryOld +=  " ORDER BY  r.CIP, r.offsetDays,   r.numeroOrdenBolsa , r.CN, r.lote, r.caducidad";
             
        String qryOld2 = " SELECT distinct d.*, r.* ";
        qryOld2 += " FROM SPD_XML_detallesTomasRobot d ";
        qryOld2 += " LEFT JOIN SPD_robotProducciones r on d.idFreeInformation = r.FreeInformation    and d.CodGtVmp=r.CodGtVmp ";
        qryOld2 += " WHERE 1 = 1 ";
        qryOld2 += " and (d.idProceso ='"+ cab.getIdProceso() +"') ";
        qryOld2 += " ORDER BY  d.CIP, d.fechaToma,  d.idToma, d.dispensar ";

		
		String qry = " SELECT CONVERT(varchar(10), fechaDiaBolsaReal, 103) AS diaBolsa, ";
		qry +=  " fechaHoraProceso, idProceso, idDivisionResidencia, CIP, ";
		qry +=  " orderNumber, CN, nombreMedicamento, cantidadToma, dispensar, ";
		qry +=  " fechaToma, idLineaRX, tramoToma, idToma, nombreToma, planta, ";
		qry +=  " habitacion, numBolsa,  freeInformation, idDetalle, ";
		qry +=  " CodGtVm, NomGtVm, CodGtVmpp, NomGtVmpp, CodGtVmp, NomGtVmp, ";
		qry +=  " idRobot, idResidenciaCarga, diaInicioSPD, diaDesemblistado, ";
		qry +=  " diaEmbolsado, horaEmbolsado, totalBolsas, numeroOrdenBolsa, ";
		qry +=  " primerIdBolsaSPD, ultimoIdBolsaSPD, idBolsaD, idBolsaR, ";
		qry +=  " cantidad, lote, caducidad, codigoBarras, codigoMedicamentoRobot,  ";
		qry +=  " offsetDays, numeroTolva, fechaInsert ";
		qry +=  " FROM ( ";
		qry +=  "   SELECT d.fechaHoraProceso, d.idProceso, ";
		qry +=  " 	COALESCE(d.idDivisionResidencia, r.idDivisionResidencia) as idDivisionResidencia, ";
		qry +=  " 	COALESCE(d.CIP, r.CIP) as CIP, ";
		qry +=  " 	COALESCE(d.orderNumber, r.prescriptionOrderNumber) as orderNumber, ";
		qry +=  " 	COALESCE(d.CN, r.CN) as CN, ";
		qry +=  " 	COALESCE(d.nombreMedicamento, r.nombreMedicamentoBolsa) as nombreMedicamento, ";
		qry +=  " 	d.cantidadToma, d.dispensar, d.fechaToma, d.idLineaRX, d.tramoToma, d.idToma, ";
		qry +=  " 	COALESCE(d.nombreToma, r.doseTime) as nombreToma, ";
		qry +=  " 	d.planta, d.habitacion, d.numBolsa, d.idBolsa as idBolsaD, ";
		qry +=  " 	COALESCE(d.idFreeInformation, r.freeInformation) as freeInformation, d.idDetalle, ";
		qry +=  " 	COALESCE(d.CodGtVm, r.CodGtVm) CodGtVm, ";
		qry +=  " 	COALESCE(d.NomGtVm, r.NomGtVm) NomGtVm, ";
		qry +=  " 	COALESCE(d.CodGtVmp, r.CodGtVmpp) CodGtVmpp, ";
		qry +=  " 	COALESCE(d.NomGtVmp, r.NomGtVmpp) NomGtVmpp, ";
		qry +=  " 	COALESCE(d.CodGtVmpp, r.CodGtVmp) CodGtVmp, ";
		qry +=  " 	COALESCE(d.NomGtVmpp, r.NomGtVmp) NomGtVmp, ";
		qry +=  " 	r.idRobot, r.idResidenciaCarga, r.diaInicioSPD, r.diaDesemblistado, r.diaEmbolsado,  ";
		qry +=  " 	r.horaEmbolsado, r.totalBolsas, r.numeroOrdenBolsa, r.primerIdBolsaSPD, r.ultimoIdBolsaSPD, ";
		qry +=  " 	r.idBolsa as idBolsaR, r.cantidad, r.lote, r.caducidad, r.codigoBarras, ";
		qry +=  " 	r.codigoMedicamentoRobot, r.offsetDays, r.numeroTolva, r.fechaInsert, ";
		qry +=  " 	    -- cálculo de la fecha de la bolsa real ";
		qry +=  " 	    CASE  ";
		qry +=  " 	      WHEN r.offsetDays IS NOT NULL  ";
		qry +=  " 	           AND ISDATE(CONVERT(varchar(8), r.diaInicioSPD)) = 1 THEN  ";
		qry +=  " 	          DATEADD(DAY, r.offsetDays, CAST(CONVERT(varchar(8), r.diaInicioSPD) AS date)) ";
		qry +=  " 	      WHEN d.fechaToma IS NOT NULL THEN  ";
		qry +=  " 	          CAST(d.fechaToma AS date) ";
		qry +=  " 	      ELSE NULL ";
		qry +=  " 	    END AS fechaDiaBolsaReal ";
		qry +=  " 	  FROM SPD_XML_detallesTomasRobot d ";
		qry +=  " 	  FULL OUTER JOIN SPD_robotProducciones r ";
		qry +=  " 	    ON d.idFreeInformation = r.FreeInformation ";
		qry +=  " 	    AND d.idDivisionResidencia = r.idDivisionResidencia ";
		qry +=  " 	    AND d.CodGtVmp = r.CodGtVmp ";
		qry +=  " 	  WHERE  ";
		qry +=  " 	    (d.idProceso = '"+ cab.getIdProceso() +"') ";
		qry +=  " 	    OR  ";
		qry +=  " 	    (r.idResidenciaCarga = '"+cab.getNombreProduccionRobot()+"' AND d.idFreeInformation IS NULL) ";
		qry +=  " 	) AS subconsulta ";
		qry +=  " 	ORDER BY CIP,  fechaDiaBolsaReal, idToma, dispensar;  ";
		        
        Connection con = Conexion.conectar();
        System.out.println(String.valueOf(InformeSpdDAO2.className) + "--> findByIdResidenciaCarga  -->" + qry);
        ResultSet rs = null;
        /* CIP - CN - TOMA - CANTIDAD*/
        try {
        	//DivisionResidencia div = DivisionResidenciaDAO.getDivisionResidenciaByOid(oidDivisionResidencia);
        	//getCabeceraByFilters(spdUsuario, form, inicio, fin, distinctCampo, total) 
        	
        	//List<CabecerasXLSBean> tomasCabecera = CabecerasXLSDAO.list(idUsuario, oidDivisionResidencia, -1);
            PreparedStatement pstat = con.prepareStatement(qry);
            rs = pstat.executeQuery();

            while (rs.next()) {
        	   	/*
        	   	keyCIP="";
        	   	keyTratamiento="";
        	    keyDiaSPD="";
        	    keyDiaTomas="";
        	    keyBolsaSPD="";
        	    */
            	// Control de Paciente 
            	keyCIP=rs.getString("CIP");
            	//if(keyCIP.equalsIgnoreCase("JAGI1340920005"))
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
            	/*
            	keyMedic = keyTto + "_" +  rs.getString("lote");
            	if (Medic_TreeMap.containsKey(keyMedic)) 
 		      	{
            		medic = Medic_TreeMap.get(keyMedic);
 		      	}
            	else
            	{
            		medic = helper.creaMedicamentoPaciente(rs);
            	}
            	*/
            	// Control del día SPD con un solo CN. Se añade en la medicación, para el report 1 global 
               	/*keyTomas =  keyCIP + rs.getString("offsetDays") + rs.getString("cn") + rs.getString("doseTime"); //contiene info de la toma
             	if (tm_DiasTomas.containsKey(keyDiaTomas)) 
 		      	{
             		toma = tm_Tomas.get(keyTomas);
 		      	}
            	else
            	{
                   	keyDiaTomas =  keyCIP + rs.getString("offsetDays") + rs.getString("cn") + rs.getString("doseTime"); //contiene solo la info del CN en el día
           		
            		diaTomas = helper.creaDiaTomas(rs);
            	}
             	*/
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
            	
            	/*
            	if(tm_DiasSPD == null || tm_DiasSPD.size()==0)
            	{
            		tm_DiasSPD = helper.crearTreemapDiasSPD(keyCIP, cab);
            		produccionPaciente.getDiasSPD().clear(); // opcional: si quieres vaciarla antes
            		produccionPaciente.getDiasSPD().addAll(tm_DiasSPD.values());
            		
            	}*/
            	
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
             	keyBolsaSPD = keyDiaSPD + "_" +  rs.getString("freeInformation"); //contiene una bolsa de la producción. Ponemos tambión freeInformation 
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
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
import lopicost.spd.struts.bean.CabecerasXLSBean;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.struts.bean.PacienteBean;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class InformeSpdDAO3
{
    static String className;
	static InformeHelper helper = new InformeHelper();
	   	
	   	
    static {
        InformeSpdDAO3.className = "InformeSPDDAO";
    }
    
	/**
	 * Metodología para procesar los datos:
	 * En esta select se recoge:
	 * - tabla D --> Tabla de generación del fichero RX
	 * - tabla R --> Tabla con los datos recogidos del robot.
	 * CASO 1 - Lo ideal es que lo enviado y recogido coincida 100% con freeInformation(localiza la bosa) enviado/recogido  y coGtVmp con lo producido (localiza el CN).
	 * CASO 2 - Es posible que se envíe un CN con codGtVmp determinado pero que se produzca uno distinto, por alguna razón de envío erróneo de un CN pero que en el robot lo produce bien.
	 * En este caso, la consulta dará dos líneas por el mismo concepto, una de lo enviado (que habría que descartar) y la otra de lo producido (correcta) pero sin información suficiente, 
	 * que deberemos localizar.
	 * CASO 3 - Lo que no se emblista no se recoge en tabla R, solo está en tabla D, por lo que habrá que tenerlo en cuenta de cara al informe.
	 * Pasos: 
	 * 	-	CASO 1 - No hay problema, se recoge toda la coincidencia, con datos tanto de D como de R
	 * 	- 	CASO 2 - Descartar los registros sin bolsa en R (idBolsaR nula) y que dispensar es 'S' (se mantienen los N)
	 * 		CASO 3 - Aceptar los que idBolsaR no es nulo y idBolsaD es nulo. Estos registros solo están en tabla R por lo tanto hay que tener en cuenta que no hay datos D
	 *				 Para estos últimos, en la select se construye un idBolsaD del tipo CIP + YYYYMMDD + [01]_[+idToma + ]S1 buscando la coincidencia de otro idBolsaD y en caso de no encontrarlo, 
	 *				 lo simula como nuevo.
	 * 	- 	CASO 4 - Aceptar los que tienen dispensar = 'N'
	 * - 	CASO 5 - Si no tiene indicado dispensar  
	 * 
     * @param spdUsuario
     * @param cab
     * @return
     * @throws Exception
     */
    public static List<ProduccionPaciente> findByIdResidenciaCarga(String spdUsuario, FicheroResiBean cab) throws Exception {
    	List<ProduccionPaciente> producciones = new ArrayList<ProduccionPaciente>();
    	
		//recuperamos las tomas
		List<CabecerasXLSBean> tomasCabecera = CabecerasXLSDAO.list(spdUsuario, cab.getOidDivisionResidencia());

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

		String qry = " SELECT ";
			  qry +=  " offsetDaysCalculado as offsetDays, ";
			  qry +=  " CONVERT(varchar(10), fechaDiaBolsaReal, 103) AS diaBolsa, fechaHoraProceso, idProceso, idDivisionResidencia, CIP, ";
			  qry +=  " orderNumber, CN, nombreMedicamento, dispensar,  cantidad_D, fechaToma, idLineaRX, tramoToma, idToma, nombreToma, planta, ";
			  qry +=  " habitacion, numBolsa, freeInformation, idDetalle,  CodGtVm, NomGtVm, CodGtVmpp, NomGtVmpp, CodGtVmp, NomGtVmp, ";
			  qry +=  " idRobot, idResidenciaCarga, diaInicioSPD, diaDesemblistado,  diaEmbolsado, horaEmbolsado, totalBolsas, numeroOrdenBolsa, ";
			  qry +=  " primerIdBolsaSPD, ultimoIdBolsaSPD, idBolsaD, idBolsaR,  cantidad_R, lote, caducidad, codigoBarras, codigoMedicamentoRobot,  ";
			  qry +=  " offsetDays, numeroTolva, fechaInsert, cantidad_OK, ";
			 // qry +=  " -- lógica para obtener el idBolsaD deseado ";
			  qry +=  " ISNULL( ";
			  qry +=  "   idBolsaD, ";
			  qry +=  "   ISNULL( ";
			  qry +=  "     bolsaEquivalente.idBolsaD_encontrado, ";
			//  qry +=  "     -- Si no se encuentra, lo construimos ";
			  qry +=  "     ISNULL(CIP, '') + ";
			  qry +=  "     ISNULL(CONVERT(varchar(8), fechaDiaBolsaReal, 112), '') + ";
			  qry +=  "     '[' + RIGHT('0' + ISNULL(CAST(idToma AS varchar), ''), 2) + ']_[' + ";
			  qry +=  "     ISNULL(nombreToma, '') + ']S1' ";
			  qry +=  "   ) ";
			  qry +=  " ) AS idBolsaD_final ";
			
			  qry +=  " FROM ( ";
			  qry +=  " 	SELECT  ";
			  qry +=  " 		ISNULL( ";
			  qry +=  " 			r.offsetDays, ";
			  qry +=  "  			CASE  ";
			  qry +=  "  			WHEN d.fechaToma IS NOT NULL THEN ";
			  qry +=  "   				DATEDIFF(DAY, CAST(CONVERT(date, '"+ fechaInicioSPD +"', 103) AS date), d.fechaToma) ";
			  qry +=  "   			ELSE NULL ";
			  qry +=  " 			END ";
			  qry +=  "  ) AS offsetDaysCalculado, ";

			  
			  qry +=  "   d.fechaHoraProceso, ";
			  qry +=  "   COALESCE(d.idProceso, '"+ cab.getIdProceso() +"') as idProceso, ";
			  qry +=  "   COALESCE(d.idDivisionResidencia, r.idDivisionResidencia) as idDivisionResidencia, ";
			  qry +=  "   COALESCE(d.CIP, r.CIP) as CIP, ";
			  qry +=  "   COALESCE(d.orderNumber, r.prescriptionOrderNumber) as orderNumber, ";
			  qry +=  "   COALESCE(d.CN, r.CN) as CN, ";
			  qry +=  "   COALESCE(d.nombreMedicamento, r.nombreMedicamentoBolsa) as nombreMedicamento, ";
			  qry +=  "   COALESCE(d.dispensar, 'S') as dispensar, ";
			  qry +=  "   d.cantidadToma as cantidad_D, d.fechaToma, d.idLineaRX, d.tramoToma, d.idToma, ";
			  qry +=  "   COALESCE(d.nombreToma, r.doseTime) as nombreToma, ";
			  qry +=  "   d.planta, d.habitacion, d.numBolsa, d.idBolsa as idBolsaD, ";
			  qry +=  "   COALESCE(d.idFreeInformation, r.freeInformation) as freeInformation, d.idDetalle, ";
			  qry +=  "   COALESCE(d.CodGtVm, r.CodGtVm) as CodGtVm, ";
			  qry +=  "   COALESCE(d.NomGtVm, r.NomGtVm) as NomGtVm, ";
			  qry +=  "   COALESCE(d.CodGtVmp, r.CodGtVmp) as CodGtVmp, ";
			  qry +=  "   COALESCE(d.NomGtVmp, r.NomGtVmp) as NomGtVmp, ";
			  qry +=  "   COALESCE(d.CodGtVmpp, r.CodGtVmpp) as CodGtVmpp, ";
			  qry +=  "   COALESCE(d.NomGtVmpp, r.NomGtVmpp) as NomGtVmpp, ";
			  qry +=  "   r.idRobot, r.idResidenciaCarga, r.diaInicioSPD, r.diaDesemblistado, r.diaEmbolsado,  ";
			  qry +=  "   r.horaEmbolsado, r.totalBolsas, r.numeroOrdenBolsa, r.primerIdBolsaSPD, r.ultimoIdBolsaSPD, ";
			  qry +=  "   r.idBolsa as idBolsaR, ";
			  qry +=  "   r.cantidad as cantidad_R, r.lote, r.caducidad, r.codigoBarras, ";
			  qry +=  "   r.codigoMedicamentoRobot, r.offsetDays, r.numeroTolva, r.fechaInsert, ";
			  qry +=  "   CASE  ";
			  qry +=  "     WHEN r.offsetDays IS NOT NULL AND ISDATE(CONVERT(varchar(8), r.diaInicioSPD)) = 1 THEN  ";
			  qry +=  "       DATEADD(DAY, r.offsetDays, CAST(CONVERT(varchar(8), r.diaInicioSPD) AS date)) ";
			  qry +=  "     WHEN d.fechaToma IS NOT NULL THEN  ";
			  qry +=  "       CAST(d.fechaToma AS date) ";
			  qry +=  "     ELSE NULL ";
			  qry +=  "   END AS fechaDiaBolsaReal, ";
			  qry +=  "   COALESCE(r.cantidad, d.cantidadToma) as cantidad_OK    ";
			  qry +=  " FROM SPD_XML_detallesTomasRobot d ";
			  qry +=  " FULL OUTER JOIN SPD_robotProducciones r ";
			  qry +=  "   ON d.idFreeInformation = r.FreeInformation ";
			  qry +=  "   AND d.idDivisionResidencia = r.idDivisionResidencia ";
			  qry +=  "   AND d.CodGtVmp = r.CodGtVmp ";
			///  qry +=  "    LEFT JOIN hst_ProductosDispensadosResis h on h.CodigoDispensado=r.CN and h.lote=r.lote and h.caducidad=r.caducidad ";     			  
			  qry +=  " WHERE  ";
			  qry +=  "   (d.idProceso = '"+ cab.getIdProceso() +"') ";
			  qry +=  "   OR  ";
			  qry +=  "   (r.idResidenciaCarga = '"+cab.getNombreProduccionRobot()+"' AND d.idFreeInformation IS NULL) ";
			  qry +=  " ) AS subconsulta ";
			  qry +=  "  ";
			//  qry +=  " --  Buscar si otro registro con el mismo idBolsaR tiene un idBolsaD conocido ";
			  qry +=  " OUTER APPLY ( ";
			  qry +=  " SELECT TOP 1 idBolsa as idBolsaD_encontrado ";
			  qry +=  " FROM SPD_XML_detallesTomasRobot d2 ";
			  qry +=  " WHERE d2.idFreeInformation = subconsulta.freeInformation ";
			  qry +=  "   AND d2.idBolsa IS NOT NULL ";
			  qry +=  "   AND d2.idProceso = '"+ cab.getIdProceso() +"' ";
			  qry +=  " ) bolsaEquivalente ";
			  qry +=  " ORDER BY CIP, idBolsaD_final, fechaDiaBolsaReal, idToma, dispensar; ";
		
		
		
        Connection con = Conexion.conectar();
        System.out.println(String.valueOf(InformeSpdDAO3.className) + "--> findByIdResidenciaCarga  -->" + qry);
        ResultSet rs = null;
        /* CIP - CN - TOMA - CANTIDAD*/
        try {
        	//DivisionResidencia div = DivisionResidenciaDAO.getDivisionResidenciaByOid(oidDivisionResidencia);
        	//getCabeceraByFilters(spdUsuario, form, inicio, fin, distinctCampo, total) 
        	
        	//List<CabecerasXLSBean> tomasCabecera = CabecerasXLSDAO.list(idUsuario, oidDivisionResidencia, -1);
            PreparedStatement pstat = con.prepareStatement(qry);
            rs = pstat.executeQuery();

            while (rs.next()) {
            	if(helper.descartable(rs)) continue; //descartamos. CASO 2 
            	
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
            		tratamiento.setCantidadUtilizadaSPD(tratamiento.getCantidadUtilizadaSPD() + rs.getInt("cantidad_OK") ); //actualizamos cantidad total
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
             		diaTomas.setCantidadDia(diaTomas.getCantidadDia() + rs.getInt("cantidad_OK")); //añadimos cantidad global
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
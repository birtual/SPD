package lopicost.spd.helper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TreeMap;

import lopicost.spd.model.BdConsejo;
import lopicost.spd.model.rd.AspectoMedicamento;
import lopicost.spd.model.rd.DiaTomas;
import lopicost.spd.model.rd.MedicamentoBirtual;
import lopicost.spd.model.rd.MedicamentoReceta;
import lopicost.spd.model.rd.MedicamentoRobot;
import lopicost.spd.model.rd.Toma;
import lopicost.spd.model.rd.TratamientoPaciente;
import lopicost.spd.model.spd.BolsaSPD;
import lopicost.spd.model.spd.DiaSPD;
import lopicost.spd.model.spd.LineaBolsaSPD;
import lopicost.spd.model.spd.ProduccionPaciente;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.struts.bean.PacienteBean;
import lopicost.spd.utils.DateUtilities;
import lopicost.spd.utils.SPDConstants;
import lopicost.spd.persistence.BdConsejoDAO;
import lopicost.spd.persistence.InformeRDDAO;
import lopicost.spd.persistence.PacienteDAO;


/**
 *Logica de negocio 
 */
public class InformeRDHelper {


	private final String cLOGGERHEADER = "InformeHelper: ";
	private final String cLOGGERHEADER_ERROR = cLOGGERHEADER + "ERROR: InformeHelper: ";
	InformeRDDAO dao =  new InformeRDDAO();
	
	public PacienteBean creaPaciente(String CIP) throws Exception {
		PacienteBean paciente =PacienteDAO.getPacientePorCIP(CIP);
		if(paciente==null)
		{
			paciente=new PacienteBean();
			paciente.setCIP(CIP);
			paciente.setNombre(paciente.getNombre());
			paciente.setApellidos(paciente.getApellidos());
		}
		return paciente;
	}

	public TratamientoPaciente creaTratamientoPaciente(ResultSet rs, boolean mezclar) throws SQLException, ClassNotFoundException {
		TratamientoPaciente tto = new TratamientoPaciente();
		tto.setMedicamentoRobot(creaMedicamentoRobot(rs));
		tto.setMedicamentoBirtual(creaMedicamentoBirtual(rs));
		
		if(mezclar) //Marco - 20250806 - Necesita que aparezca la info de receta en la dispensación (TEMPORALMENTE)
		{
			MedicamentoReceta receta = dao.buscarUltimaDispensacionReceta(rs.getString("CIP_Birtual"), rs.getString("NomGtVmp_Birtual"));
			if(receta!=null) {
				receta.setNombreMedicamentoBolsa(rs.getString("nombreMedicamento_Birtual"));
				tto.setMedicamentoReceta(receta);
			}
		}
		tto.setCantidadTotalEmblistadaSPD(rs.getFloat("cantidad_Robot"));
		tto.setPautaResidencia(rs.getString("pautaResidencia_Birtual"));
		tto.setEmblistar(rs.getString("dispensar_Birtual")!=null&&rs.getString("dispensar_Birtual").equalsIgnoreCase("S"));
		//tto.setDiaSPD(diaSPD);
		return tto;
	}
/**

 */
	
	public MedicamentoBirtual creaMedicamentoBirtual(ResultSet rs) throws SQLException, ClassNotFoundException {
		MedicamentoBirtual medic = new MedicamentoBirtual();
		medic.setCIP(rs.getString("CIP_Birtual"));
		medic.setIdProceso(rs.getString("idProceso_Birtual"));
		medic.setIdDivisionResidencia(rs.getString("idDivisionResidencia_Birtual"));
		medic.setCn(rs.getString("CN_Birtual"));
		medic.setNombreMedicamentoBolsa(rs.getString("nombreMedicamento_Birtual"));

		medic.setLab(rs.getString("nombreLab_Birtual"));
		medic.setNombreConsejoCnFinal(rs.getString("nombreConsejo_Birtual"));
		medic.setPautaResidencia(rs.getString("pautaResidencia_Birtual"));
		medic.setCodGtVm(rs.getString("CodGtVm_Birtual"));
		medic.setNomGtVm(rs.getString("NomGtVm_Birtual"));
		medic.setCodGtVmp(rs.getString("CodGtVmp_Birtual"));
		medic.setNomGtVmp(rs.getString("NomGtVmp_Birtual"));
		medic.setCodGtVmpp(rs.getString("CodGtVmpp_Birtual"));
		medic.setNomGtVmpp(rs.getString("NomGtVmpp_Birtual"));
		medic.setOrderNumber(rs.getString("orderNumber_Birtual"));
		medic.setIdDetalle(rs.getString("idDetalle_Birtual"));
		medic.setIdFreeInformation(rs.getString("idFreeInformation_Birtual"));
		medic.setNumBolsasToma(rs.getString("numBolsa_Birtual"));
		medic.setHabitacion(rs.getString("habitacion_Birtual"));
		medic.setPlanta(rs.getString("planta_Birtual"));
		medic.setDispensar(rs.getString("dispensar_Birtual"));

		medic.setAspectoMedicamento(creaIdentificacion(rs));
		
		return medic;
	}
	
	
	/**
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * 
	 */
	public MedicamentoRobot creaMedicamentoRobot(ResultSet rs) throws SQLException, ClassNotFoundException {
		MedicamentoRobot robot = new MedicamentoRobot();
		robot.setOffsetDays(rs.getInt("offsetDays"));
		robot.setIdRobot(rs.getString("idRobot_Robot"));
		robot.setIdDivisionResidencia(rs.getString("idDivisionResidencia_Robot"));
		robot.setDiaInicioSPD(rs.getString("diaInicioSPD_Robot"));
		robot.setCIP(rs.getString("CIP_Robot"));
		robot.setCn(rs.getString("CN_Robot"));
		robot.setNombreMedicamentoBolsa(rs.getString("nombreEnBolsa_Robot"));
		robot.setNombreConsejoCn(rs.getString("nombreConsejo_Robot"));
		robot.setNumeroSerie(rs.getString("numeroSerie_Robot"));
		robot.setCodGtVm(rs.getString("CodGtVm_Robot"));
		robot.setNomGtVm(rs.getString("NomGtVm_Robot"));
		robot.setCodGtVmp(rs.getString("CodGtVmp_Robot"));
		robot.setNomGtVmp(rs.getString("NomGtVmp_Robot"));
		robot.setCodGtVmpp(rs.getString("CodGtVmpp_Robot"));
		robot.setNomGtVmpp(rs.getString("NomGtVmpp_Robot"));
		robot.setLote(rs.getString("lote_Robot"));
		robot.setCaducidad(rs.getString("caducidad_Robot"));
		robot.setFechaDesemblistado(rs.getString("diaDesemblistado_Robot"));
		robot.setLab(rs.getString("nombreLab_Robot"));
		robot.setNombreConsejoCn(rs.getString("nombreConsejo_Robot"));
		robot.setIdResidenciaCarga(rs.getString("idResidenciaCarga_Robot"));
		robot.setPrescriptionOrderNumber(rs.getString("prescriptionOrderNumber_Robot"));
		robot.setDiaEmbolsado(rs.getString("diaEmbolsado_Robot"));
		robot.setHoraEmbolsado(rs.getString("horaEmbolsado_Robot"));
		robot.setTotalBolsas(rs.getInt("totalBolsas_Robot"));
		robot.setNumeroOrdenBolsa(rs.getString("numeroOrdenBolsa_Robot"));
		robot.setPrimerIdBolsaSPD(rs.getString("primerIdBolsaSPD_Robot")); 
		robot.setUltimoIdBolsaSPD(rs.getString("ultimoIdBolsaSPD_Robot")); 
		robot.setIdBolsa(rs.getString("idBolsa_Robot"));
		robot.setCantidadEmblistadaSPD(rs.getFloat("cantidad_Robot"));
		robot.setCodigoBarras(rs.getString("codigoBarras_Robot"));
		robot.setCodigoMedicamentoRobot(rs.getString("codigoM_Interno_Robot")); 
		robot.setFreeInformation(rs.getString("freeInformation_Robot"));
		robot.setOffsetDays(rs.getInt("offsetDays_Robot"));
	 	robot.setDoseTime(rs.getString("doseTime_Robot"));
	 	robot.setNumeroTolva(rs.getInt("numeroTolva_Robot"));
	 	robot.setFechaInsert(rs.getDate("fechaInsert_Robot"));
	 	robot.setIdProceso(rs.getString("idProceso_Robot"));
	 	robot.setFechaCorte(rs.getString("fechaCorte_Robot")); 
	 	
		robot.setAspectoMedicamento(creaIdentificacion(rs));
		
		return robot;
	}

	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public AspectoMedicamento creaIdentificacion(ResultSet rs) throws SQLException {
		AspectoMedicamento ident = new AspectoMedicamento();
		ident.setForma(rs.getString("FORMA"));
		ident.setColor1(rs.getString("COLOR1"));
		ident.setColor2(rs.getString("COLOR2"));
		ident.setInscripcionA(rs.getString("INSCRIPCIONA"));
		ident.setInscripcionB(rs.getString("INSCRIPCIONB"));
		ident.setRanura(rs.getString("RANURA"));
		ident.setDibujo(rs.getString("DIBUJO"));
		ident.setAncho(rs.getString("ANCHO"));
		ident.setLargo(rs.getString("LARGO"));
		return ident;
	}
	
	public AspectoMedicamento creaIdentificacion(String CN) throws SQLException, ClassNotFoundException {
		return dao.buscarIdentificacion(CN);
	}
	
	public MedicamentoReceta creaMedicamentoReceta(ResultSet rs) throws SQLException, ClassNotFoundException {
		MedicamentoReceta medic = new MedicamentoReceta();
		medic.setCn(rs.getString("CodigoDispensado"));
		BdConsejo bdConsejo = BdConsejoDAO.getByCN(rs.getString("CodigoDispensado"));
		medic.setNombreConsejoCn(bdConsejo!=null?bdConsejo.getNombreConsejo():"");
		medic.setLab(bdConsejo!=null?bdConsejo.getNombreLaboratorio():"");
		
		Timestamp  ts = rs.getTimestamp("fecha");
		if (ts != null) {
		    // Formatear fecha
		    String fechaFormateada = ts.toLocalDateTime()
		                               .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
		   medic.setFechaVenta(fechaFormateada);
		   if(esFechaMasAntiguaQue(ts, SPDConstants.DIAS_DISPENSACION_ANTIGUA))
			   medic.setVentaAntigua(true);   
		} 
		
			
		//medic.setNombreMedicamentoConsejo(rs.getString("nombreMedicamentoConsejo"));
		//medic.setLabMedicamento(rs.getString("nombreLaboratorio"));
		medic.setLote(rs.getString("lote"));
		medic.setCaducidad(rs.getString("caducidad"));
		medic.setNumeroSerie(rs.getString("numeroSerie"));
	//	if(mezclar) Se realiza en el DAO 
	//		medic.setIdentificacion(dao.buscarIdentificacion(rs.getString("codigoDispensado")));
		return medic;
	}
/**
 *  * 
 * @param rs
 * @return
 * @throws SQLException
 */
	
	public DiaTomas creaDiaTomas(ResultSet rs) throws SQLException {
		DiaTomas diaTomas = new DiaTomas();
		diaTomas.setCantidadDia(rs.getDouble("cantidadToma_Birtual"));
		//diaTomas.setPautaDiaria(pautaDiaria);
		//diaTomas.setOrden(ordenDiaEnProduccion);
        //String fecha = String.valueOf(rs.getInt("diaInicioSPD"));
        //int diaRelativo = rs.getInt("offsetDays");
		//String fechaToma = DateUtilities.desplazarFecha(fecha, diaRelativo, SPDConstants.FORMATO_FECHA_yyyyMMdd, SPDConstants.FORMATO_FECHA_DEFAULT);
		//diaTomas.setFechaToma(fechaToma);
        diaTomas.setFechaToma(rs.getString("fechaToma2_Birtual"));
		return diaTomas;
	}


	public Toma creaToma(ResultSet rs, int numTomas) throws SQLException {
		Toma toma = new Toma();
		toma.setCantidadToma(rs.getDouble("cantidadToma_Birtual"));
		String idToma = rs.getString("idToma_Birtual");
		if(idToma==null || idToma.equals("") || idToma.equalsIgnoreCase("null")) idToma=rs.getString("nombreToma_Birtual");
		toma.setIdToma(idToma);
		toma.setNombreToma(rs.getString("nombreToma_Birtual"));
		toma.setOrdenToma(numTomas);
		return toma;
	}

/**
 * 		
	
 * @param rs
 * @return
 * @throws SQLException
 */
	public DiaSPD creaDiaSPD(ResultSet rs) throws SQLException {
		DiaSPD diaSPD = new DiaSPD();
		diaSPD.setCantidadDia(rs.getDouble("cantidadToma_Birtual"));
        String fecha = String.valueOf(rs.getInt("diaInicioSPD_Robot"));
        int diaRelativo = rs.getInt("offsetDays");
		String fechaToma = DateUtilities.desplazarFecha(fecha, diaRelativo, SPDConstants.FORMATO_FECHA_yyyyMMdd, SPDConstants.FORMATO_FECHA_DEFAULT);
		diaSPD.setFechaToma(fechaToma);
		diaSPD.setOrdenDiaEnProduccion(diaRelativo);
		diaSPD.setPautaDiaria("");
		return diaSPD;
	}
	
	public DiaSPD complementaDiaSPD(ResultSet rs, DiaSPD diaSPD) throws SQLException {
		diaSPD.setCantidadDia(rs.getDouble("cantidadToma_Birtual"));
		diaSPD.setPautaDiaria("");
		return diaSPD;
	}
	public DiaSPD creaDiaSPDBasico(String fechaToma, int diaRelativo) throws SQLException {
		DiaSPD diaSPD = new DiaSPD();
		diaSPD.setFechaToma(fechaToma);
		diaSPD.setOrdenDiaEnProduccion(diaRelativo);
		return diaSPD;
	}

	public BolsaSPD creaBolsaSPD(ResultSet rs) throws SQLException {
		BolsaSPD bolsaSPD = new BolsaSPD();
		bolsaSPD.setCodigoBarras(rs.getString("codigoBarras_Robot"));
		bolsaSPD.setFechaEmbolsado(rs.getString("diaEmbolsado_Robot"));
		bolsaSPD.setFlag(1); //las que se recogen del robot son PASTILLERO
		bolsaSPD.setFreeInformation(rs.getString("freeInformation_Robot"));
		bolsaSPD.setHoraEmbolsado(rs.getString("horaEmbolsado_Robot"));
		bolsaSPD.setIdBolsa(rs.getString("idBolsa_Robot"));
		bolsaSPD.setNumeroOrdenBolsa(rs.getInt("numeroOrdenBolsa_Robot"));
		bolsaSPD.setOffsetDays(rs.getInt("offsetDays"));
		bolsaSPD.setTomaDelDia(rs.getString("nombreToma_Birtual"));
		//bolsaSPD.setLineasBolsa(drugs);
		return bolsaSPD;
	}

/**
 * 						b.fechaHoraProceso AS fechaHoraProceso_Birtual, 	
							b.idDivisionResidencia AS idDivisionResidencia_Birtual, 	
							b.idProceso AS idProceso_Birtual, 	
							b.CIP AS CIP_Birtual, 	
							b.fechaToma AS fechaToma_Birtual, 	
							b.idLineaRX AS idLineaRX_Birtual, 	
							b.tramoToma AS tramoToma_Birtual, 
 * @param rs
 * @return
 * @throws SQLException
 */
	public LineaBolsaSPD creaLineaBolsaSPD(ResultSet rs) throws SQLException {
		LineaBolsaSPD linea = new LineaBolsaSPD();
		linea.setCantidad(rs.getDouble("cantidad_Robot"));
		linea.setFreeInformation(rs.getString("freeInformation_Robot"));
		return linea;
	}


	public ProduccionPaciente creaProduccion(ResultSet rs, FicheroResiBean cab) throws SQLException {
		ProduccionPaciente produccion = new ProduccionPaciente();
		produccion.setDiaSPDInicio(cab.getNuevaFechaDesde()!=null?cab.getNuevaFechaDesde():cab.getFechaDesde());
		produccion.setDiaSPDFin(cab.getNuevaFechaHasta()!=null?cab.getNuevaFechaHasta():cab.getFechaHasta());
		produccion.setDiasProduccion(DateUtilities.diasEntreFechas(cab.getFechaDesde(), cab.getFechaHasta(), SPDConstants.FORMATO_FECHA_DEFAULT));
		produccion.setOrderNumber(rs.getString("orderNumber_Birtual"));
		return produccion;
	}


	public List<ProduccionPaciente> findByIdResidenciaCarga(String spdUsuario, FicheroResiBean cab, boolean recetas, boolean prevaleceReceta) throws Exception {
		//return InformeSpdDAO.findByIdResidenciaCarga(spdUsuario, cab);
		return dao.findLiteByIdProceso(spdUsuario, cab, recetas, prevaleceReceta);
	}

	/**
	 * Añadimos un elemento en posición determinada, controlando que los anteriores existen o en caso contrario las creamos nulas para que no desborde
	 * @param lista
	 * @param posicion
	 * @param elemento
	 */
    public <T> void insertarEnPosicion(List<T> lista, int posicion, T elemento) {
        // Rellenar posiciones intermedias si es necesario
        for (int i = lista.size(); i < posicion; i++) {
            lista.add(null); // Añadir null solo si aún no existe esa posición
        }

        if (lista.size() == posicion) {
            // Si justo estamos al final, simplemente añadimos el nuevo elemento
            lista.add(elemento);
        } else {
            // Si ya hay un valor en esa posición, lo desplazamos
            lista.add(posicion, elemento);
        }
    }


	public TreeMap<String, DiaSPD> crearTreemapDiasSPD(String keyCIP, FicheroResiBean cab) throws SQLException {
		TreeMap<String, DiaSPD> tm_DiasSPD =new TreeMap<String, DiaSPD>();
		String inicio = cab.getNuevaFechaDesde()!=null?cab.getNuevaFechaDesde():cab.getFechaDesde();
		String fin = cab.getNuevaFechaHasta()!=null?cab.getNuevaFechaHasta():cab.getFechaHasta();
		
		int diasProduccion = DateUtilities.diasEntreFechas(cab.getFechaDesde(), cab.getFechaHasta(), SPDConstants.FORMATO_FECHA_DEFAULT);
		for(int z=0; z<diasProduccion+1; z++)
		{
			String fecha= DateUtilities.desplazarFecha(inicio, z, SPDConstants.FORMATO_FECHA_DEFAULT, SPDConstants.FORMATO_FECHA_DEFAULT);
			tm_DiasSPD.put(keyCIP + "_" + z , creaDiaSPDBasico(fecha, z));
		}
		return tm_DiasSPD;
	}


	public boolean descartable(ResultSet rs) throws SQLException {
		String dispensar=rs.getString("dispensar_Birtual");
		String idBolsa=rs.getString("idBolsa_Birtual");
		if(dispensar!=null && dispensar.equalsIgnoreCase("S") && (idBolsa==null || idBolsa.equals("")  || idBolsa.equals("null") ))
			return true;
		return false;
	}

    public static boolean esFechaMasAntiguaQue(Timestamp ts, int dias) {
        if (ts == null) {
            return false; // o true, según cómo quieras tratar un NULL
        }

        LocalDateTime fecha = ts.toLocalDateTime();
        LocalDateTime limite = LocalDateTime.now().minusDays(dias);

        return fecha.isBefore(limite);
    }



}



	
	
	
	
	
	
	
	
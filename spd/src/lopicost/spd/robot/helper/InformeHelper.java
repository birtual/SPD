package lopicost.spd.robot.helper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.TreeMap;

import lopicost.spd.model.BdConsejo;
import lopicost.spd.persistence.BdConsejoDAO;
import lopicost.spd.persistence.InformeSpdDAO;
import lopicost.spd.persistence.PacienteDAO;
import lopicost.spd.robot.bean.rd.BolsaSPD;
import lopicost.spd.robot.bean.rd.DiaSPD;
import lopicost.spd.robot.bean.rd.DiaTomas;
import lopicost.spd.robot.bean.rd.Identificacion;
import lopicost.spd.robot.bean.rd.LineaBolsaSPD;
import lopicost.spd.robot.bean.rd.MedicamentoReceta;
import lopicost.spd.robot.bean.rd.MedicamentoPaciente;
import lopicost.spd.robot.bean.rd.ProduccionPaciente;
import lopicost.spd.robot.bean.rd.Toma;
import lopicost.spd.robot.bean.rd.TratamientoPaciente;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.struts.bean.PacienteBean;
import lopicost.spd.utils.DateUtilities;
import lopicost.spd.utils.HelperSPD;
import lopicost.spd.utils.SPDConstants;

/**
 *Logica de negocio 
 */
public class InformeHelper {


	private final String cLOGGERHEADER = "InformeHelper: ";
	private final String cLOGGERHEADER_ERROR = cLOGGERHEADER + "ERROR: InformeHelper: ";
	InformeSpdDAO dao =  new InformeSpdDAO();
	
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
		tto.setMedicamentoPaciente(creaMedicamentoPaciente(rs));
		if(mezclar) //Marco - 20250806 - Necesita que aparezca la info de receta en la dispensación (TEMPORALMENTE)
		{
			MedicamentoReceta receta = InformeSpdDAO.buscarUltimaDispensacionReceta(rs.getString("CIP"), rs.getString("NomGtVmp"));
			if(receta!=null) {
				receta.setNombreMedicamentoBolsa(rs.getString("nombreMedicamento"));
				tto.setMedicamentoReceta(receta);
			}
		}
		tto.setCantidadUtilizadaSPD(rs.getDouble("cantidad"));
		tto.setPautaResidencia(rs.getString("pautaResidencia"));
		tto.setEmblistar(rs.getString("dispensar")!=null&&rs.getString("dispensar").equalsIgnoreCase("S"));
		//tto.setDiaSPD(diaSPD);
		return tto;
	}



	public MedicamentoPaciente creaMedicamentoPaciente(ResultSet rs) throws SQLException, ClassNotFoundException {
		MedicamentoPaciente medic = new MedicamentoPaciente();
		medic.setCn(rs.getString("cn"));
		medic.setNombreMedicamentoBolsa(rs.getString("nombreMedicamento"));
		medic.setLote(rs.getString("lote"));
		medic.setCaducidad(rs.getString("caducidad"));
		medic.setNumeroSerie(rs.getString("numeroSerie"));
		medic.setCodigoMedicamentoRobot(rs.getString("codigoMedicamentoRobot"));
		//BdConsejo bdConsejo = BdConsejoDAO.getByCN(rs.getString("cn"));
		//medic.setNombreMedicamentoConsejo(bdConsejo!=null?bdConsejo.getNombreConsejo():"");
		//medic.setLabMedicamento(bdConsejo!=null?bdConsejo.getNombreLaboratorio():"");
		medic.setNombreMedicamentoConsejo(rs.getString("nombreMedicamentoConsejo"));
		medic.setLabMedicamento(rs.getString("nombreLaboratorio"));
		medic.setFechaDesemblistado(rs.getString("diaDesemblistado"));
		medic.setPautaResidencia(rs.getString("pautaResidencia"));
		medic.setFormaFarmaceutica("");
		medic.setIdentificacion(creaIdentificacion(rs));
		
		return medic;
	}

	public Identificacion creaIdentificacion(ResultSet rs) throws SQLException {
		Identificacion ident = new Identificacion();
		ident.setForma(rs.getString("forma"));
		ident.setColor1(rs.getString("color1"));
		ident.setColor2(rs.getString("color2"));
		ident.setInscripcionA(rs.getString("inscripcionA"));
		ident.setInscripcionB(rs.getString("inscripcionB"));
		ident.setRanura(rs.getString("ranura"));
		ident.setDibujo(rs.getString("dibujo"));
		ident.setAncho(rs.getString("ancho"));
		ident.setLargo(rs.getString("largo"));
		return ident;
	}

	public MedicamentoReceta creaMedicamentoReceta(ResultSet rs) throws SQLException, ClassNotFoundException {
		MedicamentoReceta medic = new MedicamentoReceta();
		medic.setCn(rs.getString("codigoDispensado"));
		BdConsejo bdConsejo = BdConsejoDAO.getByCN(rs.getString("codigoDispensado"));
		medic.setNombreMedicamentoConsejo(bdConsejo!=null?bdConsejo.getNombreConsejo():"");
		medic.setLabMedicamento(bdConsejo!=null?bdConsejo.getNombreLaboratorio():"");
		//medic.setNombreMedicamentoConsejo(rs.getString("nombreMedicamentoConsejo"));
		//medic.setLabMedicamento(rs.getString("nombreLaboratorio"));
		medic.setLote(rs.getString("lote"));
		medic.setCaducidad(rs.getString("caducidad"));
		medic.setNumSerie(rs.getString("numeroSerie"));
	//	if(mezclar) Se realiza en el DAO 
	//		medic.setIdentificacion(dao.buscarIdentificacion(rs.getString("codigoDispensado")));
		return medic;
	}

	
	public DiaTomas creaDiaTomas(ResultSet rs) throws SQLException {
		DiaTomas diaTomas = new DiaTomas();
		diaTomas.setCantidadDia(rs.getDouble("cantidad"));
		//diaTomas.setPautaDiaria(pautaDiaria);
		//diaTomas.setOrden(ordenDiaEnProduccion);
        //String fecha = String.valueOf(rs.getInt("diaInicioSPD"));
        //int diaRelativo = rs.getInt("offsetDays");
		//String fechaToma = DateUtilities.desplazarFecha(fecha, diaRelativo, SPDConstants.FORMATO_FECHA_yyyyMMdd, SPDConstants.FORMATO_FECHA_DEFAULT);
		//diaTomas.setFechaToma(fechaToma);
        diaTomas.setFechaToma(rs.getString("fechaToma2"));
		return diaTomas;
	}


	public Toma creaToma(ResultSet rs, int numTomas) throws SQLException {
		Toma toma = new Toma();
		toma.setCantidadToma(rs.getDouble("cantidad"));
		String idToma = rs.getString("idToma");
		if(idToma==null || idToma.equals("") || idToma.equalsIgnoreCase("null")) idToma=rs.getString("nombreToma");
		toma.setIdToma(idToma);
		toma.setNombreToma(rs.getString("nombreToma"));
		toma.setOrdenToma(numTomas);
		return toma;
	}


	public DiaSPD creaDiaSPD(ResultSet rs) throws SQLException {
		DiaSPD diaSPD = new DiaSPD();
		diaSPD.setCantidadDia(rs.getDouble("cantidad"));
        String fecha = String.valueOf(rs.getInt("diaInicioSPD"));
        int diaRelativo = rs.getInt("offsetDays");
		String fechaToma = DateUtilities.desplazarFecha(fecha, diaRelativo, SPDConstants.FORMATO_FECHA_yyyyMMdd, SPDConstants.FORMATO_FECHA_DEFAULT);
		diaSPD.setFechaToma(fechaToma);
		diaSPD.setOrdenDiaEnProduccion(diaRelativo);
		diaSPD.setPautaDiaria("");
		return diaSPD;
	}
	
	public DiaSPD complementaDiaSPD(ResultSet rs, DiaSPD diaSPD) throws SQLException {
		diaSPD.setCantidadDia(rs.getDouble("cantidad"));
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
		bolsaSPD.setCodigoBarras(rs.getString("codigoBarras"));
		bolsaSPD.setFechaEmbolsado(rs.getString("diaEmbolsado"));
		bolsaSPD.setFlag(1); //las que se recogen del robot son PASTILLERO
		bolsaSPD.setFreeInformation(rs.getString("freeInformation"));
		bolsaSPD.setHoraEmbolsado(rs.getString("horaEmbolsado"));
		bolsaSPD.setIdBolsa(rs.getString("idBolsa"));
		bolsaSPD.setNumeroOrdenBolsa(rs.getInt("numeroOrdenBolsa"));
		bolsaSPD.setOffsetDays(rs.getInt("offsetDays"));
		bolsaSPD.setTomaDelDia(rs.getString("nombreToma"));
		//bolsaSPD.setLineasBolsa(drugs);
		return bolsaSPD;
	}


	public LineaBolsaSPD creaLineaBolsaSPD(ResultSet rs) throws SQLException {
		LineaBolsaSPD linea = new LineaBolsaSPD();
		linea.setCantidad(rs.getDouble("cantidad"));
		linea.setFreeInformation(rs.getString("freeInformation"));
		return linea;
	}


	public ProduccionPaciente creaProduccion(ResultSet rs, FicheroResiBean cab) throws SQLException {
		ProduccionPaciente produccion = new ProduccionPaciente();
		produccion.setDiaSPDInicio(cab.getNuevaFechaDesde()!=null?cab.getNuevaFechaDesde():cab.getFechaDesde());
		produccion.setDiaSPDFin(cab.getNuevaFechaHasta()!=null?cab.getNuevaFechaHasta():cab.getFechaHasta());
		produccion.setDiasProduccion(DateUtilities.diasEntreFechas(cab.getFechaDesde(), cab.getFechaHasta(), SPDConstants.FORMATO_FECHA_DEFAULT));
		produccion.setOrderNumber(rs.getString("prescriptionOrderNumber"));
		return produccion;
	}


	public List<ProduccionPaciente> findByIdResidenciaCarga(String spdUsuario, FicheroResiBean cab, boolean recetas, boolean prevaleceReceta) throws Exception {
		//return InformeSpdDAO.findByIdResidenciaCarga(spdUsuario, cab);
		return InformeSpdDAO.findLiteByResidenciaCarga(spdUsuario, cab, recetas, prevaleceReceta);
		//return InformeSpdDAO.findLiteByIdProceso(spdUsuario, cab, recetas, prevaleceReceta);
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
		//System.out.println(HelperSPD.dameFechaHora() + " crearTreemapDiasSPD --> diasProduccion " + diasProduccion);	
		/*System.out.println(HelperSPD.dameFechaHora() + " crearTreemapDiasSPD --> getFechaDesde " + cab.getFechaDesde());		
		System.out.println(HelperSPD.dameFechaHora() + " crearTreemapDiasSPD --> getFechaHasta " + cab.getFechaHasta());		
		System.out.println(HelperSPD.dameFechaHora() + " crearTreemapDiasSPD --> getNuevaFechaDesde " + cab.getNuevaFechaDesde());		
		System.out.println(HelperSPD.dameFechaHora() + " crearTreemapDiasSPD --> getNuevaFechaHasta " + cab.getNuevaFechaHasta());		

		System.out.println(HelperSPD.dameFechaHora() + " crearTreemapDiasSPD --> inicio " + inicio);		
		System.out.println(HelperSPD.dameFechaHora() + " crearTreemapDiasSPD --> fin " + fin);		
		System.out.println(HelperSPD.dameFechaHora() + " crearTreemapDiasSPD --> diasProduccion " + diasProduccion);	
		*/
		for(int z=0; z<diasProduccion+1; z++)
		{
			String fecha= DateUtilities.desplazarFecha(inicio, z, SPDConstants.FORMATO_FECHA_DEFAULT, SPDConstants.FORMATO_FECHA_DEFAULT);
			tm_DiasSPD.put(keyCIP + "_" + z , creaDiaSPDBasico(fecha, z));
		}
		//System.out.println(HelperSPD.dameFechaHora() + " crearTreemapDiasSPD --> tm_DiasSPD.size " + tm_DiasSPD.size());		
	
		return tm_DiasSPD;
	}


	public boolean descartable(ResultSet rs) throws SQLException {
		String dispensar=rs.getString("dispensar");
		String idBolsa=rs.getString("idBolsa");
		if(dispensar!=null && dispensar.equalsIgnoreCase("S") && (idBolsa==null || idBolsa.equals("")  || idBolsa.equals("null") ))
			return true;
		return false;
	}




}



	
	
	
	
	
	
	
	
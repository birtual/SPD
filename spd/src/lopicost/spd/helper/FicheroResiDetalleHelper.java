
package lopicost.spd.helper;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date; 
import java.util.Iterator;
import java.util.List;


import lopicost.spd.helium.helper.HeliumHelper;
import lopicost.spd.helium.model.Dose;
import lopicost.spd.persistence.CabecerasXLSDAO;
import lopicost.spd.persistence.DoseDAO;
import lopicost.spd.persistence.FicheroResiCabeceraDAO;
import lopicost.spd.persistence.FicheroResiDetalleDAO;
import lopicost.spd.persistence.PacienteDAO;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.struts.bean.InfoAlertasBean;
import lopicost.spd.struts.bean.PacienteBean;
import lopicost.spd.struts.form.FicheroResiForm;
import lopicost.spd.utils.HelperSPD;
import lopicost.spd.utils.SPDConstants;
import lopicost.spd.utils.StringUtil;

/**
  * Logica de negocio 
 */
public class FicheroResiDetalleHelper {

	private final String cLOGGERHEADER = "FicheroResiDetalleHelper: ";
	private final String cLOGGERHEADER_ERROR = cLOGGERHEADER + "ERROR: ";


	public static void rellenaListados(FicheroResiForm formulari) {
			
			boolean verDatosPersonales = formulari.isFiltroVerDatosPersonales();
			List<FicheroResiBean> rows2 = formulari.getListaFicheroResiDetalleBean();
			//List<FicheroResiDetalleBean> rows = formulari.getTodaLaListaGestFicheroResiBolsaBean();
			//Para(FicheroResiDetalleBean name :  rows.)
			List<FicheroResiBean> procesos = formulari.getListaProcesosCargados();
			
			List listaProcesosCargados = new ArrayList();
			List<Date> listaFechaFichero = new ArrayList();
			List<String> listaIdentificador = new ArrayList();
			List<String> listaResiCIP = new ArrayList();
			List<String> listaResiNombrePaciente = new ArrayList();
			List<String> listaResiApellidosNombre = new ArrayList();
			List<String> listaResiCn = new ArrayList();
			List<String> listaResiMedicamento = new ArrayList();
			List<String> listaResiFormaMedicacion = new ArrayList();
			List<String> listaResiObservaciones = new ArrayList();
			List<String> listaResiComentarios = new ArrayList();
			List<String> listaResiTipoMedicacion = new ArrayList();
			List<String> listaResiSiPrecisa = new ArrayList();
			List<String> listaResiPeriodo = new ArrayList();
			List<String> listaResiVariante = new ArrayList();
			List<String> listaResiViaAdministracion = new ArrayList();
			List<String> listaSpdCnFinal = new ArrayList();
			List<String> listaSpdNombreBolsa = new ArrayList();
			List<String> listaSpdFormaMedicacion = new ArrayList();
			List<String> listaSpdAccionBolsa = new ArrayList();
			List<String> listaIncidencia = new ArrayList();
		//	List<String> listaResultLog = new ArrayList();
			List<String> listaMensajesInfo = new ArrayList();
			List<String> listaMensajesAlerta = new ArrayList();
			List<String> listaMensajesResidencia = new ArrayList();
			List<String> listaEstados = new ArrayList();
			List<String> listaDiasAutomaticos = new ArrayList();
			List<String> listaRevisar = new ArrayList();
			List<String> listaConfirmar = new ArrayList();
			List<String> listaSecuenciaGuide = new ArrayList();
		//	List<String> listaOrdenacion = new ArrayList();
						
			formulari.setListaFechaFichero(listaFechaFichero);
			formulari.setListaProcesosCargados(listaProcesosCargados);
			formulari.setListaIdentificador(listaIdentificador);
			formulari.setListaResiCIP(listaResiCIP);
			formulari.setListaResiNombrePaciente(listaResiNombrePaciente);
			formulari.setListaResiApellidosNombre(listaResiApellidosNombre);
			formulari.setListaResiCn(listaResiCn);
			formulari.setListaResiMedicamento(listaResiMedicamento);
			formulari.setListaResiFormaMedicacion(listaResiFormaMedicacion);
			formulari.setListaResiObservaciones(listaResiObservaciones);
			formulari.setListaResiComentarios(listaResiComentarios);
			formulari.setListaResiVariante(listaResiVariante);
			formulari.setListaResiTipoMedicacion(listaResiTipoMedicacion);
			formulari.setListaResiSiPrecisa(listaResiSiPrecisa);
			formulari.setListaResiPeriodo(listaResiPeriodo);
			formulari.setListaResiViaAdministracion(listaResiViaAdministracion);
			formulari.setListaSpdCnFinal(listaSpdCnFinal);
			formulari.setListaSpdNombreBolsa(listaSpdNombreBolsa);
			formulari.setListaSpdFormaMedicacion(listaSpdFormaMedicacion);
			formulari.setListaSpdAccionBolsa(listaSpdAccionBolsa);
			formulari.setListaIncidencia(listaIncidencia);
			//formulari.setListaResultLog(listaResultLog);
			formulari.setListaMensajesInfo(listaMensajesInfo);
			formulari.setListaMensajesAlerta(listaMensajesAlerta);
			formulari.setListaMensajesResidencia(listaMensajesResidencia);
			formulari.setListaEstados(listaEstados);
			formulari.setListaDiasAutomaticos(listaDiasAutomaticos);
			formulari.setListaValidar(listaRevisar);
			formulari.setListaConfirmar(listaConfirmar);
			
			formulari.setListaSecuenciaGuide(listaSecuenciaGuide);
		//		formulari.setListaOrdenacion(listaOrdenacion);
			
			Iterator<FicheroResiBean> it = rows2.iterator();
			while (it.hasNext())
			{
				FicheroResiBean grb = (FicheroResiBean) it.next();
				String t = (String)  grb.getResiCIP();
		            
			//	if(!listaProcesosCargados.contains( grb.getIdProceso()) && grb.getIdProceso()!=null && !grb.getIdProceso().equals(""))									
			//		listaProcesosCargados.add((String) grb.getIdProceso());
			//	Collections.sort(listaProcesosCargados);
				if(!listaIdentificador.contains( grb.getOidPaciente()) && grb.getOidPaciente()!=null && !grb.getOidPaciente().equals(""))									
					listaIdentificador.add((String) grb.getOidPaciente());
				//Collections.sort(listaIdentificador);
				Collections.sort(listaIdentificador, (a, b) -> Integer.compare(Integer.parseInt(a), Integer.parseInt(b)));
				
				//tenemos en cuenta si tiene máscara
				String CIP = (verDatosPersonales?(String) grb.getResiCIP():(String) grb.getResiCIPMask());
				if(CIP!=null && !CIP.equals("")  &&  !listaResiCIP.contains(CIP) )											
					listaResiCIP.add(CIP);
				Collections.sort(listaResiCIP);
				//actualizamos el resiCIP por si es un campo seleccionado por el usuario, y cambiamos de Mask a noMask o viceversa
				if(formulari.getSeleccionResiCIP()!=null && !formulari.getSeleccionResiCIP().equals("")) formulari.setSeleccionResiCIP(CIP);
				
				String nombrePaciente = verDatosPersonales? (String) grb.getResiNombrePaciente():(String) grb.getResiNombrePacienteMask();
				if(nombrePaciente!=null && !nombrePaciente.equals("") && !listaResiNombrePaciente.contains(nombrePaciente)  )				
					listaResiNombrePaciente.add(nombrePaciente);
				Collections.sort(listaResiNombrePaciente);
				//actualizamos el resiCIP por si es un campo seleccionado por el usuario, y cambiamos de Mask a noMask o viceversa
				if(formulari.getSeleccionResiNombrePaciente()!=null && !formulari.getSeleccionResiNombrePaciente().equals("")) formulari.setSeleccionResiNombrePaciente(nombrePaciente);
				
				String apellidosNombre = verDatosPersonales? (String) grb.getResiApellidosNombre():(String) grb.getResiApellidosNombreMask();
				if(apellidosNombre!=null && !apellidosNombre.equals("") &&  !listaResiApellidosNombre.contains(apellidosNombre) )								
					listaResiApellidosNombre.add(apellidosNombre);
				Collections.sort(listaResiApellidosNombre);
				//actualizamos el resiCIP por si es un campo seleccionado por el usuario, y cambiamos de Mask a noMask o viceversa
				if(formulari.getSeleccionResiApellidosNombre()!=null  && !formulari.getSeleccionResiApellidosNombre().equals("")) formulari.setSeleccionResiApellidosNombre(apellidosNombre);
				
				
				if(!listaResiCn.contains((String)  grb.getResiCn()) && grb.getResiCn()!=null && !grb.getResiCn().equals(""))										
					listaResiCn.add((String) grb.getResiCn());
				Collections.sort(listaResiCn);
				
				
				if(!listaResiMedicamento.contains((String)  grb.getResiMedicamento()) && grb.getResiMedicamento()!=null && !grb.getResiMedicamento().equals(""))					
					listaResiMedicamento.add((String) grb.getResiMedicamento());
				Collections.sort(listaResiMedicamento);
				if(!listaResiFormaMedicacion.contains((String)  grb.getResiFormaMedicacion()) && grb.getResiFormaMedicacion()!=null && !grb.getResiFormaMedicacion().equals(""))			
					listaResiFormaMedicacion.add((String) grb.getResiFormaMedicacion());
				Collections.sort(listaResiFormaMedicacion);
				if(!listaResiObservaciones.contains((String)  grb.getResiObservaciones()) && grb.getResiObservaciones()!=null && !grb.getResiObservaciones().equals(""))			
					listaResiObservaciones.add((String) grb.getResiObservaciones());
				Collections.sort(listaResiObservaciones);
				if(!listaResiComentarios.contains((String)  grb.getResiComentarios()) && grb.getResiComentarios()!=null && !grb.getResiComentarios().equals(""))			
					listaResiComentarios.add((String) grb.getResiComentarios());
				Collections.sort(listaResiComentarios);
				if(!listaResiSiPrecisa.contains((String)  grb.getResiSiPrecisa()) && grb.getResiSiPrecisa()!=null && !grb.getResiSiPrecisa().equals(""))			
					listaResiSiPrecisa.add((String) grb.getResiSiPrecisa());
				Collections.sort(listaResiSiPrecisa);
				if(!listaResiTipoMedicacion.contains((String)  grb.getResiTipoMedicacion()) && grb.getResiTipoMedicacion()!=null && !grb.getResiTipoMedicacion().equals(""))			
					listaResiTipoMedicacion.add((String) grb.getResiTipoMedicacion());
				Collections.sort(listaResiTipoMedicacion);
				if(!listaResiPeriodo.contains((String)  grb.getResiPeriodo()) && grb.getResiPeriodo()!=null && !grb.getResiPeriodo().equals(""))			
					listaResiPeriodo.add((String) grb.getResiPeriodo());
				Collections.sort(listaResiPeriodo);
				if(!listaResiViaAdministracion.contains((String)  grb.getResiViaAdministracion()) && grb.getResiViaAdministracion()!=null && !grb.getResiViaAdministracion().equals(""))			
					listaResiViaAdministracion.add((String) grb.getResiViaAdministracion());
				Collections.sort(listaResiViaAdministracion);
				if(!listaSpdCnFinal.contains((String)  grb.getSpdCnFinal()) && grb.getSpdCnFinal()!=null && !grb.getSpdCnFinal().equals(""))			
					listaSpdCnFinal.add((String) grb.getSpdCnFinal());
				Collections.sort(listaSpdCnFinal);
				if(!listaSpdNombreBolsa.contains((String)  grb.getSpdNombreBolsa()) && grb.getSpdNombreBolsa()!=null && !grb.getSpdNombreBolsa().equals(""))				
					listaSpdNombreBolsa.add((String) grb.getSpdNombreBolsa());
				Collections.sort(listaSpdNombreBolsa);
				if(!listaSpdFormaMedicacion.contains((String)  grb.getSpdFormaMedicacion()) && grb.getSpdFormaMedicacion()!=null && !grb.getSpdFormaMedicacion().equals(""))				
					listaSpdFormaMedicacion.add((String) grb.getSpdFormaMedicacion());
				Collections.sort(listaSpdFormaMedicacion);
				if(!listaSpdAccionBolsa.contains((String)  grb.getSpdAccionBolsa()) && grb.getSpdAccionBolsa()!=null && !grb.getSpdAccionBolsa().equals(""))				
					listaSpdAccionBolsa.add((String) grb.getSpdAccionBolsa());
				Collections.sort(listaSpdAccionBolsa);
				if(!listaIncidencia.contains((String)  (grb.getIncidencia()+"")) && (grb.getIncidencia()+"")!=null && !(grb.getIncidencia()+"").equals(""))				
					listaIncidencia.add((String) (grb.getIncidencia()+""));
				Collections.sort(listaIncidencia);
			//	if(!listaResultLog.contains((String)  grb.getResultLog()) && grb.getResultLog()!=null && !grb.getResultLog().equals(""))			
			//		listaResultLog.add((String) grb.getResultLog());
			//	Collections.sort(listaResultLog);
				if(!listaMensajesInfo.contains((String)  grb.getMensajesInfo()) && grb.getMensajesInfo()!=null && !grb.getMensajesInfo().equals(""))			
					listaMensajesInfo.add((String) grb.getMensajesInfo());
				Collections.sort(listaMensajesInfo);
				if(!listaMensajesResidencia.contains((String)  grb.getMensajesResidencia()) && grb.getMensajesResidencia()!=null && !grb.getMensajesResidencia().equals(""))			
					listaMensajesResidencia.add((String) grb.getMensajesResidencia());
				Collections.sort(listaMensajesResidencia);
				if(!listaMensajesAlerta.contains((String)  grb.getMensajesAlerta()) && grb.getMensajesAlerta()!=null && !grb.getMensajesAlerta().equals(""))				
					listaMensajesAlerta.add((String) grb.getMensajesAlerta());
				Collections.sort(listaMensajesAlerta);
			//	System.out.println("grb.getIdEstado() "  +listaFechaFichero.contains((String)  grb.getIdEstado()));
				if(!listaEstados.contains((String)  grb.getIdEstado()) && grb.getIdEstado()!=null && !grb.getIdEstado().equals(""))				
					listaEstados.add((String) grb.getIdEstado());
				Collections.sort(listaEstados);
				
				if(!listaFechaFichero.contains((Date)  grb.getFechaHoraProceso()) && grb.getFechaHoraProceso()!=null && !grb.getFechaHoraProceso().equals(""))				
					listaFechaFichero.add((Date) grb.getFechaHoraProceso());
				Collections.sort(listaEstados);
				
				if(!listaDiasAutomaticos.contains((String)  grb.getResiDiasAutomaticos()) && grb.getResiDiasAutomaticos()!=null && !grb.getResiDiasAutomaticos().equals(""))			
					listaDiasAutomaticos.add((String) grb.getResiDiasAutomaticos());
				Collections.sort(listaDiasAutomaticos);
				
				if(!listaRevisar.contains((String)  grb.getValidar()) && grb.getValidar()!=null && !grb.getValidar().equals(""))			
					listaRevisar.add((String) grb.getValidar());
				
				if(!listaRevisar.contains((String)  grb.getConfirmar()) && grb.getConfirmar()!=null && !grb.getConfirmar().equals(""))			
					listaRevisar.add((String) grb.getConfirmar());
				Collections.sort(listaConfirmar);
				
				listaRevisar.addAll(listaConfirmar);
				
				if(!listaSecuenciaGuide.contains((String)  grb.getSecuenciaGuide()) && grb.getSecuenciaGuide()!=null && !grb.getSecuenciaGuide().equals(""))			
					listaSecuenciaGuide.add((String) grb.getSecuenciaGuide());
				Collections.sort(listaSecuenciaGuide);

				if(!listaResiVariante.contains((String)  grb.getResiVariante()) && grb.getResiVariante()!=null && !grb.getResiVariante().equals(""))			
					listaResiVariante.add((String) grb.getResiVariante());
				Collections.sort(listaResiVariante);
				
				/*listaOrdenacion.add("CIP");
				listaOrdenacion.add("mensajes");
				listaOrdenacion.add("periodo");
				listaOrdenacion.add("spdCnFinal");
				listaOrdenacion.add("spdNombreBolsa");
				listaOrdenacion.add("CIP");
				listaOrdenacion.add("CIP");
				listaOrdenacion.add("CIP");
				*/
			}
	
			Iterator<FicheroResiBean> itProcesos = procesos.iterator();
			while (itProcesos.hasNext())
			{
				FicheroResiBean grb2 = (FicheroResiBean) itProcesos.next();
				if(!listaProcesosCargados.contains( grb2.getIdProceso()) && grb2.getIdProceso()!=null && !grb2.getIdProceso().equals(""))									
	         	listaProcesosCargados.add((String) grb2.getIdProceso());
	         	Collections.sort(listaProcesosCargados);
			}
		}


	public static List getTomasCabecera(FicheroResiBean cab) {

		return HeliumHelper.getTomasCabecera(cab);
	}

	/**
	 * En la importación de datos se busca la cabecera modelo de la residencia (CabecerasXLSBean), que estará actualizada según las últimas producciones y cambios de  ampliación de tomas
	 * En el caso de la gestión y listado de datos, se recupera la de la tabla bbdd ya relacionada con el detalle.
	 * En el caso de la carga de plantilla unificada se devuelve la del modelo de plantilla también.  En caso contrario se busca la cabecera por defecto.
	 * @param formulari
	 * @return
	 * @throws Exception 
	 */
	public static FicheroResiBean getCabeceraFicheroResi(String spdUsuario, String idDivisionResidencia, String idProceso, boolean recuperaPlantillaCabecera) throws Exception{
		return getCabeceraFicheroResi(spdUsuario, idDivisionResidencia, idProceso, recuperaPlantillaCabecera, false);
	}

	public static FicheroResiBean getCabeceraFicheroResi(String spdUsuario, String idDivisionResidencia, String idProceso, boolean recuperaPlantillaCabecera, boolean historico)throws Exception {

		FicheroResiBean bean =null;
		//Primero se busca en cabeceras
		if(recuperaPlantillaCabecera)
		{
			bean = CabecerasXLSDAO.getCabecerasXLS(spdUsuario, idDivisionResidencia);
			bean.setIdProceso(idProceso);
		}
		else
		{
			List <FicheroResiBean> listBean = FicheroResiDetalleDAO.getCabeceraFicheroResi(spdUsuario, idDivisionResidencia, idProceso, historico);
			if(listBean!=null && listBean.size()>0) 
				bean = (FicheroResiBean) listBean.get(0);
		}

	return bean;
	}

	
	
	
	public static List<Dose> getTomasCabeceraYHora(FicheroResiBean medResi) {
	    List<Dose> doses = new ArrayList<>();
	    try {
	        int numeroDoses = medResi.getNumeroDeTomas();
	        for (int i = 1; i <= numeroDoses && i <= 24; i++) {
	            // Construimos el nombre del método: getResiToma1, getResiToma2, ...
	            String methodName = "getResiToma" + i;
	            // Usamos reflexión para invocar el método correspondiente
	            Method method = medResi.getClass().getMethod(methodName);
	            Object tomaValue = method.invoke(medResi);

	            doses.add(DoseDAO.getTomaCabecera(
	                    medResi.getIdDivisionResidencia(),
	                    (String) tomaValue,
	                    0,
	                    i
	            ));
	        }
	    } catch (Exception e) {
	        // Manejo de errores según tu necesidad
	        e.printStackTrace();
	    }
	    return doses;
	}
	
	/*
	public static List getTomasCabeceraYHora(FicheroResiBean medResi) {
		List<Dose> doses =new ArrayList<Dose>();
		int numeroDoses=0;
		try{
			
			numeroDoses=medResi.getNumeroDeTomas();
			//if(numeroDoses>=1)  doses.add(DoseDAO.getDoseByFilters(medResi.getIdDivisionResidencia(), medResi.getResiToma1(), 0, 1)); //
			if(numeroDoses>=1)  doses.add(DoseDAO.getTomaCabecera(medResi.getIdDivisionResidencia(), medResi.getResiToma1(), 0, 1)); //
			if(numeroDoses>=2)  doses.add(DoseDAO.getTomaCabecera(medResi.getIdDivisionResidencia(), medResi.getResiToma2(), 0, 2)); //
			if(numeroDoses>=3)  doses.add(DoseDAO.getTomaCabecera(medResi.getIdDivisionResidencia(), medResi.getResiToma3(), 0, 3)); //
			if(numeroDoses>=4)  doses.add(DoseDAO.getTomaCabecera(medResi.getIdDivisionResidencia(), medResi.getResiToma4(), 0, 4)); //
			if(numeroDoses>=5)  doses.add(DoseDAO.getTomaCabecera(medResi.getIdDivisionResidencia(), medResi.getResiToma5(), 0, 5)); //
			if(numeroDoses>=6)  doses.add(DoseDAO.getTomaCabecera(medResi.getIdDivisionResidencia(), medResi.getResiToma6(), 0, 6)); //
			if(numeroDoses>=7)  doses.add(DoseDAO.getTomaCabecera(medResi.getIdDivisionResidencia(), medResi.getResiToma7(), 0, 7)); //
			if(numeroDoses>=8)  doses.add(DoseDAO.getTomaCabecera(medResi.getIdDivisionResidencia(), medResi.getResiToma8(), 0, 8)); //
			if(numeroDoses>=9)  doses.add(DoseDAO.getTomaCabecera(medResi.getIdDivisionResidencia(), medResi.getResiToma9(), 0, 9)); //
			if(numeroDoses>=10)  doses.add(DoseDAO.getTomaCabecera(medResi.getIdDivisionResidencia(), medResi.getResiToma10(), 0, 10)); //
			if(numeroDoses>=11)  doses.add(DoseDAO.getTomaCabecera(medResi.getIdDivisionResidencia(), medResi.getResiToma11(), 0, 11)); //
			if(numeroDoses>=12)  doses.add(DoseDAO.getTomaCabecera(medResi.getIdDivisionResidencia(), medResi.getResiToma12(), 0, 12)); //
			if(numeroDoses>=13)  doses.add(DoseDAO.getTomaCabecera(medResi.getIdDivisionResidencia(), medResi.getResiToma13(), 0, 13)); //
			if(numeroDoses>=14)  doses.add(DoseDAO.getTomaCabecera(medResi.getIdDivisionResidencia(), medResi.getResiToma14(), 0, 14)); //
			if(numeroDoses>=15)  doses.add(DoseDAO.getTomaCabecera(medResi.getIdDivisionResidencia(), medResi.getResiToma15(), 0, 15)); //
			if(numeroDoses>=16)  doses.add(DoseDAO.getTomaCabecera(medResi.getIdDivisionResidencia(), medResi.getResiToma16(), 0, 16)); //
			if(numeroDoses>=17)  doses.add(DoseDAO.getTomaCabecera(medResi.getIdDivisionResidencia(), medResi.getResiToma17(), 0, 17)); //
			if(numeroDoses>=18)  doses.add(DoseDAO.getTomaCabecera(medResi.getIdDivisionResidencia(), medResi.getResiToma18(), 0, 18)); //
			if(numeroDoses>=19)  doses.add(DoseDAO.getTomaCabecera(medResi.getIdDivisionResidencia(), medResi.getResiToma19(), 0, 19)); //
			if(numeroDoses>=20)  doses.add(DoseDAO.getTomaCabecera(medResi.getIdDivisionResidencia(), medResi.getResiToma20(), 0, 20)); //
			if(numeroDoses>=21)  doses.add(DoseDAO.getTomaCabecera(medResi.getIdDivisionResidencia(), medResi.getResiToma21(), 0, 21)); //
			if(numeroDoses>=22)  doses.add(DoseDAO.getTomaCabecera(medResi.getIdDivisionResidencia(), medResi.getResiToma22(), 0, 22)); //
			if(numeroDoses>=23)  doses.add(DoseDAO.getTomaCabecera(medResi.getIdDivisionResidencia(), medResi.getResiToma23(), 0, 23)); //
			if(numeroDoses>=24)  doses.add(DoseDAO.getTomaCabecera(medResi.getIdDivisionResidencia(), medResi.getResiToma24(), 0, 24)); //
		}catch(Exception e){
			
		}
			return doses;
	}
*/

	/**
	 * si llega CIP vacío el método busca algún CIP del mismo nombre+apellidos 
	 * Hace update en todos los vacíos y en caso de no encontrar se construye uno con el nombre + apellidos. En caso de error se añade un CIP temporal
	 * @param frbean
	 * @throws Exception 
	 */
	public static void actualizaCIP(String spdUsuario, FicheroResiBean frbean) throws Exception {
		
		//primero buscamos si existe un CIP con el mismo nombre EXACTO en esta misma producción
		String CIP = FicheroResiDetalleDAO.getCIPPorNombreCompleto(spdUsuario, frbean);
		boolean resultOk = false; 
		
		if(CIP==null || CIP.equals("")) 
		{
			//formato CIP con el nombre apellidos
			CIP = StringUtil.limpiarTextoyEspacios(frbean.getResiApellidosNombre().toUpperCase()).substring(0, 10);
			if(CIP==null || CIP.equals("")) 
				CIP="SIN_CIP" + new Date().getTime();
			
		} 
		frbean.setResiCIP(CIP);

		try
		{
			resultOk = FicheroResiDetalleDAO.updateCIPVacios(spdUsuario, frbean);
			//frbean.getMensajesInfo().replaceAll(SPDConstants.INFO_RESI_SIN_CIP_AVISO, "");
			frbean.getMensajesResidencia().replaceAll(SPDConstants.INFO_RESI_SIN_CIP_AVISO, "");
			
			//frbean.setMensajesInfo(SPDConstants.INFO_SIN_CIP_ARREGLO);
			frbean.setMensajesResidencia(SPDConstants.INFO_RESI_SIN_CIP_ARREGLO);
			frbean.setValidar(SPDConstants.REGISTRO_VALIDAR);
			frbean.setConfirmar(SPDConstants.REGISTRO_CONFIRMAR);
		}catch(Exception e)
		{
			
		}
	}



	public static void nuevo(String idDivisionResidencia, String idProceso, FicheroResiBean medResi) throws Exception {
		FicheroResiDetalleDAO.nuevo(medResi.getIdDivisionResidencia(), medResi.getIdProceso(), medResi);
	// ya no hace falta	FicheroResiDetalleDAO.actualizaIdSpd(medResi);
		
	}


	public static FicheroResiBean getFicheroResiDetalleByIdOid(FicheroResiForm formulari, String idUsuario) throws Exception {
		
		FicheroResiBean  frb = FicheroResiDetalleDAO.getFicheroResiDetalleByIdOid(idUsuario, formulari.getOidFicheroResiDetalle());
		//recuperamos la cabecera del detalle
		/*List cabs = FicheroResiCabeceraDAO.getGestFicheroResi(idUsuario, formulari, 0, 1, null, false);
		FicheroResiBean cab =null;
		try {cab =(FicheroResiBean)cabs.get(0);}catch(Exception e){}
		//frb.setFicheroCabecera(cab);
		*/
		return frb;
	}
	
	public static FicheroResiBean getFicheroResiDetalleByIdOid(String idUsuario, int oidFicheroResiDetalle) throws Exception {
		
		FicheroResiBean  frb = FicheroResiDetalleDAO.getFicheroResiDetalleByIdOid(idUsuario, oidFicheroResiDetalle);
	
		return frb;
	}


	public static void checkEstadoCabecera(String idUser, FicheroResiBean cab, FicheroResiBean cabeceraGeneral) throws Exception {
		
	//	FicheroResiBean cabecera=FicheroResiCabeceraDAO.getFicheroResiCabeceraByOid(idUser, cab.getOidFicheroResiCabecera());
	//	FicheroResiBean cabeceraTop=FicheroResiCabeceraDAO.getFicheroResiCabeceraByOid(idUser, cabecera.getOidFicheroResiCabecera());
		String idEstado = cabeceraGeneral.getIdEstado();
		
		int validar = FicheroResiDetalleDAO.countValidacionesPendientes(cab);
		switch (idEstado) {
		case SPDConstants.SPD_PROCESO_3_VALIDADO:
			if(validar>0)
				FicheroResiCabeceraDAO.actualizaEstadoIdProceso(idUser, cab.getIdDivisionResidencia(), cab.getIdProceso(), SPDConstants.SPD_PROCESO_2_PENDIENTE_VALIDAR);
			
			break;
		case SPDConstants.SPD_PROCESO_2_PENDIENTE_VALIDAR:
			if(validar==0)
				FicheroResiCabeceraDAO.actualizaEstadoIdProceso(idUser, cab.getIdDivisionResidencia(), cab.getIdProceso(), SPDConstants.SPD_PROCESO_3_VALIDADO);
		
			break;
		default:
			break;
		}
		
	}


	public static void borrarTratamientosCIPEnProceso(String spdUsuario, FicheroResiBean medResi) throws Exception {
		FicheroResiDetalleDAO.limpiarCIPIdproceso(spdUsuario, medResi,  false);
		
	}


	public static int getCabeceraFicheroResi(String spdUsuario, String idDivisionResidencia, String idProceso) throws Exception {
		List cabeceras = FicheroResiDetalleDAO.getCabeceraFicheroResi(spdUsuario, idDivisionResidencia, idProceso);
		int oid=-1;
		if(cabeceras!=null && cabeceras.size()>0)
		{
			FicheroResiBean cab = (FicheroResiBean)cabeceras.get(0);
			if(cab!=null) oid=cab.getOidFicheroResiCabecera();
		}
		return oid;
	}
	
	public static boolean editaFinCargaFicheroResi(String spdUsuario, String idDivisionResidencia, String idProceso, int processedRows,  int cipsTotales, int cipsActivosSPD, int porcent, List errors)  throws Exception {
		String resumenCIPSFichero = getResumenCIPSFichero(spdUsuario, idDivisionResidencia, idProceso);
		return FicheroResiCabeceraDAO.editaFinCargaFicheroResi(idDivisionResidencia, idProceso, processedRows,  cipsTotales, cipsActivosSPD, porcent, errors, resumenCIPSFichero);
	}
	public static String getResumenCIPSFichero(String spdUsuario, String idDivisionResidencia, String idProceso) throws Exception
	{
		List<PacienteBean> cipsFicheroSiGestionSPDNo = FicheroResiDetalleDAO.getCipsFicheroSiGestionSPDNo(spdUsuario, idProceso );
		List<PacienteBean> cipsFicheroSiGestionNo = FicheroResiDetalleDAO.getCipsFicheroSiGestionNo(spdUsuario, idDivisionResidencia, idProceso );
		List<PacienteBean> cipsFicheroNoGestionSi = FicheroResiDetalleDAO.getCipsFicheroNoGestionSi(spdUsuario, idDivisionResidencia, idProceso );
	
		String result = "";
		if(cipsFicheroSiGestionSPDNo!=null && cipsFicheroSiGestionSPDNo.size()>0)
		{
			result+="<span class=''textoRojo''><b>Fichero SI  -  Gestión SI pero SPD=''N''</b></span><br/> <ul>";
			Iterator it_1 = cipsFicheroSiGestionSPDNo.iterator();
			while(it_1.hasNext())
			{
				PacienteBean pac1 = (PacienteBean) it_1.next();
				result+= "<li>"+pac1.getCIP() + " - " + StringUtil.limpiarTextoComentarios(pac1.getApellidosNombre()) + "</li>";
				//System.out.println("1 - Se añade Diferencia CIP: " + pac1.getCIP() + " - " + StringUtil.limpiarTextoComentarios(pac1.getApellidosNombre()));
			}
			result+="</ul><br/>";
		}
			
		if(cipsFicheroSiGestionNo!=null && cipsFicheroSiGestionNo.size()>0)
		{
			result+="<span class=''textoRojo''><b>Fichero SI - Gestión NO</b></span><br/> <ul>";
			Iterator it_2 = cipsFicheroSiGestionNo.iterator();
			while(it_2.hasNext())
			{
				PacienteBean pac2 = (PacienteBean) it_2.next();
				result+= "<li>"+pac2.getCIP() + " - " + StringUtil.limpiarTextoComentarios(pac2.getApellidosNombre()) + "</li>";
				//System.out.println("2 - Se añade Diferencia CIP: " + pac2.getCIP() + " - " + StringUtil.limpiarTextoComentarios(pac2.getApellidosNombre()));
			}
			result+="</ul><br/>";
		}
			
		if(cipsFicheroNoGestionSi!=null && cipsFicheroNoGestionSi.size()>0)
		{
			result+="<span class=''textoRojo''><b>Fichero NO - Gestión SI:</b></span><br/> <ul>";
			Iterator it_3 = cipsFicheroNoGestionSi.iterator();
			while(it_3.hasNext())
			{
				PacienteBean pac3 = (PacienteBean) it_3.next();
				result+= "<li>"+pac3.getCIP() + " - " + StringUtil.limpiarTextoComentarios(pac3.getApellidosNombre()) + "</li>";
				//System.out.println("3 - Se añade Diferencia CIP: " + pac3.getCIP() + " - " + StringUtil.limpiarTextoComentarios(pac3.getApellidosNombre()));
			}
			result+="</ul><br/>";
			
		}
		
		return result;
	}


	public static boolean checkFechasCabecera(FicheroResiBean cab, String fDesde, String fHasta) throws SQLException {
		boolean check =true;
		if(cab.getFechaDesde()==null || cab.getFechaDesde().equals(""))
		{
			cab.setFechaDesde(fDesde);
			check=false;
		}
		if(cab.getFechaHasta()==null || cab.getFechaHasta().equals(""))
		{
			cab.setFechaHasta(fHasta);
			check=false;
		}

		
		if(!check) check=FicheroResiCabeceraDAO.editaFechas(cab);
		return check;
	}


	public static List<InfoAlertasBean> detectarAlertas(String idUsuario, FicheroResiBean frbean) throws Exception {
		List<InfoAlertasBean> listInfoAlertas = new ArrayList<InfoAlertasBean>();
		if(frbean!=null)
		{
			// C - (Número comprimidos)
			InfoAlertasBean infoAlertas = new InfoAlertasBean();
			infoAlertas.setTituloAlerta("C - (Número comprimidos) ");
			if(frbean.getControlNumComprimidos()!=null && frbean.getControlNumComprimidos().equalsIgnoreCase(SPDConstants.CTRL_NCOMPRIMIDOS_IGUAL))
			{
				infoAlertas.setCssAlerta("verde");
				infoAlertas.setTextoAlerta("Coincide  la previsión de comprimidos según fichero de la residencia (Previsión --> "+frbean.getPrevisionResi()+ ") y lo que se envía a robot (Previsión --> "+frbean.getPrevisionSPD()+ ") ");
			}
			else if(frbean.getControlNumComprimidos()!=null && frbean.getControlNumComprimidos().equalsIgnoreCase(SPDConstants.CTRL_NCOMPRIMIDOS_DIFERENTE))
			{
				infoAlertas.setCssAlerta("rojo");
				infoAlertas.setTextoAlerta("ALERTA - Comprobar comprimidos fichero de la residencia (Previsión --> "+frbean.getPrevisionResi()+ ") y lo que se envía a robot (Previsión --> "+frbean.getPrevisionSPD()+ ") ");
			}
			else
			{
				infoAlertas.setCssAlerta("naranja");
				infoAlertas.setAlertaNumComprimidos("No se detecta el número de comprimidos según fichero o es un tratamiento que no afecta a SPD ");
			}
			listInfoAlertas.add(infoAlertas);
			
			// I - (Registro anterior) 
			infoAlertas = new InfoAlertasBean();
			infoAlertas.setTituloAlerta("I - (Registro anterior) ");
			if(frbean.getControlRegistroAnterior()!=null && frbean.getControlRegistroAnterior().equalsIgnoreCase(SPDConstants.CTRL_REGISTRO_ANTERIOR_RI_SI))
			{
				infoAlertas.setCssAlerta("verde");
				infoAlertas.setTextoAlerta(" Registro reutilizado que coincide 100% con el anterior");
			}
			else if(frbean.getControlRegistroAnterior()!=null && frbean.getControlRegistroAnterior().equalsIgnoreCase(SPDConstants.CTRL_REGISTRO_ANTERIOR_RD_SI))
			{
				FicheroResiBean medResiAnterior = HelperSPD.recuperaDatosAnteriores(idUsuario, frbean, true);
				
				
				infoAlertas.setCssAlerta("naranja");
				infoAlertas.setTextoAlerta("ALERTA.- Revisar registro, la salida es igual que la anterior, pero los datos de la resi son diferentes. <br>" 
				+ "<br>  ANTERIOR --> " + medResiAnterior.getDetalleRow() 
				+ "<br>  ACTUAL------> " + frbean.getDetalleRow());
			}
			else if(frbean.getControlRegistroAnterior()!=null && frbean.getControlRegistroAnterior().equalsIgnoreCase(SPDConstants.CTRL_REGISTRO_ANTERIOR_RI_SD))
			{
				FicheroResiBean medResiAnterior = HelperSPD.recuperaDatosAnteriores(idUsuario, frbean, true);
				
				infoAlertas.setCssAlerta("rojo");
				if(medResiAnterior!=null)
				{
					infoAlertas.setTextoAlerta("ALERTA.-  REVISAR bien el tratamiento. Se envía diferente a la anterior producción. <br>"
							+ "<br>  ANTERIOR --> " + medResiAnterior.getIdTratamientoSPD() 
							+ "<br>  ACTUAL------> " + frbean.getIdTratamientoSPD());
				}
					
			}
			else if(frbean.getControlRegistroAnterior()!=null && frbean.getControlRegistroAnterior().equalsIgnoreCase(SPDConstants.CTRL_REGISTRO_ANTERIOR_RD_SD))
			{
				infoAlertas.setCssAlerta("azul");
				infoAlertas.setTextoAlerta("Registro nuevo");
			}
			listInfoAlertas.add(infoAlertas);
			
			/*
			// R - (envío a robot) 
			infoAlertas = new InfoAlertasBean();
			infoAlertas.setTituloAlerta("R - (envío a robot) ");
			if(frbean.getControlRegistroRobot()!=null && frbean.getControlRegistroRobot().equalsIgnoreCase(SPDConstants.CTRL_ROBOT_SE_ENVIA_A_ROBOT))
			{
				infoAlertas.setCssAlerta("verde");
				infoAlertas.setTextoAlerta("Se envía a robot como '" + frbean.getSpdAccionBolsa()+"'");
			}
			else if(frbean.getControlRegistroRobot()!=null && frbean.getControlRegistroRobot().equalsIgnoreCase(SPDConstants.CTRL_ROBOT_NO_SE_ENVIA))
			{
				infoAlertas.setCssAlerta("gris");
				infoAlertas.setTextoAlerta("NO se envía a robot porque es '" + frbean.getSpdAccionBolsa()+"'");
			}
			else 
			{
				infoAlertas.setCssAlerta("blanco");
				infoAlertas.setTextoAlerta("Revisar acción en bolsa del tratamiento");
			}		
			listInfoAlertas.add(infoAlertas);
			*/
			/*
			// D - (Validar datos)
			infoAlertas = new InfoAlertasBean();
			infoAlertas.setTituloAlerta("D - (Validar datos) ");
			if(frbean.getControlValidacionDatos()!=null && frbean.getControlValidacionDatos().equalsIgnoreCase(SPDConstants.CTRL_VALIDAR_NO))
			{
				infoAlertas.setCssAlerta("verde");
				infoAlertas.setTextoAlerta("Registro ok");
			}
			else if(frbean.getControlValidacionDatos()!=null && frbean.getControlValidacionDatos().equalsIgnoreCase(SPDConstants.CTRL_VALIDAR_ALERTA))
			{
				infoAlertas.setCssAlerta("naranja");
				infoAlertas.setTextoAlerta("Necesaria revisión de datos'");
			}
			else 
			{
				infoAlertas.setCssAlerta("blanco");
				infoAlertas.setTextoAlerta("No detectado");
			}		

			listInfoAlertas.add(infoAlertas);
			
			*/
			/*
			// P - Control de principio activo  
			infoAlertas = new InfoAlertasBean();
			infoAlertas.setTituloAlerta("P (Principio activo) ");
			if(frbean.getControlPrincipioActivo()!=null && frbean.getControlPrincipioActivo().equalsIgnoreCase(SPDConstants.CTRL_PRINCIPIO_ACTIVO_NO_ALERTA))
			{
				infoAlertas.setCssAlerta("verde");
				infoAlertas.setTextoAlerta("Registro ok");
				
			}
			else if(frbean.getControlPrincipioActivo()!=null && frbean.getControlPrincipioActivo().equalsIgnoreCase(SPDConstants.CTRL_PRINCIPIO_ACTIVO_ALERTA))
			{
				infoAlertas.setCssAlerta("amarillo");
				infoAlertas.setTextoAlerta("El principio activo de este tratamiento está marcado para CONTROL EXTRA  '" + frbean.getSpdNomGtVm()+"'");
			}
			else 
			{
				infoAlertas.setCssAlerta("blanco");
				infoAlertas.setTextoAlerta("No detectado");
			}
			listInfoAlertas.add(infoAlertas);

			*/
		    
			// S - Control de medicamento sustituible  
			infoAlertas = new InfoAlertasBean();
			infoAlertas.setTituloAlerta("S - Control de medicamento sustituible   ");
			if(frbean.getControlNoSustituible()!=null && frbean.getControlNoSustituible().equalsIgnoreCase(SPDConstants.CTRL_SUSTITUIBLE_NOALERTA))
			{
				infoAlertas.setCssAlerta("verde");
				infoAlertas.setTextoAlerta("Registro ok  ");
			}
			else if(frbean.getControlNoSustituible()!=null && frbean.getControlNoSustituible().equalsIgnoreCase(SPDConstants.CTRL_SUSTITUIBLE_ALERTA))
			{
				infoAlertas.setCssAlerta("rojo");
				infoAlertas.setTextoAlerta("Control medicamento NO sustituible  ");
			}
			else 
			{
				infoAlertas.setCssAlerta("blanco");
				infoAlertas.setTextoAlerta("No detectado");
			}
			listInfoAlertas.add(infoAlertas);

				
			// G - Control de GTVMP iguales  
			infoAlertas = new InfoAlertasBean();
			infoAlertas.setTituloAlerta("G - Control de GTVMP iguales  ");
			 if(frbean.getControlDiferentesGtvmp()!=null && frbean.getControlDiferentesGtvmp().equalsIgnoreCase(SPDConstants.CTRL_DIFERENTE_GTVMP_OK))
			{
				infoAlertas.setCssAlerta("verde");
				infoAlertas.setTextoAlerta("GTVMP ok ");
			}
			else if(frbean.getControlDiferentesGtvmp()!=null && frbean.getControlDiferentesGtvmp().equalsIgnoreCase(SPDConstants.CTRL_DIFERENTE_GTVMP_ALERTA))
			{
				infoAlertas.setCssAlerta("rojo");
				infoAlertas.setTextoAlerta(" El medicamento SPD tiene diferente GTVMP  que el de la residencia ");
			}
			else 
			{
				infoAlertas.setCssAlerta("blanco");
				infoAlertas.setTextoAlerta("No detectado");
			}

			listInfoAlertas.add(infoAlertas);

			// V - Control de GTVM ÚNICOS (para detectar tratamientos con el mismo GTVM) 
			infoAlertas = new InfoAlertasBean();
			infoAlertas.setTituloAlerta("V - Control de principio activo repetido");
			 if(frbean.getControlUnicoGtvm()!=null && frbean.getControlUnicoGtvm().equalsIgnoreCase(SPDConstants.CTRL_UNICO_GTVM_OK))
			{
				infoAlertas.setCssAlerta("verde");
				infoAlertas.setTextoAlerta("GTVM ok ");
			}
			else if(frbean.getControlUnicoGtvm()!=null && frbean.getControlUnicoGtvm().equalsIgnoreCase(SPDConstants.CTRL_UNICO_GTVM_ALERTA))
			{
				infoAlertas.setCssAlerta("rojo");
				infoAlertas.setTextoAlerta(" El residente tiene asignado más de un medicamento con este mismo principio activo ");
			}
			else 
			{
				infoAlertas.setCssAlerta("blanco");
				infoAlertas.setTextoAlerta("No detectado");
			}

			listInfoAlertas.add(infoAlertas);
			
		}
		return listInfoAlertas;
	}
		
}
	


	
	
	
	
	
	
	
	
	
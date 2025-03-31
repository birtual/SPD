
package lopicost.spd.struts.form;

import lopicost.spd.utils.SPDConstants;

import java.util.ArrayList;
import java.util.List;


import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;


public class GenericForm extends ActionForm 
{	
	private int numpages=-1;
	private int currpage=0;
	private String cleanCache;
	//private String actiontodo = null; // Constant que identifica la acció a executar.listaDivisionResidencia
	private String sessionid = null;
	private String contextPath= null;

	private String campoGoogle = "";	//para búsquedas
	
	private String idUsuario = "";
	private String password = "";
	private String idProceso = "";
	private String ACTIONTODO = "";
	private String parameter = "";
	private String s;
	private String mode= "";
	private String serviceId= "";
	private String messageAlert;
	private boolean mostrarMensaje=false;
	
	private String fieldName1 = " ";
	private int oidDivisionResidenciaFiltro = 0;
	private String idDivisionResidenciaFiltro = "";
	private int oidDivisionResidencia = 0;
	private String idDivisionResidencia = "";
	private String nombreDivisionResidencia = "";

	private String[] oidVariasDivisionResidencia;
	private int oidGestSustituciones = -1;
	private String oidPaciente = "";
	
	//	private String filtroProcesoFichero="";
	//	private String filtroIdEstadoProcesoFichero="";
	

	private String campoOrder = "";

	private boolean filtroVerDatosPersonales=false; 	//por defecto NO salen asteriscos (TRUE).  SI se quiere que salgan debería ser FALSE 

	
	private String idTipoAccion= "";
	private String nombreLab = "";
	private String nombreMedicamento = "";
	private String cn = "";
	private String idProcessIospd="list";
	
	
	//listas 
	private List<?> errors;
	private List<?> avisos;
	private List<?>  listaBdConsejo;
	private List<?>  listaConjuntosHomogeneos;
	private List<?>  listaGtVmp;

	private String idRobot 	= "";
	private String filtroRobot 	= "";						
	private List<?>  listaRobots;

	private List<?>  listaGtVmpp;
	private List<?>  listaLabs;
	private List<?>  listaLabsBdConsejo;
	private List<?>  listaPresentacion;
	private List<?>  listaGtVm;				//P activo
	private List<?>  listaBdPacientes;
	private List<?>  listaDivisionResidencia;
	private List<?>  listaFormasFarmaceuticas;

	private List<?>  listaSustituciones;
	private List<?>  listaMedicamentoResi;
	private List<?>  listaNombreCortoOK;

	private List<?>  listaBeans;

	public String getCampoGoogle() {
		return campoGoogle;
	}

	public void setCampoGoogle(String campoGoogle) {
		this.campoGoogle = campoGoogle;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getNumpages()
	{
		return numpages;
	}
	
	public void setNumpages(int numpages)
	{
		this.numpages= numpages;
	}

	public int getCurrpage()
	{
		return currpage;
	}
	
	public void setCurrpage(int currpage)
	{
		this.currpage= currpage;
	}
	
	public String getCleanCache()
	{
		return cleanCache;
	}
	
	public void setCleanCache(String cleanCache)
	{
		this.cleanCache = cleanCache;
	}

	public String getIdProceso() {
		return idProceso;
	}

	public void setIdProceso(String idProceso) {
		this.idProceso = idProceso;
	}

	/**
	 * @return
	public String getActiontodo() {
		return actiontodo;
	}
	 */

	/**
	 * @param string
	public void setActiontodo(String string) {
		actiontodo = string;
	}
*/
	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public String getSessionid() {
		return sessionid;
	}

	/**
	 * @return
	 */
	public String getContextPath() {
		return contextPath;
	}

	/**
	 * @param string
	 */
	public void setContextPath(String string) {
		contextPath = string;
	}

	/**
	 * @return
	 */
	public String getS() {
		return s;
	}

	/**
	 * @return
	 */
	public String getS(HttpServletRequest request) 
	{
		if (request.getParameter("sessionid")!=null) {
			request.getSession().setAttribute("s",request.getParameter("sessionid"));
		} else if (request.getParameter("s")!=null) {
			request.getSession().setAttribute("s",request.getParameter("s"));
		}
			
			
		if (request.getSession().getAttribute("s") == null) 
		{		
			request.getSession().setAttribute("s",request.getParameter("sessionid"));
			s = (String) request.getParameter("sessionid");
		} else {
			s = (String) request.getSession().getAttribute("s");
		}
		return s;
	}

	/**
	 * @param string
	 */
	public void setS(String string) {
		s = string;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getMode() {
		return mode;
	}
/*
	public void setExtendedFilters(TreeMap extendedFilters) {
		this.extendedFilters = extendedFilters;
	}

	public TreeMap getExtendedFilters() {
		return extendedFilters;
	}
*/
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceId() {
		return serviceId;
	}


	/**
	 * @return Returns the messageAlert.
	 */
	public String getMessageAlert() {
		String text="";
		if (this.isMostrarMensaje())
			text=messageAlert==null?"":messageAlert;
		return text;
	}
	/**
	 * @param messageAlert The messageAlert to set.
	 */
	public void setMessageAlert(String messageAlert) {
		this.messageAlert = messageAlert;
	}
	/**
	 * @return Returns the mostrarMensaje.
	 */
	public boolean isMostrarMensaje() {
		return mostrarMensaje;
	}
	/**
	 * @param mostrarMensaje The mostrarMensaje to set.
	 */
	public void setMostrarMensaje(boolean mostrarMensaje) {
		this.mostrarMensaje = mostrarMensaje;
	}
	
	/**
	 * Mètode per modificar, o iniciar els valors del paginador i escollir els elements de la llista necessaris
	 * @param listado Llistat complert
	 * @return Llistat paginat
	 */
	public List paginar(java.util.List listado) {

		List pagedList = new ArrayList();
								
		int elementsNumber = 0;
		if (listado != null)
			elementsNumber = listado.size();
					
		int numberPages = 0;	
									
		if (elementsNumber % SPDConstants.PAGE_ROWS != 0) {
			numberPages = elementsNumber / SPDConstants.PAGE_ROWS + 1;
		} else {
			numberPages = elementsNumber / SPDConstants.PAGE_ROWS;	
		}
	
		if (this.getNumpages() != numberPages) {	// El listado ha cambiado de numero de elementos. Recalculamos
			this.setNumpages(numberPages);
			this.setCurrpage(0);
		}

		int begin = this.getCurrpage() * SPDConstants.PAGE_ROWS;
		int end = (this.getCurrpage() + 1) * SPDConstants.PAGE_ROWS; 

		for (int i=begin ;listado != null && i<end && i<listado.size(); i++){
			pagedList.add(listado.get(i));
		}
		
		return pagedList;
	}
	/**
	 * @return
	 */
/*	public List getExtendedFiltersList() {
		return extendedFiltersList;
	}

	/**
	 * @param list
	 */
/*	public void setExtendedFiltersList(List list) {
		extendedFiltersList = list;
	}
	
	/*
	public Security getSecurity() {
		return security;
	}
	public void setSecurity(Security sec) {
		security = sec;
	}

	*/
	
	public String getACTIONTODO() {
		return ACTIONTODO;
	}

	public void setACTIONTODO(String aCTIONTODO) {
		ACTIONTODO = aCTIONTODO;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public List getErrors() {
		return errors;
	}

	public void setErrors(List errors) {
		this.errors = errors;
	}

	public List<?> getAvisos() {
		return avisos;
	}

	public void setAvisos(List<?> avisos) {
		this.avisos = avisos;
	}

	public String getFieldName1() {
		return fieldName1;
	}

	public void setFieldName1(String fieldName1) {
		this.fieldName1 = fieldName1;
	}

	public List getListaBdConsejo() {
		return listaBdConsejo;
	}

	public void setListaBdConsejo(List listaBdConsejo) {
		this.listaBdConsejo = listaBdConsejo;
	}

	public int getOidDivisionResidencia() {
		return oidDivisionResidencia;
	}

	public void setOidDivisionResidencia(int oidDivisionResidencia) {
		this.oidDivisionResidencia = oidDivisionResidencia;
	}

	public String getIdTipoAccion() {
		return idTipoAccion;
	}

	public void setIdTipoAccion(String idTipoAccion) {
		this.idTipoAccion = idTipoAccion;
	}

	public String getIdDivisionResidencia() {
		return idDivisionResidencia;
	}

	public void setIdDivisionResidencia(String idDivisionResidencia) {
		this.idDivisionResidencia = idDivisionResidencia;
	}

	public String getNombreDivisionResidencia() {
		return nombreDivisionResidencia;
	}

	public void setNombreDivisionResidencia(String nombreDivisionResidencia) {
		this.nombreDivisionResidencia = nombreDivisionResidencia;
	}

	public String getNombreLab() {
		return nombreLab;
	}

	public void setNombreLab(String nombreLab) {
		this.nombreLab = nombreLab;
	}

	public String getNombreMedicamento() {
		return nombreMedicamento;
	}

	public void setNombreMedicamento(String nombreMedicamento) {
		this.nombreMedicamento = nombreMedicamento;
	}

	public int getOidGestSustituciones() {
		return oidGestSustituciones;
	}

	public void setOidGestSustituciones(int oidGestSustituciones) {
		this.oidGestSustituciones = oidGestSustituciones;
	}

	public List getListaConjuntosHomogeneos() {
		return listaConjuntosHomogeneos;
	}

	public void setListaConjuntosHomogeneos(List listaConjuntosHomogeneos) {
		this.listaConjuntosHomogeneos = listaConjuntosHomogeneos;
	}

	public List getListaGtVmp() {
		return listaGtVmp;
	}

	public void setListaGtVmp(List listaGtVmp) {
		this.listaGtVmp = listaGtVmp;
	}

	public List getListaLabs() {
		return listaLabs;
	}

	public void setListaLabs(List listaLabs) {
		this.listaLabs = listaLabs;
	}

	public List getListaLabsBdConsejo() {
		return listaLabsBdConsejo;
	}

	public void setListaLabsBdConsejo(List listaLabsBdConsejo) {
		this.listaLabsBdConsejo = listaLabsBdConsejo;
	}

	public List getListaPresentacion() {
		return listaPresentacion;
	}

	public void setListaPresentacion(List listaPresentacion) {
		this.listaPresentacion = listaPresentacion;
	}

	public List getListaGtVm() {
		return listaGtVm;
	}

	public void setListaGtVm(List listaGtVm) {	
		this.listaGtVm = listaGtVm;
	}

	public List getListaBdPacientes() {
		return listaBdPacientes;
	}

	public void setListaBdPacientes(List listaBdPacientes) {
		this.listaBdPacientes = listaBdPacientes;
	}

	public List getListaDivisionResidencia() {
		return listaDivisionResidencia;
	}

	public List getListaFormasFarmaceuticas() {
		return listaFormasFarmaceuticas;
	}

	public void setListaFormasFarmaceuticas(List listaFormasFarmaceuticas) {
		this.listaFormasFarmaceuticas = listaFormasFarmaceuticas;
	}

	public List getListaSustituciones() {
		return listaSustituciones;
	}

	public void setListaSustituciones(List listaSustituciones) {
		this.listaSustituciones = listaSustituciones;
	}

	public List getListaMedicamentoResi() {
		return listaMedicamentoResi;
	}

	public void setListaMedicamentoResi(List listaMedicamentoResi) {
		this.listaMedicamentoResi = listaMedicamentoResi;
	}

	public List getListaNombreCortoOK() {
		return listaNombreCortoOK;
	}

	public void setListaNombreCortoOK(List listaNombreCortoOK) {
		this.listaNombreCortoOK = listaNombreCortoOK;
	}

	public List<?> getListaGtVmpp() {
		return listaGtVmpp;
	}

	public void setListaGtVmpp(List<?> listaGtVmpp) {
		this.listaGtVmpp = listaGtVmpp;
	}

	public String getCampoOrder() {
		return campoOrder;
	}

	public void setCampoOrder(String campoOrder) {
		this.campoOrder = campoOrder;
	}

	public void setListaDivisionResidencia(List<?> listaDivisionResidencia) {
		this.listaDivisionResidencia = listaDivisionResidencia;
	}

	public String getCn() {
		return cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	public List<?> getListaRobots() {
		return listaRobots;
	}

	public void setListaRobots(List<?> listaRobots) {
		this.listaRobots = listaRobots;
	}

	public String getIdRobot() {
		return idRobot;
	}

	public void setIdRobot(String idRobot) {
		this.idRobot = idRobot;
	}

	public String getFiltroRobot() {
		return filtroRobot;
	}

	public void setFiltroRobot(String filtroRobot) {
		this.filtroRobot = filtroRobot;
	}

	public String[] getOidVariasDivisionResidencia() {
		return oidVariasDivisionResidencia;
	}

	public void setOidVariasDivisionResidencia(String[] oidVariasDivisionResidencia) {
		this.oidVariasDivisionResidencia = oidVariasDivisionResidencia;
	}

	public String getIdProcessIospd() {
		return idProcessIospd;
	}

	public void setIdProcessIospd(String idProcessIospd) {
		this.idProcessIospd = idProcessIospd;
	}

	public List<?> getListaBeans() {
		return listaBeans;
	}

	public void setListaBeans(List<?> listaBeans) {
		this.listaBeans = listaBeans;
	}

	public int getOidDivisionResidenciaFiltro() {
		return oidDivisionResidenciaFiltro;
	}

	public void setOidDivisionResidenciaFiltro(int oidDivisionResidenciaFiltro) {
		this.oidDivisionResidenciaFiltro = oidDivisionResidenciaFiltro;
	}

	public String getIdDivisionResidenciaFiltro() {
		return idDivisionResidenciaFiltro;
	}

	public void setIdDivisionResidenciaFiltro(String idDivisionResidenciaFiltro) {
		this.idDivisionResidenciaFiltro = idDivisionResidenciaFiltro;
	}

	public String getOidPaciente() {
		return oidPaciente;
	}

	public void setOidPaciente(String oidPaciente) {
		this.oidPaciente = oidPaciente;
	}


	public boolean isFiltroVerDatosPersonales() 		{return filtroVerDatosPersonales;			}	public void setFiltroVerDatosPersonales(boolean dato)		{	this.filtroVerDatosPersonales = dato;		}

	
}
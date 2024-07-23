package lopicost.spd.struts.form;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import lopicost.spd.struts.bean.CamposPantallaBean;
import lopicost.spd.struts.bean.FicheroResiNewBean;
import lopicost.spd.struts.bean.TiposAccionBean;


public class FicheroResiNewForm   extends GenericForm {

	
	private int oidFicheroResiDetalle = 0;	
	private int oidFicheroResiCabecera = 0;	

	private List<FicheroResiNewBean> listaFicheroResiCabeceraBean = new ArrayList();
	private List<FicheroResiNewBean> todaLaListaFicheroResiCabeceraBean = new ArrayList();
	
//	private FicheroResiCabeceraBean gestFicheroResiNewBean = new FicheroResiCabeceraBean();
	
	private FicheroResiNewBean ficheroResiCabeceraBean = new FicheroResiNewBean();


	private List<FicheroResiNewBean> listaFicheroResiDetalleBean = new ArrayList();
	private List<FicheroResiNewBean> todaLaListaFicheroResiDetalleBean = new ArrayList();
	private List<FicheroResiNewBean> listaDivisionResidenciasCargadas = new ArrayList();
	private List<FicheroResiNewBean> listaEstadosCabecera = new ArrayList();
	
	
	private String filtroEstado = "";
	private String filtroProceso = "";
	private String filtroMedicamentoResi  = "";
	private String filtroNombreCortoOK  = "";
	private String filtroGtVm  = "";		//P Activo
	private String filtroPresentacion  = "";
	private String filtroNombreCorto = "";
	private String filtroDivisionResidenciasCargadas = "";
	private String filtroEstados = "PENDIENTE_VALIDAR";
	
	private boolean procesoValido=true;
	private boolean soloCabecera=false;
	private boolean excluirCabecera=false;
	
	
	
	private int oidDivisionResidenciaFiltro =  0;
	
	
	private String gtVm = "";  //gtVm

	private String cnOk = "";


	private String medicamento = "";
	private String accion = "";
	private String nombreConsejo = "";
	private String nombreCorto = "";
	private String cn6 = "";
	private String fieldName1 = "";
	private String cn_resi = "";
	private String comentario = "";
	private String formaFarmaceutica = "";
	private CamposPantallaBean camposPantallaBean = new CamposPantallaBean();
	
	private List<String> listaHoras = new ArrayList();

	private List<TiposAccionBean> listaTiposAccion = new ArrayList();
	private FicheroResiNewBean ficheroResiDetalleBean = new FicheroResiNewBean();
	private List<FicheroResiNewBean> listaProcesosCargados = new ArrayList();
	
	private String idProceso = "";
	private String idDivisionResidencia = "";
	private String fechaHoraProceso = "";
	private List listaFechaFichero = new ArrayList();
	private String soloConMensajesInfo = "";
	private String soloConMensajesAlerta = "";


	private String resiCIP = "";
	private String seleccionResiCIP = "";
	private List listaResiCIP = new ArrayList();
	
	private String resiNombrePaciente = "";
	private String seleccionResiNombrePaciente = "";
	private List<String> listaResiNombrePaciente = new ArrayList();

	private String resiApellidosNombre = "";
	private String seleccionResiApellidosNombre = "";
	private List<String> listaResiApellidosNombre = new ArrayList();
	
	private String resiCn = "";
	private String seleccionResiCn = "";
	private List<String> listaResiCn = new ArrayList();
	
	private String resiMedicamento = "";
	private String seleccionResiMedicamento = "";
	private List<String> listaResiMedicamento = new ArrayList();

	private String resiFormaMedicacion = "";
	private String seleccionResiFormaMedicacion = "";
	private List<String> listaResiFormaMedicacion = new ArrayList();

	private String resiInicioTratamiento = "";
	private String resiFinTratamiento = "";

	private String resiObservaciones = "";
	private String seleccionResiObservaciones = "";
	private List<String> listaResiObservaciones = new ArrayList();

	private String resiVariante = "";
	private String seleccionResiVariante = "";
	private List<String> listaResiVariante = new ArrayList();

	private String resiComentarios = "";
	private String seleccionResiComentarios = "";
	private List<String> listaResiComentarios = new ArrayList();

	private String resiSiPrecisa = "";
	private String seleccionResiSiPrecisa = "";
	private List<String> listaResiSiPrecisa = new ArrayList();

	private String resiPeriodo = "";
	private String seleccionResiPeriodo = "";
	private List<String> listaResiPeriodo= new ArrayList();

	private String resiViaAdministracion = "";
	private String seleccionResiViaAdministracion = "";
	private List<String> listaResiViaAdministracion = new ArrayList();

	private String spdCnFinal = "";
	private String seleccionSpdCnFinal = "";
	private List<String> listaSpdCnFinal = new ArrayList();

	private String spdNombreBolsa = "";
	private String seleccionSpdNombreBolsa = "";
	private List<String> listaSpdNombreBolsa = new ArrayList();

	private String spdFormaMedicacion = "";
	private String seleccionSpdFormaMedicacion = "";
	private List<String> listaSpdFormaMedicacion = new ArrayList();
	
	private String spdAccionBolsa = "";
	private String seleccionSpdAccionBolsa = "";
	private List<String> listaSpdAccionBolsa = new ArrayList();
	
	private String idEstado = "";
	private String seleccionEstado = "";
	private List<String> listaEstados = new ArrayList();
	
	private String seleccionIncidencia= "";
	private List<String> listaIncidencia = new ArrayList();
	private String seleccionRevisar= "";
	private List<String> listaRevisar = new ArrayList();
	private String seleccionSecuenciaGuide= "";
	private List<String> listaSecuenciaGuide = new ArrayList();

	
	private String seleccionEsExcepcion= "";
	private List<String> listaEsExcepcion = new ArrayList();
	private String seleccionResultLog= "";
	private List<String> listaResultLog = new ArrayList();
	private String seleccionMensajesInfo= "";
	private List<String> listaMensajesInfo = new ArrayList();
	private String seleccionMensajesAlerta= "";
	private List<String> listaMensajesAlerta = new ArrayList();
	private String seleccionDiasAutomaticos= "";
	private List<String> listaDiasAutomaticos = new ArrayList();
	
	private String resiD1 = "";
	private String resiD2 = "";
	private String resiD3 = "";
	private String resiD4 = "";
	private String resiD5 = "";
	private String resiD6 = "";
	private String resiD7 = "";
	private String resiToma1;	  
	private String resiToma2;	  
	private String resiToma3;	  
	private String resiToma4;	  
	private String resiToma5;	  
	private String resiToma6;	  
	private String resiToma7;	  
	private String resiToma8;	  
	private String resiToma9;	  
	private String resiToma10;	  
	private String resiToma11;	  
	private String resiToma12;	  
	private String resiToma13;	  
	private String resiToma14;	  
	private String resiToma15;	  
	private String resiToma16;	  
	private String resiToma17;	  
	private String resiToma18;	  
	private String resiToma19;	  
	private String resiToma20;	  
	private String resiToma21;	  
	private String resiToma22;
	private String resiToma23;	
	private String resiToma24;
	private String numeroDeTomas;
	
	
	private String fechaDesde;
	private String fechaHasta;
	
	private int frecuencia;
	private String diasSemanaConcretosToma= "";
	private String diasMesConcretos= "";
	private String secuenciaGuide;
	private String tipoEnvioHelium;

	private List listaTomasCabecera = new ArrayList();
	
	
	public String getIdProceso() {
		return idProceso;
	}

	public void setIdProceso(String idProceso) {
		this.idProceso = idProceso;
	}

	public String getIdDivisionResidencia() {
		return idDivisionResidencia;
	}

	public void setIdDivisionResidencia(String idDivisionResidencia) {
		this.idDivisionResidencia = idDivisionResidencia;
	}

	public String getFechaHoraProceso() {
		return fechaHoraProceso;
	}

	public void setFechaHoraProceso(String fechaHoraProceso) {
		this.fechaHoraProceso = fechaHoraProceso;
	}

	public String getResiCIP() {
		return resiCIP;
	}

	public void setResiCIP(String resiCIP) {
		this.resiCIP = resiCIP;
	}

	public String getResiNombrePaciente() {
		return resiNombrePaciente;
	}

	public void setResiNombrePaciente(String resiNombrePaciente) {
		this.resiNombrePaciente = resiNombrePaciente;
	}
	
	public String getResiCn() {
		return resiCn;
	}

	public String getResiApellidosNombre() {
		return resiApellidosNombre;
	}

	public void setResiApellidosNombre(String resiApellidosNombre) {
		this.resiApellidosNombre = resiApellidosNombre;
	}

	public String getSeleccionResiApellidosNombre() {
		return seleccionResiApellidosNombre;
	}

	public void setSeleccionResiApellidosNombre(String seleccionResiApellidosNombre) {
		this.seleccionResiApellidosNombre = seleccionResiApellidosNombre;
	}

	public List<String> getListaResiApellidosNombre() {
		return listaResiApellidosNombre;
	}

	public void setListaResiApellidosNombre(List<String> listaResiApellidosNombre) {
		this.listaResiApellidosNombre = listaResiApellidosNombre;
	}

	public void setResiCn(String resiCn) {
		this.resiCn = resiCn;
	}

	public String getResiMedicamento() {
		return resiMedicamento;
	}

	public void setResiMedicamento(String resiMedicamento) {
		this.resiMedicamento = resiMedicamento;
	}

	public String getResiFormaMedicacion() {
		return resiFormaMedicacion;
	}

	public void setResiFormaMedicacion(String resiFormaMedicacion) {
		this.resiFormaMedicacion = resiFormaMedicacion;
	}

	public String getResiInicioTratamiento() {
		return resiInicioTratamiento;
	}

	public void setResiInicioTratamiento(String resiInicioTratamiento) {
		this.resiInicioTratamiento = resiInicioTratamiento;
	}

	public String getResiFinTratamiento() {
		return resiFinTratamiento;
	}

	public void setResiFinTratamiento(String resiFinTratamiento) {
		this.resiFinTratamiento = resiFinTratamiento;
	}

	public String getResiObservaciones() {
		return resiObservaciones;
	}

	public void setResiObservaciones(String resiObservaciones) {
		this.resiObservaciones = resiObservaciones;
	}

	
	public String getResiVariante() {
		return resiVariante;
	}

	public void setResiVariante(String resiVariante) {
		this.resiVariante = resiVariante;
	}

	public String getResiComentarios() {
		return resiComentarios;
	}

	public void setResiComentarios(String resiComentarios) {
		this.resiComentarios = resiComentarios;
	}

	public String getResiSiPrecisa() {
		return resiSiPrecisa;
	}

	public void setResiSiPrecisa(String resiSiPrecisa) {
		this.resiSiPrecisa = resiSiPrecisa;
	}

	public String getResiViaAdministracion() {
		return resiViaAdministracion;
	}

	public void setResiViaAdministracion(String resiViaAdministracion) {
		this.resiViaAdministracion = resiViaAdministracion;
	}

	public List getListaFechaFichero() {
		return listaFechaFichero;
	}

	public void setListaFechaFichero(List listaFechaFichero) {
		this.listaFechaFichero = listaFechaFichero;
	}


	public String getSpdCnFinal() {
		return spdCnFinal;
	}

	public void setSpdCnFinal(String spdCnFinal) {
		this.spdCnFinal = spdCnFinal;
	}

	public String getSpdNombreBolsa() {
		return spdNombreBolsa;
	}

	public void setSpdNombreBolsa(String spdNombreBolsa) {
		this.spdNombreBolsa = spdNombreBolsa;
	}

	public String getSpdFormaMedicacion() {
		return spdFormaMedicacion;
	}

	public void setSpdFormaMedicacion(String spdFormaMedicacion) {
		this.spdFormaMedicacion = spdFormaMedicacion;
	}

	public String getSpdAccionBolsa() {
		return spdAccionBolsa;
	}

	public void setSpdAccionBolsa(String spdAccionBolsa) {
		this.spdAccionBolsa = spdAccionBolsa;
	}

	public String getResiD1() {
		return resiD1;
	}

	public void setResiD1(String resiD1) {
		this.resiD1 = resiD1;
	}

	public String getResiD2() {
		return resiD2;
	}

	public void setResiD2(String resiD2) {
		this.resiD2 = resiD2;
	}

	public String getResiD3() {
		return resiD3;
	}

	public void setResiD3(String resiD3) {
		this.resiD3 = resiD3;
	}

	public String getResiD4() {
		return resiD4;
	}

	public void setResiD4(String resiD4) {
		this.resiD4 = resiD4;
	}

	public String getResiD5() {
		return resiD5;
	}

	public void setResiD5(String resiD5) {
		this.resiD5 = resiD5;
	}

	public String getResiD6() {
		return resiD6;
	}

	public void setResiD6(String resiD6) {
		this.resiD6 = resiD6;
	}

	public String getResiD7() {
		return resiD7;
	}

	public void setResiD7(String resiD7) {
		this.resiD7 = resiD7;
	}

	public int getOidFicheroResiDetalle() {
		return oidFicheroResiDetalle;
	}

	public void setOidFicheroResiDetalle(int oidGestFicheroResiBolsa) {
		this.oidFicheroResiDetalle = oidGestFicheroResiBolsa;
	}

	
	public List<FicheroResiNewBean> getListaFicheroResiDetalleBean() {
		return listaFicheroResiDetalleBean;
	}

	public void setListaFicheroResiDetalleBean(List<FicheroResiNewBean> listaFicheroResiDetalleBean) {
		this.listaFicheroResiDetalleBean = listaFicheroResiDetalleBean;
	}

	public String getFiltroMedicamentoResi() {
		return filtroMedicamentoResi;
	}

	public void setFiltroMedicamentoResi(String filtroMedicamentoResi) {
		this.filtroMedicamentoResi = filtroMedicamentoResi;
	}

	public String getFiltroNombreCortoOK() {
		return filtroNombreCortoOK;
	}

	public void setFiltroNombreCortoOK(String filtroNombreCortoOK) {
		this.filtroNombreCortoOK = filtroNombreCortoOK;
	}

	public String getFiltroGtVm() {
		return filtroGtVm;
	}

	public void setFiltroGtVm(String filtroPrincipioActivo) {
		this.filtroGtVm = filtroPrincipioActivo;
	}

	public String getFiltroPresentacion() {
		return filtroPresentacion;
	}

	public void setFiltroPresentacion(String filtroPresentacion) {
		this.filtroPresentacion = filtroPresentacion;
	}

	public String getFiltroNombreCorto() {
		return filtroNombreCorto;
	}

	public void setFiltroNombreCorto(String filtroNombreCorto) {
		this.filtroNombreCorto = filtroNombreCorto;
	}

	public int getOidDivisionResidenciaFiltro() {
		return oidDivisionResidenciaFiltro;
	}

	public void setOidDivisionResidenciaFiltro(int oidDivisionResidenciaFiltro) {
		this.oidDivisionResidenciaFiltro = oidDivisionResidenciaFiltro;
	}
	public String getGtVm() {
		return gtVm;
	}

	public void setGtVm(String principioActivo) {
		this.gtVm = principioActivo;
	}

	public String getCnOk() {
		return cnOk;
	}

	public void setCnOk(String cnOk) {
		this.cnOk = cnOk;
	}


	public String getMedicamento() {
		return medicamento;
	}

	public void setMedicamento(String medicamento) {
		this.medicamento = medicamento;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getNombreConsejo() {
		return nombreConsejo;
	}

	public void setNombreConsejo(String nombreConsejo) {
		this.nombreConsejo = nombreConsejo;
	}

	public String getNombreCorto() {
		return nombreCorto;
	}

	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}

	public String getCn6() {
		return cn6;
	}

	public void setCn6(String cn6) {
		this.cn6 = cn6;
	}

	public String getFieldName1() {
		return fieldName1;
	}

	public void setFieldName1(String fieldName1) {
		this.fieldName1 = fieldName1;
	}

	public String getCn_resi() {
		return cn_resi;
	}

	public void setCn_resi(String cn_resi) {
		this.cn_resi = cn_resi;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getFormaFarmaceutica() {
		return formaFarmaceutica;
	}

	public void setFormaFarmaceutica(String formaFarmaceutica) {
		this.formaFarmaceutica = formaFarmaceutica;
	}
	public List<String> getListaHoras() {
		return listaHoras;
	}

	public void setListaHoras(List<String> listaHoras) {
		this.listaHoras = listaHoras;
	}

	public List<TiposAccionBean> getListaTiposAccion() {
		return listaTiposAccion;
	}

	public void setListaTiposAccion(List<TiposAccionBean> listaTiposAccion) {
		this.listaTiposAccion = listaTiposAccion;
	}

	public FicheroResiNewBean getFicheroResiDetalleBean() {
		return ficheroResiDetalleBean;
	}

	public void setFicheroResiDetalleBean(FicheroResiNewBean gestFicheroResiBolsaBean) {
		this.ficheroResiDetalleBean = gestFicheroResiBolsaBean;
	}

	public void setListaDivisionResidenciasCargadas(List<FicheroResiNewBean> listaDivisionResidenciasCargadas) {
		this.listaDivisionResidenciasCargadas=listaDivisionResidenciasCargadas;
		
	}

	public List<FicheroResiNewBean> getListaProcesosCargados() {
		return listaProcesosCargados;
	}

	public void setListaProcesosCargados(List<FicheroResiNewBean> listaProcesosCargados) {
		this.listaProcesosCargados = listaProcesosCargados;
	}

	public List<FicheroResiNewBean> getListaDivisionResidenciasCargadas() {
		return listaDivisionResidenciasCargadas;
	}

	public String getFiltroDivisionResidenciasCargadas() {
		return filtroDivisionResidenciasCargadas;
	}

	public void setFiltroDivisionResidenciasCargadas(String filtroDivisionResidenciasCargadas) {
		this.filtroDivisionResidenciasCargadas = filtroDivisionResidenciasCargadas;
	}

	public CamposPantallaBean getCamposPantallaBean() {
		return camposPantallaBean;
	}

	public void setCamposPantallaBean(CamposPantallaBean camposPantallaBean) {
		this.camposPantallaBean = camposPantallaBean;
	}

	public String getSeleccionResiCIP() {
		return seleccionResiCIP;
	}

	public void setSeleccionResiCIP(String seleccionResiCIP) {
		this.seleccionResiCIP = seleccionResiCIP;
	}

	public List<String> getListaResiCIP() {
		return listaResiCIP;
	}


	public String getSeleccionResiNombrePaciente() {
		return seleccionResiNombrePaciente;
	}

	public void setSeleccionResiNombrePaciente(String seleccionResiNombrePaciente) {
		this.seleccionResiNombrePaciente = seleccionResiNombrePaciente;
	}

	public List<String> getListaResiNombrePaciente() {
		return listaResiNombrePaciente;
	}

	public void setListaResiNombrePaciente(List<String> listaResiNombrePaciente) {
		this.listaResiNombrePaciente = listaResiNombrePaciente;
	}

	public String getSeleccionResiCn() {
		return seleccionResiCn;
	}

	public void setSeleccionResiCn(String seleccionResiCn) {
		this.seleccionResiCn = seleccionResiCn;
	}

	public List<String> getListaResiCn() {
		return listaResiCn;
	}

	public void setListaResiCn(List<String> listaResiCn) {
		this.listaResiCn = listaResiCn;
	}

	public String getSeleccionResiMedicamento() {
		return seleccionResiMedicamento;
	}

	public void setSeleccionResiMedicamento(String seleccionResiMedicamento) {
		this.seleccionResiMedicamento = seleccionResiMedicamento;
	}

	public List<String> getListaResiMedicamento() {
		return listaResiMedicamento;
	}

	public void setListaResiMedicamento(List<String> listaResiMedicamento) {
		this.listaResiMedicamento = listaResiMedicamento;
	}

	public String getSeleccionResiFormaMedicacion() {
		return seleccionResiFormaMedicacion;
	}

	public void setSeleccionResiFormaMedicacion(String seleccionResiFormaMedicacion) {
		this.seleccionResiFormaMedicacion = seleccionResiFormaMedicacion;
	}

	public List<String> getListaResiFormaMedicacion() {
		return listaResiFormaMedicacion;
	}

	public void setListaResiFormaMedicacion(List<String> listaResiFormaMedicacion) {
		this.listaResiFormaMedicacion = listaResiFormaMedicacion;
	}

	public String getSeleccionResiObservaciones() {
		return seleccionResiObservaciones;
	}

	public void setSeleccionResiObservaciones(String seleccionResiObservaciones) {
		this.seleccionResiObservaciones = seleccionResiObservaciones;
	}

	public List<String> getListaResiObservaciones() {
		return listaResiObservaciones;
	}

	public void setListaResiObservaciones(List<String> listaResiObservaciones) {
		this.listaResiObservaciones = listaResiObservaciones;
	}

	public String getSeleccionResiVariante() {
		return seleccionResiVariante;
	}

	public void setSeleccionResiVariante(String seleccionResiVariante) {
		this.seleccionResiVariante = seleccionResiVariante;
	}

	public List<String> getListaResiVariante() {
		return listaResiVariante;
	}

	public void setListaResiVariante(List<String> listaResiVariante) {
		this.listaResiVariante = listaResiVariante;
	}
	
	public String getSeleccionResiComentarios() {
		return seleccionResiComentarios;
	}

	public void setSeleccionResiComentarios(String seleccionResiComentarios) {
		this.seleccionResiComentarios = seleccionResiComentarios;
	}

	public List<String> getListaResiComentarios() {
		return listaResiComentarios;
	}

	public void setListaResiComentarios(List<String> listaResiComentarios) {
		this.listaResiComentarios = listaResiComentarios;
	}

	public String getSeleccionResiSiPrecisa() {
		return seleccionResiSiPrecisa;
	}

	public void setSeleccionResiSiPrecisa(String seleccionResiSiPrecisa) {
		this.seleccionResiSiPrecisa = seleccionResiSiPrecisa;
	}

	public List<String> getListaResiSiPrecisa() {
		return listaResiSiPrecisa;
	}

	public void setListaResiSiPrecisa(List<String> listaResiSiPrecisa) {
		this.listaResiSiPrecisa = listaResiSiPrecisa;
	}

	public String getSeleccionResiViaAdministracion() {
		return seleccionResiViaAdministracion;
	}

	public void setSeleccionResiViaAdministracion(String seleccionResiViaAdministracion) {
		this.seleccionResiViaAdministracion = seleccionResiViaAdministracion;
	}

	public List<String> getListaResiViaAdministracion() {
		return listaResiViaAdministracion;
	}

	public void setListaResiViaAdministracion(List<String> listaResiViaAdministracion) {
		this.listaResiViaAdministracion = listaResiViaAdministracion;
	}

	public String getSeleccionSpdCnFinal() {
		return seleccionSpdCnFinal;
	}

	public void setSeleccionSpdCnFinal(String seleccionSpdCnFinal) {
		this.seleccionSpdCnFinal = seleccionSpdCnFinal;
	}

	public List<String> getListaSpdCnFinal() {
		return listaSpdCnFinal;
	}

	public void setListaSpdCnFinal(List<String> listaSpdCnFinal) {
		this.listaSpdCnFinal = listaSpdCnFinal;
	}

	public String getSeleccionSpdNombreBolsa() {
		return seleccionSpdNombreBolsa;
	}

	public void setSeleccionSpdNombreBolsa(String seleccionSpdNombreBolsa) {
		this.seleccionSpdNombreBolsa = seleccionSpdNombreBolsa;
	}

	public List<String> getListaSpdNombreBolsa() {
		return listaSpdNombreBolsa;
	}

	public void setListaSpdNombreBolsa(List<String> listaSpdNombreBolsa) {
		this.listaSpdNombreBolsa = listaSpdNombreBolsa;
	}

	public String getSeleccionSpdFormaMedicacion() {
		return seleccionSpdFormaMedicacion;
	}

	public void setSeleccionSpdFormaMedicacion(String seleccionSpdFormaMedicacion) {
		this.seleccionSpdFormaMedicacion = seleccionSpdFormaMedicacion;
	}

	public List<String> getListaSpdFormaMedicacion() {
		return listaSpdFormaMedicacion;
	}

	public void setListaSpdFormaMedicacion(List<String> listaSpdFormaMedicacion) {
		this.listaSpdFormaMedicacion = listaSpdFormaMedicacion;
	}

	public String getSeleccionSpdAccionBolsa() {
		return seleccionSpdAccionBolsa;
	}

	public void setSeleccionSpdAccionBolsa(String seleccionSpdAccionBolsa) {
		this.seleccionSpdAccionBolsa = seleccionSpdAccionBolsa;
	}

	public List<String> getListaSpdAccionBolsa() {
		return listaSpdAccionBolsa;
	}

	public void setListaSpdAccionBolsa(List<String> listaSpdAccionBolsa) {
		this.listaSpdAccionBolsa = listaSpdAccionBolsa;
	}

	public String getSeleccionIncidencia() {
		return seleccionIncidencia;
	}

	public void setSeleccionIncidencia(String seleccionIncidencia) {
		this.seleccionIncidencia = seleccionIncidencia;
	}

	public List<String> getListaIncidencia() {
		return listaIncidencia;
	}

	public void setListaIncidencia(List<String> listaIncidencia) {
		this.listaIncidencia = listaIncidencia;
	}

	
	public String getSeleccionRevisar() {
		return seleccionRevisar;
	}
	public void setSeleccionRevisar(String seleccionRevisar) {
		this.seleccionRevisar = seleccionRevisar;
	}

	public String getSeleccionEsExcepcion() {
		return seleccionEsExcepcion;
	}

	public void setSeleccionEsExcepcion(String seleccionEsExcepcion) {
		this.seleccionEsExcepcion = seleccionEsExcepcion;
	}

	public List<String> getListaRevisar() {
		return listaRevisar;
	}	
	public List<String> getListaEsExcepcion() {
		return listaEsExcepcion;
	}

	public void setListaEsExcepcion(List<String> listaEsExcepcion) {
		this.listaEsExcepcion = listaEsExcepcion;
	}
	
	public void setListaRevisar(List<String> listaRevisar) {
		this.listaRevisar = listaRevisar;
	}

	public String getSeleccionResultLog() {
		return seleccionResultLog;
	}

	public void setSeleccionResultLog(String seleccionResultLog) {
		this.seleccionResultLog = seleccionResultLog;
	}

	public List<String> getListaResultLog() {
		return listaResultLog;
	}

	public void setListaResultLog(List<String> listaResultLog) {
		this.listaResultLog = listaResultLog;
	}

	public String getSeleccionMensajesInfo() {
		return seleccionMensajesInfo;
	}

	public void setSeleccionMensajesInfo(String seleccionMensajesInfo) {
		this.seleccionMensajesInfo = seleccionMensajesInfo;
	}

	public List<String> getListaMensajesInfo() {
		return listaMensajesInfo;
	}

	public void setListaMensajesInfo(List<String> listaMensajesInfo) {
		this.listaMensajesInfo = listaMensajesInfo;
	}

	public String getSeleccionMensajesAlerta() {
		return seleccionMensajesAlerta;
	}

	public void setSeleccionMensajesAlerta(String seleccionMensajesAlerta) {
		this.seleccionMensajesAlerta = seleccionMensajesAlerta;
	}

	public List<String> getListaMensajesAlerta() {
		return listaMensajesAlerta;
	}

	public void setListaMensajesAlerta(List<String> listaMensajesAlerta) {
		this.listaMensajesAlerta = listaMensajesAlerta;
	}

	public List<FicheroResiNewBean> getTodaLaListaFicheroResiDetalleBean() {
		return todaLaListaFicheroResiDetalleBean;
	}

	public void setTodaLaListaGestFicheroResiBolsaBean(List<FicheroResiNewBean> todaLaListaGestFicheroResiBolsaBean) {
		this.todaLaListaFicheroResiDetalleBean = todaLaListaGestFicheroResiBolsaBean;
	}

	public String getFiltroEstados() {
		return filtroEstados;
	}

	public void setFiltroEstados(String filtroEstados) {
		this.filtroEstados = filtroEstados;
	}

	public String getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(String idEstado) {
		this.idEstado = idEstado;
	}

	public String getSeleccionEstado() {
		return seleccionEstado;
	}

	public void setSeleccionEstado(String seleccionEstado) {
		this.seleccionEstado = seleccionEstado;
	}

	public List<String> getListaEstados() {
		return listaEstados;
	}

	public boolean isProcesoValido() {
		return procesoValido;
	}

	public void setProcesoValido(boolean procesoValido) {
		this.procesoValido = procesoValido;
	}

	public String getResiPeriodo() {
		return resiPeriodo;
	}

	public void setResiPeriodo(String resiPeriodo) {
		this.resiPeriodo = resiPeriodo;
	}

	public String getSeleccionResiPeriodo() {
		return seleccionResiPeriodo;
	}

	public void setSeleccionResiPeriodo(String seleccionResiPeriodo) {
		this.seleccionResiPeriodo = seleccionResiPeriodo;
	}

	public List<String> getListaResiPeriodo() {
		return listaResiPeriodo;
	}

	public void setListaResiPeriodo(List<String> listaResiPeriodo) {
		this.listaResiPeriodo = listaResiPeriodo;
	}

	public void setListaResiCIP(List listaResiCIP) {
		this.listaResiCIP = listaResiCIP;
	}

	public void setListaEstados(List<String> listaEstados) {
		this.listaEstados = listaEstados;
	}

	public int getOidFicheroResiCabecera() {
		return oidFicheroResiCabecera;
	}

	public void setOidFicheroResiCabecera(int oidFicheroResiCabecera) {
		this.oidFicheroResiCabecera = oidFicheroResiCabecera;
	}

	public List<FicheroResiNewBean> getListaFicheroResiCabeceraBean() {
		return listaFicheroResiCabeceraBean;
	}

	public void setListaFicheroResiCabeceraBean(List<FicheroResiNewBean> listaFicheroResiCabeceraBean) {
		this.listaFicheroResiCabeceraBean = listaFicheroResiCabeceraBean;
	}

	public List<FicheroResiNewBean> getTodaLaListaFicheroResiCabeceraBean() {
		return todaLaListaFicheroResiCabeceraBean;
	}

	public void setTodaLaListaFicheroResiCabeceraBean(List<FicheroResiNewBean> todaLaListaFicheroResiCabeceraBean) {
		this.todaLaListaFicheroResiCabeceraBean = todaLaListaFicheroResiCabeceraBean;
	}

	public String getFiltroEstado() {
		return filtroEstado;
	}

	public void setFiltroEstado(String filtroEstado) {
		this.filtroEstado = filtroEstado;
	}

	public String getFiltroProceso() {
		return filtroProceso;
	}

	public void setFiltroProceso(String filtroProceso) {
		this.filtroProceso = filtroProceso;
	}

	public FicheroResiNewBean getFicheroResiCabeceraBean() {
		return ficheroResiCabeceraBean;
	}

	public void setFicheroResiCabeceraBean(FicheroResiNewBean ficheroResiCabeceraBean) {
		this.ficheroResiCabeceraBean = ficheroResiCabeceraBean;
	}

	public void setTodaLaListaFicheroResiDetalleBean(List<FicheroResiNewBean> todaLaListaFicheroResiDetalleBean) {
		this.todaLaListaFicheroResiDetalleBean = todaLaListaFicheroResiDetalleBean;
	}

	public List<FicheroResiNewBean> getListaEstadosCabecera() {
		return listaEstadosCabecera;
	}

	public void setListaEstadosCabecera(List<FicheroResiNewBean> listaEstadosCabecera) {
		this.listaEstadosCabecera = listaEstadosCabecera;
	}

	public String getSoloConMensajesInfo() {
		return soloConMensajesInfo;
	}

	public void setSoloConMensajesInfo(String soloConMensajesInfo) {
		this.soloConMensajesInfo = soloConMensajesInfo;
	}

	public String getSoloConMensajesAlerta() {
		return soloConMensajesAlerta;
	}

	public void setSoloConMensajesAlerta(String soloConMensajesAlerta) {
		this.soloConMensajesAlerta = soloConMensajesAlerta;
	}

	public String getSeleccionDiasAutomaticos() {
		return seleccionDiasAutomaticos;
	}

	public void setSeleccionDiasAutomaticos(String seleccionDiasAutomaticos) {
		this.seleccionDiasAutomaticos = seleccionDiasAutomaticos;
	}

	public List<String> getListaDiasAutomaticos() {
		return listaDiasAutomaticos;
	}

	public void setListaDiasAutomaticos(List<String> listaDiasAutomaticos) {
		this.listaDiasAutomaticos = listaDiasAutomaticos;
	}

	public String getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public String getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public boolean isSoloCabecera() {
		return soloCabecera;
	}

	public void setSoloCabecera(boolean soloCabecera) {
		this.soloCabecera = soloCabecera;
	}

	public boolean isExcluirCabecera() {
		return excluirCabecera;
	}

	public void setExcluirCabecera(boolean excluirCabecera) {
		this.excluirCabecera = excluirCabecera;
	}

	public List getListaTomasCabecera() {
		return listaTomasCabecera;
	}

	public void setListaTomasCabecera(List listaTomasCabecera) {
		this.listaTomasCabecera = listaTomasCabecera;
	}

	public int getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(int frecuencia) {
		this.frecuencia = frecuencia;
	}

	public String getDiasSemanaConcretosToma() {
		return diasSemanaConcretosToma;
	}

	public void setDiasSemanaConcretosToma(String diasConcretosToma) {
		this.diasSemanaConcretosToma = diasConcretosToma;
	}

	public String getSecuenciaGuide() {
		return secuenciaGuide;
	}

	public void setSecuenciaGuide(String secuenciaGuide) {
		this.secuenciaGuide = secuenciaGuide;
	}

	public String getTipoEnvioHelium() {
		return tipoEnvioHelium;
	}

	public void setTipoEnvioHelium(String tipoEnvioHelium) {
		this.tipoEnvioHelium = tipoEnvioHelium;
	}

	public String getDiasMesConcretos() {
		return diasMesConcretos;
	}

	public void setDiasMesConcretos(String diasMesConcretos) {
		this.diasMesConcretos = diasMesConcretos;
	}

	public String getSeleccionSecuenciaGuide() {
		return seleccionSecuenciaGuide;
	}

	public void setSeleccionSecuenciaGuide(String seleccionSecuenciaGuide) {
		this.seleccionSecuenciaGuide = seleccionSecuenciaGuide;
	}

	public List<String> getListaSecuenciaGuide() {
		return listaSecuenciaGuide;
	}

	public void setListaSecuenciaGuide(List<String> listaSecuenciaGuide) {
		this.listaSecuenciaGuide = listaSecuenciaGuide;
	}

	
	
	public String getResiToma1() {
		return resiToma1;
	}

	public void setResiToma1(String resiToma1) {
		this.resiToma1 = resiToma1;
	}

	public String getResiToma2() {
		return resiToma2;
	}

	public void setResiToma2(String resiToma2) {
		this.resiToma2 = resiToma2;
	}

	public String getResiToma3() {
		return resiToma3;
	}

	public void setResiToma3(String resiToma3) {
		this.resiToma3 = resiToma3;
	}

	public String getResiToma4() {
		return resiToma4;
	}

	public void setResiToma4(String resiToma4) {
		this.resiToma4 = resiToma4;
	}

	public String getResiToma5() {
		return resiToma5;
	}

	public void setResiToma5(String resiToma5) {
		this.resiToma5 = resiToma5;
	}

	public String getResiToma6() {
		return resiToma6;
	}

	public void setResiToma6(String resiToma6) {
		this.resiToma6 = resiToma6;
	}

	public String getResiToma7() {
		return resiToma7;
	}

	public void setResiToma7(String resiToma7) {
		this.resiToma7 = resiToma7;
	}

	public String getResiToma8() {
		return resiToma8;
	}

	public void setResiToma8(String resiToma8) {
		this.resiToma8 = resiToma8;
	}

	public String getResiToma9() {
		return resiToma9;
	}

	public void setResiToma9(String resiToma9) {
		this.resiToma9 = resiToma9;
	}

	public String getResiToma10() {
		return resiToma10;
	}

	public void setResiToma10(String resiToma10) {
		this.resiToma10 = resiToma10;
	}

	public String getResiToma11() {
		return resiToma11;
	}

	public void setResiToma11(String resiToma11) {
		this.resiToma11 = resiToma11;
	}

	public String getResiToma12() {
		return resiToma12;
	}

	public void setResiToma12(String resiToma12) {
		this.resiToma12 = resiToma12;
	}

	public String getResiToma13() {
		return resiToma13;
	}

	public void setResiToma13(String resiToma13) {
		this.resiToma13 = resiToma13;
	}

	public String getResiToma14() {
		return resiToma14;
	}

	public void setResiToma14(String resiToma14) {
		this.resiToma14 = resiToma14;
	}

	public String getResiToma15() {
		return resiToma15;
	}

	public void setResiToma15(String resiToma15) {
		this.resiToma15 = resiToma15;
	}

	public String getResiToma16() {
		return resiToma16;
	}

	public void setResiToma16(String resiToma16) {
		this.resiToma16 = resiToma16;
	}

	public String getResiToma17() {
		return resiToma17;
	}

	public void setResiToma17(String resiToma17) {
		this.resiToma17 = resiToma17;
	}

	public String getResiToma18() {
		return resiToma18;
	}

	public void setResiToma18(String resiToma18) {
		this.resiToma18 = resiToma18;
	}

	public String getResiToma19() {
		return resiToma19;
	}

	public void setResiToma19(String resiToma19) {
		this.resiToma19 = resiToma19;
	}

	public String getResiToma20() {
		return resiToma20;
	}

	public void setResiToma20(String resiToma20) {
		this.resiToma20 = resiToma20;
	}

	public String getResiToma21() {
		return resiToma21;
	}

	public void setResiToma21(String resiToma21) {
		this.resiToma21 = resiToma21;
	}

	public String getResiToma22() {
		return resiToma22;
	}

	public void setResiToma22(String resiToma22) {
		this.resiToma22 = resiToma22;
	}

	public String getResiToma23() {
		return resiToma23;
	}

	public void setResiToma23(String resiToma23) {
		this.resiToma23 = resiToma23;
	}

	public String getResiToma24() {
		return resiToma24;
	}

	public void setResiToma24(String resiToma24) {
		this.resiToma24 = resiToma24;
	}

	public String getNumeroDeTomas() {
		return numeroDeTomas;
	}

	
	public void setNumeroDeTomas(String numeroDeTomas) {
		this.numeroDeTomas = numeroDeTomas;
	}

	
}

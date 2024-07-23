package lopicost.spd.struts.form;

import java.util.ArrayList;
import java.util.List;

public class SpdLogForm  extends GenericForm {

	private String idFarmacia = "";
	private String CIP = "";
	private String idProceso = "";
	private String idProcesoFiltro = "";
	private String idApartado = "";
	private String idApartadoFiltro = "";
	private String idAccion = "";
	private String idAccionFiltro = "";
	private String idSubAccion = "";
	private String idSubAccionFiltro = "";
	private String descripcion = "";
	private String fecha = "";
	
	private List lista = new ArrayList();
	
	private List listaProcesos = new ArrayList();
	private List listaApartados = new ArrayList();
	private List listaAcciones = new ArrayList();
	private List listaSubAcciones = new ArrayList();
	
	private String idUsuarioFiltro = "";
	private String fechaInicioFiltro = "";
	private String fechaFinFiltro = "";
	
	
	public String getIdFarmacia() {
		return idFarmacia;
	}
	public void setIdFarmacia(String idFarmacia) {
		this.idFarmacia = idFarmacia;
	}
	public String getCIP() {
		return CIP;
	}
	public void setCIP(String cIP) {
		CIP = cIP;
	}
	public String getIdApartado() {
		return idApartado;
	}
	public void setIdApartado(String idApartado) {
		this.idApartado = idApartado;
	}
	public String getIdAccion() {
		return idAccion;
	}
	public void setIdAccion(String idAccion) {
		this.idAccion = idAccion;
	}
	public String getIdSubAccion() {
		return idSubAccion;
	}
	public void setIdSubAccion(String idSubAccion) {
		this.idSubAccion = idSubAccion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getIdApartadoFiltro() {
		return idApartadoFiltro;
	}
	public void setIdApartadoFiltro(String idApartadoFiltro) {
		this.idApartadoFiltro = idApartadoFiltro;
	}
	public String getIdAccionFiltro() {
		return idAccionFiltro;
	}
	public void setIdAccionFiltro(String idAccionFiltro) {
		this.idAccionFiltro = idAccionFiltro;
	}
	public String getIdSubAccionFiltro() {
		return idSubAccionFiltro;
	}
	public void setIdSubAccionFiltro(String idSubAccionFiltro) {
		this.idSubAccionFiltro = idSubAccionFiltro;
	}
	public List getLista() {
		return lista;
	}
	public void setLista(List lista) {
		this.lista = lista;
	}
	public List getListaApartados() {
		return listaApartados;
	}
	public void setListaApartados(List listaApartados) {
		this.listaApartados = listaApartados;
	}
	public List getListaAcciones() {
		return listaAcciones;
	}
	public void setListaAcciones(List listaAcciones) {
		this.listaAcciones = listaAcciones;
	}
	public List getListaSubAcciones() {
		return listaSubAcciones;
	}
	public void setListaSubAcciones(List listaSubAcciones) {
		this.listaSubAcciones = listaSubAcciones;
	}
	public String getIdUsuarioFiltro() {
		return idUsuarioFiltro;
	}
	public void setIdUsuarioFiltro(String idUsuarioFiltro) {
		this.idUsuarioFiltro = idUsuarioFiltro;
	}
	public String getFechaInicioFiltro() {
		return fechaInicioFiltro;
	}
	public void setFechaInicioFiltro(String fechaInicioFiltro) {
		this.fechaInicioFiltro = fechaInicioFiltro;
	}
	public String getFechaFinFiltro() {
		return fechaFinFiltro;
	}
	public void setFechaFinFiltro(String fechaFinFiltro) {
		this.fechaFinFiltro = fechaFinFiltro;
	}
	public String getIdProceso() {
		return idProceso;
	}
	public void setIdProceso(String idProceso) {
		this.idProceso = idProceso;
	}
	public String getIdProcesoFiltro() {
		return idProcesoFiltro;
	}
	public void setIdProcesoFiltro(String idProcesoFiltro) {
		this.idProcesoFiltro = idProcesoFiltro;
	}
	public List getListaProcesos() {
		return listaProcesos;
	}
	public void setListaProcesos(List listaProcesos) {
		this.listaProcesos = listaProcesos;
	}

	
	
}

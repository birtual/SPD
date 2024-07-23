package lopicost.spd.struts.form;

import java.util.List;

import lopicost.spd.model.Report;
import lopicost.spd.model.Usuario;
import lopicost.spd.model.Enlace;

public class EnlacesForm   extends GenericForm {


			private List listaEnlaces;
		    private String idEnlace;
		    private String aliasEnlace;
		    private String idApartado;
		    private String nombreEnlace;
		    private String preEnlace;
		    private String linkEnlace;
		    private String paramsEnlace;
		    private String descripcion;
		    private boolean activo;
		    private boolean nuevaVentana;
		    private Usuario usuario;
		    private int orden;
		    private int nivel=1;
			private Enlace enlace;

		    

			public List getListaEnlaces() {
				return listaEnlaces;
			}

			public void setListaEnlaces(List listaEnlaces) {
				this.listaEnlaces = listaEnlaces;
			}

			public String getIdEnlace() {
				return idEnlace;
			}

			public void setIdEnlace(String idEnlace) {
				this.idEnlace = idEnlace;
			}

			public String getAliasEnlace() {
				return aliasEnlace;
			}

			public void setAliasEnlace(String aliasEnlace) {
				this.aliasEnlace = aliasEnlace;
			}

			public String getIdApartado() {
				return idApartado;
			}

			public void setIdApartado(String idApartado) {
				this.idApartado = idApartado;
			}

			public String getNombreEnlace() {
				return nombreEnlace;
			}

			public void setNombreEnlace(String nombreEnlace) {
				this.nombreEnlace = nombreEnlace;
			}

			public String getPreEnlace() {
				return preEnlace;
			}

			public void setPreEnlace(String preEnlace) {
				this.preEnlace = preEnlace;
			}

			public String getLinkEnlace() {
				return linkEnlace;
			}

			public void setLinkEnlace(String linkEnlace) {
				this.linkEnlace = linkEnlace;
			}

			public String getParamsEnlace() {
				return paramsEnlace;
			}

			public void setParamsEnlace(String paramsEnlace) {
				this.paramsEnlace = paramsEnlace;
			}

			public String getDescripcion() {
				return descripcion;
			}

			public void setDescripcion(String descripcion) {
				this.descripcion = descripcion;
			}

			public boolean isActivo() {
				return activo;
			}

			public void setActivo(boolean activo) {
				this.activo = activo;
			}

			public boolean isNuevaVentana() {
				return nuevaVentana;
			}

			public void setNuevaVentana(boolean nuevaVentana) {
				this.nuevaVentana = nuevaVentana;
			}

			public Usuario getUsuario() {
				return usuario;
			}

			public void setUsuario(Usuario usuario) {
				this.usuario = usuario;
			}

			public int getOrden() {
				return orden;
			}

			public void setOrden(int orden) {
				this.orden = orden;
			}

			public Enlace getEnlace() {
				return enlace;
			}

			public void setEnlace(Enlace enlace) {
				this.enlace = enlace;
			}

			public int getNivel() {
				if(nivel==0) nivel=1;
				return nivel;
			}

			public void setNivel(int nivel) {
				this.nivel = nivel;
			}


	


	
	
}

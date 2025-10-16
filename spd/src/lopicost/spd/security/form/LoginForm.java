package lopicost.spd.security.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import lopicost.spd.struts.form.GenericForm;

public class LoginForm extends GenericForm {

	// --------------------------------------------------------- Instance Variables

	/** password property */
	private String password;

	/** perfil property */
	private String perfil;

	/** dominio property */
	private String dominio;

	/** idUsuario property */
	private String idUsuario;
	/**parámetros SSO*/
	private String codigo;
	private String fecha;	
	/** dominios property */
	private List dominios;
	
	/** perfiles property */
	private List perfiles;
	
	private String idscope;
	
	private String errorurl;

	// --------------------------------------------------------- Methods

	/** 
	 * Method validate
	 * @param ActionMapping mapping
	 * @param HttpServletRequest request
	 * @return ActionErrors
	 */
	public ActionErrors validate(
		ActionMapping mapping,
		HttpServletRequest request) {

		ActionErrors errors = new ActionErrors();
		if (idUsuario == null || idUsuario.trim().equals("")) {
			 errors.add("idUsuario", new ActionError("errors.idUsuario"));
		}
		if (password == null || password.trim().equals("")) {
			 errors.add("password", new ActionError("errors.password"));
		}

		return errors;
	}

	/** 
	 * Method reset
	 * @param ActionMapping mapping
	 * @param HttpServletRequest request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		password = "";
		perfil = "";
		dominio = "";
		idUsuario = "";
		dominios = null;
		perfiles = null;

	}

	/** 
	 * Returns the password.
	 * @return String
	 */
	public String getPassword() {
		return password;
	}

	/** 
	 * Set the password.
	 * @param password The password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/** 
	 * Returns the perfil.
	 * @return String
	 */
	public String getPerfil() {
		return perfil;
	}

	/** 
	 * Set the perfil.
	 * @param perfil The perfil to set
	 */
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	/** 
	 * Returns the dominio.
	 * @return String
	 */
	public String getDominio() {
		return dominio;
	}

	/** 
	 * Set the dominio.
	 * @param dominio The dominio to set
	 */
	public void setDominio(String dominio) {
		this.dominio = dominio;
	}

	/** 
	 * Returns the idUsuario.
	 * @return String
	 */
	public String getIdUsuario() {
		return idUsuario;
	}

	/** 
	 * Set the idUsuario.
	 * @param idUsuario The idUsuario to set
	 */
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	/** 
	 * Returns the dominios.
	 * @return List
	 */
	public List getDominios() {
		return dominios;
	}

	/** 
	 * Set the dominios.
	 * @param dominios The dominios to set
	 */
	public void setDominios(List dominios) {
		this.dominios = dominios;
	}

	/** 
	 * Returns the perfiles.
	 * @return List
	 */
	public List getPerfiles() {
		return perfiles;
	}

	/** 
	 * Set the perfiles.
	 * @param perfiles The dominio to set
	 */
	public void setPerfiles(List perfiles) {
		this.perfiles = perfiles;
	}

	/**
	 * @return
	 */
	public String getIdscope() {
		return idscope;
	}

	/**
	 * @param idscope
	 */
	public void setIdscope(String idscope) {
		this.idscope = idscope;
	}

	/**
	 * @return
	 */
	public String getErrorurl() {
		return errorurl;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	/**
	 * @param errorurl
	 */
	public void setErrorurl(String errorurl) {
		this.errorurl = errorurl;
	}

}

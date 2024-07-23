package lopicost.spd.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Usuario implements Serializable {

    /** identifier field */
    private Long oidUsuario;

    /** persistent field */
    private String idUsuario;

    /** nullable persistent field */
    private String initialPass;

    /** persistent field */
    private String hashPass;

    /** persistent field */
    private String nombre;

    /** persistent field */
    private String apellido1;

    /** nullable persistent field */
    private String apellido2;


    /** nullable persistent field */
    private String email;

    /** nullable persistent field */
    private String emailExtern;

    /** nullable persistent field */
    private String telefono;

    /** nullable persistent field */
    private String estado;

    /** nullable persistent field */
    private Date ultimoLogin;

     /** nullable persistent field */
    private String free1;

    /** nullable persistent field */
    private String free2;

    /** nullable persistent field */
    private String free3;

    /** nullable persistent field */
    private String free4;

    /** nullable persistent field */
    private int perfil;
    /** nullable persistent field */
    private int implicadoRobot;
  
    /** full constructor */
    public Usuario(String idUsuario, String initialPass, String hashPass, String nombre, String apellido1, String apellido2, 
    		String email, String emailExtern, String telefono,  String estado, Date ultimoLogin, String free1, String free2, String free3, 
    		String free4, String free5,  Date freedate2, Date freedate3, Date freedate4, Long freelong1, Long freelong2, Long freelong3, Long freelong4, Long freelong5, Long freelong6, Long freelong7, Long freelong8, Long freelong9, 
     		Set domainprofiles) {
        this.idUsuario = idUsuario;
        this.initialPass = initialPass;
        this.hashPass = hashPass;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.email = email;
        this.emailExtern = emailExtern;
        this.telefono = telefono;
        this.estado = estado;
        this.ultimoLogin = ultimoLogin;
        this.free1 = free1;
        this.free2 = free2;
        this.free3 = free3;
        this.free4 = free4;

    }

    /** default constructor */
    public Usuario() {
    }

    /** minimal constructor */
    public Usuario(String idUsuario, String hashPass, String nombre, String apellido1, 
    		Set domainprofiles) {
        this.idUsuario = idUsuario;
        this.hashPass = hashPass;
        this.nombre = nombre;
        this.apellido1 = apellido1;
    }

    
    
    public Long getOidUsuario() {
		return oidUsuario;
	}

	public void setOidUsuario(Long oidUsuario) {
		this.oidUsuario = oidUsuario;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getInitialPass() {
		return initialPass;
	}

	public void setInitialPass(String initialPass) {
		this.initialPass = initialPass;
	}

	public String getHashPass() {
		return hashPass;
	}

	public void setHashPass(String hashPass) {
		this.hashPass = hashPass;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailExtern() {
		return emailExtern;
	}

	public void setEmailExtern(String emailExtern) {
		this.emailExtern = emailExtern;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getUltimoLogin() {
		return ultimoLogin;
	}

	public void setUltimoLogin(Date ultimoLogin) {
		this.ultimoLogin = ultimoLogin;
	}

	public String getFree1() {
		return free1;
	}

	public void setFree1(String free1) {
		this.free1 = free1;
	}

	public String getFree2() {
		return free2;
	}

	public void setFree2(String free2) {
		this.free2 = free2;
	}

	public String getFree3() {
		return free3;
	}

	public void setFree3(String free3) {
		this.free3 = free3;
	}

	public String getFree4() {
		return free4;
	}

	public void setFree4(String free4) {
		this.free4 = free4;
	}

	
	public int getPerfil() {
		return perfil;
	}

	public void setPerfil(int perfil) {
		this.perfil = perfil;
	}

	public String toString() {
        return new ToStringBuilder(this)
            .append("oid", getOidUsuario())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Usuario) ) return false;
        Usuario castOther = (Usuario) other;
        return new EqualsBuilder()
            .append(this.getOidUsuario(), castOther.getOidUsuario())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getOidUsuario())
            .toHashCode();
    }

	public int getImplicadoRobot() {
		return implicadoRobot;
	}

	public void setImplicadoRobot(int implicadoRobot) {
		this.implicadoRobot = implicadoRobot;
	}

}

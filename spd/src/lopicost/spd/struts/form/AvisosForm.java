package lopicost.spd.struts.form;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import lopicost.spd.model.Aviso;
import lopicost.spd.model.Farmacia;

public class AvisosForm   extends GenericForm {
	 
	
	private Aviso aviso;
	private int oidAviso;
	private Date fechaInsert;
	private String texto;
	private String fechaInicio;
	private String fechaFin;
	private String activo;
	private String idFarmacia;
	private String tipo;
	private String usuarioCreador;
	private int orden;
	private List<Farmacia> listaFarmacias= null;
	
	
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private Date parseDate(String dateStr) {
        try {
            return dateStr != null && !dateStr.isEmpty() ? DATE_FORMAT.parse(dateStr) : null;
        } catch (Exception e) {
            return null; // Manejar la excepción adecuadamente si el formato es incorrecto
        }
    }

    // Método para convertir de Date a String (formato dd/MM/yyyy)
    public String formatDate(Date date) {
        return date != null ? DATE_FORMAT.format(date) : null;
    }
	
    public Date getFechaInicioDate() {
        return parseDate(fechaInicio);
    }

    public Date getFechaFinDate() {
        return parseDate(fechaFin);
    }
	
	public int getOidAviso() {
		return oidAviso;
	}
	public void setOidAviso(int oidAviso) {
		this.oidAviso = oidAviso;
	}
	public Date getFechaInsert() {
		return fechaInsert;
	}
	public void setFechaInsert(Date fechaInsert) {
		this.fechaInsert = fechaInsert;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public String getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public String getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}
	public String getActivo() {
		return activo;
	}
	public void setActivo(String activo) {
		this.activo = activo;
	}
	public String getIdFarmacia() {
		return idFarmacia;
	}
	public void setIdFarmacia(String idFarmacia) {
		this.idFarmacia = idFarmacia;
	}
	public String getUsuarioCreador() {
		return usuarioCreador;
	}
	public void setUsuarioCreador(String usuarioCreador) {
		this.usuarioCreador = usuarioCreador;
	}
	public int getOrden() {
		return orden;
	}
	public void setOrden(int orden) {
		this.orden = orden;
	}
	public Aviso getAviso() {
		return aviso;
	}
	public void setAviso(Aviso aviso) {
		this.aviso = aviso;
	}
	public List<Farmacia> getListaFarmacias() {
		return listaFarmacias;
	}
	public void setListaFarmacias(List<Farmacia> listaFarmacias) {
		this.listaFarmacias = listaFarmacias;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	

	
	
}

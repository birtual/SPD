package lopicost.spd.struts.bean;

import java.util.Date;

public class CabecerasXLSBean {
    private int oidCabeceraXLS;
    private Date fechaInsert;
    private String idDivisionResidencia;
    private String idToma;
    private String nombreToma;
    private int posicionEnBBDD;
    private int posicionEnVistas;
    private String horaToma;
    private int langToma;
    private String tipo;

    // Constructor por defecto
    public CabecerasXLSBean() {
    }

    public CabecerasXLSBean(String idDivisionResidencia, String horaToma, String nombreToma, int posicionEnBBDD, String tipo) {
    	this.idDivisionResidencia=idDivisionResidencia;
    	this.horaToma=horaToma;
    	this.nombreToma=nombreToma;
    	this.posicionEnBBDD=posicionEnBBDD;
    	this.posicionEnVistas=posicionEnBBDD;
    	this.tipo=tipo;
    	this.idDivisionResidencia=idDivisionResidencia;
    }
    
    
    // Getters y setters para cada propiedad
    public int getOidCabeceraXLS() {
        return oidCabeceraXLS;
    }

    public void setOidCabeceraXLS(int oidCabeceraXLS) {
        this.oidCabeceraXLS = oidCabeceraXLS;
    }

    public Date getFechaInsert() {
        return fechaInsert;
    }

    public void setFechaInsert(Date fechaInsert) {
        this.fechaInsert = fechaInsert;
    }

    public String getIdDivisionResidencia() {
        return idDivisionResidencia;
    }

    public void setIdDivisionResidencia(String idDivisionResidencia) {
        this.idDivisionResidencia = idDivisionResidencia;
    }

    public String getIdToma() {
        return idToma;
    }

    public void setIdToma(String idToma) {
        this.idToma = idToma;
    }

    public String getNombreToma() {
        return nombreToma;
    }

    public void setNombreToma(String nombreToma) {
        this.nombreToma = nombreToma;
    }

    public int getPosicionEnBBDD() {
        return posicionEnBBDD;
    }

    public void setPosicionEnBBDD(int posicionEnBBDD) {
        this.posicionEnBBDD = posicionEnBBDD;
    }

    public int getPosicionEnVistas() {
        return posicionEnVistas;
    }

    public void setPosicionEnVistas(int posicionEnVistas) {
        this.posicionEnVistas = posicionEnVistas;
    }

    public String getHoraToma() {
        return horaToma;
    }

    public void setHoraToma(String horaToma) {
        this.horaToma = horaToma;
    }

    public int getLangToma() {
        return langToma;
    }

    public void setLangToma(int langToma) {
        this.langToma = langToma;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
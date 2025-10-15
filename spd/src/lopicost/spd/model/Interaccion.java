package lopicost.spd.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Interaccion implements Serializable
{
	private String cn1;
	private String cn2;
	private String pactivo1;
	private String pactivo2;
	private String nomPactivo1;
	private String nomPactivo2;
	private String sentido;
	private String textoSentido;
	private String semaforo;
	private String textoSemaforo;
	private String medidas;
	private String textoMedidas;
	public String getCn1() {
		return cn1;
	}
	public void setCn1(String cn1) {
		this.cn1 = cn1;
	}
	public String getCn2() {
		return cn2;
	}
	public void setCn2(String cn2) {
		this.cn2 = cn2;
	}
	public String getPactivo1() {
		return pactivo1;
	}
	public void setPactivo1(String pactivo1) {
		this.pactivo1 = pactivo1;
	}
	public String getPactivo2() {
		return pactivo2;
	}
	public void setPactivo2(String pactivo2) {
		this.pactivo2 = pactivo2;
	}
	public String getNomPactivo1() {
		return nomPactivo1;
	}
	public void setNomPactivo1(String nomPactivo1) {
		this.nomPactivo1 = nomPactivo1;
	}
	public String getNomPactivo2() {
		return nomPactivo2;
	}
	public void setNomPactivo2(String nomPactivo2) {
		this.nomPactivo2 = nomPactivo2;
	}
	public String getSentido() {
		return sentido;
	}
	public void setSentido(String sentido) {
		this.sentido = sentido;
	}
	public String getTextoSentido() {
		return textoSentido;
	}
	public void setTextoSentido(String textoSentido) {
		this.textoSentido = textoSentido;
	}
	public String getSemaforo() {
		return semaforo;
	}
	public void setSemaforo(String semaforo) {
		this.semaforo = semaforo;
	}
	public String getTextoSemaforo() {
		return textoSemaforo;
	}
	public void setTextoSemaforo(String textoSemaforo) {
		this.textoSemaforo = textoSemaforo;
	}
	public String getMedidas() {
		return medidas;
	}
	public void setMedidas(String medidas) {
		this.medidas = medidas;
	}
	public String getTextoMedidas() {
		return textoMedidas;
	}
	public void setTextoMedidas(String textoMedidas) {
		this.textoMedidas = textoMedidas;
	}
	

   
}
package lopicost.spd.model.rd;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lopicost.spd.model.Medicamento;


public class MedicamentoRobot extends Medicamento{
    private String nombreMedicamentoBolsa;
    private String fechaDesemblistado;
    private List<DiaTomas> diaTomas = new ArrayList<DiaTomas>();
    
	private float cantidadEmblistadaSPD;

	
    private String leyendaInfo;
    private String formaFarmaceutica;
    
    private String  idResidenciaCarga;
    private String  NombreConsejoCn;
    private String  prescriptionOrderNumber;
    private String  diaEmbolsado;
    private String  horaEmbolsado;
    private int  totalBolsas;
    private String  numeroOrdenBolsa;
    private String  primerIdBolsaSPD; 
    private String  ultimoIdBolsaSPD; 
    private String  idBolsa;
    private String  codigoBarras;
    private String  codigoMedicamentoRobot; 
    private String  freeInformation;
    private int  offsetDays;
    private String  doseTime;
    private int  numeroTolva;
    private Date  fechaInsert;


 	
	public DiaTomas getDiaTomaSegura(int index) {
	    if (index >= 0 && index < diaTomas.size()) {
	        return diaTomas.get(index); // puede devolver un objeto o null
	    }
	    return null;
	}
	public String getFormaFarmaceutica() {
		return formaFarmaceutica;
	}
	public void setFormaFarmaceutica(String formaFarmaceutica) {
		this.formaFarmaceutica = formaFarmaceutica;
	}
	public String getNombreMedicamentoBolsa() {
		return nombreMedicamentoBolsa;
	}
	public void setNombreMedicamentoBolsa(String nombreMedicamentoBolsa) {
		this.nombreMedicamentoBolsa = nombreMedicamentoBolsa;
	}
	public List<DiaTomas> getDiaTomas() {
		return diaTomas;
	}
	public void setDiaTomas(List<DiaTomas> diaTomas) {
		this.diaTomas = diaTomas;
	}
	public String getLeyendaInfo() {
		return leyendaInfo;
	}
	public void setLeyendaInfo(String leyendaInfo) {
		this.leyendaInfo = leyendaInfo;
	}

	public String getFechaDesemblistado() {
		return fechaDesemblistado;
	}
	public void setFechaDesemblistado(String fechaDesemblistado) {
		this.fechaDesemblistado = fechaDesemblistado;
	}
	public float getCantidadEmblistadaSPD() {
		return cantidadEmblistadaSPD;
	}
	public void setCantidadEmblistadaSPD(float cantidadEmblistadaSPD) {
		this.cantidadEmblistadaSPD = cantidadEmblistadaSPD;
	}
	public String getIdResidenciaCarga() {
		return idResidenciaCarga;
	}
	public void setIdResidenciaCarga(String idResidenciaCarga) {
		this.idResidenciaCarga = idResidenciaCarga;
	}
	public String getNombreConsejoCn() {
		return NombreConsejoCn;
	}
	public void setNombreConsejoCn(String nombreConsejoCn) {
		NombreConsejoCn = nombreConsejoCn;
	}
	public String getPrescriptionOrderNumber() {
		return prescriptionOrderNumber;
	}
	public void setPrescriptionOrderNumber(String prescriptionOrderNumber) {
		this.prescriptionOrderNumber = prescriptionOrderNumber;
	}
	public String getDiaEmbolsado() {
		return diaEmbolsado;
	}
	public void setDiaEmbolsado(String diaEmbolsado) {
		this.diaEmbolsado = diaEmbolsado;
	}
	public String getHoraEmbolsado() {
		return horaEmbolsado;
	}
	public void setHoraEmbolsado(String horaEmbolsado) {
		this.horaEmbolsado = horaEmbolsado;
	}
	public int getTotalBolsas() {
		return totalBolsas;
	}
	public void setTotalBolsas(int totalBolsas) {
		this.totalBolsas = totalBolsas;
	}
	public String getNumeroOrdenBolsa() {
		return numeroOrdenBolsa;
	}
	public void setNumeroOrdenBolsa(String numeroOrdenBolsa) {
		this.numeroOrdenBolsa = numeroOrdenBolsa;
	}
	public String getPrimerIdBolsaSPD() {
		return primerIdBolsaSPD;
	}
	public void setPrimerIdBolsaSPD(String primerIdBolsaSPD) {
		this.primerIdBolsaSPD = primerIdBolsaSPD;
	}
	public String getUltimoIdBolsaSPD() {
		return ultimoIdBolsaSPD;
	}
	public void setUltimoIdBolsaSPD(String ultimoIdBolsaSPD) {
		this.ultimoIdBolsaSPD = ultimoIdBolsaSPD;
	}
	public String getIdBolsa() {
		return idBolsa;
	}
	public void setIdBolsa(String idBolsa) {
		this.idBolsa = idBolsa;
	}
	public String getCodigoBarras() {
		return codigoBarras;
	}
	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}
	public String getCodigoMedicamentoRobot() {
		return codigoMedicamentoRobot;
	}
	public void setCodigoMedicamentoRobot(String codigoMedicamentoRobot) {
		this.codigoMedicamentoRobot = codigoMedicamentoRobot;
	}
	public String getFreeInformation() {
		return freeInformation;
	}
	public void setFreeInformation(String freeInformation) {
		this.freeInformation = freeInformation;
	}
	public int getOffsetDays() {
		return offsetDays;
	}
	public void setOffsetDays(int offsetDays) {
		this.offsetDays = offsetDays;
	}
	public String getDoseTime() {
		return doseTime;
	}
	public void setDoseTime(String doseTime) {
		this.doseTime = doseTime;
	}
	public int getNumeroTolva() {
		return numeroTolva;
	}
	public void setNumeroTolva(int numeroTolva) {
		this.numeroTolva = numeroTolva;
	}
	public Date getFechaInsert() {
		return fechaInsert;
	}
	public void setFechaInsert(Date fechaInsert) {
		this.fechaInsert = fechaInsert;
	}
	
}

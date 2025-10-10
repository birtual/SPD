package lopicost.spd.model.rd;

import java.util.Date;

import lopicost.spd.model.Medicamento;

public class MedicamentoReceta extends Medicamento{
    private String codigoPrescrito;
    private String descripcionPrescrito;
    private String codigoDispensado;
    private String vendedor;
    private String fechaVenta;
    private String idVenta;
    private String idNLinea;
    private boolean ventaAntigua=false;
    
    
    
	public boolean isVentaAntigua() {
		return ventaAntigua;
	}
	public void setVentaAntigua(boolean ventaAntigua) {
		this.ventaAntigua = ventaAntigua;
	}
	public String getCodigoPrescrito() {
		return codigoPrescrito;
	}
	public void setCodigoPrescrito(String codigoPrescrito) {
		this.codigoPrescrito = codigoPrescrito;
	}
	public String getDescripcionPrescrito() {
		return descripcionPrescrito;
	}
	public void setDescripcionPrescrito(String descripcionPrescrito) {
		this.descripcionPrescrito = descripcionPrescrito;
	}
	public String getCodigoDispensado() {
		return codigoDispensado;
	}
	public void setCodigoDispensado(String codigoDispensado) {
		this.codigoDispensado = codigoDispensado;
	}
	public String getVendedor() {
		return vendedor;
	}
	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}
	public String getFechaVenta() {
		return fechaVenta;
	}
	public void setFechaVenta(String fechaVenta) {
		this.fechaVenta = fechaVenta;
	}
	public String getIdVenta() {
		return idVenta;
	}
	public void setIdVenta(String idVenta) {
		this.idVenta = idVenta;
	}
	public String getIdNLinea() {
		return idNLinea;
	}
	public void setIdNLinea(String idNLinea) {
		this.idNLinea = idNLinea;
	}


	
    
}

package lopicost.spd.struts.form;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import lopicost.spd.model.DivisionResidencia;
import lopicost.spd.model.Paciente;
import lopicost.spd.struts.bean.CamposPantallaBean;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.struts.bean.PacienteBean;


public class PacientesForm   extends GenericForm {

	private List<PacienteBean> listaPacientesBean = new ArrayList();
	private PacienteBean pacienteBean = new PacienteBean();

	
	private String oidPaciente;						//oidPaciente			int,
	private String cipFicheroResi="";      			//cipFicheroResi		varchar(20),
	private String CIP; 							//CIP					varchar(20),
  	private String idPacienteResidencia="";			//idPacienteResidencia	varchar(50) DEFAULT '',
	private String nombre="";						//nom         			varchar(100) DEFAULT '',,
	private String apellido1="";					//apellido1             varchar(100),
	private String apellido2="";					//apellido2             varchar(100),
	private String nombreApellidos="";				//nomCognoms	 	 	nombre + '' + apellido1 + ' ' +apellido2,
	private String apellidosNombre="";				//cognomsNom            apellido1 + ' ' +apellido2 + ', ' + nombre,
	
	private String numIdentidad="";					//nIdentidad            varchar(25) DEFAULT '',
	private String segSocial="";					//segSocial             varchar(25) DEFAULT '',
	private String planta="";						//planta                varchar(25) DEFAULT '',
	private String habitacion="";					//habitacion            varchar(25) DEFAULT '',
	private String idDivisionResidencia;			//idDivisionResidencia  varchar(50) DEFAULT '',
	private String spd="";							//spd                   varchar(20) DEFAULT '',
	private Date fechaProceso;						//fechaProceso          datetime DEFAULT getdate(),
	private int exitus;								//exitus                bit DEFAULT 0,
	private String estatus="";						//estatus               varchar(255) DEFAULT '',
	private String bolquers="";						//bolquers              varchar(20) DEFAULT 'S',
	private String mutua;						    //mutua	                varchar(20) DEFAULT 'N',
	
	private String comentarios="";					//comentarios           varchar(255) DEFAULT '',
	private String fechaAltaPaciente;				//fechaAltaPaciente     varchar(30) DEFAULT getdate(),
	private String idFarmatic="";					//idFarmatic            varchar(15),
	private String codigoUP;						//codigoUP				varchar(15),
	private List ficheroResiCabecera;				//Listado de los tratamiento cargado del paciente para una producción 				
	private int oidFicheroResiCabecera;				//tratamiento cargado del paciente para una producción 				


	private String filtroEstadosResidente = "";
	private String filtroEstadosSPD = "";
	private String filtroEstatusResidente = "";
	private String filtroEstadosCabecera = "";
	private String filtroProceso = "";
	private String filtroBolquers = "";
	private String filtroMutua = "";
	private String filtroDivisionResidenciasCargadas = "";
	private List<FicheroResiBean> listaEstadosResidente = new ArrayList();
	private List<FicheroResiBean> listaEstadosSPD = new ArrayList();
	private List<FicheroResiBean> listaEstatusResidente = new ArrayList();
	private List<FicheroResiBean> listaEstadosCabecera = new ArrayList();
	private List<FicheroResiBean> listaProcesosCargados = new ArrayList();
	private List<FicheroResiBean> listaBolquers = new ArrayList();
	private List<DivisionResidencia> listaDivisionResidencias= new ArrayList();
	private List<FicheroResiBean> listaDivisionResidenciasCargadas = new ArrayList();
	private List<?> itemListBeans;
	private int diasCalculo;					
	private CamposPantallaBean camposPantallaBean = new CamposPantallaBean();
	private List listaTomasCabecera = new ArrayList();
	
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        // Re-inicializa con la fecha actual cada vez que se resetea el formulario
        inicializarFechaAlta();
       // Inicializar el EstadosSPD con 'S' si no está establecido
       if (this.filtroEstadosSPD == null || this.filtroEstadosSPD.isEmpty()) {
           this.filtroEstadosSPD = "%";
       }
       // Inicializar el pañales con 'S' si no está establecido
       if (this.filtroBolquers == null || this.filtroBolquers.isEmpty()) {
           this.filtroBolquers = "%";
       }    
       // Inicializar el estado a ACTIVO si no está establecido
       if (this.filtroEstadosResidente == null || this.filtroEstadosResidente.isEmpty()) {
           this.filtroEstadosResidente = "ACTIVO";
       }    
       // Inicializar el estado a ACTIVO si no está establecido
       if (this.filtroEstatusResidente == null || this.filtroEstatusResidente.isEmpty()) {
           this.filtroEstatusResidente = "";
       }    
      
       
       
    }
 
    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        
        // Validar nombre
        if (nombre == null || nombre.trim().isEmpty()) {
            errors.add("nombre", new ActionMessage("error.nombre.requerido"));
        }
        if (CIP == null || CIP.trim().isEmpty()) {
            errors.add("CIP", new ActionMessage("error.CIP.requerido"));
        }       

        return errors;
    }
    
    
    private void inicializarFechaAlta() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        this.fechaAltaPaciente = sdf.format(new Date());
    }
    
	public List<PacienteBean> getListaPacientesBean() {
		return listaPacientesBean;
	}
	public void setListaPacientesBean(List<PacienteBean> listaPacientesBean) {
		this.listaPacientesBean = listaPacientesBean;
	}
	public String getOidPaciente() {
		return oidPaciente;
	}
	public void setOidPaciente(String oidPaciente) {
		this.oidPaciente = oidPaciente;
	}
	public String getCipFicheroResi() {
		return cipFicheroResi;
	}
	public void setCipFicheroResi(String cipFicheroResi) {
		this.cipFicheroResi = cipFicheroResi;
	}
	public String getCIP() {
		return CIP;
	}
	public void setCIP(String cIP) {
		CIP = cIP;
	}
	public String getIdPacienteResidencia() {
		return idPacienteResidencia;
	}
	public void setIdPacienteResidencia(String idPacienteResidencia) {
		this.idPacienteResidencia = idPacienteResidencia;
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
	public String getNombreApellidos() {
		return nombreApellidos;
	}
	public void setNombreApellidos(String nombreApellidos) {
		this.nombreApellidos = nombreApellidos;
	}
	public String getApellidosNombre() {
		return apellidosNombre;
	}
	public void setApellidosNombre(String apellidosNombre) {
		this.apellidosNombre = apellidosNombre;
	}
	public String getNumIdentidad() {
		return numIdentidad;
	}
	public void setNumIdentidad(String numIdentidad) {
		this.numIdentidad = numIdentidad;
	}
	public String getSegSocial() {
		return segSocial;
	}
	public void setSegSocial(String segSocial) {
		this.segSocial = segSocial;
	}
	public String getPlanta() {
		return planta;
	}
	public void setPlanta(String planta) {
		this.planta = planta;
	}
	public String getHabitacion() {
		return habitacion;
	}
	public void setHabitacion(String habitacion) {
		this.habitacion = habitacion;
	}
	public String getIdDivisionResidencia() {
		return idDivisionResidencia;
	}
	public void setIdDivisionResidencia(String idDivisionResidencia) {
		this.idDivisionResidencia = idDivisionResidencia;
	}
	public String getSpd() {
		return spd;
	}
	public void setSpd(String spd) {
		this.spd = spd;
	}
	public Date getFechaProceso() {
		return fechaProceso;
	}
	public void setFechaProceso(Date fechaProceso) {
		this.fechaProceso = fechaProceso;
	}
	public int getExitus() {
		return exitus;
	}
	public void setExitus(int exitus) {
		this.exitus = exitus;
	}
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	public String getBolquers() {
		return bolquers;
	}
	public void setBolquers(String bolquers) {
		this.bolquers = bolquers;
	}
	public String getComentarios() {
		return comentarios;
	}
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
	public String getFechaAltaPaciente() {
		return fechaAltaPaciente;
	}
	public void setFechaAltaPaciente(String fechaAltaPaciente) {
		this.fechaAltaPaciente = fechaAltaPaciente;
	}
	public List getFicheroResiCabecera() {
		return ficheroResiCabecera;
	}
	public void setFicheroResiCabecera(List ficheroResiCabecera) {
		this.ficheroResiCabecera = ficheroResiCabecera;
	}
	public int getOidFicheroResiCabecera() {
		return oidFicheroResiCabecera;
	}
	public void setOidFicheroResiCabecera(int oidFicheroResiCabecera) {
		this.oidFicheroResiCabecera = oidFicheroResiCabecera;
	}
	public String getFiltroProceso() {
		return filtroProceso;
	}
	public void setFiltroProceso(String filtroProceso) {
		this.filtroProceso = filtroProceso;
	}
	public String getFiltroDivisionResidenciasCargadas() {
		return filtroDivisionResidenciasCargadas;
	}
	public void setFiltroDivisionResidenciasCargadas(String filtroDivisionResidenciasCargadas) {
		this.filtroDivisionResidenciasCargadas = filtroDivisionResidenciasCargadas;
	}
	public List<FicheroResiBean> getListaDivisionResidenciasCargadas() {
		return listaDivisionResidenciasCargadas;
	}
	public void setListaDivisionResidenciasCargadas(List<FicheroResiBean> listaDivisionResidenciasCargadas) {
		this.listaDivisionResidenciasCargadas = listaDivisionResidenciasCargadas;
	}

	public List<FicheroResiBean> getListaEstadosCabecera() {
		return listaEstadosCabecera;
	}
	public void setListaEstadosCabecera(List<FicheroResiBean> listaEstadosCabecera) {
		this.listaEstadosCabecera = listaEstadosCabecera;
	}
	public List<FicheroResiBean> getListaProcesosCargados() {
		return listaProcesosCargados;
	}
	public void setListaProcesosCargados(List<FicheroResiBean> listaProcesosCargados) {
		this.listaProcesosCargados = listaProcesosCargados;
	}
	public List<DivisionResidencia> getListaDivisionResidencias() {
		return listaDivisionResidencias;
	}
	public void setListaDivisionResidencias(List<DivisionResidencia> listaDivisionResidencias) {
		this.listaDivisionResidencias = listaDivisionResidencias;
	}
	public String getFiltroEstadosResidente() {
		return filtroEstadosResidente;
	}
	public void setFiltroEstadosResidente(String filtroEstadosResidente) {
		this.filtroEstadosResidente = filtroEstadosResidente;
	}
	public String getFiltroEstadosSPD() {
		return filtroEstadosSPD;
	}
	public void setFiltroEstadosSPD(String filtroEstadosSPD) {
		this.filtroEstadosSPD = filtroEstadosSPD;
	}
	public String getFiltroEstadosCabecera() {
		return filtroEstadosCabecera;
	}
	public void setFiltroEstadosCabecera(String filtroEstadosCabecera) {
		this.filtroEstadosCabecera = filtroEstadosCabecera;
	}
	public List<FicheroResiBean> getListaEstadosResidente() {
		return listaEstadosResidente;
	}
	public void setListaEstadosResidente(List<FicheroResiBean> listaEstadosResidente) {
		this.listaEstadosResidente = listaEstadosResidente;
	}
	public List<FicheroResiBean> getListaEstadosSPD() {
		return listaEstadosSPD;
	}
	public void setListaEstadosSPD(List<FicheroResiBean> listaEstadosSPD) {
		this.listaEstadosSPD = listaEstadosSPD;
	}
	public String getFiltroEstatusResidente() {
		return filtroEstatusResidente;
	}
	public void setFiltroEstatusResidente(String filtroEstatusResidente) {
		this.filtroEstatusResidente = filtroEstatusResidente;
	}
	public List<FicheroResiBean> getListaEstatusResidente() {
		return listaEstatusResidente;
	}
	public void setListaEstatusResidente(List<FicheroResiBean> listaEstatusResidente) {
		this.listaEstatusResidente = listaEstatusResidente;
	}
	public PacienteBean getPacienteBean() {
		return pacienteBean;
	}
	public void setPacienteBean(PacienteBean pacienteBean) {
		this.pacienteBean = pacienteBean;
	}
	public String getFiltroBolquers() {
		return filtroBolquers;
	}
	public void setFiltroBolquers(String filtroBolquers) {
		this.filtroBolquers = filtroBolquers;
	}
	public List<FicheroResiBean> getListaBolquers() {
		return listaBolquers;
	}
	public void setListaBolquers(List<FicheroResiBean> listaBolquers) {
		this.listaBolquers = listaBolquers;
	}
	public String getMutua() 									{			return mutua;									}
	public void setMutua(String mutua) 							{			this.mutua = mutua;								}

	public String getIdFarmatic() {
		return idFarmatic;
	}

	public void setIdFarmatic(String idFarmatic) {
		this.idFarmatic = idFarmatic;
	}

	public String getCodigoUP() {
		return codigoUP;
	}

	public void setCodigoUP(String codigoUP) {
		this.codigoUP = codigoUP;
	}

	public String getFiltroMutua() {
		return filtroMutua;
	}

	public void setFiltroMutua(String filtroMutua) {
		this.filtroMutua = filtroMutua;
	}


    public List<?> getItemListBeans() {
		return itemListBeans;
	}

	public void setItemListBeans(List<?> itemListBeans) {
		this.itemListBeans = itemListBeans;
	}

	public int getDiasCalculo() {
		return diasCalculo;
	}

	public void setDiasCalculo(int diasCalculo) {
		this.diasCalculo = diasCalculo;
	}

	public CamposPantallaBean getCamposPantallaBean() {
		return camposPantallaBean;
	}

	public void setCamposPantallaBean(CamposPantallaBean camposPantallaBean) {
		this.camposPantallaBean = camposPantallaBean;
	}

	public List getListaTomasCabecera() {
		return listaTomasCabecera;
	}

	public void setListaTomasCabecera(List listaTomasCabecera) {
		this.listaTomasCabecera = listaTomasCabecera;
	}


	
	
}

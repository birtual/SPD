package lopicost.spd.struts.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lopicost.spd.robot.bean.rd.BolsaSPD;
import lopicost.spd.robot.bean.rd.MedicamentoReceta;
import lopicost.spd.robot.bean.rd.MedicamentoPaciente;
import lopicost.spd.utils.HelperSPD;


public class PacienteBean {
	
	
	  private int oidPaciente;				
	  private String id; 							//CIP					varchar(20),
	  private String CIP; 							//CIP					varchar(20),
	  private String CIPMask; 						
	  private String idPacienteResidencia;			//idPacienteResidencia	varchar(50) DEFAULT '',
	  private String nombre;						//nom         			varchar(100) DEFAULT '',,
	  private String nombreMask; 						
	  private String apellido1;						//apellido1             varchar(100),
	  private String apellido1Mask; 						
	  private String apellido2;						//apellido2             varchar(100),
	  private String apellido2Mask; 						
	  private String nombreApellidos;				//nomCognoms	 	 	nombre + '' + apellido1 + ' ' +apellido2,
	  private String nombreApellidosMask; 						
	  private String apellidosNombre;				//cognomsNom           
	  private String apellidosNombreMask; 						
	  private String apellidos;						//cognoms            	
	  private String apellidosMask; 						
	  private String numIdentidad;					//nIdentidad            varchar(25) DEFAULT '',
	  private String numIdentidadMask; 						
	  private String segSocial	;					//segSocial             varchar(25) DEFAULT '',
	  private String segSocialMask; 						
	  private String planta;						//planta                varchar(25) DEFAULT '',
	  private String habitacion;					//habitacion            varchar(25) DEFAULT '',
	  private String idDivisionResidencia;			//idDivisionResidencia  varchar(50) DEFAULT '',
	  private String spd;							//spd                   varchar(20) DEFAULT '',
	  private Date fechaProceso;					//fechaProceso          datetime DEFAULT getdate(),
	  private String fechaHoraProceso;					
	  private int exitus;							//exitus                bit DEFAULT 0,
	  private String estatus;						//estatus               varchar(255) DEFAULT '',
	  private String bolquers;						//bolquers              varchar(20) DEFAULT 'S',
	  private String comentarios;					//comentarios           varchar(255) DEFAULT '',
	  private String fechaAltaPaciente;				//fechaAltaPaciente     varchar(30) DEFAULT getdate(),
//	  private String cipFicheroResi;        		//CipFicheroResi        varchar(50) 
	  private List listaTratamientos = new ArrayList();
	  private List listaTratamientosFichero= new ArrayList();;
	  private String activo; 
	  private String idPharmacy;					//Id Farmatic
	  private String UPFarmacia;					//Codigo UP Farmacia
	  private String mutua;						    //mutua	              varchar(20) DEFAULT 'S',
	  private String mensajeTratamiento;	
	  private String mensajePendientes;	
	  private String claseId;	
	  private List<BolsaSPD> produccionSPD;        				
	  private List<MedicamentoReceta> dispensacionesReceta;        				

	  public PacienteBean() 									{			super();										}
		
	  public int getOidPaciente() 								{			return oidPaciente;								}
	  public String getId() 									{			return id;										}
	  public String getCIP() 									{			return this.CIP;								}
	  public String getCIPMask() 								{			return this.CIPMask;							}
	  public String getNombre() 								{			return this.nombre;								}
	  public String getNombreMask() 							{			return this.nombreMask;							}
	  public String getApellido1() 								{			return this.apellido1;							}
	  public String getApellido1Mask() 							{			return this.apellido1Mask;						}
	  public String getApellido2() 								{			return this.apellido2;							}
	  public String getApellido2Mask() 							{			return this.apellido2Mask;						}
	  public String getNombreApellidos() 						{			return this.nombre + ' ' + this.apellido1 + ' ' + this.apellido2;	}
	  public String getNombreApellidosMask() 					{			return this.nombreApellidosMask;				}
	  public String getApellidosNombre() 						{			return this.apellidosNombre;					}
	  public String getApellidosNombreMask() 					{			return this.apellidosNombreMask;				}
	  public String getApellidos() 								{			return apellidos;								}
	  public String getApellidosMask() 							{			return apellidosMask;							}
	  public String getSegSocial()								{			return segSocial;								}
	  public String getSegSocialMask() 							{			return segSocialMask;							}
	  public String getNumIdentidad() 							{			return numIdentidad;							}
	  public String getNumIdentidadMask() 						{			return numIdentidadMask;						}
	  public String getIdPacienteResidencia() 					{			return idPacienteResidencia;					}
	  public String getPlanta()									{			return planta;									}
	  public String getHabitacion() 							{			return habitacion;								}
	  public String getIdDivisionResidencia() 					{			return idDivisionResidencia;					}
	  public String getSpd() 									{			return spd;										}
	  public Date getFechaProceso() 							{			return fechaProceso;							}
	  public int getExitus() 									{			return exitus;									}
	  public String getEstatus() 								{			return estatus;									}
	  public String getBolquers()								{			return bolquers;								}
	  public String getComentarios() 							{			return comentarios;								}
	  public String getFechaAltaPaciente() 						{			return fechaAltaPaciente;						}
	  public List getListaTratamientos() 						{			return listaTratamientos;						}
	  public List getListaTratamientosFichero() 				{			return listaTratamientosFichero;				}
	  public String getActivo() 								{			return activo;									}
	  public String getIdPharmacy() 							{			return idPharmacy;								}
	  public String getMutua() 									{			return mutua;									}
	  public String getMensajeTratamiento() 					{			return mensajeTratamiento;						}
	  public String getMensajePendientes() 						{			return mensajePendientes;						}
	  public String getClaseId() 								{			return claseId;									}
	  public String getUPFarmacia() 							{			return UPFarmacia;								}
	  
	  public void setSpd(String spd) 							{			this.spd = spd;									}
	  public void setIdPacienteResidencia(String idPacRes) 		{			this.idPacienteResidencia = idPacRes;			}
	  public void setPlanta(String planta) 						{			this.planta = planta;							}
	  public void setHabitacion(String habitacion) 				{			this.habitacion = habitacion;					}
	  public void setIdDivisionResidencia(String idDivResi) 	{			this.idDivisionResidencia = idDivResi;			}
	  public void setFechaProceso(Date fechaProceso) 			{			this.fechaProceso = fechaProceso;				}
	  public void setExitus(int exitus) 						{			this.exitus = exitus;							}
	  public void setEstatus(String estatus) 					{			this.estatus = estatus;							}
	  public void setBolquers(String bolquers) 					{			this.bolquers = bolquers;						}
	  public void setComentarios(String comentarios) 			{			this.comentarios = comentarios;					}
	  public void setFechaAltaPaciente(String fechaAltaPac) 	{			this.fechaAltaPaciente = fechaAltaPac;			}
	  public void setListaTratamientos(List listatrat) 			{			this.listaTratamientos = listatrat;				}
	  public void setListaTratamientosFichero(List listatrat) 	{			this.listaTratamientosFichero = listatrat;		}
	  public void setActivo(String activo) 						{			this.activo = activo;							}
	  public void setIdPharmacy(String idPharmacy) 				{			this.idPharmacy = idPharmacy;					}
	  public void setMutua(String mutua) 						{			this.mutua = mutua;								}
	  public void setMensajeTratamiento(String mensaje)			{			this.mensajeTratamiento = mensaje;				}
	  public void setMensajePendientes(String mensaje)			{			this.mensajePendientes = mensaje;				}
	  public void setClaseId(String claseId) 					{			this.claseId = claseId;							}
	  public void setUPFarmacia(String uPFarmacia) 				{			this.UPFarmacia = uPFarmacia;					}
	  public void setOidPaciente(int oidPaciente) 				{			this.oidPaciente = oidPaciente;					}
	  public void setId(String id)						 		{			this.id = id;									}

	  public void setCIP(String cIP) 							{			this.setCIPMask(HelperSPD.enmascararCIP(cIP)); 
	  																		this.CIP = cIP;									
	  															}
	  public void setCIPMask(String cIPMask) 					{			this.CIPMask = cIPMask;							}
	  public void setNombre(String data) 						{			this.nombre = data;	 
	  																		this.setNombreMask(HelperSPD.enmascararNombre(data, 2, 0));	
	  															}
	  public void setNombreMask(String nombreMask) 				{			this.nombreMask = nombreMask;					}
	  public void setApellido1(String data) 					{			this.apellido1 = data;
	  																		this.setApellido1Mask(HelperSPD.enmascararNombre(data, 2, 0));	
	  															}
	  public void setApellido1Mask(String data) 				{			this.apellido1Mask = data;						}
	  public void setApellido2(String data) 					{			this.apellido2 = data;
	  																		this.setApellido2Mask(HelperSPD.enmascararNombre(data, 2, 0));	
	  															}
	  public void setApellido2Mask(String data) 				{			this.apellido2Mask = data;						}
	  public void setNombreApellidos(String data) 				{			this.nombreApellidos = data;				
	  																		this.setNombreApellidosMask(HelperSPD.enmascararNombre(data, 2, 0));	
	  															}
	  public void setNombreApellidosMask(String data) 			{			this.nombreApellidosMask = data;				}
	  public void setApellidosNombre(String data) 				{			this.apellidosNombre = data;
	  																		this.setNombreApellidosMask(HelperSPD.enmascararNombre(data, 2, 0));	
	  															}
	  public void setApellidosNombreMask(String data) 			{			this.apellidosNombreMask = data;				}
	  public void setApellidos(String data) 					{			this.apellidos = data;						
		  																	this.setApellidosMask(HelperSPD.enmascararNombre(data, 2, 0));	
		  														}
	  public void setApellidosMask(String data) 				{			this.apellidosMask = data;						}
	  public void setSegSocial(String data) 					{			this.segSocial = data;						
		  																	this.setSegSocialMask(HelperSPD.enmascararNombre(data, 1, 3));
		  														}
	  public void setSegSocialMask(String data) 				{			this.segSocialMask = data;						}
	  public void setNumIdentidad(String data) 					{			this.numIdentidad = data;					
	  																		this.setNumIdentidadMask(HelperSPD.enmascararNombre(data));
		  														}
	  public void setNumIdentidadMask(String nIdentidadMask) 		{			this.numIdentidadMask = nIdentidadMask;			}

	  
	public String getFechaHoraProceso() {
		return fechaHoraProceso;
	}

	public void setFechaHoraProceso(String fechaHoraProceso) {
		this.fechaHoraProceso = fechaHoraProceso;
	}

	public List<BolsaSPD> getProduccionSPD() {
		return produccionSPD;
	}

	public void setProduccionSPD(List<BolsaSPD> produccionSPD) {
		this.produccionSPD = produccionSPD;
	}

	public List<MedicamentoReceta> getDispensacionesReceta() {
		return dispensacionesReceta;
	}

	public void setDispensacionesReceta(List<MedicamentoReceta> list) {
		this.dispensacionesReceta = list;
	}
	  
}	

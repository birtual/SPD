package lopicost.spd.struts.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import lopicost.spd.utils.StringUtil;

public class PacienteBean {
	
	
	  private int oidPaciente;				
	  private String id; 							//CIP					varchar(20),
	  private String CIP; 							//CIP					varchar(20),
	  private String idPacienteResidencia;			//idPacienteResidencia	varchar(50) DEFAULT '',
	  private String nombre;						//nom         			varchar(100) DEFAULT '',,
	  private String apellido1;						//apellido1             varchar(100),
	  private String apellido2;						//apellido2             varchar(100),
	  private String nombreApellidos;				//nomCognoms	 	 	nombre + '' + apellido1 + ' ' +apellido2,
	  private String apellidosNombre;				//cognomsNom           
	  private String apellidos;						//cognoms            	
	  private String nIdentidad;					//nIdentidad            varchar(25) DEFAULT '',
	  private String segSocial	;					//segSocial             varchar(25) DEFAULT '',
	  private String planta;						//planta                varchar(25) DEFAULT '',
	  private String habitacion;					//habitacion            varchar(25) DEFAULT '',
	  private String idDivisionResidencia;			//idDivisionResidencia  varchar(50) DEFAULT '',
	  private String spd;							//spd                   varchar(20) DEFAULT '',
	  private Date fechaProceso;					//fechaProceso          datetime DEFAULT getdate(),
	  private int exitus;							//exitus                bit DEFAULT 0,
	  private String estatus;						//estatus               varchar(255) DEFAULT '',
	  private String bolquers;						//bolquers              varchar(20) DEFAULT 'S',
	  private String comentarios;					//comentarios           varchar(255) DEFAULT '',
	  private String fechaAltaPaciente;				//fechaAltaPaciente     varchar(30) DEFAULT getdate(),
//	  private String cipFicheroResi;        		//CipFicheroResi        varchar(50) 
	  private List listaTratamientos;
	  private List listaTratamientosFichero;
	  private String activo; 
	  private String idPharmacy;					//Id Farmatic
	  private String mutua;						    //mutua	              varchar(20) DEFAULT 'S',
	  private String mensajeTratamiento;	
	  private String mensajePendientes;	
	  private String claseId;	

	  
	  public PacienteBean() 									{			super();										}
	  public int getOidPaciente() 								{			return oidPaciente;								}
	  public void setOidPaciente(int oidPaciente) 				{			this.oidPaciente = oidPaciente;					}
	  public String getId() 									{			return id;										}
	  public void setId(String id)						 		{			this.id = id;									}
	  public void setNombreApellidos(String nombreApellidos) 	{			this.nombreApellidos = nombreApellidos;			}
	  public String getNombreApellidos() 						{			return this.nombre + ' ' + this.apellido1 + ' ' + this.apellido2;	}
	  public String getApellidosNombre() 						{			return this.apellidosNombre;					}
	  public void setApellidosNombre(String apellidosNombre) 	{			this.apellidosNombre = apellidosNombre;			}
	  public String getCIP() 									{			return CIP;										}
	  public void setCIP(String cIP) 							{			CIP = cIP;										}
	  public String getIdPacienteResidencia() 					{			return idPacienteResidencia;					}
	  public void setIdPacienteResidencia(String idPacRes) 		{			this.idPacienteResidencia = idPacRes;			}
	  public String getNombre() 								{			return nombre;									}
	  public void setNombre(String nombre) 						{			this.nombre = nombre;							}
	  public String getApellido1() 								{			return apellido1;								}
	  public void setApellido1(String apellido1) 				{			this.apellido1 = apellido1;						}
	  public String getApellido2() 								{			return apellido2;								}
	  public void setApellido2(String apellido2) 				{			this.apellido2 = apellido2;						}
	  public String getNIdentidad() 							{			return nIdentidad;								}
	  public void setNIdentidad(String nIdentidad) 				{			this.nIdentidad = nIdentidad;					}
	  public String getSegSocial()								{			return segSocial;								}
	  public void setSegSocial(String segSocial) 				{			this.segSocial = segSocial;						}
	  public String getPlanta()									{			return planta;									}
	  public void setPlanta(String planta) 						{			this.planta = planta;							}
	  public String getHabitacion() 							{			return habitacion;								}
	  public void setHabitacion(String habitacion) 				{			this.habitacion = habitacion;					}
	  public String getIdDivisionResidencia() 					{			return idDivisionResidencia;					}
	  public void setIdDivisionResidencia(String idDivResi) 	{			this.idDivisionResidencia = idDivResi;			}
	  public String getSpd() 									{			return spd;										}
	  public void setSpd(String spd) 							{			this.spd = spd;									}
	  public Date getFechaProceso() 							{			return fechaProceso;							}
	  public void setFechaProceso(Date fechaProceso) 			{			this.fechaProceso = fechaProceso;				}
	  public int getExitus() 									{			return exitus;									}
	  public void setExitus(int exitus) 						{			this.exitus = exitus;							}
	  public String getEstatus() 								{			return estatus;									}
	  public void setEstatus(String estatus) 					{			this.estatus = estatus;							}
	  public String getBolquers()								{			return bolquers;								}
	  public void setBolquers(String bolquers) 					{			this.bolquers = bolquers;						}
	  public String getComentarios() 							{			return comentarios;								}
	  public void setComentarios(String comentarios) 			{			this.comentarios = comentarios;					}
	  public String getFechaAltaPaciente() 						{			return fechaAltaPaciente;						}
	  public void setFechaAltaPaciente(String fechaAltaPac) 	{			this.fechaAltaPaciente = fechaAltaPac;			}
	  public String getApellidos() 								{			return apellidos;								}
	  public void setApellidos(String apellidos) 				{			this.apellidos = apellidos;						}
	  public String getnIdentidad() 							{			return nIdentidad;								}
	  public void setnIdentidad(String nIdentidad) 				{			this.nIdentidad = nIdentidad;					}
	  public List getListaTratamientos() 						{			return listaTratamientos;						}
	  public void setListaTratamientos(List listatrat) 			{			this.listaTratamientos = listatrat;				}
	  public List getListaTratamientosFichero() 				{			return listaTratamientosFichero;				}
	  public void setListaTratamientosFichero(List listatrat) 	{			this.listaTratamientosFichero = listatrat;		}
	  public String getActivo() 								{			return activo;									}
	  public void setActivo(String activo) 						{			this.activo = activo;							}
	  public String getIdPharmacy() 							{			return idPharmacy;								}
	  public void setIdPharmacy(String idPharmacy) 				{			this.idPharmacy = idPharmacy;					}
	  public String getMutua() 									{			return mutua;									}
	  public void setMutua(String mutua) 						{			this.mutua = mutua;								}
	  public String getMensajeTratamiento() 					{			return mensajeTratamiento;						}
	  public void setMensajeTratamiento(String mensaje)			{			this.mensajeTratamiento = mensaje;				}
	  public String getMensajePendientes() 						{			return mensajePendientes;						}
	  public void setMensajePendientes(String mensaje)			{			this.mensajePendientes = mensaje;				}
	  public String getClaseId() 								{			return claseId;									}
	  public void setClaseId(String claseId) 					{			this.claseId = claseId;							}
  
}	

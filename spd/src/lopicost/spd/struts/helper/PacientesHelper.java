package lopicost.spd.struts.helper;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lopicost.config.pool.dbaccess.Conexion;
import lopicost.spd.controller.SpdLogAPI;
import lopicost.spd.persistence.FicheroResiDetalleDAO;
import lopicost.spd.persistence.PacienteDAO;
import lopicost.spd.persistence.UsuarioDAO;
import lopicost.spd.security.helper.VisibilidadHelper;
import lopicost.spd.struts.bean.BolquersDetalleBean;
import lopicost.spd.struts.bean.DiscrepanciaBean;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.struts.bean.PacienteBean;
import lopicost.spd.struts.bean.TratamientoRctBean;
import lopicost.spd.struts.form.PacientesForm;
import lopicost.spd.utils.SPDConstants;
import lopicost.spd.utils.StringUtil;


/**
 
 *Logica de negocio 
 */
public class PacientesHelper  {


	private final static String cLOGGERHEADER = "PacientesHelper: ";
	private final String cLOGGERHEADER_ERROR = cLOGGERHEADER + "ERROR: ";






	public static boolean existeCIP(String CIP)throws Exception {
		PacienteBean pac = PacienteDAO.getPacientePorCIP(CIP);
		if(pac!=null) return true;
		return false;
	}


	public static PacienteBean getPacientePorCIP(String CIP) throws Exception {
		PacienteBean p = PacienteDAO.getPacientePorCIP(CIP);
		return p;
	}
	

	public static boolean nuevo(String idUsuario, PacientesForm form) throws SQLException {
		boolean result = PacienteDAO.nuevo(form);
		if(result)
		{
		//INICIO creación de log en BBDD
			try{
				SpdLogAPI.addLog(idUsuario, form.getCIP(), form.getIdDivisionResidencia(), null, SpdLogAPI.A_RESIDENTE, SpdLogAPI.B_CREACION, ".", "SpdLog.residentes.creacion.crear", 
						 new String[]{idUsuario, form.getCIP()} );
			}catch(Exception e){}	// Cambios--> @@.
			//FIN creación de log en BBDD
		}
		return result;
	}


	
	/**
	 * Método encargado de actualizar datos del paciente modificado.
	 * Se llamará a diferentes métodos auxiliares
	 * @param idUsuario que realiza el grabado 
	 * @param p PacienteBean sobre el que se realiza el cambio
	 * @throws SQLException 
	 */
	public static void aplicaControles(String idUsuario, PacienteBean p) throws SQLException {
		actualizaDatosClaveSegunEstatus(idUsuario, p);
		//actualizaApellidosNombrePacientes(p);
	}
	
	
	/**
	 * Método encargado de actualizar los estados principales de bd_pacientes, según el estatus escogido en el formulario.
	 * Se traspasa de los procedure.sql que se realizaban cada 5 minutos en SPDAC (set_MarcasSPDpanyales_segunESTATUS.sql)
	 * @param idUsuario que realiza el grabado 
	 * @param p PacienteBean sobre el que se realiza el cambio
	 * @throws SQLException 
	 */

	public static void actualizaDatosClaveSegunEstatus(String idUsuario, PacienteBean p) throws SQLException {
		
		boolean cambios=false;
		String spd =p.getSpd();
		String bolquers =p.getBolquers();
		String activo =p.getActivo();
		int exitus =p.getExitus();

		String querySet="";
		if(p!=null)
		{
			String estatus=p.getEstatus()!=null?p.getEstatus():"";
	
			//en caso que sea baja o similar, spd y pañales pasan a N
			if(StringUtil.limpiarTextoEspaciosYAcentos(estatus, true).contains("CENTRODEDIA")
					|| StringUtil.limpiarTextoEspaciosYAcentos(estatus, true).contains("HOSPITALIZADO")
					|| StringUtil.limpiarTextoEspaciosYAcentos(estatus, true).contains("BAJAVOLUNTARIA")
					|| StringUtil.limpiarTextoEspaciosYAcentos(estatus, true).contains("EXITUS")
				)
				{
					if(p.getSpd().equalsIgnoreCase("S"))
					{
						p.setSpd("N"); 
						if(cambios) querySet+=" , ";
						querySet+=" SPD='N' ";
						cambios=true;
					} 
					if(p.getBolquers().equalsIgnoreCase("S"))
					{
						p.setBolquers("N"); 
						if(cambios) querySet+=" , ";
						querySet+=" bolquers='N' ";
						cambios=true;
					} 
					if(p.getActivo().equalsIgnoreCase("ACTIVO"))
					{
						p.setActivo("INACTIVO");
						if(cambios) querySet+=" , ";
						querySet+=" ACTIVO='INACTIVO' ";
						cambios=true;
					}
					if(p.getExitus()==0)
					{
						p.setExitus(1);
						if(cambios) querySet+=" , ";
						querySet+=" EXITUS=1 ";
						cambios=true;
					}

				}
			
			
			//en caso que sea alta, dejamos spd y pañales a elección del gestor.
			if(StringUtil.limpiarTextoEspaciosYAcentos(estatus, true).contains("ALTA"))
			{
				if(p.getActivo().equalsIgnoreCase("INACTIVO"))
				{
					p.setActivo("ACTIVO");
					if(cambios) querySet+=" , ";
					querySet+=" ACTIVO='ACTIVO' ";
					cambios=true;
				}
				if(p.getExitus()==1)
				{
					p.setExitus(0);
					if(cambios) querySet+=" , ";
					querySet+=" EXITUS=0 ";
					cambios=true;
				}
			}
			

			
		if(cambios) 
		{
			String antesAuto 	=  " | spd: "+ spd + " | pañales: "+ bolquers + " | activo: "+ activo + " | exitus: "+ exitus ;
			String despuesAuto 	=  " | spd: "+ p.getSpd() + " | pañales: "+ p.getBolquers() + " | activo: "+ p.getActivo() + " | exitus: "+ p.getExitus();
			String queryAuto=" UPDATE SPDAC.dbo.bd_pacientes SET " + querySet + "   WHERE  OIDPACIENTE='"+p.getOidPaciente()+"'";
			
			cambios = PacienteDAO.edita(queryAuto);
			//INICIO creación de log automática en base al estado en BBDD
			try{
				SpdLogAPI.addLog(idUsuario, p.getCIP(),  p.getIdDivisionResidencia(), null, SpdLogAPI.A_RESIDENTE, SpdLogAPI.B_EDICION, SpdLogAPI.C_DATOSGENERALES, "SpdLog.residente.edicion.automatica.segunEstado", 
						 new String[]{idUsuario, p.getCIP(), antesAuto, despuesAuto} );
			}catch(Exception e){}	// Cambios--> @@.
			//FIN creación de log en BBDD
		}

		}
	}
	
	
	
/*
 * 	public static void actualizaDatosClaveSegunEstatus(String idUsuario, PacienteBean p) throws SQLException {
		
		boolean cambios=false;
		String spd =p.getSpd();
		String bolquers =p.getBolquers();
		String activo =p.getActivo();
		int exitus =p.getExitus();

		String querySet="";
		if(p!=null)
		{
			String estatus=p.getEstatus()!=null?p.getEstatus():"";
			if( StringUtil.limpiarTextoEspaciosYAcentos(estatus, true).contains("ESTANCIATEMPORAL")
					|| StringUtil.limpiarTextoEspaciosYAcentos(estatus, true).contains("pañales_S")
					|| StringUtil.limpiarTextoEspaciosYAcentos(estatus, true).contains("SPD_S")
				|| StringUtil.limpiarTextoEspaciosYAcentos(estatus, true).contains("ALTA")
			)
			{
				if(p.getSpd().equalsIgnoreCase("N"))
				{
					p.setSpd("S"); 
					if(cambios) querySet+=" , ";
					querySet+=" SPD='S' ";
					cambios=true;
				} 
				if(p.getActivo().equalsIgnoreCase("INACTIVO"))
				{
					p.setActivo("ACTIVO");
					if(cambios) querySet+=" , ";
					querySet+=" ACTIVO='ACTIVO' ";
					cambios=true;
				}
				if(p.getExitus()==1)
				{
					p.setExitus(0);
					if(cambios) querySet+=" , ";
					querySet+=" EXITUS=0 ";
					cambios=true;
				}

			}
			
			if(StringUtil.limpiarTextoEspaciosYAcentos(estatus, true).contains("CENTRODEDIA")
				|| StringUtil.limpiarTextoEspaciosYAcentos(estatus, true).contains("TUTELADO")
				|| StringUtil.limpiarTextoEspaciosYAcentos(estatus, true).contains("HOSPITALIZADO")
				|| StringUtil.limpiarTextoEspaciosYAcentos(estatus, true).contains("BAJAVOLUNTARIA")
				|| StringUtil.limpiarTextoEspaciosYAcentos(estatus, true).contains("EXITUS")
			)
			{
				if(p.getSpd().equalsIgnoreCase("S"))
				{
					p.setSpd("N"); 
					if(cambios) querySet+=" , ";
					querySet+=" SPD='N' ";
					cambios=true;
				} 
				if(p.getBolquers().equalsIgnoreCase("S"))
				{
					p.setBolquers("N"); 
					if(cambios) querySet+=" , ";
					querySet+=" bolquers='N' ";
					cambios=true;
				} 
				if(p.getActivo().equalsIgnoreCase("ACTIVO"))
				{
					p.setActivo("INACTIVO");
					if(cambios) querySet+=" , ";
					querySet+=" ACTIVO='INACTIVO' ";
					cambios=true;
				}
				if(p.getExitus()==0)
				{
					p.setExitus(1);
					if(cambios) querySet+=" , ";
					querySet+=" EXITUS=1 ";
					cambios=true;
				}

			}
			if(p.getSpd().equalsIgnoreCase("S") && StringUtil.limpiarTextoEspaciosYAcentos(estatus, true).contains("SPD_N"))
			{
				p.setSpd("N"); 
				if(cambios) querySet+=" , ";
					querySet+=" SPD='N' ";
				cambios=true;
			}

			if(p.getBolquers().equalsIgnoreCase("S") && StringUtil.limpiarTextoEspaciosYAcentos(estatus, true).contains("pañales_N"))
			{
				p.setBolquers("N"); 
				if(cambios) querySet+=" , ";
					querySet+=" bolquers='N' ";
				cambios=true;
			}
			
			
			
		if(cambios) 
		{
			String antesAuto 	=  " | spd: "+ spd + " | pañales: "+ bolquers + " | activo: "+ activo + " | exitus: "+ exitus ;
			String despuesAuto 	=  " | spd: "+ p.getSpd() + " | pañales: "+ p.getBolquers() + " | activo: "+ p.getActivo() + " | exitus: "+ p.getExitus();
			String queryAuto=" UPDATE SPDAC.dbo.bd_pacientes SET " + querySet + "   WHERE  OIDPACIENTE='"+p.getOidPaciente()+"'";
			
			cambios = PacienteDAO.edita(queryAuto);
			//INICIO creación de log automática en base al estado en BBDD
			try{
				SpdLogAPI.addLog(idUsuario, p.getCIP(),  p.getIdDivisionResidencia(), null, SpdLogAPI.A_RESIDENTE, SpdLogAPI.B_EDICION, SpdLogAPI.C_DATOSGENERALES, "SpdLog.residente.edicion.automatica.segunEstado", 
						 new String[]{idUsuario, p.getCIP(), antesAuto, despuesAuto} );
			}catch(Exception e){}	// Cambios--> @@.
			//FIN creación de log en BBDD
		}

		}
	}
	
*/	
	
	
	/**
	 * Método encargado de actualizar los estados principales de bd_pacientes, según el estatus escogido en el formulario.
	 * Se traspasa de los procedure.sql que se realizaban cada 5 minutos en SPDAC (set_MarcasSPDpanyales_segunESTATUS.sql)
	 * @param idUsuario que realiza el grabado 
	 * @param p PacienteBean sobre el que se realiza el cambio
	
	public static String actualizaDatosClaveSegunEstatus(String idUsuario, PacienteBean p) {
		
		boolean cambios=false;
		String a=p.getSpd();
		String b=p.getActivo();
		String query="";
		if(p!=null)
		{
			String estatus=p.getEstatus()!=null?p.getEstatus():"";
			if(  
				(((p.getSpd()!=null && p.getSpd().equalsIgnoreCase("N")) || (p.getActivo()!=null && p.getActivo().equalsIgnoreCase("INACTIVO")))
				
					&& 
					(
						(estatus.contains("SPD_S") && estatus.contains("pañales_N"))
						|| StringUtil.limpiarTextoEspaciosYAcentos(estatus, true).contains("ESTANCIATEMPORAL")
						|| StringUtil.limpiarTextoEspaciosYAcentos(estatus, true).contains("ALTA")				
					)
				
				))
			{
				p.setSpd("S");
				p.setBolquers("S"); if(estatus.contains("pañales_N")) p.setBolquers("N");  
				p.setActivo("ACTIVO");
				p.setExitus(0);		
				cambios=true;
			}
			else if
			(  

					(((p.getSpd()!=null && p.getSpd().equalsIgnoreCase("S")) || (p.getActivo()!=null && p.getActivo().equalsIgnoreCase("ACTIVO") || p.getExitus()==1 ))
					&& 
					(
						StringUtil.limpiarTextoEspaciosYAcentos(estatus, true).contains("CENTRODEDIA")
						|| StringUtil.limpiarTextoEspaciosYAcentos(estatus, true).contains("TUTELADO")
						|| StringUtil.limpiarTextoEspaciosYAcentos(estatus, true).contains("HOSPITALIZADO")
						|| StringUtil.limpiarTextoEspaciosYAcentos(estatus, true).contains("BAJAVOLUNTARIA")
						|| StringUtil.limpiarTextoEspaciosYAcentos(estatus, true).contains("EXITUS")
						|| (estatus.contains("SPD_N") && estatus.contains("pañales_S")
						|| p.getExitus()==1
						)
					))
			)
			{
				p.setSpd("N");
				p.setBolquers("N");
				p.setActivo("INACTIVO");
				if(StringUtil.limpiarTextoEspaciosYAcentos(estatus, true).contains("EXITUS") || p.getExitus()==1)
					p.setExitus(1);
				
				cambios=true;
			}
			

		}
		if(cambios) query=" UPDATE SPDAC.dbo.bd_pacientes SET spd='" + p.getSpd() + "', bolquers= '" + p.getBolquers() + "', activo='" + p.getActivo() + "', exitus=" + p.getExitus() + "   WHERE  OIDPACIENTE='"+p.getOidPaciente()+"'";
		return query;
	
	}
		
	/**
	 * 	public static void actualizaDatosClaveSegunEstatus(String idUsuario, PacienteBean p) {
		
		boolean cambios=false;
		String antes = "Estatus: " + p.getEstatus() + " | SPD: "+ p.getSpd()  + " | Pañales: "+ p.getBolquers()+ " | Exitus: "+(p.getExitus()==1?"SI":"NO" )+ " | Activo: "+ p.getActivo();
		if(p!=null)
		{
			String estatus=p.getEstatus()!=null?p.getEstatus():"";
			if(  
				!p.getSpd().equalsIgnoreCase("S")
					&& 
					(
						(estatus.contains("SPD_N") && estatus.contains("pañales_S"))
						|| StringUtil.limpiarTextoEspaciosYAcentos(estatus, true).contains("ESTANCIATEMPORAL")
						|| StringUtil.limpiarTextoEspaciosYAcentos(estatus, true).contains("ALTA")				
					)
				
				)
			{
				p.setSpd("S");
				p.setBolquers("S");
				p.setActivo("ACTIVO");
				p.setExitus(0);		
				cambios=true;
			}
			else if
			(  
					!p.getSpd().equalsIgnoreCase("N")
					&& 
					(
						StringUtil.limpiarTextoEspaciosYAcentos(estatus, true).contains("CENTRODEDIA")
						|| StringUtil.limpiarTextoEspaciosYAcentos(estatus, true).contains("TUTELADO")
						|| StringUtil.limpiarTextoEspaciosYAcentos(estatus, true).contains("HOSPITALIZADO")
						|| StringUtil.limpiarTextoEspaciosYAcentos(estatus, true).contains("BAJAVOLUNTARIA")
						|| StringUtil.limpiarTextoEspaciosYAcentos(estatus, true).contains("EXITUS")
						|| (estatus.contains("SPD_S") && estatus.contains("pañales_N"))
					)
			)
			{
				p.setSpd("N");
				p.setBolquers("N");
				p.setActivo("INACTIVO");
				if(StringUtil.limpiarTextoEspaciosYAcentos(estatus, true).contains("EXITUS"))
					p.setExitus(1);
				
				cambios=true;
			}
			
			if(cambios)
			{
				String despues = "Estatus: " + p.getEstatus() + " | SPD: "+ p.getSpd()  + " | Pañales: "+ p.getBolquers()+ " | Exitus: "+(p.getExitus()==1?"SI":"NO" )+ " | Activo: "+ p.getActivo();
				//INICIO creación de log en BBDD
				try{
					SpdLogAPI.addLog(idUsuario, p.getCIP(),  p.getIdDivisionResidencia(), null, SpdLogAPI.A_RESIDENTE, SpdLogAPI.B_EDICION, SpdLogAPI.C_DATOSGENERALES, "SpdLog.residente.edicion.estados", 
							 new String[]{idUsuario, p.getCIP(), antes, despues} );
				}catch(Exception e){}	// Cambios--> @@.
				//FIN creación de log en BBDD
			}
		}
	
	}
	 */
					
	

	/**
	 * Método encargado de actualizar los estados principales de bd_pacientes, según el estatus escogido en el formulario.
	 * Se traspasa de los procedure.sql que se realizaban cada 5 minutos en SPDAC (actualizaApellidosNombrePacientes.sql)
	 * @param idUsuario que realiza el grabado 
	 * @param p PacienteBean sobre el que se realiza el cambio
	/*
	private static boolean actualizaApellidosNombrePacientes(PacienteBean p) 
	{
		if(p!=null)
		{
			String querySet="";
			String queryWhere="";
			boolean cambios =false;
			/*  INICIO apellido1
			String apellido1 = (p.getApellido1()!=null?p.getApellido1():"");
			String apellido1Trim = StringUtil.quitaEspacios(apellido1);
			if(!apellido1.equalsIgnoreCase(apellido1Trim))
			{
				cambios =true;
				if (!querySet.equals(""))  //añadimos la coma en caso que exista uno previo 					
					querySet+= ", ";
				 
				querySet+= " apellido1 = '"+ apellido1Trim + "'" ;
				queryWhere+= " AND apellido1 <> '"+ apellido1Trim + "' " ;
			}
			/*  FIN apellido1*/
			/*  INICIO apellido2
			String apellido2 = (p.getApellido2()!=null?p.getApellido2():"");
			String apellido2Trim = StringUtil.quitaEspacios(apellido2);
			if(!apellido2.equalsIgnoreCase(apellido2Trim))
			{
				cambios =true;
				if (!querySet.equals(""))  //añadimos la coma en caso que exista uno previo 					
					querySet+= ", ";
				 
				querySet+= " apellido2 = '"+ apellido2Trim + "'" ;
				queryWhere+= " AND apellido2 <> '"+ apellido2Trim + "' " ;
			}
			/*  FIN apellido2*/
			/*  INICIO cognoms
			String cognoms = apellido1 + " " + apellido2;
			String cognomsTrim = apellido1Trim + " " + apellido2Trim;
			if(!cognoms.equalsIgnoreCase(cognomsTrim))
			{
				cambios =true;
				if (!querySet.equals(""))  //añadimos la coma en caso que exista uno previo 					
					querySet+= ", ";
				 
				querySet+= " cognoms = '"+ cognomsTrim + "'" ;
				queryWhere+= " AND cognoms <> '"+ cognomsTrim + "' " ;
			}
			/*  FIN cognoms*/
			/*  INICIO nom
			String nom = (p.getNombre()!=null?p.getNombre():"");
			String nomTrim = StringUtil.quitaEspacios(nom);
			if(!nom.equalsIgnoreCase(nomTrim))
			{
				cambios =true;
				if (!querySet.equals(""))  //añadimos la coma en caso que exista uno previo 					
					querySet+= ", ";
				 
				querySet+= " nom = '"+ nomTrim + "'" ;
				queryWhere+= " AND nom <> '"+ nomTrim + "' " ;

			}
			/*  FIN nom*/
			/*  INICIO cognomsNom
			
			String cognomsNom = cognoms + ", " + nom;
			String cognomsNomTrim = cognomsTrim + ", " + nomTrim;
			if(!cognomsNom.equalsIgnoreCase(cognomsNomTrim))
			{
				cambios =true;
				if (!querySet.equals(""))  //añadimos la coma en caso que exista uno previo 					
					querySet+= ", ";
				 
				querySet+= " cognomsNom = '"+ cognomsNomTrim + "'" ;
				queryWhere+= " AND cognomsNom <> '"+ cognomsNomTrim + "' " ;
			}
			/*  FIN cognomsNom
			
			if(cambios)
			{
				String query = " UPDATE SPDAC.dbo.bd_pacientes SET " + querySet + " WHERE 1=1 AND " + queryWhere;  
			}
				
		}
	
		 * 
		 * 	UPDATE SPDAC.dbo.bd_pacientes SET apellido1 = UPPER(LTRIM(RTRIM(REPLACE(apellido1,'  ',' ')))) WHERE apellido1 <> UPPER(LTRIM(RTRIM(REPLACE(apellido1,'  ',' '))));
			UPDATE SPDAC.dbo.bd_pacientes SET apellido2 = UPPER(LTRIM(RTRIM(REPLACE(apellido2,'  ',' ')))) WHERE apellido2 <> UPPER(LTRIM(RTRIM(REPLACE(apellido2,'  ',' '))));
			UPDATE SPDAC.dbo.bd_pacientes SET cognoms = LTRIM(RTRIM(apellido1)) + ' ' + LTRIM(RTRIM(apellido2)) WHERE cognoms <> LTRIM(RTRIM(apellido1)) + ' ' + LTRIM(RTRIM(apellido2));
			UPDATE SPDAC.dbo.bd_pacientes SET cognoms = UPPER(LTRIM(RTRIM(REPLACE(cognoms,'  ',' ')))) WHERE cognoms <> UPPER(LTRIM(RTRIM(REPLACE(cognoms,'  ',' '))));
			UPDATE SPDAC.dbo.bd_pacientes SET nom = UPPER(LTRIM(RTRIM(REPLACE(nom,'  ',' ')))) WHERE nom <> UPPER(LTRIM(RTRIM(REPLACE(nom,'  ',' '))));
			UPDATE SPDAC.dbo.bd_pacientes SET cognomsNom = LTRIM(RTRIM(cognoms)) + ', ' + LTRIM(RTRIM(nom)) WHERE cognomsNom <> LTRIM(RTRIM(cognoms)) + ', ' + LTRIM(RTRIM(nom));
			UPDATE SPDAC.dbo.bd_pacientesFarmatic SET cognomsNom = UPPER(LTRIM(RTRIM(REPLACE(cognomsNom,'  ',' ')))) WHERE cognomsNom <> UPPER(LTRIM(RTRIM(REPLACE(cognomsNom,'  ',' '))));
		
		return false;
	}
*/

	
	public static boolean actualizaDatos(String idUsuario, PacienteBean p, PacientesForm f) throws SQLException {
		boolean cambios =false;
		boolean cambiosNombre =false;
		boolean cambiosEstado =false;
		String antes = ""; 
		String despues = "";

		
		String querySet="";
		if(p!=null)
		{
			//String querySet="UPDATE bd_pacientes ";
			/*  INICIO divisionResidencia*/
			if (!Objects.equals(p.getIdDivisionResidencia(), f.getIdDivisionResidencia())) 
			{
				
				antes+=  " | Residencia: "+ p.getIdDivisionResidencia();
				despues+=" | Residencia: "+ f.getIdDivisionResidencia();

				cambios =true;
				cambiosNombre =true;
				if (!querySet.equals("")) 
					querySet+= ", "; //añadimos la coma en caso que exista uno previo 		
				 
				querySet+= " idDivisionResidencia = '"+ f.getIdDivisionResidencia() + "'" ;
			}
			/*  INICIO nom*/
			if (!Objects.equals(p.getNombre(), f.getNombre())) 
			{
				
				antes+=  " | Nombre: "+ p.getNombre();
				despues+=" | Nombre: "+ f.getNombre();

				cambios =true;
				cambiosNombre =true;
				if (!querySet.equals("")) 
					querySet+= ", "; //añadimos la coma en caso que exista uno previo 		
				 
				querySet+= " NOM = '"+ f.getNombre() + "'" ;
			}
			/*  INICIO apellido1*/ 
			if (!Objects.equals(p.getApellido1(), f.getApellido1())) 
			{
				antes+=  " | apellido1: "+ p.getApellido1();
				despues+=" | apellido1: "+ f.getApellido1();

				if (!querySet.equals(""))  	
					querySet+= ", ";
				 
				cambios =true;
				cambiosNombre =true;
				querySet+= " apellido1 = '"+ StringUtil.quitaEspacios(f.getApellido1()) + "'" ;
			}
			/*  INICIO apellido2*/
			if(f.getApellido2()==null || (f.getApellido2()!=null && f.getApellido2().equalsIgnoreCase("null"))) f.setApellido2("");
			if(p.getApellido2()==null || (p.getApellido2()!=null && p.getApellido2().equalsIgnoreCase("null"))) p.setApellido2("");
			if (!Objects.equals(p.getApellido2(), f.getApellido2()))
			{
				
				antes+=  " | apellido2: "+ p.getApellido2();
				despues+=" | apellido2: "+ f.getApellido2();

				if (!querySet.equals(""))  	
					querySet+= ", ";
				 
				cambios =true;
				cambiosNombre =true;
				querySet+= " apellido2 = '"+ StringUtil.quitaEspacios(f.getApellido2()) + "'" ;
			}
			
			/*  INICIO nIdentidad*/
			if(f.getnIdentidad()==null || (f.getnIdentidad()!=null && f.getnIdentidad().equalsIgnoreCase("null"))) f.setnIdentidad("");
			if(p.getnIdentidad()==null || (p.getnIdentidad()!=null && p.getnIdentidad().equalsIgnoreCase("null"))) p.setnIdentidad("");
			if (!Objects.equals(p.getNIdentidad(), f.getnIdentidad())) 
			{
				antes+=  " | nIdentidad: "+ p.getNIdentidad();
				despues+=" | nIdentidad: "+ f.getnIdentidad();

				if (!querySet.equals(""))  	
					querySet+= ", ";
				 
				cambios =true;
				querySet+= " nIdentidad = '"+ f.getnIdentidad() + "'" ;
			}
			/*  INICIO segSocial*/
			if(f.getSegSocial()==null || (f.getSegSocial()!=null && f.getSegSocial().equalsIgnoreCase("null"))) f.setSegSocial("");
			if(p.getSegSocial()==null || (p.getSegSocial()!=null && p.getSegSocial().equalsIgnoreCase("null"))) p.setSegSocial("");
			if (!Objects.equals(p.getSegSocial(), f.getSegSocial())) 
			{
				antes+=  " | segSocial: "+ p.getSegSocial();
				despues+=" | segSocial: "+ f.getSegSocial();

				if (!querySet.equals(""))  	
					querySet+= ", ";
				 
				cambios =true;
				querySet+= " segSocial = '"+ f.getSegSocial() + "'" ;
			}			
			/*  INICIO segSocial*/
			if(f.getIdPacienteResidencia()==null || (f.getIdPacienteResidencia()!=null && f.getIdPacienteResidencia().equalsIgnoreCase("null"))) f.setIdPacienteResidencia("");
			if(p.getIdPacienteResidencia()==null || (p.getIdPacienteResidencia()!=null && p.getIdPacienteResidencia().equalsIgnoreCase("null"))) p.setIdPacienteResidencia("");
			if (!Objects.equals(p.getIdPacienteResidencia(), f.getIdPacienteResidencia()))
			{
				antes+=  " | idPacienteResidencia: "+ p.getIdPacienteResidencia();
				despues+=" | idPacienteResidencia: "+ f.getIdPacienteResidencia();

				if (!querySet.equals(""))  	
					querySet+= ", ";
				 
				cambios =true;
				querySet+= " idPacienteResidencia = '"+ f.getIdPacienteResidencia() + "'" ;
			}					
			/*  INICIO planta*/
			if(f.getPlanta()==null || (f.getPlanta()!=null && f.getPlanta().equalsIgnoreCase("null"))) f.setPlanta("");
			if(p.getPlanta()==null || (p.getPlanta()!=null && p.getPlanta().equalsIgnoreCase("null"))) p.setPlanta("");
			if (!Objects.equals(p.getPlanta(), f.getPlanta())) 
			{
				
				antes+=  " | planta: "+ p.getPlanta();
				despues+=" | planta: "+ f.getPlanta();

				if (!querySet.equals(""))  	
					querySet+= ", ";
				 
				cambios =true;
				querySet+= " planta = '"+ f.getPlanta() + "'" ;
			}							
			/*  INICIO habitacion*/
			if(f.getHabitacion()==null || (f.getHabitacion()!=null && f.getHabitacion().equalsIgnoreCase("null"))) f.setHabitacion("");
			if(p.getHabitacion()==null || (p.getHabitacion()!=null && p.getHabitacion().equalsIgnoreCase("null"))) p.setHabitacion("");
			if (!Objects.equals(p.getHabitacion(), f.getHabitacion())) 
			{
				antes+=  " | habitacion: "+ p.getHabitacion();
				despues+=" | habitacion: "+ f.getHabitacion();

				if (!querySet.equals(""))  	
					querySet+= ", ";
				 
				cambios =true;
				querySet+= " habitacion = '"+ f.getHabitacion() + "'" ;
			}			
			/*  INICIO spd*/
			if (!Objects.equals(p.getMutua(), f.getMutua())) 
			{
				antes+=  " | mutua: "+ p.getMutua();
				despues+=" | mutua: "+ f.getMutua();

				if (!querySet.equals(""))  	
					querySet+= ", ";
				 
				cambios =true;
				querySet+= " mutua = '"+ f.getMutua() + "'" ;
				
			}	
			/*  INICIO spd*/
			if (!Objects.equals(p.getSpd(), f.getSpd())) 
			{
				antes+=  " | spd: "+ p.getSpd();
				despues+=" | spd: "+ f.getSpd();

				if (!querySet.equals(""))  	
					querySet+= ", ";
				 
				cambios =true;
				querySet+= " spd = '"+ f.getSpd() + "'" ;
				
			}	
			/*  INICIO bolquers*/
			if (!Objects.equals(p.getBolquers(), f.getBolquers()))
			{
				antes+=  " | bolquers: "+ p.getBolquers();
				despues+=" | bolquers: "+ f.getBolquers();

				if (!querySet.equals(""))  	
					querySet+= ", ";
				 
				cambios =true;
				querySet+= " bolquers = '"+ f.getBolquers() + "'" ;
			}	
			/*  INICIO comentarios*/
			if(f.getComentarios()==null || (f.getComentarios()!=null && f.getComentarios().equalsIgnoreCase("null"))) f.setComentarios("");
			if(p.getComentarios()==null || (p.getComentarios()!=null && p.getComentarios().equalsIgnoreCase("null"))) p.setComentarios("");
			if(f.getComentarios()!=null) f.setComentarios(StringUtil.limpiarTextoComentarios(f.getComentarios())); 
			if(p.getComentarios()!=null) p.setComentarios(StringUtil.limpiarTextoComentarios(p.getComentarios()));

			if (!Objects.equals(p.getComentarios(), f.getComentarios())) 
			{
				antes+=  " | comentarios: "+ p.getComentarios();
				despues+=" | comentarios: "+ f.getComentarios();

				if (!querySet.equals(""))  	
					querySet+= ", ";
				 
				cambios =true;
				querySet+= " comentarios = '"+ f.getComentarios() + "'" ;
			}	
			/*  INICIO estatus*/
			if (!Objects.equals(p.getEstatus(), f.getEstatus())) 
			{
				antes+=  " | estatus: "+ p.getEstatus();
				despues+=" | estatus: "+ f.getEstatus();

				if (!querySet.equals(""))  	
					querySet+= ", ";
				 
				cambios =true;
				cambiosEstado=true;
				querySet+= " estatus = '"+ f.getEstatus() + "'" ;
			}				
			/*  INICIO exitus
			if (!Objects.equals(p.getExitus(), f.getExitus())) 
			{
				antes+=  " | exitus: "+ (p.getExitus()==1?"SI":"NO" );
				despues+=" | exitus: "+ (f.getExitus()==1?"SI":"NO" );

				if (!querySet.equals(""))  	
					querySet+= ", ";
				 
				cambios =true;
				cambiosEstado=true;
				querySet+= " exitus = '"+ f.getExitus() + "'" ;
			}	*/			
		}	

		if(cambiosNombre) {
			querySet+= ", cognoms = apellido1 +' ' + apellido2, cognomsNom = cognoms + ', ' + nom ";  //actualizamos campos dependientes
		}
		//if(cambiosEstado) {
		//	querySet+= ", exitus = '"+f.getExitus()+"', estatus = '"+f.getEstatus()+"', spd = '"+f.getEstatus()+"', estatus = '"+f.getEstatus()+"' ";  //actualizamos campos dependientes
		//}

		
		
			if(cambios)
			{
				String query = " UPDATE SPDAC.dbo.bd_pacientes SET " + querySet + " WHERE  OIDPACIENTE='"+p.getOidPaciente()+"'";
				cambios = PacienteDAO.edita(query);

				//INICIO creación de log en BBDD
				try{
					SpdLogAPI.addLog(idUsuario, p.getCIP(),  p.getIdDivisionResidencia(), null, SpdLogAPI.A_RESIDENTE, SpdLogAPI.B_EDICION, SpdLogAPI.C_DATOSGENERALES, "SpdLog.residente.edicion.general", 
							 new String[]{idUsuario, p.getCIP(), antes, despues} );
				}catch(Exception e){}	// Cambios--> @@.
				//FIN creación de log en BBDD
			}
			return cambios;
	}


	public static boolean actualizaPlantaHabitacion(String idUsuario, FicheroResiBean medResi) throws SQLException {
		return PacienteDAO.actualizaPlantaHabitacion( idUsuario,  medResi);
	}

	public static List<BolquersDetalleBean> getBolquersPaciente(String idUsuario, String oidPaciente) throws SQLException {
		return PacienteDAO.getBolquersPaciente( idUsuario,  oidPaciente);
	}

	public static List<DiscrepanciaBean> getDiscrepanciasPorCIP(String idUsuario, PacienteBean pac, int diasCalculo) throws Exception {
		List<DiscrepanciaBean> discrepancias = new ArrayList<>();

		if(pac!=null) discrepancias = PacienteDAO.getDiscrepanciasPorCIP( idUsuario, pac.getCIP(), diasCalculo);
		return discrepancias;
	}

	public static PacienteBean getPacientePorOID(String idUsuario, String oidPaciente) throws Exception {
		return PacienteDAO.getPacientePorOID( idUsuario,  oidPaciente);
	}


	public static List<TratamientoRctBean> getTratamientoRctPorCIP(String idUsuario, PacienteBean pac) throws SQLException {
		List<TratamientoRctBean> tratamiento = new ArrayList<>();

		if(pac!=null) tratamiento = PacienteDAO.getTratamientoRctPorCIP( idUsuario, pac.getCIP());
		return tratamiento;
	}


	public static List<FicheroResiBean> getListTratamientosSPDPorCIP(String idUsuario, PacienteBean pac, boolean historico) throws Exception {
		List<FicheroResiBean> tratamientosSpd = new ArrayList<>();
		if(pac!=null) tratamientosSpd = FicheroResiDetalleDAO.getProduccionesPorCIP( idUsuario,  pac.getCIP(), historico);
		
		return tratamientosSpd;
	}

	public static List<FicheroResiBean> getDetalleTratamientosSPDPorCIP(String idUsuario, PacienteBean pac, String idProceso, boolean historico) throws Exception {
		List<FicheroResiBean> tratamientosSpd = new ArrayList<>();

		if(pac!=null)  tratamientosSpd = FicheroResiDetalleDAO.getFicheroResiBeanPorCipYProceso(idUsuario, idProceso, pac.getCIP(), historico);
		return tratamientosSpd;
	}


	 public static List getEstatusResidente() throws Exception {
	        final List<String> listaEstatusResidente = new ArrayList<>();
		listaEstatusResidente.add(SPDConstants.STATUS_PACIENTE_ALTA);
		listaEstatusResidente.add(SPDConstants.STATUS_PACIENTE_BAJA);
		listaEstatusResidente.add(SPDConstants.STATUS_PACIENTE_EXITUS);
		listaEstatusResidente.add(SPDConstants.STATUS_PACIENTE_HOSPITAL);
		listaEstatusResidente.add(SPDConstants.STATUS_PACIENTE_BAJAVOLUNTARIA);
		listaEstatusResidente.add(SPDConstants.STATUS_PACIENTE_CENTRODEDIA);

		return listaEstatusResidente;

	}
	




	
		
}
	


	
	
	
	
	
	
	
	
	
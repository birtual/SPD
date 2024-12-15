package lopicost.spd.struts.helper;

import java.sql.SQLException;
import java.util.Objects;

import lopicost.spd.controller.SpdLogAPI;
import lopicost.spd.model.Aviso;
import lopicost.spd.persistence.AvisosDAO;
import lopicost.spd.persistence.PacienteDAO;
import lopicost.spd.struts.bean.PacienteBean;
import lopicost.spd.struts.form.AvisosForm;
import lopicost.spd.struts.form.PacientesForm;
import lopicost.spd.utils.StringUtil;

public class AvisoHelper {

	public static boolean actualizaDatos(String idUsuario, Aviso aviso, AvisosForm f)  throws SQLException {
			boolean cambios =false;
			String antes = ""; 
			String despues = "";
			
			String querySet="";
			if(aviso!=null)
			{
				/*  INICIO Texto*/
				if (!Objects.equals(aviso.getTexto(), f.getTexto())) 
				{
					antes+=  " | Texto: "+ aviso.getTexto();
					despues+=" | Texto: "+ f.getTexto();

					cambios =true;
					if (!querySet.equals("")) 
						querySet+= ", "; //añadimos la coma en caso que exista uno previo 		
					 
					querySet+= " texto = '"+ f.getTexto() + "'" ;
				}
			
				/*  INICIO getFechaInicio*/
				if (!Objects.equals(aviso.getFechaInicio(), f.getFechaInicio())) 
				{
					antes+=  " | FechaInicio: "+ aviso.getFechaInicio();
					despues+=" | FechaInicio: "+ f.getFechaInicio();

					if (!querySet.equals(""))  	querySet+= ", ";
					 
					cambios =true;
					querySet+= " fechaInicio = '"+ f.getFechaInicio() + "'" ;
				}
				/*  INICIO getFechaFin*/
				if (!Objects.equals(aviso.getFechaFin(), f.getFechaFin())) 
				{
					antes+=  " | FechaFin: "+ aviso.getFechaFin();
					despues+=" | FechaFin: "+ f.getFechaFin();

					if (!querySet.equals(""))  	querySet+= ", ";
					 
					cambios =true;
					querySet+= " fechaFin = '"+ f.getFechaFin() + "'" ;
				}
				/*  INICIO Farmacia*/
				if (!Objects.equals(aviso.getIdFarmacia(), f.getIdFarmacia())) 
				{
					antes+=  " | Farmacia: "+ aviso.getIdFarmacia();
					despues+=" | Farmacia: "+ f.getIdFarmacia();

					if (!querySet.equals(""))  	querySet+= ", ";
					 
					cambios =true;
					querySet+= " idFarmacia = '"+ f.getIdFarmacia() + "'" ;
				}
				/*  INICIO activo*/
				if (!Objects.equals(aviso.getActivo(), f.getActivo())) 
				{
					antes+=  " | Activo: "+ aviso.getActivo();
					despues+=" | Activo: "+ f.getActivo();

					if (!querySet.equals(""))  	querySet+= ", ";
					 
					cambios =true;
					querySet+= " activo = '"+ f.getActivo() + "'" ;
				}
				/*  INICIO Tipo*/
				if (!Objects.equals(aviso.getTipo(), f.getTipo())) 
				{
					antes+=  " | Tipo: "+ aviso.getTipo();
					despues+=" | Tipo: "+ f.getTipo();

					if (!querySet.equals(""))  	querySet+= ", ";
					 
					cambios =true;
					querySet+= " tipo = '"+ f.getTipo() + "'" ;
				}
				/*  INICIO Orden*/
				if (!Objects.equals(aviso.getOrden(), f.getOrden())) 
				{
					antes+=  " | Orden: "+ aviso.getOrden();
					despues+=" | Orden: "+ f.getOrden();

					if (!querySet.equals(""))  	querySet+= ", ";
					 
					cambios =true;
					querySet+= " orden = '"+ f.getOrden() + "'" ;
				}
				
			
				if(cambios)
				{
					String query = " UPDATE SPDAC.dbo.SPD_Avisos SET " + querySet + " WHERE  OIDAVISO='"+aviso.getOidAviso()+"'";
					cambios = AvisosDAO.update(query);

					//INICIO creación de log en BBDD
					try{
						SpdLogAPI.addLog(idUsuario, null,  null, null, SpdLogAPI.A_AVISO, SpdLogAPI.B_EDICION, SpdLogAPI.C_DATOSGENERALES, "SpdLog.aviso.edicion.general", 
								 new String[]{antes, despues} );
					}catch(Exception e){}	// Cambios--> @@.
					//FIN creación de log en BBDD
				}
		}
			return cambios;
	}

	public static boolean borrar(String idUsuario, Aviso aviso) throws SQLException {

		String mensaje = "Aviso : Oid: " + aviso.getOidAviso() + "  /";
		mensaje+= " Fecha creación: " + aviso.getFechaInsert() + " / Creador:  "+aviso.getUsuarioCreador()+ " / Farmacia:  "+aviso.getIdFarmacia()+ " /";
		mensaje+= " FechaInicio:  "+aviso.getFechaInicio()+ " / FechaFin:  "+aviso.getFechaFin() + " / texto:  "+aviso.getTexto() + " ";
		String query = " DELETE SPDAC.dbo.SPD_Avisos WHERE  OIDAVISO='"+aviso.getOidAviso()+"'";
		boolean result =  AvisosDAO.delete(query);

		if(result)
		{
			//INICIO creación de log en BBDD
			try{
				SpdLogAPI.addLog(idUsuario, null,  null, null, SpdLogAPI.A_AVISO, SpdLogAPI.B_BORRADO, "", "SpdLog.aviso.borrado.general", 
						mensaje );
			}catch(Exception e){}	// Cambios--> @@.
			//FIN creación de log en BBDD
			
		}

		
		return result;
	}

	public static boolean nuevo(String idUsuario, AvisosForm f) throws ClassNotFoundException, SQLException {


		Aviso aviso = new Aviso();
		aviso.setActivo("SI");
		aviso.setFechaFin(f.getFechaFin());
		aviso.setFechaInicio(f.getFechaInicio());
		aviso.setIdFarmacia(f.getIdFarmacia());
		aviso.setOrden(f.getOrden());
		aviso.setTexto(f.getTexto());
		aviso.setTipo(f.getTipo());
		aviso.setUsuarioCreador(idUsuario);
		String mensaje = "Aviso : ";
		mensaje+= " Fecha creación: " + aviso.getFechaInsert() + " / Creador:  "+aviso.getUsuarioCreador()+ " / Farmacia:  "+aviso.getIdFarmacia()+ " /";
		mensaje+= " FechaInicio:  "+aviso.getFechaInicio()+ " / FechaFin:  "+aviso.getFechaFin() + " /  Texto:  "+aviso.getTexto() + " /";

		
		boolean result =  AvisosDAO.nuevo(idUsuario, aviso);
		if(result)
		{
			//INICIO creación de log en BBDD
			try{
				SpdLogAPI.addLog(idUsuario, null,  null, null, SpdLogAPI.A_AVISO, SpdLogAPI.B_CREACION, "", "SpdLog.aviso.creado.general", 
						mensaje );
			}catch(Exception e){}	// Cambios--> @@.
			//FIN creación de log en BBDD
			
		}

		
		return result;
	}
}

package lopicost.spd.struts.helper;


import java.sql.SQLException;
import java.util.List;

import lopicost.spd.controller.SpdLogAPI;
import lopicost.spd.model.Proceso;
import lopicost.spd.model.ProcesoBloqueoHorario;

import lopicost.spd.persistence.ProcesoBloqueoHorarioDAO;
import lopicost.spd.utils.SPDConstants;


public class ProcesoBloqueoHorarioHelper {

    private ProcesoBloqueoHorarioDAO dao;

    public ProcesoBloqueoHorarioHelper() {
        this.dao = new ProcesoBloqueoHorarioDAO(); 
    }


    public List<ProcesoBloqueoHorario> listarTodas() throws SQLException {
        return dao.listar();
    }

    public ProcesoBloqueoHorario obtenerPorOid(int oid) throws SQLException {
        return dao.obtenerPorOid(oid);
    }


  
    
    public boolean guardar(String idUsuario, ProcesoBloqueoHorario bloqueoOriginal, ProcesoBloqueoHorario bloqueo, List errors) throws Exception {
    	boolean result = checktipoBloqueoHorario(bloqueo, errors);
    	
        if(result && (bloqueoOriginal==null || bloqueoOriginal.getOidBloqueoHorario()<=0)) 
  	  	{
 	  		result = dao.insertar(idUsuario, bloqueo);
  	  		
  	  		if(result)
  	  		{
                String mensaje =construyeMensajeLog(bloqueo);
                //INICIO creación de log en BBDD
  	  			try{
  	  				//INICIO creación de log en BBDD
  	  				SpdLogAPI.addLog(idUsuario, null,  null, null, SpdLogAPI.A_PROCESO_BLOQUEO_HORARIO, SpdLogAPI.B_CREACION, "", "SpdLog.procesobloqueo.creado.general", 
  	  						" Creado bloqueo horario --> " + mensaje);
  	  				}catch(Exception e){}	// Cambios--> @@.
  	  				//FIN creación de log en BBDD
  	  			
  	  		}
  	  		
  	  	}	  
    	else if(result)
    	{
    		String mensajeOrig =construyeMensajeLog(bloqueoOriginal);
    		String mensajeFinal =construyeMensajeLog(bloqueo);
    		result = dao.actualizar(idUsuario, bloqueo);
    	  		if(result)
    	  		{
    				//INICIO creación de log en BBDD
    				try{
    					//INICIO creación de log en BBDD
    					SpdLogAPI.addLog(idUsuario, null,  null, null, SpdLogAPI.A_PROCESO_BLOQUEO_HORARIO, SpdLogAPI.B_EDICION, "", "SpdLog.procesobloqueo.edicion.general", 
    							" Modificado bloqueo horario --> ANTES:" + mensajeOrig + " // DESPUES: " + mensajeFinal);
    					}catch(Exception e){}	// Cambios--> @@.
    					//FIN creación de log en BBDD
    		    }
    		
    	}
        return result;
    }


    /**
     * Chequea que el tipo de bloqueo tengo los campos hora, día o fecha correctamente indicados 
     * @param bloqueo
     * @param errors
     */
    private boolean checktipoBloqueoHorario(ProcesoBloqueoHorario bloqueo, List errors) {
    	switch (bloqueo.getTipoBloqueoHorario()) {
		case "HORA":
			bloqueo.setValorDia(0);
			bloqueo.setValorFecha("");
			if(bloqueo.getHorasDesde()==null || bloqueo.getHorasDesde().equals("")
					|| bloqueo.getHorasHasta()==null || bloqueo.getHorasHasta().equals("")
			)
			{
				errors.add("Es necesario indicar el rango de horas del bloqueo horario en procesos");
				return false; 
				
			}
		break;	
		case "DIA":
			bloqueo.setHorasDesde("");
			bloqueo.setHorasHasta("");
			bloqueo.setValorFecha("");
			if(bloqueo.getValorDia()<=0 || bloqueo.getValorDia().equals(null))
			{
				errors.add("Es necesario indicar el día del bloqueo en procesos");
				return false; 
			}

		break;	
		case "FECHA":
			bloqueo.setValorDia(0);
			bloqueo.setHorasDesde("");
			bloqueo.setHorasHasta("");
			if(bloqueo.getValorFecha()==null || bloqueo.getValorFecha().equals(""))
			{
				errors.add("Es necesario indicar la fecha del bloqueo horario en procesos");
				return false; 
			}
		break;	
		default:
			return true;
		
	}
    	return true;
	
}


	public void borrar(String idUsuario, ProcesoBloqueoHorario bloqueo) throws SQLException {
        String mensaje =construyeMensajeLog(bloqueo);
        boolean result = dao.borrar(bloqueo.getOidBloqueoHorario());
        if(result)
        {
      		//INICIO creación de log en BBDD
            try{
    		SpdLogAPI.addLog(idUsuario, null,  null, null, SpdLogAPI.A_PROCESO_BLOQUEO_HORARIO, SpdLogAPI.B_BORRADO, "", "SpdLog.procesobloqueo.borrado.general", 
    				" Borrado bloqueo horario --> " + mensaje);
    		}catch(Exception e){}	// Cambios--> @@.
    		//FIN creación de log en BBDD
        	
        }
	}
	
	

	private String construyeMensajeLog(ProcesoBloqueoHorario bloqueo) 
	{
		if(bloqueo==null) return null;
		String mensaje =" Tipo de bloqueo - " + bloqueo.getTipoBloqueoHorario() + " -->  ";
		
		
		switch (bloqueo.getTipoBloqueoHorario()) {
		case "HORA":
			mensaje+= " Desde " + bloqueo.getHorasDesde() + " Hasta " + bloqueo.getHorasHasta() ;
			break;
		case "DIA":
			mensaje+= bloqueo.getValorDia();
			break;
		case "FECHA":
			mensaje+= bloqueo.getValorFecha();
			break;
		default:
			break;
		}
		
		mensaje+="  /  Procesos --> ";
		switch (bloqueo.getOidProceso()) {
		case 0:
			mensaje+=" Todos" ;
			break;
		default:
			mensaje+=" Oid: " + +bloqueo.getOidProceso();
			break;
		}
		
		mensaje+="  /  Farmacia --> ";
		switch (bloqueo.getOidProceso()) {
		case 0:
			mensaje+=" Todas" ;
			break;
		default:
			mensaje+= bloqueo.getIdFarmacia() + " " + bloqueo.getNombreFarmacia() ;
			break;
		}
		
		mensaje+="  /  Lanzadera  --> ";
		switch (bloqueo.getOidProceso()) {
		case 0:
			mensaje+=" Todas" ;
			break;
		default:
			mensaje+= bloqueo.getLanzadera()  ;
			break;
		}
		return mensaje;
}


	public boolean bloqueosPorTipo(Proceso proceso, String tipoBloqueoHorario) throws SQLException {
		int result=0;
		/*
		List lista = dao.bloqueosPorTipo(proceso, tipoBloqueoHorario);
		if(lista!=null)
			result = lista.size();
		*/
		result = dao.countBloqueosPorTipo(proceso, tipoBloqueoHorario, true);
		return result>0;
	}
	
    /**
     * Método que mira si hay algún bloqueo por hora, dia o fecha, para poder ejecutar los procesos. Para que se puedan ejecutar siempre han de devolver "false"
     * En caso que exista algún bloqueo devolverá un "true"
     * @param proceso
     * @return
     * @throws SQLException
     * TO-DO Realizarlo para farmacia
     */
	public boolean hayBloqueoHorario(Proceso proceso) throws SQLException {
    	boolean bloqueoHora=false;
    	boolean bloqueoDia=false;
		boolean bloqueoFecha=false;
		ProcesoBloqueoHorarioHelper helper = new ProcesoBloqueoHorarioHelper();
		bloqueoHora = helper.bloqueosPorTipo(proceso, SPDConstants.PROCESO_BLOQUEO_HORA);
		if(!bloqueoHora)
			bloqueoDia = helper.bloqueosPorTipo(proceso, SPDConstants.PROCESO_BLOQUEO_DIA);
		if(!bloqueoHora && !bloqueoDia)
			bloqueoFecha = helper.bloqueosPorTipo(proceso, SPDConstants.PROCESO_BLOQUEO_FECHA);

		System.out.println(bloqueoHora +" "+ bloqueoDia +" "+ bloqueoFecha);

		return bloqueoHora || bloqueoDia || bloqueoFecha;
	}



}

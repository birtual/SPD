package lopicost.spd.struts.helper;


import java.sql.SQLException;
import java.util.List;

import lopicost.spd.controller.SpdLogAPI;
import lopicost.spd.model.Proceso;
import lopicost.spd.model.ProcesoRestricciones;

import lopicost.spd.persistence.ProcesoRestriccionesDAO;
import lopicost.spd.utils.SPDConstants;


public class ProcesoRestriccionesHelper {

    private ProcesoRestriccionesDAO dao;

    public ProcesoRestriccionesHelper() {
        this.dao = new ProcesoRestriccionesDAO(); 
    }


    public List<ProcesoRestricciones> listarTodas() throws SQLException {
        return dao.listar();
    }

    public ProcesoRestricciones obtenerPorOid(int oid) throws SQLException {
        return dao.obtenerPorOid(oid);
    }


  
    
    public boolean guardar(String idUsuario, ProcesoRestricciones restriccionOriginal, ProcesoRestricciones restriccion, List errors) throws Exception {
    	boolean result = checkTipoRestriccion(restriccion, errors);
    	
        if(result && (restriccionOriginal==null || restriccionOriginal.getOidRestriccion()<=0)) 
  	  	{
 	  		result = dao.insertar(idUsuario, restriccion);
  	  		
  	  		if(result)
  	  		{
                String mensaje =construyeMensajeLog(restriccion);
                //INICIO creación de log en BBDD
  	  			try{
  	  				//INICIO creación de log en BBDD
  	  				SpdLogAPI.addLog(idUsuario, null,  null, null, SpdLogAPI.A_PROCESO_RESTRICCION, SpdLogAPI.B_CREACION, "", "SpdLog.procesorestriccion.creado.general", 
  	  						" Creada restricción horaria procesos --> " + mensaje);
  	  				}catch(Exception e){}	// Cambios--> @@.
  	  				//FIN creación de log en BBDD
  	  			
  	  		}
  	  		
  	  	}	  
    	else if(result)
    	{
    		String mensajeOrig =construyeMensajeLog(restriccionOriginal);
    		String mensajeFinal =construyeMensajeLog(restriccion);
    		result = dao.actualizar(idUsuario, restriccion);
    	  		if(result)
    	  		{
    				//INICIO creación de log en BBDD
    				try{
    					//INICIO creación de log en BBDD
    					SpdLogAPI.addLog(idUsuario, null,  null, null, SpdLogAPI.A_PROCESO_RESTRICCION, SpdLogAPI.B_EDICION, "", "SpdLog.procesorestriccion.edicion.general", 
    							" Modificada restricción horaria procesos --> ANTES:" + mensajeOrig + " // DESPUES: " + mensajeFinal);
    					}catch(Exception e){}	// Cambios--> @@.
    					//FIN creación de log en BBDD
    		    }
    		
    	}
        return result;
    }


    /**
     * Chequea que el tipo de restricción tengo los campos hora, día o fecha correctamente indicados 
     * @param restriccion
     * @param errors
     */
    private boolean checkTipoRestriccion(ProcesoRestricciones restriccion, List errors) {
    	switch (restriccion.getTipoRestriccion()) {
		case "HORA":
			restriccion.setValorDia(0);
			restriccion.setValorFecha("");
			if(restriccion.getHorasDesde()==null || restriccion.getHorasDesde().equals("")
					|| restriccion.getHorasHasta()==null || restriccion.getHorasHasta().equals("")
			)
			{
				errors.add("Es necesario indicar el rango de horas de la restricción horaria en procesos");
				return false; 
				
			}
		break;	
		case "DIA":
			restriccion.setHorasDesde("");
			restriccion.setHorasHasta("");
			restriccion.setValorFecha("");
			if(restriccion.getValorDia()<=0 || restriccion.getValorDia().equals(null))
			{
				errors.add("Es necesario indicar el día de la restricción en procesos");
				return false; 
			}

		break;	
		case "FECHA":
			restriccion.setValorDia(0);
			restriccion.setHorasDesde("");
			restriccion.setHorasHasta("");
			if(restriccion.getValorFecha()==null || restriccion.getValorFecha().equals(""))
			{
				errors.add("Es necesario indicar la fecha de la restricción horaria en procesos");
				return false; 
			}
		break;	
		default:
			return true;
		
	}
    	return true;
	
}


	public void borrar(String idUsuario, ProcesoRestricciones restriccion) throws SQLException {
        String mensaje =construyeMensajeLog(restriccion);
        boolean result = dao.borrar(restriccion.getOidRestriccion());
        if(result)
        {
      		//INICIO creación de log en BBDD
            try{
    		SpdLogAPI.addLog(idUsuario, null,  null, null, SpdLogAPI.A_PROCESO_RESTRICCION, SpdLogAPI.B_BORRADO, "", "SpdLog.procesorestriccion.borrado.general", 
    				" Borrada restricción horaria --> " + mensaje);
    		}catch(Exception e){}	// Cambios--> @@.
    		//FIN creación de log en BBDD
        	
        }
	}
	
	

	private String construyeMensajeLog(ProcesoRestricciones restriccion) 
	{
		if(restriccion==null) return null;
		String mensaje =" Tipo de Restricción - " + restriccion.getTipoRestriccion() + " -->  ";
		
		
		switch (restriccion.getTipoRestriccion()) {
		case "HORA":
			mensaje+= " Desde " + restriccion.getHorasDesde() + " Hasta " + restriccion.getHorasHasta() ;
			break;
		case "DIA":
			mensaje+= restriccion.getValorDia();
			break;
		case "FECHA":
			mensaje+= restriccion.getValorFecha();
			break;
		default:
			break;
		}
		
		mensaje+="  /  Procesos --> ";
		switch (restriccion.getOidProceso()) {
		case 0:
			mensaje+=" Todos" ;
			break;
		default:
			mensaje+=" Oid: " + +restriccion.getOidProceso();
			break;
		}
		
		mensaje+="  /  Farmacia --> ";
		switch (restriccion.getOidProceso()) {
		case 0:
			mensaje+=" Todas" ;
			break;
		default:
			mensaje+= restriccion.getIdFarmacia() + " " + restriccion.getNombreFarmacia() ;
			break;
		}
		
		mensaje+="  /  Lanzadera  --> ";
		switch (restriccion.getOidProceso()) {
		case 0:
			mensaje+=" Todas" ;
			break;
		default:
			mensaje+= restriccion.getLanzadera()  ;
			break;
		}
		return mensaje;
}


	public boolean restriccionesPorTipo(Proceso proceso, String tipoRestriccion) throws SQLException {
		int result=0;
		/*
		List lista = dao.restriccionesPorTipo(proceso, tipoRestriccion);
		if(lista!=null)
			result = lista.size();
		*/
		result = dao.countRestriccionesPorTipo(proceso, tipoRestriccion, true);
		return result>0;
	}
	
    /**
     * Método que mira si hay alguna restricción por hora, dia o fecha, para poder ejecutar los procesos. Para que se puedan ejecutar siempre han de devolver "false"
     * En caso que exista alguna restricción devolverá un "true"
     * @param proceso
     * @return
     * @throws SQLException
     * TO-DO Realizarlo para farmacia
     */
	public boolean hayRestriccionHorario(Proceso proceso) throws SQLException {
    	boolean restriccionHora=false;
    	boolean restriccionDia=false;
		boolean restriccionFecha=false;
		ProcesoRestriccionesHelper helper = new ProcesoRestriccionesHelper();
		restriccionHora = helper.restriccionesPorTipo(proceso, SPDConstants.PROCESO_RESTRIC_HORA);
		if(!restriccionHora)
			restriccionDia = helper.restriccionesPorTipo(proceso, SPDConstants.PROCESO_RESTRIC_DIA);
		if(!restriccionHora && !restriccionDia)
			restriccionFecha = helper.restriccionesPorTipo(proceso, SPDConstants.PROCESO_RESTRIC_FECHA);

		System.out.println(restriccionHora +" "+ restriccionDia +" "+ restriccionFecha);

		return restriccionHora || restriccionDia || restriccionFecha;
	}



}

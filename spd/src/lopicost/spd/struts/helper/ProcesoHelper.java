package lopicost.spd.struts.helper;

import java.util.Date;
import java.util.List;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import lopicost.spd.controller.SpdLogAPI;
import lopicost.spd.model.Proceso;
import lopicost.spd.model.ProcesoEjecucion;
import lopicost.spd.model.ProcesoEjecucionLog;
import lopicost.spd.persistence.ProcesoDAO;
import lopicost.spd.persistence.ProcesoEjecucionDAO;
import lopicost.spd.persistence.ProcesoEjecucionLogDAO;
import lopicost.spd.persistence.ProcesoHistoricoDAO;
import lopicost.spd.struts.form.ProcesosForm;
import lopicost.spd.utils.DataUtil;
import lopicost.spd.utils.DateUtilities;
import lopicost.spd.utils.StringUtil;

public class ProcesoHelper {
	private final ProcesoDAO procDAO = new ProcesoDAO();
	private final ProcesoEjecucionDAO procEjecDAO = new ProcesoEjecucionDAO();
	private final ProcesoEjecucionLogDAO procEjecLogDAO = new ProcesoEjecucionLogDAO();
	
	/**
	 * Recupera todos los procesos del sistema
	 * @param idUsuario
	 * @return
	 * @throws SQLException
	 */
	public List<Proceso> list(String idUsuario) throws SQLException {
		return procDAO.list(idUsuario);
	}


  	/**
  	 * Recupera un objeto a partir de su OID
  	 * @param idUsuario
  	 * @param oidProceso
  	 * @return
  	 * @throws SQLException
  	 */
	public Proceso findByOidProceso(String idUsuario, int oidProceso) throws SQLException {
		return procDAO.findByOidProceso( idUsuario,  oidProceso);
	}
	
	/**
	 * Creación de un proceso en la base de datos, según los datos que se reciben del formulario
	 * @param idUsuario
	 * @param form
	 * @param errors
	 * @return
	 * @throws ParseException
	 * @throws SQLException
	 */
	public boolean nuevoProceso(String idUsuario, ProcesosForm form, List errors) throws ParseException, SQLException {
		boolean check = compruebaSiExisteLanzadera(idUsuario, form, errors);
		//check = compruebaDatosFrecuenciasPeriodos(idUsuario, form, errors);
		check = validarDatos(idUsuario, form, errors);
		if(!check) 
			return false;
		
		// Conversores
		SimpleDateFormat timestampFormat = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		Proceso proceso = new Proceso();

		// Asignaciones simples
		proceso.setNombreProceso(form.getNombreProceso()); 
		proceso.setLanzadera(form.getLanzadera());
		proceso.setActivo(form.getActivo());
		proceso.setDescripcion(form.getDescripcion());
		proceso.setParametros(form.getParametros());
		proceso.setTipoEjecucion(form.getTipoEjecucion());
		proceso.setTipoPeriodo(form.getTipoPeriodo());
		proceso.setDiasSemana(form.getDiasSemana());
		proceso.setDiasMes(form.getDiasMes());
		// Campos opcionales con conversión y validación
		if (form.getHoraEjecucion() != null && !form.getHoraEjecucion().isEmpty()) {
		    proceso.setHoraEjecucion(form.getHoraEjecucion());
		}
		if (form.getMaxReintentos()>0) {
		    proceso.setMaxReintentos(form.getMaxReintentos());
		}
		if (form.getMaxDuracionSegundos()>0) {
		    proceso.setMaxDuracionSegundos(form.getMaxDuracionSegundos());
		}
		if (form.getFechaDesde() != null && !form.getFechaDesde().isEmpty()) {
			Date fechaDesde = DateUtilities.getDate( form.getFechaDesde(), "dd/MM/yyyy");
			String fechaDesdeFormateada =  timestampFormat.format(fechaDesde);
		    proceso.setFechaDesde(fechaDesdeFormateada);
		}
		if (form.getFechaHasta() != null && !form.getFechaHasta().isEmpty()) {
			Date fechaHasta = DateUtilities.getDate( form.getFechaHasta(), "dd/MM/yyyy");
			String fechaHastaFormateada =  timestampFormat.format(fechaHasta);
		    proceso.setFechaHasta(fechaHastaFormateada);
		}

		proceso.setUsuarioCreacion(idUsuario);
		boolean result =  procDAO.nuevo(idUsuario, proceso);
		if(result)
		{
			
			String mensaje = proceso.toString();

			//INICIO creación de historico en BBDD
			boolean historico = creaProcesoHistoricoPorLanzadera(proceso.getLanzadera());
			
			//INICIO creación de log en BBDD
			try{
				SpdLogAPI.addLog(idUsuario, null,  null, null, SpdLogAPI.A_PROCESO, SpdLogAPI.B_CREACION, "", "SpdLog.proceso.creado.general", 
						" Creado histórico --> " + historico  + " / Datos proceso " +  proceso.toString() );
			}catch(Exception e){}	// Cambios--> @@.
			//FIN creación de log en BBDD
		}
		return result;
	}

    public boolean validarDatos(String idUsuario, ProcesosForm form, List errors) {
    	boolean result = true;
		int frecuenciaPeriodo = form.getFrecuenciaPeriodo(); // ha de ser >0
		int maxDuracionSegundos = form.getMaxDuracionSegundos();
    	String periodo = form.getTipoPeriodo();				 // dependiendo del periodo ha de haber diasSemana, diasMes
		String diasSemana = form.getDiasSemana();			 //	
		String diasMes = form.getDiasMes();
		String horaEjecucion = form.getHoraEjecucion();		 //ha de estar indicada, menos en los horarios restringidos
		String descripcion = form.getDescripcion();
		String fechaDesde = form.getFechaDesde();
		
		switch (periodo) {
		case "MESES":
			if(!contieneAlgunDiaValido(diasMes))
			{
				errors.add("Si es mensual es necesario indicar día de mes");
				result = false;
				break;
			}
		case "SEMANAS":
			if(diasSemana==null || diasSemana.equals("") || diasSemana.equalsIgnoreCase("null"))
			{
				errors.add("Es necesario indicar un día de semana");
				result = false;
				break;
			}
		default:
			break;
		}
		
		if(maxDuracionSegundos<=0)
		{
			errors.add("Falta indicar el tope de duración posible");
			result = false;
		}
		if(fechaDesde==null || fechaDesde.equals(""))
		{
			errors.add("Falta indicar la fecha de inicio del proceso");
			result = false;
		}
		if(descripcion==null || descripcion.equals(""))
		{
			errors.add("Falta indicar alguna descripción del proceso");
			result = false;
		}
		if(frecuenciaPeriodo<=0)
		{
			errors.add("la frecuencia " + frecuenciaPeriodo + " no es correcta, es necesario que sea positiva");
			result = false;
		}
		if(horaEjecucion==null || horaEjecucion.equals(""))
		{
			errors.add("Es necesario indicar una hora de ejecución");
			result = false;
		}
	
		return result;
	}
/**
 * 			

				else if(f.maxDuracionSegundos.value=='')
				alert('Falta indicar el tope de duración posible ');

 * @param lanzadera
 * @return
 * @throws SQLException
 */

	public boolean creaProcesoHistoricoPorLanzadera(String lanzadera) throws SQLException {
    	return ProcesoHistoricoDAO.creaProcesoHistoricoPorLanzadera(lanzadera);
    }

    /**
     * Para no duplicar nombres del procedure o lanzadera que se utiliza, miramos que no exista pre
     * @param idUsuario
     * @param form
     * @param errors
     * @return
     * @throws SQLException
     */
	private boolean compruebaSiExisteLanzadera(String idUsuario, ProcesosForm form, List errors) throws SQLException {
		if(form.getLanzadera()==null || form.getLanzadera().equals(""))
		{
			errors.add( "No se ha informado de la lanzadera. ");
			return false;
		}
		List procesos = procDAO.findByFilters(idUsuario, form.getLanzadera(), -1, false, false);
		if(procesos!=null && procesos.size()>0)
		{
			errors.add( "Ya existe una lanzadera con el mismo nombre. ");
			return false;
		}
			
		return true;
	}


	/**
	 * Método que se encarga de actualizar los datos de un proceso, comparando los datos del formulario con los datos de origen.
	 * @param idUsuario
	 * @param proceso
	 * @param f
	 * @return true si ha habido cambios, false en caso contrario
	 * @throws SQLException
	 */
	public boolean actualizaDatos(String idUsuario, Proceso proceso, ProcesosForm f)  throws SQLException {
		boolean cambios =false;
		String antes = ""; 
		String despues = "";
		
		String querySet="";
		if(proceso!=null)
		{
			/*  INICIO Activo*/
			if (!Objects.equals(proceso.getActivo(), f.getActivo())) 
			{
				antes+=  " | Activo: "+ proceso.getActivo();
				despues+=" | Activo: "+ f.getActivo();

				cambios =true;
				if (!querySet.equals("")) 
					querySet+= ", "; //añadimos la coma en caso que exista uno previo 		
				 
				querySet+= " activo = '"+ f.getActivo() + "'" ;
			}
		
			/*  INICIO Parámetros*/
			if (!Objects.equals(proceso.getParametros(), f.getParametros())) 
			{
				antes+=  " | Parámetros: "+ proceso.getParametros();
				despues+=" | Parámetros: "+ f.getParametros();

				if (!querySet.equals(""))  	querySet+= ", ";
				 
				cambios =true;
				querySet+= " parametros = '"+ f.getParametros() + "'" ;
			}
			/*  INICIO TipoEjecucion*/
			if (!Objects.equals(proceso.getTipoEjecucion(), f.getTipoEjecucion())) 
			{
				antes+=  " | TipoEjecucion: "+ proceso.getTipoEjecucion();
				despues+=" | TipoEjecucion: "+ f.getTipoEjecucion();

				if (!querySet.equals(""))  	querySet+= ", ";
				 
				cambios =true;
				querySet+= " tipoEjecucion = '"+ f.getTipoEjecucion() + "'" ;
			}
			/*  INICIO tipoPeriodo*/
			if (!Objects.equals(proceso.getTipoPeriodo(), f.getTipoPeriodo())) 
			{
				antes+=  " | tipoPeriodo: "+ proceso.getTipoPeriodo();
				despues+=" | tipoPeriodo: "+ f.getTipoPeriodo();

				if (!querySet.equals(""))  	querySet+= ", ";
				 
				cambios =true;
				querySet+= " tipoPeriodo = '"+ f.getTipoPeriodo() + "'" ;
			}
			/*  INICIO frecuenciaPeriodo*/
			if (!Objects.equals(proceso.getFrecuenciaPeriodo(), f.getFrecuenciaPeriodo())) 
			{
				antes+=  " | frecuenciaPeriodo: "+ proceso.getFrecuenciaPeriodo();
				despues+=" | frecuenciaPeriodo: "+ f.getFrecuenciaPeriodo();

				if (!querySet.equals(""))  	querySet+= ", ";
				 
				cambios =true;
				querySet+= " frecuenciaPeriodo = '"+ f.getFrecuenciaPeriodo() + "'" ;
			}
			/*  INICIO DiasSemana*/
			if (!Objects.equals(proceso.getDiasSemana(), f.getDiasSemana())) 
			{
				antes+=  " | Activo: "+ proceso.getDiasSemana();
				despues+=" | Activo: "+ f.getDiasSemana();

				if (!querySet.equals(""))  	querySet+= ", ";
				 
				cambios =true;
				querySet+= " diasSemana = '"+ f.getDiasSemana() + "'" ;
			}
			/*  INICIO DiasMes*/
			if (!Objects.equals(proceso.getDiasMes(), f.getDiasMes())) 
			{
				antes+=  " | Activo: "+ proceso.getDiasMes();
				despues+=" | Activo: "+ f.getDiasMes();

				if (!querySet.equals(""))  	querySet+= ", ";
				 
				cambios =true;
				querySet+= " diasMes = '"+ f.getDiasMes() + "'" ;
			}
			/*  INICIO HoraEjecucion*/
			if (!Objects.equals(proceso.getHoraEjecucion(), f.getHoraEjecucion())) 
			{
				antes+=  " | HoraEjecucion: "+ proceso.getHoraEjecucion();
				despues+=" | HoraEjecucion: "+ f.getHoraEjecucion();

				if (!querySet.equals(""))  	querySet+= ", ";
				 
				cambios =true;
				querySet+= " horaEjecucion = '"+ f.getHoraEjecucion() + "'" ;
			}
			/*  INICIO maxReintentos*/
			if (!Objects.equals(proceso.getMaxReintentos(), f.getMaxReintentos())) 
			{
				antes+=  " | MaxReintentos: "+ proceso.getMaxReintentos();
				despues+=" | MaxReintentos: "+ f.getMaxReintentos();

				if (!querySet.equals(""))  	querySet+= ", ";
				 
				cambios =true;
				querySet+= " maxReintentos = '"+ f.getMaxReintentos() + "'" ;
			}
			/*  INICIO MaxDuracionSegundos*/
			if (!Objects.equals(proceso.getMaxDuracionSegundos(), f.getMaxDuracionSegundos())) 
			{
				antes+=  " | MaxDuracionSegundos: "+ proceso.getMaxDuracionSegundos();
				despues+=" | MaxDuracionSegundos: "+ f.getMaxDuracionSegundos();

				if (!querySet.equals(""))  	querySet+= ", ";
				 
				cambios =true;
				querySet+= " maxDuracionSegundos = '"+ f.getMaxDuracionSegundos() + "'" ;
			}		
			/*  INICIO FechaDesde*/
			if (!Objects.equals(proceso.getFechaDesde(), f.getFechaDesde())) 
			{
				antes+=  " | FechaDesde: "+ proceso.getFechaDesde();
				despues+=" | FechaDesde: "+ f.getFechaDesde();

				if (!querySet.equals(""))  	querySet+= ", ";
				 
				cambios =true;
				querySet+= " fechaDesde = '"+ f.getFechaDesde() + "'" ;
			}
			/*  INICIO FechaHasta*/
			if (!Objects.equals(proceso.getFechaHasta(), f.getFechaHasta())) 
			{
				antes+=  " | FechaHasta: "+ proceso.getFechaHasta();
				despues+=" | FechaHasta: "+ f.getFechaHasta();

				if (!querySet.equals(""))  	querySet+= ", ";
				 
				cambios =true;
				querySet+= " fechaHasta = '"+ f.getFechaHasta() + "'" ;
			}		
			if(cambios)
			{
				String query = " UPDATE SPDAC.dbo.SPD_Procesos SET " + querySet + ", version=version+1 WHERE  OIDPROCESO='"+proceso.getOidProceso()+"'";
				cambios = ProcesoDAO.update(query);
				boolean historico = creaProcesoHistoricoPorLanzadera(proceso.getLanzadera());

				//INICIO creación de log en BBDD
				try{
					SpdLogAPI.addLog(idUsuario, null,  null, null, SpdLogAPI.A_PROCESO, SpdLogAPI.B_EDICION, SpdLogAPI.C_DATOSGENERALES, "SpdLog.proceso.edicion.general", 
							 new String[]{antes, despues} );
				}catch(Exception e){}	// Cambios--> @@.
				//FIN creación de log en BBDD
			}
		}
		return cambios;
	}

    /**
     * Extrae todos los días válidos del mes, incluyendo rangos tipo "1-5".
     */
    public static Set<Integer> extraerDiasValidos(String texto) {
        Set<Integer> dias = new TreeSet<>();
        if (texto == null || texto.trim().isEmpty()) return dias;

        // Normaliza separadores
        texto = texto.replaceAll("[,;]", " ");
        texto = texto.replaceAll("\\s+", " ").trim();

        for (String parte : texto.split(" ")) {
            if (parte.contains("-")) {
                // Es un rango
                String[] extremos = parte.split("-");
                if (extremos.length == 2) {
                    try {
                        int desde = Integer.parseInt(extremos[0]);
                        int hasta = Integer.parseInt(extremos[1]);
                        if (desde > hasta) continue;
                        for (int i = desde; i <= hasta; i++) {
                            if (i >= 1 && i <= 31) dias.add(i);
                        }
                    } catch (NumberFormatException ignored) {
                    }
                }
            } else {
                // Número suelto
                try {
                    int dia = Integer.parseInt(parte);
                    if (dia >= 1 && dia <= 31) dias.add(dia);
                } catch (NumberFormatException ignored) {
                }
            }
        }

        return dias;
    }

    public static boolean contieneAlgunDiaValido(String texto) {
        return !extraerDiasValidos(texto).isEmpty();
    }

    public static String[] obtenerDiasValidosComoArray(String texto) {
        return extraerDiasValidos(texto).stream()
                .map(String::valueOf)
                .toArray(String[]::new);
    }


	public List<ProcesoEjecucion> listarEjecuciones(String idUsuario, int oidProceso) throws SQLException {
		return procEjecDAO.list(idUsuario, oidProceso);
	}


	public ProcesoEjecucion findEjecucionByOid(String idUsuario, int oidProcesoEjecucion) throws SQLException {
		ProcesoEjecucion result = null;
		List<ProcesoEjecucion> lista = procEjecDAO.findByFilters(idUsuario, -1, oidProcesoEjecucion, null, -1, null, false);
		if(lista!=null && lista.size()>0)
			result = (ProcesoEjecucion) lista.get(0);
		return result;
	}


	public List<ProcesoEjecucionLog> listarLogEjecucion(String idUsuario, int oidProcesoEjecucion) throws SQLException {
		return procEjecLogDAO.listarLogProcesoEjecuciones(idUsuario, oidProcesoEjecucion, false);
	}

}
package lopicost.spd.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;


import lopicost.config.pool.dbaccess.Conexion;
import lopicost.spd.model.Proceso;
import lopicost.spd.utils.DateUtilities;
import lopicost.spd.utils.HelperSPD;
import lopicost.spd.utils.SPDConstants;


public class ProcesoDAO extends GenericDAO{
	
	private String className="ProcesoDAO";
    //private  final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    //private  final SimpleDateFormat DATE_FORMAT_TIME = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private final ProcesoEjecucionDAO procEjecDAO = new ProcesoEjecucionDAO();

	
	public List<Proceso> list(String idUsuario) throws SQLException {
	    return findByFilters(idUsuario, null, -1, false, false) ;
	}
	
	/**
	 * Retorna un proceso según su OID
	 * @param idUsuario
	 * @param oidProceso
	 * @return
	 * @throws SQLException
	 */
	public  Proceso findByOidProceso(String idUsuario, int oidProceso) throws SQLException {
		List<Proceso> procesos= findByFilters(idUsuario, null, oidProceso, false, false);
		if(procesos!=null && procesos.size()>0)
			return (Proceso) procesos.get(0);
		
		return null;
	}
	
	/** ok
	 * Retorna un listado de procesos según filtros
	 * @param idUsuario
	 * @param idProceso
	 * @param lanzadera
	 * @param oidProceso
	 * @return
	 * @throws SQLException
	 */
	public List<Proceso> findByFilters(String idUsuario, String lanzadera, int oidProceso, boolean activo, boolean automaticos) throws SQLException {
		 List<Proceso> result = new ArrayList<Proceso>();
	     String sql = "SELECT * FROM SPD_procesos ";
	     sql+= " WHERE 1 = 1 ";
	
	    if(lanzadera != null && !lanzadera.equals(""))
	       	sql+= " AND lanzadera =  '" + lanzadera + "'";
        if( oidProceso> 0 )
        	sql+= " AND oidProceso =  '" + oidProceso + "'";
        if(activo)
        {
        	sql+= " AND activo =  '"+SPDConstants.PROCESO_ACTIVO+"'";
        	sql+= " AND fechaDesde  <=  CONVERT(datetime, getDate(), 120)";
        	sql+= " AND  ";
        	sql+= " ( ";
        	sql+= " 	fechaHasta is null OR fechaHasta ='' ";
        	sql+= " 	OR ";
        	sql+= " 	fechaHasta +1 >= CONVERT(datetime, getDate(), 120)";
        	sql+= " ) ";
        }
        if(automaticos)
        {
        	sql+= " AND tipoEjecucion =  '"+SPDConstants.PROCESO_TIPOEJEC_AUTO + "'";
        }
       	sql+= " order by apartado desc, prioridad asc, orden asc  ";

	        
	  		Connection con = Conexion.conectar();
			//System.out.println(HelperSPD.dameFechaHora() + " - " + className + "--> list -->" +sql );		
		    ResultSet resultSet = null;
		    try {
		    	PreparedStatement pstat = con.prepareStatement(sql);
		    	resultSet = pstat.executeQuery();
				Proceso proceso =null;

		    	while (resultSet.next()) {
		    		proceso = creaProceso(resultSet);
		    		proceso.setUltimaEjecucion(procEjecDAO.obtenerUltimaEjecucion(idUsuario, proceso));
		    		//si la última ejecución no tiene fecha la incorporamos como activa
		    		if(proceso.getUltimaEjecucion()!=null)
		    		{
		    			String estado = proceso.getUltimaEjecucion().getEstado();
		    			if(estado.equals(SPDConstants.PROCESO_EJEC_ESTADO_EJECUTANDO) || estado.equals(SPDConstants.PROCESO_EJEC_ESTADO_PENDIENTE))
		    				proceso.setEjecucionActiva(proceso.getUltimaEjecucion());
		    		}
		    		
		    		result.add(proceso);
	    		 }
		    } catch (SQLException e) {
		    	e.printStackTrace(); 
		    } finally {
		    	con.close();
		    }
		    return result;
		}

	/**
	 * Se encarga de insertar un nuevo proceso creado por el usuario
	 * @param idUsuario
	 * @param proceso
	 * @return
	 * @throws SQLException
	 */

	public  boolean nuevo(String idUsuario, Proceso proceso) throws SQLException {
	       int result=0;
			  Connection con = Conexion.conectar();
			  String sql = "INSERT INTO SPD_Procesos (" +
				        "nombreProceso, lanzadera, descripcion, parametros, tipoEjecucion, tipoPeriodo, " +
				        "frecuenciaPeriodo, diasSemana, diasMes, horaEjecucion, maxReintentos, maxDuracionSegundos, " +
				        "fechaDesde, fechaHasta, usuarioCreacion, activo, nombreOriginal, apartado, orden, prioridad " +
				        ") ";
			  String parametros = sql;

			  // Añadimos para sustituir
			  sql +=  "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			  
				PreparedStatement ps = con.prepareStatement(sql);
			 //  concatenamos los valores y en paralelo construimos query para consulta
			  	ps.setString(1, proceso.getNombreProceso());	
			  	parametros += "VALUES ( '" + proceso.getNombreProceso() + "'"; // nombreProceso

			  	ps.setString(2, proceso.getLanzadera());	
			  	parametros += ", '" + proceso.getLanzadera() + "'"; // lanzadera

			  	ps.setString(3, proceso.getDescripcion());	
			  	parametros += ", '" + proceso.getDescripcion() + "'"; // descripcion
/*
			  	//Inicio Estado
			  	String estado ="ACTIVO";
			  	if(proceso.getEstado()!=null && !proceso.getEstado().equalsIgnoreCase("SI"))
			  		estado ="INACTIVO";
			  	ps.setString(4, estado);	
			  	parametros += ", '" + proceso.getEstado() + "'"; // estado
			  	//Fin Estado
			  	*/
			  	//inicio parámetros
			  	String param = proceso.getParametros();
			  	if(param.equals(""))
			  	{
			  		param = null;
			  		parametros += ", " + param; // parametros
			  	}
			  	else
				  	parametros += ", '" + param + "'"; // parametros

			  	ps.setString(4, param);	
			  	//fin parámetros

			  	//inicio tipoEjecucion
			  	String tipoEjecucion = proceso.getTipoEjecucion();
			  	if(tipoEjecucion==null || tipoEjecucion.equals(""))
			  	{
			  		tipoEjecucion = "AUTOMATICA";
			  	}
			  	ps.setString(5, tipoEjecucion);	
			  	parametros += ", '" + tipoEjecucion + "'"; // tipoEjecucion
			  	//fin tipoEjecucion

			  	//inicio tipoPeriodo
			  	String tipoPeriodo = proceso.getTipoPeriodo();
			  	if(tipoPeriodo==null || tipoPeriodo.equals(""))
			  	{
			  		tipoPeriodo = "DIARIA";
			  	}
			  	ps.setString(6, tipoPeriodo);	
			  	parametros += ", '" + tipoPeriodo + "'"; // tipoEjecucion
			  	//fin tipoPeriodo

			  	//inicio frecuenciaPeriodo
			  	int frecuenciaPeriodo = proceso.getFrecuenciaPeriodo();
			  	ps.setInt(7, frecuenciaPeriodo);	
			  	parametros += ", '" + frecuenciaPeriodo + "'"; // tipoEjecucion
			  	//fin frecuencia

			    //inicio diasSemana
			  	String diasSemana = proceso.getDiasSemana();
			  	if(diasSemana==null || diasSemana.equals(""))
			  	{
			  		param = "1,2,3,4,5,6,7";
			  	}
			  	ps.setString(8, diasSemana);	
			  	parametros += ", '" + diasSemana + "'"; // diasSemana
			    //fin diasSemana

			    //inicio diasMes
			  	String diasMes = proceso.getDiasMes();
			  	ps.setString(9, diasMes);	
			  	parametros += ", '" + diasMes + "'"; // diasMes
			    //fin diasMes

			  	
			  	ps.setString(10, proceso.getHoraEjecucion());	
			  	parametros += ", '" + proceso.getHoraEjecucion() + "'"; // horaEjecucion

			  	ps.setInt(11, proceso.getMaxReintentos());	
			  	parametros += ", " + proceso.getMaxReintentos(); // maxReintentos

			  	if(proceso.getMaxDuracionSegundos()==null || proceso.getMaxDuracionSegundos().equals(""))
			  		proceso.setMaxDuracionSegundos(SPDConstants.MAX_DURACIONSEGUNDOS_PROCESO); //si no hay límite de segundos ponemos uno
				else proceso.setMaxDuracionSegundos(proceso.getMaxDuracionSegundos());

			  	
			  	ps.setInt(12, proceso.getMaxDuracionSegundos());	
			  	parametros += ", " + proceso.getMaxDuracionSegundos(); // maxDuracionSegundos

			  	
			  	//Inicio fechaFin (Activación)
			  	String fechaDesde = proceso.getFechaDesde();
		  		//Date dFechaDesde = new Date();
		  		LocalDateTime dFechaDesde = LocalDateTime.now();
			  	if(fechaDesde!=null &&  !fechaDesde.equals(""))
			  	{
			  		//dFechaDesde = DateUtilities.getDate( fechaDesde, "dd/MM/yyyy");
			  		dFechaDesde =  LocalDateTime.parse(fechaDesde, SPDConstants.FORMAT_DATE); // a LocalDateTime
			  	}
			  	fechaDesde = dFechaDesde.format(SPDConstants.FORMAT_DATE); // a String
			  	//fechaDesde =  DATE_FORMAT.format(dFechaDesde);
			  	
			  	ps.setString(13, fechaDesde);
			  	parametros += ", '" + fechaDesde + "'"; // fechaInicio
			  	//Fin fechaFin
			  	
			  	//Tratamiento de la fecha fin,
			  	//LocalDate localDate = LocalDate.of(2099, 12, 31);
			  	//fechaFin = java.sql.Date.valueOf(localDate);

			  	//Inicio fechaFin (Desctivación)
			  	String fechaHasta = proceso.getFechaHasta();
			  	LocalDateTime dFechaHasta = null;
			  	
			  	if(fechaHasta!=null &&  !fechaHasta.equals(""))
			  	{
			  		dFechaHasta =  LocalDateTime.parse(fechaHasta, SPDConstants.FORMAT_DATE); // a LocalDateTime
			  		//dFechaHasta = DateUtilities.getDate( fechaHasta, "dd/MM/yyyy");
			  		//fechaHasta =  DATE_FORMAT.format(dFechaHasta);
			  		fechaHasta = dFechaHasta.format(SPDConstants.FORMAT_DATE); // a String
				  	parametros += ", '" + fechaHasta + "'"; // fechaFin

			  	}
			  	else
				  	parametros += ", " + null; // fechaFin

			  	ps.setString(14, fechaHasta);
			  	
			  	//fin fechaFin

			  	ps.setString(15, idUsuario);
			  	parametros += ", '" + idUsuario + "'"; //idUsuario
			  	
			  	ps.setString(16, proceso.getActivo());
			  	parametros += ", '" + proceso.getActivo() + "'"; // Activo

			  	ps.setString(17, proceso.getNombreOriginal());
			  	parametros += ", '" + proceso.getNombreOriginal(); // NombreOriginal

			  	ps.setString(18, proceso.getApartado());
			  	parametros += ", '" + proceso.getApartado(); // Apartado

			  	ps.setInt(19, proceso.getOrden());	
			  	parametros += ", " + proceso.getOrden(); // Orden

			  	ps.setInt(20, proceso.getPrioridad());	
			  	parametros += ", " + proceso.getPrioridad(); // Prioridad

			  	
			  	// Cerramos la consulta
			  	parametros += ")";

			  	// imprimir el string generado
			  	System.out.println("Consulta SQL - nuevo--> " + parametros);

			    	
		    try {
		    	result=ps.executeUpdate();
		       
		     } catch (SQLException e) {
		         e.printStackTrace();
		     } finally {con.close();}
			
			return result>0;
		}

	/**
	 * Método que se encarga de actualizar el estado de un proceso según el nuevoEstado pasado por parámetro
	 * @param proceso
	 * @param nuevoEstado
	 * @return
	 * @throws SQLException
	 */
	public  boolean actualizarEstadoProceso(Proceso proceso, String actividad) throws SQLException {

		if(proceso==null )
			return false;

		System.out.println(" Actualización de estado de proceso ");
		int result=0;
		Connection con = null;
		con = Conexion.conectar();
		    String sql = "UPDATE SPD_procesos SET activo = '"+actividad+"' WHERE lanzadera='"+proceso.getLanzadera()+"' AND oidProceso = " + proceso.getOidProceso();
            System.out.println(HelperSPD.dameFechaHora() + " - actualizarEstadoProceso  " + sql);

		    try (PreparedStatement ps = con.prepareStatement(sql)) {
		        result = ps.executeUpdate();
		    }
			return result>0;
		}

	/** ok
	 * Método que se encarga de construir un bean a partir de un registro de procesoEjecución de la bbdd
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	 private  Proceso creaProceso(ResultSet rs) throws SQLException {
		 Proceso proceso = new Proceso();
		 
		//Date dFechaDesde = rs.getTimestamp("fechaCreacion");
		LocalDateTime dFechaDesde = rs.getTimestamp("fechaCreacion").toLocalDateTime();
		//String fechaDesde =  DATE_FORMAT_TIME.format(dFechaDesde);
		String fechaDesde =  dFechaDesde.format(SPDConstants.FORMAT_DATETIME_24h); // a String
		  	
		 proceso.setFechaCreacion(fechaDesde);
		 proceso.setOidProceso(rs.getInt("oidProceso"));
		 proceso.setVersion(rs.getInt("version"));
		 proceso.setUsuarioCreacion(rs.getString("usuarioCreacion"));
		 proceso.setLanzadera(rs.getString("lanzadera"));
		 proceso.setNombreProceso(rs.getString("nombreProceso"));
		 proceso.setDescripcion(rs.getString("descripcion"));
		 proceso.setActivo(rs.getString("activo"));
		 proceso.setParametros(rs.getString("parametros"));
		 proceso.setTipoEjecucion(rs.getString("tipoEjecucion"));
		 proceso.setTipoPeriodo(rs.getString("tipoPeriodo"));
		 proceso.setFrecuenciaPeriodo(rs.getInt("frecuenciaPeriodo"));
		 proceso.setDiasSemana(rs.getString("diasSemana"));
		 proceso.setDiasMes(rs.getString("diasMes"));
		 proceso.setHoraEjecucion(rs.getString("horaEjecucion"));
		 proceso.setMaxReintentos(rs.getInt("maxReintentos"));
		 proceso.setNombreOriginal(rs.getString("nombreOriginal"));
		 proceso.setApartado(rs.getString("apartado"));
		 proceso.setOrden(rs.getInt("orden"));
		 proceso.setPrioridad(rs.getInt("prioridad"));
		 int maxDuracion = rs.getInt("maxDuracionSegundos");
		 if (rs.wasNull()) proceso.setMaxDuracionSegundos(null);
		    else proceso.setMaxDuracionSegundos(maxDuracion);

		 proceso.setFechaDesde(rs.getString("fechaDesde"));
		 proceso.setFechaHasta(rs.getString("fechaHasta"));
		 
		 return proceso;
	}

	 
/*	 
-------------------------------------------------------------------------------------------------------------------
---------------------------- MÉTODOS DE EJECUCIÓN ----------------------------------------------------------------- 
-------------------------------------------------------------------------------------------------------------------
*//*
		public void resetearProcesosEnEjecucion() throws SQLException {
			System.out.println(" Limpieza de procesos");
			Connection con = null;
			con = Conexion.conectar();
			    String sql = "UPDATE SPD_procesos SET estado = 'No iniciado' WHERE estado = 'En ejecución'";
			    try (PreparedStatement ps = con.prepareStatement(sql)) {
			        ps.executeUpdate();
			    }
			}
		
		
		

	    public void finalizarProceso(int oidProceso, String resultado, String error) {
	        String sql = "UPDATE SPD_procesos SET estado = 'Finalizado', fechaEjecucionFin = CURRENT_TIMESTAMP, resultado = ?, error = ? WHERE oidProceso = ?";
	        Connection con = null;
	    	PreparedStatement stmt = null; 
	        try { con = Conexion.conectar();
	             stmt = con.prepareStatement(sql);
	            stmt.setString(1, resultado);
	            stmt.setString(2, error);
	            stmt.setInt(3, oidProceso);
	            stmt.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    */
/*	    // Obtener todos los procesos
	    public List<Proceso> obtenerTodos() throws SQLException {
	        List<Proceso> procesos = new ArrayList<>();
	        String sql = "SELECT * FROM SPD_procesos";

	        return procesos;
	    }
*/
	    // Actualizar un proceso
	    public void actualizarProceso(Proceso p) throws SQLException {
	        String sql = "UPDATE SPD_procesos SET nombreProceso = ?, descripcion = ?, parametros = ?, fechaCreacion = ?, " +
	                     "tipoEjecucion = ?, tipoPeriodo = ?, frecuenciaPeriodo = ?, horaEjecucion = ?, diasSemana = ?, maxReintentos = ?, " +
	                     "ultimoResultado = ?, fechaUltimaEjecucion = ?, duracionUltimaEjecucionSegundos = ?, errorUltimaEjecucion = ?, " +
	                     "usuarioUltimaEjecucion = ? WHERE oidProceso = ?";

	    }

		public Proceso obtenerProcesoPorId(int oidProceso) {
			// TODO Esbozo de método generado automáticamente
			return null;
		}


		public void detenerProceso(String idUsuario, int oidProceso) {
			// TODO Esbozo de método generado automáticamente
			
		}

		public void reiniciarProceso(int oidProceso) {
			// TODO Esbozo de método generado automáticamente
			
		}
/*
		 // Método para consultar el estado de ejecución de un proceso
	    private String obtenerEstadoProceso(int oidProceso) {
	        String sql = "SELECT estado FROM SPD_procesos WHERE oidProceso = ?";
	        Connection con = null;
	    	PreparedStatement stmt = null; 
	        try { con = Conexion.conectar();
	             stmt = con.prepareStatement(sql);
	             
	             stmt.setInt(1, oidProceso);
	            ResultSet rs = stmt.executeQuery();
	            if (rs.next()) {
	                return rs.getString("estado");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	    */

	    // Método para verificar si el procedimiento almacenado está en ejecución
	    public boolean estaEnEjecucion(int oidProceso) {
	        String sql = "SELECT COUNT(*) FROM sys.dm_exec_requests WHERE status = 'running' AND command = 'EXEC' AND blocking_session_id = 0";
	        Connection con = null;
	    	PreparedStatement stmt = null; 
	        try { con = Conexion.conectar();
	             stmt = con.prepareStatement(sql);
	            ResultSet rs = stmt.executeQuery();
	            if (rs.next() && rs.getInt(1) > 0) {
	                return true;
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return false;
	    }
/*
		public void actualizarEstadoProceso(int oidProceso, String string) {
			// TODO Esbozo de método generado automáticamente
			
		}

		   // Insertar un nuevo proceso
	    public void iniciarProceso(String idUsuario, int oidProceso) {
	    	Connection con = null;
	    	PreparedStatement stmt = null; 

	            try {
	            	con = Conexion.conectar();
	                // Llamada al procedimiento almacenado para ejecutar el proceso
	            	stmt = con.prepareCall("{call ejecutarProcesoEjemplo(?)}");
	                stmt.setInt(1, oidProceso);
	               
	                System.out.println("Lanzando proceso con oid: " + oidProceso);
	                stmt.execute();

	                System.out.println("Proceso lanzado correctamente.");

	                // Consultar el estado del proceso después de ejecutar el procedure
	                String estado = ProcesoHelper.obtenerEstadoProceso(idUsuario, oidProceso);

	                // Verificar si el proceso ha finalizado o ha ocurrido un error
	                if ("Finalizado".equalsIgnoreCase(estado)) {
	                    System.out.println("El proceso con OID " + oidProceso + " ha finalizado correctamente.");
	                } else {
	                    System.out.println("El proceso con OID " + oidProceso + " está en estado: " + estado);
	                }

	            } catch (SQLException e) {
	                System.err.println("Error al ejecutar el procedimiento almacenado:");
	                e.printStackTrace();
	            } finally {
	                try { if (stmt != null) stmt.close(); } catch (Exception e) { }
	                try { if (con != null) con.close(); } catch (Exception e) { }
	            }
	        }
*/

	    	
	        //String sql = "UPDATE Procesos SET estado = 'En ejecución', fechaInicio = CURRENT_TIMESTAMP WHERE oidProceso = ?";


	 
	}









 
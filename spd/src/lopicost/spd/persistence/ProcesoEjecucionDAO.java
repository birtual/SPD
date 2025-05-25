package lopicost.spd.persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;


import lopicost.config.pool.dbaccess.Conexion;
import lopicost.spd.model.Proceso;
import lopicost.spd.model.ProcesoEjecucion;
import lopicost.spd.utils.SPDConstants;



public class ProcesoEjecucionDAO extends GenericDAO{
	
	
	static String className="ProcesoEjecucionDAO";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat DATE_FORMAT_TIME = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
   
    /**
     * Por defecto se hace la llamada con dos parámetros, oidProcesoEjecucion y codigoResultado (OUT) pero es posible añadir más en la definición del proceso, separado por "|"
     * @param idUsuario
     * @param proceso
     * @return
     * @throws SQLException
     */
    public boolean iniciarEjecucionProceso(String idUsuario, Proceso proceso) throws SQLException {
    	
    	if(proceso==null) return false;
    	if(proceso.getLanzadera()==null) return false;
    	
    	Connection con = null;
    	CallableStatement  stmt = null; 
    	String lanzadera = "";
             try {
            	lanzadera = proceso.getLanzadera();
            	String parametrosExtraSeparados = proceso.getParametros();
 
            	// 1. Parsear los parámetros adicionales (pueden estar vacíos)
                String[] extras = (parametrosExtraSeparados != null && !parametrosExtraSeparados.isEmpty())
                                  ? parametrosExtraSeparados.split("\\|", -1)
                                  : new String[0];
                                  
                int totalParams = 2 + extras.length; // oid + codigoResultado + extras                                 
             // 2. Construir la llamada "{call dbo.procedimiento(?, ?, ..., ?)}"
                StringBuilder sb = new StringBuilder();
                sb.append("{call dbo.").append(lanzadera).append("(");
                for (int i = 0; i < totalParams; i++) {
                    sb.append("?");
                    if (i < totalParams - 1) sb.append(",");
                }
                sb.append(")}"); 
                
            	lanzadera=lanzadera.replace(".sql", "");
            	con=Conexion.conectar();
             	stmt = con.prepareCall(sb.toString());

                    int idx = 1;

                    // 3. Primer parámetro: oidProcesoEjecucion
                    stmt.setInt(idx++, proceso.getEjecucionActiva().getOidProcesoEjecucion());

                    // 4. Segundo parámetro: codigoResultado (OUT)
                    stmt.registerOutParameter(idx++, Types.INTEGER);

                    // 5. Parámetros adicionales
                    for (String p : extras) {
                        if ("NULL".equalsIgnoreCase(p)) {
                            stmt.setNull(idx++, Types.VARCHAR);
                        } else {
                            stmt.setString(idx++, p);                       	
                            System.out.println(" 1 parámetro " + p);
                            
                        }
                    }

                    stmt.execute();

                // 6. Recuperar código de resultado
                int  codigoResultado = stmt.getInt(2); // porque lo registraste en la posición 2
              
                
                 // Llamada al procedimiento almacenado para ejecutar el proceso
            	//stmt = con.prepareCall("{call test1("+proceso.getUltimaEjecucion().getOidProcesoEjecucion()+")}");
            	//stmt = con.prepareCall("{call test1("+proceso.getEjecucionActiva().getOidProcesoEjecucion()+")}");
            	//stmt = con.prepareCall("{call dbo.test1(?, ?)}");
                //lanzadera=lanzadera.replace(".sql", "");
            	//stmt = con.prepareCall("{call dbo."+lanzadera+"(?, ?)}");
                // Parámetro de entrada
            	//stmt.setInt(1, proceso.getEjecucionActiva().getOidProcesoEjecucion());

                // Parámetro de salida
                 // stmt.registerOutParameter(2, Types.INTEGER);
            	  
            	//stmt = con.prepareCall("{call test1(?)}");
                //stmt.setInt(1, proceso.getOidProceso());
               
                System.out.println("Lanzando proceso con oid: " + proceso.getOidProceso() + " - lanzadera: " + proceso.getLanzadera());
             // stmt.execute();
             // Leer el parámetro de salida
             // int  codigoResultado = stmt.getInt(2);

                System.out.println("Código de resultado: " + codigoResultado);
                System.out.println("Proceso lanzado correctamente.");
/*
                // Consultar el estado del proceso después de ejecutar el procedure
                String estado = ProcesoHelper.obtenerEstadoProceso(idUsuario, proceso.getOidProceso());

                // Verificar si el proceso ha finalizado o ha ocurrido un error
                if ("Finalizado".equalsIgnoreCase(estado)) {
                    System.out.println("El proceso con OID " + proceso.getOidProceso() + " ha finalizado correctamente.");
                } else {
                    System.out.println("El proceso con OID " + proceso.getOidProceso() + " está en estado: " + estado);
                }
*/
            } catch (SQLException e) {
                //System.err.println("Error al ejecutar el procedimiento almacenado:");
                //e.printStackTrace();
               /* if (e.getMessage().contains("could not find stored procedure") || e.getMessage().contains("No se encontró el procedimiento almacenado")) {
                    System.out.println("El procedimiento " + lanzadera + " no existe");
                    finalizarProceso(proceso.getEjecucionActiva().getOidProcesoEjecucion(),"FINALIZADO", "ERROR",  "El procedimiento " + lanzadera + " no existe");
                    
                } else {
                    throw e; // relanzamos si es otro error
                }
                */
            	finalizarProceso(proceso.getEjecucionActiva().getOidProcesoEjecucion(),"FINALIZADO", "ERROR", e.getMessage());
            } finally {
                try { if (stmt != null) stmt.close(); } catch (Exception e) { }
                try { if (con != null) con.close(); } catch (Exception e) { }
            }
	    return true;
	}


	public List<ProcesoEjecucion> list(String idUsuario) throws SQLException {
	    return findByFilters(idUsuario, -1, -1, null, -1, null, true) ;
	}

	public List<ProcesoEjecucion> list(String idUsuario, int oidProceso) throws SQLException {
	    return findByFilters(idUsuario, oidProceso, -1, null, -1, null, false) ;
	}
	
	/**
	 *  Estados: .
	 	"PENDIENTE"  aún no ejecutado.
		"EJECUTANDO"  en curso.
		"FINALIZADO_OK"  ejecutado correctamente.
		"ERROR"  fallo durante la ejecución.
		"CANCELADO"  aborto manual o por lógica.
	 * @param idUsuario
	 * @param oidProceso
	 * @param lanzadera
	 * @param version
	 * @param estado
	 * @return
	 * @throws SQLException
	 */
	
	public List<ProcesoEjecucion> findByFilters(String idUsuario, int oidProceso, int oidProcesoEjecucion, String lanzadera, int version, String estado, boolean orderDesc) throws SQLException {
		 List<ProcesoEjecucion> result = new ArrayList<ProcesoEjecucion>();
	     String sql = "SELECT * FROM SPD_procesosEjecucion ";
	     sql+= " WHERE 1 = 1 ";
	
	    if(estado != null && !estado.equals(""))
	       	sql+= " AND estado =  '" + estado + "'";
	    if(lanzadera != null && !lanzadera.equals(""))
	       	sql+= " AND lanzadera =  '" + lanzadera + "'";
        if( oidProceso> 0 )
        	sql+= " AND oidProceso =  '" + oidProceso + "'";
        if( oidProcesoEjecucion> 0 )
        	sql+= " AND oidProcesoEjecucion =  '" + oidProcesoEjecucion + "'";
        if( version> 0 )
        	sql+= " AND version =  '" + version + "'";
        if(orderDesc)
        	sql+= " order by fechaFinEjecucion desc, fechaInicioEjecucion desc ";
        else 
        	sql+= " order by oidProcesoEjecucion desc ";
        
	  		Connection con = Conexion.conectar();
			System.out.println(className + "--> list -->" +sql );		
		    ResultSet resultSet = null;
		    try {
		    	PreparedStatement pstat = con.prepareStatement(sql);
		    	resultSet = pstat.executeQuery();
				ProcesoEjecucion procesoEjecucion =null;

		    	while (resultSet.next()) {
		    		procesoEjecucion = creaProcesoEjecucion(resultSet);
		    		result.add(procesoEjecucion);
	    		 }
		    } catch (SQLException e) {
		    	e.printStackTrace(); 
		    } finally {
		    	con.close();
		    }
		    return result;
		}

	/** ok
	 * Método que devuelve la última ejecución de un proceso
	 * @param idUsuario
	 * @param proceso
	 * @return
	 * @throws SQLException
	 */
	public ProcesoEjecucion obtenerUltimaEjecucion(String idUsuario, Proceso proceso) throws SQLException {
		return obtenerUltimaEjecucion( idUsuario,  proceso, null) ;
	}

	/** ok
	 * Método que devuelve la última ejecución de un proceso
	 * @param idUsuario
	 * @param proceso
	 * @param estado
	 * @return
	 * @throws SQLException
	 */
	public ProcesoEjecucion obtenerUltimaEjecucion(String idUsuario, Proceso proceso, String estado) throws SQLException {
		
		if(proceso ==null) return null;
		ProcesoEjecucion procesoEjecucion =null;
	    String sql = "SELECT TOP 1 *  FROM SPD_procesosEjecucion ";
	    sql+= " WHERE 1 = 1 ";
	
	    if(proceso.getLanzadera() != null && !proceso.getLanzadera().equals(""))
	       	sql+= " AND lanzadera =  '" + proceso.getLanzadera()  + "'";
	    if( proceso.getOidProceso() > 0 )
	    	sql+= " AND oidProceso =  '" + proceso.getOidProceso() + "'";
	    if(estado != null && !estado.equals(""))
	       	sql+= " AND estado =  '" + estado  + "'";
   
		sql+= " ORDER BY fechaCreacionEjecucion desc ";
       //	sql+= " ORDER BY fechaFinEjecucion desc, fechaInicioEjecucion desc ";
	        
	  		Connection con = Conexion.conectar();
			System.out.println(className + "--> list -->" +sql );		
		    ResultSet resultSet = null;
		    try {
		    	PreparedStatement pstat = con.prepareStatement(sql);
		    	resultSet = pstat.executeQuery();
				

		    	while (resultSet.next()) {
		    		procesoEjecucion = creaProcesoEjecucion(resultSet);
		    		
	    		 }
		    } catch (SQLException e) {
		    	e.printStackTrace(); 
		    } finally {
		    	con.close();
		    }
		    return procesoEjecucion;
		}
	
	
	/** ok
	 * Método que se encarga de construir un bean a partir de un registro de proceso de la bbdd
	 * @param rs
	 * @return
	 * @throws SQLException 
	 */
    public ProcesoEjecucion creaProcesoEjecucion(ResultSet rs) throws SQLException {
		 ProcesoEjecucion procesoEjecucion = new ProcesoEjecucion();
		 procesoEjecucion.setOidProcesoEjecucion(rs.getInt("oidProcesoEjecucion"));
		 procesoEjecucion.setOidProceso(rs.getInt("oidProceso"));
		 procesoEjecucion.setLanzadera(rs.getString("lanzadera"));
		 procesoEjecucion.setVersion(rs.getInt("version"));
			Date dFechaCreacion  = rs.getTimestamp("fechaCreacionEjecucion");
			String fechaCreacion =  DATE_FORMAT_TIME.format(dFechaCreacion);
		 procesoEjecucion.setFechaCreacionEjecucion(fechaCreacion);

			Date dFechaInicioEjecucion  = rs.getTimestamp("fechaInicioEjecucion");
			String fechaInicioEjecucion =  DATE_FORMAT_TIME.format(dFechaInicioEjecucion);
		 procesoEjecucion.setFechaInicioEjecucion(fechaInicioEjecucion);
		 String fechaFinEjecucion = null;
		 try{
			 Date dFechaFinEjecucion  = rs.getTimestamp("fechaFinEjecucion");
			 fechaFinEjecucion =  DATE_FORMAT_TIME.format(dFechaFinEjecucion);
		 }catch(Exception e){
		 }
		 procesoEjecucion.setFechaFinEjecucion(fechaFinEjecucion);
		 procesoEjecucion.setEstado(rs.getString("estado"));
		 int duracionSegundos = rs.getInt("duracionSegundos");
		 if (rs.wasNull()) procesoEjecucion.setDuracionSegundos(SPDConstants.MAX_DURACIONSEGUNDOS_PROCESO); //si no hay límite de segundos ponemos uno
		    else procesoEjecucion.setDuracionSegundos(duracionSegundos);
		 
		 procesoEjecucion.setResultado(rs.getString("resultado"));
		 procesoEjecucion.setUsuarioEjecucion(rs.getString("usuarioEjecucion"));
		 procesoEjecucion.setMensaje(rs.getString("mensaje"));
		 procesoEjecucion.setTipoError(rs.getString("tipoError"));
		 procesoEjecucion.setCodigoResultado(rs.getString("codigoResultado"));
		 procesoEjecucion.setError(rs.getString("error"));

	
		 return procesoEjecucion;


	}



    	
        //String sql = "UPDATE Procesos SET estado = 'En ejecución', fechaInicio = CURRENT_TIMESTAMP WHERE oidProceso = ?";


    public void finalizarProceso(int oidProceso, String estado, String resultado, String mensaje) {
        String sql = "UPDATE SPD_procesosEjecucion SET fechaFinEjecucion = CURRENT_TIMESTAMP, estado = ?, resultado = ?, mensaje = ? WHERE oidProcesoEjecucion = ?";
        Connection con = null;
    	PreparedStatement stmt = null; 
        try { con = Conexion.conectar();
             stmt = con.prepareStatement(sql);
            stmt.setString(1, estado);
            stmt.setString(2, resultado);
            stmt.setString(3, mensaje);
            stmt.setInt(4, oidProceso);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Obtener todos los procesos
    public List<ProcesoEjecucion> obtenerTodos() throws SQLException {
        List<ProcesoEjecucion> procesos = new ArrayList<>();
        String sql = "SELECT * FROM SPD_procesos";

        return procesos;
    }

    // Actualizar un proceso
    public void actualizarProceso(ProcesoEjecucion p) throws SQLException {
        String sql = "UPDATE SPD_procesos SET nombreProceso = ?, descripcion = ?, estado = ?, parametros = ?, fechaCreacion = ?, " +
                    " .... WHERE oidProceso = ?";

    }



	public void detenerProcesoEjecucion(String idUsuario, int oidProceso) {
		// TODO Esbozo de método generado automáticamente
		
	}

	public void reiniciarProcesoEjecucion(int oidProceso) {
		// TODO Esbozo de método generado automáticamente
		
	}

	 // Método para consultar el estado de ejecución de un proceso
    private String obtenerEstadoProcesoEjecucion(int oidProcesoEjecucion) {
        String sql = "SELECT estado FROM SPD_procesos WHERE oidProceso = ?";
        Connection con = null;
    	PreparedStatement stmt = null; 
        try { con = Conexion.conectar();
             stmt = con.prepareStatement(sql);
             
             stmt.setInt(1, oidProcesoEjecucion);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("estado");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    

    // Método para verificar si el procedimiento almacenado está en ejecución
    public boolean estaEnEjecucion(int oidProcesoEjecucion) {
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

	public void actualizarEstadoProceso(int oidProceso, String string) {
		// TODO Esbozo de método generado automáticamente
		
	}
	
	
	   
	public boolean nuevo(String idUsuario, Proceso proceso) throws SQLException {
			int filas =0;
	       //if(proceso==null || proceso.getUltimaEjecucion()==null) return false;
	       if(proceso==null || proceso.getEjecucionActiva()==null) return false;
	    	   
	       //ProcesoEjecucion ejec = proceso.getUltimaEjecucion();
	       ProcesoEjecucion ejec = proceso.getEjecucionActiva();
	       
	       SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			  Connection con = Conexion.conectar();
			  String sql = "INSERT INTO SPD_ProcesosEjecucion (" +
						        "oidProceso, lanzadera, version, " + 
						        "estado,  " +
						        "usuarioEjecucion, numIntentos " +
						        ") ";
			  String parametros = sql;

			  // Añadimos para sustituir
			  sql +=  "VALUES (?, ?, ?, ?, ?, ?)";
			  
				//PreparedStatement ps = con.prepareStatement(sql);
			    PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				 
			 //  concatenamos los valores y en paralelo construimos query para consulta
			  	ps.setInt(1, ejec.getOidProceso());	
			  	parametros += "VALUES ( '" + ejec.getOidProceso() + "'";  //oidProceso

			  	ps.setString(2, ejec.getLanzadera());	
			  	parametros += ", '" + ejec.getLanzadera() + "'"; // lanzadera

			  	ps.setInt(3, ejec.getVersion());	
			  	parametros += ", '" + ejec.getVersion() + "'"; // version

			  	//Inicio Estado
			  	String estado =SPDConstants.PROCESO_EJEC_ESTADO_PENDIENTE;
			  	if(proceso.getActivo()!=null && proceso.getActivo().equalsIgnoreCase(SPDConstants.PROCESO_ACTIVO))
			  		estado =SPDConstants.PROCESO_EJEC_ESTADO_EJECUTANDO;
			  	ps.setString(4, estado);	
			  	parametros += ", '" + estado + "'"; // estado
			  	//Fin Estado

			  	ps.setString(5, idUsuario);	
			  	parametros += ", '" + idUsuario + "'"; // usuarioEjecucion

			  	
			  	ps.setInt(6, ejec.getNumIntentos());	
			  	parametros += ", '" + ejec.getNumIntentos() + "'"; // numIntentos

			  	// Cerramos la consulta
			  	parametros += ")";

			  	// imprimir el string generado
			  	System.out.println("Consulta SQL - nuevo--> " + parametros);

			    	
		    try {
		    	filas = ps.executeUpdate();
		    	if (filas > 0) {
		    	    ResultSet rs = ps.getGeneratedKeys();
		    	    if (rs.next()) {
		    	        int oidGenerado = rs.getInt(1);
		    	        ejec.setOidProcesoEjecucion(oidGenerado); // asignación al modelo Java
		    	    }
		    	    
		    	//result=ps.executeUpdate();
		    	    rs.close();
		    	}
		    	 
		     } catch (SQLException e) {
		         e.printStackTrace();
		     } finally {ps.close(); con.close();}
			
			return filas>0;
		}


	public void resetearProcesosEnEjecucion() throws SQLException {
		System.out.println(" Limpieza de procesos");
		Connection con = null;
		con = Conexion.conectar();
		    String sql = "UPDATE SPD_procesos SET estado = 'No iniciado' WHERE estado = '"+SPDConstants.PROCESO_EJEC_ESTADO_EJECUTANDO+"'";
		    try (PreparedStatement ps = con.prepareStatement(sql)) {
		        ps.executeUpdate();
		    }
		}

	/**
	 * Método que se encarga de actualizar el estado de una ejecución proceso según el nuevoEstado pasado por parámetro
	 * @param proceso
	 * @param nuevoEstado
	 * @return
	 * @throws SQLException
	
	public  boolean actualizarEstadoEjecucion(Proceso proceso, String nuevoEstado) throws SQLException {

		if(proceso==null || proceso.getUltimaEjecucion()==null)
			return false;
		
		ProcesoEjecucion ejec = proceso.getUltimaEjecucion();

		System.out.println(" Actualización de estado de ejecución ");
		int result=0;
		Connection con = null;
		con = Conexion.conectar();
		    String sql = "UPDATE SPD_procesosEjecucion SET estado = '"+nuevoEstado+"' WHERE oidProcesoEjecucion='"+ejec.getOidProcesoEjecucion()+"'";
		    try (PreparedStatement ps = con.prepareStatement(sql)) {
		        result = ps.executeUpdate();
		    }
			return result>0;
		}
*/
	public List<ProcesoEjecucion> obtenerEjecucionesEnCurso() {
	    String sql = "SELECT * FROM SPD_procesos_ejecuciones WHERE estado = '"+SPDConstants.PROCESO_EJEC_ESTADO_EJECUTANDO+"'";
	    // Mapear a objetos ProcesoEjecucion, con su Proceso incluido si necesario
		return null;
	}

	/** ok
	 * Método encargado de actualizar un procesoEjecucion según la información que llega en el objeto
	 * @param ejec
	 * @return
	 * @throws SQLException
	 */

	public  boolean actualizarEjecucion(ProcesoEjecucion ejec) throws SQLException {
		if(ejec==null)
			return false;
		System.out.println(" Actualización de ejecución ");
		int result=0;
		Connection con = null;
		con = Conexion.conectar();
		    String sql = "UPDATE SPD_procesosEjecucion SET ";
		    		sql+= " estado = '"+ejec.getEstado()+"' ";
		    		sql+= ", fechaFinEjecucion = '"+ejec.getFechaFinEjecucion()+"' ";  
		    		sql+= ", duracionSegundos  = '"+ejec.getDuracionSegundos()+"' ";
		    		sql+= ", mensaje  = '"+ejec.getMensaje()+"' ";
		    		sql+= ", codigoResultado  = '"+ejec.getCodigoResultado()+"' ";
		    		sql+= ", resultado  = '"+ejec.getResultado()+"' ";
		    		sql+= ", tipoError  = '"+ejec.getTipoError()+"' ";
		    		sql+= "  WHERE oidProcesoEjecucion='"+ejec.getOidProcesoEjecucion()+"'";
		    try (PreparedStatement ps = con.prepareStatement(sql)) {
		        result = ps.executeUpdate();
		    }
			return result>0;
		}

	/** ok
	 * Método encargado de contar los errores de un mismo proceso, a partir de la fecha del último proceso OK  
	 * @param proceso
	 * @return
	 * @throws SQLException
	 */
	public int contarErroresDesdeUltimoExito(Proceso proceso) throws SQLException {
	    ProcesoEjecucion ultimaOk = obtenerUltimaEjecucion(null, proceso, SPDConstants.PROCESO_EJEC_ESTADO_FINALIZADO);
	    String fechaDesde = ultimaOk != null ? ultimaOk.getFechaInicioEjecucion() : "01/01/1900";
	    int posteriores = contarPosterioresAFecha(proceso, fechaDesde);
		return posteriores;
	    
	}

	public int contarPosterioresAFecha(Proceso proceso, String fechaDesde) throws SQLException {
		
		if(proceso ==null) return 0;
	    String sql = "SELECT COUNT(*) as cuantas  FROM SPD_procesosEjecucion ";
	    	sql+= " WHERE 1 = 1 ";
	    	sql+= " AND estado = '"+SPDConstants.PROCESO_EJEC_ESTADO_EJECUTANDO+"'";
	    	sql+= " AND lanzadera = '"+proceso.getLanzadera()+"'";
	    	sql+= " AND fechaInicioEjecucion  >= '"+fechaDesde+"'";

	    	Connection con = Conexion.conectar();
			System.out.println(className + "--> contarPosterioresAFecha" +sql );		
		    ResultSet resultSet = null;
		 	 	
		    int result =0;
			try {
				PreparedStatement pstat = con.prepareStatement(sql);
			    resultSet = pstat.executeQuery();
			    resultSet.next();
			    result = resultSet.getInt("cuantas");
			   } catch (SQLException e) {
			       e.printStackTrace();
			  }finally {con.close();}
	   return result;
	}


	public void limpiarOtrasEjecucionesNoCerradas(Proceso proceso) throws SQLException {
	    //if(proceso == null || proceso.getUltimaEjecucion() == null) return;
		if(proceso == null) return;
		
	     String sql = "UPDATE SPD_procesosEjecucion ";
	     sql+= " SET estado = '"+SPDConstants.PROCESO_EJEC_ESTADO_CANCELADO+"', ";
	     sql+= " mensaje = 'Cancelado Por limpieza de registros antiguos', ";
	     sql+= " tipoError = 'limpieza', ";
	     sql+= " codigoResultado = '"+SPDConstants.PROCESO_CODE_CANCELADO+"', ";
	     sql+= " resultado = '"+SPDConstants.PROCESO_EJEC_RESULT_CANCEL +"'  ";
    	 sql+= " WHERE lanzadera = '" + proceso.getLanzadera() + "'";
	     sql+= " AND estado <> '"+SPDConstants.PROCESO_EJEC_ESTADO_FINALIZADO+"' ";

    	 //if(proceso.getUltimaEjecucion()!=null)
		 //sql+= " AND oidProcesoEjecucion <>  '" + proceso.getUltimaEjecucion().getOidProcesoEjecucion() + "'";
	     if(proceso.getEjecucionActiva()!=null)
    		 sql+= " AND oidProcesoEjecucion <>  '" + proceso.getEjecucionActiva().getOidProcesoEjecucion() + "'";
 
    	 Connection con = Conexion.conectar();
		 System.out.println(className + "--> limpiarOtrasEjecucionesNoCerradas -->" +sql );		
		 try {
			 PreparedStatement pstat = con.prepareStatement(sql);
			 pstat.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				
			} finally {con.close();
			}
		}


    
	}









 
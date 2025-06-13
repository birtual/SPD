package lopicost.spd.persistence;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


import lopicost.config.pool.dbaccess.Conexion;
import lopicost.spd.model.Proceso;
import lopicost.spd.model.ProcesoBloqueoHorario;


import java.sql.*;

public class ProcesoBloqueoHorarioDAO extends GenericDAO  {
	
	  private Connection connection;
	  private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	    public ProcesoBloqueoHorarioDAO() {
	    	
	    }

	  
	        public List<ProcesoBloqueoHorario> listar() throws SQLException {
	        	connection = Conexion.conectar();
	        	List<ProcesoBloqueoHorario> lista = new ArrayList<>();
			            Statement stmt = connection.createStatement();
			    	    String sql = "	SELECT ";
			            sql+=" CASE WHEN     ";
			            sql+=" ( ( r.horasDesde <= CONVERT(VARCHAR(5), GETDATE(), 108) AND r.horasHasta >= CONVERT(VARCHAR(5), GETDATE(), 108)) ";
			            sql+="  OR ";
			            sql+=" r.valorDia = DAY(GETDATE()) "; 
			            sql+=" OR r.valorFecha = CONVERT(VARCHAR(10), GETDATE(), 103)) ";
			            sql+=" AND r.usarBloqueoHorario='1' ";
			            sql+=" THEN ";
			            sql+=" 'SI' ";
			            sql+=" ELSE ";
			            sql+=" 'NO' ";
			            sql+=" END as bloqueaAhora ";
	    	            sql+=" ,  r.*, p.lanzadera, p.parametros, f.nombreFarmacia ";
	    	            sql+=" FROM SPD_procesosBloqueosHorarios r ";   
	            		sql+=" LEFT JOIN SPD_procesos p on p.oidProceso = r.oidProceso ";
	            		sql+=" LEFT JOIN bd_farmacia f on f.idFarmacia= r.idFarmacia ";
	    	            sql+=" WHERE 1=1   ";
	    	            sql+=" order by  usarBloqueoHorario desc  ";
	    	         
	            
	            ResultSet rs = stmt.executeQuery(sql);
	            while (rs.next()) {
	                ProcesoBloqueoHorario p = crearDesdeResultSet(rs);
	                lista.add(p);
	            }
	            rs.close();
	            stmt.close();
	            return lista;
	        }

	        public ProcesoBloqueoHorario obtenerPorOid(int oidBloqueoHorario) throws SQLException {
	        	connection = Conexion.conectar();
	    	    String sql = "	SELECT ";
	            sql+=" CASE WHEN     ";
	            sql+=" ( ( r.horasDesde <= CONVERT(VARCHAR(5), GETDATE(), 108) AND r.horasHasta >= CONVERT(VARCHAR(5), GETDATE(), 108)) ";
	            sql+="  OR ";
	            sql+=" r.valorDia = DAY(GETDATE()) "; 
	            sql+=" OR r.valorFecha = CONVERT(VARCHAR(10), GETDATE(), 103)) ";
	            sql+=" AND r.usarBloqueoHorario='1' ";
	            sql+=" THEN ";
	            sql+=" 'SI' ";
	            sql+=" ELSE ";
	            sql+=" 'NO'  ";
	            sql+=" END as bloqueaAhora ";
	            sql+=" ,  r.*, p.lanzadera, p.parametros, f.nombreFarmacia ";
	            sql+=" FROM SPD_procesosBloqueosHorarios r  ";
        		sql+=" LEFT JOIN SPD_procesos p on p.oidProceso = r.oidProceso  ";
        		sql+=" LEFT JOIN bd_farmacia f on f.idFarmacia= r.idFarmacia ";
        		sql+=" WHERE oidBloqueoHorario = ? ";
        		
        		
	            PreparedStatement ps = connection.prepareStatement(sql);
	            ps.setInt(1, oidBloqueoHorario);
	            ResultSet rs = ps.executeQuery();
	            ProcesoBloqueoHorario p = null;
	            if (rs.next()) {
	                p = crearDesdeResultSet(rs);
	            }
	            rs.close();
	            ps.close();
	            return p;
	        }

	        /**
	         * Devuelve el número de bloqueos con la fecha actual según fechaDesde y fechaHasta   
	         * @param proceso
	         * @param count 
	         * @return
	         * @throws SQLException
	         */
			public int countBloqueosPorTipo(Proceso proceso, String bloqueo, boolean count) throws SQLException {
				int result = 0;
	        	connection = Conexion.conectar();
	            String sql = getQueryBloqueosPorTipo(proceso, bloqueo, count);
	            		
	            PreparedStatement ps = connection.prepareStatement(sql);
	            ps.setInt(1, proceso.getOidProceso());
	            
	            ResultSet rs = ps.executeQuery();
	            if (rs.next()) {
	            	result = rs.getInt("quants");
	            }
	            rs.close();
	            ps.close();
				return result;
			}
			
			/**
	         * Devuelve el listadode bloqueos con la fecha actual según fechaDesde y fechaHasta   
	         * @param proceso
	         * @param count 
	         * @return
	         * @throws SQLException
	         */
			public List<ProcesoBloqueoHorario> bloqueosPorTipo(Proceso proceso, String bloqueo, boolean count) throws SQLException {
				List<ProcesoBloqueoHorario> result = new ArrayList<ProcesoBloqueoHorario>();
	        	connection = Conexion.conectar();
	            String sql = getQueryBloqueosPorTipo(proceso, bloqueo, count);
	            		
	            PreparedStatement ps = connection.prepareStatement(sql);
	            ps.setInt(1, proceso.getOidProceso());
	            ResultSet rs = ps.executeQuery();
	            ProcesoBloqueoHorario p = null;
	            if (rs.next()) {
	                if(!count)
	                {
	                	p = crearDesdeResultSet(rs);
	                	result.add(p);
	                }
	            }
	            rs.close();
	            ps.close();
				return result;
			}
			
	        /**
	         * Devuelve la query para trabajar con el número de bloqueos o el contenido de ellas, según el count true o false   
	         * @param proceso
	         * @param count 
	         * @return
	         * @throws SQLException
	         */
			public String getQueryBloqueosPorTipo(Proceso proceso, String bloqueo, boolean count) throws SQLException {
	    	    String sql = "	SELECT ";
	            sql+=" CASE WHEN     ";
	            sql+=" ( ( r.horasDesde <= CONVERT(VARCHAR(5), GETDATE(), 108) AND r.horasHasta >= CONVERT(VARCHAR(5), GETDATE(), 108)) ";
	            sql+="  OR ";
	            sql+=" r.valorDia = DAY(GETDATE()) "; 
	            sql+=" OR r.valorFecha = CONVERT(VARCHAR(10), GETDATE(), 103)) ";
	            sql+=" AND r.usarBloqueoHorario='1' ";
	            sql+=" THEN ";
	            sql+=" 'SI' ";
	            sql+=" ELSE ";
	            sql+=" 'NO'  ";
	            sql+=" END as bloqueaAhora ";
	            sql+=" ,  r.*, p.lanzadera, p.parametros, f.nombreFarmacia ";
	            
	            if(count)
	            	sql = "	SELECT count(*) as quants ";
	            
	            sql+=" FROM SPD_procesosBloqueosHorarios r  ";
	            
	            if(!count)
	            {
	        		sql+=" LEFT JOIN SPD_procesos p on p.oidProceso = r.oidProceso  ";
	        		sql+=" LEFT JOIN bd_farmacia f on f.idFarmacia= r.idFarmacia ";
	            }

	            sql+=" WHERE 1=1 ";

	            if(count)	//estamos mirando cuantos bloqueos hay
	            	sql+=" AND r.usarBloqueoHorario='1' ";

	            if(proceso!=null && proceso.getOidProceso()>0)
	            	sql+=" AND r.oidProceso = ?  ";
        		
	            
	            switch (bloqueo) {
				case "HORA":
	        		sql+=" AND r.horasDesde <= CONVERT(VARCHAR(5), GETDATE(), 108) ";
	   				sql+=" AND r.horasHasta >= CONVERT(VARCHAR(5), GETDATE(), 108) ";

					break;
				case "DIA":
	        		sql+=" AND r.valorDia = DAY(GETDATE()) ";
					break;
				case "FECHA":
	        		sql+=" AND r.valorFecha = CONVERT(VARCHAR(10), GETDATE(), 103) ";

					break;

				default:
					break;
				}
				return sql;
			}
			
			
		
			
			
	        public boolean insertar(String idUsuario, ProcesoBloqueoHorario p) throws Exception {
	        	connection = Conexion.conectar();
	      	  	sdf.setLenient(false); // Para evitar que acepte fechas como 32/01/2024
	        	
	            PreparedStatement ps = connection.prepareStatement("INSERT INTO SPD_procesosBloqueosHorarios (oidProceso, idFarmacia, tipoBloqueoHorario, horasDesde, horasHasta, valorDia, valorFecha, descripcion, usarBloqueoHorario) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
	            if (p.getOidProceso() != null) ps.setInt(1, p.getOidProceso()); else ps.setNull(1, Types.INTEGER);
	            ps.setString(2, p.getIdFarmacia());
	            /*	            if (p.getIdFarmacia() != null) 
	            {
	            	ps.setString(2, p.getIdFarmacia());
	            	
	            	Farmacia farm = FarmaciaDAO.getFarmaciaPorUserAndOid(idUsuario, p.getOidFarmacia());
	            	if(farm!=null){
	            		ps.setString(3, farm.getIdFarmacia());
	            		ps.setString(4, farm.getNombreFarmacia());
	            	}
	            }
	            else {
	            	ps.setNull(2, Types.VARCHAR);
	            	//ps.setNull(3, Types.VARCHAR);
	            	//ps.setNull(4, Types.VARCHAR);
	            }
	*/          
	            ps.setString(3, p.getTipoBloqueoHorario());
	            String horasDesde="";
	            String horasHasta="";
	            int valorDia=0;
	            String valorFecha="";
	            
	            switch (p.getTipoBloqueoHorario()) {
	            	case "HORA":
	            		horasDesde = p.getHorasDesde();
	    	            horasHasta = p.getHorasHasta();
	    	        break;
	            	case "DIA":
	            		valorDia = p.getValorDia();
					break;
	            	case "FECHA":
	            		valorFecha = p.getValorFecha();
					break;

				default:
					break;
				}
	            ps.setString(4, horasDesde);
	            ps.setString(5, horasHasta);
	            ps.setInt(6, valorDia);
	            ps.setString(7, valorFecha);
	            
	            //if (p.getValorDia() != null) ps.setInt(6, p.getValorDia()); else ps.setNull(6, Types.INTEGER);
	            
	            //if (p.getValorFecha() != null) ps.setDate(6, new java.sql.Date(p.getValorFecha().getTime())); else ps.setNull(6, Types.DATE);
	            /*try {
	                sdf.parse(p.getValorFecha().trim()); // Validación
	                ps.setString(7, p.getValorFecha().trim());
	            } catch (ParseException e) {
	                // Fecha inválida, puedes registrar un error o lanzar una excepción
	                ps.setNull(7, Types.VARCHAR);
	                e.printStackTrace();
	            }
	            */
	            ps.setString(8, p.getDescripcion());
	            ps.setBoolean(9, p.isUsarBloqueoHorario());
	            int count = ps.executeUpdate();
	            ps.close();
	            return count>0;
	        }

	        public boolean actualizar(String idUsuario, ProcesoBloqueoHorario p) throws Exception {
	        	
	        	if(p.getOidBloqueoHorario()<=0) 
	      	  	{
	      	  		return insertar(idUsuario, p);
	      	  		
	      	  	}	        	
	      	  	connection = Conexion.conectar();
	      	  	sdf.setLenient(false); // Para evitar que acepte fechas como 32/01/2024

		      	  	PreparedStatement ps = connection.prepareStatement("UPDATE SPD_procesosBloqueosHorarios SET oidProceso=?, idFarmacia=?, tipoBloqueoHorario=?, horasDesde=?, horasHasta=?, valorDia=?, valorFecha=?, descripcion=?, usarBloqueoHorario=? WHERE oidBloqueoHorario=?");
	            if (p.getOidProceso() != null) ps.setInt(1, p.getOidProceso()); else ps.setNull(1, Types.INTEGER);
	            ps.setString(2, p.getIdFarmacia());
	            //if (p.getIdFarmacia() != null) ps.setInt(2, p.getOidFarmacia()); else ps.setNull(2, Types.INTEGER);
	          //  if (p.getIdFarmacia() != null) ps.setString(3, p.getIdFarmacia()); else ps.setNull(3, Types.VARCHAR);
	          //  if (p.getNombreFarmacia() != null) ps.setString(3, p.getNombreFarmacia()); else ps.setNull(4, Types.VARCHAR);
	            
	            ps.setString(3, p.getTipoBloqueoHorario());
	            ps.setString(4, p.getHorasDesde());
	            ps.setString(5, p.getHorasHasta());
	            if (p.getValorDia() != null) ps.setInt(6, p.getValorDia()); else ps.setNull(6, Types.INTEGER);
	            //if (p.getValorFecha() != null) ps.setDate(6, new java.sql.Date(p.getValorFecha().getTime())); else ps.setNull(6, Types.DATE);
	            try {
	                sdf.parse(p.getValorFecha().trim()); // Validación
	                ps.setString(7, p.getValorFecha().trim());
	            } catch (ParseException e) {
	                // Fecha inválida, puedes registrar un error o lanzar una excepción
	                ps.setNull(7, Types.VARCHAR);
	                e.printStackTrace();
	            }
	            ps.setString(8, p.getDescripcion());
	            ps.setBoolean(9, p.isUsarBloqueoHorario());
	            ps.setInt(10, p.getOidBloqueoHorario());
	            int count  = ps.executeUpdate();
	            ps.close();
	            return count>0;
	        }

	        public boolean borrar(int oidBloqueoHorario) throws SQLException {
	        	connection = Conexion.conectar();
	            PreparedStatement ps = connection.prepareStatement("DELETE FROM SPD_procesosBloqueosHorarios WHERE oidBloqueoHorario=?");
	            ps.setInt(1, oidBloqueoHorario);
	            int result = ps.executeUpdate();
	            ps.close();
	            return result>0;
	        }

	        private ProcesoBloqueoHorario crearDesdeResultSet(ResultSet rs) throws SQLException {
	            ProcesoBloqueoHorario p = new ProcesoBloqueoHorario();
	            p.setOidBloqueoHorario(rs.getInt("oidBloqueoHorario"));
	            p.setOidProceso((Integer) rs.getObject("oidProceso"));
	            p.setIdFarmacia(rs.getString("idFarmacia"));
	            p.setNombreFarmacia(rs.getString("nombreFarmacia"));
	            p.setLanzadera(rs.getString("lanzadera"));
	            p.setParametros(rs.getString("parametros"));
	            p.setTipoBloqueoHorario(rs.getString("tipoBloqueoHorario"));
	            p.setHorasDesde(rs.getString("horasDesde"));
	            p.setHorasHasta(rs.getString("horasHasta"));
	            p.setValorDia((Integer) rs.getObject("valorDia"));
	            p.setValorFecha(rs.getString("valorFecha"));
	            p.setDescripcion(rs.getString("descripcion"));
	            p.setUsarBloqueoHorario(rs.getBoolean("usarBloqueoHorario"));
	            try
	            {
	            	p.setBloqueaAhora(rs.getString("bloqueaAhora"));
	            }catch(Exception e){}
/*	            
            	ProcesoDAO dao = new ProcesoDAO();
            	Proceso proceso = dao.findByOidProceso(null, p.getOidProceso());
            	ProcesoBloqueosHorariosHelper helper = new ProcesoBloqueosHorariosHelper(); 
            	p.setBloqueoEnCurso(helper.hayBloqueoHorario(proceso));
*/
            	return p;
	        }


	    }

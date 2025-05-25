package lopicost.spd.persistence;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import lopicost.config.pool.dbaccess.Conexion;
import lopicost.spd.model.Farmacia;
import lopicost.spd.model.Proceso;
import lopicost.spd.model.ProcesoRestricciones;
import lopicost.spd.struts.helper.ControladorProcesosHelper;
import lopicost.spd.struts.helper.ProcesoRestriccionesHelper;

import java.sql.*;

import javax.sql.DataSource;
public class ProcesoRestriccionesDAO extends GenericDAO  {
	
	  private Connection connection;
	  private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	    public ProcesoRestriccionesDAO() {
	    	
	    }

	  
	        public List<ProcesoRestricciones> listar() throws SQLException {
	        	connection = Conexion.conectar();
	        	List<ProcesoRestricciones> lista = new ArrayList<>();
			            Statement stmt = connection.createStatement();
			    	    String sql = "	SELECT ";
			            sql+=" CASE WHEN     ";
			            sql+=" ( ( r.horasDesde <= CONVERT(VARCHAR(5), GETDATE(), 108) AND r.horasHasta >= CONVERT(VARCHAR(5), GETDATE(), 108)) ";
			            sql+="  OR ";
			            sql+=" r.valorDia = DAY(GETDATE()) "; 
			            sql+=" OR r.valorFecha = CONVERT(VARCHAR(10), GETDATE(), 103)) ";
			            sql+=" AND r.usarRestriccion='1' ";
			            sql+=" THEN ";
			            sql+=" 'SI' ";
			            sql+=" ELSE ";
			            sql+=" 'NO'  ";
			            sql+=" END as bloqueaAhora ";
	    	            sql+=" ,  r.*, p.lanzadera, f.nombreFarmacia ";
	    	            sql+=" FROM SPD_procesosRestricciones r ";   
	            		sql+=" LEFT JOIN SPD_procesos p on p.oidProceso = r.oidProceso ";
	            		sql+=" LEFT JOIN bd_farmacia f on f.idFarmacia= r.idFarmacia ";
	    	            sql+=" WHERE 1=1   ";
	    	            sql+=" order by  usarRestriccion desc  ";
	    	         
	            
	            ResultSet rs = stmt.executeQuery(sql);
	            while (rs.next()) {
	                ProcesoRestricciones p = crearDesdeResultSet(rs);
	                lista.add(p);
	            }
	            rs.close();
	            stmt.close();
	            return lista;
	        }

	        public ProcesoRestricciones obtenerPorOid(int oidRestriccion) throws SQLException {
	        	connection = Conexion.conectar();
	    	    String sql = "	SELECT ";
	            sql+=" CASE WHEN     ";
	            sql+=" ( ( r.horasDesde <= CONVERT(VARCHAR(5), GETDATE(), 108) AND r.horasHasta >= CONVERT(VARCHAR(5), GETDATE(), 108)) ";
	            sql+="  OR ";
	            sql+=" r.valorDia = DAY(GETDATE()) "; 
	            sql+=" OR r.valorFecha = CONVERT(VARCHAR(10), GETDATE(), 103)) ";
	            sql+=" AND r.usarRestriccion='1' ";
	            sql+=" THEN ";
	            sql+=" 'SI' ";
	            sql+=" ELSE ";
	            sql+=" 'NO'  ";
	            sql+=" END as bloqueaAhora ";
	            sql+=" ,  r.*, p.lanzadera, f.nombreFarmacia ";
	            sql+=" FROM SPD_procesosRestricciones r  ";
        		sql+=" LEFT JOIN SPD_procesos p on p.oidProceso = r.oidProceso  ";
        		sql+=" LEFT JOIN bd_farmacia f on f.idFarmacia= r.idFarmacia ";
        		sql+=" WHERE oidRestriccion = ? ";
        		
        		
	            PreparedStatement ps = connection.prepareStatement(sql);
	            ps.setInt(1, oidRestriccion);
	            ResultSet rs = ps.executeQuery();
	            ProcesoRestricciones p = null;
	            if (rs.next()) {
	                p = crearDesdeResultSet(rs);
	            }
	            rs.close();
	            ps.close();
	            return p;
	        }

	        /**
	         * Devuelve el número de restricciones con la fecha actual según fechaDesde y fechaHasta   
	         * @param proceso
	         * @param count 
	         * @return
	         * @throws SQLException
	         */
			public int countRestriccionesPorTipo(Proceso proceso, String restriccion, boolean count) throws SQLException {
				int result = 0;
	        	connection = Conexion.conectar();
	            String sql = getQueryRestriccionesPorTipo(proceso, restriccion, count);
	            		
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
	         * Devuelve el listadode restricciones con la fecha actual según fechaDesde y fechaHasta   
	         * @param proceso
	         * @param count 
	         * @return
	         * @throws SQLException
	         */
			public List<ProcesoRestricciones> restriccionesPorTipo(Proceso proceso, String restriccion, boolean count) throws SQLException {
				List<ProcesoRestricciones> result = new ArrayList<ProcesoRestricciones>();
	        	connection = Conexion.conectar();
	            String sql = getQueryRestriccionesPorTipo(proceso, restriccion, count);
	            		
	            PreparedStatement ps = connection.prepareStatement(sql);
	            ps.setInt(1, proceso.getOidProceso());
	            ResultSet rs = ps.executeQuery();
	            ProcesoRestricciones p = null;
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
	         * Devuelve la query para trabajar con el número de restricciones o el contenido de ellas, según el count true o false   
	         * @param proceso
	         * @param count 
	         * @return
	         * @throws SQLException
	         */
			public String getQueryRestriccionesPorTipo(Proceso proceso, String restriccion, boolean count) throws SQLException {
	    	    String sql = "	SELECT ";
	            sql+=" CASE WHEN     ";
	            sql+=" ( ( r.horasDesde <= CONVERT(VARCHAR(5), GETDATE(), 108) AND r.horasHasta >= CONVERT(VARCHAR(5), GETDATE(), 108)) ";
	            sql+="  OR ";
	            sql+=" r.valorDia = DAY(GETDATE()) "; 
	            sql+=" OR r.valorFecha = CONVERT(VARCHAR(10), GETDATE(), 103)) ";
	            sql+=" AND r.usarRestriccion='1' ";
	            sql+=" THEN ";
	            sql+=" 'SI' ";
	            sql+=" ELSE ";
	            sql+=" 'NO'  ";
	            sql+=" END as bloqueaAhora ";
	            sql+=" ,  r.*, p.lanzadera, f.nombreFarmacia ";
	            
	            if(count)
	            	sql = "	SELECT count(*) as quants ";
	            
	            sql+=" FROM SPD_procesosRestricciones r  ";
	            
	            if(!count)
	            {
	        		sql+=" LEFT JOIN SPD_procesos p on p.oidProceso = r.oidProceso  ";
	        		sql+=" LEFT JOIN bd_farmacia f on f.idFarmacia= r.idFarmacia ";
	            }

	            sql+=" WHERE 1=1 ";
	            
	            if(proceso!=null && proceso.getOidProceso()>0)
	            	sql+=" AND r.oidProceso = ?  ";
        		
	            
	            switch (restriccion) {
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
			
			
		
			
			
	        public boolean insertar(String idUsuario, ProcesoRestricciones p) throws Exception {
	        	connection = Conexion.conectar();
	      	  	sdf.setLenient(false); // Para evitar que acepte fechas como 32/01/2024
	        	
	            PreparedStatement ps = connection.prepareStatement("INSERT INTO SPD_procesosRestricciones (oidProceso, idFarmacia, tipoRestriccion, horasDesde, horasHasta, valorDia, valorFecha, descripcion, usarRestriccion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
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
	            ps.setString(3, p.getTipoRestriccion());
	            String horasDesde="";
	            String horasHasta="";
	            int valorDia=0;
	            String valorFecha="";
	            
	            switch (p.getTipoRestriccion()) {
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
	            ps.setBoolean(9, p.isUsarRestriccion());
	            int count = ps.executeUpdate();
	            ps.close();
	            return count>0;
	        }

	        public boolean actualizar(String idUsuario, ProcesoRestricciones p) throws Exception {
	        	
	        	if(p.getOidRestriccion()<=0) 
	      	  	{
	      	  		return insertar(idUsuario, p);
	      	  		
	      	  	}	        	
	      	  	connection = Conexion.conectar();
	      	  	sdf.setLenient(false); // Para evitar que acepte fechas como 32/01/2024

		      	  	PreparedStatement ps = connection.prepareStatement("UPDATE SPD_procesosRestricciones SET oidProceso=?, idFarmacia=?, tipoRestriccion=?, horasDesde=?, horasHasta=?, valorDia=?, valorFecha=?, descripcion=?, usarRestriccion=? WHERE oidRestriccion=?");
	            if (p.getOidProceso() != null) ps.setInt(1, p.getOidProceso()); else ps.setNull(1, Types.INTEGER);
	            ps.setString(2, p.getIdFarmacia());
	            //if (p.getIdFarmacia() != null) ps.setInt(2, p.getOidFarmacia()); else ps.setNull(2, Types.INTEGER);
	          //  if (p.getIdFarmacia() != null) ps.setString(3, p.getIdFarmacia()); else ps.setNull(3, Types.VARCHAR);
	          //  if (p.getNombreFarmacia() != null) ps.setString(3, p.getNombreFarmacia()); else ps.setNull(4, Types.VARCHAR);
	            
	            ps.setString(3, p.getTipoRestriccion());
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
	            ps.setBoolean(9, p.isUsarRestriccion());
	            ps.setInt(10, p.getOidRestriccion());
	            int count  = ps.executeUpdate();
	            ps.close();
	            return count>0;
	        }

	        public boolean borrar(int oidRestriccion) throws SQLException {
	        	connection = Conexion.conectar();
	            PreparedStatement ps = connection.prepareStatement("DELETE FROM SPD_procesosRestricciones WHERE oidRestriccion=?");
	            ps.setInt(1, oidRestriccion);
	            int result = ps.executeUpdate();
	            ps.close();
	            return result>0;
	        }

	        private ProcesoRestricciones crearDesdeResultSet(ResultSet rs) throws SQLException {
	            ProcesoRestricciones p = new ProcesoRestricciones();
	            p.setOidRestriccion(rs.getInt("oidRestriccion"));
	            p.setOidProceso((Integer) rs.getObject("oidProceso"));
	            p.setIdFarmacia(rs.getString("idFarmacia"));
	            p.setNombreFarmacia(rs.getString("nombreFarmacia"));
	            p.setLanzadera(rs.getString("lanzadera"));
	            p.setTipoRestriccion(rs.getString("tipoRestriccion"));
	            p.setHorasDesde(rs.getString("horasDesde"));
	            p.setHorasHasta(rs.getString("horasHasta"));
	            p.setValorDia((Integer) rs.getObject("valorDia"));
	            p.setValorFecha(rs.getString("valorFecha"));
	            p.setDescripcion(rs.getString("descripcion"));
	            p.setUsarRestriccion(rs.getBoolean("usarRestriccion"));
	            try
	            {
	            	p.setBloqueaAhora(rs.getString("bloqueaAhora"));
	            }catch(Exception e){}
/*	            
            	ProcesoDAO dao = new ProcesoDAO();
            	Proceso proceso = dao.findByOidProceso(null, p.getOidProceso());
            	ProcesoRestriccionesHelper helper = new ProcesoRestriccionesHelper(); 
            	p.setRestriccionEnCurso(helper.hayRestriccionHorario(proceso));
*/
            	return p;
	        }


	    }

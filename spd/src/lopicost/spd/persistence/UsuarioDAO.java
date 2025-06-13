package lopicost.spd.persistence;

import lopicost.spd.struts.bean.PerfilesBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import lopicost.spd.model.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import lopicost.config.pool.dbaccess.Conexion;

public class UsuarioDAO
{
    static String className;
    
    static {
        UsuarioDAO.className = "UsuarioDAO";
    }
    
    public int getNivelLogin(final String usuario, final String password) throws ClassNotFoundException, SQLException {
        final String qry = "select nivel from SPD_usuarios where idUsuario='" + usuario + "'  and hashPass='" + password + "'";
        final Connection con = Conexion.conectar();
        System.out.println(String.valueOf(UsuarioDAO.className) + "--> getNivelLogin -->" + qry);
        int nivel = 0;
        ResultSet resultSet = null;
        try {
            final PreparedStatement pstat = con.prepareStatement(qry);
            resultSet = pstat.executeQuery();
            while (resultSet.next()) {
                nivel = resultSet.getInt("nivel");
            }
        }
        catch (SQLException e) {
            nivel = 0;
            e.printStackTrace();
            return 1;
        }
        finally {
            con.close();
        }
        con.close();
        
        //return nivel;
   	 //para hacer pruebas y no ir poniendo login
        return 1;
    }
    
    public static Usuario findByIdUser(final String usuario) throws ClassNotFoundException, SQLException {
        Usuario user = null;
        final String qry = "select * from SPD_usuarios where idUsuario='" + usuario + "'  and estado='ACTIVO' ";
        final Connection con = Conexion.conectar();
       // System.out.println(String.valueOf(UsuarioDAO.className) + "--> findByIdUser -->" + qry);
        ResultSet resultSet = null;
        try {
            final PreparedStatement pstat = con.prepareStatement(qry);
            resultSet = pstat.executeQuery();
            while (resultSet.next()) {
                user = new Usuario();
                user.setOidUsuario(Long.valueOf(resultSet.getLong("oidUsuario")));
                user.setIdUsuario(resultSet.getString("idUsuario"));
                user.setHashPass(resultSet.getString("hashPass"));
                user.setNombre(resultSet.getString("nombre"));
                user.setApellido1(resultSet.getString("apellido1"));
                user.setApellido2(resultSet.getString("apellido2"));
                user.setEmail(resultSet.getString("email"));
                user.setEmailExtern(resultSet.getString("emailExterno"));
                user.setTelefono(resultSet.getString("telefono"));
                user.setEstado(resultSet.getString("estado"));
                user.setUltimoLogin((Date)resultSet.getDate("ultimoLogin"));
                user.setFree1(resultSet.getString("free1"));
                user.setFree2(resultSet.getString("free2"));
                user.setFree3(resultSet.getString("free3"));
                user.setFree4(resultSet.getString("free4"));
                user.setOidUsuario(Long.valueOf(resultSet.getLong("oidUsuario")));
                user.setPerfil(resultSet.getInt("perfil"));
                user.setPerfil(resultSet.getInt("perfil"));
                user.setImplicadoRobot(resultSet.getInt("implicadoRobot"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return user;
        }
        finally {
        	con.close();
        }
        
        return user;
    }
    
    public static List getIdPerfiles() throws SQLException, ClassNotFoundException {
        final List result = new ArrayList();
        final String qry = "select distinct p.idperfil, p.nombreperfil, p.descripcion, p.activo from SPD_usuarios u inner join SPD_perfil p on u.perfil=p.oidperfil  ";
        final Connection con = Conexion.conectar();
        System.out.println(String.valueOf(UsuarioDAO.className) + "--> getIdPerfiles -->" + qry);
        ResultSet resultSet = null;
        try {
            final PreparedStatement pstat = con.prepareStatement(qry);
            resultSet = pstat.executeQuery();
            while (resultSet.next()) {
                final PerfilesBean perfil = new PerfilesBean();
                perfil.setIdPerfil(resultSet.getString("idperfil"));
                perfil.setNombrePerfil(resultSet.getString("nombreperfil"));
                perfil.setDescripcionPerfil(resultSet.getString("descripcion"));
                result.add(perfil);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return result;
        }
        finally {
            con.close();
        }
        con.close();
        return result;
    }
}
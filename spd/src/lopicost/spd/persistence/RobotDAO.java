package lopicost.spd.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import lopicost.spd.model.Enlace;
import lopicost.spd.model.GestSustitucionesLite;
import lopicost.spd.model.Robot;
import lopicost.config.pool.dbaccess.Conexion;
import java.util.ArrayList;
import java.util.List;
import lopicost.spd.model.Usuario;
import lopicost.spd.struts.form.EnlacesForm;

public class RobotDAO
{
    static String className = "RobotDAO";

    
    public static List getListaRobots() throws ClassNotFoundException, SQLException {
        final List result = new ArrayList();
        Robot robot = null;
        String qry = " SELECT r.idRobot, r.nombreRobot, r.alias, r.carpetaFilia, r.contacto, r.direccion, r.poblacion, r.activo, r.fechaBaja, r.fechaAlta ";
        qry+=  " FROM dbo.bd_robot r ";
        qry+=  " order by r.idRobot  ";
        final Connection con = Conexion.conectar();
        System.out.println(String.valueOf(RobotDAO.className) + "--> getListaRobots  -->" + qry);
        ResultSet resultSet = null;
        try {
            final PreparedStatement pstat = con.prepareStatement(qry);
            resultSet = pstat.executeQuery();
            while (resultSet.next()) {
            	robot = new Robot();
            	robot.setIdRobot(resultSet.getString("idRobot"));
            	robot.setNombreRobot(resultSet.getString("nombreRobot"));
            	robot.setAlias(resultSet.getString("alias"));
            	robot.setCarpetaFilia(resultSet.getString("carpetaFilia"));
            	robot.setContacto(resultSet.getString("contacto"));
            	robot.setDireccion(resultSet.getString("direccion"));
            	robot.setPoblacion(resultSet.getString("poblacion"));
               	robot.setActivo((resultSet.getString("activo").equalsIgnoreCase("SI")));
               	robot.setFechaAlta(resultSet.getDate("fechaAlta"));
              	robot.setFechaBaja(resultSet.getDate("fechaBaja"));
              result.add(robot);
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


	public static Robot getRobotById(String idRobot) throws ClassNotFoundException, SQLException {
        Robot robot = null;
        String qry = " SELECT r.idRobot, r.nombreRobot, r.alias, r.carpetaFilia, r.contacto, r.direccion, r.poblacion, r.activo, r.fechaBaja, r.fechaAlta ";
        qry+= " FROM dbo.bd_robot r ";
        qry+= " WHERE r.idRobot='"+idRobot+"' ";
       
        
        final Connection con = Conexion.conectar();
        System.out.println(String.valueOf(RobotDAO.className) + "--> getListaRobots  -->" + qry);
        ResultSet resultSet = null;
        try {
            final PreparedStatement pstat = con.prepareStatement(qry);
            resultSet = pstat.executeQuery();
            while (resultSet.next()) {
            	robot = new Robot();
            	robot.setIdRobot(resultSet.getString("idRobot"));
            	robot.setNombreRobot(resultSet.getString("nombreRobot"));
            	robot.setAlias(resultSet.getString("alias"));
            	robot.setCarpetaFilia(resultSet.getString("carpetaFilia"));
            	robot.setContacto(resultSet.getString("contacto"));
            	robot.setDireccion(resultSet.getString("direccion"));
            	robot.setPoblacion(resultSet.getString("poblacion"));
               	robot.setActivo(resultSet.getString("activo").equals("SI"));
               	robot.setFechaAlta(resultSet.getDate("fechaAlta"));
              	robot.setFechaBaja(resultSet.getDate("fechaBaja"));
             }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return robot;
        }
        finally {
            con.close();
        }
        con.close();
        return robot;
    }
    
    
    
    
}
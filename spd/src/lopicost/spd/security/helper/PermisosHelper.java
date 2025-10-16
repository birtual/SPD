	
package lopicost.spd.security.helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import lopicost.config.pool.dbaccess.Conexion;

public class PermisosHelper {
	
	/**
	 * Permiso para poder confirmar. De momento es el nivel 1, pero hay que añadir una tabla para gestionarlo bien
	 * @param userId
	 * @param idDivisionResidencia
	 * @return
	 * @throws Exception
	 */
	public static boolean confirmacion(String userId, String idDivisionResidencia) throws Exception{
		
		String sqlPlus="";
		if (userId!=null){
			sqlPlus+=" select distinct up.nivel as nivel from  bd_divisionresidencia d  ";
			sqlPlus+=" inner join dbo.bd_residencia r on (d.idResidencia=r.idResidencia) "; 
			sqlPlus+=" inner  join SPD_usuarios_permisos up on ( ";
			sqlPlus+=" 		(d.idFarmacia = up.idFarmacia and up.idFarmacia is not null) ";
			sqlPlus+=" 		or (d.idDivisionResidencia=up.idDivisionResidencia and up.idDivisionResidencia is not null) ";
			sqlPlus+=" 		or (d.idRobot=up.idRobot and up.idRobot is not null) ";
			sqlPlus+=" 		or (d.idResidencia=up.idResidencia and up.idResidencia is not null)) ";
			sqlPlus+=" inner join SPD_usuarios u on u.idUsuario=up.idUsuario ";
			sqlPlus+=" where up.idUsuario='" + userId +"'";
			sqlPlus+=" and UPPER(u.estado)='ACTIVO'";
			sqlPlus+=" and UPPER(r.status)='ACTIVA' ";
			sqlPlus+=" and d.idDivisionResidencia='" + idDivisionResidencia +"'";
		
		}
   	 	Connection con = Conexion.conectar();
        ResultSet resultSet = null;
        int result =0;
        try {
          PreparedStatement pstat = con.prepareStatement(sqlPlus);
          resultSet = pstat.executeQuery();
          resultSet.next();
          result = resultSet.getInt("nivel");

      } catch (SQLException e) {
          e.printStackTrace();
      }finally {con.close();}

      return result==1;
   }




}

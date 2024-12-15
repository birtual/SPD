
package lopicost.spd.security.helper;

import lopicost.config.logger.Logger;

import lopicost.spd.utils.SPDConstants;

public class VisibilidadHelper {
	
	
	public static String divisionResidenciasVisibles(String userId) throws Exception{
		
		String sqlPlus="";
		if (userId!=null){
			sqlPlus+=" select distinct d.idDivisionResidencia from  bd_divisionresidencia d  ";
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
		}
		else{
			Logger.log(SPDConstants.LOG_ID, "no tiene permisos para visualizar datos", Logger.DEBUG);
			return "";
		}
		
		return sqlPlus;
	}

	
	public static String oidDivisionResidenciasVisibles(String userId) throws Exception{
		
		String sqlPlus="";
		if (userId!=null){
			sqlPlus+=" select distinct d.oidDivisionResidencia from  bd_divisionresidencia d  ";
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

		}
		else{
			Logger.log(SPDConstants.LOG_ID, "no tiene permisos para visualizar datos", Logger.DEBUG);
			return "";
		}
		
		return sqlPlus;
	}

	public static String oidDivisionResidenciasVisiblesExists(String userId, String stringOidDivisionResidencia) throws Exception{
		
		String sqlPlus="";
		if (userId!=null){
			sqlPlus+=" select '1' ";
			sqlPlus+=" from  bd_divisionresidencia d2  ";
			sqlPlus+=" inner join dbo.bd_residencia r on (d2.idResidencia=r.idResidencia) "; 
			sqlPlus+=" inner  join SPD_usuarios_permisos up on ( ";
			sqlPlus+=" 		(d2.idFarmacia = up.idFarmacia and up.idFarmacia is not null) ";
			sqlPlus+=" 		or (d2.idDivisionResidencia=up.idDivisionResidencia and up.idDivisionResidencia is not null) ";
			sqlPlus+=" 		or (d2.idRobot=up.idRobot and up.idRobot is not null) ";
			sqlPlus+=" 		or (d2.idResidencia=up.idResidencia and up.idResidencia is not null)) ";
			sqlPlus+=" inner join SPD_usuarios u on u.idUsuario=up.idUsuario ";
			sqlPlus+=" where up.idUsuario='" + userId +"'";
			sqlPlus+=" and UPPER(u.estado)='ACTIVO'";
			sqlPlus+=" and UPPER(r.status)='ACTIVA' ";
			sqlPlus+=" and d2.idDivisionResidencia = "+stringOidDivisionResidencia+" ";

		}
		else{
			Logger.log(SPDConstants.LOG_ID, "no tiene permisos para visualizar datos", Logger.DEBUG);
			return "";
		}
		
		return sqlPlus;
	}

	public static String idFarmaciasVisibles(String spdUsuario) throws Exception{
		
		String qry="";
		if (spdUsuario!=null){
			qry+= "  	select distinct  f.idFarmacia from dbo.bd_farmacia f ";
			qry+= "  	inner join dbo.bd_divisionResidencia d on d.idFarmacia=f.idFarmacia ";
			qry+= "  	inner join dbo.bd_residencia r on (d.idResidencia=r.idResidencia)   ";
			qry+= "  	inner  join SPD_usuarios_permisos up on (  ";
			qry+= "         (d.idFarmacia = up.idFarmacia and up.idFarmacia is not null)  ";
			qry+= "         or (d.idDivisionResidencia=up.idDivisionResidencia and up.idDivisionResidencia is not null)  ";
			qry+= "  	       or (d.idRobot=up.idRobot and up.idRobot is not null)  ";
			qry+= "  	       or (d.idResidencia=up.idResidencia and up.idResidencia is not null))  ";
			qry+= "  	inner join SPD_usuarios u on u.idUsuario=up.idUsuario  ";
			qry+= "  	where up.idUsuario='"+spdUsuario+"' ";
			qry+= "  	and UPPER(u.estado)='ACTIVO' ";
			qry+= "  	and UPPER(r.status)='ACTIVA' ";

		}
		else{
			Logger.log(SPDConstants.LOG_ID, "no tiene permisos para visualizar datos de esta farmacia", Logger.DEBUG);
			return "";
		}
		
		return qry;
	}


}

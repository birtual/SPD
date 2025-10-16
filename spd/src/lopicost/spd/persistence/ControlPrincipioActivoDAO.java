package lopicost.spd.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lopicost.config.pool.dbaccess.Conexion;
import lopicost.spd.model.BdConsejo;
import lopicost.spd.model.ControlPrincipioActivo;
import lopicost.spd.model.DivisionResidencia;
import lopicost.spd.model.Farmacia;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.struts.form.ControlPrincipioActivoForm;

public class ControlPrincipioActivoDAO {
	
	static String className="ControlPrincipioActivoDAO";




    /**
     * Método encargado de devolver true en caso que el GTVM se encuentre definido como controlable para alerta
     * @param medResi
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static int contar(String idUsuario, String idFarmacia, String nomGtVm, String idDivisionResidencia) throws ClassNotFoundException, SQLException {
        int result = 0;
        
        //String qry = " SELECT  count(*) as quants  FROM dbo.SPD_ControlPrincipioActivo p  where p.NomGtVm ='"+nomGtVm+"' and idFarmacia='"+idFarmacia+"'";
        String qry = " SELECT  count(*) as quants  FROM dbo.SPD_ControlPrincipioActivo p  where p.NomGtVm ='"+nomGtVm+"' ";
        
       // if(idDivisionResidencia!=null && !idDivisionResidencia.equalsIgnoreCase(""))
        //  qry+= " and  ( idDivisionResidencia ='"+idDivisionResidencia+"' OR idDivisionResidencia is null)";
        /*if(idDivisionResidencia!=null && !idDivisionResidencia.equalsIgnoreCase(""))
        {
        	qry+= " and p.idFarmacia in (select idFarmacia from dbo.bd_divisionResidencia where idDivisionResidencia='"+idDivisionResidencia+"') ";
        	qry+= " and (  ";
        	qry+= " 	p.idDivisionResidencia = '"+idDivisionResidencia+"' ";
        	qry+= " 	OR p.idDivisionResidencia is null  ";
        	qry+= " 	OR p.idDivisionResidencia =''  ";
        	qry+= " 	) ";
        	qry+= "  ";
        }
        */
/*        
        if(idDivisionResidencia!=null && !idDivisionResidencia.equalsIgnoreCase(""))
        {
        	qry+= " and p.idDivisionResidencia = '"+idDivisionResidencia+"' ";
        }
        else 
        	qry+= " and (p.idDivisionResidencia is null OR p.idDivisionResidencia ='' ) ";

        if(idFarmacia!=null && !idFarmacia.equalsIgnoreCase(""))
        {
        	qry+= "  and  p.idFarmacia  = '"+idFarmacia+"' ";
        }

*/

        
    	qry+= " and p.idFarmacia in (select idFarmacia from dbo.bd_divisionResidencia where idDivisionResidencia='"+idDivisionResidencia+"') ";
    	qry+= " and (  ";
    	qry+= " 	p.idDivisionResidencia in (select idDivisionResidencia from dbo.bd_divisionResidencia where idDivisionResidencia='"+idDivisionResidencia+"') ";
    	qry+= " 	OR p.idDivisionResidencia is null  ";
    	qry+= " 	OR p.idDivisionResidencia =''  ";
    	qry+= " 	) ";
    	qry+= "  ";

        qry+= "  and  p.idFarmacia in  ";
		qry+= "  ( ";
		qry+= "  	select distinct  f.idFarmacia ";
		qry+= "  	from dbo.bd_farmacia f ";
		qry+= "  	inner join dbo.bd_divisionResidencia d on d.idFarmacia=f.idFarmacia ";
		qry+= "  	inner join dbo.bd_residencia r on (d.idResidencia=r.idResidencia)   ";
		qry+= "  	inner  join SPD_usuarios_permisos up on (  ";
		qry+= "         (d.idFarmacia = up.idFarmacia and up.idFarmacia is not null)  ";
		qry+= "         or (d.idDivisionResidencia=up.idDivisionResidencia and up.idDivisionResidencia is not null)  ";
		qry+= "  	       or (d.idRobot=up.idRobot and up.idRobot is not null)  ";
		qry+= "  	       or (d.idResidencia=up.idResidencia and up.idResidencia is not null))  ";
		qry+= "  	inner join SPD_usuarios u on u.idUsuario=up.idUsuario  ";
		qry+= "  	where up.idUsuario='"+idUsuario+"' ";
		qry+= "  	and UPPER(u.estado)='ACTIVO' ";
		qry+= "  	and UPPER(r.status)='ACTIVA' ";
		qry+= "  ) ";
        
       //qry+=getRestoQuery(idUsuario, idFarmacia,  nomGtVm,  idDivisionResidencia);
        final Connection con = Conexion.conectar();
        System.out.println(className + "--> contar  -->" + qry);
       
	     ResultSet resultSet = null;
	
		try {
			PreparedStatement pstat = con.prepareStatement(qry);
		    resultSet = pstat.executeQuery();
		    resultSet.next();
		    result = resultSet.getInt("quants");
		         
		       
		     } catch (SQLException e) {
		    	 result=-1;
		         e.printStackTrace();
		     }finally {con.close();}
		return result;
	}
    

	private static String getRestoQuery(String idUsuario, String idFarmacia, String codGtVm, int oidDivisionResidencia) {
		
		String qry = " FROM dbo.SPD_ControlPrincipioActivo p  ";
		qry+=  "  inner join bd_farmacia f on p.idFarmacia = f.idFarmacia  ";
		qry+=  "   left join bd_divisionResidencia d on p.idFarmacia = f.idFarmacia and d.idDivisionResidencia=p.idDivisionResidencia  ";
		qry+=  " where 1=1 "; 
        
		if(idFarmacia!=null && !idFarmacia.equalsIgnoreCase(""))	
	        	 qry+=  " and p.idFarmacia ='"+idFarmacia+"' ";
		  
       if(codGtVm!=null && !codGtVm.equalsIgnoreCase(""))	
        	 qry+=  " and p.codGtVm ='"+codGtVm+"' ";
        
/*        //if(idDivisionResidencia!=null && !idDivisionResidencia.equalsIgnoreCase(""))
       if(oidDivisionResidencia>0)
        {
        	qry+= " and p.idFarmacia in (select idFarmacia from dbo.bd_divisionResidencia where oidDivisionResidencia='"+oidDivisionResidencia+"') ";
        	qry+= " and (  ";
        	qry+= " 	p.idDivisionResidencia in (select idDivisionResidencia from dbo.bd_divisionResidencia where oidDivisionResidencia='"+oidDivisionResidencia+"') ";
        	qry+= " 	OR p.idDivisionResidencia is null  ";
        	qry+= " 	OR p.idDivisionResidencia =''  ";
        	qry+= " 	) ";
        	qry+= "  ";
        }
       else 
    	   qry+= " 	AND (p.idDivisionResidencia is null OR p.idDivisionResidencia ='') ";
*/
		qry+= "  and  p.idFarmacia in  ";
		qry+= "  ( ";
		qry+= "  	select distinct  f.idFarmacia ";
		qry+= "  	from dbo.bd_farmacia f ";
		qry+= "  	inner join dbo.bd_divisionResidencia d on d.idFarmacia=f.idFarmacia ";
		qry+= "  	inner join dbo.bd_residencia r on (d.idResidencia=r.idResidencia)   ";
		qry+= "  	inner  join SPD_usuarios_permisos up on (  ";
		qry+= "         (d.idFarmacia = up.idFarmacia and up.idFarmacia is not null)  ";
		qry+= "         or (d.idDivisionResidencia=up.idDivisionResidencia and up.idDivisionResidencia is not null)  ";
		qry+= "  	       or (d.idRobot=up.idRobot and up.idRobot is not null)  ";
		qry+= "  	       or (d.idResidencia=up.idResidencia and up.idResidencia is not null))  ";
		qry+= "  	inner join SPD_usuarios u on u.idUsuario=up.idUsuario  ";
		qry+= "  	where up.idUsuario='"+idUsuario+"' ";
		qry+= "  	and UPPER(u.estado)='ACTIVO' ";
		qry+= "  	and UPPER(r.status)='ACTIVA' ";
		qry+= "  ) ";
		
		qry+= " order by p.idFarmacia, p.nomGtVm asc,  p.idDivisionResidencia  ";

		
		return qry;
	}


	public static int contar(String idUsuario, FicheroResiBean medResi) throws ClassNotFoundException, SQLException {
		return contar(idUsuario, null, medResi.getSpdNomGtVm(), medResi.getIdDivisionResidencia());
		
	}

	public int getCount(String idUsuario, ControlPrincipioActivoForm formulario) throws ClassNotFoundException, SQLException {
		return contar(idUsuario, formulario.getIdDivisionResidencia(), formulario.getNomGtVm(), formulario.getIdDivisionResidencia()) ;
	}
    
	
   
	public static List<ControlPrincipioActivo> getListado(String idUsuario, String idFarmacia, String codGtVm, int oidDivisionResidencia) throws SQLException {
		   Connection con = Conexion.conectar();
			
		   List<ControlPrincipioActivo>  result = new ArrayList<ControlPrincipioActivo>();
	       String qry = " SELECT p.*, f.*, d.* ";
	       qry+=getRestoQuery(idUsuario, idFarmacia,  codGtVm,  oidDivisionResidencia);
	       System.out.println(className + "--> getListado -->" +qry );		
	       ResultSet resultSet = null;
	       try {
	    	   PreparedStatement pstat = con.prepareStatement(qry);
		 	   resultSet = pstat.executeQuery();
		 	   while (resultSet.next()) {
		 	   	ControlPrincipioActivo cpa = new ControlPrincipioActivo();
		 	    Farmacia f = new Farmacia();
		 	    	f.setIdFarmacia(resultSet.getString("idFarmacia"));
		 	       	f.setNombreFarmacia(resultSet.getString("nombreFarmacia"));
		 	    DivisionResidencia d = new DivisionResidencia();
		 	    	d.setOidDivisionResidencia(resultSet.getInt("oidDivisionResidencia"));
		 	        d.setIdDivisionResidencia(resultSet.getString("idDivisionResidencia"));
		 	        d.setNombreDivisionResidencia(resultSet.getString("nombreDivisionResidencia"));
		 	    BdConsejo b = new BdConsejo();
		 	    	b.setCodGtVm(resultSet.getString("codGtVm"));
		 	        b.setNomGtVm(resultSet.getString("nomGtVm"));
		 	    cpa.setFarmacia(f);
		 	    cpa.setDivisionResidencia(d);
		 	    cpa.setBdConsejo(b);
		 	    result.add(cpa);
		 	   }
	       } catch (SQLException e) {e.printStackTrace();}finally {con.close();}

	       return result;
		 }
	



	public boolean nuevo(String spdUsuario, String idFarmacia, DivisionResidencia div, BdConsejo bdC) throws SQLException, ClassNotFoundException {
		return nuevo( spdUsuario, idFarmacia, (div.getIdDivisionResidencia()!=null?div.getIdDivisionResidencia():""), bdC.getCodGtVm(), bdC.getNomGtVm(),"1" );
   }
	
	public static boolean nuevo(String idUsuario, String  idFarmacia, String idDivisionResidencia, String codGtVm, String nomGtVm, String nivel) throws ClassNotFoundException, SQLException {

        int result=0;
		  Connection con = Conexion.conectar();
	  	   String qry = " INSERT INTO dbo.SPD_ControlPrincipioActivo (idFarmacia,  idDivisionResidencia, CodGtVm, NomGtVm, nivel)	";
	  	   	qry+= "   VALUES  (";
	       	qry+= " '"+	idFarmacia+"',  '"+	idDivisionResidencia+"',  '"+ codGtVm+"', '"+ nomGtVm+"', '"+ nivel+"') ";

			System.out.println(className + "--> nuevo -->" +qry );		
			
	    try {
	         PreparedStatement pstat = con.prepareStatement(qry);
	         result=pstat.executeUpdate();
	         con.commit();
	         con.close();
	         pstat.close();
	       
	     } catch (SQLException e) {
	         e.printStackTrace();
	     }
		
		return result>0;
	}


	public static boolean borrar(String idFarmacia, String codGtVm, int oidDivisionResidencia) throws Exception 
	{
		int result=0;
		Connection con = Conexion.conectar();
		String query = " DELETE FROM dbo.SPD_ControlPrincipioActivo  ";
		query+= " WHERE idFarmacia = '"+idFarmacia+"' AND codGtVm='"+codGtVm+"'";

		if(oidDivisionResidencia>0)
			query+= " AND idDivisionResidencia in (select idDivisionResidencia from dbo.bd_divisionResidencia where oidDivisionResidencia='"+oidDivisionResidencia+"') ";

		System.out.println(className + "--> borrar  -->" +query );		
		 	
	    try {
	         PreparedStatement pstat = con.prepareStatement(query);
	         result=pstat.executeUpdate();
	       
	     } catch (SQLException e) {
	         e.printStackTrace();
	     }finally {con.close();}
		return result>0;
	}


	public boolean borrar(ControlPrincipioActivo cpa) throws Exception {
		return borrar(cpa.getFarmacia().getIdFarmacia(), cpa.getBdConsejo().getCodGtVm(), (cpa.getDivisionResidencia()!=null?cpa.getDivisionResidencia().getOidDivisionResidencia():0));
	}







	
	
    
    
    
    
    
}

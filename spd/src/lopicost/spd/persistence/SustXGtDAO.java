package lopicost.spd.persistence;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import com.microsoft.sqlserver.jdbc.SQLServerException;

import lopicost.config.pool.dbaccess.Conexion;
import lopicost.spd.model.BdConsejo;
import lopicost.spd.model.SustXComposicion;
import lopicost.spd.model.SustXGtvmp;
import lopicost.spd.model.SustXGtvmpp;
import lopicost.spd.struts.form.SustXComposicionForm;
import lopicost.spd.struts.form.SustXGtForm;
import lopicost.spd.utils.SPDConstants;
import lopicost.spd.utils.StringUtil;
 
 
public class SustXGtDAO {
	
    static TreeMap<String, SustXGtvmp> tPadres =new TreeMap();
    static TreeMap<String, SustXGtvmpp> tHijos =new TreeMap();

	
	public static int getCountSustXComposicion(SustXGtForm form) throws SQLException, ClassNotFoundException {
	    String qry = getQueryParts(form, true, 0, 0, true);
	    int result = 0;
	    try (Connection con = Conexion.conectar();
	         PreparedStatement pstat = con.prepareStatement(qry);
	         ResultSet resultSet = pstat.executeQuery()) {
	        if (resultSet.next()) {
	            result = resultSet.getInt("quants");
	        }
	    } catch (SQLServerException e) {
	    	e.printStackTrace();
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
	    return result;
	}

	
	
	/**
	 * Método encargado de recoger los objetos GTVMP y GTVMPP relacionados con una serie de filtros
	 * @param form
	 * @param inicio
	 * @param fin
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static List<SustXGtvmp> getSustXGtvmp(SustXGtForm form, int inicio, int fin) throws ClassNotFoundException, SQLException {
     	
		tPadres.clear();
		tHijos.clear();
		String qry = getQueryParts( form, false, inicio, fin, true);
		Connection con = Conexion.conectar();
        ResultSet resultSet = null;
 		List<SustXGtvmp> listaSustXGtvmp = new ArrayList<SustXGtvmp>();
 		try {
            PreparedStatement pstat = con.prepareStatement(qry);
            resultSet = pstat.executeQuery();

            while (resultSet.next()) {
            	creaObjeto(resultSet);
             }
            // Opción 1: Convertir las claves a un ArrayList
            ArrayList<String> keysList = new ArrayList<>(tPadres.keySet());
            //System.out.println("Claves: " + keysList);

            // Opción 2: Convertir los valores a un ArrayList
            ArrayList<SustXGtvmp> valuesList = new ArrayList<>(tPadres.values());
            // System.out.println("Valores: " + valuesList);

            // Opción 3: Convertir las entradas completas a un ArrayList
            ArrayList<Map.Entry<String, SustXGtvmp>> entriesList = new ArrayList<>(tPadres.entrySet());
            //System.out.println("Entradas:");
            for (Map.Entry<String, SustXGtvmp> entry : entriesList) {
                //System.out.println(entry.getKey() + " -> " + entry.getValue());
                listaSustXGtvmp.add(entry.getValue());
            }
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
       	 con.close();
    	}
        return listaSustXGtvmp;
    }
	
	
	
	private static void creaObjeto(ResultSet resultSet) throws SQLException {
		

		String codGtvmp = resultSet.getString("codGtVmp");
		String nomGtvmp = resultSet.getString("nomGtVmp");
		String codGtvmpp = resultSet.getString("codGtVmpp");
		String nomGtvmpp = resultSet.getString("nomGtVmpp");
		String lab = resultSet.getString("codiLab");
		String nombreRobot = resultSet.getString("nombreRobot");
		//String keyPadre = nomGtvmp + "_" + lab;
		String keyPadre = nomGtvmp;
		String keyHijo = nomGtvmpp + "_" + lab + "_" + nombreRobot;
		//si no hemos pasado aún por el GTVMP lo registramos y tratamos 
		if (!tPadres.containsKey(keyPadre)) 
		{
			SustXGtvmp padre = creaPadre(codGtvmp, resultSet);
			SustXGtvmpp hijo = creaHijo( padre, resultSet);
			tPadres.put(keyPadre, padre);
			
		}
		//si ya hemos pasado por el GTVMP previamente miramos sus GTVMPP 
		else
		{
			SustXGtvmp padre = tPadres.get(keyPadre);
			//si no hemos pasado aún por el GTVMPP lo registramos y tratamos 
			if (!tHijos.containsKey(keyHijo)) 
			{
				SustXGtvmpp hijo = creaHijo(padre, resultSet);
				tHijos.put(keyHijo, hijo);
			}
		}
	}
	


	private static SustXGtvmpp creaHijo(SustXGtvmp padre, ResultSet resultSet) throws SQLException {
		
		SustXGtvmpp hijo = new SustXGtvmpp();
		//hijo.setPresentacion(resultSet.getString("presentacion"));
		hijo.setRentabilidad(resultSet.getFloat("rentabilidad"));
		hijo.setNota(resultSet.getFloat("nota"));
		hijo.setPonderacion(resultSet.getFloat("ponderacion"));
		hijo.setCodLaboratorio(resultSet.getString("codiLab"));
		hijo.setNomLaboratorio(resultSet.getString("nombreLab"));
		hijo.setFechaCreacion(resultSet.getDate("fechaCreacion"));
		hijo.setComentarios(resultSet.getString("comentarios"));
		hijo.setCodGtvmpp(resultSet.getString("codGtVmpp"));
		hijo.setNomGtvmpp(resultSet.getString("nomGtVmpp"));
		hijo.setCn6(resultSet.getString("cn6"));	
		hijo.setCn7(resultSet.getString("cn7"));	
		hijo.setSustituible(resultSet.getString("sustituible"));	
		hijo.setIdRobot(resultSet.getString("idRobot"));
		hijo.setNombreRobot(resultSet.getString("nombreRobot"));
		hijo.setTolva(resultSet.getString("tolva"));	
		padre.getHijos().add(hijo);

		return hijo;
	}



	private static SustXGtvmp creaPadre(String codGtvmp, ResultSet resultSet) throws SQLException {
		SustXGtvmp  p = new SustXGtvmp();
	  	p.setOidSustXComposicion(resultSet.getInt("oidSustXComposicion"));
	   	p.setCodGtVm(resultSet.getString("codGtVm"));
	   	p.setNomGtVm(resultSet.getString("nomGtVm"));
	   	p.setCodGtvmp(codGtvmp);	
	   	p.setNomGtvmp(resultSet.getString("nomGtVmp"));
	   	p.setComentarios(resultSet.getString("comentarios"));
	   	return p;
	}



	/**
	 * Métodp encargado de construir la query final, en base a parámetros de entrada
	 * @param form
	 * @param count
	 * @param inicio
	 * @param fin
	 * @param todosLosConjuntosHomogeneos
	 * @return
	 */
	private static String getQueryParts(SustXGtForm form, boolean count, int inicio, int fin, boolean todosLosConjuntosHomogeneos) {
		
		String qry = getSelect(form, count);
		/*
		String select = getSelect(form, count);
		String from = 	getFrom(form);
		String where = 	getWhere(form);
		String qry = select + from + where;// + otros;
		String order = 	getOrder(form, count);
	
		if(!count) 
		{
			String union = getUnion(form);
			union+= getWhereUnion(form);

			qry+=  union;
		}
		qry+=  order;// + otros;
		*/
		 //order = 	"";
		String otros = 	getOtrosSqlPost2008(form, inicio, fin, count);
	
		
		System.out.println("SustXGtDAO getQueryParts -->" +qry );		
		return qry+ otros;
	}


	
	/**
	 * Método encargado de construir la select de la query final
	 * @param form
	 * @param count
	 * @return
	 */
	private static  String getSelect(SustXGtForm form, boolean count) 
	{
		boolean esOR = (form.getFiltroLogico()!=null && form.getFiltroLogico().equalsIgnoreCase("OR")?true:false);
		
		String select = "";
		//si es contador inicializo la query
		if(count)  
		{
			select = " SELECT SUM(total) AS total_count "; 
			select+= " FROM ( ";
			select+= "		SELECT COUNT(distinct coalesce(g.codGtVmpp,'')+coalesce(b.CodGtVmp,'')+coalesce(g.nombreLab,'')) AS quants ";
		}
		else if(!count) 
		{
			select= " SELECT DISTINCT ";
			select+= " b.CodGtVm AS CodGtVm, ";
			select+= " b.NomGtVm AS NomGtVm, ";
			select+= " COALESCE(b.CodGtVmp, g.CodGtVmp) AS codGtVmp,  ";
			select+= " COALESCE(b.NomGtVmp, g.NomGtVmp) AS nomGtVmp, ";
			select+= " COALESCE(b.CodGtVmpp, g.CodGtVmpp) AS codGtVmpp,  ";
			select+= " COALESCE(b.NomGtVmpp, g.NomGtVmpp) AS nomGtVmpp,  ";
			select+= " COALESCE(g.rentabilidad, 0) AS rentabilidad,   ";
			select+= " COALESCE(g.ponderacion, 0) AS ponderacion,  ";
			select+= " COALESCE(g.codiLab, '') AS codiLab,  ";
			select+= " COALESCE(g.nombreLab, '') AS nombreLab,  ";
			select+= " COALESCE(g.ultimaModificacion, '') AS ultimaModificacion,  ";
			select+= " COALESCE((g.rentabilidad + g.ponderacion), 0) AS nota,  ";
			select+= " COALESCE(g.comentarios, '') AS comentarios,   ";
			select+= " COALESCE(g.fechaCreacion, '') AS fechaCreacion, ";
			select+= " COALESCE(g.oidSustXComposicion, -1) AS oidSustXComposicion,  ";
			select+= " COALESCE(g.cn6, '') AS cn6,  ";
			select+= " COALESCE(g.cn7, '') AS cn7, ";
			select+= " COALESCE(g.nombreMedicamento, '') AS nombreMedicamento,  ";
			select+= " COALESCE(g.sustituible, '') AS sustituible,  ";
			select+= " COALESCE(g.tolva, '') AS tolva,  ";
			select+= " COALESCE(g.idrobot, '') AS idrobot,  ";
			select+= " COALESCE(r.nombreRobot, '') AS nombreRobot  ";
		}
			
			select+= " FROM dbo.SPD_sustXGt g  ";
			select+= " LEFT JOIN bd_consejo b ON b.codGtVmpp = g.codGtVmpp ";
			select+= " LEFT JOIN dbo.bd_robot r   ON r.idRobot = g.idRobot ";
			select+= " WHERE 1=1  ";
			if(form.getOidSustXComposicion()>0)
				select+=  " AND g.oidSustXComposicion = '"+form.getOidSustXComposicion()+"'";

			//INICIO OR - en caso de OR abrimos un paréntesis antes de los tres filtros
			if(esOR)
			{
				select+=  " AND (  1 = 0 ";
			}
				if(form.getCampoGoogle()!=null && !form.getCampoGoogle().equals(""))
				{
					select+=  (esOR?" OR ":" AND ");
					select+=  " ( ";
					select+=  " 	(g.nomGtVmpp LIKE '%"+form.getCampoGoogle() +"%' OR b.nomGtVm LIKE '%"+form.getCampoGoogle() +"%' )";
					select+=  "  	OR g.nombreLab  LIKE '%"+form.getCampoGoogle() +"%' ";
					select+=  "  	OR g.comentarios  LIKE '%"+form.getCampoGoogle() +"%' ";
					select+=  "  	OR b.CODIGO ='"+form.getCampoGoogle() +"'";
					select+=  " ) ";
				}
				if(form.getFiltroRobot()!=null && !form.getFiltroRobot().equals(""))
				{
					select+=  (esOR?" OR ":" AND ");
					select+=  "  (g.idRobot =  '"+form.getFiltroRobot() +"' OR g.idRobot is null  OR g.idRobot ='')  ";
				}
	
				if(form.getFiltroNomGtVm()!=null && !form.getFiltroNomGtVm().equals(""))
				{
					select+=  (esOR?" OR ":" AND ");
					select+=  "  b.nomGtVm =  '"+form.getFiltroNomGtVm() +"' ";
				}

			//FIN OR - en caso de OR cerramos un paréntesis antes de los tres filtros
			if(esOR)
			{
				select+=  " )";
			}
			
			//select+= " AND b.NomGtVmp='ACETILCISTEINA 600 MG COMPRIMIDO EFERVESCENTE' ";
			select+= " UNION ALL";
			if(count)  
				select = "SELECT COUNT(distinct coalesce(g2.codGtVmpp,'')+coalesce(b2.CodGtVmp,'')+coalesce(g2.nombreLab,'')) AS quants ";
			else if(!count) 
			{
				select+= " SELECT DISTINCT "; 
				select+= " 	b2.CodGtVm AS CodGtVm, ";
				select+= " 	b2.NomGtVm AS NomGtVm, ";
				select+= " 	COALESCE(b2.CodGtVmp, g2.CodGtVmp) AS codGtVmp, "; 
				select+= " 	COALESCE(b2.NomGtVmp, g2.NomGtVmp)  AS nomGtVmp, ";
				select+= " 	COALESCE(b2.CodGtVmpp, g2.CodGtVmpp) AS codGtVmpp,  ";
				select+= " 	COALESCE(b2.NomGtVmpp, g2.NomGtVmpp) AS nomGtVmpp,  ";
				select+= " 	0 AS rentabilidad,   ";
				select+= " 	0 AS ponderacion,  ";
				select+= " 	'' AS codiLab,  ";
				select+= " 	'' AS nombreLab,  ";
				select+= " 	'' AS ultimaModificacion, "; 
				select+= " 	0 AS nota,  ";
				select+= " 	'' AS comentarios, ";  
				select+= " 	'' AS fechaCreacion, ";
				select+= " 	-1 AS oidSustXComposicion, "; 
				select+= " 	'' AS cn6,  ";
				select+= " 	'' AS cn7, ";
				select+= " 	'' AS nombreMedicamento, "; 
				select+= " 	'' AS sustituible, "; 
				select+= " 	'' AS tolva,  ";
				select+= " 	'' AS idrobot,  ";
				select+= " 	'' AS nombreRobot  ";
			}
			select+= " FROM bd_consejo b2  ";
			select+= " LEFT JOIN dbo.SPD_sustXGt g2   ON b2.codGtVmpp = g2.codGtVmpp "; 
			select+= " WHERE 1=1  ";
			if(form.getOidSustXComposicion()>0)
				select+=  " AND g2.oidSustXComposicion = '"+form.getOidSustXComposicion()+"'";
			//INICIO OR - en caso de OR abrimos un paréntesis antes de los tres filtros
			if(esOR)
			{
				select+=  " AND (  1 = 0 ";
			}
			
				if(form.getCampoGoogle()!=null && !form.getCampoGoogle().equals(""))
				{
					select+=  (esOR?" OR ":" AND ");
					select+=  " ( ";
					select+=  "  	(g2.nomGtVmpp LIKE '%"+form.getCampoGoogle() +"%' OR b2.nomGtVm LIKE '%"+form.getCampoGoogle() +"%' )";
					select+=  "  	OR g2.nombreLab  LIKE '%"+form.getCampoGoogle() +"%' ";
					select+=  "  	OR g2.comentarios  LIKE '%"+form.getCampoGoogle() +"%' ";
					select+=  "  	OR b2.CODIGO ='"+form.getCampoGoogle() +"'";
					select+=  " ) ";
				}
				if(form.getFiltroRobot()!=null && !form.getFiltroRobot().equals(""))
				{
					select+=  (esOR?" OR ":" AND ");
					select+=  "  (g2.idRobot =  '"+form.getFiltroRobot() +"' OR g2.idRobot is null  OR g2.idRobot ='')  ";
				}
	
				if(form.getFiltroNomGtVm()!=null && !form.getFiltroNomGtVm().equals(""))
				{
					select+=  (esOR?" OR ":" AND ");
					select+=  "  b2.nomGtVm =  '"+form.getFiltroNomGtVm() +"' ";
				}
			
				//FIN OR - en caso de OR cerramos un paréntesis antes de los tres filtros
			if(esOR)
			{
				select+=  " )";
			}
			
			//select+= " AND b2.NomGtVmp='ACETILCISTEINA 600 MG COMPRIMIDO EFERVESCENTE' ";
			if(form.isVerSoloGestionados())
			{
				select+= " AND EXISTS "; 
				select+= " (SELECT 1 FROM dbo.SPD_sustXGt T3 WHERE b2.codGtVmp = T3.codGtVmp ) ";
			}
			if(!count) 
				select+= " ORDER BY nomGtVmp, nomGtVmpp, ponderacion desc, idrobot,  sustituible, tolva ";
			else
				select+= " ) AS quants; "; 
	
		return select;

	}
	

	
	private static  String getWhere(SustXGtForm form)
	{
		String 	where =" WHERE 1=1  ";
				where+=" AND b.codGtVmpp IS NOT NULL ";

		if(form.isVerSoloGestionados())
			where+=" AND g.codGtVmpp IS NOT NULL ";
	

		where+=  " and b.NomGtVmp='ACETILCISTEINA 600 MG COMPRIMIDO EFERVESCENTE' ";
		return where;
	}
	

	/**
	 * Fetch es una cláusula que funciona a partir del SqlServer 2008 (no inclusive)
	 * @param form
	 * @param inicio
	 * @param fin
	 * @param count
	 * @return
	 */
	private static String getOtrosSqlPost2008(SustXGtForm form, int inicio, int fin, boolean count) 
	{
		String otros="";
		if(!count) 
		{
			otros+= " offset "+ (inicio) + " rows ";
			otros+= " fetch next "+(fin)+ " rows only";
		}
		return otros;
	}

	private static String getUnion(SustXGtForm form) 
	{
		String union="";
		union+= " UNION ";
		union+= " SELECT  distinct b.CodGtVm, b.NomGtVm, "; 
		union+= " 	b.CodGtVmp AS codGtVmp, "; 
		union+= " 	b.NomGtVmp AS nomGtVmp, "; 
		union+= " 	b.CodGtVmpp AS codGtVmpp, "; 
		union+= " 	b.NomGtVmpp AS nomGtVmpp, "; 
		union+= " 	0, '', '', '', '', ";
		union+= " 	'', '', '', -1, "; 
		union+= " 	'', '', '', '', '', '' ";  
		union+= " 	FROM dbo.bd_consejo b ";
		return union;
	}


	public static boolean nuevoSustXGtHijo(SustXGtvmpp hijo) throws ClassNotFoundException, SQLException {
        int result=0;
		  Connection con = Conexion.conectar();
	  	   String qry = " INSERT INTO dbo.SPD_sustXGt (idRobot, codGtVmpp, nomGtVmpp, rentabilidad, ";
	  	   		qry+= " ponderacion, codiLab, ultimaModificacion, ";
	  	   		qry+= " comentarios, nombreLab, fechaCreacion, ultimaModificacion, sustituible, tolva) VALUES ";
	       		//qry+= "('"+ form.getFiltroListaConjuntosHomogeneos()+"', '"+ getNomConjHomog(form.getFiltroListaConjuntosHomogeneos())+"', "+ form.getRentabilidad()+", ";
	  	   		qry+= "('"+ hijo.getIdRobot()+"', '"+ hijo.getCodGtvmpp()+"', '"+ hijo.getNomGtvmpp()+"', "+ hijo.getRentabilidad()+", ";
	  	   		qry+=  + hijo.getPonderacion()+", '"+ hijo.getCodLaboratorio()+"',   CONVERT(datetime, getdate(), 120), ";
	       		qry+= "'" + hijo.getComentarios()+"', '" + hijo.getNomLaboratorio()+"', CONVERT(datetime, getdate(), 120), '" + hijo.getSustituible()+"', '" + hijo.getTolva()+"' ) ";
		    System.out.println("nuevoSustXGtHijo -->" +qry );		
	 	
	    try {
	         PreparedStatement pstat = con.prepareStatement(qry);
	         result=pstat.executeUpdate();
	       
	     } catch (SQLException e) {
	         e.printStackTrace();
	     }finally {con.close();}
		return result>0;
	}
	 
	
	
	public static SustXGtvmp getPadreByOid(int oid) throws ClassNotFoundException, SQLException {

        int result=0;
        SustXGtvmp padre = new SustXGtvmp();
		Connection con = Conexion.conectar();
	  	String qry = "select *,  COALESCE((g.rentabilidad + g.ponderacion), 0) AS nota ";
	  	qry+= " FROM dbo.SPD_sustXGt g  ";
	  	qry+= " LEFT JOIN bd_consejo b ON b.codGtVmpp = g.codGtVmpp ";
	  	qry+= " LEFT JOIN dbo.bd_robot r   ON r.idRobot = g.idRobot ";
	  	qry+= " WHERE 1=1  ";
		qry+= " AND g.oidSustXComposicion= '"+oid+"' ";
	  	System.out.println("getPadreByOid" +qry );		
	 	ResultSet resultSet = null;
	  	try {
	  		PreparedStatement pstat = con.prepareStatement(qry);
		    resultSet = pstat.executeQuery();

	        while (resultSet.next()) {
	        	String codGtvmp = resultSet.getString("codGtVmp");
	        	padre = creaPadre(codGtvmp, resultSet);
				SustXGtvmpp hijo = creaHijo( padre, resultSet);
	           	//creaObjeto(resultSet);
	        }
	    } catch (SQLException e) {
	           e.printStackTrace();
	       }finally {
	      	 con.close();
	    }
	      return padre;
	  }



	public static boolean updateRentabilidadLab(SustXGtForm form)  throws ClassNotFoundException, SQLException {

        int result=0;
		  Connection con = Conexion.conectar();
	  	
	  	    String qry = "update dbo.SPD_sustXGt ";
  	   		qry+= " set   rentabilidad= "+form.getRentabilidad() ;
 	   	  	qry+= ", ultimaModificacion= CONVERT(datetime, getdate(), 120)  ";
       		qry+= " where oidSustXComposicion= '"+form.getOidSustXComposicion()+"' ";
       		qry+= " and (  codiLab='"+form.getFiltroCodiLaboratorio()+"' OR nombreLab='"+form.getFiltroNombreLaboratorio()+"') ";
	  		System.out.println("updateRentabilidadLab-->  " +qry );		
	      		 
	    try {
	         PreparedStatement pstat = con.prepareStatement(qry);
	         result=pstat.executeUpdate();
	       
	     } catch (SQLException e) {
	         e.printStackTrace();
	     }finally {con.close();}

		
		return result>0;
	}



}

	


 
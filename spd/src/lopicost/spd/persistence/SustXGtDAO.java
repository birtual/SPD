package lopicost.spd.persistence;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.microsoft.sqlserver.jdbc.SQLServerException;

import lopicost.config.pool.dbaccess.Conexion;
import lopicost.spd.model.Nivel1;
import lopicost.spd.model.Nivel2;
import lopicost.spd.model.Nivel3;
import lopicost.spd.struts.form.SustXGtForm;
 
 
public class SustXGtDAO {
	
    static TreeMap<String, Nivel1> tPadres =new TreeMap();
    static TreeMap<String, Nivel2> tHijos =new TreeMap();
    static TreeMap<String, Nivel3> tNietos =new TreeMap();

	
	public static int getCountSustXComposicion(SustXGtForm form) throws SQLException, ClassNotFoundException {
	    String qry = getQueryParts(form, true, 0, 0, true);
	    int result = 0;
	    try (Connection con = Conexion.conectar();
	         PreparedStatement pstat = con.prepareStatement(qry);
	         ResultSet resultSet = pstat.executeQuery()) {
	        if (resultSet.next()) {
	            result = resultSet.getInt("total_count");
	        }
	    } catch (SQLServerException e) {
	    	e.printStackTrace();
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
	    return result;
	}

	
	
	/**
	 * Método encargado de recoger los objetos GTVM, GTVMP y GTVMPP relacionados con una serie de filtros
	 * @param form
	 * @param inicio
	 * @param fin
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static List<Nivel1> getDesdeNivel(SustXGtForm form, int inicio, int fin) throws ClassNotFoundException, SQLException {
     	
		tPadres.clear();
		tHijos.clear();
		tNietos.clear();
		String qry = getQueryParts( form, false, inicio, fin, true);
		Connection con = Conexion.conectar();
        ResultSet resultSet = null;
 		List<Nivel1> listaNivel1 = new ArrayList<Nivel1>();
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
            ArrayList<Nivel1> valuesList = new ArrayList<>(tPadres.values());
            // System.out.println("Valores: " + valuesList);

            // Opción 3: Convertir las entradas completas a un ArrayList
            ArrayList<Map.Entry<String, Nivel1>> entriesList = new ArrayList<>(tPadres.entrySet());
            //System.out.println("Entradas:");
            for (Map.Entry<String, Nivel1> entry : entriesList) {
                //System.out.println(entry.getKey() + " -> " + entry.getValue());
            	listaNivel1.add(entry.getValue());
            }
          } catch (SQLException e) {
            e.printStackTrace();
        }finally {
       	 con.close();
    	}
        return listaNivel1;
    }
	
	
	/*
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
	*/

	private static void creaObjeto(ResultSet resultSet) throws SQLException {
		

		String codGtVm = resultSet.getString("codGtVm");
		String nomGtVm = resultSet.getString("nomGtVm");
		String codGtVmp = resultSet.getString("codGtVmp");
		String nomGtVmp = resultSet.getString("nomGtVmp");
		String codGtVmpp = resultSet.getString("codGtVmpp");
		String nomGtVmpp = resultSet.getString("nomGtVmpp");
		
		String lab = resultSet.getString("codiLab");
		String nombreRobot = resultSet.getString("nombreRobot");
		//String keyPadre = nomGtvmp + "_" + lab;
		String keyPadre = nomGtVm;
		String keyHijo = nomGtVmp;
		String keyNieto = nomGtVmpp + "_" + lab + "_" + nombreRobot;
		//si no hemos pasado aún por el GTVMP lo registramos y tratamos 

		if (!tPadres.containsKey(keyPadre))
		{
			Nivel1 nivel1 = creaGtVm(resultSet);
			Nivel2 nivel2 = creaGtVmp(resultSet);
			Nivel3 nivel3 = creaGtVmpp(resultSet);
			nivel2.getListaNivel3().add(nivel3);
			nivel1.getListaNivel2().add(nivel2);
			tPadres.put(keyPadre, nivel1);
			tHijos.put(keyHijo, nivel2);
		}
		//si ya hemos pasado por el GTVM previamente miramos sus GTVMP 
		else
		{
			Nivel1 nivel1 = tPadres.get(keyPadre);
			//si no hemos pasado aún por el GTVMP lo registramos y tratamos 
			if (!tHijos.containsKey(keyHijo)) 
			{
				Nivel2 nivel2 = creaGtVmp(resultSet);
				Nivel3 nivel3 = creaGtVmpp(resultSet);
				nivel2.getListaNivel3().add(nivel3);
				nivel1.getListaNivel2().add(nivel2);
				tHijos.put(keyHijo, nivel2);
				
			}
			//si ya hemos pasado por el GTVMP previamente miramos sus GTVMPP 
			else
			{
				Nivel2 nivel2 = tHijos.get(keyHijo);
				
				//si no hemos pasado aún por el GTVMPP lo registramos y tratamos 
				if (!tNietos.containsKey(keyNieto)) 
				{
					Nivel3 nivel3 = creaGtVmpp(resultSet);
					nivel2.getListaNivel3().add(nivel3);
					tNietos.put(keyNieto, nivel3);
				}
			}
		}
	
	}

	private static Nivel1 creaGtVm(ResultSet resultSet) throws SQLException {
		Nivel1  p = new Nivel1();
	   	p.setCodGtVm(resultSet.getString("codGtVm"));
	   	p.setNomGtVm(resultSet.getString("nomGtVm"));
		return p;
	}

	private static Nivel2 creaGtVmp(ResultSet resultSet) throws SQLException {
		Nivel2  p = new Nivel2();
	  	//p.setOidSustXComposicion(resultSet.getInt("oidSustXComposicion"));
	   	p.setCodGtVm(resultSet.getString("codGtVm"));
	   	p.setNomGtVm(resultSet.getString("nomGtVm"));
	   	p.setCodGtVmp(resultSet.getString("codGtVmp"));
	   	p.setNomGtVmp(resultSet.getString("nomGtVmp"));
	   	//p.setComentarios(resultSet.getString("comentarios"));
	   	return p;
	}



	private static Nivel3 creaGtVmpp(ResultSet resultSet) throws SQLException {
		
		Nivel3 hijo = new Nivel3();
		hijo.setCodGtVmp(resultSet.getString("codGtVmp"));
		hijo.setNomGtVmp(resultSet.getString("nomGtVmp"));
		hijo.setCodGtVmpp(resultSet.getString("codGtVmpp"));
		hijo.setNomGtVmpp(resultSet.getString("nomGtVmpp"));
		
		try{   		}catch(Exception e){}
		
		//hijo.setPresentacion(resultSet.getString("presentacion"));
		/*
		
		hijo.setRentabilidad(resultSet.getFloat("rentabilidad"));
		hijo.setNota(resultSet.getFloat("nota"));
		hijo.setPonderacion(resultSet.getFloat("ponderacion"));
		hijo.setCodLaboratorio(resultSet.getString("codiLab"));
		hijo.setNomLaboratorio(resultSet.getString("nombreLab"));
		hijo.setFechaCreacion(resultSet.getDate("fechaCreacion"));
		hijo.setComentarios(resultSet.getString("comentarios"));
		hijo.setCn6(resultSet.getString("cn6"));	
		hijo.setCn7(resultSet.getString("cn7"));	
		hijo.setSustituible(resultSet.getString("sustituible"));	
		hijo.setIdRobot(resultSet.getString("idRobot"));
		hijo.setNombreRobot(resultSet.getString("nombreRobot"));
		hijo.setTolva(resultSet.getString("tolva"));	
*/
		return hijo;
	}




	/**
	 * Método encargado de construir la query final, en base a parámetros de entrada
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
		String select = "";
		//si es contador inicializo la query
		if(count)  
		{
			select = " SELECT SUM(total) AS total_count "; 
			select+= " FROM ( ";
			//select+= "		SELECT COUNT(distinct coalesce(g.codGtVmpp,'')+coalesce(b.CodGtVmp,'')+coalesce(g.nombreLab,'')) AS quants ";
			select+= "		SELECT COUNT(DISTINCT coalesce(b.CodGtVm,'')) AS total ";
		}
		else if(!count) 
		{
			select=  " SELECT DISTINCT  CodGtVm, NomGtVm, codGtVmp, nomGtVmp, codGtVmpp, nomGtVmpp, rentabilidad, ponderacion, codiLab, nombreLab, ultimaModificacion,  ";
			select+= " nota, comentarios, fechaCreacion, oidSustXComposicion, cn6, cn7, nombreMedicamento, sustituible, tolva, idrobot, nombreRobot ";
			select+= " FROM (  ";
		
				select+= " SELECT DISTINCT ";
				select+= " b.CodGtVm AS CodGtVm, ";
				select+= " b.NomGtVm AS NomGtVm, ";
				//select+= " b.presentacion, ";
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
				select+= " g.cn6 AS cn6,  ";
				select+= " dbo.calcularCN7(g.cn7) AS cn7, ";
				select+= " COALESCE(g.nombreMedicamento, '') AS nombreMedicamento,  ";
				select+= " COALESCE(g.sustituible, '') AS sustituible,  ";
				select+= " COALESCE(g.tolva, '') AS tolva,  ";
				select+= " COALESCE(g.idrobot, '') AS idrobot,  ";
				select+= " COALESCE(r.nombreRobot, '') AS nombreRobot  ";
		}
			
			select+= " FROM dbo.SPD_sustXGt g  ";
			select+= " INNER JOIN bd_consejo b ON b.codGtVmpp = g.codGtVmpp ";// and b.codiLABO=g.codiLab ";
			select+= " LEFT JOIN dbo.bd_robot r   ON r.idRobot = g.idRobot ";
			select+= " WHERE 1=1  ";
			if(form.getOidSustXComposicion()>0)
				select+=  " AND g.oidSustXComposicion = '"+form.getOidSustXComposicion()+"'";
			if(form.getFiltroCodGtVmp()!=null && !form.getFiltroCodGtVmp().equalsIgnoreCase(""))
				select+=  " AND b.codGtVmp = '"+form.getFiltroCodGtVmp()+"'";
				
			if(form.getCampoGoogle()!=null && !form.getCampoGoogle().equals(""))
			{
				select+=  " AND ";
				select+=  " ( ";
				select+=  " 	(g.nomGtVmpp LIKE '%"+form.getCampoGoogle() +"%' OR b.nomGtVm LIKE '%"+form.getCampoGoogle() +"%' )";
				select+=  "  	OR g.nombreLab  LIKE '%"+form.getCampoGoogle() +"%' ";
				select+=  "  	OR g.comentarios  LIKE '%"+form.getCampoGoogle() +"%' ";
				select+=  "  	OR b.CODIGO ='"+form.getCampoGoogle() +"'";
				select+=  " ) ";
			}
			if( form.isFiltroVerFarmacias() && form.getFiltroRobot()!=null && !form.getFiltroRobot().equals(""))
			{
				select+=  " AND (g.idRobot =  '"+form.getFiltroRobot() +"' OR g.idRobot is null  OR g.idRobot ='')  ";
			}
			else if (!form.isFiltroVerFarmacias())
				select+=  " AND (g.idRobot is null  OR g.idRobot ='')  ";


			if(form.getFiltroNomGtVm()!=null && !form.getFiltroNomGtVm().equals(""))
			{
				select+=  " AND  b.nomGtVm =  '"+form.getFiltroNomGtVm() +"' ";
			}

			
			//select+= " AND b.NomGtVmp='ACETILCISTEINA 600 MG COMPRIMIDO EFERVESCENTE' ";
			String selectUnion= " UNION ALL ";
			if(count)  
				//select = "SELECT COUNT(distinct coalesce(g2.codGtVmpp,'')+coalesce(b2.CodGtVmp,'')+coalesce(g2.nombreLab,'')) AS quants ";
				selectUnion+= " SELECT COUNT(distinct coalesce(b2.CodGtVm,'')) AS total ";
			else if(!count) 
			{
				selectUnion+= " SELECT DISTINCT "; 
				selectUnion+= " 	b2.CodGtVm AS CodGtVm, ";
				selectUnion+= " 	b2.NomGtVm AS NomGtVm, ";
			//	selectUnion+= "  b2.presentacion, ";
				selectUnion+= " 	b2.CodGtVmp AS codGtVmp, "; 
				selectUnion+= " 	b2.NomGtVmp AS nomGtVmp, ";
				selectUnion+= " 	b2.CodGtVmpp AS codGtVmpp,  ";
				selectUnion+= " 	b2.NomGtVmpp AS nomGtVmpp,  ";
				selectUnion+= " 	null AS rentabilidad,   ";
				selectUnion+= " 	null AS ponderacion,  ";
				selectUnion+= " 	null AS codiLab,  ";
				selectUnion+= " 	null AS nombreLab,  ";
				selectUnion+= " 	null AS ultimaModificacion, "; 
				selectUnion+= " 	null AS nota,  ";
				selectUnion+= " 	null AS comentarios, ";  
				selectUnion+= " 	null AS fechaCreacion, ";
				selectUnion+= " 	null AS oidSustXComposicion, "; 
				selectUnion+= " 	null AS cn6,  ";
				selectUnion+= " 	null AS cn7, ";
				selectUnion+= " 	null AS nombreMedicamento, "; 
				selectUnion+= " 	null AS sustituible, "; 
				selectUnion+= " 	null AS tolva,  ";
				selectUnion+= " 	null AS idrobot,  ";
				selectUnion+= " 	null AS nombreRobot  ";
			}
			selectUnion+= " FROM bd_consejo b2  ";
			selectUnion+= " WHERE 1=1   AND b2.NomGtVmp<>''  ";

			if(form.getFiltroCodGtVmp()!=null && !form.getFiltroCodGtVmp().equalsIgnoreCase(""))
				selectUnion+=  " AND b2.codGtVmp = '"+form.getFiltroCodGtVmp()+"'";

			if(form.getCampoGoogle()!=null && !form.getCampoGoogle().equals(""))
			{
				selectUnion+=  " AND ( ";
				selectUnion+=  "  	( b2.nomGtVm LIKE '%"+form.getCampoGoogle() +"%' )";
				selectUnion+=  "  	OR b2.nombreLab  LIKE '%"+form.getCampoGoogle() +"%' ";
				selectUnion+=  "  	OR b2.CODIGO ='"+form.getCampoGoogle() +"'";
				selectUnion+=  " ) ";
			}

			if(form.getFiltroNomGtVm()!=null && !form.getFiltroNomGtVm().equals(""))
			{
				selectUnion+=  " AND b2.nomGtVm =  '"+form.getFiltroNomGtVm() +"' ";
			}
			
/*			
			if(!form.isFiltroVerTodoConsejo())
			{
				selectUnion+= " AND not EXISTS "; 
				selectUnion+= " (SELECT 1 FROM dbo.SPD_sustXGt T3 WHERE b2.codGtVmp = T3.codGtVmp ) ";
			}
*/			
			selectUnion+= " AND b2.codGtVmpp not in "; 
			selectUnion+= " (SELECT CodGtVmpp FROM dbo.SPD_sustXGt where 1=1 ";
			if(!form.isFiltroVerTodoConsejo())
			{
				selectUnion+= " AND (idRobot is null or idRobot ='')  ";
			}

			selectUnion+= " )  ";

			if(form.isFiltroVerTodoConsejo())
				select+= selectUnion; 
			
			
			
			if(!count) 
			{
				select+= "  ) a ";
				select+= " ORDER BY nomGtVm, nomGtVmp, nomGtVmpp, nota desc, idrobot,  sustituible, tolva ";
			}
			else
				select+= " ) AS quants; "; 
	
		return select;

	}
	

	
	private static  String getWhere(SustXGtForm form)
	{
		String 	where =" WHERE 1=1  ";
				where+=" AND b.codGtVmpp IS NOT NULL ";

		if(form.isFiltroVerTodoConsejo())
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


	public static boolean nuevoSustXGtHijo(Nivel2 padre, Nivel3 hijo) throws ClassNotFoundException, SQLException {
        int result=0;
		  Connection con = Conexion.conectar();
	  	   String qry = " INSERT INTO dbo.SPD_sustXGt (idRobot, codGtVmp, nomGtVmp, codGtVmpp, nomGtVmpp, rentabilidad, ";
	  	   		qry+= " ponderacion, codiLab,  ";
	  	   		qry+= " comentarios, nombreLab, sustituible, tolva) VALUES ";
	       		//qry+= "('"+ form.getFiltroListaConjuntosHomogeneos()+"', '"+ getNomConjHomog(form.getFiltroListaConjuntosHomogeneos())+"', "+ form.getRentabilidad()+", ";
	  	   		qry+= "('"+ hijo.getIdRobot()+"', '"+ padre.getCodGtVmp()+"', '"+ padre.getNomGtVmp()+"', '"+ hijo.getCodGtVmpp()+"', '"+ hijo.getNomGtVmpp()+"', "+ hijo.getRentabilidad()+", ";
	  	   		qry+=  + hijo.getPonderacion()+", '"+ hijo.getCodLaboratorio()+"',  ";
	       		qry+= "'" + hijo.getComentarios()+"', '" + hijo.getNomLaboratorio()+"', ";
	       		qry+= "'" + hijo.getSustituible()+"', '" + hijo.getTolva()+"' ) ";
		    System.out.println("nuevoSustXGtHijo -->" +qry );		
	 	
	    try {
	         PreparedStatement pstat = con.prepareStatement(qry);
	         result=pstat.executeUpdate();
	       
	     } catch (SQLException e) {
	         e.printStackTrace();
	     }finally {con.close();}
		return result>0;
	}
	 
	
	/*
	public static SustXGtVmp getPadreByOid(int oid) throws ClassNotFoundException, SQLException {

        int result=0;
        SustXGtVmp padre = new SustXGtVmp();
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
				SustXGtVmpp hijo = creaHijo( padre, resultSet);
	           	//creaObjeto(resultSet);
	        }
	    } catch (SQLException e) {
	           e.printStackTrace();
	       }finally {
	      	 con.close();
	    }
	      return padre;
	  }
	*/



	public static Nivel1 getNivel1ById(String codGtVm) throws SQLException {
        Nivel1 nivel1 = new Nivel1();
		Connection con = Conexion.conectar();
		String qry = "select distinct b.codGtVm, b.nomGtVm ";
	  	qry+= " FROM dbo.bd_consejo b  ";
	  	qry+= " WHERE 1=1  ";
		qry+= " AND b.codGtVm= '"+codGtVm+"' ";
	  	System.out.println("getNivel1ById " +qry );		
	 	ResultSet resultSet = null;
	  	try {
	  		PreparedStatement pstat = con.prepareStatement(qry);
		    resultSet = pstat.executeQuery();
	        while (resultSet.next()) {
	        	nivel1  = creaGtVm(resultSet);
	        }
	    } catch (SQLException e) {
	           e.printStackTrace();
	       }finally {
	      	 con.close();
	    }
	      return nivel1;
	  }

	

	public static Nivel2 getNivel2ById(String codGtVmp) throws SQLException {
		Nivel1 nivel1 = new Nivel1();
        Nivel2 nivel2 = new Nivel2();
		Connection con = Conexion.conectar();
		String qry = "select distinct b.codGtVm, b.nomGtVm, b.codGtVmp, b.nomGtVmp ";
	  	qry+= " FROM dbo.bd_consejo b  ";
	  	qry+= " WHERE 1=1  ";
		qry+= " AND b.codGtVmp= '"+codGtVmp+"' ";
	  	System.out.println("getNivel2ById " +qry );		
	 	ResultSet resultSet = null;
	  	try {
	  		PreparedStatement pstat = con.prepareStatement(qry);
		    resultSet = pstat.executeQuery();

	        while (resultSet.next()) {
	        	nivel1  = creaGtVm(resultSet);
	        	nivel2 = creaGtVmp(resultSet);
	        	nivel2.setNivel1(nivel1);
				//SustXGtvmpp hijo = creaHijo( padre, resultSet);
	           	//creaObjeto(resultSet);
	        }
	    } catch (SQLException e) {
	           e.printStackTrace();
	       }finally {
	      	 con.close();
	    }
	      return nivel2;
	  }



	public static Nivel3 getNivel3ById(String filtroCodGtVmpp) throws SQLException {
		Nivel1 nivel1 = new Nivel1();
        Nivel2 nivel2 = new Nivel2();
        Nivel3 nivel3 = new Nivel3();
		Connection con = Conexion.conectar();
		
		String select= " SELECT DISTINCT ";
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
		select+= " g.cn6 AS cn6,  ";
		select+= " dbo.calcularCN7(g.cn7) AS cn7, ";
		select+= " COALESCE(g.nombreMedicamento, '') AS nombreMedicamento,  ";
		select+= " COALESCE(g.sustituible, '') AS sustituible,  ";
		select+= " COALESCE(g.tolva, '') AS tolva,  ";
		select+= " COALESCE(g.idrobot, '') AS idrobot,  ";
		select+= " COALESCE(r.nombreRobot, '') AS nombreRobot  ";
		
		select+= " FROM dbo.SPD_sustXGt g  ";
		select+= " INNER JOIN bd_consejo b ON b.codGtVmpp = g.codGtVmpp ";// and b.codiLABO=g.codiLab ";
		select+= " LEFT JOIN dbo.bd_robot r   ON r.idRobot = g.idRobot ";
		select+= " WHERE 1=1  ";
		
		
		String qry = "select distinct b.codGtVm, b.nomGtVm, b.codGtVmp, b.nomGtVmp, b.codGtVmpp, b.nomGtVmpp ";
	  	qry+= " FROM dbo.bd_consejo b  ";
	  	qry+= " WHERE 1=1  ";
		qry+= " AND b.codGtVmpp= '"+filtroCodGtVmpp+"' ";
	  	System.out.println("getNivel3ById " +qry );		
	 	ResultSet resultSet = null;
	  	try {
	  		PreparedStatement pstat = con.prepareStatement(qry);
		    resultSet = pstat.executeQuery();

	        while (resultSet.next()) {
	        	nivel1  = creaGtVm(resultSet);
	        	nivel2 = creaGtVmp(resultSet);
	        	nivel3 = creaGtVmpp(resultSet);
	        	nivel3.setNivel1(nivel1);
	        	nivel3.setNivel2(nivel2);

	        }
	    } catch (SQLException e) {
	           e.printStackTrace();
	       }finally {
	      	 con.close();
	    }
	      return nivel3;
	  }


	public static boolean updateRentabilidadLabporGtVmp(SustXGtForm form)  throws ClassNotFoundException, SQLException {

        int result=0;
		  Connection con = Conexion.conectar();
	  	
	  	    String qry = "update dbo.SPD_sustXGt ";
  	   		qry+= " set   rentabilidad= "+form.getRentabilidad() ;
 	   	  	qry+= ", ultimaModificacion= CONVERT(datetime, getdate(), 120)  ";
       		qry+= " where codGtVmp= '"+form.getCodGtVmp()+"' ";
       		qry+= " and (  codiLab='"+form.getFiltroCodiLaboratorio()+"' OR nombreLab='"+form.getFiltroNombreLaboratorio()+"') ";
	  		System.out.println("updateRentabilidadLabporGtVmp-->  " +qry );		
	      		 
	    try {
	         PreparedStatement pstat = con.prepareStatement(qry);
	         result=pstat.executeUpdate();
	       
	     } catch (SQLException e) {
	         e.printStackTrace();
	     }finally {con.close();}

		
		return result>0;
	}



	public static boolean existeHermanoConLab(Nivel3 hijo, SustXGtForm formulari) throws SQLException {

 		String qry = "select count('1') as quants ";
	  	qry+= " FROM dbo.SPD_sustXGt g  ";
	  	qry+= " WHERE 1=1  ";
		qry+= " AND g.codGtVmpp= '"+hijo.getCodGtVmpp()+"' ";
		qry+= " AND g.codiLab= '"+formulari.getFiltroCodiLaboratorio()+"' ";
		
	  	System.out.println("existeHermanoConLab " +qry );		
   	 	Connection con = Conexion.conectar();
   	 	ResultSet resultSet = null;
   	 	int result =0;
   	 	try {
   	 		PreparedStatement pstat = con.prepareStatement(qry);
   	 		resultSet = pstat.executeQuery();
   	 		resultSet.next();
   	 		result = resultSet.getInt("quants");
   	 	} catch (SQLException e) {
	   		e.printStackTrace();
   	 	}finally {
       	 con.close();
   		}
   	 	return result>0;
    }



	public static boolean actualizaRentabilidadHijoPorOid(Nivel3 hijo, SustXGtForm formulari)  throws ClassNotFoundException, SQLException {


        int result=0;
		  Connection con = Conexion.conectar();
	  	
	  	    String qry = "update dbo.SPD_sustXGt ";
  	   		qry+= " set   rentabilidad= "+formulari.getRentabilidad() ;
 	   	  	qry+= ", ultimaModificacion= CONVERT(datetime, getdate(), 120)  ";
       		qry+= " where oidSustXComposicion= '"+hijo.getOidSustXComposicion()+"' ";
 	  		System.out.println("actualizaRentabilidadHijoPorOid-->  " +qry );		
	      		 
	    try {
	         PreparedStatement pstat = con.prepareStatement(qry);
	         result=pstat.executeUpdate();
	       
	     } catch (SQLException e) {
	         e.printStackTrace();
	     }finally {con.close();}

		
		return result>0;
	}





}

	


 
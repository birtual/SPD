package lopicost.spd.persistence;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lopicost.config.pool.dbaccess.Conexion;
import lopicost.spd.model.BdConsejo;
import lopicost.spd.struts.form.BdConsejoForm;
import lopicost.spd.struts.form.SustXComposicionForm;
import lopicost.spd.utils.HelperSPD;
import lopicost.spd.utils.StringUtil;

 
public class BdConsejoDAO {
	
    public static int  getCountBdConsejo(BdConsejoForm form) throws ClassNotFoundException, SQLException {
   	 //String qry = getQuerySustituciones( form.getCnConsejo(),  form.getNombreCortoOK(), null, null, false, true, 0, 0);
   		String qry = getQuerySustituciones(form.getCnConsejo(), form.getNombreCortoOK(),  form.getFiltroCodiLaboratorio(),  form.getNombreLab(), false, true, 0, 0, 
   				form.getFiltroCodGtVm(), form.getFiltroNomGtVm(),  form.getFiltroCodGtVmp(), form.getFiltroNomGtVmp(),   form.getFiltroCodGtVmpp(), form.getFiltroNomGtVmpp());
   	
   		
	 System.out.println("BdConsejoDAO.getCountBdConsejo -->" +qry );		
   	 Connection con = Conexion.conectar();
   //		 System.out.println("connected main" );
     ResultSet resultSet = null;
	int result =0;
	try {
       PreparedStatement pstat = con.prepareStatement(qry);
       resultSet = pstat.executeQuery();
       resultSet.next();
       result = resultSet.getInt("quants");

   } catch (SQLException e) {
       e.printStackTrace();
   }finally {con.close();}

   return result;
}
    
    /**
	 * Método encargado de retornar un objeto BdConsejo mediante un CN
	 * @param codigoCN
	 * @return
	 */

    public static BdConsejo getByCN(String cn6) throws ClassNotFoundException , SQLException {
    	
  		String qry = "select distinct c.codigo, coalesce(c.nombre, '') + ' ' + coalesce(c.presentacion, '') as nombreConsejo,  ";
  			qry+=  " c.codiLABO, c.nomLABO, c.CodGtVmp, c.NomGtVmp, c.CodGtVm, c.NomGtVm, c.FormaFarmaceutica, c.NomFormaFarmaceutica, c.CodGtVmpp,   ";
			qry+=  " c.NomGtVmpp, emblistable, sustituible, pvl ";
  			qry+=  " from dbo.bd_consejo c ";
  			qry+=  " where 1=1 ";
  			qry+=  " and c.codigo = '"+cn6+"' ";
  		System.out.println("BdConsejoDAO.getByCN -->" +qry );		
  		Connection con = Conexion.conectar();
   		
  		ResultSet resultSet = null;
  		BdConsejo  bdc= null;
  		try {
  			PreparedStatement pstat = con.prepareStatement(qry);
  			resultSet = pstat.executeQuery();
  			if(resultSet.next())
  				bdc =creaObjeto(resultSet);
  		
  		} catch (SQLException e) {
       e.printStackTrace();
  		}
  		finally {con.close();}

   return bdc;
}
    
    
    
    
    public static BdConsejo getBdConsejobyCN(String cn6) throws ClassNotFoundException, SQLException {
    	BdConsejoForm form = new BdConsejoForm();
    	form.setCnConsejo(cn6);
    	List bdConsejos = getBdConsejo(form, 0, 1);
    	if(bdConsejos!=null && bdConsejos.size()>0)
    		return (BdConsejo) bdConsejos.get(0);
    	else return null;
     }

    
    public static List<BdConsejo> getBdConsejo(BdConsejoForm form, int inicio, int fin) throws ClassNotFoundException , SQLException {
     	
	    	 //String qry = getQuerySustituciones( form.getCnConsejo(),  form.getNombreCortoOK(), null, null, false, false, inicio, fin);
  		String qry = getQuerySustituciones(form.getCnConsejo(), form.getNombreCortoOK()
				, form.getFiltroCodiLaboratorio(),  form.getNombreLab(), false, false, inicio, fin
				, form.getFiltroCodGtVm(), form.getFiltroNomGtVm(),   form.getFiltroCodGtVmp(), form.getFiltroNomGtVmp(), form.getFiltroCodGtVmpp(), form.getFiltroNomGtVmpp()  );


	    	 System.out.println("BdConsejoDAO.getBdConsejo -->" +qry );		
	    	 Connection con = Conexion.conectar();
	    //		 System.out.println("connected main" );
       
        	//	qry+= " offset 20 rows ";
        	//	qry+= " fetch next 10 rows only";
        		
         ResultSet resultSet = null;
 		List<BdConsejo> listaBdConsejo= new ArrayList<BdConsejo>();
       try {
            PreparedStatement pstat = con.prepareStatement(qry);
        //    pstat.setString(1, employee.getempNo());
            resultSet = pstat.executeQuery();

            while (resultSet.next()) {
            	BdConsejo  c =creaObjeto(resultSet);
            	if(c!=null)
            		listaBdConsejo.add(c);
            }
            
            
           
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {con.close();}
 
        return listaBdConsejo;
    }


	public static String getQuerySustituciones(String codigoCN, String nombreMedicamento, String codiLabo, String nomLabo, boolean LookUpLabs, boolean count, int inicio, int fin) {
	
		 	     return getQuerySustituciones(codigoCN,  nombreMedicamento,  codiLabo,  nomLabo,  LookUpLabs,  count,  inicio,  fin, null, null, null, null, null, null);
		 	 }
	
	public static String getQuerySustituciones(String codigoCN, String nombreMedicamento, String codiLabo
				, String nomLabo, boolean LookUpLabs, boolean count, int inicio, int fin
				, String codGtVm, String nomGtVm, String codGtVmp,  String nomGtVmp, String codGtVmpp,  String nomGtVmpp
				) 
	{

 
   		
   		
   		
		String qry = "select * from ( select  distinct ROW_NUMBER() OVER(ORDER BY c.nomLABO, coalesce(c.nombre, '') + ' ' + coalesce(c.presentacion, '')) AS ROWNUM,  c.codigo, coalesce(c.nombre, '') + ' ' + coalesce(c.presentacion, '') as nombreConsejo,  c.codiLABO, c.nomLABO, c.PVL , c.CodGtVmp, c.NomGtVmp, c.CodGtVm, c.NomGtVm, c.FormaFarmaceutica, c.nomFormaFarmaceutica, c.CodGtVmpp, c.NomGtVmpp, c.emblistable, c.sustituible  ";
		if(LookUpLabs)  
			 qry = "select distinct codiLABO, nomLABO  from   ( select distinct ROW_NUMBER() OVER(order by c.nomLABO) AS ROWNUM,  c.codiLABO, c.nomLABO, c.PVL  ";
		
			//si es contador inicializo la query
		if(count)  
			qry = "select count(distinct c.codigo) as quants";
		if(count && LookUpLabs)  
			qry = "select count(distinct c.codiLABO) as quants";

		
		
		
		
   			qry+=  " from dbo.bd_consejo c ";
  			qry+=  " where 1=1 ";
  		if(codigoCN!=null && !codigoCN.equals(""))
  		{
			qry+=  " AND ( c.codigo = '"+StringUtil.quitaEspacios(codigoCN)+"' ";
  	  		
	  		if(codigoCN.startsWith("0"))
	  			qry+=" OR RIGHT(c.codigo, 6)=RIGHT('"+StringUtil.quitaEspacios(codigoCN)+"', 6)  ";
	  		else
	  			qry+=" OR LEFT(c.codigo, 6)= LEFT('"+StringUtil.quitaEspacios(codigoCN)+"', 6) ";

			qry+=  " ) ";
  		}
		

  		
  		
    	if(nombreMedicamento!=null && !nombreMedicamento.equals(""))
     		qry+=  " and  c.nombre like '%"+StringUtil.quitaEspacios(nombreMedicamento)+"%' ";
 		if(codiLabo!=null && !codiLabo.equals("")) 
 		{  	
 			qry+=  " and  c.codiLABO = '"+StringUtil.quitaEspacios(codiLabo)+"' ";
 		}
  	    if(codGtVm!=null && !codGtVm.equals(""))
  	    {
			qry+=  " and  c.CodGtVm =  '"+StringUtil.quitaEspacios(codGtVm) +"' ";
  	    }
	    if(codGtVmp!=null && !codGtVmp.equals(""))
	    {
			qry+=  " and c.CodGtVmp =  '"+StringUtil.quitaEspacios(codGtVmp) +"' ";
	    }
	    if(codGtVmpp!=null && !codGtVmpp.equals(""))
	    {
			qry+=  " and c.CodGtVmpp =  '"+StringUtil.quitaEspacios(codGtVmpp) +"' ";
	    }
		if(nomLabo!=null && nomLabo.trim()!=null&&  !nomLabo.trim().equals("") )
   			qry+=  " and LTRIM(RTRIM(c.nomLABO)) like '%"+StringUtil.quitaEspacios(nomLabo.trim())+"%'  ";
  	    if(nomGtVm!=null && nomGtVm.trim()!=null &&  !nomGtVm.trim().equals("") )
  	    {
			qry+=  " and LTRIM(RTRIM(c.NomGtVm)) like  '%"+StringUtil.quitaEspacios(nomGtVm.trim())+"%'  ";
  	    }
	    if(nomGtVmp!=null && nomGtVmp.trim()!=null && !nomGtVmp.trim().equals("") )
	    {
			qry+=  " and LTRIM(RTRIM(c.NomGtVmp)) like '%"+StringUtil.quitaEspacios(nomGtVmp.trim())+"%'  ";
	    }
	    if(nomGtVmpp!=null && nomGtVmpp.trim()!=null && !nomGtVmpp.trim().equals("") )
			qry+=  " and LTRIM(RTRIM(c.NomGtVmpp))= '"+StringUtil.quitaEspacios(nomGtVmpp.trim())+"' ";


	    
	    
	    
    	//if(!count) 
    	//{
    		//if(LookUpLabs)  
       			//qry+= "order by c.nomLABO";
    		//else
    			//qry+=  " order by nombreConsejo";

    		
    	//	qry+= " offset "+ (inicio) + " rows ";
    		//	qry+= " fetch next "+ fin + " rows only";
    	//}
    	
    	String otros = 	HelperSPD.getOtrosSql2008(inicio, fin, count);
    	
    	
   		
			return qry +otros;
	}

	/**
	 * Fetch es una cláusula que funciona a partir del SqlServer 2008 (no inclusive)
	 * @param form
	 * @param inicio
	 * @param fin
	 * @param count
	 * @return
	 */
	private static String getOtrosSqlPost2008(SustXComposicionForm form, int inicio, int fin, boolean count) 
	{
		String otros="";
		if(!count) 
		{
			otros+= " offset "+ (inicio) + " rows ";
			otros+= " fetch next "+(fin)+ " rows only";
		}
		return otros;
	}

	
	
	
	public static List<BdConsejo> getListaPresentacion() throws ClassNotFoundException, SQLException {
		   Connection con = Conexion.conectar();
	//	   System.out.println("connected getListaPresentacion()" );
		   List<BdConsejo>  result = new ArrayList();
		   String qry = "select distinct  coalesce(c.presentacion, '')  as presentacion ";
		    		qry+=  " from dbo.bd_consejo c ";
		 			qry+=  " order by coalesce(c.presentacion, '')";
					
			//   		System.out.println("getListaPresentacion -->" +qry );		
			     	ResultSet resultSet = null;
			    	 System.out.println("BdConsejoDAO.getListaPresentacion -->" +qry );		
	 	 	
		 	    try {
		 	         PreparedStatement pstat = con.prepareStatement(qry);
		 	         resultSet = pstat.executeQuery();
		 	         int count=0;
		 	         while (resultSet.next()) {
		 	        	BdConsejo f = new BdConsejo();
		 	        	 f.setPresentacion(resultSet.getString("presentacion"));
		 	        	result.add(f);
		 	            }
		 	     } catch (SQLException e) {
		 	         e.printStackTrace();
		 	     }finally {con.close();}
	
		 	     return result;
		 	 }
	

	public static List<BdConsejo> getListaGtVmDeConsejo() throws ClassNotFoundException, SQLException {
	
		 	     return getListaGtVmDeConsejo(null, false, false);
		 	 }

	
	public static List<BdConsejo> getListaGtVmDeConsejo(String codiLab, boolean labsAsignadosAGtVmp,  boolean labsNoAsignadosAGtVmp) throws ClassNotFoundException, SQLException {
		   Connection con = Conexion.conectar();
		//   System.out.println("connected getListaPrincipioActivo()" );
		   List<BdConsejo>  result = new ArrayList();
		   String qry = "select distinct c.NomGtVm as nomGtVm, ";
				   qry+= " c.CodGtVm as codGtVm  ";
		    		qry+=  " from dbo.bd_consejo c ";
		    		qry+=  " where c.CodGtVm<>0 ";
		    		
				   if(codiLab!=null && !codiLab.equals(""))
		    		qry+=  " and c.codiLABO ='"+codiLab+"' ";

					if(labsNoAsignadosAGtVmp)
						qry+=  " and c.codGtVmpp not in (select codGtVmpp from SPD_sustXComposicion)  ";
					if(labsAsignadosAGtVmp)
						qry+=  " and c.codGtVmpp  in (select codGtVmpp from SPD_sustXComposicion)  ";

					
				   qry+=  " order by c.NomGtVm";
					
		   		System.out.println("BdConsejoDAO.getListaGtVm -->" +qry );		
			     	ResultSet resultSet = null;
		 	 	
		 	    try {
		 	         PreparedStatement pstat = con.prepareStatement(qry);
		 	         resultSet = pstat.executeQuery();
		 	         int count=0;
		 	         while (resultSet.next()) {
		 	        	BdConsejo f = new BdConsejo();
		 	        	// f.setCodigoPrincipioActivo(resultSet.getInt("codigoPrincipioActivo"));
		 	        	// f.setNombrePrincipioActivo(resultSet.getString("nombrePrincipioActivo"));
		 	        	 f.setCodGtVm(resultSet.getString("CodGtVm"));
		 	        	 f.setNomGtVm(resultSet.getString("NomGtVm"));
		 	        	result.add(f);
		 	            }
		 	     } catch (SQLException e) {
		 	         e.printStackTrace();
		 	     }finally {con.close();}

		 	     return result;
		 	 }

	
	 public static List<BdConsejo> 	getListaGtVmp() throws ClassNotFoundException, SQLException {
		 return getListaGtVmp(null, null);
	 }
	 
		public static List<BdConsejo> getListaGtVmp(String codGtVm, String nomGtVm) throws ClassNotFoundException, SQLException {

		 	     return getListaGtVmp( null, codGtVm, nomGtVm, false, false);
		 	 }
		
	public static List<BdConsejo> getListaGtVmp(String codiLab, String codGtVm, String nomGtVm, boolean labsAsignadosAGtVmp,  boolean labsNoAsignadosAGtVmp) throws ClassNotFoundException, SQLException {
		   Connection con = Conexion.conectar();
		   List<BdConsejo>  result = new ArrayList();
		   String	qry = " select distinct c.CodGtVmp,  c.NomGtVmp ";
		    		qry+=  " from dbo.bd_consejo c ";
			    	qry+=  " WHERE 1=1 ";

		 		if(codGtVm!=null && !codGtVm.equals(""))
					qry+=  " AND c.codGtVm =  '"+codGtVm +"' ";
		 		if(nomGtVm!=null && !nomGtVm.equals(""))
					qry+=  " AND c.nomGtVm =  '"+nomGtVm +"' ";
				if(codiLab!=null && !codiLab.equals(""))
					qry+=  " AND c.codiLABO =  '"+codiLab +"' ";
				if(labsNoAsignadosAGtVmp)
					qry+=  " and c.CodGtVmp not in (select codGtVmpp from SPD_sustXComposicion)  ";
				if(labsAsignadosAGtVmp)
					qry+=  " and c.CodGtVmp  in (select codGtVmpp from SPD_sustXComposicion)  ";

					   
		    	qry+=  " order by c.NomGtVmp";
		   		System.out.println("BdConsejoDAO.getGtVmp -->" +qry );		
			
		 	ResultSet resultSet = null;
	 	    try {
	 	         PreparedStatement pstat = con.prepareStatement(qry);
	 	         resultSet = pstat.executeQuery();
	 	         int count=0;
	 	         while (resultSet.next()) {
	 	        	BdConsejo f = new BdConsejo();
	 	        	 f.setCodGtVmp(resultSet.getString("CodGtVmp"));
	 	        	 f.setNomGtVmp(resultSet.getString("NomGtVmp"));
	 	        	result.add(f);
	 	            }
	 	     } catch (SQLException e) {
	 	         e.printStackTrace();
	 	     }finally {con.close();}
	 	     return result;
	 	 }
	

	
	/**
	 * --Virtual Medicinal Product Pack -->  Equivale al conjunto homogeneo
	 * @param codPAactivo
	 * @param codiLab
	 * @param labsAsignadosAGtVmp
	 * @param labsNoAsignadosAGtVmp
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException 
	 * 
	 */
	/*
	public static List<BdConsejo> getGtVmpp(String codiLab, String codPAactivo, String codGtVmp,  boolean labsAsignadosAGtVmp,  boolean labsNoAsignadosAGtVmp) throws ClassNotFoundException {
		   Connection con = Conexion.conectar();
		   List<BdConsejo>  result = new ArrayList();
		   String	qry = " select distinct c.CodGtVmpp,  c.NomGtVmpp ";
		    		qry+=  " from dbo.bd_consejo c ";
			    	qry+=  " WHERE 1=1 ";

			    if(codPAactivo!=null && !codPAactivo.equals(""))
					qry+=  " AND c.codiPactivo =  '"+codPAactivo +"' ";
				if(codiLab!=null && !codiLab.equals(""))
					qry+=  " AND c.codiLABO =  '"+codiLab +"' ";
				if(codGtVmp!=null && !codGtVmp.equals(""))
					qry+=  " and c.CodGtVmp =  '"+codGtVmp +"' ";
				if(labsNoAsignadosAGtVmp)
					qry+=  " and c.CodGtVmpp not in (select codGtVmpp from SPD_sustXComposicion)  ";
				if(labsAsignadosAGtVmp)
					qry+=  " and c.CodGtVmpp  in (select codGtVmpp from SPD_sustXComposicion)  ";

					   
		    	qry+=  " order by c.NomGtVmpp";
		   		System.out.println("getGtVmpp -->" +qry );		
			
		 	ResultSet resultSet = null;
	 	    try {
	 	         PreparedStatement pstat = con.prepareStatement(qry);
	 	         resultSet = pstat.executeQuery();
	 	         int count=0;
	 	         while (resultSet.next()) {
	 	        	BdConsejo f = new BdConsejo();
	 	        	 f.setCodGtVmpp(resultSet.getString("CodGtVmpp"));
	 	        	 f.setNomGtVmpp(resultSet.getString("NomGtVmpp"));
	 	        	result.add(f);
	 	            }
	 	     } catch (SQLException e) {
	 	         e.printStackTrace();
	 	     }
	 	     return result;
	 	 }
	*/


	 public static List<BdConsejo> getListaGtVmpp() throws ClassNotFoundException, SQLException {
		 return getListaGtVmpp(null, null, null, null);
	 }


	 public static List<BdConsejo> getListaGtVmpp (String codGtVm, String nomGtVm, String codGtVmp, String nomGtVmp) throws ClassNotFoundException, SQLException {
		 
		 	     return getListaGtVmpp(null, codGtVm, null,  codGtVmp, null, false,   false) ;
	 }

	 /**
	  * 	/**
	 * --Virtual Medicinal Product Pack -->  Equivale al conjunto homogeneo
	  * @param codiLab
	  * @param codPAactivo
	  * @param codiGtVmp
	  * @param labsAsignadosAGtVmp
	  * @param labsNoAsignadosAGtVmp
	  * @return
	  * @throws ClassNotFoundException
	 * @throws SQLException 
	  */
	 
	 public static List<BdConsejo> getListaGtVmpp(String codiLab, String codGtVm, String nomGtVm, String codGtVmp, String nomGtVmp, boolean labsAsignadosAGtVmp,  boolean labsNoAsignadosAGtVmp) throws ClassNotFoundException, SQLException {
		   Connection con = Conexion.conectar();
		//  System.out.println("connected getListaConjuntosHomogeneos()" );
		   List<BdConsejo>  result = new ArrayList();
		   String qry = "select distinct c.CodGtVmpp as CodGtVmpp, c.NomGtVmpp as NomGtVmpp ";
		    	qry+=  " from dbo.bd_consejo c ";
		    	qry+=  " WHERE 1=1 ";
		    		

		    if(codGtVm!=null && !codGtVm.equals(""))
		    	qry+=  " and c.codGtVm =  '"+codGtVm +"' ";
		    if(nomGtVm!=null && !nomGtVm.equals(""))
		    	qry+=  " and c.nomGtVm =  '"+nomGtVm +"' ";
			if(codGtVmp!=null && !codGtVmp.equals(""))
				qry+=  " and c.codGtVmp =  '"+codGtVmp +"' ";
			if(nomGtVmp!=null && !nomGtVmp.equals(""))
				qry+=  " and c.nomGtVmp =  '"+nomGtVmp +"' ";
			if(codiLab!=null && !codiLab.equals(""))
				qry+=  " and c.codiLABO =  '"+codiLab +"' ";
			if(labsNoAsignadosAGtVmp)
				qry+=  " and c.codGtVmpp not in (select codGtVmpp from SPD_sustXComposicion)  ";
			if(labsAsignadosAGtVmp)
				qry+=  " and c.codGtVmpp  in (select codGtVmpp from SPD_sustXComposicion)  ";

					    
		    
				qry+=  " order by c.NomGtVmpp";
					
				System.out.println("BdConsejoDao.getListaGtVmpp -->" +qry );		
			 	ResultSet resultSet = null;
		 	 	
		 	    try {
		 	         PreparedStatement pstat = con.prepareStatement(qry);
		 	         resultSet = pstat.executeQuery();
		 	         int count=0;
		 	         while (resultSet.next()) {
		 	        	BdConsejo f = new BdConsejo();
		 	        	 f.setCodGtVmpp(resultSet.getString("CodGtVmpp"));
		 	        	 f.setNomGtVmpp(resultSet.getString("NomGtVmpp"));
		 	        	result.add(f);
		 	            }
		 	     } catch (SQLException e) {
		 	         e.printStackTrace();
		 	     }finally {con.close();}

		 	     return result;
	 }

	public static int getCountLabsBdConsejo( String codiLabo, String nomLabo) throws ClassNotFoundException, SQLException {
	  	 String qry = getQuerySustituciones( null, null,  codiLabo,  nomLabo,  true, true, 0, 0);
		 System.out.println("BdConsejoDAO.getCountLabsBdConsejo -->" +qry );		
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
	   }
		finally {con.close();}
	   return result;
	}
	
	
	public static List<BdConsejo> getLabsBdConsejo(String codiLabo, String nomLabo, int inicio, int fin) throws ClassNotFoundException, SQLException {
	
		return getLabsBdConsejo(codiLabo, nomLabo, inicio, fin, null, null, null, null, null, null);
	}
	public static List<BdConsejo> getLabsBdConsejo(String codiLabo, String nomLabo, int inicio, int fin, String codGtVm, String nomGtVm, String codGtVmp, String nomGtVmp, String codGtVmpp, String nomGtVmpp) throws ClassNotFoundException, SQLException {
     	
   	 String qry = getQuerySustituciones(null, null, codiLabo, nomLabo, true, false, inicio, fin,  codGtVm, nomGtVm,  codGtVmp, nomGtVm, codGtVmpp, nomGtVmpp);
   	 System.out.println("BdConsejoDAO.getLabsBdConsejo -->" +qry );		
   	 Connection con = Conexion.conectar();
   //		 System.out.println("connected main" );
  
   	//	qry+= " offset 20 rows ";
   	//	qry+= " fetch next 10 rows only";
   		
    ResultSet resultSet = null;
	List<BdConsejo> listaLabBdConsejo= new ArrayList<BdConsejo>();
  try {
       PreparedStatement pstat = con.prepareStatement(qry);
   //    pstat.setString(1, employee.getempNo());
       resultSet = pstat.executeQuery();

       while (resultSet.next()) {
    	  	BdConsejo  c =new BdConsejo();
	       	c.setCodigoLaboratorio(resultSet.getString("codiLABO"));
	       	c.setNombreLaboratorio(resultSet.getString("nomLABO"));
	       	listaLabBdConsejo.add(c);
       }

   } catch (SQLException e) {
       e.printStackTrace();
   }finally {con.close();}

   return listaLabBdConsejo;
}

	
	public static List<BdConsejo> getBdConsejoByFilters(String cn, String codiLabo, String nomLabo, int inicio, int fin, String codGtVm, String codGtVmp, String codGtVmpp, String nomGtVm, String nomGtVmp, String nomGtVmpp) throws ClassNotFoundException, SQLException {
     	
	   	 String qry = getQuerySustituciones(cn, null, codiLabo, nomLabo, false, false, inicio, fin,  codGtVm,  codGtVmp, codGtVmpp, nomGtVm,  nomGtVmp,  nomGtVmpp);
	   	 System.out.println("BdConsejoDAO.getLabsBdConsejo -->" +qry );		
	   	 Connection con = Conexion.conectar();
	   //		 System.out.println("connected main" );
	  
	   	//	qry+= " offset 20 rows ";
	   	//	qry+= " fetch next 10 rows only";
	   		
	    ResultSet resultSet = null;
		List<BdConsejo> listaLabBdConsejo= new ArrayList<BdConsejo>();
	  try {
	       PreparedStatement pstat = con.prepareStatement(qry);
	   //    pstat.setString(1, employee.getempNo());
	       resultSet = pstat.executeQuery();

	       while (resultSet.next()) {
	       	BdConsejo  c =creaObjeto(resultSet);
	       	if(c!=null)
	       		listaLabBdConsejo.add(c);
	       }

	   } catch (SQLException e) {
	       e.printStackTrace();
	   }finally {con.close();}

	   return listaLabBdConsejo;
	}

	
	
	
	public static List<BdConsejo> getLabsAsignadosAGtVmp(String filtroCodiLaboratorioBiblia, String filtroNombreLaboratorioBiblia, int inicio, int fin) throws ClassNotFoundException, SQLException 
	{
		String qry = "select distinct c.codiLABO, c.nomLABO  ";
	    	qry+=  " from dbo.bd_consejo c ";
	    	qry+=  " WHERE 1=1 ";
	    		
			if(filtroCodiLaboratorioBiblia!=null && !filtroCodiLaboratorioBiblia.equals(""))
	   			qry+=  " and c.codiLABO = '"+filtroCodiLaboratorioBiblia+"' ";
	    	if(filtroNombreLaboratorioBiblia!=null && !filtroNombreLaboratorioBiblia.equals(""))
	     		qry+=  " and  c.nomLABO like '%"+filtroNombreLaboratorioBiblia+"%' ";

			qry+=  " order by c.nomLABO";
   		
    		qry+= " offset "+ (inicio) + " rows ";
    		qry+= " fetch next "+ fin + " rows only";

	   			 
	   	 System.out.println("BdConsejoDAO.getLabsAsignadosAGtVmp -->" +qry );		
	   	 Connection con = Conexion.conectar();
	  

	    ResultSet resultSet = null;
		List<BdConsejo> listaLabBdConsejo= new ArrayList<BdConsejo>();
	  try {
	       PreparedStatement pstat = con.prepareStatement(qry);
	   //    pstat.setString(1, employee.getempNo());
	       resultSet = pstat.executeQuery();

	       while (resultSet.next()) {
	       	BdConsejo  c =new BdConsejo();
	       	c.setCodigoLaboratorio(resultSet.getString("codiLABO"));
	       	c.setNombreLaboratorio(resultSet.getString("nomLABO"));
	       	listaLabBdConsejo.add(c);
	       }
	   } catch (SQLException e) {
	       e.printStackTrace();
	   }finally {con.close();}

	   return listaLabBdConsejo;
	}
	
	
	/**
	 * Método encargado de crear un objeto BdConsejo según el resultSet
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	
	private static BdConsejo creaObjeto(ResultSet resultSet) throws SQLException 
	{
		BdConsejo  c =null;
		if(resultSet!=null){
			c =new BdConsejo();   
			
			c.setCnConsejo(resultSet.getString("codigo"));
		//	c.setNombreConsejo(resultSet.getString("nombreConsejo"));
        	c.setNombreConsejo(resultSet.getString("nombreConsejo") );
        			
        	c.setCodigoLaboratorio(resultSet.getString("codiLABO"));
        	c.setNombreLaboratorio(resultSet.getString("nomLABO"));
//        	c.setCodigoPrincipioActivo(resultSet.getInt("codiPactivo"));
//        	c.setNombrePrincipioActivo(resultSet.getString("nomPactivo"));
        	c.setCodGtVm(resultSet.getString("CodGtVm")); 	// TODO 20201001 - ahora es GTVM. Cuando exista la tabla en Consejo se debería cambiar. De momento la seguimos pillando de codiPactivo
        	c.setNomGtVm(resultSet.getString("NomGtVm"));	// TODO 20201001 - ahora es GTVM. Cuando exista la tabla en Consejo se debería cambiar. De momento la seguimos pillando de nomPactivo
        	c.setCodGtVmp(resultSet.getString("CodGtVmp"));
           	c.setNomGtVmp(resultSet.getString("NomGtVmp"));
        	c.setCodGtVmpp(resultSet.getString("CodGtVmpp"));
          	c.setNomGtVmpp(resultSet.getString("NomGtVmpp"));               	
           	c.setNombreFormaFarmaceutica(resultSet.getString("nomFormaFarmaceutica"));
           	c.setPvl(resultSet.getFloat("pvl"));
           	c.setEmblistable(resultSet.getString("emblistable")); 
           	c.setSustituible(resultSet.getString("sustituible"));
           	
		}
    	return c;
	}

	public static List<BdConsejo> getBdConsejoByFiltersAuto(Object object, Object object2, Object object3, int i, int j,
			Object object4, Object object5, Object object6, String term, Object object7, Object object8) {
		// TODO Auto-generated method stub
		return null;
	}

	public static List<BdConsejo> getAutoGtVm(String term) throws SQLException {
		
		List<BdConsejo> result = new ArrayList<BdConsejo>();
		String qry = "select distinct CodGtVm, NomGtVm from bd_consejo where NomGtVm like '%" +term+ "%'";
		qry+=  " order by NomGtVm";
	   			 
		System.out.println("BdConsejoDAO.getAutoGtVm -->" +qry );		
   		Connection con = Conexion.conectar();
  

   		ResultSet resultSet = null;

  try {
       PreparedStatement pstat = con.prepareStatement(qry);
       resultSet = pstat.executeQuery();

       while (resultSet.next()) {
	       	BdConsejo  c =new BdConsejo();
	       	c.setCodGtVm(resultSet.getString("CodGtVm"));
	       	c.setNomGtVm(resultSet.getString("NomGtVm"));
	       	result.add(c);
	       }

   } catch (SQLException e) {
       e.printStackTrace();
   }finally {con.close();}

   return result;
}

	public static List<BdConsejo> getAutoLabs(String term, String CodGtVm) throws SQLException {
		List<BdConsejo> result = new ArrayList<BdConsejo>();
		String qry = "SELECT DISTINCT codiLABO, nomLABO ";
		qry+=  " FROM bd_consejo ";
		qry+=  " WHERE nomLABO LIKE '%" +term+ "%' "; 
		if(CodGtVm!=null && !CodGtVm.equalsIgnoreCase(""))
			qry+=  " AND  CodGtVm = '" +CodGtVm+ "' "; 
		qry+=  " ORDER BY nomLABO";
	   			 
		System.out.println("BdConsejoDAO.getAutoLabs -->" +qry );		
   		Connection con = Conexion.conectar();
  

   		ResultSet resultSet = null;

  try {
       PreparedStatement pstat = con.prepareStatement(qry);
       resultSet = pstat.executeQuery();

       while (resultSet.next()) {
	       	BdConsejo  c =new BdConsejo();
	       	c.setCodigoLaboratorio(resultSet.getString("codiLABO"));
	       	c.setNombreLaboratorio(resultSet.getString("nomLABO"));
	       	result.add(c);
	       }

   } catch (SQLException e) {
       e.printStackTrace();
   }finally {con.close();}

   return result;
}

	public static BdConsejo getBdCNPorNombre(String nombrePresentacion) throws ClassNotFoundException , SQLException {
    	
  		String qry = "select distinct c.codigo, coalesce(c.nombre, '') + ' ' + coalesce(c.presentacion, '') as nombreConsejo,  ";
  			qry+=  " c.codiLABO, c.nomLABO, c.CodGtVmp, c.NomGtVmp, c.CodGtVm, c.NomGtVm, c.FormaFarmaceutica, c.NomFormaFarmaceutica, c.CodGtVmpp,   ";
			qry+=  " c.NomGtVmpp, emblistable, sustituible, pvl ";
  			qry+=  " from dbo.bd_consejo c ";
  			qry+=  " where 1=1 ";
  			//qry+=  " and coalesce(c.nombre, '') + ' - ' + coalesce(c.presentacion, '') = '"+nombrePresentacion+"' "; //importante conservar el guión y espacios ' - '

  			qry+=  " AND ( ";
  			qry+=  " 	    REPLACE(UPPER(COALESCE(c.nombre, '') + '-' + COALESCE(c.presentacion, '')), ' ', '') LIKE ";
  			qry+=  " 	        REPLACE(UPPER(REPLACE('"+nombrePresentacion+"', ' ', '')), ' ', '') + '%'";
  			qry+=  " 	    OR";
  			qry+=  " 	    REPLACE(UPPER(REPLACE('"+nombrePresentacion+"', ' ', '')), ' ', '') LIKE ";
  			qry+=  " 	        REPLACE(UPPER(COALESCE(c.nombre, '') + '-' + COALESCE(c.presentacion, '')), ' ', '') + '%'";
  			qry+=  " 	) ";
  			
  		System.out.println("BdConsejoDAO.getBdCNPorNombre -->" +qry );		
  		Connection con = Conexion.conectar();
   		
  		ResultSet resultSet = null;
  		BdConsejo  bdc= null;
  		try {
  			PreparedStatement pstat = con.prepareStatement(qry);
  			resultSet = pstat.executeQuery();
  			if(resultSet.next())
  				bdc =creaObjeto(resultSet);
  		
  		} catch (SQLException e) {
       e.printStackTrace();
  		}
  		finally {con.close();}

   return bdc;
}
    
		

}

	

 
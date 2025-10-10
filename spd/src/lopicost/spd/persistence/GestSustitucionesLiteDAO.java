package lopicost.spd.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lopicost.config.pool.dbaccess.Conexion;
import lopicost.spd.model.BdConsejo;
import lopicost.spd.model.GestSustitucionesLite;
import lopicost.spd.security.helper.VisibilidadHelper;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.struts.bean.TiposAccionBean;
import lopicost.spd.struts.form.GestSustitucionesLiteForm;
import lopicost.spd.utils.DataUtil;
import lopicost.spd.utils.HelperSPD;
import lopicost.spd.utils.SPDConstants;
import lopicost.spd.utils.StringUtil;
 
 
public class GestSustitucionesLiteDAO extends GestSustitucionesDAO{
	
	
	   public static GestSustitucionesLite getSustitucionLiteByOid(String spdUsuario, int oid) throws Exception {
	    	
	    	GestSustitucionesLite c=null;
	    	String qry = getQuerySustitucionesLite(spdUsuario,  -1, oid, null, null, null, null,  0, 1, false);
	 	    Connection con = Conexion.conectar();
	        ResultSet resultSet = null;
  
	        try {
	            PreparedStatement pstat = con.prepareStatement(qry);
	            resultSet = pstat.executeQuery();
	             
	           while (resultSet.next()) {
	        		//creaci�n del objeto sustituci�n base
	        	   c = creaObjeto(resultSet);
	       		}
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        finally {con.close();}
	        return c;
	    }


	   public static GestSustitucionesLite getSustitucionLiteByCN(String spdUsuario, GestSustitucionesLiteForm f) throws Exception {
	    	
	    	GestSustitucionesLite c=null;
	    	String qry = getQuerySustitucionesLite(spdUsuario,  f.getOidDivisionResidenciaFiltro(), -1, f.getResiCn(), null, null, null,  0, 1, false);
	 	    Connection con = Conexion.conectar();
	        ResultSet resultSet = null;
  
	        try {
	            PreparedStatement pstat = con.prepareStatement(qry);
	            resultSet = pstat.executeQuery();
	             
	           while (resultSet.next()) {
	        		//creaci�n del objeto sustituci�n base
	        	   c = creaObjeto(resultSet);
	       		}
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        finally {con.close();}
	        return c;
	    }
  
	private static GestSustitucionesLite creaObjeto(ResultSet resultSet) throws SQLException {
		GestSustitucionesLite c=new GestSustitucionesLite();
		c.setOidGestSustitucionesLite(resultSet.getInt("oidGestSustitucionesLite"));
    	c.setFecha(resultSet.getDate("fecha"));
    	c.setIdDivisionResidencia(resultSet.getString("idDivisionResidencia"));
    	c.setOidDivisionResidencia(resultSet.getInt("oidDivisionResidencia"));
    	c.setResiCn(resultSet.getString("resiCn"));
       	c.setResiMedicamento(resultSet.getString("resiMedicamento"));
       	c.setSpdCn(resultSet.getString("spdCn"));
       	c.setSpdNombreMedicamento(resultSet.getString("spdNombreMedicamento"));
       	c.setSpdNombreBolsa(resultSet.getString("spdNombreBolsa"));
     	c.setSpdFormaMedicacion(resultSet.getString("spdFormaMedicacion"));
    	c.setSpdAccionBolsa(resultSet.getString("spdAccionBolsa"));
    	c.setExcepciones(resultSet.getString("excepciones"));
    	c.setAux1(resultSet.getString("aux1"));
    	c.setAux2(resultSet.getString("aux2"));
    	c.setCodGtVm(resultSet.getString("codGtVm"));
       	c.setNomGtVm(resultSet.getString("nomGtVm"));
       	c.setCodGtVmp(resultSet.getString("codGtVmp"));
       	c.setNomGtVmp(resultSet.getString("nomGtVmp"));
       	c.setNomLABO(resultSet.getString("nomLABO"));
        
		return c;

	}

	   
	   
    public static GestSustitucionesLite getSustitucionLiteByOid(String spdUsuario, GestSustitucionesLiteForm gestSustitucionesLiteForm) throws Exception {
    	
    	GestSustitucionesLite result=null;
    	
    	//actualizamos el oid por defecto a 0 para que la select tenga en cuenta la condici�n
    	if(gestSustitucionesLiteForm.getOidGestSustitucionesLite()==-1)
    		gestSustitucionesLiteForm.setOidGestSustitucionesLite(0);
    	
    	List<GestSustitucionesLite> sustitucionesLite =getSustitucionesListadoLite(spdUsuario, gestSustitucionesLiteForm, 0, 1, false);
    	if(sustitucionesLite!=null && sustitucionesLite.size()>0)
    		result=sustitucionesLite.get(0);
    	
    	return result;

}
    
    public static int  getCountSustitucionesLite(String spdUsuario, GestSustitucionesLiteForm form) throws Exception {
    	
        String qry = getQuerySustitucionesLite(spdUsuario,  form.getOidDivisionResidenciaFiltro(), form.getOidGestSustitucionesLite(), form.getResiCn(), form.getFiltroGtVm(), form.getFiltroGtVmp(), form.getCampoGoogle(), 0, 0, true);
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
      }finally {con.close();}

      return result;
   }
 
    public static List<GestSustitucionesLite> getSustitucionesListadoLite(String spdUsuario, GestSustitucionesLiteForm form, int inicio, int fin, boolean count) throws Exception {
    	return getSustitucionesListadoLite( spdUsuario,  form.getOidDivisionResidenciaFiltro(), form.getOidGestSustitucionesLite(), form.getResiCn(), form.getFiltroGtVm(), form.getFiltroGtVmp(), form.getCampoGoogle(), inicio, fin, count );
    }	
    
  		
	public static boolean editaSustLite(String spdUsuario, GestSustitucionesLite sust) throws Exception {

        int result=0;
		  Connection con = Conexion.conectar();
	  	   String qry = "update dbo.SPD_sustitucionesLite ";
	  	   		qry+= " set  spdCn= '"+sust.getSpdCn()+"', ";
	  	   		qry+= " spdNombreBolsa= '"+sust.getSpdNombreBolsa()+"', ";
	  	   		qry+= " spdFormaMedicacion= '"+sust.getSpdFormaMedicacion()+"', ";
	  	   		qry+= " spdAccionBolsa= '"+sust.getSpdAccionBolsa()+"', ";
	  	   		qry+= " excepciones = '"+sust.getExcepciones()+"', ";
	  	   		qry+= " aux1= '"+sust.getAux1()+"', ";
	  	   		qry+= " aux2= '"+sust.getAux2()+"' ";
		   		qry+= " WHERE idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
	       		qry+= " AND idDivisionResidencia= '"+sust.getIdDivisionResidencia()+"' ";
	       		qry+= " AND  resiCn='"+StringUtil.limpiarTextoEspaciosYAcentos(sust.getResiCn(), false)+"' ";
	      		System.out.println("editaSustLite-->  " +qry );		
	    try {
	         PreparedStatement pstat = con.prepareStatement(qry);
	         result=pstat.executeUpdate();
	       
	     } catch (SQLException e) {
	         e.printStackTrace();
	     }
		
		return result>0;
	}
	
	public static boolean borraSustLite(String spdUsuario, GestSustitucionesLite sust) throws Exception {

        int result=0;
		  Connection con = Conexion.conectar();
	  	   String qry = "delete dbo.SPD_sustitucionesLite ";
	   			qry+= " WHERE idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
	       		qry+= " AND idDivisionResidencia= '"+sust.getIdDivisionResidencia()+"' and  resiCn='"+StringUtil.limpiarTextoEspaciosYAcentos(sust.getResiCn(), false)+"' ";
		   		System.out.println("borraSustLite -->" +qry );		
	 	
	    try {
	         PreparedStatement pstat = con.prepareStatement(qry);
	         result=pstat.executeUpdate();
	       
	     } catch (SQLException e) {
	         e.printStackTrace();
	     }finally {con.close();}

		
		return result>0;
	}
	
	
	public static boolean borraSustLiteByOid(String spdUsuario, int oidGestSustitucionesLite) throws Exception {

        int result=0;
		  Connection con = Conexion.conectar();
	  	   String qry = "delete dbo.SPD_sustitucionesLite ";
	   		qry+= " 	WHERE idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
	       		qry+= " AND oidGestSustitucionesLite='"+oidGestSustitucionesLite+"'";
		   		System.out.println("borraSustLiteByOid -->" +qry );		
	 	
	    try {
	         PreparedStatement pstat = con.prepareStatement(qry);
	         result=pstat.executeUpdate();
	       
	     } catch (SQLException e) {
	         e.printStackTrace();
	     }

		
		return result>0;
	}
	
	
	public static boolean nuevoSustLite(GestSustitucionesLiteForm f) throws ClassNotFoundException, SQLException {
	       int result =0;
	        
			  Connection con = Conexion.conectar();

		 	  	   String qry = " INSERT INTO dbo.SPD_sustitucionesLite (fecha, idDivisionResidencia, resiCn, resiMedicamento, spdCn, ";
		 	  	   		  qry+= " spdNombreBolsa, spdFormaMedicacion, spdAccionBolsa, excepciones, aux1, aux2) VALUES ";
		       		qry+= " (CONVERT(datetime, getdate(), 120), ";
		       		qry+= "'"+ f.getIdDivisionResidencia()+"', ";
		       		qry+= "'"+ StringUtil.limpiarTextoEspaciosYAcentos(f.getResiCn(), false)+"', ";
		       		qry+= "'"+ f.getResiMedicamento()+"', ";
		       		qry+= "'"+ f.getSpdCn() +"', ";
		       		qry+= "'"+ f.getSpdNombreBolsa() +"', ";
		       		//qry+= "'"+ f.getFiltroFormaFarmaceutica() +"', ";
		       		qry+= " (select nomFormaFarmaceutica from bd_consejo where codigo='"+f.getSpdCn()  +"'), ";
		       		qry+= "'"+ f.getIdTipoAccion()+"', ";
		       		qry+= "'"+ f.getExcepciones()+"', ";
		       		qry+= "'"+ f.getAux1()+"', ";
		       		qry+= "'"+ f.getAux2()+"' ";
		       		qry+= " )";
		       		System.out.println("nuevoSustLite f -->" +qry );		
		    try {
		         PreparedStatement pstat = con.prepareStatement(qry);
		         result=pstat.executeUpdate();
				 	
	     } catch (SQLException e) {
		         e.printStackTrace();
		     }
		    finally {con.close();}
			return result>0;
		}
	
	
	public static boolean nuevoSustLite(GestSustitucionesLite lite) throws ClassNotFoundException, SQLException {
	       int result =0;
	        
			  Connection con = Conexion.conectar();

		 	  	   String qry = " INSERT INTO dbo.SPD_sustitucionesLite (fecha, idDivisionResidencia, resiCn, resiMedicamento, spdCn, ";
		 	  	   		  qry+= " spdNombreBolsa, spdFormaMedicacion, spdAccionBolsa, excepciones, aux1, aux2) VALUES ";
		       		qry+= " (CONVERT(datetime, getdate(), 120), ";
		       		qry+= "'"+ lite.getIdDivisionResidencia()+"', ";
			       		qry+= "'"+ StringUtil.limpiarTextoEspaciosYAcentos(lite.getResiCn(), false)+"', ";
		       		qry+= "'"+ lite.getResiMedicamento()+"', ";
		       		qry+= "'"+ lite.getSpdCn() +"', ";
		       		qry+= "'"+ lite.getSpdNombreBolsa() +"', ";
		       		qry+= "'"+ lite.getSpdFormaMedicacion() +"', ";
		       		qry+= "'"+ lite.getSpdAccionBolsa()+"', ";
		       		qry+= "'"+ lite.getExcepciones()+"', ";
		       		qry+= "'"+ lite.getAux1()+"', ";
		       		qry+= "'"+ lite.getAux2()+"' ";
		       		qry+= " )";
		       		System.out.println("nuevoSustLite lite -->" +qry );		
		    try {
		         PreparedStatement pstat = con.prepareStatement(qry);
		         result=pstat.executeUpdate();
				 	
	     } catch (SQLException e) {
		         e.printStackTrace();
		     }finally {con.close();}

			return result>0;
		}
	
	
	public static boolean buscaSustitucionLite(String spdUsuario, FicheroResiBean medResi) throws Exception {
		boolean trobat=false;
		Connection con = Conexion.conectar();
		
		String cnResi=StringUtil.limpiarTextoEspaciosYAcentos(medResi.getResiCn(), false);
		String nombreMedicamento=StringUtil.limpiarTextoEspaciosYAcentos(medResi.getResiMedicamento(), true);
		
		String select =" select fecha, idDivisionResidencia, resiCn, resiMedicamento, spdCn, spdNombreBolsa, spdFormaMedicacion, spdAccionBolsa, excepciones, aux1, aux2  ";
		String 	from = " from SPD_sustitucionesLite ";
		//String 	where= " WHERE idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
		String 	where= " WHERE 1 = 1 "; // para no sobrecargar eliminamos la condici�n de la visibilidad. En este punto el usuario tendr�a permisos 
		where+=" AND idDivisionResidencia='"+medResi.getIdDivisionResidencia()+"' ";
		//if((cnResi!=null && !cnResi.isEmpty()) || !DataUtil.isNumero(cnResi))
		if((cnResi!=null && !cnResi.isEmpty()) || DataUtil.isNumero(cnResi))
		{
			where+=" AND ( ";
			where+="  replace(replace(replace(replace(replace(replace(resiCN, '�', ''), ';', ''), ',', ''), '-', ''), ' ', ''), '.', '')='"+ cnResi +"' ";
			where+=" 	 OR spdCn = '"+StringUtil.limpiarTextoEspaciosYAcentos(medResi.getResiCn(), false)+"' "; 	//si ya llega con el CN de sust final tambi�n se deber�a recoger
			if(cnResi.startsWith("0"))
				where+=" OR RIGHT(replace(replace(replace(replace(replace(replace(resiCN, '�', ''), ';', ''), ',', ''), '-', ''), ' ', ''), '.', ''), 6)=RIGHT('"+ cnResi +"', 6) ";
			else
				where+=" OR LEFT(replace(replace(replace(replace(replace(replace(resiCN, '�', ''), ';', ''), ',', ''), '-', ''), ' ', ''), '.', ''), 6)=LEFT('"+ cnResi +"', 6) ";
			where+=" ) ";
		}
		else if(	
					(cnResi==null || cnResi.isEmpty()) //llega sin CN y con nombre, miramos de buscar por descripci�n (GDR)
					&& (nombreMedicamento!=null && !nombreMedicamento.isEmpty())
				)
		{
			where+=" AND ( ";
			where+=" 	UPPER(replace(replace(replace(replace(replace(replace(resiMedicamento, '�', ''), ';', ''), ',', ''), '-', ''), ' ', ''), '.', '')) = '"+ nombreMedicamento +"' ";
			where+=" ) ";
		}
		else //llega cn y nombre vac�o, no devolver� nada 
		{
			where = " WHERE 1 = 0 "; 
		}
		
		/*PARA QUITAR ACENTOS
		 * 
		 * 
		SELECT
	    REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(
	        resiCn,
	        N'�', N'a'), N'�', N'e'), N'�', N'i'), N'�', N'o'), N'�', N'u'),
	        N'�', N'A'), N'�', N'E'), N'�', N'I'), N'�', N'O'), N'�', N'U')
	FROM
	    SPD_sustitucionesLite
	WHERE
	    resiCn COLLATE Latin1_General_CI_AI LIKE '%[����������]%'
		*/
		
		
		String qry = select + from + where;

		System.out.println("buscaSustitucionLite -->" +qry );		

			
		ResultSet resultSet = null;
		//List<GestSustituciones> sustituciones= new ArrayList<GestSustituciones>();
		try {
	        PreparedStatement pstat = con.prepareStatement(qry);
	        resultSet = pstat.executeQuery();

	        if (resultSet.next()) {
	        	trobat = true;
	        	medResi.setSpdCnFinal(StringUtil.limpiarTextoEspaciosYAcentos(resultSet.getString("spdCn"), false));
	        	//medResi.setSpdNombreBolsa(resultSet.getString("spdNombreBolsa"));
	        	medResi.setSpdNombreBolsa(resultSet.getString("spdNombreBolsa"));
        		medResi.setSpdAccionBolsa(resultSet.getString("spdAccionBolsa"));
	        	//ponemos la acci�n de la sustituci�n solo en caso que no sea un SIPRECISA
	        	//if(medResi.getResiSiPrecisa()==null || (!medResi.getResiSiPrecisa().equalsIgnoreCase("X") && ( medResi.getSpdAccionBolsa() ==null || !medResi.getSpdAccionBolsa().equalsIgnoreCase(SPDConstants.SPD_ACCIONBOLSA_SI_PRECISA))))
        		if(medResi.getResiSiPrecisa()!=null && (medResi.getResiSiPrecisa().equalsIgnoreCase("X") || medResi.getResiSiPrecisa().equalsIgnoreCase("SIPRECISA")) )
        		{
	        		medResi.setSpdAccionBolsa(SPDConstants.SPD_ACCIONBOLSA_SI_PRECISA);
        		}
	    		
	        	//en caso que tenga 999 y PASTILLERO se pone como SOLO_INFO y se a�ade mensaje 
	        	if(medResi.getAsteriscos()!=null && medResi.getAsteriscos().equalsIgnoreCase("SI")&& medResi.getSpdAccionBolsa()!=null && medResi.getSpdAccionBolsa().equalsIgnoreCase(SPDConstants.SPD_ACCIONBOLSA_PASTILLERO))
	        	{
	        		medResi.setSpdAccionBolsa(SPDConstants.SPD_ACCIONBOLSA_SOLO_INFO);
	        		String mensaje = SPDConstants.INFO_INTERNA_REVISION_SUSTITUCION;
	        		
	        		if((medResi.getMensajesInfo()==null || medResi.getMensajesInfo().equals("")) )
	        		{
	        			medResi.setMensajesInfo(mensaje);
	        		}
	        		else
	        		{
		        		if(!medResi.getMensajesInfo().contains(mensaje))
		        		{
			        		if((medResi.getMensajesInfo()==null || medResi.getMensajesInfo().equals("")) )
		        				medResi.setMensajesInfo(mensaje);
		        		else 
	        				medResi.setMensajesInfo(medResi.getMensajesInfo() + " / " +  mensaje);
		        			
		        		}
	        		}
	        		

	        		
	        		
	        	}
	        	medResi.setSpdFormaMedicacion(resultSet.getString("spdFormaMedicacion"));
	        //	medResi.setSpdComentarioLopicost(resultSet.getString("excepciones"));
	        //	medResi.setMensajesInfo(resultSet.getString("aux1"));
	        	String excepciones =resultSet.getString("excepciones");
	        	String mensajesInfo =medResi.getMensajesInfo();
	        	
	    		boolean repetido = false;
	    		if(mensajesInfo!=null && !mensajesInfo.isEmpty() && excepciones!=null && !excepciones.isEmpty())
	    			repetido=mensajesInfo.contains(excepciones);
	    		
	        	//borramos  posibles duplicados
	        	int i =0;
	        	while (mensajesInfo!=null && excepciones!=null &&repetido && i<5)
	        	{
	        		mensajesInfo=mensajesInfo.replace(excepciones, "");
	        		mensajesInfo=mensajesInfo.replace(" / ", "");
	        		repetido = mensajesInfo.contains(excepciones);
	        		i++;
	        	}
	        	if(mensajesInfo!=null && !mensajesInfo.equals("") && excepciones!=null && !excepciones.equals("") && !repetido )
	        		medResi.setMensajesInfo(mensajesInfo+ " "  + excepciones);
	        	if(mensajesInfo==null || mensajesInfo.equals("") )
	        		medResi.setMensajesInfo(excepciones);
	        	//medResi.setIdEstado("ORIGINAL");
	        	
	
	        	
	        	
	        	
	        	//if(medResi.getResiSiPrecisa()!=null && medResi.getResiSiPrecisa().equalsIgnoreCase("X")) medResi.setSpdAccionBolsa("NO_PINTAR");
	        	//if(medResi.getResiSiPrecisa()!=null && medResi.getResiSiPrecisa().equalsIgnoreCase("X")) 
	        	//	medResi.setSpdAccionBolsa(SPDConstants.SPD_ACCIONBOLSA_SI_PRECISA);

	        }
	        else medResi.setMensajesAlerta(SPDConstants.ALERTA_NO_SUSTITUCION);
	        	
	    } catch (SQLException e) {
	        e.printStackTrace();
	}finally {con.close();}

		System.out.println("buscaSustitucionLite EXIT--");	
		return trobat;
	}
	
	/**
	 * M�todo primario para la b�squeda de las sustituciones de un tratamiento que llegue en el listado con un CN de la residencia.
	 * Se buscar� si existe alguna sustituci�n para esa residencia, ya sea por CN Resi o directamente en el CN FINAL 
	 * @param spdUsuario
	 * @param lite
	 * @return un GestSustitucionesLite con la info de la sustituci�n 
	 * @throws Exception
	 */
	public static GestSustitucionesLite buscaSustitucionLite(String spdUsuario, GestSustitucionesLite lite) throws Exception {
		Connection con = Conexion.conectar();
		GestSustitucionesLite sust=null;
		
		String  select =" select fecha, oidGestSustitucionesLite, idDivisionResidencia, resiCn, resiMedicamento, spdCn, ";
				select +=" spdNombreBolsa, spdFormaMedicacion, spdAccionBolsa, excepciones, aux1, aux2  ";
		String where =" WHERE idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
		String 	from =" from SPD_sustitucionesLite ";
		where+=" and idDivisionResidencia='"+lite.getIdDivisionResidencia()+"' ";
		where+=" and (  ";
		where+="  	resiCn='"+StringUtil.limpiarTextoEspaciosYAcentos(lite.getResiCn(), false)+"'  ";
		where+=" 	OR  ";
		where+=" 	spdCn = '"+StringUtil.limpiarTextoEspaciosYAcentos(lite.getResiCn(), false)+"'  ";		
		where+=" ) ";
	//	where+=" and resiMedicamento='"+lite.getResiMedicamento()+"'  ";
		
		String qry = select + from + where;

		System.out.println("buscaSustitucionLite -->" +qry );		

			
		ResultSet resultSet = null;
		//List<GestSustituciones> sustituciones= new ArrayList<GestSustituciones>();
		try {
	        PreparedStatement pstat = con.prepareStatement(qry);
	        resultSet = pstat.executeQuery();

	        if (resultSet.next()) {
	        	sust = new GestSustitucionesLite();
	        	sust.setOidGestSustitucionesLite(resultSet.getInt("oidGestSustitucionesLite"));
	        	sust.setSpdCn(resultSet.getString("spdCn"));
	        	sust.setSpdNombreBolsa(resultSet.getString("spdNombreBolsa"));
	        	sust.setSpdAccionBolsa(resultSet.getString("spdAccionBolsa"));
	        	sust.setSpdFormaMedicacion(resultSet.getString("spdFormaMedicacion"));
	        	sust.setExcepciones(resultSet.getString("excepciones"));
	        	sust.setAux1(resultSet.getString("aux1"));
	        	sust.setAux2(resultSet.getString("aux2"));
	        	//sust.setNomGtVm(resultSet.getString("nomGtVm"));
	        	
	        }

	    	
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }finally {con.close();}
		return sust;
	}
	
	/**
	 * M�todo auxiliar para la b�squeda de las sustituciones de un tratamiento que llegue en el listado con un CN ya FINAL
	 * Se buscar� para detectar solo el nombre corto, y en caso que no exista la acci�n en bolsa, a�adirla tambi�n 
	 * @param spdUsuario
	 * @param medResi
	 * @throws Exception
	 */
	public static void buscaSustitucionLitePorCnFinal(String spdUsuario, FicheroResiBean medResi) throws Exception {
		Connection con = Conexion.conectar();
		
		
		String  select =" select fecha, oidGestSustitucionesLite, idDivisionResidencia, resiCn, resiMedicamento, spdCn, ";
				select +=" spdNombreBolsa, spdFormaMedicacion, spdAccionBolsa, excepciones, aux1, aux2  ";
		String where =" WHERE idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
		String 	from =" from SPD_sustitucionesLite ";
		where+=" and idDivisionResidencia='"+medResi.getIdDivisionResidencia()+"' ";
		where+=" and spdCn='"+StringUtil.limpiarTextoEspaciosYAcentos(medResi.getSpdCnFinal(), false)+"'  ";
		
		String qry = select + from + where;

		System.out.println("buscaSustitucionLitePorCnFinal -->" +qry );		

			
		ResultSet resultSet = null;
		
		try {
	        PreparedStatement pstat = con.prepareStatement(qry);
	        resultSet = pstat.executeQuery();

	        if (resultSet.next()) {
	        	medResi.setSpdCnFinal(resultSet.getString("spdCn"));
	        	medResi.setSpdNombreBolsa(resultSet.getString("spdNombreBolsa"));
	        	String accionPrevia = medResi.getSpdAccionBolsa();
	        	if(accionPrevia!=null && !accionPrevia.equals("") 
	        			&& !accionPrevia.equalsIgnoreCase(SPDConstants.SPD_ACCIONBOLSA_NO_PINTAR)
	        			&& !accionPrevia.equalsIgnoreCase(SPDConstants.SPD_ACCIONBOLSA_PASTILLERO)
	        			&& !accionPrevia.equalsIgnoreCase(SPDConstants.SPD_ACCIONBOLSA_SOLO_INFO)
	        				)
	        		medResi.setSpdAccionBolsa(resultSet.getString("spdAccionBolsa"));
	        	
	        	medResi.setSpdFormaMedicacion(resultSet.getString("spdFormaMedicacion"));
	        }
	    	
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }finally {con.close();}

	}
	
	
	
	
  	private static String getQuerySustitucionesLite(String spdUsuario, int oidDivisionResidencia, int oidGestSustitucionesLite, String resiCn,  String filtroGtvm, String filtroGtvmp, String campoGoogle,  int inicio, int fin, boolean count) throws Exception
  	{
 		String  select =" select * from ( select  distinct ROW_NUMBER() OVER(order by s.spdCn,  d.idDivisionResidencia desc) AS ROWNUM, ";
		select +=" s.fecha, s.oidGestSustitucionesLite, s.idDivisionResidencia, d.oidDivisionResidencia, s.resiCn, s.resiMedicamento, s.spdCn, ";
		select +=" s.spdNombreBolsa, s.spdFormaMedicacion, s.spdAccionBolsa, s.excepciones, s.aux1, s.aux2, bc.NOMBRE + ' ' + bc.PRESENTACION as spdNombreMedicamento, bc.CodGtVm, bc.NomGtVm, bc.CodGtVmp, bc.NomGtVmp, bc.nomLABO    ";
		String 	from = " FROM SPD_sustitucionesLite s inner join bd_divisionResidencia d on s.idDivisionResidencia=d.idDivisionResidencia left join bd_consejo  bc on s.spdCn=bc.codigo ";
		String where = " WHERE s.idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
		String order= " ";
		String otros =" ";
		
		if(oidDivisionResidencia>0)
			where+=" and d.oidDivisionResidencia='"+oidDivisionResidencia+"' ";
		if(oidGestSustitucionesLite>0)
			where+=" and s.oidGestSustitucionesLite='"+oidGestSustitucionesLite+"'  ";

  		if(resiCn!=null && !resiCn.equals(""))
  		{
  			where+=  " AND ( s.resiCn = '"+resiCn+"' ";
  	  		
  			if(resiCn.startsWith("0"))
  				where+=" OR RIGHT(s.resiCn, 6)=RIGHT('"+resiCn+"', 6) ";
  			else
  				where+=" OR LEFT(s.resiCn, 6)= LEFT('"+resiCn+"', 6) ";

  			where+=  " ) ";
  		}
		
		if(filtroGtvm!=null && !filtroGtvm.equals(""))
			where+=" and bc.Codgtvm='"+filtroGtvm+"'  ";
		if(filtroGtvmp!=null && !filtroGtvmp.equals(""))
			where+=" and bc.Codgtvmp='"+filtroGtvmp+"'  ";
		if(campoGoogle!=null && !campoGoogle.equals(""))
		{
			where+=" and ( s.resiCn like '%"+StringUtil.limpiarTextoEspaciosYAcentos(campoGoogle, false)+"%'	OR 	";
			where+=" 	   s.resiMedicamento like '%"+campoGoogle+"%' 											OR	";
			where+=" 	   s.spdCn like '%"+campoGoogle+"%'  													OR	";
			where+=" 	   s.spdNombreBolsa 	like '%"+campoGoogle+"%'  										OR 	";
			where+=" 	   s.excepciones 		like '%"+campoGoogle+"%'  										OR 	";
			where+=" 	   s.aux1 			like '%"+campoGoogle+"%'  											OR	";
			where+=" 	   s.aux2 			like '%"+campoGoogle+"%'  											";
			where+=" 	) ";
		}
			
			
		if(count) { //contador
			select = "select count(distinct s.oidGestSustitucionesLite) as quants";
			
		}else
    	{
     		//order+=  " order by s.spdCn,  d.idDivisionResidencia desc    ";  //importante este orden, para que salga primero la residencia en caso que exista SustXResi

    		//otros+= " offset "+ (inicio) + " rows ";
    		//otros+= " fetch next "+SPDConstants.PAGE_ROWS+ " rows only";
    	}
    	
    	
		String qry = select + from + where + order + getOtrosSql2008(inicio, inicio+SPDConstants.PAGE_ROWS, count);
		System.out.println(HelperSPD.dameFechaHora() + " getQuerySustitucionesLite -->" +qry );		

			return qry;
				
  	}
    

  	private static String getQuerySustitucionesLiteExpress(String spdUsuario, String resiCn, String resiMedicamento) throws Exception
  	{
 
  		String  qry =" SELECT d.idDivisionResidencia, s.idDivisionResidencia, d.oidDivisionResidencia, s.oidGestSustitucionesLite, s.fecha , s.resiCn, s.resiMedicamento  ";
		qry+=" , s.spdCn, s.spdNombreBolsa, s.spdFormaMedicacion, s.spdAccionBolsa, s.excepciones, s.aux1, s.aux2 ";
		qry+=" , bc.NOMBRE + ' ' + bc.PRESENTACION AS spdNombreMedicamento, bc.CodGtVm, bc.NomGtVm, bc.CodGtVmp, bc.NomGtVmp, bc.nomLABO     ";
		qry+= " FROM SPD_sustitucionesLite s ";
		qry+= " INNER JOIN bd_divisionResidencia d ON s.idDivisionResidencia=d.idDivisionResidencia ";
		qry+= " LEFT JOIN bd_consejo  bc ON s.spdCn=bc.codigo  ";
		qry+= " WHERE 1=1 ";
		qry+= " AND EXISTS ( " + VisibilidadHelper.oidDivisionResidenciasVisiblesExists(spdUsuario, "d.idDivisionResidencia")  + ")";
		qry+=" AND (  ";
		qry+=" 		s.resiCn='"+resiCn+"' OR s.resiCn=LEFT('"+ resiCn +"', 6) OR s.resiMedicamento='"+resiMedicamento+"' ";
		qry+=" 		OR (bc.CodGtVmpp!= '' AND bc.CodGtVmpp !=null AND   bc.CodGtVmpp in (select s2.CodGtVmpp from bd_consejo s2 where s2.codigo = LEFT('"+ resiCn +"', 6)  )) ";
		qry+=" 		)";
		qry+= " UNION ";
		qry+= " SELECT 'BD_CONSEJO', '', -1, -1, '', '', '' ";
		qry+= " , bc.CODIGO, bc.NomGtVmp, bc.nomFormaFarmaceutica, '', '', '', '' ";
		qry+= " , bc.NOMBRE + ' ' + bc.PRESENTACION AS spdNombreMedicamento ";
		qry+= " , bc.CodGtVm, bc.NomGtVm, bc.CodGtVmp, bc.NomGtVmp, bc.nomLABO  ";
		qry+= " FROM bd_consejo  bc ";
		qry+= " WHERE ( bc.CODIGO=LEFT('"+ resiCn +"', 6)  OR bc.CodGtVmpp=REPLACE(UPPER(LEFT('"+ resiCn +"', 6)), 'PF', '' ) ) ";
		
		
		 System.out.println("getQuerySustitucionesLiteExpress -->" +qry );		
		 return qry;
  	}

    
	/**
	 * Fetch es una cl�usula que funciona a partir del SqlServer 2008 (no inclusive)
	 * @param form
	 * @param inicio
	 * @param fin
	 * @param count
	 * @return
	 */
	private static String getOtrosSqlPost2008(int inicio, int fin, boolean count) 
	{
		String otros="";
		if(!count) 
		{
			otros+= " offset "+ (inicio) + " rows ";
			otros+= " fetch next "+(fin)+ " rows only";
		}
		return otros;
	}

	/**
	 * Como FETCH es una cl�usula de versi�n SQLSERVER>2008 se crea una funci�n un poco m�s engorrosa pero
	 * que sirve para todas las versiones (ROW_NUMBER() OVER)
	 * @param form
	 * @param inicio
	 * @param fin
	 * @param count
	 * @return
	 */
	private static String getOtrosSql2008( int inicio, int fin, boolean count) 
	{
		String otros="";
		if(!count) 
		{
			otros+= " ) cte ";
			otros+= " where ROWNUM >=  "+ (inicio) + "  AND ROWNUM <= "+(fin);

		}
		return otros;
	}
    
    public static List<GestSustitucionesLite> getSustitucionesListadoLiteExpress(String spdUsuario,String resiCn,  String resiMedicamento)  throws Exception {
	 
    	String qry = getQuerySustitucionesLiteExpress(spdUsuario, resiCn, resiMedicamento);
    	
 	    Connection con = Conexion.conectar();
        	
        ResultSet resultSet = null;
        List<GestSustitucionesLite> listaGestSustitucionesLite= new ArrayList<GestSustitucionesLite>();
       
        try {
            PreparedStatement pstat = con.prepareStatement(qry);
            resultSet = pstat.executeQuery();
             
           while (resultSet.next()) {
             	//controlamos si es una sust nueva
               		//creamos nuevo objeto de sustituci�n e inicializamos objetos asociados	
        	   GestSustitucionesLite c = creaObjeto(resultSet);
                 
           		//a�adimos la sust anterior a la lista y creamos una  nueva
           	    listaGestSustitucionesLite.add(c);

                
       		}
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {con.close();}
 
        return listaGestSustitucionesLite;
    }

    
    public static List<GestSustitucionesLite> getSustitucionesListadoLite(String spdUsuario, int oidDivisionResidencia, int oidGestSustitucionesLite, 
    		String resiCn,  String filtroGtvm, String filtroGtvmp,  String filtroGoogle, int inicio, int fin, boolean count)  throws Exception {
	 
    	String qry = getQuerySustitucionesLite(spdUsuario, oidDivisionResidencia, oidGestSustitucionesLite,  resiCn, filtroGtvm, filtroGtvmp, filtroGoogle,  inicio, fin, count);
    	
 	    Connection con = Conexion.conectar();
        	
        ResultSet resultSet = null;
        List<GestSustitucionesLite> listaGestSustitucionesLite= new ArrayList<GestSustitucionesLite>();
       
        try {
            PreparedStatement pstat = con.prepareStatement(qry);
            resultSet = pstat.executeQuery();
             
           while (resultSet.next()) {
             	//controlamos si es una sust nueva
               		//creamos nuevo objeto de sustituci�n e inicializamos objetos asociados	
        	   GestSustitucionesLite c = creaObjeto(resultSet);
                 
           		//a�adimos la sust anterior a la lista y creamos una  nueva
           	    listaGestSustitucionesLite.add(c);

                
       		}
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {con.close();}
 
        return listaGestSustitucionesLite;
    }

              	
            	
    public static List<GestSustitucionesLite> getMedicamentoResiLite(String spdUsuario) throws Exception {
    	
 	   Connection con = Conexion.conectar();
 	 //  System.out.println("connected main" );
     String qry = "select distinct g.resiMedicamento ";
     		qry+=  " from dbo.SPD_sustitucionesLite g ";
	   		qry+= " 	WHERE g.idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
 			qry+=  " order by g.resiMedicamento";
  	   	//	System.out.println("getMedicamentoResi -->" +qry );		

      ResultSet resultSet = null;
 		List<GestSustitucionesLite> listaMedicamentosResi= new ArrayList<GestSustitucionesLite>();
    try {
         PreparedStatement pstat = con.prepareStatement(qry);
     //    pstat.setString(1, employee.getempNo());
         resultSet = pstat.executeQuery();

         while (resultSet.next()) {
         	GestSustitucionesLite  c =new GestSustitucionesLite();
          	c.setResiMedicamento(resultSet.getString("resiMedicamento"));
          	listaMedicamentosResi.add(c);
         }
     } catch (SQLException e) {
         e.printStackTrace();
     }finally {con.close();}

     return listaMedicamentosResi;
 }
  	
	
	public static List<GestSustitucionesLite> getGtvmLite() throws ClassNotFoundException, SQLException {
	
		Connection con = Conexion.conectar();
		//  System.out.println("connected main" );
		String qry = "select distinct b.codgtvm as codgtvm, b.nomgtvm  as nomgtvm from dbo.SPD_sustitucionesLite g ";
				qry+= "inner join bd_consejo b on (b.CODIGO=g.spdCn) ";
				qry+= "order by b.nomgtvm  ";
			//	System.out.println("getMedicamentoResi -->" +qry );		
		
		ResultSet resultSet = null;
		List<GestSustitucionesLite> listaGtVm= new ArrayList<GestSustitucionesLite>();
		try {
		PreparedStatement pstat = con.prepareStatement(qry);
		//    pstat.setString(1, employee.getempNo());
		resultSet = pstat.executeQuery();
		
		while (resultSet.next()) {
			GestSustitucionesLite  c =new GestSustitucionesLite();
			c.setCodGtVm(resultSet.getString("codgtvm"));
			c.setNomGtVm(resultSet.getString("nomgtvm"));
			listaGtVm.add(c);
		}
		} catch (SQLException e) {
		e.printStackTrace();
		}finally {con.close();}
	
	return listaGtVm;
}

	public static List<BdConsejo> getGtvmpLite(String codgtvm) throws ClassNotFoundException, SQLException {
		
		Connection con = Conexion.conectar();
		//  System.out.println("connected main" );
		String qry = "select distinct b.codgtvmp as codgtvmp, b.nomgtvmp  as nomgtvmp from dbo.SPD_sustitucionesLite g ";
				qry+= "inner join bd_consejo b on (b.CODIGO=g.spdCn) ";
				qry+= " where 1=1 ";
			if(codgtvm!=null && !codgtvm.equals(""))
				qry+= "and b.codgtvm='"+codgtvm+"'"; 
				qry+= "order by b.nomgtvmp  ";
				System.out.println("getGtvmpLite -->" +qry );		
		
		ResultSet resultSet = null;
		List<BdConsejo> listaGtVmp= new ArrayList<BdConsejo>();
		try {
		PreparedStatement pstat = con.prepareStatement(qry);
		//    pstat.setString(1, employee.getempNo());
		resultSet = pstat.executeQuery();
		
		while (resultSet.next()) {
			BdConsejo f = new BdConsejo();
        	 f.setCodGtVmp(resultSet.getString("CodGtVmp"));
        	 f.setNomGtVmp(resultSet.getString("NomGtVmp"));
			listaGtVmp.add(f);
		}
		} catch (SQLException e) {
		e.printStackTrace();
		}finally {con.close();}
	
	return listaGtVmp;
	}

    public static List<TiposAccionBean> getListaTiposAccion() throws ClassNotFoundException, SQLException {
    	
        List  listaTiposAccion = new ArrayList();
  	   Connection con = Conexion.conectar();
   	   String  qry = "  select distinct spdAccionBolsa ";
		 qry+= "  from dbo.SPD_sustitucionesLite   ";
   		 qry+= " order by spdAccionBolsa";
      	ResultSet resultSet = null;
 	   		System.out.println("getListaTiposAccion -->" +qry );		

     try {
          PreparedStatement pstat = con.prepareStatement(qry);
          resultSet = pstat.executeQuery();
          int count=0;
          while (resultSet.next()) {
         	 TiposAccionBean t = new TiposAccionBean();
         	 t.setIdTipoAccion(resultSet.getString("spdAccionBolsa"));
         	 t.setNombreAccion(t.getIdTipoAccion());
         	 listaTiposAccion.add(t);
             }
      } catch (SQLException e) {
          e.printStackTrace();
      }

      return listaTiposAccion;
  }

	
	
	
	
}
 
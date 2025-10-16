package lopicost.spd.persistence;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.microsoft.sqlserver.jdbc.SQLServerException;

import lopicost.config.pool.dbaccess.Conexion;
import lopicost.spd.model.BdConsejo;
import lopicost.spd.model.SustXComposicion;
import lopicost.spd.struts.form.SustXComposicionForm;
import lopicost.spd.utils.SPDConstants;
import lopicost.spd.utils.StringUtil;
 
 
public class SustXComposicionDAO2 {
	
	
	public static int getCountSustXComposicion(SustXComposicionForm form) throws SQLException, ClassNotFoundException {
	    String qry = getQuerySustituciones(form, true, 0, 0, true);
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

	
	
	
    public static int  getCountSustXComposicion1(SustXComposicionForm form) throws ClassNotFoundException, SQLException 
    {
   	 	String qry = getQuerySustituciones( form, true, 0, 0, true);
   	 	//System.out.println("getCountSustXConjHomog -->" +qry );		
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
   	 	return result;
    }

	public static List<SustXComposicion> getSustXComposicion(SustXComposicionForm form, int inicio, int fin) throws ClassNotFoundException, SQLException {
     	
		String qry = getQuerySustituciones( form, false, inicio, fin, true);
		//String qry = getQuerySustituciones( form, false, inicio, fin, true);
		Connection con = Conexion.conectar();
        ResultSet resultSet = null;
 		List<SustXComposicion> listaSustXComposicion = new ArrayList<SustXComposicion>();
 		try {
            PreparedStatement pstat = con.prepareStatement(qry);
            resultSet = pstat.executeQuery();

            while (resultSet.next()) {
            	SustXComposicion  c =creaObjeto(resultSet);
    	        listaSustXComposicion.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
       	 con.close();
    	}
        return listaSustXComposicion;
    }
	
	public static Vector getSustXComposicionParaExportCSV(SustXComposicionForm form, int inicio, int fin) throws ClassNotFoundException, SQLException {
		Vector result = new Vector();
		
		String qry = getQuerySustituciones( form, false, inicio, fin, true);
		//String qry = getQuerySustituciones( form, false, inicio, fin, true);
		Connection con = Conexion.conectar();
        ResultSet resultSet = null;
 		List<SustXComposicion> listaSustXComposicion = new ArrayList<SustXComposicion>();
 		try {
            PreparedStatement pstat = con.prepareStatement(qry);
            resultSet = pstat.executeQuery();

            while (resultSet.next()) {
            	SustXComposicion  c =creaObjeto(resultSet);
            	
            	result.add(c.toStringForExportCSV());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
       	 con.close();
    	}
        return result;

	}

	
	   
	
	
	
	private static SustXComposicion creaObjeto(ResultSet resultSet) throws SQLException {
			SustXComposicion  c = new SustXComposicion();
		  	c.setOidSustXComposicion(resultSet.getInt("oidSustXComposicion"));
		  	c.setIdRobot(resultSet.getString("idRobot"));
		  	c.setRentabilidad(resultSet.getString("rentabilidad"));
	       	c.setNota(resultSet.getString("nota"));
	       	c.setPonderacion(resultSet.getString("ponderacion"));
	    	c.setCodiLab(resultSet.getString("codiLab"));
	    	c.setNombreLab(resultSet.getString("nombreLab"));
	    	c.setFechaCreacion(resultSet.getDate("fechaCreacion"));
	    	c.setUltimaModificacion(resultSet.getDate("ultimaModificacion"));
	    	c.setComentarios(resultSet.getString("comentarios"));
	    	//c.setCodigoGT(resultSet.getString("CodigoGT"));
	    	//c.setGrupoTerapeutico(resultSet.getString("GrupoTerapeutico"));
	    	//c.setNomGtVm(resultSet.getString("nomGtVm"));		//pactivo
	    	//c.setCodGtVm(resultSet.getString("codGtVm"));
	    	c.setCodGtVmp(resultSet.getString("codGtVmp"));		//base conj homog
	    	c.setNomGtVmp(resultSet.getString("nomGtVmp"));
	    	c.setCodGtVmpp(resultSet.getString("CodGtVmpp"));	// conj homog
	    	c.setNomGtVmpp(resultSet.getString("NomGtVmpp"));
	    	//c.setCodGtAtcNivel3(resultSet.getString("CodGtAtcNivel3"));
	    	//c.setNomGtAtcNivel3(resultSet.getString("NomGtAtcNivel3"));
	    	//c.setCodGtAtcNivel4(resultSet.getString("CodGtAtcNivel4"));
	    	//c.setNomGtAtcNivel4(resultSet.getString("NomGtAtcNivel4"));
	    	//c.setCodGtAtcNivel5(resultSet.getString("CodGtAtcNivel5"));
	    	//c.setNomGtAtcNivel5(resultSet.getString("NomGtAtcNivel5"));
	    	c.setCn6(resultSet.getString("cn6"));	
	    	c.setCn7(resultSet.getString("cn7"));	
	    	c.setNombreMedicamento(resultSet.getString("nombreMedicamento"));	
	    	c.setSustituible(resultSet.getString("sustituible"));	
	    	c.setTolva(resultSet.getString("tolva"));	
			return c;
	}
	
	public static SustXComposicion getSustXComposicionByOid(SustXComposicionForm form) throws ClassNotFoundException, SQLException 
	{
		
		List<SustXComposicion> listaSustXComposicion = getSustXComposicion(form, 0, 1);
		SustXComposicion  c = null;

		if(listaSustXComposicion!=null && listaSustXComposicion.size()>0)
			c= (SustXComposicion)listaSustXComposicion.get(0);
			
	
		
		
		
    	//String qry = getQuerySustituciones(form,  false, 0, 1, false);
    	/*Connection con = Conexion.conectar();
	    ResultSet resultSet = null;
	   try {
	        PreparedStatement pstat = con.prepareStatement(qry);
	        resultSet = pstat.executeQuery();
	        while (resultSet.next())
	        {
	        	c =creaObjeto(resultSet); 
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }*/
	    return c;
	}
	
	
	
	/**
	 * 	Grupo Terapéutico ATC Nivel 3. Permite obtener el listado de medicamentos que tengan su mismo nivel de grupo terapéutico de la ATC (mismo subgrupo farmacológico/terapéutico).
		Grupo Terapéutico ATC Nivel 4. Permite obtener el listado de medicamentos que tengan su mismo nivel de grupo terapéutico de la ATC (mismo subgrupo farmacológico/terapéutico).
		Grupo Terapéutico ATC Nivel 5. Permite obtener el listado de medicamentos que tengan su mismo nivel de grupo terapéutico de la ATC (mismo principio activo).
		Grupo Terapéutico VMP. Permite obtener el listado de medicamentos que tengan su mismo grupo VMP (es decir, igual principio activo, dosis y forma farmacéutica).
		Grupo Terapéutico VMPP. Permite obtener el listado de medicamentos que tengan su mismo grupo VMMP (es decir, igual principio activo, dosis, forma farmacéutica y número de unidades de dosificación).
	 * habrán varias formas de muestra:
	 * 	- Selección de LAB: Se muestran los ConjHomogéneos que hay disponibles (bdConsejo) para ese LAb
	 *  - Selección de soloAsignados: Lo mismo que lo anterior pero solo los que tienen LAB - conjHomog asignado
	 *  - Selección de PActivo / Grupo terap: Se muestran los de la tabla sustitucionXConjHomg ordenados descendentemente por rent/ponderación
	 *  
	 * @param form
	 * @param count
	 * @param inicio
	 * @param fin
	 * @param todosLosConjuntosHomogeneos
	 * @return
	 */

    
    private static String getQuerySustituciones(SustXComposicionForm form, boolean count, int inicio, int fin, boolean todasLasComposiciones) {
		
    	
		String 	select = " distinct  ";
				//select+=" b.codiLABO, b.nomLabo, ";
				select+=" b.CodigoGT, b.GrupoTerapeutico,  "; // b.codiPactivo,  b.nomPactivo,  
				select+=" b.CodGtAtcNivel3, b.NomGtAtcNivel3,   ";		// nivel de grupo terapéutico de la ATC (mismo subgrupo farmacológico/terapéutico).
				select+=" b.CodGtAtcNivel4, b.NomGtAtcNivel4, 	";		// nivel de grupo terapéutico de la ATC (mismo subgrupo farmacológico/terapéutico).
				select+=" b.CodGtAtcNivel5, b.NomGtAtcNivel5, 	";		// nivel de grupo terapéutico de la ATC (mismo principio activo)
				select+=" b.CodGtVm, b.NomGtVm, ";					// grupo VM (igual principio activo).
				select+=" b.CodGtVmp, b.NomGtVmp, ";					// grupo VMP (igual principio activo, dosis y forma farmacéutica).
				select+=" b.CodGtVmpp, b.NomGtVmpp, ";  				// grupo VMMP (igual principio activo, dosis, forma farmacéutica y número de unidades de dosificación).
				select+=" g.rentabilidad, g.ponderacion, g.codiLab, g.ultimaModificacion, (g.rentabilidad + g.ponderacion) as nota,  ";
				select+=" g.comentarios, g.nombreLab, g.fechaCreacion, g.oidSustXComposicion, g.cn6, g.cn7, g.nombreMedicamento, g.sustituible, g.tolva ";
				
				//si es contador inicializo la query
		if(count)  
					select = " count(distinct coalesce(g.codGtVmpp,'')+coalesce(b.codGtVmpp,'')+coalesce(g.nombreLab,'')) as quants";
				//	select = "select count( distinct g.oidSustXComposicion) as quants";
		  

		select = "select  " + select;
		String from =" from dbo.SPD_sustXComposicion g full outer join bd_consejo b on ( g.codGtVmpp=b.codGtVmpp and g.codGtVmpp is not null)  ";
		//String from =" from dbo.SPD_sustXComposicion g full outer join bd_consejo b on (b.codiLABO =g.codiLAB  and g.codConjHomog=b.codConjHomog and g.codConjHomog>0)  ";
		//String from =" from dbo.SPD_sustXComposicion g left join bd_consejo b on (g.codConjHomog=b.codConjHomog and g.codConjHomog>0)  ";
		
		String 	where =" where 1=1  ";
				where+=" and g.codGtVmpp is not null ";
		String order =" ";
		String otros =" ";
		
			
		if(form.isFiltroCheckedLabsSoloAsignados())
		{
			from=  " from dbo.SPD_sustXComposicion g left join bd_consejo b on (g.codGtVmpp=b.codGtVmpp and g.codGtVmpp is not null)  ";
		}
		
		
		//from=  " from dbo.SPD_sustXComposicion g full outer join bd_consejo b on (b.codiLABO =g.codiLAB  and g.codConjHomog=b.codConjHomog and g.codConjHomog>0)     ";
	
	
			
	if(form.getOidSustXComposicion()>0)
		where+=  " and g.oidSustXComposicion = '"+form.getOidSustXComposicion()+"'";
	if(form.getFiltroTextoABuscar()!=null && !form.getFiltroTextoABuscar().equals(""))
 	{
		where+=  " and ( ";
		where+=  "  g.nomGtVmpp like '%"+form.getFiltroTextoABuscar() +"%' ";
		where+=  "  OR g.nombreLab  like '%"+form.getFiltroTextoABuscar() +"%' ";
		where+=  "  OR g.comentarios  like '%"+form.getFiltroTextoABuscar() +"%' ";
		where+=  "  OR b.CODIGO ='"+form.getFiltroTextoABuscar() +"'";
		where+=  "  OR b.NOMBRE like '%"+form.getFiltroTextoABuscar() +"%' ";
		where+=  "  OR b.PRESENTACION like '%"+form.getFiltroTextoABuscar() +"%' ";
		where+=  " ) ";
 	}
	if(form.getFiltroPresentacion()!=null && !form.getFiltroPresentacion().equals(""))
		where+=  " and b.presentacion like  '%"+form.getFiltroPresentacion() +"%' ";
	if(form.getFiltroCodigoMedicamento()!=null && !form.getFiltroCodigoMedicamento().equals(""))
		where+=  " and b.CODIGO like  '%"+form.getFiltroCodigoMedicamento() +"%' ";
	if(form.getCodigoGT()!=null && !form.getCodigoGT().equals(""))
		where+=  " and b.codigoGT =  '"+form.getCodigoGT() +"' ";
	if(form.getGrupoTerapeutico()!=null && !form.getGrupoTerapeutico().equals(""))
		where+=  " and b.grupoTerapeutico like  '%"+form.getGrupoTerapeutico() +"%' ";
	if(form.getFiltroCodiLaboratorio()!=null && !form.getFiltroCodiLaboratorio().equals(""))
		where+=  " and (b.codiLABO =  '"+form.getFiltroCodiLaboratorio() +"' OR g.codiLab =  '"+form.getFiltroCodiLaboratorio() +"') ";
	if(form.getCodGtAtcNivel3()!=null && !form.getCodGtAtcNivel3().equals(""))
		where+=  " and b.codGtAtcNivel3 =  '"+form.getCodGtAtcNivel3() +"' ";
	if(form.getNomGtAtcNivel3()!=null && !form.getNomGtAtcNivel3().equals(""))
		where+=  " and b.nomGtAtcNivel3 like  '%"+form.getNomGtAtcNivel3() +"%' ";
	if(form.getCodGtAtcNivel4()!=null && !form.getCodGtAtcNivel4().equals(""))
		where+=  " and b.codGtAtcNivel4 =  '"+form.getCodGtAtcNivel4() +"' ";
	if(form.getNomGtAtcNivel4()!=null && !form.getNomGtAtcNivel4().equals(""))
		where+=  " and b.nomGtAtcNivel4 like  '%"+form.getNomGtAtcNivel4() +"%' ";
	if(form.getCodGtAtcNivel5()!=null && !form.getCodGtAtcNivel5().equals(""))
		where+=  " and b.codGtAtcNivel5 =  '"+form.getCodGtAtcNivel5() +"' ";
	if(form.getNomGtAtcNivel5()!=null && !form.getNomGtAtcNivel5().equals(""))
		where+=  " and b.nomGtAtcNivel5 like  '%"+form.getNomGtAtcNivel5() +"%' ";
	if(form.getFiltroCodGtVmp()!=null && !form.getFiltroCodGtVmp().equals(""))
		where+=  " and b.codGtVmp =  '"+form.getFiltroCodGtVmp() +"' ";
	if(form.getFiltroCodGtVm()!=null && !form.getFiltroCodGtVm().equals(""))
		where+=  " and b.CodGtVm like  '%"+form.getFiltroCodGtVm() +"%' ";
	if(form.getNomGtVm()!=null && !form.getNomGtVm().equals(""))
		where+=  " and b.NomGtVm like  '%"+form.getNomGtVm() +"%' ";
	if(form.getNomGtVmp()!=null && !form.getNomGtVmp().equals(""))
		where+=  " and b.nomGtVmp like  '%"+form.getNomGtVmp() +"%' ";
	if(form.getCodGtVmpp()!=null && !form.getCodGtVmpp().equals(""))
		where+=  " and b.codGtVmpp =  '"+form.getCodGtVmpp() +"' ";
	if(form.getNomGtVmpp()!=null && !form.getNomGtVmpp().equals(""))
		where+=  " and b.nomGtVmpp like  '%"+form.getNomGtVmpp() +"%' ";
	if(form.getCodiLab()!=null && !form.getCodiLab().equals(""))
		where+=  " and (b.codiLABO =  '"+form.getCodiLab() +"' OR g.codiLab =  '"+form.getCodiLab() +"') ";
	if(form.getNombreLab()!=null && !form.getNombreLab().equals(""))
		where+=  " and b.nomLABO like  '%"+form.getNombreLab() +"%' ";	
	if(form.isFiltroCheckedLabsSoloAsignados())
		where+=  " and g.codiLab =  '"+form.getFiltroCodiLaboratorio() +"' ";
		
	if(!count) 
	{
		order+=  " order by b.nomGtVmpp,  nota desc";
		otros+= " offset "+ (inicio) + " rows ";
		otros+= " fetch next "+(fin)*SPDConstants.PAGE_ROWS+ " rows only";
	}
	String qry = select + from + where + order + otros;
	System.out.println("getQuerySustituciones -->" +qry );		
		return qry;
}
    

    

	public static boolean edita(SustXComposicionForm form) throws ClassNotFoundException, SQLException {

        int result=0;
		  Connection con = Conexion.conectar();
	  	   /*
	  	    String qry = "update dbo.SPD_sustXComposicion ";
	  	   		qry+= " set  codConjHomog= "+form.getCodConjHomog()+" ";
	  	   		qry+= ", nomConjHomog= '"+form.getNomConjHomog()+"' ";
	  	   		qry+= ", rentabilidad= "+form.getRentabilidad() ;
	  	   		qry+= ", ponderacion= "+form.getPonderacion() ;
	  	   		qry+= ", codiLab= '"+form.getCodiLab()+"' ";
	  	   		qry+= ", nombreLab= (select distinct nomLABO from bd_consejo where codiLABO='" +form.getCodiLab()  +"') ";
	  	   		qry+= ", ultimaModificacion= CONVERT(datetime, getdate(), 120)  ";
	  	   		qry+= ", comentarios= '"+form.getComentarios()+"' ";
	       		qry+= " where codConjHomog= '"+form.getCodConjHomog()+"' ";
	       		qry+= " and codiLab= '"+form.getCodiLab()+"' ";
	       */		
	  	    String qry = "update dbo.SPD_sustXComposicion ";
  	   		qry+= " set   rentabilidad= "+form.getRentabilidad() ;
  	   		qry+= ", ponderacion= "+form.getPonderacion() ;
 	   		qry+= ", comentarios= '"+form.getComentarios()+"' ";
	   		qry+= ", sustituible= '"+form.getSustituible()+"' ";
	   		qry+= ", tolva= '"+form.getTolva()+"' ";
 	   	  	qry+= ", ultimaModificacion= CONVERT(datetime, getdate(), 120)  ";
       		qry+= " where oidSustXComposicion= '"+form.getOidSustXComposicion()+"' and idRobot='"+form.getIdRobot()+"'";
	  		System.out.println("edita-->  " +qry );		
	      		 
	    try {
	         PreparedStatement pstat = con.prepareStatement(qry);
	         result=pstat.executeUpdate();
	       
	     } catch (SQLException e) {
	         e.printStackTrace();
	     }finally {con.close();}

		
		return result>0;
	}


	
	public static boolean edita(SustXComposicion sust) throws ClassNotFoundException, SQLException {

        int result=0;
		  Connection con = Conexion.conectar();
		  	String qry = "update dbo.SPD_sustXComposicion ";
  	   		qry+= " set cn6= '"+sust.getCn6()+"', " ;
 	   		qry+= "  cn7= '"+sust.getCn7()+ "', " ;
 	   		qry+= "  nombreMedicamento= '"+sust.getNombreMedicamento()+ "', " ;
	   		qry+= "  rentabilidad= "+sust.getRentabilidad() ;
  	   		qry+= ", ponderacion= "+sust.getPonderacion() ;
  	   		qry+= ", comentarios= '"+sust.getComentarios()+"' ";
 	   		qry+= ", sustituible= '"+sust.getSustituible()+"' ";
	   		qry+= ", tolva= '"+sust.getTolva()+"' ";
	   		qry+= ", ultimaModificacion= CONVERT(datetime, getdate(), 120)  ";
  	   		qry+= "  where  oidSustXComposicion = '"+sust.getOidSustXComposicion()+"';" ;
       		//qry+= " where idRobot= '"+sust.getIdRobot()+"' ";
       		//qry+= " and ( codiLab= '"+sust.getCodiLab()+"' OR  nombreLab= '"+sust.getNombreLab()+"' )";
       		//qry+= " and ( codGtVmpp= '"+sust.getCodGtVmpp()+"' OR  nomGtVmpp= '"+sust.getNomGtVmpp()+"' )";
       		       		
       		
	  		System.out.println("edita SustXComposicion -->  " +qry );		
	      		 
	    try {
	         PreparedStatement pstat = con.prepareStatement(qry);
	         result=pstat.executeUpdate();
	       
	     } catch (SQLException e) {
	         e.printStackTrace();
	     }

		
		return result>0;
	}


	public static boolean borra(SustXComposicionForm form) throws ClassNotFoundException, SQLException {

        int result=0;
		  Connection con = Conexion.conectar();
	  	   String qry = "delete dbo.SPD_sustXComposicion ";
	  	   
      	//	qry+= " where codConjHomog= '"+form.getCodConjHomog()+"' ";
      	//	qry+= " and codiLab= '"+form.getCodiLab()+"' ";
	  	 qry+= " where oidSustXComposicion= '"+form.getOidSustXComposicion()+"' ";
		   		System.out.println("borra -->" +qry );		
	 	
	    try {
	         PreparedStatement pstat = con.prepareStatement(qry);
	         result=pstat.executeUpdate();
	       
	     } catch (SQLException e) {
	         e.printStackTrace();
	     }finally {con.close();}

		
		return result>0;
	}

	public static boolean nuevo(SustXComposicionForm form) throws ClassNotFoundException, SQLException {

        int result=0;
		  Connection con = Conexion.conectar();
	  	   String qry = " INSERT INTO dbo.SPD_sustXComposicion (idRobot, codGtVmpp, nomGtVmpp, rentabilidad, ";
	  	   		qry+= " ponderacion, codiLab, ultimaModificacion, ";
	  	   		qry+= " comentarios, nombreLab, fechaCreacion, ultimaModificacion, sustituible, tolva) VALUES ";
	       		//qry+= "('"+ form.getFiltroListaConjuntosHomogeneos()+"', '"+ getNomConjHomog(form.getFiltroListaConjuntosHomogeneos())+"', "+ form.getRentabilidad()+", ";
	  	   		qry+= "('"+ form.getFiltroRobot()+"', '"+ form.getFiltroCodGtVmpp()+"', '"+ getNomGtVmpp(form.getFiltroCodGtVmpp())+"', "+ form.getRentabilidad()+", ";
	  	   		qry+=  + form.getPonderacion()+", '"+ form.getFiltroCodiLaboratorio()+"',   CONVERT(datetime, getdate(), 120), ";
	       		qry+= "'" + form.getComentarios()+"', '" + getNombreLaboratorio(form.getFiltroCodiLaboratorio())+"', CONVERT(datetime, getdate(), 120), '" + form.getSustituible()+"', '" + form.getTolva()+"' ) ";
		    System.out.println("nuevo -->" +qry );		
	 	
	    try {
	         PreparedStatement pstat = con.prepareStatement(qry);
	         result=pstat.executeUpdate();
	       
	     } catch (SQLException e) {
	         e.printStackTrace();
	     }finally {con.close();}
		return result>0;
	}
	 
	


	@SuppressWarnings("finally")
	public static boolean nuevo(SustXComposicion sust) throws ClassNotFoundException, SQLException {

        int result=0;
		  Connection con = Conexion.conectar();
	  	   String qry = " INSERT INTO dbo.SPD_sustXComposicion  ";
	  	   		qry+= " ( ";
	  	   		qry+= " 	idRobot, codGtVmp, nomGtVmp, codGtVmpp, nomGtVmpp, rentabilidad, ";
	  	   		qry+= " 	ponderacion, codiLab, nombreLab, ultimaModificacion, CN6, CN7, nombreMedicamento, comentarios, fechaCreacion, tipoCarga, sustituible, tolva ";
	  	   		qry+= " ) VALUES (";
	  	   		qry+= "		'"+ sust.getIdRobot()+"', '"+ sust.getCodGtVmp()+"', '"+ sust.getNomGtVmp()+"', '"+ sust.getCodGtVmpp()+"', '"+ sust.getNomGtVmpp()+"', "+  sust.getRentabilidad()+", ";
	  	   		qry+= "  	" + sust.getPonderacion()+", '" + sust.getCodiLab()+"', '"+ sust.getNombreLab()+"',   CONVERT(datetime, getdate(), 120), '" + sust.getCn6() + "', '" + sust.getCn7()+ "', ";
	       		qry+= "		'"+sust.getNombreMedicamento()+"', '"+ sust.getComentarios()+"',   CONVERT(datetime, getdate(), 120) , '" +sust.getTipoCarga()+"' , '" +sust.getSustituible()+"', '" +sust.getTolva()+"')" ;
		    System.out.println("nuevo SustXComposicion -->" +qry );		
	 	
	    try {
	         PreparedStatement pstat = con.prepareStatement(qry);
	         result=pstat.executeUpdate();
	       
	     } catch (SQLException e) {
	    	 result =0;
	    	 e.printStackTrace();
	     }
	    finally{
	    	con.close();
			return result>0;
	    }
	}

	
	
	public static String getNombreLaboratorio(String codiLab) throws ClassNotFoundException, SQLException {
		List<BdConsejo>  lista = new ArrayList<BdConsejo>();
		if(codiLab!=null && !codiLab.equals(""))
			lista= BdConsejoDAO.getLabsBdConsejo(codiLab, null, 0, 1);
		if(lista!=null && lista.size()>0 && lista.get(0).getNombreLaboratorio()!=null)
			return lista.get(0).getNombreLaboratorio();
		else return null;
	}

	private static String getNomGtVmpp(String id) throws ClassNotFoundException, SQLException {
		  List<BdConsejo>  lista = new ArrayList<BdConsejo>();
		  if(id!=null && !id.equals(""))
				lista=  BdConsejoDAO.getListaGtVmpp(null, null, id, null);
		if(lista!=null && lista.size()>0 && lista.get(0).getNomGtVmp()!=null)
			return lista.get(0).getNomGtVmp();
		else return null;
	}

	
	public static  boolean nuevoAsignacionMasiva(SustXComposicionForm form) throws ClassNotFoundException, SQLException
	{
		int result=0;
		desAsignacionMasiva(form);
		Connection con = Conexion.conectar();
		String query = " INSERT INTO dbo.SPD_sustXComposicion ";
		query+= " (codGtVmpp, nomGtVmpp, rentabilidad, ponderacion, sustituible, codiLab, ultimaModificacion, comentarios, nombreLab, fechaCreacion)  ";
		query+= " ( select distinct b.codGtVmpp,  b.NomGtVmpp, " + form.getRentabilidad() + ", " + form.getPonderacion() + ", " + form.getSustituible() + ", ";
		query+= "'"+ form.getFiltroCodiLaboratorio()+"',   CONVERT(datetime, getdate(), 120), 'cargaMasiva', ";
		query+= "'" + getNombreLaboratorio(form.getFiltroCodiLaboratorio())+"',   CONVERT(datetime, getdate(), 120) ";
  				
		query+= getFrom(form);
		query+= getWhere(form);
			
	/*	query+= "AND NOT EXISTS (SELECT codConjHomog, codiLab FROM dbo.SPD_sustXComposicion ";
		query+= "WHERE codConjHomog=b.codConjHomog AND  codiLab='"+ form.getFiltroCodiLaboratorio()+"')";
	   */ 
		query+= "  );  ";
	//	query+= "commit ";
 		System.out.println("nuevoAsignacionMasiva -->" +query );		
		 	
	    try {
	         PreparedStatement pstat = con.prepareStatement(query);
	         result=pstat.executeUpdate();
	       
	     } catch (SQLException e) {
	         e.printStackTrace();
	     }finally {con.close();}
		return result>0;

	}

	public static  boolean desAsignacionMasiva(SustXComposicionForm form) throws ClassNotFoundException, SQLException
	{
		int result=0;
		Connection con = Conexion.conectar();
		String query = " DELETE FROM dbo.SPD_sustXComposicion  ";
		query+= " WHERE 1 = 1 ";

		if(form.getOidSustXComposicion()>0)
			query+=  " and oidSustXComposicion = '"+form.getOidSustXComposicion()+"'";
		if(form.getFiltroTextoABuscar()!=null && !form.getFiltroTextoABuscar().equals(""))
		{
			query+=  " and ( ";
			query+=  "  nomGtVmpp like '%"+form.getFiltroTextoABuscar() +"%' ";
			query+=  "  OR nombreLab  like '%"+form.getFiltroTextoABuscar() +"%' ";
			query+=  "  OR comentarios  like '%"+form.getFiltroTextoABuscar() +"%' ";
			query+=  " ) ";
		}
		if(form.getFiltroCodiLaboratorio()!=null && !form.getFiltroCodiLaboratorio().equals(""))
			query+=  " and codiLab =  '"+form.getFiltroCodiLaboratorio() +"' ";
		if(form.getCodiLab()!=null && !form.getCodiLab().equals(""))
			query+=  " and codiLab =  '"+form.getCodiLab() +"' ";
		if(form.getNombreLab()!=null && !form.getNombreLab().equals(""))
			query+=  " and nombreLab like  '%"+form.getNombreLab() +"%' ";	
		if(form.isFiltroCheckedLabsSoloAsignados())
			query+=  " and codiLab =  '"+form.getFiltroCodiLaboratorio() +"' ";
 		System.out.println("nuevoAsignacionMasiva -->" +query );		
		 	
	    try {
	         PreparedStatement pstat = con.prepareStatement(query);
	         result=pstat.executeUpdate();
	       
	     } catch (SQLException e) {
	         e.printStackTrace();
	     }finally {con.close();}
		return result>0;

	}

	public static SustXComposicion getByFilters(String idRobot, String codiLab, String nombreLab, String codGtVmpp, String nomGtVmpp) throws ClassNotFoundException, SQLException 
	{
		String 	query = " select g.oidSustXComposicion, g.idRobot, g.codGtVmp, g.nomGtVmp, g.codGtVmpp, g.nomGtVmpp, g.rentabilidad, ";
		query+=" g.ponderacion, g.codiLab, g.nombreLab, g.CN6, g.CN7, g.comentarios, g.sustituible, g.tolva, g.ultimaModificacion, g.fechaCreacion,  (g.rentabilidad + g.ponderacion) as nota   ";					// grupo VM (igual principio activo).

		query+=" from dbo.SPD_sustXComposicion g "; //INNER JOIN bd_consejo b on g.cn6=b.codigo ";
		
		query+=" where 1=1  ";
		query+=" and g.idRobot =  '"+idRobot +"' ";	//siempre ha de venir
		//query+=" and g.codGtVmpp is not null ";
		if(StringUtil.quitaEspacios(codiLab)!=null && !StringUtil.quitaEspacios(codiLab).equals("")) 
			query+=  " and g.codiLab =  '"+codiLab +"' ";		//preferencia de codi antes que nombre
		else
			query+=  " and g.nombreLab =  '"+nombreLab +"' ";
		

		if(StringUtil.quitaEspacios(codGtVmpp)!=null && !StringUtil.quitaEspacios(codGtVmpp).equals("")) 
			query+=  " and g.codGtVmpp =  '"+codGtVmpp +"' ";		//preferencia de codi antes que nombre
		else
			query+=  " and g.nomGtVmpp =  '"+nomGtVmpp +"' ";
		
		SustXComposicion  c = null;
    	Connection con = Conexion.conectar();
	    ResultSet resultSet = null;
	   try {
	        PreparedStatement pstat = con.prepareStatement(query);
	        resultSet = pstat.executeQuery();
	        while (resultSet.next())
	        {
	        	c =creaObjeto(resultSet); 
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }finally {con.close();}
	    return c;
	}

	public static boolean eliminaTodosRegistros(String idRobot)  throws ClassNotFoundException, SQLException {
        int result=0;
        Connection con = Conexion.conectar();
        String qry = "delete dbo.SPD_sustXComposicion WHERE idRobot='"+idRobot+"' ";
        System.out.println("eliminaTodosRegistros -->" +qry );		
	 	
	    try {
	         PreparedStatement pstat = con.prepareStatement(qry);
	         result=pstat.executeUpdate();
	       
	     } catch (SQLException e) {
	         e.printStackTrace();
	     }finally {con.close();}
		
		return result>0;
	}

	public static boolean borraAnteriores(SustXComposicion sustBak) throws ClassNotFoundException, SQLException {
		int result=0;
        Connection con = Conexion.conectar();
        String qry = "delete dbo.SPD_sustXComposicion ";
        		qry+=" WHERE idRobot='"+sustBak.getIdRobot()+"' ";
        		qry+=" AND codGtVmpp='"+sustBak.getCodGtVmpp()+"' ";
        		qry+=" AND ponderacion='"+sustBak.getPonderacion()+"' ";
        System.out.println("borraAnteriores -->" +qry );		
	    try {
	    	PreparedStatement pstat = con.prepareStatement(qry);
	    	result=pstat.executeUpdate();
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }finally {con.close();}
		return result>0;
	}

	
	
	

}

	


 
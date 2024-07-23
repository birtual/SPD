package lopicost.spd.persistence;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lopicost.config.pool.dbaccess.Conexion;
import lopicost.spd.iospd.IOSpdApi;
import lopicost.spd.model.BdConsejo;
import lopicost.spd.model.DivisionResidencia;
import lopicost.spd.model.GestSustituciones;
import lopicost.spd.model.GestSustitucionesXResi;
import lopicost.spd.security.helper.VisibilidadHelper;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.struts.bean.FormasFarmaceuticas;
import lopicost.spd.struts.bean.TiposAccionBean;
import lopicost.spd.struts.form.GestSustitucionesForm;
import lopicost.spd.utils.SPDConstants;
import lopicost.spd.utils.StringUtil;
 
 
public class GestSustitucionesDAO {
	
	public static String nuevalinea = System.getProperty("line.separator");
	
    public static int  getCountSustituciones(String spdUsuario, GestSustitucionesForm form) throws Exception {
    	
     String qry = getQuerySustituciones(spdUsuario,  form.getOidDivisionResidenciaFiltro(),  form.getOidGestSustituciones(), form.getOidGestSustitucionesXResi(),  form.getFiltroNombreCorto(), form.getFiltroMedicamentoResi(), form.getFiltroNombreCortoOK(), true, 0, 0, form.getFiltroExisteBdConsejo(), form.getFiltroAccion(), form.getFiltroFormaFarmaceutica(), false );
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
    
    public static List<GestSustituciones> getSustitucionesListado(String spdUsuario, GestSustitucionesForm form, int inicio, int fin, boolean todas) throws Exception {

    	return getSustituciones( spdUsuario, form.getOidDivisionResidenciaFiltro(), -1 /* form.getOidGestSustituciones()*/,  -1 /*form.getOidGestSustitucionesXResi()*/,  
    			form.getFiltroNombreCorto(), form.getFiltroMedicamentoResi(), form.getFiltroNombreCortoOK(), 
    			false, inicio, fin, form.getFiltroExisteBdConsejo(), form.getFiltroAccion(), form.getFiltroFormaFarmaceutica(), todas );
    }	
    
    
    
    private static List<GestSustituciones> getSustituciones(String spdUsuario, int oidDivisionResidenciaFiltro, int oidGestSustituciones, int oidGestSustitucionesXResi, String filtroNombreCorto, String filtroMedicamentoResi,
			String filtroNombreCortoOK, boolean count, int inicio, int fin, String filtroExisteBdConsejo, String filtroAccion,  String filtroFormaFarmaceutica, boolean todas)  throws Exception {
	 
    	String qry = getQuerySustituciones(spdUsuario,  oidDivisionResidenciaFiltro, oidGestSustituciones, oidGestSustitucionesXResi, filtroNombreCorto, filtroMedicamentoResi,  filtroNombreCortoOK, count, inicio, fin, filtroExisteBdConsejo, filtroAccion,  filtroFormaFarmaceutica, todas);
    	
    	
	    Connection con = Conexion.conectar();
        	
        		
       ResultSet resultSet = null;
       List<GestSustituciones> listaGestSustituciones= new ArrayList<GestSustituciones>();
       
       try {
            PreparedStatement pstat = con.prepareStatement(qry);
            resultSet = pstat.executeQuery();
             
            int oidGestSustitucionesAnterior =0; // la tabla gestSustituciones es 1 a * con gestSustitucionesXResi, para controlar cuando cambia lo guardamos en variable el oid anterior
            int oidGestSustitucionesEnCurso =0; // oidsust que se trata en ek bucle
            int contadorSustXResi=0; //inicializamos a 0 el número de filas de sustitucionesXResi que aparecen, para saber la posición en el arrayList
           
            BdConsejo bdConsejo= null;		//CN puente equivalente al CN_Resi. Es un CN válido para identificar un código de la resi, no es necesario que sea de la biblia.
            BdConsejo bdConsejoResi= null;		//Objeto que mira si el CNResi equivale a un bdConsejo directamente. PAra poder hacer comprobaciones de posteriores de equivalencias entre cnResi - cnOk.
            BdConsejo bdConsejoBiblia= null;	// CN de la biblia equivalente al CN puente anterior
          	
            GestSustituciones c = null;
            List<GestSustitucionesXResi> listaSustitucionesXResi= null;
          	String mensajesInfoBase="";
          	String mensajesAlertaBase="";
           	String mensajesInfoXResi="";
           	String mensajesAlertaXResi="";
                                 
            while (resultSet.next()) {
             	//controlamos si es una sust nueva
            	oidGestSustitucionesEnCurso=resultSet.getInt("oidGestSustituciones");
               	if(oidGestSustitucionesAnterior != oidGestSustitucionesEnCurso)  //si son iguales quiere decir que hay más de un registro en sustXResi, actualizamos posición del list
            	{

               		if(c!=null)
               		{
                   		//como vemos que hay cambio de sustitución, cerramos la anterior.
           				c.setListaSustitucionesXResi(listaSustitucionesXResi);
               			//Añadimos el número de SustXResi asociadas a esta sustitución.
               			if(listaSustitucionesXResi!=null && listaSustitucionesXResi.size()>=1 && listaSustitucionesXResi.get(0).getOidGestSustitucionesXResi()>0)
               				c.setSustXResiAsociados(listaSustitucionesXResi.size());
               				//añadimos la sust anterior a la lista y creamos una  nueva
                   	    listaGestSustituciones.add(c);
               		}
               		
               		//creamos nuevo objeto de sustitución e inicializamos objetos asociados	
            		c = new GestSustituciones();
                	bdConsejo= new BdConsejo();
                	bdConsejoResi= new BdConsejo();
                	bdConsejoBiblia= new BdConsejo();
                	
                	//lista de sustituciones específicas por Resi            	
            		listaSustitucionesXResi= new ArrayList<GestSustitucionesXResi>();
                  	contadorSustXResi=0;
            		
            	}	
            	else
            	{
            		//si es el mismo oidsust añadimos al array el sustXResi anterior y creamos uno nuevo
            	//	listaSustitucionesXResi.add(contadorSustXResi, cr);
               		contadorSustXResi++;
               		
            	}
               	
               	//inicialización de objeto sustXResi. Siempre será unno nuevo en el bucle
                GestSustitucionesXResi  cr =new GestSustitucionesXResi();
            	
        		//creación del objeto sustitución base
            	c.setOidGestSustituciones(resultSet.getInt("oidGestSustituciones"));
            	c.setFecha(resultSet.getDate("fechaSust"));
            	c.setCnResi(resultSet.getString("cnResi"));
            	c.setMedicamentoResi(resultSet.getString("medicamentoResi"));
               	c.setCnOk(resultSet.getString("cnOk"));
               	c.setNombreCorto(resultSet.getString("nombreCorto"));
               	c.setFormaFarmaceuticaSustitucion(resultSet.getString("formaFarmaceuticaSustitucion"));
             	c.setAccion(resultSet.getString("accion"));
            	c.setComentario(resultSet.getString("comentario"));
            	c.setCodGtVmppOk(resultSet.getString("codGtVmppOk"));
            	c.setNombreMedicamentoOk(resultSet.getString("nombreMedicamentoOk"));
            	c.setNomGtVmppOk(resultSet.getString("nomGtVmppOk"));
            	c.setSustituible(resultSet.getString("sustituible"));
            	
              	//0 - creamos el objeto bdconsejoResi en caso que el cnResi sea un cn válido del consejo
            	bdConsejoResi.setCnConsejo(resultSet.getString("cnConsejoResi"));
               	
            	  
               	if(bdConsejoResi.getCnConsejo()==null || bdConsejoResi.getCnConsejo().equals(""))
               	{
               		c.setExisteBdConsejoResi("NO");
               		mensajesInfoBase+="- El código de la resi NO existe en bbdd Central. </br>";
               		mensajesInfoXResi+="- El código de la resi NO existe en bbdd Central. </br>";
               	}
               	else
               	{
               		bdConsejoResi.setNombreConsejo(resultSet.getString("nombreConsejoResi"));
                	bdConsejoResi.setPresentacion(resultSet.getString("presentacionConsejoResi"));
                	bdConsejoResi.setNombreMedicamento(resultSet.getString("nombreConsejoResi") + " " + resultSet.getString("presentacionConsejoResi"));
                	bdConsejoResi.setNombreFormaFarmaceutica(resultSet.getString("nomFormaFarmaceuticaConsejoResi"));
               		bdConsejoResi.setCodGtVmpp(resultSet.getString("codGtVmppResi"));
               		bdConsejoResi.setNomGtVmpp(resultSet.getString("nomGtVmppConsejoResi"));
             		c.setExisteBdConsejoResi("SI");
               	}
             	String codGtVmppResi= bdConsejoResi.getCodGtVmpp(); //para controlar si hay código cruzado
             	c.setBdConsejo(bdConsejoResi);
              	//FIN 0 -
            	
            	
             	//1 - creamos el objeto bdconsejo para la sustitución base con los datos base del medicamento
               	bdConsejo.setCnConsejo(resultSet.getString("CODIGO"));
               	
               	if(bdConsejo.getCnConsejo()==null || bdConsejo.getCnConsejo().equals(""))
               	{
               		c.setExisteBdConsejo("NO");
               		mensajesInfoBase+="- El código de la sustitución NO existe en bbdd Central.  <br>";
               	}
               	else
               	{
                	bdConsejo.setNombreConsejo(resultSet.getString("nombre"));
                	bdConsejo.setPresentacion(resultSet.getString("presentacion"));
               		bdConsejo.setNombreMedicamento(resultSet.getString("nombre") + " " + resultSet.getString("presentacion"));
               		bdConsejo.setNombreFormaFarmaceutica(resultSet.getString("nomFormaFarmaceutica"));
               		bdConsejo.setCodGtVmpp(resultSet.getString("codGtVmpp"));
               		bdConsejo.setNomGtVmpp(resultSet.getString("nomGtVmpp"));
             		c.setExisteBdConsejo("SI");
               	}
            	c.setBdConsejo(bdConsejo);
              	//FIN 1 -
            	 
             	 
             	//2 - creamos el objeto bdconsejoBiblia 
               	bdConsejoBiblia.setCnConsejo(resultSet.getString("cnBiblia"));
               	if(bdConsejoBiblia.getCnConsejo()==null || bdConsejoBiblia.getCnConsejo().equals(""))
               	{
               		c.setExisteBdConsejoBiblia("NO");
               		mensajesInfoBase+="- Falta asignar un código Vademecum. <br>";
               	}
               	else
               	{
               		bdConsejoBiblia.setNombreMedicamento(resultSet.getString("nombreMedicamentoBiblia"));
             		bdConsejoBiblia.setCodigoLaboratorio(resultSet.getString("codiLabBiblia"));
             		bdConsejoBiblia.setNombreLaboratorio(resultSet.getString("nombreLabBiblia"));
             		bdConsejoBiblia.setCodGtVmpp(resultSet.getString("codGtVmpp"));
            		bdConsejoBiblia.setNomGtVmpp(resultSet.getString("nomGtVmpp"));
            		bdConsejoBiblia.setNota(resultSet.getInt("notaBiblia"));
             		c.setExisteBdConsejoBiblia("SI");
               	}
             	c.setBdConsejoBiblia(bdConsejoBiblia);
               	//si no es sustituible dejamos el anterior
               	if(c.getSustituible()!=null  && c.getSustituible().equals("0"))
               		c.setBdConsejoBiblia(bdConsejo);

               	String codGtVmppBiblia = bdConsejo.getCodGtVmpp(); //para controlar si las sustituciones que cuelgan de aquí tienen el mismo grupo terapéutico
             	//FIN 2 -
             	 
             	
 
               	cr.setOidGestSustitucionesXResi(resultSet.getInt("oidGestSustitucionesXResi"));
               	cr.setIdDivisionResidencia(resultSet.getString("idDivisionResidencia2"));
               	cr.setNombreDivisionResidencia(resultSet.getString("nombreDivisionResidencia"));
            	cr.setOidDivisionResidencia(resultSet.getInt("oidDivisionResidencia"));
               	cr.setFechaSustXResi(resultSet.getDate("fechaSustXResi"));
              	cr.setAccionSustXResi(resultSet.getString("accionSustXResi")); 
               	cr.setCnOkSustXResi(resultSet.getString("cnOkSustXResi")); 					//if(cr.getNuevoCnOk()==null || cr.getNuevoCnOk().contentEquals("")) cr.setNuevoCnOk(c.getCnOk());
              	cr.setComentarioSustXResi(resultSet.getString("comentarioSustXResi"));
              	cr.setNombreCortoSustXResi(resultSet.getString("nombreCortoSustXResi"));	//	if(cr.getNuevoNombreCorto()==null || cr.getNuevoNombreCorto().contentEquals("")) cr.setNuevoNombreCorto(c.getNombreCorto());    	
              	cr.setOidGestSustituciones(resultSet.getInt("oidGestSustituciones"));
              	cr.setOidUsuario(resultSet.getInt("oidUsuario2"));
            	cr.setCodGtVmppOkSustXResi(resultSet.getString("codGtVmppOkSustXResi"));	
            	cr.setNombreMedicamentoOkSustXResi(resultSet.getString("nombreMedicamentoOkSustXResi"));
            	cr.setNomGtVmppOkSustXResi(resultSet.getString("nomGtVmppOkSustXResi"));
               	cr.setSustituibleXResi(resultSet.getString("sustituibleXResi"));
               	
              	//3 - creamos el objeto bdconsejo para la sustituciónXResi con los datos base del medicamento
            	BdConsejo bdConsejoSustXResi= new BdConsejo();	//CN puente equivalente al CN_Resi específico.
             	BdConsejo bdConsejoSustXResiBiblia= new BdConsejo(); // CN de la biblia equivalente al CN puente anterior
             	bdConsejoSustXResi.setCnConsejo(resultSet.getString("CODIGO2"));
               	if(bdConsejoSustXResi.getCnConsejo()==null || bdConsejoSustXResi.getCnConsejo().equals(""))
               	{
               		cr.setExisteBdConsejoSustXResi("NO");
               		mensajesInfoXResi+="- El código de la sustitución propuesta NO existe en bbdd Central. <br>";
               	}
               	else
               	{
               		bdConsejoSustXResi.setNombreConsejo(resultSet.getString("nombre2"));
               		bdConsejoSustXResi.setPresentacion(resultSet.getString("presentacion2"));
               		bdConsejoSustXResi.setNombreMedicamento(resultSet.getString("nombre2") + " " + resultSet.getString("presentacion2"));
               		bdConsejoSustXResi.setNombreFormaFarmaceutica(resultSet.getString("nomFormaFarmaceutica2"));
               		bdConsejoSustXResi.setCodGtVmpp(resultSet.getString("codGtVmppSustXResi"));
               		bdConsejoSustXResi.setNomGtVmpp(resultSet.getString("nomGtVmppSustXResi"));
               		cr.setExisteBdConsejoSustXResi("SI");
               	}
             	cr.setBdConsejoSustXResi(bdConsejoSustXResi);

            	//FIN 3 -
             	 
             	//4 - creamos el objeto bdConsejoSustXResiBiblia 
           		bdConsejoSustXResiBiblia.setCnConsejo(resultSet.getString("cnBibliaSustXResi"));
               	if(bdConsejoSustXResiBiblia.getCnConsejo()==null || bdConsejoSustXResiBiblia.getCnConsejo().equals(""))
               	{
               		cr.setExisteBdConsejoSustXResiBiblia("NO");
               		mensajesInfoXResi+="- Falta asignar un código Vademecum.  <br>";
               	}
               	else
               	{
               		bdConsejoSustXResiBiblia.setNombreMedicamento(resultSet.getString("nombreMedicamentoBibliaSustXResi"));
              		bdConsejoSustXResiBiblia.setCodGtVmpp(resultSet.getString("codGtVmppSustXResi"));
              		bdConsejoSustXResiBiblia.setNomGtVmpp(resultSet.getString("nomGtVmppSustXResi"));
              		bdConsejoSustXResiBiblia.setCodigoLaboratorio(resultSet.getString("codiLabBibliaSustXResi"));
               		bdConsejoSustXResiBiblia.setNombreLaboratorio(resultSet.getString("nombreLabBibliaSustXResi"));
               		bdConsejoSustXResiBiblia.setNota(resultSet.getInt("notaBibliaSustXResi"));
             		cr.setExisteBdConsejoSustXResiBiblia("SI");
               	}
               		
             	cr.setBdConsejoSustXResiBiblia(bdConsejoSustXResiBiblia);
             	//si no es sustituible dejamos el anterior
               	if(cr.getSustituibleXResi()!=null  && cr.getSustituibleXResi().equals("0"))
               		cr.setBdConsejoSustXResiBiblia(bdConsejoSustXResi);
             	
             	String codGtVmppSustXResiBiblia = bdConsejoSustXResiBiblia.getCodGtVmpp(); //para controlar si las sustituciones que cuelgan de aquí tienen el mismo codGtVmpp (Grupo terapéutico)
             	//FIN 4 -
             	
             	
           		//comprobamos que tenga el mismo codGtVmpp (conjunto homogéneo) que la Sustitución madre
             	if(codGtVmppSustXResiBiblia!=null && !codGtVmppSustXResiBiblia.equals("") && !Objects.equals(codGtVmppBiblia, codGtVmppSustXResiBiblia))
             	{
             		cr.setGtVmppDiferente(true);
             		mensajesAlertaXResi+="- El conjunto homogéneo (GtVmpp) de sustitución Resi es diferente al que figura por defecto.  <br>";
             	}
           		//para detectar códigos cruzados, miramos los codGtVmpp de cnResi y cnResiConsejo en caso que exista
             	
             	if(codGtVmppResi!=null && !codGtVmppResi.equals("") && !Objects.equals(codGtVmppResi, codGtVmppBiblia))
             	{
             		c.setGtVmppDiferente(true);
             		mensajesAlertaBase+="- El conjunto homogéneo (GtVmpp) del CN de la resi es diferente al del Vademecum (Biblia) .  <br>";
             	}
             	
             	//si no va en PASTILLERO no hace falta que pongamos alerta en otro color. Traspasamos la información a info
              	//if(c!=null&& c.getAccion()!=null && (c.getAccion().equals("NO_PINTAR") || c.getAccion().equals("SOLO_INFO"))) {
             		//mensajesInfoBase+=mensajesAlertaBase+ "ES UN NO_PINTAR";
             		//mensajesInfoBase+=mensajesAlertaBase;
             		//mensajesAlertaBase="";
            	//}
             	//si es un NO_PINTAR no ponemos nada ni como alerta ni info
             	if(c!=null&& c.getAccion()!=null && c.getAccion().equals("NO_PINTAR") ) {
                 	mensajesInfoBase="";
                 	mensajesAlertaBase="";
             	}
 /*              	if(cr!=null&& cr.getAccionSustXResi()!=null && (cr.getAccionSustXResi().equals("NO_PINTAR") || cr.getAccionSustXResi().equals("SOLO_INFO"))) {
               		mensajesInfoXResi+=mensajesAlertaXResi;
               		mensajesAlertaXResi="";
             	}
*/ 
              	if(cr!=null&& cr.getAccionSustXResi()!=null && cr.getAccionSustXResi().equals("NO_PINTAR") ) {
               		mensajesInfoXResi="";
               		mensajesAlertaXResi="";
            	}	
           		//Generamos los datos de la sustXResi
       			c.setMensajesInfo(mensajesInfoBase);
       			c.setMensajesAlerta(mensajesAlertaBase);
               	cr.setMensajesInfo(mensajesInfoXResi);
               	cr.setMensajesAlerta(mensajesAlertaXResi);
       			listaSustitucionesXResi.add(contadorSustXResi, cr);
              	//inicializamos mensajes
           	    mensajesAlertaBase="";
           	    mensajesAlertaXResi="";
           	    mensajesInfoBase="";
           	    mensajesInfoXResi="";
          		oidGestSustitucionesAnterior = oidGestSustitucionesEnCurso; //actualizamos el dato de la sustitución

             }
            //añadimos el último objeto tratado
            if(c!=null)
       		{
           		//como vemos que hay cambio de sustitución, cerramos la anterior.
    			c.setListaSustitucionesXResi(listaSustitucionesXResi);
       			//Añadimos el número de SustXResi asociadas a esta sustitución.
    			if(listaSustitucionesXResi!=null && listaSustitucionesXResi.size()>=1 && listaSustitucionesXResi.get(0).getOidGestSustitucionesXResi()>0)
       				c.setSustXResiAsociados(listaSustitucionesXResi.size());

           		//añadimos la sust anterior a la lista y creamos una  nueva
           	    listaGestSustituciones.add(c);

                
       		}
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {con.close();}
 
        return listaGestSustituciones;
    }

    
    
    
    /*
    	private static String getQuerySustituciones(int oidDivisionResidencia, int oidGestSustituciones, int oidGestSustitucionesXResi,String getFiltroNombreCorto, String filtroMedicamentoResi, String filtroNombreCortoOK, boolean count, int inicio, int fin, String condicionBusqueda, String filtroAccion, String filtroFormaFarmaceutica, boolean todas) {
					
		   
		String select =" select g.oidGestSustituciones, g.fechaSust, g.cnResi, ";
				select+="  g.medicamentoResi, g.cnOk, g.nombreCorto, g.formaFarmaceuticaSustitucion, ";
				select+="  g.nombreMedicamentoOk, g.codGtVmppOk, g.nomGtVmppOk, ";
				select+="  c0.CODIGO as cnConsejoResi,  c0.nombre as nombreConsejoResi, c0.presentacion as presentacionConsejoResi, ";
				select+="  c0.codGtVmpp as codGtVmppResi, c0.nomGtVmpp as nomGtVmppConsejoResi,  c0.nomFormaFarmaceutica as nomFormaFarmaceuticaConsejoResi,  ";
				select+="  g.accion, g.comentario, g.oidUsuario, g.comentarioInterno, g.sustituible, ";
				select+="  gr.nombreMedicamentoOkSustXResi, gr.codGtVmppOkSustXResi, gr.nomGtVmppOkSustXResi, ";
				select+="  gr.oidGestSustitucionesXResi, gr.oidGestSustituciones as oidGestSustituciones2, gr.fechaSustXResi, gr.oidDivisionResidencia,  ";
				select+="  gr.idDivisionResidencia as idDivisionResidencia2, gr.cnOkSustXResi, gr.nombreCortoSustXResi, gr.accionSustXResi,  ";
				select+="  gr.comentarioSustXResi, gr.oidUsuario as oidUsuario2,  gr.sustituible as sustituibleXResi, ";
				select+="  d.nombreDivisionResidencia, c.CODIGO, c.nombre, c.presentacion, c.nomFormaFarmaceutica, ";
				select+="  c2.CODIGO as codigo2, c2.nombre as nombre2, c2.presentacion as presentacion2, c2.nomFormaFarmaceutica as nomFormaFarmaceutica2, ";
				select+="  c.codGtVmpp as codGtVmpp, c.nomGtVmpp as nomGtVmpp, c2.codGtVmpp as codGtVmppSustXResi, c2.nomGtVmpp as nomGtVmppSustXResi, ";
				select+="  n1.cn as cnBiblia, n1.nombreMedicamentoBiblia, n1.codiLabo as codiLabBiblia, n1.nombreLab as nombreLabBiblia, n1.nota as notaBiblia, "; //n1.codConjHomog as codConjHomogBiblia, n1.nomConjHomog as nomConjHomogBiblia, ";
				select+="  n2.cn as cnBibliaSustXResi, n2.nombreMedicamentoBiblia as nombreMedicamentoBibliaSustXResi, n2.codiLabo as codiLabBibliaSustXResi, n2.nombreLab as nombreLabBibliaSustXResi, n2.nota as notaBibliaSustXResi "; // n2.codConjHomog as codConjHomogBibliaSustXResi, n2.nomConjHomog as nomConjHomogBibliaSustXResi, ";
				
		String 	from =" from dbo.gest_sustituciones g ";

		
		//if(todas)
				from+=" left join dbo.gest_sustitucionesXResi gr on (g.oidGestSustituciones=gr.oidGestSustituciones  ";
				
	//	else
		//		from+=" INNER join dbo.gest_sustitucionesXResi gr on (g.oidGestSustituciones=gr.oidGestSustituciones  ";
		

				if(oidDivisionResidencia>0)
					from+=  " and gr.oidDivisionResidencia  = '"+oidDivisionResidencia +"' ";

				from+=  " ) ";
				
				if(oidDivisionResidencia>0)
					from+=" INNER join dbo.bd_divisionResidencia d  on (gr.oidDivisionResidencia=d.oidDivisionResidencia) ";
				else
					from+=" left join dbo.bd_divisionResidencia d  on (gr.oidDivisionResidencia=d.oidDivisionResidencia) ";
				
				
		String where =" where 1=1  ";
		String order =" ";
		String otros =" ";
			
		if(count)  //contador
			select = "select count(distinct g.oidGestSustituciones) as quants";
		
		
		if(condicionBusqueda!=null && condicionBusqueda.equals("1")) //No existen en BdConsejo
		{
			from+=  " left join bd_consejo c0 on (c0.codigo =  SUBSTRING(CAST(g.cnResi  AS CHAR(7)),1,6)   )  ";	
			from+=  " left join bd_consejo c on (c.codigo =  SUBSTRING(CAST(g.cnOk  AS CHAR(7)),1,6)   ) ";
			from+=  " left join bd_consejo c2 on (c2.codigo =  SUBSTRING(CAST(gr.cnOkSustXResi  AS CHAR(7)),1,6)   ) ";
			where+= " and c.codigo is null ";
			where+= " and c2.codigo is null ";
		}
		else if(condicionBusqueda!=null && condicionBusqueda.equals("2")) //sólo los que existen en BdConsejo
		{
			from+=  " left join bd_consejo c0 on (c0.codigo =  SUBSTRING(CAST(g.cnResi  AS CHAR(7)),1,6)   )  ";	
			from+=  " inner join bd_consejo c on (c.codigo =  SUBSTRING(CAST(g.cnOk AS CHAR(7)),1,6)   ) ";
			from+=  " inner join bd_consejo c2 on (c2.codigo =  SUBSTRING(CAST(gr.cnOkSustXResi  AS CHAR(7)),1,6)   ) ";
		}
		else //if(condicionBusqueda==null || condicionBusqueda.equals("") || condicionBusqueda.equals("0")) //Todos
		{
			from+=  " left join bd_consejo c0 on (c0.codigo =  SUBSTRING(CAST(g.cnResi  AS CHAR(7)),1,6)   )  ";	
			from+=  " left join bd_consejo c on (c.codigo =  SUBSTRING(CAST(g.cnOk  AS CHAR(7)),1,6)   ) ";
			from+=  " left join bd_consejo c2 on (c2.codigo =  SUBSTRING(CAST(gr.cnOkSustXResi  AS CHAR(7)),1,6)   ) ";

		}
		 
		
		//from+=  " left join dbo.gest_sustPerConjuntsHomogenis ch on ( ch.codConjHomog=c.codConjHomog and ch.codConjHomog>0) ";
		//from+=  " left join dbo.gest_sustPerConjuntsHomogenis GtVmpp on ( GtVmpp.codConjHomog=c2.codConjHomog and GtVmpp.codConjHomog>0) ";

		/*
		//miramos correspondencia con Biblia
		from+=  " left join  ";
		from+=  " ( ";
		from+=  " 	select distinct b.nota , b.codConjHomog, b.nomConjHomog, b2.codiLab, b2.nombreLab, b.CODIGO as cn  , b3.NOMBRE+' '+b3.PRESENTACION  as nombreMedicamentoBiblia";
		from+=  " 	from  ";
		from+=  " 	( ";
		from+=  " 		select max(s.ponderacion + s.rentabilidad) as nota,  max(c.codigo) as codigo, s.codConjHomog, s.nomConjHomog  ";
		from+=  " 		from gest_sustPerConjuntsHomogenis s, bd_Consejo c where s.codConjHomog=c.codConjHomog ";
		from+=  " 		group by s.codConjHomog, s.nomConjHomog    ";
		from+=  " 	) b ";
		from+=  " 	inner join  gest_sustPerConjuntsHomogenis b2  on ((b2.ponderacion + b2.rentabilidad = b.nota)  and b.codConjHomog=b2.codConjHomog) ";
		from+=  " 	left join bd_Consejo b3 on( b.codigo=b3.codigo)  ";
		from+=  "  ) n1 on  ( n1.codConjHomog=c.codConjHomog) ";

		//miramos correspondencia con Biblia la sustitución específica de la resi
		from+=  " left join  ";
		from+=  " ( ";
		from+=  " 	select distinct bx.nota , bx.codConjHomog, bx.nomConjHomog, bx2.codiLab, bx2.nombreLab, bx.CODIGO as cn  , bx3.NOMBRE+' '+bx3.PRESENTACION   as nombreMedicamentoBiblia";
		from+=  " 	from  ";
		from+=  " 	( ";
		from+=  " 		select max(sx.ponderacion + sx.rentabilidad) as nota,  max(cx.codigo) as codigo, sx.codConjHomog, sx.nomConjHomog  ";
		from+=  " 		from gest_sustPerConjuntsHomogenis sx, bd_Consejo cx where sx.codConjHomog=cx.codConjHomog ";
		from+=  " 		group by sx.codConjHomog, sx.nomConjHomog    ";
		from+=  " 	) bx ";
		from+=  " 	inner join  gest_sustPerConjuntsHomogenis bx2  on ((bx2.ponderacion + bx2.rentabilidad = bx.nota)  and bx.codConjHomog=bx2.codConjHomog) ";
		from+=  " 	left join bd_Consejo bx3 on( bx.codigo=bx3.codigo) ";	
		from+=  "  ) n2 on  ( n2.codConjHomog=c2.codConjHomog) ";

		
		//miramos correspondencia con Biblia
		from+=  " left join   ";
		from+=  " (	";
			//-- 1-CN/1-CodiLab/1-codGtVmpp/1-nota + datos CN
		from+=  " 	select distinct cnLabChNota_1.nota, cnLabChNota_1.codGtVmpp, cnLabChNota_1.nomGtVmpp,  cnLabChNota_1.codiLabo , cnLabChNota_1.nomLabo as nombreLab, cnLabChNota_1.nomLabo, cnLabChNota_1.cn,";
		from+=  "   cons_b2.NOMBRE+' '+cons_b2.PRESENTACION   as nombreMedicamentoBiblia ";
		from+=  "   from bd_Consejo cons_b2 inner join  ";
		from+=  " 	(";
			//	-- 1-CN/1-CodiLab/1-codGtVmpp/1-nota
		from+=  " 		select distinct LabChNota_1.nota, LabChNota_1.codGtVmpp, LabChNota_1.nomGtVmpp,  LabChNota_1.codiLabo ,cons_b1.nomLabo, max(cons_b1.CODIGO) as cn";
		from+=  " 		from bd_Consejo cons_b1";
		from+=  " 		inner join ";
		from+=  " 		(";
					//-- 1-CodiLab/1-codGtVmpp/1-nota";
		from+=  " 			select max(ch1.codiLab) as codiLabo,  conjNota_1.codGtVmpp, conjNota_1.nomGtVmpp, conjNota_1.nota ";
		from+=  " 			from SPD_sustXComposicion ch1";
		from+=  " 			inner join ";
		from+=  " 			(";
					//	-- 1-codGtVmpp/1-nota
		from+=  " 				select max(sx.ponderacion + sx.rentabilidad) as nota,  sx.codGtVmpp, sx.nomGtVmpp ";  		
		from+=  " 				from SPD_sustXComposicion sx 		";
		from+=  " 				group by sx.codGtVmpp, sx.nomGtVmpp";
		from+=  " 			) conjNota_1 on (conjNota_1.codGtVmpp = ch1.codGtVmpp   and conjNota_1.nota= (ch1.ponderacion + ch1.rentabilidad)) ";
		from+=  " 			group by conjNota_1.codGtVmpp, conjNota_1.nomGtVmpp, conjNota_1.nota";
		from+=  " 		) LabChNota_1 on (LabChNota_1.codiLabo=cons_b1.codiLabo and LabChNota_1.codGtVmpp=cons_b1.codGtVmpp )";
		from+=  " 		group by LabChNota_1.nota, LabChNota_1.codGtVmpp, LabChNota_1.nomGtVmpp,  LabChNota_1.codiLabo ,cons_b1.nomLabo";
		from+=  " 	)cnLabChNota_1 on (cnLabChNota_1.cn=cons_b2.codigo)";
		from+=  " ) n1 on  ( n1.codGtVmpp=c.codGtVmpp) ";

		//miramos correspondencia con Biblia la sustitución específica de la resi
		from+=  "  left join     ";
		from+=  " (	";
				//	-- 1-CN/1-CodiLab/1-codGtVmpp/1-nota + datos CN  
		from+=  "  	select distinct cnLabChNota_2.nota, cnLabChNota_2.codGtVmpp, cnLabChNota_2.nomGtVmpp,  cnLabChNota_2.codiLabo ,cnLabChNota_2.nomLabo as nombreLab, cnLabChNota_2.cn,  ";
		from+=  "      cons_a2.NOMBRE+' '+cons_a2.PRESENTACION   as nombreMedicamentoBiblia   ";
		from+=  "  	from bd_Consejo cons_a2 inner join    ";
		from+=  "  	(  ";
				//	-- 1-CN/1-CodiLab/1-codGtVmpp/1-nota  
		from+=  "  		select distinct LabChNota_2.nota, LabChNota_2.codGtVmpp, LabChNota_2.nomGtVmpp,  LabChNota_2.codiLabo ,cons_a1.nomLabo, max(cons_a1.CODIGO) as cn  ";
		from+=  "          from bd_Consejo cons_a1  ";
		from+=  "          inner join   ";
		from+=  "  		(  ";
					//	-- 1-CodiLab/1-codGtVmpp/1-nota 
		from+=  "  			select max(GtVmpp.codiLab) as codiLabo,  conjNota_2.codGtVmpp, conjNota_2.nomGtVmpp, conjNota_2.nota   ";
		from+=  "  			from SPD_sustXComposicion GtVmpp  ";
		from+=  "  			inner join   ";
		from+=  "  			(  ";
						//	-- 1-codGtVmpp/1-nota  
		from+=  "  				select max(sx.ponderacion + sx.rentabilidad) as nota,  sx.codGtVmpp, sx.nomGtVmpp ";
		from+=  "  				from SPD_sustXComposicion sx 		  ";
		from+=  "  				group by sx.codGtVmpp, sx.nomGtVmpp ";
		from+=  "  			) conjNota_2 on (conjNota_2.codGtVmpp = GtVmpp.codGtVmpp  and conjNota_2.nota= (GtVmpp.ponderacion + GtVmpp.rentabilidad))   ";
		from+=  "  		group by conjNota_2.codGtVmpp, conjNota_2.nomGtVmpp, conjNota_2.nota  ";
		from+=  "  		) LabChNota_2 on (LabChNota_2.codiLabo=cons_a1.codiLabo and LabChNota_2.codGtVmpp=cons_a1.codGtVmpp )  ";
		from+=  "  		group by LabChNota_2.nota, LabChNota_2.codGtVmpp, LabChNota_2.nomGtVmpp,  LabChNota_2.codiLabo ,cons_a1.nomLabo  ";
		from+=  "  	)cnLabChNota_2 on (cnLabChNota_2.cn=cons_a2.codigo)  ";
		from+=  "  ) n2 on  ( n2.codGtVmpp=c2.codGtVmpp)  ";
		
		
		
		
				
	//	if(oidDivisionResidencia<1)
	//		where+=  " and d.oidDivisionResidencia = '"+oidDivisionResidencia+"'";
		if(oidGestSustitucionesXResi>0)
			 where+=  " and gr.oidGestSustitucionesXResi = '"+oidGestSustitucionesXResi+"'";
		if(oidGestSustituciones>0)
			 where+=  " and g.oidGestSustituciones = '"+oidGestSustituciones+"'";
		if(getFiltroNombreCorto!=null && !getFiltroNombreCorto.equals(""))
		{
			where+=  " and ( UPPER(n1.cn) like '%"+StringUtil.limpiarTextoyEspacios(getFiltroNombreCorto).toUpperCase() +"%'  OR  UPPER(g.cnResi) like '%"+StringUtil.limpiarTextoyEspacios(getFiltroNombreCorto).toUpperCase() +"%' ";
			where+=" 		OR UPPER(g.cnOk) like '%"+StringUtil.limpiarTextoyEspacios(getFiltroNombreCorto).toUpperCase() +"%' OR UPPER(gr.cnOkSustXResi) like '%"+StringUtil.limpiarTextoyEspacios(getFiltroNombreCorto).toUpperCase() +"%'  ";
			where+=" 		OR UPPER(g.medicamentoResi) like '%"+getFiltroNombreCorto.toUpperCase() +"%' OR  UPPER(g.nombreCorto)  like '%"+getFiltroNombreCorto.toUpperCase() +"%' ";
			where+="		OR  UPPER(g.comentario)  like '%"+getFiltroNombreCorto.toUpperCase() +"%' ";
			where+="		) ";
		}
		if(filtroMedicamentoResi!=null && !filtroMedicamentoResi.equals(""))
			where+=  " and UPPER(g.medicamentoResi) = '"+filtroMedicamentoResi.toUpperCase() +"' ";
		if(filtroAccion!=null && !filtroAccion.equals(""))
//			where+=  " and ( UPPER(g.accion) = '"+StringUtil.limpiarTextoyEspacios(filtroAccion).toUpperCase() +"' OR  UPPER(gr.accionSustXResi) = '"+filtroAccion.toUpperCase() +"' ) ";  
			where+=  " and ( UPPER(g.accion) = '"+filtroAccion.toUpperCase() +"' OR  UPPER(gr.accionSustXResi) = '"+filtroAccion.toUpperCase() +"' ) ";  
		if(filtroFormaFarmaceutica!=null && !filtroFormaFarmaceutica.equals(""))
			where+=  " and ( UPPER(c.FormaFarmaceutica) = '"+filtroFormaFarmaceutica.toUpperCase() +"' OR  UPPER(c2.FormaFarmaceutica) = '"+filtroFormaFarmaceutica.toUpperCase() +"' ) ";  
		if(filtroNombreCortoOK!=null && !filtroNombreCortoOK.equals(""))
		{
			where+=  " and UPPER(g.nombreCorto) like '"+filtroNombreCortoOK.toUpperCase() +"' ";
    	}
		
    	if(!count) 
    	{
    		//order+=  " order by g.medicamentoResi, g.oidGestSustituciones, gr.idDivisionResidencia ";
    		order+=  " order by g.cnResi,  gr.idDivisionResidencia desc, n2.nota desc, n1.nota desc   ";  //importante este orden, para que salga primero la residencia en caso que exista SustXResi

    		otros+= " offset "+ (inicio) + " rows ";
    		otros+= " fetch next "+SPDConstants.PAGE_ROWS+ " rows only";
    	}
		
		String qry = select + from + where + order + otros;
	
		 System.out.println("getQuerySustituciones -->" +qry );		

			return qry;
	}

    	*/
	
    public static List<GestSustituciones> getMedicamentoResi( ) throws ClassNotFoundException, SQLException {
    	
	   Connection con = Conexion.conectar();
	 //  System.out.println("connected main" );
    String qry = "select distinct g.medicamentoResi ";
    		qry+=  " from dbo.gest_sustituciones g ";
 			qry+=  " order by g.medicamentoResi";
 	   	//	System.out.println("getMedicamentoResi -->" +qry );		

     ResultSet resultSet = null;
		List<GestSustituciones> listaMedicamentosResi= new ArrayList<GestSustituciones>();
   try {
        PreparedStatement pstat = con.prepareStatement(qry);
    //    pstat.setString(1, employee.getempNo());
        resultSet = pstat.executeQuery();

        while (resultSet.next()) {
        	GestSustituciones  c =new GestSustituciones();
         	c.setMedicamentoResi(resultSet.getString("medicamentoResi"));
         	listaMedicamentosResi.add(c);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }finally {con.close();}

    return listaMedicamentosResi;
}

    
    
    public static GestSustituciones getSustitucionByOid(String spdUsuario, int oidGestSustituciones) throws Exception {
    	
    	GestSustituciones result=null;
    	
    	//actualizamos el oid por defecto a 0 para que la select tenga en cuenta la condición
    	if(oidGestSustituciones==-1)oidGestSustituciones=0;
    	
    	List<GestSustituciones> sustituciones =getSustituciones(spdUsuario, 0,  oidGestSustituciones, -1, null, null, null, false, 0, 1, "0", null, null, false);
    	if(sustituciones!=null && sustituciones.size()>0)
    		result=sustituciones.get(0);
    	
    	return result;

}

    
    public static GestSustituciones getSustitucionXResiByOid(String spdUsuario, int oidGestSustitucionesXResi) throws Exception {
    	
    	GestSustituciones result=null;
    	
    	List<GestSustituciones> sustituciones =getSustituciones(spdUsuario, 0,  -1, oidGestSustitucionesXResi, null, null, null, false, 0, 1, "0", null, null, false);
    	if(sustituciones!=null && sustituciones.size()>0)
    		result=sustituciones.get(0);
    	
    	return result;

}

   
    
    
    public static List<TiposAccionBean> getListaTiposAccion() throws ClassNotFoundException, SQLException {
    	
       List  listaTiposAccion = new ArrayList();
 	   Connection con = Conexion.conectar();
  	   String qry = "  select distinct accion ";
  			 qry+= "  from dbo.gest_sustituciones gr  ";
  			 qry+= " union ";
  			 qry+= " select distinct accionSustXResi ";
  			 qry+= " from dbo.gest_sustitucionesXResi ";
       		 qry+= " order by accion";
     	ResultSet resultSet = null;
	   		System.out.println("getListaTiposAccion -->" +qry );		

    try {
         PreparedStatement pstat = con.prepareStatement(qry);
         resultSet = pstat.executeQuery();
         int count=0;
         while (resultSet.next()) {
        	 TiposAccionBean t = new TiposAccionBean();
        	 t.setIdTipoAccion(resultSet.getString("accion"));
        	 t.setNombreAccion(t.getIdTipoAccion());
        	 listaTiposAccion.add(t);
            }
     } catch (SQLException e) {
         e.printStackTrace();
     }

     return listaTiposAccion;
 }

	public static boolean edita(String spdUsuario, GestSustitucionesForm gestSustitucionesForm) throws Exception {

        int result=0;
		  Connection con = Conexion.conectar();
	  	   String qry = "update dbo.gest_sustituciones ";
	  	   		qry+= " set  cnResi= '"+gestSustitucionesForm.getCnResi()+"', ";
	  	   		qry+= " medicamentoResi= '"+gestSustitucionesForm.getMedicamentoResi()+"', ";
	  	   		qry+= " cnOk= "+gestSustitucionesForm.getCnOk()+", ";
	  	   		qry+= " nombreCorto= '"+gestSustitucionesForm.getNombreCorto()+"', ";
	  	   		qry+= " idDivisionResidencia= (select idDivisionResidencia from bd_divisionResidencia where oidDivisionResidencia="+gestSustitucionesForm.getOidDivisionResidencia()  +") , ";
	  	   		qry+= " accion= '"+gestSustitucionesForm.getIdTipoAccion()+"', ";
	  	   		qry+= " nombreMedicamentoOk= (select nombre + ' ' + presentacion from bd_consejo where codigo="+gestSustitucionesForm.getCnOk()  +"), ";
	  	   		qry+= " codGtVmppOk= (select codGtVmpp from bd_consejo where codigo="+gestSustitucionesForm.getCnOk()  +"), ";
	  	   		qry+= " nomGtVmppOk= (select nomGtVmpp from bd_consejo where codigo="+gestSustitucionesForm.getCnOk()  +"), ";
	  	   		qry+= " sustituible= '"+gestSustitucionesForm.getSustituible()+"', ";
	  	   		qry+= " comentario= '"+gestSustitucionesForm.getComentario()+"', ";
		  	   	//	qry+= " formaFarmaceuticaSustitucion= '"+gestSustitucionesForm.getFormaFarmaceutica()+"' ";
	  	   		qry+= " formaFarmaceuticaSustitucion= (select nomFormaFarmaceutica from bd_consejo where codigo="+gestSustitucionesForm.getCnOk()  +") ";
	  	   		qry+= " WHERE idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
	       		qry+= " AND oidGestSustituciones= '"+gestSustitucionesForm.getOidGestSustituciones()+"' ";
	      		System.out.println("edita-->  " +qry );		
	      		 
	    try {
	         PreparedStatement pstat = con.prepareStatement(qry);
	         result=pstat.executeUpdate();
	       
	     } catch (SQLException e) {
	         e.printStackTrace();
	     }finally {con.close();}

		
		return result>0;
	}
	
	
	public static boolean editaSustXResi(String spdUsuario, GestSustitucionesForm gestSustitucionesForm) throws Exception {

        int result=0;
		  Connection con = Conexion.conectar();
	  	   String qry = "update dbo.gest_sustitucionesXResi set ";
	  	   		
	  	   		if(gestSustitucionesForm.getCnOkSustXResi()!=null && !gestSustitucionesForm.getCnOkSustXResi().equals(""))
	  	   			qry+= " cnOkSustXResi= '"+gestSustitucionesForm.getCnOkSustXResi()+"', ";
	  	   		
	  	   		if(gestSustitucionesForm.getNombreCortoSustXResi()!=null && !gestSustitucionesForm.getNombreCortoSustXResi().equals(""))
	  	   			qry+= " nombreCortoSustXResi= '"+gestSustitucionesForm.getNombreCortoSustXResi()+"', ";
	  	   		

	  	   		if(gestSustitucionesForm.getSustitucion()!=null 
	  	   				&&  gestSustitucionesForm.getSustitucion().getListaSustitucionesXResi()!=null 
	  	   				&&  gestSustitucionesForm.getSustitucion().getListaSustitucionesXResi().get(0)!=null
		   				&&  gestSustitucionesForm.getSustitucion().getListaSustitucionesXResi().get(0).getOidDivisionResidencia()  >0
		   				)
	  	   		{
	  	   			qry+= " oidDivisionResidencia= '" +gestSustitucionesForm.getSustitucion().getListaSustitucionesXResi().get(0).getOidDivisionResidencia()  +"' , ";
		  	   		qry+= " idDivisionResidencia= (select idDivisionResidencia from bd_divisionResidencia where oidDivisionResidencia="+gestSustitucionesForm.getSustitucion().getListaSustitucionesXResi().get(0).getOidDivisionResidencia()  +") , ";
	  	   		}
	  	   		
	  	   		if(gestSustitucionesForm.getAccionSustXResi()!=null)
	  	   			qry+= " accionSustXResi= '"+gestSustitucionesForm.getAccionSustXResi()+"', ";
	
	  	   		if(gestSustitucionesForm.getSustituible()!=null)
	  	   			qry+= " sustituible= '"+gestSustitucionesForm.getSustituibleXResi()+"', ";

	  	   		
	  	   		if(gestSustitucionesForm.getComentarioSustXResi()!=null)
	  	   			qry+= " comentarioSustXResi= '"+gestSustitucionesForm.getComentarioSustXResi()+"' ";
	  	//   		qry+= " formaFarmaceuticaSustXResi= '"+gestSustitucionesForm.getFormaFarmaceuticaSustXResi()+"' ";
	  	   		qry+= " WHERE idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
	       		qry+= " AND oidGestSustitucionesXResi= '"+gestSustitucionesForm.getSustitucion().getListaSustitucionesXResi().get(0).getOidGestSustitucionesXResi()+"' ";
	      		System.out.println("editaSustXResi-->  " +qry );		
	      		 
	    try {
	         PreparedStatement pstat = con.prepareStatement(qry);
	         result=pstat.executeUpdate();
	       
	     } catch (SQLException e) {
	         e.printStackTrace();
	     }finally {con.close();}

		
		return result>0;
	}

	public static List<GestSustituciones> getNombresCortosOK() throws ClassNotFoundException, SQLException {
		    	
			   Connection con = Conexion.conectar();
			//	   System.out.println("connected main" );
		    String qry = "select distinct g.nombreCorto ";
		    		qry+=  " from dbo.gest_sustituciones g ";
		 			qry+=  " order by g.nombreCorto";
			   		System.out.println("getNombresCortosOK -->" +qry );		
					
		     ResultSet resultSet = null;
				List<GestSustituciones> listaNombresCortosOK= new ArrayList<GestSustituciones>();
		   try {
		        PreparedStatement pstat = con.prepareStatement(qry);
		    //    pstat.setString(1, employee.getempNo());
		        resultSet = pstat.executeQuery();

		        while (resultSet.next()) {
		        	GestSustituciones  c =new GestSustituciones();
		         	c.setNombreCorto(resultSet.getString("nombreCorto"));
		         	listaNombresCortosOK.add(c);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }finally {con.close();}

		    return listaNombresCortosOK;
		}
	
	
	public static List<FormasFarmaceuticas>  getFormasFarmaceuticas() throws ClassNotFoundException, SQLException {
		   Connection con = Conexion.conectar();
		   System.out.println("connected getFormasFarmaceuticas()" );
		    List<FormasFarmaceuticas>  result = new ArrayList();
		    
		   String qry = "select distinct c.formaFarmaceutica as codigoFormaFarmaceutica, ";
			   qry+= " c.nomFormaFarmaceutica as nombreFormaFarmaceutica ";
	    		qry+=  " from dbo.bd_consejo c ";
	 			qry+=  " order by c.nomFormaFarmaceutica";
				
		   		System.out.println("getFormasFarmaceuticas -->" +qry );		
		     	ResultSet resultSet = null;
	 	 	
	 	    try {
	 	         PreparedStatement pstat = con.prepareStatement(qry);
	 	         resultSet = pstat.executeQuery();
	 	         int count=0;
	 	         while (resultSet.next()) {
	 	        	FormasFarmaceuticas f = new FormasFarmaceuticas();
	 	        	 f.setCodigoFormaFarmaceutica(resultSet.getInt("codigoFormaFarmaceutica"));
	 	        	 f.setNombreFormaFarmaceutica(resultSet.getString("nombreFormaFarmaceutica"));
	 	        	result.add(f);
	 	            }
	 	     } catch (SQLException e) {
	 	         e.printStackTrace();
	 	     }finally {con.close();}

	 	     return result;
	 	 }






	public List<GestSustituciones> getGtVm() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<GestSustituciones> getPresentacion() {
		// TODO Auto-generated method stub
		return null;
	}

	public static boolean borra(GestSustitucionesForm gestSustitucionesForm) throws ClassNotFoundException, SQLException {

        int result=0;
		  Connection con = Conexion.conectar();
	  	   String qry = "delete dbo.gest_sustituciones ";
	       		qry+= " where oidGestSustituciones= '"+gestSustitucionesForm.getOidGestSustituciones()+"' ";
		   		System.out.println("borra -->" +qry );		
	 	
	    try {
	         PreparedStatement pstat = con.prepareStatement(qry);
	         result=pstat.executeUpdate();
	       
	     } catch (SQLException e) {
	         e.printStackTrace();
	     }finally {con.close();}

		
		return result>0;
	}
	
	public static boolean borraSustXResi(String spdUsuario, GestSustitucionesForm gestSustitucionesForm) throws Exception {

        int result=0;
		  Connection con = Conexion.conectar();
	  	   String qry = "delete dbo.gest_sustitucionesXResi ";
 	   		qry+= " 	WHERE idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
	       		qry+= " AND oidGestSustitucionesXResi= '"+gestSustitucionesForm.getOidGestSustitucionesXResi()+"' ";
		   		System.out.println("borraSustXResi -->" +qry );		
	 	
	    try {
	         PreparedStatement pstat = con.prepareStatement(qry);
	         result=pstat.executeUpdate();
	       
	     } catch (SQLException e) {
	         e.printStackTrace();
	     }finally {con.close();}

		
		return result>0;
	}
	

	public static boolean nuevo(GestSustituciones sust) throws ClassNotFoundException, SQLException {
	       int result =0;
	        
			  Connection con = Conexion.conectar();

		 	  	   String qry = " INSERT INTO dbo.gest_sustituciones (fechaSust, idDivisionResidencia, cnResi, medicamentoResi, cnOk, "
		 	  	   		+ "nombreCorto, formaFarmaceuticaSustitucion, accion, comentario, nombreMedicamentoOk, codGtVmppOk, nomGtVmppOk, sustituible) VALUES ";
		       		qry+= " (CONVERT(datetime, getdate(), 120), ";
		       		qry+= "'"+ sust.getIdDivisionResidencia()+"', ";
		       		qry+= "'"+ sust.getCnResi()+"', ";
		       		qry+= "'"+ sust.getMedicamentoResi()+"', ";
		       		qry+= "'"+ sust.getCnOk() +"', ";
		       		qry+= "'"+ sust.getNombreCorto() +"', ";
		       		qry+= "'"+ sust.getFormaFarmaceuticaSustitucion() +"', ";
		       		qry+= "'"+ sust.getAccion()+"', ";
		       		qry+= "'"+ sust.getComentario()+"', ";
		       		qry+= "'"+ sust.getNombreMedicamentoOk()+"', ";
		       		qry+= "'"+ sust.getCodGtVmppOk()+"', ";
		       		qry+= "'"+ sust.getNomGtVmppOk()+"', ";
		       		qry+= "'"+ sust.getSustituible()+"') ";		  
		       		System.out.println("nuevo -->" +qry );		
		    try {
		         PreparedStatement pstat = con.prepareStatement(qry);
		         result=pstat.executeUpdate();
				 	

		       
		     } catch (SQLException e) {
		         e.printStackTrace();
		     }
		    finally {con.close();}

			return result>0;
		}




	public static boolean nuevo(GestSustitucionesForm gestSustitucionesForm) throws ClassNotFoundException, SQLException {

        int result =0;
        String cn = (gestSustitucionesForm.getCnOk()==null||!gestSustitucionesForm.getCnOk().equals("")?gestSustitucionesForm.getCnOk():gestSustitucionesForm.getCnOkSustXResi());
        String nombreBolsa = (gestSustitucionesForm.getNombreCorto()==null||!gestSustitucionesForm.getNombreCorto().equals("")?gestSustitucionesForm.getNombreCorto():gestSustitucionesForm.getNombreCortoSustXResi());
        String accion =  (gestSustitucionesForm.getIdTipoAccion()==null||!gestSustitucionesForm.getIdTipoAccion().equals("")?gestSustitucionesForm.getIdTipoAccion():gestSustitucionesForm.getAccionSustXResi());
        
		  Connection con = Conexion.conectar();
//	  	   String qry = " INSERT INTO dbo.gest_sustituciones (fecha, cnResi, medicamentoResi, cnOk, nombreCorto, formaFarmaceuticaSustitucion, accion, comentario) VALUES ";
//	       		qry+= " (CONVERT(datetime, getdate(), 120),  ";
	 	  	   String qry = " INSERT INTO dbo.gest_sustituciones (fechaSust, idDivisionResidencia, cnResi, medicamentoResi, cnOk, nombreCorto, formaFarmaceuticaSustitucion, accion, comentario, nombreMedicamentoOk, codGtVmppOk, nomGtVmppOk, sustituible) VALUES ";
	       		qry+= " (CONVERT(datetime, getdate(), 120), '"+gestSustitucionesForm.getIdDivisionResidencia()+"', ";
	       		qry+= "'"+ gestSustitucionesForm.getCnResi()+"', '"+gestSustitucionesForm.getMedicamentoResi()+"', ";
	       		qry+= "'"+ cn +"', ";
	       		qry+= "'"+ nombreBolsa +"', ";
		       	//qry+= "'"+  gestSustitucionesForm.getFormaFarmaceutica()+"', '";
	  	   		qry+= "  (select nomFormaFarmaceutica from bd_consejo where codigo='"+cn  +"'), ";
	       		qry+= "'"+ accion+"', ";
	       		qry+= "'"+  gestSustitucionesForm.getComentario()+"', ";
	       		qry+= "  (select nombre + ' ' + presentacion from bd_consejo where codigo='"+cn  +"'), ";
	       		qry+= "  (select codGtVmpp from bd_consejo where codigo='"+cn  +"'), ";
	       		qry+= "  (select nomGtVmpp from bd_consejo where codigo='"+cn  +"'), ";
	       		qry+= "'"+  gestSustitucionesForm.getSustituible()+"') ";		  
	       		System.out.println("nuevo -->" +qry );		
	    try {
	         PreparedStatement pstat = con.prepareStatement(qry);
	         result=pstat.executeUpdate();
			 	

	       
	     } catch (SQLException e) {
	         e.printStackTrace();
	     }finally {con.close();}


		return result>0;
	}



	public static int getUltimoOid(String spdUsuario, GestSustituciones sust)  throws Exception {
        int result=-1;
		  Connection con = Conexion.conectar();
	 	  	   String 	qry = " SELECT max(oidGestSustituciones) as result from dbo.gest_sustituciones ";
	  	   				qry+= " WHERE idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
	  	   				qry+= " AND idDivisionResidencia ='" +sust.getIdDivisionResidencia()+"' ";
	 	  	   			qry+= " AND cnResi= '" + sust.getCnResi() + "' ";
	 	  	   			qry+= " AND medicamentoResi= '" + sust.getMedicamentoResi() + "' ";
	 	  	   			qry+= " AND accion= '" + sust.getAccion() + "' ";
	 	  	   			
	 	   		System.out.println("getUltimoOid -->" +qry );		
	 	   	ResultSet resultSet = null;

 	    try {
	         PreparedStatement pstat = con.prepareStatement(qry);
	         resultSet = pstat.executeQuery();
	         while (resultSet.next()) {
	        	result = (resultSet.getInt("result"));
	            }
	     } catch (SQLException e) {
	         e.printStackTrace();
	     }finally {con.close();}

	    
		return result;
	}

	
	
  /**
   * TO-DO Método a revisar para encontrar el por qué
   * @param gestSustitucionesForm
   * @return
 * @throws Exception 
   */

	public static int getUltimoOid(String spdUsuario, GestSustitucionesForm gestSustitucionesForm) throws Exception {
        int result=-1;
        GestSustituciones sust = new GestSustituciones();
        sust.setIdDivisionResidencia(gestSustitucionesForm.getIdDivisionResidencia());
        sust.setCnResi(gestSustitucionesForm.getCnResi());
        sust.setMedicamentoResi(gestSustitucionesForm.getMedicamentoResi());
        sust.setAccion(gestSustitucionesForm.getIdTipoAccion());
        return getUltimoOid(spdUsuario, sust);
	}

	
	public static boolean nuevoSustXResi(GestSustitucionesXResi sustXResi) throws ClassNotFoundException, SQLException {
	      int result=0;
	      if(sustXResi==null || sustXResi.getOidGestSustituciones()<1) return false;
	      
//	      int oidDivisionResidencia = sustXResi.getOidDivisionResidencia()>0?gestSustitucionesForm.getOidDivisionResidencia():gestSustitucionesForm.getOidDivisionResidenciaFiltro();
	      
			  Connection con = Conexion.conectar();
		  	   String qry = " INSERT INTO dbo.gest_sustitucionesXResi (fechaSustXResi, oidDivisionResidencia, idDivisionResidencia, oidGestSustituciones, cnOkSustXResi, "
		  	   			+ "nombreCortoSustXResi, accionSustXResi, comentarioSustXResi, codGtVmppOkSustXResi, nomGtVmppOkSustXResi, NombreMedicamentoOkSustXResi, sustituible, oidUsuario) VALUES ";

		  	   			qry+= " (CONVERT(datetime, getdate(), 120), " ;
		  	   			qry+=  sustXResi.getOidDivisionResidencia() +" , ";
		  	   			qry+= "'"+sustXResi.getIdDivisionResidencia() +"' , ";
		  	   			qry+= "'"+sustXResi.getOidGestSustituciones() +"' , ";
	    		
		       		if(sustXResi.getCnOkSustXResi()!=null && !sustXResi.getCnOkSustXResi().equals(""))
		       	   		qry+= "'"+sustXResi.getCnOkSustXResi() +"' , ";
		       		else
		       			qry+= "'"+sustXResi.getCnOkSustXResi() +"' , ";
		       			
		       	   	if(sustXResi.getNombreCortoSustXResi()!=null && !sustXResi.getNombreCortoSustXResi().equals(""))
		       	   		qry+=  "'"+sustXResi.getNombreCortoSustXResi()+"', ";
		       	   	else
		       	   		qry+=  "'"+sustXResi.getNombreMedicamentoOkSustXResi()+"', ";
		       	   	
		       		if(sustXResi.getAccionSustXResi()!=null)
		       			qry+= "'"+sustXResi.getAccionSustXResi()+"', ";
		       		
		       		
	       				qry+= "'"+ sustXResi.getComentarioSustXResi()+"', ";
	       				qry+= "'"+ sustXResi.getCodGtVmppOkSustXResi()+"', ";
	       				qry+= "'"+ sustXResi.getNomGtVmppOkSustXResi()+"', ";
	       				qry+= "'"+ sustXResi.getNombreMedicamentoOkSustXResi()+"', ";
			       		qry+= "'"+ sustXResi.getSustituibleXResi()+"',  0 )";

	   		
					   	System.out.println("nuevoSustXResi -->" +qry );		
		 	
		    try {
		         PreparedStatement pstat = con.prepareStatement(qry);
		         result=pstat.executeUpdate();
		       
		     } catch (SQLException e) {
		         e.printStackTrace();
		         
		     }finally {con.close();}


			return result>0;
		}

	
	
	
	/**
	 * 
	 * @param gestSustitucionesForm
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException 
	 */
	public static boolean nuevoSustXResi(GestSustitucionesForm gestSustitucionesForm) throws ClassNotFoundException, SQLException {
     
		int result=0;
		if(gestSustitucionesForm.getSustitucion()==null) return false;
		int oidDivisionResidencia = (int) (gestSustitucionesForm.getOidDivisionResidencia()>0?gestSustitucionesForm.getOidDivisionResidencia():gestSustitucionesForm.getOidDivisionResidenciaFiltro());

		
		  Connection con = Conexion.conectar();
	  	   String qry = " INSERT INTO dbo.gest_sustitucionesXResi (fechaSustXResi, oidDivisionResidencia, idDivisionResidencia, oidGestSustituciones, cnOkSustXResi, nombreCortoSustXResi, accionSustXResi, comentarioSustXResi, sustituible, oidUsuario) VALUES ";
	       		qry+= " (CONVERT(datetime, getdate(), 120), " ;
	       		
	      		//if(gestSustitucionesForm.getSustitucion()!=null )
	       		//{
	       			qry+= oidDivisionResidencia +" , ";
	    	   		qry+= " (select idDivisionResidencia from bd_divisionResidencia where oidDivisionResidencia="+oidDivisionResidencia  +") , ";
	       		//}
	      		
	      		if(gestSustitucionesForm.getSustitucion()!=null)
	      			qry+= gestSustitucionesForm.getSustitucion().getOidGestSustituciones() +" , ";
    		
	       		if(gestSustitucionesForm.getCnOkSustXResi()!=null && !gestSustitucionesForm.getCnOkSustXResi().equals(""))
	       	   		qry+= "'"+gestSustitucionesForm.getCnOkSustXResi() +"' , ";
	       		else
	       			qry+= "'"+gestSustitucionesForm.getSustitucion().getCnOk() +"' , ";
	       			
	       	   	if(gestSustitucionesForm.getNombreCortoSustXResi()!=null && !gestSustitucionesForm.getNombreCortoSustXResi().equals(""))
	       	   		qry+=  "'"+gestSustitucionesForm.getNombreCortoSustXResi()+"', ";
	       	   	else
	       	   		qry+=  "'"+gestSustitucionesForm.getNombreCorto()+"', ";
	       	   	
	       		if(gestSustitucionesForm.getAccionSustXResi()!=null && !gestSustitucionesForm.getAccionSustXResi().equals(""))
	       			qry+= "'"+gestSustitucionesForm.getAccionSustXResi()+"', ";
	       		else
	       			qry+= "'"+gestSustitucionesForm.getSustitucion().getAccion()+"', ";

	       		
	       		//if(gestSustitucionesForm.getComentarioSustXResi()!=null)
	       			qry+= "'"+gestSustitucionesForm.getComentarioSustXResi()+"', ";

	           		
		       		qry+= "'"+  gestSustitucionesForm.getSustituible()+"',  0 )";

   		
	       	   		
			   		System.out.println("nuevoSustXResi -->" +qry );		
	 	
	    try {
	         PreparedStatement pstat = con.prepareStatement(qry);
	         result=pstat.executeUpdate();
	       
	     } catch (SQLException e) {
	         e.printStackTrace();
	         
	     }finally {con.close();}


		return result>0;
	}

	
	/**
	 * Método que añade información de la sustitución de un medicamento de la resi.
	 * Si tiene específicament uno de la residencia (gest_sustitucionesXResi), tendrá preferencia sobre los creados por defecto (gest_sustituciones)
	 * @param medResi
	 * @return
	 * @throws Exception 
	 */
	public static FicheroResiBean buscaSustitucion(String spdUsuario, FicheroResiBean medResi) throws Exception {
		   Connection con = Conexion.conectar();
		//   GestSustituciones result=null;

	 		String qry = "select ";
	 				qry+= " g.oidGestSustituciones, ";
	 				qry+= " gr.oidGestSustitucionesXResi, ";
	 				qry+= " coalesce(gr.fechaSustXResi,g.fechaSust) as fechaSustitucion, ";
	 				qry+= " coalesce(gr.idDivisionResidencia,g.idDivisionResidencia) as idDivisionResidencia, ";
	 				qry+= " coalesce(gr.cnOkSustXResi,g.cnOk) as cnOk, ";
	 				qry+= " coalesce(gr.nombreCortoSustXResi,g.nombreCorto) as nombreCorto, ";
	 				qry+= " coalesce(gr.accionSustXResi,g.accion) as accion, ";
	 				qry+= " coalesce(gr.comentarioSustXResi,g.comentario) as comentario, ";
	 				qry+= " coalesce(gr.oidUsuario,g.oidUsuario) as oidUsuario, ";
	 				qry+= " coalesce(gr.sustituible,g.sustituible) as sustituible ";
	 				qry+= " from dbo.gest_sustituciones g  ";
	 				qry+= " left join dbo.gest_sustitucionesXResi gr on (g.oidGestSustituciones=gr.oidGestSustituciones ";
	 				qry+= " 		and gr.idDivisionResidencia = '"+medResi.getIdDivisionResidencia()+"') ";
		  	   		qry+= " WHERE g.idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";
	 				qry+= " AND g.cnResi='"+medResi.getResiCn()+"' ";
	 			
	 			
	 				System.out.println("buscaSustitucion -->" +qry );		
				
	     ResultSet resultSet = null;
			//List<GestSustituciones> sustituciones= new ArrayList<GestSustituciones>();
	   try {
	        PreparedStatement pstat = con.prepareStatement(qry);
	        resultSet = pstat.executeQuery();

	        if (resultSet.next()) {
	        	medResi.setSpdCnFinal(resultSet.getString("cnOk"));
	        	medResi.setSpdNombreBolsa(resultSet.getString("nombreCorto"));
	        	medResi.setSpdAccionBolsa(resultSet.getString("accion"));
	        	medResi.setSpdComentarioLopicost(resultSet.getString("comentario"));
	        //	medResi.set(resultSet.getString("oidUsuario"));
	        	
	         //	sustituciones.add(c);
	        }
	        //no debería haber más de uno, pero en ese caso devolverá el primero de la lista
	    	//if(sustituciones!=null && sustituciones.size()>0)
	    	//	result=sustituciones.get(0);
	    	
	    	
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }finally {con.close();}

	    return medResi;
	}

	
	
	/**
	 * Método que añade información de la sustitución de un medicamento de la resi pero en la Biblia de medicamentos.
	 * Si tiene específicament uno de la residencia (gest_sustitucionesXResi), tendrá preferencia sobre los creados por defecto (gest_sustituciones)
	 * @param form 
	 * @param medResi
	 * @return
	 * @throws Exception 
	 */
	
	public static FicheroResiBean buscaSustitucionBiblia(String spdUsuario, FicheroResiBean medResi) throws Exception {
		   Connection con = Conexion.conectar();
		//   GestSustituciones result=null;

			String select =" select top 1 g.oidGestSustituciones, g.fechaSust, g.cnResi, ";
			select+="  g.medicamentoResi, g.cnOk, g.nombreCorto, g.formaFarmaceuticaSustitucion, ";
			select+="  g.nombreMedicamentoOk, g.codGtVmppOk, g.nomGtVmppOk, ";
			select+="  c0.CODIGO as cnConsejoResi,  c0.nombre as nombreConsejoResi, c0.presentacion as presentacionConsejoResi, ";
			select+="  c0.codGtVmpp as codGtVmpResi, c0.nomGtVmpp as nomGtVmppConsejoResi,  c0.nomFormaFarmaceutica as nomFormaFarmaceuticaConsejoResi,  ";
			select+="  g.accion, g.comentario, g.oidUsuario, g.comentarioInterno, g.sustituible, ";
			select+="  gr.nombreMedicamentoOkSustXResi, gr.codGtVmppOkSustXResi, gr.nomGtVmppOkSustXResi, ";
			select+="  gr.oidGestSustitucionesXResi, gr.oidGestSustituciones as oidGestSustituciones2, gr.fechaSustXResi, gr.oidDivisionResidencia,  ";
			select+="  gr.idDivisionResidencia as idDivisionResidencia2, gr.cnOkSustXResi, gr.nombreCortoSustXResi, gr.accionSustXResi,  ";
			select+="  gr.comentarioSustXResi, gr.oidUsuario as oidUsuario2, gr.sustituible as sustituibleXResi, ";
			select+="  d.nombreDivisionResidencia, c.CODIGO, c.nombre, c.presentacion, c.nomFormaFarmaceutica, ";
			select+="  c2.CODIGO as codigo2, c2.nombre as nombre2, c2.presentacion as presentacion2, c2.nomFormaFarmaceutica as nomFormaFarmaceutica2, ";
			select+="  c.codGtVmpp as codGtVmpp, c.nomGtVmpp as nomGtVmpp, c2.codGtVmpp as codGtVmppSustXResi, c2.nomGtVmpp as nomGtVmppSustXResi, ";
			select+="  n1.cn as cnBiblia, n1.nombreMedicamentoBiblia, n1.codiLab as codiLabBiblia, n1.nombreLab as nombreLabBiblia, n1.nota as notaBiblia, ";
			select+="  n2.cn as cnBibliaSustXResi, n2.nombreMedicamentoBiblia as nombreMedicamentoBibliaSustXResi, n2.codiLab as codiLabBibliaSustXResi, n2.nombreLab as nombreLabBibliaSustXResi, n2.nota as notaBibliaSustXResi ";
			
	String 	from =" from dbo.gest_sustituciones g ";
			from+=" left join dbo.gest_sustitucionesXResi gr on (g.oidGestSustituciones=gr.oidGestSustituciones";

			if(medResi.getIdDivisionResidencia()!=null && !medResi.getIdDivisionResidencia().equals(""))
				from+=  " and gr.idDivisionResidencia  = '"+medResi.getIdDivisionResidencia() +"' ";

			from+=  " ) ";
			
			from+=" left join dbo.bd_divisionResidencia d  on (gr.idDivisionResidencia=d.idDivisionResidencia) ";
	String	where= " WHERE d.idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";

	String order =" ";
	String otros =" ";
		
	
		from+=  " left join bd_consejo c0 on (c0.codigo =  SUBSTRING(CAST(g.cnResi  AS CHAR(7)),1,6)   )  ";	
		from+=  " left join bd_consejo c on (c.codigo =  SUBSTRING(CAST(g.cnOk  AS CHAR(7)),1,6)   ) ";
		from+=  " left join bd_consejo c2 on (c2.codigo =  SUBSTRING(CAST(gr.cnOkSustXResi  AS CHAR(7)),1,6)   ) ";
	//miramos correspondencia con Biblia
	from+=  " left join  ";
	from+=  " ( ";
	from+=  " 	select distinct b.nota , b.codGtVmpp, b.nomGtVmpp, b2.codiLab, b2.nombreLab, b.CODIGO as cn  , b3.NOMBRE+' '+b3.PRESENTACION  as nombreMedicamentoBiblia";
	from+=  " 	from  ";
	from+=  " 	( ";
	from+=  " 		select max(s.ponderacion + s.rentabilidad) as nota,  max(c.codigo) as codigo, c.codGtVmpp, c.nomGtVmpp  ";
	from+=  " 		from SPD_sustXComposicion s, bd_Consejo c where s.codGtVmpp=c.codGtVmpp ";
	from+=  " 		group by c.codGtVmpp, c.nomGtVmpp    ";
	from+=  " 	) b ";
	from+=  " 	inner join  SPD_sustXComposicion b2  on ((b2.ponderacion + b2.rentabilidad = b.nota)  and b.codGtVmpp=b2.codGtVmpp) ";
	from+=  " 	left join bd_Consejo b3 on( b.codigo=b3.codigo)  ";
	from+=  "  ) n1 on  ( n1.codGtVmpp=c.codGtVmpp) ";


	//miramos correspondencia con Biblia la sustitución específica de la resi
	from+=  " left join  ";
	from+=  " ( ";
	from+=  " 	select distinct b.nota , b.codGtVmpp, b.nomGtVmpp, b2.codiLab, b2.nombreLab, b.CODIGO as cn  , b3.NOMBRE+' '+b3.PRESENTACION   as nombreMedicamentoBiblia";
	from+=  " 	from  ";
	from+=  " 	( ";
	from+=  " 		select max(s.ponderacion + s.rentabilidad) as nota,  max(c.codigo) as codigo, c.codGtVmpp, c.nomGtVmpp  ";
	from+=  " 		from SPD_sustXComposicion s, bd_Consejo c where s.codGtVmpp=c.codGtVmpp ";
	from+=  " 		group by c.codGtVmpp, c.nomGtVmpp    ";
	from+=  " 	) b ";
	from+=  " 	inner join  SPD_sustXComposicion b2  on ((b2.ponderacion + b2.rentabilidad = b.nota)  and b.codGtVmpp=b2.codGtVmpp) ";
	from+=  " 	left join bd_Consejo b3 on( b.codigo=b3.codigo)    ";	
	from+=  "  ) n2 on  ( n2.codGtVmpp=c2.codGtVmpp) ";

			
	if(medResi.getResiCn()!=null && !medResi.getResiCn().equals(""))
		//where+=  " and g.cnResi = '"+medResi.getResiCn() +"'";
		where+=  " and ( g.cnResi = '"+medResi.getResiCn() +"' OR g.cnOk  = '"+medResi.getResiCn() +"' OR gr.cnOkSustXResi  = '"+medResi.getResiCn() +"' OR n1.cn = '"+medResi.getResiCn() +"' OR n2.cn  = '"+medResi.getResiCn() +"') ";
	
	order+=  " order by gr.idDivisionResidencia desc, n2.nota, n1.nota desc   ";  //importante este orden, para que salga primero la residencia en caso que exista SustXResi

	String qry = select + from + where + order + otros;

	 System.out.println("buscaSustitucionBibliaANTIGUA -->" +qry );		

		   
					
	     ResultSet resultSet = null;
			//List<GestSustituciones> sustituciones= new ArrayList<GestSustituciones>();
	   try {
	        PreparedStatement pstat = con.prepareStatement(qry);
	        resultSet = pstat.executeQuery();

	        if (resultSet.next()) {
	        	medResi.setCodGtVmpp((resultSet.getString("codGtVmppSustXResi")!=null?resultSet.getString("codGtVmppSustXResi"):resultSet.getString("codGtVmpp")));
	        	medResi.setComentarioInterno(resultSet.getString("comentarioInterno"));
	        	medResi.setNombreDivisionResidencia(resultSet.getString("nombreDivisionResidencia"));
	        	medResi.setNomGtVmpp((resultSet.getString("nomGtVmppSustXResi")!=null?resultSet.getString("nomGtVmppSustXResi"):resultSet.getString("nomGtVmpp")));
	        	medResi.setOidGestSustituciones(resultSet.getInt("oidGestSustituciones"));
	        	medResi.setOidGestSustitucionesXResi(resultSet.getInt("oidGestSustitucionesXResi"));
	        	medResi.setSpdAccionBolsa((resultSet.getString("accionSustXResi")!=null?resultSet.getString("accionSustXResi"):resultSet.getString("accion")));
	        	medResi.setSpdNombreBolsa((resultSet.getString("nombreCortoSustXResi")!=null?resultSet.getString("nombreCortoSustXResi"):resultSet.getString("nombreCorto")));
	        	medResi.setSustituible((resultSet.getString("sustituibleXResi")!=null?resultSet.getString("sustituibleXResi"):resultSet.getString("sustituible")));
	        	
	        	
	        	//buscamos el CN por orden de preferencia según el resultado de la query
	        	//buscamos el nombre por orden de preferencia según el resultado de la query
	        	String cnFinal=resultSet.getString("cnBibliaSustXResi");
	        	String nombreMedicamento=resultSet.getString("nombreMedicamentoBibliaSustXResi");
	        	
	        	if(medResi.getSustituible().equalsIgnoreCase("NO"))
	        	{
	        		cnFinal=resultSet.getString("cnOkSustXResi");
	        		nombreMedicamento=resultSet.getString("nombreMedicamentoOkSustXResi");
	        		medResi.setBiblia(false);
	        	}
	        	else {
	        		if(cnFinal==null || cnFinal.equals("")) cnFinal=resultSet.getString("cnBiblia");
	        		if(cnFinal==null || cnFinal.equals("")) cnFinal=resultSet.getString("cnOkSustXResi");
	        		if(cnFinal==null || cnFinal.equals("")) cnFinal=resultSet.getString("cnOk");
		        	if(nombreMedicamento==null || (nombreMedicamento!=null && (nombreMedicamento.trim()==null || nombreMedicamento.trim().equals(""))))  nombreMedicamento=resultSet.getString("nombreMedicamentoBiblia");
		        	if(nombreMedicamento==null || (nombreMedicamento!=null && (nombreMedicamento.trim()==null || nombreMedicamento.trim().equals(""))))  nombreMedicamento=resultSet.getString("nombreMedicamentoOkSustXResi");
		        	if(nombreMedicamento==null || (nombreMedicamento!=null && (nombreMedicamento.trim()==null || nombreMedicamento.trim().equals("")))) nombreMedicamento=resultSet.getString("nombreMedicamentoOk");
	        	}
	        	
	        	
	        	
	        	medResi.setSpdCnFinal(cnFinal);
	        	medResi.setSpdNombreMedicamento(nombreMedicamento);
	        	
	        	
	        	medResi.setSpdComentarioLopicost((resultSet.getString("comentarioSustXResi")!=null?resultSet.getString("comentarioSustXResi"):resultSet.getString("comentario")));
	        	medResi.setSpdFormaMedicacion((resultSet.getString("nomFormaFarmaceutica2")!=null?resultSet.getString("nomFormaFarmaceutica2"):(resultSet.getString("nomFormaFarmaceutica")!=null?resultSet.getString("nomFormaFarmaceutica"):resultSet.getString("nomFormaFarmaceuticaConsejoResi"))));
	          	
	        	medResi.setSpdNombreLab((resultSet.getString("nombreLabBibliaSustXResi")!=null?resultSet.getString("nombreLabBibliaSustXResi"):resultSet.getString("nombreLabBiblia")));
	        	
	        	medResi.setSpdNota((resultSet.getString("notaBibliaSustXResi")!=null?resultSet.getString("notaBibliaSustXResi"):resultSet.getString("notaBiblia")));
	    		//si es un SIPRECISA ponemos "NO_PINTAR"
	        	if(medResi.getResiSiPrecisa()!=null && medResi.getResiSiPrecisa().equals("X")) medResi.setSpdAccionBolsa("NO_PINTAR");

	        	/*if(cnFinal==null || cnFinal.equals("")  || cnFinal.equals("null") )
	        		{
	        		medResi.setMensajesAlerta(medResi.getMensajesAlerta() + " FALTA CN FINAL ");
	        		medResi.setIncidencia("SI");
	        		
	        		}
	        	*/
	        	
	        	
	    		//medResi.setBiblia(biblia);
	        //	medResi.set(resultSet.getString("oidUsuario"));

	         //	sustituciones.add(c);
	        }
	        //no debería haber más de uno, pero en ese caso devolverá el primero de la lista
	    	//if(sustituciones!=null && sustituciones.size()>0)
	    	//	result=sustituciones.get(0);
	        medResi=IOSpdApi.checkTratamientoValido( spdUsuario, medResi);
	    	
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }finally {con.close();}

	    return medResi;
	}

	/**
	 * Método que devuelve UNA sustitución completa (RESI - CNOK - BIBLIA)
	 * @param idDivisionResidenciaFiltro
	 * @param resiCn
	 * @param resiMedicamento
	 * @param medResi
	 * @return
	 * @throws Exception 
	 */
	

	public static FicheroResiBean buscaSustitucionBiblia(String spdUsuario, String idDivisionResidenciaFiltro,  String resiCn, String resiMedicamento, FicheroResiBean medResi) throws Exception {
		
		DivisionResidencia divisionResidencia=DivisionResidenciaDAO.getDivisionResidenciaById(spdUsuario, idDivisionResidenciaFiltro);
		if(divisionResidencia!=null)
		return buscaSustitucionBiblia(spdUsuario, divisionResidencia.getOidDivisionResidencia(), resiCn, null, medResi, false);
		else return null;
	}
	
	
	//public static FicheroResiDetalleBean buscaSustitucionBiblia(FicheroResiDetalleForm form, FicheroResiDetalleBean medResi) throws ClassNotFoundException, SQLException {
	public static FicheroResiBean buscaSustitucionBiblia( String spdUsuario, int oidDivisionResidenciaFiltro,  String resiCn, String resiMedicamento, FicheroResiBean medResi, boolean todas) throws Exception {
		Connection con = Conexion.conectar();
		//   GestSustituciones result=null;

		List<GestSustituciones> listaSust= new ArrayList<GestSustituciones>();	
		GestSustituciones sust= null;
		GestSustitucionesXResi sustXResi= null;
		listaSust=getSustituciones(spdUsuario, oidDivisionResidenciaFiltro, -1, -1, resiCn, resiMedicamento, null,  false, 0, 1, "", "",  "", todas) ;
			 
		if(listaSust!=null && listaSust.size()>0) 
		 {
			 sust= (GestSustituciones)listaSust.get(0);
			 if(sust.getListaSustitucionesXResi()!=null && sust.getListaSustitucionesXResi().size()>0) 
				 sustXResi=sust.getListaSustitucionesXResi().get(0);
		 
			 String codGtVmpp=sustXResi.getCodGtVmppOkSustXResi()!=null&& !sustXResi.getCodGtVmppOkSustXResi().equals("")?sustXResi.getCodGtVmppOkSustXResi():sust.getCodGtVmppOk();
			 medResi.setCodGtVmpp(codGtVmpp);
			 String comentarioInterno=sustXResi.getComentarioSustXResi()!=null&& !sustXResi.getComentarioSustXResi().equals("")?sustXResi.getComentarioSustXResi():sust.getComentarioInterno();
			 medResi.setComentarioInterno(comentarioInterno);
			 String nombreDivisionResidencia=sustXResi.getNombreDivisionResidencia()!=null&& !sustXResi.getNombreDivisionResidencia().equals("")?sustXResi.getNombreDivisionResidencia():sust.getIdDivisionResidencia();
			 medResi.setNombreDivisionResidencia(nombreDivisionResidencia);
			 String nomGtVmpp=sustXResi.getNomGtVmppOkSustXResi()!=null&& !sustXResi.getNomGtVmppOkSustXResi().equals("")?sustXResi.getNomGtVmppOkSustXResi():sust.getNomGtVmppOk();
			 medResi.setNomGtVmpp(nomGtVmpp);
			 int oidGestSustituciones = sustXResi.getOidGestSustituciones()>0?sustXResi.getOidGestSustituciones():sust.getOidGestSustituciones();
			 medResi.setOidGestSustituciones(oidGestSustituciones);
			 int oidGestSustitucionesXResi = sustXResi.getOidGestSustitucionesXResi()>0?sustXResi.getOidGestSustitucionesXResi():0;
			 medResi.setOidGestSustitucionesXResi(oidGestSustitucionesXResi);
			 String spdAccionBolsa=sustXResi.getAccionSustXResi()!=null&& !sustXResi.getAccionSustXResi().equals("")?sustXResi.getAccionSustXResi():sust.getAccion();
			 medResi.setSpdAccionBolsa(spdAccionBolsa);
			 String spdNombreBolsa=sustXResi.getNombreCortoSustXResi()!=null&& !sustXResi.getNombreCortoSustXResi().equals("")?sustXResi.getNombreCortoSustXResi():sust.getNombreCorto();
			 medResi.setSpdNombreBolsa(spdNombreBolsa);
			 String sustituible=sustXResi.getSustituibleXResi()!=null&& !sustXResi.getSustituibleXResi().equals("")?sustXResi.getSustituibleXResi():sust.getSustituible();
			 medResi.setSustituible(sustituible);
			
		   	//buscamos el CN por orden de preferencia según el resultado de la query
			 //1-cnBibliaSustXResi / 2-cnBiblia / 3 - cnOkSustXResi / 4 - cnOk
			String cnFinal=sustXResi!=null && sustXResi.getBdConsejoSustXResiBiblia()!=null ?sustXResi.getBdConsejoSustXResiBiblia().getCnConsejo():null; 	//	1 -	cnBibliaSustXResi 
			cnFinal = cnFinal==null && sust!=null && sust.getBdConsejoBiblia()!=null ?sust.getBdConsejoBiblia().getCnConsejo():null;						// 	2 -	cnBiblia
			cnFinal = cnFinal==null && sustXResi!=null ?sustXResi.getCnOkSustXResi():cnFinal;																// 	3 - cnOkSustXResi 
			cnFinal = cnFinal==null && sust!=null ?sust.getCnOk():cnFinal;																					// 	4 - cnOk
			
			 
			//buscamos el nombre por orden de preferencia según el resultado de la query
			 //1-nombreMedicamentoBibliaSustXResi / 2-nombreMedicamentoBiblia / 3 - nombreMedicamentoOkSustXResi / 4 - nombreMedicamentoOk
							 
			String nombreMedicamento=sustXResi!=null && sustXResi.getBdConsejoSustXResiBiblia()!=null ?sustXResi.getBdConsejoSustXResiBiblia().getNombreMedicamento():null; 	//	1 -	nombreMedicamentoBibliaSustXResi 
			nombreMedicamento = nombreMedicamento==null && sust!=null && sust.getBdConsejoBiblia()!=null ?sust.getBdConsejoBiblia().getNombreMedicamento():null;				// 	2 -	nombreMedicamentoBiblia
			nombreMedicamento = nombreMedicamento==null && sustXResi!=null ?sustXResi.getNombreMedicamentoOkSustXResi():null;													// 	3 - nombreMedicamentoOkSustXResi 
			nombreMedicamento = nombreMedicamento==null && sust!=null ?sust.getNombreMedicamentoOk():null;																		// 	4 - nombreMedicamentoOk
		
			if(medResi.getSustituible().equalsIgnoreCase("NO"))
			{
				cnFinal = cnFinal==null && sustXResi!=null ?sustXResi.getCnOkSustXResi():sust.getCnOk();				
				nombreMedicamento = nombreMedicamento==null && sustXResi!=null ?sustXResi.getNombreMedicamentoOkSustXResi():sust.getNombreMedicamentoOk();				
				medResi.setBiblia(false);
			}

			medResi.setSpdCnFinal(cnFinal);
			medResi.setSpdNombreMedicamento(nombreMedicamento);
			

			String spdComentarioLopicost = sustXResi!=null &&sustXResi.getComentarioInterno()!=null && !sustXResi.getComentarioInterno().equals("")?(sust!=null&&sust.getComentarioInterno()!=null?sust.getComentarioInterno():""):"";
			medResi.setSpdComentarioLopicost(spdComentarioLopicost);

			String spdFormaMedicacion = sustXResi!=null 
					&& sustXResi.getBdConsejoSustXResi()!=null  
					&& sustXResi.getBdConsejoSustXResi().getNombreFormaFarmaceutica()!=null
					&& !sustXResi.getBdConsejoSustXResi().getNombreFormaFarmaceutica().equals("")
					?	sustXResi.getBdConsejoSustXResi().getNombreFormaFarmaceutica()
					:	(sust!=null && sust.getBdConsejo()!=null  && sust.getBdConsejo().getNombreFormaFarmaceutica()!=null?sust.getBdConsejo().getNombreFormaFarmaceutica():"");

			medResi.setSpdFormaMedicacion(spdFormaMedicacion);
			
			String spdNombreLab = sustXResi!=null 
					&& sustXResi.getBdConsejoSustXResi()!=null  
					&& sustXResi.getBdConsejoSustXResi().getNombreLaboratorio()!=null
					&& !sustXResi.getBdConsejoSustXResi().getNombreLaboratorio().equals("")
					?	sustXResi.getBdConsejoSustXResi().getNombreLaboratorio()
					:	(sust!=null && sust.getBdConsejo()!=null  && sust.getBdConsejo().getNombreLaboratorio()!=null?sust.getBdConsejo().getNombreLaboratorio():"");
			medResi.setSpdNombreLab(spdNombreLab);
			
			int spdNota = sustXResi!=null 
					&& sustXResi.getBdConsejoSustXResiBiblia()!=null  
					?	sustXResi.getBdConsejoSustXResiBiblia().getNota()
					:	(sust!=null && sust.getBdConsejoBiblia()!=null ?sust.getBdConsejoBiblia().getNota():null);				
					
			medResi.setSpdNota(spdNota+"");
			String mensajesInfo = sustXResi!=null 
					&& sustXResi.getMensajesInfo()!=null  
					&& !sustXResi.getMensajesInfo().equals("")
					&& !sustXResi.getMensajesInfo().equals("null")
					?	sustXResi.getMensajesInfo()
					:	(sust!=null && sust.getMensajesInfo()!=null && !sust.getMensajesInfo().equals("")&& !sust.getMensajesInfo().equals("null")?sust.getMensajesInfo():"");
			medResi.setMensajesInfo(mensajesInfo);

			String mensajesAlerta = sustXResi!=null 
					&& sustXResi.getMensajesAlerta()!=null  
					&& !sustXResi.getMensajesAlerta().equals("")
					&& !sustXResi.getMensajesAlerta().equals("null")
					?	sustXResi.getMensajesAlerta()
					:	(sust!=null && sust.getMensajesAlerta()!=null && !sust.getMensajesAlerta().equals("")&& !sust.getMensajesAlerta().equals("null") ?sust.getMensajesAlerta():"");
			medResi.setMensajesAlerta(medResi.getMensajesAlerta() + " - " + mensajesAlerta);
    		//si es un SIPRECISA ponemos "NO_PINTAR"
        	if(medResi.getResiSiPrecisa()!=null && medResi.getResiSiPrecisa().equals("X")) medResi.setSpdAccionBolsa("NO_PINTAR");

		 }

	    return medResi;
	}


   	private static String getQuerySustituciones( String spdUsuario, int oidDivisionResidencia, int oidGestSustituciones, int oidGestSustitucionesXResi,String getFiltroNombreCorto, String filtroMedicamentoResi, String filtroNombreCortoOK, boolean count, int inicio, int fin, String condicionBusqueda, String filtroAccion, String filtroFormaFarmaceutica, boolean todas) throws Exception {
		
   		
   		
   		
		String select =" select * from ( select  distinct ROW_NUMBER() OVER(order by g.cnResi,  gr.idDivisionResidencia desc, n2.nota desc, n1.nota desc ) AS ROWNUM,   ";
   				select+="  g.oidGestSustituciones, g.fechaSust, g.cnResi, ";
				select+="  g.medicamentoResi, g.cnOk, g.nombreCorto, g.formaFarmaceuticaSustitucion, ";
				select+="  g.nombreMedicamentoOk, g.codGtVmppOk, g.nomGtVmppOk, ";
				select+="  c0.CODIGO as cnConsejoResi,  c0.nombre as nombreConsejoResi, c0.presentacion as presentacionConsejoResi, ";
				select+="  c0.codGtVmpp as codGtVmppResi, c0.nomGtVmpp as nomGtVmppConsejoResi,  c0.nomFormaFarmaceutica as nomFormaFarmaceuticaConsejoResi,  ";
				select+="  g.accion, g.comentario, g.oidUsuario, g.comentarioInterno, g.sustituible, ";
				select+="  gr.nombreMedicamentoOkSustXResi, gr.codGtVmppOkSustXResi, gr.nomGtVmppOkSustXResi, ";
				select+="  gr.oidGestSustitucionesXResi, gr.oidGestSustituciones as oidGestSustituciones2, gr.fechaSustXResi, gr.oidDivisionResidencia,  ";
				select+="  gr.idDivisionResidencia as idDivisionResidencia2, gr.cnOkSustXResi, gr.nombreCortoSustXResi, gr.accionSustXResi,  ";
				select+="  gr.comentarioSustXResi, gr.oidUsuario as oidUsuario2,  gr.sustituible as sustituibleXResi, ";
				select+="  d.nombreDivisionResidencia, c.CODIGO, c.nombre, c.presentacion, c.nomFormaFarmaceutica, ";
				select+="  c2.CODIGO as codigo2, c2.nombre as nombre2, c2.presentacion as presentacion2, c2.nomFormaFarmaceutica as nomFormaFarmaceutica2, ";
				select+="  c.codGtVmpp as codGtVmpp, c.nomGtVmpp as nomGtVmpp, c2.codGtVmpp as codGtVmppSustXResi, c2.nomGtVmpp as nomGtVmppSustXResi, ";
				select+="  n1.cn as cnBiblia, n1.nombreMedicamentoBiblia, n1.codiLabo as codiLabBiblia, n1.nombreLab as nombreLabBiblia, n1.nota as notaBiblia, "; //n1.codConjHomog as codConjHomogBiblia, n1.nomConjHomog as nomConjHomogBiblia, ";
				select+="  n2.cn as cnBibliaSustXResi, n2.nombreMedicamentoBiblia as nombreMedicamentoBibliaSustXResi, n2.codiLabo as codiLabBibliaSustXResi, n2.nombreLab as nombreLabBibliaSustXResi, n2.nota as notaBibliaSustXResi "; // n2.codConjHomog as codConjHomogBibliaSustXResi, n2.nomConjHomog as nomConjHomogBibliaSustXResi, ";
				
			String 	from =" from dbo.gest_sustituciones g ";

		
		//	if(todas)
			from+=" left join dbo.gest_sustitucionesXResi gr on (g.oidGestSustituciones=gr.oidGestSustituciones  ";
				
		//	else
		//	from+=" INNER join dbo.gest_sustitucionesXResi gr on (g.oidGestSustituciones=gr.oidGestSustituciones  ";
		

		
		if(oidDivisionResidencia>0)
			from+=  " and gr.oidDivisionResidencia  = '"+oidDivisionResidencia +"' ";

			from+=  " ) ";
				
		if(oidDivisionResidencia>0)
			from+=" INNER join dbo.bd_divisionResidencia d  on (gr.oidDivisionResidencia=d.oidDivisionResidencia) ";
		else
			from+=" left join dbo.bd_divisionResidencia d  on (gr.oidDivisionResidencia=d.oidDivisionResidencia) ";
				
				
		String	where= " WHERE d.idDivisionResidencia IN ( " + VisibilidadHelper.divisionResidenciasVisibles(spdUsuario)  + ")";

		String order =" ";
		String otros =" ";
			
		if(count)  //contador
			select = "select count(distinct g.oidGestSustituciones) as quants";
		
		
		if(condicionBusqueda!=null && condicionBusqueda.equals("1")) //No existen en BdConsejo
		{
			from+=  " left join bd_consejo c0 on (c0.codigo =  SUBSTRING(CAST(g.cnResi  AS CHAR(7)),1,6)   )  ";	
			from+=  " left join bd_consejo c on (c.codigo =  SUBSTRING(CAST(g.cnOk  AS CHAR(7)),1,6)   ) ";
			from+=  " left join bd_consejo c2 on (c2.codigo =  SUBSTRING(CAST(gr.cnOkSustXResi  AS CHAR(7)),1,6)   ) ";
			where+= " and c.codigo is null ";
			where+= " and c2.codigo is null ";
		}
		else if(condicionBusqueda!=null && condicionBusqueda.equals("2")) //sólo los que existen en BdConsejo
		{
			from+=  " left join bd_consejo c0 on (c0.codigo =  SUBSTRING(CAST(g.cnResi  AS CHAR(7)),1,6)   )  ";	
			from+=  " inner join bd_consejo c on (c.codigo =  SUBSTRING(CAST(g.cnOk AS CHAR(7)),1,6)   ) ";
			from+=  " inner join bd_consejo c2 on (c2.codigo =  SUBSTRING(CAST(gr.cnOkSustXResi  AS CHAR(7)),1,6)   ) ";
		}
		else //if(condicionBusqueda==null || condicionBusqueda.equals("") || condicionBusqueda.equals("0")) //Todos
		{
			from+=  " left join bd_consejo c0 on (c0.codigo =  SUBSTRING(CAST(g.cnResi  AS CHAR(7)),1,6)   )  ";	
			from+=  " left join bd_consejo c on (c.codigo =  SUBSTRING(CAST(g.cnOk  AS CHAR(7)),1,6)   ) ";
			from+=  " left join bd_consejo c2 on (c2.codigo =  SUBSTRING(CAST(gr.cnOkSustXResi  AS CHAR(7)),1,6)   ) ";

		}
		 
	
		//miramos correspondencia con Biblia
		from+=  " left join   ";
		from+=  " (	";
			//-- 1-CN/1-CodiLab/1-codGtVmpp/1-nota + datos CN
		from+=  " 	select distinct cnLabChNota_1.nota, cnLabChNota_1.codGtVmpp, cnLabChNota_1.nomGtVmpp,  cnLabChNota_1.codiLabo , cnLabChNota_1.nomLabo as nombreLab, cnLabChNota_1.nomLabo, cnLabChNota_1.cn,";
		from+=  "   cons_b2.NOMBRE+' '+cons_b2.PRESENTACION   as nombreMedicamentoBiblia ";
		from+=  "   from bd_Consejo cons_b2 inner join  ";
		from+=  " 	(";
			//	-- 1-CN/1-CodiLab/1-codGtVmpp/1-nota
		from+=  " 		select distinct LabChNota_1.nota, LabChNota_1.codGtVmpp, LabChNota_1.nomGtVmpp,  LabChNota_1.codiLabo ,cons_b1.nomLabo, max(cons_b1.CODIGO) as cn";
		from+=  " 		from bd_Consejo cons_b1";
		from+=  " 		inner join ";
		from+=  " 		(";
					//-- 1-CodiLab/1-codGtVmpp/1-nota";
		from+=  " 			select max(ch1.codiLab) as codiLabo,  conjNota_1.codGtVmpp, conjNota_1.nomGtVmpp, conjNota_1.nota ";
		from+=  " 			from SPD_sustXComposicion ch1";
		from+=  " 			inner join ";
		from+=  " 			(";
					//	-- 1-codGtVmpp/1-nota
		from+=  " 				select max(sx.ponderacion + sx.rentabilidad) as nota,  sx.codGtVmpp, sx.nomGtVmpp ";  		
		from+=  " 				from SPD_sustXComposicion sx 		";
		from+=  " 				group by sx.codGtVmpp, sx.nomGtVmpp";
		from+=  " 			) conjNota_1 on (conjNota_1.codGtVmpp = ch1.codGtVmpp   and conjNota_1.nota= (ch1.ponderacion + ch1.rentabilidad)) ";
		from+=  " 			group by conjNota_1.codGtVmpp, conjNota_1.nomGtVmpp, conjNota_1.nota";
		from+=  " 		) LabChNota_1 on (LabChNota_1.codiLabo=cons_b1.codiLabo and LabChNota_1.codGtVmpp=cons_b1.codGtVmpp )";
		from+=  " 		group by LabChNota_1.nota, LabChNota_1.codGtVmpp, LabChNota_1.nomGtVmpp,  LabChNota_1.codiLabo ,cons_b1.nomLabo";
		from+=  " 	)cnLabChNota_1 on (cnLabChNota_1.cn=cons_b2.codigo)";
		from+=  " ) n1 on  ( n1.codGtVmpp=c.codGtVmpp) ";

		//miramos correspondencia con Biblia la sustitución específica de la resi
		from+=  "  left join     ";
		from+=  " (	";
				//	-- 1-CN/1-CodiLab/1-codGtVmpp/1-nota + datos CN  
		from+=  "  	select distinct cnLabChNota_2.nota, cnLabChNota_2.codGtVmpp, cnLabChNota_2.nomGtVmpp,  cnLabChNota_2.codiLabo ,cnLabChNota_2.nomLabo as nombreLab, cnLabChNota_2.cn,  ";
		from+=  "      cons_a2.NOMBRE+' '+cons_a2.PRESENTACION   as nombreMedicamentoBiblia   ";
		from+=  "  	from bd_Consejo cons_a2 inner join    ";
		from+=  "  	(  ";
				//	-- 1-CN/1-CodiLab/1-codGtVmpp/1-nota  
		from+=  "  		select distinct LabChNota_2.nota, LabChNota_2.codGtVmpp, LabChNota_2.nomGtVmpp,  LabChNota_2.codiLabo ,cons_a1.nomLabo, max(cons_a1.CODIGO) as cn  ";
		from+=  "          from bd_Consejo cons_a1  ";
		from+=  "          inner join   ";
		from+=  "  		(  ";
					//	-- 1-CodiLab/1-codGtVmpp/1-nota 
		from+=  "  			select max(GtVmpp.codiLab) as codiLabo,  conjNota_2.codGtVmpp, conjNota_2.nomGtVmpp, conjNota_2.nota   ";
		from+=  "  			from SPD_sustXComposicion GtVmpp  ";
		from+=  "  			inner join   ";
		from+=  "  			(  ";
						//	-- 1-codGtVmpp/1-nota  
		from+=  "  				select max(sx.ponderacion + sx.rentabilidad) as nota,  sx.codGtVmpp, sx.nomGtVmpp ";
		from+=  "  				from SPD_sustXComposicion sx 		  ";
		from+=  "  				group by sx.codGtVmpp, sx.nomGtVmpp ";
		from+=  "  			) conjNota_2 on (conjNota_2.codGtVmpp = GtVmpp.codGtVmpp  and conjNota_2.nota= (GtVmpp.ponderacion + GtVmpp.rentabilidad))   ";
		from+=  "  		group by conjNota_2.codGtVmpp, conjNota_2.nomGtVmpp, conjNota_2.nota  ";
		from+=  "  		) LabChNota_2 on (LabChNota_2.codiLabo=cons_a1.codiLabo and LabChNota_2.codGtVmpp=cons_a1.codGtVmpp )  ";
		from+=  "  		group by LabChNota_2.nota, LabChNota_2.codGtVmpp, LabChNota_2.nomGtVmpp,  LabChNota_2.codiLabo ,cons_a1.nomLabo  ";
		from+=  "  	)cnLabChNota_2 on (cnLabChNota_2.cn=cons_a2.codigo)  ";
		from+=  "  ) n2 on  ( n2.codGtVmpp=c2.codGtVmpp)  ";
		
		
	//	if(oidDivisionResidencia<1)
	//		where+=  " and d.oidDivisionResidencia = '"+oidDivisionResidencia+"'";
		if(oidGestSustitucionesXResi>0)
			 where+=  " and gr.oidGestSustitucionesXResi = '"+oidGestSustitucionesXResi+"'";
		if(oidGestSustituciones>0)
			 where+=  " and g.oidGestSustituciones = '"+oidGestSustituciones+"'";
		if(getFiltroNombreCorto!=null && !getFiltroNombreCorto.equals(""))
		{
			where+=  " and ( UPPER(n1.cn) like '%"+StringUtil.limpiarTextoyEspacios(getFiltroNombreCorto).toUpperCase() +"%'  OR  UPPER(g.cnResi) like '%"+StringUtil.limpiarTextoyEspacios(getFiltroNombreCorto).toUpperCase() +"%' ";
			where+=" 		OR UPPER(g.cnOk) like '%"+StringUtil.limpiarTextoyEspacios(getFiltroNombreCorto).toUpperCase() +"%' OR UPPER(gr.cnOkSustXResi) like '%"+StringUtil.limpiarTextoyEspacios(getFiltroNombreCorto).toUpperCase() +"%'  ";
			where+=" 		OR UPPER(g.medicamentoResi) like '%"+getFiltroNombreCorto.toUpperCase() +"%' OR  UPPER(g.nombreCorto)  like '%"+getFiltroNombreCorto.toUpperCase() +"%' ";
			where+="		OR  UPPER(g.comentario)  like '%"+getFiltroNombreCorto.toUpperCase() +"%' ";
			where+="		) ";
		}
		if(filtroMedicamentoResi!=null && !filtroMedicamentoResi.equals(""))
			where+=  " and UPPER(g.medicamentoResi) = '"+filtroMedicamentoResi.toUpperCase() +"' ";
		if(filtroAccion!=null && !filtroAccion.equals(""))
//			where+=  " and ( UPPER(g.accion) = '"+StringUtil.limpiarTextoyEspacios(filtroAccion).toUpperCase() +"' OR  UPPER(gr.accionSustXResi) = '"+filtroAccion.toUpperCase() +"' ) ";  
			where+=  " and ( UPPER(g.accion) = '"+filtroAccion.toUpperCase() +"' OR  UPPER(gr.accionSustXResi) = '"+filtroAccion.toUpperCase() +"' ) ";  
		if(filtroFormaFarmaceutica!=null && !filtroFormaFarmaceutica.equals(""))
			where+=  " and ( UPPER(c.FormaFarmaceutica) = '"+filtroFormaFarmaceutica.toUpperCase() +"' OR  UPPER(c2.FormaFarmaceutica) = '"+filtroFormaFarmaceutica.toUpperCase() +"' ) ";  
		if(filtroNombreCortoOK!=null && !filtroNombreCortoOK.equals(""))
		{
			where+=  " and UPPER(g.nombreCorto) like '"+filtroNombreCortoOK.toUpperCase() +"' ";
    	}
		
    	//if(!count) 
    	//{
    		//order+=  " order by g.medicamentoResi, g.oidGestSustituciones, gr.idDivisionResidencia ";
    		//order+=  " order by g.cnResi,  gr.idDivisionResidencia desc, n2.nota desc, n1.nota desc   ";  //importante este orden, para que salga primero la residencia en caso que exista SustXResi

    	//	otros+= " offset "+ (inicio) + " rows ";
    		//	otros+= " fetch next "+SPDConstants.PAGE_ROWS+ " rows only";
    	//}
		
		String qry = select + from + where + order + getOtrosSql2008(inicio, inicio+SPDConstants.PAGE_ROWS,count);
	
		 System.out.println("getQuerySustituciones -->" +qry );		

			return qry;
	}

   	/**
	 * Fetch es una cláusula que funciona a partir del SqlServer 2008 (no inclusive)
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
	 * Como FETCH es una cláusula de versión SQLSERVER>2008 se crea una función un poco más engorrosa pero
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
    
	
	
}
 
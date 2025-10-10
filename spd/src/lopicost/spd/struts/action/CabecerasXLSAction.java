package lopicost.spd.struts.action;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import lopicost.spd.controller.SpdLogAPI;
import lopicost.spd.persistence.CabecerasXLSDAO;
import lopicost.spd.persistence.FicheroResiDetalleDAO;
import lopicost.spd.struts.bean.CabecerasXLSBean;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.struts.form.FicheroResiForm;
import lopicost.spd.struts.helper.CabecerasXLSHelper;
import lopicost.spd.utils.StringUtil;




public class CabecerasXLSAction extends GenericAction  {

    public ActionForward consulta(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
    	FicheroResiForm formulari =  (FicheroResiForm) form;
    	formulari.setIdUsuario(getIdUsuario());
    	List tomasCabecera = CabecerasXLSDAO.list(getIdUsuario(), formulari.getOidDivisionResidencia());
    	formulari.setListaTomasCabecera(tomasCabecera);
        return mapping.findForward("consulta");
    }

    
    public ActionForward edicionToma(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
    	FicheroResiForm formulari =  (FicheroResiForm) form;
      	FicheroResiBean cab = FicheroResiDetalleDAO.getFicheroResiDetalleByIdOid(getIdUsuario(), formulari.getOidFicheroResiDetalle());

    	formulari.setIdUsuario(getIdUsuario());
      	List errors = new ArrayList();
      	List<CabecerasXLSBean> tomasCabecera = CabecerasXLSDAO.list(getIdUsuario(), formulari.getOidDivisionResidencia());
    	String idToma = formulari.getIdToma();
    	if(formulari.getACTIONTODO()!=null && formulari.getACTIONTODO().equals("NOMBRETOMA"))
    	{
    	   	CabecerasXLSHelper.marcarEdicionNombreToma(tomasCabecera, idToma);
    	} 
    	else if(formulari.getACTIONTODO()!=null && formulari.getACTIONTODO().equals("HORATOMA"))
      	{
       	   	CabecerasXLSHelper.marcarEdicionHoraToma(tomasCabecera, idToma);
        }
    	else if(formulari.getACTIONTODO()!=null && formulari.getACTIONTODO().equals("EDITAR_OK"))
    	{
    		CabecerasXLSBean toma = CabecerasXLSHelper.getByIdToma(tomasCabecera, idToma);
    		CabecerasXLSBean tomaAntigua=toma.clone();
    		
    		
    		toma = CabecerasXLSHelper.editarToma(getIdUsuario(), tomaAntigua, toma, formulari, cab);
 				//INICIO eaci�n de log en BBDD
			if(toma!=null)
			{
				try{
				SpdLogAPI.addLog(getIdUsuario(),  null,  toma.getIdDivisionResidencia(), formulari.getIdProceso()
						, SpdLogAPI.A_CABECERA, SpdLogAPI.B_EDICION, SpdLogAPI.C_TOMAS, "SpdLog.cabecera.edicion.toma"
						, new String[]{ formulari.getIdProceso(), toma.getNombreToma(), toma.getHoraToma(), formulari.getNombreToma(), formulari.getHoraToma()}  );//variables
				}catch(Exception e){}	
				//FIN de log en BBDD
			}    	
			else
		    	errors.add( "No se ha podido realizar la modificación. Revise los datos introducidos");

    	}
    	tomasCabecera.sort(Comparator.comparingInt((CabecerasXLSBean t) -> Integer.parseInt(t.getIdToma())));
		formulari.setErrors(errors);
		formulari.setListaTomasCabecera(tomasCabecera);
        return mapping.findForward("edicionLista");
    }
    
    
    
    
    public ActionForward edicionLista(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
    	FicheroResiForm formulari =  (FicheroResiForm) form;
    	formulari.setIdUsuario(getIdUsuario());
     	List tomasCabecera = CabecerasXLSDAO.list(getIdUsuario(), formulari.getOidDivisionResidencia());
    	boolean existenPosteriores = CabecerasXLSHelper.controlEdicion(formulari.getOidDivisionResidencia(), formulari.getOidFicheroResiCabecera());
    	if(!existenPosteriores)
    	{
    		formulari.setMode("EDIT");
    	}
    	formulari.setListaTomasCabecera(tomasCabecera);
        return mapping.findForward("edicionLista");
    }

    public ActionForward nuevaToma(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
    	FicheroResiForm formulari =  (FicheroResiForm) form;
		
    	formulari.setIdUsuario(getIdUsuario());
    	List errors = new ArrayList();
		boolean result = false;
		List tomasCabecera = CabecerasXLSDAO.list(getIdUsuario(), formulari.getOidDivisionResidencia());
    	FicheroResiBean  cab = FicheroResiDetalleDAO.getGestFicheroResiBolsaByForm(getIdUsuario(), 0, formulari, true, false, false);
    	cab.setNumeroDeTomas(tomasCabecera.size());
    	
    	boolean existeTomaPrevia = CabecerasXLSHelper.existeTomaPrevia(tomasCabecera, formulari.getResiToma(), formulari.getResiTomaLiteral());
    	String horaTomaLiteral=formulari.getResiTomaLiteral(); 
    	if(horaTomaLiteral!=null && !horaTomaLiteral.equals("")) 
    		horaTomaLiteral=StringUtil.limpiarTextoyEspacios(horaTomaLiteral);  //quitamos espacios y algunos caracteres que no gustan al robot

    	//CabecerasXLSBean existeIdToma = CabecerasXLSDAO.findByFilters(formulari.getOidDivisionResidencia(), -1, -1, null, formulari.getResiToma(), horaTomaLiteral, false, false);
    	if(existeTomaPrevia)
    		 errors.add( "La hora y el nombre de la toma son obligatorios y han de ser diferentes a los existentes");

    	else 
    	{
        	CabecerasXLSBean nuevaToma = new CabecerasXLSBean( cab.getIdDivisionResidencia(), formulari.getResiToma(),  horaTomaLiteral, cab.getNumeroDeTomas()+1, "EXTRA", false, false); 
        	result = CabecerasXLSHelper.nuevaToma(getIdUsuario(), cab, nuevaToma);
			//INICIO eaci�n de log en BBDD
			try{
				SpdLogAPI.addLog(getIdUsuario(),  null,  cab.getIdDivisionResidencia(), formulari.getIdProceso()
						, SpdLogAPI.A_CABECERA, SpdLogAPI.B_CREACION, SpdLogAPI.C_TOMAS, "SpdLog.cabecera.creacion.toma"
						, new String[]{ formulari.getIdProceso(), nuevaToma.getNombreToma(), nuevaToma.getHoraToma(), String.valueOf(nuevaToma.getPosicionEnBBDD())
								, String.valueOf(nuevaToma.getPosicionEnVistas()), nuevaToma.getTipo()
								, String.valueOf(nuevaToma.isDesdeTomaPrimerDia()),  String.valueOf(nuevaToma.isHastaTomaUltimoDia())}  );//variables
			}catch(Exception e){}	
			//FIN de log en BBDD

    	}
    	//actualizaci�n con la nueva toma
		tomasCabecera = CabecerasXLSDAO.list(getIdUsuario(), formulari.getOidDivisionResidencia());
		formulari.setListaTomasCabecera(tomasCabecera);
		formulari.setErrors(errors);
    	//list(mapping,  form,  request,  response);
    	return mapping.findForward("edicionLista");
    }

    
 

	public ActionForward borrar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FicheroResiForm formulari =  (FicheroResiForm) form;
		List errors = new ArrayList();
		formulari.setIdUsuario(getIdUsuario());
		//recuperamos la lista
		FicheroResiBean  cabPlantilla = CabecerasXLSDAO.getCabecerasXLSByOidCabecera(getIdUsuario(), formulari.getOidFicheroResiCabecera());
		
		//if(cabPlantilla==null || cabPlantilla.getOidFicheroResiDetalle()==0)
		//en caso que no exista plantilla de cabecera, la creamos en base a la �ltima producci�n 
		if(cabPlantilla==null)
			crearPlantilla(formulari);
		formulari.setFicheroResiDetalleBean(cabPlantilla);
		
		
		boolean result=false;
		if(formulari.getACTIONTODO()!=null && formulari.getACTIONTODO().equals("BORRAR_OK"))
		{
			FicheroResiBean  cab = FicheroResiDetalleDAO.getGestFicheroResiBolsaByForm(getIdUsuario(), 0, formulari, true, false, false);
			CabecerasXLSBean resiToma = CabecerasXLSDAO.findByFilters(formulari.getOidDivisionResidencia(), formulari.getOidResiToma(), -1, null, null, null, false, false);
			//boolean borrable = CabecerasXLSDAO.esCampoBorrable(getIdUsuario(), formulari.getOidFicheroResiCabecera(), formulari.getResiToma());
			boolean borrable = CabecerasXLSDAO.esCampoBorrable(getIdUsuario(), formulari.getOidFicheroResiCabecera(), resiToma.getPosicionEnBBDD());
		
		   		if(borrable)
				{
		   			result=CabecerasXLSDAO.borradoDeToma(getIdUsuario(), formulari, cabPlantilla, cab, resiToma.getPosicionEnBBDD());
					errors.add("Toma borrada correctamente");
					//INICIO eaci�n de log en BBDD
					try{
						SpdLogAPI.addLog(getIdUsuario(),  null,  cab.getIdDivisionResidencia(), formulari.getIdProceso()
								, SpdLogAPI.A_CABECERA, SpdLogAPI.B_BORRADO, SpdLogAPI.C_TOMAS, "SpdLog.cabecera.borrado.toma"
								, new String[]{formulari.getIdProceso(), resiToma.getIdToma(), resiToma.getNombreToma(), resiToma.getHoraToma(), String.valueOf(resiToma.getPosicionEnBBDD())
										, String.valueOf(resiToma.getPosicionEnVistas()), resiToma.getTipo()
										, String.valueOf(resiToma.isDesdeTomaPrimerDia()),  String.valueOf(resiToma.isHastaTomaUltimoDia())}  );//variables
					}catch(Exception e){}	
					//FIN de log en BBDD
					

				}
				else errors.add( "No se ha podido borrar la toma porque existen pautas asignadas en esa hora.");

				formulari.setErrors(errors);
				cabPlantilla = CabecerasXLSDAO.getCabecerasXLSByOidCabecera(getIdUsuario(), formulari.getOidFicheroResiCabecera());
				formulari.setFicheroResiDetalleBean(cabPlantilla);

		}
    	List tomasCabecera = CabecerasXLSDAO.list(getIdUsuario(), formulari.getOidDivisionResidencia());
    	
    	formulari.setListaTomasCabecera(tomasCabecera);
		//formulari.setACTIONTODO("VIEW");
		formulari.setParameter("edicionLista");
		//list(mapping,  form,  request,  response);
		return mapping.findForward("edicionLista");

	}

	
	   
	private void crearPlantilla(FicheroResiForm formulari) throws Exception {
		boolean result=CabecerasXLSDAO.crearPlantilla(getIdUsuario(), formulari);
		
		
	}



}
	
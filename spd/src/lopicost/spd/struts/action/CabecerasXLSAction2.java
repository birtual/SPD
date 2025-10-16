package lopicost.spd.struts.action;


import java.util.ArrayList;
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




public class CabecerasXLSAction2 extends GenericAction  {

    public ActionForward consulta(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
    	FicheroResiForm formulari =  (FicheroResiForm) form;
    	formulari.setIdUsuario(getIdUsuario());
    	List tomasCabecera = CabecerasXLSDAO.list(getIdUsuario(), formulari.getOidDivisionResidencia());
    	formulari.setListaTomasCabecera(tomasCabecera);
        return mapping.findForward("consulta");
    }

    
    public ActionForward edicionLista(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
    	FicheroResiForm formulari =  (FicheroResiForm) form;
    	formulari.setIdUsuario(getIdUsuario());
    	List tomasCabecera = CabecerasXLSDAO.list(getIdUsuario(), formulari.getOidDivisionResidencia());
    	boolean existenPosteriores = CabecerasXLSHelper.controlEdicion(formulari.getOidDivisionResidencia(), formulari.getOidFicheroResiCabecera());
    	if(existenPosteriores )
    	{
    		formulari.setMode("VIEW");
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

    	
    	CabecerasXLSBean existeIdToma = CabecerasXLSDAO.findByFilters(formulari.getOidDivisionResidencia(), -1, -1, null, formulari.getResiToma(), horaTomaLiteral, false, false);
    	if(existeTomaPrevia)
    		 errors.add( "La hora y el nombre de la toma son obligatorios y han de ser diferentes a los existentes");

    	else 
    	{
        	CabecerasXLSBean nuevaToma = new CabecerasXLSBean( cab.getIdDivisionResidencia(), formulari.getResiToma(),  horaTomaLiteral, cab.getNumeroDeTomas()+1, "EXTRA", false, false); 
        	result = CabecerasXLSHelper.nuevaToma(getIdUsuario(), cab, nuevaToma);
			//INICIO eación de log en BBDD
			try{
				SpdLogAPI.addLog(getIdUsuario(),  null,  cab.getIdDivisionResidencia(), formulari.getIdProceso()
						, SpdLogAPI.A_CABECERA, SpdLogAPI.B_CREACION, SpdLogAPI.C_TOMAS, "SpdLog.cabecera.creacion.toma"
						, new String[]{ formulari.getIdProceso(), nuevaToma.getNombreToma(), nuevaToma.getHoraToma(), String.valueOf(nuevaToma.getPosicionEnBBDD())
								, String.valueOf(nuevaToma.getPosicionEnVistas()), nuevaToma.getTipo()
								, String.valueOf(nuevaToma.isDesdeTomaPrimerDia()),  String.valueOf(nuevaToma.isHastaTomaUltimoDia())}  );//variables
			}catch(Exception e){}	
			//FIN de log en BBDD

    	}
    	//actualización con la nueva toma
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
		//en caso que no exista plantilla de cabecera, la creamos en base a la última producción 
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
					errors.add( "Toma borrada correctamente ");
					//INICIO eación de log en BBDD
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

	
	
	public ActionForward moverPosicion(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	    	FicheroResiForm formulari =  (FicheroResiForm) form;
	    	formulari.setIdUsuario(getIdUsuario());
	    	String accion = formulari.getACTIONTODO();
	    	CabecerasXLSBean cabResiToma = CabecerasXLSDAO.findByFilters(formulari.getOidDivisionResidencia(), formulari.getOidResiToma(), -1, null, null, null, false, false);
	    	CabecerasXLSBean cabResiTomaAIntercambiar = null;
	    	
	    	if(cabResiToma!=null && accion!=null && accion.equalsIgnoreCase("SUBIR"))
	    	{
	    		cabResiToma.setPosicionEnVistas(cabResiToma.getPosicionEnVistas()-1);
	    		cabResiTomaAIntercambiar=CabecerasXLSDAO.findByFilters(formulari.getOidDivisionResidencia(), -1, cabResiToma.getPosicionEnVistas(), null, null, null, false, false);
	    		cabResiTomaAIntercambiar.setPosicionEnVistas(cabResiTomaAIntercambiar.getPosicionEnVistas()+1);
	    	}
	    	if(cabResiToma!=null && accion!=null && accion.equalsIgnoreCase("BAJAR"))
	    	{
	    		cabResiToma.setPosicionEnVistas(cabResiToma.getPosicionEnVistas()+1);
	    		cabResiTomaAIntercambiar=CabecerasXLSDAO.findByFilters(formulari.getOidDivisionResidencia(), -1, cabResiToma.getPosicionEnVistas(), null, null, null, false, false);
	    		cabResiTomaAIntercambiar.setPosicionEnVistas(cabResiTomaAIntercambiar.getPosicionEnVistas()-1);
	    	}
	    	if(cabResiTomaAIntercambiar!=null)
	    	{
		    	CabecerasXLSDAO.actualizaPosicion(cabResiToma);
		    	CabecerasXLSDAO.actualizaPosicion(cabResiTomaAIntercambiar);
	    	}
	    	    	
			//INICIO eación de log en BBDD
			try{
				SpdLogAPI.addLog(getIdUsuario(),  null,  cabResiToma.getIdDivisionResidencia(), formulari.getIdProceso()
						, SpdLogAPI.A_CABECERA, SpdLogAPI.B_EDICION, SpdLogAPI.C_TOMAS, "SpdLog.cabecera.moverPosicion.toma"
						, new String[]{formulari.getIdProceso(), cabResiToma.getNombreToma()
								, String.valueOf(cabResiToma.getPosicionEnVistas()), String.valueOf(cabResiTomaAIntercambiar.getPosicionEnVistas())
								, cabResiTomaAIntercambiar.getNombreToma()
								, String.valueOf(cabResiTomaAIntercambiar.getPosicionEnVistas()), String.valueOf(cabResiToma.getPosicionEnVistas()) }  );//variables
			}catch(Exception e){}
			//FIN de log en BBDD

			//Se ha intercambiado la posición de la toma  @@ --> antes:  @@ - después:  @@, con la posición de la toma  @@ --> antes:  @@ - después:  @@
			
	    	List tomasCabecera = CabecerasXLSDAO.list(getIdUsuario(), formulari.getOidDivisionResidencia());
	    	
	    	formulari.setListaTomasCabecera(tomasCabecera);
	    	
	    	
	    	//list(mapping,  form,  request,  response);
	    	return mapping.findForward("edicionLista");
	    }
	   
	   
	private void crearPlantilla(FicheroResiForm formulari) throws Exception {
		boolean result=CabecerasXLSDAO.crearPlantilla(getIdUsuario(), formulari);
		
		
	}

	/*
	//SUBIR / BAJAR POSICIONES
	public ActionForward moverPosicion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FicheroResiForm formulari =  (FicheroResiForm) form;

		FicheroResiBean  cab = FicheroResiDetalleDAO.getGestFicheroResiBolsaByForm(getIdUsuario(), 0, formulari, true, false, false);

		int posicion = -1;
		try {
			posicion = new Integer(request.getParameter("posicion")).intValue();
		}
		catch(Exception e)
		{
			
		}
		
		if(posicion>-1 && formulari.getACTIONTODO()!=null && formulari.getACTIONTODO().equalsIgnoreCase("SUBIR"))
		{
			CabecerasXLSDAO.intercambiaPosicion(cab, posicion, posicion+1);
		}
		
	    // Obtén el registro correspondiente al lugar que ocupará el actual

		
	    Registro registro = obtenerRegistroPorId(registroId);

	    // Obtén la lista actualizada de registros desde la base de datos
	    List<Registro> registrosList = obtenerRegistrosDeBaseDeDatos();

	    // Encuentra la posición actual del registro en la lista
	    int posicionActual = registrosList.indexOf(registro);

	    // Realiza la lógica para subir o bajar el registro
	    if ("Subir".equals(accion) && posicionActual > 0) {
	        // Intercambia el registro con el que está en la posición anterior
	        Registro registroAnterior = registrosList.get(posicionActual - 1);
	        registrosList.set(posicionActual, registroAnterior);
	        registrosList.set(posicionActual - 1, registro);
	    } else if ("Bajar".equals(accion) && posicionActual < registrosList.size() - 1) {
	        // Intercambia el registro con el que está en la posición siguiente
	        Registro registroSiguiente = registrosList.get(posicionActual + 1);
	        registrosList.set(posicionActual, registroSiguiente);
	        registrosList.set(posicionActual + 1, registro);
	    }

	    // Actualiza la lista de registros en la base de datos (esto puede depender de tu implementación)
	    actualizarRegistrosEnBaseDeDatos(registrosList);

	    // Pasa la lista actualizada a tu JSP
	    request.setAttribute("registrosList", registrosList);

	    // Redirige de nuevo a la página de visualización de registros
	    RequestDispatcher dispatcher = request.getRequestDispatcher("tu_jsp.jsp");
	    dispatcher.forward(request, response);
	}

	// Métodos auxiliares para obtener, actualizar registros, etc.

	
	*/
	
	


	 /*
		public ActionForward editar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
			FicheroResiForm formulari =  (FicheroResiForm) form;
			List errors = new ArrayList();
			
			//recuperamos la lista
			FicheroResiBean  cabPlantilla = CabecerasXLSDAO.getCabecerasXLSByOidCabecera(getIdUsuario(), formulari.getOidFicheroResiCabecera());
			
			//if(cabPlantilla==null || cabPlantilla.getOidFicheroResiDetalle()==0)
			if(cabPlantilla==null)
				crearPlantilla(formulari);
			formulari.setFicheroResiDetalleBean(cabPlantilla);
			
			
			boolean result=false;
			if(formulari.getACTIONTODO()!=null && formulari.getACTIONTODO().equals("EDITA_OK"))
			{
				FicheroResiBean  cab = FicheroResiDetalleDAO.getGestFicheroResiBolsaByForm(getIdUsuario(), 0, formulari, true, false, false);
				CabecerasXLSBean resiToma = CabecerasXLSDAO.findByFilters(formulari.getOidDivisionResidencia(), formulari.getOidResiToma(), -1, null, null);
				
				result=CabecerasXLSDAO.edita(getIdUsuario(), formulari, cabPlantilla, cab);
				
		   		if(result)
				{
		   			//result=FicheroResiDetalleDAO.actualizaNumeroDeTomas(cab);
					errors.add( "Registro editado correctamente ");
					//volvemos a mostrar el registro
					//formulari.setOidFicheroResiDetalle(0);
				}
				else errors.add( "No se ha podido editar el registro");

				formulari.setErrors(errors);
				cabPlantilla = CabecerasXLSDAO.getCabecerasXLSByOidCabecera(getIdUsuario(), formulari.getOidFicheroResiCabecera());
				formulari.setFicheroResiDetalleBean(cabPlantilla);

				
			}
			List tomasCabecera = CabecerasXLSDAO.list(getIdUsuario(), formulari.getOidDivisionResidencia(), formulari.getOidFicheroResiCabecera());
	    	
	    	formulari.setListaTomasCabecera(tomasCabecera);
			//formulari.setACTIONTODO("VIEW");
			formulari.setParameter("list");
			//list(mapping,  form,  request,  response);
			return mapping.findForward("list");
		}
		
	*/	
	


}
	
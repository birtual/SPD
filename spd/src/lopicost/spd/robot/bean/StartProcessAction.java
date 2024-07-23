package lopicost.spd.robot.bean;

import java.sql.SQLException;
import java.util.Iterator;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import lopicost.spd.helper.FicheroResiDetalleHelper;
import lopicost.spd.model.DivisionResidencia;
import lopicost.spd.persistence.DivisionResidenciaDAO;
import lopicost.spd.robot.helper.PlantillaUnificada;
import lopicost.spd.robot.helper.PlantillaUnificadaHelper;
import lopicost.spd.robot.model.FiliaDM;
import lopicost.spd.robot.model.FiliaRX;
import lopicost.spd.robot.model.TomasOrdenadas;
import lopicost.spd.struts.action.GenericAction;
import lopicost.spd.struts.bean.FicheroResiBean;

public class StartProcessAction extends GenericAction {

	TomasOrdenadas tomasOrdenadas = null;
	int registros = 0;
	int porcentaje= 0;
	List<DetallesTomasBean> detalleTomas = null;
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        // Obtén la sesión
        HttpSession session = request.getSession();
        //FicheroResiForm formulari =  (FicheroResiForm) form;
		//FicheroResiBean cab = dao.getCabeceraByFilters(getIdUsuario(), formulari, 0, 1, null, false);
		//FicheroResiBean cabDetalle  =  FicheroResiDetalleHelper.getCabeceraFicheroResi(getIdUsuario(), "general_feixa_llarga", "feixa_llarga_20240710_20240717_699244", false);
        FicheroResiBean cabDetalle  =  FicheroResiDetalleHelper.getCabeceraFicheroResi(getIdUsuario(), "general_mt_cambrils", "mt_cambrils_20240520_20240526_694216", false);
		DivisionResidencia div = DivisionResidenciaDAO.getDivisionResidenciaById(getIdUsuario(), "general_mt_cambrils");
		
        // Inicia el proceso en segundo plano
        Thread backgroundThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Paso 1
                    session.setAttribute("processProgress", getPorcentaje());
                    session.setAttribute("processMessage", "Ejecutando paso 1 - Borrado previo de posibles datos del mismo proceso  ");
                    if (paso1(cabDetalle, div)) {
                        // Paso 2
                        session.setAttribute("processProgress", getPorcentaje());
                        session.setAttribute("processMessage", "Ejecutando paso 2 - Recuperamos el orden y nombre de las tomas del proceso   ");
                        if (paso2(cabDetalle, div)) {
                            // Paso 3
                            session.setAttribute("processProgress", getPorcentaje());
                            session.setAttribute("processMessage", " Paso 3 - Recuperamos la lista de detalleTomas ya ordenada ");
                            paso3(cabDetalle);

                            // Paso 4
                            session.setAttribute("processProgress", getPorcentaje());
                            session.setAttribute("processMessage", "Paso4 - Procesar los detallesBean para insertarlos en BBDD");
                            /*
                        	Iterator<DetallesTomasBean> _it = getDetalleTomas().iterator();
                        	int porcentaje =25;
                       	 	while (_it.hasNext()) 
                            {
                       	 		DetallesTomasBean bean = (DetallesTomasBean)_it.next();
                       	 		PlantillaUnificadaHelper.procesarDetalleTomasRobot(getIdUsuario(), cabDetalle, bean, getTomasOrdenadas());
                       	 		setRegistros(getRegistros()+1);
                       	 		setPorcentaje(25 +   (getRegistros() * 75 / getDetalleTomas().size())); 
                       	 	session.setAttribute("processProgress", getPorcentaje());
                            }
                            */
                            //paso4(cabDetalle, getDetalleTomas());
                            
                            // paso4(cabDetalle, div, response);

                            // Proceso completado
                            session.setAttribute("processProgress", getPorcentaje());
                            session.setAttribute("processMessage", "Proceso completado!");
                            if (paso5(cabDetalle, div)) {
                            	 session.setAttribute("processProgress", getPorcentaje());
                                 session.setAttribute("processMessage", "Paso5 - Procesar Excepciones (Falguera)    ");
                                if (paso6(cabDetalle, div, response)) {
                                	 session.setAttribute("processProgress", getPorcentaje());
                                     session.setAttribute("processMessage", "Paso6 - Creación del FiliaDM    ");
                                     if (paso7(cabDetalle, div, response)) {
                                    	 session.setAttribute("processProgress", getPorcentaje());
                                         session.setAttribute("processMessage", "Paso - Creación del FiliaRX   ");

                                     } else {
                                         session.setAttribute("processProgress", 0);
                                         session.setAttribute("processMessage", "Error en el paso 7");
                                     }

                                } else {
                                    session.setAttribute("processProgress", 0);
                                    session.setAttribute("processMessage", "Error en el paso 6");
                                }
                            } else {
                                session.setAttribute("processProgress", 0);
                                session.setAttribute("processMessage", "Error en el paso 5");
                            }
                        } else {
                            session.setAttribute("processProgress", 0);
                            session.setAttribute("processMessage", "Error en el paso 2");
                        }
                    } else {
                        session.setAttribute("processProgress", 0);
                        session.setAttribute("processMessage", "Error en el paso 1");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    session.setAttribute("processProgress", 0);
                    session.setAttribute("processMessage", "Proceso interrumpido");
                }
            }
        });
        backgroundThread.start();

        return mapping.findForward("success");
    }
    

    

	// Paso1 - Borrado previo de posibles datos del mismo proceso  
    private boolean paso1(FicheroResiBean cabDetalle, DivisionResidencia div) throws ClassNotFoundException, SQLException, ParseException {
    	setPorcentaje(5);
    	PlantillaUnificadaHelper.borraProceso(getIdUsuario(),  cabDetalle);
      	return true;
    }

	// Paso2 - Recuperamos el orden y nombre de las tomas del proceso  
    private boolean paso2(FicheroResiBean cabDetalle, DivisionResidencia div) throws ClassNotFoundException, SQLException, ParseException {
    	setPorcentaje(10);
    	boolean result = false;
    	setTomasOrdenadas(PlantillaUnificadaHelper.getTomasOrdenadas(getIdUsuario(),  cabDetalle));
    	if(getTomasOrdenadas()!=null && getTomasOrdenadas().getPosiciones().size()>0) 
    		result = true;
    	return result;
     }

	// Paso3 - Recuperamos la lista de detalleTomas ya ordenada
    private List<DetallesTomasBean> paso3(FicheroResiBean cabDetalle) throws ClassNotFoundException, SQLException, ParseException {
    	setPorcentaje(15);
    	setDetalleTomas(PlantillaUnificadaHelper.getDetalleTomasRobot(getIdUsuario(),  cabDetalle, getTomasOrdenadas()));
      	return getDetalleTomas();
    }
   
	// Paso4 - Procesar los detallesBean para insertarlos en BBDD
    private boolean paso4(FicheroResiBean cabDetalle, List<DetallesTomasBean> detalleTomas) throws ClassNotFoundException, SQLException, ParseException {
		 boolean result=PlantillaUnificadaHelper.procesarDetalleTomasRobot(getIdUsuario(), cabDetalle, detalleTomas, getTomasOrdenadas());

      	return result;
    }
    

     // Paso5 - Procesar Excepciones (Falguera) 
    private boolean paso5(FicheroResiBean cabDetalle, DivisionResidencia div) throws ClassNotFoundException, SQLException, ParseException {
    	 return PlantillaUnificadaHelper.procesarExcepciones(getIdUsuario(),  cabDetalle);
    }

    // Paso6 - Creación del FiliaDM 
    private boolean paso6(FicheroResiBean cabDetalle, DivisionResidencia div, HttpServletResponse response) throws Exception {
		FiliaDM filiaDM = PlantillaUnificada.creaFicheroDM(getIdUsuario(), cabDetalle);
    	String nombreFicheroFiliaDM=PlantillaUnificadaHelper.generaFicheroDM(cabDetalle, filiaDM,  response);
		return true;
   	}

    // Paso7 - Creación del FiliaRX
    private boolean paso7(FicheroResiBean cabDetalle, DivisionResidencia div, HttpServletResponse response) throws Exception {
		FiliaRX filiaRX = PlantillaUnificada.creaFicheroRX(getIdUsuario(), cabDetalle, div);
		String nombreFicheroFiliaRX=PlantillaUnificadaHelper.generaFicheroRX(cabDetalle, filiaRX,  response);
		return true;

    }


	public TomasOrdenadas getTomasOrdenadas() { 		return tomasOrdenadas;	}	
	
	public void setTomasOrdenadas(TomasOrdenadas tomasOrdenadas) 
	{		
		this.tomasOrdenadas = tomasOrdenadas;
	}
	public int getRegistros() { 	return registros;	}
	public void setRegistros(int registros) { 		this.registros = registros;	}

	public List<DetallesTomasBean> getDetalleTomas() {
		return detalleTomas;
	}

	public void setDetalleTomas(List<DetallesTomasBean> detalleTomas) {
		this.detalleTomas = detalleTomas;
	//	if(this.detalleTomas!=null && this.detalleTomas.size()>0)
	//		this.registros=this.detalleTomas.size();
	}


	public int getPorcentaje() {
		return porcentaje;
	}


	public void setPorcentaje(int porcentaje) {
		this.porcentaje = porcentaje;
	}
	
    
    
    
    
}
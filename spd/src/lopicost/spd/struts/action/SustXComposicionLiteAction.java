package lopicost.spd.struts.action;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import lopicost.spd.iospd.exportdata.process.ExcelSustXComposicion;

//import lopicost.spd.struts.action.GenericAction; //revisar si es mejor poner este GenericAction
//import lopicost.spd.commons.action.GenericAction;

import lopicost.spd.persistence.BdConsejoDAO;
import lopicost.spd.persistence.FicheroResiDetalleDAO;
import lopicost.spd.persistence.RobotDAO;
import lopicost.spd.persistence.SustXComposicionDAO;
import lopicost.spd.struts.form.FicheroResiForm;
import lopicost.spd.struts.form.SustXComposicionForm;

import lopicost.spd.utils.SPDConstants;
public class SustXComposicionLiteAction extends SustXComposicionAction  {



	
}
	
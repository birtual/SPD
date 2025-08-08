package lopicost.spd.struts.action;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.xhtmlrenderer.pdf.ITextRenderer;

import lopicost.spd.persistence.FicheroResiCabeceraDAO;
import lopicost.spd.robot.bean.rd.ProduccionPaciente;
import lopicost.spd.robot.helper.InformeHelper;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.struts.form.InformeSpdForm;

public class ExportarInformeSpdAction extends GenericAction {

	public ActionForward globalLite(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		baseLite(mapping, form, request, "globalLite", true, false);
        String name="PDFInformeGlobalLite";
        return baseExecute( name,  mapping, form,  request,  response);
	}

	
	public ActionForward globalLiteAll(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		baseLite(mapping, form, request, "globalLiteAll", true, false);
        String name="PDFInformeGlobalLiteAll";
        return baseExecute( name,  mapping, form,  request,  response);
	}

	public ActionForward global(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		base(mapping, form, request, "global", true, false);
        String name="PDFInformeGlobal";
        return baseExecute( name,  mapping, form,  request,  response);
	}
	
	public ActionForward etiquetas(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		base(mapping, form, request, "etiquetas", false, false);
        String name="PDFGeneradorEtiquetas";
        return baseExecute( name,  mapping, form,  request,  response);
	}
	public ActionForward etiquetasR(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		base(mapping, form, request, "etiquetasR", false, true);
        String name="PDFGeneradorEtiquetasR";
        return baseExecute( name,  mapping, form,  request,  response);
	}
	
	public ActionForward detalle(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		baseLite(mapping, form, request, "detalle", false, false);
        String name="PDFInformeDetalle.jsp";
        return baseExecute( name,  mapping, form,  request,  response);
	}

    
	private void baseLite(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, String forwardName, boolean recetas, boolean prevaleceReceta) throws Exception {
		InformeSpdForm f= (InformeSpdForm)form;
	    FicheroResiBean cab = FicheroResiCabeceraDAO.getFicheroResiCabeceraByOid("admin", f.getOidFicheroResiCabecera());
	    f.setCabecera(cab);

	    InformeHelper helper = new InformeHelper();
	    List<ProduccionPaciente> producciones =  helper.findByIdResidenciaCarga("admin", cab, recetas, prevaleceReceta);
	       
	    f.setProducciones(producciones);
	}
	
	private void base(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, String forwardName, boolean recetas, boolean prevaleceReceta) throws Exception {
		InformeSpdForm f= (InformeSpdForm)form;
	    FicheroResiBean cab = FicheroResiCabeceraDAO.getFicheroResiCabeceraByOid("admin", f.getOidFicheroResiCabecera());
	    f.setCabecera(cab);

	    InformeHelper helper = new InformeHelper();
	    List<ProduccionPaciente> producciones =  helper.findByIdResidenciaCarga("admin", cab, recetas, prevaleceReceta);
	       
	    f.setProducciones(producciones);
	}

	private ActionForward baseExecute(String name, final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        String rutaJsp = "/spd/app/robot/informeSpd/jsp/"+name + ".jsp";
        String html = renderJspToHtml(rutaJsp, request, response);
        // 3. Generar PDF desde HTML
        byte[] pdf = generarPdfDesdeHtml(html);

        // 4. Devolverlo como archivo
        response.setContentType("application/pdf");
        //response.setHeader("Content-Disposition", "attachment; filename=\"PDFInformeGlobal.pdf\"");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + name + ".pdf\"");
        response.getOutputStream().write(pdf);
        response.getOutputStream().flush();

        return null; // No hacer forward, porque ya has enviado respuesta
    }
	
	
    private String renderJspToHtml(String jspPath, HttpServletRequest request, HttpServletResponse response) throws Exception {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(stringWriter);

        final HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(response) {
            public PrintWriter getWriter() {
                return printWriter;
            }
        };

        request.getRequestDispatcher(jspPath).include(request, responseWrapper);
        printWriter.flush();

        return stringWriter.toString();
    }

    private byte[] generarPdfDesdeHtml(String html) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.out.println("===> Cargando clase BaseFont desde:");
        System.out.println(com.lowagie.text.pdf.BaseFont.class.getProtectionDomain().getCodeSource().getLocation());
     //   System.out.println(org.xhtmlrenderer.pdf.ITextFontResolver.class.getProtectionDomain().getCodeSource().getLocation());
        
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);
        return outputStream.toByteArray();
    }
}
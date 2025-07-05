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
import lopicost.spd.robot.helper.InformeProdHelper;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.struts.form.InformeProdSpdForm;

public class ExportarInformeProdSpdAction extends GenericAction {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 1. Obtener datos desde tu servicio igual que en la acción de HTML
        InformeProdSpdForm formulario = (InformeProdSpdForm) form;

   
        final InformeProdSpdForm f= (InformeProdSpdForm)form;
    	FicheroResiBean cab = FicheroResiCabeceraDAO.getFicheroResiCabeceraByOid("admin", f.getOidFicheroResiCabecera());
    	f.setCabecera(cab);

        InformeProdHelper helper = new InformeProdHelper();
        List<ProduccionPaciente> producciones =  helper.findByIdResidenciaCarga("admin", cab);
       
        f.setProducciones(producciones);

        
        
        request.setAttribute("producciones", producciones); // igual que en el JSP

        // 2. Renderizar el JSP a HTML
        String html = renderJspToHtml("/spd/app/robot/informeSpd/jsp/PDFInformeProdGlobal.jsp", request, response);

        // 3. Generar PDF desde HTML
        byte[] pdf = generarPdfDesdeHtml(html);

        // 4. Devolverlo como archivo
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"PDFInformeProdGlobal.pdf\"");
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
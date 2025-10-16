package lopicost.spd.robot.bean;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GetProcessStatusAction extends Action {
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // Recuperar el estado del proceso desde la sesión
        HttpSession session = request.getSession();
        Integer progress = (Integer) session.getAttribute("processProgress");
        String message = (String) session.getAttribute("processMessage");

        // Configurar la respuesta como JSON
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print("{\"progress\": " + (progress != null ? progress : 0) + ", \"message\": \"" + (message != null ? message : "") + "\"}");
        out.flush();
        return null;
    }
}
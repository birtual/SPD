<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<html>
<head>
    <title>Generación de Archivos</title>
    <jsp:include page="/spd/jsp/global/head.jsp"/>
    

<body>
    <h2>Estado de la Generación de Archivos DM y RX</h2>
    
    <% String estado = (String) request.getAttribute("estado");
    String oidFicheroResiCabecera = request.getParameter("oidFicheroResiCabecera");
    String buttonId = "generateButton_" + oidFicheroResiCabecera; // ID del botón
    %>
    
    <% if ("inicio".equals(estado)) { %>
        <p>Iniciando la generación de archivos. <br>En unos segundos podrán descargarse desde esta página
          </p>
         <p align="center"> <img src="/spd/spd/img/Loading_2.gif" alt="Cargando..." /></p>
        <script>
            // Desactivar el botón en la ventana principal
            if (window.opener) {
                var btn = window.opener.document.getElementById('<%= buttonId %>');
                if (btn) {
                    btn.disabled = true;
                }
            }
        </script>    
        
        <!-- Redirigir a la URL después de 0 segundos -->
        <meta http-equiv="refresh" content="0;URL='<%=request.getContextPath()%>/FicheroResiCabeceraLite.do?parameter=generarFicherosDMyRX&oidFicheroResiCabecera=<%= oidFicheroResiCabecera %>'">
    <% } else if ("completado".equals(estado)) { %>
        <script>
            // Reactivar el botón en la ventana principal después de completar el proceso
            if (window.opener) {
                var btn = window.opener.document.getElementById('<%= buttonId %>');
                if (btn) {
                    btn.disabled = false;
                }
            }
        </script>    
        <p>Ambos archivos han sido generados exitosamente.</p>
        <p>Archivo DM: <%= (String) request.getAttribute("filePathDM") %></p>
        <p>Archivo RX: <%= (String) request.getAttribute("filePathRX") %></p>
    <% } else { %>
        <!-- Mensaje de espera y refresco -->
        <p>Esperando la generación de archivos...</p>
        <meta http-equiv="refresh" content="5">
    <% } %>
</body>

</html>
;
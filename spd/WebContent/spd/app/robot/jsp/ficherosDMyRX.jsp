


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<html>
<head>
    <title>Generación de Archivos</title>
    <%
        // Obtener atributos de request
        Boolean fileDMGenerated = (Boolean) request.getAttribute("fileDMGenerated");
        Boolean fileRXGenerated = (Boolean) request.getAttribute("fileRXGenerated");
        
        // Comprobar si es la primera carga de la página
        boolean isFirstLoad = (fileDMGenerated == null && fileRXGenerated == null);
        
        // Comprobar si ambos archivos están generados
        boolean allFilesGenerated = (fileDMGenerated != null && fileDMGenerated) && (fileRXGenerated != null && fileRXGenerated);

        // Configurar la página para refrescar si no es la primera carga y los archivos no están completos
        if (!allFilesGenerated && !isFirstLoad) { 
            out.println("<meta http-equiv='refresh' content='5'>");
        }

    %>
    <jsp:include page="/spd/jsp/global/head.jsp"/>
   
</head>

<body>
    <h2>Estado de la Generación de Archivos DM y RX</h2>
      
    <% if (isFirstLoad) { %>
        <p>Iniciando la generación de archivos. Por favor, espera...
         <img src="img/Loading_2.gif" alt="Cargando..." />
       
         </p>
         
    <% } else { %>
        <p>
        <% if (fileDMGenerated != null && fileDMGenerated) { %>
            Archivo DM generado.  <a href="DescargarArchivos.do?parameter=descarga&fileName=<%= (String) request.getAttribute("nombreFicheroFiliaDM") %>">Descargar archivo 1 DM</a><br>
        <% } else { %>
            Generando archivo DM...
        <% } %>
        </p>
        
        <p>
        <% if (fileRXGenerated != null && fileRXGenerated) { %>
            Archivo RX generado. <a href="DescargarArchivos.do?parameter=descarga&fileName=<%= (String) request.getAttribute("nombreFicheroFiliaRX") %>">Descargar archivo 2 RX</a>
        <% } else if (fileDMGenerated != null && fileDMGenerated) { %>
            Generando archivo RX...
        <% } else { %>
            En espera para generar el archivo RX...
        <% } %>
        </p>
        
        <% if (allFilesGenerated) { %>
            <p>Ambos archivos han sido generados exitosamente.</p>

			
	
			        
        <p class="botons">
			<input type="button" onclick="javascript:cerrar()" value="Cerrar"/>
		</p>	


           	<script>
                // Reactivar el botón en la ventana principal al completar el proceso
                if (window.opener || window.close) {
                    window.opener.enableButton();
                }
           	</script>
        <% } %>
    <% } %>
</body>
</html>
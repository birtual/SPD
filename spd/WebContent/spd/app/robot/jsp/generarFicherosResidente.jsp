<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<html>
<head>
    <title>Generación de Archivos</title>
    <jsp:include page="/spd/jsp/global/head.jsp"/>
    
<script language="javaScript" src="/spd/spd/app/ficheroResiLite/js/ficheroResiCabeceraLite.js"></script>

<body>
<bean:define id="formulari" name="FicheroResiForm" type="lopicost.spd.struts.form.FicheroResiForm" />


    <h2>Generación de Archivos DM y RX</h2>
    
    <% 
    String estado = (String) request.getAttribute("estado");
    String oidFicheroResiCabecera = request.getParameter("oidFicheroResiCabecera");
    String oidPaciente = request.getParameter("oidPaciente");
    
     String buttonId = "generateButton_" + oidFicheroResiCabecera; // ID del botón
    %>
   
    <% if ("confirma".equals(estado)) { %>

        <!-- mostramos mensajes si existen -->


<fieldset>
    <!-- Iterar sobre los elementos de avisos -->
    <logic:iterate name="avisos" id="avisosItem" type="java.lang.String" offset="0">
        <!-- Para el primer elemento (índice 0), mostrarlo en negro -->
        <logic:equal name="avisosItem" value="${avisos[0]}">
            <span style="color: black;">
            	<h4><bean:write name="avisosItem" /></h4>
            </span>
        </logic:equal>
        
        <!-- Para los demás elementos, mostrarlos en rojo -->
        <logic:notEqual name="avisosItem" value="${avisos[0]}">
            <span style="color: red; font-size: 18px;"> 
                <h3><bean:write name="avisosItem" /></h3>
            </span>
        </logic:notEqual>
        <br />
    </logic:iterate>
   <input type="button" id="generateButton_<%=oidFicheroResiCabecera%>" class="negro" value="Confirmar" onclick="javascript:generarFicherosResidente('<bean:write name="formulari" property="oidPaciente" />', '<bean:write name="formulari" property="oidFicheroResiCabecera" />')" />
	<input type="button" onclick="javascript:cerrar()" value="Cancelar"/>

		
</fieldset>



         <script>
            // Desactivar el botón en la ventana principal
            if (window.opener) {
                var btn = window.opener.document.getElementById('<%= buttonId %>');
                if (btn) {
                    btn.disabled = true;
                }
            }
        </script>    
    <% } else if ("inicio".equals(estado)) { %>
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
 <% } else if ("inicioResidente".equals(estado)) { %>
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
        <meta http-equiv="refresh" content="0;URL='<%=request.getContextPath()%>/FicheroResiCabeceraLite.do?parameter=generarFicherosDMyRX&oidFicheroResiCabecera=<%= oidFicheroResiCabecera %>&oidPaciente=<%= oidPaciente %>'">
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
        
        <p class="botons">
			<input type="button" onclick="javascript:cerrar()" value="Cerrar"/>
		</p>	
		
		
		
    <% } else { %>
        <!-- Mensaje de espera y refresco -->
        <p>Esperando la generación de archivos...</p>
        <meta http-equiv="refresh" content="5">
    <% } %>
    

		
</body>

</html>

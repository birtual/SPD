<%@ page language="java" %>
<%@ page import="java.util.*" %>


<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 	

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">
	<script language="javaScript" src="/spd/spd/app/ficheroResiLite/js/ficheroResiLite.js"></script>

<head>
     <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
 <style>
        /* Estilos para los iconos de acción */
        .action-icon {
            font-size: 20px;
            color: #0000a0; /* Color del icono */
            cursor: pointer;
        }

        .selected {
            background-color: #ADD8E6;
        }
    </style>
    
<jsp:include page="/spd/jsp/global/head.jsp"/>
	<link rel="stylesheet" href="/spd/spd/css/ficheroResiLite/base.css" media="screen" />
<title>Gestión carga ficheros Robot</title>
</head>

<bean:define id="formulari" name="FicheroResiForm" type="lopicost.spd.struts.form.FicheroResiForm" />
<body>
<html:form action="/FicheroResiDetalleLite.do" method="post">	
<html:errors/>

 
<div id="container">
     <html:hidden property="parameter" value="list"/>
     <html:hidden property="ACTIONTODO" value="list"/>
     <html:hidden property="oidFicheroResiCabecera" />
     <html:hidden property="oidFicheroResiDetalle" />
     <html:hidden property="procesoValido" />
     <html:hidden property="idProceso" />
     <html:hidden property="filtroProceso" />
     <html:hidden property="idDivisionResidencia" />
     <html:hidden property="campoOrder" />
     <html:hidden property="excluirNoPintar" />
     <html:hidden property="idProcessIospd" />
     <html:hidden property="mode" />         
     <html:hidden property="oidDivisionResidencia" />
     <html:hidden property="oidDivisionResidenciaFiltro" />
     <html:hidden property="filtroCheckedMostrarGeneradosSeq" />
     

		 <fieldset> 
		 	   	<!-- mostramos mensajes y errores, si existen -->
				<logic:notEmpty name="formulari" property="errors">
					<font color="red"><ul>
						<u>Mensaje:</u>
							<logic:iterate id="error" name="formulari" property="errors" type="java.lang.String">
									<li><bean:write name="error"/></li>
							</logic:iterate>
						</ul></font>
				</logic:notEmpty>
				<div>
						<p>Residencia: <%= formulari.getIdDivisionResidencia() %>	-
						<!--String tipoVista = "origen/"+formulari.getIdProcessIospd()+formulari.getMode()+".jsp"; -->
						<% 
						
						String tipoVista = "origen/listadoDetalle.jsp"; 
						String encodedTipoVista = java.net.URLEncoder.encode(tipoVista, "UTF-8");
						out.print(formulari.getIdProcessIospd()+formulari.getMode());
						%></p> 
						<p>Proceso: <%= formulari.getFiltroProceso() %>	</p> 
						<logic:notEmpty name="FicheroResiForm" property="listaFechaFichero">
						<p>Fecha proceso:	 <%= formulari.getListaFechaFichero().get(0).toString() %> </p><BR>
						</logic:notEmpty>				
				</div>
				<div>
						<!--  <input type="button" class="btn primary" value="Actualizar previsión" onclick="javascript:enviarAPrevision()" /> -->
						<!--  <input type="button" class="btn primary" value="Generar ficheros robot" onclick="javascript:generarFicherosRobot()" /> -->
						<input type="button" class="btn primary" value="Limpiar filtros listado" onclick="javascript:limpiarFiltrosResi();" />
						<input type="button" class="btn primary" value="Excluir NoPintar" onclick="javascript:visualizarActivos();" />
						<input type="button" class="btn primary" value="Exportar Excel"  onclick="javascript:exportExcel();"/>
						<!-- input type="button" class="btn primary" value="Exportar Filas sin sustitución "  onclick="javascript:exportFilasSinSust();"/>-->
						<input type="button" class="btn primary" value="Exportar Filas con mensajes "  onclick="javascript:exportFilasConInfo();"/>
						
						<!--  Para validaciones masivas -->
						<logic:equal property="free1" name="FicheroResiForm" value="inicio">
								<input type="button" class="azul" onclick="javascript:confirmadoMasiva('<bean:write name="FicheroResiForm" property="idDivisionResidencia" />', '<bean:write name="FicheroResiForm" property="idProceso" />')" value="Confirmación masiva"  />
						</logic:equal>

								 
					<!-- 	
						<logic:equal property="mode" name="FicheroResiForm" value="">
							<input type="button" class="btn primary" value="Versión reducida" onclick="javascript:modoVisual('Reducido');" />	
						</logic:equal>
						<logic:equal property="mode" name="FicheroResiForm" value="Reducido">
							<input type="button" class="btn primary" value="Versión ampliada" onclick="javascript:modoVisual('');" />	
						</logic:equal>
 					-->						
				</div> 	
				<div>
						<!-- input type="button" value="Ordenar por CIP" onclick="javascript:ordenarPor('CIP');" /> -->
						<!-- <input type="button" value="Ordenar por mensajes" onclick="javascript:ordenarPor('mensajes');" /> -->
						
						<input type="checkbox" name="filtroValidacionDatos" value="true" ${formulari.filtroValidacionDatos ? 'checked' : ''} onchange="reloadCheckbox('filtroValidacionDatos')" />
						Medicamentos a validar
				</div>
				<div>
						 	 
						<input type="checkbox" name="filtroMostrarGeneradosSeq"   ${formulari.filtroCheckedMostrarGeneradosSeq ? 'checked' : ''}  onclick="checkValue()" />
						Mostrar registros Secuencias Guide
				</div>
				<div>
						<input type="checkbox" name="filtroPrincipioActivo" value="true" ${formulari.filtroPrincipioActivo ? 'checked' : ''} onchange="reloadCheckbox('filtroPrincipioActivo')" />
						Medicamentos control extra (margen estrecho)
				</div>
				<div>
					<input type="button" class="btn primary" value="Volver" onclick="javascript:goIndexCargas()" />
					<!-- input type="button" class="btn primary" value="Inicio" onclick="javascript:goInicio();" /> -->
					<input type="button" class="btn primary" value="Salir" onclick="javascript:salir();" />
				</div>
				<jsp:include page="<%= tipoVista %>" flush="true"/>
				
			</fieldset>
		</div>
	</html:form>
</body>
</html>
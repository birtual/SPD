<%@ page language="java" %>
<%@ page import="java.util.*" %>


<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 	

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">
	<script language="javaScript" src="/spd/spd/app/ficheroResiLiteHst/js/ficheroResiLiteHst.js"></script>

<head>
     <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
 <style>
        /* Estilos para los iconos de acción */
        .action-icon {
            font-size: 20px;
            color: #0000a0; /* Color del ícono */
            cursor: pointer;
        }

        .selected {
            background-color: #ADD8E6;
        }
    </style>
    
<jsp:include page="/spd/jsp/global/head.jsp"/>
	<link rel="stylesheet" href="/spd/spd/css/ficheroResiLite/base.css" media="screen" />
<title>HISTORICO de producciones cargadas por ficheros</title>
</head>

<bean:define id="formulari" name="FicheroResiForm" type="lopicost.spd.struts.form.FicheroResiForm" />
<body>
<html:form action="/FicheroResiDetalleLiteHst.do" method="post">	
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
						
						String tipoVista = "origen/listadoDetalleHst.jsp"; 
						String encodedTipoVista = java.net.URLEncoder.encode(tipoVista, "UTF-8");
						out.print(formulari.getIdProcessIospd()+formulari.getMode());
						%></p> 
						<p>Proceso: <%= formulari.getFiltroProceso() %>	</p> 
						<logic:notEmpty name="FicheroResiForm" property="listaFechaFichero">
						<p>Fecha proceso:	 <%= formulari.getListaFechaFichero().get(0).toString() %> </p><BR>
						</logic:notEmpty>				
				</div>
				<div>
						<input type="button" class="btn primary" value="Limpiar filtros listado" onclick="javascript:limpiarFiltrosResi();" />
						<input type="button" class="btn primary" value="Exportar Excel"  onclick="javascript:exportExcel();"/>
						
	
				</div> 	
				<div>
						<!-- input type="button" value="Ordenar por CIP" onclick="javascript:ordenarPor('CIP');" /> -->
						<!-- <input type="button" value="Ordenar por mensajes" onclick="javascript:ordenarPor('mensajes');" /> -->
						<input type="button" class="btn primary" value="Excluir NoPintar" onclick="javascript:visualizarActivos();" />
							 
						<logic:equal name="FicheroResiForm" property="filtroCheckedMostrarGeneradosSeq" value="false">
							<input type="checkbox" name="filtroMostrarGeneradosSeq"   onclick="checkValue()" />Mostrar registros Secuencias Guide
						</logic:equal>
						<logic:equal name="FicheroResiForm" property="filtroCheckedMostrarGeneradosSeq" value="true">
							<input type="checkbox" name="filtroMostrarGeneradosSeq" checked onclick="checkValue()" />Mostrar registros Secuencias Guide
						</logic:equal>
				</div>
				<div>
					<input type="button" class="btn primary" value="Volver" onclick="javascript:goHistorico()" />
					<input type="button" class="btn primary" value="Inicio" onclick="javascript:goInicio();" />

				</div>
				<jsp:include page="<%= tipoVista %>" flush="true"/>
				
			</fieldset>
		</div>
	</html:form>
</body>
</html>
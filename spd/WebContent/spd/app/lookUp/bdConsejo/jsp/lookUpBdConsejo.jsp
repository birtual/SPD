<%@ page language="java" %>
<%@ page import="java.util.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 	



<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">


   <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<jsp:include page="/spd/jsp/global/head.jsp"/>
<head>
<title>Selección de medicamento</title>
   
<bean:define id="formulari" name="BdConsejoForm" type="lopicost.spd.struts.form.BdConsejoForm" />

<script>
$(document).ready(function () {
    // Variable para almacenar el valor seleccionado
    var valorSeleccionado = "";

    // Obtener el valor de filtroGtVm del formulario (puedes obtenerlo de tu lógica del servidor)
    var valorInicial = $("#filtroNomGtVm").val();

    // Establecer el valor inicial en el campo de entrada
    $("#filtroNomGtVm").val(valorInicial);

    $("#filtroNomGtVm").autocomplete({
        source: function (request, response) {
            $.ajax({
                url: "/spd/Autocompletar.do",
                dataType: 'json',
                data: {
                    query: request.term,
                    campo: "filtroNomGtVm"
                },
                success: function (data) {
                    var mappedData = data.map(function (item) {
                        return {
                            label: item.text,
                            value: item.text
                        };
                    });
                    response(mappedData);
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    console.error("Error en la llamada Ajax:", textStatus, errorThrown);
                    response([]);
                }
            });
        },
        minLength: 3,
        select: function (event, ui) {
            // Al seleccionar un elemento, establece el valor del campo oculto
            valorSeleccionado = ui.item.value;
            //alert("Elemento seleccionado: " + ui.item.value);
            // Log para verificar la selección
            console.log("Elemento seleccionado:", valorSeleccionado);

            // Establece el valor del campo de entrada
            $("#filtroNomGtVm").val(valorSeleccionado);

            // Evitar el comportamiento predeterminado de la selección del autocompletar
            return false;
        }
    });

    // Maneja el envío del formulario
    $("#formulari").submit(function () {
        // Almacena el valor actual de filtroGtVm
        var valorFiltroGtVm = valorSeleccionado;
        console.log("Valor de filtroNomGtVm al enviar el formulario:", valorFiltroGtVm);

        // Puedes agregar lógica adicional aquí antes de enviar el formulario

        // Devuelve true para permitir que el formulario se envíe
        return true;
    });
});
    
</script>



</head>



<%
String fieldName1="";
if (request.getParameter("fieldName1")!=null)
	fieldName1 = (String) request.getParameter("fieldName1");
%>


	<script type="text/javascript">	
		function updateList()
		{
			document.forms[0].submit();
		}
		
		function buscaMedicamento(cn, nombreConsejo, nomGtVmp, nomGtVmpp) {

			if(opener.document.forms[0].spdCn) 
				opener.document.forms[0].spdCn.value=cn;
			if(opener.document.forms[0].cnOk) 
				opener.document.forms[0].cnOk.value=cn;
			if(opener.document.forms[0].cnOkSustXResi)
				opener.document.forms[0].cnOkSustXResi.value=cn;
			if(opener.document.forms[0].cn6)
				opener.document.forms[0].cn6.value=cn;
		//	alert('2 opener.document.forms[0].fieldName1.value ' +opener.document.forms[0].fieldName1.value);
			if(opener.document.forms[0].nombreConsejo)
				opener.document.forms[0].elements['nombreConsejo'].value=nombreConsejo;
			if(opener.document.forms[0].nombreCorto)
				opener.document.forms[0].elements['nombreCorto'].value=nombreConsejo;
			if(opener.document.forms[0].spdNombreBolsa) 
				opener.document.forms[0].elements['spdNombreBolsa'].value=nomGtVmp;
			if(opener.document.forms[0].spdNombreMedicamento) 
				opener.document.forms[0].elements['spdNombreMedicamento'].value=nombreConsejo;
			if(opener.document.forms[0].filtroNomGtVmpp) 
				opener.document.forms[0].elements['filtroNomGtVmpp'].value=nomGtVmpp;
			//alert('2 opener.document.forms[0].fieldName1.value ' +opener.document.forms[0].spdNombreMedicamento.value);
			
			
			
			
			
<%
		if(request.getParameter("CallBack")!=null && !request.getParameter("CallBack").equals("")&& !request.getParameter("CallBack").equals("null"))
		{
%>	

				opener.<%=request.getParameter("CallBack")%>(id, name);
<%
		}
%>
			window.close();
		}		
		
		function reloadGtVm()
		{
			var f = document.BdConsejoForm;
			f.filtroCodGtVmp.value="";
			f.filtroCodGtVmpp.value="";
			f.submit();
		}	
		
		function reloadGtVmp()
		{
			var f = document.BdConsejoForm;
			f.filtroCodGtVmpp.value="";
			f.submit();
		}	

		function reloadLab()
		{
			var f = document.BdConsejoForm;
			//			alert(f.filtroCodiLaboratorio.value);
//			alert(f.filtroCodiLaboratorio2.value);
			f.filtroCodiLaboratorio.value=f.filtroCodiLaboratorio2.value;
			//	alert(f.filtroCodiLaboratorio.value);
			f.submit();
		}			
		
		//función de carga del lookUp
		function doLookUpLabsBdConsejo(){				
			var loc = '/spd/LookUpLabsBdConsejo.do?parameter=initLabs&'+ 						//url de llamanda				
				'CallBackID=filtroCodiLaboratorio&'+			  			//Nombre del campo para el valor Id
				'CallBackNAME=nombreLab';			   		//Nombre del campo para el valor descriptivo
				//'CallBackType=nombreLab';			   		//Nombre del campo para el valor descriptivo
		
			//Importante que se realice en ventana nueva!!!!
			window.open(loc, 'LookUpLabsBdConsejo', 'dependent=yes,height=500,width=800,top=50,left=50,resizable=yes,scrollbars=yes' );
		}	
		
		
	</script>
</head>
<body id="lookUpBdConsejo" class="popup">
<html:errors />
	<div id="caixa">
		<h2>Medicamentos</h2>
		<html:form action="/LookUpBdConsejo.do"  method="post" styleId="${formulari}">
		<html:hidden property="fieldName1"/>
		<html:hidden property="numpages"/>
		<html:hidden property="currpage"/>
		<html:hidden property="parameter" value="init"/>
			

		<input type="hidden" name="CallBack" 		value="<%=request.getParameter("CallBack")%>">
		<input type="hidden" name="CallBackID" 		value="<%=request.getParameter("CallBackID")%>">
		<input type="hidden" name="CallBackNAME" 	value="<%=request.getParameter("CallBackNAME")%>">
			
			

        
        
        
		<div id="contingut">
			<fieldset>
			<div id="datos">
			<!-- contingut -->
				<div id="formdata">
					<div>
						<!-- cnConsejo -->
						<p>
							<label for="cnConsejo" accesskey="n">cnConsejo</label>
							<html:text property="cnConsejo"/>
						</p>
						<!-- MEDICAMENTO -->
						<p>
							<label for="nombreCortoOK" accesskey="e">nombreCortoOK</label>
							<html:text property="nombreCortoOK" />
						</p>
	
				       <p class="select_corto">
				       <label for="listaGtVm" accesskey="e">Grupo VM (Principio Activo)</label>
				 		    <input type="text" id="filtroNomGtVm" value="<%=formulari.getFiltroNomGtVm()%>" onchange="reloadGtVm()"  name="filtroNomGtVm" style="width: 300px;"> 
				       </p>
       
						<p>
							<label for="listaGtVmp" accesskey="e">Grupo VMP ( +  dosis y forma)</label>
							<html:select property="filtroCodGtVmp" value="<%= formulari.getFiltroCodGtVmp() %>" onchange="reloadGtVmp()" styleClass="select_corto" >
							
					   	 		<html:option value="">Todos</html:option>
			   					<html:optionsCollection name="formulari" property="listaGtVmp" label="nomGtVmp" value="codGtVmp" />
							</html:select>
						</p>	
						


						<p class="select_corto">
							<label for="listaGtVmpp" accesskey="e">Grupo VMPP (+ presentación)</label>
							<html:select property="filtroCodGtVmpp"  value="<%= formulari.getFiltroCodGtVmpp() %>" onchange="submit()" styleClass="select_corto"> 
					   	 		<html:option value="">Todos</html:option>
			   					<html:optionsCollection name="formulari" property="listaGtVmpp" label="nomGtVmpp" value="codGtVmpp" />
							</html:select>
						</p>		
						<!-- p>
							<label for="listaLabs" accesskey="e">Laboratorio:</label>
							<html:text name="formulari" readonly="true"  property="filtroCodiLaboratorio" styleClass="filtroCodiLaboratorio"/> - 
							<html:select property="filtroCodiLaboratorio2"  value="<%= formulari.getFiltroCodiLaboratorio() %>" onchange="reloadLab()" styleClass="select_corto"> 
					   	 		<html:option value="">Todos</html:option>
			   					<html:optionsCollection name="formulari" property="listaLabs" label="nombreLaboratorio" value="codigoLaboratorio" />
							</html:select>
							<a href="#" onclick="javascript:doLookUpLabsBdConsejo();">... Buscar por nombre</a>
						</p-->
					</div>
				</div>
			</div>
			<div class="botons">
				<p>
					<input type="button" onclick="window.close()" value="Volver"/>
					<input type="button" onclick="javascript:updateList()" value="Filtrar"/>					
					
								
				</p>
			</div>					

			<table class="graella">
				<thead>
					<th>CN</th>
					<th>nombre medicamento</th>
					<th>nombreLab</th>
					<th>GtVm (Principio Activo)</th>
					<th>GtVmp (Base Conjunto homogéneo)</th>
					<th>GtVmpp (Conjunto homogéneo)</th>
				</thead>

		<logic:present name="formulari" property="listaBdConsejo">
			<logic:iterate id="bdConsejo" name="formulari" property="listaBdConsejo" type="lopicost.spd.model.BdConsejo" indexId="position">
				<tbody>
					
						<!-- 					
					<tr>
						<td>
							<a href="#" onclick="javascript:buscaMedicamento('<bean:write name="bdConsejo" property="cnConsejo"/>','<bean:write name="bdConsejo" property="nombreConsejo"/>','<bean:write name="bdConsejo" property="nomGtVmp"/>');"><bean:write name="bdConsejo" property="cnConsejo"/></a>
						</td>
						<td>
							<a href="#" onclick="javascript:buscaMedicamento('<bean:write name="bdConsejo" property="cnConsejo"/>','<bean:write name="bdConsejo" property="nombreConsejo"/>');"><bean:write name="bdConsejo" property="nombreConsejo"/></a>
						</td>
						<td>
							<a href="#" onclick="javascript:buscaMedicamento('<bean:write name="bdConsejo" property="cnConsejo"/>','<bean:write name="bdConsejo" property="nombreConsejo"/>');"><bean:write name="bdConsejo" property="nombreLaboratorio"/></a>
						</td>
						<td>
							<a href="#" onclick="javascript:buscaMedicamento('<bean:write name="bdConsejo" property="cnConsejo"/>','<bean:write name="bdConsejo" property="nombreConsejo"/>');"><bean:write name="bdConsejo" property="nomGtVm"/></a>
						</td>
						<td>
							<a href="#" onclick="javascript:buscaMedicamento('<bean:write name="bdConsejo" property="cnConsejo"/>','<bean:write name="bdConsejo" property="nombreConsejo"/>');"><bean:write name="bdConsejo" property="nomGtVmp"/></a>
						</td>
						<td>
							<a href="#" onclick="javascript:buscaMedicamento('<bean:write name="bdConsejo" property="cnConsejo"/>','<bean:write name="bdConsejo" property="nombreConsejo"/>');"><bean:write name="bdConsejo" property="nomGtVmpp"/></a>
						</td>

					</tr>	
			-->
					<tr>
 						<td>
							<a href="#" onclick="javascript:buscaMedicamento('<bean:write name="bdConsejo" property="cnConsejo"/>','<bean:write name="bdConsejo" property="nombreConsejo"/>','<bean:write name="bdConsejo" property="nomGtVmp"/>');"><bean:write name="bdConsejo" property="cnConsejo"/></a>
						</td>
						<td>	
							<bean:write name="bdConsejo" property="nombreConsejo"/>
						</td>
						<td>
							<bean:write name="bdConsejo" property="nombreLaboratorio"/>
						</td>
						<td>
							<bean:write name="bdConsejo" property="nomGtVm"/>
						</td>
						<td>
							<bean:write name="bdConsejo" property="nomGtVmp"/>
						</td>
						<td>
							<bean:write name="bdConsejo" property="nomGtVmpp"/>
						</td>

					</tr>	
				</logic:iterate>	
			</logic:present> 
			<logic:notPresent name="formulari" property="listaBdConsejo">
					<tr>
						<td colspan="3" class="nota"><bean:message key="info.nolist"/></td>
					</tr>	
			</logic:notPresent>									
											
							
				</tbody>
			</table>	
		</fieldset>
	</div>			
	<div>
		<tr>
			<td width="100%">
				<logic:greaterThan name="formulari" property="numpages" value="1">
				<table border="0" width="100%">
					<tr>
						<td align="center">
						<logic:greaterThan name="formulari" property="currpage" value="0">
							<a href="javascript:pageDown();" ><<
							</a>
						</logic:greaterThan>
								&nbsp;<input type="text" name="newPage" value="<%= formulari.getCurrpage()+1 %>" size="1" maxlength="4" onkeypress="gotoPage(this.value,<%=formulari.getNumpages()%>);">/<%= formulari.getNumpages() %>&nbsp;
						<logic:lessThan name="formulari" property="currpage" value="<%= String.valueOf(formulari.getNumpages() -1)%>">
						<a href="javascript:pageUp();" >>></a>
						</logic:lessThan>
						</td>
					</tr>
				</table>
				</logic:greaterThan>
			</td>
		</tr>					
	</div>
					
					
</div>

</html:form>
		
			

</body>
</html>
<%@ page language="java" %>
<%@ page import="java.util.*" %>


<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 	

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">

<head>
<jsp:include page="/spd/jsp/global/head.jsp"/>
<title>Base para realizar sustituciones - Listado</title>
</head>


<bean:define id="formulari" name="SustXComposicionForm" type="lopicost.spd.struts.form.SustXComposicionForm" />





<script type="text/javascript">
	
	// función para borrar un registro
	function borrar(oidSustXComposicion)
	{
		var f = document.SustXComposicionForm; // se obtiene el formulario
		f.parameter.value='borrar'; // se setea el valor del parámetro 'parameter'
		f.ACTIONTODO.value='CONFIRMAR'; // se setea el valor de 'ACTIONTODO'
		f.oidSustXComposicion.value= oidSustXComposicion; // se setea el valor del ID del registro a borrar
		f.submit(); // se envía el formulario
	}
	
	// función para editar un registro
	function editar(oidSustXComposicion)
	{
		var f = document.SustXComposicionForm; // se obtiene el formulario
		f.parameter.value='editar'; // se setea el valor del parámetro 'parameter'
		f.ACTIONTODO.value='EDITA'; // se setea el valor de 'ACTIONTODO'
		f.oidSustXComposicion.value= oidSustXComposicion; // se setea el valor del ID del registro a editar
		f.submit(); // se envía el formulario
	}
	
	// función para buscar registros
	function buscar()
	{
		var f = document.SustXComposicionForm; // se obtiene el formulario
		f.parameter.value='list'; // se setea el valor del parámetro 'parameter'
		f.submit();	// se envía el formulario
	}
	
	// función para crear un nuevo registro
	function nuevo()
	{
		var f = document.SustXComposicionForm; // se obtiene el formulario
		f.parameter.value='nuevo'; // se setea el valor del parámetro 'parameter'
		f.ACTIONTODO.value='NUEVO'; // se setea el valor de 'ACTIONTODO'
		f.submit(); // se envía el formulario
	}	
	
	// función para cargar el lookUp de consejos
	function doLookUpBdConsejo(){				
		var loc = '/spd/LookUpBdConsejo.do?parameter=init&'+ // se construye la URL para la llamada AJAX
			'CallBackID=cnOk&'+ // se setea el nombre del campo para el valor ID
			'CallBackNAME=nombreConsejo'; // se setea el nombre del campo para el valor descriptivo
		
		// se abre una nueva ventana para realizar la llamada AJAX
		window.open(loc, 'LookUpBdConsejo', 'dependent=yes,height=500,width=800,top=50,left=50,resizable=yes,scrollbars=yes' );
	}	
	
	// función para cargar el lookUp de laboratorios
	function doLookUpLabsBdConsejo(){				
		var loc = '/spd/LookUpLabsBdConsejo.do?parameter=initLabs&'+ // se construye la URL para la llamada AJAX
			'CallBackID=filtroCodiLaboratorio&'+ // se setea el nombre del campo para el valor ID
			'CallBackNAME=nombreLaboratorio'; // se setea el nombre del campo para el valor descriptivo
		
		// se abre una nueva ventana para realizar la llamada AJAX
		window.open(loc, 'LookUpLabsBdConsejo', 'dependent=yes,height=500,width=800,top=50,left=50,resizable=yes ' );
	}	
		function exportCSV()
		{

			var f = document.SustXComposicionForm;
			var loc = '/spd/Iospd/SustXConjExportData.do?parameter=specificPerform';			   	
	
		//Importante que se realice en ventana nueva!!!!
		window.open(loc, 'SustXComposicionExportData', 'dependent=yes,height=500,width=800,top=50,left=50,resizable=yes,scrollbars=yes' );
			
		}	
		
		function exportExcel2()
		{
			var f = document.SustXComposicionForm;
			f.parameter.value='exportExcel';
			f.submit();   	
			f.parameter.value='list';  //volvemos a dejar list para que no se generen posteriormente al hacer submit
		}	
		
		function exportExcel() {
		    var f = document.SustXComposicionForm;
		    f.parameter.value = 'exportExcel';
		    $.ajax({
		        url: '/spd/SustXComposicion.do',
		        type: 'POST',
		        data: $(f).serialize(),
		        success: function(response) {
		            // Manejar respuesta exitosa aquí
		            console.log('Exportación exitosa');
		        },
		        error: function(xhr, status, error) {
		            // Manejar errores aquí
		            console.error('Error al exportar', error);
		        }
		    });
		}
		
		
	</script>
	<script>
function buscarAJAX() {
  var xhr = new XMLHttpRequest(); // crea una instancia del objeto XMLHttpRequest
  xhr.onreadystatechange = function() { // define la función de devolución de llamada
    if (this.readyState === 4 && this.status === 200) { // verifica que la solicitud haya sido exitosa
      document.getElementById("tablaSustituciones").innerHTML = this.responseText; // actualiza la tabla con la respuesta
    }
  };
  xhr.open("GET", "/ruta/a/tu/controlador?parameter=list", true); // define la URL del controlador y el método HTTP
  xhr.send(); // envía la solicitud AJAX
}
</script>




<body id="general">

<html:form action="/SustXComposicionLite.do" method="post"  >	
<html:errors/>

<div id="contingut">
     <html:hidden property="parameter" value="list"/>
     <html:hidden property="ACTIONTODO" value="list"/>
     <html:hidden property="codGtVmpp" />
     <!-- html:hidden property="codiLab" / -->
     <!-- html:hidden property="nombreLab" / -->
     <html:hidden property="oidSustXComposicion" />
     <html:hidden property="filtroCheckedLabsSoloAsignados" />
     <html:hidden property="filtroCheckedComposicionSinLabs" />
     
     	<% String numPages = formulari.getNumpages()+""; %>
     	<% String currpage = formulari.getCurrpage()+""; %>
     <html:hidden property="numpages" value="<%= numPages %>"/>
	 <html:hidden property="currpage" value="<%= currpage %>"/>
	
   	<!-- mostramos mensajes y errores, si existen -->
	<logic:notEmpty name="SustXComposicionForm" property="errors">
		<font color="red"><ul>
			<u>Mensaje:</u>
				<logic:iterate id="error" name="SustXComposicionForm" property="errors" type="java.lang.String">
						<li><bean:write name="error"/></li>
				</logic:iterate>
			</ul></font>
	</logic:notEmpty>
	<div id="tablaSustituciones">
  <table>
   <button onclick="buscarAJAX()">Buscar</button>
  </table>
</div>


<fieldset>
	<table>
		<th>
			<div>Robot
				<logic:notEmpty name="SustXComposicionForm" property="listaRobots">
					<html:select property="filtroRobot" value="<%= formulari.getFiltroRobot() %>" onchange="submit()">
						<html:option value="">Todos</html:option>
						<html:optionsCollection name="SustXComposicionForm" property="listaRobots" label="nombreRobot" value="idRobot" />
					</html:select>
				</logic:notEmpty>
			</div>
			<div>&nbsp;</div>
			<div>Texto a buscar
				<html:text property="filtroTextoABuscar" alt="Texto a buscar" title="Texto a buscar" />
			</div>
			<div>Búsqueda por medicamento
				<html:text name="SustXComposicionForm" property="cnOk" styleClass="cnOk" />-<html:text name="SustXComposicionForm" property="nombreConsejo" styleClass="nombreConsejo" />
				<a href="#" onclick="javascript:doLookUpBdConsejo();">Buscar CN</a>
			</div>
			<div>
				<logic:notEmpty name="SustXComposicionForm" property="listaGtVm">
					Grupo VM
					<html:select property="filtroGtVm" value="<%= formulari.getFiltroCodGtVm() %>" onchange="submit()">
						<html:option value="">Todos</html:option>
						<html:optionsCollection name="SustXComposicionForm" property="listaGtVm" label="nomGtVm" value="codGtVm" />
					</html:select>
					(Principio activo)
				</logic:notEmpty>
			</div>
			<div>
				<logic:notEmpty name="SustXComposicionForm" property="listaGtVmp">
					Grupo VMP
					<html:select property="filtroGtVmp" value="<%= formulari.getFiltroCodGtVmp() %>" onchange="submit()">
						<html:option value="">Todos</html:option>
						<html:optionsCollection name="SustXComposicionForm" property="listaGtVmp" label="nomGtVmp" value="codGtVmp" />
					</html:select>
					(Principio activo, dosis y forma farmacéutica)
				</logic:notEmpty>
			</div>
			<div>
				<logic:notEmpty name="SustXComposicionForm" property="listaGtVmpp">
					Grupo VMPP
					<html:select property="filtroGtVmpp" value="<%= formulari.getFiltroCodGtVmpp() %>" onchange="submit()">
						<html:option value="">Todos</html:option>
						<html:optionsCollection name="SustXComposicionForm" property="listaGtVmpp" label="nomGtVmpp" value="codGtVmpp" />
					</html:select>
					(Igual que Conjunto homogéneo - Principio activo, dosis, forma farmacéutica y número de unidades de dosificación)
				</logic:notEmpty>
			</div>
		</th>
		</tr>
	</table>
</fieldset>
     <p >
     <input type="button" onclick="javascript:buscar();" value="Buscar"/> 
  	 <input type="button" onclick="javascript:nuevo();" value="Nuevo"/> 


</p>
    <!-- <p class="botonCerrar">
		 	<input type="button" onclick="javascript:window.close();" value="Cerrar"/> 
	</p>--> 
<table border="1">

 
 		<c:if test="${not empty SustXComposicionForm.listaSustXComposicion}">
  <table>
    <thead>
      <tr>
        <th>ID Robot</th>
        <th>Nombre GT VMPP</th>
        <th>Rentabilidad</th>
        <th>Ponderación</th>
        <th>Nota</th>
        <th>Nombre Laboratorio</th>
        <th>Comentarios</th>
        <th>Acción</th>
        <th>CN6</th>
        <th>CN7</th>
        <th>Nombre Medicamento</th>
        <th>Sustituible</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach var="data" items="${SustXComposicionForm.listaSustXComposicion}" varStatus="status">
        <tr>
          <td><c:out value="${data.idRobot}"/></td>
          <td><c:out value="${data.nomGtVmpp}"/></td>
          <td><c:out value="${data.rentabilidad}"/></td>
          <td><c:out value="${data.ponderacion}"/></td>
          <td><c:out value="${data.nota}"/></td>
          <td><c:out value="${data.nombreLab}"/></td>
          <td><c:out value="${data.comentarios}"/></td>
          <td>
            <c:if test="${data.oidSustXComposicion ne 0}">
              <button class="borrar" data-oid="${data.oidSustXComposicion}">Borrar</button>
            </c:if>
            <c:if test="${data.oidSustXComposicion ne 0}">
              <button class="editar" data-oid="${data.oidSustXComposicion}">Editar</button>
            </c:if>
          </td>
          <td><c:out value="${data.cn6}"/></td>
          <td><c:out value="${data.cn7}"/></td>
          <td><c:out value="${data.nombreMedicamento}"/></td>
          <td><c:out value="${data.sustituible}"/></td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
</c:if>
   	
    
</table>

		  	<input type="button" value="<bean:message key="new window"/>"  onclick="javascript:exportExcel();"/>
		  	<input type="button" value="<bean:message key="Export.SustXConj.submit"/>" 	="javascript:document.forms[0].action='/spd/Iospd/ExportData.do?parameter=specificPerform'; document.forms[0].submit();" />

		
			<!--  paginación  -->
					<div>
							<tr>
							<td width="100%">
								<logic:greaterThan name="formulari" property="numpages" value="1">
									<table border="0" width="100%">
										<tr>
											<td align="center">
												<logic:greaterThan name="formulari" property="currpage" value="0">
													<a href="javascript:pageDown();" ><<</a>
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
		
		
		
	</fieldset>
	</html:form>
</div>	
</body>
</html>
<%@ page language="java" %>
<%@ page import="java.util.*" %>


<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 	

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">

<head>
<jsp:include page="/spd/jsp/global/head.jsp"/>
<title>Gestión carga ficheros Robot</title>
</head>
<bean:define id="formulari" name="FicheroResiForm" type="lopicost.spd.struts.form.FicheroResiForm" />



<script type="text/javascript">	
	function updateList()
	{
		var f = document.FicheroResiForm;
	 	f.submit();
	}
	
		function goIndex()
		{
			//var f = document.FicheroResiForm;
			//f.parameter.action='';
			//alert(f.parameter.action);
			//f.submit();		
			//document.location.href='http://'+window.location.host+'/spd/FicheroResiCabecera.do?parameter=list';
			document.location.href='http://'+window.location.host+'/<%=request.getContextPath()%>/FicheroResiCabecera.do?parameter=list&oidDivisionResidencia=';
			//alert(document.location.href);

			return true;
			}	



		function borrar(oidFicheroResiDetalle)
		{
			var f = document.FicheroResiForm;
			f.parameter.value='borrar';
			f.ACTIONTODO.value='CONFIRMAR';
			f.oidFicheroResiDetalle.value= oidFicheroResiDetalle;
			f.submit();
		}
		
		
		function editar(oidFicheroResiDetalle)
		{
			//url+= '&idDivisionResidencia='+ idDivisionResidencia;

			//url+= '&cn_resi='+cn_resi+'&medicamento='+medicamento+'&cnOk='+cnOk	
			//document.location.href= url;
			//window.open(url,'1','width=700,height=400,menubar=yes,resizable=yes,scrollbars=yes,status=yes');
			var f = document.FicheroResiForm;
			f.parameter.value='editar';
			f.ACTIONTODO.value='EDITA';
			//f.oidFicheroResiCabecera.value= oidFicheroResiCabecera;
			f.oidFicheroResiDetalle.value= oidFicheroResiDetalle;
			f.submit();
		}
		function refrescar(oidFicheroResiDetalle)
		{
			//url+= '&idDivisionResidencia='+ idDivisionResidencia;

			//url+= '&cn_resi='+cn_resi+'&medicamento='+medicamento+'&cnOk='+cnOk	
			//document.location.href= url;
			//window.open(url,'1','width=700,height=400,menubar=yes,resizable=yes,scrollbars=yes,status=yes');
			var f = document.FicheroResiForm;
			f.parameter.value='refrescar';
			f.ACTIONTODO.value='REFRESCAR';
			f.oidFicheroResiDetalle.value= oidFicheroResiDetalle;
			f.submit();
		}		
		function buscar()
		{
			var f = document.FicheroResiForm;
			f.parameter.value='list';
			f.submit();	
		}
		
		function actualizaSustitucion(oidDivisionResidencia, idDivisionResidencia, cnResi, resiMedicamento)
		{
			//var loc = '/spd//GestSustituciones.do?parameter=list&filtroNombreCorto='+ cnResi + '&filtroMedicamentoResi='+ resiMedicamento +'&oidDivisionResidenciaFiltro='+ oidDivisionResidencia	;				//url de llamanda				
			var loc = '/spd//GestSustitucionesLite.do?parameter=list&filtroNombreCorto='+ cnResi + '&oidDivisionResidenciaFiltro='+ oidDivisionResidencia+ '&idDivisionResidencia='+ idDivisionResidencia	;				//url de llamanda				
		//Importante que se realice en ventana nueva!!!!
		window.open(loc, 'actualizaSustitucionLite', 'dependent=yes,height=500,width=800,top=50,left=50,resizable=yes,scrollbars=yes' );

		}	
 		function desasignarTodos()
		{
			var f = document.FicheroResiForm;
			/*if(f.codiLab.value=='')
			{
				alert('Falta indicar el laboratorio a desasignar');
				//return;				
			}*/
			f.parameter.value='borrarMasivoPorProceso';
			f.ACTIONTODO.value='BORRADO_MASIVO';
			f.submit();
		}	
 		function submit2()
		{
			var f = document.FicheroResiForm;
			f.submit();
		}	
 		
 		function checkCIP(cip, nombre)
		{
			var f = document.FicheroResiForm;
			if(cip!='' && nombre!='')
 				return;
			else if(cip=='')
			{
				f.parameter.value='actualizaCipEnFichero';
				f.ACTIONTODO.value='EDIT';
				f.submit();
			}
		}	
 		
 		
 		function generarFicherosRobot()
		{
			var f = document.FicheroResiForm;
			if(f.filtroDivisionResidenciasCargadas.value=='')
			{
				alert('Seleccionar residencia');
				return;				
			}
			if(f.idProceso.value==''){
				alert('Seleccionar proceso'); 
				return;				
			}
		//	alert(f.procesoValido.value);
			if(f.procesoValido.value=='false'){
				alert('Existen registros no válidos'); 
				return;				
			}
			
			f.parameter.value='generarFicherosRobot';
			f.ACTIONTODO.value='GENERAR_FICHEROS';
			f.submit();
		}
 		
 		
 		function limpiarFiltrosResi()
		{
			var f = document.FicheroResiForm;
			if(f.seleccionEstado)					f.seleccionEstado.value='';
			if(f.idProceso)							f.idProceso.value='';
			if(f.seleccionResiCIP)					f.seleccionResiCIP.value='';
			if(f.seleccionResiNombrePaciente)		f.seleccionResiNombrePaciente.value='';
			if(f.seleccionResiCn)					f.seleccionResiCn.value='';
			if(f.seleccionResiMedicamento)			f.seleccionResiMedicamento.value='';
			if(f.seleccionResiFormaMedicacion)		f.seleccionResiFormaMedicacion.value='';
			if(f.seleccionResiObservaciones)		f.seleccionResiObservaciones.value='';
			if(f.seleccionResiComentarios)			f.seleccionResiComentarios.value='';
			if(f.seleccionResiSiPrecisa)			f.seleccionResiSiPrecisa.value='';
			if(f.seleccionResiPeriodo)				f.seleccionResiPeriodo.value='';
			if(f.seleccionResiViaAdministracion)	f.seleccionResiViaAdministracion.value='';
			if(f.seleccionSpdCnFinal)				f.seleccionSpdCnFinal.value='';
			if(f.seleccionSpdNombreBolsa)			f.seleccionSpdNombreBolsa.value='';
			if(f.seleccionSpdFormaMedicacion)		f.seleccionSpdFormaMedicacion.value='';
			if(f.seleccionSpdAccionBolsa)			f.seleccionSpdAccionBolsa.value='';
			if(f.seleccionValido)					f.seleccionValido.value='';
			if(f.seleccionResultLog)				f.seleccionResultLog.value='';
			if(f.seleccionMensajesInfo)				f.seleccionMensajesInfo.value='';
			if(f.seleccionMensajesAlerta)			f.seleccionMensajesAlerta.value='';
			f.submit();
		}		

</script>
		
		

<body id="general">

<html:form action="/FicheroResiDetalle.do" method="post">	
<html:errors/>


<div id="contingut">
     <html:hidden property="parameter" value="list"/>
     <html:hidden property="ACTIONTODO" value="list"/>
     <!-- <html:hidden property="idDivisionResidencia" /> /-->
     <!-- <html:hidden property="idProceso"/> /-->
     <html:hidden property="oidFicheroResiCabecera" />
     <html:hidden property="oidFicheroResiDetalle" />
     <html:hidden property="procesoValido" />
     <html:hidden property="filtroProceso" />
     
     <html:hidden property="oidDivisionResidencia" /> 
     	<% String numPages = formulari.getNumpages()+""; %>
     	<% String currpage = formulari.getCurrpage()+""; %>
     <html:hidden property="numpages" value="<%= numPages %>"/>
	 <html:hidden property="currpage" value="<%= currpage %>"/>
	
 	<fieldset>
   	<!-- mostramos mensajes y errores, si existen -->
	<logic:notEmpty name="FicheroResiForm" property="errors">
		<font color="red"><ul>
			<u>Mensaje:</u>
				<logic:iterate id="error" name="FicheroResiForm" property="errors" type="java.lang.String">
						<li><bean:write name="error"/></li>
				</logic:iterate>
			</ul></font>
	</logic:notEmpty>
	<logic:notEmpty name="FicheroResiForm" property="listaDivisionResidenciasCargadas">	
	 <html:select property="filtroDivisionResidenciasCargadas"  value="<%= formulari.getFiltroDivisionResidenciasCargadas() %>" onchange="submit()"> 
		<html:option value="">Residencia</html:option>
   			<html:optionsCollection name="FicheroResiForm" property="listaDivisionResidenciasCargadas" label="nombreDivisionResidencia" value="idDivisionResidencia" />
	</html:select>
	</logic:notEmpty>	
	
 			<logic:notEqual property="filtroDivisionResidenciasCargadas" name="FicheroResiForm" value="">
			<logic:notEmpty name="FicheroResiForm" property="listaProcesosCargados">	
		   	 <html:select property="idProceso"  value="<%= formulari.getIdProceso() %>" onchange="submit()"> 
		   	 	<html:option value="">Proceso</html:option>
   					<c:forEach items="${FicheroResiForm.listaProcesosCargados}" var="bean"> 
			       		 <option value='${bean}' ${FicheroResiForm.idProceso == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
    				</c:forEach> 
   				</html:select>
		     </logic:notEmpty>	
		</logic:notEqual>
		
		<logic:notEqual property="filtroDivisionResidenciasCargadas" name="FicheroResiForm" value="">
			<logic:notEmpty name="FicheroResiForm" property="listaEstados">	
		   	 <html:select property="seleccionEstado"  value="<%= formulari.getSeleccionEstado() %>" onchange="submit()"> 
		   	 	<html:option value="">Estado</html:option>
   				
				<c:forEach items="${FicheroResiForm.listaEstados}" var="bean"> 
			    	 <option value='${bean}' ${FicheroResiForm.seleccionEstado == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
    			</c:forEach> 
    				
    				</html:select>
    				
		     </logic:notEmpty>	
 		</logic:notEqual>
		</br>


<!--
		
		<logic:notEqual property="idProceso" name="FicheroResiForm" value="">
		<logic:notEqual property="filtroDivisionResidenciasCargadas" name="FicheroResiForm" value="">
		<p><a href="javascript:desasignarTodos();">Borrar los datos de carga de este proceso</a></p>
		</logic:notEqual>
		</logic:notEqual>
		
		 --> 
			<p>Residencia: <%= formulari.getIdDivisionResidencia() %>	</p> 
			<p>Proceso: <%= formulari.getFiltroProceso() %>	</p> 
			<p><a href="javascript:generarFicherosRobot();">Generar ficheros robot</a></p> 
			<p><a href="javascript:limpiarFiltrosResi();">Limpiar filtros listado</a></p> 
	<!--	
 			<p><html:link page="/Iospd/Iospd.do?parameter=list&operation=FILTER">Gestión de carga de ficheros resi</html:link></p> 
		
			
    <p class="botonBuscar">
			<input type="button" onclick="javascript:buscar()"  value="Buscar"  />  
			< input type="button" onclick="javascript:nuevo()"  value="Nuevo"  />  
			<input type="button" onclick="javascript:goIndex()" value="Volver"/>
			<input type="button" onclick="javascript:window.close()" value="Cerrar"/>
	</p> 
  --> 
	<input type="button" onclick="javascript:goIndex()"; value="Volver"/>
	
	<P>
	<logic:notEmpty name="FicheroResiForm" property="idProceso">
	<TD><U>IDPROCESO SELECCIONADO:</U> <%= formulari.getIdProceso() %>	</TD></BR>
		<logic:notEmpty name="FicheroResiForm" property="listaFechaFichero">
		<TD><U>FECHA PROCESO:</U>	 <%= formulari.getListaFechaFichero().get(0).toString() %> </TD></BR>
		<TD><U>ESTADO SELECCIONADO:</U><%= formulari.getSeleccionEstado() %> </TD>
		</logic:notEmpty>	
	</logic:notEmpty>	
	</P>
	
 </fieldset>
 <table border="1">

		  <!--tr>
		  <td class="clsColorMenuRow">
		    <input type="text" name="fechaini" value="" style="border:medium none;" class="clsColorMenuRow" readonly>
		    </td><td class="clsColorMenuRow">
		    <A HREF="#"  onclick="calDateFormat='DD/MM/yyyy'; window.open('/spd/spd/common/calendar/jscal/calendar.html','cal','WIDTH=270,HEIGHT=280')"><IMG SRC="/spd/spd/common/calendar/jscal/calendar.gif"  BORDER=0  alt='<bean:message key="ConvocatoriaManagerFilters.info.seleccionarfecha"/>'></a>
		    &nbsp;<A HREF="#"  onclick="JavaScript:document.FicheroResiForm.filtroDivisionResidenciasCargadas.value='';"><IMG SRC="/spd/spd/common/calendar/jscal/nocalendar.gif"  BORDER=0  alt='<bean:message key="ConvocatoriaManagerFilters.info.borrarfecha"/>'></a>
		   </td>		   		   
		  </tr-->



 <!--
 	<th>
		fecha carga fichero</br>
	</th>
 
 	<th>
		Estado</br>
	</th> 	
 
	<th>
		Proceso</br>
	</th>
-->	
	<th>CIP</br>
 			<logic:notEmpty name="FicheroResiForm" property="listaResiCIP">	
				<html:select property="seleccionResiCIP"  value="<%= formulari.getSeleccionResiCIP() %>" onchange="submit()"> 
    				<html:option value="">Todos</html:option>
    					<c:forEach items="${FicheroResiForm.listaResiCIP}" var="bean"> 
			       		 <option value='${bean}' ${FicheroResiForm.seleccionResiCIP == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
    				</c:forEach>                     
				</html:select>
		     </logic:notEmpty>				     
	  </th>
	  <th>Nombre paciente</br>
			<logic:notEmpty name="FicheroResiForm" property="listaResiNombrePaciente">	
				<html:select property="seleccionResiNombrePaciente"  value="<%= formulari.getSeleccionResiNombrePaciente() %>" onchange="submit()"> 
    				<html:option value="">Todos</html:option>
    					<c:forEach items="${FicheroResiForm.listaResiNombrePaciente}" var="bean"> 
			       		 <option value='${bean}' ${FicheroResiForm.seleccionResiNombrePaciente == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
    				</c:forEach>                     
				</html:select>
		     </logic:notEmpty>			     
	    </th>
	  
	  <th>CN</br>
			 <logic:notEmpty name="FicheroResiForm" property="listaResiCn">	
				<html:select property="seleccionResiCn"  value="<%= formulari.getSeleccionResiCn() %>" onchange="submit()"> 
    				<html:option value="">Todos</html:option>
    					<c:forEach items="${FicheroResiForm.listaResiCn}" var="bean2"> 
			       		 <option value='${bean2}' ${FicheroResiForm.seleccionResiCn == bean2 ? 'selected' : ' '}><c:out value="${bean2}" ></c:out></option>   
    				</c:forEach>                     
				</html:select>
		     </logic:notEmpty>

	    </th>
	<th>Medicamento resi</br>
			<logic:notEmpty name="FicheroResiForm" property="listaResiMedicamento">	
				<html:select property="seleccionResiMedicamento"  value="<%= formulari.getSeleccionResiMedicamento() %>" onchange="submit()"> 
    				<html:option value="">Todos</html:option>
    					<c:forEach items="${FicheroResiForm.listaResiMedicamento}" var="bean"> 
			       		 <option value='${bean}' ${FicheroResiForm.seleccionResiMedicamento == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
    				</c:forEach>                     
				</html:select>
		     </logic:notEmpty>	
	</th>
 <!--	<th>forma medicacion</br>	
			<logic:notEmpty name="FicheroResiForm" property="listaResiFormaMedicacion">	
		<html:select property="seleccionResiFormaMedicacion"  value="<%= formulari.getSeleccionResiFormaMedicacion() %>" onchange="submit()"> 
    		<html:option value="">Todos</html:option>
    			<c:forEach items="${FicheroResiForm.listaResiFormaMedicacion}" var="bean"> 
				 <option value='${bean}' ${FicheroResiForm.seleccionResiFormaMedicacion == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
    			</c:forEach>                     
		</html:select>
		     </logic:notEmpty>
	</th>
-->
	<th>Observaciones</br>
		<logic:notEmpty name="FicheroResiForm" property="listaResiObservaciones">	
			<html:select property="seleccionResiObservaciones"  value="<%= formulari.getSeleccionResiObservaciones() %>" onchange="submit()"> 
				<html:option value="">Todos</html:option>
					<c:forEach items="${FicheroResiForm.listaResiObservaciones}" var="bean"> 
						<option value='${bean}' ${FicheroResiForm.seleccionResiObservaciones == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
    				</c:forEach>                     
			</html:select>
		</logic:notEmpty>	
	</th>
	<th>Variante</br>
		<logic:notEmpty name="FicheroResiForm" property="listaResiVariante">	
			<html:select property="seleccionResiVariante"  value="<%= formulari.getSeleccionResiVariante() %>" onchange="submit()"> 
				<html:option value="">Todos</html:option>
					<c:forEach items="${FicheroResiForm.listaResiVariante}" var="bean"> 
						<option value='${bean}' ${FicheroResiForm.seleccionResiVariante == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
    				</c:forEach>                     
			</html:select>
		</logic:notEmpty>	
	</th>	
	<th>Comentarios</br>
			<logic:notEmpty name="FicheroResiForm" property="listaResiComentarios">	
				<html:select property="seleccionResiComentarios"  value="<%= formulari.getSeleccionResiComentarios() %>" onchange="submit()"> 
    				<html:option value="">Todos</html:option>
    					<c:forEach items="${FicheroResiForm.listaResiComentarios}" var="bean"> 
			       		 <option value='${bean}' ${FicheroResiForm.seleccionResiComentarios == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
    				</c:forEach>                     
				</html:select>
		     </logic:notEmpty>	
	</th>     
	<th>Si Precisa</br>	
			<logic:notEmpty name="FicheroResiForm" property="listaResiSiPrecisa">	
				<html:select property="seleccionResiSiPrecisa"  value="<%= formulari.getSeleccionResiSiPrecisa() %>" onchange="submit()"> 
    				<html:option value="">Todos</html:option>
    					<c:forEach items="${FicheroResiForm.listaResiSiPrecisa}" var="bean"> 
			       		 <option value='${bean}' ${FicheroResiForm.seleccionResiSiPrecisa == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
    				</c:forEach>                     
				</html:select>
		     </logic:notEmpty>	
	</th>     
	<th>Periodo</br>	
			<logic:notEmpty name="FicheroResiForm" property="listaResiPeriodo">	
				<html:select property="seleccionResiPeriodo"  value="<%= formulari.getSeleccionResiPeriodo() %>" onchange="submit()"> 
    				<html:option value="">Todos</html:option>
    					<c:forEach items="${FicheroResiForm.listaResiPeriodo}" var="bean"> 
			       		 <option value='${bean}' ${FicheroResiForm.seleccionResiPeriodo == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
    				</c:forEach>                     
				</html:select>
		     </logic:notEmpty>	
	</th>
	<th nowrap>inicio-fin tratamiento</th></br>
	<th nowrap>inicio-fin producción</th></br>
	<!--th nowrap>Producir SPD Desde - Hasta</th></br-->
	  <th>L</th>	  
	  <th>M</th>	  
	  <th>X</th>	  
	  <th>J</th>	  
	  <th>V</th>	  
	  <th>S</th>	  
	  <th>D</th>	  
		  <th>Marcado Aut?
	 </br>	
	  <logic:notEmpty name="FicheroResiForm" property="listaDiasAutomaticos">	
				<html:select property="seleccionDiasAutomaticos"  value="<%= formulari.getSeleccionDiasAutomaticos() %>" onchange="submit()"> 
    				<html:option value="">Todos</html:option>
    					<c:forEach items="${FicheroResiForm.listaDiasAutomaticos}" var="bean"> 
			       		 <option value='${bean}' ${FicheroResiForm.seleccionDiasAutomaticos == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
    				</c:forEach>                     
				</html:select>
		     </logic:notEmpty>	
	 </th> 

 
	   		<bean:define id="campos" name="FicheroResiForm" property="camposPantallaBean" />
	   		
	    		
	   		<logic:equal property="visibleToma1" name="campos" value="true">
				<th>01h</th>
			</logic:equal>
	   		<logic:equal property="visibleToma2" name="campos" value="true">
				<th>02h</th>
			</logic:equal>
	   		<logic:equal property="visibleToma3" name="campos" value="true">
				<th>03h</th>
			</logic:equal>
	   		<logic:equal property="visibleToma4" name="campos" value="true">
				<th>04h</th>
			</logic:equal>
	   		<logic:equal property="visibleToma5" name="campos" value="true">
				<th>05h</th>
			</logic:equal>
	   		<logic:equal property="visibleToma6" name="campos" value="true">
				<th>06h</th>
			</logic:equal>
	   		<logic:equal property="visibleToma7" name="campos" value="true">
				<th>07h</th>
			</logic:equal>
	   		<logic:equal property="visibleToma8" name="campos" value="true">
				<th>08h</th>
			</logic:equal>
	   		<logic:equal property="visibleToma9" name="campos" value="true">
				<th>09h</th>
			</logic:equal>
	   		<logic:equal property="visibleToma10" name="campos" value="true">
				<th>10h</th>
			</logic:equal>
	   		<logic:equal property="visibleToma11" name="campos" value="true">
				<th>11h</th>
			</logic:equal>
	   		<logic:equal property="visibleToma12" name="campos" value="true">
				<th>12h</th>
			</logic:equal>
	   		<logic:equal property="visibleToma13" name="campos" value="true">
				<th>13h</th>
			</logic:equal>
	   		<logic:equal property="visibleToma14" name="campos" value="true">
				<th>14h</th>
			</logic:equal>
	   		<logic:equal property="visibleToma15" name="campos" value="true">
				<th>15h</th>
			</logic:equal>
	   		<logic:equal property="visibleToma16" name="campos" value="true">
				<th>16h</th>
			</logic:equal>
	   		<logic:equal property="visibleToma17" name="campos" value="true">
				<th>17h</th>
			</logic:equal>
	   		<logic:equal property="visibleToma18" name="campos" value="true">
				<th>18h</th>
			</logic:equal>
	   		<logic:equal property="visibleToma19" name="campos" value="true">
				<th>19h</th>
			</logic:equal>
	   		<logic:equal property="visibleToma20" name="campos" value="true">
				<th>20h</th>
			</logic:equal>
	   		<logic:equal property="visibleToma21" name="campos" value="true">
				<th>21h</th>
			</logic:equal>
	   		<logic:equal property="visibleToma22" name="campos" value="true">
				<th>22h</th>
			</logic:equal>
	   		<logic:equal property="visibleToma23" name="campos" value="true">
				<th>23h</th>
			</logic:equal>
	   		<logic:equal property="visibleToma24" name="campos" value="true">
				<th>24h</th>
			</logic:equal>
	  <!-- 
	  <th>via administración</br>
	  <logic:notEmpty name="FicheroResiForm" property="listaResiViaAdministracion">	
		<html:select property="seleccionResiViaAdministracion"  value="<%= formulari.getSeleccionResiViaAdministracion() %>" onchange="submit()"> 
			<html:option value="">Todos</html:option>
				<c:forEach items="${FicheroResiForm.listaResiViaAdministracion}" var="bean"> 
			<option value='${bean}' ${FicheroResiForm.seleccionResiViaAdministracion == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
			</c:forEach>                     
		</html:select>
		     </logic:notEmpty>	
		 </th>
		 --> 
	<th>ResultLog	 </br>	
	  <logic:notEmpty name="FicheroResiForm" property="listaResultLog">	
				<html:select property="seleccionResultLog"  value="<%= formulari.getSeleccionResultLog() %>" onchange="submit()"> 
    				<html:option value="">Todos</html:option>
    					<c:forEach items="${FicheroResiForm.listaResultLog}" var="bean"> 
			       		 <option value='${bean}' ${FicheroResiForm.seleccionResultLog == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
    				</c:forEach>                     
				</html:select>
		     </logic:notEmpty>	
	 </th> 

	  <th>Mensaje INFO	 </br>	
	  <logic:notEmpty name="FicheroResiForm" property="listaMensajesInfo">	
				<html:select property="seleccionMensajesInfo"  value="<%= formulari.getSeleccionMensajesInfo() %>" onchange="submit()"> 
    				<html:option value="">Todos</html:option>
    					<c:forEach items="${FicheroResiForm.listaMensajesInfo}" var="bean"> 
			       		 <option value='${bean}' ${FicheroResiForm.seleccionMensajesInfo == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
    				</c:forEach>                     
				</html:select>
		     </logic:notEmpty>	
	 </th> 

	  <th>Mensaje ALERTA	 </br>	
	  <logic:notEmpty name="FicheroResiForm" property="listaMensajesAlerta">	
				<html:select property="seleccionMensajesAlerta"  value="<%= formulari.getSeleccionMensajesAlerta() %>" onchange="submit()"> 
    				<html:option value="">Todos</html:option>
    					<c:forEach items="${FicheroResiForm.listaMensajesAlerta}" var="bean"> 
			       		 <option value='${bean}' ${FicheroResiForm.seleccionMensajesAlerta == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
    				</c:forEach>                     
				</html:select>
		     </logic:notEmpty>	
	 </th> 
	

	  <th>spdCnFinal</br>

		   <logic:notEmpty name="FicheroResiForm" property="listaSpdCnFinal">	
			<html:select property="seleccionSpdCnFinal"  value="<%= formulari.getSeleccionSpdCnFinal() %>" onchange="submit()"> 
    				<html:option value="">Todos</html:option>
    					<c:forEach items="${FicheroResiForm.listaSpdCnFinal}" var="bean"> 
			       		 <option value='${bean}' ${FicheroResiForm.seleccionSpdCnFinal == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
    				</c:forEach>                     
				</html:select>
		     </logic:notEmpty>
		     	
		 </th>
	  <th>Nombre en bolsa</br>		
	  <logic:notEmpty name="FicheroResiForm" property="listaSpdNombreBolsa">	
			<html:select property="seleccionSpdNombreBolsa"  value="<%= formulari.getSeleccionSpdNombreBolsa() %>" onchange="submit()"> 
				<html:option value="">Todos</html:option>
 					<c:forEach items="${FicheroResiForm.listaSpdNombreBolsa}" var="bean"> 
			       		 <option value='${bean}' ${FicheroResiForm.seleccionSpdNombreBolsa == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
    				</c:forEach>                     
				</html:select>
		     </logic:notEmpty>	
		 </th> 
	  <th>forma Medicacion</br>
	  			<logic:notEmpty name="FicheroResiForm" property="listaSpdFormaMedicacion">	
				<html:select property="seleccionSpdFormaMedicacion"  value="<%= formulari.getSeleccionSpdFormaMedicacion() %>" onchange="submit()"> 
    				<html:option value="">Todos</html:option>
    					<c:forEach items="${FicheroResiForm.listaSpdFormaMedicacion}" var="bean"> 
			       		 <option value='${bean}' ${FicheroResiForm.seleccionSpdFormaMedicacion == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
    				</c:forEach>                     
				</html:select>
		     </logic:notEmpty>	
		 </th> 		
	  <th>acción bolsa</br>
	  			<logic:notEmpty name="FicheroResiForm" property="listaSpdAccionBolsa">	
				<html:select property="seleccionSpdAccionBolsa"  value="<%= formulari.getSeleccionSpdAccionBolsa() %>" onchange="submit()"> 
    				<html:option value="">Todos</html:option>
    					<c:forEach items="${FicheroResiForm.listaSpdAccionBolsa}" var="bean"> 
			       		 <option value='${bean}' ${FicheroResiForm.seleccionSpdAccionBolsa == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
    				</c:forEach>                     
				</html:select>
		     </logic:notEmpty>	
		 </th> 	
	  <th>¿Es excepción? 
	 </br>	
	  <logic:notEmpty name="FicheroResiForm" property="listaEsExcepcion">	
				<html:select property="seleccionEsExcepcion"  value="<%= formulari.getSeleccionEsExcepcion() %>" onchange="submit()"> 
    				<html:option value="">Todos</html:option>
    					<c:forEach items="${FicheroResiForm.listaEsExcepcion}" var="bean"> 
			       		 <option value='${bean}' ${FicheroResiForm.seleccionEsExcepcion == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
    				</c:forEach>                     
				</html:select>
		     </logic:notEmpty>	
	 </th> 
	  	
	  <th>Incidencia
	 </br>	
	  <logic:notEmpty name="FicheroResiForm" property="listaIncidencia">	
				<html:select property="seleccionIncidencia"  value="<%= formulari.getSeleccionIncidencia() %>" onchange="submit()"> 
    				<html:option value="">Todos</html:option>
    					<c:forEach items="${FicheroResiForm.listaIncidencia}" var="bean"> 
			       		 <option value='${bean}' ${FicheroResiForm.seleccionIncidencia == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
    				</c:forEach>                     
				</html:select>
		     </logic:notEmpty>	
	 </th> 

	  <th>Estado linea
	 </br>	
	  <logic:notEmpty name="FicheroResiForm" property="listaEstados">	
				<html:select property="seleccionEstado"  value="<%= formulari.getSeleccionEstado() %>" onchange="submit()"> 
    				<html:option value="">Todos</html:option>
    					<c:forEach items="${FicheroResiForm.listaEstados}" var="bean"> 
			       		 <option value='${bean}' ${FicheroResiForm.seleccionEstado == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
    				</c:forEach>                     
				</html:select>
		     </logic:notEmpty>	
	 </th> 
     </tr>


 	<logic:iterate id="data" name="FicheroResiForm" property="listaFicheroResiDetalleBean" type="lopicost.spd.struts.bean.FicheroResiBean" indexId="position">
		
       <tr>

	<!-- td><bean:write name="data" property="idDivisionResidencia" /></td -->
 	<!--<td><bean:write name="data" property="fechaHoraProceso" /></td>
     
    <td><bean:write name="data" property="idEstado" /></td>
	-->
     <!-- 
	<td><bean:write name="data" property="idProceso" /></td>
	-->
    <td><bean:write name="data" property="resiCIP" /></td>
	<td><a href="javascript:checkCIP('<bean:write name="data" property="resiCIP" />', '<bean:write name="data" property="resiNombrePaciente" />')"><bean:write name="data" property="resiNombrePaciente" /></a></td>
	<td><a href="javascript:actualizaSustitucion('<bean:write name="data" property="oidDivisionResidencia" />','<bean:write name="data" property="idDivisionResidencia" />','<bean:write name="data" property="resiCn" />', '<bean:write name="data" property="resiMedicamento" />')"><bean:write name="data" property="resiCn" /></a></td>
	<td><bean:write name="data" property="resiMedicamento" /></td>
    <!-- 	<td><bean:write name="data" property="resiFormaMedicacion" /></td> -->
	<td><bean:write name="data" property="resiObservaciones" /></td>
	<td><bean:write name="data" property="resiVariante" /></td>
	
	<td><bean:write name="data" property="resiComentarios" /></td>
	<td><html:checkbox disabled="true" name="data" property="resiSiPrecisa" value="X" /></td>
	<td><bean:write name="data" property="resiPeriodo" /></td>
	<td><bean:write name="data" property="resiInicioTratamiento" /> - <bean:write name="data" property="resiFinTratamiento" /></td>
	<td><bean:write name="data" property="resiInicioTratamientoParaSPD" /> - <bean:write name="data" property="resiFinTratamientoParaSPD" /></td>
	<!--td><bean:write name="data" property="fechaDesde" /> - <bean:write name="data" property="fechaHasta" /></td-->
	<td><html:checkbox disabled="true" name="data" property="resiD1" value="X" /></td>
	<td><html:checkbox disabled="true" name="data" property="resiD2" value="X" /></td>
	<td><html:checkbox disabled="true" name="data" property="resiD3" value="X"/></td>
	<td><html:checkbox disabled="true" name="data" property="resiD4" value="X"/></td>
	<td><html:checkbox disabled="true" name="data" property="resiD5" value="X"/></td>
	<td><html:checkbox disabled="true" name="data" property="resiD6" value="X"/></td>
	<td><html:checkbox disabled="true" name="data" property="resiD7" value="X"/></td>
	<td><bean:write name="data" property="resiDiasAutomaticos" /></td>
	
	   		<logic:equal property="visibleToma1" name="campos" value="true">
				<td><bean:write name="data" property="resiToma1" /></td>
			</logic:equal>
	   		<logic:equal property="visibleToma2" name="campos" value="true">
				<td><bean:write name="data" property="resiToma2" /></td>
			</logic:equal>
	   		<logic:equal property="visibleToma3" name="campos" value="true">
				<td><bean:write name="data" property="resiToma3" /></td>
			</logic:equal>
	   		<logic:equal property="visibleToma4" name="campos" value="true">
				<td><bean:write name="data" property="resiToma4" /></td>
			</logic:equal>
	   		<logic:equal property="visibleToma5" name="campos" value="true">
				<td><bean:write name="data" property="resiToma5" /></td>
			</logic:equal>
	   		<logic:equal property="visibleToma6" name="campos" value="true">
				<td><bean:write name="data" property="resiToma6" /></td>
			</logic:equal>
	   		<logic:equal property="visibleToma7" name="campos" value="true">
				<td><bean:write name="data" property="resiToma7" /></td>
			</logic:equal>
	   		<logic:equal property="visibleToma8" name="campos" value="true">
				<td><bean:write name="data" property="resiToma8" /></td>
			</logic:equal>
	   		<logic:equal property="visibleToma9" name="campos" value="true">
				<td><bean:write name="data" property="resiToma9" /></td>
			</logic:equal>
	   		<logic:equal property="visibleToma10" name="campos" value="true">
				<td><bean:write name="data" property="resiToma10" /></td>
			</logic:equal>
	   		<logic:equal property="visibleToma11" name="campos" value="true">
				<td><bean:write name="data" property="resiToma11" /></td>
			</logic:equal>
	   		<logic:equal property="visibleToma12" name="campos" value="true">
				<td><bean:write name="data" property="resiToma12" /></td>
			</logic:equal>
	   		<logic:equal property="visibleToma13" name="campos" value="true">
				<td><bean:write name="data" property="resiToma13" /></td>
			</logic:equal>
	   		<logic:equal property="visibleToma14" name="campos" value="true">
				<td><bean:write name="data" property="resiToma14" /></td>
			</logic:equal>
	   		<logic:equal property="visibleToma15" name="campos" value="true">
				<td><bean:write name="data" property="resiToma15" /></td>
			</logic:equal>
	   		<logic:equal property="visibleToma16" name="campos" value="true">
				<td><bean:write name="data" property="resiToma16" /></td>
			</logic:equal>
	   		<logic:equal property="visibleToma17" name="campos" value="true">
				<td><bean:write name="data" property="resiToma17" /></td>
			</logic:equal>
	   		<logic:equal property="visibleToma18" name="campos" value="true">
				<td><bean:write name="data" property="resiToma18" /></td>
			</logic:equal>
	   		<logic:equal property="visibleToma19" name="campos" value="true">
				<td><bean:write name="data" property="resiToma19" /></td>
			</logic:equal>
	   		<logic:equal property="visibleToma20" name="campos" value="true">
				<td><bean:write name="data" property="resiToma20" /></td>
			</logic:equal>
	   		<logic:equal property="visibleToma21" name="campos" value="true">
				<td><bean:write name="data" property="resiToma21" /></td>
			</logic:equal>
	   		<logic:equal property="visibleToma22" name="campos" value="true">
				<td><bean:write name="data" property="resiToma22" /></td>
			</logic:equal>
	   		<logic:equal property="visibleToma23" name="campos" value="true">
				<td><bean:write name="data" property="resiToma23" /></td>
			</logic:equal>
	   		<logic:equal property="visibleToma24" name="campos" value="true">
				<td><bean:write name="data" property="resiToma24" /></td>
			</logic:equal>
	
	
	<!-- td><bean:write name="data" property="resiViaAdministracion" /></td>-->
	<td><bean:write name="data" property="resultLog" /></td>

	<%	
String mensajesInfo =(data.getMensajesInfo()!=null&&!data.getMensajesInfo().equals("")?"info":"");
String mensajesAlerta =(data.getMensajesAlerta()!=null&&!data.getMensajesAlerta().equals("")?"alerta":"");
	%>
    <td NOWRAP class="<%=mensajesInfo%>"><%= data.getMensajesInfo() %></td>
    <td NOWRAP class="<%=mensajesAlerta%>"><%= data.getMensajesAlerta() %></td>
	<td><bean:write name="data" property="spdCnFinal" /></td>
	<td><bean:write name="data" property="spdNombreBolsa" /></td>
	<td><bean:write name="data" property="spdFormaMedicacion" /></td>
	<td><bean:write name="data" property="spdAccionBolsa" /></td>


	<td><bean:write name="data" property="esExcepcion" /></td>
 	<td><bean:write name="data" property="incidencia" /></td>
	<td><bean:write name="data" property="idEstado" /></td>
 	
 	<td>
			<logic:equal property="editable" name="data" value="true">
				<p class="botons">
				<input type="button" onclick="javascript:borrar('<bean:write name="data" property="oidFicheroResiDetalle" />');"  value="Borrar"  />
				<!-- a href="javascript:borrar('<bean:write name="data" property="oidFicheroResiDetalle" />')">Borrar -->
				</a -->
				</p>
			</logic:equal>
			</td>
			<td>
			<logic:equal property="editable" name="data" value="true">
					<p class="botons">
						<input type="button" onclick="javascript:editar('<bean:write name="data" property="oidFicheroResiDetalle" />');"  value="Editar"  />
					</p>
			</logic:equal>
			</td>
				<td>
			<logic:equal property="editable" name="data" value="true">
					<p class="botons">
						<input type="button" onclick="javascript:refrescar('<bean:write name="data" property="oidFicheroResiDetalle" />');"  value="Refrescar"  />
					</p>
			</logic:equal>
			</td>
        </tr>
    </logic:iterate>

    
</table>

		
		
	<!--  paginación 
	<div>
		<logic:greaterThan name="formulari" property="numpages" value="1">
			<p align="center">
				<logic:greaterThan name="formulari" property="currpage" value="0">
					<a href="javascript:pageDown();" ><<</a>
				</logic:greaterThan>
				&nbsp;<input type="text" name="newPage" value="<%= formulari.getCurrpage()+1 %>" size="1" maxlength="4" onkeypress="gotoPage(this.value,<%=formulari.getNumpages()%>);">/<%= formulari.getNumpages() %>&nbsp;
				<logic:lessThan name="formulari" property="currpage" value="<%= String.valueOf(formulari.getNumpages() -1)%>">
					<a href="javascript:pageUp();" >>></a>
				</logic:lessThan>
			</p>
		</logic:greaterThan>
	</div>
	<!--  paginación   -->
		
	</div>	
	</html:form>

</body>
</html>
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
		document.location.href='http://'+window.location.host+'/<%=request.getContextPath()%>/FicheroResiCabeceraLite.do?parameter=list&oidDivisionResidencia=';
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
	
		function actualizaSustitucionLite(oidDivisionResidencia, idDivisionResidencia, cnResi, resiMedicamento)
		{
			//var loc = '/spd//GestSustituciones.do?parameter=list&filtroNombreCorto='+ cnResi + '&filtroMedicamentoResi='+ resiMedicamento +'&oidDivisionResidenciaFiltro='+ oidDivisionResidencia	;				//url de llamanda				
			//cuando no exista LITE --> var loc = '/spd//GestSustituciones.do?parameter=list&filtroNombreCorto='+ cnResi + '&oidDivisionResidenciaFiltro='+ oidDivisionResidencia	;				//url de llamanda				
			//var campoGoogle=cnResi; 
			//if (campoGoogle==null || campoGoogle=='') campoGoogle=resiMedicamento;
//			var loc = '/spd/GestSustitucionesLite.do?parameter=list&campoGoogle='+ campoGoogle + '&oidDivisionResidenciaFiltro='+ oidDivisionResidencia + '&idDivisionResidencia='+ idDivisionResidencia + '&filtroMedicamentoResi='+ resiMedicamento	;				//url de llamanda				
		//var loc = '/spd/GestSustitucionesLite.do?parameter=list&campoGoogle='+ campoGoogle + '&oidDivisionResidenciaFiltro='+ oidDivisionResidencia ;				//url de llamanda
		var loc = '/spd/GestSustitucionesLite.do?parameter=list&campoGoogle='+ cnResi + '&oidDivisionResidenciaFiltro='+ oidDivisionResidencia ;				//url de llamanda
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
			if(f.procesoValido.value=='false'){
				alert('Existen registros no válidos'); 
				return;				
			}
			
			//f.parameter.value='generarFicherosRobot';
			f.parameter.value='generarFicherosHelium';
			f.ACTIONTODO.value='GENERAR_FICHEROS';
			f.submit();
		}
 		
 		
 		function limpiarFiltrosResi()
		{
			var f = document.FicheroResiForm;
			if(f.oidFicheroResiDetalle)				f.oidFicheroResiDetalle.value='';
			if(f.seleccionEstado)					f.seleccionEstado.value='';
			if(f.seleccionResiCIP)					f.seleccionResiCIP.value='';
			if(f.seleccionResiNombrePaciente)		f.seleccionResiNombrePaciente.value='';
			if(f.seleccionResiApellidosNombre)		f.seleccionResiApellidosNombre.value='';
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
		//	if(f.seleccionResultLog)				f.seleccionResultLog.value='';
			if(f.seleccionMensajesInfo)				f.seleccionMensajesInfo.value='';
			if(f.seleccionMensajesAlerta)			f.seleccionMensajesAlerta.value='';
			if(f.seleccionIncidencia)				f.seleccionIncidencia.value='';
			if(f.seleccionValidar)					f.seleccionValidar.value='';
			if(f.secuenciaGuide)					f.secuenciaGuide.value='';
			if(f.excluirNoPintar)					f.excluirNoPintar.value='';
			if(f.campoOrder)						f.campoOrder.value='';
			
			f.submit();
		}		

		function exportExcel()
		{
			var f = document.FicheroResiForm;
			f.parameter.value='exportExcel';
			f.ACTIONTODO.value='EXCEL';
			f.submit();   	
		}	
			
		function goSubmit()
		{
			var f = document.FicheroResiForm;
			f.parameter.value='list';
			f.ACTIONTODO.value='list';
			f.submit();   	
		}	
		function ordenarPor(orden)
		{
			var f = document.FicheroResiForm;
			f.parameter.value='list';
			f.ACTIONTODO.value='list';
			f.campoOrder.value=orden;
			f.submit();   	
		}
		
		function visualizarActivos()
		{
			var f = document.FicheroResiForm;
			f.parameter.value='list';
			f.ACTIONTODO.value='list';
			f.excluirNoPintar.value='SI';
			f.submit();   	
		}		
		
		</script>
		
		

<body id="general">

<html:form action="/FicheroResiDetalleLite.do" method="post">	
<html:errors/>

    	<!-- mostramos mensajes y errores, si existen -->
 	 <ul>
        <logic:notEmpty name="FicheroResiForm" property="errors">
            <li class="error">
                <u>Mensaje:</u>
                <logic:iterate id="error" name="FicheroResiForm" property="errors" type="java.lang.String">
                    <span><bean:write name="error"/></span>
                </logic:iterate>
            </li>
        </logic:notEmpty>
    </ul>
    
<div id="contingut">
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
      
     <html:hidden property="oidDivisionResidencia" /> 
     	<% String numPages = formulari.getNumpages()+""; %>
     	<% String currpage = formulari.getCurrpage()+""; %>
     <html:hidden property="numpages" value="<%= numPages %>"/>
	 <html:hidden property="currpage" value="<%= currpage %>"/>
	
 	<fieldset>
		  <div>
			<p>Residencia: <%= formulari.getIdDivisionResidencia() %>	</p> 
			<p>Proceso: <%= formulari.getFiltroProceso() %>	</p> 
			<logic:notEmpty name="FicheroResiForm" property="listaFechaFichero">
			<p>Fecha proceso:	 <%= formulari.getListaFechaFichero().get(0).toString() %> </p></BR>
			</logic:notEmpty>				
   </div>

	<div>
		<input type="button" value="Generar ficheros robot" onclick="javascript:generarFicherosRobot()"; />
		<input type="button" value="Limpiar filtros listado" onclick="javascript:limpiarFiltrosResi();" />
		<input type="button" value="Exportar Excel"  onclick="javascript:exportExcel();"/>
	</div> 
	<input type="button" value="Ordenar por CIP" onclick="javascript:ordenarPor('CIP');" />
	<input type="button" value="Ordenar por mensajes" onclick="javascript:ordenarPor('mensajes');" />
	<input type="button" value="Excluir NoPintar" onclick="javascript:visualizarActivos();" />
 <div>
 

	
	 </div>
 </fieldset>
 <table border="1">
	<th>acciones</th>	  
	<th>Validar 
		</br>	
		<logic:notEmpty name="FicheroResiForm" property="listaValidar">	
				<html:select property="seleccionValidar"  value="<%= formulari.getSeleccionValidar() %>" onchange="javascript:goSubmit();"> 
    				<html:option value="">Todos</html:option>
    					<c:forEach items="${FicheroResiForm.listaValidar}" var="bean"> 
			       		 <option value='${bean}' ${FicheroResiForm.seleccionValidar== bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
    				</c:forEach>                     
				</html:select>
		     </logic:notEmpty>	
	</th> 
	<th>Incidencia
		</br>	
		<logic:notEmpty name="FicheroResiForm" property="listaIncidencia">	
			<html:select property="seleccionIncidencia"  value="<%= formulari.getSeleccionIncidencia() %>" onchange="javascript:goSubmit();"> 
    				<html:option value="">Todos</html:option>
    					<c:forEach items="${FicheroResiForm.listaIncidencia}" var="bean"> 
			       		 <option value='${bean}' ${FicheroResiForm.seleccionIncidencia == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
    				</c:forEach>                     
				</html:select>
		     </logic:notEmpty>	
	</th> 
	<th class="infoSelect">Mensajes (Info /<span class="rojo
	">alerta</span> )
		</br>	
		<logic:notEmpty name="FicheroResiForm" property="listaMensajesInfo">	
			<html:select property="seleccionMensajesInfo"  value="<%= formulari.getSeleccionMensajesInfo() %>" onchange="javascript:goSubmit();" styleClass="select_corto"> 
			<html:option value="">Todas las info</html:option>
    			<c:forEach items="${FicheroResiForm.listaMensajesInfo}" var="bean"> 
			    	<option value='${bean}' ${FicheroResiForm.seleccionMensajesInfo == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
    			</c:forEach>                     
			</html:select>
		</logic:notEmpty>	
	
		<logic:notEmpty name="FicheroResiForm" property="listaMensajesAlerta">	
				<html:select property="seleccionMensajesAlerta"  value="<%= formulari.getSeleccionMensajesAlerta() %>" onchange="javascript:goSubmit();"  styleClass="select_corto"> 
    			<span class="red"><html:option value="">Todas las alertas</html:option>
    				<c:forEach items="${FicheroResiForm.listaMensajesAlerta}" var="bean"> 
			       		 <option value='${bean}' ${FicheroResiForm.seleccionMensajesAlerta == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
    				</c:forEach>                     
				</html:select>
		</logic:notEmpty>	</span>
	</th> 
	 <th nowrap>Periodo</br>	
			<logic:notEmpty name="FicheroResiForm" property="listaResiPeriodo">	
				<html:select property="seleccionResiPeriodo"  value="<%= formulari.getSeleccionResiPeriodo() %>" onchange="javascript:goSubmit();" styleClass="select_5">   
    				<html:option value="">Todos</html:option>
    					<c:forEach items="${FicheroResiForm.listaResiPeriodo}" var="bean"> 
			       		 <option value='${bean}' ${FicheroResiForm.seleccionResiPeriodo == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
    				</c:forEach>                     
				</html:select>
		     </logic:notEmpty>	


	</th>	
	 	  <th>spdCnFinal</br>
		   <logic:notEmpty name="FicheroResiForm" property="listaSpdCnFinal">	
			<html:select property="seleccionSpdCnFinal"  value="<%= formulari.getSeleccionSpdCnFinal() %>" onchange="javascript:goSubmit();" styleClass="select_5" >    
    				<html:option value="">Todos</html:option>
    					<c:forEach items="${FicheroResiForm.listaSpdCnFinal}" var="bean"> 
			       		 <option value='${bean}' ${FicheroResiForm.seleccionSpdCnFinal == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
    				</c:forEach>                     
				</html:select>
		     </logic:notEmpty>
		     	
		 </th>
	  <th>Nombre en bolsa</br>		
	  <logic:notEmpty name="FicheroResiForm" property="listaSpdNombreBolsa">	
			<html:select property="seleccionSpdNombreBolsa"  value="<%= formulari.getSeleccionSpdNombreBolsa() %>" onchange="javascript:goSubmit();"  styleClass="select_corto">  
				<html:option value="">Todos</html:option>
 					<c:forEach items="${FicheroResiForm.listaSpdNombreBolsa}" var="bean"> 
			       		 <option value='${bean}' ${FicheroResiForm.seleccionSpdNombreBolsa == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
    				</c:forEach>                     
				</html:select>
		     </logic:notEmpty>	
		 </th> 
	  <th>forma Medicacion</br>
	  			<logic:notEmpty name="FicheroResiForm" property="listaSpdFormaMedicacion">	
				<html:select property="seleccionSpdFormaMedicacion"  value="<%= formulari.getSeleccionSpdFormaMedicacion() %>" onchange="javascript:goSubmit();" styleClass="select_10" >  
    				<html:option value="">Todos</html:option>
    					<c:forEach items="${FicheroResiForm.listaSpdFormaMedicacion}" var="bean"> 
			       		 <option value='${bean}' ${FicheroResiForm.seleccionSpdFormaMedicacion == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
    				</c:forEach>                     
				</html:select>
		     </logic:notEmpty>	
		 </th> 		
	  <th>acción bolsa</br>
	  			<logic:notEmpty name="FicheroResiForm" property="listaSpdAccionBolsa">	
				<html:select property="seleccionSpdAccionBolsa"  value="<%= formulari.getSeleccionSpdAccionBolsa() %>" onchange="javascript:goSubmit();" styleClass="select_7" >    
    				<html:option value="">Todos</html:option>
    					<c:forEach items="${FicheroResiForm.listaSpdAccionBolsa}" var="bean"> 
			       		 <option value='${bean}' ${FicheroResiForm.seleccionSpdAccionBolsa == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
    				</c:forEach>                     
				</html:select>
		     </logic:notEmpty>	
		 </th> 	
	
	<th>CIP</br>
 			<logic:notEmpty name="FicheroResiForm" property="listaResiCIP">	
				<html:select property="seleccionResiCIP"  value="<%= formulari.getSeleccionResiCIP() %>" onchange="javascript:goSubmit();" styleClass="select_10" >   
    				<html:option value="">Todos</html:option>
    					<c:forEach items="${FicheroResiForm.listaResiCIP}" var="bean"> 
			       		 <option value='${bean}' ${FicheroResiForm.seleccionResiCIP == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
    				</c:forEach>                     
				</html:select>
		     </logic:notEmpty>				     
	  </th>
	  <th>Nombre paciente</br>
			<logic:notEmpty name="FicheroResiForm" property="listaResiApellidosNombre">	
				<html:select property="seleccionResiApellidosNombre"  value="<%= formulari.getSeleccionResiApellidosNombre() %>" onchange="javascript:goSubmit();" styleClass="select_corto"> 
    				<html:option value="">Todos</html:option>
    					<c:forEach items="${FicheroResiForm.listaResiApellidosNombre}" var="bean"> 
			       		 <option value='${bean}' ${FicheroResiForm.seleccionResiApellidosNombre == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
    				</c:forEach>                     
				</html:select>
		     </logic:notEmpty>			     
	    </th>	  

  	  
	  <th>CN</br>
			 <logic:notEmpty name="FicheroResiForm" property="listaResiCn">	
				<html:select property="seleccionResiCn"  value="<%= formulari.getSeleccionResiCn() %>" onchange="javascript:goSubmit();"   styleClass="select_5">  
    				<html:option value="">Todos</html:option>
    					<c:forEach items="${FicheroResiForm.listaResiCn}" var="bean2"> 
			       		 <option value='${bean2}' ${FicheroResiForm.seleccionResiCn == bean2 ? 'selected' : ' '}><c:out value="${bean2}" ></c:out></option>   
    				</c:forEach>                     
				</html:select>
		     </logic:notEmpty>

	    </th>
	<th>Medicamento resi</br>
			<logic:notEmpty name="FicheroResiForm" property="listaResiMedicamento">	
				<html:select property="seleccionResiMedicamento"  value="<%= formulari.getSeleccionResiMedicamento() %>" onchange="javascript:goSubmit();"  styleClass="select_corto">   
    				<html:option value="">Todos</html:option>
    					<c:forEach items="${FicheroResiForm.listaResiMedicamento}" var="bean"> 
			       		 <option value='${bean}' ${FicheroResiForm.seleccionResiMedicamento == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
    				</c:forEach>                     
				</html:select>
		     </logic:notEmpty>	
	</th>
	<th nowrap>inicio-fin tratamiento</br>

	<th>Si Precisa</br>	
			<logic:notEmpty name="FicheroResiForm" property="listaResiSiPrecisa">	
				<html:select property="seleccionResiSiPrecisa"  value="<%= formulari.getSeleccionResiSiPrecisa() %>" onchange="javascript:goSubmit();"> 
    				<html:option value="">Todos</html:option>
    					<c:forEach items="${FicheroResiForm.listaResiSiPrecisa}" var="bean"> 
			       		 <option value='${bean}' ${FicheroResiForm.seleccionResiSiPrecisa == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
    				</c:forEach>                     
				</html:select>
		     </logic:notEmpty>	
	</th>  
	<th nowrap>Observaciones </br>
	<!-- th nowrap>Producir SPD Desde - Hasta</th></br>	<th>Observaciones</br> -->
		<logic:notEmpty name="FicheroResiForm" property="listaResiObservaciones">	
			<html:select property="seleccionResiObservaciones"  value="<%= formulari.getSeleccionResiObservaciones() %>" onchange="javascript:goSubmit();"  styleClass="select_corto">  
				<html:option value="">Todos</html:option>
					<c:forEach items="${FicheroResiForm.listaResiObservaciones}" var="bean"> 
						<option value='${bean}' ${FicheroResiForm.seleccionResiObservaciones == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
    				</c:forEach>                     
			</html:select>
		</logic:notEmpty>	
	</th>
	<th>Variante</br>
		<logic:notEmpty name="FicheroResiForm" property="listaResiVariante">	
			<html:select property="seleccionResiVariante"  value="<%= formulari.getSeleccionResiVariante() %>" onchange="javascript:goSubmit();"  styleClass="select_corto">   
				<html:option value="">Todos</html:option>
					<c:forEach items="${FicheroResiForm.listaResiVariante}" var="bean"> 
						<option value='${bean}' ${FicheroResiForm.seleccionResiVariante == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
    				</c:forEach>                     
			</html:select>
		</logic:notEmpty>	
	</th>	
	<th>Comentarios</br>
			<logic:notEmpty name="FicheroResiForm" property="listaResiComentarios">	
				<html:select property="seleccionResiComentarios"  value="<%= formulari.getSeleccionResiComentarios() %>" onchange="javascript:goSubmit();"  styleClass="select_corto">   
    				<html:option value="">Todos</html:option>
    					<c:forEach items="${FicheroResiForm.listaResiComentarios}" var="bean"> 
			       		 <option value='${bean}' ${FicheroResiForm.seleccionResiComentarios == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
    				</c:forEach>                     
				</html:select>
		     </logic:notEmpty>	
	</th>     
   

 	
 	
 	
	  <th>L</th>	  
	  <th>M</th>	  
	  <th>X</th>	  
	  <th>J</th>	  
	  <th>V</th>	  
	  <th>S</th>	  
	  <th>D</th>	  
		  <!--  th>Marcado Aut?
	 </br>	
	  <logic:notEmpty name="FicheroResiForm" property="listaDiasAutomaticos">	
				<html:select property="seleccionDiasAutomaticos"  value=" formulari.getSeleccionDiasAutomaticos()" onchange="javascript:goSubmit();"> 
    				<html:option value="">Todos</html:option>
    					<c:forEach items="${FicheroResiForm.listaDiasAutomaticos}" var="bean"> 
			       		 <option value='${bean}' ${FicheroResiForm.seleccionDiasAutomaticos == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
    				</c:forEach>                     
				</html:select>
		     </logic:notEmpty>	
	 </th> -->


	
	<logic:notEmpty name="FicheroResiForm" property="listaTomasCabecera">	
	 	<logic:iterate id="dose" name="FicheroResiForm" property="listaTomasCabecera" type= "lopicost.spd.helium.model.Dose" >
	 		<th><bean:write  name="dose" property="name" /></th>
	 	</logic:iterate>
	</logic:notEmpty>	 	
	
	
	   		<bean:define id="campos" name="FicheroResiForm" property="camposPantallaBean" />
	   		
	 <logic:empty name="FicheroResiForm" property="listaTomasCabecera">		
	    		
	   		<logic:equal property="visibleToma1" name="campos" value="true">
				<th>T1</th>
			</logic:equal>
	   		<logic:equal property="visibleToma2" name="campos" value="true">
				<th>T2</th>
			</logic:equal>
	   		<logic:equal property="visibleToma3" name="campos" value="true">
				<th>T3</th>
			</logic:equal>
	   		<logic:equal property="visibleToma4" name="campos" value="true">
				<th>T4</th>
			</logic:equal>
	   		<logic:equal property="visibleToma5" name="campos" value="true">
				<th>T5</th>
			</logic:equal>
	   		<logic:equal property="visibleToma6" name="campos" value="true">
				<th>T6</th>
			</logic:equal>
	   		<logic:equal property="visibleToma7" name="campos" value="true">
				<th>T7</th>
			</logic:equal>
	   		<logic:equal property="visibleToma8" name="campos" value="true">
				<th>T8</th>
			</logic:equal>
	   		<logic:equal property="visibleToma9" name="campos" value="true">
				<th>T9</th>
			</logic:equal>
	   		<logic:equal property="visibleToma10" name="campos" value="true">
				<th>T10</th>
			</logic:equal>
	   		<logic:equal property="visibleToma11" name="campos" value="true">
				<th>T11</th>
			</logic:equal>
	   		<logic:equal property="visibleToma12" name="campos" value="true">
				<th>T12</th>
			</logic:equal>
	   		<logic:equal property="visibleToma13" name="campos" value="true">
				<th>T13</th>
			</logic:equal>
	   		<logic:equal property="visibleToma14" name="campos" value="true">
				<th>T14</th>
			</logic:equal>
	   		<logic:equal property="visibleToma15" name="campos" value="true">
				<th>T15</th>
			</logic:equal>
	   		<logic:equal property="visibleToma16" name="campos" value="true">
				<th>T16</th>
			</logic:equal>
	   		<logic:equal property="visibleToma17" name="campos" value="true">
				<th>T17</th>
			</logic:equal>
	   		<logic:equal property="visibleToma18" name="campos" value="true">
				<th>T18</th>
			</logic:equal>
	   		<logic:equal property="visibleToma19" name="campos" value="true">
				<th>T19</th>
			</logic:equal>
	   		<logic:equal property="visibleToma20" name="campos" value="true">
				<th>T20</th>
			</logic:equal>
	   		<logic:equal property="visibleToma21" name="campos" value="true">
				<th>T21</th>
			</logic:equal>
	   		<logic:equal property="visibleToma22" name="campos" value="true">
				<th>T22</th>
			</logic:equal>
	   		<logic:equal property="visibleToma23" name="campos" value="true">
				<th>T23</th>
			</logic:equal>
	   		<logic:equal property="visibleToma24" name="campos" value="true">
				<th>T24</th>
			</logic:equal>

	 </logic:empty>		



	  <th>Estado linea
	 </br>	
	  <logic:notEmpty name="FicheroResiForm" property="listaEstados">	
				<html:select property="seleccionEstado"  value="<%= formulari.getSeleccionEstado() %>" onchange="javascript:goSubmit();" styleClass="select_5">   
    				<html:option value="">Todos</html:option>
    					<c:forEach items="${FicheroResiForm.listaEstados}" var="bean"> 
			       		 <option value='${bean}' ${FicheroResiForm.seleccionEstado == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
    				</c:forEach>                     
				</html:select>
		     </logic:notEmpty>	
	 </th> 
     </tr>


 	<logic:iterate id="data" name="FicheroResiForm" property="listaFicheroResiDetalleBean" type="lopicost.spd.struts.bean.FicheroResiBean" indexId="position">
		
<c:choose>
    <c:when test="${data.Validar eq 'VALIDAR' || data.incidencia eq 'SI' }">
       <tr class="alertaSustXResi"  >
    </c:when>
    <c:otherwise>
        <tr class="<bean:write name="data" property="spdAccionBolsa" />">
    </c:otherwise>
</c:choose>




	<!-- td><bean:write name="data" property="idDivisionResidencia" /></td -->
 	<!--<td><bean:write name="data" property="fechaHoraProceso" /></td>
     
    <td><bean:write name="data" property="idEstado" /></td>
	-->
     <!-- 
	<td><bean:write name="data" property="idProceso" /></td>
	-->
	 	<td>
 	
			<p class="botons">
				<logic:equal property="editable" name="data" value="true">
					<input type="button"  class="boton-actualizar" onclick="javascript:refrescar('<bean:write name="data" property="oidFicheroResiDetalle" />');"  title="Actualizar sustitución"  />
					<input type="button" class="boton-editar" onclick="javascript:editar('<bean:write name="data" property="oidFicheroResiDetalle" />');"  title="Editar"  />
	  				<input type="button"   class="boton-borrar" onclick="borrar('<bean:write name="data" property="oidFicheroResiDetalle" />');" title="Borrar" />
				</logic:equal>
			</p>
			</td>
	
	<td><bean:write name="data" property="validar" /></td>
 	<td><bean:write name="data" property="incidencia" /></td>
 	
	<td><bean:write name="data" property="mensajesInfo" /> </br>
	
	<span class="rojo"><bean:write name="data" property="mensajesAlerta" /></span></td>
	
	<td><bean:write name="data" property="resiPeriodo" />    <bean:write name="data" property="diasMesConcretos" /> 	<bean:write name="data" property="secuenciaGuide" /></td>

	<td><bean:write name="data" property="spdCnFinal" /></td>
	<td><bean:write name="data" property="spdNombreBolsa" /></td>
	<td><bean:write name="data" property="spdFormaMedicacion" /></td>
	<td><bean:write name="data" property="spdAccionBolsa" /></td>
	
	
	
    <td><bean:write name="data" property="resiCIP" /></td>
<!-- 	<td><a href="javascript:checkCIP('<bean:write name="data" property="resiCIP" />', '<bean:write name="data" property="resiNombrePaciente" />')"><bean:write name="data" property="resiApellido1" /> <bean:write name="data" property="resiApellido2" />, <bean:write name="data" property="resiNombrePaciente" /> </a></td> -->
	<td><bean:write name="data" property="resiApellido1" /> <bean:write name="data" property="resiApellido2" />, <bean:write name="data" property="resiNombrePaciente" /> </td>


	<td><a href="javascript:actualizaSustitucionLite('<bean:write name="FicheroResiForm" property="oidDivisionResidencia" />', '<bean:write name="data" property="idDivisionResidencia" />','<bean:write name="data" property="resiCn" />', '<bean:write name="data" property="resiMedicamento" />')"><bean:write name="data" property="resiCn" /></a></td>
	<td><bean:write name="data" property="resiMedicamento" /></td>
	<td nowrap><bean:write name="data" property="resiInicioTratamiento" /> - <bean:write name="data" property="resiFinTratamiento" />
	

	<c:choose>
	   <c:when test="${data.resiInicioTratamientoParaSPD ne data.resiInicioTratamiento or data.resiInicioTratamientoParaSPD ne data.resiInicioTratamiento}">
	       <br><I> SPD (  <c:out value="${data.resiInicioTratamientoParaSPD}" /> - 	        <c:out value="${data.resiFinTratamientoParaSPD}" />) </I>
	    </c:when>
	</c:choose>


	

	</td>
	<td><html:checkbox disabled="true" name="data" property="resiSiPrecisa" value="X" /></td>
	<!-- td><bean:write name="data" property="fechaDesde" /> - <bean:write name="data" property="fechaHasta" /></td> -->
	<td><bean:write name="data" property="resiObservaciones" /></td>
	<td><bean:write name="data" property="resiVariante" /></td>
	<td><bean:write name="data" property="resiComentarios" /></td>
	<!--<td><bean:write name="data" property="resiVariante" /></td>-->

	<td><html:checkbox disabled="true" name="data" property="resiD1" value="X" /></td>
	<td><html:checkbox disabled="true" name="data" property="resiD2" value="X" /></td>
	<td><html:checkbox disabled="true" name="data" property="resiD3" value="X"/></td>
	<td><html:checkbox disabled="true" name="data" property="resiD4" value="X"/></td>
	<td><html:checkbox disabled="true" name="data" property="resiD5" value="X"/></td>
	<td><html:checkbox disabled="true" name="data" property="resiD6" value="X"/></td>
	<td><html:checkbox disabled="true" name="data" property="resiD7" value="X"/></td>
	

	
	<!-- td><bean:write name="data" property="resiDiasAutomaticos" /></td> -->
	
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

	<!-- td><bean:write name="data" property="resiViaAdministracion" /></td>
	<td><bean:write name="data" property="resultLog" /></td>-->


	<td><bean:write name="data" property="idEstado" /></td>
 	

        </tr>
    </logic:iterate>
 	<input type="button" value="Volver" onclick="javascript:goIndex()"; />

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
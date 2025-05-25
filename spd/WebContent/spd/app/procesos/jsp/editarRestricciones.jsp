<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 	

<html>
<head>
	<jsp:include page="/spd/jsp/global/head.jsp"/>
	<title>Editar Restricción</title>
</head>
<body>
<script>

//función de carga del lookUp
function doLookUpProcesos(){				
	var loc = '/spd/Procesos.do?parameter=lookUp'+ 						//url de llamanda
		'&oidProceso='+document.ProcesosRestriccionesForm.oidProceso.value+
		'&CallBackID=oidProceso'+			  			//Nombre del campo para el valor Id
		'&CallBackNAME=lanzadera';			   			//Nombre del campo para el valor descriptivo

	//Importante que se realice en ventana nueva!!!!
	window.open(loc, 'LookUpProcesos', 'dependent=yes,height=500,width=800,top=50,left=50,resizable=yes,scrollbars=yes' );
}	
//función de carga del lookUp
function doLookUpFarmacias(){				
	var loc = '/spd/Farmacias.do?parameter=lookUp'+ 						//url de llamanda
		'&idFarmacia='+document.ProcesosRestriccionesForm.idFarmacia.value+
		'&CallBackID=idFarmacia'+			  			//Nombre del campo para el valor Id
		'&CallBackNAME=nombreFarmacia';			   			//Nombre del campo para el valor descriptivo

	//Importante que se realice en ventana nueva!!!!
	window.open(loc, 'LookUpFarmacias', 'dependent=yes,height=500,width=800,top=50,left=50,resizable=yes,scrollbars=yes' );
}	


function guardar(oidRestriccion)
{
	var f = document.ProcesosRestriccionesForm;

	/*if(f.tipoRestriccion.value=='HORA' && (f.horasDesde.value=='' || f.horasHasta.value=='' ))
		alert('Falta indicar el rango de horas de restricción');
	else if(f.tipoRestriccion.value=='DIA' && f.valorDia.value < 1)
		alert('Falta indicar el día de restricción ');
	else if(f.tipoRestriccion.value=='FECHA' && f.valorFecha.value=='')
		alert('Falta indicar la fecha de restricción ');
	else*/
	{
		f.parameter.value='guardar';
		//f.ACTIONTODO.value='CONFIRMADO_OK';

		f.submit();
	}
}



</script>
<script type="text/javascript">
function updateVisibility() {
    var t = document.getElementById('tipoRestriccion').value;

    // HORA
    document.getElementById('rowHoras').style.display       =
    document.getElementById('rowHorasHasta').style.display  =
        (t === 'HORA') ? '' : 'none';

    // DIA
    document.getElementById('rowDia').style.display   = (t === 'DIA')   ? '' : 'none';

    // FECHA
    document.getElementById('rowFecha').style.display = (t === 'FECHA') ? '' : 'none';
}

// Ejecuta una vez al cargar para que el formulario salga ya con la vista correcta
window.onload = updateVisibility;
</script>
<script>
<!-- Configuración de Flatpickr -->


  document.addEventListener("DOMContentLoaded", function () {
    flatpickr("#horasDesde", {
      enableTime: true,
      noCalendar: true,
      dateFormat: "H:i",
      time_24hr: true
    });

	flatpickr("#horasHasta", {
	  enableTime: true,
	  noCalendar: true,
	  dateFormat: "H:i",
	  time_24hr: true
	});

	flatpickr("#valorFecha", {
	  dateFormat: "d/m/Y", // Formato de fecha
	  locale: "es",
	  allowInput: true // Permite escribir manualmente
    });
  });
  


	</script>




<bean:define id="procesosRestriccionesForm" name="ProcesosRestriccionesForm" type="lopicost.spd.struts.form.ProcesosRestriccionesForm" />


<h2><bean:write name="procesosRestriccionesForm" property="oidRestriccion" /> 
    <c:choose>
        <c:when test="${procesosRestriccionesForm.oidRestriccion == 0}">Nueva Restricción</c:when>
        <c:otherwise>Editar Restricción</c:otherwise>
    </c:choose>
</h2>

<html:form action="ProcesosRestricciones.do" method="post">
    <html:hidden property="parameter"/>
    <html:hidden property="oidRestriccion" />
    
    	<!-- mostramos mensajes y errores, si existen -->
		<logic:notEmpty name="procesosRestriccionesForm" property="errors">
			<font color="red"><ul>
				<u>Mensaje:</u>
					<logic:iterate id="error" name="procesosRestriccionesForm" property="errors" type="java.lang.String">
							<li><bean:write name="error"/></li>
					</logic:iterate>
				</ul></font>
		</logic:notEmpty>
 			

 <table style="width:70%">
        <tr>
            <td>Proceso:</td>
            <td><html:text property="oidProceso" styleId="oidProceso" readonly="true"/> - <html:text property="lanzadera" styleId="lanzadera" readonly="true"/>
            <a href="#" onclick="javascript:doLookUpProcesos();">Buscar</a>
        </tr>
		<tr>
            <td>Farmacia:</td>
            <td><html:text property="idFarmacia" styleId="idFarmacia" readonly="true"/> - <html:text property="nombreFarmacia" styleId="nombreFarmacia" readonly="true"/>
            <a href="#" onclick="javascript:doLookUpFarmacias();">Buscar</a>
        </tr>
		
		
    <tr>
        <td>Tipo Restricción:</td>
        <td>
			<html:select property="tipoRestriccion" styleId="tipoRestriccion"  onchange="updateVisibility();">
			    <html:option value="HORA">Hora</html:option>
			    <html:option value="DIA">Día</html:option>
			    <html:option value="FECHA">Fecha</html:option>
			</html:select>
        </td>
    </tr>
    <tr id="rowHoras">
        <td>Hora Desde:</td>
        <td>
	        <input type="text" id="horasDesde" name="horasDesde" placeholder="HH:mm" value="${procesosRestriccionesForm.horasDesde}" style="width: 5em;">
        </td>
    </tr>
    <tr id="rowHorasHasta">
        <td>Hora Hasta:</td>
        <td>
       		<input type="text" id="horasHasta" name="horasHasta" placeholder="HH:mm"  value="${procesosRestriccionesForm.horasHasta}" style="width: 5em;">
        </td>
    </tr>
    <tr id="rowDia">
        <td>Día (valorDia):</td>
        <td><html:text property="valorDia" size="2" maxlength="2" /></td>
    </tr>
    <tr id="rowFecha">
        <td>Fecha (dd/MM/yyyy):</td>
        <td>
			<input type="text" id="valorFecha" name="valorFecha" value="<bean:write name='procesosRestriccionesForm' property='valorFecha'/>"
               placeholder="Selecciona fecha de restricción"></td>
       </td>
    </tr>
    <tr>
        <td>Descripción:</td>
        <td><html:text property="descripcion" size="40" maxlength="255" /></td>
    </tr>
    <tr>
        <td>¿Usar restricción?</td>
        <td><html:checkbox property="usarRestriccion" /></td>
    </tr>
    <tr>
        <td colspan="2">
           <input type="button" value="Guardar" onclick="javascript:guardar('<bean:write  name="procesosRestriccionesForm" property="oidRestriccion" />');"  />
            
            <input type="button" value="Cancelar" onclick="window.location='ProcesosRestricciones.do?parameter=list';" />
        </td>
    </tr>
</table>
            
    
	</html:form>
	
</body>
</html>
	    
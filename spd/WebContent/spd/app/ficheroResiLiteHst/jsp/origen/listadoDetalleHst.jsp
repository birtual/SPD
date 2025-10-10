<%@ page language="java" %>
<%@ page import="java.util.*" %>

<%@ page import="lopicost.spd.utils.SPDConstants" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 	
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">

<head>

<bean:define id="formulari" name="FicheroResiForm" type="lopicost.spd.struts.form.FicheroResiForm" />


<table class="blueTable" border="1">
<thead>
	<tr>
		<th></th>
   		<th>
   			<table>
		    	<tr>
			    	<th>C</th>
					<th>I</th>
					<th>R</th>
					<th>D</th>
					<th>P</th>
					<th>S</th>
					<th>G</th>
					
					
				</tr>
				<tr>
				  <th><input type="checkbox" name="filtroNumComprimidos" value="true" ${formulari.filtroNumComprimidos ? 'checked' : ''} onchange="reloadCheckbox('filtroNumComprimidos')" /></th>
				  <th><input type="checkbox" name="filtroRegistroAnterior" value="true" ${formulari.filtroRegistroAnterior ? 'checked' : ''} onchange="reloadCheckbox('filtroRegistroAnterior')" /></th>
				  <th><input type="checkbox" name="filtroRegistroRobot" value="true" ${formulari.filtroRegistroRobot ? 'checked' : ''} onchange="reloadCheckbox('filtroRegistroRobot')" /></th>
				  <th><input type="checkbox" name="filtroValidacionDatos" value="true" ${formulari.filtroValidacionDatos ? 'checked' : ''} onchange="reloadCheckbox('filtroValidacionDatos')" /></th>
				  <th><input type="checkbox" name="filtroPrincipioActivo" value="true" ${formulari.filtroPrincipioActivo ? 'checked' : ''} onchange="reloadCheckbox('filtroPrincipioActivo')" /></th>
				  <th><input type="checkbox" name="filtroNoSustituible" value="true" ${formulari.filtroNoSustituible ? 'checked' : ''} onchange="reloadCheckbox('filtroNoSustituible')" /></th>
				  <th><input type="checkbox" name="filtroDiferentesGtvmp" value="true" ${formulari.filtroDiferentesGtvmp ? 'checked' : ''} onchange="reloadCheckbox('filtroDiferentesGtvmp')" /></th>
				  <th><input type="checkbox" name="filtroUnicoGtvm" value="true" ${formulari.filtroUnicoGtvm ? 'checked' : ''} onchange="reloadCheckbox('filtroUnicoGtvm')" /></th>
	  

			    </tr>
    		</table>
    	</th>
 		<th></th>
		<!-- Validar/Confirmar -->	  
		<th>
		
			<html:select property="seleccionValidar" value="${formulari.seleccionValidar}" onchange="javascript:goSubmit();"> 
			    <html:option value="">Todos</html:option>
	  				<c:forEach items="${FicheroResiForm.listaValidar}" var="bean"> 
	    	   			<c:set var="selected" value="${FicheroResiForm.seleccionValidar == bean ? 'selected' : ''}" />
			       		<option value="${bean}" ${selected}><c:out value="${bean}" /></option>   
	   				</c:forEach>                     
			</html:select>
		</th> 
		<!-- Mensajes -->
		<th>
			<html:select property="seleccionMensajesInfo"  value="${formulari.seleccionMensajesInfo}" onchange="javascript:goSubmit();" styleClass="ancho_17"> 
				<html:option value="">Todas las info</html:option>
	    		<c:forEach items="${FicheroResiForm.listaMensajesInfo}" var="bean"> 
	    			<c:set var="selected" value="${FicheroResiForm.seleccionMensajesInfo == bean ? 'selected' : ''}" />
		       		 <option value="${bean}" ${selected}><c:out value="${bean}" /></option>   
	 			</c:forEach>                     
			</html:select>
			<html:select property="seleccionMensajesResidencia"  value="${formulari.seleccionMensajesResidencia}" onchange="javascript:goSubmit();" styleClass="ancho_17"> 
				<html:option value="">Todas las info para Resi</html:option>
	    		<c:forEach items="${FicheroResiForm.listaMensajesResidencia}" var="bean"> 
	    			<c:set var="selected" value="${FicheroResiForm.seleccionMensajesResidencia == bean ? 'selected' : ''}" />
		       		 <option value="${bean}" ${selected}><c:out value="${bean}" /></option>   
	 			</c:forEach>                     
			</html:select>
			<html:select property="seleccionMensajesAlerta"  value="${formulari.listaMensajesAlerta}"  onchange="javascript:goSubmit();"  styleClass="ancho_17"> 
	   			<span class="red"><html:option value="">Todas las alertas</html:option>
	    		<c:forEach items="${FicheroResiForm.listaMensajesAlerta}" var="bean"> 
	    			<c:set var="selected" value="${FicheroResiForm.seleccionMensajesAlerta == bean ? 'selected' : ''}" />
	   	       		 <option value="${bean}" ${selected}><c:out value="${bean}" /></option>   
	 			</c:forEach>                     
			</html:select>
		</th> 
	
		<!-- Periodo -->
		<th>
			<html:select property="seleccionResiPeriodo"  value="${formulari.seleccionResiPeriodo}" onchange="javascript:goSubmit();" styleClass="ancho_7">   
				<html:option value="">Todos</html:option>
	        		<c:forEach items="${FicheroResiForm.listaResiPeriodo}" var="bean"> 
	   	       		<c:set var="selected" value="${FicheroResiForm.seleccionResiPeriodo == bean ? 'selected' : ''}" />
	   	       		<option value="${bean}" ${selected}><c:out value="${bean}" /></option>   
	 			</c:forEach>                  
			</html:select>
		</th>	
		<!-- spdCnFinal -->
		<th>
			<html:select property="seleccionSpdCnFinal"  value="${formulari.seleccionSpdCnFinal}" onchange="javascript:goSubmit();" styleClass="ancho_5" >    
	    		<html:option value="">Todos</html:option>
	           		<c:forEach items="${FicheroResiForm.listaSpdCnFinal}" var="bean"> 
	   	       		<c:set var="selected" value="${FicheroResiForm.seleccionSpdCnFinal == bean ? 'selected' : ''}" />
	   	       		<option value="${bean}" ${selected}><c:out value="${bean}" /></option>   
	 			</c:forEach>                  
			</html:select>
		</th>

		<!-- Nombre en bolsa -->
		<th >	
			<html:select property="seleccionSpdNombreBolsa"  value="${formulari.seleccionSpdNombreBolsa}" onchange="javascript:goSubmit();"  styleClass="ancho_17">  
				<html:option value="">Todos</html:option>
	          		<c:forEach items="${FicheroResiForm.listaSpdNombreBolsa}" var="bean"> 
	   	       		<c:set var="selected" value="${FicheroResiForm.seleccionSpdNombreBolsa == bean ? 'selected' : ''}" />
	   	     		<option value="${bean}" ${selected}><c:out value="${bean}" /></option>   
	 			</c:forEach>                  
			</html:select>
		</th>
	
		<!-- Forma medicacion -->
		  <th >
			<html:select property="seleccionSpdFormaMedicacion"  value="${formulari.seleccionSpdFormaMedicacion}" onchange="javascript:goSubmit();" styleClass="ancho_10" >  
	  			<html:option value="">Todos</html:option>
	        		<c:forEach items="${FicheroResiForm.listaSpdFormaMedicacion}" var="bean"> 
	   	       		<c:set var="selected" value="${FicheroResiForm.seleccionSpdFormaMedicacion == bean ? 'selected' : ''}" />
	   	     		<option value="${bean}" ${selected}><c:out value="${bean}" /></option>   
	 			</c:forEach>                  
			</html:select>
		</th>
	
		<!-- Acción bolsa -->
		  <th >
			<html:select property="seleccionSpdAccionBolsa"  value="${formulari.seleccionSpdAccionBolsa}" onchange="javascript:goSubmit();" styleClass="ancho_7" >    
	   			<html:option value="">Todos</html:option>
	   				<c:forEach items="${FicheroResiForm.listaSpdAccionBolsa}" var="bean"> 
					 <option value='${bean}' ${FicheroResiForm.seleccionSpdAccionBolsa == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
	   			</c:forEach>                     
			</html:select>
		</th> 	
		
		<!-- Si Precisa-->
		<th>
			<html:select property="seleccionResiSiPrecisa"  value="${formulari.seleccionResiSiPrecisa}" onchange="javascript:goSubmit();"> 
				<html:option value="">Todos</html:option>
	 				<c:forEach items="${FicheroResiForm.listaResiSiPrecisa}" var="bean"> 
					<option value='${bean}' ${FicheroResiForm.seleccionResiSiPrecisa == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
	  				</c:forEach>                     
			</html:select>
	    </th>	 
	    
	    
		<!-- Nombre paciente -->
		<th >
			<html:select property="seleccionResiApellidosNombre"  value="${formulari.seleccionResiApellidosNombre}" onchange="javascript:goSubmit();" styleClass="ancho_23"> 
				<html:option value="">Todos</html:option>
					<c:forEach items="${FicheroResiForm.listaResiApellidosNombre}" var="bean"> 
		       		 <option value='${bean}' ${FicheroResiForm.seleccionResiApellidosNombre == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
	  				</c:forEach>                     
			</html:select>
		</th>	
	
		<!-- CIP -->
		<th >
			<html:select property="seleccionResiCIP"  value="${formulari.seleccionResiCIP}" onchange="javascript:goSubmit();" styleClass="ancho_10" >   
	   			<html:option value="">Todos</html:option>
	 				<c:forEach items="${FicheroResiForm.listaResiCIP}" var="bean"> 
					 <option value='${bean}' ${FicheroResiForm.seleccionResiCIP == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
	  				</c:forEach>                     
			</html:select>
	    </th>	
	
	 	<!-- CN -->
		<th nowrap>
			<html:select property="seleccionResiCn"  value="${formulari.seleccionResiCn}" onchange="javascript:goSubmit();"   styleClass="ancho_5">  
	    		<html:option value="">Todos</html:option>
					<c:forEach items="${FicheroResiForm.listaResiCn}" var="bean"> 
					<option value='${bean}' ${FicheroResiForm.seleccionResiCn == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
	  				</c:forEach>                     
			</html:select>
	    </th>	
	
		<!-- Medicamento resi -->
		<th nowrap>
			<html:select property="seleccionResiMedicamento"  value="${formulari.seleccionResiMedicamento}" onchange="javascript:goSubmit();"   styleClass="ancho_10" >   
				<html:option value="">Todos</html:option>
					<c:forEach items="${FicheroResiForm.listaResiMedicamento}" var="bean"> 
					<option value='${bean}' ${FicheroResiForm.seleccionResiMedicamento == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
	  				</c:forEach>                     
			</html:select>
	    </th>	
	
		<!-- Variante -->
		<th nowrap>
			<html:select property="seleccionResiVariante"  value="${formulari.seleccionResiVariante}" onchange="javascript:goSubmit();"  styleClass="ancho_17" >   
				<html:option value="">Todos</html:option>
	 				<c:forEach items="${FicheroResiForm.listaResiVariante}" var="bean"> 
					<option value='${bean}' ${FicheroResiForm.seleccionResiVariante == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
	  				</c:forEach>                     
			</html:select>
		</th>	
	
		<!-- Observaciones -->
		<th>
		<!-- th >Producir SPD Desde - Hasta</th></br>	<th>Observaciones</br> -->
			<html:select property="seleccionResiObservaciones"  value="${formulari.seleccionResiObservaciones}" onchange="javascript:goSubmit();"  styleClass="ancho_17">  
				<html:option value="">Todos</html:option>
	 				<c:forEach items="${FicheroResiForm.listaResiObservaciones}" var="bean"> 
					<option value='${bean}' ${FicheroResiForm.seleccionResiObservaciones == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
	  				</c:forEach>                     
			</html:select>
		</th>		
		
		<!-- Comentarios -->
		<th nowrap>
			<html:select property="seleccionResiComentarios"  value="${formulari.seleccionResiComentarios}" onchange="javascript:goSubmit();"   styleClass="ancho_17" >   
	    		<html:option value="">Todos</html:option>
	 				<c:forEach items="${FicheroResiForm.listaResiComentarios}" var="bean"> 
					<option value='${bean}' ${FicheroResiForm.seleccionResiComentarios == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
	   				</c:forEach>                     
				</html:select>
		</th>  
		
	<c:choose>
 	   <c:when test="${not empty FicheroResiForm.listaResiTipoMedicacion}">
        <th nowrap>
            <html:select property="seleccionResiTipoMedicacion" value="${formulari.seleccionResiTipoMedicacion}" onchange="javascript:goSubmit();"  styleClass="ancho_17" >   
                <html:option value="">Todos</html:option>
                <c:forEach items="${FicheroResiForm.listaResiTipoMedicacion}" var="bean"> 
                    <option value='${bean}' ${FicheroResiForm.seleccionResiTipoMedicacion == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
                </c:forEach>                     
            </html:select>
        </th>
  	  </c:when>
	</c:choose>
		
		<!-- inicio-fin tratamiento -->
		<th nowrap></th>

		<!-- Días semana -->
		<th></th><th></th><th></th><th></th><th></th><th></th><th></th>	  
	

		<!-- Tomas día -->
	<logic:notEmpty name="FicheroResiForm" property="listaTomasCabecera">	
	 	<logic:iterate id="dose" name="FicheroResiForm" property="listaTomasCabecera" type= "lopicost.spd.helium.model.Dose" >
		 		<th class="<bean:write  name="dose" property="tipo" />"></th>
	 		
	 	</logic:iterate>
	</logic:notEmpty>	 	
		
		
		 <bean:define id="campos" name="FicheroResiForm" property="camposPantallaBean" />
		   		
		<logic:empty name="FicheroResiForm" property="listaTomasCabecera">		
		   		<logic:equal property="visibleToma1" name="campos" value="true"><th>T1</th></logic:equal>
		   		<logic:equal property="visibleToma2" name="campos" value="true"><th>T2</th></logic:equal>
		   		<logic:equal property="visibleToma3" name="campos" value="true"><th>T3</th></logic:equal>
		   		<logic:equal property="visibleToma4" name="campos" value="true"><th>T4</th></logic:equal>
		   		<logic:equal property="visibleToma5" name="campos" value="true"><th>T5</th></logic:equal>
		   		<logic:equal property="visibleToma6" name="campos" value="true"><th>T6</th></logic:equal>
		   		<logic:equal property="visibleToma7" name="campos" value="true"><th>T7</th></logic:equal>
		   		<logic:equal property="visibleToma8" name="campos" value="true"><th>T8</th></logic:equal>
		   		<logic:equal property="visibleToma9" name="campos" value="true"><th>T9</th></logic:equal>
		   		<logic:equal property="visibleToma10" name="campos" value="true"><th>T10</th></logic:equal>
		   		<logic:equal property="visibleToma11" name="campos" value="true"><th>T11</th></logic:equal>
		   		<logic:equal property="visibleToma12" name="campos" value="true"><th>T12</th></logic:equal>
		   		<logic:equal property="visibleToma13" name="campos" value="true"><th>T13</th></logic:equal>
		   		<logic:equal property="visibleToma14" name="campos" value="true"><th>T14</th></logic:equal>
		   		<logic:equal property="visibleToma15" name="campos" value="true"><th>T15</th></logic:equal>
		   		<logic:equal property="visibleToma16" name="campos" value="true"><th>T16</th></logic:equal>
		   		<logic:equal property="visibleToma17" name="campos" value="true"><th>T17</th></logic:equal>
		   		<logic:equal property="visibleToma18" name="campos" value="true"><th>T18</th></logic:equal>
		   		<logic:equal property="visibleToma19" name="campos" value="true"><th>T19</th></logic:equal>
		   		<logic:equal property="visibleToma20" name="campos" value="true"><th>T20</th></logic:equal>
		   		<logic:equal property="visibleToma21" name="campos" value="true"><th>T21</th></logic:equal>
		   		<logic:equal property="visibleToma22" name="campos" value="true"><th>T22</th></logic:equal>
		   		<logic:equal property="visibleToma23" name="campos" value="true"><th>T23</th></logic:equal>
		   		<logic:equal property="visibleToma24" name="campos" value="true"><th>T24</th></logic:equal>
		 </logic:empty>		
	
	
		<!-- Estado linea -->
		<th>
			<html:select property="seleccionEstado"  value="${formulari.seleccionEstado}"  onchange="javascript:goSubmit();" styleClass="ancho_10">   
				<html:option value="">Todos</html:option>
	 				<c:forEach items="${FicheroResiForm.listaEstados}" var="bean"> 
					<option value='${bean}' ${FicheroResiForm.seleccionEstado == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
	   				</c:forEach>                     
			</html:select>
		</th>  
		
		<!-- editado -->
		<th></th>
     </tr>



 	<tr>
 		<th>nº fila			<a href="javascript:void(0);" onclick="ordenarPor('row asc')" class="boton-up"></a><a href="javascript:void(0);" onclick="ordenarPor('row desc')" class="boton-down"></a></th>
		<th>alertas</th>
		<th>acciones</th>
		<!-- validar -->	  
		<th class="columna-ancho">Validar/confirmar					<a href="javascript:void(0);" onclick="ordenarPor('validar asc')" class="boton-up"></a><a href="javascript:void(0);" onclick="ordenarPor('validar desc')" class="boton-down"></a></th> 


		<!-- mensajesInfo + mensajesAlerta-->
		<th class="infoSelect"> Mensajes (Info /<span class="textoRojo">alerta</span> /<span class="textoAzul">resi</span> )<a href="javascript:void(0);" onclick="ordenarPor('mensajesAlerta+mensajesResidencia+mensajesInfo  asc')" class="boton-up"></a><a href="javascript:void(0);" onclick="ordenarPor('mensajesAlerta+mensajesResidencia+mensajesInfo desc')" class="boton-down"></a></th> 
	
		<!-- Periodo -->
		<th>Periodo<a href="javascript:void(0);" onclick="ordenarPor('resiPeriodo asc')" class="boton-up"></a><a href="javascript:void(0);" onclick="ordenarPor('resiPeriodo desc')" class="boton-down"></a></th>	

		<!-- spdCnFinal -->
		<th>spdCnFinal						<a href="javascript:void(0);" onclick="ordenarPor('spdCnFinal asc')" class="boton-up"></a><a href="javascript:void(0);" onclick="ordenarPor('spdCnFinal desc')" class="boton-down"></a></th>

		<!-- Nombre en bolsa -->
		<th >Nombre en bolsa			<a href="javascript:void(0);" onclick="ordenarPor('spdNombreBolsa asc')" class="boton-up"></a><a href="javascript:void(0);" onclick="ordenarPor('spdNombreBolsa desc')" class="boton-down"></a></th>
	
		<!-- Forma medicacion -->
		<th >Forma medicacion			<a href="javascript:void(0);" onclick="ordenarPor('spdFormaMedicacion asc')" class="boton-up"></a><a href="javascript:void(0);" onclick="ordenarPor('spdFormaMedicacion desc')" class="boton-down"></a></th>
	
		<!-- Acción bolsa -->
		<th >Acción bolsa				<a href="javascript:void(0);" onclick="ordenarPor('spdAccionBolsa asc')" class="boton-up"></a><a href="javascript:void(0);" onclick="ordenarPor('spdAccionBolsa desc')" class="boton-down"></a></th> 	
	
		<!-- Si Precisa-->
		<th>Si Precisa</th>	 
	    	    
		<!-- Nombre paciente -->
		<th >Nombre paciente			<a href="javascript:void(0);" onclick="ordenarPor('resiApellidosNombre asc')" class="boton-up"></a><a href="javascript:void(0);" onclick="ordenarPor('resiApellidosNombre desc')" class="boton-down"></a></th>	
	
		<!-- CIP -->
		<th >CIP						<a href="javascript:void(0);" onclick="ordenarPor('resiCIP asc')" class="boton-up"></a><a href="javascript:void(0);" onclick="ordenarPor('resiCIP desc')" class="boton-down"></a></th>	
	
	 	<!-- CN -->
		<th nowrap>CN						<a href="javascript:void(0);" onclick="ordenarPor('resiCn asc')" class="boton-up"></a><a href="javascript:void(0);" onclick="ordenarPor('resiCn desc')" class="boton-down"></a>
	    </th>	
	
		<!-- Medicamento resi -->
		<th >Medicamento resi			<a href="javascript:void(0);" onclick="ordenarPor('resiMedicamento asc')" class="boton-up"></a><a href="javascript:void(0);" onclick="ordenarPor('resiMedicamento  desc')" class="boton-down"></a>
	    </th>	
	
		<!-- Variante -->
		<th >Variante					<a href="javascript:void(0);" onclick="ordenarPor('resiVariante asc')" class="boton-up"></a><a href="javascript:void(0);" onclick="ordenarPor('resiVariante desc')" class="boton-down"></a></th>	
	
		<!-- resiObservaciones -->
		<th >Observaciones				<a href="javascript:void(0);" onclick="ordenarPor('resiObservaciones asc')" class="boton-up"></a><a href="javascript:void(0);" onclick="ordenarPor('resiObservaciones desc')" class="boton-down"></a></th>	
		
		
		<!-- Comentarios -->
		<th >Comentarios				<a href="javascript:void(0);" onclick="ordenarPor('resiComentarios asc')" class="boton-up"></a><a href="javascript:void(0);" onclick="ordenarPor('resiComentarios desc')" class="boton-down"></a></th>
		
<c:choose>
    <c:when test="${not empty FicheroResiForm.listaResiTipoMedicacion}">
        <th > Tipo medicación 		<a href="javascript:void(0);" onclick="ordenarPor('resiTipoMedicacion asc')" class="boton-up"></a><a href="javascript:void(0);" onclick="ordenarPor('resiTipoMedicacion desc')" class="boton-down"></a></th>
    </c:when>
</c:choose>
		
		<!-- inicio-fin tratamiento -->
		<th >inicio-fin tratamiento
	 	
		<!-- Días de la semana -->
		<th>L</th><th>M</th><th>X</th><th>J</th><th>V</th><th>S</th><th>D</th>	  
	
		<!-- Tomas del día -->
		<logic:notEmpty name="FicheroResiForm" property="listaTomasCabecera">	
		 	<logic:iterate id="dose" name="FicheroResiForm" property="listaTomasCabecera" type= "lopicost.spd.helium.model.Dose" >
		 		<th><bean:write  name="dose" property="name" /></th>
		 	</logic:iterate>
		</logic:notEmpty>	 	
	
	
		<!-- Estado linea -->
		<th>Estado linea</th>
		<th> Editado</th>    
	
     </tr>
</thead>
  
  <tbody>   

     
<!--  Inicio del bucle para mostrar las filas -->
<logic:iterate id="data" name="FicheroResiForm" property="listaFicheroResiDetalleBean" type="lopicost.spd.struts.bean.FicheroResiBean" indexId="position">



	<c:choose>
	
	<c:when test="${fn:contains(data.tipoRegistro, 'GUIDE') and formulari.filtroMostrarGeneradosSeq eq 'NO'}">
	</c:when>
	 <c:otherwise>
	 
	<c:choose>
	    <c:when test="${data.validar eq 'VALIDAR' || data.incidencia eq 'SI' || data.confirmar eq 'CONFIRMAR'}">
	       <tr class="alertaSustXResi"  >
	    </c:when>
	     <c:otherwise>
	        <tr class="<bean:write name="data" property="spdAccionBolsa" />">
	      </c:otherwise>
	</c:choose>

 	<td>
		<p class="botons">
			<logic:present property="detalleRow" name="data" >
	  			<a href="javascript:abrirVentanaRow('<bean:write name="data" property="row" />', '<bean:write name="data" property="detalleRow" />');"  >
	  		</logic:present>
	  			<bean:write name="data" property="row" />
			<logic:present property="detalleRow" name="data" >
	  			</a>
	  		</logic:present>
 		</p>
 	</td>
	<td class="gris_claro">
		<logic:equal property="editable" name="data" value="true">
		<table>
        	<tr>
<% 
// C - Control de cuadre de compimidos
    String claseCSS_1 = "";
	String altText_1 ="";
    if (data != null) {
        if (data!=null && data.getControlNumComprimidos()!=null && data.getControlNumComprimidos().equals(SPDConstants.CTRL_NCOMPRIMIDOS_IGUAL)) {
        	claseCSS_1 = "verde";
        	altText_1 = "Coincide el número de comprimidos resi-robot";
        } else if (data!=null && data.getControlNumComprimidos()!=null && data.getControlNumComprimidos().equals(SPDConstants.CTRL_NCOMPRIMIDOS_DIFERENTE)) {
        	claseCSS_1 = "rojo";
        	altText_1 = "ALERTA: número de comprimidos diferente resi-robot";
        }
        else {
        	claseCSS_1 = "naranja";
        	altText_1 = "No detectado";
        } 
    } 
 // I - Control de registro anterior    
	String claseCSS_2 = "";
	String altText_2= "";
	
    if (data != null && data.getControlRegistroAnterior()!=null && !data.getControlRegistroAnterior().equals("")) {
        if (data.getControlRegistroAnterior().equals(SPDConstants.CTRL_REGISTRO_ANTERIOR_RI_SI)) {
        	claseCSS_2 = "verde";
        	altText_2 = "Registro reutilizado OK";
        } else if (data.getControlRegistroAnterior().equals(SPDConstants.CTRL_REGISTRO_ANTERIOR_RD_SI)) {
        	claseCSS_2 = "naranja";
        	altText_2 = "REVISAR registro, la salida es igual que la anterior, pero los datos de la resi son diferentes.";
        } else if (data.getControlRegistroAnterior().equals(SPDConstants.CTRL_REGISTRO_ANTERIOR_RI_SD)) {
        	claseCSS_2 = "rojo";
        	altText_2 = "ALERTA: Revisar bien el tratamiento. Se envía diferente a la anterior producción";
        } else if (data.getControlRegistroAnterior().equals(SPDConstants.CTRL_REGISTRO_ANTERIOR_RD_SD)) {
        	claseCSS_2 = "azul";
        	altText_2 = "Registro nuevo";
    	} 
    }
    else {
    	claseCSS_2 = "azul";
    	altText_2 = "Registro nuevo2";
    }
    
 // R - Control de registro robot
    String claseCSS_3 = "";
	String altText_3 ="";
    if (data != null) {
        if (data!=null && data.getControlRegistroRobot()!=null && data.getControlRegistroRobot().equals(SPDConstants.CTRL_ROBOT_SE_ENVIA_A_ROBOT)) {
        	claseCSS_3 = "verde";
        	altText_3 = "Se envía a robot";
        } else if (data!=null && data.getControlRegistroRobot()!=null && data.getControlRegistroRobot().equals(SPDConstants.CTRL_ROBOT_NO_SE_ENVIA)) {
        	claseCSS_3 = "gris";
        	altText_3 = "No se envía a robot";
        } else {
        	claseCSS_3 = "blanco";
        	altText_3 = "Revisar acción en bolsa del tratamiento";
        	}
   
    }

 // D - Control de registro a validar datos
    String claseCSS_4 = "";
	String altText_4 ="";
    if (data != null) {
        if (data!=null && data.getControlValidacionDatos()!=null && data.getControlValidacionDatos().equals(SPDConstants.CTRL_VALIDAR_NO)) {
        	claseCSS_4 = "verde";
        	altText_4 = "Registro ok";
        } else if (data!=null && data.getControlValidacionDatos()!=null && data.getControlValidacionDatos().equals(SPDConstants.CTRL_VALIDAR_ALERTA)) {
        	claseCSS_4 = "naranja";
        	altText_4 = "Validar registro";
        } else {
        	claseCSS_4 = "blanco";
        	altText_4 = "No detectado";
        	}
 
    }
    
// P - Control de principio activo  
    String claseCSS_5 = "";
	String altText_5 ="";
    if (data != null) {
        if (data!=null && data.getControlPrincipioActivo()!=null && data.getControlPrincipioActivo().equals(SPDConstants.CTRL_PRINCIPIO_ACTIVO_NO_ALERTA)) {
        	claseCSS_5 = "verde";
        	altText_5 = "Registro ok";
        } else if (data!=null && data.getControlPrincipioActivo()!=null && data.getControlPrincipioActivo().equals(SPDConstants.CTRL_PRINCIPIO_ACTIVO_ALERTA)) {
        	claseCSS_5 = "amarillo";
        	altText_5 = "Control medicamento por principio activo ";
        } else {
        	claseCSS_5 = "blanco";
        	altText_5 = "No detectado";
        	}
    }
// S - Control de medicamento sustituible  
    String claseCSS_6 = "";
	String altText_6 ="";
    if (data != null) {
        if (data!=null && data.getControlNoSustituible()!=null && data.getControlNoSustituible().equals(SPDConstants.CTRL_SUSTITUIBLE_NOALERTA)) {
        	claseCSS_6 = "verde";
        	altText_6 = "Registro ok";
        } else if (data!=null && data.getControlNoSustituible()!=null && data.getControlNoSustituible().equals(SPDConstants.CTRL_SUSTITUIBLE_ALERTA)) {
        	claseCSS_6 = "rojo";
        	altText_6 = "Control medicamento NO sustituible ";
        } else {
        	claseCSS_6 = "blanco";
        	altText_6 = "No detectado";
        	}
    
    }
    
	
// G - Control de GTVMP iguales  
    String claseCSS_7 = "";
	String altText_7 ="";
    if (data != null) {
        if (data!=null && data.getControlDiferentesGtvmp()!=null && data.getControlDiferentesGtvmp().equals(SPDConstants.CTRL_DIFERENTE_GTVMP_OK)) {
        	claseCSS_7 = "verde";
        	altText_7 = "GTVMP ok";
        } else if (data!=null && data.getControlDiferentesGtvmp()!=null && data.getControlDiferentesGtvmp().equals(SPDConstants.CTRL_DIFERENTE_GTVMP_ALERTA)) {
        	claseCSS_7 = "rojo";
        	altText_7 = "Alerta: los GTVMP no coinciden";
        } else {
        	claseCSS_7 = "blanco";
        	altText_7 = "No detectado"; 
        	}
  
    }
// V - Control de Varios GTVM en el tratamiento de un residente 
    String claseCSS_8 = "";
	String altText_8 ="";
    if (data != null) {
        if (data!=null && data.getControlUnicoGtvm()!=null && data.getControlUnicoGtvm().equals(SPDConstants.CTRL_UNICO_GTVM_OK)) {
        	claseCSS_8 = "verde";
        	altText_8 = "Ok, no se repite el mismo principio activo en el tratamiento";
        } else if (data!=null && data.getControlUnicoGtvm()!=null && data.getControlUnicoGtvm().equals(SPDConstants.CTRL_UNICO_GTVM_ALERTA)) {
        	claseCSS_8 = "rojo";
        	altText_8 = "Alerta: El tratamiento del residente tiene varios medicamentos de un mismo principio activo";
        } else {
        	claseCSS_8 = "blanco";
        	altText_8 = "No detectado"; 
        	}
  
    }	
	
%>

<td class="<%= claseCSS_1 %>"  title="<%= altText_1 %>">C</td>
<td class="<%= claseCSS_2 %>"  title="<%= altText_2 %>">I</td>
<td class="<%= claseCSS_3 %>"  title="<%= altText_3 %>">R</td>
<td class="<%= claseCSS_4 %>"  title="<%= altText_4 %>">D</td>
<td class="<%= claseCSS_5 %>"  title="<%= altText_5 %>">P</td>
<td class="<%= claseCSS_6 %>"  title="<%= altText_6 %>">S</td>
<td class="<%= claseCSS_7 %>"  title="<%= altText_7 %>">G</td>
<td><a href="javascript:infoAlertas('<bean:write name="data" property="oidFicheroResiDetalle" />');"  > INFO</a></td>
            		</tr>
            	</table>
            </td>

    </logic:equal>

		      
  	<td nowrap>
				<i>&nbsp;&nbsp;&nbsp;</i>
	</td>    
	<td >

	<c:if  test="${data.validar eq 'VALIDAR' ||  data.validar eq 'VALIDADO'}">
		<c:out value='${data.validar}' />
	      	<br>---------------<br>
	</c:if>
	
	<c:if  test="${data.confirmar eq 'CONFIRMAR'}">
		CONFIRMAR
	</c:if>
	<c:if  test="${data.confirmar eq 'CONFIRMADO'}">
		CONFIRMADO
	</c:if>
	<c:if  test="${data.controlPrincipioActivo eq 'ALERTA'}">
		<!---no me deja poner el 3 como SPDConstants.CTRL_PRINCIPIO_ACTIVO_N_VALIDACIONES, parece que no lo interpreta -->
		<logic:lessThan name="data" property="confirmaciones" value="3">
		   	(<c:out value='${data.confirmaciones+1}' /> de 3)
		</logic:lessThan>
	</c:if>							

	<logic:notEqual property="editable" name="data" value="true">
    	REGISTRO CREADO POR SECUENCIA
	</logic:notEqual>
	
	</td>

	
	
 	<!--<td><bean:write name="data" property="incidencia" /></td>-->
	<td title="<c:out value='${data.mensajesAlerta}' /> <c:out value='${data.mensajesInfo}' /> <c:out value='${data.mensajesResidencia}' />"><bean:write name="data" property="mensajesInfo" /> </br>
		<span class="textoRojo"><bean:write name="data" property="mensajesAlerta" /></span>
		<span class="textoAzul"><bean:write name="data" property="mensajesResidencia" /></span>
	</td>
	<td><bean:write name="data" property="resiPeriodo" />    <bean:write name="data" property="diasMesConcretos" /> 	<bean:write name="data" property="secuenciaGuide" /></td>
	<td><bean:write name="data" property="spdCnFinal" /></td>
	<td><bean:write name="data" property="spdNombreBolsa" /></td>
	<td><bean:write name="data" property="spdFormaMedicacion" /></td>
	<td><bean:write name="data" property="spdAccionBolsa" /></td>
	<td><html:checkbox disabled="true" name="data" property="resiSiPrecisa" value="X" /></td> 
	<td><bean:write name="data" property="resiApellidosNombre" /></td>
    <td><bean:write name="data" property="resiCIP" /></td>

	<td><bean:write name="data" property="resiCn" /></td>
	<td><bean:write name="data" property="resiMedicamento" /></td>
	<td><bean:write name="data" property="resiVariante" /></td>
	
	<td>
	<c:choose>
		<c:when test="${fn:contains(data.mensajesInfo, '(INTERNO FARMACIA)')}">
		  <span class="textoRojo"><bean:write name="data" property="resiObservaciones" />  </span>
     	</c:when>
        <c:otherwise>
        	<bean:write name="data" property="resiObservaciones" />
     	</c:otherwise>
    </c:choose>
    </td>
  
  
  
	<td><bean:write name="data" property="resiComentarios" /></td>
	<!-- <td><bean:write name="data" property="resiTipoMedicacion" /></td> -->
<c:choose>
  	<c:when test="${not empty FicheroResiForm.listaResiTipoMedicacion}">
        <td><bean:write name="data" property="resiTipoMedicacion" />
    </c:when>
</c:choose>


	<td nowrap><bean:write name="data" property="resiInicioTratamiento" /> - <bean:write name="data" property="resiFinTratamiento" />
			<c:choose>
		  <c:when test="${not empty data.resiInicioTratamientoParaSPD and (data.resiInicioTratamientoParaSPD ne data.resiInicioTratamiento or data.resiFinTratamientoParaSPD ne data.resiFinTratamiento)}">
		       <br><I> SPD (  <c:out value="${data.resiInicioTratamientoParaSPD}" /> - 	        <c:out value="${data.resiFinTratamientoParaSPD}" />) </I>
		    </c:when>
		</c:choose>
	</td>

	<td><html:checkbox disabled="true" name="data" property="resiD1" value="X" /></td>
	<td><html:checkbox disabled="true" name="data" property="resiD2" value="X" /></td>
	<td><html:checkbox disabled="true" name="data" property="resiD3" value="X"/></td>
	<td><html:checkbox disabled="true" name="data" property="resiD4" value="X"/></td>
	<td><html:checkbox disabled="true" name="data" property="resiD5" value="X"/></td>
	<td><html:checkbox disabled="true" name="data" property="resiD6" value="X"/></td>
	<td><html:checkbox disabled="true" name="data" property="resiD7" value="X"/></td>

	   		<logic:equal property="visibleToma1" name="campos" value="true">
				<td class="toma"><bean:write name="data" property="resiToma1" /></td>
			</logic:equal>
	   		<logic:equal property="visibleToma2" name="campos" value="true">
				<td class="toma"><bean:write name="data" property="resiToma2" /></td>
			</logic:equal>
	   		<logic:equal property="visibleToma3" name="campos" value="true">
				<td class="toma"><bean:write name="data" property="resiToma3" /></td>
			</logic:equal>
	   		<logic:equal property="visibleToma4" name="campos" value="true">
				<td class="toma"><bean:write name="data" property="resiToma4" /></td>
			</logic:equal>
	   		<logic:equal property="visibleToma5" name="campos" value="true">
				<td class="toma"><bean:write name="data" property="resiToma5" /></td>
			</logic:equal>
	   		<logic:equal property="visibleToma6" name="campos" value="true">
				<td class="toma"><bean:write name="data" property="resiToma6" /></td>
			</logic:equal>
	   		<logic:equal property="visibleToma7" name="campos" value="true">
				<td class="toma"><bean:write name="data" property="resiToma7" /></td>
			</logic:equal>
	   		<logic:equal property="visibleToma8" name="campos" value="true">
				<td class="toma"><bean:write name="data" property="resiToma8" /></td>
			</logic:equal>
	   		<logic:equal property="visibleToma9" name="campos" value="true">
				<td class="toma"><bean:write name="data" property="resiToma9" /></td>
			</logic:equal>
	   		<logic:equal property="visibleToma10" name="campos" value="true">
				<td class="toma"><bean:write name="data" property="resiToma10" /></td>
			</logic:equal>
	   		<logic:equal property="visibleToma11" name="campos" value="true">
				<td class="toma"><bean:write name="data" property="resiToma11" /></td>
			</logic:equal>
	   		<logic:equal property="visibleToma12" name="campos" value="true">
				<td class="toma"><bean:write name="data" property="resiToma12" /></td>
			</logic:equal>
	   		<logic:equal property="visibleToma13" name="campos" value="true">
				<td class="toma"><bean:write name="data" property="resiToma13" /></td>
			</logic:equal>
	   		<logic:equal property="visibleToma14" name="campos" value="true">
				<td class="toma"><bean:write name="data" property="resiToma14" /></td>
			</logic:equal>
	   		<logic:equal property="visibleToma15" name="campos" value="true">
				<td class="toma"><bean:write name="data" property="resiToma15" /></td>
			</logic:equal>
	   		<logic:equal property="visibleToma16" name="campos" value="true">
				<td class="toma"><bean:write name="data" property="resiToma16" /></td>
			</logic:equal>
	   		<logic:equal property="visibleToma17" name="campos" value="true">
				<td class="toma"><bean:write name="data" property="resiToma17" /></td>
			</logic:equal>
	   		<logic:equal property="visibleToma18" name="campos" value="true">
				<td class="toma"><bean:write name="data" property="resiToma18" /></td>
			</logic:equal>
	   		<logic:equal property="visibleToma19" name="campos" value="true">
				<td class="toma"><bean:write name="data" property="resiToma19" /></td>
			</logic:equal>
	   		<logic:equal property="visibleToma20" name="campos" value="true">
				<td class="toma"><bean:write name="data" property="resiToma20" /></td>
			</logic:equal>
	   		<logic:equal property="visibleToma21" name="campos" value="true">
				<td class="toma"><bean:write name="data" property="resiToma21" /></td>
			</logic:equal>
	   		<logic:equal property="visibleToma22" name="campos" value="true">
				<td class="toma"><bean:write name="data" property="resiToma22" /></td>
			</logic:equal>
	   		<logic:equal property="visibleToma23" name="campos" value="true">
				<td class="toma"><bean:write name="data" property="resiToma23" /></td>
			</logic:equal>
	   		<logic:equal property="visibleToma24" name="campos" value="true">
				<td class="toma"><bean:write name="data" property="resiToma24" /></td>
			</logic:equal>

	<td><bean:write name="data" property="idEstado" /></td>
	<td><bean:write name="data" property="editado" /></td>

        </tr>
        
    </c:otherwise>
	</c:choose>
	
	
    </logic:iterate>

 </tbody>   
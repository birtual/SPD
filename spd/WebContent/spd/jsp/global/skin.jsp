<%@ page language="java" %>

<jsp:include page="/spd/common/jsp/cabecera.jsp"></jsp:include>
<script>

	
	function goIndex()
	{
		
		alert('<%=request.getSession().getAttribute("nivel")%>');
		if('<%=request.getSession().getAttribute("nivel")%>'=='1')
		{
			document.location.href='/spd/spd/jsp/login/indexAdmin.jsp';
		}
		else if('<%=request.getSession().getAttribute("nivel")%>'=='2')
		{
			document.location.href='/spd/spd/jsp/login/indexFarm.jsp';
		}
		else 
		{
			document.location.href='/spd/spd/jsp/login/index.jsp';
		}
	}	
	
	
</script>




<%
	String usuario=null;
	
	if(request.getSession()!=null && (String)request.getSession().getAttribute("usuario")!=null)
 	{
 	    usuario=(String)request.getSession().getAttribute("usuario");
 	   out.println("<a href='/spd/index.jsp?cerrar=true'><h5>Cerrar sessión " + usuario + "</h5></a>     ");	
 	}
	else
	{

		out.print("<script>location.replace('/spd/index.jsp');</script>");
	}
%>


<!-- script type="text/javascript" src="spd/js/validator.js" language="javascript"></script-->
<meta Http-Equiv="Cache-Control" Content="no-cache" />
<meta Http-Equiv="Pragma" Content="no-cache" />
<meta Http-Equiv="Expires" Content="0" /> 
<meta http-equiv="Content-Type" pageEncoding="text/html; charset=UTF-8" %>




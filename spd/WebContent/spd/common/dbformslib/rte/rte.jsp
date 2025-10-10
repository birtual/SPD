<%@ page  import="java.util.ResourceBundle" %>
<%@ page  import="java.text.SimpleDateFormat" %>
<%@ page  import="java.util.Hashtable" %>
<%@ page  import="java.util.Enumeration" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<%! 
	//Rutinilla para pillar literales
	public static ResourceBundle literals=null;
	public static String getString(String literal){
		try{
			if( literals==null ) literals = ResourceBundle.getBundle("dbFormsLiterals");
			return literals.getString(literal);
		}catch(Exception e){
			return "!["+literal+"]";
		}
	}
%>
<html>
<head>
<link href="../../dbforms.css" rel="stylesheet"/>

<script language="JavaScript" type="text/javascript" src="<%=getString("scriptRTE")%>"></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=getString("titleRTE")%></title>

<style type="text/css">
.btnImage {cursor: pointer; cursor: hand;}
</style>

</head>
<body class="clsPageBody">
<table  border="0" cellspacing="0" width = "100%" align="center">
  <tr>
	<td style="width:100%"  bgcolor="#EFF7EE">
 <table bgcolor="#EFF7EE" border="0" width = "100%" align="center">		
	<tr>
		<td class="clsMainMenu" bgcolor="#7BBA69">
			<strong><%=getString("titleRTE")%></strong>
		</td>
	</tr>
 </table>
	</td>
  </tr>
  <tr>
	<td style="width:100%"  bgcolor="#EFF7EE" class="clsSearchDataTableRow" >
<script>
var text = new richText('edit','<%=getString("pathImagenes")%>');
text.setValue(opener.<%=request.getParameter("getFunction")%>());
text.crearIFrame();
text.crear();
</script>
	</td>
  </tr>
</table>

<form name="form" action="">
<table>
	<tr>
		<td>
			<input type="submit"  value="<%=getString("guardar")%>"  class="clsButtonStyle"
				onClick="javascript:opener.<%=request.getParameter("setFunction")%>(text.getValue());self.close();"  
				name="<%=getString("guardar")%>"/>
		</td>
		<td>
			<input type="submit"  value="<%=getString("tancar")%>"  class="clsButtonStyle" 
				onClick="javascript:self.close();" name="ac_goto_5"/>
		</td>
	</tr>
<table>
</form>
</body>
</html>

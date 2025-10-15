<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.beanutils.PropertyUtils" %>

<%
    boolean mostrarCompletos = Boolean.parseBoolean(request.getParameter("mostrarCompletos"));
    List<FicheroResiBean> listaFicheroResiDetalleBean = (List<FicheroResiBean>)request.getAttribute("listaFicheroResiDetalleBean");
    List<Map<String, String>> response = new ArrayList<>();
    
    for (FicheroResiBean data : listaFicheroResiDetalleBean) {
        Map<String, String> row = new HashMap<>();
        
        // Definir cómo quieres enmascarar los datos
        String resiCIP = data.getResiCIP();
        String resiCIPMask = data.getResiCIPMask();
        
        row.put("resiCIP", mostrarCompletos ? resiCIP : "");
        row.put("resiCIPMask", mostrarCompletos ? "" : resiCIPMask);
        row.put("mostrarCompletos", String.valueOf(mostrarCompletos));
        
        response.add(row);
    }
    
    // Responder con los datos en formato JSON
    String jsonResponse = new com.google.gson.Gson().toJson(response);
    out.print(jsonResponse);
%>
<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.google.gson.Gson" %>

<%
    // Obtener la lista de sugerencias desde la solicitud o cualquier otra fuente
    List<String> sugerencias = (List<String>) request.getAttribute("sugerencias");

    // Convertir la lista de sugerencias a formato JSON
    String jsonSugerencias = new Gson().toJson(sugerencias);

    // Imprimir el JSON directamente en la respuesta
    response.getWriter().write(jsonSugerencias);
%>
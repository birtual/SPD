<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<html>
<head>
    <title>Estado del Proceso</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>
        $(document).ready(function() {
            function updateProgress() {
                $.ajax({
                    url: 'getProcessStatus.do',
                    method: 'GET',
                    dataType: 'json',
                    success: function(data) {
                        $('#progress').text('Progreso: ' + data.progress + '%');
                        $('#message').text(data.message);
                        if (data.progress < 100) {
                            setTimeout(updateProgress, 1000);
                        } else {
                            $('#message').text('Proceso completado!');
                        }
                    }
                });
            }
            updateProgress();
        });
    </script>
</head>
<body>
    <h2>Estado del Proceso</h2>
    <div id="progress">Progreso: 0%</div>
    <div id="message">Iniciando proceso...</div>
</body>
</html>

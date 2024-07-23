/////////////////////// funciones para validar fechas //////////////////

// variables globales

function go(url)
{
	document.location.href=url;
}



function salir() {
	  try {
		    // Verificar si la ventana actual tiene un opener o se abrió con target="_blank"
		    if (!window.opener) {
		      // Cerrar la ventana si es opener o se abrió con target="_blank"
		   // window.history.back();
		    	goInicio();  //vamos al menú inicio
		    }

		    // Volver atrás en el historial si no es opener ni se abrió con target="_blank"
		      window.close();
		  } catch (error) {
		    // Mostrar alerta en caso de excepción
		    alert('Se produjo un error: ' + error.message);
		  }
		}

	function cerrar()
	{
		window.close();	
	}

	function goInicio()
	{
		document.location.href='/spd/Enlaces.do?parameter=list&ACTIONTODO=INICIO';
	}
	
	function pageUp()
	{
		document.forms[0].currpage.value++;
		document.forms[0].submit();
	}
	function pageDown()
	{
		document.forms[0].currpage.value--;
		document.forms[0].submit();
	}	
	function gotoPage(num,numPages) 
	{
		if (window.event.keyCode == 13) 
		{
			if (num>0 && num<=numPages) {
				document.forms[0].currpage.value=num-1;
				document.forms[0].submit();			
			} else {
				
				document.forms[0].newPage.value="";
				document.forms[0].newPage.focus();
			}
		}
	}

    function sortTable(tableId, columnIndex, ascending) {
        var table, rows, switching, i, x, y, shouldSwitch;
        table = document.getElementById(tableId);
        switching = true;
        while (switching) {
            switching = false;
            rows = table.rows;
            for (i = 1; i < (rows.length - 1); i++) {
                shouldSwitch = false;
                x = rows[i].getElementsByTagName("td")[columnIndex];
                y = rows[i + 1].getElementsByTagName("td")[columnIndex];
                if (ascending) {
                    if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
                        shouldSwitch = true;
                        break;
                    }
                } else {
                    if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
                        shouldSwitch = true;
                        break;
                    }
                }
            }
            if (shouldSwitch) {
                rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
                switching = true;
            }
        }
    }
    
	
	//función de carga del lookUp
	function doLookUpBdConsejo(){				
		var loc = '/spd/LookUpBdConsejo.do?parameter=init&'+ 						//url de llamanda				
			'CallBackID=cnOk&'+			  			//Nombre del campo para el valor Id
			'CallBackNAME=nombreConsejo';			   		//Nombre del campo para el valor descriptivo
	
		//Importante que se realice en ventana nueva!!!!
		window.open(loc, 'LookUpBdConsejo', 'dependent=yes,height=500,width=800,top=50,left=50,resizable=yes,scrollbars=yes' );
	}		

	
	function abre(loc)
	{
		alert(loc);
		window.open(loc, 'new', 'dependent=yes,height=500,width=800,top=50,left=50,resizable=yes,scrollbars=yes' );
	}
	

    // Función para convertir números de Excel en fechas dd/mm/yyyy
   	//ajustado la fecha base para que sea el 30 de diciembre de 1899, que es la fecha base utilizada por Excel en sistemas Windows
   	function convertirNumeroAFecha(match) {
   	    const numeroExcel = parseFloat(match);
   	    const fechaBase = new Date('1899-12-30'); // Fecha base de Excel (ajustada)
   	    const fecha = new Date(fechaBase.getTime() + numeroExcel * 24 * 60 * 60 * 1000);
   	    const dia = fecha.getDate();
   	    const mes = fecha.getMonth() + 1;
   	    const anio = fecha.getFullYear();
   	    return `${dia.toString().padStart(2, '0')}/${mes.toString().padStart(2, '0')}/${anio}`;
   	}


	


	function generateExcel() 
	{
		document.forms[0].operation.value="GENERATE";
		document.forms[0].submit();
	}
	function refreshPage() 
	{
		document.forms[0].operation.value="IN_PROCESS";
		millis =  5000;
		setTimeout('document.forms[0].submit();',millis);
	}
	function openFile(fitxer) 
	{
		document.forms[0].idThread.value="";
		document.forms[0].operation.value="FILTER";
		document.forms[0].submit();
		window.open(fitxer);
	}	

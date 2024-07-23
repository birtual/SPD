var RichTextObjects = new Array();
var rng;

function richText(name,path){
	
	var o = new Object();
	
	var index = RichTextObjects.length;
	o.name = name;
	o.path = path;
	o.index = index;
	
	
	o.crearIFrame = crearIframe;
	o.crear = Start;
	o.omplirTexte = omplirTexte;
	o.writeRTE = writeRTE;
	o.FormatText = FormatText;
	o.enableDesignMode = enableDesignMode;
	o.seleccio = seleccio;
	o.value= '';
	o.getValue = getValue;
	o.setValue = setValue;
	o.damePathImage = damePathImage;
	RichTextObjects[index] = o;
	
	return o;
	}

function getValue(){
	return document.getElementById(this.name).contentWindow.document.body.innerHTML;
	
}
function setValue(value){
	this.value=value;
}

function crearIframe(){
	this.name;
	
	document.write('<iframe id="testFrame" style="position: relative; visibility: hidden; width: 0px; height: 0px;"></iframe>');
	document.write('<script language="JavaScript" type="text/javascript" src="browserdetect.js"></script>');
	
//	document.write('<script language="JavaScript" type="text/javascript" src="richtext.js"></script>');
	}
	
function Start() {
	//write html based on browser type
	
	this.writeRTE();
}

function FormatText(command, option) {

	if ((command == "forecolor") || (command == "hilitecolor")) {
		parent.command = command;
		buttonElement = document.getElementById(command);
		document.getElementById("colorpalette").style.left = getOffsetLeft(buttonElement) + "px";
		document.getElementById("colorpalette").style.top = (getOffsetTop(buttonElement) + buttonElement.offsetHeight) + "px";
		if (document.getElementById("colorpalette").style.visibility == "hidden")
			document.getElementById("colorpalette").style.visibility="visible";
		else {
			document.getElementById("colorpalette").style.visibility="hidden";
		}
		
		//get current selected range
		var sel = document.getElementById('edit').contentWindow.document.selection; 
		if (sel!=null) {
			rng = sel.createRange();
		}
	}
	else if (command == "createlink" && browser.isIE5up == false) {
		var szURL = prompt("Enter a URL:", "");
		document.getElementById('edit').contentWindow.document.execCommand("CreateLink",false,szURL)
	}
	else {
	document.getElementById(this.name).contentWindow.focus();
	document.getElementById(this.name).contentWindow.document.execCommand(command, false, option);
	document.getElementById(this.name).contentWindow.focus();
	}
}



function writeRTE() {
	
	//document.writeln('<td><img class="btnImage" src="images/post_button_bold.gif" width="25" height="24" alt="Bold" title="Bold" onClick="RichTextObjects['+this.index+'].FormatText(\'bold\', \'\')"></td>');
	
	var formatblock = "formatblock" + this.name;
	var fontname = "fontname" + this.name;
	var fontsizes = "fontsize" + this.name;
	//var formatblock = "formatblock";
	
	
	
	
	document.writeln('<table  class="clsMainMenuTable">');
	document.writeln('	<tr>');
	document.writeln('		<td>');
	document.writeln('			<select id="'+formatblock+'" onchange="RichTextObjects['+this.index+'].seleccio(\''+formatblock+'\',\'formatblock\');" >');
	document.writeln('				<option value="<p>">Normal</option>');
	document.writeln('				<option value="<p>">Paràgraf</option>');
	document.writeln('				<option value="<h1>">Encapçalament 1 <h1></option>');
	document.writeln('				<option value="<h2>">Encapçalament 2 <h2></option>');
	document.writeln('				<option value="<h3>">Encapçalament 3 <h3></option>');
	document.writeln('				<option value="<h4>">Encapçalament 4 <h4></option>');
	document.writeln('				<option value="<h5>">Encapçalament 5 <h5></option>');
	document.writeln('				<option value="<h6>">Encapçalament 6 <h6></option>');
	document.writeln('				<option value="<address>">Cursiva <ADDR></option>');
	document.writeln('				<option value="<pre>">Formatat <pre></option>');
	document.writeln('			</select>');
	document.writeln('		</td>');
  document.writeln('		<td>');
  document.writeln('		<select id="'+fontname+'" name="selectFont" onchange="RichTextObjects['+this.index+'].seleccio(\''+fontname+'\',\'fontname\');">');
	document.writeln('				<option value="Font" selected>Font</option>');
	document.writeln('				<option value="Arial, Helvetica, sans-serif">Arial</option>');
	document.writeln('				<option value="Courier New, Courier, mono">Courier New</option>');
	document.writeln('				<option value="Times New Roman, Times, serif">Times New Roman</option>');
	document.writeln('				<option value="Verdana, Arial, Helvetica, sans-serif">Verdana</option>');
	document.writeln('			</select>');
	document.writeln('		</td>');
	document.writeln('		<td>');
  document.writeln('			<select unselectable="on" id="'+fontsizes+'" onchange="RichTextObjects['+this.index+'].seleccio(\''+fontsizes+'\',\'fontSize\');">');
	document.writeln('				<option value="Size">Mida</option>');
	document.writeln('				<option value="1">1</option>');
	document.writeln('				<option value="2">2</option>');
	document.writeln('				<option value="3">3</option>');
	document.writeln('				<option value="4">4</option>');
	document.writeln('				<option value="5">5</option>');
	document.writeln('				<option value="6">6</option>');
	document.writeln('				<option value="7">7</option>');
	document.writeln('			</select>');
	document.writeln('		</td>'); 
	document.writeln('	</tr>');
	document.writeln('</table>');
	document.writeln('<table cellpadding="1" cellspacing="0">');
	document.writeln('	<tr>');
	document.writeln('		<td><img class="btnImage" src="images/post_button_bold.gif" width="25" height="24" alt="Bold" title="Negreta" onClick="RichTextObjects['+this.index+'].FormatText(\'bold\', \'\')"></td>');
	document.writeln('		<td><img class="btnImage" src="images/post_button_italic.gif" width="25" height="24" alt="Italic" title="Cursiva" onClick="RichTextObjects['+this.index+'].FormatText(\'italic\', \'\')"></td>');
	document.writeln('		<td><img class="btnImage" src="images/post_button_underline.gif" width="25" height="24" alt="Underline" title="Subratllat" onClick="RichTextObjects['+this.index+'].FormatText(\'underline\', \'\')"></td>');
	document.writeln('		<td>&nbsp;</td>');
	document.writeln('		<td><img class="btnImage" src="images/post_button_left_just.gif" width="25" height="24" alt="Align Left" title="Alinear Esquerra" onClick="RichTextObjects['+this.index+'].FormatText(\'justifyleft\', \'\')"></td>');
	document.writeln('		<td><img class="btnImage" src="images/post_button_centre.gif" width="25" height="24" alt="Center" title="Centrar" onClick="RichTextObjects['+this.index+'].FormatText(\'justifycenter\', \'\')"></td>');
	document.writeln('		<td><img class="btnImage" src="images/post_button_right_just.gif" width="25" height="24" alt="Align Right" title="Alinear Dreta" onClick="RichTextObjects['+this.index+'].FormatText(\'justifyright\', \'\')"></td>');
	document.writeln('		<td>&nbsp;</td>');
	document.writeln('		<td><img class="btnImage" src="images/post_button_numbered_list.gif" width="25" height="24" alt="Ordered List" title="Llista Ordenada" onClick="RichTextObjects['+this.index+'].FormatText(\'insertorderedlist\', \'\')"></td>');
	document.writeln('		<td><img class="btnImage" src="images/post_button_list.gif" width="25" height="24" alt="Unordered List" title="Llista sense Ordenar" onClick="RichTextObjects['+this.index+'].FormatText(\'insertunorderedlist\', \'\')"></td>');
	document.writeln('		<td>&nbsp;</td>');
	document.writeln('		<td><img class="btnImage" src="images/post_button_outdent.gif" width="25" height="24" alt="Outdent" title="Disminuir Sagnat" onClick="RichTextObjects['+this.index+'].FormatText(\'outdent\', \'\')"></td>');
	document.writeln('		<td><img class="btnImage" src="images/post_button_indent.gif" width="25" height="24" alt="Indent" title="Augmentar Sagnat" onClick="RichTextObjects['+this.index+'].FormatText(\'indent\', \'\')"></td>');
	document.writeln('		<td><div id="forecolor"><img class="btnImage" src="images/post_button_textcolor.gif" width="25" height="24" alt="Text Color" title="Color del Text" onClick="RichTextObjects['+this.index+'].FormatText(\'forecolor\', \'\')"></div></td>');
	document.writeln('<!--');
	document.writeln('		<td><div id="hilitecolor"><img class="btnImage" src="images/post_button_bgcolor.gif" width="25" height="24" alt="Background Color" title="Background Color" onClick="RichTextObjects['+this.index+'].FormatText(\'hilitecolor\', \'\')"></div></td>');
	document.writeln('//-->');
	document.writeln('		<td>&nbsp;</td>');
	document.writeln('		<td><img class="btnImage" src="images/post_button_hyperlink.gif" width="25" height="24" alt="Insert Link" title="Insertar Link" onClick="RichTextObjects['+this.index+'].FormatText(\'createlink\')"></td>');
	//document.writeln('		<td><a href="../../IMATGE_lookUp.jsp?CallBack=RichTextObjects['+this.index+'].damePathImage&amp;CallBackID=ID&amp;CallBackNAME=FITXER" target="_blank"><img class="btnImage" border="0" src="images/post_button_image.gif" width="25" height="24" alt="Add Image" title="Add Image"></a></td>');
	//document.writeln('		<td><a href="javascript:openWin(\'smileys.asp\',\'smilies\',\'toolbar=0,location=0,status=0,menubar=0,scrollbars=0,resizable=1,width=400,height=400\')"><img src="images/post_button_smiley.gif" width="25" height="24" align="absmiddle" alt="Insert Smiley" border="0"></a></td>');
	//if (browser.isIE5up) document.writeln('		<td><img class="btnImage" src="images/post_button_spellcheck.gif" width="25" height="24" alt="Add Image" title="Add Image" onClick="checkspell()"></td>');
	//document.writeln('		<td><img class="btnImage" src="images/post_button_spellcheck.gif" width="25" height="24" alt="Add Image" title="Add Image" onClick="checkspell()"></td>');
	document.writeln('<!--');
	document.writeln('		<td>&nbsp;</td>');
	document.writeln('		<td><img class="btnImage" src="images/post_button_cut.gif" width="25" height="24" alt="Cut" title="Cut" onClick="RichTextObjects['+this.index+'].FormatText(\'cut\')"></td>');
	document.writeln('		<td><img class="btnImage" src="images/post_button_copy.gif" width="25" height="24" alt="Copy" title="Copy" onClick="RichTextObjects['+this.index+'].FormatText(\'copy\')"></td>');
	document.writeln('		<td><img class="btnImage" src="images/post_button_paste.gif" width="25" height="24" alt="Paste" title="Paste" onClick="RichTextObjects['+this.index+'].FormatText(\'paste\')"></td>');
	document.writeln('		<td>&nbsp;</td>');
	document.writeln('		<td><img class="btnImage" src="images/post_button_undo.gif" width="25" height="24" alt="Undo" title="Undo" onClick="RichTextObjects['+this.index+'].FormatText(\'undo\')"></td>');
	document.writeln('		<td><img class="btnImage" src="images/post_button_redo.gif" width="25" height="24" alt="Redo" title="Redo" onClick="RichTextObjects['+this.index+'].FormatText(\'redo\')"></td>');
	document.writeln('//-->');
	document.writeln('	</tr>');
	document.writeln('</table>');
	document.writeln('<br>');
	
	
	
	document.writeln('<iframe id="'+this.name+'" width="690px" height="340px"></iframe>');
	setTimeout("RichTextObjects["+this.index+"].enableDesignMode()", 10);
	setTimeout("RichTextObjects["+this.index+"].omplirTexte()", 500);
	document.writeln('<iframe width="250" height="170" id="colorpalette" src="palette.htm" style="visibility:hidden; position: absolute; left: 0px; top: 0px;"></iframe>');
	
}

function enableDesignMode() {
	var nomFrame = this.name;
	eval('frames.'+nomFrame+'.document.designMode = "On"');
	//frames.edit1.document.designMode = "On";
	//frames.edit.document.onmouseup = document.selection.clear;


}

function seleccio(selectname,command)
{	
	

	
	var cursel = document.getElementById(selectname).selectedIndex;
	// First one is always a label
	if (cursel != 0) {
		var selected = document.getElementById(selectname).options[cursel].value;
		
		document.getElementById(this.name).contentWindow.document.execCommand(command, false, selected);
		
		document.getElementById(selectname).selectedIndex = 0;
	}
	document.getElementById(this.name).contentWindow.focus();
}

function omplirTexte()
{
	
	document.getElementById(this.name).contentWindow.document.body.innerHTML = this.value;
	
}

function damePathImage(id,image){
	
	var pathImg = this.path + image;
	
	document.getElementById(this.name).contentWindow.focus()
	document.getElementById(this.name).contentWindow.document.execCommand('InsertImage', false, pathImg);
}

function getOffsetTop(elm) {
	var mOffsetTop = elm.offsetTop;
	var mOffsetParent = elm.offsetParent;
	
	while(mOffsetParent){
		mOffsetTop += mOffsetParent.offsetTop;
		mOffsetParent = mOffsetParent.offsetParent;
	}
	
	return mOffsetTop;
}

function getOffsetLeft(elm) {
	var mOffsetLeft = elm.offsetLeft;
	var mOffsetParent = elm.offsetParent;
	
	while(mOffsetParent) {
		mOffsetLeft += mOffsetParent.offsetLeft;
		mOffsetParent = mOffsetParent.offsetParent;
	}
	
	return mOffsetLeft;
}

//Function to set color
function setColor(color) {
	if (browser.isIE5up) {
		//retrieve selected range
		var sel = document.getElementById('edit').contentWindow.document.selection; 
		if (sel!=null) {
			var newRng = sel.createRange();
			newRng = rng;
			newRng.select();
		}
	}
	else {
		document.getElementById("edit").contentWindow.focus();
	}
	document.getElementById("edit").contentWindow.document.execCommand(parent.command, false, color);
	document.getElementById("edit").contentWindow.focus();
	document.getElementById("colorpalette").style.visibility="hidden";
}

// Cross Navigation Functions from IE40 to IE6 and NS4 to NS6
/*function getElementByIdNS4(id){
	if(id){
		var obj = document.layers[id];
		if(obj){
			obj.style = obj;
			obj.offsetHeight = obj.clip.height;}
		return obj;
		}
	return null;
	}
var NS4=false;
if( (navigator.appName=="Netscape") && (navigator.appVersion.slice(0,1) <5) ){
	NS4=true;document.getElementById = getElementByIdNS4;}
*/


// Definition of class Folder 
// ***************************************************************** 

/* 
function Folder(folderDescription, hreference, target, url_info, target_info, alt) //constructor 
{ 
  //constant data
  this.desc = folderDescription
  this.alt  = alt
  this.hreference = hreference
  this.href_target = target
  this.hrefinfo   = url_info
  this.hrefinfo_target = target_info
  this.id = -1
  this.navObj = 0
  this.iconImg = 0
  this.nodeImg = 0
  this.isLastNode = 0
  this.iconFolderOpen = "/ftv2folderopen.gif"
  this.iconFolderSelec = "/ftv2foldersel.gif"
  this.iconFolderClosed = "/ftv2folderclosed.gif"
 
  //dynamic data 
  this.isOpen = true 
  this.iconSrc = IMGPATH+this.iconFolderOpen
  this.children = new Array 
  this.nChildren = 0 
 
  //methods 
  this.initialize = initializeFolder 	//ok
  this.setState = setStateFolder 		//ok
  this.addChild = addChild 				//ok
  this.createIndex = createEntryIndex 	//????????
  this.hide = hideFolder 					//ok
  this.display = display 					//????????
  this.renderOb = drawFolder 				//ok
  this.totalHeight = totalHeight 		//????????
  this.subEntries = folderSubEntries 	//ok
  this.outputLink = outputFolderLink 	//ok
} 
*/

function Folder(folderDescription, hreference, target, url_info, target_info, alt, ifo, ifs, ifc) //constructor 
{ 
  //constant data
  this.desc = folderDescription
  this.alt  = alt
  this.hreference = hreference
  this.href_target = target
  this.hrefinfo   = url_info
  this.hrefinfo_target = target_info
  this.id = -1
  this.navObj = 0
  this.iconImg = 0
  this.nodeImg = 0
  this.isLastNode = 0
  this.iconFolderOpen = ifo
  this.iconFolderSelec = ifs
  this.iconFolderClosed = ifc
  //dynamic data 
  this.isOpen = true 
  this.iconSrc = IMGPATH+this.iconFolderOpen
  //alert(this.iconSrc);
  this.children = new Array 
  this.nChildren = 0 
 
  //methods 
  this.initialize = initializeFolder 	//ok
  this.setState = setStateFolder 		//ok
  this.addChild = addChild 				//ok
  this.createIndex = createEntryIndex 	//????????
  this.hide = hideFolder 					//ok
  this.display = display 					//????????
  this.renderOb = drawFolder 				//ok
  this.totalHeight = totalHeight 		//????????
  this.subEntries = folderSubEntries 	//ok
  this.outputLink = outputFolderLink 	//ok
} 
	//Código del Objeto en HTML y otros
	function initializeFolder(level, lastNode, leftSide){ 
		var j=0,i=0;
		var numberOfFolders, numberOfDocs, nc;
      
  		nc = this.nChildren
   
	  this.createIndex() 
 
	  var auxEv = "" 
 
	  if (browserVersion > 0) 
	    auxEv = "<a href='javascript:clickOnNode("+this.id+")'>" 
	  else 
	    auxEv = "<a>" 
 
	  if (level>0) 
	    if (lastNode){ //the last 'brother' in the children array 
	      this.renderOb(leftSide + auxEv + "<img name='nodeIcon" + this.id + "' src='"+IMGPATH+"/ftv2mlastnode.gif' width=16 height=22 border=0></a>") 
	      leftSide = leftSide + "<img src='"+IMGPATH+"/ftv2blank.gif' width=16 height=22>"  
	      this.isLastNode = 1 
	    }else{ 
      	this.renderOb(leftSide + auxEv + "<img name='nodeIcon" + this.id + "' src='"+IMGPATH+"/ftv2mnode.gif' width=16 height=22 border=0></a>") 
	      leftSide = leftSide + "<img src='"+IMGPATH+"/ftv2vertline.gif' width=16 height=22>" 
   	   this.isLastNode = 0 
	    	} 
		else 
	    this.renderOb("") 
   
	  if (nc > 0) 
	  { 
	    level = level + 1 
	    for (i=0 ; i < this.nChildren; i++)  
	    { 
	      if (i == this.nChildren-1) 
   	     this.children[i].initialize(level, 1, leftSide) 
      	else 
	        this.children[i].initialize(level, 0, leftSide) 
   	   } 
	  } 
	} 


	function setStateFolder(isOpen){ 
	  var subEntries 
	  var totalHeight 
	  var fIt = 0 
	  var i=0 
 
	  if (isOpen == this.isOpen) 
   	 return 
 
	  if (browserVersion == 2)  
	  { 
   	 totalHeight = 0 
	    for (i=0; i < this.nChildren; i++) 
   	   totalHeight = totalHeight + this.children[i].navObj.clip.height 
      	subEntries = this.subEntries() 
	    if (this.isOpen) 
   	   totalHeight = 0 - totalHeight 
	    for (fIt = this.id + subEntries + 1; fIt < nEntries; fIt++) 
   	   indexOfEntries[fIt].navObj.moveBy(0, totalHeight) 
	  }  
	  this.isOpen = isOpen 
	  propagateChangesInState(this) 
	} 

	function addChild(childNode) 
	{ 
	  this.children[this.nChildren] = childNode 
	  this.nChildren++ 
	  return childNode 
	}
	
	function hideFolder() 
	{ 
	  if (browserVersion == 1) { 
	    if (this.navObj.style.display == "none") 
	      return 
	    this.navObj.style.display = "none" 
	  } else { 
	    if (this.navObj.visibility == "hiden") 
	      return 
	    this.navObj.visibility = "hiden" 
	  } 
	   
	  this.setState(0) 
	} 
	
	function drawFolder(leftSide) 
	{ 
	  if (browserVersion == 2) { 
   	 if (!doc.yPos) 
	      doc.yPos=8 
	    doc.write("<layer id='folder" + this.id + "' top=" + doc.yPos + " visibility=hiden>") 
	  } 
   
	  doc.write("<table ") 
	  if (browserVersion == 1) 
	    doc.write(" id='folder" + this.id + "' style='position:block;' ") 
	  doc.write(" border='0' cellspacing='0' cellpadding='0' height='22' width='0'>") 
	  doc.write("<tr><td>")
	  doc.write(leftSide) 
	  this.outputLink( this.hrefinfo, this.hrefinfo_target ) 
	  doc.write("<img name='folderIcon" + this.id + "' ") 
	  if(this.alt)	doc.write(" alt='"+this.alt+"'")
	  doc.write("src='" + this.iconSrc+"' border=0></a>") 
	  doc.write("</td><td valign=middle nowrap>") 
	  if (USETEXTLINKS) 
	  { 
	    this.outputLink( this.hreference , this.href_target ) 
	    doc.write("&nbsp;" + this.desc + "</a>") 
	  } 
	  else 
	    doc.write(this.desc) 
	  doc.write("</td>")  
	  doc.write("</table>") 
   
	  if (browserVersion == 2) { 
	    doc.write("</layer>") 
	  } 
 
	  if (browserVersion == 1) { 
	    this.navObj = doc.getElementById("folder"+this.id)
	    this.iconImg = eval('doc.folderIcon'+this.id);  	//doc.getElementById("folderIcon"+this.id)
	    this.nodeImg = eval('doc.nodeIcon'+this.id); 		//doc.getElementById("nodeIcon"+this.id)
	  } else if (browserVersion == 2) { 
	    this.navObj = doc.layers["folder"+this.id] 
	    this.iconImg = this.navObj.document.images["folderIcon"+this.id] 
	    this.nodeImg = this.navObj.document.images["nodeIcon"+this.id] 
	    doc.yPos=doc.yPos+this.navObj.clip.height 
	  } 
	} 
	
	function folderSubEntries() 
	{ 
	  var i = 0 
	  var se = this.nChildren 
 
	  for (i=0; i < this.nChildren; i++){ 
	    if (this.children[i].children) //is a folder 
	      se = se + this.children[i].subEntries() 
	  } 
 
	  return se 
	} 
	
	function outputFolderLink(href,target) 
	{ 
	  if (href) 
	  { 
	    doc.write("<a class='TextoMenu' href='" + href + "'")
	    if (target) doc.write(" TARGET=\""+target+"\" ") 
	    if(this.alt)	doc.write(" title='"+this.alt+"'")
	    if (browserVersion > 0) 
	      doc.write("onClick='openOnNode("+this.id+")'") 
	    doc.write(">") 
	  } 
	  else 
	  doc.write("<a>") 
	 // doc.write("<a href='javascript:clickOnFolder("+this.id+")'>")   
	} 
	
	function propagateChangesInState(folder) 
	{   
  		var i=0 
 
	  if (folder.isOpen) 
	  { 
	    if (folder.nodeImg) 
	      if (folder.isLastNode) 
	        	folder.nodeImg.src = IMGPATH+"/ftv2mlastnode.gif" 
	      else 
			folder.nodeImg.src = IMGPATH+"/ftv2mnode.gif" 
	    folder.iconImg.src = IMGPATH+folder.iconFolderOpen 
	    for (i=0; i<folder.nChildren; i++) 
	      folder.children[i].display() 
	  } 
	  else 
	  { 
	    if (folder.nodeImg) 
	      if (folder.isLastNode) 
	        folder.nodeImg.src = IMGPATH+"/ftv2plastnode.gif" 
	      else 
		  folder.nodeImg.src = IMGPATH+"/ftv2pnode.gif" 
	    folder.iconImg.src = IMGPATH+folder.iconFolderClosed 
	    for (i=0; i<folder.nChildren; i++) 
	      folder.children[i].hide() 

	  }  
	} 
	
	
	
	
// Definition of class Item (a document or link inside a Folder) 
// ************************************************************* 
 
function Item(itemDescription, itemLink, icon, infoLink) // Constructor 
{ 
  // constant data 
  this.desc = itemDescription 
  this.link = itemLink 
  this.infolink = infoLink  
  this.id = -1 //initialized in initalize() 
  this.navObj = 0 //initialized in render() 
  this.iconImg = 0 //initialized in render() 
  if(icon) this.iconSrc = icon
  else this.iconSrc = IMGPATH+"/ftv2doc.gif" 
 
  // methods 
  this.initialize = initializeItem 
  this.createIndex = createEntryIndex 
  this.hide = hideItem 
  this.display = display 
  this.renderOb = drawItem 
  this.totalHeight = totalHeight 
} 
 
function hideItem() 
{ 
  if (browserVersion == 1) { 
    if (this.navObj.style.display == "none") 
      return 
    this.navObj.style.display = "none" 
  } else { 
    if (this.navObj.visibility == "hiden") 
      return 
    this.navObj.visibility = "hiden" 
  }     
} 
 
function initializeItem(level, lastNode, leftSide) 
{  
  this.createIndex() 
 
  if (level>0) 
    if (lastNode) //the last 'brother' in the children array 
    { 
      this.renderOb(leftSide + "<img src='"+IMGPATH+"/ftv2lastnode.gif' width=16 height=22>") 
      leftSide = leftSide + "<img src='"+IMGPATH+"/ftv2blank.gif' width=16 height=22>"  
    } 
    else 
    { 
      this.renderOb(leftSide + "<img src='"+IMGPATH+"/ftv2node.gif' width=16 height=22>") 
      leftSide = leftSide + "<img src='"+IMGPATH+"/ftv2vertline.gif' width=16 height=22>" 
    } 
  else 
    this.renderOb("")   
} 
 
function drawItem(leftSide) 
{ 
  if (browserVersion == 2) 
    doc.write("<layer id='item" + this.id + "' top=" + doc.yPos + " visibility=hiden>") 
     
  doc.write("<table ") 
  if (browserVersion == 1) 
    doc.write(" id='item" + this.id + "' style='position:block;' ") 
  doc.write(" border='0' cellspacing='0' cellpadding='0' height='22' width='0'>") 
  doc.write("<tr><td>") 
  doc.write(leftSide) 
  doc.write("</td><td valign='middle'>") 
  doc.write("<a href=" + this.infolink + " >") 
  doc.write("<img id='itemIcon"+this.id+"' ") 
  doc.write("src='"+this.iconSrc+"' border=0>") 
  doc.write("</a>") 
  doc.write("</td><td valign=middle nowrap>") 
  if (USETEXTLINKS) 
    doc.write("<a class='TextoMenu' href=" + this.link + ">" + "&nbsp;" + this.desc + "</a>") 
  else 
    doc.write(this.desc) 
  doc.write("</table>") 
   
  if (browserVersion == 2) 
    doc.write("</layer>") 
 
  if (browserVersion == 1) { 
    this.navObj = doc.getElementById("item"+this.id)
    this.iconImg = eval('doc.itemIcon'+this.id);
  } else if (browserVersion == 2) { 
    this.navObj = doc.layers["item"+this.id] 
    this.iconImg = this.navObj.document.images["itemIcon"+this.id] 
    doc.yPos=doc.yPos+this.navObj.clip.height 
  } 
} 
	
	
	
	
// Methods common to both objects (pseudo-inheritance) 
// ******************************************************** 
 
function display() 
{ 
  if (browserVersion == 1) 
    this.navObj.style.display = "block" 
  else 
    this.navObj.visibility = "show" 
} 
 
function createEntryIndex() 
{ 
  this.id = nEntries 
  indexOfEntries[nEntries] = this 
  nEntries++ 
} 
 
// total height of subEntries open 
function totalHeight() //used with browserVersion == 2 
{ 
  var h = this.navObj.clip.height 
  var i = 0 
   
  if (this.isOpen) //is a folder and _is_ open 
    for (i=0 ; i < this.nChildren; i++)  
      h = h + this.children[i].totalHeight() 
 
  return h 
} 


// Events 
// ********************************************************* 
 
function clickOnFolder(folderId) 
{ 
  var clicked = indexOfEntries[folderId] 
  
 
  if (!clicked.isOpen) 
    clickOnNode(folderId) 
 
  return  
 
  if (clicked.isSelected) 
    return 
} 


function openOnNode(folderId) 
{ 
  var clickedFolder = 0 
  var state = 1 
  
  
  clickedFolder = indexOfEntries[folderId] 
  if(state != clickedFolder.isOpen) 
	 clickedFolder.setState(state) //open<->close  
  
	  if (state)
  	{
		   oldSelFolder = indexOfEntries[selectedFolder] 
			 oldSelFolder.iconImg.src = IMGPATH+oldSelFolder.iconFolderOpen
			 selectedFolder=folderId;
       clickedFolder.iconImg.src = IMGPATH+clickedFolder.iconFolderSelec
    }
		else  selectedFolder=0;
} 
 
function clickOnNode(folderId) 
{ 
  var clickedFolder = 0 
  var state = 1 
  
  
  clickedFolder = indexOfEntries[folderId] 
  state = clickedFolder.isOpen 
	 clickedFolder.setState(!state) //open<->close  
  
   oldSelFolder = indexOfEntries[selectedFolder] 
   oldSelFolder.iconImg.src = IMGPATH+oldSelFolder.iconFolderSelec
   if(selectedFolder==folderId)
   	  clickedFolder.iconImg.src = IMGPATH+clickedFolder.iconFolderSelec
  //if (!state)
  //{
  //} 
  //else
   //selectedFolder=0;

} 
 
function initializeDocument(foldersTree) 
{ 
  if (doc.getElementById) 
    browserVersion = 1 //IE4   
  else 
    if (doc.layers) 
      browserVersion = 2 //NS4 
    else 
      browserVersion = 0 //other 
 
  foldersTree.initialize(0, 1, "") 
  foldersTree.display()
  
  if (browserVersion > 0) 
  { 
    doc.write("<layer top="+indexOfEntries[nEntries-1].navObj.top+">&nbsp;</layer>") 
 
    // close the whole tree 
    clickOnNode(0) 
    // open the root folder 
    clickOnNode(0) 
  } 
} 
	
	
	
	
	
	
	
	
// Auxiliary Functions for Folder-Treee (modified by Meurrens!) 
// ************************************
 
function newFld(name, alt, url, target, url_info, target_info, ifo, ifs, ifc) 
{ 
  currentFolder = new Folder(name, url, target, url_info, target_info, alt, ifo, ifs, ifc) 
  return currentFolder 
} 
 
function newDoc(target, name, url, icon) 
{ 
  fullLink = "" 
 
//                     - modify the syntax of newDoc (target parameter):
//                       1. does not prefix anymore with http://
//                          (to be consistent with newFld)
//                       2. meaning of "target":
//                          0 = DEFTARGET (default to _homeWin)
//                          1 = _blank (_new)
//                          2 = _top
//                          3 = _self
//                          4 = _homeWin
//                          5 = _navigWin
//                          6 = _remoteWin
//                          7 = _barWin
//                          8 = ALTTARGET (default to _new)
//                          other: _new

  if (target==0) 
  { 
    fullLink = "'"+url+"' target=\""+DEFTARGET+"\"" 
  } 
  else if (target==1)
  {
    fullLink = "'"+url+"' target=_blank" 
  }
  else if (target==2)
  {
    fullLink = "'"+url+"' target=_top" 
  }
  else if (target==3)
  {
    fullLink = "'"+url+"' target=_self" 
  }
  else if (target==4)
  {
    fullLink = "'"+url+"' target=_homeWin" 
  }
  else if (target==5)
  {
    fullLink = "'"+url+"' target=_navigWin" 
  }
  else if (target==6)
  {
    fullLink = "'"+url+"' target=_remoteWin" 
  }
  else if (target==7)
  {
    fullLink = "'"+url+"' target=_barWin" 
  }
  else if (target==8)
  {
    fullLink = "'"+url+"' target=\""+ALTTARGET+"\"" 
  }
  else 
  {
    fullLink = "'"+url+"' target=_new" 
  }

  currentDoc = new Item(name, fullLink, icon)   
  return currentDoc
} 
 
function insFld(parentFolder, childFolder) 
{ 
  currentFolder = parentFolder.addChild(childFolder) 
  return currentFolder
} 

function insNewFld(parentFolder, name, alt, url, target, url_info, target_info )
{ 
  currentFolder = parentFolder.addChild(newFld(name, alt, url, target, url_info, target_info)) 
  return currentFolder
} 
 
function insDoc(parentFolder, doc) 
{  
  parentFolder.addChild(doc) 
} 
 
function insNewDoc(parentFolder, name, alt, icono, url, target, url_info, target_info)// target, name, url) 
{ 
  fullLink		= "'" + url + "' " + (target? "TARGET='"+target+"' " : "" ) + (alt? "TITLE='"+alt+"'" : "")
  fullLink_info= "'" + url_info + "' "+ (target_info? "TARGET='"+target_info+"' " : "" ) + (alt? "TITLE='"+alt+"'" : "")
  parentFolder.addChild( new Item(name, fullLink, icono, fullLink_info) ) 
  //parentFolder.addChild(newDoc(target, name, url)) 
} 
 
function curDoc(target, name, url, icon) 
{ 
  currentFolder.addChild(newDoc(target, name, url, icon)) 
} 
 
function curFld(parentFolder) 
{ 
  currentFolder = parentFolder
} 

function addDoc( name, alt, icono, url, target, url_info, target_info ){//function proxy
  fullLink		= "'" + url + "' " + (target? "TARGET='"+target+"' " : "" ) + (alt? "TITLE='"+alt+"'" : "")
  fullLink_info= "'" + url_info + "' "+ (target_info? "TARGET='"+target_info+"' " : "" ) + (alt? "TITLE='"+alt+"'" : "")
  currentFolder.addChild( new Item(name, fullLink, icono, fullLink_info) ) 
}






// Global variables 
// **************** 

indexOfEntries = new Array 
nEntries = 0 
doc = document 
browserVersion = 0 
selectedFolder=0
currentFolder = 0 
currentDoc = 0 

// Installation parameters (default values)
// ***********************
  
USETEXTLINKS = 1 
DEFTARGET = "_homeWin"
ALTTARGET = "_new"
IMGPATH = "admin/dbformslib/treeLib/"

	
function getDomDocumentPrefix() {
	if (getDomDocumentPrefix.prefix)
		return getDomDocumentPrefix.prefix;
	
	var prefixes = ["MSXML2", "Microsoft", "MSXML", "MSXML3"];
	var o;
	for (var i = 0; i < prefixes.length; i++) {
		try {
			// try to create the objects
			o = new ActiveXObject(prefixes[i] + ".DomDocument");
			return getDomDocumentPrefix.prefix = prefixes[i];
		}
		catch (ex) {};
	}
	
	throw new Error("Could not find an installed XML parser");
}

function getXmlHttpPrefix() {
	if (getXmlHttpPrefix.prefix)
		return getXmlHttpPrefix.prefix;

	var prefixes = ["MSXML2","Microsoft", "MSXML","MSXML3"];
	var o;
	for (var i = 0; i < prefixes.length; i++) {
		try {
			// try to create the objects
			o = new ActiveXObject(prefixes[i] + ".XmlHttp");
			return getXmlHttpPrefix.prefix = prefixes[i];
		}
		catch (ex) {};
	}
	
	throw new Error("Could not find an installed XMLHttp object");
}

function XmlHttp() {}

XmlHttp.create = function () {
	try {
		// NS & MOZ
		if (window.XMLHttpRequest) {
			var req = new XMLHttpRequest();
			
			// some versions of Moz do not support the readyState property
			// and the onreadystate event so we patch it!
			if (req.readyState == null) {
				req.readyState = 1;
				req.addEventListener("load", function () {
					req.readyState = 4;
					if (typeof req.onreadystatechange == "function")
						req.onreadystatechange();
				}, false);
			}
			
			return req;
		}
		// IE
		if (window.ActiveXObject) {
			return new ActiveXObject(getXmlHttpPrefix() + ".XmlHttp");
		}
	}
	catch (ex) {}
	// Fail
	throw new Error("Your browser does not support XmlHttp objects");
};

function XmlDocument() {}
XmlDocument.create = function () {
	try {
		if (document.implementation && document.implementation.createDocument) {
			var doc = document.implementation.createDocument("", "", null);
			if (doc.readyState == null) {
				doc.readyState = 1;
				doc.addEventListener("load", function () {
					doc.readyState = 4;
					if (typeof doc.onreadystatechange == "function")
						doc.onreadystatechange();
				}, false);
			}
			
			return doc;
		}
		if (window.ActiveXObject)
			return new ActiveXObject(getDomDocumentPrefix() + ".DomDocument");
	}
	catch (ex) {}
	throw new Error("Your browser does not support XmlDocument objects");
};


if (window.DOMParser &&
		window.XMLSerializer &&
		window.Node && Node.prototype) {

		Document.prototype.loadXML = function (s) {
			var doc2 = (new DOMParser()).parseFromString(s, "text/xml");
			
			while (this.hasChildNodes())
				this.removeChild(this.lastChild);
			for (var i = 0; i < doc2.childNodes.length; i++) {
				if(doc2.childNodes[i] instanceof Element){
					this.appendChild(this.importNode(doc2.childNodes[i], true));
				}
			}
		};
		
}


/*
 * xmlHttp Pool
 * 
 * userage: var xmlhttpObj = XmlHttpPool.pick()
 */
var XmlHttpPoolArr = new Array();
var XmlHttpPoolSize = 100;
var XHPCurrentAvailableID = 0;

function XmlHttpPool() {}
XmlHttpPool.pick = function() {
	var pos = XHPCurrentAvailableID;
	XmlHttpPoolArr[pos] =  XmlHttp.create();
	
	XHPCurrentAvailableID >= (XmlHttpPoolSize-1) ? 0 : XHPCurrentAvailableID++
	
	return XmlHttpPoolArr[pos];
}

function rpcCall(url, method, data, callback, asyn) {
    var xmlhttp = XmlHttp.create();
	xmlhttp.open(method, url, asyn);
	xmlhttp.send(data);
	if (!asyn) { // if not asyn
		callback(xmlhttp.responseText);
	} else {
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4) {
				callback(xmlhttp.responseText);
			}
		}
	}
}



/*============================================================
 * RemoteXml
 * 
 * userage: 从http读取xml数据
 *
 * 		RemoteXml.remoteCall(sURI,"testparam",function(reply){
 *  		alert(reply);
 *  	});
 *
 * auther:  zhangza 2005-09-16
 */
function RemoteXml() {}

RemoteXml.transport = null;
RemoteXml.async = true;

RemoteXml.onFinish = new Function();

RemoteXml.onException = new Function();

RemoteXml.loadingPane = null;

RemoteXml.errorPane = null;

RemoteXml.onLoading = function(state) {
	RemoteXml.loadingPane = document.getElementById("RemoteXml_loading");
	if (RemoteXml.loadingPane == null) {
		var el = document.createElement('DIV');
		el.setAttribute("id","RemoteXml_loading");
		el.style.cssText="display:none;font-family:Verdana;font-size:11px;border:1px solid #00CC00;background-color:#A4FFA4;padding:1px;position:absolute; right:1px; top:1px; width:130px; height:14px; z-index:10000";
		el.innerHTML="正在载入远程文件... ";
		document.body.appendChild(el);
		RemoteXml.loadingPane = el;
	}
	if (state) {
		RemoteXml.loadingPane.style.display="block";
		RemoteXml.loadingPane.style.top = document.body.scrollTop+1;
	} else {
		RemoteXml.loadingPane.style.display="none";
	}
}


RemoteXml.onError = function(errorStr) {
	RemoteXml.errorPane = document.getElementById("RemoteXml_error");
	if (RemoteXml.errorPane == null) {
		var el = document.createElement('DIV');
		el.setAttribute("id","RemoteXml_error");
		el.style.cssText="font-family:Verdana;font-size:11px;border:1px solid #00CC00;background-color:#ffdb9c;padding:1px;position:absolute;overflow:auto; right:1px; top:1px; width:500px; height:300px; z-index:1";
		el.innerHTML=errorStr;
		document.body.appendChild(el);
		RemoteXml.errorPane = el;
	}else{
		RemoteXml.errorPane.innerHTML=errorStr;
	}
}


RemoteXml.onStateChange = function(){

	if (RemoteXml.transport.readyState == 4) {
		if (RemoteXml.transport.status == '200') {
			RemoteXml.onLoading(false);
			
			RemoteXml.onFinish(RemoteXml.transport.responseText);

		} else {
			RemoteXml.onError(RemoteXml.transport.responseText);
		}
	}
}

//retType:str;dom
RemoteXml._remoteCall = function(url, Param, callback,retType) {
	RemoteXml.transport = XmlHttp.create();
	RemoteXml.transport.open("POST", url, RemoteXml.async);
	RemoteXml.transport.send(Param);
	RemoteXml.onFinish = callback;

	if (RemoteXml.async) {
		RemoteXml.transport.onreadystatechange = function(){RemoteXml.onStateChange();};
		//this.transport.onreadystatechange = this.onStateChange.bind(this);
		//batch.req.onreadystatechange = function() { DWREngine._stateChange(batch); };
		RemoteXml.onLoading(true);
	} else { 
		if (RemoteXml.transport.status == '200') {
			if(retType=="str"){
				RemoteXml.onFinish(RemoteXml.transport.responseText);	
			}else //(retType=="dom")
			{
				RemoteXml.onFinish(RemoteXml.transport.responseXML);
			}
		} else {
			RemoteXml.onError(RemoteXml.transport.responseText);
		}	
	}
}


RemoteXml.remoteCall = function(url, remoteXmlTypeParam, callback) {	
	var newUrl = url;
	var Param = "remoteXmlType="+remoteXmlTypeParam;
	RemoteXml._remoteCall(newUrl,Param, callback,"str");
}

RemoteXml.remoteDomCall = function(url, remoteXmlTypeParam, callback) {	
	var newUrl = url;
	var Param = "remoteXmlType="+remoteXmlTypeParam;
	RemoteXml._remoteCall(newUrl,Param, callback,"dom");
}


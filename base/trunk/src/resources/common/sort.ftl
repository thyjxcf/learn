<script>
//转换器，将列的字段类型转换为可以排序的类型：String,int,float 
function convert(sValue, sDataType) 
{ 
switch(sDataType) 
{ 
case "int": 
return parseInt(sValue); 
case "float": 
return parseFloat(sValue); 
case "date": 
return new Date(Date.parse(sValue));
default: 
return sValue.toString(); 
} 
} 
// 汉字排序方法 
function chrComp(a,b) 
{ 
return a.localeCompare(b); 
} 
//排序函数产生器 
function generateCompareTRs(iCol, sDataType,isinput,sDec) 
{ 
return function compareTRs(oTR1, oTR2) 
{ 
if(isinput == 1) 
{ 
var vValue1 = convert(oTR1.getElementsByTagName("input")[iCol].value); 
var vValue2 = convert(oTR2.getElementsByTagName("input")[iCol].value); 
} 
else 
{ 
var vValue1=""
if(oTR1.cells[iCol].firstChild != null)
{
	vValue1 = convert(oTR1.cells[iCol].firstChild.nodeValue, sDataType); 
}
var vValue2=""
if(oTR2.cells[iCol].firstChild != null)
{
 	vValue2 = convert(oTR2.cells[iCol].firstChild.nodeValue, sDataType); 
}
} 
if(sDec=='desc') 
{ 
if(sDataType=='int') 
{ 
return vValue1 == vValue2 ? 0 :(vValue1 - vValue2 <0 ? 1 : -1); 
} 
else if(sDataType =='cn') 
{ 
if(chrComp(vValue1,vValue2)>0) 
{ 
return -1; 
} 
else if(chrComp(vValue1,vValue2)<0) 
{ 
return 1; 
} 
else 
{ 
return 0; 
} 
} 
else 
{ 
if (vValue1 > vValue2) { 
return -1; 
} else if (vValue1 < vValue2) { 
return 1; 
} else { 
return 0; 
} 
} 
} 
else if(sDec=='asc') 
{ 
if(sDataType=='int') 
{ 
return vValue1 == vValue2 ? 0 :(vValue1 - vValue2 >0 ? 1 : -1); 
} 
else if(sDataType =='cn') 
{ 
return chrComp(vValue1,vValue2); 
} 
else 
{ 
if (vValue1 > vValue2) { 
return 1; 
} else if (vValue1 < vValue2) { 
return -1; 
} else { 
return 0; 
} 
} 
} 
}; 
} 
//重置单元格的classname 
function ChangeClsName(tr,num) 
{ 
num = num%2?1:2; 
num.toString(); 
for ( var i = 0 ; i < tr.childNodes.length; i ++ ) 
{ 
tr.childNodes[i].className = "row" + num 
} 
} 
/*排序方法（主函数） 
sTableID 表格的id 
iCol表示列索引 
1,当不是input类型时，iCol表示的是tr的第几个td; 
2,当是input类型时，则iCol表示在这个tr中的第几个input; 
sDataType表示该cell的数据类型或者该input的value 的数据类型. 默认是string，也可以int, float. cn是中文 
isinput表示排序的内容是不是input(1是, 0否) 
sDec表示倒序还是顺序(desc, 默认顺序), 避免出现input值改变之后再排序时候出现直接倒序的情况。 
*/ 
function sortTable(sTableID, iCol, sDataType, isinput, sDec, startRow) 
{ 
var oTable = sTableID; 
var oTBody = oTable.tBodies[0]; 
var colDataRows = oTBody.rows; 
var aTRs = new Array; 
//将所有列放入数组 
for (var i=0; i < colDataRows.length; i++) 
{
if(startRow){
if(i < startRow){
	continue;
}
}
aTRs[aTRs.length] = colDataRows[i]; 
} 
aTRs.sort(generateCompareTRs(iCol, sDataType,isinput, sDec)); 
var oFragment = document.createDocumentFragment(); 
for (var i=0; i < aTRs.length; i++) 
{
oFragment.appendChild(aTRs[i]); 
ChangeClsName(aTRs[i],i);
}
oTBody.appendChild(oFragment); 
}

function sortTd(name){
	var sortType = "${sortType?default("2")}";
	if(sortType == "2"){
		sortType = "1";
	}
	else{
		sortType = "2";
	}
	
	var url = location.href;
	if (url.indexOf("?") >= 0){
		var sci = url.indexOf("sortColumn");
		if(sci >= 0){
			var scvi = url.indexOf("=", sci);
			var scvii = url.indexOf("&", scvi + 1);
			if(scvii >= 0){
				var v = url.substring(sci, scvii);
			}
			else{
				var v = url.substring(sci);
			}
			url = url.replace(v, "sortColumn=" + name);			
		}
		else{
			url = url + "&sortColumn=" + name;
		}
		
		var sci = url.indexOf("sortType");
		if(sci >= 0){
			var scvi = url.indexOf("=", sci);
			var scvii = url.indexOf("&", scvi + 1);
			if(scvii >= 0){
				var v = url.substring(sci, scvii);
			}
			else{
				var v = url.substring(sci);
			}
			url = url.replace(v, "sortType=" + sortType);			
		}
		else{
			url = url + "&sortType=" + sortType;
		}
	}
	else{
		url = url + "?sortColumn=" + name + "&sortType=" + sortType;
	}
	location.href = url;
}

function sortTdInPage(name){
	tables = document.getElementsByTagName("TABLE");
	for(i = 0; i < tables.length; i ++){
		if(!tables[i].sortType){
			tables[i].sortType = 1;
		}
		else{
			tables[i].sortType = tables[i].sortType % 2 + 1;
		}
		rows = tables[i].rows;
		haveGetCell = -1;
		haveGetRow = -1;
		rows = tables[i].rows;
		for(j = 0; j < rows.length; j ++){
			cells = rows[j].cells;
			if(haveGetCell == -1){
				for(k = 0; k < cells.length; k ++){
					if(cells[k].sortName && cells[k].sortName != ""){
						if(name == cells[k].sortName){
							haveGetCell = k;
							haveGetRow = j;
							break;
						}
					}
				}
			}	
			else{
				break;
			}
		}
		if(haveGetCell != -1){
			sortTable(tables[i], haveGetCell, "cn", 0, (tables[i].sortType == 1 ? "asc" : "desc"), haveGetRow + 1);
			return tables[i].sortType;
		}
	}
}

var sortTdParam = function(name, reload){
	return function(){
		
		if(reload && reload == true){
			sortTd(name);
		}
		else{
			var sortType = sortTdInPage(name);
			tables = document.getElementsByTagName("TABLE");
			for(i = 0; i < tables.length; i ++){
				haveGetCell = -1;
				rows = tables[i].rows;
				for(j = 0; j < rows.length; j ++){
					cells = rows[j].cells;
					if(haveGetCell == -1){
						for(k = 0; k < cells.length; k ++){
							if(cells[k].sortName && cells[k].sortName != ""){
								var sortName = cells[k].sortName;
								var span = document.getElementById("SortSpan" + cells[k].uniqueID);
								if(!span){
									span = document.createElement("SPAN");
									span.id = "SortSpan" + cells[k].uniqueID;
									cells[k].appendChild(span);
								}
								if(sortName == name){
									if (sortType == "1")
										span.innerText = "↑";
									else if(sortType  == "2")
										span.innerText = "↓";
								}
								else{
									span.innerText = "";
								}
							}
						}
					}	
					else{
						break;
					}
				}	
			}
		}
	}
}

function initSortMark(reload){
	tables = document.getElementsByTagName("TABLE");
	for(i = 0; i < tables.length; i ++){
		haveGetCell = -1;
		rows = tables[i].rows;
		for(j = 0; j < rows.length; j ++){
			cells = rows[j].cells;
			if(haveGetCell == -1){
				for(k = 0; k < cells.length; k ++){
					if(cells[k].sortName && cells[k].sortName != ""){
						cells[k].style.textDecoration = "underline";
						cells[k].style.color="blue";
						cells[k].style.cursor="hand";
						var sortName = cells[k].sortName;
						if (window.attachEvent)
						    cells[k].attachEvent("onclick", sortTdParam(sortName, reload));
						else
						    cells[k].addEventListener("click", sortTdParam(sortName, reload), false);
							    
						if(reload && reload == true){
							if("${sortColumn?default("")}" == sortName){
								var span = document.createElement("SPAN");
								<#if sortType?default("") == "1">
									span.innerText = "↑";
								<#elseif sortType?default("") == "2">
									span.innerText = "↓";
								</#if>
								cells[k].appendChild(span);
							}
						}
					}
				}
			}	
			else{
				break;
			}
		}	
	}
}
</script>
<#import "/common/chinaexcel.ftl" as chinaExcel />
<script language="javascript">
	var baseTemplate = ""; 
	var execOnceLoadTemplate = 0;
	function init(){
		if(typeof(chinaExcel) != 'undefined'){
			chinaExcel.ReadHttpTabFile("${request.contextPath}/static/common/chinaexceltemplate.tab");
			chinaExcel.DesignMode = false;
			chinaExcel.DeleteAllPopupMenu();
			chinaExcel.AddPopupMenu("跳转到...","ChinaExcel.OnGoToCell",1,false);
			chinaExcel.AddPopupMenu("复制内容","ChinaExcel.OnEditCopy",1,false);
		}
	}
	function loadTemplate(template,canRefresh){
		execOnceLoadTemplate ++;
		chinaExcel.SetCanRefresh(false);
		if(template != baseTemplate){
			chinaExcel.ReadHttpTabFile(template);	
			baseTemplate = template;
		}
		else{
			//恢复到最近一次计算之前的状态。
			chinaExcel.RestoreAfterCalculate();
		}
		chinaExcel.DeleteAllPopupMenu();
		chinaExcel.AddPopupMenu("跳转到...","ChinaExcel.OnGoToCell",1,false);
		chinaExcel.AddPopupMenu("复制内容","ChinaExcel.OnEditCopy",1,false);				
		if(canRefresh) chinaExcel.SetCanRefresh(true);
	}
	
	<#--根据字符串取出数据-->
	function loadTemplateByString(dataString){
		chinaExcel.DesignMode = true;
		var tt = chinaExcel.ReadDataFromString(dataString);
		chinaExcel.DesignMode = false;		//设置或获得控件是否为设计模式	
		chinaExcel.ReCalculate();	//对整表所有公式全部进行计算
		chinaExcel.refresh();
		chinaExcel.SetCanRefresh(true);
	}
	
	<#-- 数据集取数设置 -->
	function loadDataCollection(template, url){
		chinaExcel.SetDataCollectionFetchDataMode(url,1,1);
		chinaExcel.DesignMode = false;		//设置或获得控件是否为设计模式	
		chinaExcel.ReCalculate();	//对整表所有公式全部进行计算
		chinaExcel.refresh();
		chinaExcel.SetCanRefresh(true);
	}
	
	function loadChinaExcelData(template, url, title, arrays, htmlOfPagination, scriptItems,isCallback, dataIsString){	
		if(execOnceLoadTemplate == 0){
			loadTemplate(template);
			execOnceLoadTemplate--;
		}
		if(scriptItems){
			for (var i = 0; i < scriptItems.length; i++){
				var item = scriptItems[i];				
				var pos = item.indexOf("=");				
				if (pos == -1){
					continue;
				}
				var nodeName = item.substring(0, pos);
				var nodeValue = item.substring(pos + 1);
				chinaExcel.SetStatScriptItem(nodeName, nodeValue,1);//设置脚本		
			}
		}
		if(dataIsString && dataIsString == true){
			chinaExcel.ReadStatDataFromString(url);//设置取数方式
		}
		else{
			if(typeof(url)=="object" ){//表示数组
				chinaExcel.SetStatFetchDataMode(url[0], 1, 1);//设置取数方式1	
				chinaExcel.SetStatFetchDataMode(url[1], 1, 2);//设置取数方式2
			}else{
				chinaExcel.SetStatFetchDataMode(url, 1, 1);//设置取数方式	
			}
		}
		chinaExcel.DesignMode = false;//设置或获得控件是否为设计模式	
		chinaExcel.ReCalculate();//对整表所有公式全部进行计算
		var maxCol = getMaxCol();		
		combiNation(1,1,1,maxCol);
		var mysubtitle = chinaExcel.FindCellVarNameRow("v_mytitle");
		if(mysubtitle > 0){
			combiNation(2,2,1,maxCol);
		}
		if(title){
			chinaExcel.SetCellValUseVarName("v_title", title);		
		}
		var arrayValue;
		var cellName;
		var cellValue;
		var pos;	
		if(arrays){
			for (var i = 0; i < arrays.length; i++){
				arrayValue = arrays[i];
				pos = arrayValue.indexOf("=");		
				if (pos == -1){
					continue;
				}
				cellName = arrayValue.substring(0, pos);			
				cellValue = arrayValue.substring(pos + 1);			
				chinaExcel.SetCellValUseVarName(cellName, cellValue);			
			}
		}
		if(htmlOfPagination){
			document.getElementById("htmlOfPagination").innerHTML = htmlOfPagination;
		}
		var subRow = chinaExcel.FindCellVarNameRow("v_subtitle");
		if(subRow > 0){
			if(maxCol > 4){
				var todaySize = 0;
				for(var j = maxCol; j > 0 ; j--){
					todaySize += chinaExcel.GetColSize(j,1);
					if(todaySize > 180) break;
				}
				combiNation(subRow,subRow,1,j);
				combiNation(subRow,subRow,j,maxCol);
				chinaExcel.SetCellHorzTextAlign(subRow,j,subRow,maxCol,3);//右对齐						
				chinaExcel.SetCellVal(subRow, j,getToday());
			}
		}
		chinaExcel.DeleteAllPopupMenu();
		chinaExcel.AddPopupMenu("跳转到...","ChinaExcel.OnGoToCell",1,false);
		chinaExcel.AddPopupMenu("复制内容","ChinaExcel.OnEditCopy",1,false);
		chinaExcel.AddPopupMenu("显示行列头","ChinaExcel.ShowHeader = not ChinaExcel.ShowHeader",2,false);
		
		var reportWidth=0;
		for(var i=1;i<=maxCol;i++){
			reportWidth+=chinaExcel.GetColSize(i,1);
		}
		var reportHeight=0;
		for(var j=1;j<=getMaxRow();j++){
			reportHeight+=chinaExcel.GetRowSize(j,1);
		}
		var winWidth=$(window).width();
		if(winWidth>=1200){
			winWidth=1200;
		}else{
			winWidth=1000;
		}
		if(winWidth>reportWidth){
			var left=(winWidth-reportWidth)/2;
			$('#TBid').css('padding-left',left);
		}else{
		
			$('#TBid').css('padding-left',0);
		}
		$('#TBid').css('height',reportHeight+50);
		
		chinaExcel.refresh();
		if(isCallback){
			parent.chinaExcelCallback();
		}
		chinaExcel.style.display = "";
		
	}
	
	function loadDataWithHideCols(template, url, title, arrays, hideCols){	
		if(execOnceLoadTemplate == 0){
			loadTemplate(template);
			execOnceLoadTemplate--;
		}
		chinaExcel.SetStatFetchDataMode(url, 1, 1);	//设置取数方式				
		chinaExcel.DesignMode = false;		//设置或获得控件是否为设计模式	
		chinaExcel.ReCalculate();	//对整表所有公式全部进行计算
		
		var maxCol = getMaxCol();			
		combiNation(1,1,1,maxCol);
		
		//设置隐藏多列
		if(hideCols) {
			multiColsHide(hideCols);
		}
		if(title){
			chinaExcel.SetCellValUseVarName("v_title", title);  //设置指定单元变量名所在的单元的单元内容				
		}	
		var arrayValue;
		var cellName;
		var cellValue;
		var pos;		
		if(arrays){
			for (var i = 0; i < arrays.length; i++){
				arrayValue = arrays[i];
				pos = arrayValue.indexOf("=");				
				if (pos == -1){
					continue;
				}
				cellName = arrayValue.substring(0, pos);				
				cellValue = arrayValue.substring(pos + 1);				
				chinaExcel.SetCellValUseVarName(cellName, cellValue);				
			}		
		}
		//判断是否有副标题
		var subRow = chinaExcel.FindCellVarNameRow("v_subtitle");
		if(subRow > 0){
			if(maxCol > 4){					
				var todaySize = 0; //日期宽度,单位为象素
				for(var j = maxCol; j > 0 ; j--){
					todaySize += chinaExcel.GetColSize(j,1);
					if(todaySize > 180) break;
				}
				
				combiNation(subRow,subRow,1,j);
				combiNation(subRow,subRow,j+1,maxCol);
				chinaExcel.SetCellHorzTextAlign(subRow,j,subRow,maxCol,3);//右对齐						
				chinaExcel.SetCellVal(subRow, j+1,getToday());
			}
		}		
		chinaExcel.DeleteAllPopupMenu();   //删除所有的弹出菜单
		chinaExcel.AddPopupMenu("跳转到...","ChinaExcel.OnGoToCell",1,false);
		chinaExcel.AddPopupMenu("复制内容","ChinaExcel.OnEditCopy",1,false);
		chinaExcel.refresh();
		chinaExcel.style.display = "";
	}
	
	function loadImage(szURL){	
		loadImageWithName(szURL,'v_photo',true,false);
	}
	
	function loadImageWithName(szURL,cellName,bPositionInCell,bOriginalSize){
		//取照片单元格的名称
		if (cellName == null){
			cellName = 'v_photo';
		}
		//取得照片位置		
		var nRow = chinaExcel.FindCellVarNameRow(cellName);
		var nCol = chinaExcel.FindCellVarNameCol(cellName);
		loadImageWithPos(szURL,nRow,nCol,bPositionInCell,bOriginalSize);
	}
	
	function loadImageWithPos(szURL,nRow,nCol,bPositionInCell,bOriginalSize){			
		//是否放在单元格里面	
		if (bPositionInCell == null){
			bPositionInCell = true;
		}
		//是否原始大小
		if (bOriginalSize == null){
			bOriginalSize = false;
		}
		chinaExcel.SetCellProtect(nRow,nCol,nRow,nCol,false);		
		var rtn = chinaExcel.ReadHttpImageFile(szURL,nRow,nCol,bPositionInCell,bOriginalSize);	
		chinaExcel.SetCellProtect(nRow,nCol,nRow,nCol,true);
		chinaExcel.DeleteAllPopupMenu();
		chinaExcel.AddPopupMenu("跳转到...","ChinaExcel.OnGoToCell",1,false);
		chinaExcel.AddPopupMenu("复制内容","ChinaExcel.OnEditCopy",1,false);
		chinaExcel.refresh();		
	    return rtn;
	}
	
	function getToday(){
		var d,s ;          
	    d = new Date(); 
	    s = d.getFullYear() + "年";                            
	    s += (d.getMonth() + 1) + "月";            
	    s += d.getDate() + "日";  
	    return s;
	}

	function combiNation(StartRow, EndRow, StartCol, EndCol){
		chinaExcel.CombiNation(StartRow, EndRow, StartCol, EndCol);
	}
	
	function refresh(){
		chinaExcel.refresh();
	}
	
	function getMaxRow(){
		return chinaExcel.GetMaxRow();
	}
	
	function getMaxCol(){
		return chinaExcel.GetMaxCol();
	}
	
	function setMaxRows(row){
		chinaExcel.SetMaxRows(row);
	}
	
	function setMaxCols(col){
		chinaExcel.SetMaxCols(col);
	}
	
	function setAllowRowResizing(bSet){
		chinaExcel.SetAllowRowResizing(bSet);
	}
	
	function setRowSize(StartRow, EndRow, Height, type){
		chinaExcel.SetRowSize(StartRow, EndRow, Height, type);
	}
	
	function setCellHorzTextAlign(StartRow, StartCol, EndRow, EndCol, nTextAlign){
		chinaExcel.SetCellHorzTextAlign(StartRow, StartCol, EndRow, EndCol, nTextAlign);
	}
	
	function setCellVal(Row, Col, CellValue){
		chinaExcel.SetCellVal(Row, Col, CellValue);
	}	
	
	function getCellVal(Row, Col){
		return chinaExcel.GetCellValue(Row, Col);
	}
	
	function getCellUserStringValue(nRow, nCol){
		return chinaExcel.GetCellUserStringValue(nRow, nCol);
	}
	
	function getCellProtect(Row, Col){
		return chinaExcel.IsCellProtect(Row, Col);
	}
	
	function getRGBValue(rValue,gValue,bValue){
		return chinaExcel.GetRGBValue(rValue,gValue,bValue);
	}
	
	function goToCell(Row, Col){
		chinaExcel.GoToCell(Row, Col);
	}
	
	function setCellAutoWrap(StartRow, StartCol, EndRow, EndCol, bSet){
		chinaExcel.SetCellAutoWrap(StartRow, StartCol, EndRow, EndCol, bSet);
	}
	
	function setCellProtect(StartRow, StartCol, EndRow, EndCol, bProtect){
		chinaExcel.SetCellProtect(StartRow, StartCol, EndRow, EndCol, bProtect);
	}
	
	function setCellTextHide(StartRow, StartCol, EndRow, EndCol, bProtect){
		chinaExcel.SetCellTextHide(StartRow, StartCol, EndRow, EndCol, bProtect);
	}
	
	function setRefresh(bCanRefresh){
		chinaExcel.SetCanRefresh(bCanRefresh);		
		if(bCanRefresh){
			chinaExcel.RefreshViewSize();
		}
		chinaExcel.style.display = "";
	}

	function deleteRow(StartRow, EndRow){
		chinaExcel.DeleteRow(StartRow, EndRow);
	}
	
	function setCellBkColor(StartRow, StartCol, EndRow, EndCol,Color){
		chinaExcel.SetCellBkColor(StartRow, StartCol, EndRow, EndCol,Color);
	}
	
	function drawCellBorder (StartRow, StartCol, EndRow, EndCol, LogPen, PenColor, Type){
		chinaExcel.DrawCellBorder (StartRow, StartCol, EndRow, EndCol, LogPen, PenColor, Type);
	}
	
	function getFirstRow(){
		var firstrow = 0;		
		var head = chinaExcel.GetRowLabel();
		firstrow = head + 1;
		return firstrow;
	}

	function sortCol(SortByColName,Ascending){
		var SortByCol = chinaExcel.FindCellVarNameCol(SortByColName);
		var StartRow = getFirstRow();		
		var endCol = chinaExcel.GetMaxCol();
		chinaExcel.SortCol(SortByCol,StartRow,1,getMaxRow(),endCol,Ascending);
	}

	function createChartByCol(title,xLabel,yLabel,chartType,yCount){		
		//X轴所在列
		var xCol = chinaExcel.FindCellVarNameCol("v_x1");
		//Y轴所在列
		var yStartCol = chinaExcel.FindCellVarNameCol("v_y1");		
		var yEndCol = 0;
		if(yCount == 0){  //交叉表
			yEndCol = chinaExcel.GetMaxCol();
		}else{
			yEndCol = chinaExcel.FindCellVarNameCol("v_y" +yCount.toString());		
		}
		//Y轴所在行
		var yRow = chinaExcel.FindCellVarNameRow("v_y1");
		//设置数据区域		
		var dataStartRow = chinaExcel.FindCellVarNameRow("v_x1") +1;
		var dataEndRow = getMaxRow();
		chinaExcel.SetChartDataArea(dataStartRow, yStartCol, dataEndRow, yEndCol);
		
		chinaExcel.SetSeriesDirection(1); //数轴方向，按行0,按列1
		setChartDrawRect(); //设置位置
		var handle = chinaExcel.CreateChart(); //自动生成图表		
		//设置图表图注中的系列数据区域
		yStartCol += 64;
		yStartCol = unescape("%" + yStartCol.toString(16));				
		yEndCol += 64;
		yEndCol = unescape("%" + yEndCol.toString(16)); 				
		var legendArea = yStartCol + yRow.toString() + ":" + yEndCol + yRow.toString();
    	chinaExcel.SetChartObjectLegendDataArea(handle,legendArea); 
    	//分类X轴数据区域
    	xCol += 64;
    	xCol = unescape("%" + xCol.toString(16)); 
		var XLabelArea = xCol + dataStartRow.toString() + ":" + xCol + dataEndRow.toString();
		chinaExcel.SetChartObjectXLabelDataArea(handle,XLabelArea); 
		setChart(title,xLabel,yLabel,chartType);
		chinaExcel.RefreshAllObjectData(); //刷新图表		
	}
	
	function createChartByRow(title,xLabel,yLabel,chartType,yCount){		
		//X轴所在行		
		var xRow = chinaExcel.FindCellVarNameRow("v_x1");		
		//Y轴所在行
		var yStartRow = chinaExcel.FindCellVarNameRow("v_y1");		
		var yEndRow = 0;
		if(yCount == 0){  //交叉表
			yEndRow = chinaExcel.GetMaxRow();
		}else{
			yEndRow = chinaExcel.FindCellVarNameRow("v_y" +yCount.toString());		
		}
		//Y轴所在列
		var yCol = chinaExcel.FindCellVarNameCol("v_y1");	
		//设置数据区域		
		var dataStartCol = chinaExcel.FindCellVarNameCol("v_x1") +1;
		var dataEndCol = getMaxCol();
		chinaExcel.SetChartDataArea(yStartRow, dataStartCol, yEndRow, dataEndCol);
		chinaExcel.SetSeriesDirection(0); //数轴方向，按行0,按列1		
		setChartDrawRect(); //设置位置		
		var handle = chinaExcel.CreateChart(); //自动生成图表
		//分类X轴数据区域	
		dataStartCol += 64;
		dataStartCol = unescape("%" + dataStartCol.toString(16));				
		dataEndCol += 64;
		dataEndCol = unescape("%" + dataEndCol.toString(16)); 		
		var XLabelArea = dataStartCol + xRow.toString()  + ":" + dataEndCol + xRow.toString();
		chinaExcel.SetChartObjectXLabelDataArea(handle,XLabelArea); 
    	//设置图表图注中的系列数据区域	    
		yCol += 64;
		yCol = unescape("%" + yCol.toString(16));
		var legendArea = yCol + yStartRow.toString() + ":" + yCol + yEndRow.toString();
    	chinaExcel.SetChartObjectLegendDataArea(handle,legendArea); 
		setChart(title,xLabel,yLabel,chartType);
		chinaExcel.RefreshAllObjectData(); //刷新图表		
	}
	
	function setChartDrawRect(){
		var left = 0;
		var top = 0; 
		var right = 0;
		var bottom = 0;
		var chartRow = chinaExcel.FindCellVarNameRow("v_chart");
		var chartCol = chinaExcel.FindCellVarNameCol("v_chart");
		for(var i=1;i < chartRow;i++){
			top = top + chinaExcel.GetRowSize(i,1);
		}
		for(var i=1;i < chartCol;i++){
			left = left + chinaExcel.GetColSize(i,1);
		}
		for(var i=1;i <= chinaExcel.GetMaxCol();i++){
			right = right + chinaExcel.GetColSize(i,1);
		}
		for(var i=1;i <= chartRow;i++){
			bottom = bottom + chinaExcel.GetRowSize(i,1);
		}	
		chinaExcel.SetChartDrawRect(left,top,right,bottom);
	}
		
	function setChart(title,xLabel,yLabel,chartType){
		var handle = chinaExcel.GetChartObjectHandle(0);	//图表对象句柄			
		chinaExcel.SetChartObjectType(handle,chartType); 	//画图:柱状图 0 ；线状图 1
		chinaExcel.SetChartObjectTitle(handle,title);  //设置标题
		chinaExcel.SetChartObjectXAxisLabel(handle,xLabel); //设置X轴坐标名称
		chinaExcel.SetChartObjectYAxisLabel(handle,yLabel); //设置Y轴坐标名称
		chinaExcel.SetChartShowLegend(handle,true); //显示图例
		chinaExcel.SetObjectHasBorder(handle,0); //设置图表的外框为无线
		chinaExcel.SetObjectProtect(handle,1); //设置图表保护		
		chinaExcel.RefreshAllObjectData(); //刷新图表		
	}
	
	function loadXmlData(template,url){
		chinaExcel.ReadHttpTabFile(template);
		chinaExcel.ReadHttpXMLFile(url);
		chinaExcel.DesignMode = false;
		chinaExcel.ReCalculate();
		chinaExcel.style.display = "";
	}
	
	function setCellValUseVarName(colName,colValue){
		chinaExcel.SetCellValUseVarName("v_" + colName, colValue);
	}
	
	function dropCol(startCol,endCol){
		chinaExcel.SetCanRefresh(false);
		chinaExcel.DeleteCol(startCol,endCol);
		chinaExcel.SetCanRefresh(true);			
		chinaExcel.RefreshViewSize();
	}

	function setColHide(startCol,endCol,bHide){
		chinaExcel.SetColHide(startCol,endCol,bHide);
	}
	
	function setRowHide(startCol,endCol,bHide){
		chinaExcel.SetRowHide(startCol,endCol,bHide);
	}	
	
	function multiColsHide(hideCols){
		for(var i=0;i<hideCols.length;i++) {
			setColHide(hideCols[i], hideCols[i], true);
		}
	}
	
	function getChinaExcel(){
		return chinaExcel;
	}
	
	function getDataCollections(){
		return chinaExcel.GetDataCollections();
	}
	
	function loadFileFromHttp(url){
		chinaExcel.ReadHttpFile(url);
		CsToBs();
		chinaExcel.DesignMode = false;		//设置或获得控件是否为设计模式	
		chinaExcel.ReCalculate();	//对整表所有公式全部进行计算
		chinaExcel.refresh();
		chinaExcel.SetCanRefresh(true);
	}
	
	function CsToBs(){
	    var tabType,strSql,strSqlSub,strConn;
	    strConn=chinaExcel.GetDatabaseSource();
	    strConn=encodeURI(strConn);
	    //获得报表类型 0表示分组报表, 1表示普通报表, 2表示主从报表, 4 表示分组交叉报表； 5 表示交叉报表。
	    tabType=chinaExcel.GetStatScriptItem("stat:cmd",1); 
	    //获得报表取数路径
	    strSql=chinaExcel.GetStatScriptItem("stat:data:sql",1);
	    strSql=encodeURI(strSql);
	    if(tabType=="2"){
	        //报表类型为主从报表，获得从表的取数路径
	        strSqlSub=chinaExcel.GetStatScriptItem("stat:data:sql2",1);
	        strSqlSub=encodeURI(strSqlSub);
	        chinaExcel.SetStatScriptItem("stat:data:xml","${request.contextPath}/report/both/userdefined/getChinaExcelData.action?tableType="+tabType+"&sqlString="+strSql+"&sqlSubString="+strSqlSub,1);
	    }else{
	        chinaExcel.SetStatScriptItem("stat:data:xml","${request.contextPath}/report/both/userdefined/getChinaExcelData.action?tableType="+tabType+"&sqlString="+strSql,1);
	    }
	    chinaExcel.SetStatScriptItem("stat:data:dataflag","2",1);
	    chinaExcel.SetStatScriptItem("stat:data:readfromhttp","1",1);
	    chinaExcel.SetStatScriptItem("stat:data:sql","",1);
	}
</script>
<div class="t-right">
	<a href="javascript:void(0);" onclick="chinaExcelPreview();return false;" class="abtn-blue-big">打印预览</a>
    <a href="javascript:void(0);" onclick="chinaExcelSave();return false;" class="abtn-blue-big">另存为</a>
</div>
<div id="TBid"><@chinaExcel.chinaExcel /></div>
<script>
$(document).ready(function(){
	<#if onload?string("yes","no") == "yes">
		init();
	</#if> 
})
</script>
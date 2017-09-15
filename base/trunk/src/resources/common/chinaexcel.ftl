<#macro chinaExcel style="LEFT: 0px; WIDTH: 100%; TOP: 0px; HEIGHT: 100%;display:none;overflow:hidden;" >
	<OBJECT id=chinaExcel
		style="${style}"
		CODEBASE='${request.contextPath}/downfolder/ceuser/chinaexcelweb.cab#version=3,8,5,8'
		classid="CLSID:15261F9B-22CC-4692-9089-0C40ACBDFDD8" name=chinaExcel>
		<PARAM NAME="_Version" VALUE="131072">
		<PARAM NAME="_ExtentX" VALUE="13970">
		<PARAM NAME="_ExtentY" VALUE="9155">
		<PARAM NAME="_StockProps" VALUE="0">
		<SPAN STYLE="color:red">不能装载报表控件。请在检查浏览器的选项中检查浏览器的安全设置或请点击<a href="${request.contextPath}/downfolder/chinaexcelweb.exe">下载</a>安装后重试</SPAN>
	</OBJECT> 
	<script language="javascript">
		if(typeof(chinaExcel) != 'undefined'){
			chinaExcel.SetPath("${request.contextPath}/static/downfolder/");
			var ret = chinaExcel.Login("ZDSoft报表打印预览","fcaf23d41893f5ebeb4cbe221b1b83a3","浙江大学网络信息系统有限公司");
			if(!ret) alert("ZDSoft报表控件注册失败！");
		}
		
		function chinaExcelPreview(){
			if(chinaExcel.style.display =="none"){
				alert("没有需要打印预览的数据！");
				return;
			}
			chinaExcel.OnFilePrintPreview();
		}
		function chinaExcelPreview1(){
			if(chinaExcel.style.display =="none"){
				alert("没有需要打印预览的数据！");
				return;
			}
			chinaExcel.SetPrintPreviewMode(false);
			chinaExcel.SetShowNewPrintPreviewToolBar(true); 
			chinaExcel.OnFilePrintPreview();
		}
		
		function chinaExcelPrint(){
		    chinaExcel.SetPrintLeftMargin(10);
		    chinaExcel.SetPrintRightMargin(10);
			chinaExcel.PrintFile();
		}
		
		function chinaExcelSave(){
			if(chinaExcel.style.display =="none"){
				alert("没有需要另存为的数据！");
				return;
			}
			chinaExcel.OnFileExport();
		}
	</script>
</#macro> 

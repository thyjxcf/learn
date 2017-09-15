


<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/public.css"/>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/layout.css"/>
<#if loginInfo.user.ownerType ==3>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/green.css"/>
<#elseif loginInfo.user.ownerType==1>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/yellow.css"/>
<#else>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/default.css"/>
</#if> 

<script>
_contextPath = "${request.contextPath}";
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/jquery.form.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/jscroll.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript-chkRadio.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/jwindow.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/LodopFuncs.js"></script>
<script>
function doPrint(){
		LODOP=getLodop(null,null,"${request.contextPath}");
		LODOP.ADD_PRINT_HTM("25mm","15mm","RightMargin:15mm","BottomMargin:15mm",getPrintContent(jQuery('#container')));
		LODOP.SET_PRINT_PAGESIZE(2, 0,0,"");
		LODOP.PREVIEW();
	}
	
function saveAsFile(){
		LODOP=getLodop(null,null,"${request.contextPath}");  
		//LODOP.PRINT_INIT("");
		LODOP.ADD_PRINT_TABLE(100,20,500,60,getPrintContent(jQuery('#container')));
		//LODOP.SET_SAVE_MODE("FILE_PROMPT",false);
		LODOP.SET_SAVE_MODE("QUICK_SAVE",true);//快速生成（无表格样式,数据量较大时或许用到）
		//if (LODOP.SAVE_TO_FILE(document.getElementById("T1").value)) alert("导出成功！");
		LODOP.SAVE_TO_FILE(jQuery("tbody > tr").find("td").first().html()+".xls");	
	}
</script>
<div class="fn-clear pb-15">
    
    <p class="fn-right t-right pt-15">
        <a href="#" onclick="doPrint();" class="abtn-blue-big">打印</a>
         <a href="#" onclick="saveAsFile();" class="abtn-blue-big">下载</a>
    </p>

</div>

<div id="container" >
${dataHtml}
</div>
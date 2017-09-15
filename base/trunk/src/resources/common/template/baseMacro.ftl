<#-- html头 -->
<#macro head title>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	
	<link href="${request.contextPath}/static/css/newstyle/public.css" rel="stylesheet" type="text/css" />
	<link href="${request.contextPath}/static/css/newstyle/datePicker.css" rel="stylesheet" type="text/css" />
	<link href="${request.contextPath}/static/skin/css/style.css" rel="stylesheet" type="text/css" />
	
	<!-- jquery script -->
	<script type="text/javascript" src="${request.contextPath}/static/js/newstyle/jquery.js"></script>
	
	<!-- date picker -->
	<script type="text/javascript" src="${request.contextPath}/static/js/newstyle/date.js"></script>
	<script type="text/javascript" src="${request.contextPath}/static/js/newstyle/jquery.datePicker.js"></script>
	
	<!-- modal -->
	<script type="text/javascript" src="${request.contextPath}/static/js/newstyle/loadAjax.js"></script>
	
	<!-- page inti function -->
	<script type="text/javascript" src="${request.contextPath}/static/js/newstyle/script.js"></script>
	
	
	<script type="text/javascript" src="${request.contextPath}/static/js/newstyle/table-split-resize.js"></script>
	
	<!--[if IE 6]>
	<script type="text/javascript" src="${request.contextPath}/static/js/newstyle/script-ie6.js"></script>
	<![endif]-->

	<title><@title/></title>
</head>
</#macro>

<#macro breadcrumbs > 
	<!-- breadcrumbs -->
		<div class="breadcrumbs">
	    	<div class="crumbs">您当前所在位置：<a href="javascript:;">查询统计</a>&gt;&gt;<a href="javascript:;">两免一补查询</a></div>
	    </div>
	<!-- tree % table out -->
</#macro>

<#--左边那棵树 -->
<#macro tree treeInner> 
	<link rel="stylesheet" href="${request.contextPath}/static/css/layout.css" type="text/css">
	<link rel="stylesheet" href="${request.contextPath}/static/common/xtree/xtree.css" type="text/css"/>
	<script language="javascript">
		var prefix = "/static/common/xtree/";
	</script>	
	<script language="JavaScript" src="${request.contextPath}/static/common/xtree/xtree.js"></script>
	<script language="JavaScript" src="${request.contextPath}/static/common/xtree/xloadtree.js"></script>
	<script language="JavaScript" src="${request.contextPath}/static/common/xtree/xmlextras.js"></script>

	<!-- tree out on the left side -->
  	<@treeInner/>  
	<!-- End .treeOut -->
</#macro>

<#--右边列表上部的tab框 -->
<#macro tab tab> 
	<div class="tab">
		<@tab/>   
    </div>
</#macro>

<#macro tabOption tabOption> 
	<div class="tabOption">
		<@tabOption/>
	</div>
</#macro>

<#--右边列表上部的条件框 -->
<#macro condition condition>
<div class="dateTableOperating">
	<@condition/>   
</div>
</#macro>

<#--列表的表头-->      
<#macro tabletitle tabletitle>
<div class="tableTit">
	<@tabletitle/>   
</div>
</#macro>    
    
<#-- 右边列表 -->        
<#macro dateTable dateTable>
<div class="dateTable">
	<@dateTable/>   
</div>
</#macro>

<#-- 右边form表单 -->
<#macro dataform dataform>
<div class="form">
	<@dataform/>
</div>
</#macro>

<#--右边列表底部 -->
<#macro dateTableBottom dateTableBottom>
	<!-- bottom Operating -->
	<div class="bottomOperating">
		<@dateTableBottom/>   
	</div> 
</#macro> 


<#--号码规范设置说明 -->
<#macro codeRule >
	<!-- right Intro box -->
	<div class="rightSide rightIntro">
		
	    <h2>号码规范设置说明</h2>
	    
		<div class="rightIntroBox">
	    	<p>对号码的每一位分别进行维护</p>
	        <ul>
	        	<li>1、号码位置：维护要设置号码的第几位；</li>
	            <li>2、类型：维护对应的号码位取自哪一个字段；</li>
	            <li>3、取值位：如果该位从该类型字段中取，则需要输入该位在这种类型中的所在位；</li>
	            <li>4、类型长度：指该项类型的总长度，取值位不能大于这个长度</li>
	            <li>5、固定值：若该位是固定值，则需要输入此固定值；</li>
	            <li>6、类型为“性别”时，“1”为男生，“2”为女生；</li>
	            <li>7、类型为“流水号”时，取值位则表示的是流水号的长度；</li>
	            <li>8、“流水号”行会自动添加且不能删除，其值必须是在1-10之间的一个整数。</li>
	        </ul>
	        
	        <div class="corcen tl"></div>
	        <div class="corcen tr"></div>
	        <div class="corcen bl"></div>
	        <div class="corcen br"></div>
	        
	    </div>
	    
	</div>   
</#macro> 

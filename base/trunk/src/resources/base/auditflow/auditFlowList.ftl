<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#include "/common/css.ftl">
<#include "/common/js.ftl">
<#include "/common/public.ftl">
<#import "../../common/htmlcomponent.ftl" as common />
<#assign ec=JspTaglibs["/WEB-INF/tld/extremecomponents.tld"]>
<#include "/common/handlefielderror.ftl"> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>异动审核步骤设定管理</title>

<script type="text/javascript" language="javascript">
	//隐藏删除单位
	function deleteTr(divId){
		var num = 0;
		//在审核单位只剩一项时，禁止用户在进行删除。
		for( var i = 1; i <= ${auditFlowListNew.size()}; i++ ){
			if ( document.getElementById("tr_"+i).style.display != "none" ){
				num++;
			}
		}
		if(num > 2)
			document.getElementById("tr_"+divId).style.display="none";
		else
			showMsgWarn("不能完成删除！");
		unite();	
		setNumble();
	}
	
	//对显示行的序号进行重设
	function setNumble(){
		var j=1;
		for( var i = 1; i <= ${auditFlowListNew.size()}; i++ ){
			if ( document.getElementById("tr_"+i).style.display != "none" ){
				document.getElementById("numbleTd_"+i).innerHTML = j;
				j++;
			}
		}
	}
	//对相同项进行合并
	function unite(){
		var j=1;
		for( var i = 2; i <= ${auditFlowListNew.size()}; i++ ){
			if (document.getElementById("tr_"+i).style.display != "none"){
				if(document.getElementById('insertTd_'+j).innerHTML == document.getElementById('insertTd_'+i).innerHTML){
					document.getElementById("tr_"+i).style.display="none";
				}
				j = i;
			}
		}
	}
	
	//隐藏删除所选单位
	function deleteTrs(){
		//在审核单位只剩一项时，禁止用户在进行删除。
		var num = 0;
		for( var i = 1; i <= ${auditFlowListNew.size()}; i++ ){
			if ( document.getElementById("tr_"+i).style.display != "none" ){
				num++;
			}
		}
		if(num <= 2){
			showMsgWarn("不能完成删除！");return;
		}
			
		var cks = document.getElementsByName("ck_name");		
		var g = 0;
		for( var i = 0; i < cks.length; i++ ){
			if ( cks[i].checked ){
				g++;
				//在审核单位只剩一项时，禁止用户在进行删除。
				num = 0;
				for( var o = 1; o <= ${auditFlowListNew.size()}; o++ ){
					if ( document.getElementById("tr_"+o).style.display != "none" ){
						num++;
					}
				}
				if(num > 2){
					document.getElementById("tr_"+cks[i].value).style.display="none";
				}
			}
		}
		if(g==0){
			showMsgWarn("请选择要删除的单位");return;
		}
		deleteTb();
		unite();	
		setNumble();
	}
	
	var addTrID;//插入位置序号
	var insertTrID;//要插入的单位序号
	var allTrID;//现存的单位序号
	
	//插入单位显示
	function insertTr(divId){
		addTrID = divId;
		var tableId = document.getElementById("selectDiv");
		openDiv("#selectDiv","#selectDiv close","",null,"320px","100px;","none",null,noOverlayer|followCursor);
	}
	
	//插入单位隐藏
	function deleteTb(divId){
		addTrID = divId;
		var tableId = document.getElementById("selectDiv");
		tableId.style.display="none";
		closeDiv("#selectDiv")
			
	}
	
	//添加单位项
	function addTr(divId){
		var cks = document.getElementsByName("rd_name");		
		var j = 0;
		//判断是否选择了所要添加的单位
		for( var i = 0; i < cks.length; i++ ){
			if ( cks[i].checked ){
				j++;
				insertTrID = cks[i].value;
			}
		}
		if(j==0){
			showMsgWarn("请选择要添加的单位");return;
		}
		
		//判断所要添加的单位,是否与所添加位置的上面或下面的单位重复--开始
		if(document.getElementById('insertTd_'+addTrID).innerHTML == document.getElementById('selectTd_'+insertTrID).innerHTML){
			showMsgWarn("不能连续添加相同单位");return;
		}
		var n = 0;
		for( var i = addTrID-1; i >= 1; i-- ){
			if(document.getElementById("tr_"+i).style.display != "none"){
				if(n==0 && document.getElementById('insertTd_'+i).innerHTML == document.getElementById('selectTd_'+insertTrID).innerHTML){
					showMsgWarn("不能连续添加相同单位");return;
				}
				n++;
			}
		}
		//判断所要添加的单位,是否与所添加位置的上面或下面的单位重复--结束
		//完成现存审核单位列表项
		for( var i = 1; i <= ${auditFlowListNew.size()}; i++ ){
			if ( document.getElementById("tr_"+i).style.display != "none" ){
				if(allTrID==null)
					allTrID = i +"-";
				else 
					allTrID = allTrID + i +"-";
			}
		}
		//var url = "refurbishAuditFlowList.action?selectType=${selectType}&section=${section}&regionLevel=${regionLevel}&auditType=${auditType}&sessionState=${sessionState}&refurbish="+addTrID+"--"+insertTrID+"--"+allTrID;
   		//window.document.location.href=url;
   		
   		//   		var url = "showAuditFlowList.action?selectType="+radioValue+"&auditType="+auditType+"&section=1";
   		var formfl = document.getElementById("flowAction");
   		formfl.action ="refurbishAuditFlowList.action";
   		document.getElementById("selectType").value="${selectType}";
   		document.getElementById("auditType").value="${auditType}";
   		document.getElementById("section").value="${section}";
   		document.getElementById("refurbish").value=addTrID+"--"+insertTrID+"--"+allTrID;  		
   		formfl.submit();
// 		window.document.location.href=url;

	}
	
	//最终对审核列表保存
	function add(){
		//读取所选学段信息
		var section = '${section}';
		<#if showSections>
		var radios = document.getElementsByName("selectNode");
		for( var i = 0; i < radios.length; i++ ){
			if ( radios[i].checked ){
				section = radios[i].value;
			}
		}
		</#if>
		
		//完成现存审核单位列表项
		var alertStr = "";
		for( var i = 1; i <= ${auditFlowListNew.size()}; i++ ){
			if ( document.getElementById("tr_"+i).style.display != "none" ){
				if(allTrID==null)
					allTrID = "-" + i + "-";
				else 
					allTrID = allTrID + i +"-";
				var tdHtml = document.getElementById('insertTd_'+i).innerHTML;
				if(alertStr==""){
					alertStr = trim(tdHtml);
				}else{
					alertStr = alertStr + "→ " + trim(tdHtml);
				}
			}
		}
		if(confirm("审核顺序为： " + alertStr + "\n\n你确定吗？")){
			//var url = "addAuditFlowList.action?selectType=${selectType}&regionLevel=${regionLevel}&auditType=${auditType}&refurbish="+allTrID+"&section="+section;
   			//window.document.location.href=url;
   			var formfl = document.getElementById("flowAction");
   			formfl.action ="addAuditFlowList.action";
   			document.getElementById("selectType").value="${selectType}";
   			document.getElementById("auditType").value="${auditType}";
   			document.getElementById("section").value=section;
   			document.getElementById("refurbish").value=allTrID;  
   			formfl.submit();
		}
	}
	
	//更改学段
	function doType(section) {
   		//var url = "showAuditFlowList.action?selectType=${selectType}&regionLevel=${regionLevel}&auditType=${auditType}&section="+section;
   		//window.document.location.href=url;
   		
   		//   		var url = "showAuditFlowList.action?selectType="+radioValue+"&auditType="+auditType+"&section=1";
   		var formfl = document.getElementById("flowAction");
   		formfl.action ="showAuditFlowList.action";
   		document.getElementById("selectType").value="${selectType}";
   		document.getElementById("auditType").value="${auditType}";
   		document.getElementById("section").value=section;
   		formfl.submit();
// 		window.document.location.href=url;
    }
    
    //返回
    function backtrack(){
    	//window.document.location.href="showAuditTypeList.action?selectType=${selectType}";
    	var formfl = document.getElementById("flowAction");
    	formfl.action ="showAuditTypeList.action";
   		document.getElementById("selectType").value="${selectType}";
    	formfl.submit();
    }
</script>
</head>
<body>
<form name="flowAction" id="flowAction" action="showAuditFlowList.action" method="post">
<input type="hidden" name="initVerifyKey"  id="initVerifyKey" value="${initVerifyKey?default('')}">
<input type="hidden" name="selectType" id="selectType"  value="">
<input type="hidden" name="regionLevel" id="regionLevel" value="${regionLevel}">
<input type="hidden" name="nowRegionLevel" id="nowRegionLevel" value="${nowRegionLevel}">
<input type="hidden" name="auditType" id="auditType" value="">
<input type="hidden" name="section" id="section" value="${section!}">
<input type="hidden" name="sessionState" id="sessionState" value="${sessionState}">
<input type="hidden" name="refurbish" id="refurbish" value="">
<input type="hidden" name="businessType" id="businessType" value="${businessType}">
<input type="hidden" name="showFlowType" id="showFlowType" value="${showFlowType?string}">
<input type="hidden" name="showSections" id="showSections" value="${showSections?string}">
<input type="hidden" name="schConfirm" id="schConfirm" value="${schConfirm?string}">
<input type="hidden" name="showDefault" id="showDefault" value="${showDefault?string}">
<input type="hidden" name="outerframe" id="outerframe" value="${outerframe}">
</form>
<div id="selectDiv" class="jwindow">
<p class="dt">	请选择添加项</p>
<div class="table-all" >
    <div class="table-header">
        <!--[if lte IE 6]>
    	<div style="position:absolute;z-index:-1;width:851px;height:30px;">  
        	<iframe style="width:851px;height:30px;border:0;filter:alpha(opacity=0);-moz-opacity:0"></iframe>  
        </div> 
        <![endif]-->
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table1">
            <tr id="copy_tr">
            </tr>
        </table>
    </div>
    <div class="table-content" id="table1" style="height:60px;">
		<table id="InputAll2" class="table1 table-vline" > 
			<#if auditFlowList?exists>
				<#list auditFlowList as y>
				<#if y[2] != "0">
				<tr  id="addTr_${y[0]?default("")}">
					<td width="60">
						<input type="radio" name="rd_name" id="radio_${y[0]}" value="${y[0]}"></input>
					</td>
					<td id="selectTd_${y[0]}">
						${y[4]?default("")}
					</td>
				</tr>
				</#if>
				</#list>
			</#if>
		</table>
	</div>
	</div>		
		<@common.ToolbarBlank class="t-center">
				<span class="input-btn1" ><button type="button" onclick="addTr();">确定</button></span>&nbsp;&nbsp;&nbsp;&nbsp;
				<span class="input-btn1" ><button type="button" onClick="deleteTb();">返回</button></span>
   		</@common.ToolbarBlank>	
</div> 
<div class="tt-le" style="color:#CC3300" id="t1">
	<strong>${bltest?default("")}</strong>
</div>
<#if showSections>
<div class="head-tt head-tt1-t">
	<div class="tt-le">
    	<input type="radio" name="selectNode" value="1" onclick="doType(1);" id="radio_1" <#if section == "1"> checked </#if>>小学</input>
		<input type="radio" name="selectNode" value="2" onclick="doType(2);" id="radio_2" <#if section == "2"> checked </#if>>初中</input>
		<input type="radio" name="selectNode" value="3" onclick="doType(3);" id="radio_3" <#if section == "3"> checked </#if>>高中</input>
    </div>
</div> 
</#if>
<@common.tableList id="InputAll">   
	<tr>
		<#if !showDefault>
		<th width="8%">选择</th>
		</#if>
		<th width="12%">审核步骤</th>
		<th>审核单位</th>
		<#if !showDefault>
		<th>操作</th>
		</#if>
	</tr>
	<#if auditFlowListNew?exists>
	<#list auditFlowListNew as x>
		<tr  id="tr_${x[0]}">
		<#if !showDefault>
			<#if x[2] != "0">	
				<td>
					<input type="checkbox" name="ck_name" id="checkbox_${x[0]}" value="${x[0]}" />
				</td>
				<#else>
					<td>
					</td>
				</#if>
		</#if>
					<td id="numbleTd_${x[0]}">
						${x[0]}
					</td>
					<td id="insertTd_${x[0]}">
						${x[4]?default("")}
					</td>
					<#if !showDefault>
					<td>
						<#if x[2] != "0">	
						<a href="javascript:insertTr('${x[0]?default("")}');">
						插入
						</a>
						<a href="javascript:deleteTr('${x[0]?default("")}');">
						删除
						</a>
						</#if>
						
					</td>
					</#if>
				</tr>
					</#list>
				</#if>
			</@common.tableList>
<@common.ToolbarBlank>
	<#if !showDefault>
	<span class="input-btn2" ><button type="button" onClick="deleteTrs();">删除</button></span>&nbsp;&nbsp;
	<span class="input-btn2" ><button type="button" onClick="add();">保存</button></span>&nbsp;&nbsp;
	</#if>
	<span class="input-btn2" ><button type="button" onClick="backtrack();">返回</button></span>
</@common.ToolbarBlank>	
</body>
<script>
jQuery(document).ready(function(){
	jQuery(".table-content").height(jQuery("#${outerframe}", window.parent.document).height() - jQuery('.head-tt').height()-jQuery('#t1').height()-jQuery('.table1-bt').height()-3);
	//jQuery(".table-content").height(300);
	jQuery("#table1").height(200-jQuery('.table1-bt').height());
	var msg="${result?default("")}";
	if(msg!=""){
		showMsgSuccess(msg);
	}
	
})
</script>
</html>

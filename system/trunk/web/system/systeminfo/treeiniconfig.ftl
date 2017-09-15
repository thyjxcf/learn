<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="平台基础信息设置">
<div id="treeiniconfigContainer">
	<form name="form1"  action="" method="post">
    	<table border="0" cellspacing="0" cellpadding="0" class="public-table table-list">
           <#list treeParams as x>
               <tr>
                  <input type="hidden" name="unitIniId" value="${x.id?default('')?trim}">
                  <input type="hidden" name="defaultValue" value="${x.defaultValue?default('')?trim}">
                  <input type="hidden" name="proname" value="${x.name?default('')?trim}">             
                  <th width="170">${x.name?default('')}：</th>
                  <td width="300"><input type="text" name="nowValue" value="${x.nowValue?default('')?trim}" msgName="${x.name?default('')?trim}" notNull="true" size="25" class="input-txt" style="width:300px;" regex="/^(0|1)$/" regexMsg="只能输入0或1"></td>
                  <td>${x.description?default('')?trim}</td>
                  <td width="100">
    	             <p ><a href="javascript:void(0);" class="abtn-blue mr-9" onclick="setDefaultValue(this);">还原默认值</a></p>
  	  	  		  </td>
               </tr>
           </#list>
               <tr>
                  <td colspan="4" class="t-center">
    	             <a href="javascript:void(0);" class="abtn-blue"  onclick="save();">保存</a>&nbsp;&nbsp;&nbsp;
				     <a href="javascript:void(0);" class="abtn-blue reset ml-5" onclick="formReset();">重置</a>
  	  	  		  </td>
               </tr>
        </table>
	</form>        
</div>

<script>
function formReset(){
  document.form1.reset();
}
  
function setDefaultValue(obj){
	var proname=$(obj).parents('tr').find("input[name='proname']").val();
	showMsgWarn("确定要将"+proname+"还原默认值？","",function(){
	     var defaultValue=$(obj).parents('tr').find("input[name='defaultValue']").val();
	     var nowValue=$(obj).parents('tr').find("input[name='nowValue']");
	     nowValue.val(defaultValue);
	});
}
  
function save() {
	if(!isActionable("#btnSaveAll")){
		return false;
	}
	if(!checkAllValidate("#treeiniconfigContainer")){
		return false;
	}
	$("#btnSaveAll").attr("class", "abtn-unable-big");
	
	var unitIniId="";
	var nowValue="";
	$("input[name='unitIniId']").each(function (){
	   unitIniId+=$(this).val()+",";
	});
	unitIniId=unitIniId.substring(0,unitIniId.length-1);
	$("input[name='nowValue']").each(function (){
	   nowValue+=$(this).val()+",";
	});
	nowValue=nowValue.substring(0,nowValue.length-1);
	$.getJSON("${request.contextPath}/system/admin/treeParamAdmin-save.action", {"unitIniId":unitIniId,"nowValue":nowValue}, function(data){
	if(data.jsonError!=null&&data.jsonError!=""){
		showMsgError(data.jsonError);
		$("#btnSaveAll").attr("class", "abtn-blue-big");
		return;
	}else{
	   showMsgSuccess("保存成功！", "提示", function(){
		    load("#treeiniconfigContainer", "${request.contextPath}/system/admin/treeParamAdmin.action");
		    });
			return;
	   }
	}).error(function(XMLHttpRequest, textStatus, errorThrown){
	   alert(XMLHttpRequest.status);
	});
}
</script>
</@htmlmacro.moduleDiv>
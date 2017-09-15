<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>

<@htmlmacro.moduleDiv titleName="">
<p class="tt"><a href="javascript:void(0);" class="close" onclick="closeDiv('#tempDivLayer')">关闭</a><span>套红模板</span></p>
<div class="wrap pa-10">
	<div class="wrap">
		<span class="b fn-left mt-5">模板类型:</span>
		<div class="ui-select-box  fn-left" id="secretLong_class" style="width:200px;">
	        <input type="text" class="ui-select-txt" id="templateName" value="" readonly />
	        <input type="hidden" value=""  class="ui-select-value" />
	        <a class="ui-select-close"></a>
	        <div class="ui-option" myfunchange="flowList">
	            <div class="a-wrap">
				    <a val="1"><span>系统模板</span></a>
					<a val="2"><span>单位模板</span></a>
					<a val="3"><span>部门模板</span></a>
					<a val="4"><span>个人模板</span></a>
	            </div>
	       </div>
	    </div>
	    <div class="fn-clear"></div>
	</div>    
    <div style="overflow-x:auto;height:420px" id="tempSelectList">
	
    </div>
    <p class="dd">
	   	模板名称：<input type="text" id="actualName" class="input-txt" style="width:200px;" readonly>
		   <input type="hidden" id="actualId">
	    <a class="abtn-blue" href="javascript:void(0);" onclick="importTemp();" id="btnSave">导入</a>
	    <a class="abtn-blue reset ml-5" href="javascript:void(0);" onclick="closeDiv('#tempDivLayer');">取消</a>
	</p>
</div>
<script>
	$(document).ready(function(){
		vselect();
		flowList(1);
	});
	function flowList(value){
		load("#tempSelectList","${request.contextPath}/office/tempcomment/tempComment-tempSelectList.action?type="+value);
	}
	
	function setActualInfo(id,title){
		
		$("#actualId").val(id);
		$("#actualName").val(title);
	}
	
	function importTemp(){
		if($("#actualId").val() == ''){
			showMsgWarn("请先选择模板");
			return;
		}
		$("#${tempObjectId!}").val($("#actualId").val());
		$("#${tempObjectName!}").html($("#actualName").val());
		closeDiv('#tempDivLayer');
	}
 	
  
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>
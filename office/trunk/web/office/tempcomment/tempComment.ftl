<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#include "/common/handlefielderror.ftl">
<@htmlmacro.moduleDiv titleName="套红模板列表">
<div class="query-builder-no pt-20">
	<div class="query-part fn-rel fn-clear promt-div">
	    <div class="pub-operation fn-clear">
	 		<span class="fn-left mt-5">&nbsp;&nbsp;&nbsp;&nbsp;模板类型：</span>
			<div class="ui-select-box  fn-left ml-5" id="supervise_class" style="width:150px;">
		        <input type="text" class="ui-select-txt" id="" value="" readonly msgName="模板类型" />
		        <input type="hidden" value="" id="type" name="type" class="ui-select-value" />
		        <a class="ui-select-close"></a>
		        <div class="ui-option owner_div" myfunchange="flowList">
		            <div class="a-wrap">
	        	        <a val="1" <#if type?exists&&type=='1'>class="selected"</#if>><span>系统模板</span></a>
						<a val="2" <#if type?exists&&type=='2'>class="selected"</#if>><span>单位模板</span></a>
						<a val="3" <#if type?exists&&type=='3'>class="selected"</#if>><span>部门模板</span></a>
						<a val="4" <#if type?exists&&type=='4'>class="selected"</#if>><span>个人模板</span></a>
		            </div>
		       </div>
		    </div>
		    <div class="fn-right ml-10">
		    	<a href="javascript:doEdit('${type!}')" class="abtn-orange-new fn-right" id="addNew">新建模板</a>
		    </div>
	    </div>
	    <div class="fn-clear"></div>
	</div>
</div>
<div>
	<table border="0" cellspacing="0" cellpadding="0" class="public-table table-list table-list-edit mt-5">
        <tr>
        	<th width="80%">模板名称</th>
        	<th width="20%" class="t-center">操作</th>
        </tr>
	       <#if templateList?exists&&(templateList?size>0)>
		        <#list templateList as item>
			        <tr>
			        	<td>${item.title!}</td>
			        	<td class="t-center">
			    			<a href="javascript:void(0)" class="setting_button " onclick="doEdit('${type!}','${item.id!}')">编辑</a>
			    			<a href="javascript:void(0)"  class="setting_button " onclick="doRemove('${item.id!}')">删除</a>
			        	</td>
			        </tr>
		        </#list>
	        <#else>
	        <tr>
		   		<td colspan="10"><p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
			 </tr>
	        </#if>
    </table>
</div>
<script>
	$(document).ready(function(){
		vselect();
	});
	function flowList(value){
		load("#container","${request.contextPath}/office/tempcomment/tempComment.action?type="+value);
	}
	
	function doEdit(type,id){
		load("#container","${request.contextPath}/office/tempcomment/tempComment-edit.action?type="+type+"&id="+id);
	}
	function doRemove(id){
	if(!showConfirm("确定要删除这条记录?" )){
		return;
	}
	$.getJSON("${request.contextPath}/office/tempcomment/tempComment-remove.action", 
		{"id":id}, function(data){
			if(data){
				var suc = data.operateSuccess;
				if(suc){
					showMsgSuccess(data.promptMessage,"",flowList('${type!}'));
				} else {
					showMsgError(data.errorMessage);
				}
			} else {
				showMsgError("删除失败！");
			}
		}).error(function(){
			showMsgError("删除失败！");
		});
	
	}
</script>
</@htmlmacro.moduleDiv>
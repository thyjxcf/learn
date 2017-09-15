<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<form>
<div class="pub-table-wrap">
	<div class="pub-table-inner">
	<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">
	<div class="query-part fn-rel fn-clear">
		<div class="query-tt ml-10">类别：</div>
	    <div class="fn-left">
	    <#assign firstId = "">
	    <@common.select style="width:100px;" valName="thisId" valId="thisId" myfunchange="search">
			<a val="">请选择</a>
			<#list mcodelist as m>
				<a val="${m.thisId}" <#if firstId == "">class="selected"</#if>>${m.content!}</a>
				<#if firstId == "">
					<#assign firstId = m.thisId>
				</#if>
			</#list>
		</@common.select>
		</div>
		<a href="javascript:void(0);" class="abtn-orange-new fn-right" onclick="edit('');">新建二级类别</a>
		<div class="fn-clear"></div>
	</div>
	</div>
	</div>
</div>
<div id="typeManageListDiv"></div>
<script>
$(function(){
	<#if firstId != "">
	load("#typeManageListDiv", "${request.contextPath}/office/repaire/repaire-typeManageList.action?thisId=${firstId!}");
	<#else>
	load('#typeManageListDiv','${request.contextPath}/common/tipMsg.action?msg=请选择一个类别！');
	</#if>
});
function search(){
	var thisId = $("#thisId").val();
	if(thisId!=''){
		load("#typeManageListDiv", "${request.contextPath}/office/repaire/repaire-typeManageList.action?thisId="+thisId);
	}else{
		load('#typeManageListDiv','${request.contextPath}/common/tipMsg.action?msg=请选择一个类别！');
	}
}
function edit(id){
   	var url="${request.contextPath}/office/repaire/repaire-typeManageEdit.action?id="+id;
   	openDiv("#classLayer3", "",url, false,"","300px");
}
</script>
</@common.moduleDiv>
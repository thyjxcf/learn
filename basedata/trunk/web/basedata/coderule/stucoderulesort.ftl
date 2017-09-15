<#import "/common/htmlcomponent.ftl" as htmlmacro> 
<SCRIPT src="${request.contextPath}/static/js/jquery.tablednd_0_5.js" type=text/javascript></SCRIPT>
<@htmlmacro.moduleDiv titleName="学号规则新增"> 
<input type="hidden" name="initCodeRule" value="${initCodeRule?string}">
<p class="tt"><a href="javascript:void(0);" class="close">关闭</a><span>调整位置</span></p>
<div class="wrap  pa-10">
	<table id="dataTable" width="100%" border="0" cellspacing="0" cellpadding="0" class="public-table table-list table-dragSort">
    <thead>
        <tr>
            <th width="50" class="t-center">拖动栏</th>
            <th>原始号码位置</th>
            <th>类　　型</th>
            <th>取值位（流水号长度）</th>
            <th>类型长度</th>
            <th>固定值</th>
            <th class="t-center">备注</th>
        </tr>
    </thead>
	    <tbody>
	    <#if stuCodeRuleList?exists>
		  <#list stuCodeRuleList as item>
	      <tr name="${item.ruleposition?default("")}" id="${item.id?default("")}" <#if item.rulePosition?exists && item.rulePosition==99>class="nodrop nodrag" </#if> >
	        	<#if item.rulePosition?exists && item.rulePosition==99>
	        		 <td class="t-center drag-sort-td"><span class=""></span></td>
	        	<#else>
			        <td class="t-center drag-sort-td"><span class="drag-sort">拖动排序</span></td>
				</#if>
	        <td><#if item.rulePosition?exists && item.rulePosition==99>末尾后缀<#else>${item.rulePosition?default("")}</#if>
	        	<input type="hidden" value="${item.id?default("")}" name="ruleDetailId"/>
	        	<input type="hidden" value="${item.ruleId?default("")}" name="ruleId"/>
	        </td>
	        <td>${item.dataType?default("")}</td>
	        <td><#if (item.ruleNumber?exists && item.ruleNumber &gt; 0) || !item_has_next>${item.ruleNumber?default("")}</#if></td>
	        <td>${item.length?default("")}</td>
	        <td>${item.constant?default("")}</td>
	        <td>${item.remark?default("")}</td>
	      </tr>
		  </#list>
		</#if>
		</tbody>
	</table>
</DIV>
<div class="explain-text">
	<p class="b">号码排序操作说明：</p>
	<p>1、鼠标移置“拖动栏”处变为十字形;<br />
	<p>2、按住左键不放，拖动该行至目标行处;<br />
	<p>3、放开鼠标按左键，该行跟目标行的位置即可交换;
</div>
<p class="dd">
     <a class="abtn-blue submit1" href="javascript:void(0);" onClick="doSave();">保存</a>
     <a class="abtn-gray reset ml-5" href="javascript:void(0);" onClick="">取消</a>
</p>
<script>
$(".table-dragSort").tableDnD({ 
  onDrop:function(table,row){
		var rows = table.tBodies[0].rows;
		for(i=0;i<rows.length;i++){
			if(i%2==0){
				$(".public-table tbody tr:even").removeClass(); 
			}
			else{
					$(".public-table tbody tr:odd").removeClass(); 
					$(".public-table tbody tr:odd").addClass("odd"); 
			}
		}
	}
});

function doSave(){
	//alert($.param($("input[name='ruleDetailId']"),true));
	var ruleId = $("input[name='ruleId']").get(0).value;
	$.ajax({
		type: "POST",
		url: "${request.contextPath}/basedata/coderule/codeRuleSetAdmin-stuCodeRuleSaveSort.action?ruleId="+ruleId,
		data: $.param($("input[name='ruleDetailId']"),true),
		success: function(data){
			if(data.jsonError != null && data.jsonError != ""){
					showMsgError(data.jsonError);
					//按钮变回样式
					return;
				}else{
					showMsgSuccess("保存成功",null,doReturn);
				}
		},
		dataType: "json",
		error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
	});
	
}
function doReturn(){
	load("#dataDiv", "${request.contextPath}/basedata/coderule/codeRuleSetAdmin-stuCodeRule.action?codeType=${codeType!}&schid=${schid!}&initCodeRule=${initCodeRule?string}");
}
</script>
</@htmlmacro.moduleDiv>
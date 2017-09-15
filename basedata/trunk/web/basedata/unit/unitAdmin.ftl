<#assign ec=JspTaglibs["/WEB-INF/tld/extremecomponents.tld"]>
<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="单位管理列表">
<script>
function treeItemClick(id,name){
	var str = "unitId="+id+"&name="+name;
	load("#telBook","${request.contextPath}/basedata/unit/unitAdmin-list.action?"+str);
}

$(document).ready(function(){
	loadZtreeDiv();
	load("#telBook","${request.contextPath}/basedata/unit/unitAdmin-list.action");
});
function loadZtreeDiv(){
	load("#ztreeDiv","${request.contextPath}/common/xtree/unitztree.action?useCheckbox=false"+"&unitId=${unitId?default('')}");
}
</script>
<div class="pub-table-wrap">
    <div class="pub-table-inner">
        <div class="user-group fn-left">
            <div class="page-list page-list-scroll">
            	<div id="ztreeDiv" class="ztree">
                </div>
            </div>
        </div>
        
        <div class="tel-book" id="telBook" style="width:81.5%;"></div>
    </div>
</div>
<div class="fn-clear"></div>
</@htmlmacro.moduleDiv>
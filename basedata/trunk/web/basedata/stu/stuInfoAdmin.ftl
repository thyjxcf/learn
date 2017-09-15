<#assign ec=JspTaglibs["/WEB-INF/tld/extremecomponents.tld"]>
<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="学生信息维护">
<script>
function treeItemClick(id,name){
	var str = "unitId="+id+"&name="+name;
	load("#telBook","${request.contextPath}/basedata/stu/studentadmin-list.action?"+str);
}

$(document).ready(function(){
	loadZtreeDiv();
	load("#telBook","${request.contextPath}/basedata/stu/studentadmin-list.action");
});
function loadZtreeDiv(){
	load("#ztreeDiv","${request.contextPath}/common/xtree/unitztree.action?useCheckbox=false"+"&unitId=${unitId?default('')}");
}
</script>
<div class="pub-table-wrap">
    <div class="pub-table-inner">
    	<#if isEdu>
        <div class="user-group fn-left">
            <div class="page-list page-list-scroll">
            	<div id="ztreeDiv" class="ztree">
                </div>
            </div>
        </div>
        </#if>
        <div <#if isEdu>class="tel-book"</#if> id="telBook"></div>
    </div>
</div>
<div class="fn-clear"></div>
</@htmlmacro.moduleDiv>
<#import "/common/commonmacro.ftl" as common>
<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="${webAppTitle}--班级设置">
<#assign SXRRT=stack.findValue("@net.zdsoft.eis.base.constant.BaseConstant@SYS_DEPLOY_SCHOOL_SXRRT")>
<script language="javascript">
//新增班级
function doAdd(){
	load("#container","${request.contextPath}/basedata/sch/basicClassAdmin-Add.action?gradeid=${gradeid?default('')}");
}
//批量新增
function doBatchAdd(){
	load("#container","${request.contextPath}/basedata/sch/basicClassAdmin-BatchAdd.action?gradeid=${gradeid?default('')}");
}
//编辑
function doEdit(classid){
    var gradeid = $("#gradeIdHidden").val();
 	load("#container","${request.contextPath}/basedata/sch/basicClassAdmin-Edit.action?id="+classid+"&gradeid="+gradeid);
}
//删除
function doDelete(){
	if(!checkSelectCheckbox(mainform,'checkid')) return;
	if(!confirm("确定要删除选中的班级？")) return;
	var options = {
		url : "${request.contextPath}/basedata/sch/basicClassAdmin-Delete.action",
		async:false,//同步操作
		dataType : 'json',
		clearForm : false,
		resetForm : false,
		type : 'post',
		success : function(data){
			if(data.operateSuccess){
				showMsgSuccess(data.promptMessage,"提示",function(){
					load("#container","${request.contextPath}/basedata/sch/basicClassAdmin.action");
				});
			}else{
				showMsgError(data.promptMessage);
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}//请求出错 
	};
	$("#mainform").ajaxSubmit(options);
}
//年级设置
function gradeAdmin(){
	load("#container","${request.contextPath}/basedata/sch/basicClassAdmin-GradeList.action");
}

function doGradeClicked(id){
	var gradeidObj = document.getElementById(id);
	gradeidObj.checked = true;
	load("#container","${request.contextPath}/basedata/sch/basicClassAdmin.action?gradeid="+id);
}
//重置
function doReset(){
	var length = mainform.elements.length;
	for(i=0;i<length;i++){
		if((mainform.elements[i].name =="gradeid") && (mainform.elements[i].checked == true)){
	   		mainform.elements[i].checked = false;
	   		break;
		}
	}
	load("#container","${request.contextPath}/basedata/sch/basicClassAdmin.action?showAllClass='showAllClass'");
}
</script>

<form action="" method="post" name="mainform" id="mainform">
<p class="pub-operation">
    <div class="query-tt fn-right">
    <a href="javascript:gradeAdmin();" class="abtn-blue">年级设置</a>
    <a href="javascript:doReset();" class="abtn-blue">显示全部</a>
    <#if gradeList?exists&&(gradeList?size>0)>
    <a href="javascript:doAdd();" class="abtn-orange-new">新增班级</a>
    <a href="javascript:doBatchAdd();" class="abtn-orange-new">批量新增</a>
    </#if>
	</div>
	<div class="fn-clear"></div>
</p>
<div class="table-all">
<div class="table-content table-content-grade" style="height:200px;width:100%;overflow:auto;;overflow-x:hidden;overflow-y:scroll;margin-top:15px;">
<@htmlmacro.tableList id="tablelist" class="public-table table-list">
	<tr>
	<th width="5%">&nbsp;</th>
	<th width="20%">入学学年</th>
	<th width="20%">学段</th>
	<th width="20%">年级</th>
	<th width="20%">年级组长</th>
	<th width="15%">学制（年）</th>
	</tr>
	<#if gradeList?exists&&(gradeList?size>0)>
	<#list gradeList as item>			  	
		<#assign gradeCnt = item.section?default("")+"|"+item.acadyear?default("")+"|"+item.schoolinglen?default("") />
		<tr onclick="doGradeClicked('${item.id!}');" >
		<td class="t-center">
		<span class=' ui-radio <#if gradeid?default("") == item.id && showAllClass?default('')==""> ui-radio-current</#if>' data-name="a">
		<input name="gradeid" id="${item.id!}" type="radio" class="radio" value="${gradeCnt?default("")}" 
		<#if gradeid?default("") == item.id && showAllClass?default('')=="">checked="checked"</#if> /></span>
		</td>
		<td>${item.acadyear?default("")}</td>
		<td>${sectionMap[(item.section?string)?default("")]?default("")}</td>
		<td>${item.gradename?default("")}</td>
		<td <#if gradeid?default("") == item.id>bgcolor="#c2cdf7"</#if>>
		<@common.getValue list=teacheridList?if_exists key=item.teacherId?default("")/>
		</td>
		<td><!--选择年级时 ，进行定位位置-->
		<div style="width:50px;">${item.schoolinglen?default("")}<input id="_${item.id!}" type="radio" name="dd" style="position:relative;z-index:-100;"/></div></td>
		</tr>
	</#list>
	<#else>
	  	<tr>
           <td colspan="88"> <p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td>
    	</tr>
	</#if>
</@htmlmacro.tableList>
</div>
<@htmlmacro.tableList id="tablelist2" class="public-table table-list mt-10">
  	 <tr>
    	<th width="5%">选择</th>
    	<#if systemDeploySchool?exists && systemDeploySchool == SXRRT>
    	<#else>
        <th width="9%">班级代码</th>
        </#if>
        <th width="9%">班级名称</th>
        <#if systemDeploySchool?exists && systemDeploySchool == SXRRT>
		<#else>
        <th width="8%">班级类型</th>
        </#if>
        <th width="8%">入学学年</th>
        <#if systemDeploySchool?exists && systemDeploySchool == SXRRT>
		<#else>
        <th width="8%">建班年月</th>
        <th width="8%">文理类型</th>
        </#if>
        <th width="8%">班长</th>
        <th width="8%">班主任</th>
        <th width="8%">副班主任</th>
        <th width="9%">学制（年）</th>
        <th width="7%">学生数</th>
        <th width="5%">操作</th>
    	<!--td>是否毕业</td-->
     </tr>
      <#if classList?exists&&(classList?size>0)>
      <#list classList as item>
      <tr>
		<td>
 			<p><span class="ui-checkbox"><input type="checkbox" name="checkid" value="${item.id?default("")}" class="chk"></span></p>
		</td>
		<#if systemDeploySchool?exists && systemDeploySchool == SXRRT>
		<#else>
        <td>${item.classcode?default("")}</td>
        </#if>
        <td>${item.classnamedynamic?default("")}</td>
        <#if systemDeploySchool?exists && systemDeploySchool == SXRRT>
		<#else>
        <td>${classtypeMap[item.classtype?default("")]?default("")}</td>
        </#if>
        <td>${item.acadyear?default("")}</td>
        <#if systemDeploySchool?exists && systemDeploySchool == SXRRT>
		<#else>
        <td><#if item.datecreated?exists>${item.datecreated?string("yyyy-MM-dd")}</#if></td>
        <td>${artsciencetypeMap[item.artsciencetype?string?default("")]?default("")}</td>
        </#if>
        <td>
		<#if item.monitor?exists>
		${(item.monitor)?default('')}
		</#if>
		</td>
        <td><@common.getValue list=teacheridList?if_exists key=item.teacherid?default("")/></td>
        <td><@common.getValue list=teacheridList?if_exists key=item.viceTeacherId?default("")/></td>
        <td>${item.schoolinglen?default("")}</td>
        <td><#if item.stucount?exists && item.stucount==0><font color="#FF0000">0</font><#else>${item.stucount?default("")}</#if></td>
		<td class="t-center">
		<a href="javascript:doEdit('${item.id?default("")}');"><img src="${request.contextPath}/static/images/icon/edit.png" alt="编辑"></a></td>
        <#--td><#if item.graduatesign?exists && item.graduatesign==1 >已毕业<#else>未毕业</#if></td-->
      </tr>
      </#list>
      </#if>
</@htmlmacro.tableList>
</div>
<#if classList?exists && (classList?size>0)>
<@htmlmacro.ToolbarBlank>
	<span class="ui-checkbox ui-checkbox-all" data-all="no"><input type="checkbox" class="chk">全选</span>
    <a class="abtn-blue" href="javascript:doDelete();">删除</a>
</@htmlmacro.ToolbarBlank>
</#if>
		<!--
	  	<table width="99%"  border="0" cellpadding="0" cellspacing="0">
        	<tr><td width="30"><input type="checkbox" name="delallflag" value="checkbox" 
          		onClick="selectAllCheckbox(this.form,this,'checkid');"></td>
          		<td width="50">全选</td>
          		<td><label>
            		<input type="button" name="delete" value="删除"class="del_button1" onMouseover = "this.className = 'del_button3';" 
            		onMousedown= "this.className = 'del_button2';" onMouseout = "this.className = 'del_button1';" 
            		onClick="doDelete();"/>
          			</label></td>
          <td align="right">
		  </td>
        </tr>
      </table>
      -->
<input name="schid" type="hidden" value="${schid?if_exists}"/>
<input name="gradeIdHidden" id="gradeIdHidden" type="hidden" value="${gradeid?default('')}"/>
</form>
<script>
$(document).ready(function(){
	$("#_${gradeid!}").focus();
})
</script>
</@htmlmacro.moduleDiv>
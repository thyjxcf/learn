<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="${webAppTitle}--<#if id?exists && id?default('')!=''>修改学年学期<#else>新增学年学期</#if>">
<script language="javascript">
var isSubmitting = false;
var showMsg = '';
//表单保存或取消
function formSubmit(){
	if(isSubmitting){
		if(trim(showMsg)){
			showMsgError(showMsg);
		}
		return;
	}
	if(!checkAllValidate()){
		return;
	}
	//数据类型和格式判断
	if(!checkTermyear2(editform.acadyear)) return;
	//数字范围判断（会先做为空判断）
	if(!checkNumRange2(editform.edudays,"周天数",1,7)) return ;
	if(!checkNumRange2(editform.amperiods,"上午课节数",0,5)) return ;
	if(!checkNumRange2(editform.pmperiods,"下午课节数",0,5)) return ;
	if(!checkNumRange2(editform.nightperiods,"晚上课节数",0,5)) return ;
	if(!checkNumRange2(editform.classhour,"课长",0,180)) return ;
	
	//日期逻辑判断
	var termyear = trim(editform.acadyear.value);
	var beginYear=termyear.substring(0,4);
	var endYear=termyear.substring(5,9);
	if(beginYear>trim(editform.workbegin.value).substring(0,4)){
		addFieldError(editform.workbegin,"工作开始日期要大于学年学期起始年份");
		return;
	}
	if(trim(editform.workend.value).substring(0,4)>endYear){
		addFieldError(editform.workend,"工作结束日期要小于学年学期结束年份");
		return;
	}
	
	if(!(checkAfterDateWithMsg(editform.workbegin,editform.workend,"工作结束日期要大于工作开始日期，请更正！"))) return;
	if(!(checkAfterDateWithMsg(editform.semesterbegin,editform.semesterend,"学期结束日期要大于学期开始日期，请更正！"))) return;
	if(!(checkAfterDateWithMsg(editform.workbegin,editform.semesterbegin,"学期开始日期要大于工作开始日期，请更正！"))) return;
	if(!(checkAfterDateWithMsg(editform.semesterend,editform.workend,"工作结束日期要大于学期结束日期，请更正！"))) return;
	if(!(checkAfterDateWithMsg(editform.semesterbegin,editform.registerdate,"学期开始日期必须小于注册日期，请更正！"))) return;
	if(!(checkAfterDateWithMsg(editform.registerdate,editform.semesterend,"学期结束日期要大于注册日期，请更正！"))) return;	
	isSubmitting = true;
	jQuery.ajax({
		url:"${request.contextPath}/basedata/sch/basicSemesterAdmin-save.action",
		type:"POST",
		dataType:"JSON",
		data:jQuery("#editform").serialize(),
		async:false,
		success:function(data){
			if(data.operateSuccess){
   				showMsgSuccess(data.promptMessage,"",cancel());
   				isSubmitting = false;
   			}else{
   				showMsgError(data.promptMessage);
   			}
			isSubmitting = false;
		}
	});
}
function cancel(){
	load("#container","${request.contextPath}/basedata/sch/basicSemesterAdmin.action");
}

function checkTermyear2(elem){
	var termyear = trim(elem.value);
	if(termyear.length!=9){
		showMsgError("学年必须为9位数据");
		elem.select();
		return false;
	}
	if(termyear.charAt(4)!='-'){
		showMsgError("格式错误,正确格式示例：2003-2004");
		elem.select();
		 return false;
	}
	if(parseInt(termyear.substring(0,4))!=(parseInt(termyear.substring(5,9))-1)){
		showMsgError("学年信息错误！");
		elem.select();
		return false;
 	}
	return true;
}

//选择学期名称
function doChange(){
	//load("#container","${request.contextPath}/basedata/sch/basicSemesterAdmin-add.action");
	showMsg = "";
	isSubmitting = false;
	var acadyear = $("#acadyear").val();
	var semester = $("#semester").val();
	if(trim(acadyear)==''){
		return ;
	}
	if(trim(semester)==''){
		return ;
	}
	jQuery.ajax({
		url:"${request.contextPath}/basedata/sch/basicSemesterAdmin-valiAcadyearOrSem.action",
		type:"POST",
		dataType:"JSON",
		data:$.param({acadyear:acadyear,semester:semester},true),
		async:false,
		success:function(data){
			if(data.operateSuccess){
				load("#container", "${request.contextPath}/basedata/sch/basicSemesterAdmin-add.action?schoolSemester.acadyear="+acadyear+"&schoolSemester.semester="+semester);
   			}else{
   				showMsg = data.errorMessage;
   				showMsgError(showMsg);
   				isSubmitting = true;
   			}
		}
	});
}

</script>
<form action="" method="post" name="editform" id="editform">
<div class="table-content">
<p class="table-dt"><#if schoolSemester.id?exists && schoolSemester.id?default("")!="">编辑[${schoolSemester.acadyear?if_exists}学年${semesterName?if_exists}]<#else>新增学年学期</#if>信息</p>
<table width="100%" cellspacing="0" cellpadding="0" border="0" class="table-form">
	<#--tr--><#--200707与jxhl整合，去掉周天数、课长等默认信息 调整界面 -->
	<#-- 编辑学年学期 -->
	<#if schoolSemester.id?exists && schoolSemester.id?default("")!="">
	<tr>
		<th width="20%"><span class="c-orange">*</span>学年名称：</th>
        <td width="30%">
        	${schoolSemester.acadyear?if_exists}
        </td>
        <th width="20%"><span class="c-orange">*</span>学期名称：</td>
        <td width="30%">
        	${semesterName?if_exists}
        	<#--隐藏着的学年学期-->
		  	<input name="acadyear" type="hidden" value="${schoolSemester.acadyear?if_exists}"/>
		  	<input name="semester" type="hidden" value="${schoolSemester.semester?if_exists}"/>
	  	</td>
	</tr>
	<#else>
	<#-- 新增学年学期 -->
	<tr>
		<th width="20%"><span class="c-orange">*</span>学年名称：</th>
        <td width="30%">
			<@common.select style="width:162px;" msgName="学年名称" notNull="true" txtId="acadyearId" valName="acadyear" valId="acadyear" myfunchange="doChange();">
				<a val=""><span>请选择</span></a>
				<#list yearsList as item>
				<a val="${item!}" <#if item==acadyear?default('')>class="selected"</#if> ><span>${item!}</span></a>
				</#list>
			</@common.select>
        </td>
        <th width="20%"><span class="c-orange">*</span>学期名称：</td>
        <td width="30%">
        	<@common.select style="width:162px;" msgName="学期名称" notNull="true" txtId="semesterId" valName="semester" valId="semester" myfunchange="doChange();">
				<a val=""><span>请选择</span></a>
				${appsetting.getMcode("DM-XQXN").getHtmlTag(schoolSemester.semester?default(''),false)}
			</@common.select>
        </td>
    </tr>
	</#if>
	<tr>
        <th><span class="c-orange">*</span>工作开始日期：</th>
        <td>
	        <@common.datepicker msgName="工作开始日期" class="input-txt input-readonly input-readonly" style="width:150px" notNull="true" name="workbegin" id="workbegin" value="${(schoolSemester.workbegin?string('yyyy-MM-dd'))?if_exists}"/>			
        <th><span class="c-orange">*</span>周天数：</th>
        <td>
        	<input msgName="周天数" name="edudays" id="edudays" type="text" value="${schoolSemester.edudays?if_exists}" dataType="integer" maxlength="1" notNull="true" class="input-txt fn-left" style="width:150px" size="20" fieldtip="请输入1~7的整数"/>
        </td>
    </tr>
    <tr>
        <th><span class="c-orange">*</span>学期开始日期：</th>
        <td>
        	<@common.datepicker msgName="学期开始日期" class="input-txt input-readonly fn-left" style="width:150px" notNull="true" name="semesterbegin" id="semesterbegin" value="${(schoolSemester.semesterbegin?string('yyyy-MM-dd'))?if_exists}"/>		
        </td>
        <th><span class="c-orange">*</span>课长：</th>
        <td>
        	<input msgName="课长" name="classhour" id="classhour" type="text" dataType="integer" maxlength="3" value="${schoolSemester.classhour!}" notNull="true" class="input-txt fn-left" style="width:150px" size="20" title="请输入1-180的整数"/>
        </td>
    </tr>
    <tr>
      	<th><span class="c-orange">*</span>注册日期：</th>
        <td>
        	<@common.datepicker msgName="注册日期" class="input-txt input-readonly fn-left" style="width:150px" notNull="true" name="registerdate" id="registerdate" value="${(schoolSemester.registerdate?string('yyyy-MM-dd'))?if_exists}"/>
        </td>
        <th><span class="c-orange">*</span>上午课节数：</th>
        <td>
	 		<input msgName="上午课节数" name="amperiods" id="amperiods" type="text" dataType="integer" maxlength="1" notNull="true" value="${schoolSemester.amperiods?if_exists}" class="input-txt fn-left" style="width:150px" size="20" title="请输入0-5的整数"/>
        </td>
	  </tr>  
      <tr>
	  	<th><span class="c-orange">*</span>学期结束日期：</th>
        <td>
        	<@common.datepicker msgName="学期结束日期" class="input-txt input-readonly fn-left"  style="width:150px" notNull="true" name="semesterend" id="semesterend" value="${(schoolSemester.semesterend?string('yyyy-MM-dd'))?if_exists}"/>
		</td>
	  	<th><span class="c-orange">*</span>下午课节数：</th>
        <td>
        	<input msgName="下午课节数" name="pmperiods" id="pmperiods" type="text" dataType="integer" maxlength="1" value="${schoolSemester.pmperiods?if_exists}" notNull="true" class="input-txt fn-left" style="width:150px" size="20" title="请输入0~5的整数"/>
        </td>
	  </tr>
	  <tr>
        <th><span class="c-orange">*</span>工作结束日期：</th>
        <td>
        	<@common.datepicker msgName="工作结束日期" class="input-txt input-readonly fn-left" style="width:150px" notNull="true" name="workend" id="workend" value="${(schoolSemester.workend?string('yyyy-MM-dd'))?if_exists}"/>
        </td>
        <th><span class="c-orange">*</span>默认晚上课节数：</th>
        <td>
        	<input msgName="晚上课节数" name="nightperiods" id="nightperiods" type="text" dataType="integer" maxlength="1" value="${schoolSemester.nightperiods?if_exists}" notNull="true" class="input-txt fn-left" style="width:150px" size="20" title="请输入0-5的整数"/>
        </td>
	  </tr>
	  <tr>
    	<td colspan="5" class="td-opt">
        	<a href="javascript:formSubmit(this.form,'save');" class="abtn-blue-big">保存</a>
            <a href="javascript:cancel();" class="abtn-blue-big">返回</a>
        </td>
      </tr>
</table>
</div>  
<input name="id" type="hidden" value="${schoolSemester.id?if_exists}"/>
<input name="schid" type="hidden" value="${schoolSemester.schid?if_exists}"/>
<input name="ec_p" type="hidden" value="${ec_p?if_exists}"/>
<input name="ec_crd" type="hidden" value="${ec_crd?if_exists}"/>
</form>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>

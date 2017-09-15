<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="${webAppTitle}--<#if id?exists && id?default('')!=''>修改学年学期<#else>新增学年学期</#if>">
<script language="javascript">
vselect();
function formSubmit(){
	if(!checkAllValidate()){
		return;
	}
	//数据类型和格式判断
	if(!checkTermyear2(editform.acadyear)) return;
	//数字范围判断（会先做为空判断）
	if(!checkNumRange2(editform.eduDays,"周天数",1,7)) return ;
	if(!checkNumRange2(editform.amPeriods,"上午课节数",0,5)) return ;
	if(!checkNumRange2(editform.pmPeriods,"下午课节数",0,5)) return ;
	if(!checkNumRange2(editform.nightPeriods,"晚上课节数",0,5)) return ;
	if(!checkNumRange2(editform.classhour,"课长",0,180)) return ;
	
	//判断日期格式
	if(!checkDate(editform.workBegin,"工作开始日期")) return;
	if(!checkDate(editform.workEnd,"工作结束日期")) return;
	if(!checkDate(editform.semesterBegin,"学期开始日期")) return;
	if(!checkDate(editform.semesterEnd,"学期结束日期")) return;
	if(!checkDate(editform.registerdate,"注册日期")) return;
	
	//日期逻辑判断
	var termyear = trim(editform.acadyear.value);
	var beginYear=termyear.substring(0,4);
	var endYear=termyear.substring(5,9);
	if(beginYear>trim(editform.workBegin.value).substring(0,4)){
		addFieldError(editform.workBegin,"工作开始日期要大于学年学期起始年份");
		return;
	}
	if(trim(editform.workEnd.value).substring(0,4)>endYear){
		addFieldError(editform.workEnd,"工作结束日期要小于学年学期结束年份");
		return;
	}
	if(!(checkAfterDateWithMsg(editform.workBegin,editform.workEnd,"工作结束日期要大于工作开始日期，请更正！"))) return;
	if(!(checkAfterDateWithMsg(editform.semesterBegin,editform.semesterEnd,"学期结束日期要大于学期开始日期，请更正！"))) return;
	if(!(checkAfterDateWithMsg(editform.workBegin,editform.semesterBegin,"学期开始日期要大于工作开始日期，请更正！"))) return;
	if(!(checkAfterDateWithMsg(editform.semesterEnd,editform.workEnd,"工作结束日期要大于学期结束日期，请更正！"))) return;
	if(!(checkAfterDateWithMsg(editform.semesterBegin,editform.registerdate,"学期开始日期必须小于注册日期，请更正！"))) return;
	if(!(checkAfterDateWithMsg(editform.registerdate,editform.semesterEnd,"学期结束日期要大于注册日期，请更正！"))) return;	
	
	jQuery.ajax({
		url:"${request.contextPath}/basedata/edu/semesterAdmin-save.action",
		type:"POST",
		dataType:"JSON",
		data:jQuery("#editform").serialize(),
		async:false,
		success:function(data){
			if(data.operateSuccess){
   				showMsgSuccess(data.promptMessage,"",cancel());
   			}else{
   				showMsgError(data.errorMessage);
   			}
		}
	});
}

function cancel(){
	load("#container","${request.contextPath}/basedata/edu/semesterAdmin.action");
}

function formAction(editform,operation){
	if(operation == 'save'){
		strAct = "semesterAdmin-save.action";
	}
	if(operation == 'cancel'){
		strAct = "semesterAdmin.action";
	}
	editform.action = strAct;
  	formSubmit(editform,operation);
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
</script>
<form action="" method="post" name="editform" id="editform">
<div class="table-content">
<p class="table-dt"><#if id?exists && id?default("")!="">编辑[${acadyear?if_exists}学年${semesterName?if_exists}]<#else>新增学年学期</#if>信息</p>
<table width="100%" cellspacing="0" cellpadding="0" border="0" class="table-form">
	  <tr>
	  	<th width="20%"><span class="c-orange">*</span>学年名称：</th>
        <td width="30%">
        <#if unitEduTop>
        	<input msgName="学年名称" name="acadyear" id="acadyear" type="text" notNull="true" value="${acadyear?if_exists}" class="input-txt fn-left" style="width:150px" size="20" maxlength="9" fieldtip="学年格式为:YYYY-YYYY，如:2006-2007"/>
        <#else> 
        	${acadyear?default("")}
        </#if>
        </td>
        <th width="20%"><span class="c-orange">*</span>学期名称：</th>
        <td width="30%">
        <#if unitEduTop>
        	<@common.select style="width:162px;" msgName="学期名称" notNull="true" txtId="semesterId" valName="semester" valId="semester">
				<a val=""><span>请选择</span></a>
				${appsetting.getMcode("DM-XQXN").getHtmlTag(semester?default(''),false)}
			</@common.select>
	 	<#else> 
		 	${appsetting.getMcode("DM-XQXN")[semester?default('')]?default('')}
	 	</#if>
        </td>
	  </tr>
      <tr>
        <th><span class="c-orange">*</span>工作开始日期：</th>
        <td>
        <#if unitEduTop>
	        <@common.datepicker msgName="工作开始日期" class="input-txt input-readonly input-readonly" style="width:150px" notNull="true" name="workBegin" id="workBegin" value="${(workBegin?string('yyyy-MM-dd'))?if_exists}"/>			
        <#else> 
        	<#if workBegin?exists>${workBegin?string("yyyy-MM-dd")}</#if>
        </#if>
        <th><span class="c-orange">*</span>周天数：</th>
        <td>
        <#if unitEduTop>
        	<input msgName="周天数" name="eduDays" id="eduDays" type="text" value="${eduDays!}" dataType="integer" maxlength="1" notNull="true" class="input-txt fn-left" style="width:150px" size="20" fieldtip="请输入1~7的整数"/>
        <#else> 
        	${eduDays!}
        </#if>
        </td>
      </tr>
      <tr>
        <th><span class="c-orange">*</span>学期开始日期：</th>
        <td>
        <#if unitEduTop>
        	<@common.datepicker msgName="学期开始日期" class="input-txt input-readonly fn-left" style="width:150px" notNull="true" name="semesterBegin" id="semesterBegin" value="${(semesterBegin?string('yyyy-MM-dd'))?if_exists}"/>		
        <#else> 
        	<#if semesterBegin?exists>${semesterBegin?string("yyyy-MM-dd")}</#if>
        </#if>
        </td>
        <th><span class="c-orange">*</span>课长：</th>
        <td>
        <#if unitEduTop>
        	<input msgName="课长" name="classhour" id="classhour" type="text" dataType="integer" maxlength="3" value="${classhour!}" notNull="true" class="input-txt fn-left" style="width:150px" size="20" title="请输入1-180的整数"/>
        <#else> 
        	${classhour!}
        </#if>
        </td>
      </tr>
      <tr>
      	<th><span class="c-orange">*</span>注册日期：</th>
        <td>
        <#if unitEduTop>
        	<@common.datepicker msgName="注册日期" class="input-txt input-readonly fn-left" style="width:150px" notNull="true" name="registerdate" id="registerdate" value="${(registerdate?string('yyyy-MM-dd'))?if_exists}"/>
		<#else> 
			<#if registerdate?exists>${registerdate?string("yyyy-MM-dd")}</#if>
		</#if>
        </td>
        <th><span class="c-orange">*</span>上午课节数：</th>
        <td>
        <#if unitEduTop>
	 		<input msgName="上午课节数" name="amPeriods" id="amPeriods" type="text" dataType="integer" maxlength="1" notNull="true" value="${amPeriods!}" class="input-txt fn-left" style="width:150px" size="20" title="请输入0-5的整数"/>
	 	<#else> 
		 	${amPeriods!}
	 	</#if>
        </td>
	  </tr>
	  <tr>
	  	<th><span class="c-orange">*</span>学期结束日期：</th>
        <td>
        <#if unitEduTop>
        	<@common.datepicker msgName="学期结束日期" class="input-txt input-readonly fn-left"  style="width:150px" notNull="true" name="semesterEnd" id="semesterEnd" value="${(semesterEnd?string('yyyy-MM-dd'))?if_exists}"/>
		<#else> 
			<#if semesterEnd?exists>${semesterEnd?string("yyyy-MM-dd")}</#if>
		</#if>
		</td>
	  	<th><span class="c-orange">*</span>下午课节数：</th>
        <td>
        <#if unitEduTop>
        	<input msgName="下午课节数" name="pmPeriods" id="pmPeriods" type="text" dataType="integer" maxlength="1" value="${pmPeriods!}" notNull="true" class="input-txt fn-left" style="width:150px" size="20" title="请输入0~5的整数"/>
        <#else> 
        	${pmPeriods!}
        </#if>
        </td>
	  </tr>
	  <tr>
        <th><span class="c-orange">*</span>工作结束日期：</th>
        <td>
        <#if unitEduTop>
        	<@common.datepicker msgName="工作结束日期" class="input-txt input-readonly fn-left" style="width:150px" notNull="true" name="workEnd" id="workEnd" value="${(workEnd?string('yyyy-MM-dd'))?if_exists}"/>
		<#else> 
			<#if workEnd?exists>${workEnd?string("yyyy-MM-dd")}</#if>
		</#if>
        </td>
        <th><span class="c-orange">*</span>晚上课节数：</th>
        <td>
        <#if unitEduTop>
        	<input msgName="晚上课节数" name="nightPeriods" id="nightPeriods" type="text" dataType="integer" maxlength="1" value="${nightPeriods!}" notNull="true" class="input-txt fn-left" style="width:150px" size="20" title="请输入0-5的整数"/>
	 	<#else> 
		 	${nightPeriods!}
	 	</#if>
        </td>
	  </tr>
	  <tr>
    	<td colspan="5" class="td-opt">
    	<#if unitEduTop>
        	<a href="javascript:formSubmit(this.form,'save');" class="abtn-blue-big">保存</a>
        </#if>
            <a href="javascript:cancel();" class="abtn-blue-big"><#if unitEduTop>取消<#else>返回</#if></a>
        </td>
      </tr>
	<input name="id" type="hidden" value="${id?if_exists}"/>
</table>
</div>  
</form>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>
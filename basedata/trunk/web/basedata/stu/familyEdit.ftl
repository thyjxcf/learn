<#import "/common/htmlcomponent.ftl" as h>
<#import "/common/commonmacro.ftl" as commonmacro>
<@h.moduleDiv titleName="学生家庭信息">
	<script type="text/javascript" language="javascript" src="${request.contextPath}/static/js/strspell.js"></script>
	<script>
	var saved = false;
	function validate(){
		clearMessages();
		
		if(!isActionable("#saveBtn")){
			return false;
		}
		if(!checkAllValidate("#famDiv")){
			return false;
		}
		
		var mobilePhone = document.getElementById("mobilePhone");
		if(!checkMobilePhone(mobilePhone)){
			return false;
		}
		
		if(!checkPhone(document.getElementById("linkPhone"),"联系电话")){
	   		return false;
	    }
	    
		if(!checkPhone(document.getElementById("officeTel"),"办公电话")){
	   		return false;
	    }
	    
		//检查身份证号
		var identityCard = document.getElementById("identityCard").value;
		var identitycardType = document.getElementById("identitycardType").value;
		if("" != identityCard.trim()){
			if("" == identitycardType.trim()){
				showMsgError("请选择身份证件号所对应的身份证件类型!");
				return false;
			}else{
				if('1' == identitycardType){
					if(!checkIdentityCard(document.getElementById("identityCard"))){
						return false;
					}
				}
			}
		}
	    if(!checkPostalCode(document.getElementById("postalcode"))){
	    	return false;
	    }
	    if(!checkEmail(document.getElementById("email"))){
	    	return false;
	    }

	    return true;
	    
	}
	
	function onSave(){
		if(saved){
			return false;
		}
		saved = true;
		if(validate()== false){
			saved = false;
			return false;
		}
		
		$("#saveBtn").attr("class", "abtn-unable");
		<#if systemDeploySchVersion?exists && systemDeploySchVersion == 'eis'>
		var saveUrl = '${request.contextPath}/stusys/sch/student/studentadmin-familyAdmin-SaveAdd.action';
		<#else> 
		var saveUrl = '${request.contextPath}/basedata/stu/familyAdmin-SaveAdd.action';
		</#if>
		var options = {
	        url:saveUrl, 
	        success : showReplyEdit,
	       dataType : 'json',
	       clearForm : false,
	       resetForm : false,
	       type : 'post',
	       //timeout : 3000 
	    };
		
		$('#form1').ajaxSubmit(options);
	}
	
	function showReplyEdit(data){
		var error = data;
		if(error && error != ''){
			showMsgError(data);
			$("#saveBtn").attr("class", "abtn-blue");
			saved = false;
		} else {
			showMsgSuccess('家庭信息保存成功!','',goback);
		}
	}
	
	function goback() {
	<#if systemDeploySchVersion?exists && systemDeploySchVersion == 'eis'>
	  load("#famDiv","${request.contextPath}/stusys/sch/student/studentadmin-familyAdmin-list.action?studentId=${family.studentId!}");
	  <#else>
	  load("#famDiv","${request.contextPath}/basedata/stu/familyAdmin-list.action?studentId=${family.studentId!}");
	  </#if>
	}
	
	function setSex(a) {
		if(a==51||a==52){
		  var sv = a-50;
		  document.getElementById("sex").value = sv;
		  var aa = $("#sex").parent().find("a[val='"+sv+"']"); 
		    var $val_txt=$(aa).text();
			var $val_zhi=$(aa).attr("val");
			$(aa).parent().siblings(".ui-select-txt").val($val_txt);
			$(aa).parent().siblings(".ui-select-value").val($val_zhi);
			$(aa).addClass("selected").siblings("a").removeClass("selected");
		}
	}
	
	function onCallBack(){
		document.getElementById("birthday").value=$("#birthday1").val()+"-01";
	}
	
	function clickMethod(){
		document.getElementById("birthday").value="";
	}
</script>
<form name="form1" id="form1" method="post">
<input type="hidden" name="studentId" value="${family.studentId}"/>
<input type="hidden" name="schoolId" value="${family.schoolId}"/>
<input type="hidden" name="id" value="${family.id?default("")}"/>
<input type="hidden" name="regionCode" value="${family.regioncode!}"/>
<p class="table-dt">家庭信息维护</p>
<@h.tableDetail>
		<tr>
		<#if systemDeploySchVersion?exists && systemDeploySchVersion == 'eis'>
			<@h.tds name="relation" msgName="关系" notNull="true"  onChange="setSex" width="15%">
		  		${appsetting.getMcode("DM-GX").getHtmlTag(family.relation?default(''))}
			</@h.tds>
		<#else>
			<@h.tds name="relation" msgName="关系" notNull="true"  onChange="setSex" width="15%">
		  		${appsetting.getMcode("DM-CGX").getHtmlTag(family.relation?default(''))}
			</@h.tds>
		</#if>
			<@h.tdi name="name" msgName="姓名" value="${family.name?default('')?trim}" maxLength="100" width="15%" notNull="true"/>
		</tr>
		<tr>
			<@h.tds name="sex" msgName="性别"  value="">
				${appsetting.getMcode("DM-XB").getHtmlTag(family.sex?default(''),false)}
			</@h.tds>
			<@h.tdi name="linkPhone" msgName="联系电话" notNull="true" value="${family.linkPhone?default('')?trim}" maxLength="30" />
		  </tr>
		  <tr>
			<@h.tds msgName="身份证件类型" name="identitycardType"  value="${family.identitycardType?default('1')}" readonly="${readonly!}">
				    	${appsetting.getMcode("DM-ZJLX").getHtmlTag((family.identitycardType?string)?default(''))}
	    	</@h.tds>
			<@h.tdi name="identityCard" msgName="身份证件号" value="${family.identityCard?default('')?trim}" maxLength="18" />
		  </tr>	
		  <tr>
		  	<@h.tdi name="mobilePhone" msgName="手机" value="${family.mobilePhone?default('')?trim}" maxLength="30" />
		  	<th>出生年月：</th>
		  	<td>
			<@h.datepicker name="birthday1" msgName="出生年月"  dateFmt="yyyy-MM" value="${((family.birthday)?string('yyyy-MM'))?if_exists}" size="20" onpicked="onCallBack" oncleared="clickMethod"/>
	  		<input type='hidden' value='${((family.birthday)?string('yyyy-MM-dd'))?if_exists}' name="birthday" id="birthday" >
	  		</td>
		  </tr>			  
		  <tr>
		  	<@h.tdi name="linkAddress" msgName="家庭地址" value="${family.linkAddress?default('')?trim}" maxLength="30" />
		    <@h.tdi name="email" msgName="电子邮箱" value="${family.email?default('')?trim}" maxLength="40" />
		  </tr>	
		  <tr>
		  	<@h.tdi name="postalcode" msgName="邮政编码" value="${family.postalcode?default('')?trim}" maxLength="6" />
		  	<@h.tdi name="officeTel" msgName="办公电话" value="${family.officeTel?default('')?trim}" maxLength="30" />
		  </tr>	
		  <tr>
			<@h.tdi name="company" msgName="工作或学习单位" value="${family.company?default('')?trim}" maxLength="30" />
			<@h.tds msgName="健康状况"  name="health" value="${family.health?default('')}" readonly="${readonly!}" colspan="2">
				    	${appsetting.getMcode("DM-CJKZK").getHtmlTag((family.health?string)?default(''))}
		    </@h.tds>
		  </tr>		
		  <tr>
			<@h.tdi name="duty" msgName="职务" value="${family.duty?default('')?trim}" maxLength="10" />
			<@h.tds name="politicalStatus" msgName="政治面貌"  value="">
				${appsetting.getMcode("DM-CZZMM").getHtmlTag(politicalStatus?default(''))}
			</@h.tds>
		  </tr>
		  <tr>
			<@h.tds name="maritalStatus" msgName="婚姻状况"  value="">
				${appsetting.getMcode("DM-CHYZK").getHtmlTag(family.maritalStatus?default(''))}
			</@h.tds>
			<@h.tds name="culture" msgName="文化程度"  value="">
				${appsetting.getMcode("DM-WHCD").getHtmlTag(family.culture?default(''))}
			</@h.tds>
		  </tr>
		  <tr>
			<@h.tds name="workCode" msgName="职业"  value="">
				${appsetting.getMcode("DM-ZYM").getHtmlTag(family.workCode?default(''))}
			</@h.tds>
			<@h.tds name="nation" msgName="民族"  value="">
				${appsetting.getMcode("DM-MZ").getHtmlTag(family.nation?default(''))}
			</@h.tds>
		  </tr>
		  <tr>
			<th>是否监护人：</th>
		    <td colspan="4">
				<span class="ui-radio-box"><span class="ui-radio<#if guardian> ui-radio-current</#if>" data-name='guardian'><input type="radio" class="radio" name="guardian" value="true" <#if guardian>checked="checked"</#if>>是</span>
				<span class="ui-radio<#if !guardian> ui-radio-current</#if>" data-name='guardian'><input type="radio" class="radio" name="guardian" value="false" <#if !guardian>checked="checked"</#if>>否</span></span>
		  	</td>
		  </tr>
		  <tr>
		  	<@h.tdt msgName="备注" name="remark" maxLength="200" colspan="4" style="width:470px;" value="${family.remark?default('')?trim}" /> 
		  </tr>
		  <@h.tableBottom saveScript="onSave" closeScript="goback"  colspan=4 />
</@h.tableDetail>
</form>
<script>
vselect();
</script>
</@h.moduleDiv>
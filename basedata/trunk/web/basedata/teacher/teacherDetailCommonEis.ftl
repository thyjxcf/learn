	<#if columnsList?exists>
      <#assign sign = "0">
      <#assign i = 0>
	  <#list columnsList as item>							
		<#if i % 2 == 0><tr></#if>
		<#if item.colsCode = "empId">
			<th width="18%">编号：</th>
			<td width="32%">
				<input name="teacher.tchId" id="teacher.tchId" type="text" class="<#if isDesktop>input-readonly</#if> input-txt fn-left" style="width:140px;" 
					onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"
					value="${teacher.tchId?default('')?trim}" msgName="编号" notNull="true" maxLength="6" <#if isDesktop>readonly="true"</#if>>
			<span class="fn-left c-orange mt-5 ml-10">*</span></td>
		<#assign i = i + 1>
		</#if>
		<#if item.colsCode = "name">
			<th>姓名：</th>
			<td><input name="teacher.name" id="teacher.name" type="text" class="input-txt fn-left" style="width:140px;" value="${teacher.getName()?default('')?trim}"  msgName="姓名" notNull="true" maxLength="30" <#if teacher.getId()?default('')==''>onBlur="checkoutName(this);" </#if>>
			<span class="fn-left c-orange mt-5 ml-10">*</span></td>
		<#assign i = i + 1>
		</#if>
		<#if item.colsCode = "pname">
			<th>曾用名：</th>
			<td><input name="teacher.pname" id="teacher.pname" type="text"  msgName="曾用名" maxLength="30" class="input-txt fn-left" style="width:140px;" value="${teacher.getPname()?default('')?trim}"></td>
		<#assign i = i + 1>
		</#if>
		<#if item.colsCode = "sex">
			<th>性别：</th>
			<td>
				<div class="ui-select-box  fn-left" style="width:150px;">
	                <input type="text" class="ui-select-txt"   value="" notNull="true" />
	                <input type="hidden" name="teacher.sex" id="teacher.sex" class="ui-select-value" />
	                <a class="ui-select-close"></a>
	                <div class="ui-option">
	                	<div class="a-wrap">
	                    ${appsetting.getMcode("DM-XB").getHtmlTag(teacher.getSex()?default(''))}
	                    </div>
	                </div>
	           	</div>
			<span class="fn-left c-orange mt-5 ml-10">*</span></td>
		<#assign i = i + 1>
		</#if>
		<#if item.colsCode = "idcard">
			<th width="18%">身份证号码：</th>
			<td width="32%"><input name="teacher.idcard" id="teacher.idcard" type="text" class="input-txt fn-left" style="width:140px;"  msgName="身份证号码" maxLength="18" value="${teacher.getIdcard()?default('')?trim}">
			</td>
		<#assign i = i + 1>
		</#if>							
		<#if item.colsCode = "groupid">
			<th>部门：</th>
			<#if isDesktop>
				<td>
				<input name="teacher.unitid" type="hidden" value="${teacher.getUnitid()?default('')}">
		       	<input type="text" name="teacher.deptName" id="teacher.deptName" notNull="true" msgName="部门"  style="width:140px;" value="${teacher.deptName?default('')}" class="<#if isDesktop>input-readonly</#if> input-txt" size="20" readonly="true">
		       	<input type="hidden" name="teacher.deptid" id="teacher.deptid" value="${teacher.deptid?default('')}" class="input-txt">
		  	    <span class="c-orange mt-5 ml-5">*</span>
	       	  	</td>
       	  	<#else>
		       	<td>
		       	<input name="teacher.unitid" type="hidden" value="${teacher.getUnitid()?default('')}">
			       	<@commonmacro.selectTree idObjectId="teacher.deptid" nameObjectId="teacher.deptName"  preset="" treeUrl=request.contextPath+"/common/xtree/deptTree.action">
				  	   <input type="hidden" name="teacher.deptid" id="teacher.deptid" value="${teacher.deptid?default('')}" class="select_current02"> 
				  	   <input type="text" name="teacher.deptName" id="teacher.deptName" notNull="true" msgName="部门"  style="width:140px;" value="${teacher.deptName?default('')}" class="select_current02" size="20">
				  	   <span class="c-orange mt-5 ml-10">*</span>
		  	  	 	</@commonmacro.selectTree>
	       	  	</td> 
       	  	</#if>   	  	  
		<#assign i = i + 1>
		</#if>
		<#if item.colsCode = "birthday">
			<@common.tdd  name="teacher.birthday" id="teacher.birthday" 
			value="${(teacher.birthday?string('yyyy-MM-dd'))?if_exists}" notNull="true" msgName="出生年月"/>
		<#assign i = i + 1>
		</#if>
		<#if item.colsCode = "personTel">
				<th>手机号码：</th>
				<td><input name="teacher.personTel" id="teacher.personTel" type="text" msgName="手机号码" maxLength="20" class="input-txt fn-left" style="width:140px;" value="${teacher.getPersonTel()?default('')?trim}"></td>
			<#assign i = i + 1>
			</#if>
		<#if item.colsCode = "eusing">
			<th>在职标记：</th>
			<td>
				${appsetting.getMcode("DM-JSZZBJ").get(teacher.getEusing()?default(''))}
				<input type="hidden" name="teacher.eusing" id="teacher.eusing" class="ui-select-value" value="${teacher.getEusing()?default('')}"/>
		    </td>
		<#assign i = i + 1>
		</#if>
		<#if item.colsCode = "country">
			<th>国籍：</th>
			<td>
	       	<div class="ui-select-box" style="width:150px;">  
	                <input type="text" class="ui-select-txt" value=""  />
	                <input type="hidden" id="teacher.country" name="teacher.country" class="ui-select-value" />
	                <a class="ui-select-close"></a>
	                <div class="ui-option">
	                	<div class="a-wrap">
	                    ${appsetting.getMcode("DM-COUNTRY").getHtmlTag(teacher.country?default(''))}
	                    </div>
	                </div>
	           	</div>
	      	</td>
		<#assign i = i + 1>
		</#if>
			<#if item.colsCode = "nation">
				<th>民族：</th>
				<td>
	       		<div class="ui-select-box  fn-left" style="width:150px;">  
	                <input type="text" class="ui-select-txt" notNull="true"  msgName="民族"/>
	                <input type="hidden" name="teacher.nation" id="teacher.nation"  class="ui-select-value" />
	                <a class="ui-select-close"></a>
	                <div class="ui-option">
	                	<div class="a-wrap">
	                    ${appsetting.getMcode("DM-MZ").getHtmlTag(teacher.getNation()?default(''))}
	                	</div>
	                </div>
	           	</div>
	       		<span class="fn-left c-orange mt-5 ml-10">*</span>
	       		</td>
			<#assign i = i + 1>
			</#if>
			<#if item.colsCode = "email">
					<th>电子邮件：</th>
					<td colspan="1"><input name="teacher.email" id="teacher.email" type="text" msgName="电子邮件" regex="/^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/" regexMsg="电子邮件格式不符合校验规则" maxLength="40" class="input-txt fn-left" style="width:140px;" value="${teacher.getEmail()?default('')?trim}" title="建议填写，可用于密码遗忘时的取回"></td>
			<#assign i = i + 1>
			</#if>
			<#if item.colsCode = "homeAddress">
				<@common.tdi  name="teacher.homeAddress" id="teacher.homeAddress" value="${teacher.homeAddress?default('')}" maxLength="60" msgName="家庭地址"/>
			<#assign i = i + 1>
			</#if>
			
<#--以下为隐藏字段。保证保存时不会把原有的值置空-->
<#if item.colsCode = "remark">
		<input name="teacher.remark" id="teacher.remark" type="hidden" value="${teacher.remark?default('')?trim}">
	</#if>
<#if item.colsCode = "linkPhone">
		<input name="teacher.linkPhone" id="teacher.linkPhone" type="hidden" value="${teacher.linkPhone?default('')?trim}">
	</#if>				
<#if item.colsCode = "postalcode">
	<input name="teacher.postalcode" maxlength="6" msgName="邮政编码" id="teacher.postalcode" type="hidden" class="input-txt fn-left" style="width:140px;" value="${teacher.postalcode?default('')?trim}" >
</#if>
<#if item.colsCode = "chargeNumberType">
	<input name="teacher.chargeNumberType" id="teacher.chargeNumberType" type="hidden" msgName="扣费号码类型" maxLength="1" class="input-txt fn-left" style="width:140px;" value="${teacher.chargeNumberType?default('')?trim}" >
</#if>		
<#if item.colsCode = "multiTitle">
		<input name="teacher.multiTitle" id="teacher.multiTitle" type="hidden" class="input" maxlength="" size=""  value="${teacher.multiTitle?default('')}">
</#if>
<#if item.colsCode = "teachStatus">
		<input name="teacher.teachStatus" id="teacher.teachStatus" type="hidden" class="input" maxlength="" size=""  value="${teacher.teachStatus?default('')}">
</#if>
<#if item.colsCode = "returnedChinese">
		<input name="teacher.returnedChinese" id="teacher.returnedChinese" type="hidden" class="input" maxlength="" size=""  value="${teacher.returnedChinese?default('')}">
</#if>		
<#if item.colsCode = "weaveType">
		<input name="teacher.weaveType" id="teacher.weaveType" type="hidden" class="input" maxlength="" size=""  value="${teacher.weaveType?default('')}">
</#if>	
<#if item.colsCode = "workdate">
	    <input name="teacher.workdate" id="teacher.workdate" type="hidden" <#if teacher.workdate?exists>value="${teacher.workdate?string("yyyy-MM-dd")}"<#else>value=""</#if>>
</#if>
<#if item.colsCode = "title">
	    <input name="teacher.title" id="teacher.title" type="hidden" value="${teacher.title?default('')?trim}">	
</#if>
<#if item.colsCode = "duty">
	    <input name="teacher.duty" id="teacher.duty" type="hidden" value="${teacher.duty?default('')?trim}">
</#if>
<#if item.colsCode = "officeTel">
		 <input name="teacher.officeTel" id="teacher.officeTel" type="hidden" value="${teacher.officeTel?default('')?trim}">	
</#if>
<#if item.colsCode = "chargeNumber">
		 <input name="teacher.chargeNumber" id="teacher.chargeNumber" type="hidden" value="${teacher.chargeNumber?default('')?trim}">	
</#if>
<#if item.colsCode = "polity">
		<input name="teacher.polity" id="teacher.polity" type="hidden" value="${teacher.polity?default('')?trim}">
</#if>
<#if item.colsCode = "polityJoin">
		<input name="teacher.polityJoin" id="teacher.polityJoin" type="hidden" <#if teacher.polityJoin?exists>value="${teacher.polityJoin?string("yyyy-MM-dd")}"<#else>value=""</#if>>
</#if>				
<#if item.colsCode = "perNative">
		<input name="teacher.perNative" id="teacher.perNative" type="hidden" value="${teacher.perNative?default('')?trim}">
</#if>
<#if item.colsCode = "stulive">
		<input name="teacher.stulive" id="teacher.stulive" type="hidden" value="${teacher.stulive?default('')?trim}">
</#if>
<#if item.colsCode = "major">
		<input name="teacher.major" id="teacher.major" type="hidden" value="${teacher.major?default('')?trim}">
</#if>
<#if item.colsCode = "fsschool">
		<input name="teacher.fsschool" id="teacher.fsschool" type="hidden" value="${teacher.fsschool?default('')?trim}">
</#if>
<#if item.colsCode = "fstime">
		<input name="teacher.fstime" id="teacher.fstime" type="hidden" <#if teacher.fstime?exists>value="${teacher.fstime?string("yyyy-MM-dd")}"<#else>value=""</#if>>
</#if>
<#if item.colsCode = "registertype">
		<input name="teacher.registertype" id="teacher.registertype" type="hidden" value="${teacher.registertype?default(-1)?string}">
</#if>	
<#if item.colsCode = "cardNumber">
	     <input name="teacher.cardNumber" id="teacher.cardNumber" type="hidden" class="input" maxlength="20" size="20"  value="${teacher.getCardNumber()?default('')}">
</#if>
<#if item.colsCode = "register">
		<input name="teacher.register" id="teacher.register" type="hidden" value="${teacher.register?default('')?trim}">
</#if>
<#if item.colsCode = "displayOrder">
	     <input name="teacher.displayOrder" id="teacher.displayOrder" type="hidden" class="input" maxlength="20" size="20"  value="${teacher.getDisplayOrder()?default('')}">
</#if>	
<#if item.colsCode = "address">
		<input name="teacher.linkAddress" id="teacher.linkAddress" type="hidden" value="${teacher.linkAddress?default('')?trim}">
</#if>
<#if item.colsCode = "homepage">
		<input name="teacher.homepage" id="teacher.homepage" type="hidden" value="${teacher.homepage?default('')?trim}">
</#if>				
<#if item.colsCode = "workTeachDate">
		<input name="teacher.workTeachDate" id="teacher.workTeachDate" type="hidden" class="input" maxlength="" size=""  value="${(teacher.workTeachDate?string('yyyy-MM-dd'))?if_exists}">
</#if>	
<#if item.colsCode = "oldAcademicQualification">
		<input name="teacher.oldAcademicQualification" id="teacher.oldAcademicQualification" type="hidden" class="input" maxlength="" size=""  value="${teacher.oldAcademicQualification?default('')}">
</#if>
<#if item.colsCode = "specTechnicalDuty">
		<input name="teacher.specTechnicalDuty" id="teacher.specTechnicalDuty" type="hidden" class="input" maxlength="" size=""  value="${teacher.specTechnicalDuty?default('')}">
</#if>
<#if item.colsCode = "specTechnicalDutyDate">
		<input name="teacher.specTechnicalDutyDate" id="teacher.specTechnicalDutyDate" type="hidden" class="input" maxlength="" size=""  value="${teacher.specTechnicalDutyDate?default('')}">
</#if>
<#if item.colsCode = "generalCard">
		<input name="teacher.generalCard" id="teacher.generalCard" type="hidden" class="input" maxlength="" size=""  value="${teacher.generalCard?default('')}">
</#if>	
			
			
			<#if i % 2 == 0>
				</tr>
			<#elseif item_index == columnsList.size()-1>
				<th class='send_font_no_width' colspan='2'>&nbsp;</th>
				</tr>
			</#if>	
		</#list>
		</#if>
		
<script>
	vselect();
	
	function onCallBackWorkdate(){
		document.getElementById("teacher.workdate").value=$("#workdate1").val()+"-01";
	}

	function clickMethodWorkdate(){
		document.getElementById("teacher.workdate").value="";
	}
	
	function onCallBackWorkTeachDate(){
		document.getElementById("teacher.workTeachDate").value=$("#workTeachDate1").val()+"-01";
	}

	function clickMethodWorkTeachDate(){
		document.getElementById("teacher.workTeachDate").value="";
	}
	
	function onCallBackSpecTechnicalDutyDate(){
		document.getElementById("teacher.specTechnicalDutyDate").value=$("#specTechnicalDutyDate1").val()+"-01";
	}

	function clickMethodSpecTechnicalDutyDate(){
		document.getElementById("teacher.specTechnicalDutyDate").value="";
	}
	
</script>
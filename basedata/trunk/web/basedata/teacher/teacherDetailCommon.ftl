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
					value="${teacher.tchId?default('')?trim}" msgName="编号" notNull="true" maxLength="8" <#if isDesktop>readonly="true"</#if>>
			<span class="fn-left c-orange mt-5 ml-10">*</span></td>
		<#assign i = i + 1>
		</#if>
		<#if item.colsCode = "fileNumber">
			<th width="18%" >档案号：</th>
			<td width="32%"><input name="teacher.fileNumber" id="teacher.fileNumber" msgName="档案号" maxLength="255" type="text" class="input-txt fn-left" style="width:140px;" value="${teacher.fileNumber?default('')?trim}"></td>
		<#assign i = i + 1>
		</#if>
		<#if item.colsCode = "name">
			<th>姓名：</th>
			<td><input name="teacher.name" id="teacher.name" type="text" class="input-txt fn-left" style="width:140px;" value="${teacher.getName()?default('')?trim}"  msgName="姓名" notNull="true" maxLength="100" <#if teacher.getId()?default('')==''>onBlur="checkoutName(this);" </#if>>
			<span class="fn-left c-orange mt-5 ml-10">*</span></td>
		<#assign i = i + 1>
		</#if>
		<#if item.colsCode = "pname">
			<th>曾&nbsp;用&nbsp;名：</th>
			<td><input name="teacher.pname" id="teacher.pname" type="text"  msgName="曾用名" maxLength="100" class="input-txt fn-left" style="width:140px;" value="${teacher.getPname()?default('')?trim}"></td>
		<#assign i = i + 1>
		</#if>
		<#if item.colsCode = "researchGroup">
			<th>教研组：</th>
			<td>
				<select id="teacher.researchGroupId" name="teacher.researchGroupId" notNull="false" class="input-sel100" style="width:150px;height:22px;">
						<option value="">--请选择(不限)--</option>
					<#list researchGroupList as list>
						<option value="${list.id!}" <#if (teacher.researchGroupId!) == (list.id!)>selected</#if>>${list.teachGroupName!}</option>
					</#list>
				</select>
			</td>
		<#assign i = i + 1>
		</#if>  
		<#if item.colsCode = "sex">
			<th>性　　别：</th>
			<td>
				<div class="ui-select-box  fn-left" style="width:150px;">
	                <input type="text" class="ui-select-txt" id="sexText"  value="" notNull="true" msgName="性别" />
	                <input type="hidden" name="teacher.sex" id="teacher.sex" class="ui-select-value" />
	                <a class="ui-select-close"></a>
	                <div class="ui-option">
	                	<div class="a-wrap" id="sexOption">
	                    ${appsetting.getMcode("DM-XB").getHtmlTag(teacher.getSex()?default(''))}
	                    </div>
	                </div>
	           	</div>
			<span class="fn-left c-orange mt-5 ml-10">*</span></td>
		<#assign i = i + 1>
		</#if>
		<#if item.colsCode = "linkPhone">
		<#assign sign = "0">
			<th>联系方式：</th>
			<td><input name="teacher.linkPhone" id="teacher.linkPhone" msgName="联系方式" maxLength="20" type="text" class="input-txt fn-left" style="width:140px;" regex="/^[0-9]{1,20}$/" regexMsg="请输入正确的手机号,并且不能超过20个数字" value="${teacher.linkPhone?default('')?trim}" ></td>
		<#assign i = i + 1>
		</#if>
		<#if item.colsCode = "postalcode">
			<th>邮政编码：</th>
			<td><input name="teacher.postalcode" maxlength="6" msgName="邮政编码" id="teacher.postalcode" type="text" class="input-txt fn-left" style="width:140px;" value="${teacher.postalcode?default('')?trim}" ></td>
		<#assign i = i + 1>
		</#if>
		<#if item.colsCode = "chargeNumberType">
			<th>扣费号码类型：</th>
			<td><input name="teacher.chargeNumberType" id="teacher.chargeNumberType" type="text" msgName="扣费号码类型" maxLength="1" class="input-txt fn-left" style="width:140px;" value="${teacher.chargeNumberType?default('')?trim}" ></td>
		<#assign i = i + 1>
		</#if>
		<#if item.colsCode = "idcard">
			<th>身份证号码：</th>
			<td><input name="teacher.idcard" id="teacher.idcard" type="text" onblur="addSexBirth()" class="input-txt fn-left" style="width:140px;"  msgName="身份证号码" maxLength="18" value="${teacher.getIdcard()?default('')?trim}">
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
				  	   <input type="text" name="teacher.deptName" id="teacher.deptName" notNull="true" msgName="部门"  style="width:140px;" value="${teacher.deptName?default('')}" class="select_current02 input-readonly" size="20" readonly="readonly">
				  	   <span class="c-orange mt-5 ml-10">*</span>
		  	  	 	</@commonmacro.selectTree>
	       	  	</td> 
       	  	</#if>   	  	  
		<#assign i = i + 1>
		</#if>
		<#--if item.colsCode = "instituteId">							   
			<th>所在院系(院系下教职工必须填写)：</th>
			<td colspan="1">
				<div class="ui-select-box  fn-left" style="width:150px;">
	                <input type="text" class="ui-select-txt"   />
	                <input type="hidden" name="teacher.instituteId" id="teacher.instituteId" class="ui-select-value" />
	                <a class="ui-select-close"></a>
	                <div class="ui-option">
	                    <#list instituteList as institute>
            			<a value="${institute.id!}" <#if teacher.instituteId! == institute.id!>class="selected"</#if>>${institute.instituteName!}</a>
            			</#list>
	                </div>
	           	</div>
			</td>
		<#assign i = i + 1>
		</#if-->
		<input type="hidden" value="test" id="hello" >
		<#if item.colsCode = "birthday">
			<@common.tdd  name="teacher.birthday" id="teacher.birthday" 
			value="${(teacher.birthday?string('yyyy-MM-dd'))?if_exists}"  msgName="出生年月"/>
		<#assign i = i + 1>
		</#if>
		<#if item.colsCode = "eusing">
			<th>在职标记：</th>
			<td>
				<#if isDesktop>
					${appsetting.getMcode("DM-JSZZBJ").get(teacher.getEusing()?default(''))}
					<input type="hidden" name="teacher.eusing" id="teacher.eusing" class="ui-select-value" value="${teacher.getEusing()?default('')}"/>
				<#else>
			       	<div class="ui-select-box <#if (teacher.id?default('')==adminTeacherId?default('') && teacher.getId()?default('')!='')>ui-select-box-disable</#if> fn-left" style="width:150px;">  
		                <input type="text" class="ui-select-txt" value="" notNull="true" msgName="在职标记" />
		                <input type="hidden" name="teacher.eusing" id="teacher.eusing" class="ui-select-value"  value="${teacher.getEusing()?default('')}"/>
		                <a class="ui-select-close"></a>
		                <div class="ui-option">
		                	<div class="a-wrap">
		                    ${appsetting.getMcode("DM-JSZZBJ").getHtmlTag(teacher.getEusing()?default(''))}
		                    </div>
		                </div>
		           	</div>
		    		<span class="fn-left c-orange mt-5 ml-10">*</span>
		    	</#if>	
		    </td>
		<#assign i = i + 1>
		</#if>
		<#--增加新字段-->
		<#if item.colsCode = "multiTitle">
			<th>是否是双师型：</th>
			<td>
				<span class=" ui-radio <#if teacher.multiTitle?default('0 ') == '1 '> ui-radio-current</#if>" data-name="a" >
				<input name="teacher.multiTitle" type="radio" <#if teacher.multiTitle?default('0') == '1 '>checked="checked"</#if> value="1" class="radio">是</span>
				<span class=' ui-radio <#if teacher.multiTitle?default("0 ") == "0 "> ui-radio-current</#if>' data-name="a">
				<input name="teacher.multiTitle" type="radio" <#if teacher.multiTitle?default('0') == '0 '>checked="checked"</#if> value="0" class="radio">否</span>
	      	</td>
		<#assign i = i + 1>
		</#if>
		<#if item.colsCode = "teachStatus">
			<th>授课状态：</th>
			<td>
	       	<div class="ui-select-box" style="width:150px;">  
	                <input type="text" class="ui-select-txt"   />
	                <input type="hidden" id="teacher.teachStatus"  name="teacher.teachStatus" class="ui-select-value" />
	                <a class="ui-select-close"></a>
	                <div class="ui-option">
	                	<div class="a-wrap">
	                    ${appsetting.getMcode("DM-JSSKZT").getHtmlTag(teacher.teachStatus?default(''))}
	                    </div>
	                </div>
	           	</div>
	      	</td>
		<#assign i = i + 1>
		</#if>
		<#if item.colsCode = "returnedChinese">
			<th>港澳台侨：</th>
			<td>
	       	<div class="ui-select-box" style="width:150px;">  
	                <input type="text" class="ui-select-txt" value=""  />
	                <input type="hidden" id="teacher.returnedChinese" name="teacher.returnedChinese" class="ui-select-value" />
	                <a class="ui-select-close"></a>
	                <div class="ui-option">
	                	<div class="a-wrap">
	                    ${appsetting.getMcode("DM-GATQ").getHtmlTag(teacher.returnedChinese?default(''))}
	                    </div>
	                </div>
	           	</div>
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
		<#if item.colsCode = "weaveType">
			<th>编制类别：</th>
			<td>
	       	<div class="ui-select-box" style="width:150px;">  
	                <input type="text" class="ui-select-txt" value=""  />
	                <input type="hidden" id="teacher.weaveType" name="teacher.weaveType" class="ui-select-value" />
	                <a class="ui-select-close"></a>
	                <div class="ui-option">
	                	<div class="a-wrap">
	                    ${appsetting.getMcode("DM-BZLB").getHtmlTag(teacher.weaveType?default(''))}
	                    </div>
	                </div>
	           	</div>
	      	</td>
		<#assign i = i + 1>
		</#if>
		<#if item.colsCode = "workdate">
			<th>参加工作年月：</th>
			<td>
		        <@common.datepicker id="workdate1" dateFmt="yyyy-MM" value="${(teacher.workdate?string('yyyy-MM'))?if_exists}" onpicked="onCallBackWorkdate" oncleared="clickMethodWorkdate"/>
		        <input type='hidden' value='${(teacher.workdate?string('yyyy-MM-dd'))?if_exists}' name="teacher.workdate" id="teacher.workdate" >
	        </td>
		<#assign i = i + 1>
		</#if>
			<#if item.colsCode = "title">
				<th>现聘岗位：</th>
				<td>
	       	  	  <div class="ui-select-box" style="width:150px;">  
	                <input type="text" class="ui-select-txt"   />
	                <input type="hidden" id="teacher.title" name="teacher.title" class="ui-select-value" />
	                <a class="ui-select-close"></a>
	                <div class="ui-option">
	                	<div class="a-wrap">
	                    ${appsetting.getMcode("DM-JSZCM").getHtmlTag(teacher.getTitle()?default(''))}
	                	</div>
	                </div>
	           	</div>
	       		</td>
			<#assign i = i + 1>
			</#if>
			<#if item.colsCode = "duty"> 
				<th>职　　务：</th>
				<#if isDesktop>
					<td>
					<input type="hidden" name="teacher.duty" id="teacher.duty" value="${teacher.getDuty()?default('')}">
	                <input type="text" name="teacher.dutyName" id="teacher.dutyName" value="${teacher.getDutyName()?default('')}" class="<#if isDesktop>input-readonly</#if> select_current02" size="20" style="width:140px;" readonly="true">
		      		</td>
	      		<#else>
					<td>
						<#if loginInfo.unitClass == 1>
							<@commonmacro.selectMoreMcodeDetail idObjectId="teacher.duty" nameObjectId="teacher.dutyName" otherParam="groupId=DM-JYJZW"
								callback="test" preset="">
						  	   <input type="hidden" name="teacher.duty" id="teacher.duty" value="${teacher.getDuty()?default('')}">  
						  	   <input type="text" name="teacher.dutyName" id="teacher.dutyName" value="${teacher.getDutyName()?default('')}" class="select_current02" size="20" style="width:140px;">
					  	    </@commonmacro.selectMoreMcodeDetail>
						<#else>
							<@commonmacro.selectMoreMcodeDetail idObjectId="teacher.duty" nameObjectId="teacher.dutyName" otherParam="groupId=DM-XXZW"
								callback="test" preset="">
						  	   <input type="hidden" name="teacher.duty" id="teacher.duty" value="${teacher.getDuty()?default('')}">  
						  	   <input type="text" name="teacher.dutyName" id="teacher.dutyName" value="${teacher.getDutyName()?default('')}" class="select_current02" size="20" style="width:140px;">
					  	    </@commonmacro.selectMoreMcodeDetail>
						</#if>
		      		</td>
	      		</#if>
			<#assign i = i + 1>
			</#if>
			<#if item.colsCode = "dutyDate">
				<th width = "15%">任职时间：</th>
				<td width = "35%">
					<@common.datepicker class="input-txt input-readonly" name="teacher.dutyDate" id="teacher.dutyDate" value="${(teacher.dutyDate?string('yyyy-MM-dd'))?if_exists}"/>					
				</td>
				<#assign i = i + 1>
			</#if>
			<#if item.colsCode = "dutyLevel">
				<th width = "15%">职务级别：</th>
				<td width = "35%">
					<div class="ui-select-box" style="width:150px;">  
		                <input type="text" class="ui-select-txt"   />
		                <input type="hidden" id="teacher.dutyLevel" name="teacher.dutyLevel" class="ui-select-value" />
		                <a class="ui-select-close"></a>
		                <div class="ui-option" myfunchange="changeElement('teacher.dutyLevel', 'otherDutyLevelSpan', 'teacher.otherDutyLevel', '99');">
		                    <div class="a-wrap">
		                    ${appsetting.getMcode("DM-ZWJB").getHtmlTag(teacher.dutyLevel?default(''))}
		               		</div>
		                </div>
	           		</div>
	           		
					<span id="otherDutyLevelSpan" style="display:<#if teacher.dutyLevel?default('')?trim != '99'>none</#if>">
						<input type="text" name="teacher.otherDutyLevel" id="teacher.otherDutyLevel" msgName="其他职务级别" maxLength="255"  value="${teacher.otherDutyLevel?default('')?trim}">
					</span>
				</td>
			<#assign i = i + 1>
			</#if>
			<#if item.colsCode = "dutyEx">
				<th width = "15%">其他职务：</th>
				<td width = "35%">
					<input type="text" class="input-txt fn-left" style="width:140px;" name="teacher.dutyEx" id="teacher.dutyEx" msgName="其他职务" maxLength="255" value="${teacher.dutyEx?default('')}">
				</td>
			<#assign i = i + 1>
			</#if>
			<#if item.colsCode = "dutyExUnit">
				<th width = "15%">其他职务所在单位：</th>
				<td width = "35%">
					<input type="text" class="input-txt fn-left" style="width:140px;" name="teacher.dutyExUnit" id="teacher.dutyExUnit"  msgName="其他职务所在单位" maxLength="60" value="${teacher.dutyExUnit?default('')}">
				</td>
			<#assign i = i + 1>
			</#if>
			<#if item.colsCode = "dutyExDate">
				<th width = "15%">其他职务任职时间：</th>
				<td width = "35%">
					<@common.datepicker class="input-txt input-readonly" name="teacher.dutyExDate" id="teacher.dutyExDate" value="${(teacher.dutyExDate?string('yyyy-MM-dd'))?if_exists}"/>				
				</td>
				<#assign i = i + 1>
			</#if>
			<#if item.colsCode = "job">
				<th width = "15%">工作岗位：</th>
				<td width = "35%">
					<select name="teacher.job" id="teacher.job" class="input-txt"  style="width:150px;height:25px;" onChange="changeJob()">
						${appsetting.getMcode("DM-GZGW").getHtmlTag(teacher.job?default('')?trim,true,1)}
					</select>
					<span id="jobLevelSpan01" style = "display:<#if teacher.job?default('') != "01">none</#if>">
						<input type="hidden" class="input-txt fn-left" style="width:140px;" name="teacher.jobLevel" msgName="工作岗位" maxLength="255" id="jobLevel" value="${teacher.jobLevel?default('')}">
						<select id="jobLevelSelect01" class="input-txt" style="width:150px;height:25px;" onChange="$('#jobLevel').val(this.value);changeElement('jobLevel', 'otherJobLevelSpan', 'teacher.otherJobLevel', '99');">
							${appsetting.getMcode("DM-PRGW").getHtmlTag(teacher.jobLevel?default('')?trim,true,1)}
						</select>
					</span>
					<span id="jobLevelSpan02" style = "display:<#if teacher.job?default('') != "02">none</#if>">
						<select id="jobLevelSelect02" class="input-txt" style="width:150px;height:25px;" onChange="$('#jobLevel').val(this.value);changeElement('jobLevel', 'otherJobLevelSpan', 'teacher.otherJobLevel', '99');">
							${appsetting.getMcode("DM-PRGW").getHtmlTag(teacher.jobLevel?default('')?trim,true,1)}
						</select>
					</span>
					<span id="jobLevelSpan03" style = "display:<#if teacher.job?default('') != "03">none</#if>">
						<select id="jobLevelSelect03" class="input-txt" style="width:150px;height:25px;" onChange="$('#jobLevel').val(this.value);changeElement('jobLevel', 'otherJobLevelSpan', 'teacher.otherJobLevel', '99');">
							${appsetting.getMcode("DM-GQG").getHtmlTag(teacher.jobLevel?default('')?trim,true,1)}
						</select>
					</span>
					<span id="otherJobLevelSpan" style="display:<#if teacher.jobLevel?default('') != "99">none</#if>">
						<input type="text" class="input-txt fn-left" style="width:140px;" name="teacher.otherJobLevel" msgName="其他工作岗位级别" maxLength="255" id="teacher.otherJobLevel" value="${teacher.otherJobLevel?default('')}">
					</span>
				</td>
				<#assign i = i + 1>
			</#if>
			<#if item.colsCode = "personTel">
				<th>手机号码：</th>
				<td><input name="teacher.personTel" id="teacher.personTel" notNull="true" type="text" msgName="手机号码" maxLength="20" class="input-txt fn-left" style="width:140px;" value="${teacher.getPersonTel()?default('')?trim}"><span class="fn-left c-orange mt-5 ml-10">*</span></td>
			<#assign i = i + 1>
			</#if>
			<#if item.colsCode = "officeTel">
				<th>办公电话：</th>
				<td><input name="teacher.officeTel" id="teacher.officeTel" type="text" msgName="办公电话" maxLength="20" class="input-txt fn-left" style="width:140px;" value="${teacher.getOfficeTel()?default('')?trim}"></td>
			<#assign i = i + 1>
			</#if>
			<#if item.colsCode = "chargeNumber">
				<th>扣费号码：</th>
				<td><input name="teacher.chargeNumber" id="teacher.chargeNumber" msgName="扣费号码" maxLength="20" type="text" class="input-txt fn-left" style="width:140px;" value="${teacher.getChargeNumber()?default('')?trim}" ></td>
			<#assign i = i + 1>
			</#if>
			<#if item.colsCode = "polity">
				<th>政治面貌：</th>
				<td>
		       	<div class="ui-select-box" style="width:150px;">  
	                <input type="text" class="ui-select-txt"  value="" />
	                <input type="hidden" name="teacher.polity" id="teacher.polity" class="ui-select-value" />
	                <a class="ui-select-close"></a>
	                <div class="ui-option">
	                	<div class="a-wrap">
	                    ${appsetting.getMcode("DM-ZZMM").getHtmlTag(teacher.getPolity()?default(''))}
	                	</div>
	                </div>
	           	</div>
		       	</td>
			<#assign i = i + 1>
			</#if>
			<#if item.colsCode = "polityJoin">
				<@common.tdd  name="teacher.polityJoin" id="teacher.polityJoin"
				  value="${(teacher.polityJoin?string('yyyy-MM-dd'))?if_exists}" msgName="加入时间" />
			<#assign i = i + 1>
			</#if>
			<#if item.colsCode = "nation">
				<th>民　　族：</th>
				<td>
	       		<div class="ui-select-box  fn-left" style="width:150px;">  
	                <input type="text" class="ui-select-txt"  msgName="民族"/>
	                <input type="hidden" name="teacher.nation" id="teacher.nation"  class="ui-select-value" />
	                <a class="ui-select-close"></a>
	                <div class="ui-option">
	                	<div class="a-wrap">
	                    ${appsetting.getMcode("DM-MZ").getHtmlTag(teacher.getNation()?default(''))}
	                	</div>
	                </div>
	           	</div>

	       		</td>
			<#assign i = i + 1>
			</#if>
			<#if item.colsCode = "perNative">
				<th>籍　　贯：</th>
				<td><input name="teacher.perNative" id="teacher.perNative" type="text" msgName="籍贯" maxLength="40" class="input-txt fn-left" style="width:140px;" value="${teacher.getPerNative()?default('')?trim}"></td>
			<#assign i = i + 1>
			</#if>
			<#if item.colsCode = "stulive">
				<th>现有学历：</th>
				<td>
				<div class="ui-select-box  fn-left" style="width:150px;">  
	                <input type="text" class="ui-select-txt"   />
	                <input type="hidden" name="teacher.stulive" id="teacher.stulive" class="ui-select-value" />
	                <a class="ui-select-close"></a>
	                <div class="ui-option">
	                	<div class="a-wrap">
	                    ${appsetting.getMcode("DM-XL").getHtmlTag(teacher.getStulive()?default(''))}
	                	</div>
	                </div>
	           	</div>
	           	</td>
			<#assign i = i + 1>
			</#if>
			<#if item.colsCode = "major">
				<th>所学专业：</th>
				<td>
				<div class="ui-select-box" style="width:150px;">  
	                <input type="text" class="ui-select-txt" value=""  />
	                <input type="hidden" name="teacher.major" id="teacher.major" class="ui-select-value" />
	                <a class="ui-select-close"></a>
	                <div class="ui-option">
	                	<div class="a-wrap">
	                    ${appsetting.getMcode("DM-ZYLB").getHtmlTag(teacher.getMajor()?default(''))}
	               		</div>
	                </div>
	           	</div>
	      		</td>
			<#assign i = i + 1>
			</#if>
			<#if item.colsCode = "fsschool">
				<th>毕业院校：</th>
				<td><input name="teacher.fsschool" id="teacher.fsschool" type="text" class="input-txt fn-left" style="width:140px;" msgName="毕业院校" maxLength="40" value="${teacher.getFsschool()?default('')?trim}"></td>
			<#assign i = i + 1>
			</#if>
			<#if item.colsCode = "fstime">
				<@common.tdd  msgName="毕业时间" name="teacher.fstime" id="teacher.fstime" value="${(teacher.fstime?string('yyyy-MM-dd'))?if_exists}"/>
			<#assign i = i + 1>
			</#if>
			<#if item.colsCode = "registertype">
				<th>户口类别：</th>
				<td>
				<div class="ui-select-box  fn-left" style="width:150px;">  
	                <input type="text" class="ui-select-txt"   />
	                <input type="hidden" name="teacher.registertype" id="teacher.registertype" class="ui-select-value" />
	                <a class="ui-select-close"></a>
	                <div class="ui-option">
	                	<div class="a-wrap">
	                    ${appsetting.getMcode("DM-HKXZ").getHtmlTag(teacher.getRegistertype()?default(2)?string)}
	                	</div>
	                </div>
	           	</div>
		       	</td>
			<#assign i = i + 1>
			</#if>
			<#if item.colsCode = "cardNumber">
			      	<th>点到卡号：</th>
			      	<td><input name="teacher.cardNumber" id="teacher.cardNumber" type="text" class="input-txt fn-left" style="width:140px;"  msgName="点到卡号" maxlength="20" size="20" value="${teacher.cardNumber?default('')}"></td>
			<#assign i = i + 1>
			</#if>
			<#if item.colsCode = "register">
					<th>户口所在地：</th>
					<td colspan="1"><input name="teacher.register" id="teacher.register" type="text" msgName="户口所在地" maxlength="50" class="input-txt fn-left" style="width:140px;" value="${teacher.getRegister()?default('')?trim}" ></td>						
			<#assign i = i + 1>
			</#if>
			<#if item.colsCode = "displayOrder">
				<th>排序号：</th>
				<td><input name="teacher.displayOrder" id="teacher.displayOrder" type="text" msgName="排序号" notNull="true"  maxlength="8" dataType="integer" minValue="0" class="input-txt fn-left <#if isDesktop>input-readonly</#if>" style="width:140px;" <#if isDesktop>readonly="true"</#if> value="${teacher.displayOrder?default('')?trim}"><span class="fn-left c-orange mt-5 ml-10">*</span></td>
			<#assign i = i + 1>
			</#if>
			<#if item.colsCode = "address">
					<th>联系地址：</th>
					<td colspan="1"><input name="teacher.linkAddress" id="teacher.address" type="text" class="input-txt fn-left" style="width:140px;" msgName="联系地址" maxlength="60" value="${teacher.linkAddress?default('')?trim}" ></td>
			<#assign i = i + 1>
			</#if>
			<#if item.colsCode = "email">
					<th>电子邮件：</th>
					<td colspan="1"><input name="teacher.email" id="teacher.email" type="text" msgName="电子邮件" regex="/^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/" regexMsg="电子邮件格式不符合校验规则" maxLength="40" class="input-txt fn-left" style="width:140px;" value="${teacher.getEmail()?default('')?trim}" title="建议填写，可用于密码遗忘时的取回"></td>
			<#assign i = i + 1>
			</#if>
			<#if item.colsCode = "homepage">							   
					<th>主页地址：</th>
					<td colspan="1"><input name="teacher.homepage" id="teacher.homepage" type="text" msgName="主页地址" maxLength="40" class="input-txt fn-left" style="width:140px;" value="${teacher.getHomepage()?default('')?trim}" ></td>
			<#assign i = i + 1>
			</#if>
			<#if item.colsCode = "remark">
				<#if i% 2 != 0><td colspan="2"></td></tr><tr height="100px;"><#assign i = i + 1></#if>
				<th>备　　注：</th>
				<td colspan="3">
				<textarea name="teacher.remark" id="teacher.remark" class="text-area" 
				msgName="备注" maxLength="255" style="width:90%; height:80px;">${teacher.getRemark()?default('')?trim}</textarea></td>
			<#assign i = i + 2>
			</#if>
			
			<#if item.colsCode = "workTeachDate">
				<th>参加教育工作年月：</th>
				<td>
			        <@common.datepicker id="workTeachDate1" dateFmt="yyyy-MM" value="${(teacher.workTeachDate?string('yyyy-MM'))?if_exists}" onpicked="onCallBackWorkTeachDate" oncleared="clickMethodWorkTeachDate"/>
			        <input type='hidden' value='${(teacher.workTeachDate?string('yyyy-MM-dd'))?if_exists}' name="teacher.workTeachDate" id="teacher.workTeachDate" >
		        </td>
			<#assign i = i + 1>
			</#if>
			<#if item.colsCode = "oldAcademicQualification">
				<@common.tds msgName="原有学历（全日制）" name="teacher.oldAcademicQualification" notNull="" value="${teacher.oldAcademicQualification?default('')}" readonly="${readonly!}" style="width:150px;">
				    	${appsetting.getMcode("DM-XL").getHtmlTag((teacher.oldAcademicQualification?string)?default(''))}
	    		</@common.tds>
			<#assign i = i + 1>
			</#if>
			<#if item.colsCode = "specTechnicalDuty">
				<th>专业技术职务：</th>
	    		<td colspan="1"><input name="teacher.specTechnicalDuty" id="teacher.specTechnicalDuty" type="text" class="input-txt fn-left" style="width:140px;" msgName="专业技术职务" maxlength="60" value="${teacher.specTechnicalDuty?default('')?trim}" ></td>
			<#assign i = i + 1>
			</#if>
			<#if item.colsCode = "specTechnicalDutyDate">
				<th>专业技术职务评审年月：</th>
				<td>
			        <@common.datepicker id="specTechnicalDutyDate1" dateFmt="yyyy-MM" value="${(teacher.specTechnicalDutyDate?string('yyyy-MM'))?if_exists}" onpicked="onCallBackSpecTechnicalDutyDate" oncleared="clickMethodSpecTechnicalDutyDate"/>
			        <input type='hidden' value='${(teacher.specTechnicalDutyDate?string('yyyy-MM-dd'))?if_exists}' name="teacher.specTechnicalDutyDate" id="teacher.specTechnicalDutyDate" >
		        </td>
			<#assign i = i + 1>
			</#if>
			<#if item.colsCode = "homeAddress">
				<@common.tdi  name="teacher.homeAddress" id="teacher.homeAddress" value="${teacher.homeAddress?default('')}" maxLength="60" msgName="家庭地址"/>
			<#assign i = i + 1>
			</#if>
			<#if item.colsCode = "generalCard">
				<#if isDesktop>
					<@common.tdi  class="input-txt fn-left input-readonly" readonly="true" name="teacher.generalCard" id="teacher.generalCard" value="${teacher.generalCard?default('')}" maxLength="20" msgName="一卡通"/>
				<#else>
					<@common.tdi  name="teacher.generalCard" id="teacher.generalCard" value="${teacher.generalCard?default('')}" maxLength="20" msgName="一卡通"/>
				</#if>
			<#assign i = i + 1>
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
	function addSexBirth(){
		var idcard = document.getElementById("teacher.idcard");
		if(checkIdentityCard(idcard)){
			var idValue = idcard.value;
			var year;
			var month;
			var day;
			var sex;
			var date
			if(idValue.length == 15 ){
				year = "19" + idValue.substring(6, 8);
    		    month = idValue.substring(8, 10);
    		    day = idValue.substring(10, 12)
    		    date = year+"-" + month+"-"+day;
    		    if(idValue.charAt('14') % 2 == 0){
    		    	sex =2;
    		    }else{
    		    	sex =1;
    		    }
			}else if(idValue.length == 18){
				year = idValue.substring(6, 10);
    		    month = idValue.substring(10, 12);
    		    day = idValue.substring(12, 14);
    		    date = year+"-" + month+"-"+day;
    		    if(idValue.charAt('16') % 2 == 0){
    		    	sex =2;
    		    }else{
    		    	sex =1;
    		    }
			}
		
			var birth = document.getElementById("teacher.birthday");
			
			if(birth.value == "" ){
				birth.value = date;
			}
			var sexVal = document.getElementById("teacher.sex");
			var a = document.getElementById("sexOption"); 
			var count = a.childElementCount;
			if(sexVal.value == ""){
				document.getElementById("sexText").value
				sexVal.value = sex;
				for(var i=0;i<count;i++){
					if(a.children[i].getAttribute("val") == sex){
						a.children[i].className ="selected";	
						document.getElementById("sexText").value =a.children[i].innerText;
					}else{
						a.children[i].className ="";
					}
				}
			}
		}
	}
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
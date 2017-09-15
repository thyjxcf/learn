<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="">
<script>
vselect();
//验证表单数据
function checkform(){   
   if(!checkAllValidate()){
   		return;
   }  
   
   //学校代码不大于12位
   var codeLen = jQuery('#code').val().length;
   if(codeLen >12){
   		addFieldError(form1.code,"学校代码长度必须为12，目前的长度为"+codeLen+"位，请更改！");
   		jQuery("#code").focus();
   		return;
   }

   if(!checkDate(form1.datecreated,"建校年月")){
      return;
   }
   
   if(!checkPhone(form1.linkphone,"联系电话")){
      return;
   }
   if(!checkPhone(form1.fax,"传真电话")){
      return;
   }
   if(!checkEmail(form1.email)){
      return;
   }
   if(!checkPostalCode(form1.postalcode)){
      return;
   }
   //学校标识码不大于12位
   var organizationcode = jQuery('#organizationcode').val().length;
   if(organizationcode >9){
   		addFieldError(form1.organizationcode,"学校标识码长度不能超过9，目前的长度为"+codeLen+"位，请更改！");
   		jQuery("#organizationcode").focus();
   		return;
   }
   //组织机构代码,只能为0位或9位
   var elem = form1.organizationcode;
   if(trim(elem.value) != ''){
	   //var pattern = /^[0-9]{8}[0-9xX]{1}$/;
	   //邢台那边要15位的，现在就不做控制了，只要输入字母和数字就行
	 //  var pattern = /^[A-Za-z0-9]+$/;
	 //  if(!pattern.test(elem.value)) {
	//   	 	addFieldError(form1.organizationcode,"组织机构代码长度必须为数字或字母，请更改！");
	  //      elem.focus();
	//        return;
	//   }
   }
   jQuery.ajax({
   		url:"${request.contextPath}/basedata/baseschool/baseSchoolAdmin-save.action",
   		type:"POST",
   		data:jQuery('#form1').serialize(),
   		dataType:"JSON",
   		async:false,
   		success:function(data){
   			if(data.operateSuccess){
   				showMsgSuccess(data.promptMessage,"",function(){
   					load("#container","${request.contextPath}/basedata/baseschool/baseSchoolAdmin.action")
   				});
   			}else{
   				showMsgError(data.errorMessage);
   			}
   		}
   });
}

</script>
<form name="form1" id="form1" action=""  method="post">
<input type="hidden" name="synchroSchDistrict" value="">
<input type="hidden" name="id" value="${id?default('')}">		
<input type="hidden" name="operation" value="save">	
<div class="table-all">
<@htmlmacro.tableDetail name="table" class="table-form">
	<tr class="first" >
	  <th>学校名称：</th>
	  <td ><input name="name" id="name" type="text" class="input-txt input-readonly fn-left"  style="width:150px;"
			maxlength="64" value="${name?default('')?trim}" readonly="true"/><span class="fn-left c-orange mt-5 ml-10">*</span></td>
	  <th>所在地行政区：</th>
	  <td ><input size="20" name="regionname" id="regionname" type="text"
			class="input-txt input-readonly fn-left" style="width:150px;" readonly="true" value="${regionname?default(regionName?default(""))?trim}"/> 
			<input name="region" type="hidden" class="input" value="${region?default(regionId?default(""))}"/> 
	 	<span class="fn-left c-orange mt-5 ml-10">*</span> </td>
	</tr>
	<tr>
		<th>学校代码：</th>
		<td ><input msgName="学校代码" name="code" id="code" type="text" class="input-txt fn-left" style="width:150px;" notNull="true"
			maxlength="12" value="${code?default('')?trim}" fieldtip="学校代码不能超过12位">
		<span class="fn-left c-orange mt-5 ml-10">*</span></td>
		<th>学校简称：</th>
		<td ><input msgName="学校简称" name="shortName" id="shortName" type="text" class="input-txt" style="width:150px;" size="19" maxlength="30" value="${shortName?default('')?trim}"/>
	    </td>
	</tr>
	<tr>
		<th>学校类别：</th>
		<td >	
		<div class="ui-select-box fn-left" style="width:160px;">
                <input msgName="学校类别"  type="text" class="ui-select-txt" value="${typeName!}" notNull="true"/>
                <input name="type"  id="type" type="hidden" class="ui-select-value" value="${typeName!}"/>
                <a class="ui-select-close"></a>
                <div class="ui-option">
                	<div class="a-wrap">
                    <a val=""><span>请选择</span></a>
                    <#list schoolTypes as item>
                    	 <a val="${item.thisId!}" <#if item.thisId?default('') == type!>class="selected"</#if>><span>${item.content!}</span></a>
                    </#list>
                    </div>
                </div>
         </div>
         <span class="fn-left c-orange mt-5 ml-10">*</span>
		<input name="typemessage" id="typemessage" type="hidden" value="${typemessage?default('')}"/>
		</td>
		<th>办学模式：</th>
		<td >
		<div class="ui-select-box fn-left" style="width:160px;">
                <input msgName="办学模式" type="text" class="ui-select-txt" value="${appsetting.getMcode('DM-XXBB').get(runschtype?default(''))}" notNull="true" />
                <input  name="runschtype" id="runschtype" type="hidden"  class="ui-select-value" value=""/>
                <a class="ui-select-close"></a>
                <div class="ui-option">
                	<div class="a-wrap">
               			 ${appsetting.getMcode("DM-XXBB").getHtmlTag(runschtype?default(""))}
                	</div>
                </div>
         </div>
         <span class="fn-left c-orange mt-5 ml-10">*</span>
		</td>
	</tr>
	<tr>
		<th>行业：</th>
		<td><input msgName="行业" name="industry" id="industry" class="input-txt" style="width:150px;" value="${industry?default(industry?default(""))?trim}" maxlength="40"/></td>
		<th>法人：</th>
		<td><input msgName="法人" name="legalPerson" id="legalPerson" class="input-txt" style="width:150px;" value="${legalPerson?default(industry?default(""))?trim}" maxlength="30"/></td>
	</tr>
	<tr>
		<th>学校英文名称：</th>
		<td colspan="3"><input msgName="学校英文名称" name="englishname" id="englishname" type="text"
			class="input-txt" style="width:300px;" size="59" maxlength="180" value="${englishname?default('')?trim}"></td>
	</tr>
	<tr>
		<th>学校地址：</th>
		<td colspan="3"><input msgName="学校地址" name="address" id="address" type="text" class="input-txt" style="width:300px;"
			size="59" maxlength="60" value="${address?default('')?trim}"></td>
	</tr>												
	<tr>
		<th >学校校长：</th>
		<td><input msgName="学校校长" name="shoolmaster" id="shoolmaster" type="text"
			class="input-txt" style="width:150px;" size="19" maxlength="30" value="${shoolmaster?default('')?trim}"></td>
		<th>主页地址：</th>
		<td><input msgName="主页地址" name="homepage" id="homepage" type="text" class="input-txt"  style="width:150px;" maxlength="60" value="${homepage?default('')?trim}" ></td>
	</tr>
	<tr >
		<th>党组织负责人：</th>
		<td ><input msgName="党组织负责人" name="partymaster" id="partymaster" type="text" class="input-txt" style="width:150px;" maxlength="30" value="${partymaster?default('')?trim}" ></td>
		<@htmlmacro.tableDetailDate   style="width:150px;" msgName="建校年月" notNull="false" name="datecreated"   value="${(datecreated?string('yyyy-MM-dd'))?if_exists}" /> 
	</tr>
	<tr>
		<th>校庆日：</th>
		<td ><input msgName="校庆日" name="anniversary" id="anniversary" type="text" class="input-txt" style="width:150px;"
			maxlength="60" value="${anniversary?default('')?trim}"></td>
		<th>重点级别：</th>
		<td>
		<div class="ui-select-box fn-left" style="width:160px;">
                <input  msgName="重点级别" type="text" class="ui-select-txt" value="${appsetting.getMcode('DM-ZDJB').get(emphases?default(''))}"  />
                <input name="emphases" id="emphases" type="hidden" value="" class="ui-select-value"  />
                <a class="ui-select-close"></a>
                <div class="ui-option">
                	<div class="a-wrap">
               			 ${appsetting.getMcode("DM-ZDJB").getHtmlTag(emphases?default(""))}
                	</div>
                </div>
         </div>
		</td>
	</tr>
	<tr>
		<th>学校主管部门：</th>
		<td ><input msgName="学校主管部门" name="governor" id="governor" type="text" class="input-txt" style="width:150px;"
			maxlength="60" value="${governor?default('')?trim}"></td>
		<th>学校标识码：</th>
		<td ><input msgName="学校标识码" name="organizationcode" id="organizationcode" type="text" class="input-txt"  style="width:150px;" maxlength="9" value="${organizationcode?default('')?trim}" fieldtip="学校标识码不能超过12位"></td>
	</tr>
	<tr>
		<th>占地面积（㎡）：</th>
		<td ><input  msgName="占地面积" name="area" id="area" type="text" class="input-txt" style="width:150px;" value="${area?default("")}" dataType="float"  integerLength="8" decimalLength="2" maxlength="20" ></td>
		<th>建筑面积（㎡）：</th>
		<td ><input msgName="建筑面积" name="builtupArea" id="builtupArea" type="text" class="input-txt" style="width:150px;" value="${builtupArea?default("")}" dataType="float"  integerLength="8" decimalLength="2" maxlength="20" ></td>
	</tr>
	<tr>
		<th>绿化面积（㎡）：</th>
		<td><input  msgName="绿化面积" name="greenArea" id="greenArea" type="text" class="input-txt" style="width:150px;" value="${greenArea?default("")}" dataType="float"  integerLength="8" decimalLength="2" maxlength="20" ></td>
		<th>运动场面积（㎡）：</th>
		<td><input msgName="运动场面积" name="sportsAreal" id="sportsAreal" type="text" class="input-txt" style="width:150px;" value="${sportsAreal?default("")}" dataType="float"  integerLength="8" decimalLength="2" maxlength="20" ></td>
	</tr>
	<tr>
		<th>邮政编码：</th>
		<td ><input msgName="邮政编码" name="postalcode" id="postalcode" type="text" class="input-txt" style="width:150px;" maxlength="6" value="${postalcode?default('')?trim}" fieldtip="6位数字"></td>
		<th>联系电话：</th>
		<td ><input msgName="联系电话" name="linkphone" id="linkphone" type="text" class="input-txt" style="width:150px;" maxlength="30" value="${linkphone?default('')?trim}" ></td>
	</tr>
	<tr>
		<th>传真电话：</th>
		<td ><input msgName="传真电话" name="fax" id="fax" type="text" class="input-txt" style="width:150px;" maxlength="30" value="${fax?default('')?trim}" ></td>
		<th>电子信箱：</th>
		<td ><input msgName="电子信箱" name="email" id="email" type="text" class="input-txt" style="width:150px;" maxlength="40" value="${email?default('')?trim}" ></td>
	</tr>
	<tr>
		<th valign="top" class="pt-10">历史沿革：</th>
    	<td class="pt-10" colspan="3"><textarea msgName="历史沿革" class="text-area" style="width:400px;height:80px;" name="introduction" id="introduction" maxlength="4000">${introduction?default('')?trim}</textarea></td>
	</tr>
	<tr>
		<th valign="top" class="pt-10">备注：</th>
    	<td class="pt-10" colspan="3"><textarea msgName="备注" class="text-area" style="width:400px;height:80px;" name="remark" id="remark" maxlength="2000">${remark?default('')?trim}</textarea></td>
	</tr>
	 <tr>
        	<td colspan="5" class="td-opt">
            	<a href="javascript:checkform();" class="abtn-blue-big">保存</a>
            </td>
     </tr>
</@htmlmacro.tableDetail>
</form>
</div>
<script>
jQuery(document).ready(function(){
	$t_c_width=jQuery(".table-content").width();
	$t_c_width=$t_c_width-16;
	jQuery(".table-content").height(jQuery(".mainFrame", window.parent.document).height() - jQuery('.head-tt').height() - jQuery('.table1-bt').height()-5)
	jQuery(".table-header").width($t_c_width);
})
</script>
</@htmlmacro.moduleDiv>


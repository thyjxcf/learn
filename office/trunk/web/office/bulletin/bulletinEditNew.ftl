<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="${bulletinName!}维护">
<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
<script type="text/javascript" charset="utf-8" src="${request.contextPath}/static/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${request.contextPath}/static/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${request.contextPath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/ueditor/themes/default/css/ueditor.css"/>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/ueditor/themes/iframe.css"/>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/ueditor/themes/myUeditor.css"/>
<style type="text/css">
	#edui_fixedlayer{z-index:9999 !important;}
	.sendBulletin-form .edui-default .edui-editor{width:824px !important;}
	body.widescreen .sendBulletin-form .edui-default .edui-editor{width:1024px !important;}
</style>
<script>
	//实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var oInput = document.getElementById("title");
	oInput.focus();
    var ue = UE.getEditor('content',{
        //focus时自动清空初始化时的内容
        autoClearinitialContent:false,
        //关闭字数统计
        wordCount:false,
        //关闭elementPath
        elementPathEnabled:false,
        //默认的编辑区域高度
        initialFrameHeight:300
        //更多其他参数，请参考ueditor.config.js中的配置项
    });
    $("#edui1").css("z-index","90");
    
    //调整全屏功能
    $("#edui3_state").click(function(){
		var fontSize = $(this).attr("class").indexOf("edui-state-checked"); 
		if(fontSize != (-1)){
			$("#edui1").css("z-index","9999");
		}else{
			$("#edui1").css("z-index","90");
		}
	});
    
	var isSubmit = false;
	function saveBulletin(state){
	  	if(isSubmit){
			return;
		}
		$("#state").val(state);
	  	if(!checkAllValidate()){
	  		return;
	  	}
	  	var content = $("textarea[name='bulletin.content']") .val();
		if (content == null || content == "") {
		   showMsgWarn("${bulletinName!}内容不能为空!");
	       return;
		}
		if(state==3){
		//var flag = document.getElementById("pushToUnit").checked;
  		if($("#pushToUnit").attr("checked")){
  			var pushUnitTargetItemId = document.getElementById("pushUnitTargetItemId").value;
  			if(pushUnitTargetItemId==null || pushUnitTargetItemId==''){
  				showMsgWarn("请选择发送到网站的栏目");
  				return;
  			}
  			$("#isCheck").val("1");
  		}
  	}
		<#if officeBulletinType.needSms>
			var needSms = $("#needSms").attr('checked');
			if(needSms && needSms=='checked'){
				var smsContent = $('#smsContent1').val();
				if(smsContent == null || smsContent == "") {
					showMsgWarn("短信内容不能为空!");
	       			return;
				}
				
				if ($("#timing").attr("checked") == "checked") {
			    	var smsTime = $("#smsTime").val();
			    	if (smsTime == null || trim(smsTime) == "") {
					   showMsgWarn("定时发送时间为空!");
				       return;
					}
			    }
			}
		</#if>
		isSubmit = true;
		var bulletinUrl = "${request.contextPath}/office/bulletin/bulletin-saveOrUpdate.action?bulletinType=${bulletinType!}";
		var options = {
          target : '#bulletinform',
          url : bulletinUrl,
          success : showSuccess,
          dataType : 'json',
          clearForm : false,
          resetForm : false,
          type : 'post'
        };
      	$("#bulletinform").ajaxSubmit(options);
		
	}
      
  //操作提示
  function showSuccess(data) {
    if (data!=null && data!=''){
      showMsgError(data);
      isSubmit = false;
      return;
    }else{
        showMsgSuccess("操作成功！", "提示", function(){
		  var url="${request.contextPath}/office/bulletin/bulletin-manageList.action?bulletinType=${bulletinType!}&show=${show!}&searName=${searName!}&publishName=${publishName!}&searchAreaId=${searchAreaId!}";
		  load("#container", url);
		});
		return;
    }
  }
  
  function goBack(){
  	var url="${request.contextPath}/office/bulletin/bulletin-manageList.action?bulletinType=${bulletinType!}&show=${show!}&searName=${searName!}&publishName=${publishName!}&searchAreaId=${searchAreaId!}";
  	load("#container", url);
  }
  <#if officeBulletinType.needSms && !needAudit>
  function clickSms(){
  	var needSms = $("#needSms").attr('checked');
  	var title = $("#title").attr('value');
	if(needSms && needSms=='checked'){
		$('#smsContent').show();
		$('#smsContent1').attr('value','您有一个新公告《'+title+'》，请查阅！【'+'${userName!}-${unitName!}'+'】');
	}else{
		$('#smsContent').hide();
		$('#smsContent1').attr('value','');
	}
  }
  </#if>
  
  function markPushUnit(node){
  if ($(node).hasClass('ui-checkbox-current')) {
    if ($("#pushToUnit").attr("checked") == "checked") {
      $("#pushToUnit").attr("checked", false);
      $("#node").removeClass("ui-checkbox-current");
    }
    $("#pushUnitTargetSelectId").hide();
  }else{
  	$("#pushUnitTargetSelectId").show();
  	var pushUnitTargetSelectId = document.getElementById("pushUnitTargetSelectId");
  	pushUnitTargetSelectId.setAttribute("style", "height:24px;");
  }
}

function changeSelectPushUnitTarget(node){
  document.getElementById("pushUnitTargetItemId").value=node.options[node.selectedIndex].value;
}

function checkTime(timeObj) {
	if($(timeObj).hasClass('ui-checkbox-current')){
		$("#sendTiming").hide();
	}else{
		$("#sendTiming").show();
	}
}
</script>
<form action="" method="post" name="bulletinform" id="bulletinform">
<input type="hidden" id="id" name="bulletin.id" value="${bulletin.id!}">
<input type="hidden" id="createUserId" name="bulletin.createUserId" value="${bulletin.createUserId!}">
<input type="hidden" id="state" name="bulletin.state"/>
<input type="hidden" id="isCheck" value="0" name="isCheck"/>
<!-- 推送到网站 -->
<input type="hidden" id="pushUnitTargetItemId" name="pushUnitTargetItemId" />
<table border="0" cellspacing="0" cellpadding="0" class="table-edit mt-20" style="table-layout:fixed">
		
		<#if bulletinType=="1"&&!xinJiangDeploy&&!jiAnLiuZhongDeploy>
		<tr>
			<th ><span class="c-orange mr-5">*</span>类型：</th>
		        <td >
                <span class="ui-radio ui-radio-noTxt <#if bulletin.type?default("1")=="1">ui-radio-current</#if>" data-name="a">
                <input type="radio" class="radio" <#if bulletin.type?default("1")=="1">checked="checked"</#if> name="bulletin.type" value="1">&nbsp通知</span>&nbsp;&nbsp;
                <#if !xinJiangDeploy>
                <span class="ui-radio ui-radio-noTxt <#if bulletin.type?default("1")=="3">ui-radio-current</#if>" data-name="a" style="margin-left:13px;">
                <input type="radio" class="radio" <#if bulletin.type?default("1")=="3">checked="checked"</#if> name="bulletin.type" value="3">&nbsp公告</span></td>
                </#if>
		</tr>
		</#if>
		
        <tr <#if bulletinType != stack.findValue('@net.zdsoft.office.bulletin.entity.OfficeBulletinType@ZCWJ')>style="display:none;"</#if>>
            <th><span class="c-red">*</span> 所属类型：</th>
            <td>
            	<div class="ui-select-box fn-left" style="width:150px;">
	                <input type="text" class="ui-select-txt" id="scopeName" name="scopeName" msgName="所属类型" <#if bulletinType == stack.findValue('@net.zdsoft.office.bulletin.entity.OfficeBulletinType@ZCWJ')>notNull="true"</#if>/>
	                <input type="hidden"  id="bulletin.scope" name="bulletin.scope" class="ui-select-value" value="${bulletin.scope?default('')}"/>
	                <a class="ui-select-close"></a>
	                <div class="ui-option">
	                	<div class="a-wrap">
	                    ${appsetting.getMcode("DM-ZCWJ").getHtmlTag(bulletin.scope?default(''))}
	                	</div>
	                </div>
            　　　　　　</div>
            </td>
        </tr>
        <tr>
            <th><span class="c-red">*</span> 标&nbsp;&nbsp;&nbsp;&nbsp;题：</th>
            <td>
            	<input type="text" id="title" name="bulletin.title" notNull="true" msgName="标题" class="input-txt" style="width:700px;" maxLength="200" value="${bulletin.title!}">
            </td>
        </tr>
        <tr>
            <th><span class="c-red">*</span> 创建时间：</th>
            <td>
            	<@common.datepicker class="input-txt" style="width:150px;" name="bulletin.createTime" id="createTime" notNull="true" dateFmt="yyyy-MM-dd HH:mm:ss" maxlength="20"
				   msgName="创建时间"  value="${(bulletin.createTime?string('yyyy-MM-dd HH:mm:ss'))!}"/>
	        </td>
        </tr>
        <tr>
            <th><span class="c-red">*</span> 截止时间：</th>
            <td>
            	<@common.select style="width:120px;" valName="bulletin.endType" valId="endType" notNull="true">
					${appsetting.getMcode("DM-JZSJ").getHtmlTag(bulletin.endType?default('2'))}
				</@common.select>
	        </td>
        </tr>
        <#if bulletinType=="2"&&xinJiangDeploy>
        <#else>
        <#if unitType == '3'>
        <tr <#if teachAreaList?size lt 2 || bulletinType == stack.findValue('@net.zdsoft.office.bulletin.entity.OfficeBulletinType@ZCWJ')>style="display:none;"</#if>>
            <th><span class="c-red">*</span> 发布范围：</th>
            <td>
            	<@common.select style="width:120px;" valName="bulletin.areaId" valId="areaId" notNull="true">
					<a val="${stack.findValue('@net.zdsoft.eis.base.constant.BaseConstant@ZERO_GUID')!}" <#if bulletin.areaId == stack.findValue('@net.zdsoft.eis.base.constant.BaseConstant@ZERO_GUID')>class="selected"</#if>>全校</a>
					<#if teachAreaList?exists && teachAreaList?size gt 0>
                		<#list teachAreaList as area>
                			<a val="${area.id!}" <#if bulletin.areaId == area.id>class="selected"</#if>>${area.areaName!}</a>
                		</#list>
                	</#if>
				</@common.select>
            </td>
        </tr>
        <#elseif unitType == '1'>
       		<tr>
            <th><span class="c-red">*</span> 发布范围：</th>
            <td>
            	<@common.select style="width:120px;" valName="bulletin.releaseScope" valId="releaseScope" notNull="true">
                	<a val="${stack.findValue('@net.zdsoft.office.bulletin.entity.OfficeBulletin@SCOPE_ALL_UNIT')!}" <#if bulletin.releaseScope?default('') == stack.findValue('@net.zdsoft.office.bulletin.entity.OfficeBulletin@SCOPE_ALL_UNIT')>class="selected"</#if>>所有单位</a>
                	<a val="${stack.findValue('@net.zdsoft.office.bulletin.entity.OfficeBulletin@SCOPE_SELF_UNIT')!}" <#if bulletin.releaseScope?default('') == stack.findValue('@net.zdsoft.office.bulletin.entity.OfficeBulletin@SCOPE_SELF_UNIT')>class="selected"</#if>>本单位</a>
                	<a val="${stack.findValue('@net.zdsoft.office.bulletin.entity.OfficeBulletin@SCOPE_SELF_AND_SON_UNIT')!}" <#if bulletin.releaseScope?default('') == stack.findValue('@net.zdsoft.office.bulletin.entity.OfficeBulletin@SCOPE_SELF_AND_SON_UNIT')>class="selected"</#if>>本单位及直属单位</a>
				</@common.select>
            </td>
        </tr> 
        <#elseif unitType == '2'>
        	<tr>
            <th><span class="c-red">*</span> 发布范围：</th>
            <td>
            	<@common.select style="width:120px;" valName="bulletin.releaseScope" valId="releaseScope" notNull="true">
                	<a val="${stack.findValue('@net.zdsoft.office.bulletin.entity.OfficeBulletin@SCOPE_SELF_UNIT')!}" <#if bulletin.releaseScope?default('') == stack.findValue('@net.zdsoft.office.bulletin.entity.OfficeBulletin@SCOPE_SELF_UNIT')>class="selected"</#if>>本单位</a>
                	<a val="${stack.findValue('@net.zdsoft.office.bulletin.entity.OfficeBulletin@SCOPE_SELF_AND_SON_UNIT')!}" <#if bulletin.releaseScope?default('') == stack.findValue('@net.zdsoft.office.bulletin.entity.OfficeBulletin@SCOPE_SELF_AND_SON_UNIT')>class="selected"</#if>>本单位及直属单位</a>
				</@common.select>
            </td>
        </tr> 
        </#if>
        </#if>
        <tr style="display:none;">
            <th><span class="c-red">*</span> 排序号(降序)：</th>
            <td>
            	<input type="hidden" msgName="排序号" name="bulletin.orderId" class="input-txt" maxValue="999999999" notNull="true"minValue="0" maxLength="9" dataType="integer"style="width:150px;" value="${bulletin.orderId?default(0)}"/>
	        </td>
        </tr>
        <tr>
        	<th><span class="c-red">*</span> ${bulletinName!}内容：</th>
            <td>
            	<div class="sendBulletin-form">
            		<textarea id="content" name="bulletin.content" type="text/plain" style="width:1024px;height:500px;">${bulletin.content!}</textarea>
	        	</div>
	        </td>
        </tr>
        <#if bulletinType=="3"&&registOff>
        <tr>
          <td>&nbsp;</td>
          <td colspan="2">
                <!-- 推送到单位学校 -->
                <div style="margin-top:15px;margin-left:10px;" <#if homePage?exists && homePage!=''>style="display:block"<#else>style="display:none"</#if>>
                	<span class="ui-checkbox send-sms-btn" onclick="markPushUnit(this);">
        			<input type="checkbox" id="pushToUnit" name="pushToUnit" class="chk" value="true">推送到网站
        			</span>
                   <select id="pushUnitTargetSelectId" name="pushUnitTargetSelectId" style="display: none;" onchange="changeSelectPushUnitTarget(this)">
                       <option value="">选择栏目...</option>
					   <#list pushUnitTargetItems as pushTargetItem>
                       <option value="${pushTargetItem.id}">${pushTargetItem.name}</option>
                       </#list>
                   </select>
                </div>
          </td>
      </tr>
      </#if>
        <#if officeBulletinType.needSms && !needAudit>
        <tr>
        	<th>
        		&nbsp;
        	</th>
        	<td>
        		<span class="ui-checkbox fn-left mt-5 ml-10" myfunclick="clickSms">
        			<input type="checkbox" id="needSms" name="bulletin.needSms" value="true" class="chk"/>
        			是否发送短信
        		</span>
        	</td>
        </tr>
        <tr id="smsContent" style="display:none">
        	<th>
        		短信内容：
        	</th>
        	<td>
        		<input class="input-txt"style="width:700px;" maxLength="200" name="bulletin.smsContent" id="smsContent1" value=""/>
        	</td>
        </tr>
        <tr>
        	<th>
        		&nbsp;
        	</th>
        	<td>
        		<span class="ui-checkbox send-timing-btn fn-left" id="timingSpan" style="margin-left:10px;" onclick="checkTime(this);">
            		<input type="checkbox" id="timing" name="bulletin.timing" value="true" class="chk">定时发送
        		</span>
        	</td>
        </tr>
        <tr id="sendTiming" style="display:none">
        	<th>
        		短信时间：
        	</th>
        	<td>
        		<@common.datepicker class="input-txt" style="width:150px;" name="bulletin.smsTime" id="smsTime" 
				   msgName="短信时间" dateFmt="yyyy-MM-dd HH:mm" maxlength="20"/>
        	</td>
        </tr>
        </#if>
        <tr>
        	<th>&nbsp;</th>
        	<td>
        		<#if bulletin.state?exists && bulletin.state == '3'>
        			<a href="javascript:void(0);" class="abtn-blue" onclick="saveBulletin(3);">保存</a>
        		<#else>
            		<a href="javascript:void(0);" class="abtn-blue" onclick="saveBulletin(1);">保存</a>
	            	<#if needAudit>
	            		<a href="javascript:void(0);" class="abtn-blue" onclick="saveBulletin(2);">提交审核</a>
	            	<#else>
	            		<a href="javascript:void(0);" class="abtn-blue" onclick="saveBulletin(3);">发布</a>
	            	</#if>
            	</#if>
            	<a href="javascript:void(0);" class="abtn-blue" onclick="goBack();">取消</a>
            </td>
        </tr>
    </table>
</form>
</@common.moduleDiv>
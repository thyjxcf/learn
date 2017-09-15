<#import "/common/commonmacro.ftl" as commonmacro>
<p class="tt"><a href="#" class="close">关闭</a><span>纪要维护</span></p>
<form id="minutesForm">
<input type="hidden" id="meetId" name="meetId" value="${meetId!}"/>
<div class="wrap pa-10" id="minutesDetailDiv">
	<p class="dt"><a href="#" class="abtn-orange add" id="addMinutes">添加纪要</a><span class="f16 b">${officeExecutiveMeet.name!}</span></p>
    <#if officeExecutiveMeetMinuteList?exists && officeExecutiveMeetMinuteList?size gt 0>
	    <#list officeExecutiveMeetMinuteList as x>
	    <div class="meet-item">
	    	<p class="tit"><a href="#" class="del"></a><span class="sort">${x_index+1}</span></p>
	    	<p><textarea class="text-area" id="minutesContent1" name="minutesContent" notNull="true" msgName="纪要内容" maxLength="500">${x.content!}</textarea></p>
	        <div class="table-wrap">
	            <table>
	                <tr>
	                    <th><a href="javascript:void(0);" onclick="changeDept(${x_index+1});" class="abtn-blue edit-class">设置查看范围</a></th>
	                    <td>
	                    	<input type="hidden" id="deptIds${x_index+1}" name="deptIds" value="${x.deptIds!}"/>
	                    	<span id="showDeptNames${x_index+1}">${x.deptNames!}</span>
	                	</td>
	                </tr>
	            </table>
	        </div>
	    </div>
	    </#list>
    <#else>
	    <div class="meet-item">
	    	<p class="tit"><a href="#" class="del"></a><span class="sort">1</span></p>
	    	<p><textarea class="text-area" id="minutesContent1" name="minutesContent" notNull="true" msgName="纪要内容" maxLength="500"></textarea></p>
	        <div class="table-wrap">
	            <table>
	                <tr>
	                    <th><a href="javascript:void(0);" onclick="changeDept(1);" class="abtn-blue edit-class">设置查看范围</a></th>
	                    <td>
	                    	<input type="hidden" id="deptIds1" name="deptIds"/>
	                    	<span id="showDeptNames1"></span>
	                	</td>
	                </tr>
	            </table>
	        </div>
	    </div>
    </#if>
</div>
</form>
<p class="dd">
	<a class="abtn-blue" href="javascript:void(0);" onclick="save();"><span>保存</span></a>
	<a class="abtn-blue reset ml-5" href="#"><span>取消</span></a>
</p>
<div id="hiddenCloneDiv" style="display:none;">
	<div class="meet-item">
    	<p class="tit"><a href="#" class="del"></a><span class="sort">999</span></p>
    	<p><textarea class="text-area" id="minutesContent999" name="minutesContent" notNull="true" msgName="纪要内容" maxLength="500"></textarea></p>
        <div class="table-wrap">
            <table>
                <tr>
                    <th><a href="javascript:void(0);" onclick="changeDept(999);" class="abtn-blue edit-class">设置查看范围</a></th>
                    <td>
                    	<input type="hidden" id="deptIds999" name="deptIds"/>
                    	<span id="showDeptNames999"></span>
                	</td>
                </tr>
            </table>
        </div>
    </div>
</div>
<@commonmacro.selectMoreTree idObjectId="deptIdsSelect" nameObjectId="deptNamesSelect"  preset="" treeUrl=request.contextPath+"/common/xtree/deptTree.action?allLinkOpen=false" callback="doDeptsSave"  switchSelector=".edit-class">
	<input type="hidden" name="deptNumber" id="deptNumber">
	<input type="hidden" id="deptIdsSelect" name="deptIdsSelect"/>
	<input type="hidden" id="deptNamesSelect" name="deptNamesSelect"/>
	<a id="pop-class"></a>
</@commonmacro.selectMoreTree>
<script type="text/javascript">
<#if officeExecutiveMeetMinuteList?exists && officeExecutiveMeetMinuteList?size gt 0>
	var minutesNumber = ${officeExecutiveMeetMinuteList?size+1};
<#else>
	var minutesNumber = 2;
</#if>
$(function(){
	$('.schedule-layer-meet .wrap').scroll(function(){
		var scTop=$(this).scrollTop();
		$(this).children('.dt').css('top',scTop);
	});
	$('#addMinutes').click(function(e){
		e.preventDefault();
		$("#minutesDetailDiv").append($('#hiddenCloneDiv').html().replaceAll("999",minutesNumber));
		minutesNumber++;
		flashDelete();
	});
	String.prototype.replaceAll = function(s1,s2) {
	    return this.replace(new RegExp(s1,"gm"),s2);
	}
	function flashDelete(){
		$('.meet-item .del').click(function(e){
			$(this).parent("p").parent("div").remove();
			var scTop=$('.schedule-layer-meet .wrap').scrollTop();
			$('.schedule-layer-meet .wrap .dt').css('top',scTop);
		});
	}
	flashDelete();
});

function changeDept(inputNumber){
	$("#deptNumber").val(inputNumber);
	$("#deptIdsSelect").val($("#deptIds"+inputNumber).val());
	$("#deptNamesSelect").val($("#showDeptNames"+inputNumber).html());
	$("#pop-class").click();
}

function doDeptsSave(){
	var inputNumber = $("#deptNumber").val();
	$("#deptIds"+inputNumber).val($("#deptIdsSelect").val());
	$("#showDeptNames"+inputNumber).html($("#deptNamesSelect").val());
}
var isSubmit = false;
function save(){
    if(isSubmit) {
       return;
    }
	if(!checkAllValidate("#minutesDetailDiv")){
		return;
	}
    var deptIds = document.getElementsByName("deptIds");
    var flag = false;
    for(var i=0;i<deptIds.length-1;i++){
    	if(deptIds[i].value === ''){
    		flag = true;
    		break;
    	}
    }
    if(flag){
    	showMsgWarn("请设置每块纪要对应的查看范围");
    	return;
    }
	isSubmit = true;
	var minutesUrl = "${request.contextPath}/office/executiveMeet/executiveMeet-minutesSave.action";
	var options = {
      target : '#minutesForm',
      url : minutesUrl,
      success : showSuccess,
      dataType : 'json',
      clearForm : false,
      resetForm : false,
      type : 'post'
    };
  	$("#minutesForm").ajaxSubmit(options);
}

//操作提示
function showSuccess(data) {
    if (!data.operateSuccess){
		if(data.errorMessage!=null&&data.errorMessage!=""){
		   showMsgError(data.errorMessage);
		   isSubmit = false;
		   return;
	   }
	}else{
		showMsgSuccess(data.promptMessage,"提示",function(){
		  $("#minutesLayer").hide();
		});
	}
}
</script>
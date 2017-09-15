<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="修改备注">
<style type="text/css">
	.main th{text-align:right;}
	.main th,td{height:30px;}
</style>
<p class="tt"><a href="javascript:void(0);" onclick="closeDiv('#detailLayer');return false;" class="close">关闭</a><span>修改备注</span></p>
<div class="wrap mt-10" id="detailInfoDiv">
    <input type="hidden" name="id" value="${officeWorkArrangeDetail.id!}"/>
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-form">
        <tr>
            <th style="width:20%">&nbsp;备注：</th>
            <td style="width:80%" class="pt-10"><textarea id="remark" name="remark" style="width:360px;height:180px;">${officeWorkArrangeDetail.remark!}</textarea></td>
        </tr>
    </table>
    <p class="dd">
    	<a href="javascript:void(0);" class="abtn-blue submit" onclick="saveDetailInfo();">保存</a>
        <a href="javascript:void(0);" class="abtn-blue reset" onclick="closeDiv('#detailLayer');return false;">取消</a>
    </p>
</div>
<script type="text/javascript">
var isSubmit = false;
function saveDetailInfo(){
   if(isSubmit){
   		return;
   }
   var remark = $("#remark").val();
   var length = _getLength(remark);
   if(length>500){
	   showMsgWarn("备注长度不能大于500个字符!");
       return;
   }
   isSubmit = true;
	$.getJSON("${request.contextPath}/office/weekwork/weekwork-modifyRemark.action", 
		{"detailId":'${detailId!}',"remark":remark},
		function(data){
			if(!data.operateSuccess){
			   if(data.errorMessage!=null&&data.errorMessage!=""){
				   showMsgError(data.errorMessage);
				   isSubmit = false;
				   return;
			   }
			}else{
				showMsgSuccess(data.promptMessage,"提示",function(){
					closeDiv('#detailLayer');
					getAuditList();
				});
				return;
			}
		}
	).error(
		function(XMLHttpRequest, textStatus, errorThrown){
			alert(XMLHttpRequest.status);
		}
	);
}
</script>
</@common.moduleDiv>
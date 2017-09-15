<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.moduleDiv titleName="">
<p class="tt"><a href="javascript:void(0);" class="close">关闭</a><span>短信发送详情</span></p>
<div class="wrap pa-10" id="contentDiv">
<form id="addform" action="" method="post">
    <table border="0" cellspacing="0" cellpadding="0" class="table-form">
    	<input type="hidden" id="id" name="officeSmsInfo.id" value="${officeSmsInfo.id?default('')}">  
	    <tr>
	      	<th style="width:15%">发送时间：</th>
	    	<td style="width:85%">
	    		<input type="text" msgName="发送时间" class="input-txt fn-left input-readonly" readonly id="sendTimeStr" name="officeSmsInfo.sendTimeStr" readonly="readonly" value="${officeSmsInfo.sendTimeStr!}" style="width:180px;">
	    		<span class="c-orange mt-5 ml-10">*</span>
	    	</td>	
	    </tr>
	    <tr>
	      	<@htmlmacro.tdt msgName="短信内容" readonly="true" id="msg" name="officeSmsInfo.msg" style="width:470px;" value="${(officeSmsInfo.msg?default('')?trim)?if_exists}" />
	    </tr>
	    <tr>
	      	<@htmlmacro.tdt msgName="接收成功者" id="successPhone" name="officeSmsInfo.successPhone" style="width:470px;" value="${officeSmsInfo.successPhone!}" />
	    </tr>
	    <tr>
	      	<@htmlmacro.tdt msgName="接收失败者" id="failedPhone" name="officeSmsInfo.failedPhone" style="width:470px;" value="${officeSmsInfo.failedPhone!}" />
	    </tr>
	    <#if officeSmsInfo.noPhone! != "">
		    <tr>
		      	<@htmlmacro.tdt msgName="无电话者" id="noPhone" name="officeSmsInfo.noPhone" style="width:470px;" value="${officeSmsInfo.noPhone!}" />
		    </tr>
	    </#if>
    </table>
</form>    
</div>
<p class="dd">
    <a class="abtn-blue reset ml-5" href="javascript:void(0);">返回</a>
</p>
<script>vselect();</script>
</@htmlmacro.moduleDiv>
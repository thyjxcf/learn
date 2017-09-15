<#import "/common/htmlcomponent.ftl" as common />
<#if officeMsgFolders?exists&&officeMsgFolders?size gt 0>
<#list officeMsgFolders as x>
	<#if type?exists&&type=='folder1'>
   		<a href="javascript:void(0);" onclick="turnToFolder('${x.id!}',3);" title="${x.folderName!}"><@common.cutOff str='${x.folderName!}' length=7/></a>
   	<#elseif type?exists&&type=='folder2'>
   		<a href="javascript:void(0);" onclick="copyToFolder('${x.id!}',3);" title="${x.folderName!}"><@common.cutOff str='${x.folderName!}' length=7/></a>
   	<#elseif type?exists&&type=='folder3'>
   		<a href="javascript:void(0);" onclick="turnToFolder('${x.id!}',3);" title="${x.folderName!}"><@common.cutOff str='${x.folderName!}' length=7/></a>
   	<#elseif type?exists&&type=='folder4'>
   		<a href="javascript:void(0);" onclick="copyToFolder('${x.id!}',3);" title="${x.folderName!}"><@common.cutOff str='${x.folderName!}' length=7/></a>
   	<#elseif type?exists&&type=='folder5'>
   		<a href="javascript:void(0);" onclick="turnSingleToFolder('${msgId!}','${x.id!}','${msgState!}');" title="${x.folderName!}"><@common.cutOff str='${x.folderName!}' length=7/></a>
   	<#elseif type?exists&&type=='folder6'>
   		<a href="javascript:void(0);" onclick="copySingleToFolder('${msgId!}','${x.id!}','${msgState!}');" title="${x.folderName!}"><@common.cutOff str='${x.folderName!}' length=7/></a>
   	<#elseif type?exists&&type=='folder7'>
   		<a href="javascript:void(0);" onclick="turnToFolder('${x.id!}',3);" title="${x.folderName!}"><@common.cutOff str='${x.folderName!}' length=7/></a>
   	<#elseif type?exists&&type=='folder8'>
   		<a href="javascript:void(0);" onclick="copyToFolder('${x.id!}',3);" title="${x.folderName!}"><@common.cutOff str='${x.folderName!}' length=7/></a>
   	<#elseif type?exists&&type=='folder9'>
   		<a href="javascript:void(0);" onclick="turnToFolder('${x.id!}',3);" title="${x.folderName!}"><@common.cutOff str='${x.folderName!}' length=7/></a>
   	<#elseif type?exists&&type=='folder10'>
   		<a href="javascript:void(0);" onclick="copyToFolder('${x.id!}',3);" title="${x.folderName!}"><@common.cutOff str='${x.folderName!}' length=7/></a>
   	</#if>
</#list>
</#if>
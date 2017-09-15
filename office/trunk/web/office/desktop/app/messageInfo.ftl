<#if unReadNum gt 0 >
	<ul class="news-list" >
	<li style="cursor:pointer;" onclick="go2Module('${module.url}','${module.subsystem}','${module.id}','${module.parentid}','${module.name}','desktop');return false;">
		<span style="font-weight:bold;">
				<#if switchName>
              	【内部邮件】您有${unReadNum}条未读邮件
              	<#else>
              	【消息】您有${unReadNum}条未读消息
              	</#if>
		</span>
	</li>
	</ul>
	<p class="more" style="margin-top:-10px;"><a href="javascript:void(0);" onclick="go2Module('${module.url}','${module.subsystem}','${module.id}','${module.parentid}','${module.name}','desktop');return false;">查看更多 </a></p>
<#else>
	<div class="no_data">一有消息，我们将及时为您送达！</div>
</#if>


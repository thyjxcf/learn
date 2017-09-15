<#import "/common/htmlcomponent.ftl" as htmlmacro>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<@htmlmacro.moduleDiv titleName="">
<style type="text/css">
.desk-item-inner .dt-tab .tab font{display:inline-block;background:#ff020a;width:6px;height:6px;border-radius:50%;margin:0 0 8px 2px;}
</style>
<script>
function viewContent(id){
	$("#"+id+"_span").css("font-weight","normal");
	window.open("${request.contextPath}/office/desktop/app/info-viewDetail.action?bulletinId="+id,'','fullscreen,scrollbars,resizable=yes,toolbar=no');
	
	var i=0;
	$("#infolist").find("span").each(function(){
		if(jQuery(this).css("font-weight")==700){
			i++;
		}
	});
	if(i==0){
		$(".dt-tab .tab").find("span.current").children("font").remove();
	}
}
function query1(type){
	var url="${request.contextPath}/office/desktop/app/newslist.action?bulletinType="+type;
	load("#infolist", url);
}
function query2(){
	var url="${request.contextPath}/office/desktop/app/newMessage.action";
	load("#infolist", url);
}
</script>

                <div class="dt-tab fn-clear">
                    <span class="tab">
                    	<#if officeBulletinTypelist?exists && (officeBulletinTypelist?size>0)>
                    		<#list officeBulletinTypelist as typ>
                    			<span <#if typ.type == bulletinType>class="current first"</#if> onclick="query1('${typ.type!}');">${typ.name!}
                    				<#if (typ.type == "1" && bulletin1New) 
                    					|| (typ.type == "2" && bulletin2New)
                    					|| (typ.type == "3" && bulletin3New)
                    					|| (typ.type == "4" && bulletin4New)><font></font></#if></span>
                    		</#list>
                    	</#if>
                    	<#if switchName>
                    	<span id="msgSpan" onclick="query2();">未读邮件<#if msgNew><font></font></#if></span>
                    	<#else>
                    	<span id="msgSpan" onclick="query2();">未读消息<#if msgNew><font></font></#if></span>
                    	</#if>
                        <!-- span class="current first" onclick="query1('1');">通知</span>
                        <span onclick="query1('2');">行事历</span>
                        <span onclick="query1('3');">公告</span>
                        <span onclick="query1('4');">文件</span -->
                    </span>
                </div>
             <div id="infolist">
                <#if officeBulletinList?exists && (officeBulletinList?size>0)>
                <#assign len=officeBulletinList?size>
                <ul class="news-list" >
                <#list officeBulletinList as ent>
                	<#if ent_index lt 10>
                    <li onclick="viewContent('${ent.id!}');" style="cursor:pointer;"><span class="date">${(ent.createTime?string("yy-MM-dd"))!}</span>
                	<span id="${ent.id!}_span" style="font-weight:<#if ent.isRead>normal<#else>bold</#if>;">
                	<#if bulletinType == stack.findValue('@net.zdsoft.office.bulletin.entity.OfficeBulletinType@ZCWJ')>
	        			【${appsetting.getMcode("DM-ZCWJ").get(ent.scope?default('1')?string!)}】<@htmlmacro.cutOff4List str="${ent.title!}" length=20 />
	        		<#else>
                		【${ent.areaName!}】<@htmlmacro.cutOff4List str="${ent.title!}" length=20 />
            		</#if>
            		<#if ent.isNew>
						<img src="${request.contextPath}/static/images/icon/new.png">
					</#if>
					</span>
            		</li>
                    </#if>
                </#list>    
                </ul>
                <#if len gt 10>
                <p class="more" style="margin-top:-10px;"><a href="javascript:void(0);" onclick="go2Module('${module.url}','${module.subsystem}','${module.id}','${module.parentid}','${module.name}','desktop');return false;">查看更多 >></a></p>
                </#if>
                <#else>
                <div class="no_data">一有消息，我们将及时为您送达！</div>
                </#if>
               </div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>            
<#if !officeBulletinList?exists || (officeBulletinList?size == 0)>
<script type="text/javascript">
	$("#msgSpan").click();
</script>
</#if>
</@htmlmacro.moduleDiv>
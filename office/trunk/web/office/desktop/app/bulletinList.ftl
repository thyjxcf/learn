<#import "/common/htmlcomponent.ftl" as htmlmacro>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<@htmlmacro.moduleDiv titleName="">
<script>
function viewContent(id){
	$("#"+id+"_span").css("font-weight","normal");
	window.open("${request.contextPath}/office/desktop/app/info-viewDetail.action?bulletinId="+id,'','fullscreen,scrollbars,resizable=yes,toolbar=no');
}
function query1(type){
	var winWidth=$(window).width();
	var widescreen="Y";
	if(winWidth < 1200){
		widescreen="N";
	}
	var url="${request.contextPath}/office/desktop/app/infolist.action?bulletinType="+type+"&widescreen="+widescreen;
	load("#infolist", url);
}
function query2(){
	var url="${request.contextPath}/office/desktop/app/infoMessage.action";
	load("#infolist", url);
}
</script>

                <div class="dt-tab fn-clear">
                    <span class="tab">
                    	<#if officeBulletinTypelist?exists && (officeBulletinTypelist?size>0)>
                    		<#list officeBulletinTypelist as typ>
                    			<span <#if typ.type == bulletinType>class="current first"</#if> onclick="query1('${typ.type!}');">${typ.name!}</span>
                    		</#list>
                    	</#if>
                        <span onclick="query2();">未读消息</span>
                        <!-- span class="current first" onclick="query1('1');">通知</span>
                        <span onclick="query1('3');">公告</span>
                        <span onclick="query1('4');">文件</span -->
                    </span>
                </div>
             <div id="infolist">
                <#if officeBulletinList?exists && (officeBulletinList?size>0)>
                <#assign len=officeBulletinList?size>
                <ul class="news-list" >
                <#list officeBulletinList as ent>
                	<#if ent_index lt bulletinNum>
                    <li onclick="viewContent('${ent.id!}');" style="cursor:pointer;"><span class="date">${(ent.createTime?string("MM-dd"))!}</span>
                	<span id="${ent.id!}_span" style="font-weight:<#if ent.isRead>normal<#else>bold</#if>;">
                	<#if bulletinType == stack.findValue('@net.zdsoft.office.bulletin.entity.OfficeBulletinType@ZCWJ')>
	        			【${appsetting.getMcode("DM-ZCWJ").get(ent.scope?default('1')?string!)}】<@htmlmacro.cutOff4List str="${ent.title!}" length=13 />
	        		<#else>
                		【${ent.areaName!}】<@htmlmacro.cutOff4List str="${ent.title!}" length=13 />
            		</#if>
            		<#if ent.isNew>
						<img src="${request.contextPath}/static/images/icon/new.png">
					</#if>
					</span>
            		</li>
                    </#if>
                </#list>    
                </ul>
                <#if len gt bulletinNum>
                <p class="more"><a href="javascript:void(0);" onclick="go2Module('${module.url}','${module.subsystem}','${module.id}','${module.parentid}','${module.name}','desktop');return false;">查看更多 >></a></p>
                </#if>
                <#else>
                <div class="no_data">一有消息，我们将及时为您送达！</div>
                </#if>
               </div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>            
</@htmlmacro.moduleDiv>
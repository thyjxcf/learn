<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="">
<div class="pub-tab" id="showOrHidder" style="display:block">
	<ul class="pub-tab-list">
		<li class="current" onclick="showListDiv(1)">我的听课</li>
		<li onclick="showListDiv(2)">听课审核</li>
		<li onclick="showListDiv(3)">听课统计</li>
	</ul>
</div>
<div id="showListDiv"></div>
<script type="text/javascript">
	$(document).ready(function(){
		showListDiv(1);
	});
	function showListDiv(opt){
		switch (opt){ 
			case 1: 
				load("#showListDiv","${request.contextPath}/office/attendLecture/attendLecture-list.action");
				break; 
			case 2: 
				load("#showListDiv","${request.contextPath}/office/attendLecture/attendLecture-auditList.action");
				break; 
			case 3: 
				load("#showListDiv","${request.contextPath}/office/attendLecture/attendLecture-countList.action");
				break; 
			default : 
				break; 
		} 
	}
	
</script>  
</@htmlmacro.moduleDiv>
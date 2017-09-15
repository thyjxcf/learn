	
	function uploadAttachment(index,target){
		if($('#uploadAttFile'+index).val()!=null&&$('#uploadAttFile'+index).val()!=""){
			if(!checkFileSize(target)){
				$('#uploadAttFile'+index).val('');
				return;
			}
			var winWidth = $(window).width();
			var length = 0;
			var nameLength = 0;
			if(winWidth>=1200){
				length = 35;
				nameLength = 370;
			}else{
				length = 27;
				nameLength = 280;
			};
			var fileName =$('#uploadAttFile'+index).val();
			var fileType = fileName.substr(fileName.lastIndexOf(".")+1).toLowerCase();
			var allName = fileName;
			if(fileName.length>length){
			    fileName = fileName.substr(0,length)+'...'; 
			}
			var innerHTML = '<li id="attachmentP'+index+'"><img src="'+_contextPath+'/static/images/icon/file/'+getFileType(fileType)+'">'+
            '<span class="name" style="width:'+nameLength+'px;" title="'+allName+'">'+fileName+'</span><span class="fr"  style="width:50px;">'+
            '<a href="javascript:void(0);"  onclick="doDeleteFile('+index+')">删除</a></span></li>';
            
			$('#upload-spanLi').append(innerHTML);
			$('#uploadAttFile'+index).hide();
			$('#uploadAttFile'+index).removeClass("current");
			var num = parseInt(index)+1;
			$('#uploadAttFile'+index).after('<input style="display:none" class="current" id="uploadAttFile'+num+'" name="uploadAttFile" hidefocus type="file" onchange="uploadAttachment('+num+',this);"  value="" >');
			resetFileInit();
			resetFilePos(num);
		}
 	}
	
	function getFileType(fileType){
	    var typeName='other.png';
	    if(fileType=='pdf'){typeName='pdf.png';} 
	    if(fileType=='doc'||fileType=='docx'){typeName='word.png';}
	    if(fileType=='ppt'||fileType=='pptx'){typeName='ppt.png';}
		if(fileType=='mp4'||fileType=='avi'||fileType=='mov'){typeName='move.png';}
		if(fileType=='txt'){typeName='txt.png';}
		if(fileType=='png'||fileType=='jpg'||fileType=='jpeg'||fileType=='bmp'||fileType=='gif'){typeName='jpg.png';}
		if(fileType=='csv'){typeName='csv.png';}
		if(fileType=='mp3'||fileType=='mav'){typeName='music.png';}
		if(fileType=='rtf'){typeName='rtf.png';}
		if(fileType=='xls'||fileType=='xlsx'){typeName='xls.png';}
		return typeName;
	}
	
	
	function doDownload(url){
		document.getElementById('downloadFrame').src=url;
	}
	
	//TODO
	function doDeleteAtt(num,attId){
		if(confirm("确认要删除该附件?")){
			$('#attP'+num).remove();
			var innerHtml = $('#remove_att').html() + '<input style="display:none" name="removeAttachment" type="text" value="'+attId+'">';
			$('#remove_att').html(innerHtml);
		}
		resetFilePosCur();
	}
	
	function doDeleteFile(num){
		$('#attachmentP'+num).remove();
		$('#uploadAttFile'+num).remove();
		resetFilePosCur();
	}
	
	function resetFileInit(){
		if($(".upload-span").offset()){
			resetFilePosCur();
		}
	}
	
	function initShowAtt(){
		if($(".att_show ").size()>0){
			var attId = $(".att_show ").get(0).getAttribute('dataId');
			initViewAttachment(attId);
		}
	}
	
	//手动初始化input的位置
	function resetFilePos(num){
		$("#uploadAttFile"+num).css({"position":"absolute","-moz-opacity":"0","opacity":"0","filter":"alpha(opacity=0)","width":$("a.upload-span").width()+27,"height":$(".upload-span").height(),"cursor":"pointer"});
		$("#uploadAttFile"+num).offset({"left":$(".upload-span").offset().left});		
		$("#uploadAttFile"+num).css({"display":""});
		$("#uploadAttFile"+num).offset({"top":$(".upload-span").offset().top });
	}
	//初始话当前的input的位置
	function resetFilePosCur(){
		$(".attDiv .current").css({"position":"absolute","-moz-opacity":"0","opacity":"0","filter":"alpha(opacity=0)","width":$("a.upload-span").width()+27,"height":$(".upload-span").height(),"cursor":"pointer"});
		$(".attDiv .current").offset({"left":$(".upload-span").offset().left});		
		$(".attDiv .current").css({"display":""});
		$(".attDiv .current").offset({"top":$(".upload-span").offset().top});
	}
	
	function checkWb(){
	  if(navigator.userAgent.indexOf("MSIE")>0) { 
	      return  true;
	  } 
	  if(navigator.userAgent.indexOf("Trident")>0) { 
	      return true;
	  } 
	}

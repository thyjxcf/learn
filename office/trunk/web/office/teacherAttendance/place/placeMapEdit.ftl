<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="">
<link rel="stylesheet" href="http://cache.amap.com/lbs/static/main.css?v=1.0"/>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/public.css"/>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/layout.css"/>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/default.css"/>
<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=6fd7d85198f4e5c9019383cb17399b7f"></script>
<style type="text/css">
body {
    font-size: 12px;
}
#tip {
    background-color: #ddf;
    color: #333;
    border: 1px solid silver;
    box-shadow: 3px 4px 3px 0px silver;
    position: absolute;
    top: 10px;
    right: 10px;
    border-radius: 5px;
    overflow: hidden;
    line-height: 20px;
}
#tip input[type="text"] {
    height: 25px;
    border: 0;
    padding-left: 5px;
    width: 280px;
    border-radius: 3px;
    outline: none;
}
</style>
<div id="mapContainer" style="width:700px; height:500px"></div>
<div id="tip">
    <input type="text" id="keyword" name="keyword" value="请输入关键字：(选定后搜索)" onfocus='this.value=""'/>
</div>

<script type="text/javascript" src="${request.contextPath}/static/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/validate.js"></script>
<script type="text/javascript">
var mapName = '${place.mapName!}';
var address = '${place.address!}';
var latitude = '${place.latitude!}';
var longitude = '${place.longitude!}';
	
//加载地图
var windowsArr = [];
var marker;
var infoWindow = new AMap.InfoWindow();

var map = new AMap.Map("mapContainer", {
        resizeEnable: true,
        //center: [116.397428, 39.90923],//地图中心点---注释掉（默认当前城市）
        zoom: 13,//地图显示的缩放级别
        keyboardEnable: false
});

//工具条、比例尺控件
AMap.plugin(['AMap.ToolBar','AMap.Scale'],
    function(){
        map.addControl(new AMap.ToolBar());

        map.addControl(new AMap.Scale());

      //  map.addControl(new AMap.OverView({isOpen:true}));//鹰眼小地图
});

//地图加载完毕
AMap.event.addListener(map, 'complete', function(){
	//若已有坐标点，则添加一个marker
	if(!isBlank(mapName) && !isBlank(latitude) && !isBlank(longitude)){
		//AMap.LngLat(longitude , latitude)
		map.setCenter([longitude , latitude]);
		addMarker(mapName, address, longitude, latitude, 1)
	}
});

AMap.event.addListener(map, 'complete', function(){
	//若已有坐标点，则添加一个marker
	if(!isBlank(mapName) && !isBlank(latitude) && !isBlank(longitude)){
		//AMap.LngLat(longitude , latitude)
		map.setCenter([longitude , latitude]);
		addMarker(mapName, address, longitude, latitude, 1)
	}
});

//逆向编码
<#--
AMap.plugin(['AMap.Geocoder'], function(){
	var geocoder = new AMap.Geocoder({
            radius: 1000,
            extensions: "all"
    });
	//地图添加点击事件
	AMap.event.addListener(map, 'click', function(e){
		//逆向编码
		geocoder.getAddress([e.lnglat.lng , e.lnglat.lat], function(status, result) {
            if (status === 'complete' && result.info === 'OK') {
            	var name = result.regeocode.building;
            	var address = result.regeocode.formattedAddress;
            	var lng = e.lnglat.lng;
            	var lat = e.lnglat.lat;
            	//building有可能为空
            	if(isBlank(name) || 'undefined' == name){
            		name = '';
            	}
                addMarker(name, address, lng, lat, 0);
            }
        });  
	});
});
-->

//自动搜索
AMap.plugin(['AMap.Autocomplete','AMap.PlaceSearch'],function(){
      var autoOptions = {
        city: "全国", //城市，默认全国
        input: "keyword"//使用联想输入的input的id
      };
      
      autocomplete= new AMap.Autocomplete(autoOptions);
      
  
 	  
 	  var placeSearch = new AMap.PlaceSearch({
            city:'010',
            map:map
      })
 	  
 	//  $('#keyword').bind('keyup', function(event) {
	//		if (event.keyCode == "13") {
	//			//回车执行查询
	//			placeSearch.search("杭州电子商务大厦");
	//		}
	//	});
 	  
 	  //监听搜索回车事件
		
      
      AMap.event.addListener(autocomplete, "select", function(e){
        	//TODO 针对选中的poi实现自己的功能
		    placeSearch.search(e.poi.name,function(status, result){
				//TODO : 按照自己需求处理查询结果
				if (status === 'complete' && result.info === 'OK') {
			    	//TODO : 解析返回结果,如果设置了map和panel，api将帮助完成点标注和列表
			    	var pois = result.poiList.pois;
			    	//遍历添加窗体信息
			    	addClickListener(pois);
			    }
			});
      });
});

function addClickListener(pois){
	//清楚所有的marker
	map.clearMap();
	for(var i = 0; i < pois.length; i++){
		var name = pois[i].name;//名称
		var address = pois[i].address;//地址
		var lat = pois[i].location.lat;//纬度
		var lng = pois[i].location.lng;//经度
		
	    addMarker(name, address, lng, lat, 0);
	}
} 

//type  是否不显示设置办公地点按钮 1则不显示
function addMarker(name, address, lng, lat, type){
		marker=new AMap.Marker({
       		position : new AMap.LngLat(lng,lat),
       		title : name,//鼠标划过时文字提示
       		icon : '',
	        map : map
		});
		
		var htmlStr = '';
		if(!isBlank(name)){
			htmlStr = '名称：'+name+'</br>';
		} 
		htmlStr += '地址：'+address+'</br></br>';
		
		if(1 != type){
			htmlStr += '<div class="" style="text-align:center;">';
			htmlStr += '	<a href="" onclick="doSave(\''+name+'\',\''+address+'\',\''+lat+'\',\''+lng+'\');" class="abtn-blue">设置为办公地点</a>';
			htmlStr += '</div>';
		}else{
			//marker.setIcon('${contextPath!}/office/teacherAttendance/images/officeplace.png');
		}
		
		marker.content = htmlStr;
		
		AMap.event.addListener(marker, "click", function(e){
    		infoWindow.setContent(e.target.content);
    		infoWindow.open(map, e.target.getPosition());
    		map.setCenter(e.target.getPosition()); // 设置地图的中心点
    	});
    	
    	 //自动触发
        //AMap.event.trigger(marker, 'click');
}

function doSave(name,address,lat,lng){
	window.parent.document.getElementById("closeBtn").click();
	window.parent.doSetMapData(name,address,lat,lng);
}
</script>
<script type="text/javascript" src="http://webapi.amap.com/demos/js/liteToolbar.js"></script>
</@htmlmacro.moduleDiv>
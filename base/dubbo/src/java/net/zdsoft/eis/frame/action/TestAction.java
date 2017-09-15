package net.zdsoft.eis.frame.action;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class TestAction extends BaseAction{
	
	private static final long serialVersionUID = 1L;

	private String jsonStringChart[];
	
	public String basetest(){
		jsonStringChart=new String[6];
		
		JSONObject json=new JSONObject();
		//柱状图 OR 折线图数据
		json.put("text", "柱状图 OR 折线图");//主标题	可不填写
		json.put("subtext", "柱状图 OR 折线图副标题");//副标题	可不填写
		json.put("legendData", new String[]{"百度","谷歌","必应","其他"});
//		json.put("stackData", new String[]{"搜索引擎","搜索引擎","搜索引擎",""});//用于堆叠效果，index对应legendData，名称相同的堆叠在一起，不用堆叠可不填写
//		json.put("yMax", 400);//y轴最大值	可不填写
//		json.put("yInterval", 40);//分隔断值	可不填写
		json.put("xAxisData", new String[]{"周一","周二","周三","周四","周五","周六","周日"});
		//loadingData数据顺序和xAxisData相对
		json.put("loadingData", new Integer[][]{{620, 732, 701, 734, 1090, 1130, 1120}
		,{120, 132, 101, 134, 290, 230, 220},{60, 72, 71, 74, 190, 130, 110},{62, 82, 91, 84, 109, 110, 120}});
		//注：Integer类型也可为Double
		jsonStringChart[0]=json.toString();
		
		//柱状图-堆叠
		json.clear();
		json.put("text", "柱状图堆叠图");//主标题	可不填写
		json.put("subtext", "柱状图堆叠图副标题");//副标题	可不填写
		json.put("legendData", new String[]{"百度","谷歌","必应","其他"});
		json.put("stackData", new String[]{"搜索引擎","搜索引擎","搜索引擎",""});//用于堆叠效果，index对应legendData，名称相同的堆叠在一起，不用堆叠可不填写可不填写
//		json.put("yMax", 400);//y轴最大值	可不填写
//		json.put("yInterval", 40);//分隔断值	可不填写
		json.put("xAxisData", new String[]{"周一","周二","周三","周四","周五","周六","周日"});
		//loadingData数据顺序和xAxisData相对
		json.put("loadingData", new Integer[][]{{620, 732, 701, 734, 1090, 1130, 1120}
		,{120, 132, 101, 134, 290, 230, 220},{60, 72, 71, 74, 190, 130, 110},{62, 82, 91, 84, 109, 110, 120}});
		//注：Integer类型也可为Double
		jsonStringChart[5]=json.toString();
		
		//饼图数据
		json.clear();
		json.put("text", "饼图");//主标题	可不填写
		json.put("subtext", "饼图副标题");//副标题	可不填写
		json.put("legendData", new String[]{"直接访问","邮件营销","联盟广告","aaa","bbb","ccc"});
		JSONArray jsonArr=new JSONArray();
		JSONObject json2=new JSONObject();
		json2.put("value", 335);
		json2.put("name", "直接访问");
		jsonArr.add(json2);
		json2=new JSONObject();
		json2.put("value", 310);
		json2.put("name", "邮件营销");
		jsonArr.add(json2);
		json2=new JSONObject();
		json2.put("value", 234);
		json2.put("name", "联盟广告");
		jsonArr.add(json2);
		json2=new JSONObject();
		json2.put("value", 234);
		json2.put("name", "aaa");
		jsonArr.add(json2);
		json2=new JSONObject();
		json2.put("value", 234);
		json2.put("name", "bbb");
		jsonArr.add(json2);
		json2=new JSONObject();
		json2.put("value", 234);
		json2.put("name", "ccc");
		jsonArr.add(json2);
		json.put("loadingData", jsonArr);
		jsonStringChart[1]=json.toString();
		
		//散点图数据
		json.clear();
		json.put("text", "散点图");//主标题	可不填写
		json.put("subtext", "散点图副标题");//副标题	可不填写
		json.put("xName", "身高");//x轴名称
		json.put("yName", "体重");//y轴名称
		json.put("legendData", new String[]{"女性","男性"});
		jsonArr=new JSONArray();
		JSONArray jsonArr2=new JSONArray();
		
//		jsonArr2.add(new String[][]{{"161.2", "51.6"}, {"167.5", "59.0"}, {"159.5", "49.2"}, {"157.0", "63.0"}, {"155.8", "53.6"},
//				{"169.5", "67.3"}, {"160.0", "75.5"}, {"172.7", "68.2"}, {"162.6", "61.4"}, {"157.5", "76.8"}});
//		JSONArray jsonArr3=new JSONArray();
//		jsonArr3.add(new String[][]{{"174.0", "65.6"}, {"175.3", "71.8"}, {"193.5", "80.7"}, {"186.5", "72.6"}, {"187.2", "78.8"},
//				{"180.3", "73.2"}, {"167.6", "76.3"}, {"183.0", "65.9"}, {"183.0", "90.9"}, {"179.1", "89.1"}});
		
		jsonArr2.add(new String[][]{{"161.2", "51.6","A1"}, {"167.5", "59.0","A2"}, {"159.5", "49.2","A3"}, {"157.0", "63.0","A4"}, {"155.8", "53.6","A5"},
				{"169.5", "67.3","A6"}, {"160.0", "75.5","A7"}, {"172.7", "68.2","A8"}, {"162.6", "61.4","A9"}, {"157.5", "76.8","A10"}});
		JSONArray jsonArr3=new JSONArray();
		jsonArr3.add(new String[][]{{"174.0", "65.6","B1"}, {"175.3", "71.8","B2"}, {"193.5", "80.7","B3"}, {"186.5", "72.6","B4"}, {"187.2", "78.8","B5"},
				{"180.3", "73.2","B6"}, {"167.6", "76.3","B7"}, {"183.0", "65.9","B8"}, {"183.0", "90.9","B9"}, {"179.1", "89.1","B10"}});
		
		jsonArr.add(jsonArr2);
		jsonArr.add(jsonArr3);
		json.put("loadingData", jsonArr);
		//数据标线-适用于单图例及只有一组数据，若不需要则不用写下面内容
		json2=new JSONObject();
		json2.put("name", "重点线");
		json2.put("value", 90);
		json.put("horizontalLine", json2);//水平线，则需要设置x轴最小值和最大值
		json.put("xMin", 100);//x轴最小值
		json.put("xMax", 200);//x轴最大值
		json2=new JSONObject();
		json2.put("name", "基线");
		json2.put("value", 180);
		json.put("verticalLine", json2);//垂直线，则需要设置y轴最小值和最大值
		json.put("yMin", 10);//y轴最小值
		json.put("yMax", 100);//y轴最大值
		jsonStringChart[2]=json.toString();
		
		//雷达图
		json.clear();
		json.put("text", "雷达图");//主标题	可不填写
		json.put("subtext", "雷达图副标题");//副标题	可不填写
		json.put("legendData", new String[]{"预算分配","分配额度"});
		jsonArr = new JSONArray();
		json2 = new JSONObject();
		json2.put("text", "语文");
		json2.put("max", 100);
		jsonArr.add(json2);
		json2 = new JSONObject();
		json2.put("text", "英语");
		json2.put("max", 100);
		jsonArr.add(json2);
		json2 = new JSONObject();
		json2.put("text", "数学");
		json2.put("max", 100);
		jsonArr.add(json2);
		json2 = new JSONObject();
		json2.put("text", "政治");
		json2.put("max", 100);
		jsonArr.add(json2);
		json2 = new JSONObject();
		json2.put("text", "历史");
		json2.put("max", 100);
		jsonArr.add(json2);
		json2 = new JSONObject();
		json2.put("text", "aaa");
		json2.put("max", 100);
		jsonArr.add(json2);
		json.put("polarIndicator", jsonArr);
		jsonArr=new JSONArray();
		json2=new JSONObject();
		json2.put("name", "预算分配");//该值与legendData匹配
		json2.put("value", new Double[]{43.2, 53.5, 60.0, 70.0, 82.0,30.5});
		jsonArr.add(json2);
		json2=new JSONObject();
		json2.put("name", "分配额度");//该值与legendData匹配
		json2.put("value", new Double[]{50.2, 60.5, 25.0, 78.0, 40.0,40.2});
		jsonArr.add(json2);
		json.put("loadingData", jsonArr);
		jsonStringChart[3]=json.toString();
		
		//仪表盘-单一
		json.clear();
		json.put("text", "仪表盘-单一");//主标题	可不填写
		json.put("subtext", "仪表盘-单一副标题");//副标题	可不填写
		json2=new JSONObject();
		json2.put("min", 20);//可不写默认为0
		json2.put("max", 50);//可不写默认为100
		json2.put("name", "数量");
		json2.put("value", "30");
		json.put("loadingData", json2);
		jsonStringChart[4]=json.toString();
		
		return SUCCESS;
	}

	public String[] getJsonStringChart() {
		return jsonStringChart;
	}

	public void setJsonStringChart(String[] jsonStringChart) {
		this.jsonStringChart = jsonStringChart;
	}
	
}

package net.zdsoft.eis.system.desktop.action;

import java.math.BigDecimal;

import net.zdsoft.eis.frame.action.BaseAction;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 图表相关
 * @author
 *
 */
public class EchartsCharts extends BaseAction{

	private static final long serialVersionUID = 1L;
	
	private String jsonStringCharts[][];
	
	public String execute(){
		return SUCCESS;
	}
	
	public String desktopAll(){
		jsonStringCharts=new String[20][10];
		JSONObject jsonObject=new JSONObject();
		JSONArray jsonArr22=new JSONArray();
		JSONObject json22=new JSONObject();
		String[] xAxisDatas=null;
		Integer[][] loadingDataInt=null;
		Double[][] loadingDataDou=null;
		String[] legendDatas=null;
		BigDecimal db =null;
		int index=-1;
		//教师数据
		jsonObject.clear();
		legendDatas=new String[]{"数量"};
		xAxisDatas=new String[]{"博士","硕士","本科","专科","中专","高中","初中","小学"};
		loadingDataInt=new Integer[1][xAxisDatas.length];
		for(int i=0;i<xAxisDatas.length;i++){
			loadingDataInt[0][i]=(int) (Math.random() * 5000);
		}
//		jsonObject.put("text", "教师最高学历统计图");//主标题	可不填写
		jsonObject.put("xAxisData", xAxisDatas);
		jsonObject.put("legendData", legendDatas);
		//loadingData数据顺序和xAxisData相对
		jsonObject.put("loadingData", loadingDataInt);
		jsonStringCharts[++index][0]=jsonObject.toJSONString();
		System.out.println("index:"+index+"，教师最高学历统计图--柱状图");
		jsonObject.clear();
//		jsonObject.put("text", "教师最高学历统计图");//主标题	可不填写
		jsonObject.put("legendData", xAxisDatas);
		for(int i=0;i<xAxisDatas.length;i++){
			json22=new JSONObject();
			json22.put("value", loadingDataInt[0][i]);
			json22.put("name", xAxisDatas[i]);
			jsonArr22.add(json22);
		}
		jsonObject.put("loadingData", jsonArr22);
		jsonStringCharts[++index][0]=jsonObject.toJSONString();
		System.out.println("index:"+index+"，教师最高学历统计图--饼图");
		
		jsonObject.clear();
		legendDatas=new String[]{"数量"};
		xAxisDatas=new String[]{"20岁以下","20岁至30岁","30岁至40岁","40岁至50岁","50岁至55岁","55岁以上"};
		loadingDataInt=new Integer[1][xAxisDatas.length];
		for(int i=0;i<xAxisDatas.length;i++){
			loadingDataInt[0][i]=(int) (Math.random() * 5000);
		}
//		jsonObject.put("text", "教师年龄阶段统计图");//主标题	可不填写
		jsonObject.put("xAxisData", xAxisDatas);
		jsonObject.put("legendData", legendDatas);
		//loadingData数据顺序和xAxisData相对
		jsonObject.put("loadingData", loadingDataInt);
		jsonStringCharts[++index][0]=jsonObject.toJSONString();
		System.out.println("index:"+index+"，教师年龄阶段统计图--柱状图");
		jsonObject.clear();
//		jsonObject.put("text", "教师年龄阶段统计图");//主标题	可不填写
		jsonObject.put("legendData", xAxisDatas);
		jsonArr22=new JSONArray();
		json22=new JSONObject();
		for(int i=0;i<xAxisDatas.length;i++){
			json22=new JSONObject();
			json22.put("value", loadingDataInt[0][i]);
			json22.put("name", xAxisDatas[i]);
			jsonArr22.add(json22);
		}
		jsonObject.put("loadingData", jsonArr22);
		jsonStringCharts[++index][0]=jsonObject.toJSONString();
		System.out.println("index:"+index+"，教师年龄阶段统计图--饼图");
		jsonObject.clear();
//		jsonObject.put("text", "教师年龄阶段统计图");//主标题	可不填写
		jsonObject.put("legendData", new String[]{"数量"});
		jsonArr22 = new JSONArray();
		json22 = new JSONObject();
		for(int i=0;i<xAxisDatas.length;i++){
			json22 = new JSONObject();
			json22.put("text", xAxisDatas[i]);
			json22.put("max", 5000);
			jsonArr22.add(json22);
		}
		jsonObject.put("polarIndicator", jsonArr22);
		jsonArr22=new JSONArray();
		json22=new JSONObject();
		json22.put("name", "数量");//该值与legendData匹配
		Integer[] intLin=new Integer[xAxisDatas.length];
		for(int i=0;i<xAxisDatas.length;i++){
			intLin[i]=(int) (Math.random() * 5000);
		}
		json22.put("value", intLin);
		jsonArr22.add(json22);
		jsonObject.put("loadingData", jsonArr22);
		jsonStringCharts[++index][0]=jsonObject.toJSONString();
		System.out.println("index:"+index+"，教师年龄阶段统计图--雷达图");
		
		//学生数据
		jsonObject.clear();
		legendDatas=new String[]{"男生数","女生数","总人数"};
		xAxisDatas=new String[]{"学前教育","小学","初中","高中","九年一贯制","高职","高等教育","其他教育类学校"};
		loadingDataDou=new Double[3][xAxisDatas.length];
		BigDecimal b1;
		BigDecimal b2;
		for(int j=0;j<xAxisDatas.length;j++){
			db = new BigDecimal(Math.random() * 50); 
			double d1=Double.valueOf(db.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			db = new BigDecimal(Math.random() * 50); 
			double d2=Double.valueOf(db.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			loadingDataDou[0][j]=d1;
			loadingDataDou[1][j]=d2;
			b1 = new BigDecimal(Double.toString(d1));
			b2 = new BigDecimal(Double.toString(d2));
			loadingDataDou[2][j]=Double.valueOf(b1.add(b2).doubleValue());
		}
//		jsonObject.put("text", "学生性别统计图");//主标题	可不填写
		jsonObject.put("xAxisData", xAxisDatas);
		jsonObject.put("legendData", legendDatas);
		//loadingData数据顺序和xAxisData相对
		jsonObject.put("loadingData", loadingDataDou);
		jsonStringCharts[++index][0]=jsonObject.toJSONString();
		System.out.println("index:"+index+"，学生性别统计图--柱状图");
		
		jsonObject.clear();
		legendDatas=new String[]{"数量"};
		xAxisDatas=new String[]{"农业家庭户口","非农业家庭户口","非农业集体户口"};
		loadingDataDou=new Double[1][xAxisDatas.length];
		for(int i=0;i<xAxisDatas.length;i++){
			db = new BigDecimal(Math.random() * 50); 
			loadingDataDou[0][i]=Double.valueOf(db.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		}
//		jsonObject.put("text", "学生户口类别统计图");//主标题	可不填写
		jsonObject.put("xAxisData", xAxisDatas);
		jsonObject.put("legendData", legendDatas);
		//loadingData数据顺序和xAxisData相对
		jsonObject.put("loadingData", loadingDataDou);
		jsonStringCharts[++index][0]=jsonObject.toJSONString();
		System.out.println("index:"+index+"，学生户口类别统计图--柱状图");
		jsonObject.clear();
//		jsonObject.put("text", "学生户口类别统计图");//主标题	可不填写
		jsonObject.put("legendData", xAxisDatas);
		jsonArr22=new JSONArray();
		json22=new JSONObject();
		for(int i=0;i<xAxisDatas.length;i++){
			json22=new JSONObject();
			json22.put("value", loadingDataDou[0][i]);
			json22.put("name", xAxisDatas[i]);
			jsonArr22.add(json22);
		}
		jsonObject.put("loadingData", jsonArr22);
		jsonStringCharts[++index][0]=jsonObject.toJSONString();
		System.out.println("index:"+index+"，学生户口类别统计图--饼图");
		
		jsonObject.clear();
		legendDatas=new String[]{"数量"};
		xAxisDatas=new String[]{"普通学生","随班就读学生","工读学生","其他"};
		loadingDataDou=new Double[1][xAxisDatas.length];
		for(int i=0;i<xAxisDatas.length;i++){
			db = new BigDecimal(Math.random() * 50); 
			loadingDataDou[0][i]=Double.valueOf(db.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		}
//		jsonObject.put("text", "学生类别统计图");//主标题	可不填写
		jsonObject.put("xAxisData", xAxisDatas);
		jsonObject.put("legendData", legendDatas);
		//loadingData数据顺序和xAxisData相对
		jsonObject.put("loadingData", loadingDataDou);
		jsonStringCharts[++index][0]=jsonObject.toJSONString();
		System.out.println("index:"+index+"，学生类别统计图--柱状图");
		jsonObject.clear();
//		jsonObject.put("text", "学生类别统计图");//主标题	可不填写
		jsonObject.put("legendData", xAxisDatas);
		jsonArr22=new JSONArray();
		json22=new JSONObject();
		for(int i=0;i<xAxisDatas.length;i++){
			json22=new JSONObject();
			json22.put("value", loadingDataDou[0][i]);
			json22.put("name", xAxisDatas[i]);
			jsonArr22.add(json22);
		}
		jsonObject.put("loadingData", jsonArr22);
		jsonStringCharts[++index][0]=jsonObject.toJSONString();
		System.out.println("index:"+index+"，学生类别统计图--饼图");
		
		//资源数据
		jsonObject.clear();
		legendDatas=new String[]{"数量"};
		xAxisDatas=new String[]{"2016.1.1","2016.1.2","2016.1.3","2016.1.4","2016.1.5","2016.1.6"};
		loadingDataDou=new Double[4][xAxisDatas.length];
		for(int j=0;j<xAxisDatas.length;j++){
			db = new BigDecimal(Math.random() * 50); 
			double d1=Double.valueOf(db.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			double d2=Double.valueOf(db.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			double d3=Double.valueOf(db.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			loadingDataDou[0][j]=d1;
			loadingDataDou[1][j]=d2;
			loadingDataDou[2][j]=d3;
			loadingDataDou[3][j]=d1+d2+d3;
		}
//		jsonObject.put("text", "资源日上传量统计图");//主标题	可不填写
		jsonObject.put("xAxisData", xAxisDatas);
		jsonObject.put("legendData", legendDatas);
		//loadingData数据顺序和xAxisData相对
		jsonObject.put("loadingData", loadingDataInt);
		jsonStringCharts[++index][0]=jsonObject.toJSONString();
		System.out.println("index:"+index+"，资源日上传量统计图--折线图");
		
		jsonObject.clear();
//		jsonObject.put("text", "资源来源统计图");//主标题	可不填写
		jsonObject.put("legendData", new String[]{"学习平台","其他"});
		jsonArr22=new JSONArray();
		json22=new JSONObject();
		json22.put("value", 88);
		json22.put("name", "学习平台");
		jsonArr22.add(json22);
		json22=new JSONObject();
		json22.put("value", 12);
		json22.put("name", "其他");
		jsonArr22.add(json22);
		jsonObject.put("loadingData", jsonArr22);
		jsonStringCharts[++index][0]=jsonObject.toString();
		System.out.println("index:"+index+"，资源来源统计图--饼图");
		
		jsonObject.clear();
		legendDatas=new String[]{"数量"};
		xAxisDatas=new String[]{"教案","课件","习题","学案","资源包","实录","微客","素材"};
		loadingDataInt=new Integer[1][xAxisDatas.length];
		for(int i=0;i<xAxisDatas.length;i++){
			loadingDataInt[0][i]=(int) (Math.random() * 20);
		}
//		jsonObject.put("text", "资源种类统计图");//主标题	可不填写
		jsonObject.put("xAxisData", xAxisDatas);
		jsonObject.put("legendData", legendDatas);
		//loadingData数据顺序和xAxisData相对
		jsonObject.put("loadingData", loadingDataInt);
		jsonStringCharts[++index][0]=jsonObject.toJSONString();
		System.out.println("index:"+index+"，资源种类统计图--折线图xy反转");
		
		jsonObject.clear();
		legendDatas=new String[]{"数量"};
		xAxisDatas=new String[]{"语文","数学","英语","物理","化学","生物","政治","历史"};
		loadingDataInt=new Integer[1][xAxisDatas.length];
		for(int i=0;i<xAxisDatas.length;i++){
			loadingDataInt[0][i]=(int) (Math.random() * 20);
		}
//		jsonObject.put("text", "学科资源统计图");//主标题	可不填写
		jsonObject.put("xAxisData", xAxisDatas);
		jsonObject.put("legendData", legendDatas);
		//loadingData数据顺序和xAxisData相对
		jsonObject.put("loadingData", loadingDataInt);
		jsonStringCharts[++index][0]=jsonObject.toJSONString();
		System.out.println("index:"+index+"，学科资源统计图--折线图面积");
		
		//其他
		++index;
		jsonObject.clear();
//		jsonObject.put("text", "资源上传每分钟的数量");//主标题	可不填写
		json22=new JSONObject();
		json22.put("min", 0);//可不写默认为0
		json22.put("max", 30);//可不写默认为100
		json22.put("name", "数量");
		jsonObject.put("loadingData", json22);
		for(int q=0;q<10;q++){
			json22.put("value", (int) (Math.random() * 20));
			jsonStringCharts[index][q]=jsonObject.toString();
		}
		System.out.println("index:"+index+"，资源上传每分钟的数量--仪表盘10组数据动态");
		
		++index;
		jsonObject.clear();
		legendDatas=new String[]{"病假数","事假数","其他"};
		xAxisDatas=new String[]{"2016.1.1","2016.1.2","2016.1.3","2016.1.4","2016.1.5","2016.1.6"};
//		jsonObject.put("text", "考勤数据");//主标题	可不填写
		jsonObject.put("xAxisData", xAxisDatas);
		jsonObject.put("legendData", legendDatas);
		loadingDataInt=new Integer[legendDatas.length][xAxisDatas.length];
		for(int i=0;i<xAxisDatas.length;i++){
			for(int j=0;j<legendDatas.length;j++){
				loadingDataInt[j][i]=(int) (Math.random() * 500);
			}
		}
		//loadingData数据顺序和xAxisData相对
		jsonObject.put("loadingData", loadingDataInt);
		jsonStringCharts[index][0]=jsonObject.toJSONString();
		System.out.println("index:"+index+"，考勤数据--折线图曲线10组数据动态");
		
		++index;
		jsonObject.clear();
		legendDatas=new String[]{"线内调整","退休","辞退","其他"};
		xAxisDatas=new String[]{"2016.1.1","2016.1.2","2016.1.3","2016.1.4","2016.1.5","2016.1.6"};
//		jsonObject.put("text", "教师异动数据");//主标题	可不填写
		jsonObject.put("xAxisData", xAxisDatas);
		jsonObject.put("legendData", legendDatas);
		loadingDataInt=new Integer[legendDatas.length][xAxisDatas.length];
		for(int i=0;i<xAxisDatas.length;i++){
			for(int j=0;j<legendDatas.length;j++){
				loadingDataInt[j][i]=(int) (Math.random() * 500);
			}
		}
		jsonObject.put("loadingData", loadingDataInt);
		jsonStringCharts[index][0]=jsonObject.toJSONString();
		System.out.println("index:"+index+"，教师异动数据--折线图曲线10组数据动态");
		
		++index;
		jsonObject.clear();
		legendDatas=new String[]{"休学","退学","复学","降级","跳级","其他"};
		xAxisDatas=new String[]{"2016.1.1","2016.1.2","2016.1.3","2016.1.4","2016.1.5","2016.1.6"};
//		jsonObject.put("text", "学生异动数据");//主标题	可不填写
		jsonObject.put("xAxisData", xAxisDatas);
		jsonObject.put("legendData", legendDatas);
		loadingDataInt=new Integer[legendDatas.length][xAxisDatas.length];
		for(int i=0;i<xAxisDatas.length;i++){
			for(int j=0;j<legendDatas.length;j++){
				loadingDataInt[j][i]=(int) (Math.random() * 500);
			}
		}
		jsonObject.put("loadingData", loadingDataInt);
		jsonStringCharts[index][0]=jsonObject.toJSONString();
		System.out.println("index:"+index+"，学生异动数据--折线图曲线10组数据动态");
		return SUCCESS;
	}
	
	public String externalAppHistogram(){
		//教师统计图--柱状图
		jsonStringCharts=new String[1][1];
		JSONObject jsonObject=new JSONObject();
		String[] xAxisDatas=null;
		Integer[][] loadingDataInt=null;
		String[] legendDatas=null;
		legendDatas=new String[]{"人数"};
		xAxisDatas=new String[]{"学前教育","小学","初中","高中","九年一贯制","高职","高等教育","其他学校"};
		loadingDataInt=new Integer[1][xAxisDatas.length];
		int con = 0;
		for(int j=0;j<xAxisDatas.length;j++){
			int num=(int) (Math.random() * 100);
			loadingDataInt[0][j]=num;
			con+=num;
		}
		jsonObject.put("text", "教师统计图");//主标题	可不填写
		jsonObject.put("subtext", "共"+con+"人");//副标题	可不填写
		jsonObject.put("xAxisData", xAxisDatas);
		jsonObject.put("legendData", legendDatas);
		//loadingData数据顺序和xAxisData相对
		jsonObject.put("loadingData", loadingDataInt);
		jsonStringCharts[0][0]=jsonObject.toJSONString();
		
		return SUCCESS;
	}
	
	public String externalAppLine(){
		//毕业生升学率变化情况--折线图
		jsonStringCharts=new String[1][1];
		JSONObject jsonObject=new JSONObject();
		String[] xAxisDatas=null;
		Double[][] loadingDataDou=null;
		String[] legendDatas=null;
		BigDecimal db=null;
		legendDatas=new String[]{"升学率"};
		xAxisDatas=new String[]{"2006","2007","2008","2009","2010","2011","2012","2013","2014","2015"};
		loadingDataDou=new Double[1][xAxisDatas.length];
		for(int j=0;j<xAxisDatas.length;j++){
			db = new BigDecimal(Math.random() * 100); 
			loadingDataDou[0][j]=Double.valueOf(db.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		}
		jsonObject.put("xAxisData", xAxisDatas);
		jsonObject.put("legendData", legendDatas);
		//loadingData数据顺序和xAxisData相对
		jsonObject.put("loadingData", loadingDataDou);
		jsonStringCharts[0][0]=jsonObject.toJSONString();
		
		return SUCCESS;
	}
	
	public String externalAppPie(){
		//学生综合统计--饼图
		jsonStringCharts=new String[3][1];
		JSONObject jsonObject=new JSONObject();
		JSONArray jsonArr22=new JSONArray();
		JSONObject json22=new JSONObject();
		String[] xAxisDatas=null;
		Integer[][] loadingDataInt=null;
		int con = 0;
		jsonObject.clear();
		xAxisDatas=new String[]{"农业家庭户口","非农业家庭户口","非农业集体户口"};
		loadingDataInt=new Integer[1][xAxisDatas.length];
		for(int i=0;i<xAxisDatas.length;i++){
			int num=(int) (Math.random() * 5000);
			loadingDataInt[0][i]=num;
			con+=num;
		}
		jsonObject.put("text", "学生户口类别分布图"+"（共"+con+"人）");//主标题	可不填写
		jsonObject.put("legendData", xAxisDatas);
		jsonArr22=new JSONArray();
		json22=new JSONObject();
		for(int i=0;i<xAxisDatas.length;i++){
			json22=new JSONObject();
			json22.put("value", loadingDataInt[0][i]);
			json22.put("name", xAxisDatas[i]);
			jsonArr22.add(json22);
		}
		jsonObject.put("loadingData", jsonArr22);
		jsonStringCharts[0][0]=jsonObject.toJSONString();
		
		con = 0;
		jsonObject.clear();
		xAxisDatas=new String[]{"普通学生","随班就读学生","工读学生","其他"};
		loadingDataInt=new Integer[1][xAxisDatas.length];
		for(int i=0;i<xAxisDatas.length;i++){
			int num=(int) (Math.random() * 5000);
			loadingDataInt[0][i]=num;
			con+=num;
		}
		jsonObject.put("text", "学生类别分布图"+"（共"+con+"人）");//主标题	可不填写
		jsonObject.put("legendData", xAxisDatas);
		jsonArr22=new JSONArray();
		json22=new JSONObject();
		for(int i=0;i<xAxisDatas.length;i++){
			json22=new JSONObject();
			json22.put("value", loadingDataInt[0][i]);
			json22.put("name", xAxisDatas[i]);
			jsonArr22.add(json22);
		}
		jsonObject.put("loadingData", jsonArr22);
		jsonStringCharts[1][0]=jsonObject.toJSONString();
		
		con = 0;
		jsonObject.clear();
		xAxisDatas=new String[]{"男生","女生"};
		loadingDataInt=new Integer[1][xAxisDatas.length];
		for(int i=0;i<xAxisDatas.length;i++){
			int num=(int) (Math.random() * 20000);
			loadingDataInt[0][i]=num;
			con+=num;
		}
		jsonObject.put("text", "学生性别分布图"+"（共"+con+"人）");//主标题	可不填写
		jsonObject.put("legendData", xAxisDatas);
		jsonArr22=new JSONArray();
		json22=new JSONObject();
		for(int i=0;i<xAxisDatas.length;i++){
			json22=new JSONObject();
			json22.put("value", loadingDataInt[0][i]);
			json22.put("name", xAxisDatas[i]);
			jsonArr22.add(json22);
		}
		jsonObject.put("loadingData", jsonArr22);
		jsonStringCharts[2][0]=jsonObject.toJSONString();
		
		return SUCCESS;
	}
	
	public String externalAppRadar(){
		//教师年龄统计图--雷达
		jsonStringCharts=new String[1][1];
		JSONObject jsonObject=new JSONObject();
		JSONArray jsonArr22=new JSONArray();
		JSONObject json22=new JSONObject();
		String[] xAxisDatas=null;
		int con = 0;
		jsonObject.clear();
		xAxisDatas=new String[]{"20岁以下","20岁至30岁","30岁至40岁","40岁至50岁","50岁至55岁","55岁以上"};
		jsonObject.put("legendData", new String[]{"人数"});
		jsonArr22 = new JSONArray();
		json22 = new JSONObject();
		for(int i=0;i<xAxisDatas.length;i++){
			json22 = new JSONObject();
			json22.put("text", xAxisDatas[i]);
			json22.put("max", 1000);
			jsonArr22.add(json22);
		}
		jsonObject.put("polarIndicator", jsonArr22);
		jsonArr22=new JSONArray();
		json22=new JSONObject();
		json22.put("name", "人数");//该值与legendData匹配
		Integer[] intLin=new Integer[xAxisDatas.length];
		for(int i=0;i<xAxisDatas.length;i++){
			int num=(int) (Math.random() * 1000);
			intLin[i]=num;
			con+=num;
		}
		jsonObject.put("text", "教师年龄阶段统计图");//主标题	可不填写
		json22.put("value", intLin);
		jsonArr22.add(json22);
		jsonObject.put("loadingData", jsonArr22);
		jsonStringCharts[0][0]=jsonObject.toJSONString();
		
		return SUCCESS;
	}
	
	public String[][] getJsonStringCharts() {
		return jsonStringCharts;
	}

	public void setJsonStringCharts(String[][] jsonStringCharts) {
		this.jsonStringCharts = jsonStringCharts;
	}
}

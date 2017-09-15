package net.zdsoft.office.dataAnalysis.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author  lufeng 
 * @version 创建时间：2017-4-17 下午09:15:34 
 * 类说明 
 */
public class BaseConstants {
	
	public static final String ZERO_GUID="00000000000000000000000000000000";
	public static final String ONE_GUID= "11111111111111111111111111111111";
	public static final String DATA_ANALYSIS_TIME_FRAME_FIRST_HALF_YEAR="0";
	public static final String DATA_ANALYSIS_TIME_FRAME_LAST_HALF_YEAR="1";
	public static final Map<String,String> timeFrameMap = new HashMap<String, String>();
	static{
		timeFrameMap.put("0","上半年度");
		timeFrameMap.put("1","下半年度");
	}
}

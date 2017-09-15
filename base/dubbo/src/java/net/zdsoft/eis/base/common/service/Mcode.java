package net.zdsoft.eis.base.common.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.Mcodedetail;


/* 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author Dongzk
 * @since 1.0
 * @version $Id: Mcode.java,v 1.4 2007/03/15 12:12:46 zhaosf Exp $
 */
public interface Mcode {
	/**
	 * 得到此微代码的类型名称，如：DM-YDLB
	 * @return String
	 */
	public String getName();

	/**
	 * 根据微代码的明细项代号，得到明细项名称，若不存在返回null
	 * @param num 微代码明细项代码，不能为空
	 * @return String
	 */
	public String get(String num);

	/**
	 * 根据微代码明细项代号，得到明细项名称，如果不存在，返回缺省值。
	 * @param num 微代码明细代号，不能为空。
	 * @param defaults 微代码明细项名称缺省值。
	 * @return String 此明细项代号对应的名称，如果不存在，返回缺省值。
	 */
	public String get(String num, String defaults);

	/**
	 * 根据微代码明细项内容（名称），得到明细项代号值，如果不存在，返回 null
	 * 注意：此方法效率很低，请谨慎使用。
	 * @param content 明细项名称。
	 * @return String 代表此内容的微代码
	 */
	public String getCode(String content);

	/**
	 * 根据微代码明细项内容（名称），得到明细项代号值，如果不存在，返回缺省值
	 * 注意：此方法效率很低，请谨慎使用。建议使用McodeService中的getMcodeAarray()方法实现同样功能
	 * @param content 微代码名称。
	 * @param defaults 缺省值
	 * @return String 微代码。如果不存在，返回缺省值。
	 */
	public String getCode(String content, String defaults);

	/**
	 * 得到微代码明细项thisid和context的Map结构，代码、内容都是String类型。
	 * 注意：此方法效率低下，请谨慎使用。
	 * @return Map 此代码表的Map结构。
	 */
	public Map<String, String> getCodeMap();

	/**
	 * 返回系统内的内定的微代码
	 * @return Map
	 */
	public Map<String, String> getSystemCodeMap();

	/**
	 * 生成一份此微代码明细项的Html Select标记的内容
	 * 如：<option value='01'>留级</option><option value='02'>升级</option>....
	 * 注意：生成的部分不包括Select标记的头和尾，即不包括"<select name=..."和"</select>"。
	 * 生成的"<option>"包含一个空值，此方法等价于getHtmlTag(true);
	 * @return String Select标记的内容。
	 */
	public String getHtmlTag();
	
	/**
	 * 生成一份此微代码明细项的Html Select标记的内容
	 * 如：<option value='01'>留级</option><option value='02'>升级</option>....
	 * 注意：生成的部分不包括Select标记的头和尾，即不包括"<select name=..."和"</select>"。
	 * 生成的"<option>"包含一个空值，此方法等价于getHtmlTag(true);
	 * @return String Select标记的内容。
	 */
	public String getHtmlTag(int displayType);

	/**
	 * 生成一份此微代码明细项的Html Select 标记内容。
	 * 根据参事决定是否自动生成一个空值"<option value=''></option>"
	 * @param neednull 是否需要一个空值。如真，生成空值，否则不生成。
	 * @return String Select标记内容。
	 */
	public String getHtmlTag(boolean neednull);
	
	/**
	 * 生成一份此微代码明细项的Html Select 标记内容。
	 * 根据参事决定是否自动生成一个空值"<option value=''></option>"
	 * @param neednull 是否需要一个空值。如真，生成空值，否则不生成。
	 * @return String Select标记内容。
	 */
	public String getHtmlTag(boolean neednull, int displayType);

	/**
	 * 生成一份此微代码明细项的Html Select标记内容，显示此记录为选中状态。
	 * 与getHtmlTag()方法类似。
	 * 此方法也会生成一个空值，等价于getHtmlTag(String nowvalue,true);
	 * @param nowvalue 现在的代码值。
	 * @return Select 标记内容。
	 */
	public String getHtmlTag(String nowvalue);
	/**
	 * 生成一份此微代码明细项的Html Select标记内容，显示此记录为选中状态。
	 * 与getHtmlTag()方法类似。
	 * 此方法也会生成一个空值，等价于getHtmlTag(String nowvalue,true);
	 * @param nowvalue 现在的代码值。
	 * @return Select 标记内容。
	 */
	public String getHtmlTag(String nowvalue, int displayType);

	/**
	 * 生成一份此微代码明细项的Html checkbox或radio标记内容，显示此记录为选中状态。
	 * @param nowvalue 现在的代码值
	 * @param name 
	 * @param br 每个INPUT域之间的间隔符号 <br>或&nbsp;
	 * @return <input type="tagType" name="name" value="?">nowvalue <br>
	 * 
	 * @author ludq 2007-11-16
	 */
	
	public String getRadioTag(String nowvalue,String name,String id, String br);
	
	/**
	 * 生成一份此微代码明细项的Html checkbox标记内容，显示此记录为选中状态,可多选。
	 * @param nowvalue 现在的代码值,中间用“,”隔开
	 * @param name 
	 * @param br 每个INPUT域之间的间隔符号 <br>或&nbsp;
	 * @return <input type="checkbox" name="name" value="?">nowvalue <br>
	 * 
	 * @author chens 2013-10-31
	 */
	
	public String getCheckboxTag(String nowvalue,String name,String id, String br);
	/**
	 * (包含js事件)生成一份此微代码明细项的Html checkbox或radio标记内容，显示此记录为选中状态。
	 * @param nowvalue 现在的代码值
	 * @param name 
	 * @param br 每个INPUT域之间的间隔符号 <br>或&nbsp;
	 * @return <input type="tagType" name="name" value="?" onclick="">nowvalue <br>
	 * 
	 * @author ludq 2007-11-16
	 */
	
	public String getRadioTag(String nowvalue,String name,String id, String br,String js);

	/**
	 * 生成一份此微代码明细项的Html Select标记内容，显示此记录为选中状态。
	 * 
	 * @param nowvalue
	 *            目前的代码值。
	 * @param neednull
	 *            是否需要一个空值。
	 * @return Select 标记内容。
	 */
	public String getHtmlTag(String nowvalue, boolean neednull);
	/**
	 * 生成一份此微代码明细项的Html Select标记内容，显示此记录为选中状态。
	 * 
	 * @param nowvalue
	 *            目前的代码值。
	 * @param neednull
	 *            是否需要一个空值。
	 * @return Select 标记内容。
	 */
	public String getHtmlTag(String nowvalue, boolean neednull, int displayType);

	/**
	 * 取明细列表
	 * @return
	 */
	public List<Mcodedetail> getMcodeDetailList();
}




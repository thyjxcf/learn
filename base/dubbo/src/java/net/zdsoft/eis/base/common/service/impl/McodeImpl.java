package net.zdsoft.eis.base.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.mongodb.util.Hash;

import net.zdsoft.eis.base.common.entity.Mcodedetail;
import net.zdsoft.eis.base.common.service.Mcode;

/* 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author Dongzk
 * @since 1.0
 * @version $Id: McodeImpl.java,v 1.7 2007/03/15 12:12:46 zhaosf Exp $
 */
public class McodeImpl implements Mcode {
	private String mcodeId; // 微代码类型，如：DM-YDLB

	private Map<String, Mcodedetail> mcodedetailMap; // 微代码明细项

	/**
	 * 构造方法，由微代码类型构造
	 * 
	 * @param mcodeId
	 *            微代码类型
	 */
	public McodeImpl(String mcodeId, Map<String, Mcodedetail> mcodedetailMap) {
		this.mcodeId = mcodeId;
		this.mcodedetailMap = mcodedetailMap;
	}

	public String get(String num) {
		return get(num, null);
	}

	public String get(String num, String defaults) {
		Mcodedetail mcode = (Mcodedetail) mcodedetailMap.get(num);
		if (mcode == null) {
			return "";
		}
		return mcode.getContent();
	}

	public String getCode(String content) {
		return getCode(content, null);
	}

	public String getCode(String content, String defaults) {
		for (Iterator<String> it = mcodedetailMap.keySet().iterator(); it
				.hasNext();) {
			Object obj = it.next();
			Mcodedetail mcode = (Mcodedetail) mcodedetailMap.get(obj);
			if (mcode.getContent().equals(content)) {
				return mcode.getThisId();
			}
		}
		return defaults;
	}

	public Map<String, String> getCodeMap() {
		Mcodedetail mcode = null;
		Map<String, String> linkMap = new LinkedHashMap<String, String>();
		for (Iterator<String> it = mcodedetailMap.keySet().iterator(); it
				.hasNext();) {
			Object key = it.next();
			mcode = (Mcodedetail) mcodedetailMap.get(key);
			String[] s = new String[] { mcode.getThisId(), mcode.getContent() };
			linkMap.put(s[0], s[1]);
		}
		return linkMap;
	}

    public String getHtmlTag() {
        return getTag(null, true, 2);
    }
    
    public String getHtmlTag(int displayType) {
        return getTag(null, true, displayType);
    }

    public String getHtmlTag(boolean neednull) {
        return getTag(null, neednull, 2);
    }
    public String getHtmlTag(boolean neednull, int displayType) {
        return getTag(null, neednull, displayType);
    }

    public String getHtmlTag(String nowvalue) {
        return getTag(nowvalue, true, 2);
    }
    public String getHtmlTag(String nowvalue, int displayType) {
        return getTag(nowvalue, true, displayType);
    }

	public String getRadioTag(String nowvalue, String name, String id,
			String attribute) {
		return getRaidoHtml(nowvalue, name, id, attribute, "");
	}

	public String getCheckboxTag(String nowvalue, String name, String id,
			String attribute) {
		return getCheckBoxHtml(nowvalue, name, id, attribute, "");
	}

	/**
	 * 
	 * @param nowvalue
	 *            当前值
	 * @param name
	 * @param id
	 * @param br
	 *            如果需要换行 维护成"<br>
	 *            " 否则维护成""
	 * @param js
	 *            如果点击有js 需要传js方法；(这个参数需要的话就传 不需要的话就不传)
	 * @return
	 */
	public String getRadioTag(String nowvalue, String name, String id,
			String attribute, String js) {
		return getRaidoHtml(nowvalue, name, id, attribute, js);
	}

	private String getCheckBoxHtml(String nowvalue, String name, String id,
			String attribute, String js) {
		StringBuffer sb = new StringBuffer(150);
		Mcodedetail mcode = null;
		String[] values = nowvalue.split(",");
		int i = 0;
		for (Iterator<String> it = mcodedetailMap.keySet().iterator(); it
				.hasNext();) {
			Object key = it.next();
			mcode = (Mcodedetail) mcodedetailMap.get(key);
			Object value = mcode.getContent();
			sb.append("<input type='checkbox'").append(StringUtils.isBlank(attribute) ? "" : attribute).append(" name='").append(name)
					.append("' id='").append(id + key).append("' value='")
					.append(key.toString().trim());
			if (!"".equals(js))
				sb.append("' onclick='").append(js);
			boolean flag = false;
			if (nowvalue != null) {
				for (String str : values) {
					if (str.trim().equals(key.toString().trim())) {
						flag = true;
						break;
					}
				}
				if (flag) {
					sb.append("' checked>");
				} else
					sb.append("'>");
			} else
				sb.append("'>");
			sb.append("<label for='" + (id + key) + "'>" + value + "</label>");
			i++;
		}
		return sb.toString();
	}

    public String getHtmlTag(String nowvalue, boolean neednull) {
        return getTag(nowvalue, neednull, 2);
    }
	private String getRaidoHtml(String nowvalue, String name,
			String id, String attribute, String js) {
		StringBuffer sb = new StringBuffer(150);
		Mcodedetail mcode = null;
		int i = 0;
		sb.append("<span class='ui-radio-box'>");
		for (Iterator<String> it = mcodedetailMap.keySet().iterator(); it
				.hasNext();) {
			Object key = it.next();
			mcode = (Mcodedetail) mcodedetailMap.get(key);
			Object value = mcode.getContent();
			sb.append("<span class='ui-radio");
			if (nowvalue != null
					&& nowvalue.trim().equals(key.toString().trim()))
				sb.append(" ui-radio-current'");
			else
				sb.append("'");
			sb.append(" data-name='").append(name).append("'>");
			sb.append("<input type='").append("radio' ").append(StringUtils.isBlank(attribute) ? "" : attribute).append(" class='")
					.append("radio").append("' name='").append(name)
					.append("' id='").append(id + key).append("' value='")
					.append(key.toString().trim());
			if (!"".equals(js))
				sb.append("' onclick='").append(js);
			if (nowvalue != null
					&& nowvalue.trim().equals(key.toString().trim()))
				sb.append("' checked>");
			else
				sb.append("'>");
			sb.append("<label for='" + (id + key) + "'>" + value + "</label>");
			sb.append("</span>");
			i++;
		}
		sb.append("</span>");
		return sb.toString();
	}
    
    public String getHtmlTag(String nowvalue, boolean neednull, int displayType) {
        return getTag(nowvalue, neednull, displayType);
    }

    private void dealDetail() {
        boolean all = true;
        for (Mcodedetail detail : mcodedetailMap.values()) {
            if (detail.getLevel() == 0)
                all = false;
            if (StringUtils.isBlank(detail.getParentThisId())) {
                detail.setLevel(1);
            }
            else {
                Mcodedetail d = mcodedetailMap.get(detail.getParentThisId());
                int level = 0;
                if(d != null) {
                    d.setFolder(true);
                    level = d.getLevel();
                }
                if (level != 0) {
                    detail.setLevel(level + 1);
                }
            }
        }
        if(!all) {
            dealDetail();
        }
    }

    /**
     * 组合成HTML标筌语句
     * @param nowvalue
     * @param neednull
     * @param displayType 1 = 普通下拉框, 2 = 模拟下拉框
     * @return
     */
    private String getTag(String nowvalue, boolean neednull, int displayType) {
        StringBuffer sb = new StringBuffer(150);
        if (displayType == 1) {
            if (neednull)
                sb.append("<option height='30' value=''>--请选择(不限)--</option>");
            Mcodedetail mcode = null;
            dealDetail();
            for (Iterator<String> it = mcodedetailMap.keySet().iterator(); it.hasNext();) {
                Object key = it.next();
                mcode = (Mcodedetail) mcodedetailMap.get(key);
                Object value = mcode.getContent();
                sb.append("<option value='").append(key.toString().trim()).append("'");
                sb.append(" title='").append(value).append("'");
                if(mcode.isFolder()) {
                    sb.append(" disabled='disabled' style='color:rgb(98, 117, 255);'");
                }
                if (nowvalue != null && nowvalue.trim().equals(key.toString().trim()))
                    sb.append(" selected>");
                else
                    sb.append(">");
                int level = mcode.getLevel();
                String s = value.toString();
                if(mcode.isFolder()) {
                    s = StringUtils.leftPad("", 2 * (level - 1), "　") + "【" + s + "】";
                }
                else {
                    s = StringUtils.leftPad("", 2 * (level - 1), "　") + s;
                }
                sb.append(s);
                
                sb.append("</option>");
            }
            return sb.toString();
        }
        else {

            if (neednull)
                sb.append("<a val=''><span>--请选择(不限)--</span></a>");
            Mcodedetail mcode = null;
            for (Iterator<String> it = mcodedetailMap.keySet().iterator(); it.hasNext();) {
                Object key = it.next();
                mcode = (Mcodedetail) mcodedetailMap.get(key);
                Object value = mcode.getContent();
                sb.append("<a val='").append(key.toString().trim()).append("'");
                sb.append(" title='").append(value).append("'");
                if (nowvalue != null && nowvalue.trim().equals(key.toString().trim()))
                    sb.append(" class='selected'><span>");
                else
                    sb.append("><span>");
                sb.append(value).append("</span></a>");
            }
            return sb.toString();
        }
    }
	public String getName() {
		return mcodeId;
	}
    
	public Map<String, String> getSystemCodeMap() {
		Mcodedetail mcode = null;
		Map<String, String> linkMap = new LinkedHashMap<String, String>();
		for (Iterator<String> it = mcodedetailMap.keySet().iterator(); it
				.hasNext();) {
			Object key = it.next();
			mcode = (Mcodedetail) mcodedetailMap.get(key);
			if (mcode.getType().equals("0") || mcode.getType().equals("2")) {
				String[] s = new String[2];
				s[0] = mcode.getThisId();
				s[1] = mcode.getContent();
				linkMap.put(s[0], s[1]);
			}
		}
		return linkMap;
	}

	public String get(String num, String special, String prefix, String suffix) {
		if (num == null) {
			return null;
		}
		if (special == null) {
			return this.get(num);
		}
		if (num.trim().equals(special.trim())) {
			return prefix + this.get(num) + suffix;
		} else {
			return this.get(num);
		}
	}

	public List<Mcodedetail> getMcodeDetailList() {
		List<Mcodedetail> list = new ArrayList<Mcodedetail>();
		Mcodedetail mcode = null;
		for (Iterator<String> it = mcodedetailMap.keySet().iterator(); it
				.hasNext();) {
			Object key = it.next();
			mcode = (Mcodedetail) mcodedetailMap.get(key);
			list.add(mcode);
		}
		return list;
	}

}

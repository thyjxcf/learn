package net.zdsoft.eis.base.auditflow.template.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 该类存放了一个业务相关的业务信息.<br>
 * 其中必要的信息有:
 * <ul>
 * <li>业务申请的id(<tt>applyId</tt>)</li>
 * <li>发起业务的单位id(<tt>applyUnitId</tt>)</li>
 * <li>业务属于的学段范围(<tt>section</tt>)</li>
 * </ul>
 * 对于那些多用户参与发起的业务,可以把其他合作单位的id添加在<tt>cooperateUnitIds</tt>中.
 * 
 * @author yaox
 * @version 1.0
 */
public class BusinessInfo {

	private String applyId;

	private String applyUnitId;

	private Integer section;

	private List<String> cooperateUnitIds;

	public BusinessInfo(String applyId, String applyUnitId, Integer section) {
		this.setApplyId(applyId);
		this.setApplyUnitId(applyUnitId);
		this.setSection(section);
		cooperateUnitIds = new ArrayList<String>(2);
	}

	public void setApplyId(String applyId) {
		if (StringUtils.isBlank(applyId)) {
			throw new IllegalArgumentException("apply id should not be blank");
		}
		this.applyId = applyId;
	}

	public void setApplyUnitId(String applyUnitId) {
		if (StringUtils.isBlank(applyUnitId)) {
			throw new IllegalArgumentException(
					"applyUnitId id should not be blank");
		}
		this.applyUnitId = applyUnitId;
	}

	public void setSection(Integer section) {
		if (section == null || section == -1 || "".equals(section.toString())) {
			throw new IllegalArgumentException("section id should not be blank");
		}
		this.section = section;
	}

	/**
	 * 添加参与发起业务的其他单位id.<br>
	 * 如果添加的单位id与<tt>applyUnitId</tt>相同,则不会添加到参与单位id中去.
	 * 
	 * @param cooperateUnitId
	 *            参与发起业务的其他单位id
	 */
	public void addCooperateUnitId(String cooperateUnitId) {
		if (applyUnitId.equals(cooperateUnitId)) {
			return;
		}
		cooperateUnitIds.add(cooperateUnitId);
	}

	public List<String> getCooperateUnitIds() {
		return cooperateUnitIds;
	}

	public String getApplyId() {
		return applyId;
	}

	public String getApplyUnitId() {
		return applyUnitId;
	}

	public Integer getSection() {
		return section;
	}

}

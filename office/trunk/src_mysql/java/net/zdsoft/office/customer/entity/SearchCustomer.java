package net.zdsoft.office.customer.entity;

import java.util.Date;

public class SearchCustomer {
		private String name;//客户名称
		private String followerName;//跟进人名称
		private String[] followerIds;//跟进人bids
		private String type;//客户类别
		private String region;
		private String regionName;
		private String deptId;
		private String progressState;//查询时--客户的跟进状态--审核的审核状态
		private String dataType;//移动端--列表类型
		private Date startTime;
		private Date endTime;
		
		public Date getStartTime() {
			return startTime;
		}
		public void setStartTime(Date startTime) {
			this.startTime = startTime;
		}
		public Date getEndTime() {
			return endTime;
		}
		public void setEndTime(Date endTime) {
			this.endTime = endTime;
		}
		public String getDeptId() {
			return deptId;
		}
		public void setDeptId(String deptId) {
			this.deptId = deptId;
		}
		public String getName() {
			return name;
		}
		public String getRegionName() {
			return regionName;
		}
		public void setRegionName(String regionName) {
			this.regionName = regionName;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getFollowerName() {
			return followerName;
		}
		public void setFollowerName(String followerName) {
			this.followerName = followerName;
		}
		public String[] getFollowerIds() {
			return followerIds;
		}
		public void setFollowerIds(String[] followerIds) {
			this.followerIds = followerIds;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getRegion() {
			return region;
		}
		public void setRegion(String region) {
			this.region = region;
		}
		public String getProgressState() {
			return progressState;
		}
		public void setProgressState(String progressState) {
			this.progressState = progressState;
		}
		public String getDataType() {
			return dataType;
		}
		public void setDataType(String dataType) {
			this.dataType = dataType;
		}
		
}

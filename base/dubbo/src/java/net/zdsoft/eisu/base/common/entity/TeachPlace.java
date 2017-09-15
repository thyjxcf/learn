/* 
 * @(#)TeachPlace.java    Created on May 13, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.common.entity;

import net.zdsoft.eis.frame.client.BaseEntity;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 13, 2011 8:05:35 PM $
 */
public class TeachPlace extends BaseEntity {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -4971413125286836898L;

    private String unitId;
    private String placeCode;
    private String placeName;
    private String teachAreaId;// 所属教学区
    
    private int placeNum; // 可容纳人数
    private int classNumber;// 可容纳班级个数
    
	public int getClassNumber() {
		return classNumber;
	}

	public void setClassNumber(int classNumber) {
		this.classNumber = classNumber;
	}

	private String teachBuildingId;  //楼
	private Integer floorNumber;  //所属层        
	private Integer floorDisplayOrder;  //所属层序号
	
	/**
	 * 楼 的属性类型 ：1：教学类型
	 */
	public static final String TEACH_BUILDING_TYPE1 ="1";
	/**
	 * 楼 的属性类型 ：2：实训类型
	 */
	public static final String TEACH_BUILDING_TYPE2 ="2";
	/**
	 * 楼 的属性类型 ：99：其它类型
	 */
	public static final String TEACH_BUILDING_TYPE99="99";
    

    
    private String controllerID;//控制器ID
    private Integer doorNO;//门编号
    private String machineCode; //机器编号

    /**
	 * 教学场地类型：教学
	 */
	public static final String PLACE_TYPE_TEACHER = "1"; //教学
	
	/**
	 * 教学场地类型：资产存放
	 */
    public static final String PLACE_TYPE_ASSET_STORE = "2"; //资产存放
    
    /**
	 * 教学场地类型：资产存放
	 */
    public static final String PLACE_TYPE_COMPUTER = "3"; //机房
    
    /**
     * 教室预约使用
     */
    public static final String PLACE_TYPE_MEETING = "70"; //会议室

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getPlaceCode() {
        return placeCode;
    }

    public void setPlaceCode(String placeCode) {
        this.placeCode = placeCode;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getTeachAreaId() {
        return teachAreaId;
    }

    public void setTeachAreaId(String teachAreaId) {
        this.teachAreaId = teachAreaId;
    }

    public int getPlaceNum() {
        return placeNum;
    }

    public void setPlaceNum(int placeNum) {
        this.placeNum = placeNum;
    }


	public String getTeachBuildingId() {
		return teachBuildingId;
	}

	public void setTeachBuildingId(String teachBuildingId) {
		this.teachBuildingId = teachBuildingId;
	}

	public Integer getFloorNumber() {
		return floorNumber;
	}

	public void setFloorNumber(Integer floorNumber) {
		this.floorNumber = floorNumber;
	}

	public Integer getFloorDisplayOrder() {
		return floorDisplayOrder;
	}

	public void setFloorDisplayOrder(Integer floorDisplayOrder) {
		this.floorDisplayOrder = floorDisplayOrder;
	}
    


	public String getControllerID() {
		return controllerID;
	}

	public void setControllerID(String controllerID) {
		this.controllerID = controllerID;
	}

	public Integer getDoorNO() {
		return doorNO;
	}

	public void setDoorNO(Integer doorNO) {
		this.doorNO = doorNO;
	}

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }


}

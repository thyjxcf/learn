package net.zdsoft.eis.base.subsystemcall.service;

public interface SubsystemCallService {

    /**
     * 学年是否被引用，返回为null，表示不引用，否则为引用的内容
     * 
     * @param acadyear
     * @return
     */
    public String isSemesterRef(String acadyear);

    /**
     * 院系是否被引用，返回为null，表示不引用，否则为引用的内容
     * 
     * @param institutId
     * @return
     */
    public String isInstituteRef(String institutId);

    /**
     * 专业是否被引用，返回为null，表示不引用，否则为引用的内容
     * 
     * @param specialtyId
     * @return
     */
    public String isSpecialtyRef(String specialtyId);

    /**
     * 教学场地是否被引用，返回为null，表示不引用，否则为引用的内容
     * 
     * @param teachPlaceId
     * @return
     */
    public String isTeachPlaceRef(String teachPlaceId);
}

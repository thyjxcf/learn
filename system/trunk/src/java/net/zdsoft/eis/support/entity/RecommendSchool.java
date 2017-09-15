package net.zdsoft.eis.support.entity;

import java.util.Date;

import net.zdsoft.eis.frame.client.BaseEntity;

/* 
 * <p>ZDSoft数字校园（support）</p>
 * 
 * @author Cuibz 2007/08/18
 */
public class RecommendSchool extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String schoolId; // 学校唯一Id
    private String schoolName; // 学校名称
    private String schoolFullName;
    private String schoolHomepage; // 学校主页
    private String schoolBlog; // 学校博客
    private String schoolType; // 学校类型
    private String schoolTypeText; // 学校类型文字内容
    private String schoolAddress; // 学校地址
    private String schoolPostcode; // 学校邮政编码
    private String schoolPhone; // 学校电话
    private String schoolFax; // 学校传真
    private String schoolIntroduction; // 学校介绍
    private String schoolRegionCode; // 区域编码
    private int schoolRecommend; // 学校推荐
    private Date schoolRecommendDate; // 学校推荐日期
    private String schoolRecommendDateText; // 学校推荐日期文本显示
    private String schoolPictureUrl; // 学校图片链接
    private String schoolEmail; // 学校Email

    public String getSchoolAddress() {
        return schoolAddress;
    }

    public void setSchoolAddress(String schoolAddress) {
        this.schoolAddress = schoolAddress;
    }

    public String getSchoolBlog() {
        return schoolBlog;
    }

    public void setSchoolBlog(String schoolBlog) {
        this.schoolBlog = schoolBlog;
    }

    public String getSchoolFax() {
        return schoolFax;
    }

    public void setSchoolFax(String schoolFax) {
        this.schoolFax = schoolFax;
    }

    public String getSchoolHomepage() {
        return schoolHomepage;
    }

    public void setSchoolHomepage(String schoolHomepage) {
        this.schoolHomepage = schoolHomepage;
    }

    public String getSchoolIntroduction() {
        return schoolIntroduction;
    }

    public void setSchoolIntroduction(String schoolIntroduction) {
        this.schoolIntroduction = schoolIntroduction;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolPhone() {
        return schoolPhone;
    }

    public void setSchoolPhone(String schoolPhone) {
        this.schoolPhone = schoolPhone;
    }

    public String getSchoolPostcode() {
        return schoolPostcode;
    }

    public void setSchoolPostcode(String schoolPostcode) {
        this.schoolPostcode = schoolPostcode;
    }

    public int getSchoolRecommend() {
        return schoolRecommend;
    }

    public void setSchoolRecommend(int schoolRecommend) {
        this.schoolRecommend = schoolRecommend;
    }

    public Date getSchoolRecommendDate() {
        return schoolRecommendDate;
    }

    public void setSchoolRecommendDate(Date schoolRecommendDate) {
        this.schoolRecommendDate = schoolRecommendDate;
    }

    public String getSchoolRegionCode() {
        return schoolRegionCode;
    }

    public void setSchoolRegionCode(String schoolRegionCode) {
        this.schoolRegionCode = schoolRegionCode;
    }

    public String getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(String schoolType) {
        this.schoolType = schoolType;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolTypeText() {
        return schoolTypeText;
    }

    public void setSchoolTypeText(String schoolTypeText) {
        this.schoolTypeText = schoolTypeText;
    }

    public String getSchoolPictureUrl() {
        return schoolPictureUrl;
    }

    public void setSchoolPictureUrl(String school_picture_url) {
        this.schoolPictureUrl = school_picture_url;
    }

    public String getSchoolRecommendDateText() {
        return schoolRecommendDateText;
    }

    public void setSchoolRecommendDateText(String schoolRecommendDateText) {
        this.schoolRecommendDateText = schoolRecommendDateText;
    }

    public String getSchoolFullName() {
        return schoolFullName;
    }

    public void setSchoolFullName(String schoolFullName) {
        this.schoolFullName = schoolFullName;
    }

    public String getSchoolEmail() {
        return schoolEmail;
    }

    public void setSchoolEmail(String schoolEmail) {
        this.schoolEmail = schoolEmail;
    }

}

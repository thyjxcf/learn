package net.zdsoft.office.salary.dao.impl;

import java.sql.*;
import java.util.*;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.office.salary.entity.OfficeSalaryImport;
import net.zdsoft.office.salary.dao.OfficeSalaryImportDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_salary_import
 * @author 
 * 
 */
public class OfficeSalaryImportDaoImpl extends BaseDao<OfficeSalaryImport> implements OfficeSalaryImportDao{

	@Override
	public OfficeSalaryImport setField(ResultSet rs) throws SQLException{
		OfficeSalaryImport officeSalaryImport = new OfficeSalaryImport();
		officeSalaryImport.setId(rs.getString("id"));
		officeSalaryImport.setUnitId(rs.getString("unit_id"));
		officeSalaryImport.setSerialNumbers(rs.getString("serial_numbers"));
		officeSalaryImport.setRealname(rs.getString("realname"));
		officeSalaryImport.setCardnumber(rs.getString("cardnumber"));
		officeSalaryImport.setMonthtime(rs.getString("monthtime"));
		officeSalaryImport.setSalaryTime(rs.getTimestamp("salary_time"));
		officeSalaryImport.setCreateTime(rs.getTimestamp("create_time"));
		officeSalaryImport.setSalary1(rs.getString("salary_1"));
		officeSalaryImport.setSalary2(rs.getString("salary_2"));
		officeSalaryImport.setSalary3(rs.getString("salary_3"));
		officeSalaryImport.setSalary4(rs.getString("salary_4"));
		officeSalaryImport.setSalary5(rs.getString("salary_5"));
		officeSalaryImport.setSalary6(rs.getString("salary_6"));
		officeSalaryImport.setSalary7(rs.getString("salary_7"));
		officeSalaryImport.setSalary8(rs.getString("salary_8"));
		officeSalaryImport.setSalary9(rs.getString("salary_9"));
		officeSalaryImport.setSalary10(rs.getString("salary_10"));
		officeSalaryImport.setSalary11(rs.getString("salary_11"));
		officeSalaryImport.setSalary12(rs.getString("salary_12"));
		officeSalaryImport.setSalary13(rs.getString("salary_13"));
		officeSalaryImport.setSalary14(rs.getString("salary_14"));
		officeSalaryImport.setSalary15(rs.getString("salary_15"));
		officeSalaryImport.setSalary16(rs.getString("salary_16"));
		officeSalaryImport.setSalary17(rs.getString("salary_17"));
		officeSalaryImport.setSalary18(rs.getString("salary_18"));
		officeSalaryImport.setSalary19(rs.getString("salary_19"));
		officeSalaryImport.setSalary20(rs.getString("salary_20"));
		officeSalaryImport.setSalary21(rs.getString("salary_21"));
		officeSalaryImport.setSalary22(rs.getString("salary_22"));
		officeSalaryImport.setSalary23(rs.getString("salary_23"));
		officeSalaryImport.setSalary24(rs.getString("salary_24"));
		officeSalaryImport.setSalary25(rs.getString("salary_25"));
		officeSalaryImport.setSalary26(rs.getString("salary_26"));
		officeSalaryImport.setSalary27(rs.getString("salary_27"));
		officeSalaryImport.setSalary28(rs.getString("salary_28"));
		officeSalaryImport.setSalary29(rs.getString("salary_29"));
		officeSalaryImport.setSalary30(rs.getString("salary_30"));
		officeSalaryImport.setSalary31(rs.getString("salary_31"));
		officeSalaryImport.setSalary32(rs.getString("salary_32"));
		officeSalaryImport.setSalary33(rs.getString("salary_33"));
		officeSalaryImport.setSalary34(rs.getString("salary_34"));
		officeSalaryImport.setSalary35(rs.getString("salary_35"));
		officeSalaryImport.setSalary36(rs.getString("salary_36"));
		officeSalaryImport.setSalary37(rs.getString("salary_37"));
		officeSalaryImport.setSalary38(rs.getString("salary_38"));
		officeSalaryImport.setSalary39(rs.getString("salary_39"));
		officeSalaryImport.setSalary40(rs.getString("salary_40"));
		officeSalaryImport.setSalary41(rs.getString("salary_41"));
		officeSalaryImport.setSalary42(rs.getString("salary_42"));
		officeSalaryImport.setSalary43(rs.getString("salary_43"));
		officeSalaryImport.setSalary44(rs.getString("salary_44"));
		officeSalaryImport.setSalary45(rs.getString("salary_45"));
		officeSalaryImport.setSalary46(rs.getString("salary_46"));
		officeSalaryImport.setSalary47(rs.getString("salary_47"));officeSalaryImport.setSalary48(rs.getString("salary_48"));officeSalaryImport.setSalary49(rs.getString("salary_49"));
		officeSalaryImport.setSalary50(rs.getString("salary_50"));officeSalaryImport.setSalary51(rs.getString("salary_51"));officeSalaryImport.setSalary52(rs.getString("salary_52"));
		officeSalaryImport.setSalary53(rs.getString("salary_53"));officeSalaryImport.setSalary54(rs.getString("salary_54"));officeSalaryImport.setSalary55(rs.getString("salary_55"));
		officeSalaryImport.setSalary56(rs.getString("salary_56"));officeSalaryImport.setSalary57(rs.getString("salary_57"));officeSalaryImport.setSalary58(rs.getString("salary_58"));
		officeSalaryImport.setSalary59(rs.getString("salary_59"));officeSalaryImport.setSalary60(rs.getString("salary_60"));officeSalaryImport.setSalary61(rs.getString("salary_61"));
		officeSalaryImport.setSalary62(rs.getString("salary_62"));officeSalaryImport.setSalary63(rs.getString("salary_63"));officeSalaryImport.setSalary64(rs.getString("salary_64"));
		officeSalaryImport.setSalary65(rs.getString("salary_65"));officeSalaryImport.setSalary66(rs.getString("salary_66"));officeSalaryImport.setSalary67(rs.getString("salary_67"));
		officeSalaryImport.setSalary68(rs.getString("salary_68"));officeSalaryImport.setSalary69(rs.getString("salary_69"));officeSalaryImport.setSalary70(rs.getString("salary_70"));
		officeSalaryImport.setSalary71(rs.getString("salary_71"));officeSalaryImport.setSalary72(rs.getString("salary_72"));officeSalaryImport.setSalary73(rs.getString("salary_73"));
		officeSalaryImport.setSalary74(rs.getString("salary_74"));officeSalaryImport.setSalary75(rs.getString("salary_75"));officeSalaryImport.setSalary76(rs.getString("salary_76"));
		officeSalaryImport.setSalary77(rs.getString("salary_77"));officeSalaryImport.setSalary78(rs.getString("salary_78"));officeSalaryImport.setSalary79(rs.getString("salary_79"));
		officeSalaryImport.setSalary80(rs.getString("salary_80"));officeSalaryImport.setSalary81(rs.getString("salary_81"));officeSalaryImport.setSalary82(rs.getString("salary_82"));
		officeSalaryImport.setSalary83(rs.getString("salary_83"));officeSalaryImport.setSalary84(rs.getString("salary_84"));officeSalaryImport.setSalary85(rs.getString("salary_85"));
		officeSalaryImport.setSalary86(rs.getString("salary_86"));officeSalaryImport.setSalary87(rs.getString("salary_87"));officeSalaryImport.setSalary88(rs.getString("salary_88"));
		officeSalaryImport.setSalary89(rs.getString("salary_89"));officeSalaryImport.setSalary90(rs.getString("salary_90"));officeSalaryImport.setSalary91(rs.getString("salary_91"));
		officeSalaryImport.setSalary92(rs.getString("salary_92"));officeSalaryImport.setSalary93(rs.getString("salary_93"));officeSalaryImport.setSalary94(rs.getString("salary_94"));
		officeSalaryImport.setSalary95(rs.getString("salary_95"));officeSalaryImport.setSalary96(rs.getString("salary_96"));officeSalaryImport.setSalary97(rs.getString("salary_97"));
		return officeSalaryImport;
	}

	@Override
	public OfficeSalaryImport save(OfficeSalaryImport officeSalaryImport){
		String sql = "insert into office_salary_import(id, unit_id, serial_numbers, salary_time, realname, cardnumber, monthtime, create_time, salary_1, salary_2, salary_3, salary_4, salary_5, salary_6, salary_7, salary_8, salary_9, salary_10, salary_11, salary_12, salary_13, salary_14, salary_15, salary_16, salary_17, salary_18, salary_19, salary_20, salary_21, salary_22, salary_23, salary_24, salary_25, salary_26, salary_27, salary_28, salary_29, salary_30, salary_31, salary_32, salary_33, salary_34, salary_35, salary_36, salary_37, salary_38, salary_39, salary_40, salary_41, salary_42, salary_43, salary_44, salary_45, salary_46, salary_47, salary_48, salary_49, salary_50, salary_51, salary_52, salary_53, salary_54, salary_55, salary_56, salary_57, salary_58, salary_59, salary_60, salary_61, salary_62, salary_63, salary_64, salary_65, salary_66, salary_67, salary_68, salary_69, salary_70, salary_71, salary_72, salary_73, salary_74, salary_75, salary_76, salary_77, salary_78, salary_79, salary_80, salary_81, salary_82, salary_83, salary_84, salary_85, salary_86, salary_87, salary_88, salary_89, salary_90, salary_91, salary_92, salary_93, salary_94, salary_95, salary_96, salary_97) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeSalaryImport.getId())){
			officeSalaryImport.setId(createId());
		}
		Object[] args = new Object[]{
			officeSalaryImport.getId(), officeSalaryImport.getUnitId(), 
			officeSalaryImport.getSerialNumbers(), officeSalaryImport.getSalaryTime(), 
			officeSalaryImport.getRealname(), officeSalaryImport.getCardnumber(), 
			officeSalaryImport.getMonthtime(), officeSalaryImport.getCreateTime(), 
			officeSalaryImport.getSalary1(), officeSalaryImport.getSalary2(), 
			officeSalaryImport.getSalary3(), officeSalaryImport.getSalary4(), 
			officeSalaryImport.getSalary5(), officeSalaryImport.getSalary6(), 
			officeSalaryImport.getSalary7(), officeSalaryImport.getSalary8(), 
			officeSalaryImport.getSalary9(), officeSalaryImport.getSalary10(), 
			officeSalaryImport.getSalary11(), officeSalaryImport.getSalary12(), 
			officeSalaryImport.getSalary13(), officeSalaryImport.getSalary14(), 
			officeSalaryImport.getSalary15(), officeSalaryImport.getSalary16(), 
			officeSalaryImport.getSalary17(), officeSalaryImport.getSalary18(), 
			officeSalaryImport.getSalary19(), officeSalaryImport.getSalary20(), 
			officeSalaryImport.getSalary21(), officeSalaryImport.getSalary22(), 
			officeSalaryImport.getSalary23(), officeSalaryImport.getSalary24(), 
			officeSalaryImport.getSalary25(), officeSalaryImport.getSalary26(), 
			officeSalaryImport.getSalary27(), officeSalaryImport.getSalary28(), 
			officeSalaryImport.getSalary29(), officeSalaryImport.getSalary30(), 
			officeSalaryImport.getSalary31(), officeSalaryImport.getSalary32(), 
			officeSalaryImport.getSalary33(), officeSalaryImport.getSalary34(), 
			officeSalaryImport.getSalary35(), officeSalaryImport.getSalary36(), 
			officeSalaryImport.getSalary37(), officeSalaryImport.getSalary38(), 
			officeSalaryImport.getSalary39(), officeSalaryImport.getSalary40(), 
			officeSalaryImport.getSalary41(), officeSalaryImport.getSalary42(), 
			officeSalaryImport.getSalary43(), officeSalaryImport.getSalary44(), 
			officeSalaryImport.getSalary45(), officeSalaryImport.getSalary46(), 
			officeSalaryImport.getSalary47(), officeSalaryImport.getSalary48(), 
			officeSalaryImport.getSalary49(), officeSalaryImport.getSalary50(), 
			officeSalaryImport.getSalary51(), officeSalaryImport.getSalary52(), 
			officeSalaryImport.getSalary53(), officeSalaryImport.getSalary54(), 
			officeSalaryImport.getSalary55(), officeSalaryImport.getSalary56(), 
			officeSalaryImport.getSalary57(), officeSalaryImport.getSalary58(), 
			officeSalaryImport.getSalary59(), officeSalaryImport.getSalary60(), 
			officeSalaryImport.getSalary61(), officeSalaryImport.getSalary62(), 
			officeSalaryImport.getSalary63(), officeSalaryImport.getSalary64(), 
			officeSalaryImport.getSalary65(), officeSalaryImport.getSalary66(), 
			officeSalaryImport.getSalary67(), officeSalaryImport.getSalary68(), 
			officeSalaryImport.getSalary69(), officeSalaryImport.getSalary70(), 
			officeSalaryImport.getSalary71(), officeSalaryImport.getSalary72(), 
			officeSalaryImport.getSalary73(), officeSalaryImport.getSalary74(), 
			officeSalaryImport.getSalary75(), officeSalaryImport.getSalary76(), 
			officeSalaryImport.getSalary77(), officeSalaryImport.getSalary78(), 
			officeSalaryImport.getSalary79(), officeSalaryImport.getSalary80(), 
			officeSalaryImport.getSalary81(), officeSalaryImport.getSalary82(), 
			officeSalaryImport.getSalary83(), officeSalaryImport.getSalary84(), 
			officeSalaryImport.getSalary85(), officeSalaryImport.getSalary86(), 
			officeSalaryImport.getSalary87(), officeSalaryImport.getSalary88(), 
			officeSalaryImport.getSalary89(), officeSalaryImport.getSalary90(), 
			officeSalaryImport.getSalary91(), officeSalaryImport.getSalary92(), 
			officeSalaryImport.getSalary93(), officeSalaryImport.getSalary94(), 
			officeSalaryImport.getSalary95(), officeSalaryImport.getSalary96(), 
			officeSalaryImport.getSalary97()
		};
		update(sql, args);
		return officeSalaryImport;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_salary_import where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeSalaryImport officeSalaryImport){
		String sql = "update office_salary_import set unit_id = ?, serial_numbers = ?, salary_time = ?, realname = ?, cardnumber = ?, monthtime = ?, create_time = ?, salary_1 = ?, salary_2 = ?, salary_3 = ?, salary_4 = ?, salary_5 = ?, salary_6 = ?, salary_7 = ?, salary_8 = ?, salary_9 = ?, salary_10 = ?, salary_11 = ?, salary_12 = ?, salary_13 = ?, salary_14 = ?, salary_15 = ?, salary_16 = ?, salary_17 = ?, salary_18 = ?, salary_19 = ?, salary_20 = ?, salary_21 = ?, salary_22 = ?, salary_23 = ?, salary_24 = ?, salary_25 = ?, salary_26 = ?, salary_27 = ?, salary_28 = ?, salary_29 = ?, salary_30 = ?, salary_31 = ?, salary_32 = ?, salary_33 = ?, salary_34 = ?, salary_35 = ?, salary_36 = ?, salary_37 = ?, salary_38 = ?, salary_39 = ?, salary_40 = ?, salary_41 = ?, salary_42 = ?, salary_43 = ?, salary_44 = ?, salary_45 = ?, salary_46 = ?, salary_47 = ?, salary_48 = ?, salary_49 = ?, salary_50 = ?, salary_51 = ?, salary_52 = ?, salary_53 = ?, salary_54 = ?, salary_55 = ?, salary_56 = ?, salary_57 = ?, salary_58 = ?, salary_59 = ?, salary_60 = ?, salary_61 = ?, salary_62 = ?, salary_63 = ?, salary_64 = ?, salary_65 = ?, salary_66 = ?, salary_67 = ?, salary_68 = ?, salary_69 = ?, salary_70 = ?, salary_71 = ?, salary_72 = ?, salary_73 = ?, salary_74 = ?, salary_75 = ?, salary_76 = ?, salary_77 = ?, salary_78 = ?, salary_79 = ?, salary_80 = ?, salary_81 = ?, salary_82 = ?, salary_83 = ?, salary_84 = ?, salary_85 = ?, salary_86 = ?, salary_87 = ?, salary_88 = ?, salary_89 = ?, salary_90 = ?, salary_91 = ?, salary_92 = ?, salary_93 = ?, salary_94 = ?, salary_95 = ?, salary_96 = ?, salary_97 = ? where id = ?";
		Object[] args = new Object[]{
			officeSalaryImport.getUnitId(), officeSalaryImport.getSerialNumbers(), 
			officeSalaryImport.getSalaryTime(), officeSalaryImport.getRealname(), 
			officeSalaryImport.getCardnumber(), officeSalaryImport.getMonthtime(), 
			officeSalaryImport.getCreateTime(), officeSalaryImport.getSalary1(), 
			officeSalaryImport.getSalary2(), officeSalaryImport.getSalary3(), 
			officeSalaryImport.getSalary4(), officeSalaryImport.getSalary5(), 
			officeSalaryImport.getSalary6(), officeSalaryImport.getSalary7(), 
			officeSalaryImport.getSalary8(), officeSalaryImport.getSalary9(), 
			officeSalaryImport.getSalary10(), officeSalaryImport.getSalary11(), 
			officeSalaryImport.getSalary12(), officeSalaryImport.getSalary13(), 
			officeSalaryImport.getSalary14(), officeSalaryImport.getSalary15(), 
			officeSalaryImport.getSalary16(), officeSalaryImport.getSalary17(), 
			officeSalaryImport.getSalary18(), officeSalaryImport.getSalary19(), 
			officeSalaryImport.getSalary20(), officeSalaryImport.getSalary21(), 
			officeSalaryImport.getSalary22(), officeSalaryImport.getSalary23(), 
			officeSalaryImport.getSalary24(), officeSalaryImport.getSalary25(), 
			officeSalaryImport.getSalary26(), officeSalaryImport.getSalary27(), 
			officeSalaryImport.getSalary28(), officeSalaryImport.getSalary29(), 
			officeSalaryImport.getSalary30(), officeSalaryImport.getSalary31(), 
			officeSalaryImport.getSalary32(), officeSalaryImport.getSalary33(), 
			officeSalaryImport.getSalary34(), officeSalaryImport.getSalary35(), 
			officeSalaryImport.getSalary36(), officeSalaryImport.getSalary37(), 
			officeSalaryImport.getSalary38(), officeSalaryImport.getSalary39(), 
			officeSalaryImport.getSalary40(), officeSalaryImport.getSalary41(), 
			officeSalaryImport.getSalary42(), officeSalaryImport.getSalary43(), 
			officeSalaryImport.getSalary44(), officeSalaryImport.getSalary45(), 
			officeSalaryImport.getSalary46(), officeSalaryImport.getSalary47(), 
			officeSalaryImport.getSalary48(), officeSalaryImport.getSalary49(), 
			officeSalaryImport.getSalary50(), officeSalaryImport.getSalary51(), 
			officeSalaryImport.getSalary52(), officeSalaryImport.getSalary53(), 
			officeSalaryImport.getSalary54(), officeSalaryImport.getSalary55(), 
			officeSalaryImport.getSalary56(), officeSalaryImport.getSalary57(), 
			officeSalaryImport.getSalary58(), officeSalaryImport.getSalary59(), 
			officeSalaryImport.getSalary60(), officeSalaryImport.getSalary61(), 
			officeSalaryImport.getSalary62(), officeSalaryImport.getSalary63(), 
			officeSalaryImport.getSalary64(), officeSalaryImport.getSalary65(), 
			officeSalaryImport.getSalary66(), officeSalaryImport.getSalary67(), 
			officeSalaryImport.getSalary68(), officeSalaryImport.getSalary69(), 
			officeSalaryImport.getSalary70(), officeSalaryImport.getSalary71(), 
			officeSalaryImport.getSalary72(), officeSalaryImport.getSalary73(), 
			officeSalaryImport.getSalary74(), officeSalaryImport.getSalary75(), 
			officeSalaryImport.getSalary76(), officeSalaryImport.getSalary77(), 
			officeSalaryImport.getSalary78(), officeSalaryImport.getSalary79(), 
			officeSalaryImport.getSalary80(), officeSalaryImport.getSalary81(), 
			officeSalaryImport.getSalary82(), officeSalaryImport.getSalary83(), 
			officeSalaryImport.getSalary84(), officeSalaryImport.getSalary85(), 
			officeSalaryImport.getSalary86(), officeSalaryImport.getSalary87(), 
			officeSalaryImport.getSalary88(), officeSalaryImport.getSalary89(), 
			officeSalaryImport.getSalary90(), officeSalaryImport.getSalary91(), 
			officeSalaryImport.getSalary92(), officeSalaryImport.getSalary93(), 
			officeSalaryImport.getSalary94(), officeSalaryImport.getSalary95(), 
			officeSalaryImport.getSalary96(), officeSalaryImport.getSalary97(), 
			officeSalaryImport.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeSalaryImport getOfficeSalaryImportById(String id){
		String sql = "select * from office_salary_import where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeSalaryImport> getOfficeSalaryImportMapByIds(String[] ids){
		String sql = "select * from office_salary_import where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeSalaryImport> getOfficeSalaryImportList(){
		String sql = "select * from office_salary_import";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeSalaryImport> getOfficeSalaryImportPage(Pagination page){
		String sql = "select * from office_salary_import";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeSalaryImport> getOfficeSalaryImportByUnitIdList(String unitId){
		String sql = "select * from office_salary_import where unit_id = ? order by create_time desc";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeSalaryImport> getOfficeSalaryImportByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_salary_import where unit_id = ? order by create_time desc";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}

	@Override
	public OfficeSalaryImport getOfficeSalaryImportByUnitExcelName(String unitId,String excelName){
			String sql = "select * from office_salary_import where unit_id=? and monthtime=? ";
			return query(sql, new Object[]{unitId,excelName }, new SingleRow());
		}

	@Override
	public OfficeSalaryImport getOfficeSalaryImportByTime(String unitId,String salaryTime) {
		StringBuffer sb=new StringBuffer();
		if(StringUtils.isNotBlank(salaryTime)){
			sb.append("select * from (select * from office_salary_import where unit_id=? and salary_time=to_date(?, 'yyyy-MM')  order by create_time desc) where rownum=1 ");
			return query(sb.toString(), new Object[]{unitId,salaryTime}, new SingleRow());
		}else{
			sb.append("select * from (select * from office_salary_import where unit_id=?  order by create_time desc) where rownum=1 ");
			return query(sb.toString(), new Object[]{unitId}, new SingleRow());
		}
	}

	@Override
	public List<OfficeSalaryImport> getOfficeSalaryImportByUnitIdAndTime(
			String unitId, String salaryTime) {
		String sql="select * from office_salary_import where unit_id=? and salary_time=to_date(?, 'yyyy-MM')  order by create_time desc";
		return query(sql, new Object[]{unitId,salaryTime }, new MultiRow());
	}
	
	
}

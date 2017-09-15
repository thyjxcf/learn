package net.zdsoft.office.salary.dao.impl;

import java.sql.*;
import java.util.*;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.office.salary.entity.OfficeSalary;
import net.zdsoft.office.salary.dao.OfficeSalaryDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_salary
 * @author 
 * 
 */
public class OfficeSalaryDaoImpl extends BaseDao<OfficeSalary> implements OfficeSalaryDao{

	@Override
	public OfficeSalary setField(ResultSet rs) throws SQLException{
		OfficeSalary officeSalary = new OfficeSalary();
		officeSalary.setId(rs.getString("id"));
		officeSalary.setImportId(rs.getString("import_id"));
		officeSalary.setUnitId(rs.getString("unit_id"));
		officeSalary.setUserId(rs.getString("user_id"));
		officeSalary.setSerialNumbers(rs.getString("serial_numbers"));
		officeSalary.setRealname(rs.getString("realname"));
		officeSalary.setCardnumber(rs.getString("cardnumber"));
		officeSalary.setSalary1(rs.getString("salary_1"));
		officeSalary.setSalary2(rs.getString("salary_2"));
		officeSalary.setSalary3(rs.getString("salary_3"));
		officeSalary.setSalary4(rs.getString("salary_4"));
		officeSalary.setSalary5(rs.getString("salary_5"));
		officeSalary.setSalary6(rs.getString("salary_6"));
		officeSalary.setSalary7(rs.getString("salary_7"));
		officeSalary.setSalary8(rs.getString("salary_8"));
		officeSalary.setSalary9(rs.getString("salary_9"));
		officeSalary.setSalary10(rs.getString("salary_10"));
		officeSalary.setSalary11(rs.getString("salary_11"));
		officeSalary.setSalary12(rs.getString("salary_12"));
		officeSalary.setSalary13(rs.getString("salary_13"));
		officeSalary.setSalary14(rs.getString("salary_14"));
		officeSalary.setSalary15(rs.getString("salary_15"));
		officeSalary.setSalary16(rs.getString("salary_16"));
		officeSalary.setSalary17(rs.getString("salary_17"));
		officeSalary.setSalary18(rs.getString("salary_18"));
		officeSalary.setSalary19(rs.getString("salary_19"));
		officeSalary.setSalary20(rs.getString("salary_20"));
		officeSalary.setSalary21(rs.getString("salary_21"));
		officeSalary.setSalary22(rs.getString("salary_22"));
		officeSalary.setSalary23(rs.getString("salary_23"));
		officeSalary.setSalary24(rs.getString("salary_24"));
		officeSalary.setSalary25(rs.getString("salary_25"));
		officeSalary.setSalary26(rs.getString("salary_26"));
		officeSalary.setSalary27(rs.getString("salary_27"));
		officeSalary.setSalary28(rs.getString("salary_28"));
		officeSalary.setSalary29(rs.getString("salary_29"));
		officeSalary.setSalary30(rs.getString("salary_30"));
		officeSalary.setSalary31(rs.getString("salary_31"));
		officeSalary.setSalary32(rs.getString("salary_32"));
		officeSalary.setSalary33(rs.getString("salary_33"));
		officeSalary.setSalary34(rs.getString("salary_34"));
		officeSalary.setSalary35(rs.getString("salary_35"));
		officeSalary.setSalary36(rs.getString("salary_36"));
		officeSalary.setSalary37(rs.getString("salary_37"));
		officeSalary.setSalary38(rs.getString("salary_38"));
		officeSalary.setSalary39(rs.getString("salary_39"));
		officeSalary.setSalary40(rs.getString("salary_40"));
		officeSalary.setSalary41(rs.getString("salary_41"));
		officeSalary.setSalary42(rs.getString("salary_42"));
		officeSalary.setSalary43(rs.getString("salary_43"));
		officeSalary.setSalary44(rs.getString("salary_44"));
		officeSalary.setSalary45(rs.getString("salary_45"));
		officeSalary.setSalary46(rs.getString("salary_46"));
		officeSalary.setSalary47(rs.getString("salary_47"));
		officeSalary.setSalary48(rs.getString("salary_48"));
		officeSalary.setSalary49(rs.getString("salary_49"));
		officeSalary.setSalary50(rs.getString("salary_50"));
		officeSalary.setSalary51(rs.getString("salary_51"));
		officeSalary.setSalary52(rs.getString("salary_52"));
		officeSalary.setSalary53(rs.getString("salary_53"));
		officeSalary.setSalary54(rs.getString("salary_54"));
		officeSalary.setSalary55(rs.getString("salary_55"));
		officeSalary.setSalary56(rs.getString("salary_56"));
		officeSalary.setSalary57(rs.getString("salary_57"));
		officeSalary.setSalary58(rs.getString("salary_58"));
		officeSalary.setSalary59(rs.getString("salary_59"));
		officeSalary.setSalary60(rs.getString("salary_60"));
		officeSalary.setSalary61(rs.getString("salary_61"));
		officeSalary.setSalary62(rs.getString("salary_62"));
		officeSalary.setSalary63(rs.getString("salary_63"));
		officeSalary.setSalary64(rs.getString("salary_64"));
		officeSalary.setSalary65(rs.getString("salary_65"));
		officeSalary.setSalary66(rs.getString("salary_66"));
		officeSalary.setSalary67(rs.getString("salary_67"));
		officeSalary.setSalary68(rs.getString("salary_68"));
		officeSalary.setSalary69(rs.getString("salary_69"));
		officeSalary.setSalary70(rs.getString("salary_70"));
		officeSalary.setSalary71(rs.getString("salary_71"));
		officeSalary.setSalary72(rs.getString("salary_72"));
		officeSalary.setSalary73(rs.getString("salary_73"));
		officeSalary.setSalary74(rs.getString("salary_74"));
		officeSalary.setSalary75(rs.getString("salary_75"));
		officeSalary.setSalary76(rs.getString("salary_76"));
		officeSalary.setSalary77(rs.getString("salary_77"));
		officeSalary.setSalary78(rs.getString("salary_78"));
		officeSalary.setSalary79(rs.getString("salary_79"));
		officeSalary.setSalary80(rs.getString("salary_80"));
		officeSalary.setSalary81(rs.getString("salary_81"));
		officeSalary.setSalary82(rs.getString("salary_82"));
		officeSalary.setSalary83(rs.getString("salary_83"));
		officeSalary.setSalary84(rs.getString("salary_84"));
		officeSalary.setSalary85(rs.getString("salary_85"));
		officeSalary.setSalary86(rs.getString("salary_86"));
		officeSalary.setSalary87(rs.getString("salary_87"));
		officeSalary.setSalary88(rs.getString("salary_88"));
		officeSalary.setSalary89(rs.getString("salary_89"));
		officeSalary.setSalary90(rs.getString("salary_90"));
		officeSalary.setSalary91(rs.getString("salary_91"));
		officeSalary.setSalary92(rs.getString("salary_92"));
		officeSalary.setSalary93(rs.getString("salary_93"));
		officeSalary.setSalary94(rs.getString("salary_94"));
		officeSalary.setSalary95(rs.getString("salary_95"));
		officeSalary.setSalary96(rs.getString("salary_96"));
		officeSalary.setSalary97(rs.getString("salary_97"));
		return officeSalary;
	}

	@Override
	public OfficeSalary save(OfficeSalary officeSalary){
		String sql = "insert into office_salary(id, import_id, unit_id, user_id, serial_numbers, realname, cardnumber, salary_1, salary_2, salary_3, salary_4, salary_5, salary_6, salary_7, salary_8, salary_9, salary_10, salary_11, salary_12, salary_13, salary_14, salary_15, salary_16, salary_17, salary_18, salary_19, salary_20, salary_21, salary_22, salary_23, salary_24, salary_25, salary_26, salary_27, salary_28, salary_29, salary_30, salary_31, salary_32, salary_33, salary_34, salary_35, salary_36, salary_37, salary_38, salary_39, salary_40, salary_41, salary_42, salary_43, salary_44, salary_45, salary_46, salary_47, salary_48, salary_49, salary_50, salary_51, salary_52, salary_53, salary_54, salary_55, salary_56, salary_57, salary_58, salary_59, salary_60, salary_61, salary_62, salary_63, salary_64, salary_65, salary_66, salary_67, salary_68, salary_69, salary_70, salary_71, salary_72, salary_73, salary_74, salary_75, salary_76, salary_77, salary_78, salary_79, salary_80, salary_81, salary_82, salary_83, salary_84, salary_85, salary_86, salary_87, salary_88, salary_89, salary_90, salary_91, salary_92, salary_93, salary_94, salary_95, salary_96, salary_97) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeSalary.getId())){
			officeSalary.setId(createId());
		}
		Object[] args = new Object[]{
			officeSalary.getId(), officeSalary.getImportId(), 
			officeSalary.getUnitId(), officeSalary.getUserId(), 
			officeSalary.getSerialNumbers(), officeSalary.getRealname(), 
			officeSalary.getCardnumber(), officeSalary.getSalary1(), 
			officeSalary.getSalary2(), officeSalary.getSalary3(), 
			officeSalary.getSalary4(), officeSalary.getSalary5(), 
			officeSalary.getSalary6(), officeSalary.getSalary7(), 
			officeSalary.getSalary8(), officeSalary.getSalary9(), 
			officeSalary.getSalary10(), officeSalary.getSalary11(), 
			officeSalary.getSalary12(), officeSalary.getSalary13(), 
			officeSalary.getSalary14(), officeSalary.getSalary15(), 
			officeSalary.getSalary16(), officeSalary.getSalary17(), 
			officeSalary.getSalary18(), officeSalary.getSalary19(), 
			officeSalary.getSalary20(), officeSalary.getSalary21(), 
			officeSalary.getSalary22(), officeSalary.getSalary23(), 
			officeSalary.getSalary24(), officeSalary.getSalary25(), 
			officeSalary.getSalary26(), officeSalary.getSalary27(), 
			officeSalary.getSalary28(), officeSalary.getSalary29(), 
			officeSalary.getSalary30(), officeSalary.getSalary31(), 
			officeSalary.getSalary32(), officeSalary.getSalary33(), 
			officeSalary.getSalary34(), officeSalary.getSalary35(), 
			officeSalary.getSalary36(), officeSalary.getSalary37(), 
			officeSalary.getSalary38(), officeSalary.getSalary39(), 
			officeSalary.getSalary40(), officeSalary.getSalary41(), 
			officeSalary.getSalary42(), officeSalary.getSalary43(), 
			officeSalary.getSalary44(), officeSalary.getSalary45(), 
			officeSalary.getSalary46(), officeSalary.getSalary47(), 
			officeSalary.getSalary48(), officeSalary.getSalary49(), 
			officeSalary.getSalary50(), officeSalary.getSalary51(), 
			officeSalary.getSalary52(), officeSalary.getSalary53(), 
			officeSalary.getSalary54(), officeSalary.getSalary55(), 
			officeSalary.getSalary56(), officeSalary.getSalary57(), 
			officeSalary.getSalary58(), officeSalary.getSalary59(), 
			officeSalary.getSalary60(), officeSalary.getSalary61(), 
			officeSalary.getSalary62(), officeSalary.getSalary63(), 
			officeSalary.getSalary64(), officeSalary.getSalary65(), 
			officeSalary.getSalary66(), officeSalary.getSalary67(), 
			officeSalary.getSalary68(), officeSalary.getSalary69(), 
			officeSalary.getSalary70(), officeSalary.getSalary71(), 
			officeSalary.getSalary72(), officeSalary.getSalary73(), 
			officeSalary.getSalary74(), officeSalary.getSalary75(), 
			officeSalary.getSalary76(), officeSalary.getSalary77(), 
			officeSalary.getSalary78(), officeSalary.getSalary79(), 
			officeSalary.getSalary80(), officeSalary.getSalary81(), 
			officeSalary.getSalary82(), officeSalary.getSalary83(), 
			officeSalary.getSalary84(), officeSalary.getSalary85(), 
			officeSalary.getSalary86(), officeSalary.getSalary87(), 
			officeSalary.getSalary88(), officeSalary.getSalary89(), 
			officeSalary.getSalary90(), officeSalary.getSalary91(), 
			officeSalary.getSalary92(), officeSalary.getSalary93(), 
			officeSalary.getSalary94(), officeSalary.getSalary95(), 
			officeSalary.getSalary96(), officeSalary.getSalary97()
		};
		update(sql, args);
		return officeSalary;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_salary where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeSalary officeSalary){
		String sql = "update office_salary set import_id = ?, unit_id = ?, user_id = ?, serial_numbers = ?, realname = ?, cardnumber = ?, salary_1 = ?, salary_2 = ?, salary_3 = ?, salary_4 = ?, salary_5 = ?, salary_6 = ?, salary_7 = ?, salary_8 = ?, salary_9 = ?, salary_10 = ?, salary_11 = ?, salary_12 = ?, salary_13 = ?, salary_14 = ?, salary_15 = ?, salary_16 = ?, salary_17 = ?, salary_18 = ?, salary_19 = ?, salary_20 = ?, salary_21 = ?, salary_22 = ?, salary_23 = ?, salary_24 = ?, salary_25 = ?, salary_26 = ?, salary_27 = ?, salary_28 = ?, salary_29 = ?, salary_30 = ?, salary_31 = ?, salary_32 = ?, salary_33 = ?, salary_34 = ?, salary_35 = ?, salary_36 = ?, salary_37 = ?, salary_38 = ?, salary_39 = ?, salary_40 = ?, salary_41 = ?, salary_42 = ?, salary_43 = ?, salary_44 = ?, salary_45 = ?, salary_46 = ?, salary_47 = ?, salary_48 = ?, salary_49 = ?, salary_50 = ?, salary_51 = ?, salary_52 = ?, salary_53 = ?, salary_54 = ?, salary_55 = ?, salary_56 = ?, salary_57 = ?, salary_58 = ?, salary_59 = ?, salary_60 = ?, salary_61 = ?, salary_62 = ?, salary_63 = ?, salary_64 = ?, salary_65 = ?, salary_66 = ?, salary_67 = ?, salary_68 = ?, salary_69 = ?, salary_70 = ?, salary_71 = ?, salary_72 = ?, salary_73 = ?, salary_74 = ?, salary_75 = ?, salary_76 = ?, salary_77 = ?, salary_78 = ?, salary_79 = ?, salary_80 = ?, salary_81 = ?, salary_82 = ?, salary_83 = ?, salary_84 = ?, salary_85 = ?, salary_86 = ?, salary_87 = ?, salary_88 = ?, salary_89 = ?, salary_90 = ?, salary_91 = ?, salary_92 = ?, salary_93 = ?, salary_94 = ?, salary_95 = ?, salary_96 = ?, salary_97 = ? where id = ?";
		Object[] args = new Object[]{
			officeSalary.getImportId(), officeSalary.getUnitId(), 
			officeSalary.getUserId(), officeSalary.getSerialNumbers(), 
			officeSalary.getRealname(), officeSalary.getCardnumber(), 
			officeSalary.getSalary1(), officeSalary.getSalary2(), 
			officeSalary.getSalary3(), officeSalary.getSalary4(), 
			officeSalary.getSalary5(), officeSalary.getSalary6(), 
			officeSalary.getSalary7(), officeSalary.getSalary8(), 
			officeSalary.getSalary9(), officeSalary.getSalary10(), 
			officeSalary.getSalary11(), officeSalary.getSalary12(), 
			officeSalary.getSalary13(), officeSalary.getSalary14(), 
			officeSalary.getSalary15(), officeSalary.getSalary16(), 
			officeSalary.getSalary17(), officeSalary.getSalary18(), 
			officeSalary.getSalary19(), officeSalary.getSalary20(), 
			officeSalary.getSalary21(), officeSalary.getSalary22(), 
			officeSalary.getSalary23(), officeSalary.getSalary24(), 
			officeSalary.getSalary25(), officeSalary.getSalary26(), 
			officeSalary.getSalary27(), officeSalary.getSalary28(), 
			officeSalary.getSalary29(), officeSalary.getSalary30(), 
			officeSalary.getSalary31(), officeSalary.getSalary32(), 
			officeSalary.getSalary33(), officeSalary.getSalary34(), 
			officeSalary.getSalary35(), officeSalary.getSalary36(), 
			officeSalary.getSalary37(), officeSalary.getSalary38(), 
			officeSalary.getSalary39(), officeSalary.getSalary40(), 
			officeSalary.getSalary41(), officeSalary.getSalary42(), 
			officeSalary.getSalary43(), officeSalary.getSalary44(), 
			officeSalary.getSalary45(), officeSalary.getSalary46(), 
			officeSalary.getSalary47(), officeSalary.getSalary48(), 
			officeSalary.getSalary49(), officeSalary.getSalary50(), 
			officeSalary.getSalary51(), officeSalary.getSalary52(), 
			officeSalary.getSalary53(), officeSalary.getSalary54(), 
			officeSalary.getSalary55(), officeSalary.getSalary56(), 
			officeSalary.getSalary57(), officeSalary.getSalary58(), 
			officeSalary.getSalary59(), officeSalary.getSalary60(), 
			officeSalary.getSalary61(), officeSalary.getSalary62(), 
			officeSalary.getSalary63(), officeSalary.getSalary64(), 
			officeSalary.getSalary65(), officeSalary.getSalary66(), 
			officeSalary.getSalary67(), officeSalary.getSalary68(), 
			officeSalary.getSalary69(), officeSalary.getSalary70(), 
			officeSalary.getSalary71(), officeSalary.getSalary72(), 
			officeSalary.getSalary73(), officeSalary.getSalary74(), 
			officeSalary.getSalary75(), officeSalary.getSalary76(), 
			officeSalary.getSalary77(), officeSalary.getSalary78(), 
			officeSalary.getSalary79(), officeSalary.getSalary80(), 
			officeSalary.getSalary81(), officeSalary.getSalary82(), 
			officeSalary.getSalary83(), officeSalary.getSalary84(), 
			officeSalary.getSalary85(), officeSalary.getSalary86(), 
			officeSalary.getSalary87(), officeSalary.getSalary88(), 
			officeSalary.getSalary89(), officeSalary.getSalary90(), 
			officeSalary.getSalary91(), officeSalary.getSalary92(), 
			officeSalary.getSalary93(), officeSalary.getSalary94(), 
			officeSalary.getSalary95(), officeSalary.getSalary96(), 
			officeSalary.getSalary97(), officeSalary.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeSalary getOfficeSalaryById(String id){
		String sql = "select * from office_salary where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeSalary> getOfficeSalaryMapByIds(String[] ids){
		String sql = "select * from office_salary where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeSalary> getOfficeSalaryList(){
		String sql = "select * from office_salary";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeSalary> getOfficeSalaryPage(Pagination page){
		String sql = "select * from office_salary";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeSalary> getOfficeSalaryByUnitIdList(String unitId){
		String sql = "select * from office_salary where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeSalary> getOfficeSalaryByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_salary where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}

	@Override
	public void batchInsert(List<OfficeSalary> officeSalarys) {
		List<Object[]> listOfArgs = new ArrayList<Object[]>();
		for (OfficeSalary officeSalary : officeSalarys) {
			if(StringUtils.isBlank(officeSalary.getId())){
				officeSalary.setId(createId());
			}
			listOfArgs.add(new Object[]{
					officeSalary.getId(), officeSalary.getImportId(),officeSalary.getUserId(),  
					officeSalary.getUnitId(), officeSalary.getSerialNumbers(), 
					officeSalary.getRealname(), officeSalary.getCardnumber(), 
					officeSalary.getSalary1(), officeSalary.getSalary2(), 
					officeSalary.getSalary3(), officeSalary.getSalary4(), 
					officeSalary.getSalary5(), officeSalary.getSalary6(), 
					officeSalary.getSalary7(), officeSalary.getSalary8(), 
					officeSalary.getSalary9(), officeSalary.getSalary10(), 
					officeSalary.getSalary11(), officeSalary.getSalary12(), 
					officeSalary.getSalary13(), officeSalary.getSalary14(), 
					officeSalary.getSalary15(), officeSalary.getSalary16(), 
					officeSalary.getSalary17(), officeSalary.getSalary18(), 
					officeSalary.getSalary19(), officeSalary.getSalary20(), 
					officeSalary.getSalary21(), officeSalary.getSalary22(), 
					officeSalary.getSalary23(), officeSalary.getSalary24(), 
					officeSalary.getSalary25(), officeSalary.getSalary26(), 
					officeSalary.getSalary27(), officeSalary.getSalary28(), 
					officeSalary.getSalary29(), officeSalary.getSalary30(), 
					officeSalary.getSalary31(), officeSalary.getSalary32(), 
					officeSalary.getSalary33(), officeSalary.getSalary34(), 
					officeSalary.getSalary35(), officeSalary.getSalary36(), 
					officeSalary.getSalary37(), officeSalary.getSalary38(), 
					officeSalary.getSalary39(), officeSalary.getSalary40(), 
					officeSalary.getSalary41(), officeSalary.getSalary42(), 
					officeSalary.getSalary43(), officeSalary.getSalary44(), 
					officeSalary.getSalary45(), officeSalary.getSalary46(), 
					officeSalary.getSalary47(), officeSalary.getSalary48(), 
					officeSalary.getSalary49(), officeSalary.getSalary50(), 
					officeSalary.getSalary51(), officeSalary.getSalary52(), 
					officeSalary.getSalary53(), officeSalary.getSalary54(), 
					officeSalary.getSalary55(), officeSalary.getSalary56(), 
					officeSalary.getSalary57(), officeSalary.getSalary58(), 
					officeSalary.getSalary59(), officeSalary.getSalary60(), 
					officeSalary.getSalary61(), officeSalary.getSalary62(), 
					officeSalary.getSalary63(), officeSalary.getSalary64(), 
					officeSalary.getSalary65(), officeSalary.getSalary66(), 
					officeSalary.getSalary67(), officeSalary.getSalary68(), 
					officeSalary.getSalary69(), officeSalary.getSalary70(), 
					officeSalary.getSalary71(), officeSalary.getSalary72(), 
					officeSalary.getSalary73(), officeSalary.getSalary74(), 
					officeSalary.getSalary75(), officeSalary.getSalary76(), 
					officeSalary.getSalary77(), officeSalary.getSalary78(), 
					officeSalary.getSalary79(), officeSalary.getSalary80(), 
					officeSalary.getSalary81(), officeSalary.getSalary82(), 
					officeSalary.getSalary83(), officeSalary.getSalary84(), 
					officeSalary.getSalary85(), officeSalary.getSalary86(), 
					officeSalary.getSalary87(), officeSalary.getSalary88(), 
					officeSalary.getSalary89(), officeSalary.getSalary90(), 
					officeSalary.getSalary91(), officeSalary.getSalary92(), 
					officeSalary.getSalary93(), officeSalary.getSalary94(), 
					officeSalary.getSalary95(), officeSalary.getSalary96(), 
					officeSalary.getSalary97()	
			});
		}
		int[] argTypes = new int[] { 
				Types.CHAR, Types.CHAR, Types.CHAR,Types.CHAR,
				Types.VARCHAR, Types.VARCHAR,Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR,Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR,Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR,Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR,Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR,Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR,Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR,Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR,Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR,Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR,Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR,Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR,Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR,Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR,Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR,Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR,Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR,Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR,Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
		};
		//String sql = "insert into office_salary(id, import_id, user_id,unit_id, serial_numbers, realname, cardnumber, salary_1, salary_2, salary_3, salary_4, salary_5, salary_6, salary_7, salary_8, salary_9, salary_10, salary_11, salary_12, salary_13, salary_14, salary_15, salary_16, salary_17, salary_18, salary_19, salary_20, salary_21, salary_22, salary_23, salary_24, salary_25, salary_26, salary_27, salary_28, salary_29, salary_30, salary_31, salary_32, salary_33, salary_34, salary_35, salary_36, salary_37, salary_38, salary_39, salary_40, salary_41, salary_42, salary_43, salary_44, salary_45, salary_46, salary_47) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String sql = "insert into office_salary(id, import_id, user_id, unit_id, serial_numbers, realname, cardnumber, salary_1, salary_2, salary_3, salary_4, salary_5, salary_6, salary_7, salary_8, salary_9, salary_10, salary_11, salary_12, salary_13, salary_14, salary_15, salary_16, salary_17, salary_18, salary_19, salary_20, salary_21, salary_22, salary_23, salary_24, salary_25, salary_26, salary_27, salary_28, salary_29, salary_30, salary_31, salary_32, salary_33, salary_34, salary_35, salary_36, salary_37, salary_38, salary_39, salary_40, salary_41, salary_42, salary_43, salary_44, salary_45, salary_46, salary_47, salary_48, salary_49, salary_50, salary_51, salary_52, salary_53, salary_54, salary_55, salary_56, salary_57, salary_58, salary_59, salary_60, salary_61, salary_62, salary_63, salary_64, salary_65, salary_66, salary_67, salary_68, salary_69, salary_70, salary_71, salary_72, salary_73, salary_74, salary_75, salary_76, salary_77, salary_78, salary_79, salary_80, salary_81, salary_82, salary_83, salary_84, salary_85, salary_86, salary_87, salary_88, salary_89, salary_90, salary_91, salary_92, salary_93, salary_94, salary_95, salary_96, salary_97) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		batchUpdate(sql, listOfArgs, argTypes);
	}

	@Override
	public List<OfficeSalary> getOfficeSalaryByUnitIdId(String unitId,
			String importId,Pagination page) {
		String sql = "select * from office_salary where unit_id = ? and import_id=?";
		if(page!=null){
			return query(sql, new Object[]{unitId,importId}, new MultiRow(), page);
		}else return query(sql, new Object[]{unitId,importId }, new MultiRow());
	}

	@Override
	public List<OfficeSalary> getOfficeSalaryByUnitIdAndCardnumber(
			String unitId, String importId, String userId,String cardNumber,Pagination page) {
		StringBuffer sql=new StringBuffer("select * from office_salary where unit_id=? ");
		List<Object> args=new ArrayList<Object>();
		args.add(unitId);
		if(StringUtils.isNotBlank(userId)){
			sql.append(" and user_id=?");
			args.add(userId);
		}
		if(StringUtils.isNotBlank(importId)){
			sql.append(" and import_id=?");
			args.add(importId);
		}
		if(StringUtils.isNotBlank(cardNumber)){
			sql.append(" and cardnumber=?");
			args.add(cardNumber);
		}
		if(page==null){
			return query(sql.toString(), args.toArray(), new MultiRow());
		}else{
			return query(sql.toString(), args.toArray(), new MultiRow(), page);
		}
	}

	@Override
	public List<OfficeSalary> getOfficeSalaryListByImportId(String unitId,
			String[] importId) {
		String sql = "select * from office_salary where unit_id = ? and import_id in";
		return queryForInSQL(sql, new String[]{unitId}, importId, new MultiRow());
	}
	
	public static void main(String[] args) {//<field define="列项51" name="salary48" dbname="salary_48" type="String(20)"/>
		for (int i = 51; i <= 100; i++) {
			System.out.println("<field define=\"列项"+i+"\""+" name=\"salary"+(i-3)+"\""+" dbname=\"salary_"+(i-3)+"\""+" type=\"String(20)\"/>" );
		}
	}
	
}

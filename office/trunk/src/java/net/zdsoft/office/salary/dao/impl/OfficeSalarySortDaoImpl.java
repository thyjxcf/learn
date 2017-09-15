package net.zdsoft.office.salary.dao.impl;

import java.sql.*;
import java.util.*;

import org.apache.commons.lang3.StringUtils;

import net.zdsoft.office.salary.entity.OfficeSalarySort;
import net.zdsoft.office.salary.dao.OfficeSalarySortDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_salary_sort
 * @author 
 * 
 */
public class OfficeSalarySortDaoImpl extends BaseDao<OfficeSalarySort> implements OfficeSalarySortDao{

	@Override
	public OfficeSalarySort setField(ResultSet rs) throws SQLException{
		OfficeSalarySort officeSalarySort = new OfficeSalarySort();
		officeSalarySort.setId(rs.getString("id"));
		officeSalarySort.setUnitId(rs.getString("unit_id"));
		officeSalarySort.setImportId(rs.getString("import_id"));
		officeSalarySort.setCreateTime(rs.getTimestamp("create_time"));
		officeSalarySort.setSortAmount(rs.getInt("sort_amount"));
		officeSalarySort.setSort1(rs.getString("sort_1"));
		officeSalarySort.setSort2(rs.getString("sort_2"));
		officeSalarySort.setSort3(rs.getString("sort_3"));
		officeSalarySort.setSort4(rs.getString("sort_4"));
		officeSalarySort.setSort5(rs.getString("sort_5"));
		officeSalarySort.setSort6(rs.getString("sort_6"));
		officeSalarySort.setSort7(rs.getString("sort_7"));
		officeSalarySort.setSort8(rs.getString("sort_8"));
		officeSalarySort.setSort9(rs.getString("sort_9"));
		officeSalarySort.setSort10(rs.getString("sort_10"));
		officeSalarySort.setSort11(rs.getString("sort_11"));
		officeSalarySort.setSort12(rs.getString("sort_12"));
		officeSalarySort.setSort13(rs.getString("sort_13"));
		officeSalarySort.setSort14(rs.getString("sort_14"));
		officeSalarySort.setSort15(rs.getString("sort_15"));
		officeSalarySort.setSort16(rs.getString("sort_16"));
		officeSalarySort.setSort17(rs.getString("sort_17"));
		officeSalarySort.setSort18(rs.getString("sort_18"));
		officeSalarySort.setSort19(rs.getString("sort_19"));
		officeSalarySort.setSort20(rs.getString("sort_20"));
		officeSalarySort.setSort21(rs.getString("sort_21"));
		officeSalarySort.setSort22(rs.getString("sort_22"));
		officeSalarySort.setSort23(rs.getString("sort_23"));
		officeSalarySort.setSort24(rs.getString("sort_24"));
		officeSalarySort.setSort25(rs.getString("sort_25"));
		officeSalarySort.setSort26(rs.getString("sort_26"));
		officeSalarySort.setSort27(rs.getString("sort_27"));
		officeSalarySort.setSort28(rs.getString("sort_28"));
		officeSalarySort.setSort29(rs.getString("sort_29"));
		officeSalarySort.setSort30(rs.getString("sort_30"));
		officeSalarySort.setSort31(rs.getString("sort_31"));
		officeSalarySort.setSort32(rs.getString("sort_32"));
		officeSalarySort.setSort33(rs.getString("sort_33"));
		officeSalarySort.setSort34(rs.getString("sort_34"));
		officeSalarySort.setSort35(rs.getString("sort_35"));
		officeSalarySort.setSort36(rs.getString("sort_36"));
		officeSalarySort.setSort37(rs.getString("sort_37"));
		officeSalarySort.setSort38(rs.getString("sort_38"));
		officeSalarySort.setSort39(rs.getString("sort_39"));
		officeSalarySort.setSort40(rs.getString("sort_40"));
		officeSalarySort.setSort41(rs.getString("sort_41"));
		officeSalarySort.setSort42(rs.getString("sort_42"));
		officeSalarySort.setSort43(rs.getString("sort_43"));
		officeSalarySort.setSort44(rs.getString("sort_44"));
		officeSalarySort.setSort45(rs.getString("sort_45"));
		officeSalarySort.setSort46(rs.getString("sort_46"));
		officeSalarySort.setSort47(rs.getString("sort_47"));
		officeSalarySort.setSort48(rs.getString("sort_48"));
		officeSalarySort.setSort49(rs.getString("sort_49"));
		officeSalarySort.setSort50(rs.getString("sort_50"));
		officeSalarySort.setSort51(rs.getString("sort_51"));
		officeSalarySort.setSort52(rs.getString("sort_52"));
		officeSalarySort.setSort53(rs.getString("sort_53"));
		officeSalarySort.setSort54(rs.getString("sort_54"));
		officeSalarySort.setSort55(rs.getString("sort_55"));
		officeSalarySort.setSort56(rs.getString("sort_56"));
		officeSalarySort.setSort57(rs.getString("sort_57"));
		officeSalarySort.setSort58(rs.getString("sort_58"));
		officeSalarySort.setSort59(rs.getString("sort_59"));
		officeSalarySort.setSort60(rs.getString("sort_60"));
		officeSalarySort.setSort61(rs.getString("sort_61"));
		officeSalarySort.setSort62(rs.getString("sort_62"));
		officeSalarySort.setSort63(rs.getString("sort_63"));
		officeSalarySort.setSort64(rs.getString("sort_64"));
		officeSalarySort.setSort65(rs.getString("sort_65"));
		officeSalarySort.setSort66(rs.getString("sort_66"));
		officeSalarySort.setSort67(rs.getString("sort_67"));
		officeSalarySort.setSort68(rs.getString("sort_68"));
		officeSalarySort.setSort69(rs.getString("sort_69"));
		officeSalarySort.setSort70(rs.getString("sort_70"));
		officeSalarySort.setSort71(rs.getString("sort_71"));
		officeSalarySort.setSort72(rs.getString("sort_72"));
		officeSalarySort.setSort73(rs.getString("sort_73"));
		officeSalarySort.setSort74(rs.getString("sort_74"));
		officeSalarySort.setSort75(rs.getString("sort_75"));
		officeSalarySort.setSort76(rs.getString("sort_76"));
		officeSalarySort.setSort77(rs.getString("sort_77"));
		officeSalarySort.setSort78(rs.getString("sort_78"));
		officeSalarySort.setSort79(rs.getString("sort_79"));
		officeSalarySort.setSort80(rs.getString("sort_80"));
		officeSalarySort.setSort81(rs.getString("sort_81"));
		officeSalarySort.setSort82(rs.getString("sort_82"));
		officeSalarySort.setSort83(rs.getString("sort_83"));
		officeSalarySort.setSort84(rs.getString("sort_84"));
		officeSalarySort.setSort85(rs.getString("sort_85"));
		officeSalarySort.setSort86(rs.getString("sort_86"));
		officeSalarySort.setSort87(rs.getString("sort_87"));
		officeSalarySort.setSort88(rs.getString("sort_88"));
		officeSalarySort.setSort89(rs.getString("sort_89"));
		officeSalarySort.setSort90(rs.getString("sort_90"));
		officeSalarySort.setSort91(rs.getString("sort_91"));
		officeSalarySort.setSort92(rs.getString("sort_92"));
		officeSalarySort.setSort93(rs.getString("sort_93"));
		officeSalarySort.setSort94(rs.getString("sort_94"));
		officeSalarySort.setSort95(rs.getString("sort_95"));
		officeSalarySort.setSort96(rs.getString("sort_96"));
		officeSalarySort.setSort97(rs.getString("sort_97"));
		return officeSalarySort;
	}

	@Override
	public OfficeSalarySort save(OfficeSalarySort officeSalarySort){
		String sql = "insert into office_salary_sort(id, unit_id, import_id, create_time,sort_amount, sort_1, sort_2, sort_3, sort_4, sort_5, sort_6, sort_7, sort_8, sort_9, sort_10, sort_11, sort_12, sort_13, sort_14, sort_15, sort_16, sort_17, sort_18, sort_19, sort_20, sort_21, sort_22, sort_23, sort_24, sort_25, sort_26, sort_27, sort_28, sort_29, sort_30, sort_31, sort_32, sort_33, sort_34, sort_35, sort_36, sort_37, sort_38, sort_39, sort_40, sort_41, sort_42, sort_43, sort_44, sort_45, sort_46, sort_47, sort_48, sort_49, sort_50, sort_51, sort_52, sort_53, sort_54, sort_55, sort_56, sort_57, sort_58, sort_59, sort_60, sort_61, sort_62, sort_63, sort_64, sort_65, sort_66, sort_67, sort_68, sort_69, sort_70, sort_71, sort_72, sort_73, sort_74, sort_75, sort_76, sort_77, sort_78, sort_79, sort_80, sort_81, sort_82, sort_83, sort_84, sort_85, sort_86, sort_87, sort_88, sort_89, sort_90, sort_91, sort_92, sort_93, sort_94, sort_95, sort_96, sort_97) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeSalarySort.getId())){
			officeSalarySort.setId(createId());
		}
		Object[] args = new Object[]{
			officeSalarySort.getId(), officeSalarySort.getUnitId(), 
			officeSalarySort.getImportId(), officeSalarySort.getCreateTime(), officeSalarySort.getSortAmount(),
			officeSalarySort.getSort1(), officeSalarySort.getSort2(), 
			officeSalarySort.getSort3(), officeSalarySort.getSort4(), 
			officeSalarySort.getSort5(), officeSalarySort.getSort6(), 
			officeSalarySort.getSort7(), officeSalarySort.getSort8(), 
			officeSalarySort.getSort9(), officeSalarySort.getSort10(), 
			officeSalarySort.getSort11(), officeSalarySort.getSort12(), 
			officeSalarySort.getSort13(), officeSalarySort.getSort14(), 
			officeSalarySort.getSort15(), officeSalarySort.getSort16(), 
			officeSalarySort.getSort17(), officeSalarySort.getSort18(), 
			officeSalarySort.getSort19(), officeSalarySort.getSort20(), 
			officeSalarySort.getSort21(), officeSalarySort.getSort22(), 
			officeSalarySort.getSort23(), officeSalarySort.getSort24(), 
			officeSalarySort.getSort25(), officeSalarySort.getSort26(), 
			officeSalarySort.getSort27(), officeSalarySort.getSort28(), 
			officeSalarySort.getSort29(), officeSalarySort.getSort30(), 
			officeSalarySort.getSort31(), officeSalarySort.getSort32(), 
			officeSalarySort.getSort33(), officeSalarySort.getSort34(), 
			officeSalarySort.getSort35(), officeSalarySort.getSort36(), 
			officeSalarySort.getSort37(), officeSalarySort.getSort38(), 
			officeSalarySort.getSort39(), officeSalarySort.getSort40(), 
			officeSalarySort.getSort41(), officeSalarySort.getSort42(), 
			officeSalarySort.getSort43(), officeSalarySort.getSort44(), 
			officeSalarySort.getSort45(), officeSalarySort.getSort46(), 
			officeSalarySort.getSort47(), officeSalarySort.getSort48(), 
			officeSalarySort.getSort49(), officeSalarySort.getSort50(), 
			officeSalarySort.getSort51(), officeSalarySort.getSort52(), 
			officeSalarySort.getSort53(), officeSalarySort.getSort54(), 
			officeSalarySort.getSort55(), officeSalarySort.getSort56(), 
			officeSalarySort.getSort57(), officeSalarySort.getSort58(), 
			officeSalarySort.getSort59(), officeSalarySort.getSort60(), 
			officeSalarySort.getSort61(), officeSalarySort.getSort62(), 
			officeSalarySort.getSort63(), officeSalarySort.getSort64(), 
			officeSalarySort.getSort65(), officeSalarySort.getSort66(), 
			officeSalarySort.getSort67(), officeSalarySort.getSort68(), 
			officeSalarySort.getSort69(), officeSalarySort.getSort70(), 
			officeSalarySort.getSort71(), officeSalarySort.getSort72(), 
			officeSalarySort.getSort73(), officeSalarySort.getSort74(), 
			officeSalarySort.getSort75(), officeSalarySort.getSort76(), 
			officeSalarySort.getSort77(), officeSalarySort.getSort78(), 
			officeSalarySort.getSort79(), officeSalarySort.getSort80(), 
			officeSalarySort.getSort81(), officeSalarySort.getSort82(), 
			officeSalarySort.getSort83(), officeSalarySort.getSort84(), 
			officeSalarySort.getSort85(), officeSalarySort.getSort86(), 
			officeSalarySort.getSort87(), officeSalarySort.getSort88(), 
			officeSalarySort.getSort89(), officeSalarySort.getSort90(), 
			officeSalarySort.getSort91(), officeSalarySort.getSort92(), 
			officeSalarySort.getSort93(), officeSalarySort.getSort94(), 
			officeSalarySort.getSort95(), officeSalarySort.getSort96(), 
			officeSalarySort.getSort97()
		};
		update(sql, args);
		return officeSalarySort;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_salary_sort where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public void deletes(String[] importId) {
		String sql = "delete from office_salary_sort where import_id in";
		updateForInSQL(sql, null, importId);
	}

	@Override
	public Integer update(OfficeSalarySort officeSalarySort){
		String sql = "update office_salary_sort set unit_id = ?, import_id = ?, create_time = ?,sort_amount =?, sort_1 = ?, sort_2 = ?, sort_3 = ?, sort_4 = ?, sort_5 = ?, sort_6 = ?, sort_7 = ?, sort_8 = ?, sort_9 = ?, sort_10 = ?, sort_11 = ?, sort_12 = ?, sort_13 = ?, sort_14 = ?, sort_15 = ?, sort_16 = ?, sort_17 = ?, sort_18 = ?, sort_19 = ?, sort_20 = ?, sort_21 = ?, sort_22 = ?, sort_23 = ?, sort_24 = ?, sort_25 = ?, sort_26 = ?, sort_27 = ?, sort_28 = ?, sort_29 = ?, sort_30 = ?, sort_31 = ?, sort_32 = ?, sort_33 = ?, sort_34 = ?, sort_35 = ?, sort_36 = ?, sort_37 = ?, sort_38 = ?, sort_39 = ?, sort_40 = ?, sort_41 = ?, sort_42 = ?, sort_43 = ?, sort_44 = ?, sort_45 = ?, sort_46 = ?, sort_47 = ?, sort_48 = ?, sort_49 = ?, sort_50 = ?, sort_51 = ?, sort_52 = ?, sort_53 = ?, sort_54 = ?, sort_55 = ?, sort_56 = ?, sort_57 = ?, sort_58 = ?, sort_59 = ?, sort_60 = ?, sort_61 = ?, sort_62 = ?, sort_63 = ?, sort_64 = ?, sort_65 = ?, sort_66 = ?, sort_67 = ?, sort_68 = ?, sort_69 = ?, sort_70 = ?, sort_71 = ?, sort_72 = ?, sort_73 = ?, sort_74 = ?, sort_75 = ?, sort_76 = ?, sort_77 = ?, sort_78 = ?, sort_79 = ?, sort_80 = ?, sort_81 = ?, sort_82 = ?, sort_83 = ?, sort_84 = ?, sort_85 = ?, sort_86 = ?, sort_87 = ?, sort_88 = ?, sort_89 = ?, sort_90 = ?, sort_91 = ?, sort_92 = ?, sort_93 = ?, sort_94 = ?, sort_95 = ?, sort_96 = ?, sort_97 = ? where id = ?";
		Object[] args = new Object[]{
			officeSalarySort.getUnitId(), officeSalarySort.getImportId(), 
			officeSalarySort.getCreateTime(),officeSalarySort.getSortAmount(), officeSalarySort.getSort1(), 
			officeSalarySort.getSort2(), officeSalarySort.getSort3(), 
			officeSalarySort.getSort4(), officeSalarySort.getSort5(), 
			officeSalarySort.getSort6(), officeSalarySort.getSort7(), 
			officeSalarySort.getSort8(), officeSalarySort.getSort9(), 
			officeSalarySort.getSort10(), officeSalarySort.getSort11(), 
			officeSalarySort.getSort12(), officeSalarySort.getSort13(), 
			officeSalarySort.getSort14(), officeSalarySort.getSort15(), 
			officeSalarySort.getSort16(), officeSalarySort.getSort17(), 
			officeSalarySort.getSort18(), officeSalarySort.getSort19(), 
			officeSalarySort.getSort20(), officeSalarySort.getSort21(), 
			officeSalarySort.getSort22(), officeSalarySort.getSort23(), 
			officeSalarySort.getSort24(), officeSalarySort.getSort25(), 
			officeSalarySort.getSort26(), officeSalarySort.getSort27(), 
			officeSalarySort.getSort28(), officeSalarySort.getSort29(), 
			officeSalarySort.getSort30(), officeSalarySort.getSort31(), 
			officeSalarySort.getSort32(), officeSalarySort.getSort33(), 
			officeSalarySort.getSort34(), officeSalarySort.getSort35(), 
			officeSalarySort.getSort36(), officeSalarySort.getSort37(), 
			officeSalarySort.getSort38(), officeSalarySort.getSort39(), 
			officeSalarySort.getSort40(), officeSalarySort.getSort41(), 
			officeSalarySort.getSort42(), officeSalarySort.getSort43(), 
			officeSalarySort.getSort44(), officeSalarySort.getSort45(), 
			officeSalarySort.getSort46(), officeSalarySort.getSort47(), 
			officeSalarySort.getSort48(), officeSalarySort.getSort49(), 
			officeSalarySort.getSort50(), officeSalarySort.getSort51(), 
			officeSalarySort.getSort52(), officeSalarySort.getSort53(), 
			officeSalarySort.getSort54(), officeSalarySort.getSort55(), 
			officeSalarySort.getSort56(), officeSalarySort.getSort57(), 
			officeSalarySort.getSort58(), officeSalarySort.getSort59(), 
			officeSalarySort.getSort60(), officeSalarySort.getSort61(), 
			officeSalarySort.getSort62(), officeSalarySort.getSort63(), 
			officeSalarySort.getSort64(), officeSalarySort.getSort65(), 
			officeSalarySort.getSort66(), officeSalarySort.getSort67(), 
			officeSalarySort.getSort68(), officeSalarySort.getSort69(), 
			officeSalarySort.getSort70(), officeSalarySort.getSort71(), 
			officeSalarySort.getSort72(), officeSalarySort.getSort73(), 
			officeSalarySort.getSort74(), officeSalarySort.getSort75(), 
			officeSalarySort.getSort76(), officeSalarySort.getSort77(), 
			officeSalarySort.getSort78(), officeSalarySort.getSort79(), 
			officeSalarySort.getSort80(), officeSalarySort.getSort81(), 
			officeSalarySort.getSort82(), officeSalarySort.getSort83(), 
			officeSalarySort.getSort84(), officeSalarySort.getSort85(), 
			officeSalarySort.getSort86(), officeSalarySort.getSort87(), 
			officeSalarySort.getSort88(), officeSalarySort.getSort89(), 
			officeSalarySort.getSort90(), officeSalarySort.getSort91(), 
			officeSalarySort.getSort92(), officeSalarySort.getSort93(), 
			officeSalarySort.getSort94(), officeSalarySort.getSort95(), 
			officeSalarySort.getSort96(), officeSalarySort.getSort97(), 
			officeSalarySort.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeSalarySort getOfficeSalarySortById(String id){
		String sql = "select * from office_salary_sort where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeSalarySort> getOfficeSalarySortMapByIds(String[] ids){
		String sql = "select * from office_salary_sort where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeSalarySort> getOfficeSalarySortList(){
		String sql = "select * from office_salary_sort";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeSalarySort> getOfficeSalarySortPage(Pagination page){
		String sql = "select * from office_salary_sort";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeSalarySort> getOfficeSalarySortByUnitIdList(String unitId){
		String sql = "select * from office_salary_sort where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeSalarySort> getOfficeSalarySortByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_salary_sort where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}

	@Override
	public OfficeSalarySort getOfficeSalarySortByImportId(String importId) {
		String sql="select * from office_salary_sort where import_id = ?";
		return query(sql, new String[]{importId}, new SingleRow());
	}
	
}

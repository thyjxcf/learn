package net.zdsoft.office.desktop.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keel.util.Validators;
import net.zdsoft.office.desktop.dao.MemoDAO;
import net.zdsoft.office.desktop.entity.Memo;
import net.zdsoft.office.desktop.service.MemoService;

public class MemoServiceImpl implements MemoService {

    private MemoDAO memoDAO;

    public void setMemoDAO(MemoDAO memoDAO) {
        this.memoDAO = memoDAO;
    }

    @Override
    public void addMemo(Memo memo, String userId) {
        memo.setUserId(userId);
        memoDAO.insertMemo(memo);
    }

    @Override
    public void addMemos(Memo... memos) {
        memoDAO.insertMemos(memos);
    }

    @Override
    public void modifyMemo(Memo memo) {
        if (Validators.isBlank(memo.getId())) {
            memoDAO.insertMemo(memo);
        }
        else {
            memoDAO.updateMemo(memo);
        }
    }

    @Override
    public Memo getMemo(String id) {
        return memoDAO.findMemo(id);
    }

    @Override
    public List<Memo> getMemos(String userId, Pagination page) {
        /* YUYI MODIFY FOR 4.0 */
        DateFormat f = new SimpleDateFormat("MM月dd日");
        List<Memo> memoList = memoDAO.findMemos(userId, page);
        List<Memo> result = Collections.emptyList();
        if (null != memoList && !memoList.isEmpty()) {
            result = new ArrayList<Memo>();
            for (Memo tmpMemo : memoList) {
                tmpMemo.setTimeString(f.format(tmpMemo.getTime()));
                result.add(tmpMemo);
            }
        }
        return result;
    }

    @Override
    public List<Memo> getMemosByTime(String userId, Date startDate, Date endDate, Pagination page, boolean isDesc) {
        /* YUYI MODIFY FOR 4.0 */
        DateFormat f = new SimpleDateFormat("MM月dd日");
        List<Memo> memoList = memoDAO.findMemosByTime(userId, DateUtils.getStartDate(startDate),
                DateUtils.getEndDate(endDate), page, isDesc);
        List<Memo> result = Collections.emptyList();
        if (null != memoList && !memoList.isEmpty()) {
            result = new ArrayList<Memo>();
            for (Memo tmpMemo : memoList) {
                tmpMemo.setTimeString(f.format(tmpMemo.getTime()));
                result.add(tmpMemo);
            }
        }
        return result;
    }

    @Override
    public void removeMemos(String[] ids) {
        memoDAO.deleteMemos(ids);
    }

    @Override
    public Map<String, Integer> getMenoCount(String userId, Date startDate, Date endDate) {
        return memoDAO.findMenosCount(userId, startDate, endDate);
    }

    @Override
    public List<Memo> getMemosByCurentDay(String userId, int count) {
        Date now = new Date();
        List<Memo> result = new ArrayList<Memo>();
        List<Memo> afterMemos = memoDAO.findMemosByTime(userId, now, DateUtils.currentEndDate(), new Pagination(count,
                true), false);
        if (null == afterMemos || afterMemos.isEmpty()) {
            List<Memo> beforeMemos = memoDAO.findMemosByTime(userId, DateUtils.currentStartDate(), now, new Pagination(
                    count, true), true);
            if (null != beforeMemos && beforeMemos.size() > 1) {
                Collections.reverse(beforeMemos);
            }
            result = beforeMemos;
        }
        else if (afterMemos.size() < count) {
            result.addAll(afterMemos);
            List<Memo> beforeMemos = memoDAO.findMemosByTime(userId, DateUtils.currentStartDate(), now, new Pagination(
                    count - afterMemos.size(), true), true);
            if (null != beforeMemos && !beforeMemos.isEmpty()) {
                result.addAll(beforeMemos);
            }
            if (null != result && result.size() > 1) {
                Collections.reverse(result);
            }
        }
        else {
            result = afterMemos;
        }
        return result;
    }

    @Override
    public List<Memo> getMemosByDayDesc(String userId, String[] dateStrs, Pagination page) {
        return memoDAO.findMemosByDayDesc(userId, dateStrs, page);
    }

    @Override
    public List<Memo> getMemosByDayAsc(String userId, String[] dateStrs, Pagination page) {
        return memoDAO.findMemosByDayAsc(userId, dateStrs, page);
    }

    @Override
    public int getMemoCount(String userId, Date date) {
        return memoDAO.findMemoCount(userId, date);
    }
    
    @Override
    public int findTotalMemoCountByDate(String userId, Date date) {
        return memoDAO.findTotalMemoCountByDate(userId, date);
    }

	@Override
	public List<List<Memo>> getWeekMemos(String userId, List<Date> weekDate) {
		List<List<Memo>> memoList = new ArrayList<List<Memo>>();
		List<Memo> tempMemos = new ArrayList<Memo>();
		for (Date date : weekDate) {
			Date date1 = DateUtils.string2DateTime(DateUtils.date2StringByDay(date)+" 00:00:00");
			Date date2 = DateUtils.string2DateTime(DateUtils.date2StringByDay(date)+" 23:59:59");
			List<Memo> memos = new ArrayList<Memo>();
			memos = getMemosByTime(userId,date1,date2,null,false);
			if(memos!=null&&memos.size()>0){
				memoList.add(memos);
			}else{
				memoList.add(tempMemos);
			}
		}
		return memoList;
	}
	
    public List<Memo> getMemoListByUserId(String userId){
    	return memoDAO.getMemoListByUserId(userId);
    }

}

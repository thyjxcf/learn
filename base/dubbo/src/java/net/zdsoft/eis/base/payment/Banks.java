/* 
 * @(#)Banks.java    Created on 2013-11-1
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.payment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2013-11-1 下午05:15:45 $
 */
public class Banks {
	private static List<String[]> bigEBanks = new ArrayList<String[]>();
	private static List<String[]> smallEBanks = new ArrayList<String[]>();
	private static List<String[]> ebanks = new ArrayList<String[]>();
	private static Map<String, String> bankMap = new HashMap<String, String>();

	// 信用卡
	private static List<String[]> bigCreditBanks = new ArrayList<String[]>();

	static {
		bigEBanks.add(new String[] { "ICBC", "中国工商银行" });
		bigEBanks.add(new String[] { "BOC", "中国银行" });
		bigEBanks.add(new String[] { "CCB", "中国建设银行" });
		bigEBanks.add(new String[] { "ABC", "中国农业银行" });
		bigEBanks.add(new String[] { "PSBC", "中国邮政储蓄银行" });
		bigEBanks.add(new String[] { "COMM", "交通银行" });
		bigEBanks.add(new String[] { "CMB", "招商银行" });
		bigEBanks.add(new String[] { "CEB", "中国光大银行" });
		bigEBanks.add(new String[] { "CITIC", "中信银行" });
		bigEBanks.add(new String[] { "SDB", "深圳发展银行" });
		bigEBanks.add(new String[] { "SPDB", "上海浦东发展银行" });
		bigEBanks.add(new String[] { "CMBC", "中国民生银行" });
		bigEBanks.add(new String[] { "CIB", "兴业银行" });
		bigEBanks.add(new String[] { "SPABANK", "平安银行" });
		bigEBanks.add(new String[] { "GDB", "广发银行" });

		smallEBanks.add(new String[] { "SHRCB", "上海农村商业银行" });
		smallEBanks.add(new String[] { "SHBANK", "上海银行" });
		smallEBanks.add(new String[] { "NBBANK", "宁波银行" });
		smallEBanks.add(new String[] { "HZCB", "杭州银行" });
		smallEBanks.add(new String[] { "BJBANK", "北京银行" });
		smallEBanks.add(new String[] { "BJRCB", "北京农商银行" });
		smallEBanks.add(new String[] { "SPABANK", "平安银行" });
		smallEBanks.add(new String[] { "FDB", "富滇银行" });
		smallEBanks.add(new String[] { "WZCB", "温州银行" });
		smallEBanks.add(new String[] { "CDCB", "成都银行" });

		ebanks.addAll(bigEBanks);
		ebanks.addAll(smallEBanks);

		for (String[] bank : ebanks) {
			bankMap.put(bank[0], bank[1]);
		}

		bigCreditBanks.add(new String[] { "ICBC", "中国工商银行" });
		bigCreditBanks.add(new String[] { "BOC", "中国银行" });
		bigCreditBanks.add(new String[] { "CCB", "中国建设银行" });
		bigCreditBanks.add(new String[] { "ABC", "中国农业银行" });
	}
	
}

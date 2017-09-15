package net.zdsoft.office.goodmanage.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.action.ObjectDivAction;
import net.zdsoft.eis.base.simple.entity.SimpleObject;
import net.zdsoft.eis.frame.client.BaseEntity;
import net.zdsoft.office.goodmanage.entity.OfficeGoods;
import net.zdsoft.office.goodmanage.service.OfficeGoodsService;

@SuppressWarnings("serial")
class NameClass extends BaseEntity{
	
	private String id;
	private String name;
	
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id = id;
	}
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
}

public class GoodsDivAction extends ObjectDivAction<NameClass>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2295687758546408742L;

	private String goodsName;
	private String goodsUnit;
	private String divType;
	
	private OfficeGoodsService officeGoodsService;
	
	@Override
	protected List<NameClass> getDatasByConditon() {
		List<NameClass> list = new ArrayList<NameClass>();
		Set<String> nameSet = new HashSet<String>();
		List<OfficeGoods> goodsList = officeGoodsService.getOfficeGoodsByUnitIdList(getUnitId());
		if(StringUtils.equals(divType, "goodsName")){
			for(OfficeGoods goods : goodsList){
				if(StringUtils.isNotBlank(goods.getName())){
					nameSet.add(goods.getName());
				}
			}
			for(String name : nameSet){
				NameClass item = new NameClass();
				item.setId(name);
				item.setName(name);
				list.add(item);
			}
			if(StringUtils.isNotBlank(goodsName)){
				List<NameClass> list1 = new ArrayList<NameClass>();
				for(NameClass item : list){
					if(item.getId().indexOf(goodsName) != -1){
						list1.add(item);
					}
				}
				list = list1;
			}
		}
		if(StringUtils.equals(divType, "goodsUnit")){
			for(OfficeGoods goods : goodsList){
				if(StringUtils.isNotBlank(goods.getGoodsUnit())){
					nameSet.add(goods.getGoodsUnit());
				}
			}
			for(String name : nameSet){
				NameClass item = new NameClass();
				item.setId(name);
				item.setName(name);
				list.add(item);
			}
			if(StringUtils.isNotBlank(goodsUnit)){
				List<NameClass> list1 = new ArrayList<NameClass>();
				for(NameClass item : list){
					if(item.getId().indexOf(goodsUnit) != -1){
						list1.add(item);
					}
				}
				list = list1;
			}
		}
		return list;
	}
	
	@Override
	protected void toObject(NameClass e, SimpleObject object) {
		if (e == null) {
			return;
		}
		if (object == null) {
			return;
		}
		object.setId(e.getId());
		object.setObjectName(e.getName());
	}

	public void setOfficeGoodsService(OfficeGoodsService officeGoodsService) {
		this.officeGoodsService = officeGoodsService;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsUnit() {
		return goodsUnit;
	}

	public void setGoodsUnit(String goodsUnit) {
		this.goodsUnit = goodsUnit;
	}

	public String getDivType() {
		return divType;
	}

	public void setDivType(String divType) {
		this.divType = divType;
	}

}

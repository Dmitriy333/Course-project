package by.brashevets.entity.deal;

import java.util.ArrayList;
import java.util.List;

public class ListOfDeals {
	private List<Deal> deals = new ArrayList<Deal>();
	
	public Deal getDeal(int index){
		return deals.get(index);
	}
	public void addDeal(Deal deal){
		if(deal != null){
			deals.add(deal);
		}
	}
	public List<Deal> getDeals() {
		return deals;
	}

	public void setDeals(List<Deal> deals) {
		this.deals = deals;
	}
	
}

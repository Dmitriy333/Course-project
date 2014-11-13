package by.brashevets.dao;

import java.sql.SQLException;
import java.util.List;

import by.brashevets.entity.deal.Deal;

public interface DealDao {
	public void addDeal(Deal deal) throws SQLException;

	public void deleteDeal(Deal deal) throws SQLException;
	
	public void updateDeal(Deal deal) throws SQLException;

	public Deal getDeal(int id) throws SQLException;

	public List<Deal> getDeals() throws SQLException;

	public Deal getDealByDeal(Deal deal) throws SQLException;

	public Deal getDealByInfo(String name, String description)
			throws SQLException;

	public List<Deal> getDealByDate(String date) throws SQLException;
}

package by.brashevets.factory;

import by.brashevets.dao.DealDao;
import by.brashevets.dao.implementation.DealDaoImpl;

public class Factory {
	public static Factory instance = new Factory();
	public DealDao dealDao;

	private Factory() {

	}

	public static Factory getInstance() {
		return Factory.instance;
	}

	public DealDao getDealDao() {
		if (dealDao == null) {
			dealDao = new DealDaoImpl();
		}
		return dealDao;
	}
}

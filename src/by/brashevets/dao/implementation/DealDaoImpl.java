package by.brashevets.dao.implementation;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import by.brashevets.dao.DealDao;
import by.brashevets.entity.deal.Deal;
import by.brashevets.util.HibernateUtil;

public class DealDaoImpl implements DealDao{
	private final String NAME = "name";
	private final String DESCRIPTION = "description";
	private final String DATE = "date";
	private final String SELECT_DEAL_BY_INFO = "from Deal where name = :name and description = :description";
	private final String SELECT_DEALS_BY_DATE = "from Deal where date = :date";

	@Override
	public void addDeal(Deal deal) throws SQLException {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.save(deal);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(session != null && session.isOpen()){
				session.close();
			}
		}
	}
	@Override
	public void deleteDeal(Deal deal) throws SQLException {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.delete(deal);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(session != null && session.isOpen()){
				session.close();
			}
		}
	}

	@Override
	public Deal getDeal(int id) throws SQLException {
		Deal result = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			result = (Deal)session.load(Deal.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(session != null && session.isOpen()){
				session.close();
			}
		}
		return result;
	}
	
	@Override
	public Deal getDealByDeal(Deal deal) throws SQLException {
		Deal result = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			result = (Deal)session.load(Deal.class, deal);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(session != null && session.isOpen()){
				session.close();
			}
		}
		return result;
	}

	@Override
	public Deal getDealByInfo(String name, String description)
			throws SQLException {
		Deal deal = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery(SELECT_DEAL_BY_INFO);
			query.setParameter(NAME, name);
			query.setParameter(DESCRIPTION, description);
			deal = (Deal) query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(session != null && session.isOpen()){
				session.close();
			}
		}
		return deal;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Deal> getDeals() throws SQLException {
		List<Deal> deals = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			deals = session.createCriteria(Deal.class).list();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(session != null && session.isOpen()){
				session.close();
			}
		}
		return deals;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Deal> getDealByDate(String date) throws SQLException {
		List<Deal> deals = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery(SELECT_DEALS_BY_DATE);
			query.setParameter(DATE, date);
			deals = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(session != null && session.isOpen()){
				session.close();
			}
		}
		return deals;
	}
	@Override
	public void updateDeal(Deal deal) throws SQLException {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			session.update(deal);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(session != null && session.isOpen()){
				session.close();
			}
		}
	}

}

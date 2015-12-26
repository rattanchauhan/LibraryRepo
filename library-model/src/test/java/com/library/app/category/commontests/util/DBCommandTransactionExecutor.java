package com.library.app.category.commontests.util;

import javax.persistence.EntityManager;

import org.junit.Ignore;

import com.library.app.common.exceptions.AlreadyExistsException;

@Ignore
public class DBCommandTransactionExecutor {

	private EntityManager em;

	public DBCommandTransactionExecutor(final EntityManager em) {
		super();
		this.em = em;
	}

	public <T> T executeCommand(final DbCommand<T> dbCommand) {
		try {
			em.getTransaction().begin();
			final T toReturn = dbCommand.execute();
			em.getTransaction().commit();
			em.clear();
			return toReturn;
		} catch (final AlreadyExistsException e) {
			em.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} catch (final Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
			throw new IllegalStateException(e);
		}
	}

}

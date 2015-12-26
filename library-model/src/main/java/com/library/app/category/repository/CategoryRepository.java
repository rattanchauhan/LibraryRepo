package com.library.app.category.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.library.app.category.model.Category;

public class CategoryRepository {

	EntityManager em;

	public Category addCategory(final Category category) {
		em.persist(category);
		return category;
	}

	public Category findById(final Long id) {
		if (id == null) {
			return null;
		}
		return em.find(Category.class, id);
	}

	public void update(final Category category) {
		em.merge(category);
	}

	public boolean delete(final Long id) {
		final Category category = em.find(Category.class, id);
		if (category == null) {
			return false;
		}
		em.remove(category);
		return true;
	}

	@SuppressWarnings("unchecked")
	public List<Category> getAllCategoriesSortedByField(final String orderField) {
		if (orderField == null) {
			return null;
		}
		return em.createQuery("Select c From Category c order by c." + orderField).getResultList();
	}

	public List<Category> list() {
		return em.createQuery("From Category", Category.class).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Category> findCategoryByName(final String pattern) {
		return em.createQuery(
				"SELECT c FROM Category c WHERE c.name =:categoryName")
				.setParameter("categoryName", pattern)
				.getResultList();

	}

	@SuppressWarnings("unchecked")
	public List<Category> searchCategoriesMatchingName(final String pattern) {
		return em.createQuery(
				"SELECT c FROM Category c WHERE c.name LIKE :categoryName")
				.setParameter("categoryName", "%" + pattern + "%")
				.getResultList();

	}

	public boolean alreadyExistsByName(final Category category) {
		final StringBuilder jpql = new StringBuilder("SELECT 1 FROM Category c WHERE c.name  =:categoryName");
		if (category.getId() != null) {
			jpql.append("AND c.id =:id");
		}
		final Query query = em.createQuery(jpql.toString());
		query.setParameter("categoryName", category.getName());
		if (category.getId() != null) {
			query.setParameter("id", category.getId());
		}
		return query.setMaxResults(1).getResultList().size() > 0 ? true : false;
	}

	public boolean existsById(final Long id) {
		if (id == null) {
			return false;
		}
		return em.find(Category.class, id) != null ? true : false;
	}
}

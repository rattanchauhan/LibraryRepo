package com.library.app.category.repository;

import java.util.List;

import javax.persistence.EntityManager;

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

	@SuppressWarnings("unchecked")
	public List<Category> findAllCategoriesSortedByField(final String orderField) {
		if (orderField == null) {
			return null;
		}
		return em.createQuery("Select c From Category c order by c." + orderField).getResultList();
	}

	public List<Category> list() {
		return em.createQuery("From Category", Category.class).getResultList();
	}
}

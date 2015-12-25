package com.library.app.category.repository;

import javax.persistence.EntityManager;

import com.library.app.category.model.Category;

public class CategoryRepository {

	EntityManager em;

	public Category addCategory(final Category category) {
		em.persist(category);
		return category;
	}

	public Category findById(final Long categoryAddedId) {
		return em.find(Category.class, categoryAddedId);
	}
}

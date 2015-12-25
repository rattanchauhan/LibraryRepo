package com.library.app.category.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.library.app.category.commontests.CategoryForTestsRepository;
import com.library.app.category.model.Category;

public class CategoryRepositoryUTest {

	private EntityManagerFactory emf;
	private EntityManager em;
	private CategoryRepository categoryRepository;

	@Before
	public void initTestCase() {
		emf = Persistence.createEntityManagerFactory("libraryPU");
		em = emf.createEntityManager();
		categoryRepository = new CategoryRepository();
		categoryRepository.em = em;
	}

	@After
	public void afterTestCase() {
		em.close();
		emf.close();
	}

	@Test
	public void addCategoryAndFindIt() {
		Long categoryAddedId = null;
		try {
			em.getTransaction().begin();
			categoryAddedId = categoryRepository.addCategory(CategoryForTestsRepository.java()).getId();
			System.out.println("Added new category with id : " + categoryAddedId);
			Assert.assertNotNull(categoryAddedId);
			em.getTransaction().commit();
			em.clear();
		} catch (final Exception e) {
			Assert.fail("Test could not be executed. error ocurred.");
			em.getTransaction().rollback();
			e.printStackTrace();
		}

		final Category category = categoryRepository.findById(categoryAddedId);
		Assert.assertNotNull(category);
		Assert.assertTrue(category.getName() != null && !category.getName().isEmpty()
				&& category.getName().equals(CategoryForTestsRepository.java().getName()));
		System.out.println("Fetched category details for categoryId : " + categoryAddedId);
		System.out.println(category);

	}

}
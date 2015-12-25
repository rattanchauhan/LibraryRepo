package com.library.app.category.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.library.app.category.commontests.CategoryRepositoryDataMock;
import com.library.app.category.commontests.util.DBCommandTransactionExecutor;
import com.library.app.category.model.Category;

public class CategoryRepositoryUTest {

	private EntityManagerFactory emf;
	private EntityManager em;
	private CategoryRepository categoryRepository;
	private DBCommandTransactionExecutor dBCommandTransactionExecutor;

	@Before
	public void initTestCase() {
		emf = Persistence.createEntityManagerFactory("libraryPU");
		em = emf.createEntityManager();
		categoryRepository = new CategoryRepository();
		categoryRepository.em = em;
		dBCommandTransactionExecutor = new DBCommandTransactionExecutor(em);
	}

	@After
	public void afterTestCase() {
		em.close();
		emf.close();
	}

	@Test
	public void addCategoryAndFindIt() {

		final Long categoryAddedId = dBCommandTransactionExecutor.executeCommand(() -> {
			return categoryRepository.addCategory(CategoryRepositoryDataMock.java()).getId();
		});

		System.out.println("Added new category with id : " + categoryAddedId);
		Assert.assertNotNull(categoryAddedId);

		final Category category = dBCommandTransactionExecutor.executeCommand(() -> {
			return categoryRepository.findById(categoryAddedId);
		});

		Assert.assertNotNull(category);
		Assert.assertTrue(category.getName() != null && !category.getName().isEmpty()
				&& category.getName().equals(CategoryRepositoryDataMock.java().getName()));
		System.out.println("Fetched category details for categoryId : " + categoryAddedId);
		System.out.println(category);

	}

	@Test
	public void categoryNotFound() {
		final Category category1 = dBCommandTransactionExecutor.executeCommand(() -> {
			return categoryRepository.findById(555L);
		});
		final Category category2 = dBCommandTransactionExecutor.executeCommand(() -> {
			return categoryRepository.findById(null);
		});
		Assert.assertNull(category1);
		Assert.assertNull(category2);
	}

	@Test
	public void updateCategory() {
		final Long categoryAddedId = dBCommandTransactionExecutor.executeCommand(() -> {
			return categoryRepository.addCategory(CategoryRepositoryDataMock.java()).getId();
		});
		System.out.println("Added new category with id ----------------------------- " + categoryAddedId);
		System.out.println("Searching category with id ----------------------------- " + categoryAddedId);

		final Category category = dBCommandTransactionExecutor.executeCommand(() -> {
			return categoryRepository.findById(categoryAddedId);
		});

		System.out.println("Found category ----------------------------- " + categoryAddedId);
		System.out.println(category);

		Assert.assertTrue(category.getName().equals(CategoryRepositoryDataMock.java().getName()));

		System.out.println("Updating category name as Advanced Java for category with id ----------------------------- "
				+ categoryAddedId);
		category.setName("Advanced Java");
		dBCommandTransactionExecutor.executeCommand(() -> {
			categoryRepository.update(category);
			return null;
		});
		System.out.println("Searching category after update with id ----------------------------- " + categoryAddedId);
		final Category categoryReturned = dBCommandTransactionExecutor.executeCommand(() -> {
			return categoryRepository.findById(categoryAddedId);
		});
		System.out.println("Found category ----------------------------- " + categoryReturned);
		System.out.println(categoryReturned);
		Assert.assertNotNull(categoryReturned);
		Assert.assertTrue(category.getName().equals("Advanced Java"));
	}

	@Test
	public void getAllCategory() {
		dBCommandTransactionExecutor.executeCommand(() -> {
			return categoryRepository.addCategory(CategoryRepositoryDataMock.java()).getId();
		});

		dBCommandTransactionExecutor.executeCommand(() -> {
			return categoryRepository.addCategory(CategoryRepositoryDataMock.c()).getId();
		});

		dBCommandTransactionExecutor.executeCommand(() -> {
			return categoryRepository.addCategory(CategoryRepositoryDataMock.cpp()).getId();
		});

		final List<Category> categories = dBCommandTransactionExecutor.executeCommand(() -> {
			return categoryRepository.list();
		});
		System.out.println("getAllCategory test case :");
		for (final Category c : categories) {
			System.out.println(c);
		}
		Assert.assertTrue(categories != null && categories.size() == 3);
	}
}
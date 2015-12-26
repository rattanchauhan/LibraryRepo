package com.library.app.category.services.impl;

import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.library.app.category.commontests.CategoryRepositoryDataMock;
import com.library.app.category.model.Category;
import com.library.app.category.repository.CategoryRepository;
import com.library.app.category.services.CategoryService;
import com.library.app.common.exceptions.AlreadyExistsException;
import com.library.app.common.exceptions.CategoryNotFoundException;
import com.library.app.common.exceptions.FieldNotValidException;

public class CategoryServiceUTest {
	private CategoryService categoryService;
	private Validator validator;
	private CategoryRepository categoryRepository;

	@Before
	public void initTestCase() {
		validator = Validation.buildDefaultValidatorFactory().getValidator();
		categoryRepository = Mockito.mock(CategoryRepository.class);
		categoryService = new CategoryServiceImpl();
		((CategoryServiceImpl) categoryService).validator = validator;
		((CategoryServiceImpl) categoryService).categoryRepository = categoryRepository;
	}

	@Test(expected = FieldNotValidException.class)
	public void addCategoryWithNullName() {
		try {
			categoryService.add(new Category());
		} catch (final FieldNotValidException e) {
			Assert.assertTrue("throws FieldNotValidException", e.getFieldName().equalsIgnoreCase("name"));
			throw e;
		}
	}

	@Test(expected = FieldNotValidException.class)
	public void addCategoryWithShortName() {
		try {
			final Category category = new Category();
			category.setName("I");
			categoryService.add(category);
		} catch (final FieldNotValidException e) {
			Assert.assertTrue("throws FieldNotValidException", e.getFieldName().equalsIgnoreCase("name"));
			throw e;
		}
	}

	@Test(expected = FieldNotValidException.class)
	public void addCategoryWithLongName() {
		try {
			final Category category = new Category();
			category.setName("itsaverylongnameforacategoryitsaverylongnameforacategoryitsaverylongnameforacategory");
			categoryService.add(category);
		} catch (final FieldNotValidException e) {
			Assert.assertTrue("throws FieldNotValidException", e.getFieldName().equalsIgnoreCase("name"));
			throw e;
		}
	}

	@Test
	public void addCategory() {
		Mockito.when(categoryRepository.alreadyExistsByName(new Category(CategoryRepositoryDataMock.java().getName())))
				.thenReturn(false);
		Mockito.when(categoryRepository.addCategory(CategoryRepositoryDataMock.java()))
				.thenReturn(CategoryRepositoryDataMock.addIdToCategory(CategoryRepositoryDataMock.java(), 1L));
		final Category categoryAdded = categoryService.add(CategoryRepositoryDataMock.java());
		Assert.assertTrue(categoryAdded.getId().equals(1L));
	}

	@Test(expected = AlreadyExistsException.class)
	public void addExistingCategory() {
		Mockito.when(categoryRepository.alreadyExistsByName(CategoryRepositoryDataMock.java())).thenReturn(true);

		categoryService.add(CategoryRepositoryDataMock.java());
	}

	@Test(expected = FieldNotValidException.class)
	public void updateCategoryWithNullName() {
		try {
			categoryService.update(new Category());
		} catch (final FieldNotValidException e) {
			Assert.assertTrue("throws FieldNotValidException", e.getFieldName().equalsIgnoreCase("name"));
			throw e;
		}
	}

	@Test(expected = FieldNotValidException.class)
	public void updateCategoryWithShortName() {
		try {
			final Category category = new Category();
			category.setName("I");
			categoryService.update(category);
		} catch (final FieldNotValidException e) {
			Assert.assertTrue("throws FieldNotValidException", e.getFieldName().equalsIgnoreCase("name"));
			throw e;
		}
	}

	@Test(expected = FieldNotValidException.class)
	public void updateCategoryWithLongName() {
		try {
			final Category category = new Category();
			category.setName("itsaverylongnameforacategoryitsaverylongnameforacategoryitsaverylongnameforacategory");
			categoryService.update(category);
		} catch (final FieldNotValidException e) {
			Assert.assertTrue("throws FieldNotValidException", e.getFieldName().equalsIgnoreCase("name"));
			throw e;
		}
	}

	@Test(expected = CategoryNotFoundException.class)
	public void updateInvalidCategory() {
		Mockito.when(categoryRepository.existsById(1L)).thenReturn(false);
		categoryService.update(CategoryRepositoryDataMock.addIdToCategory(CategoryRepositoryDataMock.java(), 1L));
	}

	@Test
	public void updateValidCategory() {
		Mockito.when(categoryRepository.existsById(1L)).thenReturn(true);
		categoryService.update(CategoryRepositoryDataMock.addIdToCategory(CategoryRepositoryDataMock.java(), 1L));

		Mockito.verify(categoryRepository)
				.update(CategoryRepositoryDataMock.addIdToCategory(CategoryRepositoryDataMock.java(), 1L));
	}

	@Test
	public void findCategoryById() {
		Mockito.when(categoryRepository.findById(1L))
				.thenReturn(CategoryRepositoryDataMock.addIdToCategory(CategoryRepositoryDataMock.java(), 1L));
		final Category cat = categoryService.findById(1L);
		Assert.assertNotNull(cat);
		Assert.assertTrue(cat.getId().equals(1L));
		Mockito.verify(categoryRepository).findById(1L);
	}

	@Test(expected = CategoryNotFoundException.class)
	public void findCategoryByIdNotFound() {
		Mockito.when(categoryRepository.findById(2L))
				.thenReturn(CategoryRepositoryDataMock.addIdToCategory(CategoryRepositoryDataMock.java(), 2L));
		final Category category = categoryService.findById(1L);
		if (category == null) {
			throw new CategoryNotFoundException("Category not found in the database..");
		}
	}
}

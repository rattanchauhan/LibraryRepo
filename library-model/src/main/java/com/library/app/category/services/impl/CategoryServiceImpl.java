package com.library.app.category.services.impl;

import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.library.app.category.model.Category;
import com.library.app.category.repository.CategoryRepository;
import com.library.app.category.services.CategoryService;
import com.library.app.common.exceptions.AlreadyExistsException;
import com.library.app.common.exceptions.CategoryNotFoundException;
import com.library.app.common.exceptions.FieldNotValidException;

public class CategoryServiceImpl implements CategoryService {

	Validator validator;
	CategoryRepository categoryRepository;

	@Override
	public Category add(final Category category) throws FieldNotValidException, AlreadyExistsException {
		validateCategory(category);
		if (categoryRepository.alreadyExistsByName(category)) {
			throw new AlreadyExistsException("name", "Category already exists");
		}
		return categoryRepository.addCategory(category);
	}

	@Override
	public void update(final Category category) throws FieldNotValidException, AlreadyExistsException {
		validateCategory(category);
		if (!categoryRepository.existsById(category.getId())) {
			throw new CategoryNotFoundException("Category cannnot be updated because it does not exist..");
		}
		categoryRepository.update(category);
	}

	@Override
	public Category findById(final Long id) throws FieldNotValidException {
		if (id == null) {
			throw new FieldNotValidException("id", "Category id can not be null");
		}
		return categoryRepository.findById(id);
	}

	private void validateCategory(final Category category)
			throws FieldNotValidException {
		final Set<ConstraintViolation<Category>> errors = validator.validate(category);
		final Iterator<ConstraintViolation<Category>> it = errors.iterator();
		while (it.hasNext()) {
			final ConstraintViolation<Category> err = it.next();
			throw new FieldNotValidException(err.getPropertyPath().toString(), err.getMessage());
		}
		if (categoryRepository.alreadyExistsByName(category)) {
			throw new AlreadyExistsException("Name", "This category name already exists");
		}
	}

}

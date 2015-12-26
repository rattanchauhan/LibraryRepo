package com.library.app.category.services;

import com.library.app.category.model.Category;
import com.library.app.common.exceptions.AlreadyExistsException;
import com.library.app.common.exceptions.CategoryNotFoundException;
import com.library.app.common.exceptions.FieldNotValidException;

public interface CategoryService {

	public Category add(Category category) throws FieldNotValidException, AlreadyExistsException;

	public void update(Category category) throws FieldNotValidException, CategoryNotFoundException;

	public Category findById(final Long id) throws FieldNotValidException;
}

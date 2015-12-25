package com.library.app.category.commontests;

import java.util.Arrays;
import java.util.List;

import org.junit.Ignore;

import com.library.app.category.model.Category;

@Ignore
public class CategoryRepositoryDataMock {

	public static Category java() {
		return new Category("Java");
	}

	public static Category c() {
		return new Category("C");
	}

	public static Category cSharp() {
		return new Category("C#");
	}

	public static Category dotNet() {
		return new Category(".Net");
	}

	public static Category cpp() {
		return new Category("C++");
	}

	public static Category angularJs() {
		return new Category("AngularJs");
	}

	public static List<Category> allCategories() {
		return Arrays.asList(java(), c(), cSharp(), cpp(), dotNet(), angularJs());
	}
}

package com.ecommerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.config.AppConstants;
import com.ecommerce.entites.Category;
import com.ecommerce.payloads.CategoryDTO;
import com.ecommerce.payloads.CategoryResponse;
import com.ecommerce.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping("/admin/category")
	public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody Category category) {
		CategoryDTO savedCategoryDTO = categoryService.createCategory(category);

		return new ResponseEntity<CategoryDTO>(savedCategoryDTO, HttpStatus.CREATED);
	}

	@GetMapping("/public/categories")
	public ResponseEntity<CategoryResponse> getCategories(
			@RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(defaultValue = AppConstants.SORT_CATEGORIES_BY, required = false) String sortBy,
			@RequestParam(defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {
		
		CategoryResponse categoryResponse = categoryService.getCategories(pageNumber, pageSize, sortBy, sortOrder);

		return new ResponseEntity<CategoryResponse>(categoryResponse, HttpStatus.FOUND);
	}

	@PutMapping("/admin/categories/{categoryId}")
	public ResponseEntity<CategoryDTO> updateCategory(@RequestBody Category category,
			@PathVariable Long categoryId) {
		CategoryDTO categoryDTO = categoryService.updateCategory(category, categoryId);

		return new ResponseEntity<CategoryDTO>(categoryDTO, HttpStatus.OK);
	}

	@DeleteMapping("/admin/categories/{categoryId}")
	public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
		String status = categoryService.deleteCategory(categoryId);

		return new ResponseEntity<String>(status, HttpStatus.OK);
	}

}

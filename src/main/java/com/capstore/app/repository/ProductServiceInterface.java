package com.capstore.app.repository;

import java.util.List;

import org.springframework.stereotype.Service;

import com.capstore.app.models.Product;

@Service
public interface ProductServiceInterface {

	public List<Product> allProductsList();
	public List<Product> specificCategoryProducts(String category);
	public List<Product> specificDiscountProducts(String category,String discount);
	public List<Product> searchProducts(String category);
}
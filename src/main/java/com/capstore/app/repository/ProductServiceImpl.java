package com.capstore.app.repository;
	
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.capstore.app.models.Product;


	@Service
	public class ProductServiceImpl implements ProductServiceInterface {

		//Returns all products in database 
		@Override
		public List<Product> allProductsList() {
			return productList();
		}
		
		
	    
		
		//Returns products of specific category
		@Override
		public List<Product> specificCategoryProducts(String productCategory) {
			
			List<Product> products=new ArrayList<Product>();
			if(productCategory.equalsIgnoreCase("All Products")) {
				return productList();
			}
			for(Product p:productList()) {
				if(p.getProductCategory().equals(productCategory)) {
					products.add(p);
				}
				}
			return products;
		}
		
		
		
		
	//Returns products of specific category and discount
		@Override
		public List<Product> specificDiscountProducts(String category, String discount) {
			
			List<Product> categoryProducts=specificCategoryProducts(category);
			List<Product> products=new ArrayList<Product>();
			for(Product p:categoryProducts) {
			if(discount.equalsIgnoreCase("Less than 50")) {
				if(p.getDiscount()<50) {
					products.add(p);
				}
			}
			else if(discount.equalsIgnoreCase("50-60")) {
				if(p.getDiscount()>=50&&p.getDiscount()<60) {
					products.add(p);
				}
			}
			else if(discount.equalsIgnoreCase("60-70")) {
				if(p.getDiscount()>=60 && p.getDiscount()<70) {
					products.add(p);
				}
			}
			else if(discount.equalsIgnoreCase("Greater than 70")) {
				if(p.getDiscount()>=70) {
					products.add(p);
				}
			}
			
		}
			
			return products;
			}
		

		
		@Override
		public List<Product> searchProducts(String category) {
			List<Product> allProducts=productList();
			List<Product> products=new ArrayList<Product>();
			for(Product product:allProducts) {
				if(product.getProductInfo().toLowerCase().contains(category.toLowerCase())) {
					products.add(product);
				}
			}
			return products;
		}
		
		
		
	private static List<Product> productList(){
			
			List<Product> productList=new ArrayList<Product>();
			Product p1=new Product();
			p1.setProductCategory("shoes");
			p1.setProductPrice(20);
			p1.setDiscount(10);
			Product p5=new Product();
			p5.setProductCategory("books");
			p5.setProductPrice(20);
			p5.setDiscount(10);
			p5.setProductInfo("Java Books");
			Product p2=new Product();
			p2.setProductCategory("Men Clothing");
			p2.setProductPrice(10);
			p2.setDiscount(0);
			Product p3=new Product();
			p3.setProductCategory("Women Clothing");
			p3.setProductPrice(120);
			p3.setDiscount(80);
			Product p4=new Product();
			p4.setProductCategory("Women Clothing");
			p4.setProductPrice(1205);
			p4.setDiscount(60);
			productList.add(p1);
			productList.add(p2);
			productList.add(p3);
			productList.add(p4);
			productList.add(p5);
			
			
			return productList;
			
		}
	}
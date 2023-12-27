package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CartDto;
import com.example.demo.dto.MedicineDto;
import com.example.demo.dto.ProductDto;
import com.example.demo.entity.Cart;
import com.example.demo.entity.Product;
import com.example.demo.exception.DemoException;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service(value = "cartService")
@Transactional
public class CartServiceImpl implements CartService{
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private MedicineService medicineService;
	
	@Autowired
	private ProductRepository productRepository;

	@Override
	public String addMedicinesToCart(CartDto cart) throws DemoException {
		// TODO Auto-generated method stub
		//we will get productDto's
		//u have to convert each and every dto into entity
		//adding in the basket means inserting in the database
		
		Set<Product> products = new HashSet<>();
		for(ProductDto productDTO : cart.getProducts()) {
			Product product = new Product();
			product.setMedicineId(productDTO.getMedicineDto().getMedicineId());
			product.setQuantity(productDTO.getQuantity());
			Integer id =productRepository.save(product).getProductId();
			product.setProductId(id);
			products.add(product);
		}
	
		Optional<Cart> optional = cartRepository.findByCustomerEmailId(cart.getCustomerEmailId());
		if(optional.isEmpty()) {
			Cart newCart = new Cart();
			newCart.setCustomerEmailId(cart.getCustomerEmailId());
			newCart.setProducts(products);
			cartRepository.save(newCart);
		}
		else {
			Cart existing = optional.get();
			for(Product toadd: products) {
				Boolean alreadyPresent = false;
				for(Product existingProducts: existing.getProducts()) {
					if(existingProducts.getMedicineId().equals(toadd.getMedicineId())) {
						{
							
							existingProducts.setQuantity(toadd.getQuantity()+existingProducts.getQuantity());
							productRepository.delete(toadd);
							alreadyPresent = true;
						}
					}
				}
				if(alreadyPresent==false) {
					existing.getProducts().add(toadd);
				}
			}
		}
		return "Products are successfully added to the cart ";
	}

	@Override
	public Set<ProductDto> getMedicinesFromCart(String email) throws DemoException {
		// TODO Auto-generated method stub
		Optional<Cart> optional = cartRepository.findByCustomerEmailId(email);
		Cart cart = optional.orElseThrow(()->new DemoException("CART_DOESNT_EXISTS"));
		
		if(cart.getProducts().isEmpty()) {
			throw new DemoException("Products are not available");
		}
	
		Set<ProductDto> response = new HashSet<>();
		
		Set<Product> productSet = cart.getProducts();
		
		for (Product product : productSet) {
			
			ProductDto d = new ProductDto();
			
			d.setProductId(product.getProductId());
			
			d.setQuantity(product.getQuantity());
			
			
			MedicineDto m = medicineService.getMedicineById(product.getMedicineId());
			
			d.setMedicineDto(m);
			
			response.add(d);
		}
		
		
		return response;
	}

	@Override
	public String modifyQuantityOfMedicinesInCart(String email, Integer productId, Integer quantity)
			throws DemoException {
		// TODO Auto-generated method stub
		Optional<Cart> optional = cartRepository.findByCustomerEmailId(email);
		Cart cart = optional.orElseThrow(()->new DemoException("CART_DOESNT_EXISTS"));
		
		//product existence
		if(cart.getProducts().isEmpty())
			throw new DemoException("Products Doesnt Exists");
		
		
		Product required = null;
		
		Set<Product> p = cart.getProducts();
		
		for (Product product : p) {
			
			if(product.getProductId().equals(productId)) {
				required = product;
				break;
			}
		}
		if(required == null)
			throw new DemoException("No Such products Exists");
		
		required.setQuantity(quantity);
		
		return "Quantity Updated Successfully";
	}

	@Override
	public String deleteMedicineFromCart(String email, Integer productId) throws DemoException {
		// TODO Auto-generated method stub
		
		//Cart availability
		Optional<Cart> optional = cartRepository.findByCustomerEmailId(email);
		Cart cart = optional.orElseThrow(()->new DemoException("CART_DOESNT_EXISTS"));
		
		//Products existence
		if(cart.getProducts().isEmpty())
			throw new DemoException("NO_PRODUCTS_FOUND");
		
		//required products
		Product required = new Product();
		Set<Product> p = cart.getProducts();
		
		for (Product product : p) {
			
			if(productId.equals(product.getProductId())) {
				
				required = product;
				break;
			}
		}
		if(required == null)
			throw new DemoException("NO_PRODUCT_EXISTS");
		
		//Is available
		//1.remove it from cart
		//2.remove it from repo
		
		cart.getProducts().remove(required);
		productRepository.delete(required);
		
		return "Medicine Deleted From Cart Successfully";
	}

	@Override
	public String deleteAllMedicinesFromCart(String email) throws DemoException {
		// TODO Auto-generated method stub
		Optional<Cart> optional = cartRepository.findByCustomerEmailId(email);
		Cart cart = optional.orElseThrow(()->new DemoException("NO_CART_AVAILABLE"));
		if(cart.getProducts().isEmpty()) {
			throw new DemoException("NO_PRODUCTS_ADDED TO THE CART");
		}
		List<Integer> productIds = new ArrayList<>();
		//this is iterable if needed convert into dto - set then proceed with traditional way
		cart.getProducts().parallelStream().forEach(product ->{
			productIds.add(product.getProductId());
			cart.getProducts().remove(product);
		});
		for (Integer id: productIds) {
			productRepository.deleteById(id);
		} 
		
		
		return "All the products in the cart are deleted";
	}

}

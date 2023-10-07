package com.avangenio.warehouse.service;

import java.util.List;
import java.util.UUID;

import com.avangenio.warehouse.model.Product;

/**
 * 
 * @author angel
 *
 */
public interface SectionService {
	
	/**
	 * Add a list of products to a section.
	 * @param sectionId
	 * @param products
	 */
	public void addProductsToSection(UUID sectionId, List<Product> products);
	
	/**
	 * Add product to a section given the products ids.
	 * @param sectionId
	 * @param productsId
	 */
	public void addProductsToSectionByProductIds(UUID sectionId, List<UUID> productsId);
	
	
	
}

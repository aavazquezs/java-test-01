package com.avangenio.warehouse.service;

import java.util.List;
import java.util.UUID;

import com.avangenio.warehouse.model.Product;
import com.avangenio.warehouse.model.Section;

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
	
	/**
	 * Remove a section by Id if the section's list is empty
	 * @param id
	 * @return
	 */
	public Section delete(UUID id);
	
	public Section getSectionById(UUID id);
	
}

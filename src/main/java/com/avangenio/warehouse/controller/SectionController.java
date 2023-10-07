package com.avangenio.warehouse.controller;

import java.util.List;
import java.util.UUID;

import org.apache.tomcat.util.file.ConfigurationSource.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avangenio.warehouse.model.Product;
import com.avangenio.warehouse.service.SectionService;

@RestController
@RequestMapping("/api/sections")
public class SectionController {
	
	@Autowired private SectionService sectionService;
	
	@PatchMapping("/{sectionId}/addProductsByIds")
	public void addProductsByProductIds(
			@PathVariable("sectionId") UUID sectionId,
			@RequestBody List<UUID> productsId) {
		
		sectionService.addProductsToSectionByProductIds(sectionId, productsId);
		
	}
	
	@PatchMapping("/{sectionId}/addProducts")
	public void addProducts(
			@PathVariable("sectionId") UUID sectionId,
			@RequestBody List<Product> products) {
		
		sectionService.addProductsToSection(sectionId, products);
		
	}	
}

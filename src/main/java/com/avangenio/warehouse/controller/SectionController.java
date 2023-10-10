package com.avangenio.warehouse.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avangenio.warehouse.model.Product;
import com.avangenio.warehouse.model.Section;
import com.avangenio.warehouse.service.SectionService;

@RestController
@RequestMapping("/api/section")
public class SectionController {
	
	@Autowired private SectionService sectionService;
	
	@PatchMapping("/{sectionId}/addProductsByIds")
	public void addProductsByProductIds(
			@PathVariable("sectionId") UUID sectionId,
			@RequestBody List<UUID> productsId) {
		
		sectionService.addProductsToSectionByProductIds(sectionId, productsId);
		
	}
	
	@DeleteMapping("/{sectionId}")
	public void delete(
			@PathVariable("sectionId") UUID sectionId) {
		
		sectionService.delete(sectionId);
		
	}
	
}

package com.avangenio.warehouse.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.avangenio.warehouse.model.Product;
import com.avangenio.warehouse.model.Section;
import com.avangenio.warehouse.repository.ProductRepository;
import com.avangenio.warehouse.repository.SectionRepository;
import com.avangenio.warehouse.service.SectionService;

import lombok.extern.slf4j.Slf4j;
	
@Slf4j
@Service
public class SectionServiceImpl implements SectionService{
	
	@Autowired private ProductRepository productRepository;
	
	@Autowired private SectionRepository sectionRepository;

	@Override
	public void addProductsToSection(UUID sectionId, List<Product> products) {
		
		Section section;
		
		try {
			
			section = sectionRepository.findById(sectionId).orElseThrow();
			
		} catch (NoSuchElementException e) {
			
			log.error(SectionServiceImpl.class+ " No such Section with id:{}",sectionId);
			
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such Section with id: "+sectionId.toString());
			
		}
		
		for(Product product:products) {
			
			section.getProducts().add(product);
				
		}
		
		sectionRepository.save(section);
		
	}

	@Override
	public void addProductsToSectionByProductIds(UUID sectionId, List<UUID> productsId) {
		
		Section section;
		
		try {
			
			section = sectionRepository.findById(sectionId).orElseThrow();
			
		} catch (NoSuchElementException e) {
			
			log.error(SectionServiceImpl.class+ " No such Section with id:{}",sectionId);
			
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such Section with id: "+sectionId.toString());
			
		}
		
		for(UUID id: productsId) {
			
			Product product;
			
			try {
				
				product = productRepository.findById(id).orElseThrow();
				
			} catch (NoSuchElementException e) {
				
				log.error(SectionServiceImpl.class+ " No such Product with id:{}", id);
				
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such Product with id: "+sectionId.toString());
				
			}
			
			section.getProducts().add(product);
			
		}
		
		sectionRepository.save(section);
		
	}

	@Override
	public Section delete(UUID id) {
		
		Section section = sectionRepository.getReferenceById(id);
		
		if(section.getProducts().isEmpty()) {
			
			sectionRepository.deleteById(id);
			
		}else {
			
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Section with id="+id.toString()+" is not empty");
			
		}
		
		return section;
	}

	@Override
	public Section getSectionById(UUID id) {
		return sectionRepository.findById(id).orElseThrow();
	}
	
	
	
}

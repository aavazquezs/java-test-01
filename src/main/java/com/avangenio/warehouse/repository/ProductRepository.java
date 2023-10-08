package com.avangenio.warehouse.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.avangenio.warehouse.model.Product;


@RepositoryRestResource(collectionResourceRel = "products")
public interface ProductRepository extends JpaRepository<Product, UUID>, PagingAndSortingRepository<Product, UUID> {
	
	//List<Product> findBySectionId(@Param("section") UUID sectionId);
	
	
	
}

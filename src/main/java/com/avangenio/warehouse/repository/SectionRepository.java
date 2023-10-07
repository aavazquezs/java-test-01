package com.avangenio.warehouse.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.avangenio.warehouse.model.Section;

@RepositoryRestResource(itemResourceRel = "sections")
public interface SectionRepository extends JpaRepository<Section, UUID>{
	
	/**
	 * Remove section only if it don´t have products.
	 */
	@Override
	default void delete(Section entity) {
		//remove only if it don´t have any product
		if(entity.getProducts().isEmpty()) {
			super.delete(entity);
		}
	}
}

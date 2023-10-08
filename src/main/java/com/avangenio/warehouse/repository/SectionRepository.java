package com.avangenio.warehouse.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.avangenio.warehouse.model.Section;

@RepositoryRestResource(itemResourceRel = "sections")
public interface SectionRepository extends JpaRepository<Section, UUID>{
	
	@RestResource(exported = false)
	void delete(Section entity);
	
	@RestResource(exported = false)
	void deleteById(@Param("id") UUID id);
	
}

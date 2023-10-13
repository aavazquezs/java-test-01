package com.avangenio.warehouse.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.avangenio.warehouse.model.ContainerType;
import com.avangenio.warehouse.model.Product;


@RepositoryRestResource(collectionResourceRel = "products")
public interface ProductRepository extends JpaRepository<Product, UUID>, PagingAndSortingRepository<Product, UUID> {
	
	@RestResource(path="filter")
	@Query(value="SELECT p FROM Product p WHERE " +
	"(:lote IS NULL OR p.lote = :lote) AND (:price IS NULL OR p.price = :price) AND (:isFragile IS NULL OR p.isFragile = :isFragile) AND "+
	"(:color IS NULL OR p.color = :color) AND (:containerType IS NULL OR p.containerType = :containerType)")
	List<Product> filterProducts(
			@Param("lote") String lote,
			@Param("price") Double price,
			@Param("isFragile") Boolean isFragile,
			@Param("color") String color,
			@Param("containerType") ContainerType containerType);
	
}

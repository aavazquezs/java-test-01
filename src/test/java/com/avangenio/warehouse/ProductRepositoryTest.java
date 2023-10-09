package com.avangenio.warehouse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.avangenio.warehouse.model.ContainerType;
import com.avangenio.warehouse.model.Product;
import com.avangenio.warehouse.model.ProductType;
import com.avangenio.warehouse.model.Section;
import com.avangenio.warehouse.repository.ProductRepository;
import com.avangenio.warehouse.repository.SectionRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ProductRepositoryTest {
	
	@Autowired
	private SectionRepository sectionRepository;
	
	@Autowired
	private ProductRepository repository;
	
	@Test
	public void saveProductTest() {
		Section section = Section.builder()
				.size(100)
				.productType(ProductType.ElectricalAppliance)
				.build();
		Section savedSection = sectionRepository.save(section);
		
		Product product = Product.builder()
				.color("BLUE")
				.containerType(ContainerType.Cardboard)
				.count(0)
				.isFragile(true)
				.lote("L-01")
				.price(50.0)
				.size(50)
				.section(savedSection)
				.build();
		
		Product savedProduct = repository.save(product);
		
		assertNotNull(savedProduct);
		
		assertNotNull(savedProduct.getId());
	}
	
	@Test
	public void updateProductTest() {
		
		Section section = Section.builder()
				.size(100)
				.productType(ProductType.ElectricalAppliance)
				.build();
		Section savedSection = sectionRepository.save(section);
		
		Product product = Product.builder()
				.color("BLUE")
				.containerType(ContainerType.Cardboard)
				.count(0)
				.isFragile(true)
				.lote("L-01")
				.price(50.0)
				.size(50)
				.section(savedSection)
				.build();
		
		Product savedProduct = repository.save(product);
		
		savedProduct.setColor("YELLOW");
		savedProduct.setIsFragile(false);
		
		Product updatedProduct = repository.save(savedProduct);
		
		assertEquals("YELLOW", updatedProduct.getColor());
		assertFalse(updatedProduct.getIsFragile());
		
	}
	
	@Test
	public void findallTest() {
		
		List<Product> products = repository.findAll();
		
		assertEquals(0, products.size());
		
		Section section = Section.builder()
				.size(100)
				.productType(ProductType.ElectricalAppliance)
				.build();
		Section savedSection = sectionRepository.save(section);
		
		Product product1 = Product.builder()
				.color("BLUE")
				.containerType(ContainerType.Cardboard)
				.count(0)
				.isFragile(true)
				.lote("L-01")
				.price(50.0)
				.size(50)
				.section(savedSection)
				.build();
		
		Product savedProduct1 = repository.save(product1);
		
		Product product2 = Product.builder()
				.color("RED")
				.containerType(ContainerType.Cardboard)
				.count(100)
				.isFragile(false)
				.lote("L-02")
				.price(500.0)
				.size(100)
				.section(savedSection)
				.build();
		
		Product savedProduct2 = repository.save(product2);
		
		products = repository.findAll();
		
		assertEquals(2, products.size());
	}
	
	@Test
	public void deleteTest() {
		
		Section section = Section.builder()
				.size(100)
				.productType(ProductType.ElectricalAppliance)
				.build();
		Section savedSection = sectionRepository.save(section);
		
		Product product1 = Product.builder()
				.color("BLUE")
				.containerType(ContainerType.Cardboard)
				.count(0)
				.isFragile(true)
				.lote("L-01")
				.price(50.0)
				.size(50)
				.section(savedSection)
				.build();
		
		Product savedProduct1 = repository.save(product1);
		
		List<Product> products = repository.findAll();
		
		assertEquals(1, products.size());
		
		repository.delete(savedProduct1);
		
		products = repository.findAll();
		
		assertEquals(0, products.size());
	}
	
	@Test
	public void filterProductsTest() {
		Section section = Section.builder()
				.size(100)
				.productType(ProductType.ElectricalAppliance)
				.build();
		Section savedSection = sectionRepository.save(section);
		
		Product product1 = Product.builder()
				.color("BLUE")
				.containerType(ContainerType.Cardboard)
				.count(0)
				.isFragile(true)
				.lote("L-01")
				.price(50.0)
				.size(50)
				.section(savedSection)
				.build();
		
		repository.save(product1);
		
		Product product2 = Product.builder()
				.color("RED")
				.containerType(ContainerType.Cardboard)
				.count(100)
				.isFragile(false)
				.lote("L-02")
				.price(500.0)
				.size(100)
				.section(savedSection)
				.build();
		
		repository.save(product2);
		
		Product product3 = Product.builder()
				.color("GREEN")
				.containerType(ContainerType.Glass)
				.count(1)
				.isFragile(true)
				.lote("L-02")
				.price(500.0)
				.size(100)
				.section(savedSection)
				.build();
		
		repository.save(product2);
		
		List<Product> products = repository.filterProducts(null, "L-02", null, null, null, null);
		
		assertThat(!products.isEmpty() && products.size()==2);
		
		products = repository.filterProducts(null, null, null, true, "GREEN", null);
		
		assertThat(products.size()==1 && products.get(0).getSize()==100);
	}
}

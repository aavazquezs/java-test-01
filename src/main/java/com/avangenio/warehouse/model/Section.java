package com.avangenio.warehouse.model;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Section {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	private Integer size;
	
	@Enumerated(EnumType.STRING)
	private ProductType productType;
	
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Product> products;
}

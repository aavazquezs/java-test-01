package com.avangenio.warehouse.model;

import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	private Integer size;
	
	private String color;
	
	private Double price;
	
	private Boolean isFragile;
	
	@Enumerated(EnumType.STRING)
	private ContainerType containerType;
	
	private String lote;
	
	private Integer count;
	
	@ManyToOne(optional = false)
	private Section section;
}

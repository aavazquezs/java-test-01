package com.avangenio.warehouse.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	private Integer size;
	
	private String color;
	
	private Double price;
	
	private Boolean isFragile;
	
	@Enumerated(EnumType.STRING)
	//@Column(columnDefinition = "VARCHAR(30)")
	private ContainerType containerType;
	
	private String lote;
	
	private Integer count;
}

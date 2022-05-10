package com.nguyenphitan.BeetechAPI.payload;

import com.nguyenphitan.BeetechAPI.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductResponse {
	private Product product;
	private Long quantitySelected;
}

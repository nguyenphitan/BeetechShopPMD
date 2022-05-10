package com.nguyenphitan.BeetechAPI.payload;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BillRequest {
	private Long userId;
	private List<ProductRequest> productRequests; 
}

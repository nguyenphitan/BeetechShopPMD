package com.nguyenphitan.BeetechAPI.payload;

import java.util.Date;
import java.util.List;

import com.nguyenphitan.BeetechAPI.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BillResponse {
	private Long billId;
	private User user;
	private List<ProductResponse> products;
	private Date orderDate;
	private Integer status;
	private Double total;
}

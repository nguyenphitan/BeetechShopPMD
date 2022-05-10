package com.nguyenphitan.BeetechAPI.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity@Table(name = "order_account")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderAccount {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long userId;
	
	private Date orderDate;
	
	private Integer status;
	
	public OrderAccount(Long userId) {
		this.userId = userId;
		this.orderDate = new Date();
		this.status = 0;
	}
}

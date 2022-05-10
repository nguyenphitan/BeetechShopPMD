package com.nguyenphitan.BeetechAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nguyenphitan.BeetechAPI.entity.OrderAccount;

@Repository
public interface OrderAccountRepository extends JpaRepository<OrderAccount, Long> {

}

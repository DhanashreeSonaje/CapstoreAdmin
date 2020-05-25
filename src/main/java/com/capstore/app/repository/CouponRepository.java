package com.capstore.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.capstore.app.models.Coupon;


@Repository
public interface CouponRepository extends CrudRepository<Coupon, Integer> {

	public Coupon findByCouponCode(String code);
}


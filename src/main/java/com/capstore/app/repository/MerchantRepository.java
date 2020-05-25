package com.capstore.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.capstore.app.models.MerchantDetails;

public interface MerchantRepository extends JpaRepository<MerchantDetails, Integer>{

}



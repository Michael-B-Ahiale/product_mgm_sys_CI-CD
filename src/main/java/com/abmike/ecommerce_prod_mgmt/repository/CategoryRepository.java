package com.abmike.ecommerce_prod_mgmt.repository;

import com.abmike.ecommerce_prod_mgmt.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
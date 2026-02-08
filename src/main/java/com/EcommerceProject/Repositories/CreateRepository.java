package com.EcommerceProject.Repositories;

import com.EcommerceProject.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CreateRepository extends JpaRepository<Category,Long> { //(Type oF Entity, Type of primary key)
}

package com.ecommerce.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;


@Repository
public interface UserRepo extends JpaRepository<User, Long> , JpaSpecificationExecutor<User> {

	@Query(value="select * from users where userid = :userid",nativeQuery = true)
	Optional<User> findByUsername(@Param("userid") String userName);
	
}

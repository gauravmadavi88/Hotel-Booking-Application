package com.ums3.repository;

import com.ums3.Entity.AppUser;
import com.ums3.Entity.Property;
import com.ums3.Entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select r from Review r where r.property=:property and r.appUser=:user")
    Review getReviewByPropertyAndAppUser(@Param("property")Property property, @Param("user") AppUser user);
}
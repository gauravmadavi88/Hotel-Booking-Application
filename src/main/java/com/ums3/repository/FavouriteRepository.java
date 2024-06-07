package com.ums3.repository;

import com.ums3.Entity.AppUser;
import com.ums3.Entity.Favourite;
import com.ums3.Entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FavouriteRepository extends JpaRepository<Favourite, Long> {

    @Query("select f from Favourite f where f.appUser=:user")
    List<Favourite> getUserFavourites(@Param("user")AppUser user);

    @Query("select f from Favourite f where f.appUser=:user and f.property=:property")
    Favourite checkReviewExists(@Param("user") AppUser user, @Param("property") Property property);
}
package com.ums3.repository;

import com.ums3.Entity.Image;
import com.ums3.Entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query("select i from Image i where i.property=:property")
    public List<Image> getImageByPropertyId(@Param("property")Property property);
}
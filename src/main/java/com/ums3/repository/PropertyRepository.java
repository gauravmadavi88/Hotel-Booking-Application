package com.ums3.repository;

import com.ums3.Entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    @Query("select p from Property p join Location l on p.location=l.id join Country c on p.country=c.id where l.locationName=:locationName or c.countryName=:locationName")
    List<Property> getPropertyByLocationAndCountry(@Param("locationName")String locationName);
}
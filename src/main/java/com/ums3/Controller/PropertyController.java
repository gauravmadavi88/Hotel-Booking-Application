package com.ums3.Controller;

import com.ums3.Entity.Property;
import com.ums3.repository.PropertyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/API/v3/properties")
public class PropertyController {
    private PropertyRepository propertyRepository;

    public PropertyController(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @GetMapping("/{locationName}")
    public ResponseEntity<?> getPropertyList(@PathVariable String locationName){
        List<Property> propertyByLocation = propertyRepository.getPropertyByLocationAndCountry(locationName);
        return new ResponseEntity<>(propertyByLocation, HttpStatus.OK);
    }

    @GetMapping("/Pagination")
    public ResponseEntity<?> getAllProperty(
            @RequestParam(name = "pageSize", defaultValue = "3") int pageSize,
            @RequestParam(name="pageNumber", defaultValue = "1") int pageNumber,
            @RequestParam(name="sortBy", defaultValue = "id", required = false) String propertyName,
            @RequestParam(name="sortDir", defaultValue = "asc",required = false) String sortDir
    ){
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(Sort.Direction.ASC,propertyName) : Sort.by(Sort.Direction.DESC,propertyName);
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Property> all = propertyRepository.findAll(pageable);
        List<Property> properties = all.getContent();
        return new ResponseEntity<>(properties,HttpStatus.OK);
    }

}

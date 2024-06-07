package com.ums3.Controller;

import com.ums3.Entity.AppUser;
import com.ums3.Entity.Property;
import com.ums3.Entity.Review;
import com.ums3.repository.PropertyRepository;
import com.ums3.repository.ReviewRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/API/v3/Reviews")
public class ReviewController {
    private ReviewRepository reviewRepository;
    private PropertyRepository propertyRepository;

    public ReviewController(ReviewRepository reviewRepository, PropertyRepository propertyRepository) {
        this.reviewRepository = reviewRepository;
        this.propertyRepository = propertyRepository;
    }

    @PostMapping
    public ResponseEntity<String> addReview(
            @AuthenticationPrincipal AppUser user,
            @RequestParam long property_id,
            @RequestBody Review review
            ){
        Optional<Property> byId = propertyRepository.findById(property_id);
        Property property = byId.get();
        review.setProperty(property);
        review.setAppUser(user);
        Review r = reviewRepository.getReviewByPropertyAndAppUser(property, user);
        if (r!=null){
            return ResponseEntity.ok("Review is already Added");
        }
        Review saveReview = reviewRepository.save(review);
        return ResponseEntity.ok("Review Added successfully");
    }
}

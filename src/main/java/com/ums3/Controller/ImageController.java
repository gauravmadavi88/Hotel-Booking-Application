package com.ums3.Controller;

import com.ums3.Entity.Image;
import com.ums3.Entity.Property;
import com.ums3.Service.BucketService;
import com.ums3.repository.ImageRepository;
import com.ums3.repository.PropertyRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/API/v3/Images")
public class ImageController {
    private PropertyRepository propertyRepository;
    private BucketService bucketService;
    private ImageRepository imageRepository;

    public ImageController(PropertyRepository propertyRepository, BucketService bucketService, ImageRepository imageRepository) {
        this.propertyRepository = propertyRepository;
        this.bucketService = bucketService;
        this.imageRepository = imageRepository;
    }

    @PostMapping("/saveImageUrl")
    public ResponseEntity<Image> saveImageUrl(
            @RequestParam long propertyId,
            @RequestParam String bucketName,
            MultipartFile file
    ){
        Optional<Property> byId = propertyRepository.findById(propertyId);
        Property property = byId.get();
        String imageUrl = bucketService.uploadFile(file, bucketName);
        Image image = new Image();
        image.setProperty(property);
        image.setImageUrl(imageUrl);
        Image saveImages = imageRepository.save(image);
        return ResponseEntity.ok(saveImages);

    }

    @GetMapping("/getImage")
    public ResponseEntity<List<Image>> getImagesById(@RequestParam long propertyId){
        Optional<Property> byId = propertyRepository.findById(propertyId);
        Property property = byId.get();
        List<Image> imageByPropertyId = imageRepository.getImageByPropertyId(property);
        return ResponseEntity.ok(imageByPropertyId);
    }
}

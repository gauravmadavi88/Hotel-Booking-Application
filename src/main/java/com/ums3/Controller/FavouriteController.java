package com.ums3.Controller;

import com.ums3.Entity.AppUser;
import com.ums3.Entity.Favourite;
import com.ums3.Entity.Property;
import com.ums3.repository.FavouriteRepository;
import com.ums3.repository.PropertyRepository;
import com.ums3.repository.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/API/v3/Favourites")
public class FavouriteController {

    private PropertyRepository propertyRepository;
    private FavouriteRepository favouriteRepository;

    public FavouriteController(PropertyRepository propertyRepository, FavouriteRepository favouriteRepository) {
        this.propertyRepository = propertyRepository;
        this.favouriteRepository = favouriteRepository;
    }


    @PostMapping("/addFavourite")
    public ResponseEntity<?> addFavourite(
            @AuthenticationPrincipal AppUser user,
            @RequestParam long propertyId
            ){
        Favourite favourite = new Favourite();
        Optional<Property> byId = propertyRepository.findById(propertyId);
        Property property = byId.get();
        favourite.setProperty(property);
        favourite.setAppUser(user);
        Favourite byAppUserIdAndPropertyId = favouriteRepository.checkReviewExists(user, property);
        if (byAppUserIdAndPropertyId == null) {
            favourite.setIsFavourite(true);
            Favourite savedFavourite = favouriteRepository.save(favourite);
            return new ResponseEntity<>(savedFavourite, HttpStatus.OK);
        }
        if (byAppUserIdAndPropertyId != null) {
            favouriteRepository.deleteById(byAppUserIdAndPropertyId.getId());
            return new ResponseEntity<>("Removed From Favourite", HttpStatus.OK);
        }

        return new ResponseEntity<>("Something Wrong", HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/getFavourites")
    public ResponseEntity<List<Favourite>> getUserFavouriteList(@AuthenticationPrincipal AppUser user){
        List<Favourite> userFavourites = favouriteRepository.getUserFavourites(user);
        return new ResponseEntity<>(userFavourites, HttpStatus.OK);
    }

}
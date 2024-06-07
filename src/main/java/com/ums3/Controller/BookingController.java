package com.ums3.Controller;

import com.ums3.Entity.AppUser;
import com.ums3.Entity.Booking;
import com.ums3.Entity.Property;
import com.ums3.Service.BitlyService;
import com.ums3.Service.BucketService;
import com.ums3.Service.PDFService;
import com.ums3.Service.TwilioService;
import com.ums3.repository.BookingRepository;
import com.ums3.repository.PropertyRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/API/v3/Bookings")
public class BookingController {
    private PropertyRepository propertyRepository;
    private final BookingRepository bookingRepository;
    private PDFService pdfService;
    private BucketService bucketService;
    private BitlyService bitlyService;
    private TwilioService twilioService;


    public BookingController(PropertyRepository propertyRepository,
                             BookingRepository bookingRepository, PDFService pdfService, BucketService bucketService, BitlyService bitlyService, TwilioService twilioService) {
        this.propertyRepository = propertyRepository;
        this.bookingRepository = bookingRepository;
        this.pdfService = pdfService;
        this.bucketService = bucketService;
        this.bitlyService = bitlyService;
        this.twilioService = twilioService;
    }

    @PostMapping("/bookProperty")
    public ResponseEntity<Booking> bookProperty(
            @AuthenticationPrincipal AppUser user,
            @RequestParam long propertyId,
            @RequestBody Booking booking
            ) throws IOException {
        Optional<Property> byId = propertyRepository.findById(propertyId);
        Property property = byId.get();
        Integer nightlyPrice = property.getNightlyPrice();
        int totalPrice= nightlyPrice * booking.getTotalNight();
        double tax_Temp = totalPrice * 0.18;
        int tax = (int)tax_Temp;
        int finalTotalPrice = totalPrice + tax;
        booking.setTotalPrice(finalTotalPrice);
        booking.setProperty(property);
        booking.setAppUser(user);
        Booking savedBookings = bookingRepository.save(booking);
        String  filePath = pdfService.generateBookingPdf(booking, property);
        MultipartFile multipartfile = convert(filePath);
        String amazonUrl = bucketService.uploadFile(multipartfile, "gauravmadavi88");
        System.out.println(amazonUrl);
        String shortedLink = bitlyService.shortLink(amazonUrl);
        System.out.println(shortedLink);
        twilioService.sendSMS("+919921527872","your Booking is Confirmed. Click Here " + shortedLink);

        return ResponseEntity.ok(savedBookings);

    }

    public static MultipartFile convert(String filePath) throws IOException {
        File file = new File(filePath);
        FileInputStream input = new FileInputStream(file);
        return new MockMultipartFile(file.getName(),
                file.getName(), MediaType.MULTIPART_FORM_DATA_VALUE, input);
    }
}

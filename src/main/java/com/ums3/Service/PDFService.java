package com.ums3.Service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.ums3.Entity.Property;
import com.ums3.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.ums3.Entity.Booking;


@Service
public class PDFService {

    @Autowired
    private BookingRepository bookingRepository;

    public String generateBookingPdf(Booking booking, Property property) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("E://files//BookingDetails"+booking.getId()+".pdf"));

            document.open();

            // Create a table with the appropriate number of columns
            PdfPTable table = new PdfPTable(10); // Adjust the number of columns based on your requirements
            table.setWidthPercentage(100);

            // Add table headers
            table.addCell("ID");
            table.addCell("Guest Name");
            table.addCell("Email ID");
            table.addCell("Total Price");
            table.addCell("Total Night");
            table.addCell("Booking Date");
            table.addCell("Booking Time");
            table.addCell("Check In");
            table.addCell("Meridiem");
            table.addCell("Property ID");
            table.addCell("App User ID");

            // Add booking details
            table.addCell(String.valueOf(booking.getId()));
            table.addCell(booking.getGuestName());
            table.addCell(booking.getEmailId());
            table.addCell(String.valueOf(booking.getTotalPrice()));
            table.addCell(String.valueOf(booking.getTotalNight()));
            table.addCell(String.valueOf(booking.getBookingDate()));
            table.addCell(String.valueOf(booking.getBookingTime()));
            table.addCell(String.valueOf(booking.getCheckIn()));
            table.addCell(booking.getMeridiem());
            table.addCell(booking.getProperty() != null ? String.valueOf(booking.getProperty().getId()) : "N/A");
            table.addCell(booking.getAppUser() != null ? String.valueOf(booking.getAppUser().getId()) : "N/A");

            // Add the table to the document
            document.add(table);
            document.close();
            return "E://files//BookingDetails"+booking.getId()+".pdf";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}



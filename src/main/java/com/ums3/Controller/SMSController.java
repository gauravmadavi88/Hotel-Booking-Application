package com.ums3.Controller;

import com.ums3.Service.PDFService;
import com.ums3.Service.TwilioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/API/v3/SMS")
public class SMSController {
    private TwilioService twilioService;
    private PDFService pdfService;

    public SMSController(TwilioService twilioService, PDFService pdfService) {
        this.twilioService = twilioService;
        this.pdfService = pdfService;
    }

    @GetMapping
    public void sendSms(){
        twilioService.sendSMS("+919921527872","Your Registration Successfully completed");
    }

}

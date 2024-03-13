package com.kanevsky.controllers;

import com.kanevsky.exceptions.IngestException;
import com.kanevsky.services.IExtractorService;
import jakarta.servlet.http.HttpServletRequest;
import com.kanevsky.views.ErrorView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping("/api/ingest")
public class IngestController {
    @Autowired
    private IExtractorService extractorService;

    @PostMapping(value = "/", consumes = "application/octet-stream")
    public @ResponseBody ResponseEntity ingest(HttpServletRequest request) {
        try (var inputStream = request.getInputStream()) {
            return ResponseEntity.ok(extractorService.ingestInputStream(inputStream));
        } catch (IngestException | IOException e) {
            return ResponseEntity.internalServerError().body(new ErrorView(e.getMessage()));
        }
    }
}

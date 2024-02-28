package com.kanevsky.controllers;

import com.kanevsky.exceptions.IngestException;
import com.kanevsky.services.IExtractorService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
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
    public @ResponseBody long nothing(HttpServletRequest request) {
        try (var inputStream = request.getInputStream()) {
            return extractorService.ingestInputStream(inputStream);
        } catch (IngestException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

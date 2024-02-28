package com.kanevsky.services;

import com.kanevsky.exceptions.IngestException;

import java.io.InputStream;

public interface IExtractorService {
    long ingestInputStream(InputStream inputStream) throws IngestException;
}

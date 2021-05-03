package com.examples.customerArchive.controller;

import com.examples.customerArchive.ResourceNotFoundException;
import com.examples.customerArchive.model.File;
import com.examples.customerArchive.repository.CustomerRepository;
import com.examples.customerArchive.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class FileController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private FileRepository fileRepository;

    @GetMapping("/files")
    public List<File> GetFiles() {
        return fileRepository.findAll();
    }

    @GetMapping("/customers/{customerId}/files")
    public List<File> getAllFilesByCustomerId(@PathVariable(value = "customerId") Integer customerId) {
        return fileRepository.findByCustomerId(customerId);
    }

    @PostMapping("/customers/{customerId}/files")
    public File creatFile(@PathVariable (value = "customerId") Integer customerId,
                          @RequestBody File file) {
        return customerRepository.findById(customerId).map(customer -> {
            file.setCustomer(customer);
            return fileRepository.save(file);
        }).orElseThrow(() -> new ResourceNotFoundException("CustomerId " + customerId + " not found"));
    }

    @PutMapping("/customers/{customerId}/files/{fileId}")
    public File updateFile(@PathVariable (value = "customerId") Integer customerId,
                           @PathVariable (value = "fileId") Integer fileId,
                           @RequestBody File fileRequest) {

        return fileRepository.findById(fileId).map(file -> {
            file.setFileName(fileRequest.getFileName());
            return fileRepository.save(file);
        }).orElseThrow(() -> new ResourceNotFoundException("FileId " + fileId + "not found"));
    }

    @DeleteMapping("/customers/{customerId}/files/{fileId}")
    public ResponseEntity<?> deleteFile(@PathVariable (value = "customerId") Integer customerId,
                                        @PathVariable (value = "fileId") Integer fileId) {
        return fileRepository.findByIdAndCustomerId(fileId, customerId).map(file -> {
            fileRepository.delete(file);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("File not found with id " + fileId + " and customer not found with id " + customerId));
    }

}

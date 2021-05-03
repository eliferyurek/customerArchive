package com.examples.customerArchive.repository;

import com.examples.customerArchive.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File,Integer> {
    List<File> findByCustomerId(Integer customerId);
    Optional<File> findByIdAndCustomerId(Integer id, Integer customerId);
}

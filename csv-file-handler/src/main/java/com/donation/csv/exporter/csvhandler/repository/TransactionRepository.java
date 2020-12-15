package com.donation.csv.exporter.csvhandler.repository;

import com.donation.csv.exporter.csvhandler.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
}

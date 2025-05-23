package com.bastos.ngbillings.adapters.psersistence.repository;

import com.bastos.ngbillings.adapters.psersistence.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, String> {}

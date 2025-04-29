package org.student.banking_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.student.banking_api.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    
}
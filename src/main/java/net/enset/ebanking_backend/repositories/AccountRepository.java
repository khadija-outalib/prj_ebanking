package net.enset.ebanking_backend.repositories;

import net.enset.ebanking_backend.entites.BankAccount;
import net.enset.ebanking_backend.entites.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository <BankAccount, String> {
}

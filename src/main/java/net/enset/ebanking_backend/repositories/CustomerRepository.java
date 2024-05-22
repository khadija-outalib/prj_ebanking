package net.enset.ebanking_backend.repositories;

import net.enset.ebanking_backend.entites.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository <Customer,Long> {
}

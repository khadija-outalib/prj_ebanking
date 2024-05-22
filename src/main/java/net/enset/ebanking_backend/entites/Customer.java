package net.enset.ebanking_backend.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String  email;

    @OneToMany (mappedBy = "customer" ,fetch = FetchType.LAZY)
    private List<BankAccount> bankAccounts;



}

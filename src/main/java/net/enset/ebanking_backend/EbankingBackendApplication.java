package net.enset.ebanking_backend;

import lombok.Builder;
import net.enset.ebanking_backend.entites.AccountOperation;
import net.enset.ebanking_backend.entites.CurrentAccount;
import net.enset.ebanking_backend.entites.Customer;
import net.enset.ebanking_backend.entites.SavingAccount;
import net.enset.ebanking_backend.enums.AccountStatus;
import net.enset.ebanking_backend.enums.OperationType;
import net.enset.ebanking_backend.repositories.AccountOperationRepository;
import net.enset.ebanking_backend.repositories.AccountRepository;
import net.enset.ebanking_backend.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankingBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CustomerRepository customerRepository ,
                            AccountRepository accountRepository,
                            AccountOperationRepository OperationRepository){

        return  args -> {
            Stream.of("Sara","Ali","Noura").forEach(name ->{
                Customer customer = new Customer();
                customer.setNom(name);
                customer.setEmail(name+"@gmail.com");
                customerRepository.save(customer);
            });
            customerRepository.findAll().forEach(customer -> {

                CurrentAccount cur = new CurrentAccount();
                cur.setId(UUID.randomUUID().toString());
                cur.setCreateAt(new Date());
                cur.setBalance(Math.random()*90000);
                cur.setStatus(AccountStatus.CREATED);
                cur.setCustomer(customer);
                cur.setOverDraft(90000);
                accountRepository.save(cur);

                SavingAccount sa = new SavingAccount();
                sa.setId(UUID.randomUUID().toString());
                sa.setCreateAt(new Date());
                sa.setBalance(Math.random()*90000);
                sa.setStatus(AccountStatus.CREATED);
                sa.setCustomer(customer);
                sa.setInterestRate(5.5);
                accountRepository.save(sa);
            });

            accountRepository.findAll().forEach(acc ->{
                for (var i=0;i<10 ;i++)
                {
                    AccountOperation op = new AccountOperation();
                    op.setOperationDate(new Date());
                    op.setType(Math.random()>0.5?OperationType.CREDIT:OperationType.DEBIT);
                    op.setAmount(Math.random()*12000);
                    op.setBankAccount(acc);
                     OperationRepository.save(op);



                }

            });
        };
    }
}


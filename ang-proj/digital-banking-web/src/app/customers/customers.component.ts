import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {CustomerService} from "../services/customer.service";
import {catchError, Observable, throwError} from "rxjs";
import {Customer} from "../model/customer.model";
import {FormBuilder, FormGroup, FormRecord} from "@angular/forms";

@Component({
  selector: 'app-customers',
  templateUrl: './customers.component.html',
  styleUrls: ['./customers.component.css']
})
export class CustomersComponent implements OnInit{
  customers ?:Observable<Array<Customer>>;
  erroMassage :String|undefined;
  searchFormGroup !: FormGroup;
  private router: any;

  constructor(private  customerService :CustomerService ,private fb :FormBuilder) {
  }
  ngOnInit(): void {

    this.searchFormGroup=this.fb.group({
      keyword : this.fb.control("")
    });


    this.customers = this.customerService.getCustomers().pipe(
      catchError(err => {
        this.erroMassage = err.message;
        return throwError(() => err);
      })
    );
  }

  handleSearchCustomers() {
    let kw=this.searchFormGroup?.value.keyword;
    this.customers=this.customerService.searchCustomers(kw).pipe(
      catchError(err => {
        this.erroMassage=err.message;
        return throwError(err);
      })
    );
  }



  handleCustomerAccounts(customer: Customer) {
    this.router.navigateByUrl("/customer-accounts/"+customer.id,{state :customer});
  }



}

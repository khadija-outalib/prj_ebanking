import {Component, OnInit} from '@angular/core';
import {CustomerService} from "../services/customer.service";
import {catchError, map, Observable, throwError} from "rxjs";
import {Customer} from "../model/customer.model";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Router} from "@angular/router";

@Component({
  selector: 'app-customers',
  templateUrl: './customers.component.html',
  styleUrls: ['./customers.component.css']
})
export class CustomersComponent implements OnInit{
  customers ?:Observable<Array<Customer>>;
  erroMassage :String|undefined;
  searchFormGroup !: FormGroup;


  constructor(private  customerService :CustomerService ,private fb :FormBuilder ,private router :Router) {
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

  handleDeleteCustomer(c: Customer) {
    let conf = confirm("Are you sure?");
    if(!conf) return;
    this.customerService.deleteCustomer(c.id).subscribe({
    next : (resp) => {
      this.customers=this.customers?.pipe(
        map(data =>{

          let index=data.indexOf(c);
          data.slice(index,1);
          return data;
        })
      )
      },
     error : err => {
      console.log(err);
      }
    })
    console.log(c.nom);
    return null ;
  }


  handleCustomerAccounts(customer: Customer) {

    this.router.navigateByUrl("/customer-accounts/"+customer.id,{state :customer});
  }




}











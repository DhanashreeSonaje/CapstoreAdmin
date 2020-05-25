import { Component, OnInit } from '@angular/core';
import { MerchantDetails } from 'src/app/Model/MerchantDetails';
import { CapstoreService } from 'src/app/service/capstore.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-third-party-merchant',
  templateUrl: './third-party-merchant.component.html',
  styleUrls: ['./third-party-merchant.component.css']
})
export class ThirdPartyMerchantComponent implements OnInit {

 

    name:string;
    username:string;
    password;
    phone_number : String;
    alternate_phone_number : String;
    eMail:String;
    alternate_email: String;
    check=false;
    status: string;
    rating =0;
    isApproved;
    role="Admin";
    isACtive=false;
    securityQueston=null;
    securityAnswer=null;
    products=null;
    user_address=null;
    product_feedback=null;
    coupons=null;
    merchant1: MerchantDetails= new MerchantDetails(name,this.username,this.password,this.eMail,this.role,this.isACtive,this.securityQueston,this.securityAnswer,this.phone_number,
      this.alternate_phone_number,this.alternate_email,this.products,this.user_address,this.product_feedback,this.coupons,this.isApproved);
    
    constructor( private adminService: CapstoreService, private router: Router) { }
  
    ngOnInit(): void {
    }
    async addMerchant()
    {
      console.log(this.merchant1);
     let response= await this.adminService.registerMerchant(this.merchant1)
     console.log(response);
    
     }
  
     onCheckboxValueChange():any{
      this.check=!this.check
      if(this.check){
      this.status="Approved";
      alert(this.status)
      }
      else{
      this.status="Disapproved";
      alert(this.status)
      }
      this.isApproved=this.check;
  
  
    }

  }

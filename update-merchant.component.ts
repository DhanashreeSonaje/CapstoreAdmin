import { Component, OnInit } from '@angular/core';
import { MerchantDetails } from 'src/app/Model/MerchantDetails';
import { CapstoreService } from 'src/app/service/capstore.service';

@Component({
  selector: 'app-update-merchant',
  templateUrl: './update-merchant.component.html',
  styleUrls: ['./update-merchant.component.css']
})
export class UpdateMerchantComponent implements OnInit {
 merchant:MerchantDetails;
  userid;
  

  check=false;
  status: string;
  Rating:number;
  isApproved;
  constructor(private adminService: CapstoreService) { }

  ngOnInit(): void {
  }
  getMerchant(){
    this.adminService.getMerchant(this.userid).subscribe((data:any)=>{this.merchant=data})
  }
 update(){
this.adminService.updateMerchant(this.merchant);
   
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

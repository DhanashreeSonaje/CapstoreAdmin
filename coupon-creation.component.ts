import { Component, OnInit } from '@angular/core';
import { CapstoreService } from '../../service/capstore.service';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { Coupon } from '../../Model/Coupon.model';

@Component({
  selector: 'app-coupon-creation',
  templateUrl: './coupon-creation.component.html',
  styleUrls: ['./coupon-creation.component.css']
})
export class CouponCreationComponent implements OnInit {

  coupons: Coupon=new Coupon();
  submitted = false;
  
  constructor(private couponService:CapstoreService, private router: Router, private fb:FormBuilder) { }

  ngOnInit() {
  }

  save() {
    this.couponService.createCoupon(this.coupons)
    .subscribe(data =>{ 
      console.log(data)
    }, error =>{ 
       console.log(error.error.message);
      }); 
  }

  onSubmit(){
    this.submitted = true;
    this.save();
  }
}

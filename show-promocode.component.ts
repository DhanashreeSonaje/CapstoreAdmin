import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Coupon } from 'src/app/Model/Coupon.model';
import { Observable } from 'rxjs';
import { CapstoreService } from 'src/app/service/capstore.service';

@Component({
  selector: 'app-show-promocode',
  templateUrl: './show-promocode.component.html',
  styleUrls: ['./show-promocode.component.css']
})
export class ShowPromocodeComponent implements OnInit {

  coupon: Observable<Coupon[]>;
  couponId:number;
  couponCode:string;
  submitted=false;
  error=null;
  subId=true;
  subCode=true;
  
  coupon1: Coupon = new Coupon();


  constructor(private couponService: CapstoreService, private router: Router) { }

  ngOnInit() {
    this.reloadData();
  }

  reloadData() {
    this.coupon = this.couponService.getCouponList();
  }

  searchById(){
    this.subId=false;
    this.subCode=true;
  }

  searchByCode(){
    this.subId=true;
    this.subCode=false;
  }

  showCouponsById() {
    this.submitted=true;
    this.couponService.getCouponById(this.couponId, this.coupon)
    .subscribe((data) => {
      console.log(data)
      this.coupon1=data;
    }, error =>{ this.error=error;
      console.log(error.error);
      this.error=error.error.message;
      this.submitted=false
     });
  }

  showCouponsByCode() {
    this.submitted=true;
    this.couponService.getCouponByCode(this.couponCode,this.coupon)
    .subscribe((data) => {
      console.log(data)
      this.coupon1=data;
    }, error =>{ this.error=error;
      console.log(error.error);
      this.error=error.error;
      this.submitted=false
     });
  
    }

    backToCoupons() {
      this.submitted=false;
      this.subId=true;
      this.subCode=true;
    }
  back()
  {
    
    this.router.navigate(['admin']);
    }

}

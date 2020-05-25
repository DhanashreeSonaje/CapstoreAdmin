import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Product } from 'src/app/Model/Product';

@Component({
  selector: 'app-add-discount',
  templateUrl: './add-discount.component.html',
  styleUrls: ['./add-discount.component.css']
})
export class AddDiscountComponent implements OnInit {

  product:Product;
  constructor(private router:Router) { }

  ngOnInit() {
    this.product=new Product();
  }
  back()
  {
      this.router.navigate(['admin']);
  }
  onSubmit()
  {
    
  }
}

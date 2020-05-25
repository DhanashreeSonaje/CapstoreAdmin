import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Product } from 'src/app/Model/Product';
import { CapstoreService } from 'src/app/service/capstore.service';

@Component({
  selector: 'app-update-product',
  templateUrl: './update-product.component.html',
  styleUrls: ['./update-product.component.css']
})
export class UpdateProductComponent implements OnInit {

  product:Product = new Product();
  message: any;
  msg: any;
  productForm: FormGroup;
  submitted=false;

  constructor(private formBuilder: FormBuilder,private adminService: CapstoreService, private router: Router) { }

  ngOnInit(){
    this.productForm = this.formBuilder.group({
      id:['',Validators.required],
      name:[''],
      image:[''],
      price:[''],
      rating:[''],
      viewed:[''],
      numberProducts:[''],
      brand:[''],
	    info:[''],
      category:[''],
      activated:['',Validators.required],
      status:['',Validators.required],
      featured:['',Validators.required]
    });
  }

  updateProduct(){
    this.submitted = true;
    if(this.productForm.invalid)
    return;
    else{
    this.adminService.update(this.product).subscribe(data => {
      console.log(data);
      this.product = new Product();
      this.message = data;
      if(this.message==true){
        this.msg="You have successfully updated the product details";
        alert(this.msg);
        this.router.navigate(['/showProduct']);
    }
    else{
      this.msg="Enter correct product ID";
      alert(this.msg);
      this.router.navigate(['/showProduct']);
    }
  }, err => 
    { console.log(err.stack);
    });
  }
  }

  back(){
    this.router.navigate(['/showProduct']);
  }
}

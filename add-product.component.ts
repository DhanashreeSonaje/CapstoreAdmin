import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Product } from 'src/app/Model/Product';
import { CapstoreService } from 'src/app/service/capstore.service';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent implements OnInit {

  product:Product = new Product();
  message: any;
  productForm: FormGroup;
  submitted=false;
  check=false;

  constructor(private formBuilder: FormBuilder,private adminService: CapstoreService, private router: Router) { }

  ngOnInit(){
    this.productForm = this.formBuilder.group({
      name:['',Validators.required],
      image:['',Validators.required],
      price:['',Validators.required],
      rating:['',Validators.required],
      viewed:['',Validators.required],
      numberProducts:['',Validators.required],
      brand:['',Validators.required],
	    info:['',Validators.required],
      category:['',Validators.required],
      activated:['',Validators.required],
      status:['',Validators.required],
      featured:['',Validators.required]
    });
  }
  
  addProduct(){
    this.submitted = true;
    if(this.productForm.invalid)
    return;
    else{
      this.check=true;
    this.adminService.addProduct(this.product).subscribe(data => {
      console.log(data);
      this.product = new Product();
      this.message = data;
    }, err => 
    { console.log(err.stack);
    });
  }
}

  list()
  {
    this.router.navigate(['/showProduct']);
  }

}

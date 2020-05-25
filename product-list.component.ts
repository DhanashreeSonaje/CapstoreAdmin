import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Product } from 'src/app/Model/Product';
import { CapstoreService } from 'src/app/service/capstore.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

  products:Product[]=[];
  searchTerm;
  location:String;
  constructor(private router:Router,
    private adminService:CapstoreService) { }

  ngOnInit() {

    this.adminService.getAllProducts().subscribe(
      data=>{
        this.products=data;
        //this.location=this.products.productImage;
   
      },
      error=>{
        console.log(error);
      }
    );
    
  }

  // featured(){
  //   this.adminService.getFeaturedProducts().subscribe(
  //     data=>{
  //       this.products = data;
  //     },
  //     error=>{
  //       console.log(error);
  //     }
  //   )
  // }

  // deleteProduct(productId:number)
  // { if(confirm("Are you sure you want to remove the product?")){
  //   this.adminService.removeProduct(productId).subscribe(data => {
  //     console.log(data);
  //     this.msg=data;
  //     if(this.msg==true)
  //     {
  //       this.message="Successfully removed the product";
  //       alert(this.message);
  //       this.listOfProducts();
  //     }
  //   }, err =>
  //   { console.log(err.stack);
  //   })
  // }
  // }

  update(){
    this.router.navigate(['/updateProduct']);
  }

  back()
  {
      this.router.navigate(['/admin']);
  }
}

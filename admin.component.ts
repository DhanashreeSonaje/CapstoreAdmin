import { Component, OnInit } from '@angular/core';
import { CapstoreService } from 'src/app/service/capstore.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  searchTerm:any;
  constructor(private capstoreService:CapstoreService) { }
email;
  ngOnInit(): void {
  }
invite()
{
 this.capstoreService.inviteservice(this.email);
}
}

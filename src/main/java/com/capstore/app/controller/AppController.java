package com.capstore.app.controller;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.capstore.app.models.Coupon;
import com.capstore.app.models.CustomerDetails;
import com.capstore.app.models.MerchantDetails;
import com.capstore.app.models.Product;
import com.capstore.app.models.User;
import com.capstore.app.repository.ConfirmationTokenRepository;
import com.capstore.app.repository.CouponRepository;
import com.capstore.app.repository.MerchantRepository;
import com.capstore.app.repository.ProductServiceImpl;
import com.capstore.app.repository.UserRepository;
import com.capstore.app.signup_login.ConfirmationToken;
import com.capstore.app.signup_login.EmailSenderService;


import lombok.Data;

@Data
@Transactional
@RestController
@CrossOrigin(origins = "http://localhost:4200/",allowedHeaders = "*")
public class AppController {
	@Autowired
	ProductServiceImpl productServiceImpl;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired 
	MerchantRepository merchantRepository;
	
	@Autowired
	CouponRepository couponRepo;
	
	@Autowired
	ConfirmationTokenRepository confirmationTokenRepository;
	
	@Autowired
	EmailSenderService emailSenderService;

	@RequestMapping(value="/Billing-App/registerCustomer", method = RequestMethod.POST)
    public ResponseEntity<?> registerCustomer(@Valid @RequestBody CustomerDetails cd) throws MessagingException
    {
        CustomerDetails existingCustomer = userRepository.findCustomerByEmailIgnoreCase(cd.getEmail());
        if(existingCustomer != null)
        {
            return new ResponseEntity<Error>(HttpStatus.CONFLICT);
        }
        else
        {
            userRepository.saveCustomer(cd);
            CustomerDetails cd1=userRepository.findCustomerByEmailIgnoreCase(cd.getEmail());
            
            ConfirmationToken confirmationToken = new ConfirmationToken(cd1.getUserId());
            System.out.println(confirmationToken);

            confirmationTokenRepository.save(confirmationToken);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(cd.getEmail());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setFrom("capstore06@gmail.com");
            mailMessage.setText("To activate your account, please click here : "
            +"http://localhost:4200/verify?token="+confirmationToken.getConfirmationToken());

            emailSenderService.sendEmail(mailMessage);
            
            return ResponseEntity.ok(HttpStatus.OK);
        }
    }

    @RequestMapping(value="/Billing-App/registerMerchant", method = RequestMethod.POST)
    public ResponseEntity<?> registerMerchant(@Valid @RequestBody MerchantDetails md) throws MessagingException
    {

        MerchantDetails existingMerchant = userRepository.findMerchantByEmailIgnoreCase(md.getEmail());
        if(existingMerchant != null)
        {
            return new ResponseEntity<Error>(HttpStatus.CONFLICT);
        }
        else
        {
            userRepository.saveMerchant(md);
            MerchantDetails md1=userRepository.findMerchantByEmailIgnoreCase(md.getEmail());

            ConfirmationToken confirmationToken = new ConfirmationToken(md1.getUserId());

            confirmationTokenRepository.save(confirmationToken);
            
            MimeMessage mailMessage = emailSenderService.createMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true);
            String url = "http://localhost:4200/verifyMerchant?token="+confirmationToken.getConfirmationToken();
            helper.setTo("dsonaje6@gmail.com");
            helper.setSubject("Merchant Requesting Approval!");
            helper.setFrom("capstore06@gmail.com");
            helper.setText("<html><body><h1>Merchant Registration!</h1><br>" +
            		md+"<br><button type='submit'>"
            		+"<a href="+url+">Show Details</a></button>",true);

            emailSenderService.sendEmail(mailMessage);
            
            return ResponseEntity.ok(HttpStatus.OK);
        }
    }
    
    
    @RequestMapping(value="/Billing-App/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> confirmUserAccount(@Valid  @RequestParam("token") String confirmationToken)
    {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if(token != null)
        {
            if(userRepository.findCustomerById(token.getUid())!=null) {
            	CustomerDetails cd=userRepository.findCustomerById(token.getUid());
            	cd.setActive(true);
                userRepository.saveCustomer(cd);
            }
//            else if(userRepository.findMerchantById(token.getUid())!=null) {
//            	MerchantDetails md=userRepository.findMerchantById(token.getUid());
//            	md.setActive(true);
//            	md.setApproved(true);
//                userRepository.saveMerchant(md);
//                
//                SimpleMailMessage mailMessage1 = new SimpleMailMessage();
//                mailMessage1.setTo(md.getEmail());
//                mailMessage1.setSubject("Account Activated!");
//                mailMessage1.setFrom("capstore06@gmail.com");
//                mailMessage1.setText("Admin approved your account.\nTo login and access your account, please click here : "
//                +"http://localhost:4200");
//
//                emailSenderService.sendEmail(mailMessage1);
//            }
//            
            return ResponseEntity.ok(HttpStatus.OK);
      }
        else
        {
        	return new ResponseEntity<Error>(HttpStatus.CONFLICT);
        }
     }
    
    @GetMapping("/Billing-App/generateToken")
    public ResponseEntity<?> generateToken(@Valid  @RequestParam("token") String confirmationToken,@Valid  @RequestParam("action") String action) throws MessagingException{
    	
    	ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
    	
    	MerchantDetails md=userRepository.findMerchantById(token.getUid());
    	if(action.equals("Accept")) {
    	md.setActive(true);
    	md.setApproved(true); }
    	else {
    	md.setActive(false);
    	md.setApproved(false);  }
    	
        userRepository.saveMerchant(md);
        
        MimeMessage mailMessage = emailSenderService.createMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true);
        helper.setTo(md.getEmail());
        helper.setSubject("Account Activated!");
        helper.setFrom("capstore06@gmail.com");
        helper.setText("Admin approved your account.\nTo login and access your account, please click here : "
        +"http://localhost:4200");

        emailSenderService.sendEmail(mailMessage);
        
        return ResponseEntity.ok().body(md);
    }
    
    @RequestMapping(value="/Billing-App/login", method= {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> userLogin(@Valid @RequestBody String[] userCredentials) {
    	String email=userCredentials[0];
    	String pass=userCredentials[1];
    	String role=userCredentials[2];
    	System.out.println(email+pass+role);
    	if (role.equals("Customer")) {
    		CustomerDetails cd=userRepository.findCustomerByEmailIgnoreCase(email);
    		if(cd!=null && cd.isActive()==true) {
    			if(pass.equals(cd.getPassword())) {
    				return ResponseEntity.ok().body(cd);
    			}
    		}
    	}
    	else {
    		MerchantDetails md=userRepository.findMerchantByEmailIgnoreCase(email);
    		if(md!=null && md.isActive()==true) {
    			if(pass.equals(md.getPassword())) {
    				return ResponseEntity.ok().body(md);
    			}
    		}
    	}
    	return new ResponseEntity<Error>(HttpStatus.CONFLICT);
    }
    
    @RequestMapping(value="/Billing-App/getMerchant", method= {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> userLogin(@Valid  @RequestParam("token") String confToken) {
    	ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confToken);
    	MerchantDetails md=userRepository.findMerchantById(token.getUid());
    	return ResponseEntity.ok().body(md);
    }
    
  //All Products Data
  	@GetMapping(value="/allProducts")
  	public List<Product> getAllProducts(){
  		return productServiceImpl.allProductsList();
  	}
  	
  	
  	//Products data of particular category
  	@GetMapping(value="/productCategory/{category}")
  	public List<Product> getCategory(@PathVariable("category") String productCategory){
  		
  		return productServiceImpl.specificCategoryProducts(productCategory);
  	}
  	
  	
  	//Product data based on discount
  	@GetMapping(value="/discountCategory/{category}/{discountPercent}")
  	public List<Product> getDiscountProducts(@PathVariable("category") String productCategory,@PathVariable("discountPercent") String discount){
  		
  		return productServiceImpl.specificDiscountProducts(productCategory, discount);
  	}
  	
  	
  	@GetMapping(value="/searchProducts/{category}")
  	public List<Product> getSearchProducts(@PathVariable("category") String productSearch){
  		return productServiceImpl.searchProducts(productSearch);
  	}
  	
  	@GetMapping("/{email}")
	public ResponseEntity<MerchantDetails>verifyMerchantDetails(@PathVariable("email") String email){
		return new ResponseEntity<MerchantDetails>(userRepository.findMerchantByEmailIgnoreCase(email),HttpStatus.OK);
	}
    
  	
  	/////////////////////////////////////////////Coupons//////////////////////////////////////////
  	
  	@PostMapping(value = "/Billing-App/create")
	public ResponseEntity<Coupon> addCoupon(@Valid @RequestBody Coupon coupon) throws MessagingException {
	
  			couponRepo.save(coupon);
            
  			long cnt=merchantRepository.count();
  			List <MerchantDetails> merchants=merchantRepository.findAll();
            MimeMessage mailMessage = emailSenderService.createMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true);
            String url = "http://localhost:4200/applyCoupon/"+coupon.getCouponId();
            
           
            	for(MerchantDetails mer:merchants) {
            		System.out.println("In for");
            		helper.setTo(mer.getEmail());
            		System.out.println(mer.getEmail());
            		helper.setSubject("Coupon Creation Approval!");
                    helper.setText("<html><body><h1>Coupon Registration!</h1><br>" +
              			  coupon+"<br><button type='submit'>"
        		  		+ "<a href="+url+"/"+mer.getUserId()+">Show Details</a></button>",true);
                    
                    emailSenderService.sendEmail(mailMessage);
            	}
            
			return new ResponseEntity<Coupon>(HttpStatus.CREATED);
	}
    
    @PutMapping("/Billing-App/generateCoupon/{couponId}/{id}")
    public Coupon generateCoupon(@PathVariable("couponId") int couponId, @PathVariable("id") int id) throws Exception{
    	System.out.println("In generate coupon");
    	Coupon coupon = couponRepo.findById(couponId)
    			.orElseThrow(() -> new Exception("Coupon not found for this id : " + couponId));
    	
        ConfirmationToken confirmationToken = new ConfirmationToken(coupon.getUserId());
       // MerchantDetails merchant = merchantRepository.findById(coupon.getUserId())
       // 		.orElseThrow(()->new Exception("Merchant not found for this id : " + coupon.getUserId()));
        confirmationTokenRepository.save(confirmationToken);
        
        MerchantDetails merchant = merchantRepository.findById(id)
        		.orElseThrow(()->new Exception("Merchant not found for this id : " + coupon.getUserId()));
        
        coupon.setApproved(true);
        coupon.setUseId(id);
        couponRepo.save(coupon);
        merchantRepository.save(merchant);
        
        MimeMessage mailMessage = emailSenderService.createMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true);
        String url = "http://localhost:4200/sendCoupon/"+coupon.getCouponId();
        helper.setTo("dsonaje6@gmail.com");
        helper.setSubject("Coupon Accepted!");
        helper.setFrom("capstore06@gmail.com");
        helper.setText("Merchant accepted coupon offer: "+coupon+"\nTo send this offer, please click here : "
        +"\n"+url);

        emailSenderService.sendEmail(mailMessage);
        
        return coupon;
    }
  
    ///////////////////////////////new/////////////////////////////////
    @PutMapping("/Billing-App/sendCoupon/{couponId}")
    public Coupon sendCoupon(@PathVariable("couponId") int couponId) throws Exception{
    	Coupon coupon = couponRepo.findById(couponId)
    			.orElseThrow(() -> new Exception("Coupon not found for this id : " + couponId));
        
        MimeMessage mailMessage = emailSenderService.createMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true);
        String url = "http://localhost:4200/productpage/";
        
        List <MerchantDetails> merchants = merchantRepository.findAll();
        
        for(MerchantDetails mer:merchants) {
    		helper.setTo(mer.getEmail());
    		helper.setSubject("Latest Offers!!!");
            helper.setFrom("capstore06@gmail.com");
            helper.setText("Current Offers: "+coupon+"\nGrab this offer, please click here : "
            +"\n"+url);

            emailSenderService.sendEmail(mailMessage);
    	}
        return coupon;
    }
    
    ////////////////////////////////////////////////////////////////////////
  	@GetMapping("Billing-App/coupons")
	public ResponseEntity<List<Coupon>>getAllAccounts(){
  		List<Coupon> coupon = new ArrayList<>();
		couponRepo.findAll().forEach(coupon::add);
		return new ResponseEntity<List<Coupon>>(coupon,HttpStatus.OK);
	}
	
	@PutMapping("Billing-App/Id/{couponId}")
	public ResponseEntity<Coupon> getAccountById(@PathVariable("couponId") int couponId) throws Exception{
		Coupon coupon = couponRepo.findById(couponId)
				.orElseThrow(() -> new Exception("Coupon not found for this id : " + couponId));
	    
		return new ResponseEntity<Coupon>(coupon, HttpStatus.OK);
	}
	
	@PutMapping("Billing-App/Code/{couponCode}")
	public ResponseEntity<Coupon> getAccount(@PathVariable("couponCode") String couponCode){
		Coupon coupon = couponRepo.findByCouponCode(couponCode);
	     
		if(coupon==null) {
			return new ResponseEntity("Sorry! Coupon not found!",HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Coupon>(coupon, HttpStatus.OK);
	}
}



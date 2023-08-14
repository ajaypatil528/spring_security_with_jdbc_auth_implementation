package in.ajay.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {

	
	//Only ADMIN role users can access this method 
	@GetMapping(value = "/admin")
	public String admin() {
		return "<h3>Hey, Welcome Admin :)<h3>";
	}
	
	//Only ADMIN role users and USER role users can access this method 
	@GetMapping(value = "/user")
	public String user() {
		return "<h3>Hey, WhatsApp user :)<h3>";
	}
	
	//Anybody can access this method
	@GetMapping(value = "/welcome")
	public String welcome() {
		return "<h3>Welcome you All :)<h3>";
	}
}

package templates;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class sbbController {

	@GetMapping("/hello")
	@ResponseBody
	public String hello() {
		
		return "sbbHello";
	}
	
	
}

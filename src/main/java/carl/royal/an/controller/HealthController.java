package carl.royal.an.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Carl
 * @date Mar 2, 2016 11:13:52 PM
 */
@Controller
@RequestMapping(value = "/health")
public class HealthController {

	@RequestMapping(value = "/status", method = RequestMethod.GET)
	public @ResponseBody String getServerStatus() {
		return "200";
	}

}

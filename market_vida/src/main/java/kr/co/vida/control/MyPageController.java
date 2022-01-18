package kr.co.vida.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("mypage")
public class MyPageController {
	@GetMapping("/main")
	public String myPage() {
		return "/mypage/myPageMain";
	}
}

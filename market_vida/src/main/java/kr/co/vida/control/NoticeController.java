package kr.co.vida.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NoticeController {
	
	@RequestMapping("/noticeBoard")
	public String notice() {
		return "board/noticeBoard";
	}
}

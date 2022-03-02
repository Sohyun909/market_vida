package kr.co.vida.control;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.vida.dto.GoodsDTO;
import kr.co.vida.dto.GoodsQnaDTO;
import kr.co.vida.dto.PageUtil;
import kr.co.vida.dto.SubCatDTO;
import kr.co.vida.service.GoodsImple;
import kr.co.vida.service.GoodsQnaImple;
import kr.co.vida.service.ImgListImple;
import kr.co.vida.service.ReviewBoardImple;
import kr.co.vida.service.SubCatImple;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
public class GoodsController {
	
	@Autowired
	ImgListImple imgService;
	
	@Autowired
	SubCatImple subService;
	
	@Autowired
	GoodsImple goodsService;
	
	@Autowired
	GoodsQnaImple goodsQnaService;
	
	@Autowired
	ReviewBoardImple reviewService;

	// 상품 리스트 페이지
	@RequestMapping("/goods/goodsList")
	public String goodsList(@RequestParam("cat_code")int cat_code, 
							@RequestParam(name = "orderby", defaultValue = "")String orderBy,
							@RequestParam(name = "currentPage", defaultValue = "1") int currentPage,
							Model model) {
		
		// 총 게시물 수
		int totalNumber = imgService.getTotalbyCode(cat_code);
		System.out.println(totalNumber);
		
		// 현재 페이지의 게시물 수 : 15
		int countPerPage=15;
		
		Map<String, Object> map = PageUtil.getPageData(totalNumber, countPerPage, currentPage);
		
		model.addAttribute("map", map);
		
		int startNo = (int) map.get("startNo");
		int endNo = (int) map.get("endNo");
		
		// 상품 리스트 객체
		model.addAttribute("imgDto", imgService.readAll(startNo, endNo, cat_code, orderBy));
		model.addAttribute("getTotal", imgService.getTotalbyCode(cat_code));
		model.addAttribute("code", Integer.toString(cat_code));
		log.info("subCode=====>"+cat_code);
		
		
		int forMainCode = 0; // 메인인지 서브인지 코드 판단	
		
		if(cat_code%100==0) { // 메인코드
			forMainCode = cat_code+1;
		}else if(cat_code%100!=0) { // 서브코드
			forMainCode = cat_code;
		}
		
		// 카테고리 리스트 객체
		SubCatDTO subDto = subService.selectOne(forMainCode);
		model.addAttribute("mainCode", subDto);
		model.addAttribute("subDtoList", subService.getListAll(subDto.getMain_cat_code()));

		
		return "/goods/goodsList";
	}
	

	
	// 상품 삭제
	@RequestMapping("/goods/deleteGoods")
	@ResponseBody
	public String deleteGoods(@RequestParam List<String> goods_no){
		
		for (String goodsNo:goods_no) {
			goodsService.dropOne(Integer.parseInt(goodsNo));
		}
		
		return "success";
	}
	
	
	
	// 상품 디테일 페이지 이동
	@GetMapping("/goods/goodsDetail")
	public String goodsDetail(@RequestParam("goods_no")int goods_no, Model model, GoodsQnaDTO goodsQnaDto) {
		
		GoodsDTO goodsDto = goodsService.selectOne(goods_no);
		
		model.addAttribute("goodsDto", goodsDto);
		model.addAttribute("detailImgDto", imgService.getGoodsImgs(goodsDto.getGoods_no()));
		model.addAttribute("goodsQnaDto", goodsQnaService.selectAllList(goods_no));
		model.addAttribute("reviewDto", reviewService.getListAllByGoodsNo(goods_no));
		log.info("goodsqna======?"+goodsQnaDto);
		return "goods/goodsDetail";
	}
	
	
	
	
	@GetMapping("/admin/goodswrite")
	public String writeForm() {
		return "/admin/productRegister";
	}

	
	@PostMapping("/admin/goodswrite")
	public String writeFormOk(@ModelAttribute("dto") GoodsDTO dto, HttpServletRequest req) {
		goodsService.insertOne(dto);
		return "redirect:/main/main";
	}

}
		



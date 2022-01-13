package kr.co.vida.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.vida.dto.GoodsDTO;
import kr.co.vida.dto.SubCatDTO;
import kr.co.vida.service.GoodsImple;
import kr.co.vida.service.ImgListImple;
import kr.co.vida.service.SubCatImple;
import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.proxy.annotation.Post;

@Slf4j
@Controller
@RequestMapping("goods")
public class GoodsController {
	
	@Autowired
	ImgListImple imgService;
	
	@Autowired
	SubCatImple subService;
	
	@Autowired
	GoodsImple goodsService;
	

	@GetMapping("/goodsList")
	public String goodsList(@RequestParam("cat_code")int cat_code, Model model) {
	//	public String goodsList(Model model, @RequestParam("main_cat_code")int main_cat_code) {
	//	���ΰ� ���� �� ���
		log.info("subCode=====>"+cat_code);
		
		// ���� �ڵ� 1���� ���ϱ�
		int forMainCode;	
		if(cat_code%100==0) {
			forMainCode = cat_code+1;
		}else {
			forMainCode = cat_code;
		}
		
		// ī�װ� ����Ʈ ��ü
		SubCatDTO subDto = subService.selectOne(forMainCode);
		model.addAttribute("mainCode", subDto);
		model.addAttribute("subDtoList", subService.getListAll(subDto.getMain_cat_code()));
		
		// ��ǰ ����Ʈ ��ü
		if (cat_code %100==0) {
			model.addAttribute("imgDto", imgService.selectAllList(cat_code));
		}else {
			model.addAttribute("imgDto", imgService.getListBySubCode(cat_code));
		}
		
		return "/goods/goodsList";
	}
	
	@RequestMapping("/deleteGoods")
	@ResponseBody
	public String deleteGoods(HttpServletRequest req){
		String []chkBoxList = req.getParameterValues("deletecb");
		for (String goodsNo : chkBoxList) {
			System.out.println(goodsNo);
		}
		return "delete success";
	}
	
	
	// ��ǰ ������ ������ �̵�
	@GetMapping("/goodsDetail")
	public String goodsDetail(@RequestParam("goods_no")int goods_no, Model model) {
		
		GoodsDTO goodsDto = goodsService.selectOne(goods_no);
		
		model.addAttribute("goodsDto", goodsDto);
		model.addAttribute("detailImgDto", imgService.getGoodsImgs(goodsDto.getGoods_no()));
		
		return "goods/goodsDetail";
	}
	
	

}

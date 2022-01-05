<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<style type="text/css">

	.goodsBody {
		width: 1000px;
		margin: 0 auto;
	}
	
	.categoryBox {
		display: flex;
    	border: 1px solid silver;
    	padding: 20px;
	}
	
	.categoryList, .categoryList a  {
		list-style: none;
		text-decoration: none;
		color: black;
	}
	
	.categoryList li {
		float: left;
		width: 200px;
		padding: 10px;
		
	}

	.sortGoods {
		width: 1000px;
		margin-top: 35px;
   		display: flex;
  		justify-content: space-between;
	    font-size: small;
	}
	
	.sortBy li {
		list-style: none;
	    float: left;
	    padding: 0 8px;
	}
	
	.sortBy li:not(:last-child) {
		border-right: 1.5px solid silver;
	}
	
	
	.imgBox {
		padding: 0;
		margin: 0;	
		width: 100%;
		list-style: none;
		display: flex;
		flex-wrap: wrap;
		justify-content: space-between;
	}
	
	.imgBox_list  {
		margin-bottom: 80px;
	}
	
	.imgBox_list a{
		text-decoration: none;
		color: black;
	}
	
	.imgBox_list img {
		width: 320px;
		height: 360px;
	}
	
	.imgBox_list span {
		width: 300px;
		overflow: hidden;
		display: list-item;
		padding: 3px 0;
	}
	
	.imgBox_list #goods_price {
		text-decoration-line: line-through;
		color: grey;
	}
	
	.imgBox_list #discount_rate {
		display: contents;
		font-weight: bold;
		color: red;
	}
	
	
</style>
<script type="text/javascript">
	$("#totalGoodsList").click(function(){
		$.ajax({
			type:"POST",
			url:"goodsList?main_cat_code="+${subDto.sub_cat_name},
			data:
		})
	})

</script>
</head>
<body>
	
	<div class="goodsBody">
	
	<!-- 카테고리 박스 -->
	<div class="categoryBox">
		<ul class="categoryList">
			<li><a href="#" id="totalGoodsList">전체보기</a></li>
			<c:forEach var="subDto" items="${subDto }">
			<li><a href="#" id="subCatGoodsList">${subDto.sub_cat_name }</a></li>
			</c:forEach>		
		</ul>
	</div>
	<!-- 상품 정렬 -->
	<div class="sortGoods">
		<div class="countGoods">
			<p>총${count }개</p>
		</div>
		<div class="sortBy">
			<ul>
				<li>추천순</li>
				<li>신상품순</li>
				<li>판매량순</li>
				<li>리뷰순</li>
				<li>높은 가격순</li>
				<li>낮은 가격순</li>
			</ul>
		</div>
	</div>
	<!-- 상품 리스트-->
		<ul class="imgBox">
			<c:forEach var="imgDto" items="${imgDto }">
			<li class="imgBox_list">
				<a href="goodsDetail?goods_no=${imgDto.goods_no }">
					<img src="${imgDto.img_name }" alt="${imgDto.img_name }" />
					<span>${imgDto.goods_name }</span>
					
					<c:choose>
						<c:when test="${imgDto.discount_rate>0 }">
							<span><span id="discount_rate">${imgDto.discount_rate }% &nbsp; </span> ${Math.round(imgDto.goods_price-(imgDto.goods_price*(imgDto.discount_rate/100))) }원 </span>
							<span id="goods_price">${imgDto.goods_price } </span>
							
						</c:when>
						<c:when test="${imgDto.discount_rate==0 }">
							<span id="goods_price_woDC">${imgDto.goods_price }원 </span>
						</c:when>
					</c:choose>
				</a>
			</li>
			</c:forEach>
		</ul>
	
	
	
	</div>
</body>
</html>
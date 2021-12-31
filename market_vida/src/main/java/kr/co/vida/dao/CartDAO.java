package kr.co.vida.dao;

import java.util.List;

import kr.co.vida.dto.CartDTO;

public interface CartDAO {
	public int getNewCartNumber(); // īƮ ��ȣ
	public CartDTO checkCart(); // īƮ üũ
	public void insertGoodsInCart(); // īƮ�� ��ǰ ���
	public void updateGoodsInCart(); // īƮ�� ���� �߰�
	public List getCartList(int crew_no);	
}

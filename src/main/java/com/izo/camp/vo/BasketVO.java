package com.izo.camp.vo;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

//@Repository 빈으로 등록을 하지 않아도 될것
public class BasketVO {
//	idx number(6) PRIMARY KEY,
//	memberID VARCHAR2(50),
//	productID VARCHAR2(1000),
//	pcs NUMBER(3)
	
	private Integer idx;
	private String memberId;
	private String productId;
	private Integer pcs;
	private Integer price;
	private Integer totalPrice;
	
	
	public BasketVO() {
	
	}

	public Integer getIdx() {
		return idx;
	}

	public void setIdx(Integer idx) {
		this.idx = idx;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Integer getPcs() {
		return pcs;
	}

	public void setPcs(Integer pcs) {
		this.pcs = pcs;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	
}

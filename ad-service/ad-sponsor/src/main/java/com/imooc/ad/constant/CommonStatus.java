package com.imooc.ad.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonStatus {

	VILID(1, "有效状态"),
	INVILID(0, "无效状态")
	;

	private Integer status;
	private String description;

}

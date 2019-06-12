package com.imooc.ad.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CreativeType {

	IMAGE(1, "图片"),
	VIDEO(2, "视频"),
	TEXT(3, "文本")
	;
	private Integer type;
	private String desc;

}

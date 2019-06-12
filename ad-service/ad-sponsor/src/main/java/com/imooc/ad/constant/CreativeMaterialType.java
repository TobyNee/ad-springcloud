package com.imooc.ad.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CreativeMaterialType {

	JPG(1, "jpg"),
	BMP(2, "bmp"),
	MP4(3, "mp4"),
	AVI(4, "avi"),
	TXT(5, "txt")
	;
	private Integer type;
	private String desc;

}

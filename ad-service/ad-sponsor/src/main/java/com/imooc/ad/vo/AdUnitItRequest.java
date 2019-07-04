package com.imooc.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitItRequest {

	private List<UnitIt> unitIts;

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	private static class UnitIt {
		private Long unitId;
		private String itTag;
	}

}

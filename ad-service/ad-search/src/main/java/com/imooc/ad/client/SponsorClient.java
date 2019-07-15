package com.imooc.ad.client;

import com.imooc.ad.client.vo.AdPlan;
import com.imooc.ad.client.vo.AdPlanGetRequest;
import com.imooc.ad.vo.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "client-ad-sponsor", fallback = SponsorClientHystrix.class)
public interface SponsorClient {

	@PostMapping("/ad-sponsor/get/adPlan")
	CommonResponse<List<AdPlan>> getAdPlans(@RequestBody AdPlanGetRequest request);

}

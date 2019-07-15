package com.imooc.ad.controller;

import com.alibaba.fastjson.JSON;
import com.imooc.ad.annotation.IgnoreResponseAdvice;
import com.imooc.ad.client.SponsorClient;
import com.imooc.ad.client.vo.AdPlan;
import com.imooc.ad.client.vo.AdPlanGetRequest;
import com.imooc.ad.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@Slf4j
public class SearchController {

	private final RestTemplate restTemplate;

	private final SponsorClient sponsorClient;

	public SearchController(RestTemplate restTemplate, SponsorClient sponsorClient) {
		this.restTemplate = restTemplate;
		this.sponsorClient = sponsorClient;
	}

	@IgnoreResponseAdvice
	@PostMapping("/getAdPlansByRibbon")
	public CommonResponse<List<AdPlan>> getAdPlanByRibbon(@RequestBody AdPlanGetRequest request) {
		log.info("ad - search: getAdPlanByRibbon -> {}", JSON.toJSONString(request));
		return restTemplate.postForEntity("http://client-ad-sponsor/ad-sponsor/get/adplan", request, CommonResponse.class).getBody();
	}

	@IgnoreResponseAdvice
	@PostMapping("/getAdPlansByFeign")
	public CommonResponse<List<AdPlan>> getAdPlanByFeign(@RequestBody AdPlanGetRequest request) {
		log.info("ad - search: getAdPlanByFeign -> {}", JSON.toJSONString(request));
		return sponsorClient.getAdPlans(request);
	}



}

package com.imooc.ad.service.impl;

import com.imooc.ad.constant.CommonStatus;
import com.imooc.ad.constant.Constants;
import com.imooc.ad.dao.AdPlanRepository;
import com.imooc.ad.dao.AdUserRepository;
import com.imooc.ad.entity.AdPlan;
import com.imooc.ad.entity.AdUser;
import com.imooc.ad.exception.AdException;
import com.imooc.ad.service.IAdPlanService;
import com.imooc.ad.util.CommonUtils;
import com.imooc.ad.vo.AdPlanGetRequest;
import com.imooc.ad.vo.AdPlanRequest;
import com.imooc.ad.vo.AdPlanResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AdPlanServiceImpl implements IAdPlanService {

	private final AdPlanRepository adPlanRepository;
	private final AdUserRepository adUserRepository;

	@Autowired
	public AdPlanServiceImpl(AdPlanRepository adPlanRepository, AdUserRepository adUserRepository) {
		this.adPlanRepository = adPlanRepository;
		this.adUserRepository = adUserRepository;
	}

	@Override
	@Transactional
	public AdPlanResponse createAdPlan(AdPlanRequest request) throws AdException {
		if (!request.createValidate()) {
			throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
		}
		Optional<AdUser> adUser = adUserRepository.findById(request.getUserId());
		adUser.orElseThrow(() -> new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD));

		AdPlan oldPlan = adPlanRepository.findByUserIdAndPlanName(request.getUserId(), request.getPlanName());
		if (oldPlan != null) {
			throw new AdException(Constants.ErrorMsg.SAME_NAME_PLAN_ERROR);
		}

		AdPlan newAdPlan = adPlanRepository.save(new AdPlan(request.getUserId(), request.getPlanName(),
				CommonUtils.parseStringDate(request.getStartDate()),
				CommonUtils.parseStringDate(request.getEndDate())));

		return new AdPlanResponse(newAdPlan.getId(), newAdPlan.getPlanName());
	}

	@Override
	public List<AdPlan> getAdPlanByIds(AdPlanGetRequest request) throws AdException {

		if (!request.validate()) {
			throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
		}

		return adPlanRepository.findAllByIdInAndUserId(request.getIds(), request.getUserId());
	}

	@Override
	public AdPlanResponse updateAdPlan(AdPlanRequest request) throws AdException {

		if (!request.updateValidate()) {
			throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
		}

		AdPlan adPlan = adPlanRepository.findByIdAndUserId(request.getId(), request.getUserId());
		if (adPlan == null) {
			throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
		}

		if (request.getPlanName() != null) {
			adPlan.setPlanName(request.getPlanName());
		}

		if (request.getStartDate() != null) {
			adPlan.setStartDate(CommonUtils.parseStringDate(request.getStartDate()));
		}

		if (request.getEndDate() != null) {
			adPlan.setStartDate(CommonUtils.parseStringDate(request.getEndDate()));
		}

		adPlan.setUpdateTime(new Date());
		AdPlan plan = adPlanRepository.save(adPlan);

		return new AdPlanResponse(plan.getId(), plan.getPlanName());
	}

	@Override
	@Transactional
	public void deleteAdPlan(AdPlanRequest request) throws AdException {
		if (!request.deleteValidate()) {
			throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
		}
		AdPlan adPlan = adPlanRepository.findByIdAndUserId(request.getId(), request.getUserId());
		if (adPlan == null) {
			throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
		}
		adPlan.setPlanStatus(CommonStatus.INVILID.getStatus());
		adPlan.setUpdateTime(new Date());
		adPlanRepository.save(adPlan);
	}
}

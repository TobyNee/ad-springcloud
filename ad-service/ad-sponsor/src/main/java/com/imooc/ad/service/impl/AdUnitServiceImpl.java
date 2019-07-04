package com.imooc.ad.service.impl;

import com.imooc.ad.constant.Constants;
import com.imooc.ad.dao.AdPlanRepository;
import com.imooc.ad.dao.AdUnitRepository;
import com.imooc.ad.entity.AdUnit;
import com.imooc.ad.exception.AdException;
import com.imooc.ad.service.IAdUnitService;
import com.imooc.ad.vo.*;
import org.springframework.stereotype.Service;

@Service
public class AdUnitServiceImpl implements IAdUnitService {

	private final AdUnitRepository adUnitRepository;

	private final AdPlanRepository adPlanRepository;

	public AdUnitServiceImpl(AdUnitRepository adUnitRepository, AdPlanRepository adPlanRepository) {
		this.adUnitRepository = adUnitRepository;
		this.adPlanRepository = adPlanRepository;
	}

	@Override
	public AdUnitResponse createUnit(AdUnitRequest request) throws AdException {

		 if (!request.createValidate()) {
		 	throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
		 }
		 adPlanRepository.findById(request.getPlanId()).orElseThrow(() -> new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD));
		AdUnit oldAdUnit = adUnitRepository.findByPlanIdAndUnitName(request.getPlanId(), request.getUnitName());

		if (oldAdUnit != null) {
			throw new AdException(Constants.ErrorMsg.SAME_NAME_PLAN_ERROR);
		}

		AdUnit newAdUnit = adUnitRepository.save(new AdUnit(request.getPlanId(), request.getUnitName(),
				request.getPositionType(), request.getBudget()));

		return new AdUnitResponse(newAdUnit.getId(), newAdUnit.getUnitName());
	}

	@Override
	public AdUnitKeywordResponse createUnitKeyword(AdUnitKeywordRequest request) throws AdException {
		return null;
	}

	@Override
	public AdUnitItResponse createUnitIt(AdUnitItRequest request) throws AdException {
		return null;
	}

	@Override
	public AdUnitDistrictResponse createUnitDistrict(AdUnitDistrictRequest request) throws AdException {
		return null;
	}
}

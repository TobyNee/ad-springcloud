package com.imooc.ad.service.impl;

import com.imooc.ad.constant.Constants;
import com.imooc.ad.dao.AdPlanRepository;
import com.imooc.ad.dao.AdUnitRepository;
import com.imooc.ad.dao.CreativeRepository;
import com.imooc.ad.dao.unit_condition.AdUnitDistrictRepository;
import com.imooc.ad.dao.unit_condition.AdUnitItRepository;
import com.imooc.ad.dao.unit_condition.AdUnitKeywordRepository;
import com.imooc.ad.dao.unit_condition.CreativeUnitRepository;
import com.imooc.ad.entity.AdUnit;
import com.imooc.ad.entity.Creative;
import com.imooc.ad.entity.unit_condition.AdUnitDistrict;
import com.imooc.ad.entity.unit_condition.AdUnitIt;
import com.imooc.ad.entity.unit_condition.AdUnitKeyword;
import com.imooc.ad.entity.unit_condition.CreativeUnit;
import com.imooc.ad.exception.AdException;
import com.imooc.ad.service.IAdUnitService;
import com.imooc.ad.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdUnitServiceImpl implements IAdUnitService {

	private final AdUnitRepository adUnitRepository;

	private final AdPlanRepository adPlanRepository;

	private final AdUnitKeywordRepository adUnitKeywordRepository;

	private final AdUnitDistrictRepository adUnitDistrictRepository;

	private final AdUnitItRepository adUnitItRepository;

	private final CreativeRepository creativeRepository;

	private final CreativeUnitRepository creativeUnitRepository;

	public AdUnitServiceImpl(AdUnitRepository adUnitRepository, AdPlanRepository adPlanRepository, AdUnitKeywordRepository adUnitKeywordRepository,
							 AdUnitDistrictRepository adUnitDistrictRepository, AdUnitItRepository adUnitItRepository,
							 CreativeRepository creativeRepository, CreativeUnitRepository creativeUnitRepository) {
		this.adUnitRepository = adUnitRepository;
		this.adPlanRepository = adPlanRepository;
		this.adUnitKeywordRepository = adUnitKeywordRepository;
		this.adUnitDistrictRepository = adUnitDistrictRepository;
		this.adUnitItRepository = adUnitItRepository;
		this.creativeRepository = creativeRepository;
		this.creativeUnitRepository = creativeUnitRepository;
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

		List<Long> unitIds = request.getUnitKeywords().stream().map(AdUnitKeywordRequest.UnitKeyword::getUnitId).collect(Collectors.toList());
		if (!isRelatedUnitExist(unitIds)) {
			throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
		}
		List<Long> ids = Collections.emptyList();
		List<AdUnitKeyword> unitKeywords = new ArrayList<>();
		if (!CollectionUtils.isEmpty(request.getUnitKeywords())) {
			request.getUnitKeywords().forEach(i -> unitKeywords.add(new AdUnitKeyword(i.getUnitId(), i.getKeyword())));
			ids = adUnitKeywordRepository.saveAll(unitKeywords).stream().map(AdUnitKeyword::getId).collect(Collectors.toList());
		}
		return new AdUnitKeywordResponse((ids));
	}

	@Override
	public AdUnitItResponse createUnitIt(AdUnitItRequest request) throws AdException {

		List<Long> unitIds = request.getUnitIts().stream().map(AdUnitItRequest.UnitIt::getUnitId).collect(Collectors.toList());
		if (!isRelatedUnitExist(unitIds)) {
			throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
		}
		List<Long> ids = Collections.emptyList();
		List<AdUnitIt> unitIts = new ArrayList<>();
		if (!CollectionUtils.isEmpty(request.getUnitIts())) {
			request.getUnitIts().forEach(i -> unitIts.add(new AdUnitIt(i.getUnitId(), i.getItTag())));
			ids = adUnitItRepository.saveAll(unitIts).stream().map(AdUnitIt::getId).collect(Collectors.toList());
		}
		return new AdUnitItResponse(ids);
	}

	@Override
	public AdUnitDistrictResponse createUnitDistrict(AdUnitDistrictRequest request) throws AdException {
		List<Long> unitIds = request.getUnitDistricts().stream().map(AdUnitDistrictRequest.UnitDistrict::getUnitId).collect(Collectors.toList());
		if (!isRelatedUnitExist(unitIds)) {
			throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
		}
		List<Long> ids = Collections.emptyList();
		List<AdUnitDistrict> unitDistricts = new ArrayList<>();
		if (!CollectionUtils.isEmpty(request.getUnitDistricts())) {
			request.getUnitDistricts().forEach(i -> unitDistricts.add(new AdUnitDistrict(i.getUnitId(), i.getProvince(), i.getCity())));
			ids = adUnitDistrictRepository.saveAll(unitDistricts).stream().map(AdUnitDistrict::getId).collect(Collectors.toList());
		}
		return new AdUnitDistrictResponse(ids);
	}

	@Override
	public CreativeUnitResponse createCreativeUnit(CreativeUnitRequest request) throws AdException {
		List<Long> unitIds = request.getUnitItems().stream().map(CreativeUnitRequest.UnitItem::getUnitId).collect(Collectors.toList());
		List<Long> creativeIds = request.getUnitItems().stream().map(CreativeUnitRequest.UnitItem::getCreativeId).collect(Collectors.toList());
		if (!isRelatedUnitExist(unitIds) || !isRelatedCreativeExist(creativeIds)) {
			throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
		}
		List<CreativeUnit> creativeUnits = new ArrayList<>();
		request.getUnitItems().forEach(i -> creativeUnits.add(new CreativeUnit(i.getCreativeId(), i.getUnitId())));
		List<Long> ids = creativeUnitRepository.saveAll(creativeUnits).stream().map(CreativeUnit::getId).collect(Collectors.toList());
		return new CreativeUnitResponse(ids);
	}

	private boolean isRelatedUnitExist(List<Long> unitIds) {
		if (CollectionUtils.isEmpty(unitIds)) {
			return false;
		}
		return adUnitRepository.findAllById(unitIds).size() == new HashSet<>(unitIds).size();
	}

	private boolean isRelatedCreativeExist(List<Long> creativeIds) {
		if (CollectionUtils.isEmpty(creativeIds)) {
			return false;
		}
		return creativeRepository.findAllById(creativeIds).size() == new HashSet<>(creativeIds).size();
	}

}

package com.nyfz.mapper;

import java.util.List;
import java.util.Map;

import com.nyfz.entity.CountryEntity;

public interface CountryMapper {
	
	public List<CountryEntity> queryAll(Map<String, String> map);
	
	public int add(Map<String, String> map);
	
	public int update(Map<String, Object> map);
	
	public int delete(Map<String, Object> map);
	public int addBatch(List<CountryEntity> list);

}

package com.bbytes.plutus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.bbytes.plutus.model.ProductPlanStats;
import com.bbytes.plutus.repo.ProductPlanStatsRepository;
import com.bbytes.plutus.util.DateUtil;

@Service
public class ProductPlanStatsService extends AbstractService<ProductPlanStats, String> {

	@Autowired
	private MongoOperations mongoOperation;

	@Autowired
	public ProductPlanStatsService(ProductPlanStatsRepository productPlanStatsRepository) {
		super(productPlanStatsRepository);
	}

	public <S extends ProductPlanStats> List<S> save(Iterable<S> entites) {
		throw new UnsupportedOperationException();
	}

	/*
	 * Saves a given entity
	 */
	public <S extends ProductPlanStats> S save(S entity) {
		if (isUpdatable(entity))
			return mongoRepository.save(entity);

		throw new PersistenceException("Already updated for today");
	}

	private ProductPlanStats getLastModifiedRecord() {
		Query query = new Query();
		query.limit(1);
		query.with(new Sort(Sort.Direction.DESC, "lastModified"));
		return mongoOperation.findOne(query, ProductPlanStats.class);
	}

	private boolean isUpdatable(ProductPlanStats productPlanStats) {
		ProductPlanStats lastModifiedRecord = getLastModifiedRecord();
		if (lastModifiedRecord == null)
			return true;

		return DateUtil.isNotToday(lastModifiedRecord.getLastModified()) ? true : false;
	}
}

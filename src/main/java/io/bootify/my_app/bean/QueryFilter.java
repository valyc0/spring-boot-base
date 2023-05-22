package io.bootify.my_app.bean;

import java.util.List;

public class QueryFilter {
	int page = 0;
	int limit =0;
	String sortField ;
	String sortOrder ;
	List<FilterDTO> filters;

	

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public List<FilterDTO> getFilters() {
		return filters;
	}

	public void setFilters(List<FilterDTO> filters) {
		this.filters = filters;
	}


}

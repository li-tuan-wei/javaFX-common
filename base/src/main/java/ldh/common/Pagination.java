package ldh.common;

public class Pagination implements Pageable, Orderable {

	private long pageNo; 
	private int pageSize = 10;
	private String order;
	
	public Pagination() {}
	
	public Pagination(long pageNo, int pageSize) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	public long getStart() {
		return (pageNo - 1) * pageSize;
	}

	public int getLength() {
		return pageSize;
	}
	
	public long getEnd() {
		return getStart() + pageSize;
	}

	public long getPageNo() {
		return pageNo;
	}

	public void setPageNo(long pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public String getOrder() {
		return order;
	}

	@Override
	public void setOrder(String order) {
		this.order = order;
	}

}

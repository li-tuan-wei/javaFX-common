package ldh.common;

import java.util.List;

public class PageResult<T> {

	private long pageNo; // 当前页数
	private int pageSize; //每页的个数
	private long pageTotal;  // 总页数
	private long total; // 总条数
	private List<T> beans; //数据量
	
	public PageResult() {}
	
	
	public PageResult(long total, List<T> beans) {
		this(1, 10, total, beans);
	}
	
	public PageResult(Pageable pagination, long total, List<T> beans) {
		int length = pagination.getLength();
		if (length < 0) {
			length = 10;
		}
		pageNo = pagination.getPageNo();
		if(pageNo < 1) pageNo = 1;
		pageSize = length;
		this.total = total;
		pageTotal = total / length;
		if (pageTotal * length < total) pageTotal ++;
		this.beans = beans;
	}
	
	public PageResult(long pageNo, int pageSize, long total, List<T> beans) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.total = total;
		this.beans = beans;
		handlePage();
	}

	public long getPageNo() {
		return pageNo;
	}

//	public void setPageNo(int pageNo) {
//		this.pageNo = pageNo;
//	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getBeans() {
		return beans;
	}

	public void setBeans(List<T> beans) {
		this.beans = beans;
	}
	
	public void handlePage() {
		if (pageSize < 0) pageSize = 10;
		pageTotal = total / pageSize;
		if (pageTotal * pageSize < total) {
			pageTotal++;
		}
	}

	public void setPageNo(long pageNo) {
		this.pageNo = pageNo;
	}

	public boolean isFirst() {
		return pageNo == 0;
	}

	public boolean isEnd() {
		return pageNo == pageTotal - 1;
	}

	public boolean hasNext() {
		return pageNo < pageTotal - 1;
	}

	public boolean hasPrevious() {
		return pageNo > 0;
	}

	public long getPageTotal() {
		return pageTotal;
	}
	
	public void next() {
		if (this.hasPrevious()) {
			pageNo++;
		}
		throw new IndexOutOfBoundsException("没有下一页");
	}

	public void previous() {
		if (this.hasPrevious()) {
			pageNo--;
		}
		throw new IndexOutOfBoundsException("没有上一页");
	}
	
}

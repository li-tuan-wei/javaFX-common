package ldh.common;


public interface Pageable {
	
	public long getStart();
	
	public int getLength();
	
	public long getEnd();
	
	public long getPageNo();
	
	public void setPageNo(long pageNo);
	
	public int getPageSize();
	
	public void setPageSize(int pageSize);
}

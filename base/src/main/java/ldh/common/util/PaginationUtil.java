package ldh.common.util;

import ldh.common.PageResult;

public class PaginationUtil {

	public static long getFirstPage(PageResult<?> pageResult, int pageView) {
		long firstPage = 0;
		long pageNo = pageResult.getPageNo();
		long pageTotal = pageResult.getPageTotal();
		int h = pageView / 2;
		if (pageNo <= h) {
			firstPage = 1;
		} else {
			long rightTotal = pageTotal - pageNo;
			if (rightTotal > h) {
				firstPage = pageNo - h + 1;
			} else {
				firstPage = pageTotal - pageView + 1;
			}
		}
		if (firstPage < 1) firstPage = 1;
		if (pageView == 1) firstPage = pageNo;
		return firstPage;
	}
	
	public static long getEndPage(PageResult<?> pageResult, int pageView) {
		long endPage = 0;
		long pageNo = pageResult.getPageNo();
		long pageTotal = pageResult.getPageTotal();
		int h = pageView / 2;
		long rightTotal = pageTotal - pageResult.getPageNo();
		if (rightTotal <= h) {
			endPage = pageTotal;
		} else {
			if (pageNo > h) {
				endPage = pageNo + h - 1;
			} else {
				endPage = pageView;
			}
		}
		if (endPage > pageTotal) endPage = pageTotal;
		if (pageView == 1) endPage = pageNo;
		return endPage;
	}

	public static long getFirstPage(long pageNo, long pageTotal, int pageView) {
		long firstPage = 0L;
		int h = pageView / 2;
		if(pageNo <= (long)h) {
			firstPage = 1L;
		} else {
			long rightTotal = pageTotal - pageNo;
			if(rightTotal > (long)h) {
				firstPage = pageNo - (long)h + 1L;
			} else {
				firstPage = pageTotal - (long)pageView + 1L;
			}
		}

		if(firstPage < 1L) {
			firstPage = 1L;
		}

		if(pageView == 1) {
			firstPage = pageNo;
		}

		return firstPage;
	}

	public static long getEndPage(long pageNo, long pageTotal, int pageView) {
		long endPage = 0L;
		int h = pageView / 2;
		long rightTotal = pageTotal - pageNo;
		if(rightTotal <= (long)h) {
			endPage = pageTotal;
		} else if(pageNo > (long)h) {
			endPage = pageNo + (long)h - 1L;
		} else {
			endPage = (long)pageView;
		}

		if(endPage > pageTotal) {
			endPage = pageTotal;
		}

		if(pageView == 1) {
			endPage = pageNo;
		}

		return endPage;
	}
}


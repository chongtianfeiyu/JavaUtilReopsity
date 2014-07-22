package com.yang.base.fenye;

import java.util.List;

import org.apache.commons.lang.StringUtils;
 
/**
 * @Description	: 分页封装对象
 *
 * @Version		: V1.0 
 * @author		: yangxiaodong
 * @Email		: coder.yang2010@gmail.com
 * @date  		: 2014-3-24 下午3:11:08 
 */
public class PaginationSupport {

	/**
	 * @Fields currentIndex : 当前页
	 */
	private int currentIndex;		 
	/**
	 * @Fields pageSize : 每页显示记录数
	 */
	private int pageSize;		
	/**
	 * @Fields totalCount : 记录总数
	 */
	private int totalCount;		 
	/**
	 * @Fields pageCount : 页总数
	 */
	private int pageCount;		
	/**
	 * @Fields items : 记录结果集list对象
	 */
	private List<?> items;		
	/**
	 * @Fields indexCountOnShow : 每页显示几个page数字
	 */
	private int indexCountOnShow;	
	/**
	 * @Fields startIndex : 开始分页的下标索引值
	 */
	private int startIndex;			
	/**
	 * @Fields previousIndex : 上页
	 */
	private int previousIndex;		
	/**
	 * @Fields nextIndex : 下页
	 */
	private int nextIndex;			
	/**
	 * @Fields endIndex : 尾页
	 */
	private int endIndex;			 

	public int getCurrentIndex() {
		return currentIndex;
	}
	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public List<?> getItems() {
		return items;
	}
	public List<?> getList() {
		return items;
	}
	public void setItems(List<?> items) {
		this.items = items;
	}
	public int getIndexCountOnShow() {
		return indexCountOnShow;
	}
	public void setIndexCountOnShow(int indexCountOnShow) {
		this.indexCountOnShow = indexCountOnShow;
	}
	public int getStartRow() {
		return startIndex;
	}
	public int getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	public int getPreviousIndex() {
		return previousIndex;
	}
	public void setPreviousIndex(int previousIndex) {
		this.previousIndex = previousIndex;
	}
	public int getNextIndex() {
		return nextIndex;
	}
	public void setNextIndex(int nextIndex) {
		this.nextIndex = nextIndex;
	}
	public int getEndIndex() {
		return endIndex;
	}
	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}
	
	/**
	 * 分页支持初始化
	 */
	private void init() {
		if(pageSize <= 0) pageSize = SysConstant.PAGINATION_PAGESIZE;
		if (totalCount > 0) {
			pageCount = totalCount / pageSize;
			if (totalCount % pageSize > 0){
				pageCount++;
			}
		} else {
			pageCount = 0;
		}
		endIndex = pageCount;
		if(currentIndex <= 0) currentIndex = 1;
		if(currentIndex > pageCount) currentIndex = pageCount;
		startIndex = (currentIndex - 1) * pageSize;
		nextIndex = currentIndex < endIndex ? currentIndex + 1 : endIndex;
		previousIndex = currentIndex <= 1 ? 1 : currentIndex - 1;
	}
	
	/**
	 * 每页显示的page数字间的开始page索引值
	 * @return
	 */
	public int getStartIndexOnShow() {
		if (currentIndex < indexCountOnShow / 2 + 1){
			return 1;
		}
		if (currentIndex > endIndex - (indexCountOnShow / 2 - 1)){
			return endIndex - (indexCountOnShow - 1) <= 0 ? 1 : endIndex - (indexCountOnShow - 1);
		} else {
			if(pageCount - (currentIndex - indexCountOnShow / 2) <= indexCountOnShow-2){
				if(currentIndex - indexCountOnShow / 2 - 1 > 0){
					return currentIndex - indexCountOnShow / 2 - 1;
				}
			}
			return currentIndex - indexCountOnShow / 2;
		}
	}
	
	/**
	 * 每页显示的page数字间的结束page索引值
	 * @return
	 */
	public int getEndIndexOnShow() {
		if (currentIndex < indexCountOnShow / 2 + 1){
			if (endIndex > indexCountOnShow)
				return indexCountOnShow;
			else 
				return endIndex;
		}
		if (currentIndex > endIndex - (indexCountOnShow / 2 - 1)){
			return endIndex;
		} else {
			if((currentIndex + indexCountOnShow / 2) >= pageCount){
				return pageCount;
			}
			return currentIndex + indexCountOnShow / 2;
		}
	}
	
	/**
	 * 分页支持
	 * 
	 * @param totalCount 	记录总数
	 * @param pageSize 		每页最大显示记录数
	 * @param currentPage 	当前页值
	 */
	public PaginationSupport(int totalCount, int pageSize, String currentPage) {
		this.indexCountOnShow = SysConstant.PAGINATION_INDEX_COUNT_ONSHOW;
		this.pageSize = SysConstant.PAGINATION_PAGESIZE;
		this.currentIndex = 0;
		this.setPageSize(pageSize);
		this.setTotalCount(totalCount);
		if(StringUtils.isNotEmpty(currentPage)){
			this.setCurrentIndex(Integer.parseInt(currentPage));
		}
		init();
	}
}
package com.yang.javalib.json.dto;

/**
 * @Description: TaskJsonDTO 任务ID.

{"id":100702,"description":"中粮我买网是由世界500强企业中粮集团有限公司于2009年投资创办的食品类B2C电子商务网站。中粮我买网致力于打造中国最大、最安全的食品购物网站。我买网坚持的使命是让更多用户享受到更便捷的购物，吃上更放心的食品。中粮我买网商品包括：休闲食品、粮油、冲调品、饼干蛋糕、婴幼食品、果汁饮料、酒类、茶叶、调味品、方便食品和早餐食品等百种品类。是办公室白领、居家生活和年轻一族的首选食品网络购物网站。网址：http://sh.womai.com/index-100-0.htm",
"taskCount":1297,"days":"7天","taskType":"2","businessCirclesId":null,"margins":"200,000","activeFlag":1,
"penalty":"546","totalReward":"1,092","totalNum":"21","showTime":"5秒","taskName":"我买网-顶级初榨橄榄油",
"oneDayNum":"3","isAbnormal":0,"tagConfValue":"设置推荐人奖励","tagId":null,"tagConfStatus":null,
"tagFlag":null,"adsType":0,"videoUrl":null,"taskSeq":1011,"starNum":4,"bussinessCirclesName":null,"couponId":null,"couponName":null,"couponContent":null,"couponReceiveCount":0,
"isNew":1,"nowShowDate":"2014-04-11","cpaUrl":null,"cpaImgUrl":null}
 * @author yangxiaodon
 * @Email  coder.yang2010@gmail.com
 * @date  2014-3-19 下午8:49:48 
 */
public class TaskDTO {
	
	/**
	 * @Fields : 任务ID
	 */
	private Integer id ;
	
	/**
	 * @Fields : 任务描述
	 */
	private String description;
	
	/**
	 * @Fields : 任务被领取数量
	 */
	private Integer taskCount;
	
	/**
	 * @Fields : 任务几天能完成
	 */
	private String days;
	
	/**
	 * @Fields : 任务类型
	 *  {"count":84,"taskType":":"2"},   广告任务
	 *  {"count":21,"taskType":"3"}, vip任务
	 *  {"count":1,"taskType":"5"},
	 *  {"count":11,"taskType":"11"}, 这种类型是限量的. 部数量有限制,但是没有找到领取限制的. 
	 *  {"count":16,"taskType":"9"}, 体验任务
	 *  {"count":72,"taskType":"video"}, 视频 任务
	 *  {"count":3,"taskType":"newQuestionnaireTask"}  问卷任务
	 */
	private int taskType;
	
	/**
	 * @Fields : 任务保证金
	 */
	private String penalty; // "penalty":"1,200"
	
	/**
	 * @Fields : 完成奖金
	 */
	private String totalReward; //"totalReward":"1,092" 
	
	/**
	 * @Fields : 任务总数量
	 */
	private int totalNum;
	
	/**
	 * @Fields : 任务每次展示时间 
	 */
	String showTime; 
	
	/**
	 * @Fields : 任务名字
	 */
	String taskName;
	
	/**
	 * @Fields : 一日可执行次数
	 */
	private int oneDayNum;
	
	/**
	 * @Fields : TODO
	 */
	private int taskSeq;
	
	/**
	 * @Fields : 是否是新任务 ? 猜 的
	 */
	private int isNew;
	
	/**
	 * @Fields : 这个不太理解,难道是任务到期时间 ? 会是空的哦
	 */
	private String nowShowDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getTaskCount() {
		return taskCount;
	}

	public void setTaskCount(Integer taskCount) {
		this.taskCount = taskCount;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public int getTaskType() {
		return taskType;
	}

	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}

	public String getPenalty() {
		return penalty;
	}

	public void setPenalty(String penalty) {
		this.penalty = penalty;
	}

	public String getTotalReward() {
		return totalReward;
	}

	public void setTotalReward(String totalReward) {
		this.totalReward = totalReward;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public String getShowTime() {
		return showTime;
	}

	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public int getOneDayNum() {
		return oneDayNum;
	}

	public void setOneDayNum(int oneDayNum) {
		this.oneDayNum = oneDayNum;
	}

	public int getTaskSeq() {
		return taskSeq;
	}

	public void setTaskSeq(int taskSeq) {
		this.taskSeq = taskSeq;
	}

	public int getIsNew() {
		return isNew;
	}

	public void setIsNew(int isNew) {
		this.isNew = isNew;
	}

	public String getNowShowDate() {
		return nowShowDate;
	}

	public void setNowShowDate(String nowShowDate) {
		this.nowShowDate = nowShowDate;
	}
	
	 
	
	String businessCirclesId;
	String margins;
	int activeFlag;
	String isAbnormal;
	String tagConfValue;
	String tagId;
	String tagConfStatus;
	String tagFlag;
	String adsType;
	String videoUrl;
	String starNum;
	String bussinessCirclesName;
	String couponId;
	String couponName;
	String couponContent;
	String couponReceiveCount;
	String cpaUrl;
	String cpaImgUrl;

	public String getBusinessCirclesId() {
		return businessCirclesId;
	}

	public void setBusinessCirclesId(String businessCirclesId) {
		this.businessCirclesId = businessCirclesId;
	}

	public String getMargins() {
		return margins;
	}

	public void setMargins(String margins) {
		this.margins = margins;
	}

	public int getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(int activeFlag) {
		this.activeFlag = activeFlag;
	}

	public String getIsAbnormal() {
		return isAbnormal;
	}

	public void setIsAbnormal(String isAbnormal) {
		this.isAbnormal = isAbnormal;
	}

	public String getTagConfValue() {
		return tagConfValue;
	}

	public void setTagConfValue(String tagConfValue) {
		this.tagConfValue = tagConfValue;
	}

	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	public String getTagConfStatus() {
		return tagConfStatus;
	}

	public void setTagConfStatus(String tagConfStatus) {
		this.tagConfStatus = tagConfStatus;
	}

	public String getTagFlag() {
		return tagFlag;
	}

	public void setTagFlag(String tagFlag) {
		this.tagFlag = tagFlag;
	}

	public String getAdsType() {
		return adsType;
	}

	public void setAdsType(String adsType) {
		this.adsType = adsType;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getStarNum() {
		return starNum;
	}

	public void setStarNum(String starNum) {
		this.starNum = starNum;
	}

	public String getBussinessCirclesName() {
		return bussinessCirclesName;
	}

	public void setBussinessCirclesName(String bussinessCirclesName) {
		this.bussinessCirclesName = bussinessCirclesName;
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public String getCouponContent() {
		return couponContent;
	}

	public void setCouponContent(String couponContent) {
		this.couponContent = couponContent;
	}

	public String getCouponReceiveCount() {
		return couponReceiveCount;
	}

	public void setCouponReceiveCount(String couponReceiveCount) {
		this.couponReceiveCount = couponReceiveCount;
	}

	public String getCpaUrl() {
		return cpaUrl;
	}

	public void setCpaUrl(String cpaUrl) {
		this.cpaUrl = cpaUrl;
	}

	public String getCpaImgUrl() {
		return cpaImgUrl;
	}

	public void setCpaImgUrl(String cpaImgUrl) {
		this.cpaImgUrl = cpaImgUrl;
	}

	@Override
	public String toString() {
		return "TaskDTO [id=" + id + ", description=" + description + ", taskCount=" + taskCount + ", days=" + days
				+ ", taskType=" + taskType + ", penalty=" + penalty + ", totalReward=" + totalReward + ", totalNum=" + totalNum
				+ ", showTime=" + showTime + ", taskName=" + taskName + ", oneDayNum=" + oneDayNum + ", taskSeq=" + taskSeq
				+ ", isNew=" + isNew + ", nowShowDate=" + nowShowDate + ", businessCirclesId=" + businessCirclesId + ", margins="
				+ margins + ", activeFlag=" + activeFlag + ", isAbnormal=" + isAbnormal + ", tagConfValue=" + tagConfValue
				+ ", tagId=" + tagId + ", tagConfStatus=" + tagConfStatus + ", tagFlag=" + tagFlag + ", adsType=" + adsType
				+ ", videoUrl=" + videoUrl + ", starNum=" + starNum + ", bussinessCirclesName=" + bussinessCirclesName
				+ ", couponId=" + couponId + ", couponName=" + couponName + ", couponContent=" + couponContent
				+ ", couponReceiveCount=" + couponReceiveCount + ", cpaUrl=" + cpaUrl + ", cpaImgUrl=" + cpaImgUrl + "]";
	}
	
	
	
}

package com.mobian.pageModel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MbOrderExt extends MbOrder implements Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Integer sendNumber;
	private Integer backNumber;
	private Integer leaveNumber;

	public Integer getSendNumber() {
		return sendNumber;
	}

	public void setSendNumber(Integer sendNumber) {
		this.sendNumber = sendNumber;
	}

	public Integer getBackNumber() {
		return backNumber;
	}

	public void setBackNumber(Integer backNumber) {
		this.backNumber = backNumber;
	}

	public Integer getLeaveNumber() {
		return leaveNumber;
	}

	public void setLeaveNumber(Integer leaveNumber) {
		this.leaveNumber = leaveNumber;
	}
}

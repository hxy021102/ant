package com.mobian.model;

import java.util.Date;

public interface IEntity {
	void setAddtime(Date date);
	Date getAddtime();
	void setUpdatetime(Date date);
}

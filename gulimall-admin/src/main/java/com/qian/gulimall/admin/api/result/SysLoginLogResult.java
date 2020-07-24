package com.qian.gulimall.admin.api.result;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统登录日志
 * 
 * @author QIAN
 * @email 794308528@qq.com
 * @date 2020-07-24 15:17:44
 */
@Data
public class SysLoginLogResult implements Serializable {

	/**
	 * ID
	 */
	private Long id;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 用户操作
	 */
	private String operation;
	/**
	 * 请求方法
	 */
	private String method;
	/**
	 * 请求参数
	 */
	private String params;
	/**
	 * IP地址
	 */
	private String ip;
	/**
	 * 创建时间
	 */
	private Date createDate;

}

package com.qian.gulimall.admin.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 文件上传
 *
 * @author QIAN
 * @email 794308528@qq.com
 * @date 2020-04-19 08:57:20
 */
@Data
@TableName("sys_oss")
public class SysOssEntity implements Serializable {

	private static final long serialVersionUID = 6056087424450298008L;
	/**
	 *
	 */
	@TableId
	private Long id;

	/**
	 * URL地址-分组
	 */
	private String urlGroup;

	/**
	 * URL地址-路径
	 */
	private String urlPath;

	/**
	 * 原文件名称
	 */
	private String originalFilename;

	/**
	 * 上传者ID
	 */
	private Long uploadUserId;

	/**
	 * 上传者IP地址
	 */
	private String ip;

	/**
	 * 状态   0：隐藏   1：显示
	 */
	private Integer status;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 创建时间
	 */
	private Date createDate;

}

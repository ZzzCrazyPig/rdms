package com.rdms.base.vo;

/**
 * 描述系统中发生错误的错误代码 与前台相互对应
 * @author CrazyPig
 *
 */
public abstract class ErrorCode {
	
	// 表示登陆状态失效
	public static final String NO_LOGIN = "NO_LOGIN";
	
	// 表示权限错误
	public static final String NO_PERMISSION = "NO_PERMISSION";
	
	// 表示系统错误
	public static final String SYS_ERROR = "SYS_ERROR";

}

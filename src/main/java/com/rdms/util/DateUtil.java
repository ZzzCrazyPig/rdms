package com.rdms.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	public static String DATEBLOCK_SPLITER = "~";
	
	/**
	 * 计算两个日期之间的天数(包括两个日期在内)
	 * @param startDateStr 开始日期
	 * @param endDateStr 结束日期
	 * @return
	 * @throws ParseException
	 */
	public static int daysBetween(String startDateStr, String endDateStr) throws ParseException {
		Calendar c = Calendar.getInstance();
		c.setTime(DATE_FORMAT.parse(startDateStr));
		long start = c.getTimeInMillis();
		c.setTime(DATE_FORMAT.parse(endDateStr));
		long end = c.getTimeInMillis();
		int days = (int) ((end - start) / (60 * 60 * 24 * 1000)) + 1;
		return days;
	}
	
	/**
	 * 计算两个日期之间的工作日(包括两个日期在内)
	 * @param startDateStr 开始日期
	 * @param endDateStr 结束日期
	 * @param holidays 该日期之间的额外节假日,非周六日
	 * @return
	 */
	public static int workDaysBetween(String startDateStr, String endDateStr, int holidays) {
		int workDays = -1;
		try {
			int days = daysBetween(startDateStr, endDateStr);
			workDays = days / 7 * 5;
			Calendar c = Calendar.getInstance();
			c.setTime(DATE_FORMAT.parse(startDateStr));
			for(int i = 0; i < days % 7; i++) {
				c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH + i ));
				int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
				if(dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY) {
					workDays++;
				}
			}
			workDays = workDays - holidays;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return workDays;
	}
	
	/**
	 * 求两个日期之间的工作日
	 * @param startDateStr 开始日期
	 * @param endDateStr 结束日期
	 * @param holidayBlocks 非周末的节假日区间数组(默认日期区间数组格式为["2015-02-17~2015-02-24", ..., ...])
	 * @return
	 * @throws ParseException
	 */
	public static int workDaysBewteen(String startDateStr, String endDateStr, String[] holidayBlocks) throws ParseException {
		int holidays = 0;
		if(holidayBlocks != null && holidayBlocks.length > 0) {
			for(int i = 0; i < holidayBlocks.length; i++) {
				String holidayBlock = holidayBlocks[i];
				holidays = holidays + daysBetween(holidayBlock, startDateStr, endDateStr);
			}
		}
		return workDaysBetween(startDateStr, endDateStr, holidays);
	}
	
	/**
	 * 求日期区间在开始日期和结束日期之间的交集天数
	 * @param dateBlock 给定日期区间
	 * @param startDateStr 开始日期
	 * @param endDateStr 结束日期
	 * @return
	 * @throws ParseException
	 */
	private static int daysBetween(String dateBlock, String startDateStr, String endDateStr) throws ParseException {
		String dateBlockStartStr = dateBlock.split(DATEBLOCK_SPLITER)[0];
		String dateBlockEndStr = dateBlock.split(DATEBLOCK_SPLITER)[1];
		Date dateBlockStart = DATE_FORMAT.parse(dateBlockStartStr);
		Date dateBlockEnd = DATE_FORMAT.parse(dateBlockEndStr);
		Calendar c = Calendar.getInstance();
		c.setTime(dateBlockStart);
		long dateBlockStartTimeMillis = c.getTimeInMillis();
		c.setTime(dateBlockEnd);
		long dateBlockEndTimeMillis = c.getTimeInMillis();
		Date startDate = DATE_FORMAT.parse(startDateStr);
		Date endDate = DATE_FORMAT.parse(endDateStr);
		c.setTime(startDate);
		long startDateTimeMillis = c.getTimeInMillis();
		c.setTime(endDate);
		long endDateTimeMillis = c.getTimeInMillis();
		int days = 0;
		if(endDateTimeMillis < startDateTimeMillis) return -1;
		if(dateBlockEndTimeMillis < dateBlockStartTimeMillis) return -1;
		if(dateBlockStartTimeMillis < startDateTimeMillis) {
			if(dateBlockEndTimeMillis < endDateTimeMillis) {
				days = (int) ((dateBlockEndTimeMillis - startDateTimeMillis) / (60 * 60 * 24 * 1000)) + 1;
			} else {
				days = (int) ((endDateTimeMillis - startDateTimeMillis) / (60 * 60 * 24 * 1000)) + 1;
			}
		} else {
			if(dateBlockEndTimeMillis < endDateTimeMillis) {
				days = (int) (dateBlockEndTimeMillis - dateBlockStartTimeMillis) / (60 * 60 * 24 * 1000) + 1;
			} else {
				days = (int) (endDateTimeMillis - dateBlockStartTimeMillis) / (60 * 60 * 24 * 1000) + 1;
			}
		}
		return days;
	}

}

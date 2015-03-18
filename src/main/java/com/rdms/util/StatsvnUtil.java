package com.rdms.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class StatsvnUtil {
	
	// 执行statsvn,生成统计结果,输出到outputDir对应的路径中
	public static boolean runStatsvn(String statsvnDir,String url, String checkoutDir, String outputDir) throws IOException, InterruptedException {
		// checkout svn仓库代码到本地
		
		// 生成svn log文件
		
		// 执行java -jar statsvn.jar 命令对log文件进行解析,将统计结果输出到outputDir中
		
		return generateBatAndExec(statsvnDir, url, checkoutDir, outputDir);
		
	}
	
	public static boolean generateBatAndExec(String statsvnDir, String url, String checkoutDir, String outputDir) throws IOException, InterruptedException {
		final String BAT_FILE = checkoutDir + "/bat.bat";
		final String SVN_CHECKOUT_CMD = "svn co " + url + " " + checkoutDir;
		final String LOG_PATH = checkoutDir + "/svn.log";
		final String SVN_LOG_CMD = "svn log -v --xml " + checkoutDir + " > " + LOG_PATH;
		final String STATSVN_CMD = "java -jar " + statsvnDir + "/statsvn.jar " + " -output-dir " + outputDir + " -charset UTF-8 " +  LOG_PATH + " " + checkoutDir;
		File file = new File(BAT_FILE);
		File parentFile = file.getParentFile();
		if(parentFile != null && !parentFile.exists()) {
			parentFile.mkdirs();
		}
		if(!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream out = new FileOutputStream(file);
		StringBuffer sb = new StringBuffer();
		sb.append(SVN_CHECKOUT_CMD + "\r\n");
		sb.append(SVN_LOG_CMD + "\r\n");
		sb.append(STATSVN_CMD + "\r\n");
		out.write(sb.toString().getBytes());
		out.flush();
		out.close();
		Process p = Runtime.getRuntime().exec(file.getAbsolutePath());
		if(p.waitFor() != 0) {
			return false;
		}
		return true;
	}

}

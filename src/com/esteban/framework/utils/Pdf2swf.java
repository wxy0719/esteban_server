package com.esteban.framework.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author nlb
 * @version . 把pdf,jpeg,font,gif,pgn,wav转化为swf文件
 */
public class Pdf2swf {

	/**
	 * swf格式
	 */
	public static final String TYPE_SWF = "swf";

	private final String CONVERTFILETYPE = "pdf,jpg,jpeg,font,gif,png,wav";
	private String swftoolsPath="";

	/**
	 * @param swftoolsPath
	 *            用于进行把文件转化为swf的工具地址
	 */
	public Pdf2swf(String swftoolsPath) {
		this.swftoolsPath = swftoolsPath;
	}

	/**
	 * 把文件转化为swf格式支持"pdf,jpg,jpeg,font,gif,png,wav"
	 *
	 * @param sourceFilePath
	 *            要进行转化为swf文件的地址
	 * @param swfFilePath
	 *            转化后的swf的文件地址
	 * @return
	 */
	public boolean convertFileToSwf(String sourceFilePath, String swfFilePath) {
		System.out.println("开始转化文件到swf格式");
		if (swftoolsPath == null || swftoolsPath == "") {
			System.out.println("未指定要进行swf转化工具的地址！！！");
			return false;
		}
		String filetype = sourceFilePath.substring(sourceFilePath.lastIndexOf(".") + 1);
		// 判读上传文件类型是否符合转换为pdf
		System.out.println("判断文件类型通过");
		if (CONVERTFILETYPE.indexOf(filetype.toLowerCase()) == -1) {
			System.out.println("当前文件不符合要转化为SWF的文件类型！！！");
			return false;
		}
		File sourceFile = new File(sourceFilePath);

		if (!sourceFile.exists()) {
			System.out.println("要进行swf的文件不存在！！！");
			return false;
		}
		StringBuilder commandBuidler = new StringBuilder(swftoolsPath);
		File swfFile = new File(swfFilePath);
		if (!swfFile.getParentFile().exists()) {
			swfFile.getParentFile().mkdirs();
		}
		if (filetype.toLowerCase().equals("jpg")) {
			filetype = "jpeg";
		}
		List<String> command = new ArrayList<String>();
		command.add(this.swftoolsPath + filetype.toLowerCase() + "2swf.exe");// 从配置文件里读取
		command.add("-z");
		command.add("-s");
		command.add("flashversion=");
		command.add("-s");
		command.add("polybitmap");// 加入polybitmap的目的是为了防止出现大文件或图形过多的文件转换时的出错，没有生成swf文件的异常
		command.add(sourceFilePath);
		command.add("-o");
		command.add(swfFilePath);
		try {
			ProcessBuilder processBuilder = new ProcessBuilder();
			processBuilder.command(command);
			Process process = processBuilder.start();
			System.out.println("开始生成swf文件..");
			dealWith(process);
			try {
				process.waitFor();// 等待子进程的结束，子进程就是系统调用文件转换这个新进程
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			File swf = new File(swfFilePath);
			if (!swf.exists()) {
				return false;
			}
			System.out.println("转化SWF文件成功!!!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("转化为SWF文件失败!!!");
			e.printStackTrace();
			return false;
		}

		return true;
	}

	private void dealWith(final Process pro) {
		// 下面是处理堵塞的情况
		try {
			new Thread() {
				public void run() {
					BufferedReader br = new BufferedReader(
							new InputStreamReader(pro.getInputStream()));
					String text;
					try {
						while ((text = br.readLine()) != null) {
							System.out.println(text);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			new Thread() {
				public void run() {
					BufferedReader br = new BufferedReader(
							new InputStreamReader(pro.getErrorStream()));// 这定不要忘记处理出理时产生的信息，不然会堵塞不前的
					String text;
					try {
						while ((text = br.readLine()) != null) {
							System.err.println(text);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Pdf2swf a=new Pdf2swf("C:/Program Files (x86)/SWFTools/");
		long begin_time=new Date().getTime();
		a.convertFileToSwf("e:/test.pdf", "e:/test_.swf");
		long end_time=new Date().getTime();
		System.out.println("result:"+(end_time-begin_time));
	}
}
package com.dj.util;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/** 
 * 附件上传工具类
 * Created by  on 2015/04/11 0021.
 */
public class UploadUtil {
	
	HttpServletRequest request = null;
	
	/**
	 * 默认构造函数
	 */
	public UploadUtil() {
		
	}
	
	/**
	 * 默认构造函数
	 */
	public UploadUtil(HttpServletRequest req) {
		request = req;
	}

	/**
	 * 定义配置文件存放map
	 */
	private static Map<String, String> paramOut = null;

	/**
	 * 读取配置文件
	 *
	 * @return Map<String,String>
	 */
	public Map<String, String> getFileSet() {
		if (paramOut == null) {
			try {
				Properties props = new Properties();
				paramOut = new HashMap<String, String>();
				props.load(getClass().getClassLoader().getResourceAsStream("config/ForeWebConfig.properties"));
				paramOut.put("uploadLocalPath", props.getProperty("uploadLocalPath"));
				paramOut.put("uploadFlag", props.getProperty("uploadFlag"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return paramOut;
	}
	
	/**
	 * 校验上传文件格式及大小
	 * @param file
	 * @param fileTypes 
	 * @param mSize 最大兆字节数
	 * @return
	 */
	public Map<String, String> validateFile(MultipartFile file, String[] fileTypes, int mSize) {
		Map<String, String> map = new HashMap<String, String>();
		String result = "true";
		String errMsg = "";
		String allowTypes = "";
		for (int i=0; i<fileTypes.length; i++) {
			if (i==0)
				allowTypes = fileTypes[0].toLowerCase();
			else 
				allowTypes += "|" + fileTypes[i].toLowerCase();
		}
		// 获取文件名称
		String fileName = file.getOriginalFilename();
		// 获取文件名后缀
		String fileNameExt = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
		if (allowTypes.indexOf(fileNameExt)==-1){
			result = "false";
			errMsg = "上传文件格式规定为[ "+ allowTypes +" ]！";
		}
		if (file.getSize() > 1024 * 1024 * mSize) {
			result = "false";
			errMsg = "上传文件不能大于"+ String.valueOf(mSize) +"M！";
		}
		map.put("result", result);
		map.put("errMsg", errMsg);
		return map;
	}

	/**
	 * 保存文件
	 * 
	 * @param CommonsMultipartFile file
	 * @return Map<String, String>
	 */
	public Map<String, String> saveFile(MultipartFile file) {
		Map<String, String> retMap = new HashMap<String, String>();
		Map<String, String> paramOut = this.getFileSet();
		String uploadFlag = paramOut.get("uploadFlag");
		try {
			// 上传到本地
			if ("0".equals(uploadFlag)) {
				String uploadLocalPath = paramOut.get("uploadLocalPath");
				String pathByDate = new SimpleDateFormat("yyyy/MM/dd/").format(new Date());
				String realPath = request.getSession().getServletContext().getRealPath(uploadLocalPath + pathByDate);
				File dir = new File(realPath);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				if (!file.isEmpty()) {
					String newName = getFileNewName();
					String fileName = file.getOriginalFilename();
					String fileNewNameExt = newName + "." + fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
					File saveFile = new File(dir, fileNewNameExt);
					// 拿到输出流，同时重命名上传的文件
					FileOutputStream os = new FileOutputStream(saveFile);
					// 拿到上传文件的输入流
					InputStream in = file.getInputStream();
					// 以写字节的方式写文件
					int b = 0;
					while ((b = in.read()) != -1) {
						os.write(b);
					}
					os.flush();
					os.close();
					in.close();
					// 返回文件Url、文件名
					retMap.put("fileUrl", uploadLocalPath + pathByDate + fileNewNameExt);
					retMap.put("fileName", fileNewNameExt);
				}
			} else {
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return retMap;
	}
	
	/**
	 * 生成新文件名
	 */
	private String getFileNewName() {
		Date dt = new Date();
		Long time = dt.getTime();
		return time.toString();
	}
	
}

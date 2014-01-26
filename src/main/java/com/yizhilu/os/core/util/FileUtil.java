package com.yizhilu.os.core.util;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;

/**
 * 
 * @ClassName com.supergenius.sns.util.image.FileUtil
 * @description kindeditoe文件上传类
 * @author : qinggang.liu 305050016@qq.com
 * @Create Date : 2013-12-13 下午2:49:02
 */
public class FileUtil {

	private static Logger logger = Logger.getLogger(FileUtil.class);
	// 读取配置文件类
	public static PropertyUtil propertyUtil = PropertyUtil
			.getInstance("project");

	private static final String pathfix = propertyUtil
			.getProperty("file.pathfix");// 统一传到pathfix下

	private static final String tempPath = "temp";// 临时目录

	/**
	 * 
	 * 
	 * @param imgFile
	 *            文件
	 * @param paths
	 *            路径 0物理 1 URL
	 * @returned JsonObject
	 */
	public static JsonObject saveImage(MultipartFile imgFile, String[] paths) {

		JsonObject obj = new JsonObject();
		try {
			// 存储物理路径
			String savePath = paths[0];
			// 返回的url
			String urlPath = paths[1];
			String newFileName = "";// 新的文件名
			String upFileName = imgFile.getOriginalFilename();
			// 保存第一张图片
			if (StringUtils.isNotEmpty(upFileName)) {
				if (!checkFileName(upFileName)) {
					obj.addProperty("error", -1);
					obj.addProperty("message",
							"上传文件扩展名不允许。\n只允许 gif,jpg,jpeg,png,bmp格式。");
					return obj;
				}
				// 文件大小不能超过5M
				if (imgFile.getSize() > 5242880) {
					obj.addProperty("error", -1);
					obj.addProperty("message", "上传文件大小不能超过5M");
					return obj;
				}
				// 加锁为防止文件名重复
				final Lock lock = new ReentrantLock();
				lock.lock();
				try {
					// 加锁为防止文件名重复
					newFileName = getRandomFileNameString(upFileName);
				} finally {
					lock.unlock();
				}
				// 锁结束
				File isD = new File(savePath);
				// 校验如果目录不存在，则创建目录
				if (!isD.isDirectory()) {
					isD.mkdirs();
				}
				if (!isD.exists()) {
					synchronized (FileUtil.class) {
						isD.mkdirs();
					}
				}
				String saveFilename = savePath + File.separator + newFileName;
				// 保存文件
				imgFile.transferTo(new File(saveFilename));
				obj.addProperty("error", 0);
				urlPath = urlPath + "/" + newFileName;
				obj.addProperty("url", urlPath);
			} else {
				obj.addProperty("error", -1);
				obj.addProperty("message", "文件名为空");
			}

			return obj;

		} catch (Exception e) {
			logger.error("+++upload kindeditor images error", e);
			obj.addProperty("error", -1);
			obj.addProperty("message", "上传异常，请稍后再试");
			return obj;
		}

	}

	/**
	 * 获得随机的数字为文件名，有效防止文件名重读
	 * 
	 * @param fileName
	 *            传来的文件名
	 * @param cusid
	 *            用户id
	 * @return 返回新的文件名
	 */
	public static String getRandomFileNameString(String fileName) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(System.currentTimeMillis());// 当前时间
		// 增加6位随机的数字
		buffer.append(getRandomString(6));
		// 添加后缀名
		buffer.append(fileName.substring(fileName.lastIndexOf("."),
				fileName.length()));
		return buffer.toString();
	};

	/**
	 * 获取指定长度的随机数字
	 * 
	 * @param len
	 * @return
	 */
	public static String getRandomString(int len) {
		Random random = new Random();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < len; i++) {
			buffer.append(random.nextInt(10));
			random = new Random();
		}
		return buffer.toString();
	}

	/**
	 * 
	 * 检查是否是符合的后缀名
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean checkFileName(String fileName) {
		// 定义允许上传的文件扩展名
		HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		/*
		 * 现在只有图片 extMap.put("flash", "swf,flv"); extMap.put("media",
		 * "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		 * extMap.put("file",
		 * "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");
		 */
		// 检查扩展名
		String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1)
				.toLowerCase();
		if (!Arrays.<String> asList(extMap.get("image").split(",")).contains(
				fileExt)) {
			return false;
		}
		return true;
	}

	/**
	 * 获得存储的物理路径
	 * 
	 * @param request
	 * @return
	 */
	public static String[] getSavePathByRequest(HttpServletRequest request) {

		String base = request.getParameter("base");// 区分项目的变量 如：恒企、仁和等区分
		String param = request.getParameter("param");// 区分模块的变量 如：考试、商品、课程等区分
		String dateStr = DateUtils.toString(new Date(), "yyyyMMdd");

		if (StringUtils.isEmpty(base)) {
			base = "yizhilu";// 临时，未传次参数的项目统一到yizhilu目录下
		}
		if (StringUtils.isEmpty(param)) {
			param = "common";// 临时，未传的项目统一到common目录下
		}
		// 拼凑 存储物理路径
		String savePath = propertyUtil.getProperty("file.root") + pathfix + "/"
				+ base + "/" + param + "/" + dateStr;
		// 拼凑 url
		String urlPath = "/" + pathfix + "/" + base + "/" + param + "/"
				+ dateStr;

		String[] result = new String[] { savePath, urlPath };
		return result;
	}

	/**
	 * 获得存储的临时物理路径放到tempxia
	 * 
	 * @param request
	 * @return
	 */
	public static String[] getTempSavePathByRequest(HttpServletRequest request) {

		String base = request.getParameter("base");// 区分项目的变量 如：恒企、仁和等区分
		String param = request.getParameter("param");// 区分模块的变量 如：考试、商品、课程等区分
		String dateStr = DateUtils.toString(new Date(), "yyyyMMdd");

		if (StringUtils.isEmpty(base)) {
			base = "yizhilu";// 临时，未传次参数的项目统一到yizhilu目录下
		}
		if (StringUtils.isEmpty(param)) {
			param = "common";// 临时，未传的项目统一到common目录下
		}
		// 拼凑 存储物理路径
		String savePath = propertyUtil.getProperty("file.root") + pathfix + "/"
				+ tempPath + "/" + base + "/" + param + "/" + dateStr;
		// 拼凑 url
		String urlPath = "/" + pathfix + "/" + "+tempPath" + "/" + base + "/"
				+ param + "/" + dateStr;

		String[] result = new String[] { savePath, urlPath };
		return result;

	}

}

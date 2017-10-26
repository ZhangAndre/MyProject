package com.muke.util;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

/**
 * 
 * @Description: 文件上传表单接收处理
 * @author:Zheng Yanbo
 * @time:2017年9月11日 下午5:57:19
 *
 */
public class FileUpload {
	private HttpServletRequest request;
	private String path = "upload";
	private String typeReg = "";
	private String encodingType = "UTF-8";

	private Map<String, String> params = null;

	public FileUpload(HttpServletRequest request) {
		this.request = request;
	}	

	/**
	 * 
	 * @Description: 设置文件保存路径，默认为upload
	 * @param path
	 * void
	 * @exception:
	 */
	public void setPath(String path) {
		this.path = path;
	}

	public String getTypeReg() {
		return typeReg;
	}

	/**
	 * 
	 * @Description: 设置文件允许类型正则验证, 例如：[.]zip|jpg|png|jpeg|gif$
	 * @param typeReg
	 * void
	 * @exception:
	 */
	public void setTypeReg(String typeReg) {
		this.typeReg = typeReg;
	}

	public void setEncodingType(String encodingType) {
		this.encodingType = encodingType;
	}

	private String getSaveName(String fileName){
		return new Date().getTime() + getFileEx(fileName);
	}
	
	private String getFileEx(String fileName){
		return fileName.substring(fileName.lastIndexOf("."), fileName.length());
	}
	
	private boolean checkType(String fileName){
		
		Pattern reg = Pattern.compile(typeReg);
		Matcher matcher = reg.matcher(fileName);
		if (matcher.find()) {
			return true;
		}
		return false;
	}
	private Map getFileItem(File dir) throws FileUploadException, IOException{
		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		diskFileItemFactory.setRepository(dir);		// 设置路径 
		diskFileItemFactory.setSizeThreshold(1024000);
		
		ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
		
		servletFileUpload.setHeaderEncoding(encodingType);
		servletFileUpload.setSizeMax(-1);
		
		Map fileItems = servletFileUpload.parseParameterMap(request);
		
		return fileItems;
		
	}
	private File getDir(){
		
		// 获取当前项目所在目录
		String realDir = request.getSession().getServletContext().getRealPath("");
		String realPath = realDir + "\\" + path;
		
		File dir = new File(realPath);
		if (!dir.exists() || !dir.isDirectory()) {
			dir.mkdir();
		}
		
		return dir;
	}

	/**
	 * 
	 * @Description: 初始化数据
	 * @return
	 * int 	1：正常;
	 * 		-1：request参数为空;
	 * 		-2:类型不允许;
	 * 		-3：上传初始化失败;
	 * 		-4：上传初始化失败;
	 * 		-5：文件保存失败;
	 * 		-6：表单数据类型不匹配;
	 * @exception:
	 */
	public int initFormData() {
		params = new HashMap<>();
		
		if (request == null){
			return -1;
		}
		
		File dir = getDir();
		
		if (ServletFileUpload.isMultipartContent(request)){
			try{
				Map fileItems = getFileItem(dir);
				
				Set set = fileItems.keySet();
				
				for (Object object : set) {
					
					List<FileItem> ls = (List<FileItem>) fileItems.get(object);
					
					for (FileItem fileItem : ls) {
						String fieldName = fileItem.getFieldName();
						if (fileItem.isFormField()){
							String fieldValue = fileItem.getString(encodingType);
							
							params.put(fieldName, fieldValue);
							
						}
						else{
							
							String fileName = fileItem.getName();
							String saveName = getSaveName(fileName);
							
							if (checkType(fileName)){
								fileItem.write(new File(dir.getPath() + "\\" + saveName));
								
								params.put(fieldName, saveName);
							}
							else{
								return -2;
							}
						}
					}
				}
			
			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				// 上传初始化失败
				return -3;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				// 上传初始化失败
				return -4;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				// 文件保存失败
				return -5;
			}
		}
		else {
			// 表单数据类型不对
			return -6;
		}
		return 1;
	}
	
	public String getParameter(String paramName) {
				
		if (params.size() >0){
			return params.get(paramName);
		}
		
		return null;
	}
}

package com.ISMIS.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

	String uploadFile(String realPath, MultipartFile file);

	String deleteFiles(String realPath, String type);
	
	String deleteFolder(String realPath);

	String moveFile(String oldPath, String newPath);
	
	String deleteDoc(String realPath, String type, String folderPath);

}

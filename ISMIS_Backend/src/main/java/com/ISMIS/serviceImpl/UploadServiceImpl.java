package com.ISMIS.serviceImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ISMIS.service.UploadService;

@Service
public class UploadServiceImpl implements UploadService {

	@Override
	public String uploadFile(String realPath, MultipartFile file) {

		InputStream inputStream = null;
		OutputStream outputStream = null;
		String path = null;
		try {

			if (!new File(realPath).exists()) {
				new File(realPath).mkdirs();
			}

			inputStream = file.getInputStream();
			outputStream = new FileOutputStream(realPath + "/" + file.getOriginalFilename());
			int readBytes = 0;
			byte[] buffer = new byte[(int) file.getSize()];
			while ((readBytes = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, readBytes);
			}
			path = realPath + "/" + file.getOriginalFilename();
		} catch (Exception e) {
			path = "error";
			e.printStackTrace();
		} finally {
			try {
				if (outputStream != null) {
					outputStream.flush();
					outputStream.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
				System.gc();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return path;
	}

	@Override
	public String deleteFiles(String realPath, String type) {
		File[] showFiles;
		String status = null;		
		try {
			if (type.equalsIgnoreCase("Profile_Picture")) {

				File files_uploaded = new File(realPath);
				if (files_uploaded.exists()) {
					showFiles = files_uploaded.listFiles();
					for (int i = 0; i < showFiles.length; i++) {
						showFiles[i].delete();
					}
				}
			}
			status = "success";
		} catch (Exception e) {
			status = "error";
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public String moveFile(String oldPath, String newpath) {
		String fileName = null;
		String realPath = null;
		try {

			List<String> files = new LinkedList<>(Arrays.asList(oldPath.split("/")));
			fileName = files.get(files.size() - 1);

			files.remove(files.size() - 1);
			String path = String.join("/", files);

			if (new File(newpath).exists()) {
				delete(new File(newpath));
			}
			new File(newpath).mkdirs();

			Path temp = Files.move(Paths.get(oldPath), Paths.get(newpath + "/" + fileName));
			if (temp != null) {
				delete(new File(path));
			}
			realPath = newpath + "/" + fileName;
		} catch (Exception e) {
			realPath = "error";
			e.printStackTrace();
		}
		return realPath;
	}

	public static void delete(File file) throws IOException {

		if (file.isDirectory()) {
			if (file.list().length == 0) {
				file.delete();
			} else {
				String files[] = file.list();

				for (String temp : files) {
					File fileDelete = new File(file, temp);
					delete(fileDelete);
				}

				if (file.list().length == 0) {
					file.delete();
				}
			}

		} else {
			file.delete();
		}
	}

	/* To delete documents */
	@Override
	public String deleteDoc(String realPath, String type, String folderPath) {
		File[] showFiles;
		String status = null;
		try {
			if (!folderPath.isEmpty() || folderPath != null) {

				File files_uploaded = new File(realPath);
				if (files_uploaded.exists()) {
					showFiles = files_uploaded.listFiles();
					for (int i = 0; i < showFiles.length; i++) {
						showFiles[i].delete();
					}
				}
			}
			status = "success";
		} catch (Exception e) {
			status = "error";
			e.printStackTrace();
		}
		return status;
	}

	/* To delete Client folder */
	@Override
	public String deleteFolder(String realPath) {
		String status = null;
		try {
			FileUtils.deleteDirectory(new File(realPath));
			status = "success";
		} catch (Exception e) {
			status = "error";
			e.printStackTrace();
		}
		return status;
	}

}

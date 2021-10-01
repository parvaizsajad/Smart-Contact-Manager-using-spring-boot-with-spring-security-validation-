package com.smart.helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class Helper {


	//public final String uploadPath="C:\\Users\\Parva\\eclipse-workspace\\FileUploading\\src\\main\\resources\\static\\images";

public boolean fileuploadd(MultipartFile mpf) {
	boolean f=false;
	
	try {
		//we will try to upload file in two ways 
		// 1 old way
		//reading the data 
		/**InputStream in = mpf.getInputStream();
		byte[] ba=new byte[in.available()];
		in.read(ba);
		
		
		// writing the data
		FileOutputStream fis=new FileOutputStream(uploadPath+File.separator+mpf.getOriginalFilename());
		fis.write(ba);
		fis.flush();
		fis.close();*/
		
		// second is the new way using nio 
	//Files.copy(mpf.getInputStream(),Paths.get(uploadPath+File.separator+mpf.getOriginalFilename()),StandardCopyOption.REPLACE_EXISTING);
		f=true;
	} catch (Exception e) {
	e.printStackTrace();
	}
	
	
	return f;
	
}

public boolean fileupload(MultipartFile mpf) throws IOException {
	final String uploadPath=new ClassPathResource("static"+File.separator+"img").getFile().getAbsolutePath();
	boolean f=false;
	try {
	Files.copy(mpf.getInputStream(),Paths.get(uploadPath+File.separator+mpf.getOriginalFilename()),StandardCopyOption.REPLACE_EXISTING);
	f=true;
	System.out.println(uploadPath);
	return f;
} catch (Exception e) {


}


return f;

}

}

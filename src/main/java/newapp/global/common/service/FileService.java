package newapp.global.common.service;

import io.mkeasy.resolver.CommandMap;
import io.mkeasy.utils.WebUtil;
import io.mkeasy.webapp.processor.FileFactory;
import lombok.extern.slf4j.Slf4j;
import newapp.global.util.PropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

@Slf4j
@Service
public class FileService extends AbstractService {
	
    @Autowired
	PropertiesUtil propertiesUtil;
    
    @Autowired
    FileFactory fileFactory;
    
	@Autowired
	WebUtil webUtil;

	public String upload(MultipartFile file) throws Exception {
		return fileFactory.upload(file);
	}

	public void download(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception {

    	String dnKey = propertiesUtil.getProperty("download.key");

    	String dir = propertiesUtil.getProperty("download.dir");
    	if(StringUtils.isEmpty(dir))
    		dir = webUtil.getResourceRoot()+"/static/dl";

		String key = String.valueOf(commandMap.getParam("key"));
		if(!StringUtils.equals(key, dnKey)) {
			log.error("{} key is mismatch");
			return;
		}
		
		String fid = commandMap.getParam("fid");
		if(StringUtils.isEmpty(fid)) {
			log.error("{} fid is empty");
			return;
		}

		String dnFile = propertiesUtil.getProperty("download.file"+fid);
		String dnFileName = propertiesUtil.getProperty("download.file"+fid+"Name");
		String filePath = dir + "/" + dnFile;

		File file = new File(filePath);
		fileFactory.download(request, response, file, dnFileName);
	}
	
}

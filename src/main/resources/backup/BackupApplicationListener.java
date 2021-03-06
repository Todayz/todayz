package com.todayz;

import java.io.File;

import javax.servlet.ServletContextEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoaderListener;

public class BackupApplicationListener extends ContextLoaderListener {

	private static final String HOME_DIR_NAME = "todayz";

	@Override
	public void contextInitialized(ServletContextEvent event) {
		String homeDirPath = System.getenv("TODAYZ_HOME");
		if (homeDirPath == null) {
			StringBuilder builder = new StringBuilder();
			builder.append(System.getProperty("user.home"));
			builder.append(File.separator);
			builder.append(".");
			builder.append(HOME_DIR_NAME);

			homeDirPath = builder.toString();
		}

		System.setProperty("todayz.home", homeDirPath);

		Log log = LogFactory.getLog(getClass());

		if (log.isInfoEnabled()) {
			log.info("TODAYZ home directory :" + homeDirPath);
		}

		File homeDir = new File(homeDirPath);
		if (!homeDir.exists()) {
			homeDir.mkdirs();
			if (log.isDebugEnabled()) {
				log.debug("TODAYZ home directory 생성 : " + homeDirPath);
			}
		}

		makeUploadedImagesDir();
		super.contextInitialized(event);
	}

	private void makeUploadedImagesDir() {
		String homeDirPath = System.getProperty("todayz.home");
		StringBuilder sb = new StringBuilder();
		sb.append(homeDirPath);
		sb.append("upload");
		sb.append(File.separator);
		sb.append("images");
		sb.append(File.separator);

		Log log = LogFactory.getLog(getClass());

		File imagesDir = new File(sb.toString());
		if (!imagesDir.exists()) {
			imagesDir.mkdirs();
			if (log.isDebugEnabled()) {
				log.debug("Images upload 디렉토리 생성 : " + sb.toString());
			}
		}
	}
}

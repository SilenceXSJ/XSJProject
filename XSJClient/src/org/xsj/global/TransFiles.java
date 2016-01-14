package org.xsj.global;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;

public class TransFiles implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private File[] files;
	
	private FileInputStream[] fis;

	public File[] getFiles() {
		return files;
	}

	public void setFiles(File[] files) {
		this.files = files;
	}

	public FileInputStream[] getFis() {
		return fis;
	}

	public void setFis(FileInputStream[] fis) {
		this.fis = fis;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

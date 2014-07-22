package com.yang.javalib.mavenUtil.pojo;

public class MavenJarVO{
	private String  abstractPath;
	private String JarName;
	private String jarDir ;
	private String jarVersion ;
	
	public MavenJarVO(String jarName, String jarDir, String jarVersion,String abstractPath) {
		super();
		this.abstractPath = abstractPath;
		JarName = jarName;
		this.jarDir = jarDir;
		this.jarVersion = jarVersion;
	}
	
	public String getAbstractPath() {
		return abstractPath;
	}
	public void setAbstractPath(String abstractPath) {
		this.abstractPath = abstractPath;
	}
	public String getJarName() {
		return JarName;
	}
	public void setJarName(String jarName) {
		JarName = jarName;
	}
	public String getJarDir() {
		return jarDir;
	}
	public void setJarDir(String jarDir) {
		this.jarDir = jarDir;
	}
	public String getJarVersion() {
		return jarVersion;
	}
	public void setJarVersion(String jarVersion) {
		this.jarVersion = jarVersion;
	}

	@Override
	public String toString() {
		return "MavenJarVO [abstractPath=" + abstractPath + ", JarName=" + JarName + ", jarDir=" + jarDir + ", jarVersion="
				+ jarVersion + "]";
	}
	
	
	
}
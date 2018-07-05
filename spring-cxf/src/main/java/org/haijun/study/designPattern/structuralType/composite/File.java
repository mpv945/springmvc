package org.haijun.study.designPattern.structuralType.composite;

import java.util.List;

/**
 * 文件再文件夹内（树叶）实现文件操作的抽象
 */
public class File implements IFile {
	private String name;
	
	public File(String name) {
		this.name = name;
	}
	

	public void display() {
		System.out.println(name);
	}

	/**
	 * 默认实现，直接返回，因为文件不包含子节点
	 * @return
	 */
	public List<IFile> getChild() {
		return null;
	}


	public boolean add(IFile file) {
		return false;
	}

	public boolean remove(IFile file) {
		return false;
	}

}

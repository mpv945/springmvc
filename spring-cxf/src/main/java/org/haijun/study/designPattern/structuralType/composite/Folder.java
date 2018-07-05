package org.haijun.study.designPattern.structuralType.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * 树枝（节点）实现文件操作的抽象
 */
public class Folder implements IFile{

	private String name;

	private List<IFile> children;
	
	public Folder(String name) {
		this.name = name;
		children = new ArrayList<IFile>();
	}
	

	public void display() {
		System.out.println(name);
	}

	public List<IFile> getChild() {
		return children;
	}


	public boolean add(IFile file) {
		return children.add(file);
	}


	public boolean remove(IFile file) {
		return children.remove(file);
	}


}

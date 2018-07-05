package org.haijun.study.designPattern.structuralType.composite;

import java.util.List;

/*
 * 文件节点抽象(是文件和目录的父类)
 */
public interface IFile {

	//显示文件或者文件夹的名称
	public void display();

	//添加（管理）
	public boolean add(IFile file);//目前是透明模式，接口定义的，实现类必须实现，可以把add放到各个实现类去做，不需要add可以步加

	//移除（管理）
	public boolean remove(IFile file);

	//获得子节点
	public List<IFile> getChild();
}
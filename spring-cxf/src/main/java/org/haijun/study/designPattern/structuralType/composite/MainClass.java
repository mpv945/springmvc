package org.haijun.study.designPattern.structuralType.composite;

import java.util.List;

/**
 * 组合模式，总公司下分公司，分公司下有的还有办事处
 * 意图：将对象组合成树形结构以表示"部分-整体"的层次结构。组合模式使得用户对单个对象和组合对象的使用具有一致性。
 * 主要解决：它在我们树型结构的问题中，模糊了简单元素和复杂元素的概念，客户程序可以向处理简单元素一样来处理复杂元素，从而使得客户程序与复杂元素的内部结构解耦。
 * 何时使用： 1、您想表示对象的部分-整体层次结构（树形结构）。 2、您希望用户忽略组合对象与单个对象的不同，用户将统一地使用组合结构中的所有对象。
 * 如何解决：树枝和叶子实现统一接口，树枝内部组合该接口。
 * 关键代码：树枝内部组合该接口，并且含有内部属性 List，里面放 Component。
 */
public class MainClass {
	public static void main(String[] args) {
		//C盘
		Folder rootFolder = new Folder("C:");
		//beifeng目录
		Folder beifengFolder = new Folder("beifeng");
		//beifeng.txt文件
		File beifengFile = new File("beifeng.txt");

		rootFolder.add(beifengFolder);
		rootFolder.add(beifengFile);

		//ibeifeng目录
		Folder ibeifengFolder = new Folder("ibeifeng");
		File ibeifengFile = new File("ibeifeng.txt");
		beifengFolder.add(ibeifengFolder);
		beifengFolder.add(ibeifengFile);

		Folder iibeifengFolder = new Folder("iibeifeng");
		File iibeifengFile = new File("iibeifeng.txt");
		ibeifengFolder.add(iibeifengFolder);
		ibeifengFolder.add(iibeifengFile);

		displayTree(rootFolder,0);

	}

	public static void displayTree(IFile rootFolder, int deep) {
		for(int i = 0; i < deep; i++) {
			System.out.print("--");
		}
		//显示自身的名称
		rootFolder.display();
		//获得子树
		List<IFile> children = rootFolder.getChild();
		//遍历子树
		for(IFile file : children) {
			if(file instanceof File) {
				for(int i = 0; i <= deep; i++) {
					System.out.print("--");
				}
				file.display();
			} else {
				displayTree(file,deep + 1);
			}
		}
	}
}

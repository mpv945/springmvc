package org.haijun.study.designPattern.xieweiType.obServer;

import java.util.Observable;

/**
 * 被观察的对象。Observable 被观察者必须继承Observable，通过notifyObservers方法通知观察者（实现Observer接口的类）update方法
 */
public class BlogUser extends Observable {

	public void publishBlog(String articleTitle,String articleContent) {
		Article art = new Article();
		art.setArticleTitle(articleTitle);
		art.setArticleContent(articleContent);
		System.out.println("博主:发表新文章，文章标题:" + articleTitle + ",文章内容:" + articleContent);
		//this.hasChanged();// 是否改变了对象，只有对象发生改变才会
		this.setChanged();//强制设置成对象为改变状态
		this.notifyObservers(art);
	}
}

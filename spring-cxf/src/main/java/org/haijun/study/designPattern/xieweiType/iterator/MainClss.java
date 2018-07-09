package org.haijun.study.designPattern.xieweiType.iterator;

import java.util.Iterator;

/**
 * 意图：提供一种方法顺序访问一个聚合对象中各个元素, 而又无须暴露该对象的内部表示。
 * 主要解决：不同的方式来遍历整个整合对象。
 * 何时使用：遍历一个聚合对象。
 * 如何解决：把在元素之间游走的责任交给迭代器，而不是聚合对象。
 */
public class MainClss {
	public static void main(String[] args) {
		BookList bookList = new BookList();

		Book book1 = new Book("010203","Java编程思想",90);
		Book book2 = new Book("010204","Java从入门到精通",60);

		bookList.addBook(book1);
		bookList.addBook(book2);

//		while(bookList.hasNext()) {
//			Book book = bookList.getNext();
//			book.display();
//		}

//		List<Book> bookDateList = bookList.getBookList();
//		for(int i = 0; i < bookDateList.size(); i++) {
//			Book book = bookDateList.get(i);
//			book.display();
//		}

		Iterator iter = bookList.Iterator();
		while(iter.hasNext()) {
			Book book = (Book) iter.next();
			book.display();
		}


	}
}

/**
 * 
 */
package org.haijun.study.handler;

import org.springframework.batch.item.file.separator.RecordSeparatorPolicy;

/**
 * RecordSeparatorPolicy 接口默认系统提供的实现如下：
 * 		SimpleRecordSeparatorPolicy 每一行作为一条记录
 * 		DefaultRecordSeparatorPolicy 如果行没有不匹配的引号或者续行符"\\"，则每行作为一条记录，否则不作为一条记录，注意：判断最后是否续行时，行后面空格忽略
 * 		SuffixRecordSeparatorPolicy 判断行是否以特定的后缀结束，如果是作为一行记录，否则不作为一行记录，直到找到和后缀一样的字符串才当作一行
 * 		JsonRecordSeparatorPolicy JSON格式文件行的判断策略
 * 多行数据的定义策略实现
 * 2013-4-5下午10:01:21
 */
public class MultiLineRecordSeparatorPolicy implements RecordSeparatorPolicy {
	private String delimiter = ",";//定义读取的分割符号
	private int count = 0;//分隔符的总数，给定的字符串包含的分割字符串个数等于此值就认为是一条完整记录

	/* (non-Javadoc)
	 * @see org.springframework.batch.item.file.separator.RecordSeparatorPolicy#isEndOfRecord(java.lang.String)
	 */
	// 定义一条记录的完整规则（操作定义记录是否结束）
	public boolean isEndOfRecord(String line) {
		return countDelimiter(line) == count;
	}

	/* (non-Javadoc)
	 * @see org.springframework.batch.item.file.separator.RecordSeparatorPolicy#postProcess(java.lang.String)
	 */
	// 操作在一个完整记录返回前触发（返回一行记录）
	public String postProcess(String record) {
		return record;
	}

	/* (non-Javadoc)
	 * @see org.springframework.batch.item.file.separator.RecordSeparatorPolicy#preProcess(java.lang.String)
	 */
	// 操作在一个新行加入到记录前触发
	public String preProcess(String record) {
		return record;
	}

	/**
	 * 统计给定内容包含分隔符的个数
	 * @param s
	 * @return
	 */
	private int countDelimiter(String s) {
		String tmp = s;
		int index = -1;
		int count = 0;
		while ((index=tmp.indexOf(","))!=-1) {
			tmp = tmp.substring(index+1);
			count++;
		}
		return count;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public int getCount() {
		return count;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public void setCount(int count) {
		this.count = count;
	}
}

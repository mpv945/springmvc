package org.haijun.study.utils.dfa;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @Description: 初始化敏感词库，将敏感词加入到HashMap中，构建DFA算法模型
 * @Author : xiehaijun
 * @Date ： 2018年5月8日 下午2:27:06
 */
public class SensitiveWordInit {
	//字符编码
	private static final String ENCODING = "UTF-8";

	/**
	 * 添加敏感词的源
	 * @param keyWordSet  敏感词库
	 * @version 1.0
	 */
	public static Map addSensitiveWordToHashMap(Set<String> keyWordSet) {
		//初始化敏感词容器，减少扩容操作
		Map sensitiveWordMap = new HashMap(keyWordSet.size());
		String key = null;
		Map nowMap = null;
		Map<String, String> newWorMap = null;
		//迭代keyWordSet
		Iterator<String> iterator = keyWordSet.iterator();
		while(iterator.hasNext()){
			// 关键字
			key = iterator.next();
			nowMap = sensitiveWordMap;
			for(int i = 0 ; i < key.length() ; i++){
				//转换成char型
				char keyChar = key.charAt(i);
				//获取
				Object wordMap = nowMap.get(keyChar);
				//如果存在该key，直接赋值
				if(wordMap != null){
					nowMap = (Map) wordMap;
				}
				else{     //不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
					newWorMap = new HashMap<String,String>();
					//不是最后一个
					newWorMap.put("isEnd", "0");
					nowMap.put(keyChar, newWorMap);
					nowMap = newWorMap;
				}
				if(i == key.length() - 1){
					//最后一个
					nowMap.put("isEnd", "1");
				}
			}
		}
		return sensitiveWordMap;
	}

}

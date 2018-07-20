package org.haijun.study.coreTools.converters;

import org.haijun.study.vo.AccountVO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 自定义类型转换器
 */
@Component
public class MyStringToBeanConverter implements Converter<String,AccountVO> {

    @Override
    public AccountVO convert(String s) {// 把前端页面传递的字符串参数转换成对象bean
        if(!StringUtils.isEmpty(s)){
            AccountVO vo = new AccountVO();
            String[] data = s.split("-");
            if(data.length==2){
                vo.setEmail(data[1]);
                vo.setName(data[1]);
            }
        }
        return null;
    }
}

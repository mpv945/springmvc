package org.haijun.study.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


/**
 * 对手机号的自定义注解验证 参考https://www.baeldung.com/spring-mvc-custom-validator
 * 使用方法示例
 * @ContactNumberConstraint
 * private String phone; 最后要在控制器请求方法参数写上@Valid和，BindingResult result，result.hasErrors()判断是否有错
 */
public class ContactNumberValidator implements ConstraintValidator<ContactNumberConstraint, String> {

    ContactNumberConstraint constraintAnnotation;

    @Override
    public void initialize(ContactNumberConstraint constraintAnnotation) {
        this.constraintAnnotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean isValid = value != null && value.matches("[0-9]+")
                && (value.length() > 8) && (value.length() < 14);
        if(!isValid) {
            context.disableDefaultConstraintViolation();//禁用默认 ConstraintVioation。
            // 手动获取消息的国际化内容
            String errorMessage = PropertyGetter.getProperty(constraintAnnotation.message() ,constraintAnnotation.allowedPunc());
            // 将指定的错误验证消息传递给 ConstraintVioation。
            context.buildConstraintViolationWithTemplate(errorMessage).addConstraintViolation();
        }
        return isValid;
    }
}

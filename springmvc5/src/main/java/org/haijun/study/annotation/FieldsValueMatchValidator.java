package org.haijun.study.annotation;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 判断两个字段值是否相等
 * 使用方法，在类上写@FieldsValueMatch 如果时一个就写一个
 * @FieldsValueMatch.List({
 *     @FieldsValueMatch(
 *       field = "password",
 *       fieldMatch = "verifyPassword",
 *       message = "Passwords do not match!"
 *     ),
 *     @FieldsValueMatch(
 *       field = "email",
 *       fieldMatch = "verifyEmail",
 *       message = "Email addresses do not match!"
 *     )
 * })
 */
public class FieldsValueMatchValidator implements ConstraintValidator<FieldsValueMatch, Object> {//注解用在类上，第二个参数未对象类型

    private String field;
    private String fieldMatch;

    @Override
    public void initialize(FieldsValueMatch constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.fieldMatch = constraintAnnotation.fieldMatch();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        boolean isValid = false;
        Object fieldValue = new BeanWrapperImpl(value).getPropertyValue(field);
        Object fieldMatchValue = new BeanWrapperImpl(value).getPropertyValue(fieldMatch);
        if (fieldValue != null) {
            isValid = fieldValue.equals(fieldMatchValue);
        } else {
            isValid = fieldMatchValue == null;
        }
        //将错误信息绑定到verifyField上，否则错误信息是对应整个模型类的，无法在密码验证的位置显示
        String messageTemplate = context.getDefaultConstraintMessageTemplate();
        context.buildConstraintViolationWithTemplate(messageTemplate).addPropertyNode(fieldMatch)
                .addConstraintViolation();

        return isValid;
    }
}

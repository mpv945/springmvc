package org.haijun.study.annotation;

import org.cryptacular.bean.EncodingHashBean;
import org.cryptacular.spec.CodecSpec;
import org.cryptacular.spec.DigestSpec;
import org.passay.*;
import org.passay.dictionary.WordListDictionary;
import org.passay.dictionary.WordLists;
import org.passay.dictionary.sort.ArraysSort;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    // 依赖 org.passay  DictionaryRule - 精确匹配的语义
    private DictionaryRule dictionaryRule;
    // DictionarySubstringRule - 包含匹配的语义

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        try {
            // 强制执行自定义密码策略。我们首先初始化一个DictionaryRule读取和解析用户不能使用的不安全密码列表
            // txt文件换行写上强制不能使用的密码列表12345678! 换行 azerty12! 换行 password123

            String invalidPasswordList = this.getClass().getResource("/invalid-password-list.txt").getFile();
            dictionaryRule = new DictionaryRule(
                    new WordListDictionary(WordLists.createFromReader(
                            // Reader around the word list file
                            new FileReader[] {
                                    new FileReader(invalidPasswordList)
                            },
                            // True for case sensitivity, false otherwise
                            false,
                            // Dictionaries must be sorted
                            new ArraysSort()
                    )));
        } catch (IOException e) {
            throw new RuntimeException("could not load word list", e);
        }


    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {

        PasswordValidator validator = new PasswordValidator(Arrays.asList(

                // 强制密码长度在8到30个字符之间
                new LengthRule(8, 30),
                // 强制密码至少包含1个大写字符
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                // 强制密码至少包含1个小写字符。
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                // 强制密码至少包含1位数字符
                new CharacterRule(EnglishCharacterData.Digit, 1),
                // a强制密码至少包含1个符号（特殊字符）
                new CharacterRule(EnglishCharacterData.Special, 1),
                // 强制密码不包含空格。
                new WhitespaceRule(),
                new UsernameRule(),//sernameRule - 拒绝包含提供密码的用户的用户名的密码
                // ILLEGAL_ALPHABETICAL_SEQUENCE 字母序列；数字序列Numerical ；ILLEGAL_QWERTY_SEQUENCE qwe键盘；默认个数5个。可以指定个数
                new IllegalSequenceRule(EnglishSequenceData.Numerical),//- 拒绝包含N个字符序列的密码（例如12345）
                // 不能使用的密码
                dictionaryRule
                // NumberRangeRule- 拒绝包含定义范围内任何数字的密码（例如1000-9999）拒绝包含N个字符序列的密码（例如12345）
                // IllegalRegexRule - 拒绝符合正则表达式的密码
                // IllegalCharacterRule- 拒绝包含任何一组字符的密码
                // RepeatCharacterRegexRule - 拒绝包含重复ASCII字符的密码

        ));

        RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }
        List<String> messages = validator.getMessages(result);
        String messageTemplate = messages.stream().collect(Collectors.joining(","));
        context.buildConstraintViolationWithTemplate(messageTemplate)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();

        // 历史密码（这块应该根据用户动态取出来），不加lbl 应该就是明文密码
        List<PasswordData.Reference> history = Arrays.asList(
                // Password=P@ssword1
                new PasswordData.HistoricalReference(
                        "SHA256",
                        "j93vuQDT5ZpZ5L9FxSfeh87zznS3CM8govlLNHU8GRWG/9LjUhtbFp7Jp1Z4yS7t"),

                // Password=P@ssword2
                new PasswordData.HistoricalReference(
                        "SHA256",
                        "mhR+BHzcQXt2fOUWCy4f903AHA6LzNYKlSOQ7r9np02G/9LjUhtbFp7Jp1Z4yS7t"),

                // Password=P@ssword3
                new PasswordData.HistoricalReference(
                        "SHA256",
                        "BDr/pEo1eMmJoeP6gRKh6QMmiGAyGcddvfAHH+VJ05iG/9LjUhtbFp7Jp1Z4yS7t")
        );
        EncodingHashBean hasher = new EncodingHashBean(
                new CodecSpec("Base64"), // 默认编码方式Hex和Base64
                new DigestSpec("SHA256"), // 加密的方式 MD5，SHA256
                1, // hash 次数（希轮次数。意思就是加密次数）
                false);// 哈希数据是否会被盐渍化。
        List<Rule> rules = Arrays.asList(
                // Insert other rules as needed
                new DigestHistoryRule(hasher));
                //new HistoryRule();// 历史明文比较，让前端加密传输到后端
        PasswordValidator validatorP = new PasswordValidator(rules);
        // 用户名密码
        PasswordData data = new PasswordData("username", "P@ssword1");
        data.setPasswordReferences(history);
        RuleResult resultp = validator.validate(data);

        return false;
    }

    // 生成密码(长度为8到16个字符,必须至少包含以下之一：大写，小写和数字,没有空格字符)
    public String passwordGenerator(){
        List<CharacterRule> rules = Arrays.asList(
                // at least one upper-case character
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                // at least one lower-case character
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                // at least one digit character
                new CharacterRule(EnglishCharacterData.Digit, 1));
        // 如果需要包含空格请使用这样
        new CharacterRule(new CharacterData() {
            @Override
            public String getErrorCode() {
                return "ERR_SPACE";
            }
            @Override
            public String getCharacters() {
                return " ";
            }
        }, 1);

        PasswordGenerator generator = new PasswordGenerator();

        // Generated password is 12 characters long, which complies with policy
        String password = generator.generatePassword(12, rules);

        return password;
    }
}

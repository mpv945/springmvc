package org.haijun.study;

import static org.junit.Assert.assertTrue;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

/**
 * Unit test for simple App.  https://blog.csdn.net/zsensei/article/details/78505666
 * https://www.helplib.com/GitHub/article_83442
 * https://github.com/yangjianzhou/spring-boot-test-sample
 * https://github.com/yangjianzhou/spring-boot-unitils
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
//@ContextConfiguration(classes = { StudentJpaConfig.class }, loader = AnnotationConfigContextLoader.class)
// @DbUnitConfiguration(dataSetLoader = MyDataSetLoader.class)
//@SpringBootTest(classes = DemoTestApplication.class)//spring boot 项目启动类
// @DbUnitConfiguration(dataSetLoader = XlsDataSetLoader.class)// 自定义xls 加载器
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
public class AppTest 
{

    /*@Autowired
    private PersonService personService;*/
    /**
     * Rigorous Test :-)
     */
    @Test
    // DbUnit 可以有不同的数据库操作，最主要的两种：
    // DELETE_ALL ，它删除表中所有行。  CLEAN_INSERT ，它删除表中所有行并插入数据集提供的行。
    // DatabaseOperation.UPDATE：这个操作将从测试数据源中读取的数据集的内容更新到数据库中，注意这个操作正确执行的前提是测试数据表已经存在，如果不存在这个测试用例将会失败
    // DatabaseOperation.INSERT：这个操作把从测试数据源中读取的数据集的内容插入到数据库中，注意这个操作正确执行的前提是测试数据表不存在，这个操作将新建数据表。
    //     如果测试数据表已经存在这个测试用例将会失败。另外，在执行这个操作的时候要特别注意数据集中数据表的顺序，否则可能会因为违反外键约束而造成测试用例失败
    // DatabaseOperation.DELETE：这个操作会从数据库中删除数据，注意，这个操作只删除数据集中存在的数据行而不是整个数据表中的数据
    // DatabaseOperation.DELETE_ALL：这个操作删除数据表中的所有行，注意，这个操作也只影响数据集中声明了的数据表，数据集中没有涉及到的数据表中的数据不会删除
    // DatabaseOperation.TRUNCATE：这个操作将删除数据集中声明的数据表，如果数据中有些表并没有在预定义的数据集中提到，这个数据表将不会被影响。注意，这个操作是按照相反的顺序执行的
    // DatabaseOperation.REFRESH：顾名思义，这个操作将把预定义数据集中的数据同步到数据库中，也就是说这个操作将更新数据数中已有的数据、插入数据库中没有的数据。
    //    数据库中已有的、但是数据集中没有的行将不会被影响。我们用一个产品数据库的拷贝进行测试的时候可以使用这个操作将预定义数据同步到产品数据库中
    // DatabaseOperation.CLEAN_INSERT：删除所有的数据表中的数据，然后插入数据集中定义的数据，如果你想保证数据库是受控的，这个你会比较喜欢。
    // DatabaseOperation.NONE：不执行任何操作
    // CompositeOperation：将多个操作组合成一个，便以维护和重用
    // TransactionOperation：在一个事物内执行多个操作
    // IdentityInsertOperation：在使用MSSQL的时候，插入数据时IDENTITY列我们是没有办法控制的，用这个就可以控制了，只有在使用MSSQL的时候才会用得到

    //可以使用其它路径 @DatabaseSetup("/META-INF/dbtest/sampleData.xml") 使用@ExpectedDatabase為資料庫內容編寫斷言
    @DatabaseSetup(value = "classpath:sampleData.xml",type = DatabaseOperation.CLEAN_INSERT)//@DatabaseSetup和@DatabaseTearDown注释可用于配置数据库表中的测试执行之前和重置他们，一旦测试完成。可以在方法或类级别应用注释
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
}

package org.haijun.study.repository;

import org.haijun.study.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom, JpaSpecificationExecutor<User> {

    // entity 定义的@NamedQuery 命名查询
    //List<User> findByNames(List<String> userNames);

    // 开启原生sql查询
/*    @Query(value = "SELECT * FROM USERS WHERE EMAIL_ADDRESS = ?1", nativeQuery = true)
    User findByEmailAddress(String emailAddress，Sort sort);*/

    // 命名查询 //为避免在@Query注释的查询字符串中陈述实际的实体名称，可以使用该#{#entityName}变量。
    // @Query("select u from #{#entityName} u where u.firstname = :firstname or u.lastname = :lastname")
    // User findByLastnameOrFirstname(@Param("lastname") String lastname,@Param("firstname") String firstname);

    // 修改查询
/*    @Modifying
    @Transactional
    @Query("update User u set u.firstname = ?1 where u.lastname = ?2")
    int setFixedFirstnameFor(String firstname, String lastname);*/

    // 存储过程
    /*@Procedure("plus1inout")
    Integer explicitlyNamedPlus1inout(Integer arg);*/

    // 参考http://www.cnblogs.com/rulian/p/6434631.html 和http://www.cnblogs.com/rulian/p/6557471.html
    //Optional<User> findOptionalByameName(String name);

    // @Async// 异步查询，这意味着该方法在调用时立即返回，而实际查询执行发生在已提交给Spring的任务中TaskExecutor。
    // Future/CompletableFuture/ListenableFuture<User> findByFirstname(String firstname);

    // 限制表达式也支持Distinct关键字。此外，对于将结果集限制为一个实例的查询，支持将结果封装到Optional关键字中。
    // User findFirstByOrderByLastnameAsc();或者User findTopByOrderByAgeDesc();// 可以Page<User> queryFirst10ByLastname(String lastname, Pageable pageable);

    // @Query("select u from User u")
    //Stream<User> streamAllPaged(Pageable pageable); //并非所有的Spring Data模块当前都支持Stream<T>作为返回类型。
    // 您可以Stream通过使用该close()方法或使用Java 7 try-with-resources块手动关闭
/*    try (Stream<User> stream = repository.findAllByCustomQueryAndStream()) {
        stream.forEach(…);
    }*/
}

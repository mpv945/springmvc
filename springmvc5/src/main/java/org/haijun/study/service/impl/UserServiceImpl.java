package org.haijun.study.service.impl;

import lombok.extern.log4j.Log4j2;
import org.haijun.study.model.entity.User;
import org.haijun.study.model.vo.UserVO;
import org.haijun.study.repository.UserRepository;
import org.haijun.study.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
// 有时候一个类中可能会有多个缓存操作，而这些缓存操作可能是重复的。这个时候可以使用@CacheConfig
@CacheConfig(cacheNames = {"user"})
@Log4j2
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 新增用户
     *
     * @param user
     */
    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    /**
     * 查询全部用户列表
     *
     * @return
     */
    @Override
    public List<User> list() {
        // 创建匹配器，即如何使用查询条件// https://www.cnblogs.com/rulian/p/6533109.html
        ExampleMatcher matcher = ExampleMatcher.matching() //构建对象
                .withIgnoreNullValues()//默认忽略null值字段
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.startsWith()) //姓名采用“开始匹配”的方式查询
                .withIgnorePaths("focus");  //忽略参与条件的字段
        // 查询对象
        User user = new User();
        //创建实例
        Example<User> ex = Example.of(user, matcher);
        // userRepository.findAll(ex); //动态查询
        return userRepository.findAll();
    }

    /**
     * 分页查询
     *
     * @return
     */
    @Override
    public Page<User> listPage() {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(1, 20, sort);
        //Slice<User> userPage = userRepository.findAll(pageable); //Page和Slice的区别就像List和Iterator
        Page<User> userPage = userRepository.findAll(pageable);//可以只按Sort sort，可以直接返回List
        return userPage;
    }

    @Override
    public List<User> specification1() {
        // Specification 工具 https://github.com/wenhao/jpa-spec/blob/master/docs/3.2.3_cn.md
        // 使用工具可以节约下面的手动创建

        //根据查询结果，声明返回值对象，这里要查询用户的订单列表，所以声明返回对象为User
        Specification<User> spec = new Specification<User>() {
            //Root<X>  根查询，默认与声明相同
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // 简单查询
/*                //声明并创建MyOrder的CriteriaQuery对象
                CriteriaQuery<User> q1 = cb.createQuery(User.class);
                //连接的时候，要以声明的根查询对象（这里是root，也可以自己创建）进行join
                //Join<Z,X>是Join生成的对象，这里的Z是被连接的对象，X是目标对象，
                //  连接的属性字段是被连接的对象在目标对象的属性，这里是我们在User内声明的IdentityCard
                //join的第二个参数是可选的，默认是JoinType.INNER(内连接 inner join)，也可以是JoinType.LEFT（左外连接 left join）
                Join<IdentityCard, User> myOrderJoin = root.join("identityCard", JoinType.INNER);
                //用CriteriaQuery对象拼接查询条件，这里只增加了一个查询条件，cId=1
                q1.select(myOrderJoin).where(cb.equal(root.get("cId"), 1),
                        root.get("id").in(1,2,4),
                        cb.equal(root.get("identityCard").get("firstName"),"Jack")////对象identityCard的firstName=Jack
                );// in 查询，支持集合Collection
                //通过getRestriction获得Predicate对象
                Predicate p1 = q1.getRestriction();
                //返回对象
                return p1;*/

                /*//复杂条件
                List<Predicate> predicatesList = new ArrayList<>();
                //--------------------------------------------
                //查询条件示例
                //equal示例
                if (!StringUtils.isEmpty(name)){
                    Predicate namePredicate = cb.equal(root.get("name"), name);
                    predicatesList.add(namePredicate);
                }
                //like示例
                if (!StringUtils.isEmpty(nickName)){
                    Predicate nickNamePredicate = cb.like(root.get("nickName"), '%'+nickName+'%');
                    predicatesList.add(nickNamePredicate);
                }
                //between示例
                if (birthday != null) {
                    Predicate birthdayPredicate = cb.between(root.get("birthday"), birthday, new Date());
                    predicatesList.add(birthdayPredicate);
                }

                //关联表查询示例
                if (!StringUtils.isEmpty(courseName)) {
                    Join<Student,Teacher> joinTeacher = root.join("teachers",JoinType.LEFT);
                    Predicate coursePredicate = cb.equal(joinTeacher.get("courseName"), courseName);
                    predicatesList.add(coursePredicate);
                }

                //复杂条件组合示例
                if (chineseScore!=0 && mathScore!=0 && englishScore!=0 && performancePoints!=0) {
                    Join<Student,Examination> joinExam = root.join("exams",JoinType.LEFT);
                    Predicate predicateExamChinese = cb.ge(joinExam.get("chineseScore"),chineseScore);
                    Predicate predicateExamMath = cb.ge(joinExam.get("mathScore"),mathScore);
                    Predicate predicateExamEnglish = cb.ge(joinExam.get("englishScore"),englishScore);
                    Predicate predicateExamPerformance = cb.ge(joinExam.get("performancePoints"),performancePoints);
                    //组合
                    Predicate predicateExam = cb.or(predicateExamChinese,predicateExamEnglish,predicateExamMath);
                    Predicate predicateExamAll = cb.and(predicateExamPerformance,predicateExam);
                    predicatesList.add(predicateExamAll);
                }
                //--------------------------------------------
                //排序示例(先根据学号排序，后根据姓名排序)
                query.orderBy(cb.asc(root.get("studentNumber")),cb.asc(root.get("name")));
                //--------------------------------------------
                //最终将查询条件拼好然后return
                Predicate[] predicates = new Predicate[predicatesList.size()];
                return cb.and(predicatesList.toArray(predicates));*/

                return null;
            }
        };
        //分页查询
        Pageable pageable = new PageRequest(0,10, Sort.Direction.DESC,"id");
        //查询的分页结果
        Page<User> page =userRepository.findAll(spec,pageable);
        System.out.println(page);
        System.out.println(page.getTotalElements());
        System.out.println(page.getTotalPages());
        for (User c:page.getContent()){
            System.out.println(c.toString());
        }

        return null;
    }

    @Override
    // 不指定key，使用默认的key，使用“#参数名”或者“#p参数index”。
    @Cacheable(key = "#userVO")//cacheNames="books" 可以指定缓存名称，sync="true"  sync 可以指示底层将缓存锁住，使只有一个线程可以进入计算
    // condition="#name.length < 32" 属性condition支持这种功能，通过SpEL 表达式来指定可求值的boolean值，为true才会缓存（在方法执行之前进行评估）。
    public String queryCacheObj(UserVO userVO) {
        log.info("进入缓存获取缓存信息");
        return "缓存信息queryCacheObj(UserVO userVO)";
    }

    @Override
    @Cacheable(key = "#p1", condition="p1%2==0", unless="#result.length()>64")//p1表示第二个参数的值作为key,#result返回值
    // unless表达式是在方法调用之后进行评估的。如果返回false，才放入缓存（与condition相反）。 #result指返回值
    public String queryCache(String name, long uid) {
        log.info("进入缓存获取缓存信息");
        return "缓存信息queryCache(String name, long uid)";
    }
    // 应该避免@CachePut 和 @Cacheable同时使用的情况。
    // @CachePut标注的方法在执行前不会去检查缓存中是否存在之前执行过的结果，而是每次都会执行该方法，并将执行结果以键值对的形式存入指定的缓存中。
    /*@CachePut(cacheNames="book", key="#isbn")
    public Book updateBook(ISBN isbn, BookDescriptor descriptor)*/

    // 当指定了allEntries为true时，Spring Cache将忽略指定的key。有的时候我们需要Cache一下清除所有的元素。
    /*@CacheEvict(cacheNames="books", allEntries=true)
    public void loadBooks(InputStream batch)*/
}

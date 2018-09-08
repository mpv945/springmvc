package org.haijun.study.config;

import org.ehcache.Cache;
import org.ehcache.PersistentCacheManager;
import org.ehcache.clustered.client.config.builders.ClusteredResourcePoolBuilder;
import org.ehcache.clustered.client.config.builders.ClusteringServiceConfigurationBuilder;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.util.Arrays;

@Configuration
@EnableCaching
// 参考:https://blog.csdn.net/u012106290/article/details/52154241
public class CacheConfig {
    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        simpleCacheManager.setCaches(Arrays.asList(
                new ConcurrentMapCache("searches")
        ));
        return simpleCacheManager;
    }

    // 置启动Terracotta Server.参考http://www.ehcache.org/documentation/3.4/clustered-cache.html
    // 1 创建具有群集功能的缓存管理器
    public void cacheManager1(){
        CacheManagerBuilder<PersistentCacheManager> clusteredCacheManagerBuilder =
                CacheManagerBuilder.newCacheManagerBuilder()//返回org.ehcache.config.builders.CacheManagerBuilder实例
                        .with(ClusteringServiceConfigurationBuilder.cluster(URI.create("terracotta://localhost/my-application"))
                                //使用ClusteringServiceConfigurationBuilder静态方法.cluster(URI)将缓存管理器连接到URI指定的群集存储，
                                //以返回群集服务配置构建器实例。示例中URI提供的示例指向Terracotta服务器上名为“my-application”的集群存储实例（假设服务器在localhost和端口9410上运行）。
                                .autoCreate());//如果尚不存在，则自动创建群集存储。

        PersistentCacheManager cacheManager = clusteredCacheManagerBuilder.build(true);//返回可用于创建集群高速缓存的完全初始化的高速缓存管理器。

        cacheManager.close();//关闭缓存管理器。
    }
    // 2 缓存管理器配置和服务器端资源的使用
    public void cacheManager2(){
        CacheManagerBuilder<PersistentCacheManager> clusteredCacheManagerBuilder =
                CacheManagerBuilder.newCacheManagerBuilder()
                        .with(ClusteringServiceConfigurationBuilder.cluster(URI.create("terracotta://localhost/my-application")).autoCreate()
                                .defaultServerResource("primary-server-resource")//	defaultServerResource(String)on ClusteringServiceConfigurationBuilderinstance为缓存管理器设置默认服务器堆外资源。
                                // 从示例中，缓存管理器将其默认服务器堆外资源设置primary-server-resource为服务器。
                                .resourcePool("resource-pool-a", 28, MemoryUnit.MB, "secondary-server-resource")//为缓存管理器添加资源池，并使用指定resource-pool-a的
                                // 服务器堆外资源消耗指定的名称（）和大小（28MB）secondary-server-resource。缓存管理器级别的资源池直接映射到服务器端的共享池。
                                .resourcePool("resource-pool-b", 32, MemoryUnit.MB))//为具有指定名称（resource-pool-b）和大小（32MB）的缓存管理器添加另一个资源池。
                        .withCache("clustered-cache", CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class,//提供要创建的缓存配置。
                                ResourcePoolsBuilder.newResourcePoolsBuilder()
                                        //ClusteredResourcePoolBuilder.clusteredDedicated(String, long, MemoryUnit)从指定的服务器堆外资源为缓存分配专用存储池。
                                        // 在此示例中，为集群缓存分配了一个32MB的专用池primary-server-resource。
                                        .with(ClusteredResourcePoolBuilder.clusteredDedicated("primary-server-resource", 32, MemoryUnit.MB))))
                        .withCache("shared-cache-1", CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class,
                                ResourcePoolsBuilder.newResourcePoolsBuilder()
                                        .with(ClusteredResourcePoolBuilder.clusteredShared("resource-pool-a"))))//ClusteredResourcePoolBuilder.clusteredShared(String)，
                        // 传递资源池的名称指定shared-cache-1使用相同的资源池（resource-pool-a）与其他缓存共享存储资源。
                        .withCache("shared-cache-2", CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class,
                                ResourcePoolsBuilder.newResourcePoolsBuilder()
                                        .with(ClusteredResourcePoolBuilder.clusteredShared("resource-pool-a"))));//配置shared-cache-2与其共享资源池
                                                                                                    // （resource-pool-a）的另一个缓存（）shared-cache-1。
        PersistentCacheManager cacheManager = clusteredCacheManagerBuilder.build(true);//使用群集缓存创建完全初始化的缓存管理器。

        cacheManager.close();
    }
    // 3 配置缓存管理器以连接到集群层管理器时，有三种可能的连接模式：
    public void cacheManager3(){
        // 在自动创建模式下，如果不存在集群层管理器，则使用提供的配置创建一个集群层管理器。如果它存在且其配置与提供的配置匹配，
        // 则建立连接。如果提供的配置不匹配，则缓存管理器将无法初始化。
        CacheManagerBuilder<PersistentCacheManager> autoCreate = CacheManagerBuilder.newCacheManagerBuilder()
                .with(ClusteringServiceConfigurationBuilder.cluster(URI.create("terracotta://localhost/my-application"))
                        .autoCreate()
                        .resourcePool("resource-pool", 32, MemoryUnit.MB, "primary-server-resource"))
                .withCache("clustered-cache", CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class,
                        ResourcePoolsBuilder.newResourcePoolsBuilder()
                                .with(ClusteredResourcePoolBuilder.clusteredShared("resource-pool"))));

        // 在预期模式下，如果存在集群​​层管理器且其配置与提供的配置匹配，则建立连接。如果提供的配置不匹配或者集群层管理器不存在，则缓存管理器将无法初始化。
        CacheManagerBuilder<PersistentCacheManager> expecting = CacheManagerBuilder.newCacheManagerBuilder()
                .with(ClusteringServiceConfigurationBuilder.cluster(URI.create("terracotta://localhost/my-application"))
                        .expecting()
                        .resourcePool("resource-pool", 32, MemoryUnit.MB, "primary-server-resource"))
                .withCache("clustered-cache", CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class,
                        ResourcePoolsBuilder.newResourcePoolsBuilder()
                                .with(ClusteredResourcePoolBuilder.clusteredShared("resource-pool"))));

        //在无配置模式下，如果存在集群​​层管理器，则建立连接而不考虑其配置。如果它不存在则缓存管理器将无法初始化。
        CacheManagerBuilder<PersistentCacheManager> configless = CacheManagerBuilder.newCacheManagerBuilder()
                .with(ClusteringServiceConfigurationBuilder.cluster(URI.create("terracotta://localhost/my-application")))

                .withCache("clustered-cache", CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class,
                        ResourcePoolsBuilder.newResourcePoolsBuilder()
                                .with(ClusteredResourcePoolBuilder.clusteredShared("resource-pool"))));
    }
    //集群存储层和Ehcache提供两个级别的一致性
    public void cacheManager4(){
        CacheConfiguration<Long, String> config = CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class,
                ResourcePoolsBuilder.newResourcePoolsBuilder()
                        .heap(2, MemoryUnit.MB)//为缓存配置堆层。
                        .with(ClusteredResourcePoolBuilder.clusteredDedicated("primary-server-resource", 8, MemoryUnit.MB)))//使用从服务器堆外资源配置专用大小的集群层
                                                                                                                              // ClusteredResourcePoolBuilder。
                //.add(ClusteredStoreConfigurationBuilder.withConsistency(Consistency.STRONG))//在此处使用强一致性，通过使用其他服务配置来指定一致性级别
                .build();


       //Cache<Long, String> cache = cacheManager.createCache("clustered-cache-tiered", config);
        //cache.put(42L, "All you need to know!");//使用上面使用的一致性，put仅当所有其他客户端已使相应的映射无效时，此操作才会返回。

    }
    // 群集缓存过期
    public void cacheManager5(){
        CacheManagerBuilder<PersistentCacheManager> cacheManagerBuilderAutoCreate = CacheManagerBuilder.newCacheManagerBuilder()
                .with(ClusteringServiceConfigurationBuilder.cluster(URI.create("terracotta://localhost/my-application"))
                        .autoCreate()//使用auto create配置第一个缓存管理器
                        .resourcePool("resource-pool", 32, MemoryUnit.MB, "primary-server-resource"));

        PersistentCacheManager cacheManager1 = cacheManagerBuilderAutoCreate.build(false);
        cacheManager1.init();

        CacheConfiguration<Long, String> cacheConfigDedicated = CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class,
                ResourcePoolsBuilder.newResourcePoolsBuilder()
                        .with(ClusteredResourcePoolBuilder.clusteredDedicated("primary-server-resource", 8, MemoryUnit.MB)))//为群集dedicated资源池构建缓存配置
                //.add(ClusteredStoreConfigurationBuilder.withConsistency(Consistency.STRONG))
                .build();

        Cache<Long, String> cacheDedicated = cacheManager1.createCache("my-dedicated-cache", cacheConfigDedicated);//my-dedicated-cache使用缓存配置创建缓存

        CacheManagerBuilder<PersistentCacheManager> cacheManagerBuilderExpecting = CacheManagerBuilder.newCacheManagerBuilder()
                .with(ClusteringServiceConfigurationBuilder.cluster(URI.create("terracotta://localhost/my-application"))
                        .expecting()//将第二个缓存管理器配置为期望（自动创建关闭）
                        .resourcePool("resource-pool", 32, MemoryUnit.MB, "primary-server-resource"));

        PersistentCacheManager cacheManager2 = cacheManagerBuilderExpecting.build(false);
        cacheManager2.init();

        CacheConfiguration<Long, String> cacheConfigUnspecified = CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class,
                ResourcePoolsBuilder.newResourcePoolsBuilder()
                        .with(ClusteredResourcePoolBuilder.clustered()))//为群集未指定的资源池构建缓存配置，该资源池将使用先前配置的群集专用资源池。
                //.add(ClusteredStoreConfigurationBuilder.withConsistency(Consistency.STRONG))
                .build();

        Cache<Long, String> cacheUnspecified = cacheManager2.createCache("my-dedicated-cache", cacheConfigUnspecified);//使用相同名称创建缓存my-dedicated-cache并使用群集未指定的缓存配置
    }
}

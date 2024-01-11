package com.due.cloud.bridge.mysql.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.due.basic.tookit.utils.GeneratorUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Date;


/**
 *  mysql的相关配置
 */
@MapperScan(basePackages = {"com.due.cloud.module.**.mapper"})
@EnableTransactionManagement // 开启本地事务
@SpringBootConfiguration
public class BridgeMysqlConfig {


    /**
     *  开启本地事务
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) throws Exception {
        return new DataSourceTransactionManager(dataSource);
    }


    /**
     *  分页插件
     */
    @Bean
    public MybatisPlusInterceptor paginationInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        mybatisPlusInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return mybatisPlusInterceptor;
    }
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                this.strictInsertFill(metaObject, "createTime", Date::new, Date.class);
                this.strictInsertFill(metaObject, "updateTime", Date::new, Date.class);
                this.strictInsertFill(metaObject, "dataVersion", GeneratorUtil::getDefaultLong, Long.class);
                this.strictInsertFill(metaObject, "dataStatus", GeneratorUtil::getDefaultInteger, Integer.class);
            }

            @Override
            public void updateFill(MetaObject metaObject) {
//				this.strictUpdateFill(metaObject, "updateTime", Date::new, Date.class); // 如果原始数据有值，会导致不会更新
                this.setFieldValByName("updateTime", new Date(), metaObject);
            }
        };
    }

}

package com.due.cloud.bridge.mysql;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.due.cloud.bridge.mysql.domian.TableData;
import com.due.cloud.bridge.mysql.mapper.ITableDataMapper;
import com.due.cloud.bridge.mysql.service.ITableDataService;
import com.due.cloud.bridge.mysql.service.imple.TableDataServiceImpl;
import lombok.Data;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
public class CodeGenerator {


    /**
     * 作者
     */
    private String author;

    /**
     * 输出文件目录
     */
    private String outDir;


    /**
     * 父级文件名称
     */
    private String pkParent;


    private String dbUrl;

    private String dbUsername;

    private String dbPassword;

    private String dbDriver;


    /**
     * 需要生成的表或者视图
     */
    private Set<String> tableSet = new LinkedHashSet<String>();

    public void gc() {
        this.generatorTable();
    }

    private void generatorTable() {
//        FastAutoGenerator
        AutoGenerator autoGenerator = new AutoGenerator();

        // 配置数据源
        DataSourceConfig dataSource = new DataSourceConfig();
        dataSource.setDbType(DbType.MYSQL).setDbQuery(new MySqlQuery())
                .setPassword(dbPassword).setUsername(dbUsername).setUrl(dbUrl).setDriverName(dbDriver);
        autoGenerator.setDataSource(dataSource);

        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setAuthor(author).setOutputDir(outDir).setSwagger2(false).setEnableCache(false)
                .setDateType(DateType.ONLY_DATE).setIdType(IdType.ASSIGN_ID).setBaseResultMap(true)
                .setBaseColumnList(false).setXmlName("%sMapper").setServiceName("I%sService").setServiceImplName("%sImpl")
                .setFileOverride(true).setActiveRecord(true);
        autoGenerator.setGlobalConfig(globalConfig);

        String[] array = tableSet.toArray(new String[0]);
        // 生成策略
        StrategyConfig strategy = new StrategyConfig();
        strategy.setEntityLombokModel(true).setSuperEntityClass(TableData.class).setSuperMapperClass(ITableDataMapper.class.getName())
                .setSuperServiceClass(ITableDataService.class.getName()).setSuperServiceImplClass(TableDataServiceImpl.class.getName())
                .setSuperEntityColumns("data_id", "create_time", "update_time", "data_version", "data_status").setEntitySerialVersionUID(true)
                .setInclude(array).setNaming(NamingStrategy.underline_to_camel).setChainModel(true);
        autoGenerator.setStrategy(strategy);


        // 文件路径
        PackageConfig packageInfo = new PackageConfig();
        packageInfo.setParent(pkParent);
        packageInfo.setXml("mapper");
        autoGenerator.setPackageInfo(packageInfo);

        autoGenerator.execute();
    }

    public void addTable(String... tables) {
        if (tables == null) return;
        tableSet.addAll(Arrays.asList(tables));
    }

    public void addTable(String table) {
        tableSet.add(table);
    }
}

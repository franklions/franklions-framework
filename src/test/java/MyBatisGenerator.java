import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.franklions.example.domain.entity.BaseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author flsh
 * @version 1.0
 * @date 2022/11/15
 * @since Jdk 1.8
 */
public class MyBatisGenerator {

    public static void main(String[] args) {
        List<String> tables = new ArrayList<>();
        tables.add("sys_dict_type");
//        tables.add("sys_dict_data");
//        tables.add("sys_config");
//        tables.add("sys_dept");
//        tables.add("sys_job");
//        tables.add("sys_job_log");
//        tables.add("sys_logininfor");
//        tables.add("sys_menu");
//        tables.add("sys_notice");
//        tables.add("sys_oper_log");
//        tables.add("sys_post");
//        tables.add("sys_role");
//        tables.add("sys_role_dept");
//        tables.add("sys_role_menu");
//        tables.add("sys_user");
//        tables.add("sys_user_post");
//        tables.add("sys_user_role");

        String url = "jdbc:mysql://127.0.0.1:13306/ry-cloud?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false";
        FastAutoGenerator.create(url,"root","root123456")
                .globalConfig(builder -> {
                    builder.author("flsh")               //作者
                            .outputDir(System.getProperty("user.dir")+"/target/generated-sources")    //输出路径(写到java目录)
//                            .enableSwagger()           //开启swagger
                            .commentDate("yyyy-MM-dd");            //开启覆盖之前生成的文件

                })
                .packageConfig(builder -> {
                    builder.parent("com.lianwu.cloud.system.ms")
//                            .moduleName("practice")   //父模块名称
                            .entity("domain.entity")
                            .service("service")
                            .serviceImpl("service.impl")
                            .controller("controller")
                            .mapper("mapper")
                            .xml("mapper")
                            .pathInfo(Collections.singletonMap(OutputFile.xml,System.getProperty("user.dir")+"/target/generated-sources/resources"));
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tables)
//                            .addTablePrefix("sys_")
                            .serviceBuilder()
                            .formatServiceFileName("%sService")
                            .formatServiceImplFileName("%sServiceImpl")
                            .entityBuilder()
                            .enableLombok()
//                            .logicDeleteColumnName("deleted")
                            .enableTableFieldAnnotation()
                            .superClass(BaseEntity.class)
                            .controllerBuilder()
                            // 映射路径使用连字符格式，而不是驼峰
                            .enableHyphenStyle()
                            .formatFileName("%sController")
                            .enableRestStyle()
                            .mapperBuilder()
                            //生成通用的resultMap
                            .enableBaseResultMap()
//                            .superClass(BaseMapper.class)
                            .formatMapperFileName("%sMapper")
                            .enableMapperAnnotation()
                            .formatXmlFileName("%sMapper");
                })
                .templateConfig(new Consumer<TemplateConfig.Builder>() {
                    @Override
                    public void accept(TemplateConfig.Builder builder) {
                        // 实体类使用我们自定义模板
                        builder.entity("templates/myentity.java");
                    }
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}

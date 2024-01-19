# due-cloud

due-cloud是一个基于springboot2.7.0、springcloud、springcloudalibaba的一款企业级的快速搭建分布式项目的模板，里面包含的基础的认证授权等功能，是使用这可以快熟的搭建并使用，入门简单。使用springSecurity作为认证授权的基础，仿写springOauth2。提供多种授权方式：密码模式、手机验证码模式以及OpenId登录模式等。

#### 系统模块
due-cloud     
├── due-cloud-basic                                 // 基础类库 
│       └── due-cloud-basic-library                 // 项目需要的maven依赖
│       └── due-cloud-basic-setting                 // 项目的启动、公共的配置信息
│       └── due-cloud-basic-toolkit                 // 项目的工具类、全局常量等
├── due-cloud-bridge                                // 项目的技术点、功能，类似于start
│       └── due-cloud-bridge-auth                   // 项目的认证授权相关配置
│       └── due-cloud-bridge-file                   // 项目的文件上传相关配置
│       └── due-cloud-bridge-okhttp                 // 项目的http（proxy）相关配置
│       └── due-cloud-bridge-openfeign              // 项目的rpc（openFeign）相关配置
│       └── due-cloud-bridge-resources              // 项目的资源相关配置
│       └── due-cloud-bridge-tomcat                 // 项目的tomcat容器相关配置
├── due-cloud-module                                // 项目的基础模
│       └── due-cloud-module-customer               // 项目用户基础模块
│           └── due-cloud-module-customer-domain    // 项目用户模块的dto
│           └── due-cloud-module-customer-run       // 项目用户基础服务                                   【18001】
│       └── due-cloud-module-file                   // 项目文件存储基础模块
│           └── due-cloud-module-file-domain        // 项目文件存储的dto
│           └── due-cloud-module-file-run           // 项目文件存储服务                                   【18002】
│       └── due-cloud-module-security               // 项目的安全模块基础模块
│           └── due-cloud-module-security-domain    // 项目的安全模块的dto
│           └── due-cloud-module-security-run       // 项目的安全模块服务                                  【18003】
├── due-cloud-rpc                                   // 项目的rpc（openFeign
│       └── due-cloud-rpc-customer                  // 项目的用户基础服务对外提供的接口                     
│       └── due-cloud-rpc-file                      // 项目的文件基础服务对外提供的接口
│       └── due-cloud-rpc-security                  // 项目的安全基础服务对外提供的接口
├── due-cloud-service                               // 项目的对外服务                                       
│       └── due-cloud-service-auth                  // 项目的对外服务认证授权接口                            【8001】
│       └── due-cloud-service-back                  // 项目的对外服务后台接口                                【8002】   
│       └── due-cloud-service-mobile                // 项目的对外服务系统端接口                              【8003】
├── due-cloud-web                                   // 项目的网关模块                                       【9000】
│       └── due-cloud-web-gateway                   // 项目的geteway网关
├──pom.xml                // 公共依赖


#### 安装教程

1.  安装nacos
2.  xxxx
3.  xxxx

#### 系统架构图

1.  xxxx
2.  xxxx
3.  xxxx

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


#### 特技

感谢start，感谢支持，您的支持是我最大的动力，希望我们能一起交流，一起进步，巅峰想见！！！

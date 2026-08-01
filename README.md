# 基于微信小程序的大学校园寻物认领系

## 前后端分离

Vue / 微信小程序
        ↓ HTTP / JSON
Spring Boot 后端
        ↓
      MySQL

## 最小可运行版本

创建 Spring Boot 项目并连接 MySQL
设计数据库表
完成用户注册、登录
完成失物信息发布与查询
完成拾物信息发布与查询
完成认领申请
管理员审核
用 Apifox/Postman 测试接口
最后再接 Vue 或微信小程序

## 第一版可以使用这些后端技术：

Java 17
Spring Boot 3
MyBatis-Plus
MySQL 8
Maven
JWT 登录认证
Lombok
Validation 参数校验、

加入 Spring Data JPA 后，可以把 Java 类和数据库表对应起来

## 安装框架

curl.exe "https://start.spring.io/starter.zip?type=maven-project&language=java&groupId=com.campus&artifactId=lost-found-backend&name=lost-found-backend&packageName=com.campus.lostfound&packaging=jar&javaVersion=17&dependencies=web,mysql,validation,lombok" -o lost-found-backend.zip

curl.exe "https://start.spring.io/starter.zip?
type=maven-project& 使用 Maven 管理项目和依赖
language=java& 使用 Java
groupId=com.campus& 项目的组织标识
artifactId=lost-found-backend& 项目名称/构件名称
name=lost-found-backend& Spring 项目名称
packageName=com.campus.lostfound& Java 包名
packaging=jar& 最终打包为 JAR 文件
javaVersion=17& 使用 Java 17
dependencies=web,mysql,validation,lombok" 需要的功能依赖
-o lost-found-backend.zip

### 依赖
web
mysql
validation
lombok
/////添加
Spring Data JPA 依赖
BCrypt 密码加密
添加 JWT 依赖

## 终端运行

.\mvnw.cmd spring-boot:run

## 核心数据表

数据表	用途
user	学生和管理员账号
lost_item	丢失物品信息
found_item	捡到物品信息
claim_application	认领申请
category	物品分类
notice	系统公告

## 后端目录组织

campus-lost-found/
├── controller/     接收前端请求
├── service/        业务逻辑
│   └── impl/
├── mapper/         操作数据库
├── entity/         数据库实体类
├── dto/            接收请求参数
├── vo/             返回给前端的数据
├── config/         后端配置
├── interceptor/    JWT 登录拦截
├── common/         统一返回结果
├── exception/      统一异常处理
└── CampusApplication.java

## 完整流程

用户注册
   ↓
用户登录并获得 Token
   ↓
发布失物或拾物信息
   ↓
其他用户查询信息
   ↓
提交认领申请
   ↓
发布者或管理员审核
   ↓
物品状态变为“已认领”

## 接口设计

POST   /api/auth/register
POST   /api/auth/login

POST   /api/lost-items
GET    /api/lost-items
GET    /api/lost-items/{id}
PUT    /api/lost-items/{id}
DELETE /api/lost-items/{id}

POST   /api/found-items
GET    /api/found-items
GET    /api/found-items/{id}

POST   /api/claims
GET    /api/claims/my
PUT    /api/claims/{id}/approve
PUT    /api/claims/{id}/reject
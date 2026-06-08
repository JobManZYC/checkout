# 扫码枪收银系统 - 后端 (pos-server)

基于 Spring Boot 2.7 开发的扫码枪收银系统后端服务，提供商品管理、销售管理、用户管理等 RESTful API。

## 技术栈

- **Spring Boot** 2.7.18
- **MyBatis** 2.3.2
- **MySQL** 8.0
- **Druid** 1.2.20（数据库连接池）
- **Swagger** (Springfox 3.0) — API 文档
- **Lombok** — 简化代码
- **Java** 1.8

## 项目结构

```
src/
├── main/
│   ├── java/com/scan/pos/
│   │   ├── common/          # 通用类（统一返回结果 Result）
│   │   ├── config/          # 配置类（CORS、Swagger、数据初始化）
│   │   ├── controller/      # 控制器层
│   │   │   ├── ProductController.java
│   │   │   ├── SaleController.java
│   │   │   └── UserController.java
│   │   ├── entity/          # 实体类
│   │   │   ├── Product.java
│   │   │   ├── Sale.java
│   │   │   ├── SaleItem.java
│   │   │   └── User.java
│   │   ├── mapper/          # MyBatis Mapper 接口
│   │   │   ├── ProductMapper.java
│   │   │   ├── SaleItemMapper.java
│   │   │   ├── SaleMapper.java
│   │   │   └── UserMapper.java
│   │   └── service/         # 服务层
│   │       ├── ProductService.java
│   │       ├── SaleService.java
│   │       ├── UserService.java
│   │       └── impl/
│   │           ├── ProductServiceImpl.java
│   │           ├── SaleServiceImpl.java
│   │           └── UserServiceImpl.java
│   └── resources/
│       ├── application.yml          # 应用配置
│       └── mapper/                  # MyBatis XML 映射文件
│           ├── ProductMapper.xml
│           ├── SaleItemMapper.xml
│           ├── SaleMapper.xml
│           └── UserMapper.xml
└── pom.xml
```

## 环境要求

| 依赖 | 版本 |
|------|------|
| JDK | 1.8+ |
| Maven | 3.6+ |
| MySQL | 8.0+ |

## 快速开始

### 1. 创建数据库

```sql
CREATE DATABASE saoma_pos DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 2. 修改配置

编辑 `src/main/resources/application.yml`，修改数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/saoma_pos?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: 你的密码
```

### 3. 启动项目

```bash
mvn spring-boot:run
```

或者使用 IDE（如 IntelliJ IDEA）直接运行 `PosApplication` 主类。

### 4. 访问 API 文档

启动后访问 Swagger UI：

```
http://localhost:8080/swagger-ui/index.html
```

## API 模块

| 模块 | 路径前缀 | 说明 |
|------|---------|------|
| 用户管理 | `/api/users` | 用户登录、注册、信息管理 |
| 商品管理 | `/api/products` | 商品的增删改查、扫码查询 |
| 销售管理 | `/api/sales` | 销售记录创建、查询 |

## 功能特性

- 扫码枪输入商品条码自动匹配
- 销售订单创建与管理
- 收入统计查询
- CORS 跨域支持（便于前后端分离开发）
- 启动时自动初始化演示数据

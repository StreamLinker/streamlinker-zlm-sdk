# StreamLinker ZLM SDK

[English](README.md) | 简体中文

`streamlinker-zlm-sdk` 是 StreamLinker 项目用于访问 ZLMediaKit HTTP API 的共享 Java SDK。

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](#构建)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)

## 项目定位

这个仓库是 StreamLinker 的共享基础设施仓库，用来提供一层轻量的 ZLMediaKit Java 封装。
它的目标是同时服务于边缘侧和云端侧项目，而不是承载具体业务语义。

当前使用方包括：
- [streamlinker-edge](https://github.com/StreamLinker/streamlinker-edge)
- `streamlinker-cloud`（开发中）

## 设计目标

这个 SDK 的设计原则是：
- 尽量贴近 ZLMediaKit 官方 HTTP API 契约
- 不把业务概念混入 SDK 层
- 为 StreamLinker 服务提供清晰的 Java 客户端接口
- 支持 Spring Boot 自动装配
- 随着 StreamLinker 的实际需求逐步扩展

## 模块

```text
streamlinker-zlm-sdk/
|- streamlinker-zlm-model
|- streamlinker-zlm-api
`- streamlinker-zlm-spring-boot-starter
```

### streamlinker-zlm-model

承载 ZLMediaKit API 的请求和响应模型。

当前已覆盖：
- `addFFmpegSource`
- `addStreamProxy`
- `addStreamPusherProxy`
- 通用响应结构模型
- 媒体信息和播放地址响应模型

### streamlinker-zlm-api

Java 客户端 API 层。

当前职责：
- 封装对 ZLMediaKit 的 HTTP 请求
- 自动注入 `secret`
- 对外暴露当前所需的类型化 Java 方法

### streamlinker-zlm-spring-boot-starter

Spring Boot 集成层。

当前职责：
- `ZlmProperties`
- `ZlmAutoConfiguration`
- 自动创建 `ZlmClient`

## 当前实现状态

当前仓库已经实现：
- 核心请求和响应模型
- `DefaultZlmClient`
- Spring Boot Starter 自动装配
- 模型序列化测试
- 客户端契约测试

## 当前 API 范围

当前 SDK 主要覆盖 StreamLinker 已经使用到的那部分 ZLMediaKit API，重点包括：
- 拉流源创建
- 代理流创建
- 推流代理创建
- 媒体信息查询
- 播放地址查询
- 相关删除与控制操作

后续不会一开始就把全部 ZLMediaKit API 一次性镜像完，而是随着 edge 和 cloud 的实际需要逐步补充。

## 使用示例

依赖：

```xml
<dependency>
  <groupId>io.streamlinker</groupId>
  <artifactId>streamlinker-zlm-spring-boot-starter</artifactId>
  <version>0.1.0-SNAPSHOT</version>
</dependency>
```

配置：

```yaml
streamlinker:
  zlm:
    base-url: http://127.0.0.1:80
    secret: your-secret
```

注入方式：

```java
@Service
public class DemoService {

    private final ZlmClient zlmClient;

    public DemoService(ZlmClient zlmClient) {
        this.zlmClient = zlmClient;
    }
}
```

## 构建

运行测试：

```bash
mvn test
```

安装到本地 Maven 仓库：

```bash
mvn install
```

## 后续计划

下一步建议包括：
- 随着 edge 和 cloud 演进逐步扩展 API 覆盖面
- 完善接入说明和示例文档
- 明确制品发布和版本策略
- 补充 ZLMediaKit 版本兼容性说明
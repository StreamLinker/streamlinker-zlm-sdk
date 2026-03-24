# StreamLinker ZLM SDK

[English](README.md) | 简体中文

`streamlinker-zlm-sdk` 是 StreamLinker 项目用于访问 ZLMediaKit HTTP API 的共享 Java SDK。

当前这个仓库会被以下项目复用：
- [streamlinker-edge](https://github.com/StreamLinker/streamlinker-edge)
- `streamlinker-cloud`（开发中）

## 目标

这个仓库提供一层轻量的 Java 封装，用来对接 ZLMediaKit 官方 HTTP API。

设计原则：
- 接口契约尽量贴近 ZLMediaKit 官方 HTTP API
- 不把业务概念混入 SDK
- 让 edge 和 cloud 可以复用同一套 SDK
- 支持 Spring Boot 自动装配

## 模块

```text
streamlinker-zlm-sdk/
|- streamlinker-zlm-model
|- streamlinker-zlm-api
`- streamlinker-zlm-spring-boot-starter
```

### streamlinker-zlm-model

用于承载 ZLMediaKit API 的请求和响应模型。

当前已覆盖的模型包括：
- `addFFmpegSource`
- `addStreamProxy`
- `addStreamPusherProxy`
- 通用响应结构
- 基础流信息和播放地址响应

### streamlinker-zlm-api

客户端 Java API 层。

当前职责：
- 封装对 ZLMediaKit 的 HTTP 请求
- 自动注入 `secret`
- 对外暴露类型化的 Java 调用方法

### streamlinker-zlm-spring-boot-starter

Spring Boot 集成层。

当前职责：
- `ZlmProperties`
- `ZlmAutoConfiguration`
- 自动创建 `ZlmClient`

## 当前状态

当前仓库已经包含：
- 核心请求与响应模型
- `DefaultZlmClient`
- Spring Boot Starter 自动装配
- 模型序列化和客户端契约测试

## 使用示例

```xml
<dependency>
  <groupId>io.streamlinker</groupId>
  <artifactId>streamlinker-zlm-spring-boot-starter</artifactId>
  <version>0.1.0-SNAPSHOT</version>
</dependency>
```

```yaml
streamlinker:
  zlm:
    base-url: http://127.0.0.1:80
    secret: your-secret
```

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

```bash
mvn test
```

安装到本地 Maven 仓库：

```bash
mvn install
```

## 说明

这个 SDK 当前刻意保持轻量，只覆盖 StreamLinker 现阶段真正需要的一部分 ZLMediaKit API。
后续会随着 edge 和 cloud 的演进，逐步补充更多接口。
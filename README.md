# StreamLinker ZLM SDK

English | [简体中文](README.zh-CN.md)

Shared Java SDK for StreamLinker projects to access ZLMediaKit HTTP APIs.

`streamlinker-zlm-sdk` is the shared infrastructure repository used by:
- [streamlinker-edge](https://github.com/StreamLinker/streamlinker-edge)
- `streamlinker-cloud` (under development)

## Goal

This repository provides a lightweight Java wrapper around official ZLMediaKit HTTP APIs.

Design principles:
- keep API contracts close to ZLMediaKit official HTTP API
- keep business concepts out of the SDK
- make the same SDK reusable in both edge and cloud services
- support Spring Boot auto-configuration

## Modules

```text
streamlinker-zlm-sdk/
|- streamlinker-zlm-model
|- streamlinker-zlm-api
`- streamlinker-zlm-spring-boot-starter
```

### streamlinker-zlm-model

Request and response models for ZLMediaKit APIs.

Current models include support for:
- `addFFmpegSource`
- `addStreamProxy`
- `addStreamPusherProxy`
- common response envelopes
- basic media info and stream URL responses

### streamlinker-zlm-api

Client-side Java API layer.

Current responsibilities:
- encapsulate HTTP requests to ZLMediaKit
- inject `secret` automatically
- expose typed Java methods for core ZLM operations

### streamlinker-zlm-spring-boot-starter

Spring Boot integration layer.

Current responsibilities:
- `ZlmProperties`
- `ZlmAutoConfiguration`
- auto-create `ZlmClient`

## Current status

The repository already contains:
- core request and response models
- `DefaultZlmClient`
- Spring Boot starter integration
- unit tests for model serialization and client contracts

## Example usage

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

## Build

```bash
mvn test
```

Install to local Maven repository:

```bash
mvn install
```

## Notes

This SDK is intentionally small and only covers the API subset currently needed by StreamLinker.
More ZLMediaKit endpoints will be added incrementally as edge and cloud requirements evolve.
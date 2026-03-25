# StreamLinker ZLM SDK

English | [简体中文](README.zh-CN.md)

Shared Java SDK for StreamLinker projects to access ZLMediaKit HTTP APIs.

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](#build)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)

## Product positioning

`streamlinker-zlm-sdk` is the shared infrastructure repository for StreamLinker projects.
It provides a lightweight Java wrapper around official ZLMediaKit HTTP APIs and is intended to be reused by both edge-side and cloud-side services.

Current consumers:
- [streamlinker-edge](https://github.com/StreamLinker/streamlinker-edge)
- `streamlinker-cloud` (under development)

## Design goals

The SDK is designed to:
- stay close to official ZLMediaKit HTTP API contracts
- avoid mixing business concepts into the SDK layer
- provide a clean Java client API for StreamLinker services
- support Spring Boot auto-configuration
- grow incrementally as StreamLinker requirements evolve

## Modules

```text
streamlinker-zlm-sdk/
|- streamlinker-zlm-model
|- streamlinker-zlm-api
`- streamlinker-zlm-spring-boot-starter
```

### streamlinker-zlm-model

Carries request and response models for ZLMediaKit APIs.

Current coverage includes:
- `addFFmpegSource`
- `addStreamProxy`
- `addStreamPusherProxy`
- common response envelope models
- media info and stream URL response models

### streamlinker-zlm-api

Java client API layer.

Current responsibilities:
- encapsulate HTTP requests to ZLMediaKit
- inject `secret` automatically
- expose typed Java methods for the currently needed ZLM operations

### streamlinker-zlm-spring-boot-starter

Spring Boot integration layer.

Current responsibilities:
- `ZlmProperties`
- `ZlmAutoConfiguration`
- automatic creation of `ZlmClient`

## Current implementation status

Already implemented in this repository:
- core request and response models
- `DefaultZlmClient`
- Spring Boot starter integration
- model serialization tests
- client contract tests

## Current API scope

The SDK currently focuses on the subset of ZLMediaKit APIs already needed by StreamLinker, including operations around:
- pull source creation
- proxy stream creation
- push proxy creation
- media querying
- stream URL querying
- related delete and control operations

Additional ZLMediaKit endpoints will be added gradually instead of trying to mirror the whole API surface at once.

## Example usage

Dependency:

```xml
<dependency>
  <groupId>io.streamlinker</groupId>
  <artifactId>streamlinker-zlm-spring-boot-starter</artifactId>
  <version>0.1.0-SNAPSHOT</version>
</dependency>
```

Configuration:

```yaml
streamlinker:
  zlm:
    base-url: http://127.0.0.1:80
    secret: your-secret
```

Injection:

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

Run tests:

```bash
mvn test
```

Install to local Maven repository:

```bash
mvn install
```

## Roadmap

Planned next steps:
- expand official API coverage as edge and cloud evolve
- improve usage documentation and examples
- define artifact publishing and version strategy
- support clearer compatibility guidance for ZLMediaKit versions
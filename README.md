# oom-simulator
## Spring Boot OOM Simulator
This is just a simple Spring Boot app to simulate JVM Out-of-Memory Error on Kubernetes. It may be helpful to understand how JVM Heap allocation work and manage a Spring Boot app on Kubernetes by using Horizontal Pod Autoscaler (HPA).

## Building Jar

```sh
mvn clean package
```

## Building Container Image

```sh
podman build -t oom-simulator .
```
## Configuration
### Environment Variables
**MEMORY_LIMIT** = Maximum heap size to be allocated. Set the value in Mebibyte (MiB).

**MEMORY_INCREMENT** = Memory allocation size in MiB until it reaches to the MEMORY_LIMIT.

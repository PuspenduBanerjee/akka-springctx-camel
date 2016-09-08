# akka-springctx-camel
A library to bind akka on scala or java with spring and any spring supported framework, as camel, cxf etc. 
#[ ![Codeship Status for PuspenduBanerjee/akka-springctx-camel](https://codeship.com/projects/bc619870-56ed-0134-7219-02458a2e1ff4/status?branch=master)](https://codeship.com/projects/172426)

It's simple to use this library:
Add repository: 
```xml
   <repositories>
         <repository>
             <id>jitpack.io</id>
             <url>https://jitpack.io</url>
         </repository>
    <repositories>
```
    
then Add artifact :
```xml
<dependency>
    <groupId>com.github.PuspenduBanerjee</groupId>
    <artifactId>akka-springctx-camel</artifactId>
    <version>0.0.1</version>
</dependency>
```

If you want to use latest artifact from master branch , use the following :
```xml
<dependency>
    <groupId>com.github.PuspenduBanerjee</groupId>
    <artifactId>akka-springctx-camel</artifactId>
    <version>master-SNAPSHOT</version>
</dependency>
```

Get hold of ActorSystem :
```scala
      implicit val system = SpringContextActorSystemProvider.create
```

Reference Project: https://github.com/PuspenduBanerjee/ScalaAkkaBlah
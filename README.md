# akka-springctx-camel
A library to bind akka on scala or java with spring and any spring supported framework, as camel, cxf etc. 

[ ![Codeship Status for PuspenduBanerjee/akka-springctx-camel](https://codeship.com/projects/bc619870-56ed-0134-7219-02458a2e1ff4/status?branch=master)](https://codeship.com/projects/172426) [ ![License](http://img.shields.io/:license-Apache%202-green.svg)](http://www.apache.org/licenses/LICENSE-2.0.txt)

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
    <version>0.0.5</version>
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

Supports Multiple ActorSystem under isolated Spring ApplicationContexts, as siblings.
```scala
            val actorSystems = 2551 to 2562 map (x => {
            val as = SpringContextActorSystemProvider.create("ActorSystem" + x,
              ConfigFactory.parseString("akka.remote.netty.tcp.port=" + x).withFallback(ConfigFactory.load()))
            val echoActor = as.actorOf(Props[EchoActor])
            val probe = new TestProbe(as, "probe")
            val msg = "Hi There"
            echoActor tell(msg, probe.ref)
            probe.expectMsg(1000 millis, msg)
            as
          })
```

Reference Project: https://github.com/PuspenduBanerjee/ScalaAkkaBlah
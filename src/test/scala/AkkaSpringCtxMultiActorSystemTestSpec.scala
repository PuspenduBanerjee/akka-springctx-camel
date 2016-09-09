import actors.EchoActor
import akka.actor.Props
import com.typesafe.config.ConfigFactory
import org.scalatest.FlatSpec
import system.SpringContextActorSystemProvider

/**
  * Created by 258265 on 9/9/2016.
  */
class AkkaSpringCtxMultiActorSystemTestSpec extends FlatSpec{
  behavior of "akka-springctx-camel"
  it should "be able to create multiple instances of ActorSystem" in {
  val actorSystems=  2551 to 2552 map(x=> {
      val as=SpringContextActorSystemProvider.create("ActorSystem"+x,
        ConfigFactory.parseString("akka.remote.netty.tcp.port=" + x).withFallback(ConfigFactory.load()))
      val echoActor=as.actorOf(Props[EchoActor])
    println( echoActor ! "Hi There" )
    as
    })


    actorSystems.foreach(x=>{x.terminate()})
    Thread.sleep(10000)
  }
}

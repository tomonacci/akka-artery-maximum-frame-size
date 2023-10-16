import akka.actor._
import akka.cluster._
import akka.cluster.ClusterEvent._
import akka.cluster.routing.{ClusterRouterGroup, ClusterRouterGroupSettings}
import akka.remote.AssociationErrorEvent
import akka.remote.QuarantinedEvent
import akka.routing.BroadcastGroup

case class SizableMessage(n: Int) {
  private val hiddenWeight = "x" * n
}

object ClusterNodeActor {
  def props(workerRouter: ActorRef): Props = Props(new ClusterNodeActor(workerRouter))
}
class ClusterNodeActor(workerRouter: ActorRef) extends Actor with ActorLogging {
  private val cluster = Cluster(context.system)

  /** Is called prior to this actor being able to accept messages
    */
  override def preStart(): Unit = {
    log.info("Subscribing to event streams")
    cluster.subscribe(self,
                      initialStateMode = InitialStateAsEvents,
                      classOf[MemberEvent],
                      classOf[UnreachableMember])
    context.system.eventStream
      .subscribe(self, classOf[akka.remote.AssociationErrorEvent])
    context.system.eventStream
      .subscribe(self, classOf[akka.remote.QuarantinedEvent])
    log.info("Subscribed to event streams")
  }

  def receive: Receive = {
    case MemberUp(member) =>
      log.info("Member up: {}", member)
      log.info("workerRouter: {}", workerRouter)
      workerRouter ! s"Hello $member from $self"
      workerRouter ! SizableMessage(65536)

    case x =>
      log.info("ClusterNodeActor received {}", x)
  }
}

object Worker {
  def props(): Props = Props(new Worker())
}
class Worker extends Actor with ActorLogging {
  override def receive: Receive = {
    case x => log.info("Worker received {}", x)
  }
}

object Main {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("cluster-test")

    val worker = system.actorOf(Worker.props(), name = "worker")
    val workerRouter = system.actorOf(ClusterRouterGroup(
      BroadcastGroup(Nil),
      ClusterRouterGroupSettings(
        totalInstances = Int.MaxValue,
        routeesPaths = List(worker.path.toStringWithoutAddress),
        allowLocalRoutees = true)
    ).props(), name = "worker-router")
    val clusterNode = system.actorOf(ClusterNodeActor.props(workerRouter), "cluster-node")
    worker ! "local hello"
  }
}

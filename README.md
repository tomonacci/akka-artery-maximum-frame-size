What happens when the size of a message sent over Akka Artery exceeds
`akka.remote.artery.advanced.maximum-frame-size`?

```sh
sbt pack
# Run the following in separate terminal windows
JAVA_OPTS="-Dakka.remote.artery.canonical.port=2551" target/pack/bin/main
JAVA_OPTS="-Dakka.remote.artery.canonical.port=2552" target/pack/bin/main
```

<details>
<summary>Sample output</summary>

```
% JAVA_OPTS='-Dakka.remote.artery.canonical.port=2552' target/pack/bin/main
Picked up _JAVA_OPTIONS: -Xms2G -Xmx8G -Xss6M
[INFO] [10/16/2023 12:24:48.075] [main] [ArteryTransport(akka://cluster-test)] Remoting started with transport [Artery tcp]; listening on address [akka://cluster-test@127.0.0.1:2552] with UID [8662958017991308636]
[INFO] [10/16/2023 12:24:48.095] [main] [Cluster(akka://cluster-test)] Cluster Node [akka://cluster-test@127.0.0.1:2552] - Starting up, Akka version [2.6.21] ...
[INFO] [10/16/2023 12:24:48.189] [main] [Cluster(akka://cluster-test)] Cluster Node [akka://cluster-test@127.0.0.1:2552] - Registered cluster JMX MBean [akka:type=Cluster]
[INFO] [10/16/2023 12:24:48.189] [main] [Cluster(akka://cluster-test)] Cluster Node [akka://cluster-test@127.0.0.1:2552] - Started up successfully
[INFO] [10/16/2023 12:24:48.228] [cluster-test-akka.actor.default-dispatcher-14] [akka://cluster-test/system/cluster/core/daemon/downingProvider] SBR started. Config: strategy [KeepMajority], stable-after [20 seconds], down-all-when-unstable [15 seconds], selfUniqueAddress [akka://cluster-test@127.0.0.1:2552#8662958017991308636], selfDc [default].
[INFO] [10/16/2023 12:24:48.246] [cluster-test-akka.actor.default-dispatcher-14] [akka://cluster-test@127.0.0.1:2552/user/worker] Worker received local hello
[INFO] [10/16/2023 12:24:48.247] [cluster-test-akka.actor.default-dispatcher-14] [akka://cluster-test@127.0.0.1:2552/user/cluster-node] Subscribing to event streams
[INFO] [10/16/2023 12:24:48.250] [cluster-test-akka.actor.default-dispatcher-14] [akka://cluster-test@127.0.0.1:2552/user/cluster-node] Subscribed to event streams
[INFO] [10/16/2023 12:24:48.380] [cluster-test-akka.actor.internal-dispatcher-15] [Cluster(akka://cluster-test)] Cluster Node [akka://cluster-test@127.0.0.1:2552] - Received InitJoin message from [Actor[akka://cluster-test@127.0.0.1:2551/system/cluster/core/daemon/firstSeedNodeProcess-1#508711031]], but this node is not initialized yet
[INFO] [10/16/2023 12:24:53.454] [cluster-test-akka.actor.internal-dispatcher-3] [Cluster(akka://cluster-test)] Cluster Node [akka://cluster-test@127.0.0.1:2552] - Received InitJoinAck message from [Actor[akka://cluster-test@127.0.0.1:2551/system/cluster/core/daemon#1051269279]] to [akka://cluster-test@127.0.0.1:2552]
[INFO] [10/16/2023 12:24:53.519] [cluster-test-akka.actor.internal-dispatcher-2] [Cluster(akka://cluster-test)] Cluster Node [akka://cluster-test@127.0.0.1:2552] - Welcome from [akka://cluster-test@127.0.0.1:2551]
[INFO] [10/16/2023 12:24:53.527] [cluster-test-akka.actor.default-dispatcher-5] [akka://cluster-test@127.0.0.1:2552/user/cluster-node] Member up: Member(akka://cluster-test@127.0.0.1:2551, Up)
[INFO] [10/16/2023 12:24:53.527] [cluster-test-akka.actor.default-dispatcher-5] [akka://cluster-test@127.0.0.1:2552/user/cluster-node] workerRouter: Actor[akka://cluster-test/user/worker-router#61477818]
[INFO] [10/16/2023 12:24:53.529] [cluster-test-akka.actor.default-dispatcher-14] [akka://cluster-test@127.0.0.1:2552/user/worker] Worker received Hello Member(akka://cluster-test@127.0.0.1:2551, Up) from Actor[akka://cluster-test/user/cluster-node#1801384398]
[INFO] [10/16/2023 12:24:53.532] [cluster-test-akka.actor.default-dispatcher-5] [akka://cluster-test@127.0.0.1:2552/user/cluster-node] ClusterNodeActor received MemberJoined(Member(akka://cluster-test@127.0.0.1:2552, Joining))
[ERROR] [10/16/2023 12:24:53.554] [cluster-test-akka.remote.default-remote-dispatcher-6] [Encoder(akka://cluster-test)] Failed to serialize oversized message [ActorSelectionMessage(SizableMessage)].
akka.remote.OversizedPayloadException: Discarding oversized payload sent to Some(Actor[akka://cluster-test@127.0.0.1:2551/]): max allowed size 32768 bytes. Message type [ActorSelectionMessage(SizableMessage)].

[INFO] [akkaDeadLetter][10/16/2023 12:24:53.557] [cluster-test-akka.actor.default-dispatcher-5] [akka://cluster-test@127.0.0.1:2551/] Message [SizableMessage] wrapped in [akka.actor.ActorSelectionMessage] from Actor[akka://cluster-test/user/cluster-node#1801384398] to Actor[akka://cluster-test@127.0.0.1:2551/] was dropped. Discarding oversized payload sent to Some(Actor[akka://cluster-test@127.0.0.1:2551/]): max allowed size 32768 bytes. Message type [ActorSelectionMessage(SizableMessage)].. [1] dead letters encountered. This logging can be turned off or adjusted with configuration settings 'akka.log-dead-letters' and 'akka.log-dead-letters-during-shutdown'.
[INFO] [10/16/2023 12:24:54.343] [cluster-test-akka.actor.default-dispatcher-14] [akka://cluster-test@127.0.0.1:2552/user/cluster-node] Member up: Member(akka://cluster-test@127.0.0.1:2552, Up)
[INFO] [10/16/2023 12:24:54.343] [cluster-test-akka.actor.default-dispatcher-14] [akka://cluster-test@127.0.0.1:2552/user/cluster-node] workerRouter: Actor[akka://cluster-test/user/worker-router#61477818]
[INFO] [10/16/2023 12:24:54.345] [cluster-test-akka.actor.default-dispatcher-25] [akka://cluster-test@127.0.0.1:2552/user/worker] Worker received SizableMessage(65536)
[ERROR] [10/16/2023 12:24:54.348] [cluster-test-akka.remote.default-remote-dispatcher-12] [Encoder(akka://cluster-test)] Failed to serialize oversized message [ActorSelectionMessage(SizableMessage)].
akka.remote.OversizedPayloadException: Discarding oversized payload sent to Some(Actor[akka://cluster-test@127.0.0.1:2551/]): max allowed size 32768 bytes. Message type [ActorSelectionMessage(SizableMessage)].

[INFO] [akkaDeadLetter][10/16/2023 12:24:54.348] [cluster-test-akka.actor.default-dispatcher-25] [akka://cluster-test@127.0.0.1:2551/] Message [SizableMessage] wrapped in [akka.actor.ActorSelectionMessage] from Actor[akka://cluster-test/user/cluster-node#1801384398] to Actor[akka://cluster-test@127.0.0.1:2551/] was dropped. Discarding oversized payload sent to Some(Actor[akka://cluster-test@127.0.0.1:2551/]): max allowed size 32768 bytes. Message type [ActorSelectionMessage(SizableMessage)].. [2] dead letters encountered. This logging can be turned off or adjusted with configuration settings 'akka.log-dead-letters' and 'akka.log-dead-letters-during-shutdown'.
```

</details>

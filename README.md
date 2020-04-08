<h1>Broker Application </h1>

<h2> Overview </h2>

This application has two templates which are Deemat and ShareRequest.</br>
First template which is the Deemat template is the central party which will issue shares to the user.
Deemat will have different choice and will provide shares to authorizes users. Unauthorized users will not get shares. The broker is the controller of the choice to provide authoziation.</br>
Do not get confused between Deemat and broker. Deemat account is the platform for user and broker interaction. Broker is the service provider. To buy shares the user must have a Deemat account.</br>
ShareRequest is the client side template using which the user will be able to request shares from the broker.</br>
In this application the total number of shares a broker can provide is fixed. Once the shares are sold to one of the user. The number of shares left with the broker to give to other users is updated.
so we cannot ask for any number of shares.</br>

<h2>Building </h2>

To Compile the project :

```
daml build
```

<h2>Test</h2>

```
cd daml
```

```
daml damlc -- test --files Scenario.daml
```
</br>

<h2>Running Scenarios</h2>

Open visual studio</br>
Click on Scenario.daml</br>
Now click on:

```
Scenario results
```
</br>

<h2>Scenario Covered</h2>

1. Authorization of user. </br>
2. User requesting share quantity. </br>
3. Accepting user request. </br>
4. Checking by Asserting expecting User account balance to Broker account balance.</br>

<h2>Scala-Binding log </h2>

```
21:14:30.992 [default-akka.actor.default-dispatcher-6] INFO - Client API initialization completed, Ledger ID: ClientUtil{ledgerId=sandbox-ab7cce40-8579-4357-9cd4-3892c964e10e}
21:14:32.166 [default-akka.actor.default-dispatcher-6] INFO - Broker created account: Deemat(Broker,Broker,1000,1000,Reliance)
21:14:32.168 [default-akka.actor.default-dispatcher-6] INFO - Broker sent create command: DomainCommand(Command(Create(CreateCommand(Some(Identifier(64803a3087ae6c06ffd08f1c8a3828f583e3aea2b22f83411a435581545bcf29,Main,Deemat)),Some(Record(Some(Identifier(64803a3087ae6c06ffd08f1c8a3828f583e3aea2b22f83411a435581545bcf29,Main,Deemat)),ArrayBuffer(RecordField(owner,Some(Value(Party(Broker)))), RecordField(broker,Some(Value(Party(Broker)))), RecordField(amount,Some(Value(Int64(1000)))), RecordField(max_amount,Some(Value(Int64(1000)))), RecordField(buy_Share,Some(Value(Text(Reliance)))))))))),<function5>)
21:14:33.354 [default-akka.actor.default-dispatcher-6] INFO - Broker received transaction: Transaction(51,54b1a602-d822-41de-aeae-429f55bd3acc,Broker Workflow,Some(Timestamp(0,0)),Vector(Event(Created(CreatedEvent(#51:0,#51:0,Some(Identifier(64803a3087ae6c06ffd08f1c8a3828f583e3aea2b22f83411a435581545bcf29,Main,Deemat)),None,Some(Record(None,Vector(RecordField(,Some(Value(Party(Broker)))), RecordField(,Some(Value(Party(Broker)))), RecordField(,Some(Value(Int64(1000)))), RecordField(,Some(Value(Int64(1000)))), RecordField(,Some(Value(Text(Reliance))))))),Vector(Broker),Vector(Broker),Vector(),Some())))),52,None)
21:14:33.364 [default-akka.actor.default-dispatcher-6] INFO - Broker received contract: Contract(#51:0,Deemat(Broker,Broker,1000,1000,Reliance),Some(),Vector(Broker),Vector(),None)
21:14:33.424 [default-akka.actor.default-dispatcher-6] INFO - Broker sent exercise command: DomainCommand(Command(Exercise(ExerciseCommand(Some(Identifier(64803a3087ae6c06ffd08f1c8a3828f583e3aea2b22f83411a435581545bcf29,Main,Deemat)),#51:0,AuthorizeInvestor,Some(Value(Record(Record(Some(Identifier(64803a3087ae6c06ffd08f1c8a3828f583e3aea2b22f83411a435581545bcf29,Main,AuthorizeInvestor)),ArrayBuffer(RecordField(investor,Some(Value(Party(Upanshu)))), RecordField(updated_Token,Some(Value(Int64(100)))))))))))),<function5>)
21:14:33.425 [default-akka.actor.default-dispatcher-6] INFO - Broker transferred IOU: Contract(#51:0,Deemat(Broker,Broker,1000,1000,Reliance),Some(),Vector(Broker),Vector(),None) to: Broker
21:14:33.439 [default-akka.actor.default-dispatcher-4] INFO - Broker received final transaction: Transaction(52,8b11796e-128f-4b7a-af37-727870e6c1a2,Broker Workflow,Some(Timestamp(0,0)),Vector(Event(Created(CreatedEvent(#52:1,#52:1,Some(Identifier(64803a3087ae6c06ffd08f1c8a3828f583e3aea2b22f83411a435581545bcf29,Main,Deemat)),None,Some(Record(None,Vector(RecordField(,Some(Value(Party(Upanshu)))), RecordField(,Some(Value(Party(Broker)))), RecordField(,Some(Value(Int64(0)))), RecordField(,Some(Value(Int64(100)))), RecordField(,Some(Value(Text(Reliance))))))),Vector(Broker),Vector(Broker),Vector(Upanshu),Some())))),53,None)
21:14:33.443 [default-akka.actor.default-dispatcher-4] INFO - Broker received confirmation for the IOU Transfer: Contract(#52:1,Deemat(Upanshu,Broker,0,100,Reliance),Some(),Vector(Broker),Vector(Upanshu),None)
21:14:33.652 [default-akka.actor.default-dispatcher-4] INFO - Upanshu sent exercise Request share: DomainCommand(Command(Exercise(ExerciseCommand(Some(Identifier(64803a3087ae6c06ffd08f1c8a3828f583e3aea2b22f83411a435581545bcf29,Main,Deemat)),#52:1,RequestShare,Some(Value(Record(Record(Some(Identifier(64803a3087ae6c06ffd08f1c8a3828f583e3aea2b22f83411a435581545bcf29,Main,RequestShare)),ArrayBuffer(RecordField(requestQuantity,Some(Value(Int64(10)))))))))))),<function5>)
21:14:33.652 [default-akka.actor.default-dispatcher-4] INFO - Upanshu request sent: Contract(#51:0,Deemat(Broker,Broker,1000,1000,Reliance),Some(),Vector(Broker),Vector(),None) to: Upanshu
21:14:33.680 [default-akka.actor.default-dispatcher-4] INFO - Upanshu received final transaction: Transaction(53,94435b34-b3d7-4581-8342-550601cf81e7,Upanshu Workflow,Some(Timestamp(0,0)),Vector(Event(Created(CreatedEvent(#53:1,#53:1,Some(Identifier(64803a3087ae6c06ffd08f1c8a3828f583e3aea2b22f83411a435581545bcf29,Main,ShareRequest)),None,Some(Record(None,Vector(RecordField(,Some(Value(Party(Broker)))), RecordField(,Some(Value(Party(Upanshu)))), RecordField(,Some(Value(ContractId(#52:1)))), RecordField(,Some(Value(Int64(10)))), RecordField(,Some(Value(Text(Reliance))))))),Vector(Upanshu),Vector(Broker, Upanshu),Vector(),Some())))),54,None)[Flag]
21:14:33.852 [default-akka.actor.default-dispatcher-4] INFO - Upanshu received confirmation: Contract(#53:1,ShareRequest(Broker,Upanshu,#52:1,10,Reliance),Some(),Vector(Broker, Upanshu),Vector(),None)
21:14:33.853 [default-akka.actor.default-dispatcher-4] INFO - IOU flow completed.
21:14:33.853 [main] INFO - Shutting down...
```











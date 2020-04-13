<h1>Broker Application </h1>

<h2> Overview </h2>

This application has two templates which are Demat and ShareRequest.</br>
First template which is the Demat template is the central party which will issue shares to the user.
Demat will have different choice and will provide shares to authorizes users. Unauthorized users will not get shares. The broker is the controller of the choice to provide authoziation.</br>
Do not get confused between Demat and broker. Demat account is the platform for user and broker interaction. Broker is the service provider. To buy shares the user must have a Demat account.</br>
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

<h2>Run Application</h2>
```
sbt "application/runMain com.knoldus.demat.DematAccount localhost 6865" 
```
</br>

<h2>Scala-Binding log </h2>

```
16:23:11.628 [default-akka.actor.default-dispatcher-11] INFO - Client API initialization completed, Ledger ID: ClientUtil{ledgerId=sandbox-45b0e5a8-8c76-41cc-8797-4847ea4df030}
16:23:13.621 [default-akka.actor.default-dispatcher-11] INFO - Broker created account: Deemat(Broker,Broker,1000,1000,Reliance)
16:23:13.622 [default-akka.actor.default-dispatcher-11] INFO - Broker sent create command: DomainCommand(Command(Create(CreateCommand(Some(Identifier(64803a3087ae6c06ffd08f1c8a3828f583e3aea2b22f83411a435581545bcf29,Main,Deemat)),Some(Record(Some(Identifier(64803a3087ae6c06ffd08f1c8a3828f583e3aea2b22f83411a435581545bcf29,Main,Deemat)),ArrayBuffer(RecordField(owner,Some(Value(Party(Broker)))), RecordField(broker,Some(Value(Party(Broker)))), RecordField(amount,Some(Value(Int64(1000)))), RecordField(max_amount,Some(Value(Int64(1000)))), RecordField(buy_Share,Some(Value(Text(Reliance)))))))))),<function5>)
16:23:14.073 [default-akka.actor.default-dispatcher-5] INFO - Broker received transaction: Transaction(1,6d97d126-1645-4c19-998e-be2270cf7617,Broker Workflow,Some(Timestamp(0,0)),Vector(Event(Created(CreatedEvent(#1:0,#1:0,Some(Identifier(64803a3087ae6c06ffd08f1c8a3828f583e3aea2b22f83411a435581545bcf29,Main,Deemat)),None,Some(Record(None,Vector(RecordField(,Some(Value(Party(Broker)))), RecordField(,Some(Value(Party(Broker)))), RecordField(,Some(Value(Int64(1000)))), RecordField(,Some(Value(Int64(1000)))), RecordField(,Some(Value(Text(Reliance))))))),Vector(Broker),Vector(Broker),Vector(),Some())))),2,None)
16:23:14.090 [default-akka.actor.default-dispatcher-5] INFO - Broker received contract: Contract(#1:0,Deemat(Broker,Broker,1000,1000,Reliance),Some(),Vector(Broker),Vector(),None)
16:23:14.185 [default-akka.actor.default-dispatcher-5] INFO - Broker sent exercise command: DomainCommand(Command(Exercise(ExerciseCommand(Some(Identifier(64803a3087ae6c06ffd08f1c8a3828f583e3aea2b22f83411a435581545bcf29,Main,Deemat)),#1:0,AuthorizeInvestor,Some(Value(Record(Record(Some(Identifier(64803a3087ae6c06ffd08f1c8a3828f583e3aea2b22f83411a435581545bcf29,Main,AuthorizeInvestor)),ArrayBuffer(RecordField(investor,Some(Value(Party(Upanshu)))), RecordField(updated_Token,Some(Value(Int64(100)))))))))))),<function5>)
16:23:14.186 [default-akka.actor.default-dispatcher-5] INFO - Broker transferred Authorization Request: Contract(#1:0,Deemat(Broker,Broker,1000,1000,Reliance),Some(),Vector(Broker),Vector(),None) to: Broker
16:23:14.222 [default-akka.actor.default-dispatcher-5] INFO - Broker received final transaction: Transaction(2,87cad3a6-5bc0-4b8c-b114-a544a5102d55,Broker Workflow,Some(Timestamp(0,0)),Vector(Event(Created(CreatedEvent(#2:1,#2:1,Some(Identifier(64803a3087ae6c06ffd08f1c8a3828f583e3aea2b22f83411a435581545bcf29,Main,Deemat)),None,Some(Record(None,Vector(RecordField(,Some(Value(Party(Upanshu)))), RecordField(,Some(Value(Party(Broker)))), RecordField(,Some(Value(Int64(0)))), RecordField(,Some(Value(Int64(100)))), RecordField(,Some(Value(Text(Reliance))))))),Vector(Broker),Vector(Broker),Vector(Upanshu),Some())))),3,None)
16:23:14.227 [default-akka.actor.default-dispatcher-5] INFO - Broker received confirmation for the Authorization Transfer: Contract(#2:1,Deemat(Upanshu,Broker,0,100,Reliance),Some(),Vector(Broker),Vector(Upanshu),None)
16:23:14.263 [default-akka.actor.default-dispatcher-5] INFO - Upanshu sent exercise Request share: DomainCommand(Command(Exercise(ExerciseCommand(Some(Identifier(64803a3087ae6c06ffd08f1c8a3828f583e3aea2b22f83411a435581545bcf29,Main,Deemat)),#2:1,RequestShare,Some(Value(Record(Record(Some(Identifier(64803a3087ae6c06ffd08f1c8a3828f583e3aea2b22f83411a435581545bcf29,Main,RequestShare)),ArrayBuffer(RecordField(requestQuantity,Some(Value(Int64(10)))))))))))),<function5>)
16:23:14.263 [default-akka.actor.default-dispatcher-5] INFO - Upanshu request sent: Contract(#1:0,Deemat(Broker,Broker,1000,1000,Reliance),Some(),Vector(Broker),Vector(),None) to: Upanshu
16:23:14.310 [default-akka.actor.default-dispatcher-5] INFO - Upanshu received final transaction: Transaction(3,64791ea1-abbf-4029-a1da-7bab81b3261e,Upanshu Workflow,Some(Timestamp(0,0)),Vector(Event(Created(CreatedEvent(#3:1,#3:1,Some(Identifier(64803a3087ae6c06ffd08f1c8a3828f583e3aea2b22f83411a435581545bcf29,Main,ShareRequest)),None,Some(Record(None,Vector(RecordField(,Some(Value(Party(Broker)))), RecordField(,Some(Value(Party(Upanshu)))), RecordField(,Some(Value(ContractId(#2:1)))), RecordField(,Some(Value(Int64(10)))), RecordField(,Some(Value(Text(Reliance))))))),Vector(Upanshu),Vector(Broker, Upanshu),Vector(),Some())))),4,None)[Flag]
16:23:14.323 [default-akka.actor.default-dispatcher-5] INFO - Upanshu received confirmation: Contract(#3:1,ShareRequest(Broker,Upanshu,#2:1,10,Reliance),Some(),Vector(Broker, Upanshu),Vector(),None)
16:23:14.324 [default-akka.actor.default-dispatcher-5] INFO - Demat flow completed.
16:23:14.324 [main] INFO - Shutting down...

```











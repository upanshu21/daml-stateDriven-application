// Copyright (c) 2020 The DAML Authors. All rights reserved.
// SPDX-License-Identifier: Apache-2.0

package com.digitalasset.quickstart.iou

import java.time.Instant

import akka.actor.ActorSystem
import akka.stream.Materializer
import com.digitalasset.api.util.TimeProvider
import com.digitalasset.grpc.adapter.AkkaExecutionSequencerPool
import com.digitalasset.ledger.api.refinements.ApiTypes.{ApplicationId, WorkflowId}
import com.digitalasset.ledger.api.v1.ledger_offset.LedgerOffset
import com.digitalasset.ledger.client.LedgerClient
import com.digitalasset.ledger.client.configuration.{CommandClientConfiguration, LedgerClientConfiguration, LedgerIdRequirement}
import com.digitalasset.quickstart.iou.ClientUtil.workflowIdFromParty
import com.digitalasset.quickstart.iou.DecodeUtil.{decodeAllCreated, decodeArchived, decodeCreated}
import com.digitalasset.quickstart.iou.FutureUtil.toFuture
import com.typesafe.scalalogging.StrictLogging
import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util.{Failure, Success}

// <doc-ref:imports>
import com.digitalasset.ledger.client.binding.{Primitive => P}
import com.knoldus.{Main => M}
// </doc-ref:imports>

object IouMain extends App with StrictLogging {

  if (args.length != 2) {
    logger.error("Usage: LEDGER_HOST LEDGER_PORT")
    System.exit(-1)
  }

  private val ledgerHost = args(0)
  private val ledgerPort = args(1).toInt

  // <doc-ref:issuer-definition>
  private val broker = P.Party("Broker")
  // </doc-ref:issuer-definition>
  // <doc-ref:new-owner-definition>
  private val upanshu = P.Party("Upanshu")
  // </doc-ref:new-owner-definition>

  private val asys = ActorSystem()
  private val amat = Materializer(asys)
  private val aesf = new AkkaExecutionSequencerPool("clientPool")(asys)

  private def shutdown(): Unit = {
    logger.info("Shutting down...")
    Await.result(asys.terminate(), 10.seconds)
    ()
  }

  private implicit val ec: ExecutionContext = asys.dispatcher

  private val applicationId = ApplicationId("IOU Example")

  private val timeProvider = TimeProvider.Constant(Instant.EPOCH)

  // <doc-ref:ledger-client-configuration>
  private val clientConfig = LedgerClientConfiguration(
    applicationId = ApplicationId.unwrap(applicationId),
    ledgerIdRequirement = LedgerIdRequirement("", enabled = false),
    commandClient = CommandClientConfiguration.default,
    sslContext = None,
    token = None
  )
  // </doc-ref:ledger-client-configuration>
//clientF gives the ledger id
  private val clientF: Future[LedgerClient] =
    LedgerClient.singleHost(ledgerHost, ledgerPort, clientConfig)(ec, aesf)

  private val clientUtilF: Future[ClientUtil] =
    clientF.map(client => new ClientUtil(client, applicationId, 30.seconds, timeProvider))

  private val offset0F: Future[LedgerOffset] = clientUtilF.flatMap(_.ledgerEnd)

  private val issuerWorkflowId: WorkflowId = workflowIdFromParty(broker)
  private val newOwnerWorkflowId: WorkflowId = workflowIdFromParty(upanshu)


  val demat = M.Deemat(
    owner = broker,
    broker = broker,
    amount = 1000,
    max_amount = 1000,
    buy_Share= "Reliance")


  val issuerFlow: Future[Unit] = for {
    clientUtil <- clientUtilF
    offset0 <- offset0F
    _ = logger.info(s"Client API initialization completed, Ledger ID: ${clientUtil.toString}")

    // <doc-ref:submit-iou-create-command>
    createCmd = demat.create
    _ <- clientUtil.submitCommand(broker, issuerWorkflowId, createCmd)
    _ = logger.info(s"$broker created account: $demat")
    _ = logger.info(s"$broker sent create command: $createCmd")
    // </doc-ref:submit-iou-create-command>

    tx0 <- clientUtil.nextTransaction(broker, offset0)(amat)
    _ = logger.info(s"$broker received transaction: $tx0")
    dematId <- toFuture(decodeCreated[M.Deemat](tx0))
    _ = logger.info(s"$broker received contract: $dematId")

    offset1 <- clientUtil.ledgerEnd

    // <doc-ref:iou-exercise-transfer-cmd>
    exerciseCmd = dematId.contractId.exerciseAuthorizeInvestor(actor = broker, investor = upanshu, updated_Token = 100)
    // </doc-ref:iou-exercise-transfer-cmd>
    _ <- clientUtil.submitCommand(broker, issuerWorkflowId, exerciseCmd)
    _ = logger.info(s"$broker sent exercise command: $exerciseCmd")
    _ = logger.info(s"$broker transferred IOU: $dematId to: $broker")


    tx1 <- clientUtil.nextTransaction(broker, offset1)(amat)
    _ = logger.info(s"$broker received final transaction: $tx1")
    deematContract <- toFuture(decodeAllCreated[M.Deemat](tx1).headOption)
    _ = logger.info(s"$broker received confirmation for the IOU Transfer: $deematContract")

    offset2 <- clientUtil.ledgerEnd

    exerciseCommand = deematContract.contractId.exerciseRequestShare(actor = upanshu, requestQuantity = 10)
    _ <- clientUtil.submitCommand(upanshu, newOwnerWorkflowId, exerciseCommand)
    _ = logger.info(s"$upanshu sent exercise Request share: $exerciseCommand")
    _ = logger.info(s"$upanshu request sent: $dematId to: $upanshu")

    tx2 <- clientUtil.nextTransaction(upanshu, offset2)(amat)
    _ = logger.info(s"$upanshu received final transaction: $tx2[Flag]")
    shareRequestTransaction <- toFuture(decodeAllCreated[M.ShareRequest](tx2).headOption)
    _ = logger.info(s"$upanshu received confirmation: $shareRequestTransaction")

  } yield ()


  val returnCodeF: Future[Int] = issuerFlow.transform {
    case Success(_) =>
      logger.info("IOU flow completed.")
      Success(0)
    case Failure(e) =>
      logger.error("IOU flow completed with an error", e)
      Success(1)
  }

  val returnCode: Int = Await.result(returnCodeF, 10.seconds)
  shutdown()
  System.exit(returnCode)
}

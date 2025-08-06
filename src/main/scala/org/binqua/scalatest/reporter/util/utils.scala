package org.binqua.scalatest.reporter.util

import org.binqua.scalatest.reporter._
import org.jsoup.Jsoup

object utils {

  implicit class EitherOps[L, R](either: Either[L, R]) {
    def getOrThrow: R = {
      either match {
        case Right(value) => value
        case Left(error)  => throw new RuntimeException(s"Unexpected Left: $error")
      }
    }
  }

  def clean(toBeCleaned: String): String = LazyList.from(toBeCleaned.split("\n")).filter(_.trim.nonEmpty).map(_.trim).mkString("\n")

  def toLunrRefValue(test: Test, feature: Feature, scenario: Scenario, screenshot: Screenshot): String =
    List(test.id, feature.id, scenario.id, screenshot.id).mkString("__")

  def toLunrDocument(events: TestsReport): List[LunrDocumentEntry] = for {
    test <- events.tests.values.toList
    feature <- test.features.featuresMap.values
    scenario <- feature.scenarios.scenariosMap.values
    screenshot <- scenario.screenshots
  } yield LunrDocumentEntry(
    utils.toLunrRefValue(test, feature, scenario, screenshot),
    screenshot.screenshotExternalData.pageUrl,
    screenshot.screenshotExternalData.pageTitle,
    utils.clean(Jsoup.parse(screenshot.screenshotDriverData.pageSource).wholeText()),
    screenshot.screenshotDriverData.pageSource
  )

}

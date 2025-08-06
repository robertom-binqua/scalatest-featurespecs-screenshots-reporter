package org.binqua.scalatest.reporter

import org.binqua.scalatest.reporter.ScreenshotMoment.{ON_ENTER_PAGE, ON_EXIT_PAGE}
import org.binqua.scalatest.reporter.TestOutcome.STARTING
import org.scalatest.events.Ordinal

object ReferenceData {

  object screenshotExternalData {
    def url1: ScreenshotExternalData = ScreenshotExternalData(pageUrl = "url1", pageTitle = "title 1", ON_EXIT_PAGE)
    def url2: ScreenshotExternalData = ScreenshotExternalData(pageUrl = "url2", pageTitle = "title 2", ON_ENTER_PAGE)
    def url3: ScreenshotExternalData = ScreenshotExternalData(pageUrl = "url3", pageTitle = "title 3", ON_EXIT_PAGE)
    def url4: ScreenshotExternalData = ScreenshotExternalData(pageUrl = "url4", pageTitle = "title 4", ON_ENTER_PAGE)
  }

  object screenshotDriverData {

    private val aDummyScreenshot: Array[Byte] = Array[Byte](1)

    def source(id: Int): String = s"""<!DOCTYPE html>
                                    |<html lang="en">
                                    |<head>
                                    |  <meta charset="UTF-8">
                                    |  <title>title$id</title>
                                    |</head>
                                    |<body>
                                    |  <h1>Welcome to My Page</h1>
                                    |</body>
                                    |</html>""".stripMargin

    def url1: ScreenshotDriverData = ScreenshotDriverData(image = aDummyScreenshot, pageSource = source(1), screenshotExternalData.url1)
    def url2: ScreenshotDriverData = ScreenshotDriverData(image = aDummyScreenshot, pageSource = source(2), screenshotExternalData.url2)
    def url3: ScreenshotDriverData = ScreenshotDriverData(image = aDummyScreenshot, pageSource = source(3), screenshotExternalData.url3)
    def url4: ScreenshotDriverData = ScreenshotDriverData(image = aDummyScreenshot, pageSource = source(4), screenshotExternalData.url4)
  }

  private val ordinal: Ordinal = new Ordinal(1).next

  val screenshot: Screenshot = Screenshot(screenshotDriverData.url1, ordinal = ordinal, index = 1)

  val startingScenario: Scenario = Scenario(
    ordinal = ordinal,
    description = "scenario desc",
    startedTimestamp = 1L,
    finishedTimestamp = Option.empty,
    screenshots = Nil,
    steps = Option.empty,
    testOutcome = STARTING,
    throwable = None
  )

  val scenario: Scenario = ReferenceData.startingScenario

  val scenarioWithScreenshot: Scenario = ReferenceData.startingScenario.copy(screenshots = List(screenshot))

  val feature: Feature =
    Feature("feature desc", Scenarios(scenariosMap = Map(scenarioWithScreenshot.description -> scenarioWithScreenshot)), scenarioWithScreenshot.ordinal)

  val test: Test = Test("test desc", Features(featuresMap = Map(feature.description -> feature)), scenario.ordinal)

  val tests: TestsReport = TestsReport(tests = Map(test.name -> test))

}

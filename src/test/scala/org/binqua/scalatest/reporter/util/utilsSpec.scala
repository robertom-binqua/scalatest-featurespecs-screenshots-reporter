package org.binqua.scalatest.reporter.util

import munit.FunSuite
import org.binqua.scalatest.reporter.{LunrDocumentEntry, ReferenceData}
import org.binqua.scalatest.reporter.ReferenceData.screenshotDriverData

class utilsSpec extends FunSuite {

  test("utils.clean removes empty lines and trims the remaining one") {
    val toBeCleaned =
      """
        |   a b c
        |
        |
        |   this is a piece of test
        |
        |
        |""".stripMargin

    val expected =
      """a b c
        |this is a piece of test""".stripMargin
    assertEquals(utils.clean(toBeCleaned), expected)
  }

  test("utils.toLunrRefValue join all Ids to create a id equals to testId_featureId_scenarioId_screenshotId") {
    assertEquals(
      obtained = utils.toLunrRefValue(ReferenceData.test, ReferenceData.feature, ReferenceData.scenario, ReferenceData.screenshot),
      expected = "t_1_1__f_1_1__s_1_1__ss_1"
    )
  }

  test("utils.toLunrDocument creates the right document") {
    assertEquals(
      obtained = utils.toLunrDocument(ReferenceData.tests),
      expected = List(
        LunrDocumentEntry(
          ref = "t_1_1__f_1_1__s_1_1__ss_1",
          url = screenshotDriverData.url1.screenshotExternalData.pageUrl,
          title = screenshotDriverData.url1.screenshotExternalData.pageTitle,
          bodyWithNoHtmlTags = """title1
                                  |Welcome to My Page""".stripMargin,
          body = screenshotDriverData.source(1)
        )
      )
    )
  }

}

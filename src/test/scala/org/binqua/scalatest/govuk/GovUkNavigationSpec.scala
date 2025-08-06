package org.binqua.scalatest.govuk

import org.binqua.scalatest.web.{ConfiguredChrome, WithScreenshotsSupport}
import org.openqa.selenium.support.ui.{ExpectedConditions, WebDriverWait}
import org.openqa.selenium.{By, WebElement}
import org.scalatest.featurespec.AnyFeatureSpec
import org.scalatest.matchers.should

import java.time.Duration

class GovUkNavigationSpec extends AnyFeatureSpec with should.Matchers with ConfiguredChrome with WithScreenshotsSupport {

  Feature("Navigate GOV.UK Benefits information") {

    Scenario("User navigates to Child Benefit payment details via benefit pages") {
      val wait = new WebDriverWait(webDriver, Duration.ofSeconds(10))

      go to "https://www.gov.uk/"

      val cookieButton: WebElement = wait.until(
        ExpectedConditions.elementToBeClickable(
          By.xpath("//button[normalize-space(text())='Accept additional cookies']")
        )
      )
      takeAScreenshot(click on cookieButton)

      // Step 0: Accept cookies by visible text
      val hideThisMessage: WebElement = wait.until(
        ExpectedConditions.elementToBeClickable(
          By.xpath("//button[normalize-space(text())='Hide this message']")
        )
      )
      takeAScreenshot(click on hideThisMessage)

      // Step 1: Click "Benefits"
      val benefitsLink = find(linkText("Benefits")).getOrElse(fail("Benefits link not found"))

      takeAScreenshot(click on benefitsLink)

      wait.until(ExpectedConditions.titleContains("Benefits"))

      // Step 2: Click "Manage an existing benefit, payment or claim"
      val manageLink = find(linkText("Manage an existing benefit, payment or claim")).getOrElse(fail("Manage benefit link not found"))
      takeAScreenshot(click on manageLink)

      wait.until(ExpectedConditions.titleContains("Manage"))

      // Step 3: Click "Child Benefit payment dates"
      val childBenefitLink = find(linkText("Child Benefit payment dates")).getOrElse(fail("Child Benefit payment dates link not found"))
      takeAScreenshot(click on childBenefitLink)

      wait.until(ExpectedConditions.titleContains("Child Benefit"))

      // Step 4: Click "due on a bank holiday"
      val bankHolidayLink = find(linkText("due on a bank holiday")).getOrElse(fail("Bank holiday link not found"))
      takeAScreenshot(click on bankHolidayLink)

      wait.until(ExpectedConditions.titleContains("Child Benefit payment dates"))

      // Step 5: Click "work out when you’ll be paid."
      val paymentDatesLink = find(linkText("work out when you’ll be paid")).getOrElse(fail("Payment calculator link not found"))
      takeAScreenshot(click on paymentDatesLink)

      // Final assertion (optional)
      wait.until(ExpectedConditions.titleContains("Child Benefit payment dates"))
      assert(currentUrl.contains("payment-dates"))
    }
  }
}

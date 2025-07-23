package org.binqua.scalatest.reporter

import io.circe.Encoder
import io.circe.generic.semiauto.deriveEncoder

case class LunrDocumentEntry(ref: String, url: String, title: String, bodyWithNoHtmlTags: String, body: String)

object LunrDocumentEntry{
  implicit val encoder: Encoder[LunrDocumentEntry] = deriveEncoder[LunrDocumentEntry]
}

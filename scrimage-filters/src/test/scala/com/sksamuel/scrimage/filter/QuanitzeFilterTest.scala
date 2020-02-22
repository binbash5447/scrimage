package com.sksamuel.scrimage.filter

import com.sksamuel.scrimage.ImmutableImage
import org.scalatest.{BeforeAndAfter, FunSuite, OneInstancePerTest}

class QuanitzeFilterTest extends FunSuite with BeforeAndAfter with OneInstancePerTest {

  private val original = getClass.getResourceAsStream("/bird_small.png")
  private val expected_64 = getClass.getResourceAsStream("/com/sksamuel/scrimage/filters/bird_small_quantize_64.png")
  private val expected_256 = getClass.getResourceAsStream("/com/sksamuel/scrimage/filters/bird_small_quantize_256.png")

  test("filter output matches expected") {
    val image = ImmutableImage.fromStream(original)
    assert(image.filter(new QuantizeFilter(64)) == ImmutableImage.fromStream(expected_64))
    assert(image.filter(new QuantizeFilter(256)) == ImmutableImage.fromStream(expected_256))
  }
}

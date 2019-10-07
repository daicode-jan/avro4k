@file:UseSerializers(
   LocalDateTimeSerializer::class,
   LocalDateSerializer::class,
   LocalTimeSerializer::class,
   TimestampSerializer::class,
   InstantSerializer::class,
   SqlDateSerializer::class
)

package com.sksamuel.avro4k.encoder

import com.sksamuel.avro4k.Avro
import com.sksamuel.avro4k.ListRecord
import com.sksamuel.avro4k.serializer.InstantSerializer
import com.sksamuel.avro4k.serializer.SqlDateSerializer
import com.sksamuel.avro4k.serializer.LocalDateSerializer
import com.sksamuel.avro4k.serializer.LocalDateTimeSerializer
import com.sksamuel.avro4k.serializer.LocalTimeSerializer
import com.sksamuel.avro4k.serializer.TimestampSerializer
import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.sql.Timestamp
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.sql.Date

class DateEncoderTest : FunSpec({

   test("encode LocalTime as an Int") {

      @Serializable
      data class Foo(val s: LocalTime)

      val schema = Avro.default.schema(Foo.serializer())
      Avro.default.toRecord(Foo.serializer(), Foo(LocalTime.of(12, 50, 45))) shouldBe
         ListRecord(schema, 46245000)
   }

   test("encode LocalDate as an Int") {

      @Serializable
      data class Foo(val s: LocalDate)

      val schema = Avro.default.schema(Foo.serializer())
      Avro.default.toRecord(Foo.serializer(), Foo(LocalDate.of(2018, 9, 10))) shouldBe
         ListRecord(schema, 17784)
   }

   test("encode java.sql.Date as an Int") {

      @Serializable
      data class Foo(val s: Date)

      val schema = Avro.default.schema(Foo.serializer())
      Avro.default.toRecord(Foo.serializer(), Foo(Date.valueOf(LocalDate.of(2018, 9, 10)))) shouldBe
         ListRecord(schema, 17784)
   }

   test("encode LocalDateTime as Long") {

      @Serializable
      data class Foo(val s: LocalDateTime)

      val schema = Avro.default.schema(Foo.serializer())
      Avro.default.toRecord(Foo.serializer(), Foo(LocalDateTime.of(2018, 9, 10, 11, 58, 59))) shouldBe
         ListRecord(schema, 1536580739000L)
   }

   test("encode Timestamp as Long") {

      @Serializable
      data class Foo(val s: Timestamp)

      val schema = Avro.default.schema(Foo.serializer())
      Avro.default.toRecord(Foo.serializer(), Foo(Timestamp.from(Instant.ofEpochMilli(1538312231000L)))) shouldBe
         ListRecord(schema, 1538312231000L)
   }

   test("encode Instant as Long") {

      @Serializable
      data class Foo(val s: Instant)

      val schema = Avro.default.schema(Foo.serializer())
      Avro.default.toRecord(Foo.serializer(), Foo(Instant.ofEpochMilli(1538312231000L))) shouldBe
         ListRecord(schema, 1538312231000L)
   }


})
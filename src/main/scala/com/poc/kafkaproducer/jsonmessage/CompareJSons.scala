package com.poc.kafkaproducer.jsonmessage

import com.google.common.collect.{MapDifference, Maps}
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import java.lang.reflect.Type
import java.util


object Main extends App  {

  val json1 = "{schema:[{\"odsname\": \"acct_key\", \"hbasename\":\"akey\"}]}"
  val json2 = "{schema:[{\"odsname\": \"acct_key\", \"hbasename\":\"akey\"}, {\"odsname\": \"posnid\", \"hbasename\":\"pid\"}]}"
  val json3 = "{schema:[{\"odsname\": \"acct_key\", \"hbasename\":\"akey\"}, {\"odsname\": \"posnid\", \"hbasename\":\"pid\"}]}"
  //CompareJSons(json2, json3)

  CompareJSons(json1, json2)
}

object CompareJSons {

  val gson = new Gson()
  val mapType: Type = new TypeToken[util.Map[String, AnyRef]]() {}.getType

  def apply(json1: String, json2: String): Unit ={

    //val resp1: util.List[Nothing] = util.Arrays.asList(gson.fromJson(json1, mapType))

    val firstMap: util.Map[String, AnyRef] = gson.fromJson(json1, mapType)
    val secondMap: util.Map[String, AnyRef] = gson.fromJson(json2, mapType)

    val differences: MapDifference[String, AnyRef] = Maps.difference(firstMap, secondMap)
    println(differences)

    println(differences.areEqual)
    println(differences.entriesOnlyOnLeft)
    println(differences.entriesOnlyOnRight)
    println(differences.entriesInCommon())
  }
}

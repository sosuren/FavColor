package com.example.favcolor.core

import com.example.favcolor.core.PollMessages.ColorSelect.ColorSelect

object PollMessages {

  case class Color(id: Long, name: String, code: String)
  case class Poll(first: (Color, Int), second: (Color, Int), third: (Color, Int))

  object ColorSelect extends Enumeration {
    type ColorSelect = Value
    val First, Second, Third = Value
  }

  object GetPoll
  object NextColorPoll
  case class Vote(select: ColorSelect)
}
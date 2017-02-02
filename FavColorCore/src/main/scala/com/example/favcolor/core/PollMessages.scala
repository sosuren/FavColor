package com.example.favcolor.core

import com.example.favcolor.core.PollMessages.ColorSelect.ColorSelect

object PollMessages {

  @SerialVersionUID(1L)
  sealed trait PollMessage extends Serializable
  @SerialVersionUID(1L)
  case class Color(id: Long, name: String, code: String) extends PollMessage
  @SerialVersionUID(1L)
  case class Poll(first: (Color, Int), second: (Color, Int), third: (Color, Int)) extends PollMessage

  object ColorSelect extends Enumeration {
    type ColorSelect = Value
    val First, Second, Third = Value
  }

  @SerialVersionUID(1L)
  object GetPoll extends PollMessage
  @SerialVersionUID(1L)
  object NextColorPoll extends PollMessage
  @SerialVersionUID(1L)
  case class Vote(select: ColorSelect) extends PollMessage
}
package code.lib

import net.liftweb.util.Helpers._
import net.liftweb.util.Schedule
import net.liftweb.actor.LiftActor

object MyScheduledTask extends LiftActor {

  case class DoIt()

  case class Stop()

  private var stopped = false

  def messageHandler = {
    case DoIt if !stopped =>
      Schedule.schedule(this, DoIt(), 10 seconds)
      println("do useful work here")

    case Stop =>
      stopped = true
  }
}


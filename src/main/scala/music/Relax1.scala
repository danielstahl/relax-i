package music

import music.Instruments._
import music.LineControlInstrumentBuilder._
import music.ARControlInstrumentBuilder._
import net.soundmining.Instrument._
import net.soundmining.{BusGenerator, MusicPlayer}

/**
 * The first relax piece
 */
object Relax1 {

  def firstRelax(): Unit = {
    BusGenerator.reset()
    implicit val player: MusicPlayer = MusicPlayer()

    player.startPlay()

    setupNodes(player)

    val noise1 = new BrownNoiseInstrumentBuilder()
      .addAction(TAIL_ACTION)
      .out(16)
      .dur(20)
      .ampBus.control(ar(20, 0.7f, (0, 1, 0)))
      .buildInstruments()

    val pan1 = new PanInstrumentBuilder()
      .addAction(TAIL_ACTION)
      .dur(20)
      .in(16)
      .out(0)
      .panBus.control(line(20, -0.5f, -1))
      .buildInstruments()

    val noise2 = new PinkNoiseInstrumentBuilder()
      .addAction(TAIL_ACTION)
      .out(17)
      .dur(20)
      .ampBus.control(ar(20, 0.3f, (0, 1, 0)))
      .buildInstruments()

    val pan2 = new PanInstrumentBuilder()
      .addAction(TAIL_ACTION)
      .dur(20)
      .in(17)
      .out(0)
      .panBus.control(line(20, 0.5f, 1))
      .buildInstruments()

    player.sendNew(0, noise1 ++ pan1 ++ noise2 ++ pan2)
  }

  def main(args: Array[String]): Unit = {
    firstRelax()
  }
}

package music

import music.Instruments._
import music.LineControlInstrumentBuilder._
import music.ARControlInstrumentBuilder._
import net.soundmining.Instrument._
import net.soundmining.{BusGenerator, MusicPlayer}
import music.SinControlInstrumentBuilder._
/**
 * The first relax piece
 */
object Relax1 {

  def firstRelax(): Unit = {
    BusGenerator.reset()
    implicit val player: MusicPlayer = MusicPlayer()

    player.startPlay()

    setupNodes(player)

    val dur = 60 * 25

    val noise1 = new BrownNoiseInstrumentBuilder()
      .addAction(TAIL_ACTION)
      .out(16)
      .dur(dur)
      .ampBus.control(sin(dur, 4f/(60f*25f), 4f/(60f*25f), mulStart = 0.5f, mulEnd = 0.5f))
      .buildInstruments()

    val dust1 = new DustInstrumentBuilder()
      .addAction(TAIL_ACTION)
      .out(16)
      .dur(dur)
      .ampBus.control(sin(dur, 4f/(60f*25f), 4f/(60f*25f), mulStart = 5f, mulEnd = 5f))
      .freqBus.control(line(dur, 2, 3))
      .buildInstruments()

    val pan1 = new PanInstrumentBuilder()
      .addAction(TAIL_ACTION)
      .dur(dur)
      .in(16)
      .out(0)
      .panBus.control(line(dur, -0.5f, -1))
      .buildInstruments()

    val noise2 = new PinkNoiseInstrumentBuilder()
      .addAction(TAIL_ACTION)
      .out(17)
      .dur(dur)
      .ampBus.control(sin(dur, 7f/(60f*25f), 7f/(60f*25f), mulStart = 0.5f, mulEnd = 0.5f))
      .buildInstruments()

    val dust2 = new DustInstrumentBuilder()
      .addAction(TAIL_ACTION)
      .out(17)
      .dur(dur)
      .ampBus.control(sin(dur, 7f/(60f*25f), 7f/(60f*25f), mulStart = 5f, mulEnd = 5f))
      .freqBus.control(line(dur, 3, 2))
      .buildInstruments()

    val pan2 = new PanInstrumentBuilder()
      .addAction(TAIL_ACTION)
      .dur(dur)
      .in(17)
      .out(0)
      .panBus.control(line(dur, 0.5f, 1))
      .buildInstruments()

    player.sendNew(0, noise1 ++ dust1 ++ pan1 ++ noise2 ++ dust2 ++ pan2)
  }

  def main(args: Array[String]): Unit = {
    firstRelax()
  }
}

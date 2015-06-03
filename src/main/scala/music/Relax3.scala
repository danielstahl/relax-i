package music

import music.LineControlInstrumentBuilder._
import music.SinControlInstrumentBuilder._
import net.soundmining.BusGenerator._
import net.soundmining.Instrument._
import net.soundmining.{BusGenerator, MusicPlayer}
import java.{lang => jl}

/**
 * The third variant
 */
object Relax3 {
  val globalAmp = 0.5f

  def makePan(dur: Float, in: jl.Integer, speed: Float) = {
    new PanInstrumentBuilder()
      .addAction(TAIL_ACTION)
      .dur(dur)
      .in(in)
      .out(0)
      .panBus.control(sin(dur, speed, speed))
      .buildInstruments()
  }

  def makeLimitPan(dur: Float, in: jl.Integer, speed: Float) = {
    new PanInstrumentBuilder()
      .addAction(TAIL_ACTION)
      .dur(dur)
      .in(in)
      .out(0)
      .panBus.control(sin(dur, speed, speed, mulStart = 0.5f, mulEnd = 0.5f))
      .buildInstruments()
  }

  def makeDust(bus: jl.Integer, dur: Float, speed: Float, startFreq: Float, endFreq: Float) = {
    (new DustInstrumentBuilder()
       .addAction(TAIL_ACTION)
       .out(bus)
       .dur(dur)
       .ampBus.control(sin(dur, speed, speed, mulStart = 5f * globalAmp, mulEnd = 5f * globalAmp))
       .freqBus.control(line(dur, startFreq, endFreq))
       .buildInstruments()
     ++
     makePan(dur, bus, speed))
  }

  def makeBrownNoise(dur: Float, speed: Float, startFreq: Float, endFreq: Float) = {
    val bus = nextAudio()

    (new BrownNoiseInstrumentBuilder()
       .addAction(TAIL_ACTION)
       .out(bus)
       .dur(dur)
       .ampBus.control(sin(dur, speed, speed, mulStart = 0.2f * globalAmp, mulEnd = 0.2f * globalAmp))
       .buildInstruments()
      ++
      makeDust(bus, dur, speed, startFreq, endFreq)
      ++
     makePan(dur, bus, speed))
  }

  def makePinkNoise(dur: Float, speed: Float, startFreq: Float, endFreq: Float) = {
    val bus = nextAudio()

    (new PinkNoiseInstrumentBuilder()
       .addAction(TAIL_ACTION)
       .out(bus)
       .dur(dur)
       .ampBus.control(sin(dur, speed, speed, mulStart = 0.2f * globalAmp, mulEnd = 0.2f * globalAmp))
       .buildInstruments()
     ++
     makeDust(bus, dur, speed, startFreq, endFreq)
     ++
     makePan(dur, bus, speed))
  }

  def makeWhiteNoise(dur: Float, speed: Float, startFreq: Float, endFreq: Float) = {
    val bus = nextAudio()

    (new WhiteNoiseInstrumentBuilder()
       .addAction(TAIL_ACTION)
       .out(bus)
       .dur(dur)
       .ampBus.control(sin(dur, speed, speed, mulStart = 0.2f * globalAmp, mulEnd = 0.2f * globalAmp))
       .buildInstruments()
     ++
     makeDust(bus, dur, speed, startFreq, endFreq)
     ++
     makePan(dur, bus, speed))
  }

  def makeMonAural(dur: Float, carrier: Float, targetWave: Float, speed: Float, amp: Float) = {
    val targetFrequency = carrier + targetWave

    val absoluteAmp = amp * 0.4f
    val audioBus = BusGenerator.nextAudio()
    (new MonAuralInstrumentBuilder()
       .addAction(TAIL_ACTION)
       .out(audioBus)
       .dur(dur)
       .ampBus.control(sin(dur, speed, speed, mulStart = absoluteAmp * globalAmp, mulEnd = absoluteAmp * globalAmp))
       .leftFreqBus.control(line(dur, carrier, carrier))
       .rightFreqBus.control(line(dur, targetFrequency, targetFrequency))
       .buildInstruments()
     ++
     makeLimitPan(dur, audioBus, speed))
  }

  def thirdFocus(dur: Float, carrier: Float, targetWave: Float, startWave: Float): Unit = {
    BusGenerator.reset()
    implicit val player: MusicPlayer = MusicPlayer()

    player.startPlay()

    setupNodes(player)

    player.sendNew(0,
                   Relax1.bathRoom(dur) ++
                   makeWhiteNoise(dur, 3f/dur, 2, 3) ++
                   makePinkNoise(dur, 21f/dur, 3, 2) ++
                   makeBrownNoise(dur, 13f/dur, 2, 3) ++
                   makeMonAural(dur, carrier, targetWave, 5f/dur, 1f/2f) ++
                   makeMonAural(dur, carrier, targetWave, 8f/dur, 1f/2f))
  }

  def main(args: Array[String]) = {
    val dur = 60f * 25f
    thirdFocus(dur, 260f, 7f, 13f)
  }
}

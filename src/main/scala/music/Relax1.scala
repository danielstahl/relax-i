package music

import music.LineControlInstrumentBuilder._
import music.SinControlInstrumentBuilder._
import music.ASRControlInstrumentBuilder._
import music.ARControlInstrumentBuilder._
import net.soundmining.Instrument._
import net.soundmining.{Spectrum, BusGenerator, MusicPlayer}
import Spectrum._

/**
 * The first relax piece
 */
object Relax1 {

  def bathRoom(dur: Float) =
    new GverbInstrumentBuilder()
      .nodeId(ROOM_EFFECT)
      .addAction(TAIL_ACTION)
      .in(0)
      .out(0)
      .dur(dur)
      .roomSize(5)
      .revTime(0.6f)
      .damping(0.62f)
      .inputBw(0.48f)
      .spread(15)
      .earlyLevel(-11)
      .tailLevel(-13)
      .buildInstruments()

  def livingRoom(dur: Float) =
    new GverbInstrumentBuilder()
      .nodeId(ROOM_EFFECT)
      .addAction(TAIL_ACTION)
      .in(0)
      .out(0)
      .dur(dur)
      .roomSize(16)
      .revTime(1.24f)
      .damping(0.10f)
      .inputBw(0.95f)
      .spread(15)
      .earlyLevel(-15)
      .tailLevel(-17)
      .buildInstruments()

  def church(dur: Float) =
    new GverbInstrumentBuilder()
      .nodeId(ROOM_EFFECT)
      .addAction(TAIL_ACTION)
      .in(0)
      .out(0)
      .dur(dur)
      .roomSize(80)
      .revTime(4.85f)
      .damping(0.41f)
      .inputBw(0.19f)
      .spread(15)
      .earlyLevel(-9)
      .tailLevel(-11)
      .buildInstruments()

  def cathedral(dur: Float) =
    new GverbInstrumentBuilder()
      .nodeId(ROOM_EFFECT)
      .addAction(TAIL_ACTION)
      .in(0)
      .out(0)
      .dur(dur)
      .roomSize(243)
      .revTime(1f)
      .damping(0.1f)
      .inputBw(0.34f)
      .spread(15)
      .earlyLevel(-11)
      .tailLevel(-9)
      .buildInstruments()


  def canyon(dur: Float) =
    new GverbInstrumentBuilder()
      .nodeId(ROOM_EFFECT)
      .addAction(TAIL_ACTION)
      .in(0)
      .out(0)
      .dur(dur)
      .roomSize(300)
      .revTime(103f)
      .damping(0.43f)
      .inputBw(0.51f)
      .spread(15)
      .earlyLevel(-26)
      .tailLevel(-20)
      .buildInstruments()

  def firstFocus(dur: Float, carrier: Float, targetWave: Float, startWave: Float): Unit = {
    BusGenerator.reset()
    implicit val player: MusicPlayer = MusicPlayer()

    player.startPlay()

    setupNodes(player)

    val targetFrequency = carrier + targetWave
    val startFrequency = carrier + startWave

    val reverb = bathRoom(dur)

    val noise1 = new BrownNoiseInstrumentBuilder()
      .addAction(TAIL_ACTION)
      .out(16)
      .dur(dur)
      .ampBus.control(sin(dur, 3f/dur, 3f/dur, mulStart = 0.2f, mulEnd = 0.2f))
      .buildInstruments()

    val dust1 = new DustInstrumentBuilder()
      .addAction(TAIL_ACTION)
      .out(16)
      .dur(dur)
      .ampBus.control(sin(dur, 3f/dur, 3f/dur, mulStart = 5f, mulEnd = 5f))
      .freqBus.control(ar(dur, 0.5f, (2, 3, 2)))
      .buildInstruments()

    val pan1 = new PanInstrumentBuilder()
      .addAction(TAIL_ACTION)
      .dur(dur)
      .in(16)
      .out(0)
      .panBus.control(line(dur, 0.5f, -0.5f))
      .buildInstruments()

    val noise2 = new WhiteNoiseInstrumentBuilder()
      .addAction(TAIL_ACTION)
      .out(17)
      .dur(dur)
      .ampBus.control(sin(dur, 4f/dur, 4f/dur, mulStart = 0.2f, mulEnd = 0.2f))
      .buildInstruments()

    val dust2 = new DustInstrumentBuilder()
      .addAction(TAIL_ACTION)
      .out(17)
      .dur(dur)
      .ampBus.control(sin(dur, 4f/dur, 4f/dur, mulStart = 5f, mulEnd = 5f))
      .freqBus.control(line(dur, 2, 3))
      .buildInstruments()

    val pan2 = new PanInstrumentBuilder()
      .addAction(TAIL_ACTION)
      .dur(dur)
      .in(17)
      .out(0)
      .panBus.control(line(dur, -0.5f, -1))
      .buildInstruments()

    val noise3 = new PinkNoiseInstrumentBuilder()
      .addAction(TAIL_ACTION)
      .out(18)
      .dur(dur)
      .ampBus.control(sin(dur, 7f/dur, 7f/dur, mulStart = 0.2f, mulEnd = 0.2f))
      .buildInstruments()

    val dust3 = new DustInstrumentBuilder()
      .addAction(TAIL_ACTION)
      .out(18)
      .dur(dur)
      .ampBus.control(sin(dur, 7f/dur, 7f/dur, mulStart = 5f, mulEnd = 5f))
      .freqBus.control(line(dur, 3, 2))
      .buildInstruments()

    val pan3 = new PanInstrumentBuilder()
      .addAction(TAIL_ACTION)
      .dur(dur)
      .in(18)
      .out(0)
      .panBus.control(line(dur, 0.5f, 1))
      .buildInstruments()


    val monAural = new MonAuralInstrumentBuilder()
      .addAction(TAIL_ACTION)
      .out(18)
      .dur(dur)
      .ampBus.control(asr(dur, (0f, 0.2f, 0.2f, 0.0f), (0.1f, 0.7f, 0.2f)))
      .leftFreqBus.control(line(dur, carrier, carrier))
      .rightFreqBus.control(asr(dur, (startFrequency, targetFrequency, targetFrequency, targetFrequency), (0.1f, 0.8f, 0.1f)))
      .buildInstruments()

    val pan4 = new PanInstrumentBuilder()
      .addAction(TAIL_ACTION)
      .dur(dur)
      .in(18)
      .out(0)
      .panBus.control(line(dur, -0.5f, 0.5f))
      .buildInstruments()

    val binAural = new BinAuralInstrumentBuilder()
      .addAction(TAIL_ACTION)
      .out(0)
      .dur(dur)
      .ampBus.control(asr(dur, (0f, 0.3f, 0.3f, 0.0f), (0.1f, 0.7f, 0.2f)))
      .leftFreqBus.control(line(dur, carrier, carrier))
      .rightFreqBus.control(asr(dur, (startFrequency, targetFrequency, targetFrequency, targetFrequency), (0.1f, 0.8f, 0.1f)))
      .buildInstruments()

    player.sendNew(0, reverb ++ noise1 ++ dust1 ++ pan1 ++ noise2 ++ dust2 ++ pan2 ++ noise3 ++ dust3 ++ pan3 ++ monAural ++ pan4/* binAural*/)
  }

  /**
   * 13–39 Hz	Beta waves	Active, busy or anxious thinking and active concentration, arousal, cognition, and or paranoia
   */
  def focus(dur: Float, carrier: Float = 260) =
    firstFocus(dur, carrier = carrier, targetWave = 13, startWave = 40)

  /**
   * 7–13 Hz	Alpha waves	Relaxation (while awake), pre-sleep and pre-wake drowsiness, REM sleep, Dreams
   */
  def relax(dur: Float, carrier: Float = 210) =
    firstFocus(dur, carrier = carrier, targetWave = 7.66f, startWave = 20)

  /**
   * 4–7 Hz	Theta waves	Deep meditation/relaxation, NREM sleep
   */
  def meditation(dur: Float, carrier: Float = 160) =
    firstFocus(dur, carrier = carrier, targetWave = 4, startWave = 13)

  def golden(dur: Float) =
    firstFocus(dur, carrier = 144.72f, startWave = 7, targetWave = phi)


  def schumann(dur: Float) =
    firstFocus(dur, carrier = 210f, startWave = 13, targetWave = 7.83f)

  def main(args: Array[String]): Unit = {
    val dur = 60f * 25f
    focus(dur)
  }
}

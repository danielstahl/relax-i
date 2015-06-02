package music

import music.ASRControlInstrumentBuilder._
import music.LineControlInstrumentBuilder._
import music.SinControlInstrumentBuilder._
import net.soundmining.Instrument._
import net.soundmining.{Spectrum, MusicPlayer, BusGenerator}

/**
 * Second variant of relax/focus
 */
object Relax2 {

  def makeMonAural(dur: Float, carrier: Float, targetWave: Float, speed: Float, amp: Float) = {

    val targetFrequency = carrier + targetWave

    val absoluteAmp = amp * 0.4f
    val audioBus = BusGenerator.nextAudio()
    println(s"Mon aural with frequency $targetFrequency")
    (new MonAuralInstrumentBuilder()
      .addAction(TAIL_ACTION)
      .out(audioBus)
      .dur(dur)
      .ampBus.control(sin(dur, speed, speed, mulStart = absoluteAmp, mulEnd = absoluteAmp))
      .leftFreqBus.control(line(dur, carrier, carrier))
      .rightFreqBus.control(line(dur, targetFrequency, targetFrequency))
      .buildInstruments()
    ++
    new PanInstrumentBuilder()
      .addAction(TAIL_ACTION)
      .dur(dur)
      .in(audioBus)
      .out(0)
      .panBus.control(sin(dur, speed, speed))
      .buildInstruments())
  }

  def secondFocus(dur: Float, spectrum: Seq[Float], targetWave: Float): Unit = {
    println(s"Spectrum $spectrum")

    BusGenerator.reset()
    implicit val player: MusicPlayer = MusicPlayer()

    player.startPlay()

    setupNodes(player)

    player.sendNew(0,
                   Relax1.bathRoom(dur) ++
                   makeMonAural(dur, spectrum(10), targetWave, 3f/dur, 1f) ++
                   makeMonAural(dur, spectrum(12), targetWave, 8f/dur, 1f/5f) ++
                   makeMonAural(dur, spectrum(14), targetWave, 21f/dur, 1f/8f)++

                   makeMonAural(dur, spectrum(15), targetWave, 25f/dur, 1f/9f) ++
                   makeMonAural(dur, spectrum(16), targetWave, 26f/dur, 1f/10f) ++
                   makeMonAural(dur, spectrum(17), targetWave, 26f/dur, 1f/11f)++

                   makeMonAural(dur, spectrum(20), targetWave, 28f/dur, 1f/12f) ++
                   makeMonAural(dur, spectrum(23), targetWave, 29f/dur, 1f/13f) ++
                   makeMonAural(dur, spectrum(26), targetWave, 30f/dur, 1f/14f))

  }

  def meditation(dur: Float, spectrum: Seq[Float], targetWave: Float = 4f) =
    secondFocus(dur, spectrum, targetWave)

  def golden(dur: Float, spectrum: Seq[Float], targetWave: Float = Spectrum.phi) =
    secondFocus(dur, spectrum, targetWave)

  def main(args: Array[String]): Unit = {
    val spectrum = Spectrum.makeSpectrum(20, 1, 200)
    val dur = 10f * 60f

    meditation(dur, spectrum)
  }
}

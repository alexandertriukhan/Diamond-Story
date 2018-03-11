package debug

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.TimeUtils


class FrameRate : Disposable {

    var lastTimeCounted = TimeUtils.millis()
    private var sinceChange = 0f
    private var frameRate = Gdx.graphics.framesPerSecond
    private var font: BitmapFont = BitmapFont()
    private var batch: SpriteBatch = SpriteBatch()
    private var cam: OrthographicCamera = OrthographicCamera(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())

    fun resize(screenWidth: Int, screenHeight: Int) {
        cam = OrthographicCamera(screenWidth.toFloat(), screenHeight.toFloat())
        cam.translate(screenWidth / 2f, screenHeight / 2f)
        cam.update()
        batch.projectionMatrix = cam.combined
    }

    fun update() {
        val delta = TimeUtils.timeSinceMillis(lastTimeCounted)
        lastTimeCounted = TimeUtils.millis()

        sinceChange += delta
        if (sinceChange >= 1000) {
            sinceChange = 0f
            frameRate = Gdx.graphics.framesPerSecond
        }
    }

    fun render() {
        batch.begin()
        font.draw(batch,frameRate.toString() + " fps", 3f, Gdx.graphics.height - 3f)
        batch.end()
    }

    override fun dispose() {
        font.dispose()
        batch.dispose()
    }

}
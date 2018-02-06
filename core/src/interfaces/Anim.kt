package interfaces

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion

interface Anim {

    val texture : TextureRegion
    val speed : Float
    val isLoop : Boolean

    fun draw (batch: Batch, x : Float, y : Float, size : Float, delta : Float)
}
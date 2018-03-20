package gameobjects

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import enums.JewelType
import utils.GameScreenAssets

class Cell(var isPlaying : Boolean, var jewel : Jewel, var tileTexture : TextureRegion, private val assets: GameScreenAssets) {

    var isTop = false
    var isBottom = false
    var isRight = false
    var isLeft = false
    var isTopEdge = false
    var isBottomEdge = false

    // TODO : Use init block to determine borders (save them to the list CellBorder and use CellBorder.draw())
    fun drawTile(batch: Batch, x : Float, y : Float, size : Float) {
        if (isPlaying) {
            batch.draw(tileTexture,x,y,size,size)
        }
        batch.end()
        drawBorders(x,y,size,assets.borderWidth)
        batch.begin()
    }

    private fun drawBorders(x : Float, y : Float, size : Float, borderSize : Float) {
        assets.border.begin(ShapeRenderer.ShapeType.Filled)
        if (isTop) {
            if (isTopEdge) {
                assets.border.rect(x - borderSize, y + size, size + borderSize * 2, borderSize)
            } else if (isRight && isLeft) {
                assets.border.rect(x - borderSize, y + size, size + borderSize * 2, borderSize)
            } else if (isRight) {
                assets.border.rect(x, y + size, size + borderSize, borderSize)
            } else if (isLeft) {
                assets.border.rect(x - borderSize, y + size, size + borderSize, borderSize)
            } else {
                assets.border.rect(x, y + size, size, borderSize)
            }
        }
        if (isBottom) {
            if (isBottomEdge) {
                assets.border.rect(x - borderSize, y - borderSize, size + borderSize * 2, borderSize)
            } else if (isRight && isLeft) {
                assets.border.rect(x - borderSize, y - borderSize, size + borderSize * 2, borderSize)
            } else if (isRight) {
                assets.border.rect(x, y - borderSize, size + borderSize, borderSize)
            } else if (isLeft) {
                assets.border.rect(x - borderSize, y - borderSize, size + borderSize, borderSize)
            } else {
                assets.border.rect(x, y - borderSize, size, borderSize)
            }
        }
        if (isRight) {
            assets.border.rect(x + size,y,borderSize,size)
        }
        if (isLeft) {
            assets.border.rect(x - borderSize,y,borderSize,size)
        }
        assets.border.end()
    }

    fun drawJewel(batch: Batch, x : Float, y : Float, size : Float, delta : Float) {
        if (isPlaying) {
            if (jewel.jewelType != JewelType.NO_JEWEL) {
                jewel.draw(batch, x, y, size, delta)
            }
        }
    }

    fun isBlocked() : Boolean {  // TODO: returns true for cells that aren't allowed to be moved, ex: chained jewels
        if (!isPlaying) {
            return true
        }
        if (jewel.jewelType == JewelType.NO_JEWEL) {
            return true
        }
        return false
    }

}
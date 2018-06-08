package gameobjects

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import utils.GameScreenAssets

class GameScreenUI(private val assets: GameScreenAssets, private val gameGrid: GameGrid) {

    // TODO: check why circle of different size on phone and on desktop
    private var colWidth  = Gdx.graphics.width.toFloat() * 0.4f
    private var colHeight = Gdx.graphics.height.toFloat() / 11f
    private val movesCircleRadius = (Gdx.graphics.width.toFloat() - (colWidth * 2)) / 2  // height = width
    private val moveCircleCenter = Vector2(colWidth + movesCircleRadius,Gdx.graphics.height.toFloat() - movesCircleRadius)

    private val shape = ShapeRenderer()
    private val decreasePercent : Float = 360f / gameGrid.movesLeft.toFloat()
    private var progressArcColor = Color.GREEN

    fun drawTopBar(batch: SpriteBatch) {
        drawTopBarShapes(batch)
        drawTopBarMoves(batch)
    }

    private fun drawTopBarShapes(batch: SpriteBatch) {
//        shape.begin(ShapeRenderer.ShapeType.Filled)
//        shape.color = Color.DARK_GRAY
//        shape.rect(0f,Gdx.graphics.height.toFloat() - colHeight,Gdx.graphics.width.toFloat(),colHeight)
//        shape.color = assets.myPurpleColor
//        shape.rect(0f,Gdx.graphics.height.toFloat() - colHeight - assets.borderWidth,Gdx.graphics.width.toFloat(),assets.borderWidth)
//        shape.end()
        // TODO: draw top bar and bottom bar here
        batch.end()
        drawProgressArc()
        batch.begin()
        // TODO: remove hardcode
        batch.draw(assets.movesCircle,colWidth + 5f,(Gdx.graphics.height.toFloat() - movesCircleRadius * 2) + 5f,(movesCircleRadius * 2) - 10f,(movesCircleRadius * 2) - 10f)
    }

    private fun drawTopBarMoves(batch: SpriteBatch) {
        assets.fontScore.color = Color.WHITE
        val fontLayout = GlyphLayout(assets.fontScore,gameGrid.movesLeft.toString())
        assets.fontScore.draw(batch,fontLayout,moveCircleCenter.x - (fontLayout.width / 2),moveCircleCenter.y + (fontLayout.height / 2))
    }

//    fun drawBottomBar(batch: SpriteBatch) {
//        batch.end()
//        shape.begin(ShapeRenderer.ShapeType.Filled)
//        shape.color = Color.DARK_GRAY
//        shape.rect(0f,0f,Gdx.graphics.width.toFloat(),colHeight)
//        shape.color = assets.myPurpleColor
//        shape.rect(0f,Gdx.graphics.height.toFloat() /11f,Gdx.graphics.width.toFloat(),assets.borderWidth)
//        shape.end()
//        batch.begin()
//    }

    private fun drawProgressArc() {
        shape.begin(ShapeRenderer.ShapeType.Filled)
        shape.color = Color.BLACK
        shape.circle(moveCircleCenter.x,moveCircleCenter.y,movesCircleRadius + 5f)
        shape.color = progressArcColor
        shape.arc(moveCircleCenter.x,moveCircleCenter.y,movesCircleRadius, 90f,gameGrid.movesLeft * decreasePercent)
        shape.end()
    }

    fun resize(width : Int, height : Int) {
        colWidth = width.toFloat() * 0.4f
        colHeight = height.toFloat() / 11f
    }

}
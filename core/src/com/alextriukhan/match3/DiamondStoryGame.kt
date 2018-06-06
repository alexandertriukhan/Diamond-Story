package com.alextriukhan.match3

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import utils.MyStack
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter
import screens.GameLoadingScreen

// import com.badlogic.gdx.net.HttpRequestBuilder.json
// import gameobjects.Level
// import java.io.Reader


class DiamondStoryGame : ApplicationAdapter() {

	private val assetManager = AssetManager()
	private val screenStack = MyStack<Screen>()
	
	override fun create () {
		screenStack.push(GameLoadingScreen(assetManager,this))
//		setResourcesToBeLoaded()
//		assetManager.finishLoading()
//		screenStack.push(GameScreen(gridTypes.square(),assetManager))
//		val levels : Array<Level> = arrayOf(Level(gridTypes.square()), Level(gridTypes.lToRWaterfall()))
//		val level = Level(gridTypes.square())
//		val levelStr = json.toJson(levels)
//		val file = Gdx.files.local("l1.json")
//		file.writeString(levelStr,false)
//		val javaHeap = Gdx.app.javaHeap
//		val nativeHeap = Gdx.app.nativeHeap
//		println("Java heap: " + javaHeap.toString())
//		println("Native heap: " + nativeHeap.toString())
	}

	override fun render () {
		screenStack.peek()?.render(Gdx.graphics.deltaTime)
	}

	fun removeScreen() {
		screenStack.pop()?.dispose()
	}

	fun replaceScreen(screen : Screen) {
		screenStack.pop()?.dispose()
		screenStack.push(screen)
	}

	fun pushScreen(screen : Screen) {
		screenStack.push(screen)
	}

	override fun resize(width: Int, height: Int) {
		screenStack.peek()?.resize(width,height)
	}

	// TODO: this must be resources needed to display main menu
	fun setResourcesToBeLoaded() {
		assetManager.apply {
			load("graphics/GameScreen.atlas",TextureAtlas::class.java)
			load("graphics/effects/fire.p",ParticleEffect::class.java)
			load("graphics/effects/explosion_red.p",ParticleEffect::class.java)
			load("graphics/effects/explosion_blue.p",ParticleEffect::class.java)
			load("graphics/effects/explosion_green.p",ParticleEffect::class.java)
			load("graphics/effects/explosion_yellow.p",ParticleEffect::class.java)
			load("graphics/effects/explosion_purple.p",ParticleEffect::class.java)
			load("graphics/effects/cross.p",ParticleEffect::class.java)
			val resolver = InternalFileHandleResolver()
			setLoader(FreeTypeFontGenerator::class.java,FreeTypeFontGeneratorLoader(resolver))
			setLoader(BitmapFont::class.java,".ttf",FreetypeFontLoader(resolver))
			val font32 = FreeTypeFontLoaderParameter()
			font32.fontFileName = "fonts/JollyLodger-Regular.ttf"
			font32.fontParameters.size = (32f * (Gdx.graphics.width.toFloat() / 520f)).toInt()  // 520f used as a referenced width
			font32.fontParameters.shadowOffsetX = 3
			font32.fontParameters.shadowOffsetY = 3
			load("fonts/JollyLodger-Regular.ttf",BitmapFont::class.java,font32)
		}
	}

}

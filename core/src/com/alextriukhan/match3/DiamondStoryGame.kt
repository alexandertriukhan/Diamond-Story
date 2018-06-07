package com.alextriukhan.match3

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.assets.AssetManager
import utils.MyStack
import enums.Screens
import screens.LoadingScreen

// import com.badlogic.gdx.net.HttpRequestBuilder.json
// import gameobjects.Level
// import java.io.Reader


class DiamondStoryGame : ApplicationAdapter() {

	private val assetManager = AssetManager()
	private val screenStack = MyStack<Screen>()
	
	override fun create () {
		screenStack.push(LoadingScreen(assetManager,this, Screens.GAME_SCREEN))
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

}

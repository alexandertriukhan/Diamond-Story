package com.alextriukhan.match3

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.net.HttpRequestBuilder.json
import utils.MyStack
import enums.Screens
import gameobjects.Level
import screens.LoadingScreen


class DiamondStoryGame : ApplicationAdapter() {

	private val assetManager = AssetManager()
	private val screenStack = MyStack<Screen>()
	val levelsData = mutableListOf<Level>()
	
	override fun create () {
		var levelNumber = 1
		while (true) {
			val levelFile = Gdx.files.internal("levels/l$levelNumber.json")
			if (levelFile.exists()) {
				levelsData.add(levelNumber - 1,json.fromJson(Level::class.java,levelFile))
			} else {
				break
			}
			levelNumber++
		}
		screenStack.push(LoadingScreen(assetManager,this, Screens.GAME_SCREEN, levelsData[1]))
//		val level = Level(gridTypes.square(), arrayOf(Objective(ObjectiveType.CHAINED,0,0)), intArrayOf(10000,50000,200000))
//		val levelStr = json.toJson(level)
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

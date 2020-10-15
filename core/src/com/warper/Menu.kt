package com.warper

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont

class Menu(bitmapFont: BitmapFont): Drawable, InputAdapter() {
    //buttons
    private var buttons: MutableList<Button> = mutableListOf()
    private object wallpaper{
        var timestamp: Float = 3f
        var blueValue: Float = 0.2f
        var shouldAdd = true
    };
    init {
        buttons.add(Button(200,bitmapFont.lineHeight.toInt() ,"Start",
                Color.WHITE,bitmapFont, (Gdx.graphics.width/2).toFloat(), (Gdx.graphics.height/2).toFloat() + bitmapFont.lineHeight*3))
        buttons.add( Button(200,bitmapFont.lineHeight.toInt(),"Settings", Color.WHITE,bitmapFont,
                (Gdx.graphics.width/2).toFloat(), (Gdx.graphics.height/2).toFloat()-bitmapFont.lineHeight - 50))
    }

    override fun draw(batch: Batch) {
        if(wallpaper.shouldAdd){
            wallpaper.blueValue += Gdx.graphics.deltaTime * 0.5f / wallpaper.timestamp
        } else  {
            wallpaper.blueValue -= Gdx.graphics.deltaTime * 0.5f / wallpaper.timestamp
        }
        if (wallpaper.blueValue > 0.8f) {
            wallpaper.shouldAdd = false
        } else if (wallpaper.blueValue < 0.1f) {
            wallpaper.shouldAdd = true
        }

        Gdx.gl.glClearColor(0f, 0f, wallpaper.blueValue, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        for (button in buttons){
            button.draw(batch)
        }
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        println("Touch X:${screenX} TouchY${screenY}")
        for(i in 0 until buttons.size-1){
            var button = buttons[i]
            println("Button(${button.getX()},${button.getY()} )")
            if (button.getX() < screenX && screenX < button.getX()+ button.width  ){
                if (button.getY() < screenY &&  screenY < button.getY() + button.height){
                    when(button.text){
                        "Start" -> {

                        }
                        "Settings" -> {
                            //to do move to the settings
                        }
                     }

                }
            }
        }
        return true
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        println("Hello wolrd mouse has moved!")
        buttons.removeAt(1);
        return true
    }
    fun dispose(){
        for (button in buttons){
            button.dispose()
        }
    }

}
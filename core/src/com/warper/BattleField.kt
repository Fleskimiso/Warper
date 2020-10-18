package com.warper

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.Batch

import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.g3d.Environment
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.math.collision.BoundingBox

class BattleField(): Stage() {

    private var perspectiveCamera = PerspectiveCamera(67f,
            Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
     var cameraInputController = CameraInputController(perspectiveCamera)


    private var player = Player(0f,0f, 0f)
    private var modelBatch = ModelBatch()
    private var environment =  Environment()
    init {
        /*to do camera initialization
        /camera position is synonymous with the player position */
        perspectiveCamera.position.set(10f,10f,10f)
        perspectiveCamera.lookAt(0f,0f,0f)
        perspectiveCamera.near = 1f
        perspectiveCamera.far = 300f
        //env
        val directionalLight = DirectionalLight()
        directionalLight.color.set(Color.BLUE)
        directionalLight.setDirection(0f,0f,-10f)
    }

    override fun draw(batch: Batch) {
        Gdx.gl.glViewport(0,0,Gdx.graphics.width,Gdx.graphics.height)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)
        handle_input()
        var boundingbox = BoundingBox()
        boundingbox = player.modelInstance.calculateBoundingBox(boundingbox)
        perspectiveCamera.lookAt(boundingbox.centerX,
                boundingbox.centerX,
                boundingbox.centerX)
        perspectiveCamera.update()
        modelBatch.begin(perspectiveCamera)
        //drawing
        player.draw3D(modelBatch)
        //end of drawing
        modelBatch.end()

    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        return super.mouseMoved(screenX, screenY)
    }

    override fun keyDown(keycode: Int): Boolean {
        when(keycode){
            Input.Keys.UP -> {
                //perspectiveCamera.translate(0f,0f,20f)
            }
            Input.Keys.DOWN -> {
               // perspectiveCamera.translate(0f,0f,-20f)
            }
        }
        return false
    }
    fun handle_input() {
        val camera_translation_vector = Vector3(0f,0f,0f)
        if(Gdx.input.isKeyPressed(Input.Keys.I)){
            if(Gdx.input.isKeyPressed(Input.Keys.Z)){
                camera_translation_vector.z += -30f * Gdx.graphics.deltaTime
            }else if(Gdx.input.isKeyPressed(Input.Keys.X)){
                camera_translation_vector.x += -30f * Gdx.graphics.deltaTime
            }else if(Gdx.input.isKeyPressed(Input.Keys.Y)){
                camera_translation_vector.y += -30f * Gdx.graphics.deltaTime
            }
        }else {
            if(Gdx.input.isKeyPressed(Input.Keys.Z)){
                camera_translation_vector.z += 30f * Gdx.graphics.deltaTime
            }else if(Gdx.input.isKeyPressed(Input.Keys.X)){
                camera_translation_vector.x += 30f * Gdx.graphics.deltaTime
            }else if(Gdx.input.isKeyPressed(Input.Keys.Y)){
                camera_translation_vector.y += 30f * Gdx.graphics.deltaTime
            }
        }
        perspectiveCamera.translate(camera_translation_vector)
    }

}
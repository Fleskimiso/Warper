package com.warper

import com.badlogic.gdx.Files
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g3d.Environment
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.math.collision.BoundingBox
import com.badlogic.gdx.utils.UBJsonReader
import com.warper.interfaces.Drawable
import com.warper.interfaces.Drawable3D
import com.warper.util.ModelFactory
import kotlin.math.pow
import kotlin.math.sqrt

class Player( x: Float, y: Float, z: Float,private val camera: PerspectiveCamera,
private val orthographicCamera: OrthographicCamera): Drawable, Drawable3D {

    private var speedZ= 20f
    private var boundingBox = BoundingBox()
    private var modelInstance = ModelFactory.getSpaceShipInstance()
    init {
        modelInstance.transform.translate(x, y, z)
    }
    private var speed = 100f
    init {
        boundingBox = modelInstance.calculateBoundingBox(boundingBox)
        camera.position.set(0f, 0f, 0f)
        camera.lookAt(0f,0f,-10f)
        modelInstance.transform.translate(0f,-10f,-10f)
        modelInstance.transform.rotate(Vector3(0f,10f,0f),180f)
    }
    private var focusVector3 = Vector3(0f,0f,-10f)
    private var lastDeltaX = 1f
    private var lastDeltaY = 1f
    private var timeAfterInit = 0f;

    //draws nothing
    override fun draw(batch: Batch) {

    }
    override fun draw3D(modelBatch: ModelBatch, environment: Environment?) {
        modelBatch.render(modelInstance, environment)
    }

    override fun dispose() {
        this.modelInstance.model.dispose()
    }
    fun handleInputDesktop() {
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            val translationVector = Vector3(0f,speed*Gdx.graphics.deltaTime,0f)
            this.camera.translate(translationVector)
            this.modelInstance.transform.translate(translationVector.scl(1f))
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            val translationVector = Vector3(0f,-speed*Gdx.graphics.deltaTime,0f)
            this.camera.translate(translationVector)
            this.modelInstance.transform.translate(translationVector.scl(1f))
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)){
            val translationVector = Vector3(-speed*Gdx.graphics.deltaTime,0f,0f)
            this.camera.translate(translationVector)
            this.modelInstance.transform.translate(translationVector.scl(-1f))
        }else if (Gdx.input.isKeyPressed(Input.Keys.D)){
            val translationVector = Vector3(speed*Gdx.graphics.deltaTime,0f,0f)
            this.camera.translate(translationVector)
            this.modelInstance.transform.translate(translationVector.scl(-1f))
        }

    }
    fun handleInputAndroid() {
       if(timeAfterInit > 1f) {

       }
    }
    fun getPosition(): Vector3 {
        return this.modelInstance.transform.getTranslation(Vector3(0f,0f,0f))
    }
    fun setFocus(position: Vector3) {
        this.focusVector3 = position
        this.camera.lookAt(focusVector3)
    }
    fun handlePlayerLogic() {
        timeAfterInit += Gdx.graphics.deltaTime
        val translationVector = Vector3(0f,0f,-speedZ*Gdx.graphics.deltaTime)
        camera.translate(translationVector)
        modelInstance.transform.translate(translationVector.scl(-1f))
    }

    fun handlePan(x: Float, y: Float, deltaX: Float, deltaY: Float) {
        println("deltas: ${deltaX} : $deltaY")

       if(timeAfterInit > 1f ) {
           if((deltaX > 0.1f || deltaY > 0.1f) || (deltaX < -0.1f || deltaY < -0.1f)) {
               this.lastDeltaX = deltaX
               this.lastDeltaY = -deltaY
               var cosinus = lastDeltaX/ sqrt(lastDeltaX.pow(2) + lastDeltaY.pow(2))
               var sinus = lastDeltaY/ sqrt(lastDeltaX.pow(2) + lastDeltaY.pow(2))
               this.camera.translate(cosinus*speed*Gdx.graphics.deltaTime,sinus*speed*Gdx.graphics.deltaTime,0f)
               this.modelInstance.transform.translate(-cosinus*speed*Gdx.graphics.deltaTime,
                       sinus*speed*Gdx.graphics.deltaTime,0f)
           }
       }
    }
    fun getVelocity() : Float {
        return this.speedZ
    }
    fun setVelocity(speed: Float) {
        this.speedZ = speed
    }

}
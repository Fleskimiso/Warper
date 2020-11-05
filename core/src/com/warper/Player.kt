package com.warper

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g3d.Environment
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.math.collision.BoundingBox
import com.warper.interfaces.Drawable
import com.warper.interfaces.Drawable3D
import com.warper.util.ModelFactory
import kotlin.math.pow
import kotlin.math.sqrt

class Player( x: Float, y: Float, z: Float,private val camera: PerspectiveCamera,
private val orthographicCamera: OrthographicCamera): Drawable, Drawable3D {

    private var speedZ= 40f
    private var boundingBox = BoundingBox()
    private var modelInstance = ModelFactory.getSpaceShipInstance()
    init {
        modelInstance.transform.translate(x, y, z)
    }
    init {
        boundingBox = modelInstance.calculateBoundingBox(boundingBox)
        camera.position.set(0f, 0f, 0f)
        camera.lookAt(0f,0f,-10f)
        modelInstance.transform.translate(0f,-10f,-10f)
        modelInstance.transform.rotate(Vector3(0f,10f,0f),180f)
    }
    private var focusVector3 = Vector3(0f,0f,-10f)
    private var speed = 100f
    private var shouldMove: Boolean = false
    private var moveDirection: Vector2 = Vector2(0f,0f)

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
        if(Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            setVelocity(400f)
        }else {
            setVelocity(40f)
        }

    }
    fun handleInputAndroid() {
       //does nothing ...
    }
    fun getPosition(): Vector3 {
        return this.modelInstance.transform.getTranslation(Vector3(0f,0f,0f))
    }
    fun setFocus(position: Vector3) {
        this.focusVector3 = position
        this.camera.lookAt(focusVector3)
    }
    fun handlePlayerLogic() {
        var translationVector = Vector3(0f,0f,-speedZ*Gdx.graphics.deltaTime)
        camera.translate(translationVector)
        modelInstance.transform.translate(translationVector.scl(-1f))
        if(!Gdx.input.isTouched) {
         shouldMove = false
        }
        if(shouldMove) {
            translationVector = Vector3(moveDirection.x/ sqrt(moveDirection.x.pow(2) + moveDirection.y.pow(2)),
                    moveDirection.y/ sqrt(moveDirection.x.pow(2) + moveDirection.y.pow(2)),0f)
            translationVector.scl(Gdx.graphics.deltaTime * speed)
            camera.translate(translationVector)
            translationVector.scl(-1f,1f,1f)
            modelInstance.transform.translate(translationVector)
        }
    }

    /***
    params: x,y - screen coordinates;

     Function sets the direction vector in XY plane.
     Activates movement in XY plane
     ***/
    fun direct(x: Float, y: Float) {
        val touchWorldCoords =  orthographicCamera.unproject(Vector3(x,y,0f))
        moveDirection = Vector2(touchWorldCoords.x,touchWorldCoords.y)
        setShouldMove(true)
        println("Pan: X:${touchWorldCoords.x} ; Y:${touchWorldCoords.y}")
    }
    fun getVelocity() : Float {
        return this.speedZ
    }
    fun setVelocity(speed: Float) {
        this.speedZ = speed
    }
    fun setShouldMove(value: Boolean) {
        this.shouldMove = value
    }
    fun getShouldMove(): Boolean {
        return this.shouldMove
    }

    fun back(valueZ: Float) {
        this.camera.translate(0f,0f,valueZ)
        this.modelInstance.transform.translate(0f,0f,valueZ)
    }

}
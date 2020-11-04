package com.warper.gameobjects

import com.badlogic.gdx.graphics.g3d.Environment
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.Renderable
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.math.collision.BoundingBox

class Stargate(x:Float,y:Float,z:Float, stargateModelsInstance: ModelInstance):
        GameObject(x,y,z, stargateModelsInstance) {
    private var stargateRenderable: Renderable = Renderable()
    init {
        this.modelInstance.getRenderable(
                stargateRenderable,
                this.modelInstance.getNode("Tube03")
        )
        stargateRenderable.worldTransform.setToRotation(0f,0f,0f,0f)
        stargateRenderable.worldTransform.scale(0.1f,0.1f,
                0.1f)
        stargateRenderable.worldTransform.setTranslation(x,y,z)

    }
    private var boundingBox = BoundingBox()
    init {
        boundingBox = stargateRenderable.meshPart.mesh.calculateBoundingBox()
    }

    override fun draw3D(modelBatch: ModelBatch, environment: Environment?) {
        modelBatch.render(stargateRenderable)
    }

    override fun setPosition(x: Float, y: Float, z: Float) {
        this.stargateRenderable.worldTransform.setTranslation(x,y,z)
    }

    override fun getPosition(): Vector3 {
        return this.stargateRenderable.worldTransform.getTranslation(Vector3(0f,0f,0f))
    }
    fun isWithin(point : Vector3): Boolean {
        val transVec =  stargateRenderable.worldTransform.getTranslation(Vector3())
            if (boundingBox.width + transVec.x
             > point.x && point.x > transVec.x ) {
                if(boundingBox.height + transVec.y > point.y && point.y < transVec.y){
                    return true
                }
            }
        return false
    }
}
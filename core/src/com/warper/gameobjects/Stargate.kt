package com.warper.gameobjects

import com.badlogic.gdx.graphics.g3d.Environment
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.Renderable
import com.badlogic.gdx.math.Vector3

class Stargate(x:Float,y:Float,z:Float, stargateModelsInstance: ModelInstance):
        GameObject(x,y,z, stargateModelsInstance) {
    private var stargateRenderable: Renderable = Renderable()
    init {
        this.modelInstance.getRenderable(
                stargateRenderable,
                this.modelInstance.getNode("Tube03")
        )
        /*var boundingBox = BoundingBox()
        stargateRenderable.meshPart.mesh.calculateBoundingBox(boundingBox)
        println("Rotation: ${stargateRenderable.worldTransform.getRotation(Quaternion(0f,0f,0f,0f))}")
        var rotation = stargateRenderable.worldTransform.getRotation(Quaternion(0f,0f,0f,0f))
        stargateRenderable.worldTransform.rotate(
                        rotation.mul(-1f))*/
        stargateRenderable.worldTransform.setToRotation(0f,0f,0f,0f)
        stargateRenderable.worldTransform.scale(0.1f,0.1f,
                0.1f)
        stargateRenderable.worldTransform.setTranslation(x,y,z)

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

}
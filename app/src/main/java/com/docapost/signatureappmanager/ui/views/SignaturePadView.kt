package com.docapost.signatureappmanager.ui.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class SignaturePadView @JvmOverloads constructor(
    context: Context,
    attrs : AttributeSet? = null,
    defStyleAttr : Int = 0
) : View(context, attrs, defStyleAttr) {
    private var path = Path()
    private var paint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 5f
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
        isAntiAlias = true
    }

    private val paintBackground = Paint().apply {
        color = Color.WHITE
    }

    private var isDrawing = false
    private var lastX = 0f
    private var lastY = 0f
    private val strokes = mutableListOf<Path>()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //Fond blanc
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintBackground)

        //Redessiner tous les traits
        for(stroke in strokes){
            canvas.drawPath(stroke, paint)
        }

        //Trait actuel
        canvas.drawPath(path, paint)

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when(event.action){
            MotionEvent.ACTION_DOWN -> {
                isDrawing = true
                path.moveTo(x, y)
                lastX = x
                lastY = y
                invalidate()
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                if(isDrawing){
                    path.quadTo(lastX, lastY, (lastX + x)/2, (lastY + y)/2)
                    lastX = x
                    lastY = y
                    invalidate()
                }
                return true
            }
            MotionEvent.ACTION_UP -> {
                if(isDrawing){
                    path.lineTo(x,y)
                    strokes.add(Path(path))
                    path.reset()
                    isDrawing = false
                    invalidate()
                }
                return true
            }
        }
        return false
    }

    fun clearSignature(){
        path.reset()
        strokes.clear()
        invalidate()
    }

    fun getSignatureBitmap() : android.graphics.Bitmap{
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        val canvas = Canvas(bitmap)
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintBackground)

        for(stroke in strokes){
            canvas.drawPath(stroke, paint)
        }

        return bitmap

    }

    fun isEmpty() : Boolean = strokes.isEmpty() && path.isEmpty
}
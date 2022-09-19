package com.example.mobie_project

import android.content.Context
import android.graphics.*
import android.view.View.OnTouchListener
import android.view.MotionEvent
import android.view.View

class Board( context: Context, model: Model) : View(context), OnTouchListener {
    var model: Model = Model()

    private var bgimage: Bitmap
    private var playerbar_W: Bitmap
    private var playerbar_B: Bitmap
    private var whitestoneimage: Bitmap
    private var blackstoneimage: Bitmap
    private var whitewinimage: Bitmap
    private var blackwinimage: Bitmap
    private var surrenderimage: Bitmap

    init {
        this.model = model
        model.initstone() //맵 배열 초기화 (0값)
        val r = context.resources
        bgimage = BitmapFactory.decodeResource(r, R.drawable.badukpan)
        bgimage = Bitmap.createScaledBitmap(bgimage, 1000, 1000, true)
        whitestoneimage = BitmapFactory.decodeResource(r, R.drawable.whitestone)
        blackstoneimage = BitmapFactory.decodeResource(r, R.drawable.blackstone)
        whitewinimage = BitmapFactory.decodeResource(r, R.drawable.whitewin)
        blackwinimage = BitmapFactory.decodeResource(r, R.drawable.blackwin)
        whitestoneimage = Bitmap.createScaledBitmap(whitestoneimage, 60, 60, false)
        blackstoneimage = Bitmap.createScaledBitmap(blackstoneimage, 60, 60, false)
        whitewinimage = Bitmap.createScaledBitmap(whitewinimage, 500, 500, false)
        blackwinimage = Bitmap.createScaledBitmap(blackwinimage, 500, 500, false)
        surrenderimage = BitmapFactory.decodeResource(r, R.drawable.surrenderimage)
        surrenderimage = Bitmap.createScaledBitmap(surrenderimage, 300, 100, false)
        playerbar_B = BitmapFactory.decodeResource(r, R.drawable.playerbar_black)
        playerbar_B = Bitmap.createScaledBitmap(playerbar_B, 700, 100, false)
        playerbar_W = BitmapFactory.decodeResource(r, R.drawable.playerbar_white)
        playerbar_W = Bitmap.createScaledBitmap(playerbar_W, 700, 100, false)
    }

    public override fun onDraw(canvas: Canvas) {
        val paint = Paint()
        //배둑판 배경그리기
        canvas.drawBitmap(bgimage, 25f, 25f, null)
        //플레이어 바 그리기
        if (model.turn_count % 2 === 1) {
            canvas.drawBitmap(playerbar_W, 150f, 1150f, null)
        } else if (model.turn_count % 2 === 0) {
            canvas.drawBitmap(playerbar_B, 150f, 1150f, null)
        }
        paint.textSize = 50f
        canvas.drawText("" + model.W_playername, 250f, 1225f, paint)
        canvas.drawText("" + model.B_playername, 650f, 1225f, paint)
        canvas.drawBitmap(whitestoneimage, 170f, 1175f, null)
        canvas.drawBitmap(blackstoneimage, 550f, 1175f, null)
        //바둑판 선 그리기
        paint.color = Color.BLACK
        paint.strokeWidth = 3f //선 굵기
        //바득판 가로 새로 줄 13개씩 그리기
        for (i in 0..12) {
            canvas.drawLine(50f, ((i + 1) * 75).toFloat(), 950f, ((i + 1) * 75).toFloat(), paint)
            canvas.drawLine(((i + 1) * 75-25).toFloat(), 75f, ((i + 1) * 75 -25).toFloat(), 975f, paint)

        }
        //가운데 중앙점 찍기
        canvas.drawCircle(500f,525f,10f,paint);

        //모델에 저장되어있는 이차원 배열을 통해 현재까지의 흰돌 검은돌들을 가져와 바둑판에 그리기
        for (i in 0..12) {
            for (j in 0..12) {
                if (model.getStone(i, j) === 1) {
                    canvas.drawBitmap(
                        whitestoneimage,
                        (i * 75 +20 ).toFloat(),
                        (j * 75 + 50).toFloat(),
                        paint
                    )
                } else if (model.getStone(i, j) === 2) {
                    canvas.drawBitmap(
                        blackstoneimage,
                        (i * 75 +20 ).toFloat(),
                        (j * 75 + 50).toFloat(),
                        paint
                    )
                }
            }
        }
        //가장 최근에 놓은 돌 빨간점으로 표시
        paint.color = Color.RED
        if (model.turn_count !== 1) { //시작을 제외하고
            if (model.turn_count % 2 === 1) //검은돌을 놓고 흰색턴이 되었을 때
                canvas.drawCircle(
                    (model.black_X * 75 +50).toFloat(),
                    (model.black_Y * 75 +75).toFloat(),
                    10f,
                    paint
                ) else if (model.turn_count % 2 === 0) { //흰돌을 놓고 검은색턴이 되었을 때
                canvas.drawCircle(
                    (model.white_X * 75+50 ).toFloat(),
                    (model.white_Y * 75+75 ).toFloat(),
                    10f,
                    paint
                )
            }
        }
        //승리 할 때의 비트맵 이미지 그리기
        if (model.whitewinstate === true) {
            canvas.drawBitmap(whitewinimage, 250f, 200f, paint)

        }
        if (model.blackwinstate === true) {
            canvas.drawBitmap(blackwinimage, 250f, 200f, paint)
        }
        super.onDraw(canvas)
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        val X = event.x
        val Y = event.y
        for (i in 0..12 ){
            for (j in 0..12) {
                if (X > 25 + i * 75 && X < 25 + (i + 1) * 75 && Y > 50 + j * 75 && Y < 50 + (j + 1) * 75) {   // 클릭했을 때의 좌표 범위를 구하는 조건
                    if (model.turn_count % 2 === 1) { //백돌의 차례일 때
                        if (model.getStone(i, j) === 0 && model.blackwinstate === false && model.whitewinstate === false) { //빈칸이고 상대가 승리하지 않았을때
                            model.setStone(1, i, j)
                            model.white_X = i
                            model.white_Y = j
                            model.turn_count++
                            invalidate()
                        }
                    } else if (model.turn_count % 2 === 0) {//흑돌 차례일 때
                        if (model.getStone(i, j) === 0 && model.whitewinstate === false && model.blackwinstate === false
                        ) {
                            model.setStone(2, i, j)
                            model.black_X = i
                            model.black_Y = j
                            model.turn_count++
                            invalidate()
                        }
                    }
                }
            }
        }
        //오목 승리조건 체크
        for (i in 0..12) {
            for (j in 0..12) {
                if (model.white_widthcheck(i, j) === true || model.white_heightcheck(i, j) === true || model.white_leftdiagcheck(i, j) === true || model.white_rightdiagcheck(i, j) === true) {
                    model.whitewinstate = true
                    invalidate()
                }
                if (model.black_widthcheck(i, j) === true || model.black_heightcheck(i, j) === true || model.black_leftdiagcheck(i, j) === true || model.black_rightdiagcheck(i, j) === true) {
                    model.blackwinstate = true
                    invalidate()
                }
            }
        }

        return false
    }


}
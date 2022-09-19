package com.example.mobie_project

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle

import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PlayActivity : AppCompatActivity() {
    var model: Model? = null
    var board: Board? = null
    companion object {
        var context: Context? = null
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        val intent: Intent = getIntent()
        context = this
        super.onCreate(savedInstanceState)
        model = Model()
        board = Board(this, model!!)
        model!!.W_playername = intent.getStringExtra("name")
        model!!.B_playername = intent.getStringExtra("name2")
        board!!.setOnTouchListener(board)
        setContentView(R.layout.activity_play)
        val blo: FrameLayout = findViewById(R.id.boardlayout) as FrameLayout
        blo.addView(board)
    }
    //핸드폰 기기의 뒤로가기 버튼 클릭시
    override fun onBackPressed() {
        Toast.makeText(this, "뒤로가기", Toast.LENGTH_SHORT).show()
        val dial = AlertDialog.Builder(this)
        dial.setTitle("게임 종료")
        dial.setMessage("게임을 종료하고 메인메뉴로 돌아가시겠습니까?")
        dial.setCancelable(false)
        dial.setPositiveButton("확인", DialogInterface.OnClickListener { dialogInterface, which ->
            val intent: Intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        })
        dial.setNegativeButton("취소", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface, WhichButton: Int) {
                dialog.cancel()
            }
        })
        dial.show()
    }
    //항복 버튼을 눌렀을 때
    fun onSurrenderButtonClicked(v: View?) {
        if (model!!.whitewinstate == false && model!!.blackwinstate == false) {
            val dial = AlertDialog.Builder(this)
            dial.setTitle("항복 선언")
            dial.setMessage("패배를 인정하시겠습니까??")
            dial.setCancelable(false)
            dial.setPositiveButton("확인", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, WhichButton: Int) {
                    if (model!!.turn_count % 2 == 0) model!!.whitewinstate =
                        true else if (model!!.turn_count % 2 == 1) model!!.blackwinstate = true
                    board!!.invalidate()
                }
            })
            dial.setNegativeButton("취소", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, WhichButton: Int) {
                    dialog.cancel()
                }
            })
            dial.show()
        }
    }


}
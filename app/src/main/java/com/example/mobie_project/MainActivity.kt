package com.example.mobie_project

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    var model: Model = Model()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onPlayButtonClicked(v: View?) {
        val dial = AlertDialog.Builder(this)
        val dial2 = AlertDialog.Builder(this)
        val etext = EditText(this)
        val etext2 = EditText(this)
        dial.setTitle("플레이어 이름 입력")
        dial.setMessage("백돌 플레이어")
        dial.setView(etext)
        dial2.setTitle("플레이어 이름 입력")
        dial2.setMessage("흑돌 플레이어")
        dial2.setView(etext2)
        dial.setPositiveButton("확인", DialogInterface.OnClickListener { dialogInterface, which ->
            model.W_playername = etext.text.toString()
            val intent: Intent = Intent(applicationContext, PlayActivity::class.java)
            dialogInterface.dismiss()
        })
        dial.setNegativeButton("취소", DialogInterface.OnClickListener { dialogInterface, which ->
            dialogInterface.cancel()
        })
        dial2.setPositiveButton("확인", DialogInterface.OnClickListener { dialogInterface, which ->
            model.W_playername = etext.text.toString()
            val intent: Intent = Intent(applicationContext, PlayActivity::class.java)
            intent.putExtra("name",etext.text.toString());
            intent.putExtra("name2",etext2.text.toString());
            startActivity(intent);
        })
        dial2.setNegativeButton("취소", DialogInterface.OnClickListener { dialogInterface, which ->
            dialogInterface.cancel()
        })
        dial2.show()
        dial.show()
    }
}
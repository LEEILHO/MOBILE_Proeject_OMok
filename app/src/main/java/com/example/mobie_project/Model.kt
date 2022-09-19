package com.example.mobie_project

import android.util.Log
import com.example.mobie_project.R

class Model {
    // 바둑판 배열
    private val stone = Array(13) { IntArray(13) }
    var White_Stone = 1
    var Black_Stone = 2
    var turn_count = 1
    var black_X = 0
    var black_Y = 0
    var white_X = 0
    var white_Y = 0
    var whitewinstate = false
    var blackwinstate = false
    var W_playername: String? = null
    var B_playername: String? = null

    //Stone 배열 초기화
    fun initstone() {
        var i: Int
        var j: Int
        i = 0
        while (i < 13) {
            j = 0
            while (j < 13) {
                stone[i][j] = 0
                j++
            }
            i++
        }
    }
    //이차원 배열에 흑돌인지 백돌인지 저장
    fun setStone(value: Int, i: Int, j: Int) {
        stone[i][j] = value
    }
    // 해당 위치의 배열에 놓인 돌이 어떤 색인지 가져옴
    fun getStone(i: Int, j: Int): Int {
        return stone[i][j]
    }
    //검정돌 가로확인.
    fun black_widthcheck(a: Int, b: Int): Boolean {
        var count = 0
        var max_count = 0
        if (stone[a][b] == 2) {
            for (i in a - 5..a + 5) {
                if (i >= 0 && i < 13) {
                    if (stone[i][b] == 2) {
                        count++
                        if (max_count < count) {
                            max_count = count
                        }
                    } else {
                        count = 0
                    }
                }
            }
            if (max_count == 5){//5목 확인
                return true
            } else if (max_count >= 6) {
                return false
            }
        }
        return false
    }
    //검정돌 세로확인.
    fun black_heightcheck(a: Int, b: Int): Boolean {
        var count = 0
        var max_count = 0
        if (stone[a][b] == 2) {
            for (j in b - 5..b + 5) {
                if (j >= 0 && j < 13) {
                    if (stone[a][j] == 2) {
                        count++
                        if (max_count < count) {
                            max_count = count
                        }
                    } else {
                        count = 0
                    }
                }
            }

            if (max_count == 5){
                return true
            } else if (max_count >= 6) {  //5목 확인
                return false
            }
        }
        return false
    }
    //검정돌 좌상우하 대각선
    fun black_leftdiagcheck(a: Int, b: Int): Boolean {
        var count = 0
        var max_count = 0
        if (stone[a][b] == 2) {
            for (i in -5..5) {
                if (a + i >= 0 && a + i < 13) {
                    if (b + i >= 0 && b + i < 13) {
                        if (stone[a + i][b + i] == 2) {
                            count++
                            if (max_count < count) {
                                max_count = count
                            }
                        } else {
                            count = 0
                        }
                    }
                }
            }
            if (max_count == 5) {
                return true
            } else if (max_count >= 6) {
                return false
            }
        }
        return false
    }
    //검정돌 우상좌하 대각선
    fun black_rightdiagcheck(a: Int, b: Int): Boolean {
        var count = 0
        var max_count = 0
        if (stone[a][b] == 2) {
            for (i in -5..5) {
                if (a - i >= 0 && a - i < 13) {
                    if (b + i >= 0 && b + i < 13) {
                        if (stone[a - i][b + i] == 2) {
                            count++
                            if (max_count < count) {
                                max_count = count
                            }
                        } else {
                            count = 0
                        }
                    }
                }
            }
            if (max_count == 5) {
                return true
            } else if (max_count >= 6) {
                return false
            }
        }
        return false
    }
    //백돌 가로확인.
    fun white_widthcheck(a: Int, b: Int): Boolean {

        var count = 0
        var max_count = 0
        if (stone[a][b] == 1) {
            for (i in a - 5..a + 5) {
                if (i >= 0 && i < 13) {
                    if (stone[i][b] == 1) {
                        count++
                        if (max_count < count) {
                            max_count = count
                        }
                    } else {
                        count = 0
                    }
                }
            }
            if (max_count == 5){ //5목 확인
                return true
            } else if (max_count >= 6) { //6목 방지
                return false
            }
        }
        return false
    }
    //백돌 세로확인
    fun white_heightcheck(a: Int, b: Int): Boolean {
        var count = 0
        var max_count = 0
        if (stone[a][b] == 1) {
            for (j in b - 5..b + 5) {
                if (j >= 0 && j < 13) {
                    if (stone[a][j] == 1) {
                        count++
                        if (max_count < count) {
                            max_count = count
                        }
                    } else {
                        count = 0
                    }
                }
            }
            if (max_count == 5) { //5목 확인
                return true
            }else if (max_count >= 6) { //6목 방지
                return false
            }
        }
        return false
    }

    //백돌 좌상우하 대각선
    fun white_leftdiagcheck(a: Int, b: Int): Boolean {
        var count = 0
        var max_count = 0
        if (stone[a][b] == 1) {
            for (i in -5..5) {
                if (a + i >= 0 && a + i < 13) {
                    if (b + i >= 0 && b + i < 13) {
                        if (stone[a + i][b + i] == 1) {
                            count++
                            if (max_count < count) {
                                max_count = count
                            }
                        } else {
                            count = 0
                        }
                    }
                }
            }
            if (max_count == 5) {
                return true
            } else if (max_count >= 6) {
                return false
            }
        }
        return false
    }
    //백돌 우상좌하 대각선
    fun white_rightdiagcheck(a: Int, b: Int): Boolean {
        var count = 0
        var max_count = 0
        if (stone[a][b] == 1) {
            for (i in -5..5) {
                if (a - i >= 0 && a - i < 13) {
                    if (b + i >= 0 && b + i < 13) {
                        if (stone[a - i][b + i] == 1) {
                            count++
                            if (max_count < count) {
                                max_count = count
                            }
                        } else {
                            count = 0
                        }
                    }
                }
            }
            if (max_count == 5) {
                return true
            } else if (max_count >= 6) {
                return false
            }
        }
        return false
    }

}
package com.fly.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fly.optlib.tokens.HotpToken
import com.fly.optlib.utils.SeedConvertor
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btCreate.setOnClickListener { createPSWD() }
    }

    private fun createPSWD() {

        var hexSeed = randomSeed()
        val token = HotpToken("mark", "123456789", hexSeed, 1, 6, "fly")
        val otp = token.generateOtp()
        tvInfo.text = "password : $otp"
    }

    private fun randomSeed(): String? {
        val ba = SeedConvertor.ConvertFromEncodingToBA(HotpToken.generateNewSeed(160),
                SeedConvertor.HEX_FORMAT)
        var hexSeed = SeedConvertor.ConvertFromBA(ba, SeedConvertor.BASE32_FORMAT)
        hexSeed = SeedConvertor.ConvertFromBA(SeedConvertor.ConvertFromEncodingToBA(hexSeed, SeedConvertor.BASE32_FORMAT), 0)
        return hexSeed
    }
}
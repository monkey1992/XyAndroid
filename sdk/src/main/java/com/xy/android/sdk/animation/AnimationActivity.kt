package com.xy.android.sdk.animation

import android.graphics.drawable.Animatable
import android.os.Bundle
import com.xy.android.sdk.BackActivity
import com.xy.android.sdk.databinding.ActivityAnimationBinding

class AnimationActivity : BackActivity() {

    private lateinit var binding: ActivityAnimationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (binding.ivAnimator.drawable as? Animatable)?.start()
    }
}
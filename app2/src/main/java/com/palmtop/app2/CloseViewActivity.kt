package com.palmtop.app2

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CloseViewActivity : AppCompatActivity() {
    val mainHandler = Handler(Looper.getMainLooper())
    lateinit var hongBao: ImageView
    lateinit var tvReward: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_close_view)
        val closeView: CloseView = findViewById(R.id.close_view)
        closeView.startTimer(3)

        closeView.setOnClickListener {
            Toast.makeText(this, "点击了", Toast.LENGTH_LONG).show()
        }
        hongBao = findViewById(R.id.hongbao)
        tvReward = findViewById(R.id.tv_reward)

        startAnim()
    }

    private fun startAnim() {
        hongBao.setOnClickListener {
//            val animation = Rotate3DAnimation()
//            animation.repeatCount = 0
////            tvReward.visibility = View.INVISIBLE
//            animation.setAnimationListener(object : Animation.AnimationListener {
//                override fun onAnimationStart(animation: Animation) {}
//                override fun onAnimationEnd(animation: Animation) {
//                    Log.d("Shelter", "CloseViewActivity onAnimationEnd")
//                    onTransitionEnd()
//                }
//
//                override fun onAnimationRepeat(animation: Animation) {}
//            })
            val hongbao: View = findViewById(R.id.hongbao)
            val tvReward: View = findViewById(R.id.tv_reward)
//            animLayout.startAnimation(animation)
//            hongBao.startAnimation(animation)

            val animator1 = ObjectAnimator.ofFloat(hongbao, "rotationY", 0f, 180f)
            val animator2 = ObjectAnimator.ofFloat(tvReward, "rotationY", 180f, 360f)
            tvReward.visibility = View.INVISIBLE
            animator2.addUpdateListener {
                if (it.animatedFraction >= 0.5f) {
                    tvReward.visibility = View.VISIBLE
                }
            }
            val animatorSet = AnimatorSet()
            animatorSet.duration = 5000
            animatorSet.interpolator = LinearInterpolator()
            animatorSet.playTogether(animator1, animator2)
            animatorSet.start()
        }
    }

    private fun onTransitionEnd() {
//        tvReward.visibility = View.VISIBLE
    }

}
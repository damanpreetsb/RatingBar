package com.daman.library

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.LinearLayout

class SimpleRatingBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var totalStars = 4
    private var mRating = -1

    private var mAnimationType = 0
    private var mEmptyDrawable: Drawable? = null
    private var mFilledDrawable: Drawable? = null

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SimpleRatingBar)
        initParamsValue(typedArray)
    }

    private fun initParamsValue(typedArray: TypedArray) {
        totalStars = typedArray.getInteger(R.styleable.SimpleRatingBar_total_stars, 4)
        totalStars -= 1
        mRating = typedArray.getInteger(R.styleable.SimpleRatingBar_rating, -1)
        mEmptyDrawable = if (typedArray.hasValue(R.styleable.SimpleRatingBar_drawable_empty)) ContextCompat.getDrawable(context, typedArray.getResourceId(R.styleable.SimpleRatingBar_drawable_empty, View.NO_ID))
        else ContextCompat.getDrawable(context, R.drawable.ic_star_border_black_24dp)
        mFilledDrawable = if (typedArray.hasValue(R.styleable.SimpleRatingBar_drawable_filled)) ContextCompat.getDrawable(context, typedArray.getResourceId(R.styleable.SimpleRatingBar_drawable_filled, View.NO_ID))
        else ContextCompat.getDrawable(context, R.drawable.ic_star_black_24dp)
        mAnimationType = typedArray.getInteger(R.styleable.SimpleRatingBar_animation, 0)
        typedArray.recycle()
        init()
    }

    private fun init() {
        layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        for (i in 0..totalStars) {
            val imageView = ImageView(context)
            imageView.setImageDrawable(mEmptyDrawable)
            imageView.layoutParams = LinearLayout.LayoutParams(100, 100)
            imageView.tag = i
            val outValue = TypedValue()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                context.theme.resolveAttribute(android.R.attr.selectableItemBackgroundBorderless, outValue, true)
            } else {
                context.theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
            }
            imageView.setBackgroundResource(outValue.resourceId)
            addView(imageView)
            imageView.setOnClickListener {
                setProgress(imageView.tag.toString().toInt())
            }
        }
        if (mRating > -1) setProgress(mRating)
    }

    private fun setProgress(position: Int) {
        for (i in 0 until position) {
            val imageView = this.getChildAt(i) as ImageView
            AnimationUtils.loadAnimation(context, android.R.anim.slide_out_right)
                .apply {
                    duration = 50
                    start()
                    imageView.setImageDrawable(mFilledDrawable)
                }
        }

        val imageView = this.getChildAt(position) as ImageView
        when (mAnimationType) {
            0 -> imageView.setImageDrawable(mFilledDrawable)
            1 -> alphaAnimation(imageView)
            2 -> rotateAnimation(imageView)
            3 -> scaleAnimation(imageView)
            4 -> ringAnimation(imageView)
            5 -> flipAnimation(imageView)
        }

        if (position == totalStars) {
            return
        }
        for (j in position + 1..totalStars) {
            val imageView = this.getChildAt(j) as ImageView
            imageView.setImageDrawable(mEmptyDrawable)
        }
    }

    private fun alphaAnimation(imageView: ImageView) {
        val fadeAnim = ObjectAnimator.ofFloat(imageView, "alpha", 1f, 0.5f, 0f)
        fadeAnim.duration = 300
        fadeAnim.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                imageView.setImageDrawable(mFilledDrawable)
                val anim = ObjectAnimator.ofFloat(imageView, "alpha", 0f, 0.5f, 1f)
                anim.duration = 300
                anim.start()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }
        })
        fadeAnim.start()
    }

    private fun rotateAnimation(imageView: ImageView) {
        ObjectAnimator.ofFloat(imageView, "rotation", 0f, 180f)
            .apply {
                duration = 300
                interpolator = AccelerateInterpolator()
                addListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(animation: Animator?) {
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        imageView.setImageDrawable(mFilledDrawable)
                        ObjectAnimator.ofFloat(imageView, "rotation", 180f, 360f)
                            .apply {
                                duration = 300
                                interpolator = DecelerateInterpolator()
                                start()
                            }
                    }

                    override fun onAnimationCancel(animation: Animator?) {
                    }

                    override fun onAnimationStart(animation: Animator?) {
                    }
                })
                start()
            }
    }

    private fun scaleAnimation(imageView: ImageView) {
        val pvhX = PropertyValuesHolder.ofFloat("scaleX", 1.3f)
        val pvhY = PropertyValuesHolder.ofFloat("scaleY", 1.3f)
        ObjectAnimator.ofPropertyValuesHolder(imageView, pvhX, pvhY)
            .apply {
                duration = 300
                interpolator = AccelerateInterpolator()
                addListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(animation: Animator?) {
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        imageView.setImageDrawable(mFilledDrawable)
                        val pvhX = PropertyValuesHolder.ofFloat("scaleX", 1f)
                        val pvhY = PropertyValuesHolder.ofFloat("scaleY", 1f)
                        ObjectAnimator.ofPropertyValuesHolder(imageView, pvhX, pvhY)
                            .apply {
                                duration = 300
                                interpolator = DecelerateInterpolator()
                                start()
                            }
                    }

                    override fun onAnimationCancel(animation: Animator?) {
                    }

                    override fun onAnimationStart(animation: Animator?) {
                    }
                })
                start()
            }
    }

    private fun ringAnimation(imageView: ImageView) {
        ObjectAnimator.ofFloat(imageView, "translationY", 0F, - 10F)
            .apply {
                duration = 300
                interpolator = AccelerateInterpolator()
                addListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(animation: Animator?) {
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        imageView.setImageDrawable(mFilledDrawable)
                        ObjectAnimator.ofFloat(imageView, "translationY", - 10F, 0F)
                            .apply {
                                duration = 300
                                interpolator = DecelerateInterpolator()
                                start()
                            }
                    }

                    override fun onAnimationCancel(animation: Animator?) {
                    }

                    override fun onAnimationStart(animation: Animator?) {
                    }
                })
                start()
            }
    }

    private fun flipAnimation(imageView: ImageView) {
        ObjectAnimator.ofFloat(imageView, "rotationY", 0F, 180F)
            .apply {
                duration = 300
                interpolator = AccelerateInterpolator()
                addListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(animation: Animator?) {
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        imageView.setImageDrawable(mFilledDrawable)
                        ObjectAnimator.ofFloat(imageView, "rotationY", 180F, 0F)
                            .apply {
                                duration = 300
                                interpolator = DecelerateInterpolator()
                                start()
                            }
                    }

                    override fun onAnimationCancel(animation: Animator?) {
                    }

                    override fun onAnimationStart(animation: Animator?) {
                    }
                })
                start()
            }
    }
}
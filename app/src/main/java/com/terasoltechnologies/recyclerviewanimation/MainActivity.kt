package com.terasoltechnologies.recyclerviewanimation

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.terasoltechnologies.recyclerviewanimation.CarouseAdapter.Companion.instance
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    var SELECTED: Int = 0
    var NEWSELECTION: Int = 3
    var NEWX: Int = 0
    var NEWY: Int = 0
    var NEWWIDTH: Int = 0

    private var carouseAdapter: CarouseAdapter? = null
    var carouselLayoutManager: CarouselLayoutManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        instance = this

        val list = ArrayList<String>()

        list.add("0")
        list.add("0")
        list.add("0")


        list.add("A")
        list.add("B")
        list.add("C")
        list.add("D")
        list.add("E")
        list.add("F")
        list.add("G")
        list.add("H")

        list.add("0")
        list.add("0")
        list.add("0")

        carouseAdapter =
            CarouseAdapter(list, applicationContext, NEWSELECTION, object : ScrollerInterface {
                override fun scrollToNewPosition() {

                    SELECTED = NEWSELECTION
                    val centerOfScreen: Int =
                        carousel.width / 2 - NEWWIDTH / 2
                    carouselLayoutManager!!.scrollToPositionWithOffset(
                        SELECTED,
                        centerOfScreen
                    )
                }

                override fun getPositionItem(item: String) {
                    Log.i("selectedName", item)
                }

            })

        carouselLayoutManager = CarouselLayoutManager(
            this.applicationContext,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        carousel.layoutManager = carouselLayoutManager
        carousel.adapter = carouseAdapter

        // scrollToNewPostion()

    }

    class CarouselLayoutManager(
        context: Context?,
        orientation: Int,
        reverseLayout: Boolean
    ) :
        LinearLayoutManager(context, orientation, reverseLayout) {
        private val mShrinkAmount = 0.25f
        private val mShrinkDistance = 0.9f
        override fun onLayoutCompleted(state: RecyclerView.State) {
            super.onLayoutCompleted(state)
            scaleChild()
        }

        override fun scrollHorizontallyBy(
            dx: Int,
            recycler: Recycler,
            state: RecyclerView.State
        ): Int {
            val orientation = orientation
            return if (orientation == RecyclerView.HORIZONTAL) {
                val scrolled = super.scrollHorizontallyBy(dx, recycler, state)
                scaleChild()
                scrolled
            } else {
                0
            }
        }

        fun scaleChild() {
            val midpoint = width / 2f
            val d0 = 0f
            val d1 = mShrinkDistance * midpoint
            val s0 = 1f
            val s1 = 1f - mShrinkAmount
            for (i in 0 until childCount) {
                val child = getChildAt(i)
                val childMidpoint =
                    (getDecoratedRight(child!!) + getDecoratedLeft(child)) / 2f
                val d =
                    Math.min(d1, Math.abs(midpoint - childMidpoint))
                val scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0)
                child.scaleX = scale
                child.scaleY = scale

            }
        }
    }
}

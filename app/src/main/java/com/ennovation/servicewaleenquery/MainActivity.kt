package com.ennovation.servicewaleenquery

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.ennovation.servicewaleenquery.Fragment.Enquries
import com.ennovation.servicewaleenquery.Fragment.EnquriesManager
import com.ennovation.servicewaleenquery.InterFace.IOnBackPressed
import com.ennovation.servicewaleenquery.Utils.YourPreference
import com.sanojpunchihewa.updatemanager.UpdateManager
import com.sanojpunchihewa.updatemanager.UpdateManagerConstant


class MainActivity : AppCompatActivity() {
    var doubleBackToExitPressedOnce = false
    var ishome: Boolean? = true
    var footer: View? = null
    var img_enquiry: ImageView? = null
    var txt_enquiry: TextView? = null
    var img_enquiryManager: ImageView? = null
    var txt_leadManager: TextView? = null
    var lead_badge: TextView? = null
    var enqueryLayout: LinearLayout? = null
    var enqueryManagerLayout: LinearLayout? = null
    var main_nav_host: FrameLayout? = null
    var mUpdateManager: UpdateManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        footer = findViewById(R.id.footer)
        enqueryLayout = findViewById(R.id.enqueryLayout)
        enqueryManagerLayout = findViewById(R.id.enqueryManagerLayout)
        img_enquiry = findViewById(R.id.img_enquiry)
        img_enquiryManager = findViewById(R.id.img_enquiryManager)
        txt_enquiry = findViewById(R.id.txt_enquiry)
        txt_leadManager = findViewById(R.id.txt_leadManager)
        lead_badge = findViewById(R.id.lead_badge)
        main_nav_host = findViewById(R.id.main_nav_host)

        mUpdateManager = UpdateManager.Builder(this).mode(UpdateManagerConstant.IMMEDIATE);
        mUpdateManager!!.start()

        enqueryLayout!!.setOnClickListener({ openEnqueryFragment() })
        enqueryManagerLayout!!.setOnClickListener({ openEnquryManagerFragment() })

    }

    override fun onResume() {
        super.onResume()
        openEnqueryFragment()

    }

    private fun openEnquryManagerFragment() {
        val args = Bundle()
        val fragment: Fragment = EnquriesManager()
        val transaction = supportFragmentManager.beginTransaction()
        fragment.arguments = args
        transaction.replace(R.id.main_nav_host, fragment)
        transaction.commit()
        ishome = false

        img_enquiry!!.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_notification_inactive))
        img_enquiryManager!!.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_forword_enquiry_activite))
        txt_enquiry!!.setTextColor(Color.parseColor("#8A8A8A"))
        txt_leadManager!!.setTextColor(Color.parseColor("#0077ff"))

        val yourPrefrence = YourPreference.getInstance(applicationContext)
        yourPrefrence.saveData("badge", "")
        if (lead_badge!!.visibility === View.VISIBLE) {
            lead_badge!!.visibility = View.GONE
        } else {
            lead_badge!!.visibility = View.GONE
        }

    }

    override fun onBackPressed() {
        if (ishome == true) {
            val fragment = supportFragmentManager.findFragmentById(R.id.main_nav_host)
            if (fragment !is IOnBackPressed || !(fragment as IOnBackPressed).onBackPressed()) {
                if (doubleBackToExitPressedOnce) {
                    finishAffinity()
                    System.exit(0)

                }
                this.doubleBackToExitPressedOnce = true
                Toast.makeText(this, "Press again to close service wale", Toast.LENGTH_SHORT).show()
                Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
            }

        } else {
            ishome = true
            val fragmenthome = Enquries()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.main_nav_host, fragmenthome)
            transaction.commitNow()

            img_enquiry!!.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_notification_active))
            img_enquiryManager!!.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_forword_enquiry_inactive))
            txt_enquiry!!.setTextColor(Color.parseColor("#0077ff"))
            txt_leadManager!!.setTextColor(Color.parseColor("#8A8A8A"))

        }
    }

    private fun openEnqueryFragment() {
        ishome = true
        val args = Bundle()
        val fragment: Fragment = Enquries()
        val transaction = supportFragmentManager.beginTransaction()
        fragment.arguments = args
        transaction.replace(R.id.main_nav_host, fragment)
        transaction.commit()

        img_enquiry!!.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_notification_active))
        img_enquiryManager!!.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_forword_enquiry_inactive))
        txt_enquiry!!.setTextColor(Color.parseColor("#0077ff"))
        txt_leadManager!!.setTextColor(Color.parseColor("#8A8A8A"))

        val yourPrefrence = YourPreference.getInstance(applicationContext)
        val badge: String = yourPrefrence.getData("badge")

        if (badge == "" || badge == "0") {
            if (lead_badge!!.visibility === View.VISIBLE) {
                lead_badge!!.visibility = View.GONE
            }
        } else {
            lead_badge!!.visibility = View.VISIBLE
            lead_badge!!.text = badge
        }
    }
}
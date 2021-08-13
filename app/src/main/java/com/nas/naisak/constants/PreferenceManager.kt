package com.nas.naisak.constants

import android.content.Context
import com.nas.naisak.R

class PreferenceManager {

    companion object{
        private val PREFSNAME = "NAISAK"

        /*************************App First Launch************************/
        fun isFirstLaunch(context: Context, isFirstLaunch: Boolean)
        {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putBoolean("is_first_launch", isFirstLaunch)
            editor.apply()
        }
        fun getIsFirstLaunch(context: Context): Boolean
        {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            return prefs.getBoolean("is_first_launch", false)
        }
        /*************************CALENDAR First Launch************************/
        fun isCalendarFirstLaunch(context: Context, isFirstLaunch: Boolean)
        {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putBoolean("is_cal_first_launch", isFirstLaunch)
            editor.apply()
        }
        fun getIsCalendarFirstLaunch(context: Context): Boolean
        {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            return prefs.getBoolean("is_cal_first_launch", false)
        }
        /*************************USER CODE************************/
        fun setUserCode(context: Context, usercode: String?) {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("user_code", usercode)
            editor.apply()
        }
        fun getUserCode(context: Context): String? {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            return prefs.getString("user_code", "")
        }
        /*************************setCalendarEventNames************************/
        fun setCalendarEventNames(context: Context, usercode: String?) {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("cal_event", usercode)
            editor.apply()
        }
        fun getCalendarEventNames(context: Context): String? {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            return prefs.getString("cal_event", "")
        }

        /*************************USER EMAIL************************/
        fun setUserEmail(context: Context, useremail: String?) {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("user_email", useremail)
            editor.apply()
        }
        fun getUserEmail(context: Context): String? {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            return prefs.getString("user_email", "")
        }


        /***************************** BADGE *****************************************/
        /*************************Calendar************************/
        fun setCalendarBadge(context: Context, calendarbadge: Int)
        {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putInt("calendar_badge", calendarbadge)
            editor.apply()
        }
        fun getCalendarBadge(context: Context): Int
        {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            return prefs.getInt("calendar_badge", 0)
        }
        fun setCalendarEditedBadge(context: Context, calendarbadge: Int)
        {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putInt("calendar_edited_badge", calendarbadge)
            editor.apply()
        }
        fun getCalendarEditedBadge(context: Context): Int
        {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            return prefs.getInt("calendar_edited_badge", 0)
        }

        /*************************Notification************************/
        fun setNotificationBadge(context: Context, calendarbadge: Int)
        {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putInt("notification_badge", calendarbadge)
            editor.apply()
        }
        fun getNotificationBadge(context: Context): Int
        {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            return prefs.getInt("notification_badge", 0)
        }
        fun setNotificationEditedBadge(context: Context, calendarbadge: Int)
        {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putInt("notification_edited_badge", calendarbadge)
            editor.apply()
        }
        fun getNotificationEditedBadge(context: Context): Int
        {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            return prefs.getInt("notification_edited_badge", 0)
        }

        /*************************Whole school coming up************************/
        fun setWholeSchoolBadge(context: Context, calendarbadge: Int)
        {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putInt("whole_school_badge", calendarbadge)
            editor.apply()
        }
        fun getWholeSchoolBadge(context: Context): Int
        {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            return prefs.getInt("whole_school_badge", 0)
        }
        fun setWholeSchoolEditedBadge(context: Context, calendarbadge: Int)
        {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putInt("whole_school_edited_badge", calendarbadge)
            editor.apply()
        }
        fun getWholeSchoolEditedBadge(context: Context): Int
        {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            return prefs.getInt("whole_school_edited_badge", 0)
        }
        /*************************Payment************************/
        fun setPaymentBadge(context: Context, calendarbadge: Int)
        {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putInt("payment_badge", calendarbadge)
            editor.apply()
        }
        fun getPaymentBadge(context: Context): Int
        {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            return prefs.getInt("payment_badge", 0)
        }
        fun setPaymentEditedBadge(context: Context, calendarbadge: Int)
        {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putInt("payment_edited_badge", calendarbadge)
            editor.apply()
        }
        fun getPaymentEditedBadge(context: Context): Int
        {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            return prefs.getInt("payment_edited_badge", 0)
        }


        /*Teal 1 settings for both reg and guest*/

        /*REG Teal 1*/
        fun setButtonOneRegTabID(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("button_one_reg_tab_id", id)
            editor.apply()
        }

        fun getButtonOneRegTabID(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_one_reg_tab_id", "1")
        }
        fun setButtonOneRegTextImage(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("button_one_reg_text_image", id)
            editor.apply()
        }

        fun getButtonOneRegTextImage(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_one_reg_text_image", "1")
        }

        /*GUEST Teal 1*/
        fun setButtonOneGuestTabID(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("button_one_guest_tab_id", id)
            editor.apply()
        }

        fun getButtonOneGuestTabID(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_one_guest_tab_id", "1")
        }
        fun setButtonOneGuestTextImage(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("button_one_guest_text_image", id)
            editor.apply()
        }

        fun getButtonOneGuestTextImage(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_one_guest_text_image", "1")
        }

        /*Teal 2 settings for both reg and guest*/

        /*REG Teal 2*/
        fun setButtonTwoRegTabID(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("button_two_reg_tab_id", id)
            editor.apply()
        }

        fun getButtonTwoRegTabID(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_two_reg_tab_id", "2")
        }
        fun setButtonTwoRegTextImage(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("button_two_reg_text_image", id)
            editor.apply()
        }

        fun getButtonTwoRegTextImage(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_two_reg_text_image", "2")
        }

        /*GUEST Teal 2*/
        fun setButtonTwoGuestTabID(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("button_two_guest_tab_id", id)
            editor.apply()
        }

        fun getButtonTwoGuestTabID(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_two_guest_tab_id", "2")
        }
        fun setButtonTwoGuestTextImage(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("button_two_guest_text_image", id)
            editor.apply()
        }

        fun getButtonTwoGuestTextImage(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_two_guest_text_image", "2")
        }


        /*Teal 3 settings for both reg and guest*/

        /*REG Teal 3*/
        fun setButtonThreeRegTabID(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("button_three_reg_tab_id", id)
            editor.apply()
        }

        fun getButtonThreeRegTabID(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_three_reg_tab_id", "3")
        }
        fun setButtonThreeRegTextImage(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("button_three_reg_text_image", id)
            editor.apply()
        }

        fun getButtonThreeRegTextImage(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_three_reg_text_image", "3")
        }

        /*GUEST Teal 3*/
        fun setButtonThreeGuestTabID(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("button_three_guest_tab_id", id)
            editor.apply()
        }

        fun getButtonThreeGuestTabID(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_three_guest_tab_id", "3")
        }
        fun setButtonThreeGuestTextImage(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("button_three_guest_text_image", id)
            editor.apply()
        }

        fun getButtonThreeGuestTextImage(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_three_guest_text_image", "3")
        }


        /*REG Teal 4*/
        fun setButtonFourRegTabID(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("button_four_reg_tab_id", id)
            editor.apply()
        }

        fun getButtonFourRegTabID(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_four_reg_tab_id", "4")
        }
        fun setButtonFourRegTextImage(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("button_four_reg_text_image", id)
            editor.apply()
        }

        fun getButtonFourRegTextImage(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_four_reg_text_image", "4")
        }

        /*GUEST Teal 3*/
        fun setButtonFourGuestTabID(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("button_four_guest_tab_id", id)
            editor.apply()
        }

        fun getButtonFourGuestTabID(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_four_guest_tab_id", "4")
        }
        fun setButtonFourGuestTextImage(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("button_four_guest_text_image", id)
            editor.apply()
        }

        fun getButtonFourGuestTextImage(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_four_guest_text_image", "4")
        }


        /*REG Teal 5*/
        fun setButtonFiveRegTabID(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("button_five_reg_tab_id", id)
            editor.apply()
        }

        fun getButtonFiveRegTabID(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_five_reg_tab_id", "5")
        }
        fun setButtonFiveRegTextImage(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("button_four_reg_text_image", id)
            editor.apply()
        }

        fun getButtonFiveRegTextImage(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_five_reg_text_image", "5")
        }

        /*GUEST Teal 3*/
        fun setButtonFiveGuestTabID(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("button_five_guest_tab_id", id)
            editor.apply()
        }

        fun getButtonFiveGuestTabID(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_five_guest_tab_id", "5")
        }
        fun setButtonFiveGuestTextImage(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("button_five_guest_text_image", id)
            editor.apply()
        }

        fun getButtonFiveGuestTextImage(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_five_guest_text_image", "5")
        }


        /*REG Teal 6*/
        fun setButtonSixRegTabID(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("button_six_reg_tab_id", id)
            editor.apply()
        }

        fun getButtonSixRegTabID(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_six_reg_tab_id", "6")
        }
        fun setButtonSixRegTextImage(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("button_six_reg_text_image", id)
            editor.apply()
        }

        fun getButtonSixRegTextImage(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_six_reg_text_image", "6")
        }

        /*GUEST Teal 3*/
        fun setButtonSixGuestTabID(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("button_six_guest_tab_id", id)
            editor.apply()
        }

        fun getButtonSixGuestTabID(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_six_guest_tab_id", "6")
        }
        fun setButtonSixGuestTextImage(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("button_six_guest_text_image", id)
            editor.apply()
        }

        fun getButtonSixGuestTextImage(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_six_guest_text_image", "6")
        }

        /*REG Teal 7*/
        fun setButtonSevenRegTabID(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("button_three_reg_tab_id", id)
            editor.apply()
        }

        fun getButtonSevenRegTabID(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_seven_reg_tab_id", "7")
        }
        fun setButtonSevenRegTextImage(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("button_seven_reg_text_image", id)
            editor.apply()
        }

        fun getButtonSevenRegTextImage(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_seven_reg_text_image", "7")
        }

        /*GUEST Teal 3*/
        fun setButtonSevenGuestTabID(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("button_seven_guest_tab_id", id)
            editor.apply()
        }

        fun getButtonSevenGuestTabID(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_seven_guest_tab_id", "7")
        }
        fun setButtonSevenGuestTextImage(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("button_seven_guest_text_image", id)
            editor.apply()
        }

        fun getButtonSevenGuestTextImage(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_seven_guest_text_image", "7")
        }


        /*REG Teal 8*/
        fun setButtonEightRegTabID(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("button_eight_reg_tab_id", id)
            editor.apply()
        }

        fun getButtonEightRegTabID(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_three_reg_tab_id", "8")
        }
        fun setButtonEightRegTextImage(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("button_eight_reg_text_image", id)
            editor.apply()
        }

        fun getButtonEightRegTextImage(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_eight_reg_text_image", "8")
        }

        /*GUEST Teal 8*/
        fun setButtonEightGuestTabID(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("button_eight_guest_tab_id", id)
            editor.apply()
        }

        fun getButtonEightGuestTabID(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_eight_guest_tab_id", "9")
        }
        fun setButtonEightGuestTextImage(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("button_eight_guest_text_image", id)
            editor.apply()
        }

        fun getButtonEightGuestTextImage(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_eight_guest_text_image", "8")
        }

        /*REG Teal 9*/
        fun setButtonNineRegTabID(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("button_nine_reg_tab_id", id)
            editor.apply()
        }

        fun getButtonNineRegTabID(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_nine_reg_tab_id", "9")
        }
        fun setButtonNineRegTextImage(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("button_nine_reg_text_image", id)
            editor.apply()
        }

        fun getButtonNineRegTextImage(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_nine_reg_text_image", "8")
        }

        /*GUEST Teal 3*/
        fun setButtonNineGuestTabID(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("button_nine_guest_tab_id", id)
            editor.apply()
        }

        fun getButtonNineGuestTabID(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_nine_guest_tab_id", "9")
        }
        fun setButtonNineGuestTextImage(context: Context, id: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("button_nine_guest_text_image", id)
            editor.apply()
        }

        fun getButtonNineGuestTextImage(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("button_nine_guest_text_image", "8")
        }

        fun setButtonOneGuestBg(context: Context, color: Int) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putInt("buttononeguestbg", color)
            editor.apply()
        }

        fun getButtonOneGuestBg(context: Context): Int {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getInt(
                "buttononeguestbg", context.resources
                    .getColor(R.color.transparent)
            )
        }

        fun setButtontwoGuestBg(context: Context, color: Int) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putInt("buttontwoguestbg", color)
            editor.apply()
        }

        fun getButtontwoGuestBg(context: Context): Int {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getInt(
                "buttontwoguestbg", context.resources
                    .getColor(R.color.transparent)
            )
        }

        fun setButtonthreeGuestBg(context: Context, color: Int) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putInt("buttonthreeguestbg", color)
            editor.apply()
        }

        fun getButtonthreeGuestBg(context: Context): Int {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getInt(
                "buttonthreeguestbg", context.resources
                    .getColor(R.color.transparent)
            )
        }

        fun setButtonfourGuestBg(context: Context, color: Int) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putInt("buttonfourguestbg", color)
            editor.apply()
        }

        fun getButtonfourGuestBg(context: Context): Int {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getInt(
                "buttonfourguestbg", context.resources
                    .getColor(R.color.transparent)
            )
        }

        fun setButtonfiveGuestBg(context: Context, color: Int) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putInt("buttonfiveguestbg", color)
            editor.apply()
        }

        fun getButtonfiveGuestBg(context: Context): Int {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getInt(
                "buttonfiveguestbg", context.resources
                    .getColor(R.color.transparent)
            )
        }

        fun setButtonsixGuestBg(context: Context, color: Int) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putInt("buttonsixguestbg", color)
            editor.apply()
        }

        fun getButtonsixGuestBg(context: Context): Int {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getInt(
                "buttonsixguestbg", context.resources
                    .getColor(R.color.transparent)
            )
        }

        fun setButtonsevenGuestBg(context: Context, color: Int) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putInt("buttonsevenguestbg", color)
            editor.apply()
        }

        fun getButtonsevenGuestBg(context: Context): Int {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getInt(
                "buttonsevenguestbg", context.resources
                    .getColor(R.color.transparent)
            )
        }

        fun setButtoneightGuestBg(context: Context, color: Int) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putInt("buttoneightguestbg", color)
            editor.apply()
        }

        fun getButtoneightGuestBg(context: Context): Int {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getInt(
                "buttoneightguestbg", context.resources
                    .getColor(R.color.transparent)
            )
        }

        fun setButtonnineGuestBg(context: Context, color: Int) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putInt("buttonnineguestbg", color)
            editor.apply()
        }

        fun getButtonnineGuestBg(context: Context): Int {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getInt(
                "buttonnineguestbg", context.resources
                    .getColor(R.color.transparent)
            )
        }

        /*Student Data*/

        /*SET STUDENT_ID*/
        fun setStudentID(context: Context, id: Int) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putInt("student_id", id)
            editor.apply()
        }

        /*GET STUDENT_ID*/
        fun getStudentID(context: Context): Int {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getInt("student_id", 0)
        }

        /*SET STUDENT_ID*/
        fun setStudentWallet(context: Context, id: Int) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putInt("student_wallet", id)
            editor.apply()
        }

        /*GET STUDENT_ID*/
        fun getStudentWallet(context: Context): Int {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getInt("student_wallet", 0)
        }

        /*SET STUDENT_NAME*/
        fun setStudentName(context: Context, name: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("student_name", name)
            editor.apply()
        }

        /*GET STUDENT_NAME*/
        fun getStudentName(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("student_name", "")
        }

        /*SET StudentPhoto*/
        fun setStudentPhoto(context: Context, name: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("student_photo", name)
            editor.apply()
        }

        /*GET STUDENT_NAME*/
        fun getStudentPhoto(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("student_photo", "")
        }
        /*SET StudentClass*/
        fun setStudentClass(context: Context, name: String?) {
            val prefs = context.getSharedPreferences(
                PREFSNAME, Context.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString("student_class", name)
            editor.apply()
        }

        /*GET STUDENT_NAME*/
        fun getStudentClass(context: Context): String? {
            val prefs = context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
            return prefs.getString("student_class", "")
        }

    }
}
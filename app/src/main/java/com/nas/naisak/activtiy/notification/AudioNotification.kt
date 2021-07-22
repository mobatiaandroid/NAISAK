package com.nas.naisak.activtiy.notification

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.webkit.WebView
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.nas.naisak.R
import com.nas.naisak.activtiy.home.HomeActivity
import com.nas.naisak.activtiy.notification.model.MessageDetailResponse
import com.nas.naisak.activtiy.notification.model.NotificationDetailApiModel
import com.nas.naisak.constants.ApiClient
import com.nas.naisak.constants.CommonMethods
import com.nas.naisak.constants.PreferenceManager
import kotlinx.android.synthetic.main.activity_audio_notification.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("UNREACHABLE_CODE")
class AudioNotification : AppCompatActivity(), Player.EventListener{
    lateinit var mContext: Context
    var id:String=""
    var title:String=""
    var idApi:String=""
    var titleApi:String=""
    var message:String=""
    var url:String=""
    var date:String=""
    private lateinit var relativeHeader: RelativeLayout
    private lateinit var logoClickImgView: ImageView
    private lateinit var btn_left: ImageView
    private lateinit var heading: TextView
    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar
    lateinit var simpleExoplayer: SimpleExoPlayer
    lateinit var playerView: PlayerView
    var playbackPosition: Long = 0
    lateinit var mediaDataSourceFactory: DataSource.Factory
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_notification)
        mContext=this
        id=intent.getStringExtra("id")
        title=intent.getStringExtra("title")
        initUI()
        if (CommonMethods.isInternetAvailable(mContext)) {
            callMessageDetailAPI()

        } else {
            CommonMethods.showSuccessInternetAlert(mContext)
        }



    }
    fun initUI() {
        relativeHeader = findViewById(R.id.relativeHeader)
        heading = findViewById(R.id.heading)
        btn_left = findViewById(R.id.btn_left)
        logoClickImgView = findViewById(R.id.logoClickImgView)
        playerView = findViewById(R.id.exoplayerView)
        progressBar = findViewById(R.id.progressBar)
        heading.setText("Notification")
        btn_left.setOnClickListener(View.OnClickListener {
            finish()
        })
        logoClickImgView.setOnClickListener(View.OnClickListener {
            val intent = Intent(mContext, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        })


    }
    fun callMessageDetailAPI()
    {
        val token = PreferenceManager.getUserCode(mContext)
        val studentbody= NotificationDetailApiModel(id)
        val call: Call<MessageDetailResponse> = ApiClient.getClient.notificationdetail(studentbody,"Bearer "+token)
        call.enqueue(object : Callback<MessageDetailResponse> {
            override fun onFailure(call: Call<MessageDetailResponse>, t: Throwable) {

                Log.e("Error", t.localizedMessage)
            }
            override fun onResponse(call: Call<MessageDetailResponse>, response: Response<MessageDetailResponse>) {

                if (response.body()!!.status==100)
                {
                    idApi=id
                    titleApi=title
                    message=response.body()!!.data.message
                    url=response.body()!!.data.url
                    date=response.body()!!.data.time_stamp
                    initializePlayer()
                }


            }

        })
    }



    private fun initializePlayer() {
        // Create a default TrackSelector
        simpleExoplayer = ExoPlayerFactory.newSimpleInstance(mContext)
        mediaDataSourceFactory =
            DefaultDataSourceFactory(mContext, Util.getUserAgent(mContext, "mediaPlayerSample"))
        val mediaSource = ProgressiveMediaSource.Factory(mediaDataSourceFactory)
            .createMediaSource(Uri.parse(url))
        simpleExoplayer.prepare(mediaSource, false, false)
        simpleExoplayer.playWhenReady = true
        playerView.setShutterBackgroundColor(Color.TRANSPARENT)
        playerView.player = simpleExoplayer
        playerView.requestFocus()
        simpleExoplayer.addListener(object : Player.EventListener {
            override fun onTracksChanged(trackGroups: TrackGroupArray, trackSelections: TrackSelectionArray) {

            }

            override fun onLoadingChanged(isLoading: Boolean) {}
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                if (playbackState == ExoPlayer.STATE_BUFFERING) {
                    progressBar.visibility = View.VISIBLE
                } else {
                    progressBar.visibility = View.INVISIBLE
                }
            }

            override fun onPlayerError(error: ExoPlaybackException) {
            }

            fun onPositionDiscontinuity() {
            }

            override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters) {

            }
        })
    }


    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) releasePlayer()
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) releasePlayer()
    }

    private fun releasePlayer() {
        simpleExoplayer.release()
    }

    override fun onBackPressed() {
        Log.e("Back","Pressed")
        if (Util.SDK_INT > 23) releasePlayer()
        super.onBackPressed()
        return
        super.onBackPressed()


    }
    override fun onResume() {
        super.onResume()
       // if (Util.SDK_INT <= 23) initializePlayer()
    }


}


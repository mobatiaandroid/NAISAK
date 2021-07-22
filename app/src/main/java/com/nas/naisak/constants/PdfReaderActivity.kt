package com.nas.naisak.constants

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import com.github.barteksc.pdfviewer.PDFView
import com.nas.naisak.R
import java.io.File

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "DEPRECATION")
class PdfReaderActivity : AppCompatActivity() {
    lateinit var back: ImageView
    lateinit var downloadpdf: ImageView
    lateinit var context: Context
    lateinit var pdfviewer: PDFView
    var urltoshow: String = ""
    var title: String = ""
    private val STORAGE_PERMISSION_CODE: Int = 1000
    lateinit var pdfprogress: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_reader)
        context = this
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        urltoshow = intent.getStringExtra("pdf_url")
        title = intent.getStringExtra("pdf_title")
        back = findViewById(R.id.back)
        downloadpdf = findViewById(R.id.downloadpdf)
        pdfviewer = findViewById(R.id.pdfview)
        pdfprogress = findViewById(R.id.pdfprogress)

        PRDownloader.initialize(applicationContext)
        val fileName = "myFile.pdf"
        downloadPdfFromInternet(
            urltoshow,
            getRootDirPath(this),
            fileName
        )
        back.setOnClickListener {
            finish()
        }
        downloadpdf.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    requestPermissions(
                        arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        STORAGE_PERMISSION_CODE
                    )
                }

                val fileWithinMyDir = File(getFilepath("$title.pdf"))

                if (fileWithinMyDir.exists()) {
                    fileWithinMyDir.delete()
                    startdownloading()
                    onDownloadComplete()
                } else {
                    startdownloading()
                    onDownloadComplete()
                }
            }
        }
    }

    fun onDownloadComplete() {
        val onComplete = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                pdfprogress.visibility = View.GONE
                Toast.makeText(context, "File downloaded", Toast.LENGTH_SHORT).show()

            }

        }
        registerReceiver(onComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }

    private fun startdownloading() {
        val request = DownloadManager.Request(Uri.parse(urltoshow))   //URL = URL to download
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        request.setTitle("Download")
        request.setDescription("The file is downloading...")
        request.allowScanningByMediaScanner()
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "$title.pdf")
        pdfprogress.visibility = View.VISIBLE
        val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(request)
    }

    private fun getFilepath(filename: String): String? {
        return File(
            Environment.getExternalStorageDirectory().absolutePath,
            "/Download/$filename"
        ).path
    }

    fun getRootDirPath(context: Context): String {
        return if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            val file: File = ContextCompat.getExternalFilesDirs(
                context.applicationContext,
                null
            )[0]
            file.absolutePath
        } else {
            context.applicationContext.filesDir.absolutePath
        }
    }

    private fun downloadPdfFromInternet(url: String, dirPath: String, fileName: String) {
        PRDownloader.download(
            url,
            dirPath,
            fileName
        ).build()
            .start(object : OnDownloadListener {
                override fun onDownloadComplete() {
                    val downloadedFile = File(dirPath, fileName)

                    showPdfFromFile(downloadedFile)
                }

                override fun onError(error: com.downloader.Error?) {

                }

            })
    }

    private fun showPdfFromFile(file: File) {
        pdfviewer.fromFile(file)
            .password(null)
            .defaultPage(0)
            .enableSwipe(true)
            .swipeHorizontal(false)
            .enableDoubletap(true)
            .onPageError { page, _ ->
            }
            .load()
        pdfprogress.visibility = View.GONE

    }
}
package com.beni.core.util

import android.content.ContentResolver
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.exifinterface.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.view.View
import android.widget.ArrayAdapter
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.beni.core.R
import com.beni.core.data.local.room.PatientEntitiy
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.UUID

object ConstantFunction {
    fun Any.getErrorMessage(): String {
        return when (this) {
            is String -> {
                this.toString()
            }

            is Map<*, *> -> {
                this.values.joinToString {
                    (it as List<*>).first().toString()
                }
            }

            else -> {
                this.toString()
            }
        }
    }

    fun String.toRequestBodyParameter(): RequestBody {
        return this.toRequestBody("text/plain".toMediaType())
    }

    fun File.toMultipartBodyParamter(name: String): MultipartBody.Part {
        val imageFile = this.asRequestBody("image/jpeg".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(
            name,
            this.name,
            imageFile
        )
    }

    fun List<File>.toListMultipartBodyParamter(name: String): List<MultipartBody.Part> {
        val images: ArrayList<MultipartBody.Part> = ArrayList()
        for (i in this) {
            val imageFile = i.asRequestBody("image/jpeg".toMediaTypeOrNull())
            images.add(
                MultipartBody.Part.createFormData(
                    name,
                    i.name,
                    imageFile
                )
            )
        }
        return images
    }

    fun Number.formatRupiah(): String {
        val rupiahsFormat = NumberFormat.getCurrencyInstance(Locale("in", "ID")).apply {
            maximumFractionDigits = 0
        }
        return rupiahsFormat.format(this)
    }

    fun Int.roundDown(): String {
        return if (this > 10) {
            "${(this - this % 10)}+"
        } else if (this > 5) {
            "${(this - this % 5)}+"
        } else {
            this.toString()
        }
    }

    fun TextInputLayout.setInputError(message: String): Boolean {
        this.isErrorEnabled = true
        this.error = message
        this.requestFocus()
        return false
    }

    private const val FILENAME_FORMAT = "dd-MMM-yyyy"

    private val timeStamp: String = SimpleDateFormat(
        FILENAME_FORMAT,
        Locale.US
    ).format(System.currentTimeMillis())

    fun createCustomTempFile(context: Context): File {
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(timeStamp, ".jpg", storageDir)
    }

    fun uriToFile(selectedImg: Uri, context: Context): File {
        val contentResolver: ContentResolver = context.contentResolver
        val myFile = createCustomTempFile(context)

        val inputStream = contentResolver.openInputStream(selectedImg) as InputStream
        val outputStream: OutputStream = FileOutputStream(myFile)
        val buf = ByteArray(1024)
        var len: Int
        while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
        outputStream.close()
        inputStream.close()

        return myFile
    }

    fun getRotatedBitmap(file: File): Bitmap? {
        val imgBitmap = BitmapFactory.decodeFile(file.path)

        val ei: ExifInterface = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ExifInterface(file)
        } else {
            ExifInterface(file.path)
        }
        val orientation = ei.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_UNDEFINED
        )

        val rotatedBitmap: Bitmap? = when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> TransformationUtils.rotateImage(
                imgBitmap,
                90
            )

            ExifInterface.ORIENTATION_ROTATE_180 -> TransformationUtils.rotateImage(
                imgBitmap,
                180
            )

            ExifInterface.ORIENTATION_ROTATE_270 -> TransformationUtils.rotateImage(
                imgBitmap,
                270
            )

            ExifInterface.ORIENTATION_NORMAL -> imgBitmap
            else -> imgBitmap
        }
        return rotatedBitmap
    }

    fun bitmapToFile(bitmap: Bitmap, context: Context): Uri {
        val wrapper = ContextWrapper(context)

        var file = wrapper.getDir("Images", Context.MODE_PRIVATE)
        file = File(file, "${UUID.randomUUID()}.jpg")

        try {
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return Uri.parse(file.absolutePath)
    }

    fun View.showSnackbar(message: String) =
        Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()

    fun List<Any?>.getArrayAdapter(context: Context) = ArrayAdapter(
        context,
        R.layout.dropdown_menu_popup,
        this
    )

    fun String.getErrorMessage(): String {
        val jObject = JSONObject(this);
        return jObject.getString("message");
    }

    fun getRandomString(length: Int): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

    fun List<PatientEntitiy>.divideIntoEachCategory(): List<Int> {
        return this.map { patient ->
            if (patient.systole < 120 && patient.diastole < 80) {
                0
            } else if (patient.systole < 129 && patient.diastole < 80) {
                1
            } else if (patient.systole < 139 || patient.diastole < 90) {
                2
            } else if (patient.systole < 180 || patient.diastole < 120) {
                3
            } else {
                4
            }
        }
    }
}
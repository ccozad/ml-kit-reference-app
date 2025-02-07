package com.charlescozad.barcodedemo.barcodescanner

import android.content.Context
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.ZoomSuggestionOptions
import com.google.mlkit.vision.barcode.ZoomSuggestionOptions.ZoomCallback
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.charlescozad.barcodedemo.camera.GraphicOverlay

/** Barcode Detector Demo. */
class UPCBarcodeScannerProcessor(context: Context, zoomCallback: ZoomCallback?) :
  VisionProcessorBase<List<Barcode>>(context) {

  private var barcodeScanner: BarcodeScanner

  init {
    barcodeScanner =
      if (zoomCallback != null) {
        val options =
          BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_UPC_A, Barcode.FORMAT_UPC_E )
            .setZoomSuggestionOptions(ZoomSuggestionOptions.Builder(zoomCallback).build())
            .build()
        BarcodeScanning.getClient(options)
      } else {
        val options =
          BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_UPC_A, Barcode.FORMAT_UPC_E )
            .build()
        BarcodeScanning.getClient(options)
      }
  }

  override fun stop() {
    super.stop()
    barcodeScanner.close()
  }

  override fun detectInImage(image: InputImage): Task<List<Barcode>> {
    return barcodeScanner.process(image)
  }

  override fun onSuccess(barcodes: List<Barcode>, graphicOverlay: GraphicOverlay) {
    if (barcodes.isEmpty()) {
      Log.v(MANUAL_TESTING_LOG, "No barcode has been detected")
    }
    for (i in barcodes.indices) {
      val barcode = barcodes[i]
      graphicOverlay.add(BarcodeGraphic(graphicOverlay, barcode))
      logExtrasForTesting(barcode)
    }
  }

  override fun onFailure(e: Exception) {
    Log.e(TAG, "Barcode detection failed $e")
  }

  companion object {
    private const val TAG = "BarcodeProcessor"

    private fun logExtrasForTesting(barcode: Barcode?) {
      if (barcode != null) {
        Log.v(
          MANUAL_TESTING_LOG,
          String.format(
            "Detected barcode's bounding box: %s",
            barcode.boundingBox!!.flattenToString()
          )
        )
        Log.v(
          MANUAL_TESTING_LOG,
          String.format("Expected corner point size is 4, get %d", barcode.cornerPoints!!.size)
        )
        for (point in barcode.cornerPoints!!) {
          Log.v(
            MANUAL_TESTING_LOG,
            String.format("Corner point is located at: x = %d, y = %d", point.x, point.y)
          )
        }
        Log.v(MANUAL_TESTING_LOG, "barcode display value: " + barcode.displayValue)
        Log.v(MANUAL_TESTING_LOG, "barcode raw value: " + barcode.rawValue)
      }
    }
  }
}

package com.charlescozad.barcodedemo.barcodescanner

import com.google.mlkit.vision.barcode.common.Barcode

interface BarcodeDetectionCallback {
    fun detected(barcodes: List<Barcode>)
}
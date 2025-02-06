# Introduction

This repo has reference implementations for using ML Kit for different tasks.

# Bar code demo

Process bar codes with the camera.

## Technical notes

### Permisions

Required permissions:
 - Camera

 ```kotlin
private fun isPermissionGranted(context: Context, permission: String): Boolean {
    if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    ) {
        Log.i(TAG, "Permission granted: $permission")
        return true
    }
    Log.i(TAG, "Permission NOT granted: $permission")
    return false
}
 ```


### LivePreviewActivity

This activity has a selection dropdown that brings in different models that act on camera previw data. There is also basic functionality to switch from rear to front facing cameras. The screen links to a settings screen.

This was part of a larger demo in the ML Kit vision section that showed many different types of models. Most of that functionality has been removed to focus in on the barcode processing alone.

Process:

1. A camera source is setup
2. A machine learning frame processor processes data coming in from the camera
3. Data processing results are sent to the live preview rendering surface

### BarcodeScannerProcessor

### VisionImageProcessor

### VisionProcessorBase



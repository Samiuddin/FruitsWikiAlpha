LOCAL_PATH := $(call my-dir)
LOCAL_C_INCLUDES += /home/sami/Android/OpenCV-2.4.9-android-sdk/sdk/native/jni/include/

include $(CLEAR_VARS)

OPENCV_LIB_TYPE:=STATIC
OPENCV_INSTALL_MODULES:=on

include /home/sami/Android/OpenCV-2.4.9-android-sdk/sdk/native/jni/OpenCV.mk

LOCAL_MODULE    := FeatLib
LOCAL_SRC_FILES := main.cpp
LOCAL_LDLIBS +=  -llog -ldl

include $(BUILD_SHARED_LIBRARY)

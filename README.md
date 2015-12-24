## AnySDK Plugin 简介

-	AnySDK Plugin作为一个第三方Cordova插件，旨在让Cordova应用能使用AnySDK的服务。它封装了AnySDK提供的Java接口，同时开放JavaScript接口给Cordova开发者调用。
-	作为一个免费的互联网服务，AnySDK为开发者提供了统一的接口来访问超过80个第三方服务，涵盖了广告、应用内置支付、统计、社交和推送等种类。
-	当前AnySDK plugin仅支持Android平台。iOS平台的支持将在后续版本中加入。
-	在您使用此插件及AnySDK服务时，可能需要受制于第三方的权利要求及符合其相应条款和条件，而非受Intel为XDK设置的条款和条件的限制。Intel对于此插件及AnySDK服务不承担责任。

## 预安装设置
1. [node.js](https://nodejs.org/).
2. Cordova-CLI:  `npm install -g cordova`.
3. plugman: `npm install -g plugman`.

## 通过Cordova CLI安装
`plugman install --platform android --project <dir-to-project>/platforms/android --plugin <url-to-AnySDK-plugin> --variable ANYSDK_APPKEY=<ANYSDK_APPKEY> --variable ANYSDK_APPSECRET=<ANYSDK_APPSECRET> --variable ANYSDK_PRIVATEKEY=<ANYSDK_PRIVATEKEY> --variable ANYSDK_OAUTHSERVER=<ANYSDK_OAUTHSERVER>`

上述命令将会安装AnySDK plugin和相关数据依赖到您的应用。

## 在Intel® XDK中安装
如欲在Intel® XDK中安装和使用AnySDK plugin, 请按照[documentation](https://software.intel.com/en-us/xdk/docs/adding-third-party-plugins-to-your-xdk-cordova-app)中"Including Third-Party Plugins"章节所描述的步骤进行操作。

**警告：** 谨记，在Intel® XDK中，到目前为止，并非所有第三方插件都能在emulator和App Preview (真机测试)中调试。这些插件都是由第三方所创建和维护。在上传和使用这些第三方插件之前，请务必浏览每一份第三方插件的文档并接受相关授权许可。

## Introduction to AnySDK Plugin
- This third party plugin allows the Cordova app to access the AnySDK service.  It wraps the AnySDK's Java APIs, and exposes JavaScript APIs to Cordova developers. 

- AnySDK is a free service which provides unified interfaces to access 80+ 3rd-party services, including Ads, IAP, Statistic, SNS and Push Notification.
- Currently the AnySDK plugin supports Android only. iOS Support will be added later.

- Your use of this plugin and the AnySDK service may be subject to the third party’s rights and their terms and conditions and not Intel’s Terms and Conditions for Intel® XDK.  Intel is not liable for this plugin and for the AnySDK service.

## Prerequisites
1. [node.js](https://nodejs.org/).
2. Cordova-CLI:  `npm install -g cordova`.
3. plugman: `npm install -g plugman`.

## Install via Cordova CLI
`plugman install --platform android --project <dir-to-project>/platforms/android --plugin <url-to-AnySDK-plugin> --variable ANYSDK_APPKEY=<ANYSDK_APPKEY> --variable ANYSDK_APPSECRET=<ANYSDK_APPSECRET> --variable ANYSDK_PRIVATEKEY=<ANYSDK_PRIVATEKEY> --variable ANYSDK_OAUTHSERVER=<ANYSDK_OAUTHSERVER>`

This will install the AnySDK plugin, and dependencies in your app.

## Install in Intel® XDK
To install the AnySDK plugin in the Intel® XDK, please follow the part "Including Third-Party Plugins" in the [documentation](https://software.intel.com/en-us/xdk/docs/adding-third-party-plugins-to-your-xdk-cordova-app). 

**Caution:** Remember in the Intel® XDK, the emulator and App Preview (on-device-testing) may not work with all 3rd-parties plugins (yet!). These plugins were created and supported by third-parties. Please be sure to read each third party plugin's documentation and accept the applicable license prior to uploading and using such third party plugins.



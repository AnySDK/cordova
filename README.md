# AnySDK Cordova Plugin

## 简介
- AnySDK-Cordova-Plugin是基于AnySDK的Java接口来实现的Cordova插件。
- 它将AnySDK Java接口封装成了JavaScript接口（接口名称和参数和Java基本一致， 系统回调封装成了JavaScript事件。Cordova应用开发者在安装了这个插件以后，就能通过JavaScript接口来调用AnySDK的服务了。
- 目前使用的AnySDK版本是AnySDK_Framework_Java1.4。
- 目前这个插件仅支持Android。后续版本会加入对IOS的支持。


## 安装
Cordova项目的创建和插件的安装基于[Cordova CLI](http://cordova.apache.org/docs/en/4.0.0/guide_cli_index.md.html#The%20Command-Line%20Interface)和[Plugman](http://cordova.apache.org/docs/en/4.0.0/plugin_ref_plugman.md.html#Using%20Plugman%20to%20Manage%20Plugins) , 下面将详细介绍整个安装流程。

在正式创建Cordova项目前：

1. 下载并安装[node.js](https://nodejs.org/).
2. 安装Cordova-CLI:  `npm install -g cordova`.
3. 安装plugman: `npm install -g plugman`.

创建Cordova项目并安装插件：

1. 创建空项目：运行`cordova create <folder-name> <package-name> <project-name>` ，其中，folder-name用来指定项目文件名；package-name用来指定包名；project-name用来指定项目名。
例如：`cordova create AnySDKCordovaPluginSample com.intel.anysdkplugin AnySDK-Cordova-Plugin-Sample`
2. 添加平台：`cordova platform add android`。目前该插件只支持Android平台。
3. 将AnySDK-Cordova-Plugin中"test"目录下的文件复制到刚才创建的Cordova项目的“www”目录下：“test”目录中保存了用于测试AnySDK插件JavaScript接口的用例。
4. Build项目：命令行cd到Cordova项目的根目录，运行`cordova build`。
5. 安装AnySDK插件: 运行`plugman install --platform android --project <dir-to-project>/platforms/android --plugin <dir-to-AnySDK-plugin> --variable ANYSDK_APPKEY=<ANYSDK_APPKEY> --variable ANYSDK_APPSECRET=<ANYSDK_APPSECRET> --variable ANYSDK_PRIVATEKEY=<ANYSDK_PRIVATEKEY> --variable ANYSDK_OAUTHSERVER=<ANYSDK_OAUTHSERVER>`, 安装插件。
例如：`plugman install --platform android --project platforms/android/ --plugin ../AnySDK-Cordova-Plugin/ --variable ANYSDK_APPKEY="aa" --variable ANYSDK_APPSECRET="bb" --variable ANYSDK_PRIVATEKEY="cc" --variable ANYSDK_OAUTHSERVER="dd"`

## 测试
将刚才创建的项目导入Eclipse中，就可以在模拟器或者Android设备上运行并测试AnySDK的功能。上面的安装步骤3已经把自动化测试工程和AnySDK接口的使用范例安装到你的项目中。可以参考这些范例，来学习如何调用AnySDK的JavaScript接口。

## 安装AnySDK插件后的Cordova安卓应用Sample
https://bitbucket.org/wenxizhu/anysdk-cordova-plugin-sample

## Introduction 
This plugin is for Cordova app to access the AnySDK service. It wraps the AnySDK's Java APIs, and exposes JavaScript APIs to Cordova developers.
AnySDK is a free service which provides unified interfaces to access 80+ 3rd-party servcies, including Ads, IAP, Statistic, SNS and Push Notification.



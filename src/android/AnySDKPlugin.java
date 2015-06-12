package com.anysdk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;

import com.anysdk.framework.AdsWrapper;
import com.anysdk.framework.IAPWrapper;
import com.anysdk.framework.PluginWrapper;
import com.anysdk.framework.PushWrapper;
import com.anysdk.framework.SocialWrapper;
import com.anysdk.framework.UserWrapper;
import com.anysdk.framework.java.AnySDK;
import com.anysdk.framework.java.AnySDKAds;
import com.anysdk.framework.java.AnySDKAnalytics;
import com.anysdk.framework.java.AnySDKIAP;
import com.anysdk.framework.java.AnySDKListener;
import com.anysdk.framework.java.AnySDKParam;
import com.anysdk.framework.java.AnySDKPush;
import com.anysdk.framework.java.AnySDKShare;
import com.anysdk.framework.java.AnySDKSocial;
import com.anysdk.framework.java.AnySDKUser;
import com.anysdk.framework.java.ToolBarPlaceEnum;

public class AnySDKPlugin extends CordovaPlugin {

  private String TAG = "AnySDK-Cordova-Plugin";

  private Activity activity;

  private CordovaWebView webView;

  private AnySDKUser userInstance;

  private boolean isAppForeground = true;

  private CallbackContext loginCallbackContext = null;

  private CallbackContext logoutCallbackContext = null;

  /*
   * Constructor
   */
  public AnySDKPlugin() {

  }

  @Override
  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    Log.d(TAG, "initialize");
    super.initialize(cordova, webView);
    this.activity = cordova.getActivity();
    this.webView = webView;

    // Register the onActivityResult callback.
    cordova.setActivityResultCallback(this);

    // Will be used in registerActivityLifecycleCallbacks().
    final Activity cordovaActivity = this.activity;
    final Application application = this.activity.getApplication();

    ApplicationInfo info = null;
    try {
      info = application.getPackageManager().getApplicationInfo(
          application.getPackageName(), PackageManager.GET_META_DATA);
    } catch (NameNotFoundException e) {
      Log.d(TAG, e.getMessage());
      return;
    }

    if (info != null) {
      // appKey, appSecret, privateKey,
      // oauthServer这些用户指定的参数将伴随插件的安装，被存贮在AndroidManifest.xml的<meta-data>标签里。
      // 在这里把它们读取出来，作为initPluginSystem()函数的参数。
      String appKey = info.metaData.getString("com.anysdk.appkey");
      String appSecret = info.metaData.getString("com.anysdk.appsecret");
      String privateKey = info.metaData
          .getString("com.anysdk.privatekey");
      String oauthServer = info.metaData
          .getString("com.anysdk.oauthserver");

      AnySDK.getInstance().initPluginSystem(cordovaActivity, appKey,
          appSecret, privateKey, oauthServer);

      userInstance = AnySDKUser.getInstance();

      /*
       * 所有的系统回调都将被封装成JavaScript事件，用户可以监听这些事件并添加相应回调。
       */

      // 用户系统回调：
      userInstance.setListener(new AnySDKListener() {

        @Override
        public void onCallBack(int code, String msg) {

          switch (code) {
          case UserWrapper.ACTION_RET_INIT_SUCCESS:// 初始化SDK成功回调
            fireJSEvent("anysdk.init.success", "init SDK sucess");
            break;
          case UserWrapper.ACTION_RET_INIT_FAIL:// 初始化SDK失败回调
            fireJSEvent("anysdk.init.fail", "init SDK fail");
            break;
          case UserWrapper.ACTION_RET_LOGIN_SUCCESS:// 登陆成功回调
            fireJSEvent("anysdk.login.success", "login success");
            loginCallbackContext.success("login success");
            break;
          case UserWrapper.ACTION_RET_LOGIN_TIMEOUT:// 登陆超时回调
            fireJSEvent("anysdk.login.timeout", "login timeout");
            loginCallbackContext.error("login timeout");
            break;
          case UserWrapper.ACTION_RET_LOGIN_NO_NEED:// 无需登陆回调
            fireJSEvent("anysdk.login.noneed", "login no need");
            loginCallbackContext.success("login no need");
            break;
          case UserWrapper.ACTION_RET_LOGIN_CANCEL:// 登陆取消回调
            fireJSEvent("anysdk.login.cancel", "login cancel");
            loginCallbackContext.error("login cancel");
            break;
          case UserWrapper.ACTION_RET_LOGIN_FAIL:// 登陆失败回调
            fireJSEvent("anysdk.login.fail", "login fail");
            loginCallbackContext.success("login fail");
            break;
          case UserWrapper.ACTION_RET_LOGOUT_SUCCESS:// 登出成功回调
            fireJSEvent("anysdk.logout.success", "logout success");
            logoutCallbackContext.success("logout success");
            break;
          case UserWrapper.ACTION_RET_LOGOUT_FAIL:// 登出失败回调
            fireJSEvent("anysdk.logout.fail", "logout fail");
            logoutCallbackContext.error("logout fail");
            break;
          case UserWrapper.ACTION_RET_PLATFORM_ENTER:// 平台中心进入回调
            fireJSEvent("anysdk.platform.enter", "enter platform");
            break;
          case UserWrapper.ACTION_RET_PLATFORM_BACK:// 平台中心退出回调
            fireJSEvent("anysdk.platform.back", "quit platform");
            break;
          case UserWrapper.ACTION_RET_PAUSE_PAGE:// 暂停界面回调
            fireJSEvent("anysdk.pausePage", "pause page");
            break;
          case UserWrapper.ACTION_RET_EXIT_PAGE:// 退出游戏回调
            fireJSEvent("anysdk.exitPage", "quit game");
            break;
          case UserWrapper.ACTION_RET_ANTIADDICTIONQUERY:// 防沉迷查询回调
            fireJSEvent("anysdk.antiAddictionQuery",
                "anti-addiction query");
            break;
          case UserWrapper.ACTION_RET_REALNAMEREGISTER:// 实名注册回调
            fireJSEvent("anysdk.realnameRegister",
                "realname register");
            break;
          case UserWrapper.ACTION_RET_ACCOUNTSWITCH_SUCCESS:// 切换账号成功回调
            fireJSEvent("anysdk.accountSwitch.success",
                "account switch success");
            break;
          case UserWrapper.ACTION_RET_ACCOUNTSWITCH_FAIL:// 切换账号失败回调
            fireJSEvent("anysdk.accountSwitch.fail",
                "account switch fail");
            break;
          case UserWrapper.ACTION_RET_OPENSHOP:// 应用汇特有回调，接受到该回调调出游戏商店界面
            fireJSEvent("anysdk.openShop", "open shop");
            break;
          default:
            break;
          }
        }
      });

      // 支付系统回调：
      AnySDKIAP.getInstance().setListener(new AnySDKListener() {
        @Override
        public void onCallBack(int arg0, String arg1) {
          switch (arg0) {
          case IAPWrapper.PAYRESULT_SUCCESS:// 支付成功回调
            fireJSEvent("anysdk.payment.success", "payment success");
            break;
          case IAPWrapper.PAYRESULT_FAIL:// 支付失败回调
            fireJSEvent("anysdk.payment.fail", "payment fail");
            break;
          case IAPWrapper.PAYRESULT_CANCEL:// 支付取消回调
            fireJSEvent("anysdk.payment.cancel", "payment cancel");
            break;
          case IAPWrapper.PAYRESULT_NETWORK_ERROR:// 支付超时回调
            fireJSEvent("anysdk.payment.networkError",
                "payment timeout");
            break;
          case IAPWrapper.PAYRESULT_PRODUCTIONINFOR_INCOMPLETE:// 支付信息提供不完全回调
            fireJSEvent("anysdk.payment.productInfoIncomplete",
                "production information incomplete");
            break;
          /**
           * 新增加:正在进行中回调 支付过程中若SDK没有回调结果，就认为支付正在进行中
           * 游戏开发商可让玩家去判断是否需要等待，若不等待则进行下一次的支付
           */
          case IAPWrapper.PAYRESULT_NOW_PAYING:
            fireJSEvent("anysdk.payment.pendingPayment",
                "pending payment");
            break;
          default:
            break;
          }
        }
      });

      // 广告系统回调：
      AnySDKAds.getInstance().setListener(new AnySDKListener() {
        @Override
        public void onCallBack(int arg0, String arg1) {
          Log.d(String.valueOf(arg0), arg1);
          switch (arg0) {
          case AdsWrapper.RESULT_CODE_AdsDismissed:// 广告消失回调
            fireJSEvent("anysdk.ads.dismiss", "Ads dismissed");
            break;
          case AdsWrapper.RESULT_CODE_AdsReceived:// 接受到网络回调
            fireJSEvent("anysdk.ads.receive", "Ads received");
            break;
          case AdsWrapper.RESULT_CODE_AdsShown:// 显示网络回调
            fireJSEvent("anysdk.ads.show", "Ads shown");
            break;
          case AdsWrapper.RESULT_CODE_PointsSpendFailed:// 积分墙消费失败
            fireJSEvent("anysdk.ads.pointsSpendFailed",
                "points spend fail");
            break;
          case AdsWrapper.RESULT_CODE_PointsSpendSucceed:// 积分墙消费成功
            fireJSEvent("anysdk.ads.pointsSpendSucceed",
                "points speed succeed");
            break;
          case AdsWrapper.RESULT_CODE_OfferWallOnPointsChanged:// 积分墙积分改变
            fireJSEvent("anysdk.ads.offerWallPointsChanged",
                "offerwall points changed");
            break;
          case AdsWrapper.RESULT_CODE_NetworkError:// 网络出错
            fireJSEvent("anysdk.ads.networkError", "networkError");
            break;
          default:
            break;
          }
        }
      });

      // 推送系统回调：
      AnySDKPush.getInstance().setListener(new AnySDKListener() {
        @Override
        public void onCallBack(int arg0, String arg1) {
          Log.d(String.valueOf(arg0), arg1);
          switch (arg0) {
          case PushWrapper.ACTION_RET_RECEIVEMESSAGE:
            fireJSEvent("anysdk.push.receiveMessage",
                "receive message");
            break;
          default:
            break;
          }
        }
      });

      // 社交系统回调：
      AnySDKSocial.getInstance().setListener(new AnySDKListener() {
        @Override
        public void onCallBack(int arg0, String arg1) {
          Log.d(String.valueOf(arg0), arg1);
          switch (arg0) {
          case SocialWrapper.SOCIAL_SIGNIN_FAIL:// 社交登陆失败
            fireJSEvent("anysdk.social.signin.fail",
                "social signin fail");
            break;
          case SocialWrapper.SOCIAL_SIGNIN_SUCCEED:// 社交登陆成功
            fireJSEvent("anysdk.social.signin.success",
                "social signin success");
            break;
          case SocialWrapper.SOCIAL_SIGNOUT_FAIL:// 社交登出失败
            fireJSEvent("anysdk.social.signout.fail",
                "social signout fail");
            break;
          case SocialWrapper.SOCIAL_SIGNOUT_SUCCEED:// 社交登出成功
            fireJSEvent("anysdk.social.signout.success",
                "social signout success");
            break;
          case SocialWrapper.SOCIAL_SUBMITSCORE_FAIL:// 提交分数失败
            fireJSEvent("anysdk.social.submitScore.fail",
                "social submit score fail");
            break;
          case SocialWrapper.SOCIAL_SUBMITSCORE_SUCCEED:// 提交分数成功
            fireJSEvent("anysdk.social.submitScore.success",
                "social submit score success");
            break;
          default:
            break;
          }
        }
      });

    } else {
      Log.d(TAG, "Fail to read Androidmanifest.xml");
    }

    // 监听Activity的onStart, onStop, onPause,
    // onResume事件，并在这些事件触发时，调用相应的AnySDK统计API
    this.activity.getApplication().registerActivityLifecycleCallbacks(
        new ActivityLifecycleCallbacks() {

          @Override
          public void onActivityCreated(Activity activity,
              Bundle savedInstanceState) {

          }

          @Override
          public void onActivityStarted(Activity activity) {
            AnySDKAnalytics.getInstance().startSession();

            // Hack here. Cordova plugin does not provide
            // "onRestart()" handle.
            PluginWrapper.onRestart();
          }

          @Override
          public void onActivityResumed(Activity activity) {
            PluginWrapper.onResume();
            if (!isAppForeground) {
              AnySDKUser.getInstance().callFunction("pause");
              isAppForeground = true;
            }
          }

          @Override
          public void onActivityPaused(Activity activity) {
            PluginWrapper.onPause();

          }

          @Override
          public void onActivityStopped(Activity activity) {
            AnySDKAnalytics.getInstance().stopSession();
            if (!isAppOnForeground()) {
              isAppForeground = false;
            }
          }

          @Override
          public void onActivitySaveInstanceState(Activity activity,
              Bundle outState) {
            // TODO Auto-generated method stub

          }

          @Override
          public void onActivityDestroyed(Activity activity) {
            PluginWrapper.onDestroy();
            AnySDK.getInstance().release();
          }
        });
  }

  @Override
  public boolean execute(String action, JSONArray args,
      CallbackContext callbackContext) throws JSONException {
    if (action.equals("login")) {
      String serverId = args.getString(0);
      loginCallbackContext = callbackContext;
      if (serverId == null) {
        login();
      } else {
        login(serverId);
      }

      return true;

    } else if (action.equals("logout")) {
      logoutCallbackContext = callbackContext;

      logout();

      return true;

    } else if (action.equals("isLogined")) {
      boolean isLogined = isLogined();

      if (isLogined) {
        callbackContext.success("is logined.");
      } else {
        callbackContext.error("not logined.");
      }

      return true;

    } else if (action.equals("getUserID")) {
      String userID = getUserID();

      if (userID != null && !userID.equals("")) {
        callbackContext.success(userID);
      } else {
        callbackContext.error("fail to get the user ID.");
      }

      return true;
    } else if (action.equals("enterPlatform")) {
      enterPlatform();
      return true;
    } else if (action.equals("showToolBar")) {
      String position = args.getString(0);

      showToolBar(position);

      return true;
    } else if (action.equals("hideToolBar")) {
      hideToolBar();
      return true;
    } else if (action.equals("accountSwitch")) {
      accountSwitch();
      return true;
    } else if (action.equals("exit")) {
      exit();
      return true;
    } else if (action.equals("realNameRegister")) {
      realNameRegister();
      return true;
    } else if (action.equals("antiAddictionQuery")) {
      antiAddictionQuery();
      return true;
    } else if (action.equals("submitLoginGameRole")) {
      Map<String, String> data = getMap(args, 0);

      submitLoginGameRole(data);

      return true;
    } else if (action.equals("payForProduct")) {
      int index = args.getInt(0);

      Map<String, String> data = getMap(args, 1);
      payForProduct(index, data);

      return true;
    } else if (action.equals("getOrderId")) {
      String pluginId = args.getString(0);

      String orderId = getOrderId(pluginId);

      if (orderId != null) {
        callbackContext.success(orderId);
      } else {
        callbackContext.error("Fail to get order id.");
      }

      return true;
    } else if (action.equals("logEvent")) {
      String eventId = args.getString(0);
      Map<String, String> paramMap = getMap(args, 1);

      logEvent(eventId, paramMap);

      return true;
    } else if (action.equals("setSessionContinueMillis")) {
      int millis = args.getInt(0);

      setSessionContinueMillis(millis);

      return true;
    } else if (action.equals("setCaptureUncaughtException")) {
      boolean captureOn = args.getBoolean(0);

      setCaptureUncaughtException(captureOn);

      return true;

    } else if (action.equals("logError")) {
      String errorId = args.getString(0);
      String message = args.getString(1);

      logError(errorId, message);

      return true;
    } else if (action.equals("setAccount")) {
      Map<String, String> data = getMap(args, 0);

      setAccount(data);

      return true;
    } else if (action.equals("onChargeRequest")) {
      Map<String, String> data = getMap(args, 0);

      onChargeRequest(data);

      return true;
    } else if (action.equals("onChargeRequest")) {
      Map<String, String> data = getMap(args, 0);

      onChargeRequest(data);

      return true;
    } else if (action.equals("onChargeSuccess")) {
      String orderId = args.getString(0);

      onChargeSuccess(orderId);

      return true;
    } else if (action.equals("onChargeFail")) {
      Map<String, String> data = getMap(args, 0);

      onChargeFail(data);

      return true;
    } else if (action.equals("onChargeOnlySuccess")) {
      Map<String, String> data = getMap(args, 0);

      onChargeOnlySuccess(data);

      return true;
    } else if (action.equals("onPurchase")) {
      Map<String, String> data = getMap(args, 0);

      onPurchase(data);

      return true;
    } else if (action.equals("onUse")) {
      Map<String, String> data = getMap(args, 0);

      onUse(data);

      return true;
    } else if (action.equals("onReward")) {
      Map<String, String> data = getMap(args, 0);

      onReward(data);

      return true;
    } else if (action.equals("startLevel")) {
      Map<String, String> data = getMap(args, 0);

      startLevel(data);

      return true;
    } else if (action.equals("finishLevel")) {
      String levelId = args.getString(0);

      finishLevel(levelId);

      return true;
    } else if (action.equals("failLevel")) {
      Map<String, String> data = getMap(args, 0);

      failLevel(data);

      return true;
    } else if (action.equals("startTask")) {
      Map<String, String> data = getMap(args, 0);

      startTask(data);

      return true;
    } else if (action.equals("finishTask")) {
      String taskId = args.getString(0);

      finishTask(taskId);

      return true;
    } else if (action.equals("failTask")) {
      Map<String, String> data = getMap(args, 0);

      failTask(data);

      return true;
    } else if (action.equals("share")) {
      Map<String, String> data = getMap(args, 0);

      share(data);

      return true;
    } else if (action.equals("isAdTypeSupported")) {
      String type = args.getString(0);

      isAdTypeSupported(type, callbackContext);

      return true;
    } else if (action.equals("preloadAds")) {
      String type = args.getString(0);

      preloadAds(type);

      return true;
    } else if (action.equals("showAds")) {
      String type = args.getString(0);

      showAds(type);

      return true;
    } else if (action.equals("hideAds")) {
      String type = args.getString(0);

      hideAds(type);

      return true;
    } else if (action.equals("queryPoints")) {
      queryPoints(callbackContext);
      return true;
    } else if (action.equals("spendPoints")) {
      int points = args.getInt(0);

      spendPoints(points);

      return true;
    } else if (action.equals("startPush")) {
      startPush();
      return true;
    } else if (action.equals("closePush")) {
      closePush();
      return true;
    } else if (action.equals("setAlias")) {
      String alias = args.getString(0);

      setAlias(alias);

      return true;
    } else if (action.equals("delAlias")) {
      String alias = args.getString(0);

      delAlias(alias);

      return true;
    } else if (action.equals("setTags")) {
      ArrayList<String> tags = getArrayList(args, 0);

      setTags(tags);

      return true;
    } else if (action.equals("delTags")) {
      ArrayList<String> tags = getArrayList(args, 0);

      delTags(tags);

      return true;
    } else if (action.equals("submitScore")) {
      String leadboardID = args.getString(0);
      long score = args.getLong(1);

      submitScore(leadboardID, score);

      return true;
    } else if (action.equals("showLeaderboard")) {
      String leaderboardID = args.getString(0);

      showLeaderboard(leaderboardID);

      return true;
    } else if (action.equals("unlockAchievement")) {
      Map<String, String> info = getMap(args, 0);

      unlockAchievement(info);

      return true;
    } else if (action.equals("showAchievements")) {
      showAchievements();
      return true;
    }

    return false;
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    PluginWrapper.onActivityResult(requestCode, resultCode, data);
  }

  @Override
  public void onNewIntent(Intent intent) {
    PluginWrapper.onNewIntent(intent);
    super.onNewIntent(intent);
  }

  private void login() {
    setupUserInstance();
    userInstance.login();
  }

  private void login(String serverId) {
    setupUserInstance();
    userInstance.login(serverId);
  }

  private void logout() {
    if (AnySDKUser.getInstance().isFunctionSupported("logout")) {
      AnySDKUser.getInstance().callFunction("logout");
    }
  }

  private void setupUserInstance() {
    if (userInstance == null) {
      userInstance = AnySDKUser.getInstance();
    }
  }

  private boolean isLogined() {
    setupUserInstance();
    return userInstance.isLogined();
  }

  private String getUserID() {
    setupUserInstance();
    return userInstance.getUserID();
  }

  private void enterPlatform() {
    setupUserInstance();
    if (AnySDKUser.getInstance().isFunctionSupported("enterPlatform")) {
      AnySDKUser.getInstance().callFunction("enterPlatform");
    }
  }

  private void showToolBar(String position) {
    position = position.toUpperCase();
    ToolBarPlaceEnum place = null;

    if (position.equals("TOPLEFT")) {
      place = ToolBarPlaceEnum.kToolBarTopLeft;
    } else if (position.equals("TOPRIGHT")) {
      place = ToolBarPlaceEnum.kToolBarTopRight;
    } else if (position.equals("TOPLEFTMID")) {
      place = ToolBarPlaceEnum.kToolBarTopLeftMid;
    } else if (position.equals("TOPRIGHTMID")) {
      place = ToolBarPlaceEnum.kToolBarTopRightMid;
    } else if (position.equals("TOPBOTTOMLEFT")) {
      place = ToolBarPlaceEnum.kToolBarTopBottomLeft;
    } else if (position.equals("TOPBOTTOMRIGHT")) {
      place = ToolBarPlaceEnum.kToolBarTopBottomRight;
    } else {
      place = ToolBarPlaceEnum.kUnkown;
    }

    AnySDKParam param = new AnySDKParam(place.getPlace());
    setupUserInstance();

    AnySDKUser.getInstance().callFunction("showToolBar", param);
  }

  private void hideToolBar() {
    if (AnySDKUser.getInstance().isFunctionSupported("hideToolBar")) {
      AnySDKUser.getInstance().callFunction("hideToolBar");
    }
  }

  private void accountSwitch() {
    if (AnySDKUser.getInstance().isFunctionSupported("accountSwitch")) {
      AnySDKUser.getInstance().callFunction("accountSwitch");
    }
  }

  private void exit() {
    if (AnySDKUser.getInstance().isFunctionSupported("exit")) {
      AnySDKUser.getInstance().callFunction("exit");
    }
  }

  private void realNameRegister() {
    if (AnySDKUser.getInstance().isFunctionSupported("realNameRegister")) {
      AnySDKUser.getInstance().callFunction("realNameRegister");
    }
  }

  private void antiAddictionQuery() {
    if (AnySDKUser.getInstance().isFunctionSupported("antiAddictionQuery")) {
      AnySDKUser.getInstance().callFunction("antiAddictionQuery");
    }
  }

  private void submitLoginGameRole(Map<String, String> data) {
    AnySDKParam param = new AnySDKParam(data);
    AnySDKUser.getInstance().callFunction("submitLoginGameRole", param);
  }

  private void payForProduct(int index, Map<String, String> data) {
    ArrayList<String> idArrayList = AnySDKIAP.getInstance().getPluginId();

    if (index < 0 || index > idArrayList.size() - 1) {
      Log.d(TAG, "Invalid index");
      return;
    }

    String pluginId = idArrayList.get(0);

    AnySDKIAP.getInstance().payForProduct(pluginId, data);
  }

  private String getOrderId(String pluginId) {
    return AnySDKIAP.getInstance().getOrderId(pluginId);
  }

  private void logEvent(String eventId, Map<String, String> paramMap) {
    if (paramMap.isEmpty()) {
      AnySDKAnalytics.getInstance().logEvent(eventId);
    } else {
      AnySDKAnalytics.getInstance().logEvent(eventId, paramMap);
    }
  }

  private void setSessionContinueMillis(int millis) {
    AnySDKAnalytics.getInstance().setSessionContinueMillis(millis);
  }

  private void setCaptureUncaughtException(boolean captureOn) {
    AnySDKAnalytics.getInstance().setCaptureUncaughtException(captureOn);
  }

  private void logError(String errorId, String message) {
    AnySDKAnalytics.getInstance().logError(errorId, message);
  }

  private void setAccount(Map<String, String> paramMap) {
    AnySDKParam param = new AnySDKParam(paramMap);
    AnySDKAnalytics.getInstance().callFunction("setAccount", param);
  }

  private void onChargeRequest(Map<String, String> paramMap) {
    AnySDKParam param = new AnySDKParam(paramMap);
    AnySDKAnalytics.getInstance().callFunction("onChargeRequest", param);
  }

  private void onChargeSuccess(String orderId) {
    AnySDKParam param = new AnySDKParam(orderId);
    AnySDKAnalytics.getInstance().callFunction("onChargeSuccess", param);
  }

  private void onChargeFail(Map<String, String> paramMap) {
    AnySDKParam param = new AnySDKParam(paramMap);
    AnySDKAnalytics.getInstance().callFunction("onChargeFail", param);
  }

  private void onChargeOnlySuccess(Map<String, String> paramMap) {
    AnySDKParam param = new AnySDKParam(paramMap);
    AnySDKAnalytics.getInstance()
        .callFunction("onChargeOnlySuccess", param);
  }

  private void onPurchase(Map<String, String> paramMap) {
    AnySDKParam param = new AnySDKParam(paramMap);
    AnySDKAnalytics.getInstance().callFunction("onPurchase", param);
  }

  private void onUse(Map<String, String> paramMap) {
    AnySDKParam param = new AnySDKParam(paramMap);
    AnySDKAnalytics.getInstance().callFunction("onUse", param);
  }

  private void onReward(Map<String, String> map) {
    AnySDKParam param = new AnySDKParam(map);
    AnySDKAnalytics.getInstance().callFunction("onReward", param);
  }

  private void startLevel(Map<String, String> map) {
    AnySDKParam param = new AnySDKParam(map);
    AnySDKAnalytics.getInstance().callFunction("startLevel", param);
  }

  private void finishLevel(String levelID) {
    AnySDKParam param = new AnySDKParam(levelID);
    AnySDKAnalytics.getInstance().callFunction("finishLevel", param);
  }

  private void failLevel(Map<String, String> map) {
    AnySDKParam param = new AnySDKParam(map);
    AnySDKAnalytics.getInstance().callFunction("failLevel", param);
  }

  private void startTask(Map<String, String> map) {
    AnySDKParam param = new AnySDKParam(map);
    AnySDKAnalytics.getInstance().callFunction("startTask", param);
  }

  private void finishTask(String taskID) {
    AnySDKParam param = new AnySDKParam(taskID);
    AnySDKAnalytics.getInstance().callFunction("finishTask", param);
  }

  private void failTask(Map<String, String> map) {
    AnySDKParam param = new AnySDKParam(map);
    AnySDKAnalytics.getInstance().callFunction("failTask", param);
  }

  private void share(Map<String, String> data) {
    AnySDKShare.getInstance().share(data);
  }

  private void isAdTypeSupported(String type, CallbackContext callbackContext) {
    type = type.toUpperCase();
    AnySDKAds ads = AnySDKAds.getInstance();
    int adType = AdsWrapper.AD_TYPE_BANNER;

    if (type.equals("place = ToolBarPlaceEnum.kUnkown;")) {
      adType = AdsWrapper.AD_TYPE_BANNER;
    } else if (type.equals("AD_TYPE_FULLSCREEN")) {
      adType = AdsWrapper.AD_TYPE_FULLSCREEN;
    } else if (type.equals("AD_TYPE_MOREAPP")) {
      adType = AdsWrapper.AD_TYPE_MOREAPP;
    } else if (type.equals("AD_TYPE_OFFERWALL")) {
      adType = AdsWrapper.AD_TYPE_OFFERWALL;
    } else {
      adType = AdsWrapper.AD_TYPE_BANNER;
    }

    if (ads.isAdTypeSupported(adType)) {
      callbackContext.success();
    } else {
      callbackContext.error("not a valid ad type.");
      Log.d(TAG, "not a valid ad type.");
    }
  }

  private void preloadAds(String type) {
    type = type.toUpperCase();
    AnySDKAds ads = AnySDKAds.getInstance();
    int adType = AdsWrapper.AD_TYPE_BANNER;

    if (type.equals("place = ToolBarPlaceEnum.kUnkown;")) {
      adType = AdsWrapper.AD_TYPE_BANNER;
    } else if (type.equals("AD_TYPE_FULLSCREEN")) {
      adType = AdsWrapper.AD_TYPE_FULLSCREEN;
    } else if (type.equals("AD_TYPE_MOREAPP")) {
      adType = AdsWrapper.AD_TYPE_MOREAPP;
    } else if (type.equals("AD_TYPE_OFFERWALL")) {
      adType = AdsWrapper.AD_TYPE_OFFERWALL;
    } else {
      adType = AdsWrapper.AD_TYPE_BANNER;
    }

    if (ads.isAdTypeSupported(adType)) {
      ads.preloadAds(adType);
    } else {
      Log.d(TAG, "not a valid ad type.");
    }
  }

  private void showAds(String type) {
    type = type.toUpperCase();
    AnySDKAds ads = AnySDKAds.getInstance();
    int adType = AdsWrapper.AD_TYPE_BANNER;

    if (type.equals("place = ToolBarPlaceEnum.kUnkown;")) {
      adType = AdsWrapper.AD_TYPE_BANNER;
    } else if (type.equals("AD_TYPE_FULLSCREEN")) {
      adType = AdsWrapper.AD_TYPE_FULLSCREEN;
    } else if (type.equals("AD_TYPE_MOREAPP")) {
      adType = AdsWrapper.AD_TYPE_MOREAPP;
    } else if (type.equals("AD_TYPE_OFFERWALL")) {
      adType = AdsWrapper.AD_TYPE_OFFERWALL;
    } else {
      adType = AdsWrapper.AD_TYPE_BANNER;
    }

    if (ads.isAdTypeSupported(adType)) {
      ads.showAds(adType);
    }
  }

  private void hideAds(String type) {
    type = type.toUpperCase();
    AnySDKAds ads = AnySDKAds.getInstance();
    int adType = AdsWrapper.AD_TYPE_BANNER;

    if (type.equals("place = ToolBarPlaceEnum.kUnkown;")) {
      adType = AdsWrapper.AD_TYPE_BANNER;
    } else if (type.equals("AD_TYPE_FULLSCREEN")) {
      adType = AdsWrapper.AD_TYPE_FULLSCREEN;
    } else if (type.equals("AD_TYPE_MOREAPP")) {
      adType = AdsWrapper.AD_TYPE_MOREAPP;
    } else if (type.equals("AD_TYPE_OFFERWALL")) {
      adType = AdsWrapper.AD_TYPE_OFFERWALL;
    } else {
      adType = AdsWrapper.AD_TYPE_BANNER;
    }

    if (ads.isAdTypeSupported(adType)) {
      ads.hideAds(adType);
    }
  }

  private void queryPoints(CallbackContext callbackContext) {
    float points = AnySDKAds.getInstance().queryPoints();
    callbackContext.success(String.valueOf(points));
  }

  private void spendPoints(int points) {
    AnySDKAds.getInstance().spendPoints(points);
  }

  private void startPush() {
    AnySDKPush.getInstance().startPush();
  }

  private void closePush() {
    AnySDKPush.getInstance().closePush();
  }

  private void setAlias(String alias) {
    AnySDKPush.getInstance().setAlias(alias);
  }

  private void delAlias(String alias) {
    AnySDKPush.getInstance().delAlias(alias);
  }

  private void setTags(ArrayList<String> tags) {
    AnySDKPush.getInstance().setTags(tags);
  }

  private void delTags(ArrayList<String> tags) {
    AnySDKPush.getInstance().delTags(tags);
  }

  private void submitScore(String leadboardID, long score) {
    AnySDKSocial.getInstance().submitScore(leadboardID, score);
  }

  private void showLeaderboard(String leaderboardID) {
    AnySDKSocial.getInstance().showLeaderboard(leaderboardID);
  }

  private void unlockAchievement(Map<String, String> info) {
    AnySDKSocial.getInstance().unlockAchievement(info);
  }

  private void showAchievements() {
    AnySDKSocial.getInstance().showAchievements();
  }

  // Helper functions:

  private boolean isAppOnForeground() {
    ActivityManager activityManager = (ActivityManager) this.activity
        .getApplicationContext().getSystemService(
            Context.ACTIVITY_SERVICE);
    String packageName = this.activity.getApplicationContext()
        .getPackageName();
    List<RunningAppProcessInfo> appProcesses = activityManager
        .getRunningAppProcesses();
    if (appProcesses == null)
      return false;
    for (RunningAppProcessInfo appProcess : appProcesses) {
      if (appProcess.processName.equals(packageName)
          && appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
        return true;
      }
    }
    return false;
  }

  /*
   * Convert JSONObject to Map<String, String>.
   */
  private Map<String, String> getMap(JSONArray args, int index)
      throws JSONException {
    Map<String, String> data = new HashMap<String, String>();
    JSONObject obj = args.getJSONObject(index);
    JSONArray names = obj.names();

    for (int i = 0; i < names.length(); i++) {
      String key = names.getString(i);
      String value = obj.getString(key);
      data.put(key, value);
    }

    return data;
  }

  /*
   * Convert JSONArray to ArrayList<String, String>
   */
  private ArrayList<String> getArrayList(JSONArray args, int index)
      throws JSONException {
    ArrayList<String> tags = new ArrayList<String>();
    JSONArray array = args.getJSONArray(0);
    for (int i = 0; i < array.length(); i++) {
      String tag = array.getString(i);
      tags.add(tag);
    }
    return tags;
  }

  private void injectJS(final String js) {
    activity.runOnUiThread(new Runnable() {
      public void run() {
        webView.loadUrl("javascript:" + js);
      }
    });
  }

  private void fireJSEvent(String eventName, String message) {
    String js = "javascript:var e = document.createEvent('Events'); e.initEvent('"
        + eventName
        + "', true, true);e.message='"
        + message
        + "';document.dispatchEvent(e)";
    Log.d(TAG, js);
    injectJS(js);
  }

}
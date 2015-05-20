var cordova = require('cordova');
var exec = require('cordova/exec');

module.exports = {

	login: function(serverId, successCallback, errorCallback) {
		exec(successCallback, errorCallback, 'AnySDK', 'login', [serverId]);
	},

	logout: function(successCallback, errorCallback) {
		exec(successCallback, errorCallback, 'AnySDK', 'logout', []);
	},

	isLogined: function(successCallback, errorCallback) {
		exec(successCallback, errorCallback, 'AnySDK', 'isLogined', []);
	},

	getUserID: function(successCallback, errorCallback) {
		exec(successCallback, errorCallback, 'AnySDK', 'getUserID', []);
	},

	enterPlatform: function() {
		exec(null, null, 'AnySDK', 'enterPlatform', []);
	},

	showToolBar: function(position) {
		exec(null, null, 'AnySDK', 'showToolBar', [position]);
	},

	hideToolBar: function() {
		exec(null, null, 'AnySDK', 'hideToolBar', []);
	},

	accountSwitch: function() {
		exec(null, null, 'AnySDK', 'accountSwitch', []);
	},

	exit: function() {
		exec(null, null, 'AnySDK', 'exit', []);
	},

	pause: function() {
		exec(null, null, 'AnySDK', 'pause', []);
	},

	destory: function() {
		exec(null, null, 'AnySDK', 'destory', []);
	},

	// Special service providers:

	/*
	 * QiHu 360
	 */
	realNameRegister: function() {
		exec(null, null, 'AnySDK', 'realNameRegister', []);
	},

	/*
	 * QiHu 360
	 */
	antiAddictionQuery: function() {
		exec(null, null, 'AnySDK', 'antiAddictionQuery', []);
	},

	/*
	 * UC & Shanghai YiWan
	 */ 
	submitLoginGameRole: function(data) {
		exec(null, null, 'AnySDK', 'submitLoginGameRole', [data]);
	},

	// Payment 

	payForProduct: function(index, productionInfo) {
		exec(null, null, 'AnySDK', 'payForProduct', [index, productionInfo]);
	},

	getOrderId: function(successCallback, errorCallback, pluginId) {
		exec(successCallback, errorCallback, 'AnySDK', 'getOrderId', [pluginId]);
	},

	// Analytics
	logEvent: function(eventId, paramMap) {
		exec(null, null, 'AnySDK', 'logEvent', [eventId, paramMap]);
	},

	setSessionContinueMillis: function(millis) {
		exec(null, null, 'AnySDK', 'setSessionContinueMillis', [millis]);
	},

	setCaptureUncaughtException: function(captureOn) {
		exec(null, null, 'AnySDK', 'setCaptureUncaughtException', [captureOn]);
	},

	logError: function(errorId, message) {
		exec(null, null, 'AnySDK', 'logError', [errorId, message]);
	},

	setAccount: function(paramMap) {
		exec(null, null, 'AnySDK', 'setAccount', [paramMap]);
	},

	onChargeRequest: function(paramMap) {
		exec(null, null, 'AnySDK', 'onChargeRequest', [paramMap]);
	},

	onChargeSuccess: function(orderId) {
		exec(null, null, 'AnySDK', 'onChargeSuccess', [orderId]);
	},

	onChargeFail: function(paramMap) {
		exec(null, null, 'AnySDK', 'onChargeFail', [paramMap]);
	},

	onChargeOnlySuccess: function(paramMap) {
		exec(null, null, 'AnySDK', 'onChargeOnlySuccess', [paramMap]);
	},

	onPurchase: function(paramMap) {
		exec(null, null, 'AnySDK', 'onPurchase', [paramMap]);
	},

	onUse: function(paramMap) {
		exec(null, null, 'AnySDK', 'onUse', [paramMap]);
	},

	onReward: function(paramMap) {
		exec(null, null, 'AnySDK', 'onReward', [paramMap]);
	},

	startLevel: function(paramMap) {
		exec(null, null, 'AnySDK', 'startLevel', [paramMap]);
	},

	finishLevel: function(levelId) {
		exec(null, null, 'AnySDK', 'finishLevel', [levelId]);
	},

	failLevel: function(paramMap) {
		exec(null, null, 'AnySDK', 'failLevel', [paramMap]);
	},

	startTask: function(paramMap) {
		exec(null, null, 'AnySDK', 'startTask', [paramMap]);
	}, 

	finishTask: function(taskId) {
		exec(null, null, 'AnySDK', 'finishTask', [taskId]);
	},

	failTask: function(paramMap) {
		exec(null, null, 'AnySDK', 'failTask', [paramMap]);
	},

	// SNS
	share: function(info) {
		exec(null, null, 'AnySDK', 'share', [info]);
	},

	// Ads
	isAdTypeSupported: function(successCallback, errorCallback, type) {
		exec(successCallback, errorCallback, 'AnySDK', 'isAdTypeSupported', [type]);
	},

	preloadAds: function(type) {
		exec(null, null, 'AnySDK', 'preloadAds', [type]);
	},

	showAds: function(type) {
		exec(null, null, 'AnySDK', 'showAds', [type]);
	},

	hideAds: function(type) {
		exec(null, null, 'AnySDK', 'hideAds', [type]);
	},

	queryPoints: function(successCallback, errorCallback) {
		exec(successCallback, errorCallback, 'AnySDK', 'queryPoints', []);
	},

	spendPoints: function(point) {
		exec(null, null, 'AnySDK', 'spendPoints', [point]);
	},

	// Push
	startPush: function() {
		exec(null, null, 'AnySDK', 'startPush', []);
	},

	closePush: function() {
		exec(null, null, 'AnySDK', 'closePush', []);
	},

	setAlias: function(alias) {
		exec(null, null, 'AnySDK', 'setAlias', [alias]);
	},

	delAlias: function(alias) {
		exec(null, null, 'AnySDK', 'delAlias', [alias]);
	},

	setTags: function(tags) {
		exec(null, null, 'AnySDK', 'setTags', [tags]);
	},

	delTags: function(tags) {
		exec(null, null, 'AnySDK', 'delTags', [tags]);
	},

	submitScore: function(leadboardID, score) {
		exec(null, null, 'AnySDK', 'submitScore', [leadboardID, score]);
	},

	showLeaderboard: function(eaderboardID) {
		exec(null, null, 'AnySDK', 'showLeaderboard', [eaderboardID]);
	},

	unlockAchievement: function(info) {
		exec(null, null, 'AnySDK', 'unlockAchievement', [info]);
	},

	showAchievements: function(){
		exec(null, null, 'AnySDK', 'showAchievements', []);
	},

};
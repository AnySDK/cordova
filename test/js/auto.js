define('Test if all JavaScript APIs are loaded', function () {
	beforeAll(function (done) {
		document.addEventListener('deviceready', function () {
			done();		
		}, false);
	});
	
	// Test suites for AnySDK
	describe('AnySDK Cordova Plugin', function () {
		it('anysdk is defined', function () {
			expect(anysdk).toBeDefined();
		});
	});
	
	// Test suites for Login.
	describe('AnySDK Login', function () {
	
		it('has a login method', function () {
			expect(anysdk.login).toBeDefined();
		});
	
		it('has a logout method', function () {
			expect(anysdk.logout).toBeDefined();
		});
	
		it('has a isLogined method', function () {
			expect(anysdk.isLogined).toBeDefined();
		});
	
		it('has a getUserID method', function () {
			expect(anysdk.getUserID).toBeDefined();
		});
	
		it('has a enterPlatform method', function () {
			expect(anysdk.enterPlatform).toBeDefined();
		});
	
		it('has a showToolBar method', function () {
			expect(anysdk.showToolBar).toBeDefined();
		});
	
		it('has a hideToolBar method', function () {
			expect(anysdk.hideToolBar).toBeDefined();
		});
	
		it('has a accountSwitch method', function () {
			expect(anysdk.accountSwitch).toBeDefined();
		});
	
		it('has a exit method', function () {
			expect(anysdk.exit).toBeDefined();
		});
	
		it('has a realNameRegister method', function () {
			expect(anysdk.realNameRegister).toBeDefined();
		});
	
		it('has a antiAddictionQuery method', function () {
			expect(anysdk.antiAddictionQuery).toBeDefined();
		});
	
		it('has a submitLoginGameRole method', function () {
			expect(anysdk.submitLoginGameRole).toBeDefined();
		});
	
	});
	
	// Test suites for Payment.
	describe('AnySDK Payment', function () {
	
		it('has a payForProduct method', function () {
			expect(anysdk.payForProduct).toBeDefined();
		});
	
		it('has a getOrderId method', function () {
			expect(anysdk.getOrderId).toBeDefined();
		});
	
	});
	
	// Test suites for Statistic
	describe('AnySDK Statistic', function () {
	
		it('has a logEvent method', function () {
			expect(anysdk.logEvent).toBeDefined();
		});
	
		it('has a setSessionContinueMillis method', function () {
			expect(anysdk.setSessionContinueMillis).toBeDefined();
		});
	
		it('has a setCaptureUncaughtException method', function () {
			expect(anysdk.setCaptureUncaughtException).toBeDefined();
		});
	
		it('has a logError method', function () {
			expect(anysdk.logError).toBeDefined();
		});
	
		it('has a setAccount method', function () {
			expect(anysdk.setAccount).toBeDefined();
		});
	
		it('has a onChargeRequest method', function () {
			expect(anysdk.onChargeRequest).toBeDefined();
		});
	
		it('has a onChargeSuccess method', function () {
			expect(anysdk.onChargeSuccess).toBeDefined();
		});
	
		it('has a onChargeFail method', function () {
			expect(anysdk.onChargeFail).toBeDefined();
		});
	
		it('has a onChargeFail method', function () {
			expect(anysdk.onChargeFail).toBeDefined();
		});
	
		it('has a onChargeOnlySuccess method', function () {
			expect(anysdk.onChargeOnlySuccess).toBeDefined();
		});
	
		it('has a onPurchase method', function () {
			expect(anysdk.onPurchase).toBeDefined();
		});
	
		it('has a onUse method', function () {
			expect(anysdk.onUse).toBeDefined();
		});
	
		it('has a onReward method', function () {
			expect(anysdk.onReward).toBeDefined();
		});
	
		it('has a startLevel method', function () {
			expect(anysdk.startLevel).toBeDefined();
		});
	
		it('has a finishLevel method', function () {
			expect(anysdk.finishLevel).toBeDefined();
		});
	
		it('has a failLevel method', function () {
			expect(anysdk.failLevel).toBeDefined();
		});
	
		it('has a startTask method', function () {
			expect(anysdk.startTask).toBeDefined();
		});
	
		it('has a finishTask method', function () {
			expect(anysdk.finishTask).toBeDefined();
		});
	
		it('has a failTask method', function () {
			expect(anysdk.failTask).toBeDefined();
		});
	
	});
	
	// Test suites for Sharing.
	describe('AnySDK Sharing', function () {
	
		it('has a share method', function () {
			expect(anysdk.share).toBeDefined();
		});
	
	});
	
	// Test suites for Ads.
	describe('AnySDK Ads', function () {
	
		it('has a isAdTypeSupported method', function () {
			expect(anysdk.isAdTypeSupported).toBeDefined();
		});
	
		it('has a preloadAds method', function () {
			expect(anysdk.preloadAds).toBeDefined();
		});
	
		it('has a showAds method', function () {
			expect(anysdk.showAds).toBeDefined();
		});
	
		it('has a hideAds method', function () {
			expect(anysdk.hideAds).toBeDefined();
		});
	
		it('has a queryPoints method', function () {
			expect(anysdk.queryPoints).toBeDefined();
		});
	
		it('has a spendPoints method', function () {
			expect(anysdk.spendPoints).toBeDefined();
		});
	
	});
	
	// Test suites for Push
	describe('AnySDK Push', function () {
	
		it('has a startPush method', function () {
			expect(anysdk.startPush).toBeDefined();
		});
	
		it('has a closePush method', function () {
			expect(anysdk.closePush).toBeDefined();
		});
	
		it('has a setAlias method', function () {
			expect(anysdk.setAlias).toBeDefined();
		});
	
		it('has a delAlias method', function () {
			expect(anysdk.delAlias).toBeDefined();
		});
	
		it('has a setTags method', function () {
			expect(anysdk.setTags).toBeDefined();
		});
	
		it('has a delTags method', function () {
			expect(anysdk.delTags).toBeDefined();
		});
	
	});
	
	// Test suites for SNS.
	describe('AnySDK SNS', function () {
	
		it('has a submitScore method', function () {
			expect(anysdk.submitScore).toBeDefined();
		});
	
		it('has a showLeaderboard method', function () {
			expect(anysdk.showLeaderboard).toBeDefined();
		});
	
		it('has a unlockAchievement method', function () {
			expect(anysdk.unlockAchievement).toBeDefined();
		});
	
		it('has a showAchievements method', function () {
			expect(anysdk.showAchievements).toBeDefined();
		});
	
	});
});

describe('Test if all JavaScript APIs won\'t crash or throw', function () {
	beforeAll(function (done) {
		document.addEventListener('deviceready', function () {
			done();		
		}, false);
	});

	it('any.login() should not throw exceptions', function () {
		expect(anysdk.login).not.toThrow();
	});

	it('any.logout() should not throw exceptions', function () {
		expect(anysdk.logout).not.toThrow();
	});

	it('any.isLogined() should not throw exceptions', function () {
		expect(anysdk.isLogined).not.toThrow();
	});

	it('any.getUserID() should not throw exceptions', function () {
		expect(anysdk.getUserID).not.toThrow();
	});

	it('any.enterPlatform() should not throw exceptions', function () {
		expect(anysdk.enterPlatform).not.toThrow();
	});

	it('any.showToolBar() should not throw exceptions', function () {
		expect(anysdk.showToolBar).not.toThrow();
	});

	it('any.hideToolBar() should not throw exceptions', function () {
		expect(anysdk.hideToolBar).not.toThrow();
	});

	it('any.accountSwitch() should not throw exceptions', function () {
		expect(anysdk.accountSwitch).not.toThrow();
	});

	it('any.exit() should not throw exceptions', function () {
		expect(anysdk.exit).not.toThrow();
	});

	it('any.realNameRegister() should not throw exceptions', function () {
		expect(anysdk.realNameRegister).not.toThrow();
	});

	it('any.antiAddictionQuery() should not throw exceptions', function () {
		expect(anysdk.antiAddictionQuery).not.toThrow();
	});

	it('any.submitLoginGameRole() should not throw exceptions', function () {
		expect(anysdk.submitLoginGameRole).not.toThrow();
	});

	it('any.payForProduct() should not throw exceptions', function () {
		expect(anysdk.payForProduct).not.toThrow();
	});

	it('any.getOrderId() should not throw exceptions', function () {
		expect(anysdk.getOrderId).not.toThrow();
	});
	
	it('any.logEvent() should not throw exceptions', function () {
		expect(anysdk.logEvent).not.toThrow();
	});
	
	it('any.setSessionContinueMillis() should not throw exceptions', function () {
		expect(anysdk.setSessionContinueMillis).not.toThrow();
	});
	
	it('any.setCaptureUncaughtException() should not throw exceptions', function () {
		expect(anysdk.setCaptureUncaughtException).not.toThrow();
	});
	
	it('any.logError() should not throw exceptions', function () {
		expect(anysdk.logError).not.toThrow();
	});
	
	it('any.setAccount() should not throw exceptions', function () {
		expect(anysdk.setAccount).not.toThrow();
	});
	
	it('any.onChargeRequest() should not throw exceptions', function () {
		expect(anysdk.onChargeRequest).not.toThrow();
	});
	
	it('any.onChargeFail() should not throw exceptions', function () {
		expect(anysdk.onChargeFail).not.toThrow();
	});
	
	it('any.onChargeOnlySuccess() should not throw exceptions', function () {
		expect(anysdk.onChargeOnlySuccess).not.toThrow();
	});
	
	it('any.onPurchase() should not throw exceptions', function () {
		expect(anysdk.onPurchase).not.toThrow();
	});
	
	it('any.onUse() should not throw exceptions', function () {
		expect(anysdk.onUse).not.toThrow();
	});
	
	it('any.onReward() should not throw exceptions', function () {
		expect(anysdk.onReward).not.toThrow();
	});
	
	it('any.startLevel() should not throw exceptions', function () {
		expect(anysdk.startLevel).not.toThrow();
	});
	
	it('any.finishLevel() should not throw exceptions', function () {
		expect(anysdk.finishLevel).not.toThrow();
	});
	
	it('any.failLevel() should not throw exceptions', function () {
		expect(anysdk.failLevel).not.toThrow();
	});
	
	it('any.startTask() should not throw exceptions', function () {
		expect(anysdk.startTask).not.toThrow();
	});
	
	it('any.finishTask() should not throw exceptions', function () {
		expect(anysdk.finishTask).not.toThrow();
	});
	
	it('any.failTask() should not throw exceptions', function () {
		expect(anysdk.failTask).not.toThrow();
	});
	
	it('any.share() should not throw exceptions', function () {
		expect(anysdk.share).not.toThrow();
	});
	
	it('any.isAdTypeSupported() should not throw exceptions', function () {
		expect(anysdk.isAdTypeSupported).not.toThrow();
	});
	
	it('any.preloadAds() should not throw exceptions', function () {
		expect(anysdk.preloadAds).not.toThrow();
	});
	
	it('any.showAds() should not throw exceptions', function () {
		expect(anysdk.showAds).not.toThrow();
	});
	
	it('any.hideAds() should not throw exceptions', function () {
		expect(anysdk.hideAds).not.toThrow();
	});
	
	it('any.queryPoints() should not throw exceptions', function () {
		expect(anysdk.queryPoints).not.toThrow();
	});
	
	it('any.spendPoints() should not throw exceptions', function () {
		expect(anysdk.spendPoints).not.toThrow();
	});
	
	it('any.startPush() should not throw exceptions', function () {
		expect(anysdk.startPush).not.toThrow();
	});
	
	it('any.closePush() should not throw exceptions', function () {
		expect(anysdk.closePush).not.toThrow();
	});
	
	it('any.setAlias() should not throw exceptions', function () {
		expect(anysdk.setAlias).not.toThrow();
	});
	
	it('any.delAlias() should not throw exceptions', function () {
		expect(anysdk.delAlias).not.toThrow();
	});
	
	it('any.setTags() should not throw exceptions', function () {
		expect(anysdk.setTags).not.toThrow();
	});
	
	it('any.delTags() should not throw exceptions', function () {
		expect(anysdk.delTags).not.toThrow();
	});
	
	it('any.submitScore() should not throw exceptions', function () {
		expect(anysdk.submitScore).not.toThrow();
	});
	
	it('any.showLeaderboard() should not throw exceptions', function () {
		expect(anysdk.showLeaderboard).not.toThrow();
	});
	
	it('any.unlockAchievement() should not throw exceptions', function () {
		expect(anysdk.unlockAchievement).not.toThrow();
	});
	
	it('any.showAchievements() should not throw exceptions', function () {
		expect(anysdk.showAchievements).not.toThrow();
	});
	
});
	
	
	
	
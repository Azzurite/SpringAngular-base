'use strict';

angular.module('baseApp.security').service('Session',
	function($q, $http, $log, $rootScope, $state, CacheFactory, growl, azzu) {
		var self = this;
		var sessionCache = CacheFactory.createCache('SessionCache', {
			maxAge: 10000,
			deleteOnExpire: 'passive'
		});


		$rootScope.$on('$stateChangeStart',
			function(event, toState, toParams) {
				if (toState.name === 'login' || sessionCache.get('account')) {
					return;
				}

				event.preventDefault();
				self.get().then(
					function() {
						$state.go(toState, toParams);
					},
					function() {
						growl.error('Session not found. Please log in.');
						$state.go('login');
					}
				);
			}
		);

		this.get = function() {
			var account = sessionCache.get('account');

			if (account) {
				$log.debug('Session is cached:', account);
				return $q(function(resolve) {
					resolve(account);
				});
			}

			var promise = getAccount();

			promise.then(function(account) {
				sessionCache.put('account', account);
			});

			return promise;
		};

		function getAccount() {
			return $q(function(resolve, reject) {
				$http.get('/rest/account').success(
					function(account) {
						$log.debug('Session exists:', account);
						self.account = account;
						resolve(account);
					}
				).error(
					function() {
						$log.debug('Session missing');
						reject();
					}
				);
			});
		}

		this.create = function(username, password) {
			return $q(function(resolve, reject) {
				self.get().then(
					function(account) {
						$log.debug('Won\'t create session, it already exists.');
						reject(account);
					},
					function() {
						self.login(username, password).then(
							function(account, a1, a2, a3, a4) {
								$log.debug('Created session:', account, a1, a2, a3, a4);
								self.get().then(resolve, reject);
							},
							function(data, status, a1, a2, a3, a4) {
								$log.debug('Session creation failed:', status, data, a1, a2, a3, a4);
								reject();
							}
						);
					}
				);
			});
		};

		this.login = function(username, password) {
			return $q(function(resolve, reject) {
				$http({
					method: 'POST',
					url: '/login',
					headers: {'Content-Type': 'application/x-www-form-urlencoded'},
					transformRequest: azzu.formPostTransform,
					data: {
						username: username,
						password: password
					}
				}).success(
					function() {
						resolve();
					}
				).error(
					function(data) {
						reject(data);
					}
				);
			});
		};
	}
)
;

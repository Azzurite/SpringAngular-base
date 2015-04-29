'use strict';

angular.module('baseApp.login', [
		'baseApp.dependencies',
		'baseApp.security'
	]
);

angular.module('baseApp.login').config(
	function($stateProvider) {
		$stateProvider.state('login', {
			url: '/login',
			templateUrl: 'app/login/login.html',
			controller: 'LoginController'
		});
	}
);

angular.module('baseApp.login').controller('LoginController',
	function($scope, $http, $state, growl, Session) {
		$scope.login = function(username, password) {
			Session.login(username, password).then(
				function() {
					$state.go('home');
				},
				function(account) {
					if (account) {
						growl.info('Already logged in as ' + account.name);
					} else {
						growl.error('Username or password wrong.');
					}
				}
			);
		};
	}
);

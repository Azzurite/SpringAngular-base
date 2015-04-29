'use strict';

angular.module('baseApp.dependencies', [
	'azUtils',
	'azResourceAddStatus',
	'ngCookies',
	'ngResource',
	'ui.router',
	'angular-growl',
	'angular-cache'
]);

angular.module('baseApp', [
		'baseApp.dependencies',
		'baseApp.security',
		'baseApp.login',
		'baseApp.customers'
	]
).config(
	function($stateProvider, $urlRouterProvider, $locationProvider, growlProvider) {
		$locationProvider.html5Mode({
			enabled: true
		});

		$urlRouterProvider.otherwise('/');

		$stateProvider.state('home', {
			url: '/',
			templateUrl: 'app/home/home.html'
		});

		growlProvider.globalPosition('top-center');
		growlProvider.globalTimeToLive(6000);
	}
).run(
	function(Session, $state, $location, $rootScope, $log) {
		$log.debug('Application start');


	}
);

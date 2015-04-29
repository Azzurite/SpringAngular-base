'use strict';

describe('Controller tests', function() {
	describe('LoginController', function() {
		beforeEach(module('baseApp.login'));

		var $controller;
		var Session;
		var $state;
		beforeEach(inject(function(_$controller_, _Session_, _$state_) {
			$controller = _$controller_;
			Session = _Session_;
			$state = _$state_;
		}));

		it('should provide a login function on scope', function() {
			var $scope = {};
			$controller('LoginController', {$scope: $scope});
			expect($scope.login).toBeDefined();
		});

		it('should call the Session service for login', function() {

			var $scope = {};

			spyOn(Session, 'login').and.returnValue(jasmine.createSpyObj('promise', ['then']));

			var LoginController = $controller('LoginController', {
				$scope: $scope,
				Session: Session
			});

			$scope.login('test', 'test');

			expect(Session.login).toHaveBeenCalledWith('test', 'test');
		});

		it('should go to the home screen on successful login', function() {
			var $scope = {};

			spyOn($state, 'go');

			var promise = jasmine.createSpyObj('promise', ['then']);
			promise.then.and.callFake(function(successCallback) {
				successCallback();
			});
			spyOn(Session, 'login').and.returnValue(promise);

			var LoginController = $controller('LoginController', {
				$scope: $scope,
				Session: Session,
				$state: $state
			});

			$scope.login('test', 'test');

			expect($state.go).toHaveBeenCalledWith('home');
		});
	});
});

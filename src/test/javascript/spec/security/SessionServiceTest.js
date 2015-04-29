'use strict';

describe('Service tests', function() {
	describe('SessionService', function() {
		beforeEach(module('baseApp.security'));

		var Session;
		var $httpBackend;

		beforeEach(inject(function(_Session_, _$httpBackend_) {
			Session = _Session_;
			$httpBackend = _$httpBackend_;
		}));

		afterEach(function() {
			$httpBackend.verifyNoOutstandingExpectation();
			$httpBackend.verifyNoOutstandingRequest();
		});

		it('should login', function() {
			$httpBackend.expectPOST('/login', 'username=test&password=test').respond(200);

			var success = false;
			Session.login('test', 'test').then(function() {
				success = true;
			}).catch(fail);

			$httpBackend.flush();

			if (!success) {
				fail('Success callback not called');
			}
		});

		it('should get account data', function() {
			$httpBackend.expectGET('/rest/account').respond({name: 'test'});

			Session.get().then(function(account) {
				expect(account.name).toBe('test');
			});

			$httpBackend.flush();
		});

		it('should cache account data', function() {
			$httpBackend.expectGET('/rest/account').respond({name: 'test'});

			Session.get().then(function(account) {
				expect(account.name).toBe('test');
			});

			$httpBackend.flush();

			Session.get().then(function(account) {
				expect(account.name).toBe('test');
			});
		});

		it('should create a session', function() {
			$httpBackend.expectGET('/rest/account').respond(403);

			$httpBackend.expectPOST('/login', 'username=test&password=test').respond(200);

			$httpBackend.expectGET('/rest/account').respond({name: 'test'});

			Session.create('test', 'test').then(function(account) {
				expect(account.name).toBe('test');
			});

			$httpBackend.flush();
		});

		it('should not create a session if a session already exists', function() {
			$httpBackend.expectGET('/rest/account').respond({name: 'test'});

			Session.create('test', 'test').then(
				fail,
				function(account) {
					expect(account.name).toBe('test');
				}
			);

			$httpBackend.flush();
		});
	});
});

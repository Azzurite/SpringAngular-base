'use strict';

describe('Utility Tests', function() {

	describe('ResourceAddStatus', function() {
		beforeEach(module('azResourceAddStatus'));

		var $resource;
		var $httpBackend;
		var $http;
		beforeEach(inject(function(_$resource_, _$httpBackend_, _$http_) {
			$resource = _$resource_;
			$httpBackend = _$httpBackend_;
			$http = _$http_;
			jasmine.DEFAULT_TIMEOUT_INTERVAL = 1000;
		}));

		afterEach(function() {
			$httpBackend.verifyNoOutstandingExpectation();
			$httpBackend.verifyNoOutstandingRequest();
		});

		it('has a $status on get', function() {
			$httpBackend.expectGET('/rest/test/1').respond({test: 1});
			$resource('/rest/test/:id').get({id: 1}, function(data) {
				expect(data.$status).toBeDefined();
			});
			$httpBackend.flush();
		});

		it('has a $status on save', function() {
			$httpBackend.expectPOST('/rest/test', {test: 1}).respond({test: 1});
			$resource('/rest/test/:id').save({test: 1}, function(data) {
				expect(data.$status).toBeDefined();
			});
			$httpBackend.flush();
		});


		it('has a $status on remove', function() {
			$httpBackend.expectDELETE('/rest/test/1').respond({test: 1});
			$resource('/rest/test/:id').remove({id: 1}, function(data) {
				expect(data.$status).toBeDefined();
			});
			$httpBackend.flush();
		});


		it('has a $status on delete', function() {
			$httpBackend.expectDELETE('/rest/test/1').respond({test: 1});
			$resource('/rest/test/:id').delete({id: 1}, function(data) {
				expect(data.$status).toBeDefined();
			});
			$httpBackend.flush();
		});

		it('has a $status on each element returned by query', function() {
			$httpBackend.expectGET('/rest/test').respond([
				{test: 1},
				{test: 2}
			]);
			$resource('/rest/test/:id').query(function(data) {
				angular.forEach(data, function(value) {
					expect(value.$status).toBeDefined();
				});
			});
			$httpBackend.flush();
		});
	});

});

'use strict';

angular.module('baseApp.customers').service('CustomersService',
	function($resource) {
		return $resource('/rest/customers/:customer', null, {

		});
	}
);

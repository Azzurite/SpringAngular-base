'use strict';

angular.module('baseApp.customers').config(
	function($stateProvider) {
		$stateProvider.state('customers', {
			url: '/customers/:id',
			templateUrl: 'app/customers/customers.html',
			controller: function($scope, $log, CustomersService, $state) {
				$scope.states = $state.get();
				$scope.customers = CustomersService.query(
					function(data) {
						$log.debug('Found customers: ', data);
					},
					function(data, headers) {
						$log.error('Error while retrieving customer list: ', data, headers);
					}
				);

				$scope.createCustomer = function(customer) {
					$log.debug('Creating customer: ', customer);
					CustomersService.save(customer,
						function(data) {
							$log.debug('Customer saved: ', data);
							if (data.$status === 201) {
								$scope.customers.push(data);
							}
							$scope.newCustomer = undefined;
						},
						function(error) {
							$log.debug('Nay...', error);
						}
					);
				};

				$scope.deleteCustomer = function(customer) {
					CustomersService.delete(
						{customer: customer.uniqueName},
						function() {
							var idx = $scope.customers.indexOf(customer);
							$scope.customers.splice(idx, 1);
						},
						function(data, status) {
							$log.error('error deleting customer ', customer, status);
						}
					);
				};

			}
		});
	}
);

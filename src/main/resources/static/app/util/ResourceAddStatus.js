'use strict';

/**
 * @description Adds a $status attribute to the successful result provided by the resource
 */
angular.module('azResourceAddStatus', ['ngResource']).config(
	function($resourceProvider, $provide) {

		function addStatusToResult(result, status) {
			if (angular.isArray(result)) {
				angular.forEach(result, function(value) {
					value.$status = status;
				});
			} else {
				result.$status = status;
			}

			return result;
		}

		function addStatusToResponse(response) {
			return addStatusToResult(response.resource, response.status);
		}

		$resourceProvider.defaults.actions = {
			'get': {
				method: 'GET',
				interceptor: {
					response: addStatusToResponse
				}
			},
			'save': {
				method: 'POST',
				interceptor: {
					response: addStatusToResponse
				}
			},
			'query': {
				method: 'GET',
				isArray: true,
				interceptor: {
					response: addStatusToResponse
				}
			},
			'remove': {
				method: 'DELETE',
				interceptor: {
					response: addStatusToResponse
				}
			},
			'delete': {
				method: 'DELETE',
				interceptor: {
					response: addStatusToResponse
				}
			}
		};

		$provide.decorator('$resource', function($delegate) {
			return function(url, paramDefaults, actions, options) {
				angular.forEach(actions, function(action) {
					if (action.interceptor && action.interceptor.response) {

						action.interceptor.response = function(response) {
							var result = action.interceptor.response(response);

							addStatusToResult(result, response.status);

							return result;
						};
					} else {
						action.interceptor = {
							response: addStatusToResponse
						};
					}
				});
				return $delegate(url, paramDefaults, actions, options);
			};
		});
	}
);

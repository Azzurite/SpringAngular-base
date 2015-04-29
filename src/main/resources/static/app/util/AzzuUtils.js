'use strict';

angular.module('azUtils', []).constant('azzu', {
	formPostTransform: function(data) {
		var params = [];
		angular.forEach(data, function(value, key) {
			params.push(encodeURIComponent(key) + '=' + encodeURIComponent(value));
		});
		return params.join('&');
	}
});

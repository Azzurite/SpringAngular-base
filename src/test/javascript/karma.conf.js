// Karma configuration
// http://karma-runner.github.io/0.10/config/configuration-file.html

module.exports = function(config) {
	config.set({
		// base path, that will be used to resolve files and exclude
		basePath: '../../',

		// testing framework to use (jasmine/mocha/qunit/...)
		frameworks: ['jasmine'],

		// list of files / patterns to load in the browser
		files: [
			// bower:js
			'main/resources/static/bower_components/modernizr/modernizr.js',
			'main/resources/static/bower_components/jquery/dist/jquery.js',
			'main/resources/static/bower_components/angular/angular.js',
			'main/resources/static/bower_components/angular-cache-buster/angular-cache-buster.js',
			'main/resources/static/bower_components/angular-cookies/angular-cookies.js',
			'main/resources/static/bower_components/angular-dynamic-locale/src/tmhDynamicLocale.js',
			'main/resources/static/bower_components/angular-growl-v2/build/angular-growl.js',
			'main/resources/static/bower_components/angular-local-storage/dist/angular-local-storage.js',
			'main/resources/static/bower_components/angular-resource/angular-resource.js',
			'main/resources/static/bower_components/angular-sanitize/angular-sanitize.js',
			'main/resources/static/bower_components/angular-translate/angular-translate.js',
			'main/resources/static/bower_components/angular-translate-loader-partial/angular-translate-loader-partial.js',
			'main/resources/static/bower_components/angular-ui-router/release/angular-ui-router.js',
			'main/resources/static/bower_components/bootstrap-sass/assets/javascripts/bootstrap.js',
			'main/resources/static/bower_components/json3/lib/json3.js',
			'main/resources/static/bower_components/ngInfiniteScroll/build/ng-infinite-scroll.js',
			'main/resources/static/bower_components/angular-cache/dist/angular-cache.js',
			'main/resources/static/bower_components/angular-mocks/angular-mocks.js',
			// endbower
			'main/resources/static/app/app.js',
			'main/resources/static/app/**/*.js',
			'test/javascript/**/!(karma.conf).js'
		],


		// list of files / patterns to exclude
		exclude: [],

		// web server port
		port: 9876,

		// level of logging
		// possible values: LOG_DISABLE || LOG_ERROR || LOG_WARN || LOG_INFO || LOG_DEBUG
		logLevel: config.LOG_INFO,

		// enable / disable watching file and executing tests whenever any file changes
		autoWatch: false,

		// Start these browsers, currently available:
		// - Chrome
		// - ChromeCanary
		// - Firefox
		// - Opera
		// - Safari (only Mac)
		// - PhantomJS
		// - IE (only Windows)
		browsers: ['PhantomJS'],

		// Continuous Integration mode
		// if true, it capture browsers, run tests and exit
		singleRun: false
	});
};

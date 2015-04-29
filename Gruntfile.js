'use strict';

var fs = require('fs');

var parseString = require('xml2js').parseString;
// Returns the second occurence of the version number
var getCurrentAppVersion = function impl() {
	if (impl.appVersion) {
		return impl.appVersion;
	}
	var pomXml = fs.readFileSync('pom.xml', 'utf8');
	parseString(pomXml, function(err, result) {
		impl.appVersion = result.project.version[0];
	});
	return impl.appVersion;
};

// usemin custom step
var useminAutoprefixer = {
	name: 'autoprefixer',
	createConfig: require('grunt-usemin/lib/config/cssmin').createConfig // Reuse cssmins createConfig
};

var htmlMinOptions = {
	removeCommentsFromCDATA: true,
	// https://github.com/yeoman/grunt-usemin/issues/44
	collapseWhitespace: true,
	collapseBooleanAttributes: true,
	conservativeCollapse: true,
	removeAttributeQuotes: true,
	removeRedundantAttributes: true,
	useShortDoctype: true,
	removeEmptyAttributes: true
};


module.exports = function(grunt) {
	require('load-grunt-tasks')(grunt);
	//require('time-grunt')(grunt);

	grunt.initConfig({
		yeoman: {
			// configurable paths
			app: 'src/main/resources/static',
			dist: 'src/main/resources/static/dist'
		},
		watch: {
			bower: {
				files: ['bower.json'],
				tasks: [
					'wiredep',
					'copy:bowerfonts'
				]
			},
			ngconstant: {
				files: [
					'Gruntfile.js',
					'src/main/resources/config/constants.json',
					'pom.xml'
				],
				tasks: ['ngconstant:dev']
			},
			compass: {
				files: ['src/main/resources/scss/**/*.{scss,sass}'],
				tasks: ['compass:dev']
			},
			jshint: {
				files: ['<%= yeoman.app %>/app/**/*.js'],
				tasks: ['jshint']
			}
		},
		autoprefixer: {
			// not used since Uglify task does autoprefixer,
			//    options: ['last 1 version'],
			//    dist: {
			//        files: [{
			//            expand: true,
			//            cwd: '.tmp/styles/',
			//            src: '**/*.css',
			//            dest: '.tmp/styles/'
			//        }]
			//    }
		},
		wiredep: {
			app: {
				src: [
					'<%= yeoman.app %>/index.html',
					'src/main/resources/scss/main.scss'
				],
				exclude: [
					/angular-i18n/, // localizations are loaded dynamically
					/swagger-ui/
				],
				ignorePath: /\.\.\/webapp\/bower_components\// // remove ../webapp/bower_components/ from paths of
															   // injected sass files
			},
			test: {
				src: 'src/test/javascript/karma.conf.js',
				exclude: [
					/angular-i18n/,
					/swagger-ui/,
					/angular-scenario/
				],
				ignorePath: /\.\.\/\.\.\//, // remove ../../ from paths of
											// injected javascripts
				devDependencies: true,
				fileTypes: {
					js: {
						block: /(([\s\t]*)\/\/\s*bower:*(\S*))(\n|\r|.)*?(\/\/\s*endbower)/gi,
						detect: {
							js: /'(.*\.js)'/gi
						},
						replace: {
							js: '\'{{filePath}}\','
						}
					}
				}
			}
		},
		clean: {
			dist: {
				files: [
					{
						dot: true,
						src: [
							'.tmp',
							'<%= yeoman.dist %>/*'
						]
					}
				]
			}
		},
		jshint: {
			options: {
				jshintrc: '.jshintrc'
			},
			all: [
				'Gruntfile.js',
				'<%= yeoman.app %>/app/**/*.js'
			]
		},
		compass: {
			options: {
				sassDir: 'src/main/resources/scss',
				cssDir: '<%= yeoman.app %>/assets/styles',
				generatedImagesDir: '.tmp/assets/images/generated',
				imagesDir: '<%= yeoman.app %>/assets/images',
				javascriptsDir: '<%= yeoman.app %>/app',
				fontsDir: '<%= yeoman.app %>/assets/fonts',
				importPath: '<%= yeoman.app %>/bower_components',
				httpImagesPath: '/assets/images',
				httpGeneratedImagesPath: '/assets/images/generated',
				httpFontsPath: '/assets/fonts',
				relativeAssets: false,
				force: true
			},
			dist: {
				options: {
					noLineComments: true,
					debugInfo: false
				}
			},
			dev: {
				options: {
					debugInfo: true
				}
			}
		},
		concat: {
			// not used since Uglify task does concat,
			// but still available if needed
			//    dist: {}
		},
		rev: {
			dist: {
				files: {
					src: [
						'<%= yeoman.dist %>/app/**/*.js',
						'<%= yeoman.dist %>/assets/styles/**/*.css',
						'<%= yeoman.dist %>/assets/images/**/*.{png,jpg,jpeg,gif,webp,svg}',
						'<%= yeoman.dist %>/assets/fonts/**/*.{eot,svg,ttf,woff,woff2}'
					]
				}
			}
		},
		useminPrepare: {
			html: '<%= yeoman.app %>/**/*.html',
			options: {
				dest: '<%= yeoman.dist %>',
				flow: {
					html: {
						steps: {
							js: [
								'concat',
								'uglifyjs'
							],
							css: [
								'cssmin',
								useminAutoprefixer
							] // Let cssmin concat files so it corrects relative paths to fonts and images
						},
						post: {}
					}
				}
			}
		},
		usemin: {
			html: ['<%= yeoman.dist %>/**/*.html'],
			css: ['<%= yeoman.dist %>/assets/styles/**/*.css'],
			js: ['<%= yeoman.dist %>/app/**/*.js'],
			options: {
				assetsDirs: [
					'<%= yeoman.dist %>',
					'<%= yeoman.dist %>/assets/styles',
					'<%= yeoman.dist %>/assets/images',
					'<%= yeoman.dist %>/assets/fonts'
				],
				patterns: {
					js: [
						[
							/(assets\/images\/.*?\.(?:gif|jpeg|jpg|png|webp|svg))/gm,
							'Update the JS to reference our revved images'
						]
					]
				},
				dirs: ['<%= yeoman.dist %>']
			}
		},
		imagemin: {
			dist: {
				files: [
					{
						expand: true,
						cwd: '<%= yeoman.app %>/assets/images',
						src: '**/*.{png,jpg,jpeg}',
						dest: '<%= yeoman.dist %>/assets/images'
					}
				]
			}
		},
		svgmin: {
			dist: {
				files: [
					{
						expand: true,
						cwd: '<%= yeoman.app %>/assets/images',
						src: '**/*.svg',
						dest: '<%= yeoman.dist %>/assets/images'
					}
				]
			}
		},
		cssmin: {
			// By default, your `index.html` <!-- Usemin Block --> will take care of
			// minification. This option is pre-configured if you do not wish to use
			// Usemin blocks.
			// dist: {
			//     files: {
			//         '<%= yeoman.dist %>/styles/main.css': [
			//             '.tmp/styles/**/*.css',
			//             'styles/**/*.css'
			//         ]
			//     }
			// }
			options: {
				root: '<%= yeoman.app %>' // Replace relative paths for static resources with absolute path
			}
		},
		ngtemplates: {
			dist: {
				cwd: '<%= yeoman.app %>',
				src: [
					'app/**/*.html'
				],
				dest: '.tmp/templates/templates.js',
				options: {
					module: 'baseApp',
					usemin: 'app/app.js',
					htmlmin: htmlMinOptions
				}
			}
		},
		htmlmin: {
			dist: {
				options: htmlMinOptions,
				files: [
					{
						expand: true,
						cwd: '<%= yeoman.dist %>',
						src: ['*.html'],
						dest: '<%= yeoman.dist %>'
					}
				]
			}
		},
		copy: {
			dist: {
				files: [
					{
						expand: true,
						dot: true,
						cwd: '<%= yeoman.app %>',
						dest: '<%= yeoman.dist %>',
						src: [
							'*.html',
							'assets/images/**/*.{png,gif,webp,jpg,jpeg,svg}',
							'assets/fonts/**/*.{eot,svg,ttf,woff,woff2}'
						]
					},
					{
						expand: true,
						cwd: '.tmp/assets/images',
						dest: '<%= yeoman.dist %>/assets/images',
						src: [
							'generated/*'
						]
					}
				]
			},
			bowerfonts: {
				files: [
					{
						expand: true,
						dot: true,
						cwd: '<%= yeoman.app %>/bower_components',
						dest: '<%= yeoman.app %>/assets/fonts',
						src: [
							'**/fonts/**/*.{eot,svg,ttf,woff,woff2}'
						],
						rename: function(dest, src) {
							var fontsIdx = src.indexOf('fonts');
							var destWithoutPath = dest + src.substr(fontsIdx + 'fonts'.length);
							return destWithoutPath;
						}

					}
				]
			}
		},
		concurrent: {
			dist: [
				'compass:dist',
				'imagemin',
				'svgmin'
			]
		},
		karma: {
			unit: {
				configFile: 'src/test/javascript/karma.conf.js',
				singleRun: true
			}
		},
		ngAnnotate: {
			dist: {
				files: [
					{
						expand: true,
						cwd: '.tmp/concat/app',
						src: '*.js',
						dest: '.tmp/concat/apps'
					}
				]
			}
		},
		ngconstant: {
			options: {
				name: 'baseApp',
				deps: false,
				wrap: '\'use strict\';\n// Do not edit this file, edit the constants.json or grunt task ngconstant settings instead which generates this file!\n\n{%= __ngModule %}',
				constants: require('./src/main/resources/config/constants.json')
			},

			dev: {
				options: {
					dest: '<%= yeoman.app %>/app/app.constants.js'
				},
				constants: {
					ENV: 'dev',
					VERSION: getCurrentAppVersion()
				}
			},
			prod: {
				options: {
					dest: '.tmp/app/app.constants.js'
				},
				constants: {
					ENV: 'prod',
					VERSION: getCurrentAppVersion()
				}
			}
		}
	});

	grunt.registerTask('buildDev', [
		'wiredep',
		'copy:bowerfonts',
		'ngconstant:dev',
		'compass:dev',
		'jshint',
		'watch'
	]);

	grunt.registerTask('test', [
		'wiredep:test',
		'copy:bowerfonts',
		'ngconstant:dev',
		'compass:dev',
		'karma'
	]);

	grunt.registerTask('build', [
		'clean:dist',
		'wiredep:app',
		'copy:bowerfonts',
		'ngconstant:prod',
		'useminPrepare',
		'ngtemplates',
		'concurrent:dist',
		'concat',
		'copy:dist',
		'ngAnnotate',
		'cssmin',
		'autoprefixer',
		'uglify',
		'rev',
		'usemin',
		'htmlmin'
	]);

	grunt.registerTask('default', [
		'test',
		'build'
	]);
};

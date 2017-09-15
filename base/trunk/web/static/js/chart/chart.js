var chart = {
	chartMap : new Map(),

	defaultOptions : {
		sortData : true,
		grid : {
			backgroundColor : "#ffffff"
		},
		legend : {
			show : true,
			location : 'e',
			placement : 'outsideGrid'
		},
		highlighter : {
			show : false,
			sizeAdjust : 7.5
		},
		cursor : {
			show : true,
			showTooltip : false,
			zoom : true
		}
	},
	
	defaultAxesOptions : {
		axesDefaults : {
			labelRenderer : jQuery.jqplot.CanvasAxisLabelRenderer
		},
		axes : {
			xaxis : {
				renderer : jQuery.jqplot.CategoryAxisRenderer,
				tickRenderer : jQuery.jqplot.CanvasAxisTickRenderer,
				tickOptions : {
					angle : 0,
					fontFamily:'宋体'
				}
			},
			yaxis : {
				autoscale : true
			}
		},
		seriesDefaults : {
			showMarker : true,
			pointLabels : {
				show : true,
				formatString : '%.1f'
			}
		}
	},

	// 加载图表数据
	loadData : function(url, params) {
		if(null == params){
			params = {};
		}
		var chart = null;
		jQuery.ajaxSetup({
			async : false
		});// 同步
		jQuery.post(url, params, function(data) {
			chart = data;
		}, 'json').error(function() {
			if(typeof addActionMessage != 'function'){
				alert('[error]getChartData error.');
			} else {
				addActionError('[error]getChartData error.');
			}
		});
		return chart;
	},

	/**
	 * 重绘plot图表
	 * 
	 * @param containerId
	 *            容器ID
	 * @param chartData
	 *            图表数据
	 * @param plotConfig
	 *            图表配置信息
	 * @returns 返回重绘后的图表对象
	 */
	replotChart : function(plot, chartData) {
		this.setChartDataToPlot(plot, chartData);

		plot.replot({
			resetAxes : true
		});

		return plot;
	},

	/**
	 * 将最新的数据设置到plot图表中
	 * 
	 * @param plot
	 * @param chartData
	 */
	setChartDataToPlot : function(plot, chartData) {
		// 清空数据
		for ( var i = 0; i < plot.series.length; i++) {
			plot.series[i].data = [];
		}

		/*
		 * for ( var i = 0; i < plot.series.length; i++) { for ( var j = 0; j <
		 * plot.series[i].data.length; j++) { try { plot.series[i].data[j][1] =
		 * chartData[i][j]; } catch (e) { } } }
		 */
		// plot.axes.xaxis.reset();
		// plot.axes.xaxis.ticks = ['a','b','c'];
		// plot.series[0].data = [[1,70],[2,71],[3,73]];
		chartData = [ [ [ 1, 70 ], [ 2, 71 ], [ 3, 73 ] ],
				[ [ 1, 80 ], [ 2, 81 ], [ 3, 83 ] ] ];

		for ( var i = 0; i < chartData.length; i++) {
			for ( var j = 0; j < chartData[i].length; j++) {
				// try {
				plot.series[i].data[j] = chartData[i][j];
				// } catch (e) {
				// }
			}
			// plot.series[i].data = chartData[i];
		}

	},

	/**
	 * 将plot图表从容器中销毁。
	 * 
	 * @param containerId
	 *            容器
	 * @param plot
	 *            在容器中的图表
	 */
	releasePlotChart : function(containerId, plot) {
		if (plot) {
			plot.destroy();

			var elementId = '#' + containerId;
			$(elementId).unbind(); // for iexplorer
			$(elementId).empty();

			plot = null;
		}
	},

	applyTheme : function(plot) {
		commonStyle = {
			target : {
				backgroundColor : "transparent"
			},
			grid : {
				backgroundColor : "#ffffff"
			}
		}

		plot.themeEngine.copy('Default', 'common', commonStyle);
		plot.activateTheme('common');
	},

	genImg : function() {
		$('.jqplot-image-container').remove();
		$('.jqplot-image-button').remove();

		if (!jQuery.jqplot.use_excanvas) {
			$('div.jqplot-target').each(
					function() {
						var outerDiv = $(document.createElement('div'));
						var header = $(document.createElement('div'));
						var div = $(document.createElement('div'));

						outerDiv.append(header);
						outerDiv.append(div);

						outerDiv.addClass('jqplot-image-container');
						header.addClass('jqplot-image-container-header');
						div.addClass('jqplot-image-container-content');

						header.html('Right Click to Save Image As...');

						var close = $(document.createElement('a'));
						close.addClass('jqplot-image-container-close');
						close.html('Close');
						close.attr('href', '#');
						close.click(function() {
							$(this).parents('div.jqplot-image-container').hide(
									500);
						})
						header.append(close);

						$(this).after(outerDiv);
						outerDiv.hide();

						outerDiv = header = div = close = null;

						if (!jQuery.jqplot._noToImageButton) {
							var btn = $(document.createElement('button'));
							btn.text('View Plot Image');
							btn.addClass('jqplot-image-button');
							btn.bind('click', {
								chart : $(this)
							}, function(evt) {
								var imgelem = evt.data.chart
										.jqplotToImageElem();
								var div = $(this).nextAll(
										'div.jqplot-image-container').first();
								div.children(
										'div.jqplot-image-container-content')
										.empty();
								div.children(
										'div.jqplot-image-container-content')
										.append(imgelem);
								div.show(500);
								div = null;
							});

							$(this).after(btn);
							btn.after('<br />');
							btn = null;
						}
					});
		}

		$(document).unload(function() {
			$('*').unbind();
		});
	},

	loadChart : function(divId, data, options) {
		var plot = this.chartMap.get(divId);
		if (null != plot) {
			plot = plot.value;// map返回的是整个对象，而非value
			plot.destroy();
			// this.replotChart(plot,data);
		}

		plot = jQuery.jqplot(divId, data, options);
		this.chartMap.put(divId, plot);

		// this.applyTheme(plot);

		return plot;
	},


	loadCommonChart : function(divId, url, params, options, customOptions) {
		if(options == null){
			options = {};
		}
		var chart = this.loadData(url, params);
		if(!chart){
			return;
		}
		var series = chart.series;
		var data = chart.datas;
		var ticks = chart.ticks;

		if(data.length == 0){
			document.getElementById(divId).innerHTML = "";
			if(typeof addActionMessage != 'function'){
				alert("没有数据，无法显示！");
			} else {
				addActionMessage("没有数据，无法显示！");
			}
			return;
		}
		
		var dataOptions = {
			series : series
		};
		options = jQuery.extend(true,{},this.defaultOptions,dataOptions,options, customOptions);
		this.loadChart(divId, data, options);
		//this.genImg();
	},
	
	// 加载折线图表
	loadLineChart : function(divId, url, params, customOptions) {
		var options = jQuery.extend(true,{},this.defaultAxesOptions);
		this.loadCommonChart(divId, url, params, options,customOptions);
	},
	
	// 加载柱形图表
	loadBarChart : function(divId, url, params, customOptions) {
		var options = {
			seriesDefaults : {
				renderer : jQuery.jqplot.BarRenderer
			}
		};
		jQuery.extend(true,options,this.defaultAxesOptions);
		this.loadCommonChart(divId, url, params, options,customOptions);
	},
	
	// 加载饼形图表
	loadPieChart : function(divId, url, params, customOptions) {
		var options = {
			seriesDefaults: {
		        renderer: jQuery.jqplot.PieRenderer,
		        rendererOptions: {
		        	fill: true,
		        	sliceMargin: 0,
		            lineWidth: 5,
		            //dataLabels: 'percentage',
		            dataLabelFormatString : '%.2f%%', 
		            showDataLabels: true
		        }
		      }
		};
		this.loadCommonChart(divId, url, params, options,customOptions);
	},
	
	// 加载圆环形图表
	loadDonutChart : function(divId, url, params, customOptions) {
		var options = {
			 seriesDefaults: {			     
			      renderer:jQuery.jqplot.DonutRenderer,
			      rendererOptions:{			       
			        sliceMargin: 3,			  
			        startAngle: -90,
			        showDataLabels: true,
			        dataLabels: 'value'
			      }
			 }
		};
		this.loadCommonChart(divId, url, params, options,customOptions);
	},
	
	
	 	
	
	
	
	
	
	
	
	
	
	
	// 加载折线图表==============================================
	loadLineChart11 : function(divId, url, params, title) {
		var chart = this.loadData(url, params);
		var series = chart.series;
		var data = chart.datas;
		var ticks = chart.ticks;

		var options = {
			title : title,
			grid : {
				backgroundColor : "#ffffff"
			},
			axesDefaults : {
				labelRenderer : jQuery.jqplot.CanvasAxisLabelRenderer
			},
			axes : {
				xaxis : {
					renderer : jQuery.jqplot.CategoryAxisRenderer,
					tickRenderer : jQuery.jqplot.CanvasAxisTickRenderer,
					tickOptions : {
						angle : 0
					}
				},
				yaxis : {
					autoscale : true
				}
			},
			seriesDefaults : {
				renderer : jQuery.jqplot.BarRenderer,
				showMarker : true,
				pointLabels : {
					show : true
				}
			},
			series : series,

			legend : {
				show : true,
				location : 'e',
				placement : 'outsideGrid'
			},
			highlighter : {
				show : false,
				sizeAdjust : 7.5
			},
			cursor : {
				show : true,
				showTooltip : false,
				zoom : true
			}
		};
		this.loadChart(divId, data, options);
		this.genImg();
	}


}

// JavaScript Document

if(document.getElementById("echarts1")) {
    // 基于准备好的dom，初始化echarts实例
    var myChart1 = echarts.init(document.getElementById("echarts1"));
    window.addEventListener('resize',function(){myChart1.resize();},false);
    // 指定图表的配置项和数据
    var option1 = {
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b}: {c} ({d}%)",
        },
		legend: {
            left: 'center',
            y : 'bottom',
            data: ['180-240分,36.21%','120-180分,29.31%','90-120分,18.97%','70-90分,10.34%','40-70分,5.17%']
        },
        series: [
            {
                type: 'pie',
                name: '选项分布',
                radius: ['50', '60'],
				label: {
					normal: {
						show: false,
					},
				},
                data: [
                    { name: '180-240分,36.21%', value: 21, itemStyle: {normal: {color: '#53bf8a',},},},
                    { name: '120-180分,29.31%', value: 17, itemStyle: {normal: {color: '#ff8d3a',},},},
                    { name: '90-120分,18.97%', value: 11, itemStyle: {normal: {color: '#cc72ee',},},},
                    { name: '70-90分,10.34%', value: 6, itemStyle: {normal: {color: '#6dc6cd',},},},
					{ name: '40-70分,5.17%', value: 3, itemStyle: {normal: {color: '#c6c6c6',},},},
                ],
            },
        ],
    };
    // 使用刚指定的配置项和数据显示图表
    myChart1.setOption(option1);
}

if(document.getElementById("echarts2")) {
    // 基于准备好的dom，初始化echarts实例
    var myChart2 = echarts.init(document.getElementById("echarts2"));
    window.addEventListener('resize',function(){myChart2.resize();},false);
    // 指定图表的配置项和数据
    var option2 = {
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b}: {c} ({d}%)",
        },
		legend: {
            left: 'center',
            y : 'bottom',
            data: ['优秀','良好','一般','及格','不及格']
        },
        series: [
            {
                type: 'pie',
                name: '选项分布',
                radius: ['50', '60'],
				label: {
					normal: {
						show: false,
					},
				},
                data: [
                    {name: '优秀', value: 21, itemStyle: {normal: {color: '#53bf8a',},},},
                    {name: '良好', value: 17, itemStyle: {normal: {color: '#ff8d3a',},},},
                    {name: '一般', value: 11, itemStyle: {normal: {color: '#cc72ee',},},},
                    {name: '及格', value: 6, itemStyle: {normal: {color: '#6dc6cd',},},},
					{name: '不及格', value: 3, itemStyle: {normal: {color: '#c6c6c6',},},},
                ],
            },
        ],
    };
    // 使用刚指定的配置项和数据显示图表
    myChart2.setOption(option2);
}

if(document.getElementById("echarts3")) {
    // 基于准备好的dom，初始化echarts实例
    var myChart3 = echarts.init(document.getElementById("echarts3"));
    window.addEventListener('resize',function(){myChart3.resize();},false);
    // 指定图表的配置项和数据
    var option3 = {
		series: [
			{
				type:'pie',
				radius: ['65', '70'],
				label: {
					normal: {
						show: false,
					},
				},
				data: [
                    {value: 277, itemStyle: {normal: {color: '#0ec587',},},},
                    {value: 23, itemStyle: {normal: {color: '#d8f9ee',},},},
                ],
			}
		]
    };
    // 使用刚指定的配置项和数据显示图表s
    myChart3.setOption(option3);
}

if(document.getElementById("echarts4")) {
    // 基于准备好的dom，初始化echarts实例
    var myChart4 = echarts.init(document.getElementById("echarts4"));
    window.addEventListener('resize',function(){myChart4.resize();},false);
    // 指定图表的配置项和数据
    var option4 = {
		series: [
			{
				type:'pie',
				radius: ['65', '70'],
				label: {
					normal: {
						show: false,
					},
				},
				data: [
                    {value: 277, itemStyle: {normal: {color: '#ff8832',},},},
                    {value: 23, itemStyle: {normal: {color: '#ffecde',},},},
                ],
			}
		]
    };
    // 使用刚指定的配置项和数据显示图表s
    myChart4.setOption(option4);
}

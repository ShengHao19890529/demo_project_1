(function (global, factory) {
  typeof exports === 'object' && typeof module !== 'undefined' ? factory() :
  typeof define === 'function' && define.amd ? define(factory) :
  (factory());
}(this, (function () { 'use strict';

var classCallCheck = function (instance, Constructor) {
  if (!(instance instanceof Constructor)) {
    throw new TypeError("Cannot call a class as a function");
  }
};

var createClass = function () {
  function defineProperties(target, props) {
    for (var i = 0; i < props.length; i++) {
      var descriptor = props[i];
      descriptor.enumerable = descriptor.enumerable || false;
      descriptor.configurable = true;
      if ("value" in descriptor) descriptor.writable = true;
      Object.defineProperty(target, descriptor.key, descriptor);
    }
  }

  return function (Constructor, protoProps, staticProps) {
    if (protoProps) defineProperties(Constructor.prototype, protoProps);
    if (staticProps) defineProperties(Constructor, staticProps);
    return Constructor;
  };
}();

/*
 * @name 生成日历数组
 * @author by wilson wang
 * @config { object } config: {
 *	                    beginDate: 日历开始时间, 默认从今天开始(格式YYYY-MM)
 *                      durationMonth: 日历最大显示几个月, 默认从开始时间往后推12个月
 *                    }
 * @ext 目标数据 [{ title: '2017年11月',
 *								  week: ['一', '二', '三', '四', '五', '六', '日'],
 *                  day: [ { day: 0, lazy: fasle, miss: false }, 
 *											   { day: 1, lazy: false, miss: false, time: 2017-11-01 }] // 0代表占位。
 *							 }]
 */

var Calendar = function () {
	function Calendar(config) {
		classCallCheck(this, Calendar);

		var config = config || {};
		this.beginDate = config.beginDate || moment().format('YYYY-MM');
		this.durationMonth = config.durationMonth || 12;
		this.data = [];
		this.initData();
	}

	createClass(Calendar, [{
		key: 'initData',
		value: function initData() {
			var that = this;

			function initItem(i) {
				var time = moment(that.beginDate, 'YYYY-MM');
				var title = time.add(i, 'months').format('YYYY年MM月');
				var week = [{ name: '一', lazy: false }, { name: '二', lazy: false }, { name: '三', lazy: false }, { name: '四', lazy: false }, { name: '五', lazy: false }, { name: '六', lazy: true }, { name: '日', lazy: true }];

				// 生成每月有日期的天数
				var maxDay = time.daysInMonth();
				var dataDay = [];

				var now = moment();
				var nowStr = now.format('YYYY-MM-DD');

				for (var i = 0; i < maxDay; i++) {
					// 从每月的第一天开始递增
					var time = moment(title + '01', 'YYYY年MM月DD日').add(i, 'days');

					// 是否是周末
					var lazy = time.weekday();
					if (lazy == 6 || lazy == 0) {
						lazy = true;
					} else {
						lazy = false;
					}

					// 是否比现在小
					var miss = time.isBefore(now, 'day') ? true : false;

					var day = time.format('D');

					// 今天 - 特别展示
					var timeStr = time.format('YYYY-MM-DD');
					if (timeStr == nowStr) {
						day = '今天';
					}

					dataDay.push({
						day: day,
						lazy: lazy,
						miss: miss,
						time: time.format('YYYY-MM-DD')
					});
				}

				// 生成每月开始的空白天数
				var beginDayWeek = moment(title + '01日', 'YYYY年MM月DD日').weekday();
				var beginBlaceDay = [];
				var count = beginDayWeek ? beginDayWeek - 1 : 6;
				for (var i = 0; i < count; i++) {
					beginBlaceDay.push({
						day: 0,
						lazy: false,
						miss: true
					});
				}

				var day = beginBlaceDay.concat(dataDay);
				return {
					title: title,
					week: week,
					day: day
				};
			}

			for (var i = 0; i <= this.durationMonth; i++) {
				this.data.push(initItem(i));
			}
		}
	}]);
	return Calendar;
}();

var cityTab = 'cityTab'; // 首页 -> 城市列表页   当前被选择的城市数据
var orderParams = 'orderParams'; // 首页 -> 预订航班页   预订查询参数
var selectCity = 'selectCity'; // 城市列表页 -> 首页   更改被选择的城市数据
var dateTab = 'dateTab'; // 首页 -> 日历页       当前被选择的日期数据
var selectDate = 'selectDate'; // 日历页 -> 首页       更改被选择的日历数据
var user = 'user'; // 登录页 -> 其他页     当前登录的用户详情

var store = {
	cityTab: cityTab,
	orderParams: orderParams,
	selectCity: selectCity,
	dateTab: dateTab,
	selectDate: selectDate,
	user: user
};

new Vue({
	directives: {
		contact: {
			bind: function bind(el) {
				setTimeout(function () {
					var contactList = new Contact({
						id: '#date-list'
					});
				}, 0);
			}
		}
	},
	el: '#calander-list',
	data: function data() {
		var calendar = new Calendar();
		return {
			calendarList: calendar.data,
			selectDay: {}
		};
	},
	created: function created() {
		var dayInfo = sessionStorage.getItem(store.dateTab);
		if (dayInfo) {
			this.selectDay = JSON.parse(dayInfo);
		}
	},

	methods: {
		back: function back() {
			window.history.go(-1);
		},
		selectItem: function selectItem(item) {
			if (item.miss) {
				return false;
			}
			this.selectDay.time = item.time;

			sessionStorage.setItem(store.selectDate, JSON.stringify(this.selectDay));
			window.history.go(-1);
		}
	}
});

})));

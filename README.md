# ncalendar
日历样式的日期选择控件，基于NCalendar做了封装
参考：https://github.com/yannecer/NCalendar

使用方法：
 NCalendar.newInstance(this).show(new NCalendar.OnDateSelectedListener() {
 
            @Override
            public void onDateSelect(Date date, String dateStr) {
            
                String yymMddByDate = DateUtil.getYYMMddByDate(date);
                tv.setText(yymMddByDate);
                
            }
        }, new NCalendar.OnInitListener() {
        
            @Override
            public void onInit(MonthCalendar monthCalendar) {
            
                monthCalendar.setDateInterval("1999-01-01","2020-12-31");//设置日期区间
                monthCalendar.jumpDate("2020-10-01");//选中日期
                
                }
        });

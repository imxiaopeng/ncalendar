# ncalendar
日历样式的日期选择控件，基于NCalendar做了封装
参考：https://github.com/yannecer/NCalendar

该组件已经集成至豫电助手主包中 # ncalendar.aar
集成步骤：
1.从ncalendar.aar中提取ncalendar.jar
2.复制ncalendar.jar到插件module的providedlibs目录中，sync project
3.恭喜，你已集成完毕！

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
                monthCalendar.jumpDate("2020-10-01");//设置默认选中日期
                
                }
        });
![image](https://user-images.githubusercontent.com/18377025/119327750-e7dc2980-bcb5-11eb-9713-4b6e57d5b1bc.png)

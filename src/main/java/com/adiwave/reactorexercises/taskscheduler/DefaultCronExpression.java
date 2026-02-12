package com.adiwave.reactorexercises.taskscheduler;

import java.time.ZonedDateTime;

public class DefaultCronExpression implements CronExpression {
    
    private final CronField seconds;
    private final CronField minutes;
    private final CronField hours;
    private final CronField dayOfMonth;
    private final CronField month;
    private final CronField dayOfWeek;
    
    
    public DefaultCronExpression(String expression) {
        String[] fields = expression.split("\\s+");
        
        if(fields.length != 6) {
            throw new IllegalArgumentException("Invalid cron expression, cron must have 6 fields");
        }
        
        this.seconds  = CronFieldParser.parse(fields[0], 0, 59);
        this.minutes  = CronFieldParser.parse(fields[1], 0, 59);
        this.hours    = CronFieldParser.parse(fields[2], 0, 23);
        this.dayOfMonth = CronFieldParser.parse(fields[3], 1, 31);
        this.month = CronFieldParser.parse(fields[4], 1, 12);
        this.dayOfWeek = CronFieldParser.parse(fields[5], 1, 7);
    }
    
    @Override
    public ZonedDateTime nextAfter(ZonedDateTime from) {
        ZonedDateTime next = from.plusSeconds(1);
        
        while(true) {
            // move to the next month
            if(!month.matches(next.getMonthValue())) {
                next = next.plusMonths(1)
                        .withDayOfMonth(1)
                        .withHour(0).withMinute(0).withSecond(0);
                continue;
            }
            
            if(!dayOfMonth.matches(next.getDayOfMonth()) || 
               !dayOfWeek.matches(next.getDayOfWeek().getValue())) {
                
                next = next.plusDays(1)
                        .withHour(0).withMinute(0).withSecond(0);
                
                continue;
            }
            
            if(!hours.matches(next.getHour())) {
                next = next.plusHours(1)
                        .withMinute(0).withSecond(0);
                continue;
            }
            
            if(!minutes.matches(next.getMinute())) {
                next = next.plusMinutes(1)
                        .withSecond(0);
                continue;   
            }
            
            if(!seconds.matches(next.getSecond())) {
                next = next.plusSeconds(1);
                continue;
            }
            
            return next;
        }
    }
}

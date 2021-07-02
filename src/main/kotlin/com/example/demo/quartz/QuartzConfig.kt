//package com.example.demo.quartz
//
//import org.quartz.CronScheduleBuilder.cronSchedule
//import org.quartz.JobBuilder
//import org.quartz.JobDetail
//import org.quartz.Trigger
//import org.quartz.TriggerBuilder
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import java.util.*
//
//@Configuration
//class QuartzConfig {
//
//    @Bean
//    fun jobDetail(): JobDetail {
//        return JobBuilder.newJob().ofType(CollectJob::class.java)
//            .storeDurably()
//            .withIdentity("job_detail")
//            .withDescription("Invoke Tistory Job service")
//            .build()
//    }
//
//    @Bean
//    fun trigger(@Autowired jobDetail: JobDetail): Trigger {
//        return TriggerBuilder.newTrigger()
//            .forJob(jobDetail)
//            .withIdentity("job_trigger")
//            .withSchedule(
//                cronSchedule("0 0 9 * * ?")
//                    .inTimeZone(TimeZone.getTimeZone("Asia/Seoul"))
//            ).build()
//    }
//}